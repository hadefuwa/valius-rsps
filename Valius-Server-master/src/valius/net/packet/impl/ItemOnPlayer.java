package valius.net.packet.impl;

import java.util.Objects;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

public class ItemOnPlayer implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		@SuppressWarnings("unused")
		int interfaceId = c.getInStream().readUnsignedWordBigEndianA();
		int playerIndex = c.getInStream().readUnsignedWord();
		int itemId = c.getInStream().readUnsignedWord();
		int slotId = c.getInStream().readUnsignedWordBigEndian();
		c.setItemOnPlayer(null);
		if (c.teleTimer > 0)
			return;
		if (playerIndex > PlayerHandler.players.length) {
			return;
		}
		if (slotId > c.playerItems.length) {
			return;
		}
		if (PlayerHandler.players[playerIndex] == null) {
			return;
		}
		if (!c.getItems().playerHasItem(itemId, 1, slotId)) {
			return;
		}
		Player other = PlayerHandler.players[playerIndex];
		if (other == null) {
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (other.getBankPin().requiresUnlock()) {
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (other.getInterfaceEvent().isActive()) {
			c.sendMessage("This player is busy.");
			return;
		}
		if (Misc.distanceBetween(c, other) > 15) {
			c.sendMessage("You need to move closer to do this.");
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
		c.setItemOnPlayer(other);
		if (c.getRights().isOrInherits(Right.ADMINISTRATOR) && itemId != 5733 && itemId != 6713) {
			c.sendMessage("You gave " + other.playerName + " some " + ItemAssistant.getItemName(itemId) + ".");
			other.sendMessage("You were given some " + ItemAssistant.getItemName(itemId) + " from " + c.playerName + ".");
			other.getItems().addItem(itemId, c.getItems().isStackable(itemId) ? c.getItems().getItemAmount(itemId) : 1);
			c.getItems().deleteItem(itemId, c.getItems().isStackable(itemId) ? c.getItems().getItemAmount(itemId) : 1);
		}
		Item item = c.getItems().getItemAtSlot(slotId);
		if(item == null || item.getId() == -1)
			return;
		c.getQuestManager().onItemOnPlayer(item, other);
		switch (itemId) {
		case 2697:
			if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (other.inWild() || other.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(other)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4006, -1);
			}
			break;
		case 2698:
			if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (other.inWild() || other.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(other)) {
				return;
			}
			if (c.getItems().playerHasItem(2698, 1)) {
				c.getDH().sendDialogues(4007, -1);
			}
			break;
		case 2699:
			if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (other.inWild() || other.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(other)) {
				return;
			}
			if (c.getItems().playerHasItem(2699, 1)) {
				c.getDH().sendDialogues(4008, -1);
			}
			break;
		case 2700:
			if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (other.inWild() || other.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(other)) {
				return;
			}
			if (c.getItems().playerHasItem(2700, 1)) {
				c.getDH().sendDialogues(4009, -1);
			}
			break;
		case 962:
			if (other.connectedFrom.equalsIgnoreCase(c.connectedFrom)) {
				c.sendMessage("You cannot use this on another player that is on the same host as you.");
				return;
			}
			c.turnPlayerTo(other.getX(), other.getY());
			c.getDH().sendDialogues(612, -1);
			break;
			
		case 13345:
			if (other.connectedFrom.equalsIgnoreCase(c.connectedFrom)) {
				c.sendMessage("You cannot use this on another player that is on the same host as you.");
				return;
			}
			if (!c.getItems().playerHasItem(13345)) {
				return;
			}
			if (other.getItems().freeSlots() == 0) {
				c.sendMessage("This player does not have any space.");
				return;
			}
			c.turnPlayerTo(other.getX(), other.getY());
			c.getItems().deleteItem(13345, 1);
			int random = Misc.random(4);
			switch (random) {
			case 0:
			case 1:
			case 2:
				c.sendMessage("How unlucky, this present was empty.");
				other.sendMessage(""+c.playerName+" used a present on you and it was empty!");
				break;
				
			case 3:
				other.getItems().addItem(13343, 1);
				c.sendMessage("How unlucky, "+other.playerName+" got the Black Santa Hat!");
				other.sendMessage(""+c.playerName+" used a present on you and you got a Black Santa Hat!");
				break;
				
			case 4:
				c.getItems().addItem(13343, 1);
				c.sendMessage("You were lucky enough to find a Black Santa Hat in the present!");
				other.sendMessage(""+c.playerName+" used a present on you and got a Black Santa Hat!");
				break;
		}
			break;
			
		case 13346:
			if (other.connectedFrom.equalsIgnoreCase(c.connectedFrom)) {
				c.sendMessage("You cannot use this on another player that is on the same host as you.");
				return;
			}
			if (!c.getItems().playerHasItem(13346)) {
				return;
			}
			if (other.getItems().freeSlots() == 0) {
				c.sendMessage("This player does not have any space.");
				return;
			}
			c.turnPlayerTo(other.getX(), other.getY());
			c.getItems().deleteItem(13346, 1);
			int random_hat = Misc.random(4);
			switch (random_hat) {
				case 0:
				case 1:
				case 2:
					c.sendMessage("How unlucky, this present was empty.");
					other.sendMessage(""+c.playerName+" used a present on you and it was empty!");
					break;
					
				case 3:
					other.getItems().addItem(13344, 1);
					c.sendMessage("How unlucky, "+other.playerName+" got the Inverted Santa Hat!");
					other.sendMessage(""+c.playerName+" used a present on you and you got a Inverted Santa Hat!");
					break;
					
				case 4:
					c.getItems().addItem(13344, 1);
					c.sendMessage("You were lucky enough to find a Inverted Santa Hat in the present!");
					other.sendMessage(""+c.playerName+" used a present on you and got a Inverted Santa Hat!");
					break;
			}
			break;
		}
	}
}