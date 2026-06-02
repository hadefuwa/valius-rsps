package valius.net.packet.impl;

import java.util.Objects;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.net.packet.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		if(tradeId < 0 || tradeId >= PlayerHandler.players.length) {
			return;
		}
		Player requested = PlayerHandler.players[tradeId];
		
		if(requested == null) {
			return;
		}
		
		c.getPA().resetFollow();
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.stopMovement();
			c.sendMessage("@cr10@You cannot trade from here.");
			return;
		}
		if (c.inRaidLobby() || c.inXericLobby()) {
			c.stopMovement();
			c.sendMessage("@cr10@You cannot trade within minigame lobbies.");
			return;
		}
		if (Boundary.isIn(c, Boundary.DUEL_ARENA)) {
			c.sendMessage("You cannot trade whilst inside the duel arena.");
			return;
		}
		if (Objects.equals(requested, c)) {
			c.sendMessage("You cannot trade yourself.");
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (requested.getInterfaceEvent().isActive()) {
			c.sendMessage("That player needs to finish what they're doing.");
			return;
		}
		if (c.getMode().isGroupIronman()) {
			if (c.inEdgeville() && c.getGroupIronman().hasPendingRequest()) {
				c.getGroupIronman().getRequest().accept();
				return;
			}
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		if (c.getTrade().requestable(requested)) {
			c.getTrade().request(requested);
			return;
		}
	}

}