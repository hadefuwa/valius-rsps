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
public class PvmCasket implements CycleEvent {

	/**
	 * The item id of the PvM Casket required to trigger the event
	 */
	public static final int MYSTERY_BOX = 405; //Casket

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
					new Item(1079),
					new Item(1077),
					new Item(1075), 
					new Item(1071), 
					new Item(2528),
					new Item(1073), 
					new Item(6106),
					new Item(6107),
					new Item(6108),
					new Item(6109),
					new Item(6110),
					new Item(6111),
					new Item(2550),
					new Item(1127), 
					new Item(995, 10000),
					new Item(1125), 
					new Item(1123), 
					new Item(1121), 
					new Item(987),
					new Item(1333),
					new Item(7947, 100), 
					new Item(1331), 
					new Item(1381), 
					new Item(1383), 
					new Item(1385), 
					new Item(1387), 
					new Item(1329), 
					new Item(1327), 
					new Item(1325),
					new Item(1323),
					new Item(6128),
					new Item(6129),
					new Item(6130),
					new Item(6131),
					new Item(6133),
					new Item(6135),
					new Item(6137),
					new Item(6143),
					new Item(6149),
					new Item(1321),
					new Item(1319), 
					new Item(1317), 
					new Item(1315),
					new Item(6185), 
					new Item(6322),
					new Item(6324),
					new Item(6326),
					new Item(6328),
					new Item(6330),
					new Item(5667),
					new Item(5668),
					new Item(537, 10), 
					new Item(6181), 
					new Item(1725),
					new Item(1727),
					new Item(1729),
					new Item(1731),
					new Item(1704),
					new Item(527, 50), 
					new Item(114, 20), 
					new Item(128, 20), 
					new Item(4129),
					new Item(4127),
					new Item(4125),
					new Item(4123),
					new Item(4121),
					new Item(1215, 1), 
					new Item(134, 20), 
					new Item(3145, 100), 
					new Item(6177), 
					new Item(985, 1))
					
			);
			
		items.put(ItemRarity.UNCOMMON,
				Arrays.asList(
						new Item(4566),
						new Item(4587), 
						new Item(861),
						new Item(158, 25),
						new Item(164, 25),
						new Item(146, 25),
						new Item(140, 25),
						new Item(537, 50),
						new Item(859),
						new Item(4131),
						new Item(5698, 1),
						new Item(6568),
						new Item(4089),
						new Item(4091),
						new Item(4093),
						new Item(4095),
						new Item(4097),
						new Item(4099),
						new Item(4100),
						new Item(4102),
						new Item(4675),
						new Item(4104),
						new Item(4106),
						new Item(2368),
						new Item(4108),
						new Item(6536),
						new Item(6524),
						new Item(6525),
						new Item(6526),
						new Item(6522, 200),
						new Item(6527),			
						new Item(11937, 100), 
						new Item(386, 100), 
						new Item(1409),
						new Item(140, 20), 
						new Item(11876, 100),
						new Item(995, 1000000))
						
		);
			
			items.put(ItemRarity.RARE,
					Arrays.asList(
							new Item(4566), 
							new Item(990, 10),
							new Item(3751),
							new Item(3753),
							new Item(3755),
							new Item(6577),
							new Item(2572),
							new Item(6528),
							new Item(9242, 50),
							new Item(9244, 50),
							new Item(9245, 50),
							new Item(11212, 50),
							new Item(4212), 
							new Item(535, 100), 
							new Item(4153, 1),
							new Item(11840),
							new Item(2366),
							new Item(4151, 1),
							new Item(995, 2500000),
							new Item(6199, 1),
							new Item(11818, 1)));
							
							
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new PvM Casket to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public PvmCasket(Player player) {
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
			player.sendMessage("You need atleast two free slots to open a PvM Casket.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need PvM Casket to do this.");
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
		int coins = 500_000 + Misc.random(500_000);
		int coinsDouble = 1_000_000 + Misc.random(1_000_000);
		int random = Misc.random(100);
		List<Item> itemList = random < 55 ? items.get(ItemRarity.COMMON) : random >= 65 && random <= 90 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
		Item item = Misc.getRandomItem(itemList);
		Item itemDouble = Misc.getRandomItem(itemList);

		if (Misc.random(10) == 0) {
			player.getItems().addItemUnderAnyCircumstance(13307, Misc.random(400, 1000));
			player.getItems().addItem(995, coins + coinsDouble);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " just got very lucky and hit the double!", GlobalMessages.MessageType.LOOT);
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " has received <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
			+ "</col> and <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col> from a PvM Casket.", GlobalMessages.MessageType.LOOT);
		} else {
			player.getItems().addItemUnderAnyCircumstance(13307, Misc.random(200, 500));
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>, and <col=255>"
					+ Misc.insertCommas(Integer.toString(coins)) + "</col>GP.");
		}
		container.stop();
	}


}