package valius.model.entity.player.skills.agility;

import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;
import valius.world.World;

/**
 * Mark of grace
 * 
 * @author Matt
 *
 */
public class MarkOfGrace {

	private static int MARK_OF_GRACE = 11849;
	private static int[][] SEERS_COORDINATES = { 
			{ 2728, 3495, 3 }, 
			{ 2707, 3492, 2 }, 
			{ 2713, 3479, 2 },
			{ 2698, 3463, 2 } 
	};
	private static int[][] VARROCK_COORDINATES = { 
			{ 3219, 3418, 3 }, 
			{ 3202, 3417, 3 }, 
			{ 3195, 3416, 1 },
			{ 3196, 3404, 3 }, 
			{ 3193, 3393, 3 }, 
			{ 3205, 3403, 3 }, 
			{ 3218, 3395, 3 }, 
			{ 3240, 3411, 3 } 
	};
	private static int[][] ARDOUGNE_COORDINATES = { 
			{ 2671, 3303, 3 }, 
			{ 2663, 3318, 3 }, 
			{ 2655, 3318, 3 },
			{ 2653, 3312, 3 }, 
			{ 2651, 3307, 3 }, 
			{ 2653, 3302, 3 }, 
			{ 2656, 3297, 3 }, 
			{ 2668, 3297, 0 } 
	};

	public static void spawnMarks(Player player, String location) {
		int chance = 0;
		
		switch (location) {
		case "ARDOUGNE":
			int ardougne = 
						  player.getRechargeItems().hasItem(13121) ? 40
						: player.getRechargeItems().hasItem(13122) ? 45
						: player.getRechargeItems().hasItem(13123) ? 50
						: player.getRechargeItems().hasItem(13124) || player.getRechargeItems().hasItem(20760) ? 60 : 30;
			chance = player.getSkills().getLevel(Skill.AGILITY) / ardougne;
			break;
			
		case "SEERS":
			int seers = player.getRechargeItems().hasItem(13137) ? 40 
					  : player.getRechargeItems().hasItem(13138) ? 45
					  : player.getRechargeItems().hasItem(13139) ? 50
					  : player.getRechargeItems().hasItem(13140) ? 60 : 30;
			chance = player.getSkills().getLevel(Skill.AGILITY) / seers;
			break;
			
		case "VARROCK":
			int varrock = player.getRechargeItems().hasItem(13104) ? 40 
					 	: player.getRechargeItems().hasItem(13105) ? 45
					 	: player.getRechargeItems().hasItem(13106) ? 50
					 	: player.getRechargeItems().hasItem(13107) ? 60 : 30;
			chance = player.getSkills().getLevel(Skill.AGILITY) / varrock;
			break;
		}
		int i = Misc.random(location == "SEERS" ? SEERS_COORDINATES.length - 1 : location == "ARDOUGNE" ? ARDOUGNE_COORDINATES.length - 1 : VARROCK_COORDINATES.length - 1);
		int x = location == "SEERS" ? SEERS_COORDINATES[i][0] : location == "ARDOUGNE" ? ARDOUGNE_COORDINATES[i][0] : VARROCK_COORDINATES[i][0];
		int y = location == "SEERS" ? SEERS_COORDINATES[i][1] : location == "ARDOUGNE" ? ARDOUGNE_COORDINATES[i][1] : VARROCK_COORDINATES[i][1];
		int z = location == "SEERS" ? SEERS_COORDINATES[i][2] : location == "ARDOUGNE" ? ARDOUGNE_COORDINATES[i][2] : VARROCK_COORDINATES[i][2];
		if (Misc.random(chance) == 0) {
			if (System.currentTimeMillis() - player.lastMarkDropped < 3000) {
				return;
			}
			World.getWorld().getItemHandler().createGroundItem(player, MARK_OF_GRACE, x, y, z, 1, player.getIndex());
			player.lastMarkDropped = System.currentTimeMillis();
		}
	}

}
