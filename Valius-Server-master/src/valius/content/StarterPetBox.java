package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
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
public class StarterPetBox implements CycleEvent {

	/**
	 * The item id of the pet Casket required to trigger the event
	 */
	public static final int MYSTERY_BOX = 33498; //Casket

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
					new Item(33491, 1),
					new Item(33492, 1))
					
			);
			
		items.put(ItemRarity.UNCOMMON,
				Arrays.asList(
						new Item(33493, 1),
						new Item(33494, 1))
						
		);
			
			items.put(ItemRarity.RARE,
					Arrays.asList(
							new Item(33495, 1)));
							
							
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new pet Casket to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public StarterPetBox(Player player) {
		this.player = player;
	}

	/**
	 * Opens a pet Casket if possible, and ultimately triggers and event, if possible.
	 *
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 1) {
			player.sendMessage("You need at least one free slots to open a Pet Casket.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need Pet Casket to do this.");
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
		int random = Misc.random(100);
		List<Item> itemList = random < 55 ? items.get(ItemRarity.COMMON) : random >= 65 && random <= 90 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("@blu@You will receive a 2% Drop Rate bonus while this pet is following you!");
			player.sendMessage("You receive a <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
		container.stop();
	}


}