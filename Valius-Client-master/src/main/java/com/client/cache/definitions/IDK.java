package com.client.cache.definitions;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.util.Arrays;

import com.client.cache.Archive;
import com.client.io.Buffer;
import com.client.model.Model;

public final class IDK {

	public static void unpackConfig(Archive streamLoader) {
		Buffer stream = new Buffer(streamLoader.getDataForName("idk.dat"));
		length = stream.readShort();
		if (cache == null)
			cache = new IDK[length];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null)
				cache[j] = new IDK();
			cache[j].readValues(stream);
		}
	}

	private void readValues(Buffer stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				bodyPartId = stream.readUnsignedByte();
			else if (i == 2) {
				int j = stream.readUnsignedByte();
				modelIds = new int[j];
				for (int k = 0; k < j; k++)
					modelIds[k] = stream.readShort();
			} else if (i == 3)
				nonSelectable = true;
			else if (i == 40) {
				int length = stream.readUnsignedByte();
				colourToFind = new int[length];
				colourToReplace = new int[length];
				for(int idx = 0;idx<length;idx++) {
					colourToFind[idx] = stream.readShort();
					colourToReplace[idx] = stream.readShort();
				}
			} else if (i == 41) {
				int length = stream.readUnsignedByte();
				textureToFind = new int[length];
				textureToReplace = new int[length];
				for(int idx = 0;idx<length;idx++) {
					textureToFind[idx] = stream.readShort();
					textureToReplace[idx] = stream.readShort();
				}
			} else if (i >= 60 && i < 70) {
				models[i - 60] = stream.readShort();
				if(models[i - 60] == 65535)
					models[i - 60] = -1;
			}
			else
				System.out.println("Error unrecognised config code: " + i);
		} while (true);
	}

	public boolean method537() {
		if (modelIds == null)
			return true;
		boolean flag = true;
		for (int j = 0; j < modelIds.length; j++)
			if (!Model.method463(modelIds[j]))
				flag = false;

		return flag;
	}

	public Model method538() {
		if (modelIds == null)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[modelIds.length];
		for (int i = 0; i < modelIds.length; i++)
			aclass30_sub2_sub4_sub6s[i] = Model.method462(modelIds[i]);

		Model model;
		if (aclass30_sub2_sub4_sub6s.length == 1)
			model = aclass30_sub2_sub4_sub6s[0];
		else
			model = new Model(aclass30_sub2_sub4_sub6s.length,
					aclass30_sub2_sub4_sub6s);
		if(colourToFind != null)
		for (int j = 0; j < colourToFind.length; j++) {
			model.replaceColor(colourToFind[j], colourToReplace[j]);
		}

		if(textureToFind != null)
		for (int j = 0; j < textureToFind.length; j++) {
			model.replaceTexture(textureToFind[j], textureToReplace[j]);
		}

		return model;
	}

	public boolean method539() {
		boolean flag1 = true;
		for (int i = 0; i < 5; i++)
			if (models[i] != -1 && !Model.method463(models[i]))
				flag1 = false;

		return flag1;
	}

	public Model method540() {
		Model aclass30_sub2_sub4_sub6s[] = new Model[5];
		int j = 0;
		for (int k = 0; k < 5; k++)
			if (models[k] != -1)
				aclass30_sub2_sub4_sub6s[j++] = Model
						.method462(models[k]);

		Model model = new Model(j, aclass30_sub2_sub4_sub6s);
		if(colourToFind != null)
			for (int l = 0; l < colourToFind.length; l++) {
				model.replaceColor(colourToFind[l], colourToReplace[l]);
			}
		if(textureToFind != null)
		for (int l = 0; l < textureToFind.length; l++) {
			model.replaceTexture(textureToFind[l], textureToReplace[l]);
		}


		return model;
	}

	private IDK() {
		bodyPartId = -1;
		nonSelectable = false;
	}

	public static int length;
	public static IDK cache[];
	public int bodyPartId;
	private int[] modelIds;
	private int[] colourToFind;
	private int[] colourToReplace;
	private int[] textureToFind;
	private int[] textureToReplace;
	private final int[] models = { -1, -1, -1, -1, -1 };
	public boolean nonSelectable;
}
