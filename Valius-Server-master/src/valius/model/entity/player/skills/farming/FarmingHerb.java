package valius.model.entity.player.skills.farming;

import valius.model.items.ItemAssistant;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Oct 27, 2013
 */
public class FarmingHerb {

	public enum Herb {
		//Not 100% sure of these times going off what was already there...
		GUAM(5291, 199, 5, 36, 1, 500, 8000), //.50 mins - 30 seconds || NEW 5 mins
		MARRENTIL(5292, 201, 10, 60, 7, 500, 7800),
		TARROMIN(5293, 203, 15, 120, 19, 500, 7600),
		HARRALANDER(5294, 205, 25, 180, 26, 500, 7400), 
		RANARR(5295, 207, 30, 240, 32, 1250, 7200), // 1.25 mins - 75 seconds || NEW 12.5 mins
		TOADFLAX(5296, 3049, 50, 300, 38, 1250, 7000), 
		IRIT(5297, 209, 62, 360, 44, 1250, 6800), 
		AVANTOE(5298, 211, 75, 420, 50, 1250, 6600),
		KWUARM(5299, 213, 75, 450, 56, 1250, 6400), 
		SNAP_DRAGON(5300, 3051, 100, 480, 62, 2500, 6200), //2.5 mins - 150 seconds || NEW 25 mins
		CADANTINE(5301, 215, 105, 510, 67, 2500, 6000), 
		LANTADYME(5302, 2485, 115, 540, 73, 2500, 5800), 
		DWARF_WEED(5303, 217, 120, 570, 79, 2500, 5600), 
		TORSTOL(5304, 219, 125, 600, 85, 5000, 5000); // 5 mins - 300 seconds || NEW 50 mins

		int seedId, grimyId, plantXp, harvestXp, levelRequired, time, petChance;

		Herb(int seedId, int grimyId, int plantXp, int harvestXp, int levelRequired, int time, int petChance) {
			this.seedId = seedId;
			this.grimyId = grimyId;
			this.plantXp = plantXp;
			this.harvestXp = harvestXp;
			this.levelRequired = levelRequired;
			this.time = time;
			this.petChance = petChance;
		}

		public int getSeedId() {
			return seedId;
		}

		public int getGrimyId() {
			return grimyId;
		}

		public int getPlantingXp() {
			return plantXp;
		}

		public int getHarvestingXp() {
			return harvestXp;
		}

		public int getLevelRequired() {
			return levelRequired;
		}

		public int getGrowthTime() {
			return time;
		}

		public String getSeedName() {
			return ItemAssistant.getItemName(seedId);
		}

		public String getGrimyName() {
			return ItemAssistant.getItemName(grimyId);
		}
		public int getPetChance() {
			return petChance;
		}

	}

	public static Herb getHerbForSeed(int seedId) {
		for (Herb h : Herb.values())
			if (h.getSeedId() == seedId)
				return h;
		return null;
	}

	public static Herb getHerbForGrimy(int grimyId) {
		for (Herb h : Herb.values())
			if (h.getGrimyId() == grimyId)
				return h;
		return null;
	}

}
