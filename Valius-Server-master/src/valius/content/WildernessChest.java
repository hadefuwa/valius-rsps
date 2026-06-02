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

public class WildernessChest {

	private static final int[] KEY = {33774, 33775, 33776, 33777};
	private static final int ANIMATION = 881;

	private static final Map<ItemRarity, List<Item>> items = new HashMap<>();

	static {
        items.put(ItemRarity.COMMON, Arrays.asList(
                new Item(995, Misc.random(1000000, 5000000)), //coins
				new Item(13307, Misc.random(1000, 5000)),//bm
				new Item(990, Misc.random(10, 20)),//crystal keys
				new Item(450, Misc.random(100, 200)),//addy ore
				new Item(33912, Misc.random(5, 10)),//Serinic scales
				new Item(384, Misc.random(150, 300)),//raw shark
				new Item(1632, Misc.random(20, 50)),//uncut dragonstone
				new Item(33773, Misc.random(1, 2))//rev impling
				
        		));
		
        items.put(ItemRarity.UNCOMMON, Arrays.asList( 
				new Item(995, Misc.random(1000000, 5000000)),//coins
				new Item(13307, Misc.random(5000, 8000)),//bm
				new Item(452, Misc.random(50, 150)),//runite ore
				new Item(6572, Misc.random(1, 3)),//uncut onyx
				new Item(22296, 1),//staff of light
				new Item(33668, 1),//bloodmoney mbox
				new Item(4152, Misc.random(1, 2))//Abyssal Whip
				
				));
        
		items.put(ItemRarity.RARE, Arrays.asList( 
				new Item(995, Misc.random(5000000, 10000000)),//coins
				new Item(13307, Misc.random(5000, 8000)),//bm
				new Item(13302, 1),// event boss keys
				new Item(13303, 1),
				new Item(33592, 1),//nightmare key
				new Item(33797, 1),//dark lord battleaxe
				new Item(33774, 2),//2x full keys
				new Item(33775, 2),//2x full keys
				new Item(33776, 2),//2x full keys
				new Item(33777, 2),//2x full keys
				new Item(33805, 1)//dark lord shield
				
				));
		
		items.put(ItemRarity.VERY_RARE, Arrays.asList( 
				new Item(995, Misc.random(10000000, 20000000)),//coins
				new Item(13307, Misc.random(8000, 10000)),//bm
				new Item(33767, 1),//gem of fortune
				new Item(33801, 1),//pets
				new Item(33802, 1),
				new Item(20784, 1),
				new Item(33803, 1)
				
				));
	}

	private static Item randomChestRewards(int chance) {
		int random = Misc.random(1000);
		List<Item> itemList = random <= 600 ? items.get(ItemRarity.COMMON) ://60%
			random >= 600 && random < 940 ? items.get(ItemRarity.UNCOMMON) ://35%
				random >= 940 && random < 980 ? items.get(ItemRarity.RARE) ://4%
					items.get(ItemRarity.VERY_RARE);//1%
		return Misc.getRandomItem(itemList);
	}

	public static void searchChest(Player c) {
		for (int key : KEY) {
		if (c.getItems().playerHasItem(key)) {
			c.getItems().deleteItem(key, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(500, 2500));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			c.sendMessage("@blu@You stick your hand in the chest and pull an item out of the chest.");
			GlobalMessages.send( Misc.capitalizeJustFirst(c.playerName) + " has received a " + ItemAssistant.getItemName(reward.getId()) + " from the Wilderness Chest!", GlobalMessages.MessageType.LOOT);
		} else {
			c.getDH().sendStatement("You will need a Key dropped by",
					"Callisto, Venenatis, Vet'ion or the Shadow Beast.");
		}
	}
}
}