package com.client.cache.animation;

import com.client.Client;
import com.client.io.Buffer;

public final class Frame {

	public static void method528() {
	 	animationlist = new Frame[4500][0];
	}

	public static void load(int file, byte[] fileData){
		try {
			Buffer stream = new Buffer(fileData);
            FrameBase frameBase = new FrameBase(stream);
			int frameCount = stream.readShort();
			animationlist[file] = new Frame[(int)(frameCount*3)];
			int indices[] = new int[500];
			int transformX[] = new int[500];
			int transformY[] = new int[500];
			int transformZ[] = new int[500];
			for(int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
				int id = stream.readShort();
				Frame frame = animationlist[file][id] = new Frame();
				frame.frameBase = frameBase;
				int transformations = stream.readUnsignedByte();
				int transformation = 0;
				int lastIndex = -1;
				for(int index = 0; index < transformations; index++) {
					int attribute = stream.readUnsignedByte();
					if(attribute > 0) {
						if(frameBase.transformationType[index] != 0) {
							for(int l3 = index - 1; l3 > lastIndex; l3--) {
								if(frameBase.transformationType[l3] != 0)
									continue;
								indices[transformation] = l3;
								transformX[transformation] = 0;
								transformY[transformation] = 0;
								transformZ[transformation] = 0;
								transformation++;
								break;
							}
						}
						indices[transformation] = index;
						int standard = frameBase.transformationType[index]  == 3 ? 128 : 0;
						transformX[transformation] = (attribute & 1) != 0 ? stream.readShort2() : standard;
						transformY[transformation] = (attribute & 2) != 0 ? stream.readShort2() : standard;
						transformZ[transformation] = (attribute & 4) != 0 ? stream.readShort2() : standard;
					
						lastIndex = index;
						transformation++;
					}
				}
				frame.transformationCount = transformation;
				frame.transformationIndices = new int[transformation];
				frame.transformX = new int[transformation];
				frame.transformY = new int[transformation];
				frame.transformZ = new int[transformation];
				for(int k3 = 0; k3 < transformation; k3++) {
					frame.transformationIndices[k3] = indices[k3];
					frame.transformX[k3] = transformX[k3];
					frame.transformY[k3] = transformY[k3];
					frame.transformZ[k3] = transformZ[k3];
				}
			}
		} catch(Exception exception) { }
	}

	public static Frame forId(int i) {
		try {
			final String hexString;
			final int fileId = Integer
					.parseInt((hexString = Integer.toHexString(i)).substring(0, hexString.length() - 4), 16);
			int index = Integer.parseInt(hexString.substring(hexString.length() - 4), 16);
			//System.out.println("file2: " + fileId + " frame: " + index);
			if (animationlist[fileId].length == 0) {
				clientInstance.onDemandFetcher.method558(1, fileId);
				return null;
			}
			return animationlist[fileId][index];
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void nullLoader() {
		animationlist = null;
	}

	public static boolean method532(int i) {
		return i == -1;
	}
	
	public static Frame animationlist[][];
	public int anInt636;
	public FrameBase frameBase;
	public int transformationCount;
	public int transformationIndices[];
	public int transformX[];
	public int transformY[];
	public int transformZ[];
	public static Client clientInstance;

}
