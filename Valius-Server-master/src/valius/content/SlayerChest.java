package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemRarity;
import valius.util.Misc;
import valius.world.World;

/**
 * 
 * @author Divine
 * May 30, 2019 12:50:09 a.m.
 */

/*
 * Handling for the Slayer Chest
 */

public class SlayerChest {

	/*
	 * Item Id's & Animations
	 */
	private static final int MYSTERY_BOX = 6199;
	private static final int KEY1 = 33521;
	private static final int KEY2 = 33522;
	private static final int KEY3 = 33523;
	private static final int KEY4 = 33524;
	private static final int ANIMATION = 881;

	private static final Map<ItemRarity, List<Item>> items = new HashMap<>();

	static {
		
		/*
		 * Tier 1
		 */
        items.put(ItemRarity.COMMON, Arrays.asList(
                new Item(995, Misc.random(50000, 150000)), //coins
				new Item(13307, Misc.random(2000, 3000)),
				new Item(33912, Misc.random(1, 3)),
				new Item(1618, Misc.random(125, 175)),//uncut diamond
				new Item(1620, Misc.random(125, 175)),//uncut ruby
				new Item(454, Misc.random(300, 500)),//coal
    			new Item(445, Misc.random(100, 200)),//gold
				new Item(11237, Misc.random(50, 200)),//dragon arrow tips
                new Item(441, Misc.random(350, 500)),//iron ore
        		new Item(1164, Misc.random(2, 4)),//rune fullhelm
        		new Item(1128, Misc.random(1, 2)),//rune platebody
        		new Item(1080, Misc.random(1, 2)),//rune platelegs
        		new Item(360, Misc.random(100, 350)),//tuna
        		new Item(378, Misc.random(100, 300)),//lobster
        		new Item(372, Misc.random(100, 300)),//swordfish
        		new Item(7945, Misc.random(100, 300)),//monkfish
        		new Item(384, Misc.random(100, 300)),//shark
        		new Item(396, Misc.random(80, 200)),// sea turtle
        		new Item(390, Misc.random(80, 160)),//manta
        		new Item(452, Misc.random(25, 50)),// runite ore
        		new Item(2354, Misc.random(300, 500)),//steel bar
        		new Item(1514, Misc.random(125, 200)),// magic logs
        		new Item(11232, Misc.random(100, 150)),//dragon dart tip
        		new Item(5295, Misc.random(15, 25)),//rannar seed
        		new Item(5300, Misc.random(15, 25)),//snap seed
        		new Item(5304, Misc.random(15, 25)),//torstol seed
        		new Item(7937, Misc.random(6000, 9000)),//pure ess
        		new Item(12934, Misc.random(200, 600)),//zulrah scale
        		new Item(22732, 1),//d hasta
				new Item(33153, 2)));//2x full keys
        
        /*
         * Tier 2
         */
        items.put(ItemRarity.UNCOMMON, Arrays.asList(
                new Item(995, Misc.random(70000, 180000)), //coins
				new Item(13307, Misc.random(2000, 4000)),
				new Item(1618, Misc.random(125, 175)),//uncut diamond
				new Item(1620, Misc.random(125, 175)),//uncut ruby
				new Item(454, Misc.random(400, 600)),//coal
    			new Item(445, Misc.random(200, 250)),//gold
				new Item(33912, Misc.random(1, 4)),
				new Item(11237, Misc.random(100, 300)),//dragon arrow tips
                new Item(441, Misc.random(350, 500)),//iron ore
        		new Item(1164, Misc.random(3, 5)),//rune fullhelm
        		new Item(1128, Misc.random(2, 3)),//rune platebody
        		new Item(1080, Misc.random(2, 3)),//rune platelegs
        		new Item(360, Misc.random(100, 350)),//tuna
        		new Item(378, Misc.random(100, 300)),//lobster
        		new Item(372, Misc.random(100, 300)),//swordfish
        		new Item(7945, Misc.random(100, 300)),//monkfish
        		new Item(384, Misc.random(200, 400)),//shark
        		new Item(396, Misc.random(100, 250)),// sea turtle
        		new Item(390, Misc.random(80, 160)),//manta
        		new Item(452, Misc.random(30, 55)),// runite ore
        		new Item(2354, Misc.random(300, 500)),//steel bar
        		new Item(1514, Misc.random(150, 300)),// magic logs
        		new Item(11232, Misc.random(110, 170)),//dragon dart tip
        		new Item(5295, Misc.random(15, 25)),//rannar seed
        		new Item(5300, Misc.random(15, 25)),//snap seed
        		new Item(5304, Misc.random(15, 25)),//torstol seed
        		new Item(7937, Misc.random(6000, 9000)),//pure ess
        		new Item(12934, Misc.random(200, 600)),//zulrah scale
        		new Item(22732, 1),//d hasta
				new Item(33153, 2)));//2x full keys
        
        /*
         * Tier 3
         */
        items.put(ItemRarity.RARE, Arrays.asList(
                new Item(995, Misc.random(1000000, 2800000)), //coins
				new Item(13307, Misc.random(2000, 4000)),
				new Item(1618, Misc.random(135, 185)),//uncut diamond
				new Item(1620, Misc.random(135, 185)),//uncut ruby
				new Item(454, Misc.random(400, 600)),//coal
    			new Item(445, Misc.random(200, 250)),//gold
				new Item(33912, Misc.random(1, 5)),
				new Item(11237, Misc.random(100, 300)),//dragon arrow tips
                new Item(441, Misc.random(450, 500)),//iron ore
        		new Item(1164, Misc.random(3, 6)),//rune fullhelm
        		new Item(1128, Misc.random(2, 4)),//rune platebody
        		new Item(1080, Misc.random(2, 4)),//rune platelegs
        		new Item(360, Misc.random(100, 350)),//tuna
        		new Item(378, Misc.random(100, 300)),//lobster
        		new Item(372, Misc.random(100, 300)),//swordfish
        		new Item(7945, Misc.random(100, 300)),//monkfish
        		new Item(384, Misc.random(300, 500)),//shark
        		new Item(396, Misc.random(100, 250)),// sea turtle
        		new Item(390, Misc.random(80, 160)),//manta
        		new Item(452, Misc.random(40, 60)),// runite ore
        		new Item(2354, Misc.random(300, 500)),//steel bar
        		new Item(1514, Misc.random(200, 400)),// magic logs
        		new Item(11232, Misc.random(150, 200)),//dragon dart tip
        		new Item(5295, Misc.random(15, 25)),//rannar seed
        		new Item(5300, Misc.random(15, 25)),//snap seed
        		new Item(5304, Misc.random(15, 25)),//torstol seed
        		new Item(7937, Misc.random(6000, 9000)),//pure ess
        		new Item(12934, Misc.random(200, 600)),//zulrah scale
        		new Item(22732, 1),//d hasta
				new Item(33153, 2)));//2x full keys
		
        /*
         * Tier 4
         */
		items.put(ItemRarity.VERY_RARE, Arrays.asList( 
                new Item(995, Misc.random(1000000, 2800000)), //coins
				new Item(13307, Misc.random(2000, 4000)),
				new Item(1618, Misc.random(135, 185)),//uncut diamond
				new Item(1620, Misc.random(135, 185)),//uncut ruby
				new Item(454, Misc.random(400, 600)),//coal
    			new Item(445, Misc.random(200, 250)),//gold
				new Item(11237, Misc.random(100, 300)),//dragon arrow tips
                new Item(441, Misc.random(450, 500)),//iron ore
        		new Item(1164, Misc.random(4, 7)),//rune fullhelm
        		new Item(1128, Misc.random(3, 5)),//rune platebody
        		new Item(1080, Misc.random(3, 5)),//rune platelegs
        		new Item(360, Misc.random(100, 350)),//tuna
				new Item(33912, Misc.random(1, 6)),
        		new Item(378, Misc.random(100, 300)),//lobster
        		new Item(372, Misc.random(100, 300)),//swordfish
        		new Item(7945, Misc.random(100, 300)),//monkfish
        		new Item(384, Misc.random(350, 600)),//shark
        		new Item(396, Misc.random(100, 250)),// sea turtle
        		new Item(390, Misc.random(80, 160)),//manta
        		new Item(452, Misc.random(50, 70)),// runite ore
        		new Item(2354, Misc.random(300, 500)),//steel bar
        		new Item(1514, Misc.random(250, 450)),// magic logs
        		new Item(11232, Misc.random(170, 250)),//dragon dart tip
        		new Item(5295, Misc.random(15, 25)),//rannar seed
        		new Item(5300, Misc.random(15, 25)),//snap seed
        		new Item(5304, Misc.random(15, 25)),//torstol seed
        		new Item(7937, Misc.random(6000, 9000)),//pure ess
        		new Item(12934, Misc.random(200, 600)),//zulrah scale
        		new Item(22732, 1),//d hasta
				new Item(33153, 2)));//2x full keys
		
        /*
         * Tier 5
         */
		items.put(ItemRarity.ULTRA_RARE, Arrays.asList( 
				new Item(995, Misc.random(20000000, 30000000)),//coins
				new Item(13307, Misc.random(8000, 13000)),
				new Item(12922, 1),//tanz fang
				new Item(12927, 1),//serp vis
				new Item(12932, 1),//magic fang
				new Item(13227, 1),//boot crystals
				new Item(13229, 1),
				new Item(33912, Misc.random(5, 15)),
				new Item(13231, 1),
				new Item(19547, 1),//zenyte
				new Item(19553, 1),
				new Item(19544, 1),
				new Item(19550, 1),
				new Item(4151, 1),//whip
				new Item(12004, 1),//tent
				new Item(33153, 4)));//4x full keys
	}

	/*
	 * Handles getting a random item from each list depending on the Tier.
	 */
	private static Item randomTier1ChestRewards(int chance) {
		int random = Misc.random(1, 100);
		List<Item> itemList = random <= 99 ? items.get(ItemRarity.COMMON) : items.get(ItemRarity.ULTRA_RARE);
		return Misc.getRandomItem(itemList);
	}
	
	private static Item randomTier2ChestRewards(int chance) {
		int random = Misc.random(1, 100);
		List<Item> itemList = random <= 98 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.ULTRA_RARE);
		return Misc.getRandomItem(itemList);
	}
	
	private static Item randomTier3ChestRewards(int chance) {
		int random = Misc.random(1, 100);
		List<Item> itemList = random <= 97 ? items.get(ItemRarity.RARE) : items.get(ItemRarity.ULTRA_RARE);
		return Misc.getRandomItem(itemList);
	}

	private static Item randomTier4ChestRewards(int chance) {
		int random = Misc.random(1, 100);
		List<Item> itemList = random <= 96 ? items.get(ItemRarity.VERY_RARE) : items.get(ItemRarity.ULTRA_RARE);
		return Misc.getRandomItem(itemList);
	}
	
	public static void searchChest(Player c) {
		//Tier 1
		if (c.getItems().playerHasItem(KEY1)) {
			c.getItems().deleteItem(KEY1, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(MYSTERY_BOX, 1);
			c.getItems().addItemUnderAnyCircumstance(995, Misc.random(500_000, 1_000_000));
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(500, 1000));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomTier1ChestRewards(2) : randomTier1ChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (Misc.random(1, 20) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33269, 1);
				c.sendMessage("You got lucky and got a Valius mystery box!");
			}
			if (Misc.random(1, 20) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33374, 1);
				GlobalMessages.send("" + c.playerName + " has received Slayer Boots from the Slayer chest!", GlobalMessages.MessageType.LOOT);
			}
			c.sendMessage( " You receive a " + ItemAssistant.getItemName(reward.getId()) + " from the Slayer Chest!");
		}
		//Tier 2
		else if (c.getItems().playerHasItem(KEY2)) {
			c.getItems().deleteItem(KEY2, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(MYSTERY_BOX, 1);
			c.getItems().addItemUnderAnyCircumstance(995, Misc.random(1_000_000, 2_000_000));
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1000, 1500));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomTier2ChestRewards(2) : randomTier2ChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (Misc.random(1, 19) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33269, 1);
				c.sendMessage("You got lucky and got a Valius mystery box!");
			}
			if (Misc.random(1, 20) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33373, 1);
				GlobalMessages.send("" + c.playerName + " has received Slayer platelegs from the Slayer chest!", GlobalMessages.MessageType.LOOT);
			}
			c.sendMessage( " You receive a " + ItemAssistant.getItemName(reward.getId()) + " from the Slayer Chest!");
		}
		//Tier 3
		else if (c.getItems().playerHasItem(KEY3)) {
			c.getItems().deleteItem(KEY3, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(MYSTERY_BOX, 1);
			c.getItems().addItemUnderAnyCircumstance(995, Misc.random(2_000_000, 3_000_000));
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1500, 2000));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomTier3ChestRewards(2) : randomTier3ChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (Misc.random(1, 18) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33269, 1);
				c.sendMessage("You got lucky and got a Valius mystery box!");
			}
			if (Misc.random(1, 20) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33372, 1);
				GlobalMessages.send("" + c.playerName + " has received a Slayer platebody from the Slayer chest!", GlobalMessages.MessageType.LOOT);
			}
			c.sendMessage( " You receive a " + ItemAssistant.getItemName(reward.getId()) + " from the Slayer Chest!");
		} 
		//Tier 4
		else if (c.getItems().playerHasItem(KEY4)) {
			c.getItems().deleteItem(KEY4, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(MYSTERY_BOX, 1);
			c.getItems().addItemUnderAnyCircumstance(995, Misc.random(3_000_000, 5_000_000));
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(2000, 3000));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomTier4ChestRewards(2) : randomTier4ChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (Misc.random(1, 15) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33269, 1);
				c.sendMessage("You got lucky and got a Valius mystery box!");
			}
			if (Misc.random(1, 20) == 5) {
				c.getItems().addItemUnderAnyCircumstance(33371, 1);
				GlobalMessages.send("" + c.playerName + " has received a Spiked Slayer Helmet from the Slayer chest!", GlobalMessages.MessageType.LOOT);
			}
			c.sendMessage( " You receive a " + ItemAssistant.getItemName(reward.getId()) + " from the Slayer Chest!");
		}
		
		else {
			c.getDH().sendStatement("You will need a Slayer key Tier 1-4 to open this.",
					"All Slayer monsters drop Keys. Higher level monsters = higher tier keys!");
		}
	} 
}