package com.client.misc;

import java.util.Arrays;

public class ItemBonusDefinition {

	private short id;
	private int[] bonuses;

	public int[] getBonuses() {
		return bonuses;
	}

	public int getId() {
		return id;
	}
	
	public boolean hasValues() {
		return Arrays.stream(bonuses).anyMatch(intVal -> intVal != 0);
	}

	@Override
	public String toString() {
		return "ItemBonusDefinition [id=" + id + ", bonuses=" + Arrays.toString(bonuses) + "]";
	}
}