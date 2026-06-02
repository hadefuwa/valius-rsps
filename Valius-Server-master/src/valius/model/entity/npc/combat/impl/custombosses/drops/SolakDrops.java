package valius.model.entity.npc.combat.impl.custombosses.drops;

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
 * @author Divine | 8:24:45 p.m. | Nov. 14, 2019
 *
 */

public class SolakDrops {
	
	/*
	 * An array containing all items dropped from Solak
	 */
    private static Map<ItemRarity, List<Item>> items = new HashMap<>();
	
	static {
		items.put(ItemRarity.COMMON,
		Arrays.asList(
				new Item(995, 200_000 + Misc.random(2_000_000)),//gold
				new Item(33912, Misc.random(1, 6)),//serenic scales
				new Item(1080, 2 + Misc.random(5)),//rune legs
				new Item(1128, 2 + Misc.random(5)),//rune plate
				new Item(1164, 5 + Misc.random(10)),//rune helm
				new Item(246, 1 + Misc.random(1, 2)),//uncut onyx
				new Item(1632, 1 + Misc.random(30, 50)),//uncut dstone
				new Item(214, Misc.random(20, 50)),//kwauarm
				new Item(216, Misc.random(20, 50)),//cadantine
				new Item(216, Misc.random(20, 50)),//dwarfweed
				new Item(9193, 50 + Misc.random(50, 120))//black dhide
			));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(995, 500_000 + Misc.random(5_000_000)),//gold
				new Item(1080, 5 + Misc.random(10)),//rune legs
				new Item(1128, 5 + Misc.random(10)),//rune plate
				new Item(1164, 10 + Misc.random(20)),//rune helm
				new Item(6572, 1 + Misc.random(1, 4)),//uncut onyx
				new Item(1632, 1 + Misc.random(50, 80)),//uncut dstone
				new Item(214, Misc.random(30, 80)),//kwauarm
				new Item(216, Misc.random(30, 80)),//cadantine
				new Item(218, Misc.random(30, 80)),//dwarfweed
				new Item(22125, 100 + Misc.random(1, 50)),//Superior Dragon bones
				new Item(1514, 200 + Misc.random(1, 200)),//Magic logs
				new Item(33912, Misc.random(5, 15)),//serenic scales
				new Item(11212, 50 + Misc.random(1, 50)),//darrows
				new Item(1392, 30 + Misc.random(20, 60)),//bstaffs
				new Item(9194, 30 + Misc.random(20, 60)),//onyx bolttips
				new Item(9193, 40 + Misc.random(30, 80))//dstone bolttips
				
			));
		
		items.put(ItemRarity.RARE, Arrays.asList(
				new Item(33913, 1),//thread
				new Item(33912, Misc.random(20, 40))//serenic scales
			));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList(
				new Item(33915, 1),//stock
				new Item(33916, 1)//unf bow
			));
	}
	
	/**
	 * Gets a random reward from the array & rewards it to the player
	 * @param p
	 * @return
	 */
	
	public static void execute(Player player) {
		int petChance = Misc.random(1, 500);
		int rewardRoll = Misc.random(1000);// 1 = 0.01%
		List<Item> itemList = rewardRoll <= 700 ? items.get(ItemRarity.COMMON) : 
				rewardRoll > 700 && rewardRoll <= 980 ? items.get(ItemRarity.UNCOMMON) : 
					rewardRoll > 980 && rewardRoll <= 999 ? items.get(ItemRarity.RARE) : 
						items.get(ItemRarity.VERY_RARE); 
						Item reward = Misc.getRandomItem(itemList);
			if (petChance == 5) {
				player.getItems().addItemUnderAnyCircumstance(33923, 1);
				GlobalMessages.send("" + Misc.capitalizeJustFirst(player.playerName) + " has just received a Pet Solak from Solak", GlobalMessages.MessageType.PET);
			}
			player.getItems().addItemUnderAnyCircumstance(reward.getId(), reward.getAmount());
			player.SolakDamage = 0;
			player.sendMessage("As Solak dies, a reward appears in your inventory/bank. Reward: " + ItemAssistant.getItemName(reward.getId()));
			for (Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
				for (Item gift_item : gift.getValue())
					if (gift_item == reward)
					if (gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
					GlobalMessages.send(Misc.capitalize(player.playerName) + " received a rare item: "
					+ (reward.getAmount() > 1 ? (reward.getAmount() + "x ")
					: ItemAssistant.getItemName(reward.getId()) + " from Solak."), GlobalMessages.MessageType.LOOT);
	}
}
