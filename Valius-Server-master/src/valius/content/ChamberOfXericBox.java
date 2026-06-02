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
 * 
 * @author Divine | 4:55:34 a.m. | Oct. 29, 2019
 *
 */
public class ChamberOfXericBox implements CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 33941;

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
                		new Item(2528, 1),//
                		new Item(13307, 75 + Misc.random(2000)),//
                		new Item(537, 75 + Misc.random(75)),
                        new Item(995, 1000000 + Misc.random(2000000)),//
                        new Item(892, 250 + Misc.random(1500)),//
                        new Item(11212, 200),//
                        new Item(11230, 150),//
                        new Item(208, 120),//
                		new Item(210, 120),//
                		new Item(212, 120),
                        new Item(214, 100),//
                        new Item(3052, 120),//
                        new Item(216, 120),//
                        new Item(2486, 150),//
                		new Item(218, 150)//

        ));

        items.put(ItemRarity.RARE,
                Arrays.asList(
                		new Item(22296, 1),//
                		new Item(21000, 1),//
                		new Item(21009, 1),
                        new Item(20849, 1),//
                        new Item(21031, 1),//
                        new Item(22296, 1),//
                        new Item(33505, 1),//
                        new Item(33506, 1),//
                		new Item(33507, 1),//
                		new Item(21000, 1),
                        new Item(21009, 1),//
                        new Item(21028, 1),//
                        new Item(20849, 1),//
                        new Item(21031, 1),//
                		new Item(20997, 1),//
                		new Item(20784, 1),
                        new Item(21006, 1),//
                        new Item(21015, 1),//
                        new Item(21012, 1),//
                		new Item(21018, 1),
                        new Item(21021, 1),//
                        new Item(21024, 1),//
                        new Item(20784, 1),//
                        new Item(21006, 1),//
                		new Item(21015, 1),//
                		new Item(21012, 1),
                        new Item(21018, 1),//
                        new Item(21021, 1),//
                        new Item(21024, 1),//
                		new Item(33124, 1),
                        new Item(21000, 1)
                        
                		));
        
    }

    /**
     * The player object that will be triggering this event
     */
    private Player player;

    /**
     * Constructs a new mystery box to handle item receiving for this player and this player alone
     *
     * @param player the player
     */
    public ChamberOfXericBox(Player player) {
        this.player = player;
    }

    /**
     * Opens a mystery box if possible, and ultimately triggers and event, if possible.
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
        int coins = 2_500_000 + Misc.random(5_000_000);
        int coinsDouble = 200_000 + Misc.random(1_500_000);
        int random = Misc.random(100);
        List<Item> itemList = random < 97 ? items.get(ItemRarity.COMMON) :  items.get(ItemRarity.RARE);
        Item item = Misc.getRandomItem(itemList);
        Item itemDouble = Misc.getRandomItem(itemList);

        if (Misc.random(10) == 0) {
            player.getItems().addItem(995, coins + coinsDouble);
            player.getItems().addItem(item.getId(), item.getAmount());
            player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
            player.sendMessage("You receive " + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
           
        } else {
            player.getItems().addItem(995, coins);
            player.getItems().addItem(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
            for(Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
                for(Item gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == ItemRarity.RARE || gift.getKey() == ItemRarity.VERY_RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received a rare item: "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from a  Raids Chest."), GlobalMessages.MessageType.LOOT);
        }
        container.stop();
    }


}