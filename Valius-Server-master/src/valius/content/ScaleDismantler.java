package valius.content;

import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;
import valius.util.Misc;

/**
 * 
 * @author Divine | 3:02:24 a.m. | Aug. 16, 2019
 *
 */

//Handles items dismantled for zulrah scales
public class ScaleDismantler {

/*
 * An Array of Zulrah items that can be dismantled
 */
	private static final int[][] DISMANTABLES = {
			{ 12922, 12934, 20000 },
			{ 12924, 12934, 20000 },
			{ 12927, 12934, 20000 },
			{ 12929, 12934, 20000 },
			{ 12932, 12934, 20000 }
			};// itemid, scaleid, scale amount

	public static void dismantle(Player player) {
		for (int i = 0; i < DISMANTABLES.length; i++) {

			if (player.getItems().playerHasItem(DISMANTABLES[i][0])) {
				player.getItems().deleteItem(DISMANTABLES[i][0], 1);
				player.getItems().addItemUnderAnyCircumstance(DISMANTABLES[i][1], DISMANTABLES[i][2]);
				//player.sendMessage("You Dismantle Your" + ItemAssistant.getItemName(Misc.stringToInt(DISMANTABLES[i][0] + ".")));
			}
		}
	}

}