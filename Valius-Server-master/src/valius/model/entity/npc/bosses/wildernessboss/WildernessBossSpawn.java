package valius.model.entity.npc.bosses.wildernessboss;


import lombok.Getter;
import valius.model.Location;
import valius.model.entity.player.Boundary;
import valius.util.Misc;

/**
 * 
 * @author Created by Crank Mar 9, 2019
 *
 * 6:54:58 PM
 */
@Getter
	public enum WildernessBossSpawn {
		FOURTYSEVEN_WILDERNESS(3322, 3892, "Level 47 wilderness"),
		FOURTY_WILDERNESS(3165, 3833, "Level 40 wilderness"),
		FOURTYSIX_WILDERNESS(3043, 3884, "Level 46 wilderness west of rune rocks"),
		BANDIT_CAMP(3020, 3666, "Level 19 wilderness east of the bandit camp");
	
	private WildernessBossSpawn(int x, int y, String locationName) {
		this.x = x;
		this.y = y;
		this.locationName = locationName;
	}
	
	private Boundary boundary;
	private int x, y;
	
	private String locationName;
	
	public static WildernessBossSpawn generateLocation() {
		return WildernessBossSpawn.values()[Misc.random(0, WildernessBossSpawn.values().length - 1)];
	}

	public Location getAsLocation() {
		return Location.of(x, y);
	}
	
}
