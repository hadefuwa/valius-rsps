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
 * Remove Item
 **/
public class RemoveItem implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readUnsignedWordA();
		int removeSlot = c.getInStream().readUnsignedWordA();
		int removeId = c.getInStream().readUnsignedWordA();
		
		if (c.viewingLootBag || c.addingItemsToLootBag) {
			if (c.getLootingBag().handleClickItem(removeId, 1)) {
				return;
			}
		}
		if (c.getRunePouch().handleClickItem(removeId, 1, interfaceId)) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.debugMessage)
			c.sendMessage("Bank 1: interfaceid: "+interfaceId+", removeSlot: "+removeSlot+", removeID: " + removeId);
		
		if(interfaceId >= TabbedShop.BASE_TAB_INVENTORY && interfaceId <= TabbedShop.MAX_TAB_INVENTORY) {
			c.getShops().buyFromShopPrice(removeId, removeSlot);
			return;
		}
		switch (interfaceId) {
			case 41609:
				switch(c.boxCurrentlyUsing) {
					case 13346: //ultra rare
						c.getUltraMysteryBox().reward();
						break;
					case 33668://blood mbox
						c.getBloodMysteryBox().reward();
						break;
					case 33717://event box
						c.getEventMysteryBox().reward();
						break;
					case 33669://Pet mbox
						c.getPetMysteryBox().reward();
						break;
					case 33269: //valius mbox
						c.getValiusMysteryBox().reward();
						break;
					case 33285: //Easter mbox
						c.getEasterMysteryBox().reward();
						break;
					case 33961: //Easter mbox
						c.getChristmasBox().reward();
						break;
					case 33756:
						c.getHalloweenMysteryBox().reward();
						break;
					case 33266://valentines box
						c.getValentinesBox().reward();
						break;
						
					case 33422://stargaze box
						c.getStarBox().reward();
						break;
						
				}
				break;
		case 48021:
			Listing.buyListing(c, removeSlot, 1);
		break;
		
		case 48847:
			Listing.cancelListing(c, removeSlot, removeId);
		break;
		
		case 48500: //Listing interface
			if(c.insidePost) {
				Listing.offerSelectedItem(c, removeId, 1);
			}
		break;
		
		case 35007:
			c.getSafeBox().withdraw(removeId, c.getItems().isStackable(removeId) ? 10000000 : 1);
			break;
		case 7423:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot add items to the deposit box whilst trading.");
				return;
			}
			c.getItems().addToBank(removeId, 1, false);
			c.getItems().resetItems(7423);
			break;
		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(c, removeId, 1);
			break;
		case 1688:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.TRADE).finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot remove items whilst trading, trade declined.");
				return;
			}
			DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("Your actions have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			c.getItems().removeItem(removeId, removeSlot);
			break;

		case 5064:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot add items to the bank whilst trading.");
				return;
			}
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, 1, true);
			}
			if (c.inSafeBox) {
				if (!c.pkDistrict && removeId != 13307) {
					c.sendMessage("You cannot do this right now.");
					return;
				}
				c.getSafeBox().deposit(removeId, 1);
			}
			break;
		case 5382:
			if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				c.sendMessage("You cannot remove items from the bank whilst trading.");
				return;
			}
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().removeItem(removeId, 1);
				return;
			}
			c.getItems().removeFromBank(removeId, 1, true);
			break;
			
			/**
			 * Shop value
			 */
			
		case 64016:
			c.getShops().buyFromShopPrice(removeId, removeSlot);
			break;

		case 3900:
			c.getShops().buyFromShopPrice(removeId, removeSlot);
			break;

		case 3823:
			c.getShops().sellToShopPrice(removeId, removeSlot);
			break;

		case 3322:
			MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new Item(removeId, 1));
			}
			break;

		case 3415:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new Item(removeId, 1));
			}
			break;

		case 6669:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new Item(removeId, 1));
			}
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			Smithing.readInput(c.getSkills().getLevel(Skill.SMITHING), Integer.toString(removeId), c, 1);
			break;

		}
	}

}
