package valius.model.entity.npc.combat.impl.eventboss.drop;

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
 * @author Divine
 * Apr. 16, 2019 1:58:56 a.m.
 */

/*
 * An array that handles the Colossal Chickens drops (Easter 2019)
 */
public class ColossalChickenDrops {

	
	/*
	 * An array containing all items dropped from the Colossal Chicken
	 */
    private static Map<ItemRarity, List<Item>> items = new HashMap<>();
	
	static {
		items.put(ItemRarity.COMMON,
		Arrays.asList(
				new Item(995, 500_000 + Misc.random(5_000_000)),//gold
				new Item(990, 2 + Misc.random(3)),//ckeys
				new Item(13307, 1500 + Misc.random(50)),//blood money
				new Item(6199, 1)//mbox
			));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(33285, 1),//easter mbox
				new Item(1037, 1),//bunny ears
				new Item(23448, 1),//bunny mask
				new Item(23446, 1)//Giant Egg
			));
		
		items.put(ItemRarity.RARE, Arrays.asList(
				new Item (4565, 1),//Easter Basket
				new Item(7927, 1)//easter ring
			));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList(
				new Item(33287, 1),//bunny
				new Item(33288, 1),
				new Item(33289, 1),
				new Item(33290, 1),
				new Item(33291, 1),
				new Item(33294, 1),
				new Item(33295, 1),
				new Item(33296, 1),
				new Item(33297, 1),
				new Item(33269, 1)
				));
	}
	
	/**
	 * Gets a random reward from the array & rewards it to the player
	 * @param p
	 * @return
	 */
	public static void execute(Player player) {
		int rewardRoll = Misc.random(1000);// 10 = 1%
		List<Item> itemList = rewardRoll <= 500 ? items.get(ItemRarity.COMMON) :
				rewardRoll > 500 && rewardRoll <= 900 ? items.get(ItemRarity.UNCOMMON) :
						rewardRoll > 900 && rewardRoll <= 950 ? items.get(ItemRarity.RARE) :
								items.get(ItemRarity.VERY_RARE);
						Item reward = Misc.getRandomItem(itemList);

			player.getItems().addItem(reward.getId(), reward.getAmount());
			player.getItems().addItemUnderAnyCircumstance(7932, 1 + Misc.random(2));
			player.sendMessage("You receive a " + ItemAssistant.getItemName(reward.getId()));
			for (Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
				for (Item gift_item : gift.getValue())
					if (gift_item == reward)
					if (gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
					GlobalMessages.send(Misc.capitalize(player.playerName) + " received a rare item: "
					+ (reward.getAmount() > 1 ? (reward.getAmount() + "x ")
					: ItemAssistant.getItemName(reward.getId()) + " from a EASTER Boss Event."), GlobalMessages.MessageType.LOOT);
			}
	}