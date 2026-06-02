package valius.model.map;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import valius.model.Location;

/**
 * Manages a palette of map regions for use in the constructed map region
 * packet.
 * @author Graham
 */
public class Palette {
	
	@Getter
	private int width, length, height;
	/**
	 * The array of tiles.
	 */
	private PaletteTile[][][] tiles;
	
	public Palette(int width, int length, int height) {
		this.width = width;
		this.length = length;
		this.height = height;
		tiles = new PaletteTile[width][length][height];
	}
	/**
	 * Gets a tile.
	 * @param x X position.
	 * @param y Y position.
	 * @param z Z position.
	 * @return The tile.
	 */
	public PaletteTile getTile(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	/**
	 * Sets a tile.
	 * @param x X position.
	 * @param y Y position.
	 * @param z Z position.
	 * @param tile The tile.
	 */
	public void setTile(int x, int y, int z, PaletteTile tile) {
		if(x >= 0 && y >= 0 && z >= 0 && x < width && y < length && z < height)
			tiles[x][y][z] = tile;
	}

	@Override
	public String toString() {
		return Stream.of(tiles).flatMap(subTiles -> Stream.of(subTiles)).flatMap(subTile -> Arrays.stream(subTile)).filter(Objects::nonNull).map(val -> val.toString() + "\n").collect(Collectors.joining(", "));
	}

	/**
	 * 
	 */
	public void reset() {
		tiles = new PaletteTile[width][length][height];
	}

	/**
	 * @param tile
	 * @return
	 */
	public Location getPositionOf(PaletteTile tile) {
		for(int x = 0;x<width;x++) {
			for(int y = 0;y<length;y++) {
				for(int z = 0;z<height;z++) {
					if(tiles[x][y][z] == tile) {
						return new Location(x, y, z);
					}
				}
			}
		}
		return null;
	}
	
	

}