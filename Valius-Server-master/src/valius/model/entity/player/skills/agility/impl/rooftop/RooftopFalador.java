package valius.model.entity.player.skills.agility.impl.rooftop;

import valius.model.entity.player.Player;
import valius.model.entity.player.skills.agility.AgilityHandler;

/**
 * Rooftop Agility Falador
 * 
 * @author Matt
 */

public class RooftopFalador {

	public static final int ROUGH_WALL = 14898, TIGHT_ROPE = 14899;

	public boolean execute(final Player c, final int objectId) {
		switch (objectId) {
		case ROUGH_WALL:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3036, 3343, 3, 2);
			c.getAgilityHandler().agilityProgress[0] = true;
			return true;
		case TIGHT_ROPE:
			if (c.getAgilityHandler().hotSpot(c, 3039, 3343))
				c.getAgilityHandler().move(c, 8, 0, 762, -1);
			c.getAgilityHandler().agilityProgress[1] = true;
			return true;
		}
		/*
		 * 
		 * To be continued..
		 * 
		 */
		return false;
	}

}
