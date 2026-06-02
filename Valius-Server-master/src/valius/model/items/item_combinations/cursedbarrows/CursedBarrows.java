package valius.model.items.item_combinations.cursedbarrows;

import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | 6:25:53 a.m. | Aug. 25, 2019
 *
 */

public class CursedBarrows {

	/*
	 * An array of all boxes, barrows items and cursed barrows items to use for creation
	 */
	private static int[][] CURSED_IDS = {
			//box id, regular item id, cursed item id
			
			//Ahrims
			{33658, 4708, 33634},
			{33658, 4710, 33635},
			{33658, 4712, 33636},
			{33658, 4714, 33637},
			
			//Dharoks
			{33659, 4716, 33638},
			{33659, 4718, 33639},
			{33659, 4720, 33640},
			{33659, 4722, 33641},
			
			//Guthans
			{33660, 4724, 33642},
			{33660, 4726, 33643},
			{33660, 4728, 33644},
			{33660, 4730, 33645},
			
			//Karils
			{33661, 4732, 33646},
			{33661, 4734, 33647},
			{33661, 4736, 33648},
			{33661, 4738, 33649},
			
			//Torags
			{33662, 4745, 33650},
			{33662, 4747, 33651},
			{33662, 4749, 33652},
			{33662, 4751, 33653},
			
			//Veracs
			{33663, 4753, 33654},
			{33663, 4755, 33655},
			{33663, 4757, 33656},
			{33663, 4759, 33657},
	};
	

	 //Creates a Cursed barrows item
	public static void cursedBarrowsCreation(Player player) {
		for (int i = 0; i < CURSED_IDS.length; i++) {
			if (player.getItems().playerHasItem(CURSED_IDS[i][0], 1) && player.getItems().playerHasItem(CURSED_IDS[i][1], 1)) {
				player.getItems().deleteItem(CURSED_IDS[i][0], 1);
				player.getItems().deleteItem(CURSED_IDS[i][1], 1);
				player.getItems().addItemUnderAnyCircumstance(CURSED_IDS[i][2], 1);
				player.sendMessage("You create a Cursed barrows item.");
				return;
			}
		}
	}
}
