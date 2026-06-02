package valius.net.packet.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import valius.clip.ObjectDef;
import valius.clip.Region;
import valius.clip.WorldObject;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.farming.FarmingConstants;
import valius.model.entity.player.skills.hunter.impling.PuroPuro;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.net.packet.impl.objectoptions.ObjectOptionFive;
import valius.net.packet.impl.objectoptions.ObjectOptionFour;
import valius.net.packet.impl.objectoptions.ObjectOptionOne;
import valius.net.packet.impl.objectoptions.ObjectOptionThree;
import valius.net.packet.impl.objectoptions.ObjectOptionTwo;
import valius.world.World;

/**
 * Click Object
 */
public class ClickObject implements PacketType {


	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234, FIFTH_CLICK = 228;

	@Override
	public void processPacket(final Player c, int packetType, int packetSize) {

		int objectId = c.getInStream().readInteger();
		int objectType = c.getInStream().readUnsignedByte();
		int objectX = c.getInStream().readUnsignedWord();
		int objectY = c.getInStream().readUnsignedWord();
		c.getPA().resetFollow();
		c.getCombat().resetPlayerAttack();

		if (c.isForceMovementActive()) {
			return;
		}
		if (c.teleTimer > 0) {
			return;
		}

		if (c.isForceMovementActive()) {
			return;
		}

		if (c.viewingLootBag || c.addingItemsToLootBag || c.viewingRunePouch) {
			return;
		}

		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
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
		
		if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			return;
		}
		if (c.teleTimer > 0) {
			return;
		}
		Optional<WorldObject> existingObject = Region.getWorldObject(objectId, objectX, objectY, c.getLocation().getZ(), objectType);
		if(!existingObject.isPresent()) {
			if(c.debugMessage) {
				c.sendMessage("z: " + c.getLocation().getZ());
				List<WorldObject> existingObjs = Region.getWorldObjectsAt(objectX, objectY, c.getLocation().getZ());
				existingObjs.forEach(obj -> c.sendMessage("OBJ " + obj.id + " : " + obj.type + " : " + obj.face));
			}
			return;
		} else {
			if(c.debugMessage) {
				c.sendMessage("Exists " + existingObject.get().toString());
			}
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
		
		ObjectDef objectDef = ObjectDef.forID(objectId);
		if(objectDef != null) {

			c.setInteractingObject(existingObject);
			int direction = existingObject.get().getFace();
			int objectWidth;
			int objectLength;
			if (direction != 1 && direction != 3) {
				objectWidth = objectDef.width;
				objectLength = objectDef.length;
			} else {
				objectWidth = objectDef.length;
				objectLength = objectDef.width;
			}
			c.turnPlayerTo(existingObject.get().getX(), existingObject.get().getY());
			
			Runnable onReached = () -> existingObject.ifPresent(worldObject -> {
				switch(packetType) {
				case FIRST_CLICK:
					if(c.getInstance() != null && c.getInstance().clickObject(c, worldObject, 1)){
						c.resetInteractingObject();
						return;
					}
					c.getQuestManager().onObjectClick(1, worldObject);
					ObjectOptionOne.handleOption(c, worldObject);
					break;
				case SECOND_CLICK:
					if(c.getInstance() != null && c.getInstance().clickObject(c, worldObject, 2)){
						c.resetInteractingObject();
						return;
					}
					c.getQuestManager().onObjectClick(2, worldObject);
					ObjectOptionTwo.handleOption(c, worldObject);
					break;
				case THIRD_CLICK:
					if(c.getInstance() != null && c.getInstance().clickObject(c, worldObject, 3)){
						c.resetInteractingObject();
						return;
					}
					c.getQuestManager().onObjectClick(3, worldObject);
					ObjectOptionThree.handleOption(c, worldObject);
					break;
				case FOURTH_CLICK:
					if(c.getInstance() != null && c.getInstance().clickObject(c, worldObject, 4)){
						c.resetInteractingObject();
						return;
					}
					c.getQuestManager().onObjectClick(4, worldObject);
					ObjectOptionFour.handleOption(c, worldObject);
					break;
				case FIFTH_CLICK:
					if(c.getInstance() != null && c.getInstance().clickObject(c, worldObject, 1)) {
						c.resetInteractingObject();
						return;
					}
					c.getQuestManager().onObjectClick(5, worldObject);
					ObjectOptionFive.handleOption(c, worldObject);
					break;
				}
			});
			if (c.goodDistance(objectX, objectY, c.getX(), c.getY(), objectWidth, objectLength)) {
				onReached.run();
				
			} else {
				
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(!c.getInteractingObject().isPresent() || !c.getInteractingObject().equals(existingObject)) {
							stop();
							return;
						}
						if (c.goodDistance(objectX, objectY, c.getX(), c.getY(), objectWidth, objectLength)) {
							onReached.run();
							container.stop();
						}
					}

				}, 1);
			}
		}
		
	}

}
