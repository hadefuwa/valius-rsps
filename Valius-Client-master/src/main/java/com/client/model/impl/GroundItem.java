package com.client.model.impl;

import com.client.cache.definitions.ItemDefinition;
import com.client.model.Model;

public final class GroundItem extends Renderable {

	@Override
	public final Model getRotatedModel() {
		ItemDefinition itemDef = ItemDefinition.forID(ID);
		return itemDef.method201(anInt1559);
	}

	public GroundItem() {
	}

	public int ID;
	public int x;
	public int y;
	public int anInt1559;
}
