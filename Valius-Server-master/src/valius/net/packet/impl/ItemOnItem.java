package valius.net.packet.impl;

/**
 * @author Ryan / Lmctruck30
 */

import java.util.Objects;

import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.UseItem;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.world.World;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		if (usedWithSlot > c.playerItems.length - 1 || usedWithSlot < 0 || itemUsedSlot > c.playerItems.length - 1 || itemUsedSlot < 0) {
			return;
		}
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		if (useWith == -1 || itemUsed == -1) {
			return;
		}
		if (!c.getItems().playerHasItem(useWith, 1) || !c.getItems().playerHasItem(itemUsed, 1)) {
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
		c.getSkilling().stop();
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		Item firstItem = c.getItems().getItemAtSlot(itemUsedSlot);
		Item secondItem = c.getItems().getItemAtSlot(usedWithSlot);
		if (c.getInstance() != null) {
			if (c.getInstance().useItemOnItem(c, firstItem, secondItem))
				return;
		}
		c.getQuestManager().onItemCombine(firstItem, secondItem);
		UseItem.ItemonItem(c, itemUsed, useWith, itemUsedSlot, usedWithSlot);
	}

}
