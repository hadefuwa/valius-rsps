package com.client.graphics.interfaces.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.stream.IntStream;

import com.client.Client;
import com.client.cache.graphics.Sprite;
import com.client.draw.raster.Rasterizer2D;
import com.client.draw.raster.Rasterizer3D;
import com.client.graphics.interfaces.RSInterface;
import com.client.utilities.ColourUtils;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;

public class ColorPicker {
	
	enum DragComponentType {
		NONE, COLOR_WHEEL, BRIGHTNESS; 
	}

	@Getter
	private final int id;
	
	public ColorPicker(int id) {
		this.id = id;
		generateGradient();
		regenerateSprites();
		value.addListener((obs, oldVal, newVal) -> calculatePosition());
	}
	
	@Getter @Setter
	private ColorPickerGroup group;
	
	@Getter @Setter
	private boolean clickable = true;

	
	
	private static double currentBrightness;
	
	public static void handleClick(int mX, int mY, boolean drag) {

		int interfaceId = Client.openInterfaceID;
		if (interfaceId != -1) {
			RSInterface widget = RSInterface.interfaceCache[interfaceId];
			for (int childId : widget.children) {
				RSInterface child = RSInterface.interfaceCache[childId];
				if (child == null || child.colorPicker == null)
					continue;
				child.colorPicker.getGroup().forEach(picker -> picker.click(mX, mY, drag));
				break;
			}
		}
	}
	private int colorBoxSize = 58;
	private int brightnessSliderWidth = 16;
	private int paddingLeftRight = 10;
	private int paddingTopBottom = 10;

	
	private int lastDrawX = 0, lastDrawY = 0;
	
	private int componentSpacing = 10;
	
	
	public void click(int clickX, int clickY, boolean drag) {
		System.out.println("Expanded: " + expanded);
		if(!clickable)
			return;
		if(!expanded) {
			dragType = DragComponentType.NONE;
			if(inColorSquare(clickX, clickY)) {
				System.out.println("XPANDING");
				if(group != null)
					group.onExpand(this);
			}
		} else {
			if(!drag) {
				if(dragType == DragComponentType.COLOR_WHEEL) {
					System.out.println("Color: " + ColourUtils.stripAlpha(getValueInt()));
					System.out.println("valid? " + validRS2Color());
					//Client.testColor = ColourUtils.RGB_to_RS2HSB(ColourUtils.stripAlpha(getValueInt()));
					//dumpHSL();
					
				}
				dragType = DragComponentType.NONE;
			}
			int baseX = lastDrawX + paddingLeftRight;
			int baseY = lastDrawY + paddingTopBottom;
			baseX += colorBoxSize + 24;
			baseY += paddingTopBottom - 5;

			if(inWheel(baseX, baseY, clickX, clickY)) {
				if(drag) {
					if(dragType != DragComponentType.COLOR_WHEEL && dragType != DragComponentType.NONE)
						return;
					dragType = DragComponentType.COLOR_WHEEL;
				}
				selectionX = clickX - baseX;
				selectionY = clickY - baseY;
				grabColor();
			} else if(inBrightnessSlider(baseX, baseY, clickX, clickY)) {
				if(drag) {
					if(dragType != DragComponentType.BRIGHTNESS && dragType != DragComponentType.NONE)
						return;
					dragType = DragComponentType.BRIGHTNESS;
				} 
				brightness = (clickX - baseX) / (size * 1.0d);
				regenerateSprites();
				grabColor();
			} else if(inExpandedColorSquare(clickX, clickY)) {
				if (drag) {
					return;
				} 
				expanded = false;

				if(group != null)
					group.onShrink(this);
			} else if(!inExpandedBackground(clickX, clickY)) {
				if (drag) {
					return;
				} 
				expanded = false;

				if(group != null)
					group.onShrink(this);
			}
		}
		
	}
	
	private DragComponentType dragType = DragComponentType.NONE;
	
	public boolean inColorSquare(int x, int y) {
		return x >= lastDrawX + 5 && x <= lastDrawX + colorBoxSize + 10 + 5
				&&  y >= lastDrawY + 5 && y <= lastDrawY + colorBoxSize + 5;
	}
	public boolean inExpandedColorSquare(int x, int y) {
		return x >= lastDrawX + 5 && x <= lastDrawX + colorBoxSize + 10 + 5
				&&  y >= lastDrawY + 5 && y <= lastDrawY + colorBoxSize + 5;
	}
	
	public boolean inExpandedBackground(int x, int y) {
		int bgWidth = size + brightnessSliderWidth  + 20;
		int bgHeight = size + (paddingTopBottom * 2) + 25;
		return x >= lastDrawX + 5 + colorBoxSize + 12 && x <= lastDrawX + 5 + colorBoxSize + 12 + bgWidth
				&& y >= lastDrawY + 5 && y <= lastDrawY + 5 + bgHeight;

	}
	public void grabColor() {
		value.set(selectionCircle.myPixels[selectionX + (selectionY * selectionCircle.myWidth)]);
	}
	
	public boolean validRS2Color() {
		IntStream.range(0, Rasterizer3D.hslToRgb.length).filter(val -> val == ColourUtils.RGB_to_RS2HSB(getValueInt())).forEach(val -> System.out.println(Rasterizer3D.hslToRgb[val]));
		return true;
	}
	
	public int getValueInt() {
		return value.get();
	}
	
	private boolean inBrightnessSlider(int baseX, int baseY, int x, int y) {

		int brightnessLeft = baseX;
		int brightnessTop = baseY + size + 8;
		return x >= brightnessLeft && x <= brightnessLeft + size
				&& y >= brightnessTop && y <= brightnessTop + brightnessSliderWidth;
	}
	
	private boolean inWheel(int baseX, int baseY, int x, int y) {
		baseX += radius;
		baseY += radius;
		x = (x - baseX);
		y = (y - baseY);
		double rad = Math.sqrt((y * y) + (x * x));
		return rad < radius - 2 && rad >= 0;
	}
	
	public Rectangle bounds() {
		if(!expanded) {
			return new Rectangle(lastDrawX, lastDrawY, colorBoxSize, colorBoxSize);
		}
		int bgWidth = size + brightnessSliderWidth + 5 + (paddingLeftRight * 2) + colorBoxSize + 20;
		int bgHeight = size + (paddingTopBottom * 2);
		return new Rectangle(lastDrawX, lastDrawY, bgWidth, bgHeight);
	}
	
	public void draw(int x, int y) {
		if(selectionCircle == null || pickerCircle == null || selectionBg == null)
			regenerateSprites();
		if(currentBrightness != Rasterizer3D.currentBrightness) {
			currentBrightness = Rasterizer3D.currentBrightness;
			regenerateSprites();
			grabColor();
		}
		lastDrawX = x;
		lastDrawY = y;
		int baseX = x + 5;
		int baseY = y + 5;
		if(!expanded) {
			Rasterizer2D.drawRectangle(baseX, baseY, colorBoxSize, colorBoxSize, 0x5e5045, false, true);
			Rasterizer2D.drawRectangle(baseX + 1, baseY + 1, colorBoxSize - 2, colorBoxSize - 2, 0x281f17, true, false);
			Rasterizer2D.drawRectangle(baseX + 8, baseY + 8, colorBoxSize - 16, colorBoxSize - 16, getValueInt(), true, true);
			Rasterizer2D.drawRectangle(baseX + colorBoxSize, baseY, 13, colorBoxSize + 1, 0x43352a, true, false);
			Rasterizer2D.drawRectangle(baseX + colorBoxSize, baseY + 1, 12, colorBoxSize - 1, 0x8a5932, true, false);
			Rasterizer2D.drawTriangle(baseX + colorBoxSize + 3, baseY + (colorBoxSize / 2) - 6, 6, 10, 0);
			Rasterizer2D.drawTriangle(baseX + colorBoxSize + 4, baseY + (colorBoxSize / 2) - 5, 4, 8, 0xffffff);
		} else {
			int bgWidth = size + brightnessSliderWidth  + 20;
			int bgHeight = size + (paddingTopBottom * 2) + 25;

			Rasterizer2D.drawRectangle(baseX, baseY, colorBoxSize, colorBoxSize, 0x5e5045, false, true);
			Rasterizer2D.drawRectangle(baseX + 1, baseY + 1, colorBoxSize - 2, colorBoxSize - 2, 0x281f17, true, false);
			Rasterizer2D.drawRectangle(baseX + 8, baseY + 8, colorBoxSize - 16, colorBoxSize - 16, getValueInt(), true, true);
			

			Rasterizer2D.drawRectangle(baseX + colorBoxSize + 12, baseY, bgWidth, bgHeight, 0x5e5045, false, true);
			Rasterizer2D.drawRectangle(baseX + colorBoxSize + 13, baseY + 1, bgWidth - 2, bgHeight - 2, 0x281f17, true, false);
			

			Rasterizer2D.drawRectangle(baseX + colorBoxSize, baseY, 13, colorBoxSize + 1, 0x43352a, true, false);
			Rasterizer2D.drawRectangle(baseX + colorBoxSize, baseY + 1, 12, colorBoxSize - 1, 0x8a5932, true, false);
			Rasterizer2D.drawFlippedTriangle(baseX + colorBoxSize + 3, baseY + (colorBoxSize / 2) - 6, 6, 10, 0);
			Rasterizer2D.drawFlippedTriangle(baseX + colorBoxSize + 4, baseY + (colorBoxSize / 2) - 5, 4, 8, 0xffffff);

			baseX += paddingLeftRight + colorBoxSize + 20;
			baseY += paddingTopBottom;

			selectionBg.drawAdvancedSprite(baseX - 4, baseY - 4);
			selectionCircle.drawSprite(baseX, baseY);
			pickerCircle.drawSprite(baseX + selectionX - (pickerCircle.myWidth / 2), baseY + selectionY - (pickerCircle.myHeight / 2));
			
			int brightnessLeft = baseX;
			int brightnessTop = baseY + size + 10;
			Rasterizer2D.drawRectangle(brightnessLeft - 2, brightnessTop - 1, gradient.myWidth + 3, gradient.myHeight + 2, 0xFF201408, true, true);
			gradient.drawAdvancedSprite(brightnessLeft, brightnessTop);

			
			Rasterizer2D.drawLineBetween((int) (brightnessLeft + (size * (brightness) - 1)) - 1, (int) (brightnessTop - 1), (int) (brightnessLeft + (size * (brightness) - 1)) - 1, (int) (brightnessTop) + brightnessSliderWidth, 0xFFc27e41);
			//Rasterizer2D.drawLineBetween((int) (brightnessLeft + (size * (brightness))), (int) (brightnessTop - 1), (int) (brightnessLeft + (size * (brightness))), (int) (brightnessTop) + brightnessSliderWidth + 1, ColourUtils.adjustBrightness(0xFFFFFFFF, 1 - brightness));
			Rasterizer2D.drawLineBetween((int) (brightnessLeft + (size * (brightness) - 1)) + 1, (int) (brightnessTop - 1), (int) (brightnessLeft + (size * (brightness)) - 1) + 1, (int) (brightnessTop) + brightnessSliderWidth, 0xFFc27e41);
			Rasterizer2D.drawLineBetween((int) (brightnessLeft + (size * (brightness) - 1)) - 1, (int) (brightnessTop - 1), (int) (brightnessLeft + (size * (brightness)) - 1) + 1, (int) (brightnessTop - 1), 0xFFc27e41);
			Rasterizer2D.drawLineBetween((int) (brightnessLeft + (size * (brightness) - 1)) - 1, (int) (brightnessTop) + brightnessSliderWidth, (int) (brightnessLeft + (size * (brightness)) - 1) + 1, (int) (brightnessTop) + brightnessSliderWidth, 0xFFc27e41);
			
		}
	}
	
	@Getter @Setter
	private boolean expanded;
	private int radius = 38;
	private int size = radius * 2;
	private int selectionX = radius, selectionY = radius;
	@Getter
	private SimpleIntegerProperty value = new SimpleIntegerProperty(0xFFFFFF);

	private Sprite selectionCircle, pickerCircle, selectionBg;
	
	private double brightness = 1;

	public void regenerateSprites() {
		selectionCircle = getWheelImage(0);
		selectionBg = getWheelBg(0xffae00);
		pickerCircle = getPickerSprite(6, 0xABABAB);
		

	}
	private Sprite gradient;
	public void generateGradient() {
		System.out.println("gradient");
		gradient = new Sprite(size, brightnessSliderWidth);

		BufferedImage image = new BufferedImage(size, brightnessSliderWidth, BufferedImage.TYPE_INT_RGB);
		GradientPaint paint = new GradientPaint(0, 0, Color.black, size, brightnessSliderWidth, Color.white);
		Graphics2D g2d = image.createGraphics();
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, size, brightnessSliderWidth);

		gradient.myPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	private Sprite getPickerSprite(int radius, int outlineColor) {
		outlineColor = outlineColor|
				0xFF000000;
		outlineColor = ColourUtils.adjustBrightness(outlineColor, 1 - brightness);
		int minSize = radius - 1;
		int doubleSize = radius * 2 + 1;
		Sprite sprite = new Sprite(doubleSize , doubleSize);
		BufferedImage image = new BufferedImage(doubleSize, doubleSize, BufferedImage.TYPE_INT_ARGB);
		for (int y = -radius; y < radius; y++) {
			int ySquared = y * y;
			for (int x = -radius; x < radius; x++) {
				double rad = Math.sqrt(ySquared + x * x);

				if (rad < radius && rad > minSize) {
					
					image.setRGB(x + radius, y + radius, outlineColor);
					
				}
			}
		}
		for(int x = 0;x < doubleSize;x++) {
			if(x != radius) {
				image.setRGB(x, radius, outlineColor);
				image.setRGB(radius, x, outlineColor);
			}
		}

		sprite.myPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		return sprite;

	}
	
	private Sprite getWheelBg(int outlineColor) {

		int doubleSize = radius * 2;
		BufferedImage bgImg = new BufferedImage(doubleSize + 8, doubleSize + 8, BufferedImage.TYPE_INT_ARGB);
		
		Sprite sprite = new Sprite(bgImg.getWidth(), bgImg.getHeight());

		Graphics2D g2d = bgImg.createGraphics();
		g2d.setPaint(new Color(0x7b6500));
		g2d.fill(new Ellipse2D.Double(0, 0, bgImg.getWidth(), bgImg.getHeight()));
		g2d.setPaint(new Color(outlineColor));
		g2d.fill(new Ellipse2D.Double(1, 1, bgImg.getWidth() - 2, bgImg.getHeight() - 2));
		g2d.setPaint(new Color(0xb77400));
		g2d.fill(new Ellipse2D.Double(2, 2, bgImg.getWidth() - 4, bgImg.getHeight() - 4));

		sprite.myPixels = ((DataBufferInt) bgImg.getRaster().getDataBuffer()).getData();
		
		return sprite;
	}

	private Sprite getWheelImage(int minSize) {
		int doubleSize = radius * 2;
				
		Sprite sprite = new Sprite(doubleSize, doubleSize);
		BufferedImage image = new BufferedImage(doubleSize, doubleSize,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage overlayImage = new BufferedImage(doubleSize, doubleSize,
				BufferedImage.TYPE_INT_ARGB);

		for (int y = -radius; y < radius; y++) {
			int ySquared = y * y;
			for (int x = -radius; x < radius; x++) {
				double rad = Math.sqrt(ySquared + x * x);

				if (rad < radius && rad >= minSize) {
					int rgb = colorWheelLocationToRGB(x, y, rad);
					double distance = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
					double percent = 1 - (distance / radius);
					int overlayRGB = (((int) (255 * percent) << 24)) | ColourUtils.adjustBrightness(0x00FFFFFF, brightness);
					
					rgb = ColourUtils.exponent(rgb, Rasterizer3D.currentBrightness);
					//overlayRGB = ColourUtils.exponent(ColourUtils.adjustBrightness(overlayRGB, brightness), Rasterizer3D.currentBrightness);
					rgb |= 0xFF000000;
					image.setRGB(x + radius, y + radius, rgb);
					overlayImage.setRGB(x + radius, y + radius, overlayRGB);

				}
			}
		}
		image.getGraphics().drawImage(overlayImage, 0, 0, null);
		//g2d.draw(new Ellipse2D.Double(0, 0, doubleSize, doubleSize));
		sprite.myPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		return sprite;

	}



	private static final float PI_3 = (float)(Math.PI / 3);

	private static int colorWheelLocationToRGB(int x, int y, double rad) {
		double angle = Math.acos((double)x / rad);
		int rgb;

		if (angle < PI_3) {
			if (y < 0) {
				// FFFF00 - FF0000
				rgb = 0xFF0000 | Math.min(255,
						(int)(255 * angle / PI_3)) << 8;
			}
			else {
				// FF0000 - FF00FF
				rgb = 0xFF0000 | Math.min(255,
						(int)(255 * angle / PI_3));
			}
		}
		else if (angle < 2 * PI_3) {
			angle -= PI_3;
			if (y < 0) {
				// 00FF00 - FFFF00
				rgb = 0x00FF00 | Math.max(0, 255 -
						(int)(255 * angle / PI_3)) << 16;
			}
			else {
				// FF00FF - 0000FF
				rgb = 0x0000FF | Math.max(0, 255 -
						(int)(255 * angle / PI_3)) << 16;
			}
		}
		else {
			angle -= 2 * PI_3;
			if (y < 0) {
				// 00FFFF - 00FF00
				rgb = 0x00FF00 | Math.min(255,
						(int)(255 * angle / PI_3));
			}
			else {
				// 0000FF - 00FFFF
				rgb = 0x0000FF | Math.min(255,
						(int)(255 * angle / PI_3)) << 8;
			}
		}
		return rgb;
	}
	public void setValue(int colour) {
		value.set(colour);
	}
	
	public void calculatePosition() {
		int color = getValueInt();
		int idx = ColourUtils.RGB_to_RS2HSB(color);
		for(int index = 0;index<pickerCircle.myPixels.length;index++) {
			if(pickerCircle.myPixels[index] == (0xFF000000 | color)) {
				selectionX = (index % pickerCircle.myWidth);
				selectionY = ((index - selectionX) / pickerCircle.myWidth);
				return;
			}
		}
		System.out.println("Failed to find colour");
	}
}
