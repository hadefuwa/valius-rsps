package valius.model.entity.player.skills.fishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.karamja.KaramjaDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;
import valius.world.World;

public class Fishing {
	
	/*
	 * Player Object
	 */
	private Player player;
	
	/*
	 * Randomizer
	 */
	private Random random = new Random();//used for random fish, rewards, chance of continuous fishing
	
	/*
	 * Holds possible fish you can get from the specific fishing spot you are interacting with
	 */
	List <Integer> attemptCatch = new ArrayList <Integer>();
	
	/*
	 * Holds if the player is currently fishing or not
	 */
	public boolean isFishing = false;//if player is currently fishing or not
	
	double bonus;
	
	
	/*
	 * POSSIBLE REWARDS
	 */
	
	private static final int EASY_CLUE = 13648;
	private static final int MEDIUM_CLUE = 13649;
	private static final int HARD_CLUE = 13650;
	private static final int ANGLER_HAT = 13258;
	private static final int ANGLER_TOP = 13259;
	private static final int ANGLER_WADERS = 13648;
	private static final int ANDLER_BOOTS = 13648;
	private static final int PET_HERON = 13320;
	
	public static int fishingOutfit [] = { 13258, 13259, 13260, 13261};
	
	/*
	 * FISH ID'S
	 */
	private static final int RAW_SHRIMP = 317;
	private static final int RAW_HERRING = 345;
	private static final int RAW_ANCHOVIE = 321;
	private static final int RAW_SARDINE = 327;
	private static final int RAW_TROUT = 335;
	private static final int RAW_PIKE = 349;
	private static final int RAW_SALMON = 331;
	private static final int RAW_TUNA = 359;
	private static final int RAW_LOBSTER = 377;
	private static final int RAW_SWORDFISH = 371;
	private static final int RAW_KARAMBWAN = 3142;
	private static final int RAW_MONKFISH = 7944;
	private static final int RAW_SHARK = 383;
	private static final int RAW_ANGLER = 13439;
	private static final int DARK_CRAB = 11934;
	private static final int MANTA_RAY = 389;
	
	/*
	 * FISHING METHODS -the fishing tool item id's
	 */
	private static final int SMALL_NET = 303;
	private static final int FLY_FISHING = 309;
	private static final int HARPOON = 311;
	private static final int CAGE = 301;
	private static final int BIG_NET = 305;
	private static final int KARAMBWAN_VESSEL = 3157;
	private static final int FISHING_ROD = 307;
	private static final int PEARL_ROD = 22842;
	
	/*
	 * BAIT TYPES
	 */
	private static final int NO_BAIT = 0;
	private static final int FISHING_BAIT = 313;
	private static final int FEATHERS = 314;
	private static final int SANDWORM = 13431;//
	private static final int DARK_FISHING_BAIT = 11940;//change to dark fishing bait id
	private static final int CAVE_WORMS = 20853;
	
	private static final int FISH_SACK = 22838;
	
	
	public Fishing(Player p) {//constructor sets player objects
		this.player = p;
	}
		
	
	public static enum FISHING{//all fishing data
		//xp*30
		SHRIMP(RAW_SHRIMP,FishingTool.SMALL_NET,NO_BAIT,1,15,3913,621),
		HERRING(RAW_HERRING,FishingTool.FISHING_ROD,FISHING_BAIT,15,13,3913,622),
		SARDINE(RAW_SARDINE,FishingTool.FISHING_ROD,FISHING_BAIT,15,13,3913,622),
		ANCHOVIE(RAW_ANCHOVIE,FishingTool.SMALL_NET,NO_BAIT,15,29,3913,621),
		TROUT(RAW_TROUT,FishingTool.FLY_FISHING_ROD,FEATHERS,20,33,3417,623),
		PIKE(RAW_PIKE,FishingTool.FISHING_ROD,FISHING_BAIT,25,41,3417,622),
		SALMON(RAW_SALMON,FishingTool.FLY_FISHING_ROD, FEATHERS, 30,47,3417,623),
		TUNA(RAW_TUNA,FishingTool.HARPOON,NO_BAIT,35,53,3657,618),
		LOBSTER(RAW_LOBSTER,FishingTool.CAGE,NO_BAIT,40,59,3657,619),
		SWORDFISH(RAW_SWORDFISH,FishingTool.HARPOON,NO_BAIT,45,65,3657,618),
		MONKFISH(RAW_MONKFISH,FishingTool.BIG_NET,NO_BAIT,62,67,1520,620),
		KARAMBWAN(RAW_KARAMBWAN,FishingTool.KARAMBWAN_VESSEL,NO_BAIT,62,77,4712,620),
		SHARK(RAW_SHARK,FishingTool.HARPOON, NO_BAIT,76,83,1520,618),
		ANGLER(RAW_ANGLER,FishingTool.FISHING_ROD,SANDWORM,82,89,6825,622),
		DARKCRAB(DARK_CRAB,FishingTool.CAGE,DARK_FISHING_BAIT,85,95,635,619),
		MANTA(389,FishingTool.BIG_NET,NO_BAIT,91,95,4712,620),
		
		//DeepSea fishing
		LECKISH(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 25, 45, 18532, 622),
		MYCIL(20863, FishingTool.PEARL_ROD, CAVE_WORMS, 40, 62, 18532, 622),
		ROQED(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 50, 70, 18532, 622),
		BRAWK(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 60, 74, 18532, 622),
		KYREN(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 70, 81, 18532, 622),
		EEL(32294, FishingTool.PEARL_ROD, CAVE_WORMS, 75, 85, 18532, 622),
		SUPHI(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 80, 90, 18532, 622),
		PYSK(20859, FishingTool.PEARL_ROD, CAVE_WORMS, 90, 95, 18532, 622),
		BARON_SHARK(32297, FishingTool.PEARL_ROD, CAVE_WORMS, 95, 104, 18532, 622),
		;
		
		 int fishId;//the type of fish you get
		 FishingTool fishingTool;//the equipment needed to fish3
		 int bait;//what type of bait used to fish
		 int levelRequired;//level needed to fish
		 int xpGained;//how much experience you get per fish
		 int fishingSpotId;//the fishing spot object id
		 int animationId;//animation performed
		
		FISHING(int fishId, FishingTool fishingType, int bait, int levelRequired, int xpGained, int fishingSpotId, int animationId){
			this.fishId = fishId;
			this.fishingTool = fishingType;
			this.bait = bait;
			this.levelRequired = levelRequired;
			this.xpGained = xpGained;
			this.fishingSpotId = fishingSpotId;
			this.animationId = animationId;
		}
		
	}
	
	
	
	public enum REWARDS{//all random rewards you can possibly get while fishing
		EASYCLUE(EASY_CLUE,240,0),
		MEDIUMCLUE(MEDIUM_CLUE,120,1),
		HARDCLUE(HARD_CLUE,60,2),
		ANGLERHAT(ANGLER_HAT,60,3),
		ANGLERTOP(ANGLER_TOP,60,4),
		ANGLERWADERS(ANGLER_WADERS,60,5),
		ANGLERBOOTS(ANDLER_BOOTS,60,6),
		HERON(PET_HERON,5,7);
		
		int itemId;
		int chance;
		int index;
		
		REWARDS(int itemId, int chance, int index){
			this.itemId = itemId;
			this.chance = chance;
			this.index = index;
		}
	}
	
	
	public void startFishing(int fishingSpotId, int fishingType) {//starts fishing
		int useBaitChance = Misc.random(1, 2);
		attemptCatch.clear();//resets possible catches from spot
		for(FISHING fishing: FISHING.values()) {
			if(fishingSpotId == fishing.fishingSpotId && player.getSkills().getLevel(Skill.FISHING) < fishing.levelRequired && fishing.fishingTool.anyMatch(fishingType) && attemptCatch.isEmpty() == true) {
				player.sendMessage("You need a fishing level of "+fishing.levelRequired+" to fish this");
				return;
			}
			if(fishingSpotId == fishing.fishingSpotId && player.getSkills().getLevel(Skill.FISHING) >= fishing.levelRequired && fishing.fishingTool.anyMatch(fishingType)) {
				
				if(!fishing.fishingTool.playerHasAny(player)) {
					player.sendMessage("You need a "+World.getWorld().getItemHandler().getItemList(fishing.fishingTool.getFirst()).itemName+" to fish here.");
					return;
				}
				 if (useBaitChance == 2 && !player.getItems().isWearingItem(FISH_SACK)) {
				if(!player.getItems().playerHasItem(fishing.bait) && fishing.bait != 0) {
					player.sendMessage("You need a "+World.getWorld().getItemHandler().getItemList(fishing.bait).itemName+" to fish here.");
					return;
				}
			 }
				if(player.getItems().freeSlots() == 0) {
					player.sendMessage("You have no more space in your inventory.");
					return;
				}
				player.startAnimation(fishing.animationId);
				attemptCatch.add(fishing.fishId);
			}
		}//end of loop
		if(isFishing == true) {
			return;
		}
		//if you pass all the conditions you begin to fish
		isFishing = true;
		player.sendMessage("You begin fishing.");
		player.stopPlayerSkill = true;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if(attemptCatch.isEmpty()) {//makes it so players cant get null fish
					container.stop();//stops actual event
					stop();//resets player
					return;
				}
				if(player.getItems().freeSlots() == 0) {//if player continues to fish without stopping this will stop event when inv is full
					player.sendMessage("You have no more space in your inventory.");
					container.stop();
					stop();
					return;
				}

				if (Misc.random(50) == 0) {
					int sPoints = Misc.random(1, 5);
		            player.skillPoints += sPoints;
		            player.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
		        }
				
				if (!player.stopPlayerSkill) {//if player walks away or stops fishing this stops thge container
					container.stop();
					stop();
				}
				catchFish(fishingSpotId);//after you pass conditions you catch the fish
		}
				@Override
				public void stop() {//resets fishing at the of the event
					resetFishing();
					return;
				}
			}, 5 + random.nextInt(10));//time between caught fish (randomizes intervals)
	}
	
	
	public void catchFish(int fishingSpotId) {// when you catch the fish it is handled here
		if (isFishing == false) {
			return;
		}
		if (player.getItems().freeSlots() == 0) {// Will stop playing from fishing with full inventory
			player.sendMessage("You have no more space in your inventory.");
			return;
		}

		for (FISHING fishing : FISHING.values()) {
			if (attemptCatch.stream().findFirst().get().equals(fishing.fishId)) {
				if (!player.getItems().playerHasItem(fishing.bait) && fishing.bait != 0) {
					attemptCatch.clear();
					player.sendMessage("You have ran out of "
							+ World.getWorld().getItemHandler().getItemList(fishing.bait).itemName + "(s).");
					return;
				}

				int pieces = 0;
				if (player.getItems().isWearingItem(13258)) {
					pieces++;
				}
				if (player.getItems().isWearingItem(13259)) {
					pieces++;
				}
				if (player.getItems().isWearingItem(13260)) {
					pieces++;
				}
				if (player.getItems().isWearingItem(13261)) {
					pieces++;
				}

				double bonus = pieces * .05 * fishing.xpGained;
				double xpGained = fishing.xpGained + bonus;
				player.startAnimation(fishing.animationId);
				player.sendMessage(
						"You caught a " + World.getWorld().getItemHandler().getItemList(fishing.fishId).itemName);
				Achievements.increase(player, AchievementType.FISH, 1);
				player.getPA().addSkillXP((int) xpGained, player.playerFishing, true);
				player.getItems().deleteItem(fishing.bait, 1);
				player.getItems().addItem(fishing.fishId, 1);
				giveAchievements(player, fishing.fishId);
				attemptRandomReward();
			}
			if (fishing.fishId == attemptCatch.get(random.nextInt(attemptCatch.size())) && extraFish() == true
					&& player.getItems().freeSlots() != 0) {
				player.sendMessage(
						"You caught a " + World.getWorld().getItemHandler().getItemList(fishing.fishId).itemName);
				Achievements.increase(player, AchievementType.FISH, 1);
				player.getItems().deleteItem(fishing.bait, 1);
				player.getPA().addSkillXP(fishing.xpGained, player.playerFishing, true);
				player.getItems().addItem(fishing.fishId, 1);
				giveAchievements(player, fishing.fishId);
				return;
			}
		}
	}

	private boolean extraFish() {// chance of getting an extra fish
		return random.nextInt(100) < 30 + (player.getSkills().getLevel(Skill.FISHING) / 4);
	}

	public void attemptRandomReward() {// random chance of getting clue scroll
		for (REWARDS rewards : REWARDS.values()) {
			if (random.nextInt(REWARDS.values().length) == rewards.index && random.nextInt(4000) < rewards.chance) {
				player.sendMessage(
						"You recieved a " + World.getWorld().getItemHandler().getItemList(rewards.itemId).itemName);
				player.getItems().addItemUnderAnyCircumstance(rewards.itemId, 1);
			}
		}
	}

	void giveAchievements(Player player, int fish) {
		switch (fish) {

		case 383: //shark
			DailyTasks.increase(player, PossibleTasks.SHARKS);
			break;
		case 3142: //karambwan
			if (Boundary.isIn(player, Boundary.RESOURCE_AREA_BOUNDARY)) {
				player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.KARAMBWAN);
			}
			break;			
		case 389: //manta
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.FISH_MANTA);
			}
			break;
		case 371: //swordfish
			if (Boundary.isIn(player, Boundary.CATHERBY_BOUNDARY)) {
				player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.FISH_SWORD);
			}
			break;
			
		case 377: //lobster
			if (Boundary.isIn(player, Boundary.KARAMJA_BOUNDARY)) {
				player.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.FISH_LOBSTER_KAR);
			}
			break;
		}
	}
public static int getFishId(int fishId) {//gets the fish type of a specific fish id || used for noting fish in resource
	for(FISHING fishes : FISHING.values()) {
		if(fishes.fishId == fishId) {
			return fishId;
		}
	}
	return 0;
}

private void resetFishing() {//when anything interrupts player from fishing this resets player
	isFishing = false;
	player.startAnimation(65535);
	player.getPA().removeAllWindows();
	player.playerSkilling[10] = false;
}	
}//end of class