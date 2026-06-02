package valius.net.packet.impl;

import java.util.Optional;

import valius.model.Location;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.skills.SkillHandler;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.duel.DuelSessionRules.Rule;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		c.nextChat = 0;
		c.dialogueOptions = 0;
		c.homeTeleport = 50;
		if (!c.inWild() && c.teleBlockLength > 0) {
			c.getPA().resetTb();
		}
		if (c.isDead || c.getHealth().getAmount() <= 0) {
			c.sendMessage("You are dead you cannot walk.");
			return;
		}
		if (c.isIdle) {
			if (c.debugMessage)
				c.sendMessage("You are no longer in idle mode.");
			c.isIdle = false;
		}
		if (!c.getPlayerAction().canWalk()) {
			return;
		}
		if (c.isForceMovementActive()) {
			return;
		}
		if (!c.inClanWars() && !c.inClanWarsSafe() && c.pkDistrict) {
			c.sendMessage("You did not leave the district properly, therefore your items have been deleted.");
			c.getItems().deleteAllItems();
		}
		if (c.rottenPotatoOption != "") {
			c.rottenPotatoOption = "";
		}
		if (c.getInferno() != null && c.getInferno().cutsceneWalkBlock)
			return;
		if (c.morphed) {
			c.sendMessage("You cannot do this now.");
			return;
		}
		if (c.getCurrentCombination().isPresent()) {
			c.setCurrentCombination(Optional.empty());
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		
		if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			c.sendMessage("You must decline the trade to start walking.");
			return;
		}
		DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (session != null && session.getStage().getStage() == MultiplayerSessionStage.FURTHER_INTERATION && !Boundary.isIn(c, Boundary.DUEL_ARENA)) {
			if (session.getRules().contains(Rule.NO_MOVEMENT)) {
				Player opponent = session.getOther(c);
				if (Boundary.isIn(opponent, session.getArenaBoundary())) {
					c.getPA().movePlayer(opponent.getX(), opponent.getY() - 1, 0);
				} else {
					int x = session.getArenaBoundary().getMinimumX() + 6 + Misc.random(12);
					int y = session.getArenaBoundary().getMinimumY() + 1 + Misc.random(11);
					c.getPA().movePlayer(x, y, 0);
					opponent.getPA().movePlayer(x, y - 1, 0);
				}
			} else {
				c.getPA().movePlayer(session.getArenaBoundary().getMinimumX() + 6 + Misc.random(12), session.getArenaBoundary().getMinimumY() + 1 + Misc.random(11), 0);
			}
			return;
		}
		if (Boundary.isIn(c, Boundary.DUEL_ARENA)) {
			if (session == null) {
				c.getPA().movePlayer(3362, 3264, 0);
				return;
			}
			if (session.getRules().contains(Rule.NO_MOVEMENT)) {
				c.sendMessage("Movement has been disabled for this duel.");
				return;
			}
		}
		if (session != null && session.getStage().getStage() > MultiplayerSessionStage.REQUEST && session.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("You have declined the duel.");
			session.getOther(c).sendMessage("The challenger has declined the duel.");
			session.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		// if (c.settingBet || c.settingMax || c.settingMin) {
		// Dicing.resetDicing(c);
		// }
//		if (c.hasNpc) {
//			if (c.isRunning || c.isRunning2) {
//				c.isRunning = false;
//				c.isRunning2 = false;
//				c.getPA().setConfig(173, 0);
//				c.sendMessage("You can not run with a pet. You are now walking.");
//				return;
//			}
//		}
		
		if (Boundary.isIn(c, Boundary.ICE_PATH)) {
			if (c.isRunning || c.isRunning2) {
				c.isRunning = false;
				c.isRunning2 = false;
				c.getPA().setConfig(173, 0);
				return;
			}
		}

		if (c.canChangeAppearance) {
			c.canChangeAppearance = false;
		}
		// if (c.getBarrows().cantWalk) {
		// c.getBarrows().challengeMinigame();
		// return;
		// }
		if (c.getSkilling().isSkilling()) {
			c.getSkilling().stop();
		}
		c.getPA().resetVariables();
		SkillHandler.isSkilling[12] = false;
		if (c.teleporting) {
			c.startAnimation(65535);
			c.teleporting = false;
			c.isRunning = false;
			c.gfx0(-1);
			c.startAnimation(-1);
		}
		c.walkingToItem = false;
		c.isWc = false;
		c.clickNpcType = 0;
		c.resetInteractingObject();
		if (c.isBanking)
			c.isBanking = false;
		if (c.tradeStatus >= 0) {
			c.tradeStatus = 0;
		}
		if (packetType == 248 || packetType == 164) {
			c.clickNpcType = 0;
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			if (c.followId > 0 || c.followId2 > 0)
				c.getPA().resetFollow();
		}
		c.getPA().removeAllWindows();
		if (c.stopPlayerSkill) {
			SkillHandler.resetPlayerSkillVariables(c);
			c.stopPlayerSkill = false;
		}

		if (c.teleTimer > 0) {
			if (PlayerHandler.players[c.playerIndex] != null
					&& c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(), PlayerHandler.players[c.playerIndex].getY(), 1) && packetType != 98) {
				c.playerIndex = 0;
			} else {
				// c.sendMessage("Stop.");
				if (packetType != 98) {
					c.playerIndex = 0;
				}

			}
			return;
		}

		if (c.freezeTimer > 0) {
			if (PlayerHandler.players[c.playerIndex] != null
					&& c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(), PlayerHandler.players[c.playerIndex].getY(), 1) && packetType != 98) {
				c.playerIndex = 0;
			} else {
				c.sendMessage("A magical force stops you from moving.");
				if (packetType != 98) {
					c.playerIndex = 0;
				}

			}
			return;
		}
		if (System.currentTimeMillis() - c.lastSpear < 3000) {
			c.sendMessage("You have been stunned.");
			c.playerIndex = 0;
			return;
		}
		if (packetType == 98) {
			c.mageAllowed = true;
		}
		if (c.respawnTimer > 3) {
			return;
		}
		if (c.inTrade) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			c.getInterfaceEvent().draw();
			return;
		}
		c.setInterfaceOpen(-1);
		if (packetType == 248) {
			packetSize -= 14;
		}
//		c.newWalkCmdSteps = (packetSize - 5) / 2;
//		if (++c.newWalkCmdSteps > c.walkingQueueSize) {
//			c.newWalkCmdSteps = 0;
//			return;
//		}
//		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
//		int firstStepX = c.getInStream().readSignedWordBigEndianA() - c.getMapRegionX() * 8;
//		for (int i = 1; i < c.newWalkCmdSteps; i++) {
//			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
//			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
//		}
//		int firstStepY = c.getInStream().readSignedWordBigEndian() - c.getMapRegionY() * 8;
//		c.setNewWalkCmdIsRunning((c.getInStream().readSignedByteC() == 1));
//		for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
//			c.getNewWalkCmdX()[i1] += firstStepX;
//			c.getNewWalkCmdY()[i1] += firstStepY;
//		}
		
		boolean teleportable = c.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER, Right.GAME_DEVELOPER);
		
		final int steps = (packetSize - 5) / 2;
		if (steps < 0) {
			return;
		}
		final int firstStepX = c.getInStream().readSignedWordBigEndianA(); //- c.getMapRegionX() * 8;
		final int[][] path = new int[steps][2];
		for (int i = 0; i < steps; i++) {
			path[i][0] = c.getInStream().readSignedByte();
			path[i][1] = c.getInStream().readSignedByte();
		}
		final int firstStepY = c.getInStream().readSignedWordBigEndian(); //- c.getMapRegionX() * 8;
		final boolean teleport = c.getInStream().readSignedByteC() == 1 && teleportable;
		final Location[] locations = new Location[steps + 1];
		locations[0] = new Location(firstStepX, firstStepY, c.getLocation().getZ());
		for (int i = 0; i < steps; i++) {
			locations[i + 1] = new Location(path[i][0] + firstStepX, path[i][1] + firstStepY, c.getLocation().getZ());
		}
		
		//The ending location
		Location end = locations[locations.length - 1];
		
		if (!teleport) {
			if (c.getLocation().getDistance(end) >= 64) {
				System.out.println("Invalid walk distance: " + c.getLocation().getDistance(end));
				return;
			}	
			if (c.getMovementQueue().addFirstStep(locations[0])) {
				for (int i = 1; i < locations.length; i++) {
					c.getMovementQueue().addStep(locations[i]);
				}
			}
		} else {
			if (c.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER, Right.GAME_DEVELOPER)) {
				c.setX(end.getX());
				c.setY(end.getY());
				c.getMovementQueue().handleRegionChange();
			}
		}
		
	}

}
