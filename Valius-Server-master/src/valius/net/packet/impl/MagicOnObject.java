package valius.net.packet.impl;

import java.util.Objects;
import java.util.Optional;

import valius.clip.Region;
import valius.clip.WorldObject;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.crafting.OrbCharging;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.world.World;

public class MagicOnObject implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
//		int x = c.getInStream().readSignedWordBigEndian();
//		int y = c.getInStream().readSignedWordBigEndianA();
//		int magicId = c.getInStream().readUnsignedWord();
		
//		c.objectX = c.getInStream().readSignedWordBigEndianA();
//		c.objectId = c.getInStream().readUnsignedWord();
//		c.objectY = c.getInStream().readUnsignedWordA();
		
		int x = c.getInStream().readSignedWordBigEndian();
		int magicId = c.getInStream().readUnsignedWord();
		int y = c.getInStream().readUnsignedWordA();
		int objectId = c.getInStream().readInteger();
		int objectType = c.getInStream().readUnsignedByte();

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
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
				MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		
		Optional<WorldObject> worldObject = Region.getWorldObject(objectId, x, y, c.getHeight(), objectType);
		
		if(!worldObject.isPresent())
			return;
		
		c.turnPlayerTo(x, y);

		
		c.usingMagic = true;
		
		switch (objectId) {
		case 2153:
		case 2152:
		case 2151:
		case 2150:
			OrbCharging.chargeOrbs(c, magicId, objectId);
			break;
		}

	}

}
