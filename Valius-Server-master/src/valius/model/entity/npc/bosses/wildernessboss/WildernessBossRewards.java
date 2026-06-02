package valius.model.entity.npc.bosses.wildernessboss;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemRarity;
import valius.util.Misc;

/**
 * 
 * @author Divine | Dec. 31, 2018 , 5:37:21 a.m.
 *
 */

public class WildernessBossRewards {
	
	
	/*
	 * An array containing all items dropped from Event Bosses
	 */
    private static Map<ItemRarity, List<Item>> items = new HashMap<>();

	static {
		items.put(ItemRarity.COMMON,
		Arrays.asList(
				//TODO MORE COMMON JUNK REWARDS
				new Item(995, 500_000 + Misc.random(2_000_000)),//gold
				new Item(990, 1 + Misc.random(2)),//ckeys
				new Item(13307, 300 + Misc.random(100)),//blood money
				new Item(537, 50 + Misc.random(30)),//dragon bones
				new Item(11944, 25 + Misc.random(15)),//lava dragon bones
				new Item(11937, 25 + Misc.random(30)),//dark crab
				new Item(11232, 30 + Misc.random(30)),//dragon dart tips
				new Item(208, 20 + Misc.random(10)),//herbs
				new Item(210, 20 + Misc.random(10)),//
				new Item(212, 20 + Misc.random(10)),//
				new Item(214, 20 + Misc.random(10)),//
				new Item(216, 20 + Misc.random(10)),//
				new Item(218, 20 + Misc.random(10)),//
				new Item(220, 20 + Misc.random(10)),//
				new Item(3025, 5 + Misc.random(5)),//potions
				new Item(12696, 5 + Misc.random(5)),//
				new Item(11944, 50 + Misc.random(25)),//
				new Item(12934, Misc.random(50, 100))
			));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(//32994-33009
				new Item(995, 500_000 + Misc.random(4_000_000)),//gold
				new Item(13307, 400 + Misc.random(150)),//blood money
				new Item(990, 2 + Misc.random(3)),//ckeys
				new Item(13307, 500 + Misc.random(100)),//blood money
				new Item(537, 100 + Misc.random(100)),//dragon bones
				new Item(11944, 50 + Misc.random(50)),//lava dragon bones
				new Item(11937, 50 + Misc.random(150)),//dark crab
				new Item(11232, 150 + Misc.random(75)),//dragon dart tips
				new Item(20849, 50),//dragon thrown axe
				new Item(1187, 1),
				new Item(12748, 1),
				new Item(1632, 75),
				new Item(12934, Misc.random(100, 300)),
				new Item(11928, 1),
				new Item(11929, 1),
				new Item(11930, 1),
				new Item(11931, 1),
				new Item(11932, 1),
				new Item(11933, 1),
				new Item(32994, 1),//pvp set items
				new Item(32995, 1),
				new Item(32996, 1),
				new Item(32997, 1),
				new Item(32998, 1),
				new Item(32999, 1),
				new Item(33000, 1),
				new Item(33001, 1),
				new Item(33002, 1),
				new Item(33003, 1),
				new Item(33004, 1),
				new Item(33005, 50),
				new Item(33006, 50),
				new Item(33007, 1),
				new Item(33008, 1),
				new Item(33009, 1),
				new Item(22731, 1),
				new Item(21795, 1),//imbued capes
				new Item(21793, 1),
				new Item(21791, 1)
				
				
			));
		
		items.put(ItemRarity.RARE, Arrays.asList(
				new Item(6585, 1),//fury
				new Item(6199, 1),//Mystery box
				new Item(995, 500_000 + Misc.random(6_000_000)),//gold
				new Item(13307, 500 + Misc.random(1000)),//blood money
				new Item(11928, 1),//ward pieces
				new Item(11929, 1),
				new Item(11930, 1),
				new Item(11931, 1),
				new Item(11932, 1),
				new Item(11933, 1),
				new Item(21902, 1),
				new Item(22296, 1),
				new Item(11920, 1),
				new Item(2581, 1)
			));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList(
				new Item(33153, 1),//Infernal Key
				new Item(19493, 1),//zenyte gem
				new Item(13092, 1),//crystal hally
				new Item(12004, 1),//kraken tent
				new Item(13302, 1),//Graardor key
				new Item(13303, 1),//Tarn key
				new Item (33283),//hearts
				new Item(33284),
				new Item(32285)
				));
	}
	
	/**
	 * Gets a random reward from the array & rewards it to the player
	 * @param p
	 * @return
	 */
	public static void execute(Player player) {
		Item[] KEY = {new Item(33774), new Item(33775), new Item(33776), new Item(33777)};
		Item[] itemList2 = KEY;
		int rewardRoll = Misc.random(1000);// 10 = 1%
		List<Item> itemList = rewardRoll <= 850 ? items.get(ItemRarity.COMMON) : // 85%
				rewardRoll > 850 && rewardRoll <= 940 ? items.get(ItemRarity.UNCOMMON) : // 9.5%
						rewardRoll > 940 && rewardRoll <= 995 ? items.get(ItemRarity.RARE) : // 6.5%
								items.get(ItemRarity.VERY_RARE); // 0.5%
						Item reward = Misc.getRandomItem(itemList);
						Item key = Misc.getRandomItem(itemList2);
						
			player.getItems().addItemUnderAnyCircumstance(reward.getId(), reward.getAmount());
			player.getItems().addItemUnderAnyCircumstance(13307, 500 + Misc.random(500));
			player.getItems().addItemUnderAnyCircumstance(10476, 5 + Misc.random(10));
			player.getItems().addItemUnderAnyCircumstance(995, 2000000 + Misc.random(1000000));
			player.getItems().addItemUnderAnyCircumstance(key.getId(), 1);
			if (Misc.random(1, 300) == 150) {
				player.getItems().addItemUnderAnyCircumstance(33272, 1);
				GlobalMessages.send(Misc.capitalizeJustFirst(player.playerName) + " has just received a Justiciars Sword from an Event Boss!", GlobalMessages.MessageType.LOOT);
			}
			
			player.WildyEventBossDamage = 0;
			player.sendMessage("You receive a " + ItemAssistant.getItemName(reward.getId()));
			for (Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
				for (Item gift_item : gift.getValue())
					if (gift_item == reward)
					if (gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
					GlobalMessages.send(Misc.capitalize(player.playerName) + " received a rare item: "
					+ (reward.getAmount() > 1 ? (reward.getAmount() + "x ")
					: ItemAssistant.getItemName(reward.getId()) + " from a Boss Event."), GlobalMessages.MessageType.LOOT);
			} 
}
