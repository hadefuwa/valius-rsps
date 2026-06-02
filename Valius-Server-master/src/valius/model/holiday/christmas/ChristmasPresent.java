package valius.model.holiday.christmas;

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

public class ChristmasPresent implements CycleEvent {

	/**
	 * The item id of the christmas present required to trigger the event
	 */
	public static final int PRESENT = 6542;

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
				new Item(12890, 1), 
				new Item(12891, 1), 
				new Item(12895, 1), 
				new Item(12896, 1))
		);
		
	items.put(ItemRarity.UNCOMMON,
			Arrays.asList(
					new Item(1050), 
					new Item(12887), 
					new Item(12888), 
					new Item(12889), 
					new Item(12892), 
					new Item(12893), 
					new Item(12894))
			);
	
	items.put(ItemRarity.RARE,
			Arrays.asList(
					new Item(11863, 1))
			);
}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new christmas present to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public ChristmasPresent(Player player) {
		this.player = player;
	}

	/**
	 * Random action of obtaining a santa jr pet
	 */
	private void randomPet() {
		if (Misc.random(4) == 1 && player.getItems().getItemCount(9958, true) == 0 && player.summonId != 9958) {
			GlobalMessages.send(player.playerName+" hit the jackpot and got a Santa Jr. pet from a Christmas Present!", GlobalMessages.MessageType.LOOT);
			player.getItems().addItemUnderAnyCircumstance(9958, 1);
		}
	}	
	
	/**
	 * Opens a christmas present if possible, and ultimately triggers and event, if possible.
	 * 
	 * @param player the player triggering the event
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 600 * 2) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a christmas present.");
			return;
		}
		if (!player.getItems().playerHasItem(PRESENT)) {
			player.sendMessage("You need a christmas present to do this.");
			return;
		}
		randomPet();
		player.getItems().deleteItem(PRESENT, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the christmas present
	 */
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int random = Misc.random(100);
		List<Item> itemList = random < 51 ? items.get(ItemRarity.COMMON) : random > 50 && random < 91 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + "x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			GlobalMessages.send(player.playerName+" has received " + item.getAmount() + "x " + ItemAssistant.getItemName(item.getId()) + " from a Christmas present!", GlobalMessages.MessageType.LOOT);
			container.stop();
	}

}