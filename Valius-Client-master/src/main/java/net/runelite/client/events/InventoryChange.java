package net.runelite.client.events;

import lombok.Value;

@Value
public class InventoryChange {

	private int inventoryId;
	private int[] itemIds, itemCounts;
}
