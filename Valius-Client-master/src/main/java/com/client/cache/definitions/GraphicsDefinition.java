package com.client.cache.definitions;

import com.client.cache.Archive;
import com.client.collection.Cache;
import com.client.io.Buffer;
import com.client.model.Model;

public final class GraphicsDefinition {

	public static void unpackConfig(Archive streamLoader) {
		Buffer stream = new Buffer(streamLoader.getDataForName("spotanim.dat"));
		int length = stream.readShort();
		if (cache == null)
			cache = new GraphicsDefinition[length + 15000];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new GraphicsDefinition();
			}
			if (j == 65535) {
				j = -1;
			}
			cache[j].index = j;
			cache[j].setDefault();
			cache[j].readValues(stream);
		}
		
		cache[1282] = new GraphicsDefinition();
		cache[1282].index = 1282;
		cache[1282].modelId = 44811;
		cache[1282].animationId = 7155;
		cache[1282].animation = AnimationDefinition.anims[cache[1282].animationId];
	}

	private void readValues(Buffer stream) {
		while(true) {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelId = stream.readShort();
			} else if (i == 2) {
				animationId = stream.readShort();
				if (AnimationDefinition.anims != null) {
					animation = AnimationDefinition.anims[animationId];
				}
			} else if (i == 4) {
				breadthScale = stream.readShort();
			} else if (i == 5) {
				depthScale = stream.readShort();
			} else if (i == 6) {
				orientation = stream.readShort();
			} else if (i == 7) {
				ambience = stream.readUnsignedByte();
			} else if (i == 8) {
				modelShadow = stream.readUnsignedByte();
			} else if (i == 40) {
				int j = stream.readUnsignedByte();
				for (int k = 0; k < j; k++) {
					originalColours[k] = stream.readShort();
					replacementColours[k] = stream.readShort();
				}
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		}
	}

	
	public static GraphicsDefinition fetch(int modelId) {
		for (GraphicsDefinition anim : cache) {
			if (anim == null) {
				continue;
			}
			if (anim.modelId == modelId) {
				return anim;
			}
		}
		return null;
	}

	public Model getModel() {
		Model model = (Model) modelCache.insertFromCache(index);
		if (model != null)
			return model;
		model = Model.method462(modelId);
		if (model == null)
			return null;
		for (int i = 0; i < originalColours.length; i++)
			if (originalColours[0] != 0) //default frame id
				model.replaceColor(originalColours[i], replacementColours[i]);
		if(scaleX != 128 || scaleY != 128 || scaleZ != 128) {
			model.method478(scaleX, scaleZ, scaleY);
		}
		if(translateX != 0 || translateY != 0 || translateZ != 0) {
			model.method475(translateX, translateY, translateZ);
		}
		modelCache.removeFromCache(model, index);
		return model;
	}
	
	private void setDefault() {
		modelId = -1;
		animationId = -1;
		originalColours = new int[6];
		replacementColours = new int[6];
		breadthScale = 128;
		depthScale = 128;
		orientation = 0;
		ambience = 0;
		modelShadow = 0;
		scaleX = 128;
		scaleY = 128;
		scaleZ = 128;
		translateX = 0;
		translateY = 0;
		translateZ = 0;
	}

	public GraphicsDefinition() {
		anInt400 = 9;
		animationId = -1;
		originalColours = new int[6];
		replacementColours = new int[6];
		breadthScale = 128;
		depthScale = 128;
		scaleX = 128;
		scaleY = 128;
		scaleZ = 128;
		translateX = 0;
		translateY = 0;
		translateZ = 0;
	}
	
	public int getModelId() {
		return modelId;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static GraphicsDefinition forId(int id) {
		switch(id) {
		
		case 3004:
            cache[3004] = new GraphicsDefinition();
            cache[3004].modelId = 61427;
            cache[3004].animationId = 328;
            cache[3004].animation = AnimationDefinition.anims[cache[3004].animationId];
            cache[3004].originalColours = new int[6];
            cache[3004].replacementColours = new int[6];
            cache[3004].breadthScale = 128;
            cache[3004].depthScale = 128;
            cache[3004].orientation = 0;
            cache[3004].ambience = 0;
            cache[3004].modelShadow = 0;
            cache[3004].scaleX = 300;
            cache[3004].scaleY = 300;
            cache[3004].scaleZ = 300;
            cache[3004].translateX = 0;
            cache[3004].translateY = 0;
            cache[3004].translateZ = 0;
            break;
            
		case 3005:
            cache[3005] = new GraphicsDefinition();
            cache[3005].modelId = 50112;
            cache[3005].animationId = 1685;
            cache[3005].animation = AnimationDefinition.anims[cache[3005].animationId];
            cache[3005].originalColours = new int[6];
            cache[3005].replacementColours = new int[6];
            cache[3005].breadthScale = 128;
            cache[3005].depthScale = 128;
            cache[3005].orientation = 0;
            cache[3005].ambience = 192;
            cache[3005].modelShadow = 0;
            cache[3005].scaleX = 128;
            cache[3005].scaleY = 128;
            cache[3005].scaleZ = 128;
            cache[3005].translateX = 0;
            cache[3005].translateY = 0;
            cache[3005].translateZ = 0;
            break;
            
		case 3006:
            cache[3006] = new GraphicsDefinition();
            cache[3006].modelId = 50113;
            cache[3006].animationId = 1134;
            cache[3006].animation = AnimationDefinition.anims[cache[3006].animationId];
            cache[3006].originalColours = new int[6];
            cache[3006].replacementColours = new int[6];
            cache[3006].breadthScale = 128;
            cache[3006].depthScale = 128;
            cache[3006].orientation = 0;
            cache[3006].ambience = 192;
            cache[3006].modelShadow = 0;
            cache[3006].scaleX = 128;
            cache[3006].scaleY = 128;
            cache[3006].scaleZ = 128;
            cache[3006].translateX = 0;
            cache[3006].translateY = 0;
            cache[3006].translateZ = 0;
            break;
            
		case 3007:
            cache[3007] = new GraphicsDefinition();
            cache[3007].modelId = 50114;
            cache[3007].animationId = 1675;
            cache[3007].animation = AnimationDefinition.anims[cache[3007].animationId];
            cache[3007].originalColours = new int[6];
            cache[3007].replacementColours = new int[6];
            cache[3007].breadthScale = 128;
            cache[3007].depthScale = 128;
            cache[3007].orientation = 0;
            cache[3007].ambience = 64;
            cache[3007].modelShadow = 0;
            cache[3007].scaleX = 128;
            cache[3007].scaleY = 128;
            cache[3007].scaleZ = 128;
            cache[3007].translateX = 0;
            cache[3007].translateY = 0;
            cache[3007].translateZ = 0;
            break;
            
		case 3008:
            cache[3008] = new GraphicsDefinition();
            cache[3008].modelId = 50115;
            cache[3008].animationId = 1689;
            cache[3008].animation = AnimationDefinition.anims[cache[3008].animationId];
            cache[3008].originalColours = new int[6];
            cache[3008].replacementColours = new int[6];
            cache[3008].breadthScale = 128;
            cache[3008].depthScale = 128;
            cache[3008].orientation = 0;
            cache[3008].ambience = 64;
            cache[3008].modelShadow = 0;
            cache[3008].scaleX = 128;
            cache[3008].scaleY = 128;
            cache[3008].scaleZ = 128;
            cache[3008].translateX = 0;
            cache[3008].translateY = 0;
            cache[3008].translateZ = 0;
            break;
            
            
		case 3009:
            cache[3009] = new GraphicsDefinition();
            cache[3009].modelId = 50116;
            cache[3009].animationId = 693;
            cache[3009].animation = AnimationDefinition.anims[cache[3009].animationId];
            cache[3009].originalColours = new int[6];
            cache[3009].replacementColours = new int[6];
            cache[3009].breadthScale = 128;
            cache[3009].depthScale = 128;
            cache[3009].orientation = 0;
            cache[3009].ambience = 64;
            cache[3009].modelShadow = 0;
            cache[3009].scaleX = 128;
            cache[3009].scaleY = 128;
            cache[3009].scaleZ = 128;
            cache[3009].translateX = 0;
            cache[3009].translateY = 0;
            cache[3009].translateZ = 0;
            break;
            
		case 3010:
            cache[3010] = new GraphicsDefinition();
            cache[3010].modelId = 50117;
            cache[3010].animationId = 7883;
            cache[3010].animation = AnimationDefinition.anims[cache[3010].animationId];
            cache[3010].originalColours = new int[6];
            cache[3010].replacementColours = new int[6];
            cache[3010].breadthScale = 128;
            cache[3010].depthScale = 128;
            cache[3010].orientation = 0;
            cache[3010].ambience = 64;
            cache[3010].modelShadow = 0;
            cache[3010].scaleX = 128;
            cache[3010].scaleY = 128;
            cache[3010].scaleZ = 128;
            cache[3010].translateX = 0;
            cache[3010].translateY = 0;
            cache[3010].translateZ = 0;
            break;
            
		case 3011:
            cache[3011] = new GraphicsDefinition();
            cache[3011].modelId = 50118;
            cache[3011].animationId = 7616;
            cache[3011].animation = AnimationDefinition.anims[cache[3011].animationId];
            cache[3011].originalColours = new int[6];
            cache[3011].replacementColours = new int[6];
            cache[3011].breadthScale = 128;
            cache[3011].depthScale = 128;
            cache[3011].orientation = 0;
            cache[3011].ambience = 64;
            cache[3011].modelShadow = 0;
            cache[3011].scaleX = 128;
            cache[3011].scaleY = 128;
            cache[3011].scaleZ = 128;
            cache[3011].translateX = 0;
            cache[3011].translateY = 0;
            cache[3011].translateZ = 0;
            break;
            
		case 3012:
            cache[3012] = new GraphicsDefinition();
            cache[3012].modelId = 50119;
            cache[3012].animationId = 7615;
            cache[3012].animation = AnimationDefinition.anims[cache[3012].animationId];
            cache[3012].originalColours = new int[6];
            cache[3012].replacementColours = new int[6];
            cache[3012].breadthScale = 128;
            cache[3012].depthScale = 128;
            cache[3012].orientation = 0;
            cache[3012].ambience = 64;
            cache[3012].modelShadow = 0;
            cache[3012].scaleX = 128;
            cache[3012].scaleY = 128;
            cache[3012].scaleZ = 128;
            cache[3012].translateX = 0;
            cache[3012].translateY = 0;
            cache[3012].translateZ = 0;
            break;
            
		case 3013:
            cache[3013] = new GraphicsDefinition();
            cache[3013].modelId = 50120;
            cache[3013].animationId = 7505;
            cache[3013].animation = AnimationDefinition.anims[cache[3013].animationId];
            cache[3013].originalColours = new int[6];
            cache[3013].replacementColours = new int[6];
            cache[3013].breadthScale = 128;
            cache[3013].depthScale = 128;
            cache[3013].orientation = 0;
            cache[3013].ambience = 64;
            cache[3013].modelShadow = 0;
            cache[3013].scaleX = 128;
            cache[3013].scaleY = 128;
            cache[3013].scaleZ = 128;
            cache[3013].translateX = 0;
            cache[3013].translateY = 0;
            cache[3013].translateZ = 0;
            break;
            
		
			case 1180:

				cache[1180].scaleX = 128 * 3;
				cache[1180].scaleY = 128 * 3;
				cache[1180].scaleZ = 128 * 3;

				cache[1180].translateY = 32;
				
				
			break;
		}
		return cache[id];
	}
	
	

	public final int anInt400;
	private int scaleX = 128, scaleY = 128, scaleZ = 128;
	private int translateX, translateY, translateZ;
	private static GraphicsDefinition cache[];
	private int index;
	private int modelId;
	public int animationId;
	public AnimationDefinition animation;
	public int[] originalColours;
	public int[] replacementColours;
	public int breadthScale;
	public int depthScale;
	public int orientation;
	public int ambience;
	public int modelShadow;
	public static Cache modelCache = new Cache(30);
	
	public static int length() {
		return cache.length;
	}

	public static void reset() {
		cache = null;
	}

}
