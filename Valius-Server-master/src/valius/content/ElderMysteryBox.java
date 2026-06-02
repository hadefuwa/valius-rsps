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
 * 
 * @author Divine | 4:55:34 a.m. | Oct. 29, 2019
 *
 */
public class ElderMysteryBox implements CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 33884;

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
                		new Item(537, Misc.random(15, 30)),//dbones
                		new Item(1632, Misc.random(2, 5)),//uncut dragonstone
						new Item(Misc.randomElementOf(ClueScrollRiddle.EASY_CLUES), 1),//clues
        				new Item(989, 1),//ckeys
                		new Item(990, 2),
                		new Item(9185, 1)//rune crossbow

        ));

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(4587),//d scimi
						new Item(Misc.randomElementOf(ClueScrollRiddle.MEDIUM_CLUES), 1),//clues
						new Item(452, Misc.random(10, 25)),//rune ore
						new Item(2364, Misc.random(5, 15)),//rune bar
                        new Item(208, Misc.random(3, 10)),//herbs
                        new Item(210, Misc.random(3, 10)),
                        new Item(212, Misc.random(3, 10)),
                        new Item(214, Misc.random(3, 10)),
                        new Item(216, Misc.random(3, 10)),
                		new Item(4585, 1)//dragon plateskirt
                        
                		));

        items.put(ItemRarity.RARE,
                Arrays.asList(
                		new Item(33882, 1),//nature dye
                		new Item(33883, 1),//grace dye
                		new Item(995, Misc.random(1000000, 5000000)),
                        new Item(33875, 1),//Occult cape
                        new Item(33876, 1),//Wings
                        new Item(33877, 1),//
                        new Item(33878, 1)//
                        
                		));
        
        items.put(ItemRarity.VERY_RARE,
                Arrays.asList(
                		new Item(995, Misc.random(5000000, 10000000)),
                        new Item(33875, 1)//Occult cape
                        
                		));
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
    public ElderMysteryBox(Player player) {
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
        int coins = 200_000 + Misc.random(1_500_000);
        int coinsDouble = 200_000 + Misc.random(1_500_000);
        int random = Misc.random(100);
        List<Item> itemList = random < 60 ? items.get(ItemRarity.COMMON) : random >= 60 && random <= 96 ? items.get(ItemRarity.UNCOMMON)
        		 : random > 96 && random <= 99 ? items.get(ItemRarity.RARE) : items.get(ItemRarity.VERY_RARE);
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
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received a rare item: "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from a Elder mystery box."), GlobalMessages.MessageType.LOOT);
        }
        container.stop();
    }


}