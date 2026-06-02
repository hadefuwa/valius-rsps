package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.cluescroll.ClueScrollRiddle;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.items.Item;
import valius.model.items.ItemRarity;
import valius.util.Misc;
import valius.world.World;

public class CrystalChest {

	private static final int KEY = 989;
	private static final int DRAGONSTONE = 1631;
	private static final int KEY_HALVE1 = 985;
	private static final int KEY_HALVE2 = 987;
	private static final int ANIMATION = 881;

	private static final Map<ItemRarity, List<Item>> items = new HashMap<>();

	static {
        items.put(ItemRarity.COMMON, Arrays.asList(
                new Item(2350, Misc.random(1, 50)), 
                new Item(2352, Misc.random(1, 50)),
                new Item(2354, Misc.random(1, 50)),
                new Item(995, Misc.random(75000, 500000)), 
                new Item(2358, Misc.random(1, 35)),
                new Item(2360, Misc.random(1, 25)),
                new Item(2362, Misc.random(1, 15)), 
                new Item(2364, Misc.random(1, 7)), 
                new Item(441, Misc.random(1, 70)), 
                new Item(454, Misc.random(1, 100)),
                new Item(208, Misc.random(1, 15)), 
                new Item(437, Misc.random(1, 100)), 
                new Item(439, Misc.random(1, 100)), 
                new Item(1079, 1), 
                new Item(1127, 1), 
                new Item(1201, 1), 
                new Item(1163, 1), 
                new Item(1215, 1), 
                new Item(1305, 1), 
                new Item(1377, 1), 
                new Item(1149, 1), 
                new Item(384, 15), 
                new Item(1319, 1)));
		
		items.put(ItemRarity.UNCOMMON, Arrays.asList(
				new Item(386, 20), 
				new Item(990, 3), 
				new Item(995, Misc.random(100000, 1000000)),
				new Item(13307, Misc.random(250, 2500)),
				new Item(1305, 1), 
				new Item(1377, 1),
				new Item(2368, 1),
				new Item(Misc.randomElementOf(ClueScrollRiddle.MEDIUM_CLUES), 1), 
				new Item(3027, 10), 
				new Item(3145, 15), 
				new Item(4587, 1), 
				new Item(6688, 10),
				new Item(10386, 1),
				new Item(10384, 1),
				new Item(10388, 1),
				new Item(10390, 1),
				new Item(10368, 1),
				new Item(10370, 1),
				new Item(10372, 1),
				new Item(10374, 1),
				new Item(10376, 1),
				new Item(10378, 1),
				new Item(10380, 1),
				new Item(10382, 1),
                new Item(33912, Misc.random(1, 2)),
				new Item(11840, 1)));
	}

	private static Item randomChestRewards(int chance) {
		int random = Misc.random(chance);
		List<Item> itemList = random < chance ? items.get(ItemRarity.COMMON) : items.get(ItemRarity.UNCOMMON);
		return Misc.getRandomItem(itemList);
	}

	public static void makeKey(Player c) {
		if (c.getItems().playerHasItem(KEY_HALVE1, 1) && c.getItems().playerHasItem(KEY_HALVE2, 1)) {
			c.getItems().deleteItem(KEY_HALVE1, 1);
			c.getItems().deleteItem(KEY_HALVE2, 1);
			c.getItems().addItem(KEY, 1);
		}
	}

	public static void searchChest(Player c) {
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItem(DRAGONSTONE, 1);
			Item reward = (Boundary.isIn(c, Boundary.DONATOR_ZONE) || Boundary.isIn(c, Boundary.EDZ)) && c.getRights().isOrInherits(Right.DIAMOND) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (Misc.random(30000) == 60) {
				c.getItems().addItemUnderAnyCircumstance(1038, 1);
				GlobalMessages.send(c.getName() + " has received a Red Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(30000) == 10) {
				c.getItems().addItemUnderAnyCircumstance(1040, 1);
				GlobalMessages.send(c.getName() + " has received a Yellow Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(30000) == 20) {
				c.getItems().addItemUnderAnyCircumstance(1042, 1);
				GlobalMessages.send(c.getName() + " has received a Blue Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(30000) == 30) {
				c.getItems().addItemUnderAnyCircumstance(1044, 1);
				GlobalMessages.send(c.getName() + " has received a Green Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(30000) == 40) {
				c.getItems().addItemUnderAnyCircumstance(1046, 1);
				GlobalMessages.send(c.getName() + " has received a Purple Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(30000) == 50) {
				c.getItems().addItemUnderAnyCircumstance(1048, 1);
				GlobalMessages.send(c.getName() + " has received a White Partyhat from the Crystal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(50) == 5) {
				c.getItems().addItemUnderAnyCircumstance(6199, 1);
				c.sendMessage("You receive a Mystery box from the crystal chest.");
			}
			Achievements.increase(c, AchievementType.LOOT_CRYSTAL_CHEST, 1);
			c.sendMessage("@blu@You stick your hand in the chest and pull an item out of the chest.");
		} else {
			c.sendMessage("@blu@The chest is locked, it won't budge!");
		}
	}
}