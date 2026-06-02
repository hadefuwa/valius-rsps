package valius.model.entity.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.util.Misc;

/**
 * 
 * @author Jason http://www.rune-server.org/members/jason
 * @date Mar 2, 2014
 */
public class Boundary {

	int minX, minY, highX, highY;
	int height;

	public Boundary(Rectangle rect) {
		this.minX = rect.x;
		this.minY = rect.y;
		this.highX = rect.x + rect.width;
		this.highY = rect.y + rect.height;
		this.height = -1;
	}
	/**
	 * 
	 * @param minX The south-west x coordinate
	 * @param minY The south-west y coordinate
	 * @param highX The north-east x coordinate
	 * @param highY The north-east y coordinate
	 */
	public Boundary(int minX, int minY, int highX, int highY) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		
		if(minX > highX || minY > highY)
			System.out.println("Boundary incorrect! " + minX + ", " + minY + ", " + highX + ", " + highY);
		height = -1;
	}

	/**
	 * 
	 * @param minX The south-west x coordinate
	 * @param minY The south-west y coordinate
	 * @param highX The north-east x coordinate
	 * @param highY The north-east y coordinate
	 * @param height The height of the boundary
	 */
	public Boundary(int minX, int minY, int highX, int highY, int height) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		this.height = height;
	}

	public int getMinimumX() {
		return minX;
	}

	public int getMinimumY() {
		return minY;
	}

	public int getMaximumX() {
		return highX;
	}

	public int getMaximumY() {
		return highY;
	}
	
	public Location getMinLocation(){
	    return Location.of(minX, minY);
	}
	
	public Location getMaxLocation(){
	    return Location.of(highX, highY);
	}
	
	public static Boundary of(int minX, int minY, int maxX, int maxY) {
		return new Boundary(minX, minY, maxX, maxY);
	}
	
	public static Boundary ofRect(int x, int y, int width, int height) {
		return new Boundary(new Rectangle(x, y, width, height));
	}
	
	public List<Location> getRandomLocations(int count, int height) {
		List<Location> locations = new ArrayList<Location>();
		int lock = 50; //prevents deadlocks
		while (locations.size() < count && lock-- > 0) {
			int x = Misc.random(minX, highX);
			int y = Misc.random(minY, highY);
			Location location = new Location(x, y, height);
			if (!location.getRegion().solidObjectExists(x, y, height) && !locations.contains(location)) {
				locations.add(location);
			}
		}	
		return locations;
	}

	/**
	 * 
	 * @param player The player object
	 * @param boundaries The array of Boundary objects
	 * @return
	 */
	public static boolean isIn(Entity entity, Boundary... boundaries) {
		for (Boundary b : boundaries) {
			if (b.height >= 0) {
				if (entity.getHeight() != b.height) {
					continue;
				}
			}
			if (entity.getX() >= b.minX && entity.getX() <= b.highX && entity.getY() >= b.minY && entity.getY() <= b.highY) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInExclusive(Entity entity, Boundary... boundaries) {
		for (Boundary b : boundaries) {
			if (b.height >= 0) {
				if (entity.getHeight() != b.height) {
					continue;
				}
			}
			if (entity.getX() >= b.minX && entity.getX() < b.highX && entity.getY() >= b.minY && entity.getY() < b.highY) {
				return true;
			}
		}
		return false;
	}
	

	public static boolean isIn(Location location, Boundary... boundaries) {
		for (Boundary b : boundaries) {
			if (b.height >= 0) {
				if (location.getZ() != b.height) {
					continue;
				}
			}
			if (location.getX() >= b.minX && location.getX() <= b.highX && location.getY() >= b.minY && location.getY() <= b.highY) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param npc The npc object
	 * @param boundaries The boundary object
	 * @return
	 */
	public static boolean isIn(NPC npc, Boundary boundaries) {
		if (boundaries.height >= 0) {
			if (npc.getHeight() != boundaries.height) {
				return false;
			}
		}
		if(npc.getSize() > 1) {
			return IntStream.range(npc.getX(), npc.getX() + npc.getSize()).anyMatch(x -> x >= boundaries.minX && x <= boundaries.highX) &&
					IntStream.range(npc.getY(), npc.getY() + npc.getSize()).anyMatch(y -> y >= boundaries.minY && y <= boundaries.highY);
		}
		return npc.getX() >= boundaries.minX && npc.getX() <= boundaries.highX && npc.getY() >= boundaries.minY && npc.getY() <= boundaries.highY;
	}

	public static boolean isIn(NPC npc, Boundary[] boundaries) {
		for (Boundary boundary : boundaries) {
			if(isIn(npc, boundary))
				return true;
		}
		return false;
	}

	public static boolean isInSameBoundary(Player player1, Player player2, Boundary[] boundaries) {
		Optional<Boundary> boundary1 = Arrays.asList(boundaries).stream().filter(b -> isIn(player1, b)).findFirst();
		Optional<Boundary> boundary2 = Arrays.asList(boundaries).stream().filter(b -> isIn(player2, b)).findFirst();
		if (!boundary1.isPresent() || !boundary2.isPresent()) {
			return false;
		}
		return Objects.equals(boundary1.get(), boundary2.get());
	}

	public static int entitiesInArea(Boundary boundary) {
		int i = 0;
		for (Player player : PlayerHandler.players)
			if (player != null)
				if (isIn(player, boundary))
					i++;
		return i;
	}

	/**
	 * Returns the centre point of a boundary as a {@link Location}
	 * 
	 * @param boundary The boundary of which we want the centre.
	 * @return The centre point of the boundary, represented as a {@link Location}.
	 */
	public static Location centre(Boundary boundary) {
		int x = (boundary.minX + boundary.highX) / 2;
		int y = (boundary.minY + boundary.highY) / 2;
		if (boundary.height >= 0) {
			return new Location(x, y, boundary.height);
		} else {
			return new Location(x, y, 0);
		}
	}
	
	/**
	 * Diary locations
	 */
	public static final Boundary VARROCK_BOUNDARY = new Boundary(3136, 3349, 3326, 3519);
	public static final Boundary ARDOUGNE_BOUNDARY = new Boundary(2432, 3259, 2690, 3380);
	public static final Boundary ARDOUGNE_ZOO_BRIDGE_BOUNDARY = new Boundary(2611, 3270, 2614, 3280);
	public static final Boundary FALADOR_BOUNDARY = new Boundary(2935, 3310, 3066, 3394);
	public static final Boundary CRAFTING_GUILD_BOUNDARY = new Boundary(2925, 3274, 2944, 3292);
	public static final Boundary TAVERLY_BOUNDARY = new Boundary(2866, 3388, 2938, 3517);
	public static final Boundary LUMRIDGE_BOUNDARY = new Boundary(3142, 3139, 3265, 3306);
	public static final Boundary DRAYNOR_DUNGEON_BOUNDARY = new Boundary(3084, 9623, 3132, 9700);
	public static final Boundary AL_KHARID_BOUNDARY = new Boundary(3263, 3136, 3388, 3328);
	public static final Boundary DRAYNOR_MANOR_BOUNDARY = new Boundary(3074, 3311, 3131, 3388);
	public static final Boundary DRAYNOR_BOUNDARY = new Boundary(3065, 3216, 3136, 3292);
	public static final Boundary KARAMJA_BOUNDARY = new Boundary(2816, 3139, 2965, 3205);
	public static final Boundary BRIMHAVEN_BOUNDARY = new Boundary(2683, 3138, 2815, 3248);
	public static final Boundary BRIMHAVEN_DUNGEON_BOUNDARY = new Boundary(2627, 9415, 2745, 9600);
	public static final Boundary TZHAAR_CITY_BOUNDARY = new Boundary(2368, 5056, 2495, 5183);
	public static final Boundary FOUNTAIN_OF_RUNE_BOUNDARY = new Boundary(3367, 3888, 3380, 3899);
	public static final Boundary DEMONIC_RUINS_BOUNDARY = new Boundary(3279, 3879, 3294, 3893);
	public static final Boundary WILDERNESS_GOD_WARS_BOUNDARY = new Boundary(3008, 10112, 3071, 10175);
	public static final Boundary RESOURCE_AREA_BOUNDARY = new Boundary(3173, 3923, 3197, 3945);
	public static final Boundary CANIFIS_BOUNDARY = new Boundary(3471, 3462, 3516, 3514);
	public static final Boundary CATHERBY_BOUNDARY = new Boundary(2767, 3392, 2864, 3521);
	public static final Boundary SEERS_BOUNDARY = new Boundary(2574, 3393, 2766, 3517);
	public static final Boundary RELLEKKA_BOUNDARY = new Boundary(2590, 3597, 2815, 3837);
	public static final Boundary GNOME_STRONGHOLD_BOUNDARY = new Boundary(2369, 3398, 2503, 3550);
	public static final Boundary LLETYA_BOUNDARY = new Boundary(2314, 3153, 2358, 3195);
	public static final Boundary BANDIT_CAMP_BOUNDARY = new Boundary(3156, 2965, 3189, 2993);
	public static final Boundary DESERT_BOUNDARY = new Boundary(3136, 2880, 3517, 3122);
	public static final Boundary SLAYER_TOWER_BOUNDARY = new Boundary(3399, 3527, 3454, 3581);
	public static final Boundary APE_ATOLL_BOUNDARY = new Boundary(2691, 2692, 2815, 2812);
	public static final Boundary FELDIP_HILLS_BOUNDARY = new Boundary(2474, 2880, 2672, 3010);
	public static final Boundary YANILLE_BOUNDARY = new Boundary(2531, 3072, 2624, 3126);
	public static final Boundary ZEAH_BOUNDARY = new Boundary(1402, 3446, 1920, 3972);
	public static final Boundary LUNAR_ISLE_BOUNDARY = new Boundary(2049, 3844, 2187, 3959);
	public static final Boundary FREMENNIK_ISLES_BOUNDARY = new Boundary(2300, 3776, 2436, 3902);
	public static final Boundary WATERBIRTH_ISLAND_BOUNDARY = new Boundary(2495, 3711, 2559, 3772);
	public static final Boundary MISCELLANIA_BOUNDARY = new Boundary(2493, 3835, 2628, 3921);
	public static final Boundary WOODCUTTING_GUILD_BOUNDARY = new Boundary(1608,3479,1657,3516);
	
	
	/*
	 * yearly mimic
	 */
	public static final Boundary YEARLY_MIMIC = new Boundary(2960, 3395, 3001, 3420);
	
	/*
	 * halloween event boss 2019
	 */
	public static final Boundary JACK_O_KRAKEN = new Boundary(3493, 3167, 3558, 3224);
	
	/*
	 * Minigame Lobbys
	 */
	public static final Boundary LOBBY = new Boundary(3010,9921,3070,9982);
	//South Side
	public static final Boundary RAIDS_LOBBY = new Boundary(3052,9924,3067,9942);
	public static final Boundary RAIDS_LOBBY_ENTRANCE = new Boundary(3058,9944,3061,9951);
	public static final Boundary XERIC_LOBBY = new Boundary(3032,9924,3047,9942);
	public static final Boundary XERIC_LOBBY_ENTRANCE = new Boundary(3038,9944,3041,9951);
	//north side
	public static final Boundary THEATRE_LOBBY = new Boundary(3052,9961,3067,9979);
	public static final Boundary THEATRE_LOBBY_ENTRANCE = new Boundary(3058,9952,3061,9960);
	public static final Boundary TOURNY_LOBBY = new Boundary(3032, 9961, 3047, 9979);
	
	/*
	 * Hydra Dungeon
	 */
	public static final Boundary HYDRA_DUNGEON = new Boundary(1297, 10215, 1397, 10283);
	public static final Boundary HYDRA_DUNGEON2 = new Boundary(1248, 10144, 1302, 10210);
	public static final Boundary HYDRA_BOSS_ROOM = new Boundary(1355, 10253, 1380, 10281);
	public static final Boundary[] HYDRA_ROOMS = { HYDRA_DUNGEON, HYDRA_DUNGEON2, HYDRA_BOSS_ROOM };
	
	/*
	 * Void knight champion area
	 */
	
	public static final  Boundary CHAMPION_AREA = new Boundary(1560, 3930, 1660, 4030);

	/**
	 * Holidays
	 */
	public static final Boundary HALLOWEEN_ORDER_MINIGAME = new Boundary(2591, 4764, 2617, 4786);
	public static final Boundary CHRISTMAS = new Boundary(3035, 3458, 3065, 3513);
	
	/**
	 * Hunter
	 */
	public static final Boundary HUNTER_JUNGLE = new Boundary(1486, 3392, 1685, 3520);
	public static final Boundary HUNTER_LOVAK = new Boundary(1468, 3840, 1511, 3890);
	public static final Boundary HUNTER_DONATOR = new Boundary(2124, 4917, 2157, 4946);
	public static final Boundary HUNTER_WILDERNESS = new Boundary(3128, 3755, 3172, 3796);
	public static final Boundary PURO_PURO = new Boundary(2561, 4289, 2623, 4351);
	public static final Boundary[] HUNTER_BOUNDARIES = { HUNTER_JUNGLE, HUNTER_WILDERNESS, HUNTER_LOVAK, HUNTER_DONATOR };
	
	public static final Boundary LAVA_DRAGON_ISLE = new Boundary(3174, 3801, 3233, 3855);
	
	public static final Boundary ABYSSAL_SIRE = new Boundary(2942, 4735, 3136, 4863);
	
	public static final Boundary COMBAT_DUMMY = new Boundary(2846, 2960, 2848, 2962);
	
	public static final Boundary SAFEPKMULTI = new Boundary(3090, 3525, 3109, 3536);
	
	public static final Boundary SAFEPKSAFE = new Boundary(3068, 3516, 3109, 3536);
	
	public static final Boundary SAFEPK = new Boundary(3068, 3525, 3109, 3536);
	/**
	 * Raids bosses
	 */
	public static final Boundary RAID_MAIN = new Boundary(3295, 5152, 3359, 5407, 0);
	public static final Boundary RAID_F1 = new Boundary(3295, 5152, 3359, 5407, 1);
	public static final Boundary RAID_F2 = new Boundary(3295, 5152, 3359, 5407, 2);
	public static final Boundary RAID_F3 = new Boundary(3295, 5152, 3359, 5407, 3);
	
	public static final Boundary OLM = new Boundary(3197, 5708, 3270, 5780);
	public static final Boundary RAIDS = new Boundary(3259, 5145, 3361, 5474);
	public static final Boundary TEKTON = new Boundary(3296, 5281, 3327, 5310);
	public static final Boundary TEKTON_ATTACK_BOUNDARY = new Boundary(3299, 5285, 3321, 5301);
	public static final Boundary SHAMAN_BOUNDARY = new Boundary(3305, 5257, 3320, 5269);
	public static final Boundary SKELETAL_MYSTICS = new Boundary(3298, 5249, 3325, 5275);
	public static final Boundary ICE_DEMON = new Boundary(3297, 5343, 3325, 5374);
	public static final Boundary RAIDROOMS[] = { OLM, RAIDS, RAID_MAIN, RAID_F1, RAID_F2, RAID_F3, TEKTON, TEKTON_ATTACK_BOUNDARY, SKELETAL_MYSTICS, ICE_DEMON };
	
	public static final Boundary XERIC = new Boundary (2685, 5440, 2743, 5495);
	
	public static final Boundary THEATRE = new Boundary (3127, 4229, 3329, 4489);
	public static final Boundary MAIDEN = new Boundary (3149, 4418, 3228, 4467);
	public static final Boundary BLOAT = new Boundary (3265, 4428, 3327, 4465);
	public static final Boundary NYLOCAS = new Boundary (3276, 4231, 3315, 4285);
	public static final Boundary SOTETSEG = new Boundary (3264, 4291, 3295, 4336);
	public static final Boundary XARPUS = new Boundary (3149, 4365, 3193, 4410);
	public static final Boundary VERZIK = new Boundary (3147, 4291, 3192, 4336);
	public static final Boundary LOOT = new Boundary (3222, 4301, 3253, 4337);
	public static final Boundary THEATRE_ROOMS[] = { THEATRE, MAIDEN, BLOAT, NYLOCAS, SOTETSEG, XARPUS, LOOT};
	
	
	public static final Boundary ALTAR = new Boundary(3223, 3603, 3255, 3633);
	public static final Boundary FORTRESS = new Boundary(2993, 3615, 3024, 3648);
	public static final Boundary DEMONIC = new Boundary(3236, 3852, 3275, 3884);
	public static final Boundary ROGUES = new Boundary(3293, 3919, 3320, 3950);
	public static final Boundary DRAGONS = new Boundary(3293, 3655, 3320, 3682);
	public static final Boundary[] PURSUIT_AREAS  = { ALTAR, FORTRESS, DEMONIC, ROGUES, DRAGONS };
	
	//Event Boss Spawns
	public static final Boundary EVENT_BANDITS = new Boundary(3135, 2995, 3198, 3038);
	public static final Boundary EVENT_BARROWS = new Boundary(3507, 3219, 3592, 3360);
	public static final Boundary EVENT_TAVERLY = new Boundary(2890, 3380, 2972, 3438);
	public static final Boundary EVENT_CATHERBY = new Boundary(2761, 3442, 2832, 3490);
	public static final Boundary EVENT_NEITIZNOT = new Boundary(2305, 3802, 2390, 3890);
	public static final Boundary[] EVENT_AREAS = { EVENT_BANDITS, EVENT_BARROWS, EVENT_TAVERLY, EVENT_CATHERBY, EVENT_NEITIZNOT };
	
	/*
	 * Nightmare boundary
	 */
public static final Boundary NIGHTMARE_AREA = new Boundary(1855, 4928, 1921, 4967);

/*
 * Solak boundary
 */
public static final Boundary SOLAK_AREA = new Boundary(3154, 9746, 3193, 9777);

    /*
     * Wilderness boss spawns
     */
    public static final Boundary AREA1 = new Boundary(3295, 3878, 3346, 3921);
    public static final Boundary AREA2 = new Boundary(3141, 3797, 3179, 3856);
    public static final Boundary AREA3 = new Boundary(3029, 3869, 3058, 3900);
    public static final Boundary AREA4 = new Boundary(3005, 3649, 3033, 3677);
    public static final Boundary[] WILDY_EVENT_AREAS = { AREA1, AREA2, AREA3, AREA4 };
	/**
	 * Kalphite Queen
	 */
	public static final Boundary KALPHITE_QUEEN = new Boundary(3457, 9472, 3514, 9527);
	
	public static final Boundary CATACOMBS = new Boundary(1590, 9980, 1731, 10115);
	

	public static final Boundary CLAN_WARS = new Boundary(3272, 4759, 3380, 4852);
	public static final Boundary CLAN_WARS_SAFE = new Boundary(3263, 4735, 3390, 4761);
	/**
	 * Cerberus spawns
	 */
	public static final Boundary BOSS_ROOM_WEST = new Boundary(1224, 1225, 1259, 1274);
	public static final Boundary BOSS_ROOM_NORTH = new Boundary(1290, 1288, 1323, 1338);
	public static final Boundary BOSS_ROOM_EAST = new Boundary(1354, 1224, 1387, 1274);
	public static final Boundary WITHIN_BOUNDARY_CERB = new Boundary(1234, 1246, 1246, 1256);
	public static final Boundary[] CERBERUS_BOSSROOMS  = { BOSS_ROOM_NORTH, BOSS_ROOM_WEST, BOSS_ROOM_EAST };
	
	public static final Boundary SMOKE_DEVILS = new Boundary(2342, 9422, 2439, 9475);
	
	
	public static final Boundary SKOTIZO_BOSSROOM = new Boundary(1678, 9870, 1714, 9905);
	
	public static final Boundary BANDOS_GODWARS = new Boundary(2864, 5351, 2876, 5369);
	public static final Boundary ARMADYL_GODWARS = new Boundary(2824, 5296, 2842, 5308);
	public static final Boundary ZAMORAK_GODWARS = new Boundary(2918, 5318, 2936, 5331);
	public static final Boundary SARADOMIN_GODWARS = new Boundary(2889, 5258, 2907, 5276);
	public static final Boundary[] GODWARS_BOSSROOMS = { BANDOS_GODWARS, ARMADYL_GODWARS, ZAMORAK_GODWARS, SARADOMIN_GODWARS };
	
	public static final Boundary FIGHT_ROOM = new Boundary(1671,4690,1695,4715);
	
	public static final Boundary WILDERNESS = new Boundary(2941, 3525, 3392, 3968);
	public static final Boundary WILDERNESS_UNDERGROUND = new Boundary(2941, 9918, 3392, 10366);
	public static final Boundary REVENANT_CAVE_2 = new Boundary(new Rectangle(1600, 5312, 64, 64));
	public static final Boundary[] WILDERNESS_PARAMETERS = { WILDERNESS, WILDERNESS_UNDERGROUND, WILDERNESS_GOD_WARS_BOUNDARY, REVENANT_CAVE_2 };

	
	//public static final Boundary WILDERNESS = new Boundary(2941, 3525, 3392, 3968); // (2941, 3525, 3392, 3968);
//public static final Boundary WILDERNESS = new Boundary(1486, 1444, 3767, 3730);
	//public static final Boundary ZEAH_WILDERNESS = new Boundary(1420, 3730, 1600, 4060);
	//public static final Boundary WILDERNESS_UNDERGROUND = new Boundary(2941, 9918, 3392, 10366);
	//public static final Boundary[] WILDERNESS_PARAMETERS = { WILDERNESS, ZEAH_WILDERNESS, WILDERNESS_UNDERGROUND, WILDERNESS_GOD_WARS_BOUNDARY };
	
	public static final Boundary INFERNO = new Boundary(2250, 5325, 2290, 5361);
	
	//pvp world
	public static final Boundary EDGE_BANK = new Boundary(3091, 3495, 3096, 3500);

	public static final Boundary ICE_PATH = new Boundary(2837, 3786, 2870, 3821);
	public static final Boundary ICE_PATH_TOP = new Boundary(2822, 3806, 2837, 3813);
	public static final Boundary SEERS = new Boundary(2689, 3455, 2734, 3500);
	public static final Boundary VARROCK = new Boundary(3178, 3390, 3243, 3423);
	public static final Boundary ARDOUGNE = new Boundary(2644, 3288, 2678, 3323);
	public static final Boundary[] ROOFTOP_COURSES = { SEERS_BOUNDARY, VARROCK_BOUNDARY, ARDOUGNE_BOUNDARY };
	//public static final Boundary DONATOR_ZONE = new Boundary(1611, 3660, 1651, 3686);
	public static final Boundary DONATOR_ZONE = new Boundary(2819, 5056, 2879, 5119);
	public static final Boundary EDZ = new Boundary(3205, 2752, 3267, 2807);
	public static final Boundary LIZARDMAN_CANYON = new Boundary(1468, 3674, 1567, 3709);
	public static final Boundary CORPOREAL_BEAST_LAIR = new Boundary(2972, 4370, 2999, 4397);
	public static final Boundary DAGANNOTH_KINGS = new Boundary(2891, 4428, 2934, 4469);
	public static final Boundary SCORPIA_LAIR = new Boundary(3216, 10329, 3248, 10354);
	public static final Boundary KRAKEN_CAVE = new Boundary(2240, 9984, 2303, 10047);
	public static final Boundary LIGHTHOUSE = new Boundary(2501, 4627, 2541, 4662);
	public static final Boundary RFD = new Boundary(1888, 5344, 1911, 5367);
	public static final Boundary RESOURCE_AREA = new Boundary(3174, 3924, 3196, 3944);
	public static final Boundary KBD_AREA = new Boundary(2251, 4675, 2296, 4719);
	public static final Boundary PEST_CONTROL_AREA = new Boundary(2622, 2554, 2683, 2675);
	public static final Boundary FIGHT_CAVE = new Boundary(2365, 5052, 2437, 5123);
	public static final Boundary EDGEVILLE_PERIMETER = new Boundary(3073, 3465, 3108, 3518);
	public static final Boundary[] DUEL_ARENA = new Boundary[] { new Boundary(3332, 3244, 3359, 3259), new Boundary(3364, 3244, 3389, 3259) };
	public static final Boundary ZULRAH = new Boundary(2257, 3065, 2278, 3080);
	public static final Boundary EMPTY = new Boundary(0, 0, 0, 0);
	@Override
	public String toString() {
		return "Boundary [minX=" + minX + ", minY=" + minY + ", highX=" + highX + ", highY=" + highY + ", height="
				+ height + "]";
	}

	public static final Boundary BLOAT_ARENA = new Boundary(3287, 4439, 3304, 4456);
	public static final Boundary[] CANNON_NOT_ALLOWED = { RAIDS, ZULRAH, FIGHT_CAVE, KBD_AREA, RFD, RESOURCE_AREA, PEST_CONTROL_AREA, 
			SCORPIA_LAIR, KRAKEN_CAVE, BANDOS_GODWARS, ARMADYL_GODWARS, ZAMORAK_GODWARS, SARADOMIN_GODWARS,
			SKOTIZO_BOSSROOM, WILDERNESS, BOSS_ROOM_NORTH, BOSS_ROOM_WEST, BOSS_ROOM_EAST, CLAN_WARS_SAFE, CLAN_WARS, CATACOMBS, 
			KALPHITE_QUEEN, XERIC, THEATRE, HYDRA_DUNGEON, HYDRA_DUNGEON2, HYDRA_BOSS_ROOM, LOBBY, NIGHTMARE_AREA };

	public boolean contains(Location location) {
		return location.getX() >= minX && location.getX() < highX && location.getY() >= minY && location.getY() < highY;
	}

	public void forEach(Consumer<Location> consumer) {
		for(int x = minX;x<highX;x++) {
			for(int y = minY;y<highY;y++) {
				consumer.accept(new Location(x, y));
			}
		}
		
	}
	
	public Stream<Location> stream() {
		List<Location> list = Lists.newArrayList();
		for(int x = minX;x<highX;x++) {
			for(int y = minY;y<highY;y++) {
				list.add(new Location(x, y));
			}
		}
		return list.stream();
	}
	
	public Stream<Location> streamWithHeight() {
		List<Location> list = Lists.newArrayList();
		for(int z = 0;z < 4; z++)
		for(int x = minX;x<highX;x++) {
			for(int y = minY;y<highY;y++) {
				list.add(new Location(x, y, z));
			}
		}
		return list.stream();
	}

	public Stream<Location> insideBorderStream(int size) {
		List<Location> list = Lists.newArrayList();
		for(int x = minX;x<=highX;x++) {
			for(int y = minY;y<=highY;y++) {
				if(x >= minX + size && x <= highX - size && y >= minY + size && y <= highY - size)
					continue;
				list.add(new Location(x, y));
			}
		}
		return list.stream();
	}
	
	public Boundary expand(int x, int y) {
		return new Boundary(this.minX - x, this.minY - y, this.highX + x, this.highY + y);
	}
	
	public Boundary translate(int x, int y, int height) {
		return new Boundary(this.minX + x, this.minY + y, this.highX + x, this.highY + y, this.height - height);
	}

	/**
	 * @param i
	 * @return
	 */
	public Boundary inset(int size) {
		return new Boundary(this.minX + size, this.minY + size, this.highX - size, this.highY - size);
	}

	/**
	 * @param boundaries
	 * @return
	 */
	public Boundary not(Boundary... boundaries) {//TODO Finish this
		List<Location> tiles = stream().filter(loc -> !Boundary.isIn(loc, boundaries)).collect(Collectors.toList());
		Location bottomLeft = tiles.stream().sorted((loc1, loc2) -> loc1.getX() - loc2.getX() < 0 ? loc1.getY() < loc2.getY() ? -1 : 1 : 1 ).findFirst().orElse(new Location(0 , 0));
		Location topRight = tiles.stream().sorted((loc1, loc2) -> loc1.getX() - loc2.getX() < 0 ? loc1.getY() < loc2.getY() ? 1 : -1 : -1 ).findFirst().orElse(new Location(0 , 0));
		return new Boundary(bottomLeft.getX(), bottomLeft.getY(), topRight.getX(), topRight.getY());
	}

}