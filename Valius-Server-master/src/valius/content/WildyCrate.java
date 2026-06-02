package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemRarity;
import valius.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class WildyCrate implements CycleEvent {

	/**
	 * The item id of the Pursuit Crate required to trigger the event
	 */
	public static final int MYSTERY_BOX = 21307; //Crate

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
					new Item(565, 1000),
					new Item(12746),
					new Item(560, 1000), 
					new Item(11937, 150), 
					new Item(2996, Misc.random(15) + 5), 
					new Item(9242, 200), 
					new Item(9244, 200),
					new Item(19685), 
					new Item(4153), 
					new Item(4087), 
					new Item(1128, 5), 
					new Item(1080, 5),
					new Item(4588, 5),
					new Item(11730),
					new Item(13307, Misc.random(500) + 500), 
					new Item(12696, 15), 
					new Item(990, 3))
					
			);
			
		items.put(ItemRarity.UNCOMMON,
				Arrays.asList(
						new Item(11818),
						new Item(12748),
						new Item(11820), 
						new Item(11822), 
						new Item(20113), 
						new Item(20116), 
						new Item(20119), 
						new Item(20122), 
						new Item(20125), 
						new Item(19841), 
						new Item(20773), 
						new Item(20775),
						new Item(19677),
						new Item(20775),
						new Item(6816, 20),
						new Item(13307, Misc.random(1000) + 1000),
						new Item(20777))
						
		);
			
			items.put(ItemRarity.RARE,
					Arrays.asList(
							new Item(11840), 
							new Item(12749),
							new Item(4675), 
							new Item(20595), 
							new Item(20517), 
							new Item(20520), 
							new Item(20838),
							new Item(20840),
							new Item(20842),
							new Item(20844), 		
							new Item(20846),
							new Item(20272),
							new Item(20775),
							new Item(19677, 3),
							new Item(11232, Misc.random(200) + 50),
							new Item(13307, Misc.random(1000) + 2000),
							new Item(995, 1000000)));
							
							
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new Wildy Crate to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public WildyCrate(Player player) {
		this.player = player;
	}

	/**
	 * Opens a PvM Casket if possible, and ultimately triggers and event, if possible.
	 *
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a Pursuit Crate.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a Pursuit Crate to do this.");
			return;
		}
		player.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1000, 5000));
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@SuppressWarnings("unused")
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int coins = 50000 + Misc.random(50000);
		int coinsDouble = 100000 + Misc.random(100000);
		int random = Misc.random(100);
		List<Item> itemList = random < 55 ? items.get(ItemRarity.COMMON) : random >= 55 && random <= 90 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
		Item itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(200) == 1) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName)+" hit the jackpot and got an Obsidian Piece!", GlobalMessages.MessageType.LOOT);
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(21298, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(21301, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(21304, 1);
				break;
			}
		}

		if (Misc.random(10) == 0) {
			player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			//player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("@red@You receive double coins!");
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
		}
		container.stop();
	}

}