package com.client.graphics.loaders;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.client.utilities.DataUtils;
import com.client.utilities.FileOperations;
import com.client.cache.graphics.Sprite;
import com.client.config.Configuration;
import com.client.io.Buffer;
import com.client.sign.Signlink;

public class SpriteLoader {

	public SpriteLoader(String fileName) {
		loadSprites(fileName);
	}



	/**
	 * Loads the sprite data and index files from the overlays location.
	 * This can be edited to use an archive such as config or media to load from the overlays.
	 * @param archive
	 */
	public void loadSprites(String fileName) {
		try {
			Buffer index = new Buffer(FileOperations.readFile(Signlink.getCacheDirectory() + fileName + ".idx"));
			Buffer data = new Buffer(FileOperations.readFile(Signlink.getCacheDirectory() + fileName + ".dat"));
			DataInputStream indexFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(index.payload)));
			DataInputStream dataFile = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(data.payload)));
			int totalSprites = indexFile.readInt();
			if (cache == null) {
				cache = new SpriteLoaderSprite[totalSprites];
				sprites = new Sprite[totalSprites];
			}
			for (int i = 0; i < totalSprites; i++) {
				int id = indexFile.readInt();
				if (cache[id] == null) {
					cache[id] = new SpriteLoaderSprite();
				}
				cache[id].readValues(indexFile, dataFile);
				createSprite(fileName, cache[id]);
			}
			indexFile.close();
			dataFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Creates a sprite out of the spriteData.
	 * @param sprite
	 */
	public void createSprite(String fileName, SpriteLoaderSprite sprite) {
		if (Configuration.DUMP_SPRITES) {
			File directory = new File(Signlink.getCacheDirectory() + "sprites/dump/" + fileName.replace(".dat", "").trim() + "/");
			if (!directory.exists()) {
				directory.mkdirs();
			}
			DataUtils.writeFile(
					new File(directory.getAbsolutePath() + System.getProperty("file.separator") + sprite.id + ".png"),
					sprite.spriteData);
		}
		sprites[sprite.id] = new Sprite(sprite.spriteData);
		sprites[sprite.id].anInt1442 = sprite.drawOffsetX;
		sprites[sprite.id].anInt1443 = sprite.drawOffsetY;
	}

	/**
	 * Gets the name of a specified sprite index.
	 * @param index
	 * @return
	 */
	public String getName(int index) {
		if (cache[index].name != null) {
			return cache[index].name;
		} else {
			return "null";
		}
	}

	/**
	 * Gets the drawOffsetX of a specified sprite index.
	 * @param index
	 * @return
	 */
	public int getOffsetX(int index) {
		return cache[index].drawOffsetX;
	}

	/**
	 * Gets the drawOffsetY of a specified sprite index.
	 * @param index
	 * @return
	 */
	public int getOffsetY(int index) {
		return cache[index].drawOffsetY;
	}

	/**
	 * Sets the default values.
	 */
	

	public SpriteLoaderSprite[] cache;
	public Sprite[] sprites = null;
	public int totalSprites;
}