/**
 * 
 */
package valius.content.falling_stars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import valius.util.Misc;

/**
 * @author Patrity
 *
 */
@Getter
@AllArgsConstructor
public enum StarSpawns {
	THIEVING(3074, 3477, 0, "in the Thieving area!"),
	LANDS_END(1514, 3442, 0, "North of Lands End!"),
	PARK(3010, 3384, 0, "in Falador Park!"),
	ALKHARID(3288, 3182, 0, "in Al Kharid!"),
	EDGEVILLE(3097, 3511, 1, "on top of Edgeville castle!"),
	ZEAH(1776, 3700, 0, "near the Zeah Rock Crabs!"),
	LUMB(3245, 3197, 0, "In the Lumbridge Graveyard!"),
	CAMELOT(2728, 3477, 0, "South of Camelot Bank!"),
	CHAOS(3232, 3616, 0, "near the Chaos Altar!"),
	WC_GUILD(1670, 3517, 0, "North-East of the Woodcutting Guild!")
	
	;
	
	int x, y, z;
	private String location;
	
	public static StarSpawns random() {
		return StarSpawns.values()[Misc.random(0, StarSpawns.values().length - 1)];
	}

}
