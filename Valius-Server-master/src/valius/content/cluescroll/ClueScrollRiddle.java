/**
 * 
 */
package valius.content.cluescroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import valius.model.Location;
import valius.model.entity.player.Boundary;
import valius.util.Misc;

/**
 * Used for reading clue scrolls
 * @author ReverendDread & Divine
 * Oct 16, 2019
 */
@Getter @AllArgsConstructor
public enum ClueScrollRiddle {
	//Easy digging clues
	EASY_CLUE1(10180, ClueDifficulty.EASY, new String[] { "", "", "", "Dig between some ominous", "stones in Falador.", "", "", "" }, Boundary.ofRect(3040, 3398, 4, 4)),
	EASY_CLUE2(10182, ClueDifficulty.EASY, new String[] { "", "", "", "Dig in the centre of a", "great kingdom of 5 cities.", "", "", "" }, Boundary.ofRect(1639, 3673, 4, 4)),
	EASY_CLUE3(10184, ClueDifficulty.EASY, new String[] { "", "", "", "Dig near some giant mushrooms", "behind the Grand Tree.", "", "", "" }, Boundary.ofRect(2458, 3504, 4, 4)),
	EASY_CLUE4(10186, ClueDifficulty.EASY, new String[] { "", "", "", "Dig where only the skilled,", "the wealthy, or the brave", "can choose not to visit again.", "", "" }, Boundary.ofRect(3222, 3218, 4, 4)),
	EASY_CLUE5(10188, ClueDifficulty.EASY, new String[] { "", "", "The treasure is buried in", "a small building full of bones.", "Here is a hint:", "it's not near a graveyard.", "", "" }, Boundary.ofRect(3356, 3507, 4, 4)), 
	
	//Medium digging clues
	MEDIUM_CLUE1(10254, ClueDifficulty.MEDIUM, new String[] { "Hint:", "West of", "Tree Gnome Village", "", "00 degrees 05 minutes south,", "01 degrees 13 minutes east", "", "" }, Boundary.ofRect(2478, 3157, 4, 4)),
	MEDIUM_CLUE2(10256, ClueDifficulty.MEDIUM, new String[] { "Hint:", "West of", "Musa Point General store", "", "00 degrees 13 minutes south,", "13 degrees 58 minutes east", "", "" }, Boundary.ofRect(2888, 3153, 4, 4)),
	MEDIUM_CLUE3(10258, ClueDifficulty.MEDIUM, new String[] { "Hint:", "South of", "Water altar", "", "00 degrees 20 minutes north,", "23 degrees 15 minutes east", "", "" }, Boundary.ofRect(3183, 3151, 4, 4)),
	MEDIUM_CLUE4(10260, ClueDifficulty.MEDIUM, new String[] { "Hint:", "South of", "Taverley", "", "07 degrees 33 minutes north,", "15 degrees 00 minutes east", "", "" }, Boundary.ofRect(2920, 3404, 4, 4)),
	MEDIUM_CLUE5(10262, ClueDifficulty.MEDIUM, new String[] { "Hint:", "West of", "The Monastery", "", "09 degrees 48 minutes north,", "17 degrees 39 minutes east", "", "" }, Boundary.ofRect(3005, 3475, 4, 4)),
	
	//Hard digging clues
	HARD_CLUE1(10234, ClueDifficulty.HARD, new String[] { "Hint:", "North of", "Bandit Camp", "", "03 degrees 45 minutes south,", "22 degrees 45 minutes east", "", "" }, Boundary.ofRect(3168, 3041, 4, 4)),
	HARD_CLUE2(10236, ClueDifficulty.HARD, new String[] { "Hint:", "West of", "Bandit Camp", "", "06 degrees 00 minutes south,", "21 degrees 48 minutes east", "", "" }, Boundary.ofRect(3139, 2969, 4, 4)),
	HARD_CLUE3(10238, ClueDifficulty.HARD, new String[] { "Hint:", "South-West", "Karamja", "", "06 degrees 11 minutes south,", "15 degrees 07 minutes east", "", "" }, Boundary.ofRect(2924, 2963, 4, 4)),
	HARD_CLUE4(10240, ClueDifficulty.HARD, new String[] { "Hint:", "West of", "Barrows", "", "08 degrees 03 minutes north,", "31 degrees 16 minutes east", "", "" }, Boundary.ofRect(3441, 3420, 4, 4)),
	HARD_CLUE5(10242, ClueDifficulty.HARD, new String[] { "Hint:", "North of", "Edgeville", "", "13 degrees 46 minutes north,", "21 degrees 01 minutes east", "", "" }, Boundary.ofRect(3113, 3602, 4, 4)),
	HARD_CLUE6(10244, ClueDifficulty.HARD, new String[] { "Hint:", "Near", "Trollheim", "", "16 degrees 03 minutes north,", "14 degrees 07 minutes east", "", "" }, Boundary.ofRect(2892, 3675, 4, 4)),
	HARD_CLUE7(10246, ClueDifficulty.HARD, new String[] { "Hint:", "Inside", "Graveyard of Shadows", "", "16 degrees 07 minutes north,", "22 degrees 45 minutes east", "", "" }, Boundary.ofRect(3168, 3677, 4, 4)),
	HARD_CLUE8(10248, ClueDifficulty.HARD, new String[] { "Hint:", "North of", "Black Salamander hunting", "", "16 degrees 43 minutes north,", "19 degrees 13 minutes east", "", "" }, Boundary.ofRect(3304, 3692, 4, 4)),
	
	//Elite digging clues
	ELITE_CLUE1(12073, ClueDifficulty.ELITE, new String[] { "", "", "", "05 degrees 39 minutes south,", "02 degrees 13 minutes east", "", "", "" }, Boundary.ofRect(2511, 2980, 4, 4)),
	ELITE_CLUE2(12074, ClueDifficulty.ELITE, new String[] { "", "", "", "08 degrees 15 minutes north,", "35 degrees 24 minutes east", "", "", "" }, Boundary.ofRect(3573, 3425, 4, 4)),
	ELITE_CLUE3(12075, ClueDifficulty.ELITE, new String[] { "", "", "", "10 degrees 05 minutes south,", "24 degrees 31 minutes east", "", "", "" }, Boundary.ofRect(3224, 2839, 4, 4)),
	ELITE_CLUE4(12076, ClueDifficulty.ELITE, new String[] { "", "", "", "10 degrees 54 minutes north,", "20 degrees 50 minutes west", "", "", "" }, Boundary.ofRect(1773, 3510, 4, 4)),
	ELITE_CLUE5(12077, ClueDifficulty.ELITE, new String[] { "", "", "", "17 degrees 58 minutes north,", "19 degrees 05 minutes east", "", "", "" }, Boundary.ofRect(3052, 3735, 4, 4)),
	ELITE_CLUE6(12078, ClueDifficulty.ELITE, new String[] { "", "", "", "19 degrees 56 minutes north,", "02 degrees 31 minutes west", "", "", "" }, Boundary.ofRect(2359, 3799, 4, 4)),
	ELITE_CLUE7(12079, ClueDifficulty.ELITE, new String[] { "", "", "", "21 degrees 03 minutes north,", "24 degrees 13 minutes east", "", "", "" }, Boundary.ofRect(3215, 3835, 4, 4)),
	
	//Master digging clues
	MASTER_CLUE1(19835, ClueDifficulty.MASTER, new String[] { "", "", "", "03 degrees 50 minutes north,", "09 degrees 07 minutes east", "", "", "" }, Boundary.ofRect(2732, 3283, 4, 4)),
	MASTER_CLUE2(33895, ClueDifficulty.MASTER, new String[] { "", "", "", "07 degrees 37 minutes north,", "35 degrees 18 minutes east", "", "", "" }, Boundary.ofRect(3570, 3406, 4, 4)),
	MASTER_CLUE3(33896, ClueDifficulty.MASTER, new String[] { "", "", "", "12 degrees 45 minutes north,", "20 degrees 09 minutes east", "", "", "" }, Boundary.ofRect(3085, 3569, 4, 4)),
	MASTER_CLUE4(33897, ClueDifficulty.MASTER, new String[] { "", "", "", "16 degrees 41 minutes north,", "30 degrees 54 minutes west", "", "", "" }, Boundary.ofRect(1450, 3695, 4, 4)),
	MASTER_CLUE5(33898, ClueDifficulty.MASTER, new String[] { "", "", "", "24 degrees 00 minutes north,", "29 degrees 22 minutes east", "", "", "" }, Boundary.ofRect(3380, 3929, 4, 4));
	
	private int id; //the scroll id
	private ClueDifficulty difficulty; //the scroll difficulty
	private String[] riddle; // riddle text
	private Boundary boundary; // dig area
	
    public static final int[] EASY_CLUES = { 10180, 10182, 10184, 10186, 10188 };
    public static final int[] MEDIUM_CLUES = { 10254, 10256, 10258, 10260, 10262 };
    public static final int[] HARD_CLUES = { 10234, 10236, 10238, 10240, 10242, 10244, 10246, 10248 };
    public static final int[] ELITE_CLUES = { 12073, 12074, 12075, 12076, 12077, 12078, 12079 };
    public static final int[] MASTER_CLUES = { 19835, 33895, 33896, 33897, 33898 };
    
	public static ClueScrollRiddle getScrollForId(int itemId) {
		for (ClueScrollRiddle scroll : ClueScrollRiddle.values()) {
			if (itemId == scroll.getId()) {
				return scroll;
			}
		}
		return null;
	}
	
	public static ClueScrollRiddle getScrollForLocation(Location loc) {
		for (ClueScrollRiddle scroll : ClueScrollRiddle.values()) {
			if (Boundary.isIn(loc, scroll.getBoundary())) {
				return scroll;
			}
		}
		return null;
	}
	
}