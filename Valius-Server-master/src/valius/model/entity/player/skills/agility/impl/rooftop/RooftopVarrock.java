package valius.model.entity.player.skills.agility.impl.rooftop;

import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.agility.MarkOfGrace;

/**
 * Rooftop Agility Varrock
 * 
 * @author Matt
 */

public class RooftopVarrock {

	public static final int ROUGH_WALL = 14412, 
			CLOTHES_LINE = 14413, LEAP_GAP = 14414, 
			BALANCE_WALL_JUMP = 14832, LEAP_2ND_GAP = 14833, 
			LEAP_3RD_GAP = 14834, LEAP_4TH_GAP = 14835,
			HURDLE_LEDGE = 14836, JUMP_OFF_EDGE = 14841;
	
	public static int[] VARROCK_OBJECTS = { ROUGH_WALL, CLOTHES_LINE, LEAP_GAP, BALANCE_WALL_JUMP, LEAP_2ND_GAP, LEAP_3RD_GAP, LEAP_4TH_GAP, HURDLE_LEDGE, JUMP_OFF_EDGE };

	public boolean execute(final Player c, final int objectId) {
		
		for (int id : VARROCK_OBJECTS) {
			if (System.currentTimeMillis() - c.lastObstacleFail < 3000) {
				return false;
			}
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (id == objectId) {
				MarkOfGrace.spawnMarks(c, "VARROCK");
			}
		}
		
		switch (objectId) {
		case ROUGH_WALL:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3219, 3414, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
			
		case CLOTHES_LINE:
			if (AgilityHandler.failObstacle(c, 3212, 3414, 0)) {
				return false;
			}
			if (c.getAgilityHandler().hotSpot(c, 3214, 3414)) {
				c.setForceMovement(3208, 3414, 0, 100, "WEST", 762);
				c.getAgilityHandler().agilityProgress[1] = true;
			}
			return true;
			
		case LEAP_GAP:
			AgilityHandler.delayEmote(c, "JUMP", 3197, 3416, 1, 2);
			c.getAgilityHandler().agilityProgress[2] = true;
			return true;
			
		case BALANCE_WALL_JUMP:
			//AgilityHandler.delayEmote(c, "JUMP", 3190, 3414, 1, 3);
			c.startAnimation(3067);
			c.setForceMovement(3190, 3414, 0, 20, "WEST", -1);
			c.getAgilityHandler().agilityProgress[3] = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						stop();
						return;
					}
					if (c.getX() == 3190 && c.getY() == 3414) {
						c.setForceMovement(3190, 3410, 0, 150, "SOUTH", 3060);
					} else if (c.getX() == 3190 && c.getY() == 3410) {
						c.setForceMovement(3190, 3405, 0, 150, "SOUTH", 756);
					} else if (c.getX() == 3190 && c.getY() == 3405) {
						c.turnPlayerTo(3192, 3405);
						AgilityHandler.delayEmote(c, "JUMP", 3192, 3405, 3, 2);
						c.getAgilityHandler().agilityProgress[3] = true;
						container.stop();
					}
				}

				@Override
				public void stop() {

				}
			}, 2);
			return true;
			
		case LEAP_2ND_GAP:
			if (c.getAgilityHandler().agilityProgress[3] == true) {
				AgilityHandler.delayEmote(c, "JUMP", c.getX(), 3398, 3, 2);
				c.getAgilityHandler().agilityProgress[4] = true;
			} else {
				c.appendDamage(1, Hitmark.HIT);
				c.sendMessage("Apperantly i skipped a gap, ouch..");
			}
			return true;
			
		case LEAP_3RD_GAP:
			AgilityHandler.delayEmote(c, "JUMP", 3215, 3399, 3, 2);
			c.getAgilityHandler().agilityProgress[5] = true;
			return true;
			
		case LEAP_4TH_GAP:
			if (c.getAgilityHandler().agilityProgress[5] == true) {
				AgilityHandler.delayEmote(c, "JUMP", 3236, 3403, 3, 2);
				c.getAgilityHandler().agilityProgress[6] = true;
			} else {
				c.appendDamage(1, Hitmark.HIT);
				c.sendMessage("Apperantly i skipped a gap, ouch..");
			}
			return true;
			
		case HURDLE_LEDGE:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3236, 3410, 3, 2);
			c.getAgilityHandler().agilityProgress[7] = true;
			return true;
			
		case JUMP_OFF_EDGE:
			c.getAgilityHandler().roofTopFinished(c, 7, 238, 8000);
			AgilityHandler.delayEmote(c, "JUMP", 3236, 3417, 0, 2);
			c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.VARROCK_ROOFTOP);
			return true;
		}
		return false;
	}

}
