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
 * @author Divine
 * Jun. 22, 2019 1:02:17 a.m.
 */


public class StarBox {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 33422;



	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new mystery box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public StarBox(Player player) {
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
		VERY_RARE("<col=7806A6>");
		
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
		Map<ItemRarity, List<Item>> items = new HashMap<>();
		items.put(ItemRarity.COMMON, 
				Arrays.asList(
						new Item(33458, 1),//xp scroll 10 min 150%
						new Item(448, Misc.random(60, 100)),//mithril ore
						new Item(2360, Misc.random(30, 50)),//mithril bar
						new Item(450, Misc.random(50, 90)),//adamant ore
						new Item(2362, Misc.random(25, 45)),//adamant bar
					new Item(452, Misc.random(25, 50)))//rune ore
				);
		
		items.put(ItemRarity.UNCOMMON,
				Arrays.asList(
						new Item(2528, 1),//XP lamp
						new Item(33462, 1),//xp scroll 10 min 200%
						new Item(452, Misc.random(50, 100)),//rune ore
						new Item(2364, Misc.random(20, 50)))//rune bar
				);
		
		items.put(ItemRarity.RARE,
				Arrays.asList(
						new Item(33542),//stargaze rings
						new Item(33543),
						new Item(33544))
				);
		
		items.put(ItemRarity.VERY_RARE,
				Arrays.asList(
						new Item(33545),//crystal pickaxe
						new Item(33547))//stargaze perk box
				);
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		canMysteryBox = false;
		
		random = Misc.random(100);
		List<Item> itemList = random < 50 ? items.get(ItemRarity.COMMON) : random >= 50 && random < 97 ? items.get(ItemRarity.UNCOMMON) : random >= 97 && random <= 99 ? items.get(ItemRarity.RARE) : items.get(ItemRarity.VERY_RARE);
		rewardRarity = random < 50 ? ItemRarity.COMMON : random >= 50 && random < 97 ? ItemRarity.UNCOMMON : random >= 97 && random <= 99 ? ItemRarity.RARE : ItemRarity.VERY_RARE;
		
		Item item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				ItemRarity notPrizeRarity = ItemRarity.values()[new Random().nextInt(ItemRarity.values().length)];
				Item NotPrize = Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID, i == 55 ? mysteryAmount : NOT_PRIZE_AMOUNT);
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
		player.getItems().addItemUnderAnyCircumstance(995, Misc.random(2000000, 5000000));
		
		// Reward text colour
		String tier = rewardRarity.getColor();
		
		// Reward message
		String name = ItemDefinition.forId(mysteryPrize).getName();
		player.sendMessage("You receive " + mysteryAmount + " x " + tier + name + "@bla@!");
		
		
		if (random > 95) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName)+" got a <col=CC0000>"+name + " from the Star Box", GlobalMessages.MessageType.LOOT);
			DiscordBot.sendMessage("valius-bot","[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " hit the jackpot and received a "+name+" from an Star Box!");
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
		player.getPA().sendString("Star Box", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}