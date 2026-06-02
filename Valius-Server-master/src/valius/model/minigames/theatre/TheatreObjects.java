package valius.model.minigames.theatre;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;


/**
 * @author Patrity, ReverendDread, RSPSi
 *
 */

public class TheatreObjects {

	public static boolean handleObjectClick(Player c, int objectId) {
		Theatre theatre = c.getTheatreInstance();

		switch (objectId) {

		case 32755:

			if(!theatre.canEnterBossRoom(c)) {
				c.sendMessage("You have died and can't re-enter this fight!");
				return true;
			}
			if (Boundary.isIn(c, Boundary.MAIDEN)) {
				if (c.getX() >= 3186) {
					c.getPA().movePlayer(3184, c.getY(), theatre.getHeight());			
				} else if (c.getX() <= 3184 && theatre.maidenDead()) {
					c.getPA().movePlayer(3186, c.getY(), theatre.getHeight());
				}
			}
			if (Boundary.isIn(c, Boundary.BLOAT)) {
				if (c.getX() <= 3286) {
					c.getPA().movePlayer(3288, c.getY(), theatre.getHeight());
				} else if (c.getX() >= 3288 && c.getX() <= 3291 && theatre.bloatDead()) {
					c.getPA().movePlayer(3286, c.getY(), theatre.getHeight());
				} else if (c.getX() >= 3300 && c.getX() <= 3303 && theatre.bloatDead()) {
					c.getPA().movePlayer(3305, c.getY(), theatre.getHeight());
				} else if (c.getX() >= 3305) {
					c.getPA().movePlayer(3303, c.getY(), theatre.getHeight());
				}
			}
			if (Boundary.isIn(c, Boundary.NYLOCAS)) {
				if (c.getY() >= 4256) {
					c.getPA().movePlayer(c.getX(), 4254, theatre.getHeight());
				} else if (c.getY() <= 4254 && theatre.vasiliasDead()) {
					c.getPA().movePlayer(c.getX(), 4256, theatre.getHeight());
				}
			}
			if (Boundary.isIn(c, Boundary.SOTETSEG)) {
				if (c.getY() <= 4306) {
					c.getPA().movePlayer(c.getX(), 4308, theatre.getHeight());
				} else if (c.getY() >= 4308 && theatre.sotetsegDead()) {
					c.getPA().movePlayer(c.getX(), 4306, theatre.getHeight());
				}
			}
			if (Boundary.isIn(c, Boundary.XARPUS)) {
				if (c.getY() <= 4378) {
					c.getPA().movePlayer(c.getX(), 4380,  theatre.getHeight() + 1);
				} else if (c.getY() >= 4380 && c.getY() <= 4385 && theatre.xarpusDead()) {
					c.getPA().movePlayer(c.getX(), 4378,  theatre.getHeight() + 1);
				} else if (c.getY() <= 4394 && c.getY() >= 4389 && theatre.xarpusDead()) {
					c.getPA().movePlayer(c.getX(), 4396,  theatre.getHeight() + 1);
				} else if (c.getY() >= 4396) {
					c.getPA().movePlayer(c.getX(), 4394, theatre.getHeight() + 1);
				}
			}
			break;

		case 33113:
			if (Boundary.isIn(c, Boundary.MAIDEN)) {
				if (theatre.maidenDead()) {
					c.getPA().movePlayer(3322, 4448);
					theatre.spawnBoss(2);
				} else {
					c.sendMessage("You must defeat Maiden before progressing!");
				}
			}
			if (Boundary.isIn(c, Boundary.BLOAT)) {
				if (theatre.bloatDead()) {
					c.getPA().movePlayer(3296, 4283);
					theatre.spawnBoss(3);
				} else {
					c.sendMessage("You must defeat Bloat before progressing!");
				}
			}
			if (Boundary.isIn(c, Boundary.NYLOCAS)) {
				if (theatre.vasiliasDead()) {
					c.getPA().movePlayer(3291, 4328);
					theatre.spawnBoss(4);
				} else {
					c.sendMessage("You must defeat Nylocas before progressing!");
				}
			}
			if (Boundary.isIn(c, Boundary.SOTETSEG)) {
				if (theatre.sotetsegDead()) {
					c.getPA().movePlayer(3170, 4375, theatre.getHeight() + 1);
					theatre.spawnBoss(5);
				} else {
					c.sendMessage("You must defeat Sotetseg before progressing!");
				}
			}
			break;

		case 32751:
			if (Boundary.isIn(c, Boundary.XARPUS)) {
				if (theatre.xarpusDead()) {
					c.getPA().movePlayer(3168, 4303, theatre.getHeight());
					theatre.spawnBoss(6);
				} else {
					c.sendMessage("You must defeat Xarpus before progressing!");
				}
			}
			break;
		case 32741:
			if (c.getItems().playerHasItem(22516)) {
				c.sendMessage("You've already found a Dawnbringer staff.");
			} else {
				c.getItems().addItem(22516, 1);
				c.sendMessage("You have found the Dawnbringer!");
			}
			break;
			
		case 32738:
			if (theatre.verzikDead()) {
				c.getPA().movePlayer(3237, 4307, theatre.getHeight());
				theatre.spawnLoot();
			} else {
				c.sendMessage("You must defeat Verzik before progressing!");				
			}
			break;
		case 32995:
			c.sendMessage("You cannot go back!");
			break;
			
		case 32758:// point shop
			c.getShops().openShop(135);
			break;

		case 32996:
			c.getPA().movePlayer(3050, 9950, 0);
			c.sendMessage("Congratulations on completing Theatre of Blood!");
			TheatreConstants.capeCheck(c);
			c.totalTheatreFinished ++;
			c.sendMessage("You have now completed Theatre of Blood "+c.totalTheatreFinished+" times!");
			break;

		case 32990: //Loot box
			TheatreConstants.giveLoot(c);
			break;		 

		}
		return false;
	}

}
