package valius.clip.doors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import valius.model.Location;

@Slf4j
public class DoorDefinition {

	private static Map<Location, DoorDefinition> definitions = new HashMap<>();

	public static void load() {
		definitions.clear();
		try(FileReader fr = new FileReader("./Data/json/door_definitions.json")){
			Gson gson = new Gson();
			List<DoorDefinition> list = gson.fromJson(fr, new TypeToken<List<DoorDefinition>>() {
			}.getType());

			list.stream().filter(Objects::nonNull).forEach(door -> definitions.put(door.getCoordinate(), door));

			log.info("Loaded {} door definitions", definitions.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Get a door definition by Coordinate.
	 *
	 * @param Coordiante The coordinate of the door.
	 * @return The door definition.
	 */
	public static DoorDefinition forCoordinate(Location coordinate) {
		return definitions.get(coordinate);
	}

	/**
	 * Get a door definition by Coordinate.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The door definition.
	 */
	public static DoorDefinition forCoordinate(int x, int y) {
		return definitions.get(new Location(x, y));
	}

	/**
	 * Get a door definition by Coordinate.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param y The height coordinate.
	 * @return The door definition.
	 */
	public static DoorDefinition forCoordinate(int x, int y, int h) {
		return definitions.get(new Location(x, y, h));
	}

	/**
	 * A map of all definitions.
	 * 
	 * @return the map.
	 */
	public static Map<Location, DoorDefinition> getDefinitions() {
		return definitions;
	}

	private int id;

	private int x;

	private int y;

	private int h;

	private int face;

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getH() {
		return h;
	}

	public int getFace() {
		return face;
	}

	public Location getCoordinate() {
		return new Location(x, y, h);
	}

	@Override
	public String toString() {
		return "DoorDefinition [id=" + id + ", x=" + x + ", y=" + y + ", h=" + h + ", face=" + face + "]";
	}

}
