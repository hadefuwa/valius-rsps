package valius.model.entity.player.skills.herblore;

import valius.model.items.Item;

public enum Potion {
	AGILITY(new Item(3032), 34, 80, new Item(2998), new Item(2152)), 
	ANTI_VENOM(new Item(12905), 87, 120, new Item(5954), new Item(12934, 15)), 
	ANTI_VENOM_PLUS(new Item(12913), 94, 125, new Item(12907), new Item(269)), 
	ANTIDOTE_PLUS(new Item(5943), 68, 155, new Item(2998), new Item(6049)), 
	ANTIDOTE_PLUS_PLUS(new Item(5952), 79, 177, new Item(259), new Item(6051)), 
	STAMINA(new Item(12625), 77, 152, new Item(3016), new Item(12640, 3)), 
	ANTIFIRE(new Item(2452), 69, 157, new Item(2481), new Item(241)),
	ANTIPOISON(new Item(2446), 5, 37, new Item(251), new Item(235)), 
	ATTACK(new Item(2428), 3, 25, new Item(249), new Item(221)), 
	COMBAT(new Item(9739), 36, 84, new Item(255), new Item(9736)), 
	DEFENCE(new Item(2432), 30, 75, new Item(257), new Item(239)), 
	ENERGY(new Item(3008), 26, 67, new Item(255), new Item(1975)), 
	FISHING(new Item(2438), 50, 112, new Item(261), new Item(231)), 
	GUTHIX_BALANCE(new Item(7660), 22, 62, new Item(257), new Item(223), new Item(1550), new Item(7650)), 
	
	PRAYER(new Item(2434), 38, 87, new Item(257), new Item(231)), 
	RANGING(new Item(2444), 72, 162, new Item(267), new Item(245)), 
	RESTORE(new Item(2430), 22, 62, new Item(255), new Item(223)), 
	SARADOMIN_BREW(new Item(6685), 81, 180, new Item(2998), new Item(6693)), 
	STRENGTH(new Item(113), 12, 50, new Item(253), new Item(225)), 
	SUPER_ANTIPOISON(new Item(2448), 48, 106, new Item(259), new Item(235)), 
	SUPER_ATTACK(new Item(2436), 45, 100, new Item(259), new Item(221)), 
	SUPER_COMBAT(new Item(12695), 90, 150, new Item(269), new Item(2436), new Item(2440), new Item(2442)), 
	SUPER_DEFENCE(new Item(2442), 66, 150, new Item(265), new Item(239)), 
	SUPER_ENERGY(new Item(3016), 52, 117, new Item(261), new Item(2970)), 
	SUPER_RESTORE(new Item(3024), 63, 142, new Item(3000), new Item(223)), 
	SUPER_STRENGTH(new Item(2440), 55, 125, new Item(263), new Item(225)), 
	WEAPON_POISON(new Item(187), 60, 137, new Item(263), new Item(241)), 
	WEAPON_POISON_PLUS(new Item(5937), 73, 165, new Item(6124), new Item(6016), new Item(223)), 
	WEAPON_POISON_PLUS_PLUS(new Item(5940), 82, 190, new Item(5935), new Item(2398), new Item(6018)), 
	BASTION(new Item(3040), 76, 155, new Item(22443), new Item(245)), 
	BATTLEMAGE(new Item(3040), 76, 155, new Item(22443), new Item(3138)), 
	ZAMORAK_BREW(new Item(2450), 78, 175, new Item(269), new Item(247));

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
	private Potion(Item result, int level, int experience, Item primary, Item... ingredients) {
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