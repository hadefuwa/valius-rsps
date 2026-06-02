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
public class SkillCasket implements CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 7310; //Casket

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
					new Item(454, 150),  
					new Item(11818, 1))
					
			);
			
		items.put(ItemRarity.UNCOMMON,
				Arrays.asList(
						new Item(4566),
						new Item(11818, 1))
						
		);
			
			items.put(ItemRarity.RARE,
					Arrays.asList(
							new Item(4566), 
							new Item(11818, 1)));
							
							
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
	public SkillCasket(Player player) {
		this.player = player;
	}

	/**
	 * Opens a mystery box if possible, and ultimately triggers and event, if possible.
	 * 
	 * @param player the player triggering the evnet
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 600 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a mystery box.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a mystery box to do this.");
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
		int coins = 500_000 + Misc.random(1_500_000);
		int coinsDouble = 500_000 + Misc.random(1_500_000);
		int random = Misc.random(100);
		List<Item> itemList = random < 55 ? items.get(ItemRarity.COMMON) : random >= 55 && random <= 80 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
		Item itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(200) == 2 && player.getItems().getItemCount(19730, true) == 0 && player.summonId != 19730) {
			GlobalMessages.send(player.playerName + "hit the jackpot and got a Bloodhound pet!", GlobalMessages.MessageType.LOOT);
			player.getItems().addItemUnderAnyCircumstance(19730, 1);
		}

		if (Misc.random(10) == 0) {
			player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " just got very lucky and hit the double!", GlobalMessages.MessageType.LOOT);
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " has received " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
			+ " and " + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + " from a mystery box.", GlobalMessages.MessageType.LOOT);
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " has received " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + " from a mystery box.", GlobalMessages.MessageType.LOOT);
		}
		container.stop();
	}

}