package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import valius.content.cluescroll.ClueScrollRiddle;
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
public class DailyGearBox implements CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 20703;

	/**
	 * A map containing a List of {@link Item}'s that contain items relevant to their rarity.
	 */
	private static Map<ItemRarity, List<Item>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(ItemRarity.COMMON, //
			Arrays.asList(
				new Item(1163),
				new Item(1080),  
				new Item(1127),  
				new Item(2491),  
				new Item(2493),  
				new Item(2503),  
				new Item(4089),  
				new Item(4093),  
				new Item(4091),  
				new Item(4109),  
				new Item(4113),  
				new Item(4111),  
				new Item(6328),  
				new Item(7460),  
				new Item(4131),  
				new Item(995, 150000),   
				new Item(Misc.randomElementOf(ClueScrollRiddle.EASY_CLUES)))
		);
		
	items.put(ItemRarity.UNCOMMON,
			Arrays.asList(
					new Item(6920),
					new Item(7461),   
					new Item(10828),  
					new Item(20050),  
					new Item(6524),  
					new Item(6528),  
					new Item(6522, 200),  
					new Item(3204),  
					new Item(4587),  
					new Item(9185),  
					new Item(9144, 250),  
					new Item(892, 250),  
					new Item(5698),  
					new Item(4153),  
					new Item(995, 300000),    
					new Item(Misc.randomElementOf(ClueScrollRiddle.MEDIUM_CLUES)))
	);
		
		items.put(ItemRarity.RARE,
				Arrays.asList(
						new Item(11840),
						new Item(1409),
						new Item(2572),
						new Item(4675),
						new Item(11232, 150),
						new Item(4212),
						new Item(4151),
						new Item(4716),
						new Item(4720),
						new Item(4723),
						new Item(4718),
						new Item(4708),
						new Item(4710),
						new Item(4712),
						new Item(4714),
						new Item(4753),
						new Item(4756),
						new Item(4759),
						new Item(4757),
						new Item(4745),
						new Item(4749),
						new Item(4751),
						new Item(4747),
						new Item(4724),
						new Item(4726),
						new Item(4728),
						new Item(4730),
						new Item(4732),
						new Item(4734),
						new Item(4736),
						new Item(4738),
						new Item(995, 500000),
						new Item(Misc.randomElementOf(ClueScrollRiddle.HARD_CLUES))));
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
	public DailyGearBox(Player player) {
		this.player = player;
	}

	/**
	 * Opens a mystery box if possible, and ultimately triggers and event, if possible.
	 * 
	 * @param player the player triggering the evnet
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a mystery box.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a daily gear box to do this.");
			return;
		}
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int random = Misc.random(10);
		List<Item> itemList = random < 5 ? items.get(ItemRarity.COMMON) : random >= 5 && random <= 8 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
		Item itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(200) == 1) {
			GlobalMessages.send(player.playerName + "</col> hit the jackpot on a Daily Gear Box!", GlobalMessages.MessageType.LOOT);
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(12004, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(11286, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(11907, 1);
				break;
			}
		}

		if (Misc.random(25) == 0) {
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>.");
		} else {
			//player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
		}
		container.stop();
	}

}