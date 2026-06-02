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

public class GemRareDropTable {

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
                        new Item(1631, Misc.random(1, 3)))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(1617, Misc.random(1, 5)),
                        new Item(1631, Misc.random(1, 3)))
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
                        new Item(6571, 1))
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
    public GemRareDropTable(Player player) {
        this.player = player;
    }

    /**
     * Gets the drop from the rare drop table
     */
    public void getDrop() {
        int coins = 50_000 + Misc.random(250_000);
        int random = Misc.random(100);
        
        List<Item> itemList = random < 50 ? items.get(ItemRarity.COMMON) : random >= 50 && random <= 99 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
      
        Item item = Misc.getRandomItem(itemList);
            player.getItems().addItemUnderAnyCircumstance(995, coins);
            player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
            for(Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
                for(Item gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == ItemRarity.RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from the Gem rare drop table."), GlobalMessages.MessageType.LOOT);
        }
	}