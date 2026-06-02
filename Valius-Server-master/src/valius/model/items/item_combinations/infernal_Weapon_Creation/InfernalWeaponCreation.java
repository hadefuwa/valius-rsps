package valius.model.items.item_combinations.infernal_Weapon_Creation;

import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;

/**
 * 
 * @author Divine | 8:01:19 p.m. | Oct. 14, 2019
 *
 */

public class InfernalWeaponCreation {

	private static int[][] COMBINE_IDS = {
			//box/kit id, regular item id, upgraded item id
			//Items cannot be undone once created
			
			//Infernal Weapons
			{33765, 33277, 33761},
			{33765, 33278, 33762},
			{33765, 33281, 33763},
			{33765, 33282, 33764},
			
	};
	

	 //creates an item from the array above
	public static void combineItem(Player player) {
		for (int i = 0; i < COMBINE_IDS.length; i++) {
			if (player.getItems().playerHasItem(COMBINE_IDS[i][0], 1) && player.getItems().playerHasItem(COMBINE_IDS[i][1], 1)) {
				player.getItems().deleteItem(COMBINE_IDS[i][0], 1);
				player.getItems().deleteItem(COMBINE_IDS[i][1], 1);
				player.getItems().addItemUnderAnyCircumstance(COMBINE_IDS[i][2], 1);
				player.getItems();
				player.getDH().sendItemStatement("You use your Infernal weapon enhancement kit and upgrade your weapon", COMBINE_IDS[i][2]);
				return;
			}
		}
	}
}
