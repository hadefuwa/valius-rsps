package com.client.model.entity.impl;

import com.client.cache.animation.Frame;
import com.client.cache.definitions.AnimationDefinition;
import com.client.cache.definitions.GraphicsDefinition;
import com.client.cache.definitions.NpcDefinition;
import com.client.model.Model;
import com.client.model.entity.Entity;

public final class NPC extends Entity {

	private Model method450() {
		if (super.anim >= 0 && super.anInt1529 == 0) {
			int k = AnimationDefinition.anims[super.anim].primaryFrames[super.anInt1527];
			int i1 = -1;
			if (super.anInt1517 >= 0 && super.anInt1517 != super.standAnim)
				i1 = AnimationDefinition.anims[super.anInt1517].primaryFrames[super.anInt1518];
			return desc.method164(i1, k,
					AnimationDefinition.anims[super.anim].interleaveOrder);
		}
		int l = -1;
		if (super.anInt1517 >= 0)
			l = AnimationDefinition.anims[super.anInt1517].primaryFrames[super.anInt1518];
		return desc.method164(-1, l, null);
	}

	@Override
	public Model getRotatedModel() {
		if (desc == null)
			return null;
		Model model = method450();
		if (model == null)
			return null;
		super.height = model.modelHeight;
		if(super.anInt1520 >= GraphicsDefinition.length())
			super.anInt1520 = -1;
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			GraphicsDefinition spotAnim = GraphicsDefinition.forId(super.anInt1520);
			if(spotAnim != null) {
				Model model_1 = spotAnim.getModel();
				if (model_1 != null) {
					int j = spotAnim.animation.primaryFrames[super.anInt1521];
					Model model_2 = new Model(true, Frame.method532(j), false, model_1);
					model_2.method475(0, -super.anInt1524, 0);
					model_2.method469();
					model_2.method470(j);
					model_2.faceGroups = null;
					model_2.vertexGroups = null;
					if (spotAnim.breadthScale != 128 || spotAnim.depthScale != 128)
						model_2.method478(spotAnim.breadthScale, spotAnim.breadthScale,
								spotAnim.depthScale);
					model_2.method479(64 + spotAnim.ambience,
							850 + spotAnim.modelShadow, -30, -50, -30, true);
					Model aModel[] = { model, model_2 };
					model = new Model(aModel);
				}
			}
		}
		if (desc.size == 1)
			model.aBoolean1659 = true;
		return model;
	}

	@Override
	public boolean isVisible() {
		return desc != null;
	}

	public NPC() {
	}

	public NpcDefinition desc;
	public int owner;
}
