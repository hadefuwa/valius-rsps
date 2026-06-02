package valius.net.packet.impl;

import java.util.Objects;

import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

/**
 * Magic on items
 **/
public class MagicOnItems implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int slot = c.getInStream().readSignedWord();
		int itemId = c.getInStream().readSignedWordA() & 0xFFFF;
		@SuppressWarnings("unused")
		int junk = c.getInStream().readSignedWord();
		int spellId = c.getInStream().readSignedWordA();

		c.usingMagic = true;
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
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
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		c.getPA().magicOnItems(slot, itemId, spellId);
		c.usingMagic = false;

		if (c.getRights().isOrInherits(Right.OWNER)) {
			Misc.println(c.playerName + " - Magic on item spellId: " + spellId);
		}

	}

}
