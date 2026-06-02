package valius.content.gauntlet;

import static valius.content.gauntlet.RoomDirection.ALL;
import static valius.content.gauntlet.RoomDirection.EAST;
import static valius.content.gauntlet.RoomDirection.SOUTH;
import static valius.content.gauntlet.RoomDirection.WEST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import valius.model.Location;
import valius.util.Misc;
/**
 * @author ReverendDread
 * Aug 3, 2019
 * Could be done better because all rooms are on the same y axis, offset by same amount.
 */
@Getter
public enum GauntletRooms {

	STARTER_ROOM(1904, 5664, GauntletRoomType.MIDDLE, ALL),
	BOSS_ROOM(1904, 5680, GauntletRoomType.MIDDLE, ALL),
	FOUR_DOOR_ROOM_1(1856, 5632, GauntletRoomType.MIDDLE, ALL),
	FOUR_DOOR_ROOM_2(1856, 5648, GauntletRoomType.MIDDLE, ALL),
	FOUR_DOOR_ROOM_3(1856, 5664, GauntletRoomType.MIDDLE, ALL),
	FOUR_DOOR_ROOM_4(1856, 5680, GauntletRoomType.MIDDLE, ALL),
	
	THREE_DOOR_ROOM_1(1872, 5632, GauntletRoomType.SIDE, EAST, SOUTH, WEST),
	THREE_DOOR_ROOM_2(1872, 5648, GauntletRoomType.SIDE, EAST, SOUTH, WEST),
	THREE_DOOR_ROOM_3(1872, 5664, GauntletRoomType.SIDE, EAST, SOUTH, WEST),
	THREE_DOOR_ROOM_4(1872, 5680, GauntletRoomType.SIDE, EAST, SOUTH, WEST),
	
	TWO_DOOR_ROOM_1(1888, 5632, GauntletRoomType.CORNER, WEST, SOUTH),
	TWO_DOOR_ROOM_2(1888, 5648, GauntletRoomType.CORNER, WEST, SOUTH),
	TWO_DOOR_ROOM_3(1888, 5664, GauntletRoomType.CORNER, WEST, SOUTH),
	TWO_DOOR_ROOM_4(1888, 5680, GauntletRoomType.CORNER, WEST, SOUTH);
	
	private GauntletRooms(int x, int y, GauntletRoomType type, int... openDirections) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.openDirections = openDirections;
	}
	
	private int x, y;
	private GauntletRoomType type;
	private int[] openDirections;
	
	/**
	 * Finds a room of a specifc {@link GauntletRoomType}
	 * @param type The room type.
	 * @return
	 */
	public static GauntletRooms findRoomOfType(GauntletRoomType type) {
		List<GauntletRooms> lookup = Stream.of(values()).filter(room -> room.getType() == type && room != GauntletRooms.STARTER_ROOM && room != GauntletRooms.BOSS_ROOM).collect(Collectors.toList());
		return Misc.randomTypeOfList(lookup);
	}

	/**
	 * Checks if the room has the requested direction to be opened.
	 * @param requestedDir The requested direction to be opened.
	 * @param rotation The room rotation.
	 * @return
	 */
	public boolean hasDirection(int requestedDir, int rotation) {
		return Arrays.stream(openDirections).anyMatch(dir -> ((dir + rotation) & 3) == requestedDir);
	}

	public Location getLocation() {
		return new Location(x, y);
	}

}
