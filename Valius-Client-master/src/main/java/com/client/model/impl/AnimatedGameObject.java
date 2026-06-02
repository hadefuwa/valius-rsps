package com.client.model.impl;

import com.client.Client;
import com.client.cache.config.VarBit;
import com.client.cache.definitions.AnimationDefinition;
import com.client.cache.definitions.ObjectDefinition;
import com.client.model.Model;

public class AnimatedGameObject extends Renderable {
	
	private int frame;
	private final int[] morphisms;
	private final int vabit;
	private final int varp;
	private final int vertexHeightBottomLeft;
	private final int vertexHeightBottomRight;
	private final int vertexHeightTopRight;
	private final int vertexHeightTopLeft;
	private AnimationDefinition animationDef;
	private int nextFrameTime;
	public static Client clientInstance;
	private final int objectId;
	private final int type;
	private final int orientation;

	private ObjectDefinition getMorphism() {
		int morphismIndex = -1;
		if (vabit != -1) {
			VarBit varBit = VarBit.cache[vabit];
			int setting = varBit.setting;
			int low = varBit.low;
			int high = varBit.high;
			int mask = Client.BIT_MASK[high - low];
			morphismIndex = clientInstance.variousSettings[setting] >> low & mask;
		} else if (varp != -1)
			morphismIndex = clientInstance.variousSettings[varp];
		
		int var2 = -1;
		if (morphismIndex >= 0 || morphismIndex < morphisms.length)
			var2 = morphisms[morphismIndex];
		else
			var2 = morphisms[morphisms.length - 1];
		
		return var2 == -1 ? null : ObjectDefinition.forID(var2);
	}

	@Override
	public Model getRotatedModel() {
		int j = -1;
		if (animationDef != null) {
			int k = Client.loopCycle - nextFrameTime;
			if (k > 100 && animationDef.loopOffset > 0)
				k = 100;
			while (k > animationDef.method258(frame)) {
				k -= animationDef.method258(frame);
				frame++;
				if (frame < animationDef.frameCount)
					continue;
				frame -= animationDef.loopOffset;
				if (frame >= 0 && frame < animationDef.frameCount)
					continue;
				animationDef = null;
				break;
			}
			nextFrameTime = Client.loopCycle - k;
			if (animationDef != null)
				j = animationDef.primaryFrames[frame];
		}
		ObjectDefinition class46;
		if (morphisms != null)
			class46 = getMorphism();
		else
			class46 = ObjectDefinition.forID(objectId);
		if (class46 == null) {
			return null;
		} else {
			return class46.modelAt(type, orientation, vertexHeightBottomLeft,
					vertexHeightBottomRight, vertexHeightTopRight, vertexHeightTopLeft, j);
		}
	}

	public AnimatedGameObject(int id, int orientation, int type, int vertexHeightBottomRight, int vertexHeightTopRight, int vertexHeightBottomLeft, int vertexHeightTopLeft,
			int animId, boolean animating) {
		this.objectId = id;
		this.type = type;
		this.orientation = orientation;
		this.vertexHeightBottomLeft = vertexHeightBottomLeft;
		this.vertexHeightBottomRight = vertexHeightBottomRight;
		this.vertexHeightTopRight = vertexHeightTopRight;
		this.vertexHeightTopLeft = vertexHeightTopLeft;
		if (animId != -1 && animId < AnimationDefinition.anims.length) {
			animationDef = AnimationDefinition.anims[animId];
			frame = 0;
			nextFrameTime = Client.loopCycle;
			if (animating && animationDef.loopOffset != -1) {
				frame = (int) (Math.random() * animationDef.frameCount);
				nextFrameTime -= (int) (Math.random() * animationDef
						.method258(frame));
			}
		}
		ObjectDefinition definition = ObjectDefinition.forID(objectId);
		vabit = definition.varbit;
		varp = definition.varp;
		morphisms = definition.morphisms;
	}
}