package valius.model.entity.npc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import valius.Config;
import valius.clip.PathChecker;
import valius.content.DailyTaskKills;
import valius.content.SkillcapePerks;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.AchievementDiaryKills;
import valius.content.barrows.Barrows;
import valius.content.barrows.brothers.Brother;
import valius.content.godwars.God;
import valius.content.godwars.GodwarsNPCs;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.instance.Instance;
import valius.model.entity.npc.animations.AttackAnimation;
import valius.model.entity.npc.animations.DeathAnimation;
import valius.model.entity.npc.bosses.CorporealBeast;
import valius.model.entity.npc.bosses.Scorpia;
import valius.model.entity.npc.bosses.ShadowBeast;
import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.npc.bosses.EventBoss.impl.AntiSanta;
import valius.model.entity.npc.bosses.EventBoss.impl.Tarn;
import valius.model.entity.npc.bosses.cerberus.Cerberus;
import valius.model.entity.npc.bosses.raids.Tekton;
import valius.model.entity.npc.bosses.skotizo.Skotizo;
import valius.model.entity.npc.bosses.vorkath.Vorkath;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.npc.bosses.wildypursuit.Glod;
import valius.model.entity.npc.bosses.wildypursuit.IceQueen;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.impl.eventboss.bosses.EnragedGraardor;
import valius.model.entity.npc.combat.impl.eventboss.bosses.Jackokraken;
import valius.model.entity.npc.combat.impl.general.Derwen;
import valius.model.entity.npc.combat.impl.general.JusticiarZachariah;
import valius.model.entity.npc.combat.impl.general.Porazdir;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.GlobalMessages.MessageType;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.DamageEffect;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.combat.Specials;
import valius.model.entity.player.combat.effects.SerpentineHelmEffect;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.hunter.impling.PuroPuro;
import valius.model.minigames.Wave;
import valius.model.minigames.inferno.InfernoWave;
import valius.model.minigames.lighthouse.DagannothMother;
import valius.model.minigames.lighthouse.DisposeType;
import valius.model.minigames.raids.Raids;
import valius.model.minigames.rfd.DisposeTypes;
import valius.model.minigames.rfd.RecipeForDisaster;
import valius.model.minigames.warriors_guild.AnimatedArmour;
import valius.model.minigames.xeric.XericWaveConstants;
import valius.model.shops.FlashSale;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class NPCHandler {

	public void checkMa(Player c,int i) {
				if (c.roundNpc == 2 && !c.spawned) {
					spawnNpc(c, 1606, 3106, 3934, 0, 1, 30, 24, 70, 60, true, true);
					c.roundNpc = 3;
					c.spawned = true;
				} else if (c.roundNpc == 3 && !c.spawned) {
					spawnNpc(c, 1607, 3106, 3934, 0, 1, 60, 24, 70, 60, true, true);
					c.roundNpc = 4;
					c.spawned = true;
				} else if (c.roundNpc == 4 && !c.spawned) {
					spawnNpc(c, 1608, 3106, 3934, 0, 1, 80, 15, 70, 60, true, true);
					c.roundNpc = 5;
					c.spawned = true;
				} else if (c.roundNpc == 5 && !c.spawned) {
					spawnNpc(c, 1609, 3106, 3934, 0, 1, 140, 19, 70, 60, true, true);
					c.roundNpc = 6;
					c.spawned = true;
				} else if (c.roundNpc == 6 && !c.spawned) {
					c.getPA().movePlayer(2541, 4716, 0);
					c.getDH().sendNpcChat3("Congratulations you have proved your self worthy!","Head back to the arena now to earn points!","Goodluck wizard!",1603,"Kolodion");
					c.roundNpc = 0;
					c.maRound = 2;
				}
			}

	public static int maxNPCs = 30000;
	public static int maxListedNPCs = 20000;
	public static int maxNPCDrops = 20000;
	public static NPC npcs[] = new NPC[maxNPCs];
	private static NPCDef[] npcDef = new NPCDef[maxListedNPCs];

	public static boolean projectileClipping = true;

	/**
	 * Tekton variables
	 */
	public static String tektonAttack = "MELEE";
	public static Boolean tektonWalking = false;
	NPC TEKTON = NPCHandler.getNpc(7544);
	NPC RDRAGON = NPCHandler.getNpc(8031);
	NPC ADRAGON = NPCHandler.getNpc(8030);

	/**
	 * Ice demon variables
	 */
	NPC ICE_DEMON = NPCHandler.getNpc(7584);

	/**
	 * Skeletal mystics
	 */
	NPC SKELE_MYSTIC_ONE = NPCHandler.getNpc(7604);
	NPC SKELE_MYSTIC_TWO = NPCHandler.getNpc(7605);
	NPC SKELE_MYSTIC_THREE = NPCHandler.getNpc(7606);

	/**
	 * Glod variables
	 */
	public static String glodAttack = "MELEE";
	public static Boolean glodWalking = false;
	NPC GLOD = NPCHandler.getNpc(5219);
	
	/**
	 * Tarn variables
	 */
	public static String TarnAttack = "MAGIC";
	public static String TarnAttack2 = "MELEE";
	public static Boolean TarnWalking = false;
	NPC TARN = NPCHandler.getNpc(6477);
	
	/*
	 *Event boss variables 
	 */
	NPC GRAARDOR = NPCHandler.getNpc(5462);

	/*
	 * wilderness bosses (MA2) variables
	 */
	NPC JUSTICIAR = NPCHandler.getNpc(7858);
	NPC DERWEN = NPCHandler.getNpc(7859);
	NPC PORAZDIR = NPCHandler.getNpc(7860);
	

	/**
	 * Queen variables
	 */
	public static String queenAttack = "MAGIC";
	public static Boolean queenWalking = false;
	NPC QUEEN = NPCHandler.getNpc(4922);

	public void init() {
		for (int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
			NPCDefinitions.getDefinitions()[i] = null;
		}
		loadNPCList("./Data/CFG/npc_config.cfg");
		
		NPCSpawns.loadNPCSpawns();
		NPCSpawns.getSpawns().forEach(npcSpawn -> {
			newNPC(npcSpawn.getNpcId(), npcSpawn.getXPos(), npcSpawn.getYPos(),
					npcSpawn.getHeight(), npcSpawn.getWalkType(),
					getNpcListHP(npcSpawn.getNpcId()), npcSpawn.getMaxHit(),
					npcSpawn.getAttack(), npcSpawn.getDefence(), null);
		});
		
		loadNPCSizes("./Data/cfg/npc_sizes.txt");
		startGame();
	}

	public static boolean ringOfLife(Player c) {
		boolean defenceCape = SkillcapePerks.DEFENCE.isWearing(c);
		boolean maxCape = SkillcapePerks.isWearingMaxCape(c);
		if (c.getItems().isWearingItem(2570) || defenceCape || (maxCape && c.getRingOfLifeEffect())) {
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("The ring of life effect does not work as you are teleblocked.");
				return false;
			}
			if (defenceCape || maxCape) {
				c.sendMessage("Your cape activated the ring of life effect and saved you!");
			} else {
				c.getItems().deleteEquipment(2570, c.playerRing);
				c.sendMessage("Your ring of life saved you!");
			}
			c.getPA().spellTeleport(3087, 3499, 0, false);
			return true;
		}
		return false;
	}

	public void spawnNpc3(Player c, int id, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit,
			int attack, int defence, boolean attackPlayer, boolean headIcon, boolean summonFollow) {
		// first, search for a free slot
		int index = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			System.out.println("Cannot find any available slots to spawn npc into + npchandler @spawnNpc3");
			return;
		}
		NPCDefinitions definition = NPCDefinitions.get(id);
		NPC newNPC = new NPC(index, id, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getIndex();
		newNPC.underAttack = true;
		newNPC.faceEntity(c.getIndex());
		if (headIcon)
			c.getPA().drawHeadicon(1, index, 0, 0);
		if (summonFollow) {
			newNPC.summoner = true;
			newNPC.summonedBy = c.getIndex();
			c.hasFollower = true;
		}
		if (attackPlayer) {
			newNPC.underAttack = true;
			newNPC.killerId = c.getIndex();
		}
		npcs[index] = newNPC;
	}

	public void stepAway(int i) {
		int[][] points = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

		for (int[] k : points) {
			int dir = NPCClipping.getDirection(k[0], k[1]);
			if (NPCDumbPathFinder.canMoveTo(npcs[i], dir)) {
				NPCDumbPathFinder.walkTowards(npcs[i], npcs[i].getX() + NPCClipping.DIR[dir][0],
						npcs[i].getY() + NPCClipping.DIR[dir][1]);
				break;
			}
		}
	}

	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Player c = PlayerHandler.players[j];
				if (c.getHeight() != npcs[i].getHeight())
					continue;
				if (PlayerHandler.players[j].goodDistance(c.getX(), c.getY(), npcs[i].getX(), npcs[i].getY(), 15)) {
					int nX = NPCHandler.npcs[i].getX() + offset(i);
					int nY = NPCHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nX - pX) * -1;
					int offY = (nY - pY) * -1;
					int centerX = nX + npcs[i].getSize() / 2;
					int centerY = nY + npcs[i].getSize() / 2;
					c.getPA().createPlayersProjectile(centerX, centerY, offX, offY, 50, getProjectileSpeed(i),
							npcs[i].projectileId, getProjectileStartHeight(npcs[i].npcType, npcs[i].projectileId),
							getProjectileEndHeight(npcs[i].npcType, npcs[i].projectileId), -c.getIndex() - 1, 65);
					if (npcs[i].npcType == 7554) {
						c.getPA().sendPlayerObjectAnimation(c, 3220, 5738, 7371, 10, 3, c.getHeight());
					}
				}
			}
		}
	}

	public boolean switchesAttackers(int i) {
		switch (npcs[i].npcType) {
		case 2205:
		case 963:
		case 965:
		case 3129:
		case 3162:
		case 2208:
		case 239:
		case 6611:
		case 6612:
		case 494:
		case 319:
		case 7554:
		case 320:
		case 5535:
		case 2551:
		case 6609:
		case 2552:
		case 2553:
		case 2559:
		case 2560:
		case 2561:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 1046:
		case 6615:
		case 6616:
		case 7604:
		case 7605:
		case 7606:
		case 7544:
		case 5129:
		case 4922:
		case 6475:
			return true;

		}

		return false;
	}

	public static boolean isSpawnedBy(Player player, NPC npc) {
		if (player != null && npc != null)
			if (npc.spawnedBy == player.getIndex() || npc.killerId == player.getIndex())
				return true;
		return false;
	}

	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<>();
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR)) {
					if (!Boundary.isIn(PlayerHandler.players[j], Boundary.CORPOREAL_BEAST_LAIR)) {
						npcs[i].killerId = 0;
						continue;
					}
				}
				/**
				 * Skips attacking a player if mode set to invisible
				 */
				if (PlayerHandler.players[j].isInvisible()) {
					continue;
				}
				if (Boundary.isIn(npcs[i], Boundary.GODWARS_BOSSROOMS)) {
					if (!Boundary.isIn(PlayerHandler.players[j], Boundary.GODWARS_BOSSROOMS)) {
						npcs[i].killerId = 0;
						continue;
					}
				}
				if (goodDistance(PlayerHandler.players[j].getX(), PlayerHandler.players[j].getY(), npcs[i].getX(),
						npcs[i].getY(), distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].getHeight() == npcs[i].getHeight())
							players.add(j);
				}
			}
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() - 1));
		else
			return 0;
	}

	public int culinomancer = 0;

	/**
	 * Updated to support the new drop table checker
	 * 
	 * @param i
	 * @param searching
	 * @return
	 */
	public boolean isAggressive(int i, boolean searching) {
		if (!searching) {
			if (Boundary.isIn(npcs[i], Boundary.GODWARS_BOSSROOMS)
					|| Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR) 
						|| Boundary.isIn(npcs[i], Boundary.XERIC) || Boundary.isIn(npcs[i], Boundary.RAIDROOMS) || Boundary.isIn(npcs[i], Boundary.CATACOMBS)) {
				return true;
			}
			if (npcs[i].npcId == 7283) {
				return false;
			}
		}
		if(npcs[i] != null && npcs[i].getCombatScript() != null)
            return npcs[i].getCombatScript().isAggressive(npcs[i]);
		if (searching) {
			switch (i) {
			case 5001://event bosses
			case 6477:
				return true;
			case 7283://lillia
				return false;
			case 5916:
			case 690:
			case 963:
			case 965:
			case 955:
			case 957:
			case 6524:
			case 959:
			case 5867:
			case 5868:
			case 5869:
			case 2042:
			case 239:
			case 7413:
			case 1739:
			case 1740:
			case 1741:
			case 1742:
			case 2044:
			case 2043:
			case 465:
			case 6475:
			case 8062:// vorkath crab aggressive
				case 7706:
			case Zulrah.SNAKELING:
			case 5054:
			case 6611:
			case 6612:
			case 6610:
			case 494:
			case 5535:
			case 2550:
			case 2551:
			case 50:
			case 28:
			case 2552:
			case 6609:
			case 2553:
			case 2558:
			case 2559:
			case 2560:
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
			case 2892:
			case 2894:
			case 2265:
			case 2266:
			case 2267:
			case 2035:
			case 5779:
			case 291:
			case 435:
			case 135:
			case 7276:
			case 5944: // Rock lobster

				// Godwars
			case 3138:
			case 2205:
			case 2206:
			case 2207:
			case 2208:
			case 2209:
			case 2211:
			case 2212:
			case 2215:
			case 5462:
			case 2216:
			case 2217:
			case 2218:
			case 2233:
			case 2234:
			case 2235:
			case 2237:
			case 2242:
			case 2243:
			case 2244:
			case 2245:
			case 3129:
			case 3130:
			case 3131:
			case 3132:
			case 3133:
			case 3134:
			case 3135:
			case 3137:
			case 3139:
			case 3140:
			case 3141:
			case 3159:
			case 3160:
			case 3161:
			case 3162:
			case 3163:
			case 3164:
			case 3165:
			case 3166:
			case 3167:
			case 3168:
			case 3174:

				// Barrows tunnel monsters
			case 1678:
			case 1679:
			case 1683:
			case 1684:
			case 1685:

			case Skotizo.SKOTIZO_ID:
			case Skotizo.REANIMATED_DEMON:
			case Skotizo.DARK_ANKOU:
				// GWD
			case 6230:
			case 6231:
			case 6229:
			case 6232:
			case 6240:
			case 6241:
			case 6242:
			case 6233:
			case 6234:
			case 6243:
			case 6244:
			case 6245:
			case 6246:
			case 6238:
			case 6239:
			case 6625:
			case 122:// Npcs That Give BandosKC
			case 6278:
			case 6277:
			case 6276:
			case 6283:
			case 6282:
			case 6281:
			case 6280:
			case 6279:
			case 6271:
			case 6272:
			case 6273:
			case 6274:
			case 6269:
			case 6270:
			case 6268:
			case 6221:
			case 6219:
			case 6220:
			case 6217:
			case 6216:
			case 6215:
			case 6214:
			case 6213:
			case 6212:
			case 6211:
			case 6218:
			case 6275:
			case 6257:// Npcs That Give SaraKC
			case 6255:
			case 6256:
			case 6259:
			case 6254:
			case 1689:
			case 1694:
			case 1699:
			case 1704:
			case 1709:
			case 1714:
			case 1724:
			case 1734:
			case 6914: // Lizardman, Lizardman brute
			case 6915:
			case 6916:
			case 6917:
			case 6918:
			case 6919:
			case 6766:
			case 7573:
			case 7617: // Tekton magers
			case 7544: // Tekton
			case 7604: // Skeletal mystic
			case 7605: // Skeletal mystic
			case 7606: // Skeletal mystic
			case 7585: //
			case 7554: //great olm
			case 7563: // muttadiles
			case 5129:
			case 4922:
				
			case 7547: //xeric mini raid
			case 7548:
			case 7559:
			case 7560:
			case 7597:
			case 7596:
			case 7577:
			case 7578:
			case 7579:
			case 7586:
			case 7538:
			case 7531://xeric mini raid bosses
			case 7543:
			case 7566:
				return true;
			case 8030:// Addy dragon
			case 8031:// Rune dragon
				return true;
			case 1524:
			case 6600:
			case 6601:
			case 7553:
			case 7555:
			case 6602:
			case 1049:
			case 6617:
			case 6620:
				return false;
			}
		} else {
			switch (npcs[i].npcType) {
			case 5916:
			case 690:
			case 963:
			case 965:
			case 6475:
			case 6477:
			case 955:
			case 957:
			case 959:
			case 5867:
			case 5868:
			case 5869:
			case 2042:
			case 239:
			case 7413:
			case 1739:
			case 1740:
			case 1741:
			case 1742:
			case 2044:
			case 2043:
			case 465:
			case Zulrah.SNAKELING:
			case 5054:
			case 6611:
			case 6612:
			case 6610:
			case 494:
			case 5535:
			case 2550:
			case 2551:
			case 50:
			case 28:
			case 2552:
			case 6609:
			case 2553:
			case 2558:
			case 2559:
			case 2560:
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
			case 2892:
			case 2894:
			case 2265:
			case 2266:
			case 2267:
			case 2035:
			case 5779:
			case 291:
			case 435:
			case 135:
			case 484:
			case 7276:
			case 5944: // Rock lobster

				// Godwars
			case 3138:
			case 2205:
			case 2206:
			case 2207:
			case 2208:
			case 2209:
			case 2211:
			case 2212:
			case 2215:
			case 5462:
			case 2216:
			case 2217:
			case 2218:
			case 2233:
			case 2234:
			case 2235:
			case 2237:
			case 2242:
			case 2243:
			case 2244:
			case 2245:
			case 3129:
			case 3130:
			case 3131:
			case 3132:
			case 3133:
			case 3134:
			case 3135:
			case 3137:
			case 3139:
			case 3140:
			case 3141:
			case 3159:
			case 3160:
			case 3161:
			case 3162:
			case 3163:
			case 3164:
			case 3165:
			case 3166:
			case 3167:
			case 3168:
			case 3174:

			case Skotizo.SKOTIZO_ID:
			case Skotizo.REANIMATED_DEMON:
			case Skotizo.DARK_ANKOU:

				// Barrows tunnel monsters
			case 1678:
			case 1679:
			case 1683:
			case 1684:
			case 1685:
				// GWD
			case 6230:
			case 6231:
			case 6229:
			case 6232:
			case 6240:
			case 6241:
			case 6242:
			case 6233:
			case 6234:
			case 6243:
			case 6244:
			case 6245:
			case 6246:
			case 6238:
			case 6239:
			case 6625:
			case 122:// Npcs That Give BandosKC
			case 6278:
			case 6277:
			case 6276:
			case 6283:
			case 6282:
			case 6281:
			case 6280:
			case 6279:
			case 6271:
			case 6272:
			case 6273:
			case 6274:
			case 6269:
			case 6270:
			case 6268:
			case 6221:
			case 6219:
			case 6220:
			case 6217:
			case 6216:
			case 6215:
			case 6214:
			case 6213:
			case 6212:
			case 6211:
			case 6218:
			case 6275:
			case 6257:// Npcs That Give SaraKC
			case 6255:
			case 6256:
			case 6259:
			case 6254:
			case 1689:
			case 1694:
			case 1699:
			case 1704:
			case 1709:
			case 1714:
			case 1724:
			case 1734:
			case 6914: // Lizardman, Lizardman brute
			case 6915:
			case 6916:
			case 6917:
			case 6918:
			case 6919:
			case 6766:
			case 7573:
			case 7617: // Tekton magers
			case 7544: // Tekton
			case 7604: // Skeletal mystic
			case 7605: // Skeletal mystic
			case 7606: // Skeletal mystic
			case 5129:
			case 4922:
			case 7388: // Start of superior
			case 7389:
			case 7390:
			case 7391:
			case 7392:
			case 7393:
			case 7394:
			case 7395:
			case 7396:
			case 7397:
			case 7398:
			case 7399:
			case 7400:
			case 7401:
			case 7402:
			case 7403:
			case 7404:
			case 7405:
			case 7406:
			case 7407:
			case 7409:
			case 7410:
			case 7411: // end of superior
				return true;
			case 1524:
			case 6600:
			case 6601:
			case 6602:
			case 1049:
			case 6617:
			case 6620:
				return false;

			case 8028:
				return true;
			}
			if (npcs[i].inWild() && npcs[i].getHealth().getMaximum() > 0)
				return true;
			if (npcs[i].inRaids() && npcs[i].getHealth().getMaximum() > 0)
				return true;
			if (npcs[i].inXeric() && npcs[i].getHealth().getMaximum() > 0)
				return true;
			return isFightCaveNpc(i);
		}
		return false;
	}

	public static boolean isDagannothMother(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {
		case 983:
		case 984:
		case 985:
		case 986:
		case 987:
		case 988:
			return true;
		}
		return false;
	}

	public static boolean isFightCaveNpc(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {
		case Wave.TZ_KEK_SPAWN:
		case Wave.TZ_KIH:
		case Wave.TZ_KEK:
		case Wave.TOK_XIL:
		case Wave.YT_MEJKOT:
		case Wave.KET_ZEK:
		case Wave.TZTOK_JAD:
			return true;
		}
		return false;
	}

	public static boolean isInfernoNpc(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {

		case InfernoWave.JAL_NIB:
		case InfernoWave.JAL_MEJRAH:
		case InfernoWave.JAL_AK:
		case InfernoWave.JAL_AKREK_MEJ:
		case InfernoWave.JAL_AKREK_XIL:
		case InfernoWave.JAL_AKREK_KET:
		case InfernoWave.JAL_IMKOT:
		case InfernoWave.JAL_XIL:
		case InfernoWave.JAL_ZEK:
		case InfernoWave.JALTOK_JAD:
		case InfernoWave.YT_HURKOT:
		case InfernoWave.TZKAL_ZUK:
		case InfernoWave.ANCESTRAL_GLYPH:
		case InfernoWave.JAL_MEJJAK:

			return true;
		}
		return false;
	}

	public static boolean isXericNpc(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {

		case XericWaveConstants.RUNT:
		case XericWaveConstants.BEAST:
		case XericWaveConstants.RANGER:
		case XericWaveConstants.MAGE:
		case XericWaveConstants.SHAMAN:
		case XericWaveConstants.LIZARD:
		case XericWaveConstants.VESPINE:
		case XericWaveConstants.AIR_CRAB:
		case XericWaveConstants.FIRE_CRAB:
		case XericWaveConstants.EARTH_CRAB:
		case XericWaveConstants.WATER_CRAB:
		case XericWaveConstants.ICE_FIEND:
		case XericWaveConstants.VANGUARD:
		case XericWaveConstants.VESPULA:
		case XericWaveConstants.TEKTON:
		case XericWaveConstants.MUTTADILE:
		case XericWaveConstants.VASA:
		case XericWaveConstants.ICE_DEMON:

			return true;
		}
		return false;
	}

	public static boolean isSkotizoNpc(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {
		case Skotizo.SKOTIZO_ID:
		case Skotizo.AWAKENED_ALTAR_NORTH:
		case Skotizo.AWAKENED_ALTAR_SOUTH:
		case Skotizo.AWAKENED_ALTAR_WEST:
		case Skotizo.AWAKENED_ALTAR_EAST:
		case Skotizo.REANIMATED_DEMON:
		case Skotizo.DARK_ANKOU:
			return true;
		}
		return false;
	}

	/**
	 * Summon npc, barrows, etc
	 **/
	public NPC spawnNpc(final Player c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit,
			int attack, int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			System.out.println("Cannot find any available slots to spawn npc into + npchandler @spawnNpc");
			return null;
		}
		NPCDefinitions definition = NPCDefinitions.get(npcType);
		final NPC newNPC = new NPC(slot, npcType, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getIndex();
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (attackPlayer) {
			newNPC.underAttack = true;
			newNPC.killerId = c.getIndex();
			c.underAttackBy = slot;
			c.underAttackBy2 = slot;
		}
		npcs[slot] = newNPC;
		if (newNPC.npcType == 1605) {
			newNPC.forceChat("You must prove yourself... now!");
			newNPC.gfx100(86);
		}
		if (newNPC.npcType == 1606) {
			newNPC.forceChat("This is only the beginning, you can't beat me!");
			newNPC.gfx100(86);
		}
		if (newNPC.npcType == 1607) {
			newNPC.forceChat("Foolish mortal, I am unstoppable.");
		}
		if (newNPC.npcType == 1608) {
			newNPC.forceChat("Now you feel it... The dark energy.");
		}
		if (newNPC.npcType == 1609) {
			newNPC.forceChat("Aaaaaaaarrgghhhh! The power!");
		}
		return newNPC;
	}

	/**
	 * Attack animations
	 * 
	 * @param i
	 *            the npc to perform the animation.
	 * @return the animation to be performed.
	 */
	public static int getAttackEmote(int i) {
		return AttackAnimation.handleEmote(i);
	}

	/**
	 * Death animations
	 * 
	 * @param i
	 *            the npc to perform the animation.
	 * @return the animation to be performed.
	 */
	public int getDeadEmote(int i) {
		return DeathAnimation.handleEmote(i);
	}

	/**
	 * Death delay
	 * 
	 * @param i
	 *            the npc whom were setting the delay to
	 * @return the delay were setting
	 */
	public int getDeathDelay(int i) {
		if(npcs[i] == null)
			return 4;
		switch (npcs[i].npcType) {
		case 8612:
		case 8613:
			return 6;
		case 8610:
		case 8611:
			return 2;
		case 8028:
			return 8;
		case 5548:
		case 5549:
		case 5550:
		case 5551:
		case 5552:
		case 1505:
		case 2910:
		case 2911:
		case 2912:
		case 484:
		case 7276:
		case 3138:
		case 1635:
		case 1636:
		case 1637:
		case 1638:
		case 1639:
		case 1640:
		case 1641:
		case 1642:
		case 1643:
		case 1654:
		case 7302:
			return 1;
		case 2209:
		case 2211:
		case 2212:
		case 2233:
		case 2234:
		case 435:
		case 3137:
		case 3139:
		case 3140:
		case 3159:
		case 3160:
		case 3161:
		case 2241:
			return 2;
		case 3134:
		case 3141:
			return 3;
		case 3166:
		case 3167:
		case 3168:
		case 3174:
			return 5;
		case 3129:
		case 3130:
		case 3131:
		case 3132:
			return 6;
		case 2237:
		case 2242:
		case 2243:
		case 2244:
		case 3135:
			return 7;
		default:
			return 4;
		}
	}

	/**
	 * Attack delay
	 * 
	 * @param i
	 *            the npc whom were setting the delay to
	 * @return the delay were setting
	 */
	public int getNpcDelay(int i) {
		switch (npcs[i].npcType) {
		case 499:
			return 4;
		case 498:
			return 7;
		case 6611:
		case 6612:
			return npcs[i].attackType == CombatType.MAGE ? 6 : 5;
		case 319:
			return npcs[i].attackType == CombatType.MAGE ? 7 : 6;
		case 7554:
			return npcs[i].attackType == CombatType.MAGE ? 4 : 6;
		case 2025:
		case 2028:
		case 963:
		case 965:
			return 7;
		case 6475:
		case 6477:
			return 4;
		case 8030:
		case 8031:
			return 5;
		case 3127:
			case 7700:
			return 8;
		case 2205:
			return 4;
		case Brother.AHRIM:
			return 6;
		case Brother.DHAROK:
			return 7;
		case Brother.GUTHAN:
			return 5;
		case Brother.KARIL:
			return 4;
		case Brother.TORAG:
			return 5;
		case Brother.VERAC:
			return 5;
		case 3167:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
			return 6;
		// saradomin gw boss
		case 2562:
		case 7597:
		case 7547:
			return 2;
		case 3162:
			return 7;
		default:
			return 5;
		}
	}

	/**
	 * Projectile start height
	 * 
	 * @param npcType
	 *            the npc to perform the projectile
	 * @param projectileId
	 *            the projectile to be performed
	 * @return
	 */
	private int getProjectileStartHeight(int npcType, int projectileId) {
		switch (npcType) {
		case 2044:
			return 60;
		case 3162:
			return 0;
		case 3127:
		case 3163:
			case 7700:
		case 3164:
		case 3167:
		case 3174:
			return 110;
		case 6610:
			switch (projectileId) {
			case 1998:
			case 165:
				return 20;
			}
			break;
		}
		return 43;
	}

	/**
	 * Projectile end height
	 * 
	 * @param npcType
	 *            the npc to perform the projectile
	 * @param projectileId
	 *            the projectile to be performed
	 * @return
	 */
	private int getProjectileEndHeight(int npcType, int projectileId) {
		switch (npcType) {
		case 1605:
			return 0;
		case 3162:
			return 15;
		case 6610:
			switch (projectileId) {
			case 165:
				return 30;
			case 1996:
				return 0;
			}
			break;
		}
		return 31;
	}

	/**
	 * Hit delay
	 * 
	 * @param i
	 *            the npc whom were setting the delay to
	 * @return the delay were setting
	 */
	public int getHitDelay(int i) {
		switch (npcs[i].npcType) {
		case 7706:
		return 7;
		case 1605:
		case 1606:
		case 1607:
		case 1608:
		case 1609:
		case 499:
			return 4;
		case 498:
			return 4;
		case 6611:
		case 6612:
			return npcs[i].attackType == CombatType.MAGE ? 3 : 2;
		case 1672:
		case 1675:
		case 1046:
		case 1049:
		case 6610:
		case 2265:
		case 2266:
		case 2054:
		case 2892:
		case 2894:
		case 3125:
		case 3121:
		case 2167:
		case 2558:
		case 2559:
		case 2560:
		case 2209:
		case 2211:
		case 2218:
		case 2242:
		case 2244:
		case 3160:
		case 3163:
		case 3167:
		case 3174:
		case 2028:
			return 3;
		case 2212:
		case 2217:
		case 3161:
		case 3162:
		case 3164:
		case 3168:
		case 6914: // Lizardman, Lizardman brute
		case 6915:
		case 6916:
		case 6917:
		case 6918:
		case 6919:
		case 2025:
			return 4;
		case 3127:
			case 7700:
			if (npcs[i].attackType == CombatType.RANGE || npcs[i].attackType == CombatType.MAGE) {
				return 5;
			} else {
				return 2;
			}

		default:
			return 2;
		}
	}

	/**
	 * Respawn time
	 * 
	 * @param i
	 *            the npc whom were setting the time to
	 * @return the time were setting
	 */
	public int getRespawnTime(int i) {
		switch (npcs[i].npcType) {
		case 6600:
		case 6601:
		case 6602:
		case 320:
		case 1049:
		case 6617:
		case 3118:
		case 3120:
		case 6768:
		case 5862:
		case 5054:
		case 2402:
		case 2401:
		case 2400:
		case 2399:
		case 5916:
		case 7604:
		case 7605:
		case 7606:
		case 7585:
		case 5129:
		case 4922:
		case 7563:
		case 7573:
		case 7544:
		case 7566:
		case 7559:
			case 7553:
			case 7554:
			case 7555:
		case 7560://Trials of Xeric
		case 7527:
		case 7528:
		case 7529:
		case 8679://Colossal Chicken
		case 6014://Void Knight Champion
			return -1;
		case 5001://anti-santa
		case 6477:
		case 5462:
		case 7858:
		case 7859:
		case 7860:
		case 3383:
			return -1;
			
		case 3833:
		case 3845:
			return 300;
			
		case 3407:
			return 600;
			
		case 3842:
			return 180;

		case 963:
		case 965:
			return 10;
			
		case 6475:
			return 25;
			
			/* hydra */
		case 8609:
			return 40;

		case 6618:
		case 6619:
		case 319:
		case 5890:
			return 30;

		case 1046:
		case 465:
			return 60;

		case 6609:
		case 2265:
		case 2266:
		case 2267:
			return 70;

		case 6611:
		case 6612:
		case 492:
			return 90;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2205:
		case 2206:
		case 2207:
		case 2208:
		case 2215:
		case 2216:
		case 2217:
		case 2218:
		case 3129:
		case 3130:
		case 3131:
		case 3132:
		case 3162:
		case 3163:
		case 3164:
		case 3165:
		case 1641:
		case 1642:
			return 100;

		case 1643:
			return 180;

		case 1654:
			return 250;

		case 3777:
		case 3778:
		case 3779:
		case 3780:
		case 7302:
			return 500;
		default:
			return 25;
		}
	}

	/**
	 * Spawn a new npc on the world
	 * 
	 * @param npcType
	 *            the npcType were spawning
	 * @param x
	 *            the x coordinate were spawning on
	 * @param y
	 *            the y coordinate were spawning on
	 * @param heightLevel
	 *            the heightLevel were spawning on
	 * @param WalkingType
	 *            the WalkingType were setting
	 * @param HP
	 *            the HP were setting
	 * @param maxHit
	 *            the maxHit were setting
	 * @param attack
	 *            the attack level were setting
	 * @param defence
	 *            the defence level were setting
	 * @param instance TODO
	 */
	public void newNPC(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack,
			int defence, Instance instance) {
		// first, search for a free slot
		
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return;

		NPCDefinitions definition = NPCDefinitions.get(npcType);
		NPC newNPC = new NPC(slot, npcType, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.resetDamageTaken();
		newNPC.setInstance(instance);
		
		npcs[slot] = newNPC;
	}

	public boolean olmDead;
	public static boolean rightClawDead;
	public static boolean leftClawDead;

	/**
	 * Constructs a new npc list
	 * 
	 * @param npcType
	 *            npcType to be set
	 * @param npcName
	 *            npcName to be gathered
	 * @param combat
	 *            combat level to be set
	 * @param HP
	 *            HP level to be set
	 */
	public void newNPCList(int npcType, String npcName, int combat, int HP) {
		NPCDefinitions newNPCList = new NPCDefinitions(npcType);
		newNPCList.setNpcName(npcName.replaceAll("_", " ").toLowerCase());
		newNPCList.setNpcCombat(combat);
		newNPCList.setNpcHealth(HP);
		NPCDefinitions.getDefinitions()[npcType] = newNPCList;
	}

	public int olmStage;

	/**
	 * Handles processes for NPCHandler every 600ms
	 */
	public void process() {
		nonNullStream().forEach(npc -> {
			npc.clearUpdateFlags();
		});
		
		nonNullStream().forEach(npc -> {
				int index = npc.getIndex();
				int type = npc.npcType;
				Player slaveOwner = (PlayerHandler.players[npc.summonedBy]);
				if (npc != null && slaveOwner == null && npc.summoner) {
					npc.setX(0);
					npc.setY(0);
				}
				if (npc != null && slaveOwner != null && slaveOwner.hasFollower
						&& (!slaveOwner.goodDistance(npc.getX(), npc.getY(), slaveOwner.getX(), slaveOwner.getY(),
								15) || slaveOwner.getHeight() != npc.getHeight())
						&& npc.summoner) {
					npc.setX(slaveOwner.getX());
					npc.setY(slaveOwner.getY());
					npc.setHeight(slaveOwner.getHeight());

				}
				if (npc.actionTimer > 0) {
					npc.actionTimer--;
				}

				if (npc.freezeTimer > 0) {
					npc.freezeTimer--;
				}
				if (npc.hitDelayTimer > 0) {
					npc.hitDelayTimer--;
				}
				if (npc.hitDelayTimer == 1) {
					npc.hitDelayTimer = 0;
					applyDamage(index);
				}
				if (npc.attackTimer > 0) {
					npc.attackTimer--;
				}
				if (npc.npcType == 7553) {
					npc.walkingHome = true;
				}
				if (npc.npcType == 7555) {
					npc.walkingHome = true;
				}
				if (npc.targetingDelay > 0) {
					npc.targetingDelay--;
				}
				if (npc.npcType == 8045) {//sir tiffy
					if (Misc.random(1, 5) == 2) {
					npc.forceChat("Falador is under Siege! Speak to me now to assist.");
					}
				}
				
				if (npc.npcType == 3413) {//santa
					if (Misc.random(1, 6) == 2) {
						npc.startAnimation(861);
					npc.forceChat("Ho! Ho! Ho!");
					}
				}
				if (npc.npcType == 8165) {//sir tiffy
					if (Misc.random(1, 8) == 2) {
						int randomMessage = Misc.random(1, 2);
						
						switch (randomMessage) {
						case 1:
						npc.updateRequired = true;
						npc.forceChat("Slay that beast!");
							break;
						case 2:
							npc.updateRequired = true;
							npc.forceChat("Defend Falador with your lives!");
							break;
						}
					}
				}
				/*
				 * town crier
				 */
				if (npc.npcType == 279) {
					if (Misc.random(1, 10) == 4) {
						int randomMessage = Misc.random(1, 2);
						
						switch (randomMessage) {
						case 1:
							npc.updateRequired = true;
							npc.forceChat("The Gauntlet is here a 100% accurate to OSRS!");
							npc.startAnimation(6865);
							break;
						case 2:
							npc.updateRequired = true;
							npc.forceChat("Speak with Mod Divine if you wish to donate using OSRS or RS3 Gold.");
							npc.startAnimation(6865);
							break;
						}
					}
				}
				
				if (npc.npcType == 3396) {
					npc.updateRequired = true;
					npc.forceChat("Speak to me for Information about this dungeon and its rewards!");
				}
				
				/*
				 * limited time salesman
				 */
				if (npc.npcType == 3819) {
					if (Misc.random(1, 5) == 5) {
						int randomMessage = Misc.random(1, 2);
						
						switch (randomMessage) {
						case 1:
						npc.updateRequired = true;
						npc.forceChat("Items in my Donator token shop are available until: Next Update!");
							break;
						case 2:
							npc.updateRequired = true;
							npc.forceChat("Items in my Gold Coin shop are available until: Next Update!");
							break;
						}
				}
				}
				
				if (npc.npcType == 7204) {
					if (FlashSale.discount != null) {
					npc.forceChat("Flash sale is currently: [ACTIVE] | Get " + FlashSale.discount.getDiscountPercentage() + "% off select items!");
				}
			}
				/*if (npc.npcType == 1143) {
					if (Misc.random(30) == 3) {
						npc.updateRequired = true;
						npc.forceChat("Sell your PvM items here for a limited time!");
				}
				if (Misc.random(15) == 15) {
					if (npcs[i].npcType == 7204) {
						npcs[i].updateRequired = true;
						npcs[i].forceChat("Flash Sale!");						
>>>>>>> branch 'master' of https://github.com/Patrity/Valius-V69.git
					}
<<<<<<< HEAD
				}

				if (npc.npcType == 306) {
					if (Misc.random(50) == 3) {
						npc.forceChat("Speak to me if you wish to learn more about this land!");
					}
				}
=======
				}
>>>>>>> branch 'master' of https://github.com/Patrity/Valius-V69.git

				// /**
				// * Tekton walking
				// */
				// if (tektonWalking) {
				// if (npc.npcType == 7544) {
				// if (npc.getX() != 3308 && npc.getX() != 5296) {
				// NPCDumbPathFinder.walkTowards(TEKTON, 3308, 5296);
				// } else {
				// tektonWalking = false;
				// }
				// }
				// }*/
				if (npc.getHealth().getAmount() > 0 && !npc.isDead) {
					if (npc.npcType == 6611 || npc.npcType == 6612) {
						if (npc.getHealth().getAmount() < (npc.getHealth().getMaximum() / 2)
								&& !npc.spawnedMinions) {
							NPCHandler.spawnNpc(5054, npc.getX() - 1, npc.getY(), 0, 1, 175, 14, 100, 120);
							NPCHandler.spawnNpc(5054, npc.getX() + 1, npc.getY(), 0, 1, 175, 14, 100, 120);
							npc.spawnedMinions = true;
						}
					}
				}
				if (npc.npcType == 6600 && !npc.isDead) {
					NPC runiteGolem = getNpc(6600);
					if (runiteGolem != null && !runiteGolem.isDead) {
						npc.isDead = true;
						npc.needRespawn = false;
						npc.actionTimer = 0;
					}
				}
				if (npc.spawnedBy > 0) { // delete summons npc
					Player spawnedBy = PlayerHandler.players[npc.spawnedBy];
					if (spawnedBy == null || spawnedBy.getHeight() != npc.getHeight() || spawnedBy.respawnTimer > 0
							|| !spawnedBy.goodDistance(npc.getX(), npc.getY(), spawnedBy.getX(),
									spawnedBy.getY(),
									NPCHandler.isFightCaveNpc(index) ? 80 : NPCHandler.isSkotizoNpc(index) ? 60 : 20)) {
						npcs[index] = null;
						return;
					}
				}
				if (npc.lastX != npc.getX() || npc.lastY != npc.getY()) {
					npc.lastX = npc.getX();
					npc.lastY = npc.getY();
				}
				Player glyphSpawner = PlayerHandler.players[npc.spawnedBy];
				if(type ==7707){
					glyphSpawner.getInferno().glyphX=npc.getX();
					glyphSpawner.getInferno().glyphY=npc.getY();
					//glyphSpawner.sendMessage("@red@"+glyphSpawner.getInferno().glyphX + " " +glyphSpawner.getInferno().glyphY);
				}
				if (type == 7707 && npc.getX() == 2270 && npc.getY() >= 5361 && glyphSpawner.getInferno().glyphCanMove) { // Move
																														// forward
					npc.moveX = GetMove(npc.getX(), 2270);
					npc.moveY = GetMove(npc.getY(), 5360);
					npc.updateRequired = true;
					npc.getNextNPCMovement();
					npc.walkingHome = false;
				} else if (type == 7707 && npc.getX() == 2270 && npc.getY() == 5360
						&& glyphSpawner.getInferno().glyphCanMove) { // From forward, start left
					glyphSpawner.getInferno().glyphCanMove = false;
					glyphSpawner.getInferno().glyphMoveLeft = true;
					npc.moveX = GetMove(npc.getX(), 2257);
					npc.moveY = GetMove(npc.getY(), 5360);
					npc.updateRequired = true;
					npc.getNextNPCMovement();
					npc.walkingHome = false;
				} else if (type == 7707 && npc.getY() == 5360 && glyphSpawner.getInferno().glyphMoveLeft) { // Once all
																											// the way
																											// to the
																											// left,
																											// move all
																											// the way
																											// to the
																											// right
					if (npc.getX() == 2257 && npc.getY() == 5360) {
						glyphSpawner.getInferno().glyphMoveLeft = false;
						glyphSpawner.getInferno().glyphMoveRight = true;
					}
					npc.moveX = GetMove(npc.getX(), 2257);
					npc.moveY = GetMove(npc.getY(), 5360);
					npc.updateRequired = true;
					npc.getNextNPCMovement();
					npc.walkingHome = false;
				} else if (type == 7707 && npc.getY() == 5360 && glyphSpawner.getInferno().glyphMoveRight) { // Once all
																											// the way
																											// to the
																											// right,
																											// move all
																											// the way
																											// to the
																											// left
					if (npc.getX() == 2283 && npc.getY() == 5360) {
						glyphSpawner.getInferno().glyphMoveLeft = true;
						glyphSpawner.getInferno().glyphMoveRight = false;
					}
					npc.moveX = GetMove(npc.getX(), 2283);
					npc.moveY = GetMove(npc.getY(), 5360);
					npc.updateRequired = true;
					npc.getNextNPCMovement();
					npc.walkingHome = false;
				}

				if (type == 6615) {
					if (npc.walkingHome) {
						npc.getHealth().setAmount(200);
					}
					Scorpia.spawnHealer();
				}

				if (type == 319) {
					if (npc.walkingHome) {
						npc.getHealth().setAmount(2000);
					}
					CorporealBeast.checkCore();
				}

				if (type == 8026 || type == 8027 || type == 7413) {
					npc.setFacePlayer(false);
				}
				if (type == 8028) {
					npc.setFacePlayer(true);
				}
				if (type >= 2042 && type <= 2044 && npc.getHealth().getAmount() > 0) {
					Player player = PlayerHandler.players[npc.spawnedBy];
					if (player != null && player.getZulrahEvent().getNpc() != null
							&& npc.equals(player.getZulrahEvent().getNpc())) {
						int stage = player.getZulrahEvent().getStage();
						if (type == 2042) {
							if (stage == 0 || stage == 1 || stage == 4 || stage == 9 && npc.totalAttacks >= 20
									|| stage == 11 && npc.totalAttacks >= 5) {
								return;
							}
						}
						if (type == 2044) {
							if ((stage == 5 || stage == 8) && npc.totalAttacks >= 5) {
								return;
							}
						}
					}
				}
				/**
				 * Attacking player
				 **/
				if (isAggressive(index, false) && !npc.underAttack && npc.killerId <= 0 && !npc.isDead
						&& !switchesAttackers(index) && npc.inMulti() && !Boundary.isIn(npc, Boundary.GODWARS_BOSSROOMS)
						&& !Boundary.isIn(npc, Boundary.CORPOREAL_BEAST_LAIR)) {
					Player closestPlayer = null;
					int closestDistance = Integer.MAX_VALUE;
					God god = GodwarsNPCs.NPCS.get(npc.npcType);

					for (Player player : PlayerHandler.players) {
						if (player == null) {
							continue;
						}
						if (player.isIdle)
							continue;

						if (god != null && player.inGodwars() && player.getEquippedGodItems() != null
								&& player.getEquippedGodItems().contains(god)) {
							continue;
						}
						/**
						 * Skips attacking a player if mode set to invisible
						 */
						if (player.isInvisible()) {
							continue;
						}

						int distance = Misc.distanceToPoint(npc.getX(), npc.getY(), player.getX(), player.getY());
						if (distance < closestDistance && distance <= distanceRequired(index) + followDistance(index)) {
							closestDistance = distance;
							closestPlayer = player;
						}
					}
					if (closestPlayer != null) {
						npc.killerId = closestPlayer.getIndex();
						closestPlayer.underAttackBy = npc.getIndex();
						closestPlayer.underAttackBy2 = npc.getIndex();
					}
				} else if (isAggressive(index, false) && !npc.underAttack && !npc.isDead
						&& (switchesAttackers(index) || Boundary.isIn(npc, Boundary.GODWARS_BOSSROOMS))) {

					if (System.currentTimeMillis() - npc.lastRandomlySelectedPlayer > 10000) {
						int player = getCloseRandomPlayer(index);

						if (player > 0) {
							npc.killerId = player;
							PlayerHandler.players[player].underAttackBy = index;
							PlayerHandler.players[player].underAttackBy2 = index;
							npc.lastRandomlySelectedPlayer = System.currentTimeMillis();
						}
					}
				}

				if (System.currentTimeMillis() - npc.lastDamageTaken > 5000 && !npc.underAttack) {
                    npc.underAttackBy = 0;
                    npc.lastRandomlySelectedPlayer = 0;
                    npc.underAttack = false;
                    npc.randomWalk = true;
                }
                if (System.currentTimeMillis() - npc.lastDamageTaken > 10000) {
                    npc.underAttackBy = 0;
                    npc.lastRandomlySelectedPlayer = 0;
                    npc.underAttack = false;
                    npc.randomWalk = true;
                }            
                
				int p = npc.killerId;
                
                CombatScript script = npc.getCombatScript();
                if (script != null) {
        			script.process(npc, PlayerHandler.players[p]);
	                p = npc.killerId;

                }

				if ((npc.killerId > 0 || npc.underAttack) && !npc.walkingHome
						&& retaliates(npc.npcType)) {
					if (!npc.isDead) {
						if (PlayerHandler.players[p] != null) {
							if (!npc.summoner) {
								Player c = PlayerHandler.players[p];
								if (script == null)
									followPlayer(index, c.getIndex());				    
								if (npc.attackTimer == 0) {
									attackPlayer(c, index);
								}
							} else {
								Player c = PlayerHandler.players[p];
								if (c.getX() == npc.getX() && c.getY() == npc.getY()) {
									stepAway(index);
									npc.randomWalk = false;
									npc.faceEntity(c.getIndex());
								} else {
									followPlayer(index, c.getIndex());
								}
							}
						} else {
							npc.killerId = 0;
							npc.lastRandomlySelectedPlayer = 0;
							npc.underAttack = false;
							npc.faceEntity(0);
						}
					}
				}

				/**
				 * 
				 * Random walking and walking home
				 **/
				if ((!npc.underAttack) && !isFightCaveNpc(index) && npc.randomWalk && !npc.isDead && !npc.neverWalkHome) {
					npc.faceEntity(0);
					npc.killerId = 0;
					// handleClipping(i);
					if (npc.spawnedBy == 0) {
						if ((npc.getX() > npc.makeX + Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npc.getX() < npc.makeX - Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npc.getY() > npc.makeY + Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npc.getY() < npc.makeY - Config.NPC_RANDOM_WALK_DISTANCE)
										&& npc.npcType != 1635 && npc.npcType != 1636 && npc.npcType != 1637
										&& npc.npcType != 1638 && npc.npcType != 1639 && npc.npcType != 1640
										&& npc.npcType != 1641 && npc.npcType != 1642 && npc.npcType != 1643
										&& npc.npcType != 1654 && npc.npcType != 7302) {
							npc.walkingHome = true;
						}
					}

					if (npc.walkingType >= 0) {
						switch (npc.walkingType) {
						case 5:
							npc.turnNpc(npc.getX() - 1, npc.getY());
							break;
						case 4:
							npc.turnNpc(npc.getX() + 1, npc.getY());
							break;
						case 3:
							npc.turnNpc(npc.getX(), npc.getY() - 1);
							break;
						case 2:
							npc.turnNpc(npc.getX(), npc.getY() + 1);
							break;
						}
					}

					if (npc.walkingType == 1 && (!npc.underAttack) && !npc.walkingHome) {
						if (System.currentTimeMillis() - npc.getLastRandomWalk() > npc.getRandomWalkDelay()) {
							int direction = Misc.random3(8);
							int movingToX = npc.getX() + NPCClipping.DIR[direction][0];
							int movingToY = npc.getY() + NPCClipping.DIR[direction][1];
							if (npc.npcType >= 1635 && npc.npcType <= 1643 || npc.npcType == 1654
									|| npc.npcType == 7302) {
								NPCDumbPathFinder.walkTowards(npc, npc.getX() - 1 + Misc.random(8),
										npc.getY() - 1 + Misc.random(8));
							} else {
								if (Math.abs(npc.makeX - movingToX) <= 1 && Math.abs(npc.makeY - movingToY) <= 1
										&& NPCDumbPathFinder.canMoveTo(npc, direction)) {
									NPCDumbPathFinder.walkTowards(npc, movingToX, movingToY);
								}
							}
							npc.setRandomWalkDelay(TimeUnit.SECONDS.toMillis(1 + Misc.random(2)));
							npc.setLastRandomWalk(System.currentTimeMillis());
						}
					}
				}
				if (!npc.neverWalkHome && npc.walkingHome) {
					if (!npc.isDead) {
						NPCDumbPathFinder.walkTowards(npc, npc.makeX, npc.makeY);
						if (npc.moveX == 0 && npc.moveY == 0) {
							npc.teleport(npc.makeX, npc.makeY, npc.getHeight());
						}
						if (npc.getX() == npc.makeX && npc.getY() == npc.makeY) {
							npc.walkingHome = false;
						}
					} else {
						npc.walkingHome = false;
					}
				}
				/**
				 * Npc death
				 */

				if (npc.isDead) {	
				
					
					Player player = PlayerHandler.players[npc.spawnedBy];

					if (npc.actionTimer == 0 && !npc.applyDead && !npc.needRespawn) {
						
							
						if (npc.npcType == 6618) {
							npc.forceChat("Ow!");
						}
						if (npc.npcType == 6611) {
							npc.requestTransform(6612);
							npc.getHealth().reset();
							npc.isDead = false;
							npc.spawnedMinions = false;
							npc.forceChat("Do it again!!");
						} else {
							if (npc.npcType == 6612) {
								npc.npcType = 6611;
								npc.spawnedMinions = false;
							}


							if (npc.npcType == 1605) {
								CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
									@Override
									public void execute(CycleEventContainer container) {
										spawnNpc(player, 1606, 3106, 3934, 0, 1, 30, 24, 70, 60, true, true);
										container.stop();
									}

									@Override
									public void stop() {
									}
								}, 5);
							}
							Player killer1 = PlayerHandler.players[npc.spawnedBy];
							if (npc.npcType == 1606) {
								killer1.roundNpc=3;
								killer1.spawned = false;
								checkMa(killer1,index);
							}
							if (npc.npcType == 1607) {
								killer1.spawned = false;
								checkMa(killer1,index);
							}
							if (npc.npcType == 1608) {
								killer1.spawned = false;
								checkMa(killer1,index);
							}

							if (npc.npcType == 1609) {
								if (killer1 != null) {
									killer1.spawned = false;
									checkMa(killer1,index);
								}
							}
							/*
							 * if (npc.npcType == 7554) { //TODO animate objec
							 * player.getRaids().finishRaids(); }
							 */
							npc.updateRequired = true;
							npc.faceEntity(0);
							Entity killer = npc.calculateKiller();
							
							if (npc.getCombatScript() != null) {
								npc.getCombatScript().handleDeath(npc, killer);
							}

							if (killer != null) {
								npc.killedBy = killer.getIndex();
							}
							
							if (npc.npcType == 963) {
								npc.isDead = false;
								npc.npcType = 965;
								npc.requestTransform(965);
								npc.getHealth().reset();
							} else {
								npc.animNumber = getDeadEmote(index); // dead emote
								npc.animUpdateRequired = true;
							}
							
							if (npc.npcType == 6475) {
								npc.isDead = false;
								npc.npcType = 6477;
								npc.requestTransform(6477);
								npc.getHealth().reset();
							} else {
								npc.animNumber = getDeadEmote(index); // dead emote
								npc.animUpdateRequired = true;
							}
							
							npc.freezeTimer = 0;
							npc.applyDead = true;

							if (npc.npcType == 3118) {
								spawnNpc(3120, npc.getX(), npc.getY(), player.getHeight(), 10, 2, 15, 15, 0);
								spawnNpc(3120, npc.getX(), npc.getY() + 1, player.getHeight(), 10, 2, 15, 15, 0);
							}
							if (player != null) {
								this.tzhaarDeathHandler(player, index);
								this.infernoDeathHandler(player, index);
							}
							killedBarrow(index);
							npc.actionTimer = getDeathDelay(index);
							resetPlayersInCombat(index);
						}
					} else if (npc.actionTimer == 0 && npc.applyDead && !npc.needRespawn) {

						if(npc.getInstance() != null && npc.getInstance().onDeath(npc)) {
							return;
						}
						int killerIndex = npc.killedBy;
						npc.needRespawn = true;
						npc.actionTimer = getRespawnTime(index); // respawn time
						dropItems(index);
						if (killerIndex < PlayerHandler.players.length - 1) {
							Player target = PlayerHandler.players[npc.killedBy];
							
							if (target != null) {
								target.getSlayer().killTaskMonster(npc);
								target.getQuestManager().onNpcKilled(npc);
								/*
								 * if (target.getSlayer().isSuperiorNpc()) {
								 * target.getSlayer().handleSuperiorExp(npc); }
								 */
							}
						}
						if(npc.getRaidsInstance() != null){
							Optional<Player> plrOpt = PlayerHandler.getPlayerByIndex(npc.killedBy);
							npc.getRaidsInstance().handleMobDeath(plrOpt.orElse(null), type);
						}

						if(npc.getXeric() != null) {
							this.xericDeathHandler(npc);
						}
						appendBossKC(index);
						appendKillCount(index);
						handleGodwarsDeath(npc);
						handleDiaryKills(npc);
						handleDailyKills(npc);
						npc.setX(npc.makeX);
						npc.setY(npc.makeY);
						npc.getHealth().reset();
						npc.animNumber = 0x328;
						npc.updateRequired = true;
						npc.animUpdateRequired = true;

						/**
						 * Actions on certain npc deaths
						 */
						switch (npc.npcType) {
						case 965:
							npc.npcType = 963;
							break;
						case 5129:
							Glod.rewardPlayers(player);
							Glod.specialAmount = 0;
							break;
						case 4922:
							IceQueen.rewardPlayers(player);
							IceQueen.specialAmount = 0;
							break;
						case 5001:
							AntiSanta.rewardPlayers(player);
							AntiSanta.specialAmount = 0;
							break;
						case 6477:
							Tarn.tarnDeath();
							break;
						case 5462:
							EnragedGraardor.graardorDeath();
							break;
						case 7858:
							JusticiarZachariah.JusticiarDeath();
							break;
						case 7859:
							Derwen.DerwenDeath();
							break;
						case 7860:
							Porazdir.PorazdirDeath();
							break;
						case 3127:
							player.getFightCave().stop();
							break;
						/*
						 * case 5762: //Not in use case 5744: player.raidPoints += 1;
						 * player.sendMessage("You currently have @red@" + player.raidPoints +
						 * "@bla@ Assault Points."); break; case 7595: player.raidPoints += 2;
						 * player.sendMessage("You currently have @red@" + player.raidPoints +
						 * "@bla@ Assault Points."); break;
						 */
						case 6367:
						case 6369:
						case 6370:
						case 6371:
						case 6372:
						case 6373:
						case 6374:
						case 6375:
						case 6376:
						case 6377:
						case 6378:
							RecipeForDisaster rfd = player.getrecipeForDisaster();
							if (player.rfdWave < 5) {
								rfd.wave();
							}
							player.rfdWave++;
							player.rfdGloves++;

							switch (player.rfdWave) {
							case 0:
								player.getDH().sendNpcChat1("You DARE come HERE?!?!", 6368, "Culinaromancer");
								player.nextChat = -1;
								break;

							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
								player.getDH().sendNpcChat1("NOOOooo...", 6368, "Culinaromancer");
								player.nextChat = -1;
								break;

							case 6:
								player.getDH().sendNpcChat2("You have caused me enough grief!",
										"I guess I'll have to finish you off myself!", 6368, "Culinaromancer");
								player.nextChat = -1;
								break;
							}
							break;
						case 6368:
							RecipeForDisaster rfdd = player.getrecipeForDisaster();
							if (rfdd != null) {
								rfdd.end(DisposeTypes.COMPLETE);
							}
							break;

						case 5862:
							Cerberus cerb = player.getCerberus();
							if (cerb != null) {
								cerb.end(DisposeTypes.COMPLETE);
							}
							break;

						case Skotizo.SKOTIZO_ID:
							if (player.getSkotizo() != null) {
								player.getSkotizo().end(DisposeTypes.COMPLETE);
							}
							break;

						case InfernoWave.TZKAL_ZUK:
							if (player.getInferno() != null) {
								player.getInferno().end(DisposeTypes.COMPLETE);
							}
							break;

						case Skotizo.AWAKENED_ALTAR_NORTH:
							World.getWorld().getGlobalObjects().remove(28923, 1694, 9904, player.getSkotizo().getHeight()); // Remove
																													// North
																													// -
																													// Awakened
																													// Altar
							World.getWorld().getGlobalObjects().add(new GlobalObject(28924, 1694, 9904,
									player.getSkotizo().getHeight(), 2, 10, -1, -1)); // North - Empty Altar
							player.getPA().sendChangeSprite(29232, (byte) 0);
							player.getSkotizo().altarCount--;
							player.getSkotizo().northAltar = false;
							player.getSkotizo().altarMap.remove(1);
							break;
						case Skotizo.AWAKENED_ALTAR_SOUTH:
							World.getWorld().getGlobalObjects().remove(28923, 1696, 9871, player.getSkotizo().getHeight()); // Remove
																													// South
																													// -
																													// Awakened
																													// Altar
							World.getWorld().getGlobalObjects().add(new GlobalObject(28924, 1696, 9871,
									player.getSkotizo().getHeight(), 0, 10, -1, -1)); // South - Empty Altar
							player.getPA().sendChangeSprite(29233, (byte) 0);
							player.getSkotizo().altarCount--;
							player.getSkotizo().southAltar = false;
							player.getSkotizo().altarMap.remove(2);
							break;
						case Skotizo.AWAKENED_ALTAR_WEST:
							World.getWorld().getGlobalObjects().remove(28923, 1678, 9888, player.getSkotizo().getHeight()); // Remove
																													// West
																													// -
																													// Awakened
																													// Altar
							World.getWorld().getGlobalObjects().add(new GlobalObject(28924, 1678, 9888,
									player.getSkotizo().getHeight(), 1, 10, -1, -1)); // West - Empty Altar
							player.getPA().sendChangeSprite(29234, (byte) 0);
							player.getSkotizo().altarCount--;
							player.getSkotizo().westAltar = false;
							player.getSkotizo().altarMap.remove(3);
							break;
						case Skotizo.AWAKENED_ALTAR_EAST:
							World.getWorld().getGlobalObjects().remove(28923, 1714, 9888, player.getSkotizo().getHeight()); // Remove
																													// East
																													// -
																													// Awakened
																													// Altar
							World.getWorld().getGlobalObjects().add(new GlobalObject(28924, 1714, 9888,
									player.getSkotizo().getHeight(), 3, 10, -1, -1)); // East - Empty Altar
							player.getPA().sendChangeSprite(29235, (byte) 0);
							player.getSkotizo().altarCount--;
							player.getSkotizo().eastAltar = false;
							player.getSkotizo().altarMap.remove(4);
							break;
						case Skotizo.DARK_ANKOU:
							player.getSkotizo().ankouSpawned = false;
							break;

						case 6615:
							Scorpia.stage = 0;
							break;

						case 319:
							CorporealBeast.stage = 0;
							break;


						case 6600:
							spawnNpc(6601, npc.getX(), npc.getY(), 0, 0, 0, 0, 0, 0);
							break;

						case 6601:
							spawnNpc(6600, npc.getX(), npc.getY(), 0, 0, 0, 0, 0, 0);
							npcs[index] = null;
							NPC golem = getNpc(6600);
							if (golem != null) {
								golem.actionTimer = 150;
							}
							return;
						}

						if (DagannothMother.RANGE_OF_IDS.contains(npc.npcType)) {
							DagannothMother dm = player.getDagannothMother();

							if (dm != null) {
								dm.end(DisposeType.COMPLETE);
							}
						}
					} else if (npc.actionTimer == 0 && npc.needRespawn && npc.npcType != 1739
							&& npc.npcType != 1740 && npc.npcType != 1741 && npc.npcType != 1742) {
						if (player != null || npc.isNoRespawn()) {
							npcs[index] = null;
							return;
						} else {
							int newType = npc.npcType;
							int newX = npc.makeX;
							int newY = npc.makeY;
							int newH = npc.getHeight();
							int newWalkingType = npc.walkingType;
							int newHealth = npc.getHealth().getMaximum();
							int newMaxHit = npc.maxHit;
							int newAttack = npc.attack;
							int newDefence = npc.defence;
							npcs[index] = null;
							newNPC(newType, newX, newY, newH, newWalkingType, newHealth, newMaxHit, newAttack, newDefence, npc.getInstance());
							return;
						}
						/*
						 * } else { for(MonsterHunt.Npcs hunt : MonsterHunt.Npcs.values()) {
						 * if(npc.npcType == hunt.getNpcId()) { npc = null; return; } } }
						 */
					}
				}
			
		
		});
	}

	/**
	 * Apply damage
	 * 
	 * @param i
	 *            the damage were applying towards a playable character
	 */
	public void applyDamage(int i) {
		if (npcs[i] != null) {
			if (PlayerHandler.players[npcs[i].oldIndex] == null) {
				return;
			}
			if (npcs[i].isDead) {
				return;
			}
			if (npcs[i].npcType >= 1739 && npcs[i].npcType <= 1742 || npcs[i].npcType == 7413) {
				return;
			}

			Player c = PlayerHandler.players[npcs[i].oldIndex];

			if(npcs[i].npcType == 7706 && c.getInferno().behindGlyph){
				c.getInferno().behindGlyph = false;
				return;
			}
			if (multiAttacks(i)) {
				multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0) {
				if (c.autoRet == 1 && !c.getMovementQueue().hasSteps()) {
					c.npcIndex = i;
				}
			}
			if (c.attackTimer <= 3) {
				if (!NPCHandler.isFightCaveNpc(i) && !isInfernoNpc(i) && npcs[i].npcType != 319) {
					c.startAnimation(c.getCombat().getBlockEmote());
				}
			}
			if (c.getItems().isWearingItem(12931) || c.getItems().isWearingItem(13197)
					|| c.getItems().isWearingItem(13199)) {
				DamageEffect venom = new SerpentineHelmEffect();
				if (venom.isExecutable(c)) {
					venom.execute(c, npcs[i], new Damage(6));
				}
			}
			
			npcs[i].totalAttacks++;
			boolean protectionIgnored = prayerProtectionIgnored(i);
			if (c.respawnTimer <= 0) {
				int damage = 0;
				int secondDamage = -1;

				Optional<Brother> activeBrother = c.getBarrows().getActive();

				/**
				 * Handles all the different damage approaches npcs are dealing
				 */
				if (npcs[i].attackType != null) {
					switch (npcs[i].attackType) {

					/**
					 * Handles npcs who are dealing melee based attacks
					 */
					case MELEE:
						damage = Misc.random(getMaxHit(i));

						switch (npcs[i].npcType) {
						case 6477:
							Tarn.performMelee(c);
							break;
						case 6374:
							secondDamage = Misc.random(getMaxHit(i));
							break;
							
						case 3116:
							c.getSkills().decreaseLevelOrMin(1, 0, Skill.PRAYER);
							c.getPA().refreshSkill(Config.PRAYER);
							break;

						/**
						 * Summoned soul melee
						 */
						// case 5869:
						// damage = !c.protectingMelee() ? 30 : 0;
						// player.getSkills().getLevel(Skill.PRAYER) -= c.protectingMelee() || c.protectingMelee() &&
						// c.getItems().isWearingItem(12821) ? 30 : 0;
						// if (player.getSkills().getLevel(Skill.PRAYER) < 0) {
						// player.getSkills().getLevel(Skill.PRAYER) = 0;
						// }
						// break;
						}

						/**
						 * Calculate defence
						 */
						if (10 + Misc.random(c.getCombat().calculateMeleeDefence()) > Misc
								.random(NPCHandler.npcs[i].attack)) {
							damage = 0;
						}

						if (npcs[i].npcType == 5869) {
							damage = !c.protectingMelee() ? 30 : 0;
							c.getSkills().decreaseLevelOrMin(c.protectingMelee() && !c.getItems().isWearingItem(12821) ? 30
									: c.protectingMelee() && c.getItems().isWearingItem(12821) ? 15 : 0, 0, Skill.PRAYER);
							c.getSkills().sendRefresh();
						}

						/**
						 * Zulrah
						 */
						if (npcs[i].npcType == 2043 && c.getZulrahEvent().getNpc() != null
								&& c.getZulrahEvent().getNpc().equals(npcs[i])) {
							Boundary boundary = new Boundary(npcs[i].targetedLocation.getX(),
									npcs[i].targetedLocation.getY(), npcs[i].targetedLocation.getX(),
									npcs[i].targetedLocation.getY());
							if (!Boundary.isIn(c, boundary)) {
								return;
							}
							damage = 20 + Misc.random(25);
						}

						/**
						 * Special attacks
						 */
						if (activeBrother.isPresent() && activeBrother.get().getId() == npcs[i].npcType) {
							double random = Math.random();
							if (random <= Barrows.SPECIAL_CHANCE) {
								switch (activeBrother.get().getId()) {
								case Brother.DHAROK:
									double healthRatio = Math.round(
											(npcs[i].getHealth().getAmount() / npcs[i].getHealth().getMaximum()) * 10)
											/ 10d;
									healthRatio = Double.max(0.1, healthRatio);
									damage *= -2 * healthRatio + 3;
									break;
								case Brother.GUTHAN:
									int addedHealth = c.protectingMelee() ? 0
											: Integer.min(damage,
													npcs[i].getHealth().getMaximum() - npcs[i].getHealth().getAmount());
									if (addedHealth > 0) {
										c.gfx0(398);
										npcs[i].getHealth().increase(addedHealth);
									}
									break;

								case Brother.TORAG:
									c.gfx0(399);
									break;

								case Brother.VERAC:
									protectionIgnored = true;
									damage /= 2;
									break;

								}
							}
						}

						/**
						 * Protection prayer
						 */
						if (c.protectingMelee() && !protectionIgnored) {
							if (npcs[i].npcType == 5890)
								damage /= 3;
							else if (npcs[i].npcType == 963 || npcs[i].npcType == 965 || npcs[i].npcType == 8349
									|| npcs[i].npcType == 8133 || npcs[i].npcType == 6342 || npcs[i].npcType == 2054
									|| npcs[i].npcType == 239
									|| npcs[i].npcType == 998 || npcs[i].npcType == 999
									|| npcs[i].npcType == 1000 || npcs[i].npcType == 7554 || npcs[i].npcType == 319
									|| npcs[i].npcType == 320 || npcs[i].npcType == 6615 || npcs[i].npcType == 5916
									|| npcs[i].npcType == 7544 || npcs[i].npcType == 5129)
								damage /= 2;
							else
								damage = 0;
						} else if (c.protectingMelee() && protectionIgnored) {
							damage /= 2;
						}
						
						if (c.protectingRange() && !protectionIgnored) {
							if (npcs[i].npcType == 7706)
								damage /= 4;
							if (npcs[i].npcType == 7554)
								damage /= 2;
						}

						/**
						 * Specials and defenders
						 */
						if (World.getWorld().getEventHandler().isRunning(c, "staff_of_the_dead")) {
							Special special = Specials.STAFF_OF_THE_DEAD.getSpecial();
							Damage d = new Damage(damage);
							special.hit(c, npcs[i], d);
							damage = d.getAmount();
						}
						if (c.playerEquipment[c.playerShield] == 12817) {//ely
							if (Misc.random(100) > 30 && damage > 0) {
								damage *= .75;
								c.gfx100(321);
							}
						}
						
						if (c.playerEquipment[c.playerShield] == 33787) {//Shadow spirit shield
							if (Misc.random(1, 6) == 3) {
								damage *= .75;
								c.startAnimation(6696);
								c.gfx100(1681);
								npcs[i].gfx100(1681);
								npcs[i].appendDamage(Misc.random(20), Hitmark.HIT);
							}
						}
						
						if (c.playerEquipment[c.playerShield] == 33788) {//Reflective spirit shield
							if (Misc.random(1, 6) == 3) {
							damage *= .75;
							} else {
								c.startAnimation(6696);
								damage *= .85;
								npcs[i].appendDamage((int) Math.ceil(damage *= .85), Hitmark.HIT);
							}
						}
							
						if (c.playerEquipment[c.playerShield] == 33789) {//Siphon spirit shield
							if (Misc.random(1, 6) == 3) {
								c.startAnimation(6696);
								damage *= .75;
								npcs[i].appendDamage((int) Math.ceil(damage *= .85), Hitmark.HIT);
								c.getHealth().increase((int) Math.ceil(damage *= .85));
						}
					}
						
						if (c.playerEquipment[c.playerWeapon] == 33813) {//grace bow
							if (Misc.random(1, 10) == 5 && damage > 0) {
								c.gfx100(433);
								if (c.getItems().isWearingItem(33824) && c.getItems().isWearingItem(33827) && c.getItems().isWearingItem(33830)) {
									damage *= .65;
								} else {
								npcs[i].appendDamage((int) Math.ceil(damage *= .75), Hitmark.HIT);
								damage *= .75;
								}
							}
						}
						if (c.playerEquipment[c.playerWeapon] == 33820) {//grace staff
							if (Misc.random(1, 10) == 5 && damage > 0) {
								c.gfx100(433);
								if (c.getItems().isWearingItem(33824) && c.getItems().isWearingItem(33827) && c.getItems().isWearingItem(33830)) {
									damage *= .65;
								} else {
								npcs[i].appendDamage((int) Math.ceil(damage *= .75), Hitmark.HIT);
								damage *= .75;
								}
							}
						}
						if (c.playerEquipment[c.playerWeapon] == 33816) {//grace sword
							if (Misc.random(1, 10) == 5 && damage > 0) {
								c.gfx100(433);
								if (c.getItems().isWearingItem(33824) && c.getItems().isWearingItem(33827) && c.getItems().isWearingItem(33830)) {
									damage *= .65;
								} else {
								npcs[i].appendDamage((int) Math.ceil(damage *= .75), Hitmark.HIT);
								damage *= .75;
								}
							}
						}
						
						if (c.playerEquipment[c.playerFeet] == 33879 || c.playerEquipment[c.playerFeet] == 33880 || c.playerEquipment[c.playerFeet] == 33881) {
							damage *= .95;
						}
						
						
						if (c.playerEquipment[c.playerShield] == 32991 && c.prayerPoint > 0) {
							int drain =  (int) Math.ceil((damage * 0.3) / 2);
							if ( c.getSkills().getLevel(Skill.PRAYER) >= drain) {
								c.getSkills().decreaseLevelOrMin(drain, 0, Skill.PRAYER);
								damage *= .8;
								c.gfx100(321);
							}
						}
						
						if (c.playerEquipment[c.playerShield] == 33805 && c.inWild()) {//shadow lord shield
							damage *= .75;
							c.gfx100(321);
						}

						
						if (c.asPlayer().getItems().isWearingItem(33795) && c.asPlayer().getItems().isWearingItem(33794) && c.asPlayer().getItems().isWearingItem(33793)) {//shadow lord armor
							if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.appendDamage(damage = 0, Hitmark.MISS);
							}
						}						
						//Bracelet of ethereum
						 if (c.getItems().isWearingItem(21816)) {
							if (c.ethereumCharge <= 0) {
								c.getItems().deleteEquipment(21816, 9);
								c.getItems().wearItem(21817, 1, 9);
							} else if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.ethereumCharge--;
								c.appendDamage(0, Hitmark.MISS);
								return;
							}
						}
						
						if (c.getItems().isWearingItem(33918)) {
							if (c.sirenicMaskCharge <= 0) {
								c.getItems().deleteEquipment(33918, 0);
								c.getItems().wearItem(33933, 1, 0);
								c.sendMessage("Your Sirenic mask has run out of charges.");
							} else {
								c.sirenicMaskCharge--;
							}
						}
						
						if (c.getItems().isWearingItem(33919)) {
							if (c.sirenicBodyCharge <= 0) {
								c.getItems().deleteEquipment(33919, 0);
								c.getItems().wearItem(33934, 1, 4);
								c.sendMessage("Your Sirenic body has run out of charges.");
							} else {
								c.sirenicBodyCharge--;
							}
						}
						
						if (c.getItems().isWearingItem(33920)) {
							if (c.sirenicBodyCharge <= 0) {
								c.getItems().deleteEquipment(33920, 0);
								c.getItems().wearItem(33935, 1, 7);
								c.sendMessage("Your Sirenic chaps has run out of charges.");
							} else {
								c.sirenicChapsCharge--;
							}
						}
						
						/*
						 * chaotic shield
						 */
						if (c.playerEquipment[c.playerShield] == 33096) {
							int blockChance = Misc.random(1, 5);
							
							if (blockChance == 5) {
								npcs[i].gfx100(558);
								c.sendMessage("Your shield invigorates a Chaotic Force around you.");
								damage = 0;
							}
						}
						
						/*
						 * justiciar set def buff
						 */
						
						  if (c.playerEquipment[c.playerChest] == 22327 && c.playerEquipment[c.playerHat] == 22326 && c.playerEquipment[c.playerLegs] == 22328) {
	                                damage *= .80;
	                        }
						  if (c.playerEquipment[c.playerChest] == 33375 && c.playerEquipment[c.playerHat] == 33376 && c.playerEquipment[c.playerLegs] == 33377) {
                              damage *= .80;
                      }
						if (c.getHealth().getAmount() - damage < 0) {
							damage = c.getHealth().getAmount();
						}
						break;

					/**
					 * Handles npcs who are dealing range based attacks
					 */
					case RANGE:
						
						if (c.getItems().isWearingItems(33795, 33794, 33793)) {
							if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.appendDamage(damage = 0, Hitmark.MISS);
							}
						} 							
						//Bracelet of ethereum
						 if (c.getItems().isWearingItem(21816)) {
							if (c.ethereumCharge <= 0) {
								c.getItems().deleteEquipment(21816, 9);
								c.getItems().wearItem(21817, 1, 9);
							} else if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.ethereumCharge--;
								c.appendDamage(0, Hitmark.MISS);
								return;
							}
						}
						
						damage = Misc.random(getMaxHit(i));

						switch (npcs[i].npcType) {
						case 6377:
							secondDamage = Misc.random(getMaxHit(i));
							break;
							
						case 8028:// VORKATH range hits will hit you no matter what
							c.getVorkath().executeAttack(c, 5);// executes vorkaths range attack
							return;
							
						case 6371: //Karamel ranged drain
							int[] skills = {0, 1, 2, 4, 6};
							int skill = skills[Misc.random(skills.length - 1)];
							int drain = Misc.random((int) (getMaxHit(i) / 2));
							c.getSkills().decreaseLevelOrMin(drain, Skill.forId(skill));
							c.getPA().refreshSkill(skill);
							break;

						/**
						 * Summoned soul range
						 */
						// case 5867:
						// damage = !c.protectingRange() ? 30 : 0;
						// player.getSkills().getLevel(Skill.PRAYER) -= c.protectingRange() || c.protectingRange() &&
						// c.getItems().isWearingItem(12821) ? 30 : 0;
						// if (player.getSkills().getLevel(Skill.PRAYER) < 0) {
						// player.getSkills().getLevel(Skill.PRAYER) = 0;
						// }
						// break;
						}

						/**
						 * Range defence
						 */
						if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc.random(NPCHandler.npcs[i].attack)) {
							damage = 0;
						}

						if (npcs[i].npcType == 5867) {
							damage = !c.protectingRange() ? 30 : 0;
							c.getSkills().decreaseLevelOrMin(c.protectingMelee() && !c.getItems().isWearingItem(12821) ? 30
									: c.protectingMelee() && c.getItems().isWearingItem(12821) ? 15 : 0, 0, Skill.PRAYER);
						
						}

						/**
						 * Special attacks
						 */
						if (activeBrother.isPresent() && activeBrother.get().getId() == npcs[i].npcType) {
							double random = Math.random();
							if (random <= Barrows.SPECIAL_CHANCE) {
								switch (activeBrother.get().getId()) {
								case Brother.KARIL:
									c.getSkills().setLevelOrMin((int) (c.getSkills().getLevel(Skill.AGILITY) * 0.8), Skill.AGILITY);
									c.getPA().refreshSkill(Config.AGILITY);
									c.gfx0(401);
									break;
								}
							}
						}
						/**
						 * Protection prayer
						 */
						if (c.protectingRange() && !protectionIgnored) {
							if (npcs[i].npcType == 963 || npcs[i].npcType == 965 || npcs[i].npcType == 8349
									|| npcs[i].npcType == 8133 || npcs[i].npcType == 6342 || npcs[i].npcType == 2054
									|| npcs[i].npcType == 7554 || npcs[i].npcType == 239 || npcs[i].npcType == 8027 
									|| npcs[i].npcType == 319
									|| npcs[i].npcType == 499) {
								damage /= 2;
							} else {
								damage = 0;
							}
							if (c.getHealth().getAmount() - damage < 0) {
								damage = c.getHealth().getAmount();
							}
						} else if (c.protectingRange() && protectionIgnored) {
							damage /= 2;
						}
						if (npcs[i].npcType == 2042 || npcs[i].npcType == 2044) {
							c.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(npcs[i]));
						}
						if (npcs[i].endGfx > 0 || npcs[i].npcType == 3127) {
							c.gfx100(npcs[i].endGfx);
						}
						if (npcs[i].endGfx > 0 || npcs[i].npcType == 7700) {
							c.gfx100(npcs[i].endGfx);
						}
						break;

					/**
					 * Handles npcs who are dealing mage based attacks
					 */
					case MAGE:
						

						if (c.getItems().isWearingItems(33795, 33794, 33793)) {
							if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.appendDamage(damage = 0, Hitmark.MISS);
							}
						} 						
						//Bracelet of ethereum
						else if (c.getItems().isWearingItem(21816)) {
							if (c.ethereumCharge <= 0) {
								c.getItems().deleteEquipment(21816, 9);
								c.getItems().wearItem(21817, 1, 9);
							} else if (Misc.linearSearch(Config.REV_IDS, npcs[i].npcType) != -1) {
								c.ethereumCharge--;
								c.appendDamage(0, Hitmark.MISS);
								return;
							}
						}
						
						if(npcs[i].npcType == 6477) {
							Tarn.performFreeze();
							return;
						}
						damage = Misc.random(getMaxHit(i));
						boolean magicFailed = false;
						/**
						 * Attacks
						 */
						switch (npcs[i].npcType) {
						case 6373:
						case 6375:
						case 6376:
						case 6378:
							secondDamage = Misc.random(getMaxHit(i));
							break;
						case 6477:
							Tarn.performFreeze();
							break;
						case 8028:// if player is using protect from mage or not against vorkath instant damage
							if (c.protectingMagic() == true) {//protects players from vorkaths magic attacks
								damage = 0;
							}
							c.getVorkath().executeAttack(c, c.getVorkath().attackType);//exectues effects
							break;

						/**
						 * Summoned soul mage
						 */
						// case 5868:
						// damage = !c.protectingMagic() ? 30 : 0;
						// player.getSkills().getLevel(Skill.PRAYER) -= c.protectingMagic() || c.protectingMagic() &&
						// c.getItems().isWearingItem(12821) ? 30 : 0;
						// if (player.getSkills().getLevel(Skill.PRAYER) < 0) {
						// player.getSkills().getLevel(Skill.PRAYER) = 0;
						// }
						// break;

						case 6371: // Karamel
							c.freezeTimer = 4;
							break;

						case 2205:
							secondDamage = Misc.random(27);
							break;

						case 6609:
							c.sendMessage("Callisto's fury sends an almighty shockwave through you.");
							break;
						}

						/**
						 * Magic defence
						 */
						if (10 + Misc.random(c.getCombat().mageDef()) > Misc.random(NPCHandler.npcs[i].attack)) {
							damage = 0;
							if (secondDamage > -1) {
								secondDamage = 0;
							}
							magicFailed = true;
						}

						if (npcs[i].npcType == 5868) {
							damage = !c.protectingMagic() ? 30 : 0;
							c.getSkills().decreaseLevelOrMin(c.protectingMagic() && !c.getItems().isWearingItem(12821) ? 30
									: c.protectingMagic() && c.getItems().isWearingItem(12821) ? 15 : 0, 0, Skill.PRAYER);
							
						}
						/**
						 * Protection prayer
						 */
						if (c.protectingMagic() && !protectionIgnored) {
							switch (npcs[i].npcType) {
							case 494:
							case 492:
							case 5535:
								int max = npcs[i].npcType == 494 ? 2 : 0;
								if (Misc.random(2) == 0) {
									damage = 1 + Misc.random(max);
								} else {
									damage = 0;
									if (secondDamage > -1) {
										secondDamage = 0;
									}
								}
								break;

							case 1677:
							case 963:
							case 965:
							case 8349:
							case 8133:
							case 6342:
							case 2054:
							case 239:
							case 8027:
							case 1046:
							case 319:
							case 7554:
							case 7604: // Skeletal mystic
							case 7605: // Skeletal mystic
							case 7606: // Skeletal mystic
							case 7617:
							case 4922:
								damage /= 2;
								break;
								
							case 3822:
								if (Misc.random(1, 5) == 3) {
									damage /= 3;
								}

							default:
								damage = 0;
								if (secondDamage > -1) {
									secondDamage = 0;
								}
								magicFailed = true;
								break;

							}

						} else if (c.protectingMagic() && protectionIgnored) {
							damage /= 2;
						}
						if (c.getHealth().getAmount() - damage < 0) {
							damage = c.getHealth().getAmount();
						}
						if (npcs[i].endGfx > 0 && (!magicFailed || isFightCaveNpc(i))) {
							c.gfx100(npcs[i].endGfx);
						} else {
							c.gfx100(85);
						}
						c.getCombat().appendVengeanceNPC(damage + (secondDamage > 0 ? secondDamage : 0), i);
						break;

					/**
					 * Handles npcs who are dealing dragon fire based attacks
					 */
					case DRAGON_FIRE:
						int resistance = c.getItems().isWearingItem(1540) || c.getItems().isWearingItem(33115) || c.getItems().isWearingItem(11283)
								|| c.getItems().isWearingItem(11284) ? 1 : 0;
						if (System.currentTimeMillis() - c.lastAntifirePotion < c.antifireDelay) {
							resistance++;
						}
						if (System.currentTimeMillis() - c.lastSuperAntifirePotion < c.SuperantifireDelay) {
							resistance = 2;
						}
						if (resistance == 0) {
							damage = Misc.random(getMaxHit(i));
							if (npcs[i].npcType == 465 || npcs[i].npcType == 7795 || npcs[i].npcType == 7794 || npcs[i].npcType == 7793 || npcs[i].npcType == 7792) {
								c.sendMessage("You are badly burnt by the cold breeze!");
							} else {
								c.sendMessage("You are badly burnt by the dragon fire!");
							}
						} else if (resistance == 1) {
							damage = Misc.random(15);
						} else if (resistance == 2) {
							damage = 0;
						}
						if (npcs[i].endGfx != 430 && resistance == 2) {
							damage = 5 + Misc.random(5);
						}

						/**
						 * Attacks
						 */
						switch (npcs[i].endGfx) {
						case 429:
							c.getHealth().proposeStatus(HealthStatus.POISON, 6, Optional.of(npcs[i]));
							break;

						case 163:
							c.freezeTimer = 15;
							c.sendMessage("You have been frozen to the ground.");
							break;

						case 428:
							c.freezeTimer = 10;
							break;

						case 431:
							c.lastSpear = System.currentTimeMillis();
							break;
						}
						if (c.getHealth().getAmount() - damage < 0)
							damage = c.getHealth().getAmount();
						c.gfx100(npcs[i].endGfx);

						c.getCombat().appendVengeanceNPC(damage + 0, i);
						break;

					/**
					 * Handles npcs who are dealing special attacks
					 */
					case SPECIAL:
						damage = Misc.random(getMaxHit(i));

						/**
						 * Attacks
						 */
						switch (npcs[i].npcType) {
						case 3129:
							int prayerReduction = c.getSkills().getLevel(Skill.PRAYER) / 2;
							if (prayerReduction < 1) {
								break;
							}
							c.getSkills().decreaseLevelOrMin(prayerReduction, 0, Skill.PRAYER);
							c.getPA().refreshSkill(5);
							c.sendMessage(
									"K'ril Tsutsaroth slams through your protection prayer, leaving you feeling drained.");
							break;
						case 1046:
						case 1049:
							prayerReduction = c.getSkills().getLevel(Skill.PRAYER) / 10;
							if (prayerReduction < 1) {
								break;
							}
							c.getSkills().decreaseLevelOrMin(prayerReduction, 0, Skill.PRAYER);
							c.getPA().refreshSkill(5);
							c.sendMessage("Your prayer has been drained drastically.");
							break;
						case 6609:
							damage = 3;
							c.gfx0(80);
							c.lastSpear = System.currentTimeMillis();
							c.getPA().getSpeared(npcs[i].getX(), npcs[i].getY(), 3);
							c.sendMessage("Callisto's roar sends your backwards.");
							break;
						case 6610:
							if (c.protectingMagic()) {
								damage *= .7;
							}
							secondDamage = Misc.random(getMaxHit(i));
							if (secondDamage > 0) {
								c.gfx0(80);
							}
							break;

						case 465:
							c.freezeTimer = 15;
							c.sendMessage("You have been frozen.");
							break;

						case 7144:
						case 7145:
						case 7146:
							if (gorillaBoulder.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								return;
							}
							break;

						case 5890:
							if (damage > 0 && Misc.random(2) == 0) {
								if (npcs[i].getHealth().getStatus() == HealthStatus.POISON) {
									c.getHealth().proposeStatus(HealthStatus.POISON, 15, Optional.of(npcs[i]));
								}
							}
							if (gorillaBoulder.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								return;
							}
							break;
						}
						break;
					}

					if (npcs[i].npcType == 320) {
						int distanceFromTarget = c.distanceToPoint(npcs[i].getX(), npcs[i].getY());

						if (distanceFromTarget <= 1) {
							NPC corp = NPCHandler.getNpc(319);
							Damage heal = new Damage(
									damage + Misc.random(15 + 5) + (secondDamage > 0 ? secondDamage : 0));
							if (corp != null && corp.getHealth().getAmount() < 2000) {
								corp.getHealth().increase(heal.getAmount());
							}
						}
					}
					if (npcs[i].npcType == 6617 || npcs[i].npcType == 6616 || npcs[i].npcType == 6615) {
						int distanceFromTarget = c.distanceToPoint(npcs[i].getX(), npcs[i].getY());

						List<NPC> healer = Arrays.asList(NPCHandler.npcs);

						if (distanceFromTarget <= 1 && Scorpia.stage > 0 && healer.stream().filter(Objects::nonNull)
								.anyMatch(n -> n.npcType == 6617 && !n.isDead && n.getHealth().getAmount() > 0)) {
							NPC scorpia = NPCHandler.getNpc(6615);
							Damage heal = new Damage(
									damage + Misc.random(20 + 5) + (secondDamage > 0 ? secondDamage : 0));
							if (scorpia != null && scorpia.getHealth().getAmount() < 150) {
								scorpia.getHealth().increase(heal.getAmount());
							}
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx100(npcs[i].endGfx);
					}
					int poisonDamage = getPoisonDamage(npcs[i]);
					if (poisonDamage > 0 && Misc.random(10) == 1) {
						c.getHealth().proposeStatus(HealthStatus.POISON, poisonDamage, Optional.of(npcs[i]));
					}
					if (damage < npcs[i].minHit) {
						damage = npcs[i].minHit;
					}
					playerDamage(c, i, damage, secondDamage);
					switch (npcs[i].npcType) {
					// Abyssal sire
					case 5890:
						int health = npcs[i].getHealth().getAmount();
						c.sireHits++;
						int randomAmount = Misc.random(5);
						switch (c.sireHits) {
						case 10:
						case 20:
						case 30:
						case 40:
							for (int id = 0; id < randomAmount; id++) {
								int x = npcs[i].getX() + Misc.random(2);
								int y = npcs[i].getY() - Misc.random(2);
								newNPC(5916, x, y, 0, 0, 15, 15, 100, 0, null);
							}
							break;

						case 45:
							c.sireHits = 0;
							break;

						}
						if (health < 400 && health > 329 || health < 100) {
							npcs[i].attackType = CombatType.MELEE;
						}
						if (health < 330 && health > 229) {
							npcs[i].attackType = CombatType.MAGE;
						}
						if (health < 230 && health > 99) {
							npcs[i].attackType = CombatType.SPECIAL;
							npcs[i].getHealth().increase(6);
						}
						break;

					/**
					 * Demonic Gorillas attack
					 */

					/*
					 * case 7554: npcs[i].attackType = CombatType.MAGE; npcs[i].projectileId = 970;
					 * npcs[i].endGfx = 971; npcs[i].hitDelayTimer = 3; break;
					 */
					case 7144:
					case 7145:
					case 7146:
						if (damage == 0) {
							if (c.totalMissedGorillaHits >= 6) {
								c.totalMissedGorillaHits = 0;
							}
							c.totalMissedGorillaHits++;
						}
						if (c.totalMissedGorillaHits == 6) {
							c.totalMissedGorillaHits = 0;

							switch (npcs[i].attackType) {
							case MELEE:
								switch (Misc.random(2)) {
								case 0:
									npcs[i].attackType = CombatType.MAGE;
									break;
								case 1:
									npcs[i].attackType = CombatType.SPECIAL;
									break;
								case 2:
									npcs[i].attackType = CombatType.RANGE;
									break;
								}
								break;
							case MAGE:
								switch (Misc.random(2)) {
								case 0:
									npcs[i].attackType = CombatType.MELEE;
									break;
								case 1:
									npcs[i].attackType = CombatType.SPECIAL;
									break;
								case 2:
									npcs[i].attackType = CombatType.RANGE;
									break;
								}
								break;
							case RANGE:
								switch (Misc.random(2)) {
								case 0:
									npcs[i].attackType = CombatType.MAGE;
									break;
								case 1:
									npcs[i].attackType = CombatType.SPECIAL;
									break;
								case 2:
									npcs[i].attackType = CombatType.MELEE;
									break;
								}
								break;
							case SPECIAL:
								switch (Misc.random(2)) {
								case 0:
									npcs[i].attackType = CombatType.MAGE;
									break;
								case 1:
									npcs[i].attackType = CombatType.MELEE;
									break;
								case 2:
									npcs[i].attackType = CombatType.RANGE;
									break;
								}
								break;

							default:
								break;
							}
							break;
						}
						c.updateRequired = true;
					}
				}
			}
		}
	}
	
	public void playerDamage(Player c, int i,  int damage, int secondDamage) {
		if (c.getHealth().getAmount() - damage < 0
				|| secondDamage > -1 && c.getHealth().getAmount() - secondDamage < 0) {
			damage = c.getHealth().getAmount();
			if (secondDamage > -1) {
				secondDamage = 0;
			}
		}
		handleSpecialEffects(c, i, damage);
		c.logoutDelay = System.currentTimeMillis();
		if (damage > -1) {
			c.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
			c.addDamageTaken(npcs[i], damage);
		}
		if (secondDamage > -1) {
			c.appendDamage(secondDamage, secondDamage > 0 ? Hitmark.HIT : Hitmark.MISS);
			c.addDamageTaken(npcs[i], secondDamage);
		}
		if (damage > 0 || secondDamage > 0) {
			c.getCombat().appendVengeanceNPC(damage + (secondDamage > 0 ? secondDamage : 0), i);
			c.getCombat().applyRecoilNPC(damage + (secondDamage > 0 ? secondDamage : 0), i);
		}
		int rol = c.getHealth().getAmount() - damage;
		if (rol > 0 && rol < c.getHealth().getMaximum() / 10) {
			ringOfLife(c);
		}
	}

	/**
	 * Poison damage
	 * 
	 * @param npc
	 *            the npc whom can be poisonous
	 * @return the amount of damage the poison will begin on
	 */
	private int getPoisonDamage(NPC npc) {
		switch (npc.npcType) {
		case 3129:
			return 16;

		case 3021:
			return 5;

		case 957:
			return 4;

		case 959:
			return 6;

		case 6615:
			return 10;
		}
		return 0;
	}

	/**
	 * Multi attacks from a distance
	 * 
	 * @param npc
	 *            the npc whom can pefrom multiattacks from a distance
	 * @return the distance that the npc can reach from
	 */
	private int multiAttackDistance(NPC npc) {
		if (npc == null) {
			return 0;
		}
		switch (npc.npcType) {
		case 239:
		case 8031:
		case 8030:
			return 35;
		case 7554:
			return 30;
		}
		return 15;
	}

	/**
	 * Multi attack damage
	 * 
	 * @param i
	 *            the damage set
	 */
	public void multiAttackDamage(int i) {
		int damage = Misc.random(getMaxHit(i));
		Hitmark hitmark = damage > 0 ? Hitmark.HIT : Hitmark.MISS;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Player c = PlayerHandler.players[j];
				if (c.isDead || c.getHeight() != npcs[i].getHeight())
					continue;
				if (PlayerHandler.players[j].isInvisible()) {
					continue;
				}
				if (PlayerHandler.players[j].goodDistance(c.getX(), c.getY(), npcs[i].getX(), npcs[i].getY(),
						multiAttackDistance(npcs[i]))) {
					if (npcs[i].attackType == CombatType.SPECIAL) {
						if (npcs[i].npcType == 5862) {
							if (cerberusGroundCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						if (npcs[i].npcType == 6618) {
							if (archSpellCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						
						if (npcs[i].npcType == 8609) {
							if (hydraPoisonCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						
						if (npcs[i].npcType == 7566) {
							if (vasaRockCoordinates.parallelStream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						if (npcs[i].npcType == 8030) {
							if (DragonGroundCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						if (npcs[i].npcType == 6766) {
							if (explosiveSpawnCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}

						if (Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR)) {
							if (!Boundary.isIn(c, Boundary.CORPOREAL_BEAST_LAIR)) {
								return;
							}
						}
						if (npcs[i].npcType == 6619) {
							if (fanaticSpellCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						if (npcs[i].npcType == 6611 || npcs[i].npcType == 6612) {
							if (!(c.getX() > npcs[i].getX() - 5 && c.getX() < npcs[i].getX() + 5 && c.getY() > npcs[i].getY() - 5
									&& c.getY() < npcs[i].getY() + 5)) {
								continue;
							}
							c.sendMessage(
									"Vet'ion pummels the ground sending a shattering earthquake shockwave through you.");
							createVetionEarthquake(c);
						}
						if (npcs[i].npcType == 319) {
							if (corpSpellCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						c.appendDamage(damage, hitmark);
					} else if (npcs[i].attackType == CombatType.DRAGON_FIRE) {
						int resistance = c.getItems().isWearingItem(1540) || c.getItems().isWearingItem(33115) || c.getItems().isWearingItem(11283)
								|| c.getItems().isWearingItem(11284) ? 1 : 0;
						if (System.currentTimeMillis() - c.lastAntifirePotion < c.antifireDelay) {
							resistance++;
						}
					//	c.sendMessage("Resistance: " + resistance);
						if (resistance == 0) {
							damage = Misc.random(getMaxHit(i));
							c.sendMessage("You are badly burnt by the dragon fire!");
						} else if (resistance == 1)
							damage = Misc.random(15);
						else if (resistance == 2)
							damage = 0;
						if (c.getHealth().getAmount() - damage < 0)
							damage = c.getHealth().getAmount();
						c.gfx100(npcs[i].endGfx);
						c.appendDamage(damage, hitmark);
					} else if (npcs[i].attackType == CombatType.MAGE) {
						if (npcs[i].npcType == 6611 || npcs[i].npcType == 6612) {
							if (vetionSpellCoordinates.stream().noneMatch(p -> p[0] == c.getX() && p[1] == c.getY())) {
								continue;
							}
						}
						if (npcs[i].npcType == 3162) {
							damage /= 3;
						}
						if (!c.protectingMagic()) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().mageDef())) {
								c.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
							} else {
								c.appendDamage(0, Hitmark.MISS);
							}
						} else {
							switch (npcs[i].npcType) {
							case 1046:
							case 3162:
							case 6610:
							case 6611:
							case 6612:
								damage *= .5;
								break;
								
							case 7554:
								if (c.protectingMagic())
									damage /= 2;
								break;

							case 319:
								if (c.protectingMagic())
									damage /= 2;
								break;
							case 6477:
								if (c.protectingMagic())
									damage /= 2;
							default:
								damage = 0;
								break;
							}
							c.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
						}
					} else if (npcs[i].attackType == CombatType.RANGE) {
						if (!c.protectingRange()) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().calculateRangeDefence())) {
								c.appendDamage(damage, damage > 0 ? Hitmark.HIT : Hitmark.MISS);
							} else {
								c.appendDamage(0, Hitmark.MISS);
							}
							if (npcs[i].npcType == 2215) {
								damage /= 2;
							}
							if (npcs[i].npcType == 5462) {
								damage /= 2;
							}
						} else {
							switch (npcs[i].npcType) {
							default:
								damage = 0;
								break;
							}
							c.appendDamage(damage, Hitmark.MISS);
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx0(npcs[i].endGfx);
					}
					c.getCombat().appendVengeanceNPC(damage, i);
				}
			}
		}
	}

	/**
	 * Gets pulled
	 * 
	 * @param i
	 *            the npc to be pulled
	 * @return return true if it can, false otherwise
	 */
	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
		case 2216:
			if (npcs[i].firstAttacker > 0)
				return false;
			break;
		}
		return true;
	}

	/**
	 * Multi attacks
	 * 
	 * @param i
	 *            the npc whom can perform multi attacks
	 * @return true if it can, false otherwise
	 */
	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
			case 7554:
				return npcs[i].attackType == CombatType.SPECIAL || npcs[i].attackType == CombatType.MAGE || 
				npcs[i].attackType == CombatType.RANGE;
		case 6611:
		case 6612:
		case 6618:
		case 6619:
		case 319:
		case 6766:
		case 7617:
			return npcs[i].attackType == CombatType.SPECIAL || npcs[i].attackType == CombatType.MAGE;
			
		case 8609:
			return npcs[i].attackType == CombatType.SPECIAL;

		case 7604:
		case 7605:
		case 7606:
			return npcs[i].attackType == CombatType.SPECIAL;

		case 1046:
			return npcs[i].attackType == CombatType.MAGE
					|| npcs[i].attackType == CombatType.SPECIAL && Misc.random(3) == 0;
		case 6610:
			return npcs[i].attackType == CombatType.MAGE;
		case 2558:
			return true;
		case 2562:
		case 6477:
			if (npcs[i].attackType == CombatType.MAGE)
				return true;
		case 2215:
		case 5462:
			return npcs[i].attackType == CombatType.RANGE;
		case 3162:
			return npcs[i].attackType == CombatType.MAGE;
		case 963:
		case 965:
			return npcs[i].attackType == CombatType.MAGE || npcs[i].attackType == CombatType.RANGE;
		default:
			return false;
		}

	}

	/**
	 * Npc killer id
	 * 
	 * @param npcId
	 *            the npc whom were grabbing the id from
	 * @return the killeId to be printed
	 */
	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int p = 1; p < Config.MAX_PLAYERS; p++) {
			if (PlayerHandler.players[p] != null) {
				if (PlayerHandler.players[p].lastNpcAttacked == npcId) {
					if (PlayerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = PlayerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					PlayerHandler.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}

	/**
	 * Barrows kills
	 * 
	 * @param i
	 *            the barrow brother whom been killed
	 */
	private void killedBarrow(int i) {
		if(npcs[i] == null)
			return;
		Player player = PlayerHandler.players[npcs[i].killedBy];
		if (player != null && player.getBarrows() != null) {
			Optional<Brother> brother = player.getBarrows().getBrother(npcs[i].npcType);
			if (brother.isPresent()) {
				brother.get().handleDeath();
			} else if (Boundary.isIn(npcs[i], Barrows.TUNNEL)) {
				if (player.getBarrows().getKillCount() < 25) {
					player.getBarrows().increaseMonsterKilled();
				}
			}
		}
	}

	/**
	 * Godwars kill
	 * 
	 * @param npc
	 *            the godwars npc whom been killed
	 */
	private void handleGodwarsDeath(NPC npc) {
		Player player = PlayerHandler.players[npc.killedBy];
		if (!GodwarsNPCs.NPCS.containsKey(npc.npcType)) {
			return;
		}
		if (player != null && player.getGodwars() != null) {
			player.getGodwars().increaseKillcount(GodwarsNPCs.NPCS.get(npc.npcType));
			/*
			 * if (Misc.random(60 + 10 * player.getItems().getItemCount(Godwars.KEY_ID,
			 * true)) == 1) { /** Key will not drop if player owns more than 3 keys already
			 *
			 * int key_amount =
			 * player.getDiaryManager().getWildernessDiary().hasCompleted("ELITE") ? 6 : 3;
			 * if (player.getItems().getItemCount(Godwars.KEY_ID, true) > key_amount) {
			 * return; } World.getWorld().getItemHandler().createGroundItem(player, Godwars.KEY_ID,
			 * npc.getX(), npc.getY(), player.heightLevel, 1, player.getIndex()); }
			 */
		}
	}

	/**
	 * Handles kills towards the achievement diaries
	 * 
	 * @param npc
	 *            The npc killed.
	 */
	private void handleDiaryKills(NPC npc) {
		Player player = PlayerHandler.players[npc.killedBy];

		if (player != null) {
			AchievementDiaryKills.kills(player, npc.npcType);
		}
	}

	/**
	 * Handles kills towards daily tasks
	 * 
	 * @param npc
	 *            The npc killed.
	 */
	private void handleDailyKills(NPC npc) {
		Player player = PlayerHandler.players[npc.killedBy];

		if (player != null) {
			DailyTaskKills.kills(player, npc.npcType);
		}
	}

	/**
	 * Tzhaar kill
	 * 
	 * @param player
	 *            the player who killed a tzhaar
	 * @param i
	 *            the tzhaar to be killed
	 */
	private void tzhaarDeathHandler(Player player, int i) {// hold a vit plz
		if (npcs[i] != null) {
			if (player != null) {
				if (player.getFightCave() != null) {
					if (isFightCaveNpc(i) && i != Wave.TZTOK_JAD)
						killedTzhaar(player, i);
				}
			}
		}
	}

	private void infernoDeathHandler(Player player, int i) {// hold a vit plz
		if (npcs[i] != null) {
			if (player != null) {
				if (player.getInfernoMinigame() != null) {
					if (isInfernoNpc(i))
						killedInferno(player, i);
				}
			}
		}
	}

	private void xericDeathHandler(NPC npc) {// hold a vit plz
		if (Boundary.isIn(npc, Boundary.XERIC)) {
			npc.getXeric().killed(npc);
		}
	}

	/**
	 * Killed tzhaar
	 * 
	 * @param player
	 *            the player who killed a tzhaar
	 * @param i
	 *            the tzhaar to be killed
	 */
	private void killedTzhaar(Player player, int i) {
		if (player.getFightCave() != null) {
			player.getFightCave().setKillsRemaining(player.getFightCave().getKillsRemaining() - 1);
			if (player.getFightCave().getKillsRemaining() == 0) {
				player.waveId++;
				player.getFightCave().spawn();
			}
		}
	}

	private void killedInferno(Player player, int i) {
		if (player.getInfernoMinigame() != null) {
			player.getInfernoMinigame().setKillsRemaining(player.getInfernoMinigame().getKillsRemaining() - 1);
			if (player.getInfernoMinigame().getKillsRemaining() == 0) {
				player.infernoWaveId++;
				System.out.println("Inferno Wave ID: " + player.infernoWaveId);
				player.getInfernoMinigame().spawn();
			}
		}
	}

	/**
	 * Jad kill
	 * 
	 * @param i
	 */
	public void handleJadDeath(int i) {
		Player c = PlayerHandler.players[npcs[i].spawnedBy];
		c.getItems().addItem(6570, 1);
		c.getFightCave();
		// c.getDH().sendDialogues(69, 2617);
		c.getFightCave().stop();
		c.waveId = 300;
	}

	/**
	 * Dropping items
	 * 
	 * @param i
	 */
	public void dropItems(int i) {
		Player c = PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			if (c.getTargeted() != null && npcs[i].equals(c.getTargeted())) {
				c.setTargeted(null);
				c.getPA().sendEntityTarget(0, npcs[i]);
			}
			c.getAchievements().kill(npcs[i]);
			PetHandler.receive(c, npcs[i]);
			if (npcs[i].npcType >= 1610 && npcs[i].npcType <= 1612) {
				c.setArenaPoints(c.getArenaPoints() + 1);
				c.refreshQuestTab(4);
				c.sendMessage("@red@You gain 1 point for killing the Mage! You now have " + c.getArenaPoints()
						+ " Arena Points.");
			}
			if (npcs[i].npcType == 5744 || npcs[i].npcType == 5762) {
				c.setShayPoints(c.getShayPoints() + 1);
				c.sendMessage("@red@You gain 1 point for killing the Penance! You now have " + c.getShayPoints()
						+ " Assault Points.");
			}
			if (EventBossHandler.getActiveBoss() != null) {
				if (npcs[i].npcType == EventBossHandler.getActiveBoss().getNpcId() && npcs[i].getHealth().getAmount() <= 0) {
					EventBossHandler.destroyBoss();
				}
			}
			if (WildernessBossHandler.getActiveBoss() != null) {
				if (npcs[i].npcType == WildernessBossHandler.getActiveBoss().getNpcId() && npcs[i].getHealth().getAmount() <= 0) {
					WildernessBossHandler.destroyBoss();
					int randomPkp = Misc.random(15) + 10;
					c.pkp += randomPkp;
				}
			}
			for (MonsterHunt.Npcs hunt : MonsterHunt.Npcs.values()) {
				if (npcs[i].npcType == hunt.getNpcId() && npcs[i].getHealth().getAmount() <= 0) {
					// Player p = PlayerHandler.players[npcs[i].killedBy];
					int randomPkp = Misc.random(15) + 10;
					c.pkp += randomPkp;
					c.refreshQuestTab(0);
					MonsterHunt.setCurrentLocation(null);
					c.sendMessage("Well done! You killed the monster currently being hunted.");
					c.sendMessage("You received: " + randomPkp + " PK Points for killing the monster being hunted.");
				}
			}
			int dropX = npcs[i].getX();
			int dropY = npcs[i].getY();
			int dropHeight = npcs[i].getHeight();
			/*
			 * if (npcs[i].npcType == 494) { if (Boundary.isIn(c, Boundary.KRAKEN_CAVE)) {
			 * dropX = c.absX; dropY = c.absY; } else { dropX = 1770; dropY = 3426; } }
			 */
			if (npcs[i].npcType == 492 || npcs[i].npcType == 494) {
				dropX = c.getX();
				dropY = c.getY();
			}
			if (npcs[i].npcType == 2042 || npcs[i].npcType == 2043 || npcs[i].npcType == 2044
					|| npcs[i].npcType == 6720) {
				dropX = 2268;
				dropY = 3069;
				c.getZulrahEvent().stop();
			}
			if (npcs[i].npcType == 8028) {// VORKATH drop locations
				dropX = Vorkath.lootCoordinates[0];
				dropY = Vorkath.lootCoordinates[1];
			}
			/**
			 * Warriors guild
			 */
			c.getWarriorsGuild().dropDefender(npcs[i].getX(), npcs[i].getY());
			if (AnimatedArmour.isAnimatedArmourNpc(npcs[i].npcType)) {

				if (npcs[i].getX() == 2851 && npcs[i].getY() == 3536) {
					dropX = 2851;
					dropY = 3537;
					AnimatedArmour.dropTokens(c, npcs[i].npcType, npcs[i].getX(), npcs[i].getY() + 1);
				} else if (npcs[i].getX() == 2857 && npcs[i].getY() == 3536) {
					dropX = 2857;
					dropY = 3537;
					AnimatedArmour.dropTokens(c, npcs[i].npcType, npcs[i].getX(), npcs[i].getY() + 1);
				} else {
					AnimatedArmour.dropTokens(c, npcs[i].npcType, npcs[i].getX(), npcs[i].getY());
				}
			}
			
			//ShadowBeast.spawn(npcs[i].npcType);
			
			Location location = new Location(dropX, dropY, dropHeight);
			int amountOfDrops = 1;
			if (Config.DOUBLE_DROPS && c.getLastIncentive() > 0
					&& (System.currentTimeMillis() - c.getLastIncentive()) < TimeUnit.DAYS.toMillis(1)) {
				amountOfDrops++;
			}
			World.getWorld().getDropManager().create(c, npcs[i], location, amountOfDrops);
			if (NPCDefinitions.get(npcs[i].npcType).getNpcCombat() >= 100) {
				c.getNpcDeathTracker().add(NPCDefinitions.get(npcs[i].npcType).getNpcName());
			}
		}
	}

	public void appendKillCount(int i) {
		if(npcs[i] == null)
			return;
		Optional<Player> plrOpt = PlayerHandler.getPlayerByIndex(npcs[i].killedBy);
		plrOpt.ifPresent(c -> {
			int[] kcMonsters = { 122, 49, 2558, 2559, 2560, 2561, 2550, 2551, 2552, 2553, 2562, 2563, 2564, 2565 };
			for (int j : kcMonsters) {
				if (npcs[i].npcType == j) {
					if (c.killCount < 20) {
						// c.killCount++;
						// c.sendMessage("Killcount: " + c.killCount);
					} else {
						// c.sendMessage("You already have 20 kill count");
					}
					break;
				}
			}

		});
	}

	public void appendBossKC(int i) {
		NPC npc = npcs[i];
		if(npc == null || npc.killedBy < 0)
			return;
		Player player = PlayerHandler.players[npcs[i].killedBy];
		if (player == null) {
			return;
		}
		if (npc.getDefinition().getNpcCombat() >= 170) {
			Achievements.increase(player, AchievementType.SLAY_BOSSES, 1);
		}
	}

	/**
	 * Resets players in combat
	 */
	public static NPC getNpc(int npcType) {
		for (NPC npc : npcs)
			if (npc != null && npc.npcType == npcType)
				return npc;
		return null;
	}

	public static NPC spawnNpc(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit,
			int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			System.out.println("Cannot find any available slots to spawn npc into + npchandler @spawnNpc - line 2287");
			return null;
		}
		NPCDefinitions definition = NPCDefinitions.get(npcType);
		NPC newNPC = new NPC(slot, npcType, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
		return newNPC;
	}

	public static NPC spawn(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack,
			int defence, boolean attackPlayer) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			System.out.println("Cannot find any available slots to spawn npc into + npchandler @spawnNpc - line 2287");
			return null;
		}
		NPCDefinitions definition = NPCDefinitions.get(npcType);
		NPC newNPC = new NPC(slot, npcType, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
		return newNPC;
	}

	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null)
				if (PlayerHandler.players[j].underAttackBy2 == i)
					PlayerHandler.players[j].underAttackBy2 = 0;
		}
	}

	public static NPC getNpc(int npcType, int x, int y) {
		for (NPC npc : npcs)
			if (npc != null && npc.npcType == npcType && npc.getX() == x && npc.getY() == y)
				return npc;
		return null;
	}

	public static NPC getNpc(int npcType, int x, int y, int height) {
		for (NPC npc : npcs) {
			if (npc != null && npc.npcType == npcType && npc.getX() == x && npc.getY() == y && npc.getHeight() == height) {
				return npc;
			}
		}
		return null;
	}

	public static NPC getNpc(int npcType, int height) {
		for (NPC npc : npcs) {
			if (npc != null && npc.npcType == npcType && npc.getHeight() == height) {
				return npc;
			}
		}
		return null;
	}

	/**
	 * Npc names
	 **/

	public String getNpcName(int npcId) {
		if (npcId <= -1) {
			return "None";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "None";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	/**
	 * Npc Follow Player
	 **/

	public int GetMove(int Place1, int Place2) {
		if ((Place1 - Place2) == 0) {
			return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean followPlayer(int i) {
		switch (npcs[i].npcType) {
		case 2042:
		case 2043:
		case 2044:
		case 492:
		case 494:
		case 5535:
		case 2892:
		case 2894:
		case 1739:
		case 7413:
		case 1740:
		case 1741:
		case 1742:
		case 7288:
		case 7290:
		case 7292:
		case 7294:
		case 5867:
		case 5868:
		case 5869:
			return false;
		}
		return true;
	}

	public void followPlayer(int i, int playerId) {
		
		if (PlayerHandler.players[playerId] == null) {
			return;
		}
		Player player = PlayerHandler.players[playerId];
		
		if (PlayerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].faceEntity(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}
		if (Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR)) {
			if (!Boundary.isIn(player, Boundary.CORPOREAL_BEAST_LAIR)) {
				npcs[i].killerId = 0;
				return;
			}
		}
		if (Boundary.isIn(npcs[i], Boundary.GODWARS_BOSSROOMS)) {
			if (!Boundary.isIn(player, Boundary.GODWARS_BOSSROOMS)) {
				npcs[i].killerId = 0;
				return;
			}
		}
		if (Boundary.isIn(npcs[i], Zulrah.BOUNDARY)
				&& (npcs[i].npcType >= 2042 && npcs[i].npcType <= 2044 || npcs[i].npcType == 6720)) {
			return;
		}
		
		if (!followPlayer(i)) {
			npcs[i].faceEntity(playerId);
			return;
		}

		npcs[i].faceEntity(playerId);

		if (npcs[i].npcType >= 1739 && npcs[i].npcType <= 1742 || npcs[i].npcType == 7413
				|| npcs[i].npcType >= 7288 && npcs[i].npcType <= 7294) {
			return;
		}
		
		int playerX = PlayerHandler.players[playerId].getX();
		int playerY = PlayerHandler.players[playerId].getY();
		npcs[i].randomWalk = false;
		int followDistance = followDistance(i);
		double distance = ((double) distanceRequired(i)) + (npcs[i].getSize() > 1 ? 0.5 : 0.0);

		if (player.getX() == npcs[i].getX() && player.getY() == npcs[i].getY()) {
			stepAway(i);
			npcs[i].randomWalk = false;
			npcs[i].faceEntity(player.getIndex());
		}

		if (npcs[i].getDistance(playerX, playerY) <= distance)
			return;

		if ((npcs[i].spawnedBy > 0) || (npcs[i].getX() < npcs[i].makeX + followDistance)
				&& (npcs[i].getX() > npcs[i].makeX - followDistance) && (npcs[i].getY() < npcs[i].makeY + followDistance)
				&& (npcs[i].getY() > npcs[i].makeY - followDistance)) {
			if (npcs[i].getHeight() == PlayerHandler.players[playerId].getHeight()) {
				if (PlayerHandler.players[playerId] != null && npcs[i] != null) {
					NPCDumbPathFinder.follow(npcs[i], player);
				}
			}
		} else {
			npcs[i].faceEntity(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

	public void loadSpell(Player player, int i) {
		int chance = 0;
		switch (npcs[i].npcType) {
			case 7931:
			case 7932:
			case 7933:
			case 7934:
			case 7935:
			case 7936:
			case 7937:
			case 7938:
			case 7939:
			case 7940:
				if (player != null) {
					if (npcs[i].getHealth().getAmount() < npcs[i].getHealth().getMaximum() /2) {
						 
						int healchance = Misc.random(100);
//						if (healchance > 75) {
//							npcs[i].gfx0(1196);
//							npcs[i].getHealth().setAmount(npcs[i].getHealth().getAmount() + (npcs[i].getHealth().getMaximum() / 4));
//							Player killer = PlayerHandler.players[npcs[i].underAttackBy];
//							killer.sendMessage("The revenant drains power from within and heals.");
//							return;
//						}
					}
					
					int randomHit = Misc.random(15);
					boolean distance = !player.goodDistance(npcs[i].getX(), npcs[i].getY(), player.getX(), player.getY(), 5);
					if (randomHit <= 5 && !distance) {
						npcs[i].attackType = CombatType.MELEE;
						npcs[i].projectileId = -1;
						npcs[i].endGfx = -1;
					} else if (randomHit >5 && randomHit <= 10 || distance) {
						npcs[i].attackType = CombatType.MAGE;
						npcs[i].projectileId = 1415;
						npcs[i].endGfx =-1;
					} else if (randomHit >10 &&randomHit <= 15 || distance) {
						npcs[i].attackType = CombatType.RANGE;
						npcs[i].projectileId = 1415;
						npcs[i].endGfx =-1;
					}
				}

				break;
		case 1605:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 78;
			break;
		case 5890:

			if (npcs[i].attackType != null) {
				switch (npcs[i].attackType) {
				case MAGE:
					npcs[i].endGfx = -1;
					npcs[i].projectileId = 1274;
					break;
				case MELEE:
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].endGfx = -1;
					npcs[i].projectileId = -1;
					break;
				case SPECIAL:
					npcs[i].attackType = CombatType.SPECIAL;
					groundAttack(npcs[i], player, -1, 1284, -1, 5);
					npcs[i].attackTimer = 8;
					npcs[i].hitDelayTimer = 5;
					npcs[i].endGfx = -1;
					npcs[i].projectileId = -1;
					break;

				default:
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].endGfx = -1;
					npcs[i].projectileId = -1;
					break;

				}
			}
			break;
			/*
			 * sets vorkaths attack details
			 */
		case 8028:// VORKATH
			player.getVorkath().attacking = true;//checks if vorkath is executing a current attack or not
			
			/*
			 * If Vorkath is less than 50% health and venom stage has not happened yet it will trigger
			 */
			if(npcs[i].getHealth().getAmount() <= npcs[i].getHealth().getMaximum()/2 && player.getVorkath().venomStageCompleted == false && player.getVorkath().venomStage == false) {
				player.getVorkath().startVenomStage();//starts venom stage
				return;
			}
			
			/*
			 * while in venom/spitfire stage is shoots these fast projectiles
			 */
			if(player.getVorkath().venomStage == true && player.getVorkath().venomStageCompleted == false) {
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1482;
			npcs[i].attackTimer = 2;
			player.getVorkath().playerX = player.getX();
			player.getVorkath().playerY = player.getY();
			return;
			}
			
			switch (player.getVorkath().attackType) {//vorkaths regular attacks
			case 0://regular fireball no effect
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 5;
				npcs[i].projectileId = 393;

				break;
			case 1://venom attack
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 5;
				npcs[i].projectileId = 394;
				player.getHealth().proposeStatus(HealthStatus.POISON, 15, Optional.of(npcs[i]));
				break;
			case 2://blue fire ball no effect
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 5;
				npcs[i].projectileId = 1479;
				break;
			case 3://kamakaze crab
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 5;
				npcs[i].projectileId = 395;
				break;
			case 4://pink fire ball. turns off prayer
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 5;
				npcs[i].projectileId = 1471;
				break;
			case 5://spike ball that deals damage no matter what
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].hitDelayTimer = 6;
				npcs[i].projectileId = 1477;
				break;
			case 6://big fire ball that insta kills if player does not move
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 6;
				npcs[i].projectileId = 1481;
				player.getVorkath().playerX = player.getX();
				player.getVorkath().playerY = player.getY();
				break;
			}
			break;
			//end of vorkath
			case 7706:
					npcs[i].attackType = CombatType.RANGE;
					npcs[i].hitDelayTimer = 5;
					npcs[i].projectileId = 1375;
					npcs[i].endGfx = -1;

				break;
			/**
			 * Demonic Gorillas attack
			 */
			case 7144:
			case 7145:
			case 7146:
				if (npcs[i].attackType != null)
					switch (npcs[i].attackType) {
						case MAGE:
							npcs[i].attackType = CombatType.MAGE;
							npcs[i].endGfx = 1304;
							npcs[i].projectileId = 1305;
							break;

						case MELEE:
							npcs[i].attackType = CombatType.MELEE;
							npcs[i].endGfx = -1;
							npcs[i].projectileId = -1;
							break;

						case RANGE:
							npcs[i].attackType = CombatType.RANGE;
							npcs[i].endGfx = 1303;
							npcs[i].projectileId = 1302;
							break;

						case SPECIAL:
							npcs[i].attackType = CombatType.SPECIAL;
							groundAttack(npcs[i], player, 304, 303, 305, 5);
							npcs[i].attackTimer = 8;
							npcs[i].hitDelayTimer = 5;
							npcs[i].endGfx = -1;
							npcs[i].projectileId = -1;
							break;

						default:
							break;
					}
				break;

		case 320:
			if (npcs[i].getHealth().getStatus() == HealthStatus.POISON) {
				npcs[i].attackTimer *= 2;
			}
			break;
		case 498:
		case 499:
			npcs[i].projectileId = 642;
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].endGfx = -1;
			break;

		// Zilyana
		case 2205:
			if (Misc.random(3) == 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = -1;
			} else {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = -1;
			}
			break;
		// Growler
		case 2207:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1203;
			break;
		// Bree
		case 2208:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 9;
			break;
		// Saradomin priest
		case 2209:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 76;
			break;
		// Saradomin ranger
		case 2211:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 20;
			npcs[i].endGfx = -1;
			break;
		// Saradomin mage
		case 2212:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 163;
			npcs[i].setProjectileDelay(2);
			break;

		case 3428:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 249;
			npcs[i].endGfx = -1;
			break;
		// Steelwill
		case 2217:
			npcs[i].projectileId = 1217;
			npcs[i].endGfx = 1218;
			npcs[i].attackType = CombatType.MAGE;
			break;
		// Grimspike
		case 2218:
			npcs[i].projectileId = 1193;
			npcs[i].endGfx = -1;
			npcs[i].attackType = CombatType.RANGE;
			break;
		// Bandos ranger
		case 2242:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1197;
			npcs[i].endGfx = -1;
			break;
		// Bandos mage
		case 2244:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 165;
			npcs[i].endGfx = 166;
			break;
		// Saradomin ranger
		case 3160:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 20;
			npcs[i].endGfx = -1;
			break;
		// Zammorak mage
		case 3161:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 156;
			npcs[i].endGfx = 157;
			npcs[i].setProjectileDelay(2);
			break;
		// Armadyl boss
		case 3162:
			if (Misc.random(2) == 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1200;
			} else {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 1199;
			}
			npcs[i].setProjectileDelay(1);
			break;
		// Skree
		case 3163:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1192;
			npcs[i].endGfx = -1;
			break;
		// Geerin
		case 3164:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1192;
			npcs[i].endGfx = -1;
			break;
		// Armadyl ranger
		case 3167:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1192;
			npcs[i].endGfx = -1;
			break;
		// Armadyl mage
		case 3168:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 159;
			npcs[i].endGfx = 160;
			break;
		// Aviansie
		case 3174:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1193;
			npcs[i].endGfx = -1;
			break;

		case 1672: // Ahrim
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 156;
			npcs[i].endGfx = Math.random() <= Barrows.SPECIAL_CHANCE ? 400 : 157;
			break;

		case 1675: // Karil
			npcs[i].projectileId = 27;
			npcs[i].attackType = CombatType.RANGE;
			break;

		case 2042:
			chance = 1;
			if (player != null) {
				if (player.getZulrahEvent().getStage() == 9) {
					chance = 2;
				}
			}
			chance = Misc.random(chance);
			npcs[i].setFacePlayer(true);
			if (chance < 2) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 97;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 3;
				npcs[i].attackTimer = 4;
			} else {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 156;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 3;
				npcs[i].attackTimer = 4;
			}
			break;

		case 1610:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 78;
			break;

		case 1611:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 76;
			break;

		case 1612:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 77;
			break;

		case 2044:
			npcs[i].setFacePlayer(true);
			if (Misc.random(3) > 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1046;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 3;
				npcs[i].attackTimer = 4;
			} else {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 1044;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 3;
				npcs[i].attackTimer = 4;
			}
			break;

		case 983: // Dagannoth mother Air
		case 6373:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 159;
			npcs[i].endGfx = 160;
			break;

		case 984: // Dagannoth mother Water
		case 6375:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 163;
			break;

		case 985: // Dagannoth mother Fire
		case 6376:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 156;
			npcs[i].endGfx = 157;
			break;

		case 6378: // Mother Earth
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 165;
			npcs[i].endGfx = 166;
			break;

		case 987: // Dagannoth mother range
		case 6377:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 996;
			npcs[i].endGfx = -1;
			break;

		case 6371: // Karamel
			if (Misc.random(10) > 6) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].attackTimer = 6;
				npcs[i].endGfx = 369;
				npcs[i].forceChat("Semolina-Go!");
			} else {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].attackTimer = 3;
				npcs[i].endGfx = -1;
			}
			break;

		case 6372: // Dessourt
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			npcs[i].projectileId = 866;
			npcs[i].endGfx = 865;
			if (Misc.random(10) > 6) {
				npcs[i].forceChat("Hssssssssss");
			}
			break;

		case 6368: // Culinaromancer
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].attackTimer = 4;
			break;

		case 2043:
			npcs[i].setFacePlayer(false);
			npcs[i].turnNpc(player.getX(), player.getY());
			npcs[i].targetedLocation = new Location(player.getX(), player.getY(), player.getHeight());
			npcs[i].attackType = CombatType.MELEE;
			npcs[i].attackTimer = 9;
			npcs[i].hitDelayTimer = 6;
			npcs[i].projectileId = -1;
			npcs[i].endGfx = -1;
			break;

		case 6611:
		case 6612:
			chance = Misc.random(100);
			int distanceToVet = player.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			if (distanceToVet < 3) {
				if (chance < 25) {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].attackTimer = 7;
					npcs[i].hitDelayTimer = 4;
					groundSpell(npcs[i], player, 280, 281, "vetion", 4);
				} else if (chance > 90 && System.currentTimeMillis() - npcs[i].lastSpecialAttack > 15_000) {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].attackTimer = 5;
					npcs[i].hitDelayTimer = 2;
					npcs[i].lastSpecialAttack = System.currentTimeMillis();
				} else {
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].attackTimer = 5;
					npcs[i].hitDelayTimer = 2;
				}
			} else {
				if (chance < 71) {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].attackTimer = 7;
					npcs[i].hitDelayTimer = 4;
					groundSpell(npcs[i], player, 280, 281, "vetion", 4);
				} else if (System.currentTimeMillis() - npcs[i].lastSpecialAttack > 15_000) {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].attackTimer = 5;
					npcs[i].hitDelayTimer = 2;
					npcs[i].lastSpecialAttack = System.currentTimeMillis();
				} else {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].attackTimer = 7;
					npcs[i].hitDelayTimer = 4;
					groundSpell(npcs[i], player, 280, 281, "vetion", 4);
				}
			}
			break;

		case 6914: // Lizardman, Lizardman brute
		case 6915:
		case 6916:
		case 6917:
		case 6918:
		case 6919:
			int randomAtt = Misc.random(1);
			if (randomAtt == 1) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1291;
				npcs[i].endGfx = -1;
				if (Misc.random(10) == 5) {
					player.getHealth().proposeStatus(HealthStatus.POISON, 3, Optional.of(npcs[i]));
				}
			} else {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			}
			break;

		/**
		 * Lizardman Shaman<
		 */
		case 6766:
			int randomAttack3 = Misc.random(100);
			if (randomAttack3 > 9 && randomAttack3 < 90) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].attackTimer = 5;
				npcs[i].hitDelayTimer = 2;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			} else if (randomAttack3 > 89) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 3;
				npcs[i].projectileId = 1293;
				npcs[i].endGfx = 1294;

				if (Misc.random(5) == 5) {
					player.getHealth().proposeStatus(HealthStatus.POISON, 10, Optional.of(npcs[i]));
				}
			} else {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].attackTimer = 10;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 8;
				groundSpell(npcs[i], player, -1, 1295, "spawns", 10);
			}
			break;
			

		/**
		 * Crazy Archaeologist
		 */
		case 6618:
			int randomAttack = Misc.random(10);
			String[] shout = { "I'm Bellock - respect me!", "Get off my site!", "No-one messes with Bellock's dig!",
					"These ruins are mine!", "Taste my knowledge!", "You belong in a museum!" };
			String randomShout = (shout[new Random().nextInt(shout.length)]);

			if (player.distanceToPoint(npcs[i].getX(), npcs[i].getY()) < 2) {
				npcs[i].forceChat(randomShout);
				if (randomAttack > 2 && randomAttack < 7) {
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].attackTimer = 5;
					npcs[i].hitDelayTimer = 2;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				} else if (randomAttack > 6) {
					npcs[i].attackType = CombatType.RANGE;
					npcs[i].hitDelayTimer = 3;
					npcs[i].projectileId = 1259;
					npcs[i].endGfx = 140;
				} else {
					npcs[i].forceChat("Rain of knowledge!");
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].hitDelayTimer = 3;
					groundSpell(npcs[i], player, 1260, 131, "archaeologist", 4);
				}
			} else {
				if (randomAttack > 3) {
					npcs[i].forceChat(randomShout);
					npcs[i].attackType = CombatType.RANGE;
					npcs[i].projectileId = 1259;
					npcs[i].endGfx = 140;
				} else {
					npcs[i].forceChat("Rain of knowledge!");
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].hitDelayTimer = 3;
					groundSpell(npcs[i], player, 1260, 131, "archaeologist", 4);
				}
			}
			break;

		/**
		 * Chaos fanatic
		 */
		case 6619:
			int randomAttack2 = Misc.random(10);
			String[] shout_chaos = { "Burn!", "WEUGH!", "Develish Oxen Roll!",
					"All your wilderness are belong to them!", "AhehHeheuhHhahueHuUEehEahAH",
					"I shall call him squidgy and he shall be my squidgy!" };
			String randomShoutChaos = (shout_chaos[new Random().nextInt(shout_chaos.length)]);

			npcs[i].forceChat(randomShoutChaos);

			if (player.distanceToPoint(npcs[i].getX(), npcs[i].getY()) < 2) {
				if (randomAttack2 > 2) {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].hitDelayTimer = 3;
					npcs[i].projectileId = 1044;
					npcs[i].endGfx = 140;
				} else {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].hitDelayTimer = 3;
					groundSpell(npcs[i], player, 1045, 131, "fanatic", 4);
				}
			} else {
				if (randomAttack2 > 3) {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 1044;
					npcs[i].endGfx = 140;
				} else {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].hitDelayTimer = 3;
					groundSpell(npcs[i], player, 1045, 131, "fanatic", 4);
				}
			}
			break;

		case 465:
			boolean distanceToWyvern = player.goodDistance(npcs[i].getX(), npcs[i].getY(), player.getX(), player.getY(), 3);
			int newRandom = Misc.random(10);
			if (newRandom >= 2) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 258;
				npcs[i].endGfx = -1;
			} else if (distanceToWyvern && newRandom == 1) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			} else {
				npcs[i].attackType = CombatType.DRAGON_FIRE;
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			break;
			
			/*
			 * Hydra combat
			 */
			
		case 8609:
			
		npcs[i].attack = 150;
		npcs[i].defence = 450;
		
			
			if (player.hydraAttackCount == 12) {
				player.hydraAttackCount = 0;
				//player.sendMessage("Hydra attack count reset to 0");
			}
			
			if (player.hydraAttackCount <= 6 && player.countUntilPoison != 20) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1663;
				npcs[i].endGfx = -1;
				npcs[i].hitDelayTimer = 4;
				player.hydraAttackCount++;
				player.countUntilPoison++;
				//player.sendMessage("count until poison hits: " + player.countUntilPoison + " [MAGE]");
				//player.sendMessage("hydra attack counter: " + player.hydraAttackCount + "[MAGE]");
			}
			if (player.hydraAttackCount > 6 && player.hydraAttackCount <= 12 && player.countUntilPoison != 20) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 1662;
				npcs[i].endGfx = -1;
				npcs[i].maxHit = 22;
				player.hydraAttackCount++;
				player.countUntilPoison++;
				//player.sendMessage("count until poison hits: " + player.countUntilPoison + " [RANGE]");
				//player.sendMessage("hydra attack counter: " + player.hydraAttackCount + " [RANGE]");
			}
			if (player.countUntilPoison == 20) {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				player.countUntilPoison = 0;
				groundSpell(npcs[i], player, 1660, 1655, "hydra", 4);
				player.getHealth().proposeStatus(HealthStatus.POISON, Misc.random(3, 10), Optional.of(npcs[i]));
				player.sendMessage("You have been poisoned.");
			}
			break;
			
		case 70:
			npcs[i].attackType = CombatType.MELEE;
			npcs[i].getStats().getAttack();
			npcs[i].getStats().getStrength();
			npcs[i].getStats().getStabAttack();
			npcs[i].getStats().getSlashAttack();
			npcs[i].getStats().getDefence();
			npcs[i].maxHit = 6;
			break;
		case 2098:
			npcs[i].attackType = CombatType.MELEE;
			npcs[i].getStats().getAttack();
			npcs[i].getStats().getStrength();
			npcs[i].getStats().getDefence();
			npcs[i].getStats().getStabAttack();
			npcs[i].getStats().getSlashAttack();
			npcs[i].getStats().getCrushAttack();
			npcs[i].maxHit = 4;
			break;
			
		/*
		 * Gets the npc id for the Rune Dragon & addy dragon
		 */
		case 8031:
		case 8030:
			boolean distanceToDragon = player.goodDistance(npcs[i].getX(), npcs[i].getY(), player.getX(), player.getY(), 3);

			int randomAttack1 = Misc.random(12);
			int damage = 0;
			damage = Misc.random(getMaxHit(i));
			int hit = damage;
			int hp = npcs[i].getHealth().getAmount();
			int maxHp = npcs[i].getHealth().getMaximum();
			
			/*
			 * Handles the main/range attack if any number 4 and above is rolled
			 * for randomAttack
			 */
			if (randomAttack1 >= 5 && randomAttack1 <= 7) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 258;
				npcs[i].endGfx = -1;
				npcs[i].maxHit = 18;

				/*
				 * Handles the melee attack if the player is within 1 tile and 1
				 * is rolled for randomAttack
				 */
			} else if (distanceToDragon && randomAttack1 == 1) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				npcs[i].maxHit = 20;

				/*
				 * Handles the magic attack if 2 is rolled for randomAttack
				 */
			} else if (randomAttack1 >= 8 && randomAttack1 <= 10) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				npcs[i].maxHit = 30;
			} 
			
			/*
			 * Handles the first special attack if a 2 is rolled for
			 * randomAttack + adds 100% dmg dealt to the Rune Dragons hp
			 */
			else if (randomAttack1 == 3) {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].projectileId = 1183;
				npcs[i].endGfx = 1363;
				npcs[i].maxHit = 20;
				// TODO REAL Special gfx / special 1 / healing isnt working
				if (hp >= maxHp) {
					return;
				} else if (npcs[i] != null && hp < maxHp) {
					npcs[i].getHealth().increase(hit / 2);
					player.sendMessage("You feel the dragon leeching your life force.");
				}

				/*
				 * Handles the second special attack if a 3 is rolled for the
				 * randomAttack + does dmg per tick if hit 
				 * Projectile hits at the players coordinates TODO
				 * 5x5 square radius for tick dmg TODO
				 */
			} else if (randomAttack1 == 4) {
				
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].singleCombatDelay = 5;
				npcs[i].projectileId = 1198;
				npcs[i].endGfx = 1196;

				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					int ticks = 0;
					@Override
					public void execute(CycleEventContainer container) {
						if (player.disconnected) {
							stop();
							return;
						}
						switch (ticks++) {
						case 0:
							npcs[i].attackType = CombatType.SPECIAL;
							npcs[i].projectileId = -1;
							npcs[i].endGfx = -1;
							npcs[i].maxHit = 7;
							//groundSpell(npcs[i], player, 1198, 1196, "Dragon", 4);
							//player.sendMessage("@red@Lightening attack 1/5");
							break;

						case 1:
							npcs[i].attackType = CombatType.SPECIAL;
							npcs[i].projectileId = -1;
							npcs[i].endGfx = -1;
							npcs[i].maxHit = 7;
							//player.sendMessage("@red@Lightening attack 2/5");
							break;

						case 2:
							npcs[i].attackType = CombatType.SPECIAL;
							npcs[i].projectileId = -1;
							npcs[i].endGfx = -1;
							npcs[i].maxHit = 7;
							//player.sendMessage("@red@Lightening attack 3/5");
							container.stop();
							break;
						}
					}

					@Override
					public void stop() {

					}
				}, 1); //handles delay between ticks
				/*
				 * handles the dragonfire breathe
				 */
			} else {
				npcs[i].attackType = CombatType.DRAGON_FIRE;
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			break;

		case 1046:
		case 1049:
			if (Misc.random(10) > 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].gfx100(194);
				npcs[i].projectileId = 195;
				npcs[i].endGfx = 196;
			} else {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].gfx100(194);
				npcs[i].projectileId = 195;
				npcs[i].endGfx = 576;

			}
			break;
		case 6610:
			if (Misc.random(15) > 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			} else {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].gfx0(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			break;

		case 6609:
			if (player != null) {
				int randomHit = Misc.random(20);
				boolean distance = !player.goodDistance(npcs[i].getX(), npcs[i].getY(), player.getX(), player.getY(), 5);
				if (randomHit < 15 && !distance) {
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				} else if (randomHit >= 15 && randomHit < 20 || distance) {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 395;
					npcs[i].endGfx = 431;
				} else {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				}
			}
			break;
		case 5535:
		case 494:
		case 492:
			npcs[i].attackType = CombatType.MAGE;
			if (Misc.random(5) > 0 && npcs[i].npcType == 494 || npcs[i].npcType == 5535) {
				npcs[i].gfx0(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			} else {
				npcs[i].gfx0(155);
				npcs[i].projectileId = 156;
				npcs[i].endGfx = 157;
			}
			break;
		case 2892:
			npcs[i].projectileId = 94;
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 95;
			break;
		case 2894:
			npcs[i].projectileId = 298;
			npcs[i].attackType = CombatType.RANGE;
			break;
		case 264:
		case 259:
		case 247:
		case 268:
		case 270:
		case 274:
		case 6593:
		case 273:
		case 2919:
		case 2918:
			int random2 = Misc.random(2);
			if (random2 == 0) {
				npcs[i].projectileId = 393;
				npcs[i].endGfx = 430;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			} else {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			}
			break;
			
			/*
			 * brutal black dragons
			 */
		case 7275:
			int bbdrandom2 = Misc.random(5);
			int distanceToBrutal = player.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			
			if (bbdrandom2 <= 4) {
				npcs[i].projectileId = 396;
				npcs[i].endGfx = 428;
				npcs[i].attackType = CombatType.MAGE;
			} else if (distanceToBrutal <= 4) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			} else {
				npcs[i].projectileId = 393;
				npcs[i].endGfx = 430;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			}
			break;
			/*
			 * brutal black dragons
			 */
		case 7274:
			int bReddrandom2 = Misc.random(5);
			int distanceToBrutalR = player.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			
			if (bReddrandom2 <= 4) {
				npcs[i].projectileId = 396; 
				npcs[i].endGfx = 428;
				npcs[i].attackType = CombatType.MAGE;
			} else if (distanceToBrutalR <= 4) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			} else {
				npcs[i].projectileId = 393;
				npcs[i].endGfx = 430;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			}
			break;
			/*
			 * brutal blue dragons
			 */
		case 7273:
			int bblueDrandom2 = Misc.random(5);
			int distanceToBrutalB = player.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			
			if (bblueDrandom2 <= 4) {
				npcs[i].projectileId = 396;
				npcs[i].endGfx = 428;
				npcs[i].attackType = CombatType.MAGE;
			} else if (distanceToBrutalB <= 4) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			} else {
				npcs[i].projectileId = 393;
				npcs[i].endGfx = 430;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			}
			break;
		case 239:
			int random = Misc.random(100);
			int distance = player.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			if (random >= 60 && random < 65) {
				npcs[i].projectileId = 394; // green
				npcs[i].endGfx = 429;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			} else if (random >= 65 && random < 75) {
				npcs[i].projectileId = 395; // white
				npcs[i].endGfx = 431;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			} else if (random >= 75 && random < 80) {
				npcs[i].projectileId = 396; // blue
				npcs[i].endGfx = 428;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			} else if (random >= 80 && distance <= 4) {
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = CombatType.MELEE;
			} else {
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = CombatType.DRAGON_FIRE;
			}
			break;
		// arma npcs
		case 2561:
			npcs[i].attackType = CombatType.MELEE;
			break;
		case 2560:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1190;
			break;
		case 2559:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1203;
			break;
		case 2558:
			random = Misc.random(1);
			npcs[i].attackType = CombatType.getRandom(CombatType.RANGE, CombatType.MAGE);
			if (npcs[i].attackType == CombatType.RANGE) {
				npcs[i].projectileId = 1197;
			} else {
				npcs[i].projectileId = 1198;
			}
			break;
		// sara npcs
		case 2562: // sara
			random = Misc.random(1);
			if (random == 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].endGfx = 1224;
				npcs[i].projectileId = -1;
			} else if (random == 1)
				npcs[i].attackType = CombatType.MELEE;
			break;
		case 2563: // star
			npcs[i].attackType = CombatType.MELEE;
			break;
		case 2564: // growler
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1203;
			break;
		case 2565: // bree
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 9;
			break;
		case 2551:
			npcs[i].attackType = CombatType.MELEE;
			break;
		case 2552:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1203;
			break;
		case 2553:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 1206;
			break;
		case 2025:
			npcs[i].attackType = CombatType.MAGE;
			int r = Misc.random(3);
			if (r == 0) {
				npcs[i].gfx100(158);
				npcs[i].projectileId = 159;
				npcs[i].endGfx = 160;
			}
			if (r == 1) {
				npcs[i].gfx100(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			if (r == 2) {
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			if (r == 3) {
				npcs[i].gfx100(155);
				npcs[i].projectileId = 156;
			}
			break;
		case 2265:// supreme
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 298;
			break;

		case 2266:// prime
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 477;
			break;

		case 2028:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 27;
			break;

		case 2054:
			int r2 = Misc.random(1);
			if (r2 == 0) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
			} else {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
			}
			break;
		case 6257:// saradomin strike
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 76;
			break;
		case 6221:// zamorak strike
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].endGfx = 78;
			break;
		case 6231:// arma
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1199;
			break;


			case 7692:
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 1382;
				break;
			case 7693:
				int r3 = Misc.random(1);
				if (r3 == 0) {
					npcs[i].attackType = CombatType.MELEE;
				} else {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 1380;

				}
				break;
			case 7694:
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1381;
				break;
			case 7702:
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].projectileId = 1378;
				break;
			case 7699:
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 449;
				break;
			case 7708:
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 660;
				break;



			// sara npcs
		case 3129:
			random = Misc.random(15);
			if (random > 0 && random < 7) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
			} else if (random >= 7) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1211;
			} else if (random == 0) {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].projectileId = -1;
			}
			break;
		case 1047:// cave horror
			random = Misc.random(3);
			if (random == 0 || random == 1) {
				npcs[i].attackType = CombatType.MELEE;
			} else {
				npcs[i].attackType = CombatType.MAGE;
			}
			break;
		case 3127:
		case 7700:
			int r23 = 0;
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), PlayerHandler.players[npcs[i].spawnedBy].getX(),
					PlayerHandler.players[npcs[i].spawnedBy].getY(), 1)) {
				r23 = Misc.random(2);
			} else {
				r23 = Misc.random(1);
			}
			if (r23 == 0) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 448;
				npcs[i].endGfx = 157;
				npcs[i].hitDelayTimer = 6;
				npcs[i].attackTimer = 9;
			} else if (r23 == 1) {
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].endGfx = 451;
				npcs[i].projectileId = -1;
				npcs[i].hitDelayTimer = 6;
				npcs[i].attackTimer = 9;
			} else if (r23 == 2) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			}
			break;
		case 3125:
			if (player.distanceToPoint(npcs[i].getX(), npcs[i].getY()) > 2) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 445;
				npcs[i].endGfx = 446;
			} else {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
			}
			break;

		case 3121:
		case 2167:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].projectileId = 443;
			break;
		case 1678:
		case 1679:
		case 1680:
		case 1683:
		case 1684:
		case 1685:
			npcs[i].attackType = CombatType.MELEE;
			npcs[i].attackTimer = 4;
			break;

		case 319:
			int corpRandom = Misc.random(15);
			if (corpRandom >= 12) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 3;
				npcs[i].projectileId = Misc.random(1) == 0 ? 316 : 314;
				npcs[i].endGfx = -1;
			}
			if (corpRandom >= 3 && corpRandom <= 11) {
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].hitDelayTimer = 2;
				npcs[i].endGfx = -1;
			}
			if (corpRandom <= 2) {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].hitDelayTimer = 3;
				groundSpell(npcs[i], player, 315, 317, "corp", 4);
			}
			break;

		/**
		 * Kalphite Queen Stage One
		 */
		case 963:
		case 965:
			int kqRandom = Misc.random(2);
			switch (kqRandom) {
			case 0:
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].hitDelayTimer = 3;
				npcs[i].projectileId = 280;
				npcs[i].endGfx = 281;
				break;
			case 1:
				npcs[i].attackType = CombatType.RANGE;
				npcs[i].hitDelayTimer = 3;
				npcs[i].projectileId = 473;
				npcs[i].endGfx = 281;
				break;
			case 2:
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				break;
			}
			break;
		/**
		 * Tekton
		 */
		case 7544:
			if (Objects.equals(tektonAttack, "MELEE")) {
				npcs[i].attackType = CombatType.MELEE;
			} else if (Objects.equals(tektonAttack, "SPECIAL")) {
				npcs[i].attackType = CombatType.SPECIAL;
				Tekton.tektonSpecial(player);
				tektonAttack = "MELEE";
				npcs[i].hitDelayTimer = 4;
				npcs[i].attackTimer = 8;
			}
			break;
		/**
		 * Glod
		 */
		case 5129:
			if (Objects.equals(glodAttack, "MELEE")) {
				npcs[i].attackType = CombatType.MELEE;
			} else if (Objects.equals(glodAttack, "SPECIAL")) {
				npcs[i].attackType = CombatType.SPECIAL;
				Glod.glodSpecial(player);
				glodAttack = "MELEE";
				npcs[i].hitDelayTimer = 4;
				npcs[i].attackTimer = 8;
			}
			break;
			
			/*
			 * Tarn Combat
			 */
		/**
		 * Ice Queen
		 */
		case 4922:
			if (Objects.equals(queenAttack, "MAGIC")) {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = 367;
				npcs[i].hitDelayTimer = 5;
			} else if (Objects.equals(queenAttack, "SPECIAL")) {
				npcs[i].attackType = CombatType.SPECIAL;
				IceQueen.queenSpecial(player);
				queenAttack = "MAGIC";
				npcs[i].hitDelayTimer = 4;
				npcs[i].attackTimer = 8;
			}
			break;

		/**
		 * Tekton magers
		 */
		case 7617:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1348;
			npcs[i].endGfx = 1345;
			npcs[i].hitDelayTimer = 5;
			npcs[i].attackTimer = 15;
			break;
			case 7529:
				if (Misc.random(10) == 5) {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = 1348;
					npcs[i].endGfx = 1345;
					npcs[i].hitDelayTimer = 3;
				} else {
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 1348;
					npcs[i].endGfx = 1345;
					npcs[i].hitDelayTimer = 3;
				}
				break;

			case 7566:
				if (Misc.random(10) <= 8) {
				  npcs[i].attackType = CombatType.MAGE;
				  npcs[i].projectileId = 1327;
				  npcs[i].endGfx = 1328;
				  npcs[i].hitDelayTimer = 3;
				  npcs[i].maxHit = 35;
				} else {
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = 1329;
					npcs[i].endGfx = 1330;
					npcs[i].maxHit = 48;
					npcs[i].hitDelayTimer = 3;
					break;
				}
				break;			
			
			case 7554://great olm
				int randomStyle1 = Misc.random(12);

				switch (randomStyle1) {
				case 0://mage
				case 1:
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 1339;
					npcs[i].endGfx = 1353;
					npcs[i].maxHit = 30;
					npcs[i].hitDelayTimer = 2;
					break;
					
				case 2://range
				case 3:
				case 4:
				case 5:
				case 6:
					npcs[i].attackType = CombatType.RANGE;
					npcs[i].projectileId = 1340;
					npcs[i].endGfx = 1353;
					npcs[i].maxHit = 30;
					npcs[i].hitDelayTimer = 2;
					break;
					
				case 7://acid
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = 1354;
					npcs[i].endGfx = 1358;
					npcs[i].maxHit = 40;
					npcs[i].hitDelayTimer = 2;
					player.getHealth().proposeStatus(HealthStatus.POISON, Misc.random(3, 10), Optional.of(npcs[i]));
					player.sendMessage("You have been poisoned by Olm's acid attack!");
					break;
					
				case 8://dragon fire
				case 9:
					npcs[i].attackType = CombatType.DRAGON_FIRE;
					npcs[i].projectileId = 393;
					npcs[i].endGfx = 430;
					npcs[i].maxHit = 52;
					npcs[i].hitDelayTimer = 3;
					break;
					
				case 10://burn
				case 11:
					npcs[i].attackType = CombatType.SPECIAL;
					npcs[i].projectileId = 1349;
					npcs[i].endGfx = -1;
					npcs[i].maxHit = 40;
					npcs[i].hitDelayTimer = 2;
					break;
				}
				break;
				
		case 7604:
		case 7605:
		case 7606:
			if (Misc.random(10) == 5) {
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].forceChat("RAA!");
				npcs[i].projectileId = 1348;
				npcs[i].endGfx = 1345;
				npcs[i].hitDelayTimer = 3;
			} else {
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1348;
				npcs[i].endGfx = 1345;
				npcs[i].hitDelayTimer = 3;
			}
			break;

		/**
		 * Cerberus
		 */
		case 5862:
			if (Objects.equals(player.CERBERUS_ATTACK_TYPE, "GROUND_ATTACK")) {
				startAnimation(4492, i);
				npcs[i].forceChat("Grrrrrrrrrrrrrr");
				npcs[i].attackType = CombatType.SPECIAL;
				npcs[i].hitDelayTimer = 4;
				groundSpell(npcs[i], player, -1, 1246, "cerberus", 4);
				player.CERBERUS_ATTACK_TYPE = "MELEE";
			}
			if (Objects.equals(player.CERBERUS_ATTACK_TYPE, "GHOST_ATTACK")) {
				startAnimation(4494, i);
				// npcs[i].forceChat("Aaarrrooooooo");
				player.CERBERUS_ATTACK_TYPE = "MELEE";
			}
			if (Objects.equals(player.CERBERUS_ATTACK_TYPE, "FIRST_ATTACK")) {
				startAnimation(4493, i);
				npcs[i].attackTimer = 5;
				player.CERBERUS_ATTACK_TYPE = "MELEE";
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					int ticks = 0;

					@Override
					public void execute(CycleEventContainer container) {
						if (player.disconnected) {
							stop();
							return;
						}
						switch (ticks++) {
						case 0:
							npcs[i].attackType = CombatType.MELEE;
							npcs[i].projectileId = -1;
							npcs[i].endGfx = -1;
							break;

						case 2:
							npcs[i].attackType = CombatType.RANGE;
							npcs[i].projectileId = 1245;
							npcs[i].endGfx = 1244;
							break;

						case 4:
							npcs[i].attackType = CombatType.MAGE;
							npcs[i].projectileId = 1242;
							npcs[i].endGfx = 1243;
							container.stop();
							break;
						}
						// System.out.println("Ticks - cerb " + ticks);
					}

					@Override
					public void stop() {

					}
				}, 2);
			} else {
				int randomStyle = Misc.random(2);

				switch (randomStyle) {
				case 0:
					npcs[i].attackType = CombatType.MELEE;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					break;

				case 1:
					npcs[i].attackType = CombatType.RANGE;
					npcs[i].projectileId = 1245;
					npcs[i].endGfx = 1244;
					break;

				case 2:
					npcs[i].attackType = CombatType.MAGE;
					npcs[i].projectileId = 1242;
					npcs[i].endGfx = 1243;
					break;
				}
			}
			break;

		case Skotizo.AWAKENED_ALTAR_NORTH:
		case Skotizo.AWAKENED_ALTAR_SOUTH:
		case Skotizo.AWAKENED_ALTAR_WEST:
		case Skotizo.AWAKENED_ALTAR_EAST:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].projectileId = 1242;
			npcs[i].endGfx = 1243;
			break;

		case Skotizo.SKOTIZO_ID:
			int randomStyle;
			if (player.getSkotizo().firstHit) {
				randomStyle = 1;
				player.getSkotizo().firstHit = false;
			} else {
				randomStyle = Misc.random(1);
			}
			switch (randomStyle) {
			case 0:
				npcs[i].attackType = CombatType.MELEE;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				break;

			case 1:
				npcs[i].attackType = CombatType.MAGE;
				npcs[i].projectileId = 1242;
				npcs[i].endGfx = 1243;
				break;
			}
			break;

		case 5867:
			npcs[i].attackType = CombatType.RANGE;
			npcs[i].hitDelayTimer = 3;
			npcs[i].projectileId = 1230;
			npcs[i].attackTimer = 15;
			break;

		case 5868:
			npcs[i].attackType = CombatType.MAGE;
			npcs[i].hitDelayTimer = 3;
			npcs[i].projectileId = 127;
			npcs[i].attackTimer = 15;
			break;

		case 5869:
			npcs[i].attackType = CombatType.MELEE;
			npcs[i].hitDelayTimer = 3;
			npcs[i].projectileId = 1248;
			npcs[i].attackTimer = 15;
			break;
		}
	}

	/**
	 * Distanced required to attack
	 **/
	public int distanceRequired(int i) {
		switch (npcs[i].npcType) {

		case Skotizo.SKOTIZO_ID:
			return npcs[i].attackType == CombatType.MAGE ? 15 : 2;
		case 7706:
		return 80;
		case 8028:
			return 10;
			
			/* Hydra */
		case 8609:
			return 6;
			
			/*xeric monsters*/
			case 7576://crabs
			case 7577:
			case 7578:
			case 7579:
				return npcs[i].attackType == CombatType.MELEE ? 1 : 2;
			case 7586://ice fiend
				return npcs[i].attackType == CombatType.MELEE ? 1 : 2;
			case 7585://ice demon
				return npcs[i].attackType == CombatType.MELEE ? 1 : 2;
		/**
		 * Cerberus
		 */
		case 5862:
		case 6766:
		case 7144:
		case 7145:
		case 7146:
			return npcs[i].attackType == CombatType.MELEE ? 1 : 7;

		case 5867:
		case 5868:
		case 5869:
		case 7617:
			return 30;

		case 7559:
		case 7560:
		return 10;
		
		case 5001:
			if (npcs[i].attackType == CombatType.MAGE) {
			return 6;
			} else 
				return 3;
		case 6477: //tarn
		case 5462:
			return 10;
			
		case 7604: // Skeletal mystic
		case 7605: // Skeletal mystic
		case 7606: // Skeletal mystic
		case 4922:
			return 8;

		case 319:
			return npcs[i].attackType == CombatType.MAGE ? 10 : 7;

		case 5890:
		case 7544:
		case 5129:
			return npcs[i].attackType == CombatType.MELEE ? 3 : 7;

		case 6914: // Lizardman, Lizardman brute
		case 6915:
		case 6916:
		case 6917:
		case 6918:
		case 6919:
			return npcs[i].attackType == CombatType.MAGE ? 4 : 1;

		case 6618:
			return npcs[i].attackType == CombatType.RANGE || npcs[i].attackType == CombatType.SPECIAL ? 4 : 1;

		case 465:
			return npcs[i].attackType == CombatType.RANGE || npcs[i].attackType == CombatType.SPECIAL ? 6 : 2;

		case 6615: // Scorpia
		case 6619: // Chaos fanatic
			return 4;

		case 6367:
		case 6368:
		case 6369:
		case 6371:
		case 6372:
		case 6373:
		case 6374:
		case 6375:
		case 6376:
		case 6377:
		case 6378:
			if (npcs[i].attackType == CombatType.MAGE || npcs[i].attackType == CombatType.RANGE)
				return 8;
			else
				return 4;

		case 6370:
			return 10;

		case 498:
		case 499:
			return 6;
		case 1672: // Ahrim
		case 1675: // Karil
		case 983: // Dagannoth mother
		case 984:
		case 985:
		case 987:
			return 8;

		case 986:
		case 988:
			return 3;

		case 2209:
		case 2211:
		case 2212:

		case 2242:
		case 2244:
		case 3160:
		case 3161:
		case 3162:
		case 3167:
		case 3168:
		case 3174:
			return 4;

		case 1610:
		case 1611:
		case 1612:
			return 4;

		case 2205:
			return npcs[i].attackType == CombatType.MAGE ? 3 : 2;
		case Zulrah.SNAKELING:
			return 2;
		case 2208:
			return 8;
		case 2217:
			return 9;
		case 2218:
			return 6;
		case 2042:
		case 2043:
		case 2044:
			case 7554:
			return 25;
		case 3163:
			return 8;
		case 3164:
		case 1049:
			return 5;
		case 6611:
		case 6612:
			return npcs[i].attackType == CombatType.SPECIAL || npcs[i].attackType == CombatType.MAGE ? 12 : 3;
		case 1046:
		case 6610:
			return 8;
		case 494:
		case 492:
		case 6609:
		case 5535:
			return 10;
		case 2025:
		case 2028:
			return 6;
		case 2562:
		case 3131:
		case 3132:
		case 3130:
		case 2206:
		case 2207:
		case 2267:
			return 2;
		case 2054:// chaos ele
		case 3125:
		case 3121:
		case 2167:
		case 3127:
			case 7700:
			return 8;
		case 3129:
			return 5;
		case 2265:// dag kings
		case 2266:
			return 4;
		case 239:
		case 8030:
		case 8031:
			return npcs[i].attackType == CombatType.DRAGON_FIRE ? 18 : 4;
		case 2552:
		case 2553:
		case 2556:
		case 2557:
		case 2558:
		case 2559:
		case 2560:
		case 2564:
		case 2565:
			return 9;
		// things around dags
		case 2892:
		case 2894:
			return 10;
		default:
			return 1;
		}
	}

	public int followDistance(int i) {
		if (Boundary.isIn(npcs[i], Boundary.GODWARS_BOSSROOMS) || Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR) || Boundary.isIn(npcs[i], Boundary.FIGHT_CAVE)
				|| Boundary.isIn(npcs[i], Boundary.CERBERUS_BOSSROOMS)) {
			return 20;
		}
		if (Boundary.isIn(npcs[i], Boundary.XERIC)) {
			return 128;
		}
		switch (npcs[i].npcType) {
		case 2045:
			return 20;
		case 5000://anti santa
			return 30;
		case 6615:
			return 30;
			/* Hydra */
		case 8609:
			return 10;
		case 1739:
		case 1740:
		case 1741:
		case 1742:
		case 7413:
		case 7288:
		case 7290:
		case 7292:
		case 7294:
			return -1;
		case 1678: // Barrows tunnel NPCs
		case 1679:
		case 1680:
		case 1683:
		case 1684:
		case 1685:
		case 484:
		case 7276:
		case 135:
		case 6914: // Lizardman, Lizardman brute
		case 6915:
		case 6916:
		case 6917:
		case 6918:
		case 6919:
			return 4;
		case 2209:
		case 2211:
		case 2212:
		case 2233:
		case 2234:
		case 2235:
		case 2237:
		case 2241:
		case 2242:
		case 2243:
		case 2244:
		case 2245:
		case 3133:
		case 3134:
		case 3135:
		case 3137:
		case 3138:
		case 3139:
		case 3140:
		case 3141:
		case 3159:
		case 3160:
		case 3161:
		case 3166:
		case 3167:
		case 3168:
			return 3;
		case 2205:
		case 2206:
		case 2207:
		case 2208:
		case 2216:
		case 2217:
		case 2218:
		case 3129:
		case 3130:
		case 3131:
		case 3132:
		case 3162:
		case 3163:
		case 3164:
		case 3165:
			return 20;
		case 239:
		case 8031:
		case 8030:
			return 40;
		case 6611:
		case 6612:
		case 963:
		case 965:
		case 7544:
			return 15;
		case 5129:
		case 4922:
			return 9;
		case 2551:
		case 2562:
		case 2563:
			return 8;
		case 2054:
		case 5890:
		case 5916:
			return 10;
		case 2265:
		case 2266:
		case 2267:
			return 7;
		default:
			return 7;

		}

	}

	public int getProjectileSpeed(int i) {
		switch (npcs[i].npcType) {
		case 8028:// VORKATH regular attack speed
			return 100;
		case 498:
			return 120;
		case 499:
			return 105;
		case 2265:
		case 2266:
		case 2054:
			return 85;

		case 3127:
			case 7700:
		case 7617:
			return 130;
		case 1672:
		case 239:
		case 8031:
		case 8030:
			return 90;
		case 5001:
		case 6477:
			return 90;
			
		case 8609://Hydra
			return 110;

		case 2025:
			return 85;

		case 2028:
			return 80;
		case 3162:
			return 100;

		default:
			return 85;
		}
	}

	/**
	 * Npcs who ignores projectile clipping to ensure no safespots
	 * 
	 * @param i
	 * @return true is the npc is using range, mage or special
	 */
	public static boolean ignoresProjectile(int i) {
		if (npcs[i] == null)
			return false;
		switch (npcs[i].npcType) {
		case 6611:
		case 6612:
		case 319:
		case 6618:
		case 6766:
		case 5862:
		case 963:
		case 965:
		case 7706:
		case 7144:
		case 8028:
		case 7145:
		case 7146:
		case 5890:
		case 8609:
		case 7566:
		case 7563:
		case 7585:
		case 7544:
		case 7554:
			return true;
		}
		return false;
	}

	/**
	 * NPC Attacking Player
	 **/
	public void attackPlayer(Player c, int i) {
		NPC npc = npcs[i];
		CombatScript script = npc.getCombatScript();
		if (script != null) {
			if (script.handleAttack(npc, c))
				return;
		}
		if (npcs[i].lastX != npcs[i].getX() || npcs[i].lastY != npcs[i].getY()) {
			return;
		}
		if (c.isInvisible()) {
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != c.getIndex()) {
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				return;
			}
			if (!npcs[i].inMulti() && ((c.underAttackBy > 0 && c.underAttackBy2 != i)
					|| (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				return;
			}
			if (npcs[i].getHeight() != c.getHeight()) {
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				return;
			}
			switch (npcs[i].npcType) {
			case 8062:// vorkath crab when gets close and attacks player insta kills player
				npcs[i].gfx100(427);
				c.appendDamage(150, Hitmark.HIT);
				npcs[i].isDead = true;
				break;
			case 1739:
			case 7413:
			case 1740:
			case 1741:
			case 1742:
			case 6600:
			case 7288:
			case 7290:
			case 7292:
			case 7294:
			case 6602:
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				break;
			case 6477:
				Tarn.getCombatMode();
				break;

			// case 5862:
			// if (Cerberus.ATTACK_TYPE == "FIRST_ATTACK") {
			// loadSpell(c, i);
			//
			// Cerberus.ATTACK_TYPE = "MELEE";
			// }
			// break;
			}
			if (Boundary.isIn(npcs[i], Boundary.GODWARS_BOSSROOMS) ^ Boundary.isIn(c, Boundary.GODWARS_BOSSROOMS)) {
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				return;
			}
			if (Boundary.isIn(npcs[i], Boundary.CORPOREAL_BEAST_LAIR)
					^ Boundary.isIn(c, Boundary.CORPOREAL_BEAST_LAIR)) {
				npcs[i].killerId = 0;
				npcs[i].faceEntity(0);
				npcs[i].underAttack = false;
				npcs[i].randomWalk = true;
				return;
			}
			npcs[i].faceEntity(c.getIndex());

			int distance = c.distanceToPoint(npcs[i].getX(), npcs[i].getY());
			boolean hasDistance = npcs[i].getDistance(c.getX(), c.getY()) <= ((double) distanceRequired(i)) + (npcs[i].getSize() > 1 ? 0.5 : 0.0);

			/**
			 * Npc's who will ignore projectile clipping
			 */
			if (ignoresProjectile(i)) {
				if (distance < 10) {
					c.getPA().removeAllWindows();
					npcs[i].oldIndex = c.getIndex();
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					loadSpell(c, i);
					startAnimation(getAttackEmote(i), i);
				}
			}

			if (hasDistance) {
				if (projectileClipping) {
					if (npcs[i].attackType == CombatType.MAGE || npcs[i].attackType == CombatType.RANGE) {
						int x1 = npcs[i].getX();
						int y1 = npcs[i].getY();
						int z = npcs[i].getHeight();
						if (!PathChecker.isProjectilePathClear(x1, y1, z, c.getX(), c.getY())
								&& !PathChecker.isProjectilePathClear(c.getX(), c.getY(), z, x1, y1)) {
							return;
						}
					}
				}

				if (c.respawnTimer <= 0) {
					/**
					 * Npcs who follow projectile clipping
					 */
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					if (npcs[i].attackType == null) {
						npcs[i].attackType = CombatType.MELEE;
					}
					loadSpell(c, i);
					npcs[i].oldIndex = c.getIndex();
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
					if (npcs[i].attackType == CombatType.DRAGON_FIRE) {
						npcs[i].hitDelayTimer += 2;
						c.getCombat().absorbDragonfireDamage();
					}
					if (multiAttacks(i)) {
						startAnimation(getAttackEmote(i), i);
						multiAttackGfx(i, npcs[i].projectileId);
						npcs[i].oldIndex = c.getIndex();
						return;
					}
					if (npcs[i].projectileId > 0) {
						if(npcs[i].npcType == 7706) {
							NPC glyph = getNpc(7707,c.getHeight());
							if (glyph == null){
								return;
							}

							if (c.getInferno().isBehindGlyph()) {
								c.getInferno().behindGlyph=true;
								//c.sendMessage("is behind glyph");
							}
						}
						int nX = NPCHandler.npcs[i].getX() + offset(i);
						int nY = NPCHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						//c.sendMessage(pX + " "+ pY);
						int offX = (nX - pX) * -1;
						int offY = (nY - pY) * -1;
						int centerX = nX + npcs[i].getSize() / 2;
						int centerY = nY + npcs[i].getSize() / 2;
						if (npcs[i].npcType == 8028 && c.getVorkath().attackType == 6) {// VORKATH BIG FIRE ATTACK
							int endCoordX = c.getVorkath().playerX;
							int endCoordY = c.getVorkath().playerY;
							int targetY = (nX - (int) endCoordX) * -1;
							int targetX = (nY - (int) endCoordY) * -1;
							c.getPA().createPlayersProjectile2(centerX, centerY, targetX, targetY, 50, 150,
									npcs[i].projectileId,
									getProjectileStartHeight(npcs[i].npcType, npcs[i].projectileId),
									getProjectileEndHeight(npcs[i].npcType, npcs[i].projectileId), -1,
									65, npcs[i].getProjectileDelay()-100);
							return;
						}
							if (npcs[i].npcType == 8028 &&c.getVorkath().attackType == 7) {// VORKATH quick FIRE ATTACK
								int endCoordX = c.getVorkath().playerX;
								int endCoordY = c.getVorkath().playerY;
								int targetY = (nX - (int) endCoordX) * -1;
								int targetX = (nY - (int) endCoordY) * -1;
								c.getPA().createPlayersProjectile2(centerX, centerY, targetX, targetY, 50, 80,
										npcs[i].projectileId,
										getProjectileStartHeight(npcs[i].npcType, npcs[i].projectileId),
										getProjectileEndHeight(npcs[i].npcType, npcs[i].projectileId), -1,
										65, npcs[i].getProjectileDelay());
						
								return;
						}
							c.getPA().createPlayersProjectile(centerX, centerY, offX, offY, 50, getProjectileSpeed(i),
									npcs[i].projectileId, getProjectileStartHeight(npcs[i].npcType, npcs[i].projectileId),
									getProjectileEndHeight(npcs[i].npcType, npcs[i].projectileId), -c.getIndex() - 1, 65,
									npcs[i].getProjectileDelay());
					}
					if (c.teleporting) {
						c.startAnimation(65535);
						c.teleporting = false;
						c.isRunning = false;
						c.gfx0(-1);
						c.startAnimation(-1);
					}
				}
			}
		}
	}

	public int offset(int i) {
		switch (npcs[i].npcType) {
		case 2044:
			return 0;
		case 6611:
		case 6612:
			return 3;
		case 6610:
			return 2;
		case 239:
		case 8031:
		case 8030:
		case 5001:
			return 2;
		case 2265:
		case 2266:
			return 1;
		case 3127:
		case 3125:
			case 7700:
			return 1;
		}
		return 0;
	}

	public boolean retaliates(int npcType) {
		return npcType < 3777 || npcType > 3780;
	}

	private boolean prayerProtectionIgnored(int npcId) {
		switch (npcs[npcId].npcType) {
		case 1610:
		case 1611:
		case 1612:
		case 2205:
		case 2206:
		case 2207:
		case 2208:
		case 2215:
		case 5462:
		case 2216:
		case 2217:
		case 2218:
		case 3129:
		case 3130:
		case 3131:
		case 3132:
		case 3162:
		case 3163:
		case 3164:
		case 3165:
		case 7617:
			return true;
		case 1672:
			return false;
		case 6611:
		case 6612:
		case 6609:
			return npcs[npcId].attackType == CombatType.MAGE || npcs[npcId].attackType == CombatType.SPECIAL;
		case 7706:
			return npcs[npcId].attackType == CombatType.RANGE;
		case 465:
			return npcs[npcId].attackType == CombatType.DRAGON_FIRE;
		}
		return false;
	}

	public void handleSpecialEffects(Player c, int i, int damage) {
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.getSkills().getLevel(Skill.PRAYER) > 0) {
						c.getSkills().decreaseLevelOrMin(1, 0, Skill.PRAYER);
						c.getPA().refreshSkill(5);
					}
				}
			}
		}

	}

	public static void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}

	public NPC[] getNPCs() {
		return npcs;
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance)
				&& (objectY - playerY <= distance && objectY - playerY >= -distance));
	}

	public int getMaxHit(int i) {
		if (npcs[i] == null) {
			return 0;
		}
		if (Boundary.isIn(npcs[i], Boundary.XERIC)) {
			return XericWaveConstants.getMax(npcs[i].npcType);
		}
		switch (npcs[i].npcType) {
		case 7706:
		return 120;
		case 3021: // KBD Spiders
			return 7;
		case Skotizo.SKOTIZO_ID:
			return 38;
		case Skotizo.AWAKENED_ALTAR_NORTH:
		case Skotizo.AWAKENED_ALTAR_SOUTH:
		case Skotizo.AWAKENED_ALTAR_WEST:
		case Skotizo.AWAKENED_ALTAR_EAST:
			return 15;
		case Skotizo.REANIMATED_DEMON:
		case Skotizo.DARK_ANKOU:
			return 8;
		case 6914: // Lizardman, Lizardman brute
		case 6915:
		case 6916:
		case 6917:
			return 7;
		case 7617:
			return 30;
		case 6918:
		case 6919:
			return 11;
		case 2042:
		case 2043:
		case 2044:
			return 41;
		case 5862:
			return 23;
		case 499:
			return 21;
		case 498:
			return 12;
		case 5867:
		case 5868:
		case 5869:
			return 30;
		case 239:
			return npcs[i].attackType == CombatType.DRAGON_FIRE ? 50 : 20;
		case 465:
			return npcs[i].attackType == CombatType.DRAGON_FIRE ? 55 : 13;
		case 8031:
		case 8030:
			return npcs[i].attackType == CombatType.DRAGON_FIRE ? 55 : 34;
		case 2208:
		case 2207:
		case 2206:
			return 16;
		case 2098:
			return 4;
			
			/*Hydra*/
		case 8609:
			return 22;
			
		case 319:
			return npcs[i].attackType == CombatType.MELEE ? 55 : npcs[i].attackType == CombatType.SPECIAL ? 35 : 49;
		case 320:
			return 10;
		case 3129:
			return npcs[i].attackType == CombatType.MELEE ? 60 : npcs[i].attackType == CombatType.SPECIAL ? 49 : 30;
		case 6611:
		case 6612:
			return npcs[i].attackType == CombatType.MELEE ? 30 : npcs[i].attackType == CombatType.MAGE ? 34 : 46;
		case 1046:
			return npcs[i].attackType == CombatType.MAGE ? 40 : 50;
		case 6610:
		case 7144:
		case 7145:
		case 7146:
			return 30;
		case 6609:
			return npcs[i].attackType == CombatType.SPECIAL ? 3 : npcs[i].attackType == CombatType.MAGE ? 60 : 40;
		case 6618:
			return npcs[i].attackType == CombatType.SPECIAL ? 23 : 15;
		case 6619:
			return npcs[i].attackType == CombatType.SPECIAL ? 31 : 25;
		case 2558:
			return npcs[i].attackType == CombatType.MAGE ? 38 : 68;
		case 2562:
			return 31;
		case 3162:
			return npcs[i].attackType == CombatType.RANGE ? 71 : npcs[i].attackType == CombatType.MAGE ? 21 : 15;
		case 963:
			return npcs[i].attackType == CombatType.MAGE ? 30 : 21;
		case 965:
			return npcs[i].attackType == CombatType.MAGE || npcs[i].attackType == CombatType.RANGE ? 30 : 21;
		}
		return npcs[i].maxHit == 0 ? 1 : npcs[i].maxHit;
	}

	public void startGame() {
		for (int i = 0; i < PuroPuro.IMPLINGS.length; i++) {
			newNPC(PuroPuro.IMPLINGS[i][0], PuroPuro.IMPLINGS[i][1], PuroPuro.IMPLINGS[i][2], 0, 1, -1, -1, -1, -1, null);
		}

		/**
		 * Random spawns
		 */
		int random_spawn = Misc.random(2);
		int x = 0;
		int y = 0;
		switch (random_spawn) {
		case 0:
			x = 2620;
			y = 4347;
			break;

		case 1:
			x = 2607;
			y = 4321;
			break;

		case 2:
			x = 2589;
			y = 4292;
			break;
		}
		newNPC(7302, x, y, 0, 1, -1, -1, -1, -1, null);
	}

	public int getNpcListHP(int npcId) {
		if (npcId <= -1) {
			return 0;
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return 0;
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcHealth();

	}

	public String getNpcListName(int npcId) {
		if (npcId <= -1) {
			return "?";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "?";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	private void loadNPCSizes(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new RuntimeException("ERROR: " + fileName + " does not exist.");
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				int npcId, size;
				try {
					npcId = Integer.parseInt(line.split("\t")[0]);
					size = Integer.parseInt(line.split("\t")[1]);
					if (npcId > -1 && size > -1) {
						if (NPCDefinitions.getDefinitions()[npcId] == null) {
							NPCDefinitions.create(npcId, "None", 0, 0, size);
						} else {
							NPCDefinitions.getDefinitions()[npcId].setSize(size);
						}
					}
				} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean loadNPCList(String fileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		File file = new File("./" + fileName);
		if (!file.exists()) {
			throw new RuntimeException("ERROR: NPC Configuration file does not exist.");
		}
		try (BufferedReader characterfile = new BufferedReader(new FileReader("./" + fileName))) {
			while ((line = characterfile.readLine()) != null && !line.equals("[ENDOFNPCLIST]")) {
				line = line.trim();
				int spot = line.indexOf("=");
				if (spot > -1) {
					token = line.substring(0, spot);
					token = token.trim();
					token2 = line.substring(spot + 1);
					token2 = token2.trim();
					token2_2 = token2.replaceAll("\t\t", "\t");
					token2_2 = token2_2.replaceAll("\t\t", "\t");
					token2_2 = token2_2.replaceAll("\t\t", "\t");
					token2_2 = token2_2.replaceAll("\t\t", "\t");
					token2_2 = token2_2.replaceAll("\t\t", "\t");
					token3 = token2_2.split("\t");
					if (token.equals("npc")) {
						newNPCList(Integer.parseInt(token3[0]), token3[1], Integer.parseInt(token3[2]),
								Integer.parseInt(token3[3]));
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

	public static NPC spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit,
			int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return null; // no free slot found
		}
		NPCDefinitions definition = NPCDefinitions.get(npcType);
		NPC newNPC = new NPC(slot, npcType, definition);
		newNPC.setX(x);
		newNPC.setY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.setHeight(heightLevel);
		newNPC.walkingType = WalkingType;
		newNPC.getHealth().setMaximum(HP);
		newNPC.getHealth().reset();
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
		
		return newNPC;
	}

	public static NPCDef[] getNpcDef() {
		return npcDef;
	}

	private ArrayList<int[]> vetionSpellCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> archSpellCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> hydraPoisonCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> fanaticSpellCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> corpSpellCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> olmSpellCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> explosiveSpawnCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> cerberusGroundCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> DragonGroundCoordinates = new ArrayList<>(3);
	private ArrayList<int[]> vasaRockCoordinates = new ArrayList<>(1);

	private void groundSpell(NPC npc, Player player, int startGfx, int endGfx, String coords, int time) {
		if (player == null) {
			return;
		}
		switch (coords) {
		
		case "hydra":
			player.coordinates = hydraPoisonCoordinates;
			break;
			
		case "vasa":
			player.coordinates = vasaRockCoordinates;
			break;
			
		case "vetion":
			player.coordinates = vetionSpellCoordinates;
			break;
			
		case "Dragon":
			player.coordinates = DragonGroundCoordinates;
			break;

		case "archaeologist":
			player.coordinates = archSpellCoordinates;
			break;

		case "fanatic":
			player.coordinates = fanaticSpellCoordinates;
			break;

		case "corp":
			player.coordinates = corpSpellCoordinates;
			break;

		case "olm":
			player.coordinates = olmSpellCoordinates;
			break;

		case "spawns":
			player.coordinates = explosiveSpawnCoordinates;

			List<NPC> exploader = Arrays.asList(NPCHandler.npcs);
			if (exploader.stream().filter(Objects::nonNull).anyMatch(n -> n.npcType == 6768 && !n.isDead)) {
				return;
			}
			break;

		case "cerberus":
			player.coordinates = cerberusGroundCoordinates;
			break;
		}
		int x = player.getX();
		int y = player.getY();
		player.coordinates.add(new int[] { x, y });
		for (int i = 0; i < 2; i++) {
			player.coordinates.add(new int[] { (x - 1) + Misc.random(3), (y - 1) + Misc.random(3) });
		}
		for (int[] point : player.coordinates) {
			int nX = npc.getX() + 2;
			int nY = npc.getY() + 2;
			int x1 = point[0] + 1;
			int y1 = point[1] + 2;
			int offY = (nX - x1) * -1;
			int offX = (nY - y1) * -1;
			if (startGfx > 0) {
				player.getPA().createPlayersProjectile(nX, nY, offX, offY, 40, getProjectileSpeed(npc.getIndex()),
						startGfx, 31, 0, -1, 5);
			}
			if (Objects.equals(coords, "spawns")) {
				spawnNpc(6768, point[0], point[1], 0, 0, -1, -1, -1, -1);
			}

		}
		if (Objects.equals(coords, "spawns")) {
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					kill(6768, 0);
					container.stop();
				}

			}, 7);
		}
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				for (int[] point : player.coordinates) {
					int x2 = point[0];
					int y2 = point[1];
					if (endGfx > 0) {
						player.getPA().createPlayersStillGfx(endGfx, x2, y2, player.getHeight(), 5);
					}
					if (Objects.equals(coords, "cerberus")) {
						player.getPA().createPlayersStillGfx(1247, x2, y2, player.getHeight(), 5);
					}
				}
				player.coordinates.clear();
				container.stop();
			}

		}, time);
	}

	private ArrayList<int[]> gorillaBoulder = new ArrayList<>(1);

	private void groundAttack(NPC npc, Player player, int startGfx, int endGfx, int explosionGfx, int time) {
		if (player == null) {
			return;
		}
		player.totalMissedGorillaHits = 3;
		player.coordinates = gorillaBoulder;
		int x = player.getX();
		int y = player.getY();
		player.coordinates.add(new int[] { x, y });

		for (int[] point : player.coordinates) {
			int nX = npc.getX() + 2;
			int nY = npc.getY() + 2;
			int x1 = point[0] + 1;
			int y1 = point[1] + 2;
			int offY = (nX - x1) * -1;
			int offX = (nY - y1) * -1;
			if (startGfx > 0)
				player.getPA().createPlayersProjectile(nX, nY, offX, offY, 40, getProjectileSpeed(npc.getIndex()),
						startGfx, 31, 0, -1, 5); // 304
		}
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				for (int[] point : player.coordinates) {
					int x2 = point[0];
					int y2 = point[1];
					if (endGfx > 0)
						player.getPA().createPlayersStillGfx(endGfx, x2, y2, player.getHeight(), 5); // 303
				}
				container.stop();
			}

		}, 3);
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				for (int[] point : player.coordinates) {
					int x2 = point[0];
					int y2 = point[1];
					if (explosionGfx > 0)
						player.getPA().createPlayersStillGfx(explosionGfx, x2, y2, player.getHeight(), 5); // 305
				}
				npc.attackType = CombatType.getRandom(CombatType.MELEE, CombatType.RANGE, CombatType.MAGE);
				player.coordinates.clear();
				container.stop();
			}

		}, time);
	}

	public static void kill(int minHeight, int maxHeight, int... npcType) {
		nonNullStream()
		.filter(n -> IntStream.of(npcType).anyMatch(type -> type == n.npcType) && n.getHeight() >= minHeight && n.getHeight() <= maxHeight)
		.forEach(npc -> npc.isDead = true);
	}
	public static void kill(int npcType, int height) {
		nonNullStream()
		.filter(n -> n.npcType == npcType && n.getHeight() == height)
		.filter(npc -> !npc.isDead)
		.forEach(npc -> npc.isDead = true);
	}


	public static void destroy(NPC npc) {
		IntStream.range(0, npcs.length).forEach(index -> {
			if (npcs[index] != null && npcs[index] == npc) {
				npcs[index].setInstance(null);
				npcs[index] = null;
			}
		});
	}
	private void createVetionEarthquake(Player player) {
		player.getPA().shakeScreen(3, 2, 3, 2);
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				player.getPA().resetCinematicCamera();
				container.stop();
			}

		}, 4);
	}

	public static Stream<NPC> nonNullStream() {
		return Arrays.stream(npcs).filter(Objects::nonNull);
	}

	public static NPC getNpcForIndex(int index) {
		if(index < 0 || index >= npcs.length)
			return null;
		return npcs[index];
	}

}
