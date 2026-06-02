package valius.model.items;

import lombok.Data;

@Data
public class LootItem {
	private final int id, min, max;
	private final ItemRarity rarity;
	public static LootItem of(int id, int min, int max, ItemRarity rarity) {
		return new LootItem(id, min, max, rarity);
	}
}
