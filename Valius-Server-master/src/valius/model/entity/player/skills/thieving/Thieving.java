package valius.model.entity.player.skills.thieving;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Lists;

import valius.content.SkillcapePerks;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.desert.DesertDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.content.achievement_diary.western_provinces.WesternDiaryEntry;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.SkillPets;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.items.ItemDefinition;
import valius.util.Misc;

/**
 * A representation of the thieving skill. Support for both object and npc actions will be supported.
 * 
 * @author Jason MacKeigan
 * @date Feb 15, 2015, 7:12:14 PM
 */
public class Thieving {
	
	private static int[] rogueOutfit = { 5553, 5554, 5555, 5556, 5557 };

	/**
	 * The managing player of this class
	 */
	private Player player;

	/**
	 * The last interaction that player made that is recorded in milliseconds
	 */
	private long lastInteraction;

	/**
	 * The constant delay that is required inbetween interactions
	 */
	private static final long INTERACTION_DELAY = 1_500L;

	/**
	 * The stealing animation
	 */
	private static final int ANIMATION = 881;

	/**
	 * Constructs a new {@link Thieving} object that manages interactions between players and stalls, as well as players and non playable characters.
	 * 
	 * @param player the visible player of this class
	 */
	public Thieving(final Player player) {
		this.player = player;
	}

	/**
	 * A method for stealing from a stall
	 * 
	 * @param stall the stall being stolen from
	 * @param objectId the object id value of the stall
	 * @param location the location of the stall
	 */
	public void steal(Stall stall, int objectId, Location location) {
		int pieces = 1;
		for (int aRogueOutfit : rogueOutfit) {
			if (player.getItems().isWearingItem(aRogueOutfit)) {
				pieces += 1;
			}
		}
		if (System.currentTimeMillis() - lastInteraction < INTERACTION_DELAY) {
			//player.sendMessage("You must wait a few more seconds before you can steal again.");
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.sendMessage("You need at least one free slot to steal from this.");
			return;
		}/*
		if (!World.getWorld().getGlobalObjects().exists(objectId, location.getX(), location.getY()) || World.getWorld().getGlobalObjects().exists(4797, location.getX(), location.getY())) {
			player.sendMessage("The stall has been depleted.");
			return;
		}*/
		if (player.getSkills().getLevel(Skill.THIEVING) < stall.level) {
			player.sendMessage("You need a thieving level of " + stall.level + " to steal from this.");
			return;
		}
		if (Misc.random(100) == 0 && player.getInterfaceEvent().isExecutable()) {
			player.getInterfaceEvent().execute();
			return;
		}
		switch (stall) {
		case Food:
			player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TEA_STALL);
			break;
		case Crafting:
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_CAKE);
			}
			break;
		case Magic:
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_GEM_ARD);
			}
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.STEAL_GEM_FAL);
			}
			break;
		case General:
			DailyTasks.increase(player, PossibleTasks.SILVER_SICKLES);
			break;
		case Scimitar:
			break;
		case Fur:
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_FUR);
			}
			break;
		default:
			break;
		}
		player.turnPlayerTo(location.getX(), location.getY());
/**		if (Misc.random(stall.depletionProbability) == 0) {
			GlobalObject stallObj = World.getWorld().getGlobalObjects().get(objectId, location.getX(), location.getY(), location.getZ());
			if (stallObj != null) {
				World.getWorld().getGlobalObjects().add(new GlobalObject(4797, location.getX(), location.getY(), location.getZ(), stallObj.getFace(), 10, 8, stallObj.getObjectId()));
			}
		}
 */
		Item item = stall.item;
		ItemDefinition definition = ItemDefinition.forId(item.getId());
		if (Misc.random(40) == 1) {
			int sPoints = Misc.random(1, 5);
            player.skillPoints += sPoints;
            player.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
            player.updateQuestTab();
		}
		
		 if (Misc.random(stall.petChance) == 1 && !SkillPets.THIEVING.hasPet(player)) {
			 PetHandler.skillPet(player, SkillPets.THIEVING);
		 }
		 
		int experience = (int) stall.experience;
		
		player.startAnimation(ANIMATION);
		player.getItems().addItem(item.getId(), item.getAmount());
		player.getPA().addSkillXP((int) experience + (experience / 20 * pieces)  , Skill.THIEVING.getId(), true);
		player.sendMessage("You steal a " + definition.getName() + " from the stall.");
		Achievements.increase(player, AchievementType.THIEV, 1);
		lastInteraction = System.currentTimeMillis();
	}

	/**
	 * A method for pick pocketing npc's
	 * 
	 * @param pickpocket the pickpocket type
	 * @param npc the npc being pick pocketed
	 */
	public void steal(Pickpocket pickpocket, NPC npc) {
		double multiplier = 0;
		for (int aRogueOutfit : rogueOutfit) {
			if (player.getItems().isWearingItem(aRogueOutfit)) {
				multiplier+=0.625;
			}
		}
		if (System.currentTimeMillis() - lastInteraction < INTERACTION_DELAY) {
			//player.sendMessage("You must wait a few more seconds before you can steal again.");
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.sendMessage("You need at least one free slot to steal from this npc.");
			return;
		}
		if (player.getSkills().getLevel(Skill.THIEVING) < pickpocket.level) {
			player.sendMessage("You need a thieving level of " + pickpocket.level + " to steal from this npc.");
			return;
		}
		if (Misc.random(100) == 0 && player.getInterfaceEvent().isExecutable()) {
			player.getInterfaceEvent().execute();
			return;
		}
		/**
		 * Incorporate chance for failure
		 */
		switch (pickpocket) {
		case FARMER:
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PICKPOCKET_ARD);
			}
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICKPOCKET_MASTER_FARMER_FAL);
			}
			if (Boundary.isIn(player, Boundary.DRAYNOR_BOUNDARY)) {
				player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_FARMER_DRAY);
			}
			DailyTasks.increase(player, PossibleTasks.MASTER_FARMER);
			break;
		case MASTER_FARMER:
			if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
				player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PICKPOCKET_ARD);
			}
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICKPOCKET_MASTER_FARMER_FAL);
			}
			if (Boundary.isIn(player, Boundary.DRAYNOR_BOUNDARY)) {
				player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_FARMER_DRAY);
			}
			DailyTasks.increase(player, PossibleTasks.MASTER_FARMER);
			break;
		case MAN:
			if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
				player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICKPOCKET_MAN);
			}
			if (Boundary.isIn(player, Boundary.LUMRIDGE_BOUNDARY)) {
				player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_MAN_LUM);
			}
			break;
		case GNOME:
			player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PICKPOCKET_GNOME);
			break;
		case HERO:
			player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PICKPOCKET_HERO);
			break;
		case MENAPHITE_THUG:
			player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PICKPOCKET_THUG);
			break;
		default:
			break;
		
		}
		player.turnPlayerTo(npc.getX(), npc.getY());
		player.startAnimation(ANIMATION);
		Item item = pickpocket.getRandomItem();
		double percentOfXp = pickpocket.experience / 100 * multiplier;
		boolean maxCape = SkillcapePerks.THIEVING.isWearing(player) || SkillcapePerks.isWearingMaxCape(player);
		if (item != null) {
			player.getItems().addItem(item.getId(), maxCape ? item.getAmount()*2 : item.getAmount());
		} else {
			player.sendMessage("You were unable to find anything useful.");
		}
		if (Misc.random(25) == 0) {
			int sPoints = Misc.random(1, 5);
            player.skillPoints += sPoints;
            player.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
            player.updateQuestTab();
		}
		 if (Misc.random(pickpocket.petChance) == 1 && !SkillPets.THIEVING.hasPet(player)) {
			 PetHandler.skillPet(player, SkillPets.THIEVING);
		 }
		Achievements.increase(player, AchievementType.THIEV, 1);
		player.getPA().addSkillXP((int) (pickpocket.experience + percentOfXp), Skill.THIEVING.getId(), true);
		lastInteraction = System.currentTimeMillis();
	}

	private enum Rarity {
		ALWAYS(0), COMMON(5), UNCOMMON(10), RARE(15), VERY_RARE(25);

		/**
		 * The rarity
		 */
		private final int rarity;

		/**
		 * Creates a new rarity
		 * 
		 * @param rarity the rarity
		 */
		Rarity(int rarity) {
			this.rarity = rarity;
		}
	}

	@SuppressWarnings("serial")
	public enum Pickpocket {
		MAN(1, 8, 10000, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(995, 750), new Item(995, 1000), new Item(995, 1250)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
			}
		}), FARMER(10, 20, 9800, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(5291), new Item(5292), new Item(5293)));
				put(Rarity.COMMON, Arrays.asList(new Item(5294), new Item(5297), new Item(5296)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
				put(Rarity.RARE, Arrays.asList(new Item(5299), new Item(5300), new Item(5303)));
				put(Rarity.VERY_RARE, Collections.singletonList(new Item(5304)));
			}
		}), MENAPHITE_THUG(65, 75, 9000, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(995, 1000), new Item(995, 800), new Item(995, 950)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
			} 
		}), GNOME(75, 85, 8500, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(995, 1200), new Item(995, 800), new Item(995, 1250)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(444), new Item(557), new Item(13431, 5)));
			}
		}), HERO(80, 100, 7000, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(995, 1500), new Item(995, 1800), new Item(995, 3500)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(560, 2), new Item(565), new Item(444), new Item(1601)));
			}
		}),
		MASTER_FARMER(38, 65, 9500, new HashMap<Rarity, List<Item>>() {
			{
				put(Rarity.ALWAYS, Arrays.asList(new Item(5291), new Item(5292), new Item(5293)));
				put(Rarity.COMMON, Arrays.asList(new Item(5294), new Item(5297), new Item(5296)));
				put(Rarity.COMMON, Arrays.asList(new Item(5295), new Item(5298), new Item(5301), new Item(5302)));
				put(Rarity.UNCOMMON, Arrays.asList(new Item(5299), new Item(5300), new Item(5303)));
				put(Rarity.RARE, Collections.singletonList(new Item(5304)));
			}
		});

		/**
		 * The level required to pickpocket
		 */
		private final int level;

		/**
		 * The experience gained from the pick pocket
		 */
		private final int experience;
		
		/**
		 * The chance of receiving a pet
		 */
		private final int petChance;

		/**
		 * The list of possible items received from the pick pocket
		 */
		private Map<Rarity, List<Item>> items = new HashMap<>();

		/**
		 * Creates a new pickpocket level requirement and experience gained
		 * 
		 * @param level the level required to steal from
		 * @param experience the experience gained from stealing
		 */
		Pickpocket(int level, int experience, int petChance, Map<Rarity,List<Item>> items) {
			this.level = level;
			this.experience = experience;
			this.petChance = petChance;
			this.items = items;
		}

		Item getRandomItem() {
			for (Entry<Rarity, List<Item>> entry : items.entrySet()) {
				final Rarity rarity = entry.getKey();

				if (rarity == Rarity.ALWAYS) {
					continue;
				}
				final List<Item> items = entry.getValue();

				if (items.isEmpty()) {
					continue;
				}

				if (RandomUtils.nextInt(1, rarity.rarity) == 1) {
					return Misc.getItemFromList(items).randomizedAmount();
				}
			}

			List<Item> always = items.getOrDefault(Rarity.ALWAYS, Lists.newArrayList());

			if (!always.isEmpty()) {
				return Misc.getItemFromList(always).randomizedAmount();
			}

			return null;
		}
	}

	public enum Stall {
		Crafting(new Item(1893), 1, 16, 20, 10000),
		Food(new Item(712), 25, 30, 10, 9500), 
		General(new Item(2961), 50, 54, 10, 9000), 
		Magic(new Item(1613), 75, 80, 10, 8500), 
		Scimitar(new Item(1993), 90, 100, 10, 8000),
		Fur(new Item(6814), 50, 54, 10, 7500);

		/**
		 * The item received from the stall
		 */
		private final Item item;

		/**
		 * The experience gained in thieving from a single stall thieve
		 */
		private final double experience;

		/**
		 * The probability that the stall will deplete
		 */
		private final int depletionProbability;

		/**
		 * The level required to steal from the stall
		 */
		private final int level;
		
		/**
		 * The chance of receiving a pet
		 */
		private final int petChance;

		/**
		 * Constructs a new {@link Stall} object with a single parameter, {@link Item} which is the item received when interacted with.
		 * 
		 * @param item the item received upon interaction
		 */
		Stall(Item item, int level, int experience, int depletionProbability, int petChance) {
			this.item = item;
			this.level = level;
			this.experience = experience;
			this.depletionProbability = depletionProbability;
			this.petChance = petChance;
		}
	}

}
