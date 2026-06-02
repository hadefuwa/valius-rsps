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
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class UltraMysteryBox {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 13346;

	/**
	 * A map containing a List of {@link Item}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<Item>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
				new Item(4716),
				new Item(4724),
				new Item(4745),
				new Item(4753),
				new Item(4708),
				new Item(4732),
				new Item(4720),
				new Item(4728),
				new Item(4749),
				new Item(4757),
				new Item(4712),
				new Item(4736),
				new Item(4722),
				new Item(4730),
				new Item(4751),
				new Item(4759),
				new Item(4714),
				new Item(4738),
				new Item(4718),
				new Item(4726),
				new Item(4747),
				new Item(4755),
				new Item(4710),
				new Item(4734),
				new Item(11804),
				new Item(11808),
				new Item(11806),
				new Item(6585),
				new Item(13229),
				new Item(13227),
				new Item(13231),
				new Item(4225),
				new Item(11838),
				new Item(21298),
				new Item(21301),
				new Item(21298),
				new Item(21304),
				new Item(11771),
				new Item(11770),
				new Item(11773),
				new Item(11772),
				new Item(2577),
				new Item(19550, 1))
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new Item(12006),
					new Item(19481),
					new Item(12929),
					new Item(13198),
					new Item(13196),
					new Item(19547),
					new Item(19544),
					new Item(12900),
					new Item(11785),
					new Item(21012),
					new Item(11785),
					new Item(11283),
					new Item(13271),
					new Item(21633),
					new Item(11832),
					new Item(11834),
					new Item(11828),
					new Item(11830),
					new Item(11802),
					new Item(21003),
					new Item(19553))
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new Item(20997),
						new Item(1038),
						new Item(1040),
						new Item(1042),
						new Item(1044),
						new Item(1046),
						new Item(1048),
						new Item(11862),
						new Item(11863),
						new Item(12399),
						new Item(20784),
						new Item(12817),
						new Item(13343),
						new Item(13344),
						new Item(11847),
						new Item(11863),
						new Item(1053),
						new Item(1055),
						new Item(1057),
						new Item(1050),
						new Item(9959, 1)));
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public UltraMysteryBox(Player player) {
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
	private Rarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON("<col=005eff>"),
		COMMON("<col=336600>"),
		RARE("<col=B80000>");
		
		private String color;
		
		Rarity(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return color;
		}
		
	    public static Rarity forId(int id) {
	        for (Rarity tier : Rarity.values()) {
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
		List<Item> itemList = random < 40 ? items.get(Rarity.COMMON) : random >= 40 && random <= 95 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		rewardRarity = random < 40 ? Rarity.COMMON : random >= 40 && random <= 95 ? Rarity.UNCOMMON : Rarity.RARE;
		
		Item item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];
				Item NotPrize =Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID,1);
			}
		} else {
			for (int i=spinNum*50 + 16; i<spinNum*50 + 66; i++){
				Rarity notPrizeRarity = Rarity.values()[new Random().nextInt(Rarity.values().length)];	
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
		
		if (random > 95) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName)+" hit the jackpot and got a <col=CC0000>"+name, GlobalMessages.MessageType.LOOT);
			DiscordBot.sendMessage("valius-bot","[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " hit the jackpot and received a "+name+" from a Raid Mystery Box!");
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
		player.getPA().sendString("Raid Mystery Box", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}