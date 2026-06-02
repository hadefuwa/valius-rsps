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
 * @author Divine 
 *
 */

public class SlayerRareDropTable {

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
                		new Item(556, 5000),//runes
                        new Item(554, 5000),
                        new Item(557, 5000),
                        new Item(555, 5000),
                        new Item(2, Misc.random(500, 1000)),//cannon balls
                        new Item(1631, Misc.random(30, 35)),//gems
                        new Item(1620, Misc.random(25, 30)),
                        new Item(1617, Misc.random(20, 25)),
                        new Item(1631, Misc.random(15, 20)))
        );

        items.put(ItemRarity.UNCOMMON,
                Arrays.asList(
                        new Item(441, Misc.random(450, 600)),//iron ore
                        new Item(545, Misc.random(350, 500)),//coal
                        new Item(9143, Misc.random(300, 400)),//adamant bolt
                        new Item(9143, Misc.random(200, 300)),//runite bolt
                        new Item(7945, Misc.random(250, 350)),//raw monkfish
                        new Item(1617, Misc.random(20, 30)),//gems
                        new Item(1516, Misc.random(200, 300)),//Yew log
                        new Item(208, Misc.random(200, 300)),//herbs
                        new Item(3052, Misc.random(200, 300)),
                        new Item(208, Misc.random(200, 300)),
                        new Item(990, Misc.random(10, 20)),//crystal keys
                        new Item(452, Misc.random(30, 50)))//runite bar
        );

        items.put(ItemRarity.RARE,
                Arrays.asList(
                        new Item(11237, Misc.random(150, 300)),//dragon arrow tips
                        new Item(11232, Misc.random(150, 300)),
                        new Item(9379, Misc.random(400, 600)),
                        new Item(9381, Misc.random(200, 400)),
                        new Item(9382, Misc.random(150, 300)),
                        new Item(384, Misc.random(300, 400)),//raw shark
                        new Item(22731, 1))//dragon hasta
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
    public SlayerRareDropTable(Player player) {
        this.player = player;
    }

    /**
     * Gets the drop from the rare drop table
     */
    public void getDrop() {
        int coins = 50_000 + Misc.random(250_000);
        int random = Misc.random(100);
        
        List<Item> itemList = random < 60 ? items.get(ItemRarity.COMMON) : random >= 60 && random <= 90 ? items.get(ItemRarity.UNCOMMON) : items.get(ItemRarity.RARE);
      
        Item item = Misc.getRandomItem(itemList);
            player.getItems().addItemUnderAnyCircumstance(995, coins);
            player.getItems().addItemUnderAnyCircumstance(item.getId(), item.getAmount());
            player.sendMessage("You receive " + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + ", and "
                    + Misc.insertCommas(Integer.toString(coins)) + " coins.");
            for(Map.Entry<ItemRarity, List<Item>> gift : items.entrySet())
                for(Item gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == ItemRarity.RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from the Slayer rare drop table."), GlobalMessages.MessageType.LOOT);
        }
	}