/**
 * 
 */
package valius.content.cluescroll;

import valius.content.PlayerEmotes;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.model.entity.player.Player;
import valius.model.items.Item;

/**
 * 
 * @author ReverendDread
 * Oct 14, 2019
 */
public class ClueScrollHandler {
	
	/**
	 * Called when reading a clue scroll.
	 * @param player
	 * 			The player.
	 * @param item
	 * 			The clue scroll item.
	 * @return
	 * 			If this action was handled.
	 */
	public static boolean readClue(Player player, Item item) {
		ClueScrollRiddle scroll = ClueScrollRiddle.getScrollForId(item.getId());
		if (scroll != null) {
			player.getPA().showInterface(6965);
			for (int index = 0; index < scroll.getRiddle().length; index++) {
				player.getPA().sendFrame126(scroll.getRiddle()[index], 6968 + index);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Called when digging with a spade.
	 * @param player
	 * 			The player.
	 * @return
	 * 			If this action was handled.
	 */
	public static boolean dig(Player player) {
		ClueScrollRiddle scroll = ClueScrollRiddle.getScrollForLocation(player.getLocation());
		if (scroll != null && player.getItems().playerHasItem(scroll.getId())) {
			player.getItems().deleteItem(scroll.getId(), 1);
			player.getItems().addItem(scroll.getDifficulty().getCasket(), 1);
			Achievements.increase(player, AchievementType.CLUES, 1);
			return true;
		}
		return false;
	}

	/**
	 * @param player
	 */
	public static void handleEmote(Player player, PlayerEmotes.PLAYER_ANIMATION_DATA emote) {
		ClueScrollEmote scroll = ClueScrollEmote.getScrollForLocation(player.getLocation());
		if (scroll != null && emote.equals(scroll.getEmote()) && player.getItems().playerHasItem(scroll.getId())) {
			player.getItems().deleteItem(scroll.getId(), 1);
			player.getItems().addItem(scroll.getDifficulty().getCasket(), 1);
			Achievements.increase(player, AchievementType.CLUES, 1);
		}
	}
	
}
