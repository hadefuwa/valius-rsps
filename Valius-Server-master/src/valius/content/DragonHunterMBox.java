package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import valius.content.UltraMysteryBox.Rarity;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.util.Misc;


public class DragonHunterMBox implements CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 33270;

	/**
	 * A map containing a List of {@link Item}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<Item>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
				new Item(4716),
				new Item(4724),
				new Item(4745),
				new Item(4753),
				new Item(4708),
				new Item(4732),
				new Item(4720),
				new Item(4728),
				new Item(4749),
				new Item(4757),
				new Item(4712),
				new Item(4736),
				new Item(4722),
				new Item(4730),
				new Item(4751),
				new Item(4759),
				new Item(4714),
				new Item(4738),
				new Item(4718),
				new Item(4726),
				new Item(4747),
				new Item(4755),
				new Item(4710),
				new Item(4734),
				new Item(2581),
				new Item(11804),
				new Item(11808),
				new Item(11806),
				new Item(6585),
				new Item(13229),
				new Item(13227),
				new Item(13231),
				new Item(4225),
				new Item(11838),
				new Item(21298),
				new Item(21301),
				new Item(21298),
				new Item(21304),
				new Item(11771),
				new Item(11770),
				new Item(11773),
				new Item(11772),
				new Item(2577),
				new Item(19550, 1)));
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new Item(12006),
					new Item(19481),
					new Item(12929),
					new Item(13198),
					new Item(13196),
					new Item(19547),
					new Item(19544),
					new Item(12900),
					new Item(11785),
					new Item(21012),
					new Item(11785),
					new Item(11283),
					new Item(13271),
					new Item(21633),
					new Item(11832),
					new Item(11834),
					new Item(11828),
					new Item(11830),
					new Item(11802),
					new Item(21003),
					new Item(19553)));
	
	items.put(Rarity.RARE,
			Arrays.asList(
					new Item(33127),
					new Item(33128),
					new Item(33129),
					new Item(33130),
					new Item(33131),
					new Item(22978),
					new Item(21012),
					new Item(33101),
					new Item(33102),
					new Item(33103),
					new Item(33104),
					new Item(33105),
					new Item(22002),
					new Item(11283),
					new Item(20784),
					new Item(12821),
					new Item(13343),
					new Item(13344),
					new Item(11847),
					new Item(11863),
					new Item(1053),
					new Item(1055),
					new Item(1057),
					new Item(33249),
					new Item(1050)));
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
	public DragonHunterMBox(Player player) {
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
        int coins = 2_000_000 + Misc.random(1_000_000);
        int coinsDouble = 2_000_000 + Misc.random(1_000_000);
        int random = Misc.random(100);
        List<Item> itemList = random < 60 ? items.get(Rarity.COMMON) : random >= 60 && random <= 96 ? items.get(Rarity.UNCOMMON): items.get(Rarity.RARE);
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
            for(Map.Entry<Rarity, List<Item>> gift : items.entrySet())
                for(Item gift_item : gift.getValue())
                    if(gift_item == item)
                        if(gift.getKey() == Rarity.RARE)
            				GlobalMessages.send(Misc.capitalize(player.playerName)+" received a rare item: "+(item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + " from a Dragonhunter Mbox."), GlobalMessages.MessageType.LOOT);
        }
        container.stop();
    }
}

