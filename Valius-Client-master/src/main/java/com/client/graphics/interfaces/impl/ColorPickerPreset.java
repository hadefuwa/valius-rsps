package com.client.graphics.interfaces.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

import com.client.cache.graphics.Sprite;
import com.client.draw.raster.Rasterizer2D;
import com.client.draw.raster.Rasterizer3D;
import com.client.sign.Signlink;

public class ColorPickerPreset {

	private int id;
	private static Sprite saveSprite, loadSprite;
	
	private int[] colours;
	private ColorPickerGroup group;
	private int lastDrawX, lastDrawY;

	public ColorPickerPreset(int id, ColorPickerGroup group, int[] colours) {
		this.id = id;
		this.group = group;
		this.colours = IntStream.of(colours).map(val -> Rasterizer3D.hslToRgb[val]).toArray();
		tryLoad();
	}

	
	public static void loadSprites() {
		saveSprite = new Sprite("interfaces/cape/savepreset");
		loadSprite = new Sprite("interfaces/cape/loadpreset");
	}
	
	public void draw(int x, int y) {
		lastDrawX = x;
		lastDrawY = y;
		if(saveSprite == null || loadSprite == null)
			loadSprites();
		int size = 18;
		int baseY = y + 5;
		int baseX = x + 17;
		int offsetX = size + 2;
		int offsetY = size + 3;
		Rasterizer2D.drawRectangle(baseX, baseY, size, size, colours[0], true, true);
		Rasterizer2D.drawRectangle(baseX + offsetX, baseY, size, size, colours[1], true, true);
		Rasterizer2D.drawRectangle(baseX, baseY + offsetY, size, size, colours[2], true, true);
		Rasterizer2D.drawRectangle(baseX + offsetX, baseY + offsetY, size, size, colours[3], true, true);

		saveSprite.drawAdvancedSprite(x + 41, y + 52);
		loadSprite.drawAdvancedSprite(x + 16, y + 53);
	}
	
	public boolean inSave(int clickX, int clickY) {
		if(group.anyExpanded())
			return false;
		clickX -= lastDrawX;
		clickY -= lastDrawY;

		return (clickX >= 41 && clickX <= 41 + saveSprite.myWidth
				&& clickY >= 52 && clickY <= 52 + saveSprite.myHeight);
	}

	
	public boolean inLoad(int clickX, int clickY) {
		if(group.anyExpanded())
			return false;
		clickX -= lastDrawX;
		clickY -= lastDrawY;

		return clickX >= 16 && clickX <= 16 + loadSprite.myWidth
				&& clickY >= 53 && clickY <= 53 + loadSprite.myHeight;
	}


	public void load() {
		group.load(colours);
	}
	
	private File getFile() {
		return new File(Signlink.getCacheDirectory() + "settings" + File.separator + "capepreset" + id + ".dat");
	}
	
	public void save() {
		colours = group.getColours();
		try {

			ByteBuffer byteBuffer = ByteBuffer.allocate(16);
			IntStream.of(colours).forEach(byteBuffer::putInt);
			FileUtils.writeByteArrayToFile(getFile(), byteBuffer.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void tryLoad() {
		try {
			ByteBuffer buffer = ByteBuffer.wrap(FileUtils.readFileToByteArray(getFile()));
			IntStream.range(0, 4).forEach(index -> colours[index] = buffer.getInt());
		} catch(Exception ex) {
			
		}
	}
}
