package valius.content.bossCaskets;

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
public class BandosCasket implements CycleEvent {

    /**
     * The item id of the Casket required to trigger the event
     */
    private static final int BANDOS_CASKET = 12022;

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
                		new Item(11836))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(11832),
                		new Item(11812))
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
                        new Item(11834)));
    }

    /**
     * The player object that will be triggering this event
     */
    private Player player;

    /**
     * Constructs a new casket to handle item receiving for this player and this player alone
     *
     * @param player the player
     */
    public BandosCasket(Player player) {
        this.player = player;
    }

    /**
     * Opens a casket if possible, and ultimately triggers and event, if possible.
     */
    public void open() {
        if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
            return;
        }
        if (player.getItems().freeSlots() < 2) {
            player.sendMessage("You need atleast two free slots to open a Bandos Casket.");
            return;
        }
        if (!player.getItems().playerHasItem(BANDOS_CASKET)) {
            player.sendMessage("You need a Bandos Casket to do this.");
            return;
        }
        player.getItems().deleteItem(BANDOS_CASKET, 1);
        player.lastMysteryBox = System.currentTimeMillis();
        CycleEventHandler.getSingleton().stopEvents(this);
        CycleEventHandler.getSingleton().addEvent(this, this, 2);
    }

    /**
     * Executes the event for receiving the casket
     */
    @Override
    public void execute(CycleEventContainer container) {
        if (player.disconnected || Objects.isNull(player)) {
            container.stop();
            return;
        }
        int coins = 500_000 + Misc.random(1_500_000);
        int random = Misc.random(10);
        List<Item> itemList = random < 4 ? items.get(ItemRarity.COMMON) : random >= 4 && random <= 7 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
        Item item = Misc.getRandomItem(itemList);

		if (Misc.random(200) == 2 && player.getItems().getItemCount(19730, true) == 0 && player.summonId != 19730) {
			GlobalMessages.send(player.playerName + " has receive a Justicair Shield from a</col> @blu@Bandos Casket<col/>@blu@!", GlobalMessages.MessageType.LOOT);
			player.getItems().addItemUnderAnyCircumstance(33168, 1);
		} else {
			player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId())
					+ ", and " + Misc.insertCommas(Integer.toString(coins)) + " coins.");
		}
		container.stop();
	}


}