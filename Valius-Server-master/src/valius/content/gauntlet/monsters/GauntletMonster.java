/**
 * 
 */
package valius.content.gauntlet.monsters;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.content.gauntlet.GauntletRoom;
import valius.content.gauntlet.GauntletType;
import valius.model.entity.npc.NPCDefinitions;
import valius.util.Misc;

/**
 * @author ReverendDread | RSPSi
 * Aug 24, 2019
 */
@RequiredArgsConstructor @Getter
@Slf4j
public enum GauntletMonster {

	CRYSTALLINE_RAT(9026, 14, 70, GauntletType.NORMAL, GauntletMonsterType.NORMAL),
	CRYSTALLINE_SPIDER(9027, 12, 65, GauntletType.NORMAL, GauntletMonsterType.NORMAL),
	CRYSTALLINE_BAT(9028, 16, 60, GauntletType.NORMAL, GauntletMonsterType.NORMAL),
	CRYSTALLINE_UNICORN(9029, 35, 55, GauntletType.NORMAL, GauntletMonsterType.HARD),
	CRYSTALLINE_SCORPION(9030, 45, 50, GauntletType.NORMAL, GauntletMonsterType.HARD),
	CRYSTALLINE_WOLF(9031, 60, 53, GauntletType.NORMAL, GauntletMonsterType.HARD),
	CRYSTALLINE_BEAR(9032, 98, 5, GauntletType.NORMAL, GauntletMonsterType.MINI_BOSS),
	CRYSTALLINE_DRAGON(9033, 98, 5, GauntletType.NORMAL, GauntletMonsterType.MINI_BOSS),
	CRYSTALLINE_DARK_BEAST(9034, 98, 5, GauntletType.NORMAL, GauntletMonsterType.MINI_BOSS),
	
	CORRUPTED_RAT(9040, 1, 70, GauntletType.CORRUPTED, GauntletMonsterType.NORMAL),
	CORRUPTED_SPIDER(9041, 1, 65, GauntletType.CORRUPTED, GauntletMonsterType.NORMAL),
	CORRUPTED_BAT(9042, 1, 60, GauntletType.CORRUPTED, GauntletMonsterType.NORMAL),
	CORRUPTED_UNICORN(9043, 1, 55, GauntletType.CORRUPTED, GauntletMonsterType.HARD),
	CORRUPTED_SCORPION(9044, 1, 50, GauntletType.CORRUPTED, GauntletMonsterType.HARD),
	CORRUPTED_WOLF(9045, 1, 45, GauntletType.CORRUPTED, GauntletMonsterType.HARD),
	CORRUPTED_BEAR(9046, 1, 30, GauntletType.CORRUPTED, GauntletMonsterType.MINI_BOSS),
	CORRUPTED_DRAGON(9047, 1, 20, GauntletType.CORRUPTED, GauntletMonsterType.MINI_BOSS),
	CORRUPTED_DARK_BEAST(9048, 1, 15, GauntletType.CORRUPTED, GauntletMonsterType.MINI_BOSS);
	
	private final int id;
	private final int hp; 
	private final int chance;
	private final GauntletType type;
	private final GauntletMonsterType monsterType;

	/**
	 * Gets a random monster based on the gauntlet type.
	 * @param type
	 * @return
	 */
	public static final List<GauntletMonster> getRandomSpawn(GauntletRoom room, GauntletType type) {
		List<GauntletMonster> monsters = Stream.of(values())
		.filter(monster -> monster.getMonsterType() == getTypeForChance(room.getMonster_chance()))
		.filter(monster -> (monster.getMonsterType() != GauntletMonsterType.MINI_BOSS || monster.getMonsterType() == GauntletMonsterType.MINI_BOSS && isOuterBounds(room.getX(), room.getY())))
		.filter(monster -> monster.getType() == type)
		.collect(Collectors.toList());
		return monsters;		
	}
	
	public static int getMaxiumumForType(GauntletMonsterType monster) {
		switch (monster) {
			case HARD:
				return 2;
			case MINI_BOSS:
				return 1;
			case NORMAL:
				return 3;
			default:
				return 1;
		}
	}
	
	public static GauntletMonsterType getTypeForChance(double chance) {
		if (chance >= 0.75D) {
			return GauntletMonsterType.MINI_BOSS;
		} else if (chance >= 0.55D) {
			return GauntletMonsterType.HARD;
		} else if (chance >= 0.25D) {
			return GauntletMonsterType.NORMAL;
		} else {
			return GauntletMonsterType.NONE;
		}
	}
	
	public static boolean isOuterBounds(int x, int y) {
		return (x == 0 && y >= 0) || (x >= 0 && y == 0) || (x == 7 && y >= 0) || (y == 7 && x >= 0);
	}
	
}
