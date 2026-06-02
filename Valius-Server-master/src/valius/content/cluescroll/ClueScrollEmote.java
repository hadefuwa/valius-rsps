/**
 * 
 */
package valius.content.cluescroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import valius.content.PlayerEmotes;
import valius.model.Location;
import valius.model.entity.player.Boundary;

/**
 * @author ReverendDread
 * Oct 16, 2019
 */
@Getter @AllArgsConstructor
public enum ClueScrollEmote {

	TEST_CLUE(2677, ClueDifficulty.MEDIUM, PlayerEmotes.PLAYER_ANIMATION_DATA.WAVE, Boundary.ofRect(3091, 3490, 4, 4)),
	
	;
	
	private int id; //the scroll id
	private ClueDifficulty difficulty; //the scroll difficulty
	private PlayerEmotes.PLAYER_ANIMATION_DATA emote; //who the fuck named that enum
	private Boundary boundary; //the emote area
	
	public static ClueScrollEmote getScrollForId(int itemId) {
		for (ClueScrollEmote scroll : ClueScrollEmote.values()) {
			if (itemId == scroll.getId()) {
				return scroll;
			}
		}
		return null;
	}
	
	public static ClueScrollEmote getScrollForLocation(Location loc) {
		for (ClueScrollEmote scroll : ClueScrollEmote.values()) {
			if (Boundary.isIn(loc, scroll.getBoundary())) {
				return scroll;
			}
		}
		return null;
	}
	
}
