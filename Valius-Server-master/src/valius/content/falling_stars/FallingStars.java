/**
 * 
 */
package valius.content.falling_stars;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.Getter;
import valius.discord.DiscordBot;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.mining.Pickaxe;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * @author Patrity
 *
 */
public class FallingStars {
	
	

	/*
	 * Star IDs from smallest to largest
	 */
	public static final int[] STAR_OBJECT_IDS = { 17204, 17203, 17202, 17201, 17200, 17199, 17198, 17197, 17196 };
	public static final int[] STARDUST_AMOUNTS = { 1200, 700, 439, 250, 175, 80, 40, 40, 15};
	public static final int[] EXPERIENCE_RATES = { 14, 25, 29, 32, 47, 71, 114, 145, 210 };
	public static final int[] LEVEL_REQ = { 10, 20, 30, 40, 50, 60, 70, 80, 90 };
	
	public static final int STAR_DUST_ID = 33423;
	
	private static final int STAR_DUST_BOX_ID = 33422;
	private static final int PET_ID = 33421;
	
	private static final int EXTRACTION_TIME = 20;
	private static final int MINIMUM_EXTRACTION_TIME = 2;
	

	@Getter
	private static StarSpawns activeStarLocation;

	@Getter
	private static CycleEvent cycleEvent;

	@Getter
	private static CycleEventContainer cycleEventContainer;
	
	@Getter
	private static GlobalObject activeStar;
	
	private static int starDustLeft = -1;
	private static int starIndex = -1;

	public static void newStar() {
		cycleEvent = new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (activeStar != null) {
					destroyStar();
					container.setTick(generateTime());
					return;
				}
				container.setTick(generateTime());
				initialSpawn();
				System.out.println("Fallen star run");
			}
		};

		cycleEventContainer = CycleEventHandler.getSingleton().addEvent(FallingStars.class, cycleEvent, generateTime());
	}

	public static void destroyStar() {
		if(activeStar == null)
			return;
		World.getWorld().getGlobalObjects().remove(activeStar);
		activeStar = null;
		activeStarLocation = null;
		starDustLeft = -1;
		starIndex = -1;
	}

	public static void initialSpawn() {
		System.out.println("Star spawned");
		starIndex = Misc.random(STAR_OBJECT_IDS.length - 1);
		
		int objectId = STAR_OBJECT_IDS[starIndex];
		starDustLeft = STARDUST_AMOUNTS[starIndex];
		
		activeStarLocation = StarSpawns.random();
		activeStar = new GlobalObject(objectId, activeStarLocation.getX(), activeStarLocation.getY(), activeStarLocation.getZ(), 0, 10);
		World.getWorld().getGlobalObjects().add(activeStar);
		
		DiscordBot.sendMessage("shooting-stars", "A star has fallen " + activeStarLocation.getLocation() + "");
		GlobalMessages.send("A star has fallen " + activeStarLocation.getLocation(), GlobalMessages.MessageType.EVENT);
	}
	
	private static void nextStar() {
		int pickaxes[] = {33545, 33546};
		int petChance = Misc.random(1, 500);
		
		List<Player> activeMining = PlayerHandler
									.nonNullStream()
									.filter(plr -> World.getWorld().getEventHandler().isRunning(plr, StarMineEvent.class))
									.collect(Collectors.toList());
		
		activeMining.forEach(player -> {
			for (int Pickaxes : pickaxes) {
				if (player.getItems().isWearingItem(Pickaxes)) {
					if (petChance == 5) {
						player.getItems().addItemUnderAnyCircumstance(33421, 1);
						player.sendMessage("You rescue a Star sprite trapped within a layer of the star!");
						GlobalMessages.send("" + player.playerName + " has received a pet Star sprite while Mining a shooting star!", GlobalMessages.MessageType.LOOT);
					}
				}
			}
		});
		
		
		starIndex--;
		if(starIndex < 0) {
			destroyStar();
			activeMining.forEach(player -> player.sendMessage("You have successfully mines through all the layers of the star!"));
			return;
		}
		int objectId = STAR_OBJECT_IDS[starIndex];
		starDustLeft = STARDUST_AMOUNTS[starIndex];
		if(activeStar != null) {
			World.getWorld().getGlobalObjects().remove(activeStar);
		}
		activeStar = new GlobalObject(objectId, activeStarLocation.getX(), activeStarLocation.getY(), activeStarLocation.getZ(), 0, 10);
		World.getWorld().getGlobalObjects().add(activeStar);
	}

	private static int generateTime() {
		return Misc.toRandomCycle(30, 90, TimeUnit.MINUTES);
	}
	
	public static boolean attemptMine(Player player, int objectId, Location location) {
		if(activeStar == null)
			return false;
		if(objectId != activeStar.getObjectId())
			return false;
		if(!location.equals(activeStar.getLocation()))
			return false;
		if(starDustLeft <= 0)
			return false;
		
		int levelReq = LEVEL_REQ[starIndex];
		int experience = EXPERIENCE_RATES[starIndex];
		Pickaxe pickaxe = Pickaxe.getBestPickaxe(player);
		if(player.getSkills().getLevel(Skill.MINING) < levelReq) {
			player.sendMessage("You need a mining level of at least " + levelReq + " to mine this.");
			return true;
		}
		if (pickaxe == null) {
			player.sendMessage("You need a pickaxe to mine this.");
			return true;
		}
		if (player.getItems().freeSlots() == 0 && !player.getItems().playerHasItem(STAR_DUST_ID)) {
			player.getDH().sendStatement("You have no more free slots.");
			player.nextChat = -1;
			return true;
		}
		int pickaxeReduction = pickaxe.getExtractionReduction();
		int extractionTime = EXTRACTION_TIME - (pickaxeReduction);
		if (extractionTime < MINIMUM_EXTRACTION_TIME) {
			extractionTime = MINIMUM_EXTRACTION_TIME;
		}
		player.sendMessage("You swing your pickaxe.");
		player.startAnimation(pickaxe.getAnimation());
		player.turnPlayerTo(location.getX(), location.getY());
		player.getSkilling().stop();
		player.getSkilling().setSkill(Skill.MINING);
		World.getWorld().getEventHandler().submit(new StarMineEvent(player, objectId, experience, location, pickaxe, extractionTime));
		return true;
	}

	public static boolean starExists() {
		return activeStar != null;
	}

	public static int deplete() {
		starDustLeft--;
		if(starDustLeft <= 0) {
			nextStar();
		}
		return 1;
	}

}
