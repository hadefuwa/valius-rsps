/**
 * 
 */
package valius.model.entity.npc.bosses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.npc.bosses.EventBoss.EventBossSpawns;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.GlobalMessages.MessageType;
import valius.util.Misc;

/**
 * Why did i make a class for this
 * @author ReverendDread
 * Oct 22, 2019
 */
public class ShadowBeast {

	private static NPC npc;
	
	private static final int[] WILDERNESS_BOSSES = {
		6503,
		6609,
		6618,
		6619,
		6504,
		6610,
		6615,
		6611,
		2054,
		6505,
	};
		
	public static void spawn(int id) {
		if ((npc == null || npc.isDead) && isBoss(id) && Misc.random(100) == 1) {
			spawn = ShadowBeastSpawn.generateLocation();
			npc = NPCHandler.spawnNpc(3371, spawn.getLoc().getX(), spawn.getLoc().getY(), spawn.getLoc().getZ(), 1, npc.getDefinition().getNpcHealth(), 5000, 255, 255);		
			GlobalMessages.send("The Shadow Beast has been summoned in the wilderness.", MessageType.EVENT);
		}
	}
	
	private static boolean isBoss(int id) {
		for (int bossId : WILDERNESS_BOSSES) {
			if (id == bossId)
				return true;
		}
		return false;
	}
	
	@Getter
	private static ShadowBeastSpawn spawn;
	
	public static ShadowBeastSpawn generateLocation() {
		return ShadowBeastSpawn.values()[Misc.random(0, ShadowBeastSpawn.values().length - 1)];
	}
	
	@AllArgsConstructor @Getter
	enum ShadowBeastSpawn {
		
		EAST_WILDERNESS(Location.of(3364, 3742, 0)),
		DEMONIC_RUINS(Location.of(3254, 3883, 0)),
		PIRATE_HIDEOUT(Location.of(3020, 3925, 0)),
		
		;
		
		public static ShadowBeastSpawn generateLocation() {
		return ShadowBeastSpawn.values()[Misc.random(0, ShadowBeastSpawn.values().length - 1)];
	}

		private Location loc;
		
	}
	
}
