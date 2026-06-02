package valius.net.packet.impl.objectoptions;

import java.util.Objects;
import java.util.stream.IntStream;

import valius.Config;
import valius.clip.ObjectDef;
import valius.clip.WorldObject;
import valius.clip.doors.DoorDefinition;
import valius.clip.doors.DoorHandler;
import valius.content.CompCapeRequirements;
import valius.content.CrystalChest;
import valius.content.InfernalChest;
import valius.content.Obelisks;
import valius.content.SlayerChest;
import valius.content.WildernessChest;
import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.desert.DesertDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.karamja.KaramjaDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.morytania.MorytaniaDiaryEntry;
import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.content.cannon.DwarfCannon;
import valius.content.events.Event;
import valius.content.events.WildernessEscape;
import valius.content.falling_stars.FallingStars;
import valius.content.gauntlet.GauntletPrepRoom;
import valius.content.gauntlet.GauntletRewards;
import valius.content.gauntlet.TheGauntlet;
import valius.content.godwars.God;
import valius.content.tradingpost.Listing;
import valius.content.wogw.Wogw;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.instance.impl.HydraInstance;
import valius.model.entity.instance.impl.VoidChampionInstance;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.EventBoss.EventBossChest;
import valius.model.entity.npc.bosses.cerberus.Cerberus;
import valius.model.entity.npc.combat.impl.custombosses.drops.NightmareDrops;
import valius.model.entity.npc.combat.impl.eventboss.drop.EnragedGraardorDrops;
import valius.model.entity.npc.drops.DropManager;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerAssistant;
import valius.model.entity.player.Right;
import valius.model.entity.player.WildernessDitch;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.path.Direction;
import valius.model.entity.player.skills.FlaxPicking;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.crafting.JewelryMaking;
import valius.model.entity.player.skills.herblore.Herblore;
import valius.model.entity.player.skills.hunter.Hunter;
import valius.model.entity.player.skills.hunter.impling.Impling;
import valius.model.entity.player.skills.hunter.impling.PuroPuro;
import valius.model.entity.player.skills.runecrafting.Runecrafting;
import valius.model.entity.player.skills.slayer.Task;
import valius.model.entity.player.skills.woodcutting.Tree;
import valius.model.entity.player.skills.woodcutting.Woodcutting;
import valius.model.holiday.halloween.HalloweenRandomOrder;
import valius.model.items.EquipmentSet;
import valius.model.lobby.LobbyManager;
import valius.model.lobby.LobbyType;
import valius.model.minigames.lighthouse.DagannothMother;
import valius.model.minigames.lighthouse.DisposeType;
import valius.model.minigames.pest_control.PestControl;
import valius.model.minigames.pk_arena.Highpkarena;
import valius.model.minigames.pk_arena.Lowpkarena;
import valius.model.minigames.rfd.DisposeTypes;
import valius.model.minigames.rfd.RecipeForDisaster;
import valius.model.minigames.theatre.Theatre;
import valius.model.minigames.theatre.TheatreObjects;
import valius.model.minigames.xeric.XericRewards;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.duel.DuelSessionRules.Rule;
import valius.net.packet.impl.objectoptions.impl.CompCapeRack;
import valius.net.packet.impl.objectoptions.impl.DarkAltar;
import valius.net.packet.impl.objectoptions.impl.Overseer;
import valius.net.packet.impl.objectoptions.impl.RaidObjects;
import valius.net.packet.impl.objectoptions.impl.TrainCart;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/*
 * @author Matt
 * Handles all first options for objects.
 */

public class ObjectOptionOne {

	static int[] barType = { 2363, 2361, 2359, 2353, 2351, 2349 };

	public static void handleOption(final Player c, WorldObject worldObject) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		int objectId = worldObject.getId();
		int objectX = worldObject.getX();
		int objectY = worldObject.getY();
		
		c.resetInteractingObject();
		
		GlobalObject object = new GlobalObject(objectId, objectX, objectY, c.getHeight());
		c.getPA().resetVariables();
		c.clickObjectType = 0;
		c.turnPlayerTo(objectX, objectY);
		c.getFarming().patchObjectInteraction(objectId, -1, objectX, objectY);
		c.boneOnAltar = false;
		Tree tree = Tree.forObject(objectId);

		RaidObjects.clickObject1(c, objectId, objectX, objectY);
		if(FallingStars.attemptMine(c, objectId, new Location(objectX, objectY, c.getHeight())))
			return;
		if (tree != null) {
			Woodcutting.getInstance().chop(c, objectId, objectX, objectY);
			return;
		}
		if (World.getWorld().getHolidayController().clickObject(c, 1, objectId, objectX, objectY)) {
			return;
		}
		if (c.getGnomeAgility().gnomeCourse(c, objectId)) {
			return;
		}
		if (c.getWildernessAgility().wildernessCourse(c, objectId)) {
			return;
		}
		if (c.getBarbarianAgility().barbarianCourse(c, objectId)) {
			return;
		}
		if (c.getBarbarianAgility().barbarianCourse(c, objectId)) {
			return;
		}
		if (c.getAgilityShortcuts().agilityShortcuts(c, objectId)) {
			return;
		}
		if (c.getRoofTopSeers().execute(c, objectId)) {
			return;
		}
		if (c.getRoofTopFalador().execute(c, objectId)) {
			return;
		}
		if (c.getRoofTopVarrock().execute(c, objectId)) {
			return;
		}
		if (c.getRoofTopArdougne().execute(c, objectId)) {
			return;
		}
		if (c.getLighthouse().execute(c, objectId)) {
			return;
		}
		ObjectDef def = ObjectDef.forID(objectId);
		if ((def != null ? def.name : null) != null && def.name.toLowerCase().contains("bank")) {
			c.getPA().openUpBank();
		}
		final int[] HUNTER_OBJECTS = new int[] { 9373, 9377, 9379, 9375, 9348, 9380, 9385, 9344, 9345, 9383, 721, 9382 };
		if (IntStream.of(HUNTER_OBJECTS).anyMatch(id -> objectId == id)) {
			if (Hunter.pickup(c, object)) {
				return;
			}
			if (Hunter.claim(c, object)) {
				return;
			}
		}
		c.getMining().mine(objectId, new Location(objectX, objectY, c.getHeight()));
		Obelisks.get().activate(c, objectId);
		Runecrafting.execute(c, objectId);
		if (objectId >= 26281 && objectId <= 26290) {
			HalloweenRandomOrder.chooseOrder(c, objectId);
		}
		DoorDefinition door = DoorDefinition.forCoordinate(objectX, objectY, c.getHeight());

		if (door != null && DoorHandler.clickDoor(c, door)) {
			return;
		}

		if (c.getRaidsInstance() != null && c.getRaidsInstance().handleObjectClick(c, worldObject)) {
			return;
		}
		if (c.getTheatreInstance() != null && Boundary.isIn(c, Boundary.THEATRE)) {
			TheatreObjects.handleObjectClick(c, objectId);
		}

		switch (objectId) {
		case 30283:
			c.getInfernoMinigame().leaveGame();
			break;
			
		case 29336:
			c.getPA().sendString("https://valius.net/community/index.php?/forum/55-custom-donations/", 12000);
			break;
			
		case 37340:
			if(c.hasFollower) {
				c.sendMessage("You can't take pets in with you.");
				return;
			}
			if(c.getItems().hasAnyItems()) {
				c.sendMessage("You can't take any items into The Gauntlet!");
				return;
			}
			if (!c.optionalInstance().isPresent()) {			
				c.setInstance(new TheGauntlet(c));
			}
			break;
			
		case 37341:
			GauntletRewards.openChest(c);
			break;
			
		case 36082:
			c.startAnimation(1719);
			c.getPA().startTeleport2(3263, 6077, 0);
			break;
			
		case 11726:
			if (c.getX() >= 3189 && c.getX() <= 3191 && c.getY() == 3957) {
				c.getPA().movePlayer(3190, 3958, 0);	
			} else if (c.getX() >= 3189 && c.getX() <= 3191 && c.getY() == 3958) {
			c.getPA().movePlayer(3190, 3957, 0);
			}
			break;
			
		case 23644:
			if (c.getSkills().getLevel(Skill.AGILITY) <= 70) {
					c.getDH().sendStatement("You need 70 Agility to cross this log.");
					c.sendMessage("You need 70 Agility to cross this log.");
					break;
				}
			if (objectX == 2907) {
				AgilityHandler.delayEmote(c, "BALANCE", 2910, 3049, 0, 2);
			} else if (objectX == 2909) {
				AgilityHandler.delayEmote(c, "BALANCE", 2906, 3049, 0, 2);
			}
			break;
			

			
		case 29338:
			if (c.getItems().playerHasAllItems(33793, 33794, 33795)) {
				c.getItems().deleteItems(33793, 33794, 33795);
				c.sendMessage("You summon the Shadow Lord! Prepare yourself.");
				NPCHandler.spawnNpc(3383, 3110, 3697, 0, 1, 1600, 50, 250, 350);
			} else {
				c.getDH().sendStatement("You need the 3 Shadow lord armor pieces dropped by ", "@blu@ Calisto, Vet'ion and Venenatis.");
			}
			break;
			
		case 29337:
			WildernessChest.searchChest(c);
			break;
	
		case 11701:
			c.getPA().startTeleport(2202, 3056, 0, "modern", false);
			break;
		case 9398:// deposit
			c.getPA().sendFrame126("The Bank of Valius - Deposit Box", 7421);
			c.getPA().sendFrame248(4465, 197);// 197 just because you can't
			// see it =\
			c.getItems().resetItems(7423);
			break;
		case 29735:// Basic training
			if (objectX != 3277 && objectY != 5169) {
				c.getPA().movePlayer(2634, 5069, 0);
				c.sendMessage("Welcome to the Basic training dungeon, you can find basic monsters here!");
			}
			break;
		case 6450:// Basic training ladder
			c.getPA().movePlayer(1644, 3673, 0);
			break;
		case 32153:// rune dragon barrier entry
			if (objectX == 1574 && (objectY <= 5077 && (objectY >= 5072 && c.getX() <= 1573))) {
				AgilityHandler.delayEmote(c, -1, 1575, c.getY(), 0, 4);
				c.sendMessage("@pur@You have entered the Rune dragon room.");
				return;
			} else if (objectX == 1574 && (objectY <= 5077 && (objectY >= 5072 && c.getX() >= 1575))) {
				AgilityHandler.delayEmote(c, -1, 1573, c.getY(), 0, 4);
				c.sendMessage("@pur@You have left the Rune dragon room.");
				return;
				//Adamant dragon barrier entry
			} else if (objectX == 1561 && (objectY <= 5077 && (objectY >= 5072 && c.getX() >= 1562))) {
				AgilityHandler.delayEmote(c, -1, 1560, c.getY(), 0, 4);
				c.sendMessage("@pur@You have entered the Adamant dragon room.");
				return;
			} else if (objectX == 1561 && (objectY <= 5077 && (objectY >= 5072 && c.getX() <= 1560))) {
				AgilityHandler.delayEmote(c, -1, 1562, c.getY(), 0, 4);
				c.sendMessage("@pur@You have left the Adamant dragon room.");
				return;
			}
			break;
		case 26709:// strongholdslayer cave
			c.getPA().movePlayer(2429, 9825, 0);
			c.sendMessage("Welcome to the Stronghold slayer cave, you can find many slayer monsters here!");
			break;
		case 26710:// strongholdslayer caveexit
		case 27258:
			c.getPA().movePlayer(2430, 3425, 0);
			break;
		case 28892:// catacomb agility
			if (c.getSkills().getLevel(Skill.AGILITY) < 34) {
				c.sendMessage("You need an Agility level of 34 to pass this.");
				return;
			}
			if (c.getX() == 1648) {
				AgilityHandler.delayEmote(c, "CRAWL", 1646, 10000, 0, 2);
			} else if (c.getX() == 1716) {
				AgilityHandler.delayEmote(c, "CRAWL", 1706, 10078, 0, 2);
			} else if (c.getX() == 1706) {
				AgilityHandler.delayEmote(c, "CRAWL", 1716, 10056, 0, 2);
			} else if (c.getX() == 1646) {
				AgilityHandler.delayEmote(c, "CRAWL", 1648, 10009, 0, 2);
			}
			break;
		case 30175:// Stronghold short
			if (c.getSkills().getLevel(Skill.AGILITY) < 72) {
				c.sendMessage("You need an Agility level of 72 to pass this.");
				return;
			}
			if (c.getX() == 2429) {
				AgilityHandler.delayEmote(c, "CRAWL", 2435, 9806, 0, 2);
			} else if (c.getX() == 2435) {
				AgilityHandler.delayEmote(c, "CRAWL", 2429, 9806, 0, 2);
			}
			break;
	
		case 536:// Smoke Devil Exit
			if (c.getX() == 2376) {
				AgilityHandler.delayEmote(c, "CRAWL", 2379, 9452, 0, 2);
			}
			break;
			/*
			 * case 17385://taveryly exit AgilityHandler.delayEmote(c, "CLIMB_UP", 1662,
			 * 3529, 0, 2); break;
			 */
		case 1738:// tav entrance
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 2884, 9798, 0, 2);
			break;
		case 2123:// relleka entrance
			AgilityHandler.delayFade(c, "CRAWL", 2808, 10002, 0, "You crawl into the entrance.",
					"and you end up in a dungeon.", 3);
			c.sendMessage("Welcome to the Relleka slayer dungeon, find many slayer tasks here.");
			break;
		case 2268:// ice dung exit
			AgilityHandler.delayEmote(c, "CLIMB_UP", 1651, 3619, 0, 2);
			break;
		case 2141:// relleka exit
			c.getPA().movePlayer(1259, 3502, 0);
			break;
			/*
			 * case 29734://dgorillas if (objectX == 1349 && objectY == 3591) {
			 * c.getPA().movePlayer(2130, 5646, 0); c.
			 * sendMessage("Welcome to the Demonic Gorilla's Dungeon, try your luck for a heavy frame!"
			 * ); } break;
			 */
		case 28687:// dgexit
			c.getPA().movePlayer(1348, 3590, 0);
			break;
		case 4153:// corpexit
			c.getPA().movePlayer(1547, 3571, 0);
			break;
		case 2544:// daggentrence
			c.getPA().movePlayer(2446, 10147, 0);
			break;
		case 8966:// dagexit
			c.getPA().movePlayer(1547, 3571, 0);
			break;
		case 2823:// mdragsentrance
			AgilityHandler.delayFade(c, "CRAWL", 1746, 5323, 0, "You crawl into the entrance.",
					"and you end up in a dungeon.", 3);
			c.sendMessage("Welcome to the Mith Dragons Cave, try your luck for a visage or d full helm!");
			break;
		case 25337:// mdragsexit
			c.getPA().movePlayer(1792, 3709, 0);
			break;
		case 4151:// barrows
			c.getPA().movePlayer(3565, 3308, 0);
			c.sendMessage("Welcome to Barrows, good luck with your rewards!");
			break;
		case 25016:
		case 25017:
		case 25018:
		case 25029:
			PuroPuro.magicalWheat(c);
			break;
			
		case 29334:
			CompCapeRequirements.executeRequirements(c);
		break;
			
		case DwarfCannon.COLLAPSED_CANNON_ID:
			c.cannon.pickupCannon(object);
			break;
			
		case DwarfCannon.CANNON_OBJECT_ID:
			c.cannon.addAmmo(object);
			break;
			
		case 34548: //Hydra boss rocks
			if (c.getY() < 10251) {
				c.setInstance(new HydraInstance());
			} else if (c.getY() > 10251) {
				AgilityHandler.delayEmote(c, "JUMP", c.getX(), c.getY() - 2, 0, 2);
				if (c.getInstance() != null) {
					c.getInstance().leave(c);
				}
			}
			break;
			
		case 28449://Void champion boss cross
			c.startAnimation(1651);
			c.setInstance(new VoidChampionInstance());
			c.getDH().sendStatement("You have awakened the Void Knight Champion from his Slumber.");
			break;
			

		case 9369:
		case 18532:
			c.getFishing().startFishing(18532, 22842);
			break;
			
		case 34553:
		case 34554: //Hydra boss door
			if (c.getX() <= 1355)
				c.getPA().movePlayer(c.getX() + 1, c.getY(), c.getHeight());
			else
				c.sendMessage("The door is locked securly from the other side.");
			break;
			
			
		case 29333:
			//c.sendMessage("Trading post has been temporarily disabled!");
	            Listing.openPost(c, false, true);
	            break;
		
		case 29709: //santa's table
			c.getShops().openShop(128);
			break;
			
		//Wyvern Cave Stuff
		case 30844: //rope back home
			AgilityHandler.delayEmote(c, "CLIMB_UP", Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, 2);
			break;
		case 31485:
			if (c.getX() <= 3603) {
				AgilityHandler.delayEmote(c, "JUMP", 3607, 10290, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 3603, 10290, 0, 2);				
			}
			break;
		case 30849:
			AgilityHandler.delayEmote(c, "JUMP", 3633, 10264, 0, 2);
			break;
		case 30847:
			if (c.getY() >= 10259) {
				AgilityHandler.delayEmote(c, "JUMP", 3633, 10260, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 3633, 10264, 0, 2);
			}
			break;

		case 31990://VORKATH
			if (c.getY() == 4054) {
				c.getVorkath().exit(c);
			} else if (c.getY() == 4052) {
				c.getVorkath().enterInstance(c, 10);
			}
			break;
		case 27288:
			c.getShops().openShop(79);
			break;
			
			case 31561:
				// south jump north
				if(c.getY() == objectY - 2) {
					c.getPA().walkTo2(objectX, objectY-2);
					c.turnPlayerTo(objectX, objectY);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY, 0, 2);
					c.startAnimation(3067);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY+2, 0, 4);

				}
				//north jump south
				if(c.getY() == objectY + 2) {
					c.getPA().walkTo2(objectX, objectY+2);
					c.turnPlayerTo(objectX, objectY);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY, 0, 2);
					c.getPlayerAction().setAction(true);
					c.startAnimation(3067);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY-2, 0, 4);
				}
				//east jump west
				if(c.getX() == objectX + 2) {
					c.getPA().walkTo2(objectX, objectX+2);
					c.turnPlayerTo(objectX, objectY);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY, 0, 2);
					c.getPlayerAction().setAction(true);
					c.startAnimation(3067);
					AgilityHandler.delayEmote(c, "JUMP", objectX-2, objectY, 0, 4);
				}
				//west jump east
				if(c.getX() == objectX - 2) {
					c.getPA().walkTo2(objectX, objectX-2);
					c.turnPlayerTo(objectX, objectY);
					AgilityHandler.delayEmote(c, "JUMP", objectX, objectY, 0, 2);
					c.getPlayerAction().setAction(true);
					c.startAnimation(3067);
					AgilityHandler.delayEmote(c, "JUMP", objectX+2, objectY, 0, 4);
				}
				break;
				
			//Home stair Objects
			case 11807:
				c.getPA().movePlayer(3085, 3515, 1);
				break;
			case 11799:
				c.getPA().movePlayer(3085, 3511, 0);
				break;
			case 11790:
				c.getPA().movePlayer(c.getX(), c.getY(), c.getHeight()+1);
				break;
			case 11793:
				c.getPA().movePlayer(c.getX(), c.getY(), c.getHeight()-1);
				break;
				
		case 23271:
			if(WildernessEscape.eventActive == true && WildernessEscape.currentCheckpoint == 7) {//if host gets all 7 checkpoints and crosses ditch they win
				WildernessEscape.hostWins();
			}
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.getY() == 3520) {
						WildernessDitch.wildernessDitchEnter(c);
						container.stop();
					} else if (c.getY() == 3523) {
						WildernessDitch.wildernessDitchLeave(c);
						container.stop();
					}
				}

				@Override
				public void stop() {
				}
			}, 1);
			break;

		case 16680:
			if (objectX == 3088) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3088, 9970, 0, 2);
			} else {
			c.getPA().movePlayer(2884, 9798, 0);
			}
			break;

/*
		case 29150:
			int spellBook = c.playerMagicBook == 0 ? 1 : (c.playerMagicBook == 1 ? 2 : 0);
			int interfaceId = c.playerMagicBook == 0 ? 838 : (c.playerMagicBook == 1 ? 29999 : 938);
			String type = c.playerMagicBook == 0 ? "ancient" : (c.playerMagicBook == 1 ? "lunar" : "normal");

			c.sendMessage("You switch spellbook to " + type + " magic.");
			c.setSidebarInterface(6, interfaceId);
			c.playerMagicBook = spellBook;
			c.autocasting = false;
			c.autocastId = -1;
			c.getPA().resetAutocast();
			return;*/
		case 31858:
			int spellBook = c.playerMagicBook == 0 ? 1 : (c.playerMagicBook == 1 ? 2 : 0);
			int interfaceId = c.playerMagicBook == 0 ? 838 : (c.playerMagicBook == 1 ? 29999 : 938);
			String type = c.playerMagicBook == 0 ? "ancient" : (c.playerMagicBook == 1 ? "lunar" : "normal");

			c.sendMessage("You switch spellbook to " + type + " magic.");
			c.setSidebarInterface(6, interfaceId);
			c.playerMagicBook = spellBook;
			c.autocasting = false;
			c.autocastId = -1;
			c.getPA().resetAutocast();
			return;
		case 29241:
			if(!c.getRights().isOrInherits(Right.ADMINISTRATOR)) {
				if (c.amDonated == 0) {
					c.sendMessage("@red@You need to be a donator to use this feature.");
					return;
				}
	
				if (c.specRestore > 0) {
					int seconds = ((int) Math.floor(c.specRestore * 0.6));
					c.sendMessage("You have to wait another " + seconds + " seconds to use this altar.");
					return;
				}
			}

			c.startAnimation(645);
			c.specRestore = 120;
			c.getHealth().removeAllStatuses();
			c.getHealth().reset();
			c.specAmount = 10.0;
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.getSkills().resetToActualLevel(Skill.PRAYER);
			c.getHealth().removeAllStatuses();
			c.getHealth().reset();
			c.getPA().refreshSkill(5);
			c.sendMessage("You feel rejuvinated.");
			break;
		case 6150:

			if (c.getItems().playerHasItem(barType[0])) {
				c.getSmithingInt().showSmithInterface(barType[0]);
			} else if (c.getItems().playerHasItem(barType[1])) {
				c.getSmithingInt().showSmithInterface(barType[1]);
			} else if (c.getItems().playerHasItem(barType[2])) {
				c.getSmithingInt().showSmithInterface(barType[2]);
			} else if (c.getItems().playerHasItem(barType[3])) {
				c.getSmithingInt().showSmithInterface(barType[3]);
			} else if (c.getItems().playerHasItem(barType[4])) {
				c.getSmithingInt().showSmithInterface(barType[4]);
			} else if (c.getItems().playerHasItem(barType[5])) {
				c.getSmithingInt().showSmithInterface(barType[5]);
			} else {
				c.sendMessage("You don't have any bars.");
			}
			break;
		case 11846:
			if (c.combatLevel >= 100) {
				if (c.getY() > 5175) {
					Highpkarena.addPlayer(c);
				} else {
					Highpkarena.removePlayer(c, false);
				}
			} else if (c.combatLevel >= 80) {
				if (c.getY() > 5175) {
					Lowpkarena.addPlayer(c);
				} else {
					Lowpkarena.removePlayer(c, false);
				}
			} else {
				c.sendMessage("You must be at least level 80 to compete in events.");
			}
			break;

		case 11845:
			if (c.combatLevel >= 100) {
				if (c.getY() < 5169) {
					Highpkarena.removePlayer(c, false);
				}
			} else if (c.combatLevel >= 80) {
				if (c.getY() < 5169) {
					Lowpkarena.removePlayer(c, false);
				}
			} else {
				c.sendMessage("You must be at least level 80 to compete in events.");
			}

			break;
		case 22472:
			c.getPA().showInterface(65000);
			break;
		case 31621: //tp interface home portal
		case 29344: //dzone teleporter
		case 33393:
			c.getPortalTeleports().openInterface();
			break;
		case 15615:
			c.turnPlayerTo(objectX, objectY);
			c.startAnimation(5067);
			c.setLastContainerSearch(System.currentTimeMillis());
			c.getItems().addItem(10501, 1);
			c.sendMessage("You successfully made a snowball.");
			break;
		case 10068:
			c.getDH().sendDialogues(637, 2040);
			break;
		case 12941:
			PlayerAssistant.refreshSpecialAndHealth(c);
			break;
		case 29349:// miniportal
			// c.getDH().sendDialogues(402, 403);
			c.getPA().showInterface(65000);
			break;
		case 33395://donator bossportal
			c.getDH().sendDialogues(10001, 0);
			break;
		case 29345:// Training
			// c.getDH().sendDialogues(400, 399);
			c.getPA().showInterface(65000);
			break;
		case 29346:// Wilderness
			// c.getDH().sendDialogues(401, 0);
			c.getPA().showInterface(65000);
			break;
		/*
		 * case 29747: //Ice Demon Brazziers if (c.getItems().playerHasItem(20799, 1)) {
		 * World.getWorld().getGlobalObjects().replace(new GlobalObject(29747, obX, obY,
		 * c.heightLevel, 0, 10, 50, -1), new GlobalObject(29748, obX, obY,
		 * c.heightLevel, 0, 10, 0, -1)); c.getItems().deleteItem(20799, 1); } else {
		 * c.sendMessage("You need some kindling to light this brazier!"); } break; case
		 * 29748: if (c.getItems().playerHasItem(20799, 1)) { //addBrazzierVariable
		 * c.sendMessage("You add a piece of kindling to the brazier.");
		 * c.getItems().deleteItem(20799, 1); } else {
		 * c.sendMessage("You need some kindling to light this brazier!"); } break;
		 */
			case 26811:
				c.getShops().openShop(77);
				c.sendMessage("@red@ You have</col> @blu@" + c.votePoints + "</col> @red@Vote points");
				c.sendMessage("@red@ Please type in ::vote to go to the site. And type ::reward to receive them!");
				c.sendMessage("@red@ Thank you for supporting Valius!");
				break;
			case 31556:
				if (object.getLocation().getY() == 5361) {
					c.getPA().movePlayer(3011, 3927);
				} else if (object.getLocation().getY() == 3926) {
					c.getPA().movePlayer(1645, 5365);
					c.sendMessage("You enter the Deep Wilderness Revenant Dungeon. Beware!");
				} else {
				c.getPA().movePlayer(3241, 10234);
				c.sendMessage("@blu@You enter the Revenant Dungeon. You can upgrade your Wilderness weapons");
				c.sendMessage("@blu@by using Shards dropped by the Revenants in the Deep wilderness");
				c.sendMessage("@blu@Revenant dungeon located East of the Wilderness agility course!");
				}
				break;
				
			case 31558:
				c.getPA().movePlayer(3126, 3833);
				break;

		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getShops().openShop(116);
			break;
		case 4150:
			c.sendMessage("Welcome to the Warriors guild, good luck with your defenders!");
			c.getPA().spellTeleport(2855, 3543, 0, false);
		case 23115:// from bobs
			c.getPA().spellTeleport(3094, 3500, 0, false);
			break;
		case 10251:
			c.getPA().spellTeleport(2525, 4776, 0, false);
			break;
		case 26756:

			break;

		case 27057:
			Overseer.handleBludgeon(c);
			break;

		case 14918:
			if (!c.getDiaryManager().getWildernessDiary().hasDoneAll()) {
				c.sendMessage("You must have completed the whole wilderness diary to use this shortcut.");
				return;
			}

			if (c.getY() > 3808) {
				AgilityHandler.delayEmote(c, "JUMP", 3201, 3807, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 3201, 3810, 0, 2);
			}
			break;

		case 29728:
			if (c.getY() > 3508) {
				AgilityHandler.delayEmote(c, "JUMP", 1722, 3507, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 1722, 3512, 0, 2);
			}
			break;

		case 28893:
			if (c.getSkills().getLevel(Skill.AGILITY) < 54) {
				c.sendMessage("You need an Agility level of 54 to pass this.");
				return;
			}
			if (c.getY() > 10064) {
				AgilityHandler.delayEmote(c, "JUMP", 1610, 10062, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 1613, 10069, 0, 2);
			}
			break;

		case 27987: // scorpia
			if (c.getX() == 1774) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1769, 3849, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 1774, 3849, 0, 2);
			}
			break;

		case 27988: // scorpia
			if (c.getX() == 1774) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1769, 3849, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 1774, 3849, 0, 2);
			}
			break;

		case 27985:
			if (c.getY() > 3872) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1761, 3871, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 1761, 3874, 0, 2);
			}
			break;

		case 27984:
			if (c.getY() > 3872) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1761, 3871, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 1761, 3874, 0, 2);
			}
			break;

		case 29730:
			if (c.getX() > 1604) {
				AgilityHandler.delayEmote(c, "JUMP", 1603, 3571, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "JUMP", 1607, 3571, 0, 2);
			}
			break;

		case 25014:
			if (Boundary.isIn(c, Boundary.PURO_PURO)) {
				c.getPA().startTeleport(2525, 2916, 0, "puropuro", false);
			} else {
				c.getPA().startTeleport(2592, 4321, 0, "puropuro", false);
			}
			break;
			
			/*
			 * Hydra Dungeon Rocks/Entrances & Drake + Wyrms
			 * TODO: movment to drake area, movement to wyrm area, dmg when switching boots inside the dungeon
			 */
		case 34544:		
			if (c.getY() == 10205 || c.getY() == 10206) { //going west on west rocks
				AgilityHandler.delayEmote(c, 839, (c.getX() == 1303 || c.getX() == 1322) ? c.getX() - 2 : c.getX() + 2, c.getY(), 0, 2);
			} else if (c.getY() == 10214 || c.getY() == 10216) {
				AgilityHandler.delayEmote(c, 839, c.getX(), c.getY() == 10214 ? c.getY() + 2 : c.getY() - 2, 0, 2);
			}
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {

					if (c.disconnected || c.getHealth().getAmount() <= 0 || !Boundary.isIn(c, Boundary.HYDRA_ROOMS)) {
						container.stop();
						return;
					}

					if (Boundary.isIn(c, Boundary.HYDRA_ROOMS)) {
						if (c.getItems().isWearingItem(23037) || c.getItems().isWearingItem(22951) || c.getItems().isWearingItem(21643)) {
							return;
						}
						c.appendDamage(1, Hitmark.HIT);
					}
				}
			}, 3); // handles delay between dmg (in ticks | 600ms)
			break;
			
		case 34530:// drake stairs
			c.getPA().movePlayer(1334, 10205, 1);
			break;
		case 34531:// drake stairs
			c.getPA().movePlayer(1329, 10205, 0);
			break;

		case 4154:// lizexit
			c.getPA().movePlayer(1465, 3687, 0);
			break;

		case 30366:// Mining Guild Entrance
			if (c.getX() == 3043 && c.getY() == 9730) {
				if (c.getSkills().getLevel(Skill.MINING) >= 60) {
					c.getPA().movePlayer(3043, 9729, 0);
				} else {
					c.sendMessage("You must have a mining level of 60 to enter.");
				}
			} else if (c.getX() == 3043 && c.getY() == 9729) {
				c.getPA().movePlayer(3043, 9730, 0);
			}
			break;

		case 30365:// Mining Guild Entrance
			if (c.getX() == 3019 && c.getY() == 9733) {
				if (c.getSkills().getLevel(Skill.MINING) >= 60) {
					c.getPA().movePlayer(3019, 9732, 0);
				} else {
					c.sendMessage("You must have a mining level of 60 to enter.");
				}
			} else if (c.getX() == 3019 && c.getY() == 9732) {
				c.getPA().movePlayer(3019, 9733, 0);
			}
			break;

		case 8356:
			c.getDH().sendDialogues(55874, 2200);
			break;
			
		case 4004:
			int InterfaceId = 38000;
			Wogw.open(c, InterfaceId);
			break;

		case 1727:
		case 1728: // Kbd gates
			if (c.getX() == 3007) {
				c.getPA().walkTo(+1, 0);
			} else if (c.getX() == 3008) {
				c.getPA().walkTo(-1, 0);
			} else if (c.getX() == 2816) {
				c.getPA().walkTo(-1, 0);				
			} else if (c.getX() == 2815) {
				c.getPA().walkTo(+1, 0);				
			}
			break;

		case 10439:
		case 7814:
			PlayerAssistant.refreshHealthWithoutPenalty(c);
			break;
		case 2670:
			if (!c.getItems().playerHasItem(1925) || !c.getItems().playerHasItem(946)) {
				c.sendMessage("You must have an empty bucket and a knife to do this.");
				return;
			}
			c.getItems().deleteItem(1925, 1);
			c.getItems().addItem(1929, 1);
			c.sendMessage("You cut the cactus and pour some water into the bucket.");
			c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.CUT_CACTUS);
			break;
		// Carts Start
		case 7029:
			TrainCart.handleInteraction(c);
			break;
		case 28837:
			c.getDH().sendDialogues(193193, -1);
			break;
		// Carts End
		case 10321:
			c.getPA().spellTeleport(1752, 5232, 0, false);
			c.sendMessage("Welcome to the Giant Mole cave, try your luck for a granite maul.");
			break;
		case 1294:
			c.getDH().tree = "stronghold";
			c.getDH().sendDialogues(65, -1);
			break;

		case 1293:
			c.getDH().tree = "village";
			c.getDH().sendDialogues(65, -1);
			break;

		case 1295:
			c.getDH().tree = "grand_exchange";
			c.getDH().sendDialogues(65, -1);
			break;

		case 2073:
			c.getItems().addItem(1963, 1);
			c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.PICK_BANANAS);
			break;

		case 20877:
			AgilityHandler.delayFade(c, "CRAWL", 2712, 9564, 0, "You crawl into the entrance.",
					"and you end up in a dungeon.", 3);
			c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.ENTER_BRIMHAVEN_DUNGEON);
			break;
		case 20878:
			AgilityHandler.delayFade(c, "CRAWL", 1571, 3659, 0, "You crawl into the entrance.",
					"and you end up in a dungeon.", 3);
			c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.ENTER_BRIMHAVEN_DUNGEON);
			break;
		case 16675:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 2445, 3416, 1, 2);
			break;
		case 16677:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 2445, 3416, 0, 2);
			break;

		case 6434:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3118, 9644, 0, 2);
			break;

		case 11441:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 2856, 9570, 0, 2);
			break;

		case 18969:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 2857, 3167, 0, 2);
			break;

		case 11835:
			AgilityHandler.delayFade(c, "CRAWL", 2480, 5175, 0, "You crawl into the entrance.",
					"and you end up in Tzhaar City.", 3);
			break;
		case 11836:
			AgilityHandler.delayFade(c, "CRAWL", 1212, 3540, 0, "You crawl into the entrance.",
					"and you end up back on Mt. Quidamortem.", 3);
			break;

		case 155:
			AgilityHandler.delayEmote(c, "BALANCE", 3096, 3359, 0, 2);
			break;
		case 160:
			AgilityHandler.delayEmote(c, 2140, 3098, 3357, 0, 2);
			break;

		case 23568:
			c.getPA().movePlayer(2704, 3205, 0);
			break;

		case 23569:
			c.getPA().movePlayer(2709, 3209, 0);
			break;

		case 17068:
			if (c.getSkills().getLevel(Skill.AGILITY) < 8 || c.getSkills().getLevel(Skill.STRENGTH) < 19
					|| c.getSkills().getLevel(Skill.RANGED) < 37) {
				c.sendMessage(
						"You need an agility level of 8, strength level of 19 and ranged level of 37 to do this.");
				return;
			}
			AgilityHandler.delayEmote(c, "JUMP", 3253, 3180, 0, 2);
			c.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.RIVER_LUM_SHORTCUT);
			break;

		case 16465:
			if (!c.getDiaryManager().getDesertDiary().hasCompletedSome("ELITE")) {
				c.sendMessage("You must have completed all tasks in the desert diary to do this.");
				return;
			}
			if (c.getSkills().getLevel(Skill.AGILITY) < 82) {
				c.sendMessage("You need an agility level of at least 82 to squeeze through here.");
				return;
			}
			c.sendMessage("You squeeze through the crevice.");
			if (c.getX() == 3506 && c.getY() == 9505)
				c.getPA().movePlayer(3500, 9510, 2);
			else if (c.getX() == 3500 && c.getY() == 9510)
				c.getPA().movePlayer(3506, 9505, 2);
			break;

		case 2147:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3104, 9576, 0, 2);
			break;
		case 2148:
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3105, 3162, 0, 2);
			break;
		case 1579:
			AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3097, 9868, 0, 2);
			break;
		case 17385:
			if (objectX == 3088) {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 3088, 3572, 0, 2);
			} else {
			AgilityHandler.delayEmote(c, "CLIMB_UP", 3093, 3505, 0, 2);
			}
			break;

		case 27785:
			c.getDH().sendDialogues(70300, -1);
			break;
		case 30266:
			/*if (c != null) {
				c.sendMessage("The Inferno is currently under construction.");
				return;
			}*/
			c.getPA().movePlayer(2495, 5174, 0);
			break;
		case 28894:
		case 28895:
		case 28898:
		case 28897:
		case 28896: // catacomb exits
				c.getPA().movePlayer(1639, 3673, 0);
				c.sendMessage("You return to the statue.");
			break;
		case 882:
			c.getPA().movePlayer(2885, 5292, 2);
			c.sendMessage("Welcome to the Godwars Dungeon!.");
			break;
		case 27777:
			c.getPA().movePlayer(1781, 3412, 0);
			c.sendMessage("Welcome to the CrabClaw Isle, try your luck for a tentacle or Trident of the Seas!.");
			break;
		case 3828:
			c.getPA().movePlayer(3484, 9510, 2);
			c.sendMessage("Welcome to the Kalphite Lair, try your luck for a dragon chain or uncut onyx!.");
			break;

		case 3829:
			c.getPA().movePlayer(1845, 3809, 0);
			c.sendMessage("You find the light of day outside of the tunnel!");
			break;
		case 3832:
			c.getPA().movePlayer(3510, 9496, 2);
			break;

		case 4031:
			if (c.getY() == 3117) {
				if (EquipmentSet.DESERT_ROBES.isWearing(c)) {
					c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PASS_GATE_ROBES);
				} else {
					c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PASS_GATE);
				}
				c.getPA().movePlayer(c.getX(), 3115);
			} else {
				c.getPA().movePlayer(c.getX(), 3117);
			}
			break;

		case 7122:
			if (c.getX() == 2564 && c.getY() == 3310)
				c.getPA().movePlayer(2563, 3310);
			else if (c.getX() == 2563 && c.getY() == 3310)
				c.getPA().movePlayer(2564, 3310);
			break;

		case 24958:
			if (c.getDiaryManager().getVarrockDiary().hasCompleted("EASY")) {
				if (c.getX() == 3143 && c.getY() == 3443)
					c.getPA().movePlayer(3143, 3444);
				else if (c.getX() == 3143 && c.getY() == 3444)
					c.getPA().movePlayer(3143, 3443);
			} else {
				c.sendMessage("You must have completed all easy tasks in the varrock diary to enter.");
				return;
			}
			break;

		case 10045:
			if (c.getDiaryManager().getVarrockDiary().hasCompleted("EASY")) {
				if (c.getX() == 3143 && c.getY() == 3452)
					c.getPA().movePlayer(3144, 3452);
				else if (c.getX() == 3144 && c.getY() == 3452)
					c.getPA().movePlayer(3143, 3452);
			} else {
				c.sendMessage("You must have completed all easy tasks in the varrock diary to enter.");
				return;
			}
			break;

		case 11780:
			if (c.getDiaryManager().getVarrockDiary().hasCompleted("HARD")) {
				if (c.getX() == 3255)
					c.getPA().movePlayer(3256, c.getY());
				else
					c.getPA().movePlayer(3255, c.getY());
			} else {
				c.sendMessage("You must have completed all hard tasks in the varrock diary to enter.");
				return;
			}
			break;
		case 1805:
			if (c.getDiaryManager().getVarrockDiary().hasCompleted("EASY")) {
				c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.CHAMPIONS_GUILD);
				if (c.getY() == 3362)
					c.getPA().movePlayer(c.getX(), 3363);
				else
					c.getPA().movePlayer(c.getX(), 3362);
			} else {
				c.sendMessage("You must have completed all easy tasks in the varrock diary to enter.");
				return;
			}
			break;

		case 538:
			c.getPA().movePlayer(2280, 10016, 0);
			break;

		case 537:
			c.getPA().movePlayer(2280, 10022, 0);
			break;

		case 6462: // Ice gate
		case 6461:
			c.getPA().movePlayer(2852, 3809, 2);
			break;

		case 6456: // Ice ledge
			c.getPA().movePlayer(2855, c.getY(), 1);
			break;

		case 6455: // Ice ledge (Bottom)
			if (c.getY() >= 3804)
				c.getPA().movePlayer(2837, 3803, 1);
			else
				c.getPA().movePlayer(2837, 3805, 0);
			break;

		case 677:	
			if (c.getX() <= 2970)
				c.getPA().movePlayer(2974, 4384, 2);
			else if (c.getX() >= 2979)
				c.getPA().movePlayer(2970, 4384, 2);		
			break;

		case 13641: // Teleportation Device
			c.getDH().sendDialogues(63, -1);
			break;

		case 23104:
			if (System.currentTimeMillis() - c.cerbDelay > 5000) {
				Cerberus cerb = c.createCerberusInstance();
				if (!c.debugMessage)
					if (!c.getSlayer().getTask().isPresent()) {
						c.sendMessage("You must have an active cerberus or hellhound task to enter this cave...");
						return;
					}
				if (!c.debugMessage)
					if (!c.getSlayer().getTask().get().getPrimaryName().equals("cerberus")
							&& !c.getSlayer().getTask().get().getPrimaryName().equals("hellhound")) {
						c.sendMessage("You must have an active cerberus or hellhound task to enter this cave...");
						return;
					}
				if (c.getCerberusLostItems().size() > 0) {
					c.getDH().sendDialogues(642, 5870);
					c.nextChat = -1;
					return;
				}

				if (cerb == null) {
					c.sendMessage("We are unable to allow you in at the moment.");
					c.sendMessage("Too many players.");
					return;
				}

				if (World.getWorld().getEventHandler().isRunning(c, "cerb")) {
					c.sendMessage("You're about to fight start the fight, please wait.");
					return;
				}
				c.getCerberus().init();
				c.cerbDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("Please wait a few seconds between clicks.");
			}
			break;

		case 21772:
			if (!Boundary.isIn(c, Boundary.BOSS_ROOM_WEST)) {
				return;
			}
			Cerberus cerb = c.getCerberus();

			if (cerb != null) {
				cerb.end(DisposeTypes.INCOMPLETE);
			} else {
				c.getPA().movePlayer(1309, 1250, 0);
			}
			break;

		case 28900:
			DarkAltar.handleDarkTeleportInteraction(c);
			break;
		case 10061:
		//	WellOfGoodWillObject.handleInteraction(c, 0);
			break;
		case 28925:
			DarkAltar.handlePortalInteraction(c);
			break;

		case 23105:
			c.appendDamage(5, Hitmark.HIT);
			if (c.getY() == 1241) {
				c.getPA().walkTo(0, +2);
			} else {
				if (c.getCerberus() != null) {
					c.getCerberus().end(DisposeTypes.INCOMPLETE);
					c.getPA().movePlayer(1309, 1250, 0);
				}
			}
			break;

		case 12355:
			RecipeForDisaster rfd = c.createRecipeForDisasterInstance();

			if (c.rfdChat == 1) {
				if (rfd == null) {
					c.sendMessage("We are unable to allow you to start the minigame.");
					c.sendMessage("Too many players.");
					return;
				}

				if (World.getWorld().getEventHandler().isRunning(c, "rfd")) {
					c.sendMessage("You're about to fight start the minigame, please wait.");
					return;
				}
				c.getrecipeForDisaster().init();
			} else {
				c.getDH().sendDialogues(58, 4847);
			}
			break;

		case 12356: // Rfd Portal
			if (!Boundary.isIn(c, Boundary.RFD)) {
				return;
			}
			rfd = c.getrecipeForDisaster();

			if (rfd != null) {
				rfd.end(DisposeTypes.INCOMPLETE);
			} else {
				c.getPA().movePlayer(3218, 9622, 0);
			}
			break;

		case 4383:
			DagannothMother mother = c.createDagannothMotherInstance();

			if (mother == null) {
				c.sendMessage("We are unable to allow you to fight the mother.");
				c.sendMessage("She is already fighting too many players.");
				return;
			}

			if (World.getWorld().getEventHandler().isRunning(c, "dagannoth_mother")) {
				c.sendMessage("You're about to fight the mother, please wait.");
				return;
			}

			c.getDagannothMother().init();
			break;

		case 4577: // Lighthouse door
			if (c.getY() >= 3636)
				c.getPA().movePlayer(2509, 3635, 0);
			else
				c.getPA().movePlayer(2509, 3636, 0);
			break;
			
		case 30364: // mining guild door
			if (c.getY() == 9756) {
				c.getPA().movePlayer(3046, 9757, 0);
		}
			else if (c.getY() == 9757) {
				c.getPA().movePlayer(3046, 9756, 0);
		}
			break;

		case 4413:
			if (!Boundary.isIn(c, Boundary.LIGHTHOUSE)) {
				return;
			}
			mother = c.getDagannothMother();

			if (mother != null) {
				c.getDagannothMother().end(DisposeType.INCOMPLETE);
			} else {
				c.getPA().movePlayer(2509, 3639, 0);
			}
			break;

		case 13642: // Lectern
			c.getDH().sendDialogues(10, -1);
			break;

		case 8930:
			c.getPA().movePlayer(1975, 4409, 3);
			break;

		case 10177: // Dagganoth kings ladder
			c.getPA().movePlayer(2900, 4449, 0);
			break;

		case 10193:
			c.getPA().movePlayer(2545, 10143, 0);
			break;

		case 10195:
			c.getPA().movePlayer(1809, 4405, 2);
			break;

		case 10196:
			c.getPA().movePlayer(1807, 4405, 3);
			break;

		case 10197:
			c.getPA().movePlayer(1823, 4404, 2);
			break;

		case 10198:
			c.getPA().movePlayer(1825, 4404, 3);
			break;

		case 10199:
			c.getPA().movePlayer(1834, 4388, 2);
			break;

		case 10200:
			c.getPA().movePlayer(1834, 4390, 3);
			break;

		case 10201:
			c.getPA().movePlayer(1811, 4394, 1);
			break;

		case 10202:
			c.getPA().movePlayer(1812, 4394, 2);
			break;

		case 10203:
			c.getPA().movePlayer(1799, 4386, 2);
			break;

		case 10204:
			c.getPA().movePlayer(1799, 4388, 1);
			break;

		case 10205:
			c.getPA().movePlayer(1796, 4382, 1);
			break;

		case 10206:
			c.getPA().movePlayer(1796, 4382, 2);
			break;

		case 10207:
			c.getPA().movePlayer(1800, 4369, 2);
			break;

		case 10208:
			c.getPA().movePlayer(1802, 4370, 1);
			break;

		case 10209:
			c.getPA().movePlayer(1827, 4362, 1);
			break;

		case 10210:
			c.getPA().movePlayer(1825, 4362, 2);
			break;

		case 10211:
			c.getPA().movePlayer(1863, 4373, 2);
			break;

		case 10212:
			c.getPA().movePlayer(1863, 4371, 1);
			break;

		case 10213:
			c.getPA().movePlayer(1864, 4389, 1);
			break;

		case 10214:
			c.getPA().movePlayer(1864, 4387, 2);
			break;

		case 10215:
			c.getPA().movePlayer(1890, 4407, 0);
			break;

		case 10216:
			c.getPA().movePlayer(1890, 4406, 1);
			break;

		case 10217:
			c.getPA().movePlayer(1957, 4373, 1);
			break;

		case 10218:
			c.getPA().movePlayer(1957, 4371, 0);
			break;

		case 10219:
			c.getPA().movePlayer(1824, 4379, 3);
			break;

		case 10220:
			c.getPA().movePlayer(1824, 4381, 2);
			break;

		case 10221:
			c.getPA().movePlayer(1838, 4375, 2);
			break;

		case 10222:
			c.getPA().movePlayer(1838, 4377, 3);
			break;

		case 10223:
			c.getPA().movePlayer(1850, 4386, 1);
			break;

		case 10224:
			c.getPA().movePlayer(1850, 4387, 2);
			break;

		case 10225:
			c.getPA().movePlayer(1932, 4378, 1);
			break;

		case 10226:
			c.getPA().movePlayer(1932, 4380, 2);
			break;

		case 10227:
			if (c.getX() == 1961 && c.getY() == 4392)
				c.getPA().movePlayer(1961, 4392, 2);
			else
				c.getPA().movePlayer(1932, 4377, 1);
			break;

		case 10228:
			c.getPA().movePlayer(1961, 4393, 3);
			break;

		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
			break;

		/**
		 * Dagannoth king entrance
		 */
		case 10230:
			if (c.getRights().isOrInherits(Right.IRONMAN) || c.getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || c.getRights().isOrInherits(Right.HC_IRONMAN)) {
				c.getPA().movePlayer(2899, 4449, 4);
			} else {
				c.getPA().movePlayer(2899, 4449, 0);
			}
			break;

		case 8958:
			if (c.getX() <= 2490)
				c.getPA().movePlayer(2492, 10163, 0);
			if (c.getX() >= 2491)
				c.getPA().movePlayer(2490, 10163, 0);
			break;
		case 8959:
			if (c.getX() <= 2490)
				c.getPA().movePlayer(2492, 10147, 0);
			if (c.getX() >= 2491)
				c.getPA().movePlayer(2490, 10147, 0);
			break;
		case 8960:
			if (c.getX() <= 2490)
				c.getPA().movePlayer(2492, 10131, 0);
			if (c.getX() >= 2491)
				c.getPA().movePlayer(2490, 10131, 0);
			break;
		//
		case 26724:
			if (c.getSkills().getLevel(Skill.AGILITY) < 72) {
				c.sendMessage("You need an agility level of 72 to cross over this mud slide.");
				return;
			}
			if (c.getX() == 2427 && c.getY() == 9767) {
				c.getPA().movePlayer(2427, 9762);
			} else if (c.getX() == 2427 && c.getY() == 9762) {
				c.getPA().movePlayer(2427, 9767);
			}
			break;
		case 535:
			if (objectX == 3722 && objectY == 5798) {
				if (c.getMode().isIronman() || c.getMode().isUltimateIronman() || c.getMode().isHcIronman() || c.getMode().isGroupIronman()) {
					c.getPA().movePlayer(3677, 5775, 4);
				} else {
					c.getPA().movePlayer(3677, 5775, 0);
				}
			}
			break;

		

		case 26720:
			if (objectX == 2427 && objectY == 9747) {
				if (c.getX() == 2427 && c.getY() == 9748) {
					c.getPA().movePlayer(2427, 9746);
				} else if (c.getX() == 2427 && c.getY() == 9746) {
					c.getPA().movePlayer(2427, 9748);
				}
			} else if (objectX == 2420 && objectY == 9750) {
				if (c.getX() == 2420 && c.getY() == 9751) {
					c.getPA().movePlayer(2420, 9749);
				} else if (c.getX() == 2420 && c.getY() == 9749) {
					c.getPA().movePlayer(2420, 9751);
				}
			} else if (objectX == 2418 && objectY == 9742) {
				if (c.getX() == 2418 && c.getY() == 9741) {
					c.getPA().movePlayer(2418, 9743);
				} else if (c.getX() == 2418 && c.getY() == 9743) {
					c.getPA().movePlayer(2418, 9741);
				}
			} else if (objectX == 2357 && objectY == 9778) {
				if (c.getX() == 2358 && c.getY() == 9778) {
					c.getPA().movePlayer(2356, 9778);
				} else if (c.getX() == 2356 && c.getY() == 9778) {
					c.getPA().movePlayer(2358, 9778);
				}
			} else if (objectX == 2388 && objectY == 9740) {
				if (c.getX() == 2389 && c.getY() == 9740) {
					c.getPA().movePlayer(2387, 9740);
				} else if (c.getX() == 2387 && c.getY() == 9740) {
					c.getPA().movePlayer(2389, 9740);
				}
			} else if (objectX == 2379 && objectY == 9738) {
				if (c.getX() == 2380 && c.getY() == 9738) {
					c.getPA().movePlayer(2378, 9738);
				} else if (c.getX() == 2378 && c.getY() == 9738) {
					c.getPA().movePlayer(2380, 9738);
				}
			}
			break;

		case 26721:
			if (objectX == 2358 && objectY == 9759) {
				if (c.getX() == 2358 && c.getY() == 9758) {
					c.getPA().movePlayer(2358, 9760);
				} else if (c.getX() == 2358 && c.getY() == 9760) {
					c.getPA().movePlayer(2358, 9758);
				}
			}
			if (objectX == 2380 && objectY == 9750) {
				if (c.getX() == 2381 && c.getY() == 9750) {
					c.getPA().movePlayer(2379, 9750);
				} else if (c.getX() == 2379 && c.getY() == 9750) {
					c.getPA().movePlayer(2381, 9750);
				}
			}
			break;

		case 154:
			if (objectX == 2356 && objectY == 9783) {
				if (c.getSkills().getLevel(Skill.SLAYER) < 93) {
					c.sendMessage("You need a slayer level of 93 to enter into this crevice.");
					return;
				}
				c.getPA().movePlayer(3748, 5761, 0);
			}
			break;

		case 534:
			if (objectX == 3748 && objectY == 5760) {
				c.getPA().movePlayer(2356, 9782, 0);
			}
			break;
		case 9706:
			if(c.maRound !=2){
				c.sendMessage("@blu@Please talk to Kolodion before having access to this magical arena. He is located in Mage Bank towards the east.");
				return;
			}
			if (objectX == 3104 && objectY == 3956) {
				c.getPA().startLeverTeleport(3105, 3951, 0);
			}
			break;

		case 9707:
			if (objectX == 3105 && objectY == 3952) {
				c.getPA().startLeverTeleport(3105, 3956, 0);
			}
			break;
		case 3610:
			if (objectX == 3550 && objectY == 9695) {
				c.getPA().startTeleport(3565, 3308, 0, "modern", false);
			}
			break;
		case 26561:
			if (objectX == 2913 && objectY == 5300) {
				c.getPA().movePlayer(2914, 5300, 1);
			}
			break;
		case 26562:
			if (objectX == 2920 && objectY == 5274) {
				c.getPA().movePlayer(2920, 5274, 0);
			}
			break;
		case 26504:
			if (objectX == 2908 && objectY == 5265) {
				c.getGodwars().enterBossRoom(God.SARADOMIN);
			}
			break;
		case 26518:
			if (objectX == 2885 && objectY == 5333) {
				c.getPA().movePlayer(2885, 5344);
			} else if (objectX == 2885 && objectY == 5344) {
				c.getPA().movePlayer(2885, 5333);
			}
			break;
		case 26505:
			if (objectX == 2925 && objectY == 5332) {
				c.getGodwars().enterBossRoom(God.ZAMORAK);
			}
		case 26503:
			if (objectX == 2863 && objectY == 5354) {
				c.getGodwars().enterBossRoom(God.BANDOS);
			}
			break;
		case 26380:
			if (objectX == 2871 && objectY == 5270) {
				if (c.getY() == 5279) {
					c.getPA().movePlayer(2872, 5269);
				} else if (c.getY() == 5269) {
					c.getPA().movePlayer(2872, 5279);
				}
			}
			break;
		case 21578: // Stairs up
		case 10:
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3096, 9867, 0, 2);
			break;
		case 26502:
			if (objectX == 2839 && objectY == 5295) {
				c.getGodwars().enterBossRoom(God.ARMADYL);
			}
			break;
		case 7674:
			c.getFarming().farmPoisonBerry();
			break;
		case 172:
		case 170:
			CrystalChest.searchChest(c);
			break;
		case 33114:
			if (c.getItems().playerHasItem(13305)) {
				c.getItems().removeFromBank(13305, 1000, true);
				c.getItems().deleteItem(13305, 1000);
				c.startAnimation(829);
				c.getDH().sendItemStatement("You get the urge to eat your key and without thinking, you eat it?", 15);
				c.addDamageTaken(c, 10);
				return;
			}
			if (c.getItems().playerHasItem(13303, 1)) {
		    	EventBossChest.execute(c);
			    return;
			}
			if (c.getItems().playerHasItem(13302, 1)) {
			    EnragedGraardorDrops.execute(c);
			    return;
		  }
			break;
		case 29335:
			if (c.getItems().playerHasItem(33592, 1)) {
				NightmareDrops.execute(c);
				return;
			}
			break;
		case 23319:
			InfernalChest.searchChest(c);
			break;
		case 17205:
			SlayerChest.searchChest(c);
			break;
		case 4873:
		case 26761:
			c.getPA().startLeverTeleport(3158, 3953, 0);
			c.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.WILDERNESS_LEVER);
			break;
		case 7813:
		case 3840: // Compost Bin
			c.getFarming().handleCompostAdd();
			break;
		case 2492:
		case 15638:
		case 7479:
			c.getPA().startTeleport(3088, 3504, 0, "modern", false);
			break;
		case 11803:
			if (c.getRights().isOrInherits(Right.RUBY)) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 3577, 9927, 0, 2);
				c.sendMessage("<img=4> Welcome to the donators only slayer cave.");
			}
			break;
		case 17387:
			if (c.getRights().isOrInherits(Right.RUBY)) {
				AgilityHandler.delayEmote(c, "CLIMB_UP", 2125, 4913, 0, 2);
			}
			break;
		case 25824:
			c.turnPlayerTo(objectX, objectY);
			c.getDH().sendDialogues(40, -1);
			break;

		case 5097:
		case 21725:
			c.getPA().movePlayer(2636, 9510, 2);
			break;
		case 5098:
		case 21726:
			c.getPA().movePlayer(2636, 9517, 0);
			break;
		case 5094:
		case 21722:
			c.getPA().movePlayer(2643, 9594, 2);
			break;
		case 5096:
		case 21724:
			c.getPA().movePlayer(2649, 9591, 0);
			break;
		case 2320:
		case 23566:
			if (objectX == 3119 && objectY == 9964 || objectX == 3121 && objectY == 9963|| objectX == 3120 && objectY == 9963) {
				c.getPA().movePlayer(3120, 9970, 0);
			} else if (objectX == 3119 && objectY == 9969 || objectX == 3120 && objectY == 9970 ||  objectX == 3121 && objectY == 9970||  objectX == 3121 && objectY == 9969) {
				c.getPA().movePlayer(3120, 9963, 0);
			}
			break;
		case 26760:
			if (c.getX() == 3184 && c.getY() == 3945) {
				c.getDH().sendDialogues(631, -1);
			} else if (c.getX() == 3184 && c.getY() == 3944) {
				c.getPA().movePlayer(3184, 3945, 0);
			}
			break;
		case 19206:
		//	if (c.absX == 1502 && c.absY == 3838) {
			//	c.getDH().sendDialogues(63100, -1);
		//	} else if (c.absX == 1502 && c.absY == 3840) {
		//		c.getPA().movePlayer(1502, 3838, 0);
		//	}
			break;
		case 9326:
			if (c.getSkills().getLevel(Skill.AGILITY) < 62) {
				c.sendMessage("You need an Agility level of 62 to pass this.");
				return;
			}
			if (c.getX() < 2769) {
				c.getPA().movePlayer(2775, 10003, 0);
			} else {
				c.getPA().movePlayer(2768, 10002, 0);
			}
			break;
		case 4496:
		case 4494:
			if (c.getHeight() == 2) {
				c.getPA().movePlayer(3412, 3540, 1);
			} else if (c.getHeight() == 1) {
				c.getPA().movePlayer(3418, 3540, 0);
			}
			break;
		case 9319:
			if (c.getHeight() == 0)
				c.getPA().movePlayer(c.getX(), c.getY(), 1);
			else if (c.getHeight() == 1)
				c.getPA().movePlayer(c.getX(), c.getY(), 2);
			break;

		case 9320:
			if (c.getHeight() == 1)
				c.getPA().movePlayer(c.getX(), c.getY(), 0);
			else if (c.getHeight() == 2)
				c.getPA().movePlayer(c.getX(), c.getY(), 1);
			break;
		case 4493:
			if (c.getHeight() == 0) {
				c.getPA().movePlayer(c.getX() - 5, c.getY(), 1);
			} else if (c.getHeight() == 1) {
				c.getPA().movePlayer(c.getX() + 5, c.getY(), 2);
			}
			break;

		case 4495:
			if (c.getHeight() == 1 && c.getY() > 3538 && c.getY() < 3543) {
				c.getPA().movePlayer(c.getX() + 5, c.getY(), 2);
			} else {
				c.sendMessage("I can't reach that!");
			}
			break;
		case 2623:
			if (c.getX() == 2924 && c.getY() == 9803) {
				c.getPA().movePlayer(c.getX() - 1, c.getY(), 0);
			} else if (c.getX() == 2923 && c.getY() == 9803) {
				c.getPA().movePlayer(c.getX() + 1, c.getY(), 0);
			}
			break;
		case 15644:
		case 15641:
		case 24306:
		case 24309:
			if (c.getHeight() == 2) {
				// if(Boundary.isIn(c, WarriorsGuild.WAITING_ROOM_BOUNDARY) &&
				// c.heightLevel == 2) {
				c.getWarriorsGuild().handleDoor();
				return;
				// }
			}
			if (c.getHeight() == 0) {
				if (c.getX() == 2855 || c.getX() == 2854) {
					if (c.getY() == 3546)
						c.getPA().movePlayer(c.getX(), c.getY() - 1, 0);
					else if (c.getY() == 3545)
						c.getPA().movePlayer(c.getX(), c.getY() + 1, 0);
					c.turnPlayerTo(objectX, objectY);
				}
			}
			break;
		case 15653:
			if (c.getY() == 3546) {
				if (c.getX() == 2877)
					c.getPA().movePlayer(c.getX() - 1, c.getY(), 0);
				else if (c.getX() == 2876)
					c.getPA().movePlayer(c.getX() + 1, c.getY(), 0);
				c.turnPlayerTo(objectX, objectY);
			}
			break;

		case 18987: // Kbd ladder
			c.getPA().movePlayer(3069, 10255, 0);
			break;
		case 1817:
			c.getPA().startLeverTeleport(3093, 3500, 0);
			break;

		case 18988:
			c.getPA().movePlayer(3017, 3850, 0);
			break;

		case 24303:
			c.getPA().movePlayer(2840, 3539, 0);
			break;

		case 16671:
			int distanceToPoint = c.distanceToPoint(2840, 3539);
			if (distanceToPoint < 5) {
				c.getPA().movePlayer(2840, 3539, 2);
			}
			break;
		case 2643:
		case 14888:

			JewelryMaking.mouldInterface(c);
			break;
		case 878:
			c.getDH().sendDialogues(613, -1);
			break;
		case 1733:
			if (c.getY() > 3920 && c.inWild())
				c.getPA().movePlayer(3045, 10323, 0);
			break;
		case 1734:
			if (c.getY() > 9000 && c.inWild())
				c.getPA().movePlayer(3044, 3927, 0);
			break;
		case 2466:
			if (c.getY() > 3920 && c.inWild())
				c.getPA().movePlayer(1622, 3673, 0);
			break;
		case 2467:
			c.getPA().spellTeleport(2604, 3154, 0, false);
			c.sendMessage("This is the dicing area. Place a bet on designated hosts.");
			break;
		case 28851:// wcgate
			if (c.getSkills().getLevel(Skill.WOODCUTTING) < 60) {
				c.sendMessage("You need a Woodcutting level of 60 to enter the Woodcutting Guild.");
				return;
			} else {
				c.getPA().movePlayer(1657, 3505, 0);
			}
			break;
		case 28852:// wcgate
			if (c.getSkills().getLevel(Skill.WOODCUTTING) < 60) {
				c.sendMessage("You need a Woodcutting level of 60 to enter the Woodcutting Guild.");
				return;
			} else {
				c.getPA().movePlayer(1657, 3504, 0);
			}
			break;
		case 2309:
			if (c.getX() == 2998 && c.getY() == 3916) {
				c.getAgility().doWildernessEntrance(c, 2998, 3916, false);
			}
			if (c.getX() == 2998 && c.getY() == 3917) {
				c.getPA().movePlayer(2998, 3916, 0);
			}
			break;
		case 1766:
			if (c.inWild() && c.getX() == 3069 && c.getY() == 10255) {
				c.getPA().movePlayer(3017, 3850, 0);
			}
			break;
		case 1765:
			if (c.inWild() && c.getY() >= 3847 && c.getY() <= 3860) {
				c.getPA().movePlayer(3069, 10255, 0);
			}
			break;

		case 2118:
			if (Boundary.isIn(c, new Boundary(3433, 3536, 3438, 3539))) {
				c.getPA().movePlayer(3438, 3537, 0);
			}
			break;

		case 2114:
			if (Boundary.isIn(c, new Boundary(3433, 3536, 3438, 3539))) {
				c.getPA().movePlayer(3433, 3537, 1);
			}
			break;


		case 7108:
		case 7111:
			if (c.getX() == 2907 || c.getX() == 2908) {
				if (c.getY() == 9698) {
					c.getPA().walkTo(0, -1);
				} else if (c.getY() == 9697) {
					c.getPA().walkTo(0, +1);
				}
			}
			break;

		case 2119:
			if (c.getHeight() == 1) {
				if (c.getX() == 3412 && (c.getY() == 3540 || c.getY() == 3541)) {
					c.getPA().movePlayer(3417, c.getY(), 2);
				}
			}
			break;

		case 2120:
			if (c.getHeight() == 2) {
				if (c.getX() == 3417 && (c.getY() == 3540 || c.getY() == 3541)) {
					c.getPA().movePlayer(3412, c.getY(), 1);
				}
			}
			break;

		case 2102:
		case 2104:
			if (c.getHeight() == 1) {
				if (c.getX() == 3426 || c.getX() == 3427) {
					if (c.getY() == 3556) {
						c.getPA().walkTo(0, -1);
					} else if (c.getY() == 3555) {
						c.getPA().walkTo(0, +1);
					}
				}
			}
			break;

		case 1597:
		case 1596:
			// case 7408:
			// case 7407:
			if (c.getY() < 9000) {
				if (c.getY() > 3903) {
					c.getPA().movePlayer(c.getX(), c.getY() - 1, 0);
				} else {
					c.getPA().movePlayer(c.getX(), c.getY() + 1, 0);
				}
			} else if (c.getY() > 9917) {
				c.getPA().movePlayer(c.getX(), c.getY() - 1, 0);
			} else {
				c.getPA().movePlayer(c.getX(), c.getY() + 1, 0);
			}
			break;
		/*
		 * case 1276: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(0, objectX, objectY,
		 * c.clickObjectType); break; case 1278: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(1, objectX, objectY,
		 * c.clickObjectType); break; case 1286: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(2, objectX, objectY,
		 * c.clickObjectType); break; case 1281: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(3, objectX, objectY,
		 * c.clickObjectType); break; case 1308: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(4, objectX, objectY,
		 * c.clickObjectType); break; case 5552: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(5, objectX, objectY,
		 * c.clickObjectType); break; case 1307: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(6, objectX, objectY,
		 * c.clickObjectType); break; case 1309: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(7, objectX, objectY,
		 * c.clickObjectType); break; case 1306: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(8, objectX, objectY,
		 * c.clickObjectType); break; case 5551: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(9, objectX, objectY,
		 * c.clickObjectType); break; case 5553: if (!c.inWc() && !c.inDz()) { return; }
		 * c.getWoodcutting().startWoodcutting(10, objectX, objectY,
		 * c.clickObjectType); break;
		 */

		case 24600:
			c.getDH().sendDialogues(500, -1);
			break;

		case 20973:
			c.getBarrows().useChest();
			break;

		case 20720:
		case 20721:
		case 20722:
		case 20770:
		case 20771:
		case 20772:
			c.getBarrows().spawnBrother(objectId);
			break;

		case 14315:
			PestControl.addToLobby(c);
			break;

		case 14314:
			PestControl.removeFromLobby(c);
			break;

		case 14235:
		case 14233:
			if (objectX == 2670) {
				if (c.getX() <= 2670) {
					c.setX(2671);
				} else {
					c.setX(2670);
				}
			}
			if (objectX == 2643) {
				if (c.getX() >= 2643) {
					c.setX(2642);
				} else {
					c.setX(2643);
				}
			}
			if (c.getX() <= 2585) {
				c.setY(c.getY() + 1);
			} else {
				c.setY(c.getY() - 1);
			}
			c.getPA().movePlayer(c.getX(), c.getY(), 0);
			break;

		case 245:
			c.getPA().movePlayer(c.getX(), c.getY() + 2, 2);
			break;
		case 246:
			c.getPA().movePlayer(c.getX(), c.getY() - 2, 1);
			break;
		case 272:
			c.getPA().movePlayer(c.getX(), c.getY(), 1);
			break;
		case 273:
			c.getPA().movePlayer(c.getX(), c.getY(), 0);
			break;
		/* Godwars Door */
		/*
		 * case 26426: // armadyl if (c.absX == 2839 && c.absY == 5295) {
		 * c.getPA().movePlayer(2839, 5296, 2);
		 * c.sendMessage("@blu@May the gods be with you."); } else {
		 * c.getPA().movePlayer(2839, 5295, 2); } break; case 26425: // bandos if
		 * (c.absX == 2863 && c.absY == 5354) { c.getPA().movePlayer(2864, 5354, 2);
		 * c.sendMessage( "@blu@May the gods be with you."); } else {
		 * c.getPA().movePlayer(2863, 5354, 2); } break; case 26428: // bandos if
		 * (c.absX == 2925 && c.absY == 5332) { c.getPA().movePlayer(2925, 5331, 2);
		 * c.sendMessage("@blu@May the gods be with you."); } else {
		 * c.getPA().movePlayer(2925, 5332, 2); } break; case 26427: // bandos if
		 * (c.absX == 2908 && c.absY == 5265) { c.getPA().movePlayer(2907, 5265, 0);
		 * c.sendMessage("@blu@May the gods be with you."); } else {
		 * c.getPA().movePlayer(2908, 5265, 0); } break;
		 */

		case 5960:
			if (!c.leverClicked) {
				c.getDH().sendDialogues(114, 9985);
				c.leverClicked = true;
			} else {
				c.getPA().startLeverTeleport(3090, 3956, 0);
			}
			break;
		case 5959:
			c.getPA().startLeverTeleport(2539, 4712, 0);
			break;
		case 1814:
			if (Boundary.isIn(c, Boundary.ARDOUGNE_BOUNDARY)) {
				c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.WILDERNESS_LEVER);
			}
			c.getPA().startLeverTeleport(3158, 3953, 0);
			break;
		case 1815:
			c.getPA().startLeverTeleport(3087, 3500, 0);
			break;
		case 1816:
			c.getPA().startLeverTeleport(2271, 4680, 0);
			c.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KBD_LAIR);
			break;
		/* Start Brimhavem Dungeon */
		case 2879:
			c.getPA().movePlayer(2542, 4718, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 5083:
			c.getPA().movePlayer(2713, 9564, 0);
			c.sendMessage("You enter the dungeon.");
			break;

		case 5103:
			if (c.getX() == 2691 && c.getY() == 9564) {
				c.getPA().movePlayer(2689, 9564, 0);
			} else if (c.getX() == 2689 && c.getY() == 9564) {
				c.getPA().movePlayer(2691, 9564, 0);
			}
			break;

		case 5106:
		case 21734:
			if (c.getX() == 2674 && c.getY() == 9479) {
				c.getPA().movePlayer(2676, 9479, 0);
			} else if (c.getX() == 2676 && c.getY() == 9479) {
				c.getPA().movePlayer(2674, 9479, 0);
			}
			break;
		case 5105:
		case 21733:
			if (c.getX() == 2672 && c.getY() == 9499) {
				c.getPA().movePlayer(2674, 9499, 0);
			} else if (c.getX() == 2674 && c.getY() == 9499) {
				c.getPA().movePlayer(2672, 9499, 0);
			}
			break;

		case 5107:
		case 21735:
			if (c.getX() == 2693 && c.getY() == 9482) {
				c.getPA().movePlayer(2695, 9482, 0);
			} else if (c.getX() == 2695 && c.getY() == 9482) {
				c.getPA().movePlayer(2693, 9482, 0);
			}
			break;

		case 21731:
			if (c.getX() == 2691) {
				c.getPA().movePlayer(2689, 9564, 0);
			} else if (c.getX() == 2689) {
				c.getPA().movePlayer(2691, 9564, 0);
			}
			break;

		case 5104:
		case 21732:
			if (c.getX() == 2683 && c.getY() == 9568) {
				c.getPA().movePlayer(2683, 9570, 0);
			} else if (c.getX() == 2683 && c.getY() == 9570) {
				c.getPA().movePlayer(2683, 9568, 0);
			}
			break;

		case 5100:
			if (c.getY() <= 9567) {
				c.getPA().movePlayer(2655, 9573, 0);
			} else if (c.getY() >= 9572) {
				c.getPA().movePlayer(2655, 9566, 0);
			}
			break;
		case 21728:
			if (c.getSkills().getLevel(Skill.AGILITY) < 34) {
				c.sendMessage("You need an Agility level of 34 to pass this.");
				return;
			}
			if (c.getY() == 9566) {
				AgilityHandler.delayEmote(c, "CRAWL", 2655, 9573, 0, 2);
			} else {
				AgilityHandler.delayEmote(c, "CRAWL", 2655, 9566, 0, 2);
			}
			break;
		case 5099:
		case 21727:
			if (c.getSkills().getLevel(Skill.AGILITY) < 34) {
				c.sendMessage("You need an Agility level of 34 to pass this.");
				return;
			}
			if (objectX == 2698 && objectY == 9498) {
				c.getPA().movePlayer(2698, 9492, 0);
			} else if (objectX == 2698 && objectY == 9493) {
				c.getPA().movePlayer(2698, 9499, 0);
			}
			break;
		case 5088:
		case 20882:
			if (c.getSkills().getLevel(Skill.AGILITY) < 30) {
				c.sendMessage("You need an Agility level of 30 to pass this.");
				return;
			}
			c.getPA().movePlayer(2687, 9506, 0);
			break;
		case 5090:
		case 20884:
			if (c.getSkills().getLevel(Skill.AGILITY) < 30) {
				c.sendMessage("You need an Agility level of 30 to pass this.");
				return;
			}
			c.getPA().movePlayer(2682, 9506, 0);
			break;

		case 16511:
			if (c.getSkills().getLevel(Skill.AGILITY) < 51) {
				c.sendMessage("You need an agility level of at least 51 to squeeze through.");
				return;
			}
			if (c.getX() == 3149 && c.getY() == 9906) {
				c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.OBSTACLE_PIPE);
				c.getPA().movePlayer(3155, 9906, 0);
			} else if (c.getX() == 3155 && c.getY() == 9906) {
				c.getPA().movePlayer(3149, 9906, 0);
			}
			break;

		case 5110:
		case 21738:
			if (c.getSkills().getLevel(Skill.AGILITY) < 12) {
				c.sendMessage("You need an Agility level of 12 to pass this.");
				return;
			}
			c.getPA().movePlayer(2647, 9557, 0);
			break;
		case 5111:
		case 21739:
			if (c.getSkills().getLevel(Skill.AGILITY) < 12) {
				c.sendMessage("You need an Agility level of 12 to pass this.");
				return;
			}
			c.getPA().movePlayer(2649, 9562, 0);
			break;
		case 27362:// lizardmen
			if (c.getY() > 3688) {
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1454, 3690, 0, 2);
				c.sendMessage("You climb down into Shayzien Assault.");
			} else
				AgilityHandler.delayEmote(c, "CLIMB_DOWN", 1477, 3690, 0, 2);
			c.sendMessage("You climb down into Lizardman Camp.");
			break;
		case 4155:// zulrah
			c.getPA().movePlayer(2200, 3055, 0);
			c.sendMessage("You climb down.");
			break;
		case 5084:
			c.getPA().movePlayer(2744, 3151, 0);
			c.sendMessage("You exit the dungeon.");
			break;
		/* End Brimhavem Dungeon */
		case 6481:
			c.getPA().movePlayer(3233, 9315, 0);
			break;

		/*
		 * case 17010: if (c.playerMagicBook == 0) {
		 * c.sendMessage("You switch spellbook to lunar magic.");
		 * c.setSidebarInterface(6, 29999); c.playerMagicBook = 2; c.autocasting =
		 * false; c.autocastId = -1; c.getPA().resetAutocast(); break; } if
		 * (c.playerMagicBook == 1) {
		 * c.sendMessage("You switch spellbook to lunar magic.");
		 * c.setSidebarInterface(6, 29999); c.playerMagicBook = 2; c.autocasting =
		 * false; c.autocastId = -1; c.getPA().resetAutocast(); break; } if
		 * (c.playerMagicBook == 2) { c.setSidebarInterface(6, 1151); c.playerMagicBook
		 * = 0; c.autocasting = false;
		 * c.sendMessage("You feel a drain on your memory."); c.autocastId = -1;
		 * c.getPA().resetAutocast(); break; } break;
		 */

		case 1551:
			if (c.getX() == 3252 && c.getY() == 3266) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.getX() == 3253 && c.getY() == 3266) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;
		case 1553:
			if (c.getX() == 3252 && c.getY() == 3267) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.getX() == 3253 && c.getY() == 3267) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;
		case 3044:
		case 24009:
		case 26300:
		case 16469:
		case 11010:
		case 14838:
		case 2030:
			c.getSmithing().sendSmelting();
			break;
		/*
		 * case 2030: if (c.absX == 1718 && c.absY == 3468) {
		 * c.getSmithing().sendSmelting(); } else { c.getSmithing().sendSmelting(); }
		 * break;
		 */

		/* AL KHARID */
		case 2883:
		case 2882:
			c.getDH().sendDialogues(1023, 925);
			break;
		// case 2412:
		// Sailing.startTravel(c, 1);
		// break;
		// case 2414:
		// Sailing.startTravel(c, 2);
		// break;
		// case 2083:
		// Sailing.startTravel(c, 5);
		// break;
		// case 2081:
		// Sailing.startTravel(c, 6);
		// break;
		// case 14304:
		// Sailing.startTravel(c, 14);
		// break;
		// case 14306:
		// Sailing.startTravel(c, 15);
		// break;

		case 2213:
		case 24101:
		case 3045:
		case 14367:
		case 3193:
		case 10517:
		case 11402:
		case 26972:
		case 4483:
		case 25808:
		case 11744:
		case 10060:
		case 12309:
		case 10058:
		case 2693:
		case 21301:
		case 6943:
		case 3194:
		case 10661:
			c.getPA().openUpBank();
			break;

		case 21305:
			if (c.getItems().playerHasItem(10810, 5)) {
				c.getItems().deleteItem2(10810, 5);
				c.getItems().addItem(10826, 1);
				c.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.FREMENNIK_SHIELD);
			} else {
				c.sendMessage("You need 5 arctic pine logs to create a fremennik shield.");
				return;
			}
			break;
		
		case 21505:
		case 21507:
			if (c.getX() <= 2328) {
				c.getPA().movePlayer(2329, c.getY(), 0);
			} else if (c.getX() >= 2329) {
				c.getPA().movePlayer(2328, c.getY(), 0);
			}
			break;
		case 3506:
		case 3507:
			if (c.getY() == 3458) {
				c.getPA().movePlayer(c.getX(), 3457, 0);
			c.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.MORYTANIA_SWAMP);
		} else if (c.getY() == 3457) {
				c.getPA().movePlayer(c.getX(), 3458, 0);
		}
			break;

		case 11665:
			if (c.getX() == 2658) {
				c.getPA().movePlayer(2659, 3437, 0);
			c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.RANGING_GUILD);
			} else if (c.getX() == 2659) {
				c.getPA().movePlayer(2657, 3439, 0);
			}
			break;

		/**
		 * Entering the Fight Caves.
		 */
		case 11833:
			if (Boundary.entitiesInArea(Boundary.FIGHT_CAVE) >= 50) {
				c.sendMessage("There are too many people using the fight caves at the moment. Please try again later");
				return;
			}
			c.getDH().sendDialogues(633, -1);
			break;
		
		case 30396: //Raids Lobbies
			if (Boundary.isIn(c, Boundary.XERIC_LOBBY_ENTRANCE)) {
				LobbyManager.get(LobbyType.TRIALS_OF_XERIC)
				.ifPresent(lobby -> lobby.attemptJoin(c));
				break;
			}
			if  (Boundary.isIn(c, Boundary.XERIC_LOBBY)) {
				LobbyManager.get(LobbyType.TRIALS_OF_XERIC)
				.ifPresent(lobby -> lobby.attemptLeave(c));
				break;			
			}
			if (Boundary.isIn(c, Boundary.RAIDS_LOBBY_ENTRANCE)) {
				LobbyManager.get(LobbyType.CHAMBERS_OF_XERIC)
				.ifPresent(lobby -> lobby.attemptJoin(c));
				break;
			}
			if  (Boundary.isIn(c, Boundary.RAIDS_LOBBY)) {
				LobbyManager.get(LobbyType.CHAMBERS_OF_XERIC)
				.ifPresent(lobby -> lobby.attemptLeave(c));
				break;			
			}
			if (Boundary.isIn(c, Boundary.THEATRE_LOBBY_ENTRANCE)) {
				LobbyManager.get(LobbyType.THEATRE_OF_BLOOD)
				.ifPresent(lobby -> lobby.attemptJoin(c));
				break;
			}
			if (Boundary.isIn(c, Boundary.THEATRE_LOBBY)) {
				LobbyManager.get(LobbyType.THEATRE_OF_BLOOD)
				.ifPresent(lobby -> lobby.attemptLeave(c));
				break;
			}
			System.out.println("LOBBY OBJECT JOIN FAILURE! NO CONDITION MET!");
			c.sendMessage("This Lobby is not yet in use! New minigame coming soon!");
			break;
			 
			
		/*case 29879:		
		if (!Boundary.isIn(c, Boundary.RAIDS_LOBBY)) {
			c.getPA().movePlayer((3040+Misc.random(-3,3)), (9936+Misc.random(-3,3)));
			c.getRaids();
			Raids.joinRaidLobby(c);
			break;
		}
		if  (Boundary.isIn(c, Boundary.RAIDS_LOBBY)) {
			Player.move(c, 0, 2);
			XericLobby.removePlayer(c);
			c.sendMessage("You have left the Raids lobby");		
			break;	
		}*/

		case 20667:
		case 20668:
		case 20669:
		case 20670:
		case 20671:
		case 20672:
			c.getBarrows().moveUpStairs(objectId);
			break;

		/**
		 * Clicking on the Ancient Altar.
		 */
		case 6552:
			if (c.inWild()) {
				return;
			}
			c.autocasting = false;
			c.autocastId = -1;
			c.getPA().resetAutocast();
			if (c.getY() == 9312) {
				c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.ACTIVATE_ANCIENT);
			}
			PlayerAssistant.switchSpellBook(c);
			break;

		/**
		 * c.setSidebarInterface(6, 1151); Recharing prayer points.
		 */
		case 20377:
			if (c.inWild()) {
				return;
			}
			if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {

				if(c.getSkills().getActualLevel(Skill.PRAYER) >= 85) {
					c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PRAY_SOPHANEM);
				}
				c.startAnimation(645);
				c.getSkills().resetToActualLevel(Skill.PRAYER);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 61:
			if (c.inWild()) {
				return;
			}
			if (c.getY() >= 3508 && c.getY() <= 3513) {
				if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
					if (Boundary.isIn(c, Boundary.VARROCK_BOUNDARY)
							&& c.getDiaryManager().getVarrockDiary().hasCompleted("HARD")) {
						if (c.prayerActive[25]) {
							c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.PRAY_WITH_PIETY);
						}
					}
					c.startAnimation(645);
					c.getSkills().resetToActualLevel(Skill.PRAYER);
					c.sendMessage("You recharge your prayer points.");
					c.getPA().refreshSkill(5);
				} else {
					c.sendMessage("You already have full prayer points.");
				}
			}
			break;

		case 410:
			if (c.inWild()) {
				return;
			}
			if (c.getSkills().getLevel(Skill.PRAYER) == c.getSkills().getActualLevel(Skill.PRAYER)) {
				c.sendMessage("You already have full prayer points.");
				return;
			}
			if (Boundary.isIn(c, Boundary.TAVERLY_BOUNDARY)) {
				if (c.getItems().isWearingItem(5574) && c.getItems().isWearingItem(5575)
						&& c.getItems().isWearingItem(5576)) {
					c.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.ALTAR_OF_GUTHIX);
				}
			}
			c.startAnimation(645);
			c.getSkills().resetToActualLevel(Skill.PRAYER);
			c.sendMessage("You recharge your prayer points.");
			c.getPA().refreshSkill(5);
			break;

		case 409:
		case 6817:
		case 14860:
			if (c.inWild()) {
				return;
			}
			if (c.getSkills().getLevel(Skill.PRAYER) == c.getSkills().getActualLevel(Skill.PRAYER)) {
				c.sendMessage("You already have full prayer points.");
				return;
			}
			if (Boundary.isIn(c, Boundary.VARROCK_BOUNDARY)) {
				if (c.prayerActive[23]) {
					c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.PRAY_WITH_SMITE);
				}
			}
			if (Boundary.isIn(c, Boundary.ARDOUGNE_BOUNDARY)) {
				if (c.prayerActive[25]) {
					if (!c.getDiaryManager().getArdougneDiary().hasCompleted("MEDIUM")) {
						c.sendMessage("You must have completed all the medium tasks in the ardougne diary to do this.");
						return;
					}
					c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PRAY_WITH_CHIVALRY);
				}
			}
			c.startAnimation(645);
			c.getSkills().resetToActualLevel(Skill.PRAYER);
			c.sendMessage("You recharge your prayer points.");
			c.getPA().refreshSkill(5);
			break;

		case 411:
			if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
				if (c.inWild()) {
					c.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.WILDERNESS_ALTAR);
				}
				c.startAnimation(645);
				c.getSkills().resetToActualLevel(Skill.PRAYER);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;

		case 14896:
			c.turnPlayerTo(objectX, objectY);
			FlaxPicking.getInstance().pick(c, new Location(objectX, objectY, c.getHeight()));
			break;

		case 412:
			if (c.inWild()) {
				return;
			}
			if (c.getMode().isIronman() || c.getMode().isUltimateIronman() || c.getMode().isHcIronman() || c.getMode().isGroupIronman()) {
				c.sendMessage("Your game mode prohibits use of this altar.");
				return;
			}
			// if (c.absY >= 3504 && c.absY <= 3507) {
			if (c.specAmount < 10.0) {
				if (c.specRestore > 0) {
					int seconds = ((int) Math.floor(c.specRestore * 0.6));
					c.sendMessage("You have to wait another " + seconds + " seconds to use this altar.");
					return;
				}
				if (c.getRights().isOrInherits(Right.RUBY)) {
					c.specRestore = 120;
					c.specAmount = 10.0;
					c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
					c.sendMessage("Your special attack has been restored. You can restore it again in 3 minutes.");
				} else {
					c.specRestore = 240;
					c.specAmount = 10.0;
					c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
					c.sendMessage("Your special attack has been restored. You can restore it again in 6 minutes.");
				}
			}
			// }
			break;

		case 26366: // Godwars altars
		case 26365:
		case 26364:
		case 26363:
			if (c.inWild()) {
				return;
			}
			if (c.gwdAltar > 0) {
				int seconds = ((int) Math.floor(c.gwdAltar * 0.6));
				c.sendMessage("You have to wait another " + seconds + " seconds to use this altar.");
				return;
			}
			if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
				c.startAnimation(645);
				c.getSkills().resetToActualLevel(Skill.PRAYER);
				c.sendMessage("You recharge your prayer points.");
				c.gwdAltar = 600;
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;

		/**
		 * Aquring god capes.
		 */
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			} else {
				c.sendMessage("You already have a cape");
			}
			break;

		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:

			break;

		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if (objectX == 3562 && objectY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (objectX == 3558 && objectY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6730:
			if (objectX == 3558 && objectY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (objectX == 3558 && objectY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6727:
			if (objectX == 3551 && objectY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6746:
			if (objectX == 3552 && objectY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6748:
			if (objectX == 3545 && objectY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (objectX == 3541 && objectY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6729:
			if (objectX == 3545 && objectY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (objectX == 3541 && objectY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6726:
			if (objectX == 3534 && objectY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (objectX == 3535 && objectY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6745:
			if (objectX == 3535 && objectY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (objectX == 3534 && objectY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6743:
			if (objectX == 3545 && objectY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (objectX == 3541 && objectY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 6724:
			if (objectX == 3545 && objectY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (objectX == 3541 && objectY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 1516:
		case 1519:
			if (objectY == 9698) {
				if (c.getY() >= objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
				break;
			}

		case 11737:
			if (!c.getRights().isOrInherits(Right.SAPPHIRE)) {
				return;
			}
			c.getPA().movePlayer(3365, 9641, 0);
			break;

		// case 12355:
		// if (!c.getRights().isOrInherits(Right.CONTRIBUTOR)) {
		// return;
		// }
		// c.getPA().movePlayer(3577, 9927, 0);
		// break;

		case 5126:
		case 2100:
			if (c.getY() == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1759:
			if (objectX == 2884 && objectY == 3397)
				c.getPA().movePlayer(c.getX(), c.getY() + 6400, 0);
			break;
		case 1557:
		case 1558:
		case 7169:
			if ((objectX == 3106 || objectX == 3105) && objectY == 9944) {
				if (c.getY() > objectY)
					c.getPA().walkTo(0, -1);
				else
					c.getPA().walkTo(0, 1);
			} else {
				if (c.getX() > objectX)
					c.getPA().walkTo(-1, 0);
				else
					c.getPA().walkTo(1, 0);
			}
			break;
		case 2558:
			c.sendMessage("This door is locked.");
			break;
			
		case 1568:
		case 1569:
			if (c.getY() <= objectY && objectX == 3105) {
				c.getPA().walkTo(0, 1);
			} else {
				c.getPA().walkTo(0, -1);
			}
			break;

		case 9294:
			if (c.getX() < objectX) {
				c.getPA().movePlayer(objectX + 1, c.getY(), 0);
			} else if (c.getX() > objectX) {
				c.getPA().movePlayer(objectX - 1, c.getY(), 0);
			}
			break;

		case 9293:
			if (c.getX() < objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;

		case 10529:
		case 10527:
			if (c.getY() <= objectY)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;
			
			

		case 733:
			GlobalObject objectOne = null;
			int chance = c.getRechargeItems().hasAnyItem(13108, 13109, 13110, 13111) ? 0 : 4;
			c.startAnimation(451);
			c.sendMessage("You fail to cut through it.");
			if (Misc.random(chance) == 0) {
				c.sendMessage("You slash the web apart.");
				if (objectX == 3092 && objectY == 3957) {
					objectOne = new GlobalObject(734, objectX, objectY, c.getHeight(), 2, 0, 50, 733);
				} else if (objectX == 3095 && objectY == 3957) {
					objectOne = new GlobalObject(734, objectX, objectY, c.getHeight(), 0, 0, 50, 733);
				} else if (objectX == 3158 && objectY == 3951) {
					objectOne = new GlobalObject(734, objectX, objectY, c.getHeight(), 1, 10, 50, 733);
				} else if (objectX == 3105 && objectY == 3958 || objectX == 3106 && objectY == 3958) {
					objectOne = new GlobalObject(734, objectX, objectY, c.getHeight(), 3, 10, 50, 733);
				}
				if (objectOne != null) {
					World.getWorld().getGlobalObjects().add(objectOne);
				}
			}
			break;

		case 7407:
			GlobalObject gate;
			gate = new GlobalObject(objectId, objectX, objectY, c.getHeight(), 2, 0, 50, 7407);
			World.getWorld().getGlobalObjects().add(gate);
			break;

		case 7408:
			GlobalObject secondGate;
			secondGate = new GlobalObject(objectId, objectX, objectY, c.getHeight(), 0, 0, 50, 7408);
			World.getWorld().getGlobalObjects().add(secondGate);
			break;
			
		case 11766://boneman
			GlobalObject boneman_gate;
			boneman_gate = new GlobalObject(objectId, objectX, objectY, c.getHeight(), 1, 0, 50, 11766);
			World.getWorld().getGlobalObjects().add(boneman_gate);
			break;
			
		case 11767:
			GlobalObject second_boneman_gate;
			second_boneman_gate = new GlobalObject(objectId, objectX, objectY, c.getHeight(), 3, 0, 50, 11767);
			World.getWorld().getGlobalObjects().add(second_boneman_gate);
			break;

		/**
		 * Forfeiting a duel.
		 */
		case 3203:
			DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(session)) {
				return;
			}
			if (!Boundary.isIn(c, Boundary.DUEL_ARENA)) {
				return;
			}
			if (session.getRules().contains(Rule.FORFEIT)) {
				c.sendMessage("You are not permitted to forfeit the duel.");
				return;
			}
			break;

		}
	}

}