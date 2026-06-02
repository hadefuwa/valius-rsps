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
 * @author Divine
 */
public class InfernalMysteryBox implements CycleEvent {

    /**
     * The item id of the Infernal Mystery Box required to trigger the event
     */
    private static final int INFERNAL_MYSTERY_BOX = 33154;

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
                		new Item(33164, 1),//Firecape mixes
        				new Item(33165, 1),
						new Item(33166, 1),
						new Item(33167, 1))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(33161, 1),//Infernal cape mixes
                        new Item(33162, 1),
                		new Item(33163, 1))
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
        				new Item(33116, 1),//Zilyana's Longbow 
                        new Item(33153, 1)));//infernal chest key
    }

    /**
     * The player object that will be triggering this event
     */
    private Player player;

    /**
     * Constructs a new Infernal Mystery Box to handle item receiving for this player and this player alone
     *
     * @param player the player
     */
    public InfernalMysteryBox(Player player) {
        this.player = player;
    }

    /**
     * Opens a Mbox if possible, and ultimately triggers and event, if possible.
     */
    public void open() {
        if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
            return;
        }
        if (player.getItems().freeSlots() < 2) {
            player.sendMessage("You need atleast two free slots to open a Infernal Mystery Box.");
            return;
        }
        if (!player.getItems().playerHasItem(INFERNAL_MYSTERY_BOX)) {
            player.sendMessage("You need a Infernal Mystery Box to do this.");
            return;
        }
        player.getItems().deleteItem(INFERNAL_MYSTERY_BOX, 1);
        player.lastMysteryBox = System.currentTimeMillis();
        CycleEventHandler.getSingleton().stopEvents(this);
        CycleEventHandler.getSingleton().addEvent(this, this, 2);
    }

    /**
     * Executes the event for receiving the mbox
     */
    @Override
    public void execute(CycleEventContainer container) {
        if (player.disconnected || Objects.isNull(player)) {
            container.stop();
            return;
        }
        int coins = 1_500_000 + Misc.random(3_500_000);
        int random = Misc.random(10);
        List<Item> itemList = random < 4 ? items.get(ItemRarity.COMMON) : random >= 4 && random <= 7 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
        Item item = Misc.getRandomItem(itemList);

		if (Misc.random(400) == 2) {
			GlobalMessages.send(player.playerName + " has receive a Infernal Partyhat from a</col> @blu@Infernal Mystery Box<col/>@blu@", GlobalMessages.MessageType.LOOT);
			player.getItems().addItemUnderAnyCircumstance(22001, 1);
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
					+ ", and " + Misc.insertCommas(Integer.toString(coins)) + " coins.");
			GlobalMessages.send(Misc.capitalizeJustFirst(player.playerName) + " has received a " + ItemAssistant.getItemName(item.getId()) + " from an Infernal Mysterybox!", GlobalMessages.MessageType.LOOT);
		}
		container.stop();
	}

}