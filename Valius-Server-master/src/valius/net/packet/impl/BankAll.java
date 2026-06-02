package valius.net.packet.impl;

import java.util.Objects;

import valius.content.tradingpost.ListedItem;
import valius.content.tradingpost.Listing;
import valius.content.tradingpost.Sale;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemUtility;
import valius.model.items.bank.BankItem;
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
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int removeSlot = c.getInStream().readUnsignedWordA();
		int interfaceId = c.getInStream().readUnsignedWord();
		int removeId = c.getInStream().readUnsignedWordA();
		if (c.debugMessage)
			c.sendMessage("Bank All: interfaceid: "+interfaceId+", removeSlot: "+removeSlot+", removeID: " + removeId);
		
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.viewingLootBag || c.addingItemsToLootBag) {
			if (c.getLootingBag().handleClickItem(removeId, c.getItems().getItemAmount(removeId))) {
				return;
			}
		}
		if (c.viewingRunePouch) {
			if (c.getRunePouch().handleClickItem(removeId, Integer.MAX_VALUE, interfaceId)) {
				return;
			}
		}
		if(interfaceId >= TabbedShop.BASE_TAB_INVENTORY && interfaceId <= TabbedShop.MAX_TAB_INVENTORY) {
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 10);
			return;
		}
		switch (interfaceId) {
		
		case 48021:
			Listing.buyListing(c, removeSlot, Integer.MAX_VALUE);
		break;
	
		case 48500: //Listing interface
			if(c.insidePost) {
				Listing.offerSelectedItem(c, removeId, c.getItems().getItemAmount(removeId));
			}
		break;
		
		case 3900:
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 10);
			break;
			
		case 64016:
			c.getShops().buyItem(interfaceId, removeId, removeSlot, 10);
			break;

		case 3823:
			c.getShops().sellItem(removeId, removeSlot, 10);
			break;

		case 5064:
			if (c.inTrade) {
				return;
			}
			DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
			if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
					&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
				c.sendMessage("You have declined the duel.");
				duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
				duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				return;
			}
			if (c.isBanking) {
				c.getItems().addToBank(removeId, c.getItems().getItemAmount(removeId), true);
			}
			if (c.inSafeBox) {
				if (!c.pkDistrict && removeId != 13307) {
					c.sendMessage("You cannot do this right now.");
					return;
				}
				c.getSafeBox().deposit(removeId, c.getItems().getItemAmount(removeId));
			}
			break;

		case 5382:
			if (!c.isBanking) {
				return;
			}
			if (c.getItems().freeSlots() == 0 && !c.getItems().playerHasItem(removeId)) {
				c.sendMessage("There is not enough space in your inventory.");
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().removeItem(removeId, c.getBank().getCurrentBankTab().getItemAmount(new BankItem(removeId + 1)));
				return;
			}
			c.getItems().removeFromBank(removeId, c.getBank().getCurrentBankTab().getItemAmount(new BankItem(removeId + 1)), true);
			break;

		case 3322:
			MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession || session instanceof DuelSession) {
				session.addItem(c, new Item(removeId, c.getItems().getItemAmount(removeId)));
			}
			break;

		case 3415:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof TradeSession) {
				session.removeItem(c, removeSlot, new Item(removeId, Integer.MAX_VALUE));
			}
			break;

		case 6669:
			session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c);
			if (Objects.isNull(session)) {
				return;
			}
			if (session instanceof DuelSession) {
				session.removeItem(c, removeSlot, new Item(removeId, Integer.MAX_VALUE));
			}
			break;

		case 7295:
			if (ItemUtility.itemStackable[removeId]) {
				c.getItems().addToBank(c.playerItems[removeSlot], c.playerItemsN[removeSlot], false);
				c.getItems().resetItems(7423);
			} else {
				c.getItems().addToBank(c.playerItems[removeSlot], c.getItems().itemAmount(c.playerItems[removeSlot]), false);
				c.getItems().resetItems(7423);
			}
			break;

		}
	}

}
