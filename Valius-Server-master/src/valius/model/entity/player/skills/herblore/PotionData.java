package valius.model.entity.player.skills.herblore;

import lombok.Getter;
import valius.model.items.Item;

public class PotionData {
	
	/**
	 * A set of data involving unfinished herblore potions
	 * @author Matt - https://www.rune-server.org/members/matt%27/
	 *
	 * @date 23 dec. 2016
	 */
	@Getter
	public enum UnfinishedPotions {
		
		GUAM_POTION(new Item(91), new Item(249), 1), //Attack potion
		MARRENTILL_POTION(new Item(93), new Item(251), 5), //Antipoison
		TARROMIN_POTION(new Item(95), new Item(253), 12), //Strength potion
		HARRALANDER_POTION(new Item(97), new Item(255), 22), //Restore potion, Energy potion & Combat potion
		RANARR_POTION(new Item(99), new Item(257), 30), //Prayer potion
		TOADFLAX_POTION(new Item(3002), new Item(2998), 34), //Agility potion & Saradomin brew
		IRIT_POTION(new Item(101), new Item(259), 45), //Super attack & Fishing potion
		AVANTOE_POTION(new Item(103), new Item(261), 50), //Super energy
		KWUARM_POTION(new Item(105), new Item(263), 55), //Super strength & Weapon poison
		SNAPDRAGON_POTION(new Item(3004), new Item(3000), 63), //Super restore
		CADANTINE_POTION(new Item(107), new Item(265), 66), //Super defence
		LANTADYME(new Item(2483), new Item(2481), 69), //Antifire, Magic potion
		DWARF_WEED_POTION(new Item(109), new Item(267), 72), //Ranging potion
		TORSTOL_POTION(new Item(111), new Item(269), 78), //Zamorak brew
		BLOOD_CADANTINE_POTION(new Item(22446), new Item(22443), new Item(265), 80), //Super defence
		GRYM_POTION(new Item(23881), new Item(23875), 70),
		
		;
		
		private final Item potion, ingredient;
		private Item vial = new Item(227);
		private int levelReq;
		

		private UnfinishedPotions(Item vial, Item potion, Item ingredient, int levelReq) {
			this.vial = vial;
			this.potion = potion;
			this.ingredient = ingredient;
			this.levelReq = levelReq;
		}
		
		private UnfinishedPotions(Item potion, Item ingredient, int levelReq) {
			this.potion = potion;
			this.ingredient = ingredient;
			this.levelReq = levelReq;
		}
		
		
		public static UnfinishedPotions forId(int vialId, int herbId) {
			for(UnfinishedPotions unf : UnfinishedPotions.values()) {
				if (unf.ingredient.getId() == herbId && unf.vial.getId() == vialId) {
					return unf;
				}
			}
			return null;
		}
	}
	
	public enum FinishedPotions {
		AGILITY(new Item(3032), 34, 80, new Item(3002), new Item(2152)), 
		ANTI_VENOM(new Item(12905), 87, 120, new Item(5954), new Item(12934, 15)), 
		ANTI_VENOM_PLUS(new Item(12913), 94, 125, new Item(12907), new Item(111)), 
		ANTIDOTE_PLUS(new Item(5943), 68, 155, new Item(3002), new Item(6049)), 
		ANTIDOTE_PLUS_PLUS(new Item(5952), 79, 177, new Item(101), new Item(6051)), 
		STAMINA(new Item(12625), 77, 152, new Item(3016), new Item(12640, 3)), 
		ANTIFIRE(new Item(2452), 69, 157, new Item(2483), new Item(241)), 
		SUPER_ANTIFIRE(new Item(21978), 92, 220, new Item(2452), new Item(21977)),
		ANTIPOISON(new Item(2446), 5, 37, new Item(93), new Item(235)), 
		ATTACK(new Item(2428), 3, 25, new Item(91), new Item(221)), 
		COMBAT(new Item(9739), 36, 84, new Item(97), new Item(9736)), 
		DEFENCE(new Item(2432), 30, 75, new Item(99), new Item(239)), 
		ENERGY(new Item(3008), 26, 67, new Item(97), new Item(1975)), 
		FISHING(new Item(2438), 50, 112, new Item(103), new Item(231)), 
		GUTHIX_BALANCE(new Item(7660), 22, 62, new Item(99), new Item(223), new Item(1550), new Item(7650)), 
		MAGIC(new Item(3040), 76, 172, new Item(2483), new Item(3138)), 
		PRAYER(new Item(2434), 38, 87, new Item(99), new Item(231)), 
		RANGING(new Item(2444), 72, 162, new Item(109), new Item(245)), 
		RESTORE(new Item(2430), 22, 62, new Item(97), new Item(223)), 
		SARADOMIN_BREW(new Item(6685), 81, 180, new Item(3002), new Item(6693)), 
		STRENGTH(new Item(113), 12, 50, new Item(95), new Item(225)), 
		SUPER_ANTIPOISON(new Item(2448), 48, 106, new Item(101), new Item(235)), 
		SUPER_ATTACK(new Item(2436), 45, 100, new Item(101), new Item(221)), 
		SUPER_COMBAT(new Item(12695), 90, 150, new Item(111), new Item(2436), new Item(2440), new Item(2442)), 
		SUPER_DEFENCE(new Item(2442), 66, 150, new Item(107), new Item(239)), 
		SUPER_ENERGY(new Item(3016), 52, 117, new Item(103), new Item(2970)), 
		SUPER_RESTORE(new Item(3024), 63, 142, new Item(3004), new Item(223)), 
		SUPER_STRENGTH(new Item(2440), 55, 125, new Item(105), new Item(225)), 
		WEAPON_POISON(new Item(187), 60, 137, new Item(105), new Item(241)), 
		WEAPON_POISON_PLUS(new Item(5937), 73, 165, new Item(6124), new Item(6016), new Item(223)), 
		WEAPON_POISON_PLUS_PLUS(new Item(5940), 82, 190, new Item(5935), new Item(2398), new Item(6018)), 
		ZAMORAK_BREW(new Item(2450), 78, 175, new Item(111), new Item(247)),
		
		BASTION(new Item(22461), 80, 155, new Item(22443), new Item(245)), 
		BATTLEMAGE(new Item(22449), 80, 155, new Item(22443), new Item(3138)), 
		
		EGNOIL(new Item(23884), 70, 1, new Item(23881), new Item(23866, 10)),
		
		;

		/**
		 * The primary ingredient required
		 */
		private final Item primary;

		/**
		 * An array of {@link Item} objects that represent the ingredients
		 */
		private final Item[] ingredients;

		/**
		 * The item received from combining the ingredients
		 */
		private final Item result;

		/**
		 * The level required to make this potion
		 */
		private final int level;

		/**
		 * The experience gained from making this potion
		 */
		private final int experience;

		/**
		 * Creates a new in-game potion that will be used in herblore
		 * 
		 * @param result the result from combining ingredients
		 * @param level the level required
		 * @param experience the experience
		 * @param ingredients the ingredients to make the result
		 */
		private FinishedPotions(Item result, int level, int experience, Item primary, Item... ingredients) {
			this.result = result;
			this.level = level;
			this.experience = experience;
			this.primary = primary;
			this.ingredients = ingredients;
		}

		/**
		 * The result from combining the ingredients
		 * 
		 * @return the result
		 */
		public Item getResult() {
			return result;
		}

		/**
		 * The level required to combine the ingredients
		 * 
		 * @return the level required
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * The total amount of experience gained in the herblore skill
		 * 
		 * @return the experience gained
		 */
		public int getExperience() {
			return experience;
		}

		/**
		 * An array of {@link Item} objects that represent the ingredients required to create this potion.
		 * 
		 * @return the ingredients required
		 */
		public Item[] getIngredients() {
			return ingredients;
		}

		/**
		 * The primary ingredient required for the potion
		 * 
		 * @return the primary ingredient
		 */
		public Item getPrimary() {
			return primary;
		}
	}

}