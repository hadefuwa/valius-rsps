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
 * @author Divine | 6:18:23 a.m. | Aug. 2, 2019
 *
 */

public class NightmareDrops {
	
	
	/*
	 * The ID of the Nightmare Key
	 */
	public static final int NIGHTMARE_KEY = 33592;
	
	/*
	 * An array containing all items dropped from Nightmare
	 */
    private static Map<ItemRarity, List<Item>> items = new HashMap<>();
	
	static {
		items.put(ItemRarity.COMMON,
		Arrays.asList(
				new Item(995, 500_000 + Misc.random(5_000_000)),//gold
				new Item(990, 5 + Misc.random(5)),//ckeys
				new Item(11937, 50 + Misc.random(250)),//dark crab
				new Item(11232, 250 + Misc.random(250)),//dragon dart tips
				new Item(11237, 250 + Misc.random(250)),//dragon arrow tips
				new Item(6572, 1 + Misc.random(1, 2)),//uncut onyx
				new Item(2528, 1),//lamp
				new Item(6199, 1), //Mbox
				new Item (21892, 1),//Dragon platebody
				new Item(11335, 1)//dragon fullhelm
			));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(13307, 3500 + Misc.random(1000)),//Blood money
				new Item(22125, 100 + Misc.random(150)),//Superior Dragon bones
				new Item(2577, 1),//ranger boots
				new Item(19493, 1),//zenyte gem
				new Item(1514, 500 + Misc.random(200)),//Magic logs
				new Item(8800, 1),//Valius token
				new Item(33269, 1),// Valius Mbox
				new Item(33464, 1),//1 HR 200% xp
				new Item(6914, 1),//Master wand
				new Item(12785, 1),//row (i) scroll
				new Item(33153, 1)//Infernal Key
				
			));
		
		items.put(ItemRarity.RARE, Arrays.asList(
				new Item(33571, 1)//Grotesque upgrade kit
			));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList(
				new Item(33569, 1),//grotesque
				new Item(33567, 1),//
				new Item(33568, 1),//
				new Item(33531, 1)//boogie
			));
	}
	
	/**
	 * Gets a random reward from the array & rewards it to the player
	 * @param p
	 * @return
	 */
	
	public static void execute(Player player) {
		int petChance = Misc.random(1, 500);
		int rewardRoll = Misc.random(1000);// 10 = 1%
		List<Item> itemList = rewardRoll <= 500 ? items.get(ItemRarity.COMMON) : 
				rewardRoll > 500 && rewardRoll <= 950 ? items.get(ItemRarity.UNCOMMON) : 
					rewardRoll > 950 && rewardRoll <= 990 ? items.get(ItemRarity.RARE) : 
						items.get(ItemRarity.VERY_RARE); 
						Item reward = Misc.getRandomItem(itemList);
		if (player.getItems().playerHasItem(NIGHTMARE_KEY)) {
			player.getItems().deleteItem(NIGHTMARE_KEY, 1);
			if (petChance == 5) {
				player.getItems().addItemUnderAnyCircumstance(33593, 1);
				GlobalMessages.send("" + Misc.capitalizeJustFirst(player.playerName) + " has just received a Pet Nightmare from Nightmare", GlobalMessages.MessageType.PET);
			}
				if (player.getItems().isWearingItem(33336) &&
						player.getItems().isWearingItem(33331) &&
						player.getItems().isWearingItem(33332) &&
						player.getItems().isWearingItem(33334)) {
					player.getItems().addItemUnderAnyCircumstance(33269, 1);
					player.sendMessage("You receive a Valius Mystery box for wearing the Ancient armor set!");
					} else if (player.getItems().isWearingItem(33520) &&
							player.getItems().isWearingItem(33516) &&
							player.getItems().isWearingItem(33517) &&
							player.getItems().isWearingItem(33518)) {
						player.getItems().addItemUnderAnyCircumstance(33269, 1);
						player.sendMessage("You receive a Valius Mystery box for wearing the Ancient armor set!");
						}
			player.getItems().addItem(reward.getId(), reward.getAmount());
			player.getItems().addItemUnderAnyCircumstance(10476, 5 + Misc.random(5));
			player.NightmareDamage = 0;
			player.sendMessage("You unlock the chest and pull out a " + ItemAssistant.getItemName(reward.getId()));
			for (Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
				for (Item gift_item : gift.getValue())
					if (gift_item == reward)
					if (gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
					GlobalMessages.send(Misc.capitalize(player.playerName) + " received a rare item: "
					+ (reward.getAmount() > 1 ? (reward.getAmount() + "x ")
					: ItemAssistant.getItemName(reward.getId()) + " from Nightmare."), GlobalMessages.MessageType.LOOT);
			} else {
			player.sendMessage("You need a Nightmare Key to open this!");
			}
	}
}
