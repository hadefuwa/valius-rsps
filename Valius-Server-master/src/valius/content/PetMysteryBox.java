package valius.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import valius.discord.DiscordBot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemDefinition;
import valius.util.Misc;

/**
 * 
 * @author Divine | 9:20:39 p.m. | Aug. 30, 2019
 *
 */
public class PetMysteryBox {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 33669;

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
					new Item(33233),//rev imp
					new Item(33234),//rev goblin
					new Item(33235),//rev pyrefiend
					new Item(33236),//rev hobgoblin
					new Item(33237),//rev cyclops
					new Item(33271), // pet moo
					new Item(33175), // crawling hand
					new Item(33176), // cave bug
					new Item(33177), // cave crawler
					new Item(33178), // banshee
					new Item(33279), // cave slime
					new Item(33180), // rock slug
					new Item(33182), // pyrefiend
					new Item(33183), // basalisk
					new Item(33184), // infernal mage
					new Item(33185), // bloodveld
					new Item(33186), // jelly
					new Item(33187), // turoth
					new Item(33181), // cockatrice
					new Item(33189), // dust devil
					new Item(33191), // wyvern
					new Item(33192), // gargoyle
					new Item(33193), // nechryael
					new Item(33194), // abby demon
					new Item(33195), // dark beast
					new Item(33190), // kurask
					new Item(33228),//greater demon 
					new Item(33227),//black demon
					new Item(33188))// abre spec
			
		);
		
	items.put(ItemRarity.UNCOMMON,
			Arrays.asList(
					new Item(33577),//ice wyrm
					new Item(33576),//wildy wyrm
					new Item(33174),//demonic gorilla
					new Item(33196),//night beast
					new Item(33197),//great abby demon
					new Item(33198),//crushing hand
					new Item(33199),//chasm crawler
					new Item(33200),//screaming banshee
					new Item(33201),//twisted banshee
					new Item(33203),//cockathrice
					new Item(33204),//flaming pyrelord
					new Item(33205),//monstrous basalisk
					new Item(33206),//malevolent mage
					new Item(33207),//insatiable bloodveld
					new Item(33209),//vitreous jelly
					new Item(33210),//warped jelly
					new Item(33211),//cave abomination
					new Item(33212),//abhorrent spec
					new Item(33213),//repugnant spec
					new Item(33214),//choke devil
					new Item(33215),//king kurask
					new Item(33217),//nuclear smoke devil
					new Item(33218),//marble gargoyle
					new Item(33219),//nechryarch
					new Item(33202))//giant rockslug
	);
		
		items.put(ItemRarity.RARE,
				Arrays.asList(
						new Item(33404),//celestial fairy
						new Item(33402),//andy
						new Item(33403),//Divine
						new Item(33593),//nightmare
						new Item(33421),//star sprite
						new Item(33439),//blood phoenix
						new Item(33426),//celestial penguin
						new Item(22746),//hydra
						new Item(22748),//hydra
						new Item(22750),//hydra
						new Item(22752),//hydra
						new Item(33221),//xarpus
						new Item(33222),//vasilias
						new Item(33223),//bloat
						new Item(33224),//maiden
						new Item(33225),//lizard shaman
						new Item(33473),//lil zik
						new Item(22382),//vasa miniro
						new Item(22378),//tektiny
						new Item(22376),//puppadile
						new Item(22384),//vespina
						new Item(21273),//skotos
						new Item(12921),//zulrah
						new Item(21992),//vorki
						new Item(13181),//scorpia
						new Item(12816),//dark core
						new Item(12647),//kq
						new Item(13225),//jad (new)
						new Item(12648),//smoke devil
						new Item(33245),//glod
						new Item(33246),//ice queen
						new Item(33247),//tarn
						new Item(20851),//olmlet
						new Item(33249),//rune dragon
						new Item(33259),//wyrm
						new Item(33260),//drake
						new Item(33261),//wyrm
						new Item(33133),//frost imp
						new Item(12650),//graardor
						new Item(12649),//kree
						new Item(12651),//zilly
						new Item(12652),//tsut
						new Item(11995),//chaos ele
						new Item(12921),//zulrah
						new Item(13247),//hell puppy
						new Item(12653),//kbd
						new Item(12655),//kraken
						new Item(13178),//callisto
						new Item(13179),//vetion
						new Item(13177),//venenatis
						new Item(33477)));//void knight champ
		
		items.put(ItemRarity.VERY_RARE,
				Arrays.asList(
						new Item(33548),//scoop
						new Item(33481),//test(justiciar follower)
						new Item(19730),//bloodhound
						new Item(964),//post petie
						new Item(33549)));//solomon
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
	public PetMysteryBox(Player player) {
		this.player = player;
	}

	/**
	 * Can the player open the mystery box
	 */
	private boolean canMysteryBox = true;
	
	/**
	 * The prize received
	 */
	private int mysteryPrize;

	private int mysteryAmount;
	
	private int spinNum = 0;
	
	/**
	 * The chance to obtain the item
	 */
	private int random;
	
	private final int INTERFACE_ID = 47000;
	private final int ITEM_FRAME = 47101;
	
	/**
	 * The rarity of the reward
	 */
	private ItemRarity rewardRarity;

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum ItemRarity {
		UNCOMMON("<col=005eff>"),
		COMMON("<col=336600>"),
		RARE("<col=B80000>"),
		VERY_RARE("<col=B80000>");
		
		private String color;
		
		ItemRarity(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return color;
		}
		
	    public static ItemRarity forId(int id) {
	        for (ItemRarity tier : ItemRarity.values()) {
	            if (tier.ordinal() == id)
	                return tier;
	        }
	        return null;
	    }
	}
	
	public void spin() {
		// Server side checks for spin
		if (!canMysteryBox) {
			player.sendMessage("Please finish your current spin.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You require a mystery box to do this.");
			return;
		}

		// Delete box
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		// Initiate spin
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		player.sendMessage(":spin");
		process();
	}
	
	public void process() {
		// Reset prize
		mysteryPrize = -1;

		mysteryAmount = -1;
		// Can't spin when already in progress
		canMysteryBox = false;
		
		random = Misc.random(100);
		List<Item> itemList = random < 60 ? items.get(ItemRarity.COMMON) :
			random >= 60 && random < 90 ? items.get(ItemRarity.UNCOMMON):
				random >= 90 && random <= 98 ? items.get(ItemRarity.RARE) :
					items.get(ItemRarity.VERY_RARE);
		rewardRarity = random < 60 ? ItemRarity.COMMON :
			random >= 60 && random < 90 ? ItemRarity.UNCOMMON :
			 random >= 90 && random <= 98 ? ItemRarity.RARE :
				ItemRarity.VERY_RARE;
		
		Item item = Misc.getRandomItem(itemList);

		mysteryPrize = item.getId();

		mysteryAmount = item.getAmount();

		// Send items to interface
		// Move non-prize items client side if you would like to reduce server load
		if (spinNum == 0) {
			for (int i=0; i<66; i++){
				ItemRarity notPrizeRarity = ItemRarity.values()[new Random().nextInt(ItemRarity.values().length)];
				Item NotPrize =Misc.getRandomItem(items.get(notPrizeRarity));
				final int NOT_PRIZE_ID = NotPrize.getId();
				final int NOT_PRIZE_AMOUNT = NotPrize.getAmount();
				sendItem(i, 55, mysteryPrize, NOT_PRIZE_ID,1);
			}
		} else {
			for (int i=spinNum*50 + 16; i<spinNum*50 + 66; i++){
				ItemRarity notPrizeRarity = ItemRarity.values()[new Random().nextInt(ItemRarity.values().length)];	
				final int NOT_PRIZE_ID = Misc.getRandomItem(items.get(notPrizeRarity)).getId();
				sendItem(i, (spinNum+1)*50 + 5, mysteryPrize, NOT_PRIZE_ID, mysteryAmount);
			}
		}
		spinNum++;
	}
	
	public void reward() {
		if (mysteryPrize == -1) {
			return;
		}
		
		//player.boxCurrentlyUsing = -1;
		
		player.getItems().addItemUnderAnyCircumstance(mysteryPrize, mysteryAmount);
		
		// Reward text colour
		String tier = rewardRarity.getColor();
		
		// Reward message
		String name = ItemDefinition.forId(mysteryPrize).getName();
		if (name.substring(name.length() - 1).equals("s")) {
			player.sendMessage("Congratulations, you have won " + tier + name + "@bla@!");
		}
		else {
			player.sendMessage("Congratulations, you have won a " + tier + name + "@bla@!");
		}
		
		if (random > 85) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName)+" hit the jackpot and got a <col=CC0000>"+name, GlobalMessages.MessageType.LOOT);
			DiscordBot.sendMessage("valius-bot","[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " hit the jackpot and received a "+name+" from an Pet Mystery Box!");
		}
		
		// Can now spin again
		canMysteryBox = true;
	}
	
	public void sendItem(int i, int prizeSlot, int PRIZE_ID, int NOT_PRIZE_ID, int amount) {
		if (i == prizeSlot) {
			player.getPA().mysteryBoxItemOnInterface(PRIZE_ID, amount, ITEM_FRAME, i);
		}
		else {
			player.getPA().mysteryBoxItemOnInterface(NOT_PRIZE_ID, amount, ITEM_FRAME, i);
		}
	}
	
	public void openInterface() {
		player.boxCurrentlyUsing = MYSTERY_BOX;
		// Reset interface
		player.sendMessage(":resetBox");
		for (int i=0; i<66; i++){
			player.getPA().mysteryBoxItemOnInterface(-1, 1, ITEM_FRAME, i);
		}
		spinNum = 0;
		// Open
		player.getPA().sendString("Pet Mystery Box", 47002);
		player.getPA().showInterface(INTERFACE_ID);
	}

}