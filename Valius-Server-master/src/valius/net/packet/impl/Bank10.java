package valius.net.packet.impl;

import java.util.Objects;

import valius.content.tradingpost.Listing;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.Smithing;
import valius.model.entity.player.skills.crafting.JewelryMaking;
import valius.model.items.Item;
import valius.model.multiplayer_session.MultiplayerSession;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.trade.TradeSession;
import valius.model.shops.TabbedShop;
import valius.net.packet.PacketType;
import valius.world.World;

/**
 * Bank 10 Items
 **/
public class Bank10 implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readUnsignedWordBigEndian();
		int removeId = c.getInStream().readUnsignedWordA();
		int removeSlot = c.getInStream().readUnsignedWordA();
		
		if (c.debugMessage)
			c.sendMessage("Bank 10: interfaceid: "+interfaceId+", removeSlot: "+removeSlot+", removeID: " + removeId);
		
		
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.viewingLootBag || c.addingItemsToLootBag) {
			if (c.getLootingBag().handleClickItem(removeId, 10)) {
				return;
			}
		}
		if (c.viewingRunePouch) {
			if (c.getRunePouch().handleClickItem(removeId, 10, interfaceId)) {
				return;
			}
		}

		if(interfaceId >= TabbedShop.BASE_TAB_INVENTORY && interfaceId <= TabbedShop.MAX_TAB_INVENTORY) {
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 5);
			return;
		}
		switch (interfaceId) {
		
		case 48021:
			Listing.buyListing(c, removeSlot, 10);
		break;
	
		case 48500: //Listing interface
			if(c.insidePost) {
				int amount = 10;
				if(c.getItems().getItemAmount(removeId) < 10)
					amount = c.getItems().getItemAmount(removeId);
				Listing.offerSelectedItem(c, removeId, amount);
			}
		break;
		
		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(c, removeId, 10);
			break;
		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			Smithing.readInput(c.getSkills().getLevel(Skill.SMITHING), Integer.toString(removeId), c, 27);
			break;
		case 1688:
			c.getPA().useOperate(removeId);
			break;
		case 3900:
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 5);
			break;
		case 64016:
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 5);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 5);
			break;

		case 5064:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot do this whilst trading.");
				return;
			}
			DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, 10, true);
			}
			if (c.inSafeBox) {
				if (!c.pkDistrict && removeId != 13307) {
					c.sendMessage("You cannot do this right now.");
					return;
				}
				c.getSafeBox().deposit(removeId, 10);
			}
			break;

		case 5382:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot do this whilst trading.");
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().removeItem(removeId, 10);
				return;
			}
			c.getItems().removeFromBank(removeId, 10, true);
			break;

		case 3322:
			MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new Item(removeId, 10));
			}
			break;

		case 3415:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new Item(removeId, 10));
			}
			break;

		case 6669:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new Item(removeId, 10));
			}
			break;

		}
	}

}
