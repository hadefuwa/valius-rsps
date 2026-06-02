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
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * 
 * @author Divine | Dec. 31, 2018 , 5:37:21 a.m.
 *
 */

public class EnragedGraardorDrops {
	
	
	/*
	 * The ID of the Event Key
	 */
	public static final int EVENT_KEY = 13302;
	
	public static final int EVENT_CHEST = 33114;
	
	
	/*
	 * An array containing all items dropped from Event Bosses
	 */
    private static Map<ItemRarity, List<Item>> items = new HashMap<>();
	
	static {
		items.put(ItemRarity.COMMON,
		Arrays.asList(
				new Item(995, 500_000 + Misc.random(5_000_000)),//gold
				new Item(990, 2 + Misc.random(3)),//ckeys
				new Item(13307, 1500 + Misc.random(50)),//blood money
				new Item(537, 100 + Misc.random(100)),//dragon bones
				new Item(11944, 50 + Misc.random(50)),//lava dragon bones
				new Item(11937, 50 + Misc.random(150)),//dark crab
				new Item(11232, 150 + Misc.random(75)),//dragon dart tips
				new Item(6585, 1),//fury
				new Item(6199, 1),//Mystery box
				new Item(20849, 50)//dragon thrown axe
			));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(11836, 1),//bandos boots
				new Item(13307, 2500 + Misc.random(100)),//Blood money
				new Item(22125, 25 + Misc.random(50)),//Superior Dragon bones
				new Item(2528, 1),// XP lamp
				new Item(11212, 100 + Misc.random(75)),//dragon arrows 
				new Item(11230, 150 + Misc.random(75)),//dragon darts
				new Item(6572, 1),//uncut onyx
				new Item(11907, 1),//trident of sea
				new Item(6731, 1),//seers ring
				new Item(6733, 1),//archer ring
				new Item(6735, 1),//warrior ring
				new Item(6737, 1),//berserker ring
				new Item(20849, 50 + Misc.random(50))//dragon thrown axe
				
			));
		
		items.put(ItemRarity.RARE, Arrays.asList(
				new Item(33153, 1),//Infernal Key
				new Item(2572, 1),//ring of wealth
				new Item(4151, 1),//Whip
				new Item(19994, 1),//ranger gloves
				new Item(19493, 1),//zenyte gem
				new Item(12022, 1),//bandos casket
				new Item(33135, 1),//Bandos Mask
				new Item(11834, 1),//Bandos tassets
				new Item(11836, 1),//Bandos chest
				new Item(33270, 1),//dhunter mbox
				new Item(12004, 1)//kraken tent
			));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList(
				new Item(33336, 1),//event armor
				new Item(33331, 1),
				new Item(33332, 1),
				new Item(33334, 1),
				new Item(33765, 1),//Infernal weapon enhancement kit
				new Item(33277, 1),//Infernal staff
				new Item(33278, 1),//Infernal rapier
				new Item(33281, 1),//Infernal bow
				new Item(33282, 1),//Infernal hammer
				new Item(33248, 1)//pet jad (inferno jad)
				));
	}
	
	/**
	 * Gets a random reward from the array & rewards it to the player
	 * @param p
	 * @return
	 */
	
	public static void execute(Player player) {
		int rewardRoll = Misc.random(1000);// 10 = 1%
		List<Item> itemList = rewardRoll <= 500 ? items.get(ItemRarity.COMMON) : // 50%
				rewardRoll > 500 && rewardRoll <= 930 ? items.get(ItemRarity.UNCOMMON) : // 44.0%
						rewardRoll > 930 && rewardRoll <= 950 ? items.get(ItemRarity.RARE) : // 5%
								items.get(ItemRarity.VERY_RARE); // 1%
						Item reward = Misc.getRandomItem(itemList);

		if (player.getItems().playerHasItem(EVENT_KEY)) {
			player.getItems().deleteItem(EVENT_KEY, 1);
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
			player.getItems().addItemUnderAnyCircumstance(13307, 500 + Misc.random(1000));
			player.getItems().addItemUnderAnyCircumstance(10476, 10 + Misc.random(10));
			player.EventBossDamage = 0;
			player.sendMessage("You reach into the chest and pull out a " + ItemAssistant.getItemName(reward.getId()));
			for (Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
				for (Item gift_item : gift.getValue())
					if (gift_item == reward)
					if (gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
					GlobalMessages.send(Misc.capitalize(player.playerName) + " received a rare item: "
					+ (reward.getAmount() > 1 ? (reward.getAmount() + "x ")
					: ItemAssistant.getItemName(reward.getId()) + " from a Boss Event."), GlobalMessages.MessageType.LOOT);
			} else {
			player.sendMessage("You need an Event Key to open this!");
		}
	}
	
	/**
	 * Spawns the event chest at the locations of the event bosses
	 */
	public static void SpawnChest() {
		  World.getWorld().getGlobalObjects().add(new GlobalObject(EnragedGraardorDrops.EVENT_CHEST, 3177, 3007, 0, 0, 10));
		  World.getWorld().getGlobalObjects().add(new GlobalObject(EnragedGraardorDrops.EVENT_CHEST, 3540, 3312, 0, 0, 10));
		  World.getWorld().getGlobalObjects().add(new GlobalObject(EnragedGraardorDrops.EVENT_CHEST, 2939, 3426, 0, 0, 10));
		  World.getWorld().getGlobalObjects().add(new GlobalObject(EnragedGraardorDrops.EVENT_CHEST, 2796, 3454, 0, 0, 10));
		  World.getWorld().getGlobalObjects().add(new GlobalObject(EnragedGraardorDrops.EVENT_CHEST, 2331, 3833, 0, 0, 10));
	}

}
