package valius.model.entity.player.skills.agility.impl;

import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.morytania.MorytaniaDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.util.Misc;

/**
 * Agility Shortcuts
 * 
 * @author Matt
 */

public class Shortcuts {

	public static final int SLAYER_TOWER_CHAIN_UP = 16537, SLAYER_TOWER_CHAIN_DOWN = 16538,
			RELLEKKA_STRANGE_FLOOR = 16544, RELLEKKA_CREVICE = 16539, STEPPING_STONE = 16466, ARDOUGNE_LOG = 16548;

	public boolean agilityShortcuts(final Player c, final int objectId) {
		int x = c.getX();
		int y = c.getY();
		int z = c.getHeight();

		switch (objectId) {

		case 4469:
			if (System.currentTimeMillis() - c.miscTimer < 2000)
				return false;
			if (x == 3089 && y >= 3533 && y <= 3534)
				c.getAgilityHandler().move(c, 1, 0, 0x333, -1);
			else if (x == 3090 && y >= 3533 && y <= 3534)
				c.getAgilityHandler().move(c, -1, 0, 0x333, -1);
			else if ((x >= 3093 && x <= 3094 || x >= 3103 && x <= 3104 || x >= 3085 && x <= 3086
					|| x >= 3075 && x <= 3076) && y == 3536)
				c.getAgilityHandler().move(c, 0, 1, 0x333, -1);
			else if ((x >= 3093 && x <= 3094 || x >= 3103 && x <= 3104 || x >= 3085 && x <= 3086
					|| x >= 3075 && x <= 3076) && y == 3537)
				c.getAgilityHandler().move(c, 0, -1, 0x333, -1);
			c.miscTimer = System.currentTimeMillis();
			return true;
			case 16510:
				if(c.getSkills().getLevel(Skill.AGILITY) < 80) {
					c.sendMessage("You must have an Agility level of at least 80 to cross here.");
					return false;
				}
					if (c.getX() == 2880 && c.getY() == 9813) {
						c.getPA().movePlayer(c.getX() - 2, c.getY(), 0);
					} else if (c.getX() == 2878 && c.getY() == 9813) {
						c.getPA().movePlayer(c.getX() + 2, c.getY(), 0);
					}
				return true;
			case 16509:
				if(c.getSkills().getLevel(Skill.AGILITY) < 70) {
					c.sendMessage("You must have an Agility level of at least 70 to squeeze-through the pipe.");
					return false;
				}
				if (c.getX() == 2886 && c.getY() == 9799) {
					c.getPA().movePlayer(c.getX() + 6, c.getY(), 0);
				} else if (c.getX() == 2892 && c.getY() == 9799) {
					c.getPA().movePlayer(c.getX() - 6, c.getY(), 0);
				}
				return true;
		case 23274:
			if (c.getSkills().getLevel(Skill.AGILITY) < 20) {
				c.sendMessage("You must have an agility level of at least 20 to cross here.");
				return false;
			}
			c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CROSS_BALANCE);
			if (c.getAgilityHandler().hotSpot(c, 2603, 3477))
				c.getAgilityHandler().move(c, -5, 0, 762, -1);
			if (c.getAgilityHandler().hotSpot(c, 2598, 3477))
				c.getAgilityHandler().move(c, 5, 0, 762, -1);
			return true;

		case 20843:
			AgilityHandler.delayFade(c, "NONE", 3102, 3482, 0, "You enter the portal..", "and end up by the rowboat.",
					3);
			return true;

		case 29082:
			NPC npc = NPCHandler.getNpc(7439);
			npc.faceEntity(c.getIndex());
			npc.startAnimation(7328);
			npc.forceChat("HAH! Off you go " + Misc.formatPlayerName(c.playerName) + "!");
			c.startAnimation(2304);
			AgilityHandler.delayFade(c, "NONE", 2610, 4776, 0, "Abigail hits you while entering the boat..",
					"and you wake up in a random place.", 2);
			return true;

		case 26762:
			AgilityHandler.delayFade(c, "CRAWL", 3232, 10351, 0, "You crawl into the cavern..",
					"and end up at scorpia's cave.", 3);
			return true;
		case 26763:
			AgilityHandler.delayFade(c, "CRAWL", 3233, 3950, 0, "You crawl out of scorpia's cave..",
					"and end up outside.", 3);
			return true;

		case 678: // Cave to corp
			AgilityHandler.delayFade(c, "CRAWL", 2964, 4382, 2, "You crawl into the cave.",
					"and end up at corporeal beast lair.", 3);
			break;

		case 679: // Cave out from corp
			AgilityHandler.delayFade(c, "CRAWL", 3206, 3681, 0, "You crawl into the cave.",
					"and end up outside corporeal beast lair.", 3);
			break;

		case 26567:
		case 26568: // Cerberus cave
		case 26569:
			AgilityHandler.delayFade(c, "CRAWL", 1310, 1237, 0, "You crawl into the cave",
					"and end up in a dark place.", 3);
			return true;

		case 26564:
		case 26565:
		case 26566:
			AgilityHandler.delayFade(c, "CRAWL", 2873, 9847, 0, "You crawl into the cave", "and end up on the outside.",
					3);
			return true;

		case 26711:
			AgilityHandler.delayFade(c, "CRAWL", 3748, 5849, 0, "You crawl into the crevice",
					"and end up in the kalphite hive.", 3);
			return true;
		case 26712:
			AgilityHandler.delayFade(c, "CRAWL", 2436, 9824, 0, "You crawl into the crevice",
					"and end up back on the outside.", 3);
			return true;

		// case 26645:
		// AgilityHandler.delayFade(c, "JUMP", 3328, 4751, 0, "You jump into the
		// portal..", "and end up at the free-for-all area.", 3);
		// return true;
		// case 26646:
		// AgilityHandler.delayFade(c, "JUMP", 3101, 3492, 0, "You jump into the
		// portal..", "and end up in edgeville.", 3);
		// return true;

		case 537: // Kraken Cave
			c.getPA().movePlayer(2280, 10022, 0);
			return true;

		case 8729: // Wyvern top floor
			if (!c.getSlayer().getTask().isPresent()) {
				c.sendMessage("You must have an active skeletal wyvern task to go up here.");
				return false;
			}
			if (!c.getSlayer().getTask().get().getPrimaryName().equals("skeletal wyvern")) {
				c.sendMessage("You must have an active skeletar wyvern task to enter this crevice.");
				return false;
			}
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3060, 9558, 0, 2);
			return true;

		case 10596:
			AgilityHandler.delayEmote(c, "CRAWL", 3056, 9555, 0, 2);
			return true;

		case 10595:
			AgilityHandler.delayEmote(c, "CRAWL", 3056, 9562, 0, 2);
			return true;

		case ARDOUGNE_LOG:
			if (c.getSkills().getLevel(Skill.AGILITY) < 32) {
				c.sendMessage("You must have an agility level of at least 32 to cross here.");
				return false;
			}
			c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.CROSS_THE_LOG);
			if (c.getAgilityHandler().hotSpot(c, 2602, 3336))
				c.getAgilityHandler().move(c, -4, 0, 762, -1);
			if (c.getAgilityHandler().hotSpot(c, 2598, 3336))
				c.getAgilityHandler().move(c, 4, 0, 762, -1);
			break;

		case RELLEKKA_STRANGE_FLOOR:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (x == 2768)
				c.getAgilityHandler().move(c, 2, 0, 756, -1);
			if (x == 2770)
				c.getAgilityHandler().move(c, -2, 0, 756, -1);
			if (x == 2773)
				c.getAgilityHandler().move(c, 2, 0, 756, -1);
			if (x == 2775)
				c.getAgilityHandler().move(c, -2, 0, 756, -1);
			return true;
		case RELLEKKA_CREVICE:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (x <= 2730) {
				c.getAgilityHandler().move(c, 5, 0, 756, -1);
			} else {
				c.getAgilityHandler().move(c, -5, 0, 756, -1);
			}
			return true;
		case SLAYER_TOWER_CHAIN_UP:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (z == 0 && y <= 3552) {
				if (c.getSkills().getLevel(Skill.AGILITY) < 60) {
					c.sendMessage("You need to have an agility level of at least 61 to climb this chain.");
					return false;
				}
				AgilityHandler.delayEmote(c, "CLIMB_UP", 3422, 3549, 1, 2);
				c.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.CLIMB_CHAIN);
			} else if (z == 1 && y >= 3574) {
				if (c.getSkills().getLevel(Skill.AGILITY) < 70) {
					c.sendMessage("You need to have an agility level of at least 71 to climb this chain.");
					return false;
				}
				AgilityHandler.delayEmote(c, "CLIMB_UP", 3447, 3575, 2, 2);
				c.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.CLIMB_CHAIN);
			}
			return true;
		case SLAYER_TOWER_CHAIN_DOWN:
			if (z == 1 && y <= 3552) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3422, 3549, 0, 2);
			} else if (z == 2 && y >= 3574) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3447, 3575, 1, 2);
			}
			return true;

		case STEPPING_STONE:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (y < 2972) {
				AgilityHandler.delayEmote(c, "JUMP", 2863, 2976, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 2863, 2971, 0, 2);
			}
			return true;

		case 29729:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (x <= 1610) {
				AgilityHandler.delayEmote(c, "JUMP", 1614, 3570, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 1610, 3570, 0, 2);
			}
			return true;

		case 29730:
			if (c.getAgilityHandler().checkLevel(c, objectId)) {
				return false;
			}
			if (x <= 1603) {
				AgilityHandler.delayEmote(c, "JUMP", 1607, 3571, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 1603, 3571, 0, 2);
			}
			return true;

		case 28857:
			AgilityHandler.delayEmote(c, "CLIMB_UP", c.getX() > 1573 ? 1574 : 1567, c.getY() > 3490 ? 3493 : 3482, 1, 2);
			return true;

		case 28858:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", c.getX() > 1573 ? 1576 : 1565, c.getY() > 3490 ? 3493 : 3482, 0, 2);
			return true;

		// Red Woods
		case 29681:
			AgilityHandler.delayEmote(c, "CLIMB_UP", c.getX() == 1570 ? 1570 : 1571, c.getY() > 3488 ? 3489 : 3486, 2, 2);
			return true;

		case 29682:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", c.getX() == 1570 ? 1570 : 1571, c.getY() > 3488 ? 3489 : 3486, 1, 2);
			return true;

		case 17050:
		case 17049:
			if (c.getSkills().getLevel(Skill.AGILITY) < 11 || c.getSkills().getLevel(Skill.STRENGTH) < 37
					|| c.getSkills().getLevel(Skill.RANGED) < 19) {
				c.sendMessage(
						"You must have a ranged level of 19, strength level of 37 and agility level of 11 to do this.");
				return false;
			}
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3032, c.getY() == 3388 ? 3390 : 3388, 0, 2);
			c.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.GRAPPLE_NORTH_WALL);
			return true;

		case 26766:
			AgilityHandler.delayFade(c, "CRAWL", 3062, 10130, 0, "You crawl into the entrance",
					"And end up in the wilderness god wars dungeon..", 3);
			c.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.WILDERNESS_GODWARS);
			return true;

		case 26769:
			AgilityHandler.delayFade(c, "CRAWL", 3017, 3740, 0, "You crawl into the crevice",
					"And end up outside the entrace..", 3);
			return true;
		}
		return false;
	}

}
