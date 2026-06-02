package valius.model.entity.npc.bosses.EventBoss;

import lombok.Getter;
import valius.model.entity.player.Boundary;
import valius.util.Misc;

@Getter
public enum EventBossSpawns {
		DESERT_BANDITS(Boundary.EVENT_BANDITS, 3175, 3005, "North of Desert Bandits"),
		BARROWS(Boundary.EVENT_BARROWS, 3538, 3310, "West of Barrows"),
		TAVERLY(Boundary.EVENT_TAVERLY, 2937, 3424, "South of Taverly"), 
		CATHERBY(Boundary.EVENT_CATHERBY, 2794, 3452, "North of Catherby"), 
		NEITIZNOT(Boundary.EVENT_NEITIZNOT, 2329, 3831, "North-East of Neitiznot"),
		
		;
	private EventBossSpawns(Boundary boundary, int x, int y, String locationName) {
		this.x = x;
		this.y = y;
		this.locationName = locationName;
		this.boundary = boundary;
	}

	private Boundary boundary;
	private int x, y;
	
	private String locationName;

	public static EventBossSpawns generateLocation() {
		return EventBossSpawns.values()[Misc.random(0, EventBossSpawns.values().length - 1)];
	}
}