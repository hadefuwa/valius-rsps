package valius.content;

import valius.model.entity.player.Player;
import valius.util.Misc;

/**
 * Opening resource packs
 * @author Matt
 */
public enum Packs {
		/**
		 * Packs data
		 * id, itemId, amount
		 */
		EYE_OF_NEWT_PACK(12859, new int[]{222} ,100),
		UNFINISHED_BROAD_ARROW_PACK(11885,new int[]{11874},100),
		COMPOST_PACK(19704,new int[]{6033},100),
		AIR_PACK(12728, new int[]{556}, 100), 
		WATER_PACK(12730, new int[]{555}, 100), 
		EARTH_PACK(12732, new int[]{557}, 100), 
		FIRE_PACK(12734, new int[]{554}, 100), 
		CHAOS_PACK(12738, new int[]{562}, 100), 
		FEATHER_PACK(11881, new int[]{314}, 100), 
		VIAL_OF_WATER_PACK(11879, new int[]{228}, 100), 
		EMPTY_VIAL_PACK(11877, new int[]{230}, 100), 
		BAIT_PACK(11883, new int[]{313}, 100), 
		SOFT_CLAY_PACK(12009, new int[]{1762}, 50), 
		BIRD_SNARE_PACK(12740, new int[]{10007}, 50), 
		BOX_TRAP_PACK(12742, new int[]{10009}, 50), 
		MAGIC_IMP_PACK(12744, new int[]{-1}, -1),
		AMYLASE_PACK(12641, new int[]{12640}, 100),
		EASY_CLUE_BOTTLE(13648, new int[]{10180, 10182, 10184, 10186, 10188}, 1),
		MEDIUM_CLUE_BOTTLE(13649, new int[]{10254, 10256, 10258, 10260, 10262}, 1),
		HARD_CLUE_BOTTLE(13650, new int[]{10234, 10236, 10238, 10240, 10242, 10244, 10246, 10248}, 1),
		EASY_CLUE_GEODE(20358, new int[]{10180, 10182, 10184, 10186, 10188}, 1),
		MEDIUM_CLUE_GEODE(20360, new int[]{10254, 10256, 10258, 10260, 10262}, 1),
		HARD_CLUE_GEODE(20362, new int[]{10234, 10236, 10238, 10240, 10242, 10244, 10246, 10248}, 1),
		EASY_CLUE_NEST(19712, new int[]{10180, 10182, 10184, 10186, 10188}, 1),
		MEDIUM_CLUE_NEST(19714, new int[]{10254, 10256, 10258, 10260, 10262}, 1),
		HARD_CLUE_NEST(19716, new int[]{10234, 10236, 10238, 10240, 10242, 10244, 10246, 10248}, 1),
		UNFINISIHED_BROAD_BOLT_PACK(11887, new int[]{11876}, 100);

		private int packId;
		private int[] itemId;
		private int itemAmount;

		public int getPackId() {
			return packId;
		}

		public int[] getItemId() {
			return  itemId;
		}

		public int getItemAmount() {
			return itemAmount;
		}

		Packs(int packId, int[] itemId, int itemAmount) {
			this.packId = packId;
			this.itemId = itemId;
			this.itemAmount = itemAmount;
		}
	
	public static void openPack(final Player player, int item) {
		for (Packs pack : Packs.values()) {
			String name = pack.name().toLowerCase().replaceAll("_", " ");
			if (pack.getPackId() == item) {
				if (player.getItems().playerHasItem(item)) {
					player.getItems().deleteItem(pack.getPackId(), 1);
					player.getItems().addItem(Misc.randomElementOf(pack.itemId), pack.getItemAmount());
					player.sendMessage("You opened the " + name + ".");
				}
			}
		}
	}

	public static void openSuperSet(Player c, int itemId){
		if (itemId == 13066) {
				if (c.getItems().freeSlots() < 3) {
					c.sendMessage("You need at least three slots to do this.");
					return;
				}
				int random = Misc.random(2);
				int amount = c.getItems().getItemAmount(itemId);
				for (int i = 0; i < amount; amount--) {
					c.getItems().deleteItem2(itemId, 1);
					if (random == 0) {
						c.getItems().addItemUnderAnyCircumstance(12696, 5);
						c.sendMessage("You break the combat potion set into one super combat potion.");
					} else {
						c.getItems().addItemUnderAnyCircumstance(2442, 10);
						c.getItems().addItemUnderAnyCircumstance(2438, 10);
						c.getItems().addItemUnderAnyCircumstance(2444, 10);
						c.sendMessage("You break the combat potion set into three individual potions.");
					}
				}
		}
	}


}