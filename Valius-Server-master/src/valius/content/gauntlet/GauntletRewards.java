/**
 * 
 */
package valius.content.gauntlet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.GlobalMessages.MessageType;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.items.Item;
import valius.model.items.ItemRarity;
import valius.util.Misc;

/**
 * @author ReverendDread
 * Sep 14, 2019
 */
public class GauntletRewards {
	
	private static Map<ItemRarity, List<Item>> possibleLoot = Maps.newConcurrentMap();
	
	static {
		possibleLoot.put(ItemRarity.COMMON, Arrays.asList(
				new Item(1164, Misc.random(2, 6)),
				new Item(1080, Misc.random(1, 4)),
				new Item(1094, Misc.random(1, 4)),
				new Item(1114, Misc.random(1, 4)),
				new Item(1128, Misc.random(1, 4)),
				new Item(1276, Misc.random(1, 4)),
				new Item(3203, Misc.random(1, 4)),
				new Item(564, Misc.random(160, 340)),
				new Item(561, Misc.random(100, 190)),
				new Item(563, Misc.random(80, 190)),
				new Item(562, Misc.random(180, 350)),
				new Item(560, Misc.random(100, 190)),
				new Item(565, Misc.random(80, 170)),
				new Item(888, Misc.random(800, 1400)),
				new Item(890, Misc.random(400, 700)),
				new Item(892, Misc.random(200, 350)),
				new Item(11212, Misc.random(30, 90)),
				new Item(1624, Misc.random(20, 80)),
				new Item(1622, Misc.random(10, 70)),
				new Item(1620, Misc.random(5, 50)),
				new Item(1618, Misc.random(3, 12)),
				new Item(1392, Misc.random(4, 16)),
				new Item(995, Misc.random(1000000, 5000000)),
				new Item(3205, 1)));

			possibleLoot.put(ItemRarity.RARE, Arrays.asList(
					new Item(4207, 1),
					new Item(23956, 1),
					new Item(23953, 1)));

			possibleLoot.put(ItemRarity.VERY_RARE, Arrays.asList(
						new Item(23997, 1),
						new Item(33719, 1),
						new Item(23757, 1)));
	}

	private static final int randomother = Misc.random(500);
	private static final int randomseed = Misc.random(100);
	
	public static Item rollForItem(Player player) {
        List<Item> itemList = possibleLoot.get(ItemRarity.COMMON);
        return itemList.isEmpty() ? rollForItem(player) : Misc.randomTypeOfList(itemList);
	}
	
	public static void openChest(Player player) {
		if (player.isGaunletLootAvailable()) {
			Item item = rollForItem(player);
			player.getItems().addItem(23962, Misc.random(3, 7));
			player.getItems().addItem(13307, Misc.random(500, 1500));
			player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
			
			//second roll
			Item item2 = rollForItem(player);
			player.getItems().addItemUnderAnyCircumstance(item2.getId(), item2.getAmount());
			
			//third roll for rares
			if (randomother == 5) {
				possibleLoot.get(ItemRarity.VERY_RARE);
				GlobalMessages.send("" + player.playerName + " has received a " + item.getName() + " from The Gauntlet!", MessageType.LOOT);
			}
			
			else if (randomseed == 5) {
				possibleLoot.get(ItemRarity.RARE);
				GlobalMessages.send("" + player.playerName + " has received a " + item.getName() + " from The Gauntlet!", MessageType.LOOT);
			} 
		}
		player.setGaunletLootAvailable(false);
		GauntletPrepRoom.sendChest(player);
		PlayerSave.save(player);
	}

}
