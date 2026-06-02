package valius.model.entity.npc.drops.dropTables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemRarity;
import valius.util.Misc;

/**
 * 
 * @author Divine | Feb. 9, 2019 , 5:00:01 a.m.
 *
 */

public class RareDropTable {

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
                		new Item(1624, Misc.random(1, 10)),
                        new Item(1622, Misc.random(1, 8)),
                        new Item(1620, Misc.random(1, 6)),
                        new Item(1617, Misc.random(1, 5)),
                        new Item(1631, Misc.random(1, 3)),
                        new Item(561, Misc.random(50, 100)),
                        new Item(563, Misc.random(50, 100)),
                        new Item(560, Misc.random(50, 100)),
                        new Item(886, Misc.random(150, 250)),
                        new Item(892, Misc.random(50, 100)),
                        new Item(829, Misc.random(40, 80)),
                        new Item(12351),
                        new Item(1419),
                        new Item(1305, 1),
                        new Item(11235, 1))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(830, Misc.random(30, 60)),
                        new Item(1319, 1),
                		new Item(1373, 1),
                		new Item(2364, Misc.random(5, 10)),
                		new Item(1616, Misc.random(1, 4)),
                		new Item(445, Misc.random(50, 100)),
                        new Item(1185, 1))
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
                        new Item(1201, 1),
                        new Item(995, 2000000),
                        new Item(1149, 1),
                        new Item(1247, 1),
                        new Item(1187, 1),
                        new Item(1249, 1))
        );
    }

    /**
     * The player object that will be triggering this event
     */
    private Player player;

    /**
     *
     * @param player the player
     */
    public RareDropTable(Player player) {
        this.player = player;
    }

    /**
     * Gets the drop from the rare drop table
     */
    public void getDrop() {
        int coins = 50_000 + Misc.random(250_000);
        int random = Misc.random(150);
        
        List<Item> itemList = random < 90 ? items.get(ItemRarity.COMMON) : random >= 90 && random <= 140 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
      
        Item item = Misc.getRandomItem(itemList);
            player.getItems().addItemUnderAnyCircumstance(995, coins);
            player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
            for(Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
                for(Item gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == ItemRarity.RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from the rare drop table."), GlobalMessages.MessageType.LOOT);
        }
	}