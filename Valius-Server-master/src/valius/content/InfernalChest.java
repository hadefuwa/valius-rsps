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

public class InfernalChest {

	private static final int MYSTERY_BOX = 33269;
	private static final int KEY = 33153;
	private static final int KEY_PIECE1 = 33150;
	private static final int KEY_PIECE2 = 33151;
	private static final int KEY_PIECE3 = 33152;
	private static final int ANIMATION = 881;

	private static final Map<ItemRarity, List<Item>> items = new HashMap<>();

	static {
        items.put(ItemRarity.COMMON, Arrays.asList(
                new Item(995, Misc.random(5000000, 15000000)), //coins
				new Item(13307, Misc.random(5000, 10000)),
				new Item(33139, 1),//god bows
				new Item(33912, Misc.random(1, 6)),
				new Item(33140, 1),
				new Item(33141, 1),
    			new Item(33158, 1),//dagon hai
				new Item(33159, 1),
                new Item(33160, 1)));
		
		items.put(ItemRarity.RARE, Arrays.asList( 
				new Item(995, Misc.random(20000000, 30000000)),//coins
				new Item(13307, Misc.random(8000, 13000)),
				new Item(33154, 1),//infernal mbox
				new Item(33765, 1),//infernal weapon enhancement kit
				new Item(33315, 1),//Colossal
				new Item(33317, 1),
				new Item(33313, 1),
				new Item(33314, 1),
				new Item(33316, 1),
				new Item(33153, 2)));//2x full keys
	}

	private static Item randomChestRewards(int chance) {
		int random = Misc.random(100);
		List<Item> itemList = random <= 90 ? items.get(ItemRarity.COMMON) : items.get(ItemRarity.RARE);
		return Misc.getRandomItem(itemList);
	}

	public static void makeKey(Player c) {
		if (c.getItems().playerHasItem(KEY_PIECE1, 1) && c.getItems().playerHasItem(KEY_PIECE2, 1) && c.getItems().playerHasItem(KEY_PIECE3, 1)) {
			c.getItems().deleteItem(KEY_PIECE1, 1);
			c.getItems().deleteItem(KEY_PIECE2, 1);
			c.getItems().deleteItem(KEY_PIECE3, 1);
			c.getItems().addItem(KEY, 1);
		}
	}

	public static void searchChest(Player c) {
		int random = Misc.random(250);
		if (c.getItems().playerHasItem(KEY)) {
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(ANIMATION);
			c.getItems().addItemUnderAnyCircumstance(MYSTERY_BOX, 1);
			c.getItems().addItemUnderAnyCircumstance(995, Misc.random(2_500_000, 5_000_000));
			c.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1000, 2500));
			Item reward = Boundary.isIn(c, Boundary.DONATOR_ZONE) && c.getRights().isOrInherits(Right.DIAMOND) ? randomChestRewards(2) : randomChestRewards(9);
			if (!c.getItems().addItem(reward.getId(), reward.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(c, reward.getId(), c.getX(), c.getY(), c.getHeight(), reward.getAmount());
			}
			if (random == 1) {
				c.getItems().addItemUnderAnyCircumstance(22001, 1);
				GlobalMessages.send(c.getName() + " has received an Infernal Partyhat from the Infernal chest!", GlobalMessages.MessageType.LOOT);
			}
			if (random == 3) {
				c.getItems().addItemUnderAnyCircumstance(33248, 1);
				GlobalMessages.send(c.getName() + " just got a Pet Jaltok Jad from the Infernal Mysterybox!", GlobalMessages.MessageType.LOOT);
			}
			c.sendMessage("@blu@You stick your hand in the chest and pull an item out of the chest.");
			GlobalMessages.send( Misc.capitalizeJustFirst(c.playerName) + " has received a " + ItemAssistant.getItemName(reward.getId()) + " from the Infernal Chest!", GlobalMessages.MessageType.LOOT);
		} else {
			c.getDH().sendStatement("You will need the 3 pieces of the @blu@Infernal Key</col> to open this.",
					"All monsters drop Key pieces! Bosses have higher chances to drop them.");
		}
	}

}