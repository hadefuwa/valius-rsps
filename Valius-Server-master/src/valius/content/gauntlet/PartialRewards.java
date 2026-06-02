/**
 * 
 */
package valius.content.gauntlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.items.Item;
import valius.model.items.ItemRarity;
import valius.util.Misc;

/**
 * @author ReverendDread
 * Sep 14, 2019
 */
public class PartialRewards {
	
	private static Map<ItemRarity, List<Item>> possibleLoot = Maps.newConcurrentMap();
	
	static {
		possibleLoot.put(ItemRarity.COMMON, Arrays.asList(
				new Item(1073, 1),
				new Item(1091, 1),
				new Item(1123, 1),
				new Item(1161, 1),
				new Item(1211, 1),
				new Item(1271, 1),
				new Item(1331, 1),
				new Item(1431, Misc.random(2, 3))
				
				));
		
		possibleLoot.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(851, Misc.random(7, 13)),
				new Item(853, Misc.random(8, 11)),
				new Item(1071, 1),
				new Item(1085, 1),
				new Item(1121, 1),
				new Item(1159, 1),
				new Item(1210, 1),
				new Item(1273, 1),
				new Item(1429, Misc.random(2, 5))
				
				));
	}
	
	private static final int random = Misc.random(200);
	
	public static Item rollForItem(Player player) {
        List<Item> itemList = random < 80 ? possibleLoot.get(ItemRarity.COMMON) : possibleLoot.get(ItemRarity.UNCOMMON);
        return itemList.isEmpty() ? rollForItem(player) : Misc.randomTypeOfList(itemList);
	}
	
	public static void openChest(Player player) {
		if (player.isGaunletLootAvailable()) {
			Item item = rollForItem(player);
			player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
			player.sendMessage("You receive a small reward for partially completing The Gauntlet!");
		}
		player.setGaunletLootAvailable(false);
		GauntletPrepRoom.sendChest(player);
		PlayerSave.save(player);
	}

}
