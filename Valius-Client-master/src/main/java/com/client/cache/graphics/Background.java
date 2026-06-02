package com.client.cache.graphics;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.util.List;

import javax.swing.ImageIcon;

import com.client.cache.Archive;
import com.client.draw.raster.Raster;
import com.client.graphics.loaders.SpriteLoaderSprite;
import com.client.io.Buffer;
import com.client.utilities.ColourUtils;
import com.google.common.collect.Lists;

public final class Background extends Raster {

	public Background(Archive streamLoader, String s, int i) {
		Buffer stream = new Buffer(streamLoader.getDataForName(s + ".dat"));
		Buffer stream_1 = new Buffer(streamLoader.getDataForName("index.dat"));
		stream_1.position = stream.readShort();
		resizeWidth = stream_1.readShort();
		resizeHeight = stream_1.readShort();
		int j = stream_1.readUnsignedByte();
		palette = new int[j];
		for (int k = 0; k < j - 1; k++)
			palette[k + 1] = stream_1.readUTriByte();

		for (int l = 0; l < i; l++) {
			stream_1.position += 2;
			stream.position += stream_1.readShort()
					* stream_1.readShort();
			stream_1.position++;
		}

		drawOffsetX = stream_1.readUnsignedByte();
		drawOffsetY = stream_1.readUnsignedByte();
		width = stream_1.readShort();
		height = stream_1.readShort();
		int i1 = stream_1.readUnsignedByte();
		int j1 = width * height;
		palettePixels = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++)
				palettePixels[k1] = stream.readSignedByte();

			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < width; l1++) {
				for (int i2 = 0; i2 < height; i2++)
					palettePixels[l1 + i2 * width] = stream
							.readSignedByte();

			}

		}
	}

	public Background(SpriteLoaderSprite spriteLoaderSprite) {
		byte[] data = spriteLoaderSprite.spriteData;
		drawOffsetX = spriteLoaderSprite.drawOffsetX;
		drawOffsetY = spriteLoaderSprite.drawOffsetY;
		Image image =  Toolkit.getDefaultToolkit().createImage(data);
		ImageIcon sprite = new ImageIcon(image);
		width = sprite.getIconWidth();
		height = sprite.getIconHeight();
		resizeWidth = width;
		resizeHeight = height;
		drawOffsetX = 0;
		drawOffsetY = 0;
		int[] myPixels = new int[width * height];
		PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, width, height, myPixels, 0, width);
		try {
			pixelgrabber.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = null;
		for(int x = 0; x < width; x++) {				
			for(int y = 0; y < height; y++) {	
				int pixel = myPixels[x + y * width];
				int alpha = ColourUtils.getAlpha(pixel);
				myPixels[x + y * width] = alpha == 0 ? 0xff00ff : ColourUtils.getRGB(pixel);
			}
		}
		List<Integer> colourList = Lists.newArrayList();
		colourList.add(0);
		for(int pixel : myPixels) {
			if(!colourList.contains(pixel))
				colourList.add(pixel);
		}
		
		palette = new int[colourList.size()];
		for(int i = 0;i<colourList.size();i++)
			palette[i] = colourList.get(i);
		
		palettePixels = new int[width * height];
		for(int x = 0; x < width; x++) {				
			for(int y = 0; y < height; y++) {					
				int pixel = myPixels[x + y * width];
				if(pixel == 0xff00ff)
					continue;
				palettePixels[x + y * width] = colourList.indexOf(pixel);
			}
		}
	}

	public void method356() {
		resizeWidth /= 2;
		resizeHeight /= 2;
		int[] abyte0 = new int[resizeWidth * resizeHeight];
		int i = 0;
		for (int j = 0; j < height; j++) {
			for (int k = 0; k < width; k++)
				abyte0[(k + drawOffsetX >> 1) + (j + drawOffsetY >> 1) * resizeWidth] = palettePixels[i++];

		}

		palettePixels = abyte0;
		width = resizeWidth;
		height = resizeHeight;
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

	public void resize() {
		if (width == resizeWidth && height == resizeHeight)
			return;
		int[] abyte0 = new int[resizeWidth * resizeHeight];
		int i = 0;
		for (int j = 0; j < height; j++) {
			for (int k = 0; k < width; k++)
				abyte0[k + drawOffsetX + (j + drawOffsetY) * resizeWidth] = palettePixels[i++];

		}

		palettePixels = abyte0;
		width = resizeWidth;
		height = resizeHeight;
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

	public void method358() {
		int abyte0[] = new int[width * height];
		int j = 0;
		for (int k = 0; k < height; k++) {
			for (int l = width - 1; l >= 0; l--)
				abyte0[j++] = palettePixels[l + k * width];

		}

		palettePixels = abyte0;
		drawOffsetX = resizeWidth - width - drawOffsetX;
	}

	public void method359() {
		int abyte0[] = new int[width * height];
		int i = 0;
		for (int j = height - 1; j >= 0; j--) {
			for (int k = 0; k < width; k++)
				abyte0[i++] = palettePixels[k + j * width];

		}

		palettePixels = abyte0;
		drawOffsetY = resizeHeight - height - drawOffsetY;
	}

	public void method360(int i, int j, int k) {
		for (int i1 = 0; i1 < palette.length; i1++) {
			int j1 = palette[i1] >> 16 & 0xff;
			j1 += i;
			if (j1 < 0)
				j1 = 0;
			else if (j1 > 255)
				j1 = 255;
			int k1 = palette[i1] >> 8 & 0xff;
			k1 += j;
			if (k1 < 0)
				k1 = 0;
			else if (k1 > 255)
				k1 = 255;
			int l1 = palette[i1] & 0xff;
			l1 += k;
			if (l1 < 0)
				l1 = 0;
			else if (l1 > 255)
				l1 = 255;
			palette[i1] = (j1 << 16) + (k1 << 8) + l1;
		}
	}

	public void drawBackground(int i, int k) {
		i += drawOffsetX;
		k += drawOffsetY;
		int l = i + k * Raster.width;
		int i1 = 0;
		int j1 = height;
		int k1 = width;
		int l1 = Raster.width - k1;
		int i2 = 0;
		if (k < Raster.topY) {
			int j2 = Raster.topY - k;
			j1 -= j2;
			k = Raster.topY;
			i1 += j2 * k1;
			l += j2 * Raster.width;
		}
		if (k + j1 > Raster.bottomY)
			j1 -= (k + j1) - Raster.bottomY;
		if (i < Raster.topX) {
			int k2 = Raster.topX - i;
			k1 -= k2;
			i = Raster.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Raster.bottomX) {
			int l2 = (i + k1) - Raster.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			method362(j1, Raster.pixels, palettePixels, l1, l, k1, i1,
                    palette, i2);
		}
	}

	private void method362(int i, int ai[], int[] palettePixels2, int j, int k, int l,
			int i1, int ai1[], int j1) {
		int k1 = -(l >> 2);
		l = -(l & 3);
		for (int l1 = -i; l1 < 0; l1++) {
			for (int i2 = k1; i2 < 0; i2++) {
				int byte1 = palettePixels2[i1++];
				if (byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = palettePixels2[i1++];
				if (byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = palettePixels2[i1++];
				if (byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
				byte1 = palettePixels2[i1++];
				if (byte1 != 0)
					ai[k++] = ai1[byte1 & 0xff];
				else
					k++;
			}

			for (int j2 = l; j2 < 0; j2++) {
				int byte2 = palettePixels2[i1++];
				if (byte2 != 0)
					ai[k++] = ai1[byte2 & 0xff];
				else
					k++;
			}

			k += j;
			i1 += j1;
		}

	}

	public int[] palettePixels;
	public final int[] palette;
	public int width;
	public int height;
	public int drawOffsetX;
	public int drawOffsetY;
	public int resizeWidth;
	public int resizeHeight;
}
