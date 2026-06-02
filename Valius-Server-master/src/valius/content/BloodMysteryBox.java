package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import valius.discord.DiscordBot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemDefinition;
import valius.util.Misc;

/**
 * 
 * @author Divine | 2:17:12 a.m. | Aug. 31, 2019
 *
 */

public class BloodMysteryBox {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 33668;

	/**
	 * A map containing a List of {@link Item}'s that contain items relevant to their rarity.
	 */
	private static Map<ItemRarity, List<Item>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(ItemRarity.COMMON, 
			Arrays.asList(
				new Item(22125, 100 + Misc.random(100)),//superior dbones
				new Item(2528, 1),//lamp
				new Item(33452, 1),//xp scroll
				new Item(33455, 1),//xp scroll
				new Item(33153, 2),//1x infernal keys
				new Item(33466, Misc.random(2, 5)))
		);
		
	items.put(ItemRarity.UNCOMMON,
			Arrays.asList(
					new Item(33466, Misc.random(2, 10)),//dtd's
					new Item(12934, Misc.random(5000, 10000)),//zulrah scales
					new Item(6572, Misc.random(2, 4)))//uncut onyx
	);
		
		items.put(ItemRarity.RARE,
				Arrays.asList(
						new Item(33115),//dragonfire shield upgraded
						new Item(33526, 1),//blood whip
						new Item(33089),//chaotics
						new Item(33094),
						new Item(33095),
						new Item(33096),
						new Item(33031),
						new Item(33032),
						new Item(33168),//Justiciar shield
						new Item(33581)));//blood tcb kit
		
		items.put(ItemRarity.VERY_RARE,
				Arrays.asList(
						new Item(33414),//blood phat
						new Item(33279),//Necrolord staff
						new Item(33582),//bloodknight
						new Item(33583),
						new Item(33584)));
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new mystery box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public BloodMysteryBox(Player player) {
		this.player = player;
	}

	/**
	 * Can the player open the mystery box
	 */
	private boolean canMysteryBox = true;
	
	/**
	 * The prize received
	 */
	private int mysteryPrize;

	private int mysteryAmount;
	
	private int spinNum = 0;
	
	/**
	 * The chance to obtain the item
	 */
	private int random;
	
	private final int INTERFACE_ID = 47000;
	private final int ITEM_FRAME = 47101;
	
	/**
	 * The rarity of the reward
	 */
	private ItemRarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum ItemRarity {
		UNCOMMON("<col=005eff>"),
		COMMON("<col=336600>"),
		RARE("<col=B80000>"),
		VERY_RARE("<col=B80000>");
		
		private String color;
		
		ItemRarity(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return color;
		}
		
	    public static ItemRarity forId(int id) {
	        for (ItemRarity tier : ItemRarity.values()) {
	            if (tier.ordinal() == id)
	                return tier;
	        }
	        return null;
	    }
	}
	
	public void spin() {
		// Server side checks for spin
		if (!canMysteryBox) {
			player.sendMessage("Please finish your current spin.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You require a mystery box to do this.");
			return;
		}

		// Delete box
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		// Initiate spin
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		player.sendMessage(":spin");
		process();
	}
	
	public void process() {
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		canMysteryBox = false;
		
		random = Misc.random(100);
		List<Item> itemList = random < 60 ? items.get(ItemRarity.COMMON) :
			random >= 60 && random < 90 ? items.get(ItemRarity.UNCOMMON):
				random >= 90 && random <= 98 ? items.get(ItemRarity.RARE) :
					items.get(ItemRarity.VERY_RARE);
				
		rewardRarity = random < 60 ? ItemRarity.COMMON :
			random >= 60 && random < 90 ? ItemRarity.UNCOMMON :
			 random >= 90 && random <= 98 ? ItemRarity.RARE :
				ItemRarity.VERY_RARE;
		
		Item item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				ItemRarity notPrizeRarity = ItemRarity.values()[new Random().nextInt(ItemRarity.values().length)];
				Item NotPrize =Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID,1);
			}
		} else {
			for (int i=spinNum*50 + 16; i<spinNum*50 + 66; i++){
				ItemRarity notPrizeRarity = ItemRarity.values()[new Random().nextInt(ItemRarity.values().length)];	
				final int NOT_PRIZE_ID = Misc.getRandomItem(items.get(notPrizeRarity)).getId();
				sendItem(i, (spinNum+1)*50 + 5, mysteryPrize, NOT_PRIZE_ID, mysteryAmount);
			}
		}
		spinNum++;
	}
	
	public void reward() {
		if (mysteryPrize == -1) {
			return;
		}
		
		//player.boxCurrentlyUsing = -1;
		
		player.getItems().addItemUnderAnyCircumstance(mysteryPrize, mysteryAmount);
		
		// Reward text colour
		String tier = rewardRarity.getColor();
		
		// Reward message
		String name = ItemDefinition.forId(mysteryPrize).getName();
		if (name.substring(name.length() - 1).equals("s")) {
			player.sendMessage("Congratulations, you have won " + tier + name + "@bla@!");
		}
		else {
			player.sendMessage("Congratulations, you have won a " + tier + name + "@bla@!");
		}
		
		if (random > 85) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName)+" hit the jackpot and got a <col=CC0000>"+name, GlobalMessages.MessageType.LOOT);
			DiscordBot.sendMessage("valius-bot","[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " hit the jackpot and received a "+name+" from an Blood Mystery Box!");
		}
		
		// Can now spin again
		canMysteryBox = true;
	}
	
	public void sendItem(int i, int prizeSlot, int PRIZE_ID, int NOT_PRIZE_ID, int amount) {
		if (i == prizeSlot) {
			player.getPA().mysteryBoxItemOnInterface(PRIZE_ID, amount, ITEM_FRAME, i);
		}
		else {
			player.getPA().mysteryBoxItemOnInterface(NOT_PRIZE_ID, amount, ITEM_FRAME, i);
		}
	}
	
	public void openInterface() {
		player.boxCurrentlyUsing = MYSTERY_BOX;
		// Reset interface
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		// Open
		player.getPA().sendString("Blood Mystery Box", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}