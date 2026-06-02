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
public class MysteryBox implements CycleEvent {

    /**
     * The item id of the mystery box required to trigger the event
     */
    private static final int MYSTERY_BOX = 6199;

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
                		new Item(4566),
                        new Item(452, 45),
                        new Item(6739, 1),
                        new Item(71, 20),
                        new Item(12439),
                        new Item(12397),
                        new Item(452, 500),
                        new Item(2364, 350),
                        new Item(12412),
                        new Item(12357),
                        new Item(13442, 75),
                        new Item(11937, 75),
                        new Item(12351),
                        new Item(1419),
                        new Item(1305, 1),
                        new Item(11235, 1),
                        new Item(4084),
                        new Item(4212),
                        new Item(4708),
                        new Item(4710),
                        new Item(4712),
                        new Item(4714),
                        new Item(11840),
                        new Item(11731, 1),
                        new Item(12848),
                        new Item(3140),
                        new Item(9740),
                        new Item(11232, 80),
                        new Item(811, 50),
                        new Item(11128),
                        new Item(11130),
                        new Item(4716),
                        new Item(4718),
                        new Item(4720),
                        new Item(4722),
                        new Item(4724),
                        new Item(4726),
                        new Item(4728),
                        new Item(4730),
                        new Item(4732),
                        new Item(4734),
                        new Item(4736),
                        new Item(4738),
                        new Item(4745),
                        new Item(7158),
                        new Item(4747),
                        new Item(4749),
                        new Item(1514, 50),
                        new Item(1516, 50),
                        new Item(1518, 60),
                        new Item(11944, 25),
                        new Item(1520, 70),
                        new Item(4751),
                        new Item(4753),
                        new Item(19670, 25),
                        new Item(4755),
                        new Item(4757),
                        new Item(4759),
                        new Item(220, 25),
                        new Item(12391),
                        new Item(12849),
                        new Item(4151),
                        new Item(12786),
                        new Item(12783),
                        new Item(1615, 1),
                        new Item(12798),
                        new Item(13307, 2000),
                        new Item(2996, 400),
                        new Item(12337))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(4566),
                        new Item(452, 45),
                        new Item(6739, 1),
                        new Item(71, 20),
                        new Item(12439),
                        new Item(12397),
                        new Item(452, 500),
                        new Item(2364, 350),
                        new Item(12412),
                        new Item(12357),
                        new Item(13442, 75),
                        new Item(11937, 75),
                        new Item(12351),
                        new Item(1419),
                        new Item(1305, 1),
                        new Item(11235, 1),
                        new Item(4084),
                        new Item(4212),
                        new Item(4708),
                        new Item(4710),
                        new Item(4712),
                        new Item(4714),
                        new Item(11840),
                        new Item(11731, 1),
                        new Item(12848),
                        new Item(2513),
                        new Item(9740),
                        new Item(11230, 150),
                        new Item(811, 50),
                        new Item(11128),
                        new Item(11130),
                        new Item(4716),
                        new Item(4718),
                        new Item(4720),
                        new Item(4722),
                        new Item(4724),
                        new Item(4726),
                        new Item(4728),
                        new Item(4730),
                        new Item(4732),
                        new Item(4734),
                        new Item(4736),
                        new Item(4738),
                        new Item(4745),
                        new Item(7158),
                        new Item(4747),
                        new Item(4749),
                        new Item(1514, 50),
                        new Item(1516, 50),
                        new Item(1518, 60),
                        new Item(11944, 25),
                        new Item(1520, 70),
                        new Item(4751),
                        new Item(4753),
                        new Item(19670, 25),
                        new Item(4755),
                        new Item(4757),
                        new Item(4759),
                        new Item(12379),
                        new Item(220, 25),
                        new Item(12391),
                        new Item(12849),
                        new Item(4151),
                        new Item(12786),
                        new Item(12783),
                        new Item(1615, 1),
                        new Item(12798),
                        new Item(13307, 3000),
                        new Item(2996, 400),
                        new Item(12337))
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
                        new Item(11818, 1),
                        new Item(995, 10000000),
                        new Item(2996, 600),
                        new Item(12437, 1),
                        new Item(12359, 1),
                        new Item(12849, 1),
                        new Item(12802, 1),
                        new Item(12800, 1),
                        new Item(12798, 1),
                        new Item(2697, 1),
                        new Item(12373, 1)));
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
    public MysteryBox(Player player) {
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
        int random = Misc.random(200);
        List<Item> itemList = random < 105 ? items.get(ItemRarity.COMMON) : random >= 105 && random <= 190 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
        Item item = Misc.getRandomItem(itemList);
        Item itemDouble = Misc.getRandomItem(itemList);

        if (Misc.random(200) == 2 && player.getItems().getItemCount(19730, true) == 0 && player.summonId != 19730) {
            GlobalMessages.send(player.playerName + " has found a Bloodhound!", GlobalMessages.MessageType.LOOT);
            player.getItems().addItemUnderAnyCircumstance(19730, 1);
        }

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
                        if(gift.getKey() == ItemRarity.RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received a rare item: "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from a mystery box."), GlobalMessages.MessageType.LOOT);
        }
        container.stop();
    }


}