package valius.model.entity.npc.drops;

import java.util.stream.IntStream;

import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.util.Misc;
import valius.world.World;

/*
 * @author Patrity
 */

public class Points {
	/*
	 * NPCs that SHOULD award boss points
	 */
	static int[] bosses = { 
			/* Misc bosses */
			6619, 6618, 6615, 6766, 963, 965, 5890, 6609, 319, 6610, 6611, 5779, 6342, 2205, 2215, 3129, 3162, 2054, 2265, 2266, 2267,
			7544, 7604, 7605, 7606, 6342, 239, 2042, 2043, 2044, 5862, 5866, 7323, 494, 239, 499, 5462, 6014, 8615, 8373, 3833, 9021,
			/* Rune and Addy dragons */
			8030, 8031,
			/* Godwars minions */ 
			//2206, 2207, 2208, 3130, 3131, 3132, 2216, 2217, 2218, 3163, 3164, 3165
	};
	/*
	 * NPCs that should NOT award PVM Points
	 */
	static int [] noPoints = {2790, 2692};

	public static void applyPvmPoints(Player player, NPC npc, Location location) {
		if (IntStream.of(noPoints).noneMatch(mobId -> npc.npcType == mobId)) {
			if (Misc.random(10) == 5) {
				player.sendMessage("@pur@You now have " + player.pvmPoints + " PVM Points.");
			}
			player.pvmPoints += 1;
			if (Misc.random(150) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 13307, location.getX(), location.getY(),
						location.getZ(), Misc.random(50, 250), player.getIndex());
				player.sendMessage("@blu@The monster drops some Blood Money.");
			}

		}

	}
	public static void applyBossPoints(Player player, NPC npc, int npcLevel) {
		if (IntStream.of(bosses).anyMatch(id -> id == npc.npcType)) {
			if (Misc.random(50) == 5) {
				player.bossPoints += 1;
				player.sendMessage("@red@You receive double boss points for this kill!");
			}
			player.bossPoints += 1;
			player.sendMessage("@pur@You now have " + player.bossPoints + " Boss Points.");
		}
	}

}
