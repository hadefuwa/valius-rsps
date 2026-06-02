package valius.content.tradingpost;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FilenameUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemDefinition;
import valius.model.items.ItemUtility;
import valius.util.Misc;
import valius.world.World;

/**
 *
 * @author Nighel
 * @credits Nicholas
 *
 */

@Slf4j
public class Listing {
	
	public static boolean tradingPostEnabled = true;

	private static Map<String, PlayerListings> sales = Maps.newConcurrentMap();
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	//test
	public static void save() {
		sales.entrySet().stream().forEach(entry -> {
			try(FileWriter fw = new FileWriter("./data/tradingpost/listings/" + entry.getKey() + ".json")){
				gson.toJson(entry.getValue(), fw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public static void load() {
		sales.clear();
		File listingFolder = new File("./data/tradingpost/listings/");
		if (!listingFolder.exists()) {
			listingFolder.mkdirs();
		}
		for(File f : listingFolder.listFiles()) {
			try(FileReader fr = new FileReader(f)){
				String name = FilenameUtils.removeExtension(f.getName());
	    		Type type = new TypeToken<PlayerListings>() {}.getType();
	    		PlayerListings listings = gson.fromJson(fr, type);
	    		sales.put(name.toLowerCase(), listings);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("Loaded {} trading post listings!", sales.size());
	}
	public static void save(PlayerListings listing) {
		sales.entrySet().stream().filter(entry -> entry.getValue() == listing).forEach(entry -> {
			try(FileWriter fw = new FileWriter("./data/tradingpost/listings/" + entry.getKey() + ".json")){
				gson.toJson(entry.getValue(), fw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}


	public static PlayerListings getListingForPlayer(String playerName) {
		return sales.get(playerName.toLowerCase());
	}
	/**
	 * Loads the sales via player name
	 * 
	 * @param playerName - player his username
	 * @return
	 */

	public static List<ListedItem> getSalesForPlayer(String playerName) {
		PlayerListings listing = getListingForPlayer(playerName.toLowerCase());
		return listing == null ? Lists.newArrayList() : listing.getItemsForSale();
	}

	/**
	 * Loads the sales via item id
	 * 
	 * @param itemId
	 * @return
	 */

	public static List<ListedItem> getSalesForId(int itemId) {
		List<ListedItem> items = Lists.newArrayList();
		sales.values().stream().map(listing -> listing.getSalesMatching(itemId)).forEach(list -> items.addAll(list));
		return items;
	}


	public static List<ListedItem> getSalesForName(String itemName) {
		List<ListedItem> items = Lists.newArrayList();
		sales.values().stream().map(listing -> listing.getSalesMatching(itemName)).forEach(list -> items.addAll(list));
		return items;
	}

	public static List<ListedItem> getRecentListings(){
		List<ListedItem> items = Lists.newArrayList();
		sales.values().stream().map(listing -> listing.getItemsForSale()).forEach(list -> items.addAll(list));
		items.sort((first, second) -> first.getListedAt() > second.getListedAt() ? -1 : 1);
		return items;
	}

	/**
	 * Opens up the first interface for the trading post. And then loading all the
	 * data thats needed.
	 * 
	 * @param c
	 */

	public static void openPost(Player c, boolean soldItem, boolean openFirst) {
		
		
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if(c.getMode() == null) {
			c.sendMessage("Your mode is null, please tell an admin!");
			return;
		}
		if (!c.getMode().isTradingPermitted(null, null)) {
			c.sendMessage("You are not permitted to make use of this.");
			return;
		}
		if(!sales.containsKey(c.getName().toLowerCase())) {
			PlayerListings listings = new PlayerListings();
			sales.put(c.getName().toLowerCase(), listings);
			save(listings);
		}
		
		resetEverything(c);
		emptyInterface(c, openFirst);
		c.getPA().showInterface(48600);
		sendSidebar(c);

		if (soldItem) {
			String each = c.pendingItem.getAmount() > 1 ? "each" : "";
			c.sendMessage("[@red@Trading Post@bla@] You successfully list " + c.pendingItem.getAmount() + "x "
					+ ItemAssistant.getItemName(c.pendingItem.getId()) + " for " + Misc.format(c.pendingItem.getPricePer()) + " GP " + each);
			c.pendingItem = null;
		}
		loadPlayersListings(c);
		c.insidePost = true;
		loadHistory(c);
	}

	/**
	 * Makes all the listings show up for the player.
	 * 
	 * @param c
	 */

	public static void loadPlayersListings(Player c) {
		int start = 48788, id = 0, moneyCollectable = 0;

		List<ListedItem> items = getSalesForPlayer(c.getName().toLowerCase());

		for (ListedItem sale : items) {
			c.getPA().sendTradingPost(48847, sale.getId(), id, 1);
			id++;
			c.getPA().sendFrame126(sale.getName(), start);
			start++;
			c.getPA().sendFrame126("" + Misc.format(sale.getPricePer()), start);
			start++;
			c.getPA().sendFrame126((sale.getAmount() - sale.getSold()) + " / " + sale.getAmount(), start);
			start += 2;
			moneyCollectable += sale.getPendingCash();
		}
		c.getPA().sendFrame126(Misc.format(moneyCollectable) + " GP", 48610);
		for (int k = id; k < 15; k++) {
			c.getPA().sendTradingPost(48847, -1, k, -1);
		}
		for (int i = start; i < 48850; i++) {
			c.getPA().sendFrame126("", i);
		}
	}

	/**
	 * Shows the last 10 latest sales you have done.
	 * 
	 * @param c
	 */

	public static void loadHistory(Player c) {

		PlayerListings listing = getListingForPlayer(c.getName().toLowerCase());

		for (int i = 0, start1 = 48636, start2 = 48637; i < c.saleItems.size(); i++) {
			// System.out.println("salesItems - " + c.saleItems.get(i).intValue());
			// System.out.println("saleAmount - " + c.saleAmount.get(i).intValue());
			// System.out.println("salePrice - " + c.salePrice.get(i).intValue());
			if (c.saleItems.get(i).intValue() > 0 && c.saleAmount.get(i).intValue() > 0
					&& c.salePrice.get(i).intValue() > 0) {
				String each = c.saleAmount.get(i).intValue() > 1 ? "each" : "coins";
				c.getPA().sendFrame126(c.saleAmount.get(i).intValue() + " x "
						+ ItemAssistant.getItemName(c.saleItems.get(i).intValue()), start1);
				c.getPA().sendFrame126("sold for " + zerosintomills(c.salePrice.get(i).intValue()) + " " + each,
						start2);
				start1 += 2;
				start2 += 2;
			}
		}
	}
	
	private static boolean itemSellable(int itemId) {
		if (ItemDefinition.forId(itemId) != null) {
			if (!ItemDefinition.forId(itemId).isTradable()) {
				return false;
			}
		}
		for (int item : Config.NOT_SHAREABLE) {
			if (item == itemId) {
				return false;
			}
		}
		if (itemId == 995) {
			return false;
		}
		return true;
	}

	/**
	 * Opens up the selected item using offer 1/5/10/all/x
	 * 
	 * @param c
	 * @param itemId
	 * @param amount
	 * @param p
	 */

	public static void offerSelectedItem(Player c, int itemId, int amount) {
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {

			if(c.debugMessage)
				c.sendMessage("In session");
			return;
		}
		
		
		PlayerListings listing = Listing.getListingForPlayer(c.getName().toLowerCase());
		if(listing.getItemsForSale().size() >= 15) {
			c.sendMessage("[@red@Trading Post@bla@] You can't sell more than 15 items!");
			return;
		}
		
		if(!itemSellable(itemId)) {
			c.sendMessage("[@red@Trading Post@bla@] You can't sell that item");
			return;
		}
		if (!c.getItems().playerHasItem(itemId, amount)) {
			c.sendMessage("[@red@Trading Post@bla@] You don't have that many " + ItemAssistant.getItemName(itemId)
					+ (amount > 1 ? "s" : "") + ".");
			return;
		}
		

		//openNewListing(c);
		c.pendingItem = new ListedItem(itemId, amount);
		c.pendingItem.setSeller(c.getName().toLowerCase());
		c.pendingItem.setPricePer(ItemDefinition.forId(itemId) == null ? 1 : ItemDefinition.forId(itemId).getHighAlchValue());

		if(c.pendingItem.getPricePer() <= 0) {
			c.pendingItem.setPricePer(1);
		}
		
		c.inSelecting = false;
		c.isListing = true;

		c.getPA().showInterface(48598);
		int id = c.pendingItem.getId();
		if(ItemUtility.itemIsNote[id])
			id = World.getWorld().getItemHandler().getCounterpartOrSelf(id);
		c.getPA().sendTradingPost(48962, id, 0, c.pendingItem.getAmount());
		c.getPA().sendFrame126(ItemAssistant.getItemName(c.pendingItem.getId()), 48963);
		c.getPA().sendFrame126("Price (each): " + Misc.format(c.pendingItem.getPricePer()) + "", 48964);
		c.getPA().sendFrame126("Quantity: " + c.pendingItem.getAmount(), 48965);
	}
	
	public static void setPriceForItem(Player c, int price) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		
		if(c.pendingItem == null) {
			return;
		}
		
		c.pendingItem.setPricePer(price);

		c.inSelecting = false;
		c.isListing = true;
		
		c.getPA().showInterface(48598);
		int id = c.pendingItem.getId();
		if(ItemUtility.itemIsNote[id])
			id = World.getWorld().getItemHandler().getCounterpartOrSelf(id);
		c.getPA().sendTradingPost(48962, id, 0, c.pendingItem.getAmount());
		c.getPA().sendFrame126(ItemAssistant.getItemName(c.pendingItem.getId()), 48963);
		c.getPA().sendFrame126("Price (each): " + Misc.format(c.pendingItem.getPricePer()) + "", 48964);
		c.getPA().sendFrame126("Quantity: " + c.pendingItem.getAmount(), 48965);
	}

	/**
	 * Writes every thing the the proper files.
	 * 
	 * @param c
	 */

	public static void confirmListing(Player c) {
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		
		if (c.pendingItem == null) {
			return;
		}
		
		PlayerListings listing = sales.getOrDefault(c.getName().toLowerCase(), new PlayerListings());
		ListedItem listedItem = c.pendingItem;
		if(listing.offer(listedItem)) {
			listedItem.setListedAt(System.currentTimeMillis());
			c.getItems().deleteItem2(listedItem.getId(), listedItem.getAmount());
			if(ItemUtility.itemIsNote[listedItem.getId()]) {
				listedItem.setId(World.getWorld().getItemHandler().getCounterpartOrSelf(listedItem.getId()));
			}
		}
		
		openPost(c, true, false);
		PlayerSave.save(c);
		
		sales.put(c.getName().toLowerCase(), listing);
		save(listing);
	}

	/**
	 * Cancel a listing via its sale id
	 * 
	 * @param c
	 * @param saleId
	 */

	public static void cancelListing(Player c, int slot, int itemId) {
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if (slot < 0 || itemId < 0)
			return;
		PlayerListings listing = getListingForPlayer(c.getName().toLowerCase());
		
		if(listing == null)
			return;
		ListedItem sale = listing.getBySlot(slot);

		if(sale == null || sale.soldAll())
			return;

		int leftOver = sale.getRemaining();
		
			if (leftOver > 0) {
				
				int counterPartId = World.getWorld().getItemHandler().getCounterpartOrSelf(sale.getId());
				if(!ItemUtility.itemIsNote[counterPartId] && !ItemUtility.itemStackable[counterPartId]) {
					if(c.getItems().freeSlots() < leftOver) {
						c.sendMessage("You don't have enough inventory space to do that!");
						return;
					}
					sale.setSold(sale.getAmount());
					IntStream.range(0, leftOver).forEach(id ->  c.getItems().addItem(counterPartId, 1));
				} else {
					if(!c.getItems().playerHasItem(counterPartId) && c.getItems().freeSlots() < 1) {
						c.sendMessage("You don't have enough inventory space to do that!");
						return;
					}
					sale.setSold(sale.getAmount());
					c.getItems().addItem(counterPartId, leftOver);
				}
				
				
			}
			updateHistory(c, sale.getId(), sale.getAmount() - leftOver, sale.getPricePer());
			//listing.removeBySlot(slot);
			if(sale.getPendingCash() == 0) {
				listing.remove(sale);
			}
			sendSidebar(c);
			c.getItems().resetItems(3214);
			loadPlayersListings(c);
			PlayerSave.save(c);
			save(listing);
		
	}

	/**
	 * Collecting your money via the button
	 * 
	 * @param c
	 */

	public static void collectMoney(Player c) {
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if(c.getItems().getItemAmount(995) <= 0 && c.getItems().freeSlots() < 1) {
			c.sendMessage("You don't have enough inventory space to do that!");
			return;
		}
		PlayerListings listing = getListingForPlayer(c.getName().toLowerCase());
		int moneyCollectable = 0;
		for (ListedItem sale : Lists.newArrayList(listing.getItemsForSale())) {
			moneyCollectable += sale.getPendingCash();
			sale.resetPendingCash();
			if(sale.soldAll()) {
				listing.remove(sale);
			}
		}
		save(listing);
		if(moneyCollectable == 0) {
			c.sendMessage("[@red@Trading Post@bla@] You don't have any coins to collect.");
		} else {
			c.getItems().addItem(995, moneyCollectable);
			c.sendMessage("[@red@Trading Post@bla@] You successfully collect " + Misc.format(moneyCollectable)
					+ " coins from your coffer.");
		}
	
		moneyCollectable = 0;
		c.getPA().sendFrame126(Misc.format(moneyCollectable) + " GP", 48610);
		c.getItems().resetItems(3214);
		PlayerSave.save(c);
		loadPlayersListings(c);
	}


	/**
	 * Displays the 6 sales based on pages and item name/player name and recent
	 * 
	 * @param sales
	 * @param c
	 */

	public static void displayResults(List<ListedItem> sales, Player c) {
		int total = 0, skipped = 0, start = 48022;
		for (ListedItem sale : sales) {
			if (sale.soldAll()) {
				skipped++;
				continue;
			}
			
			if (skipped < (c.pageId - 1) * 6) {
				skipped++;
				continue;
			}
			c.getPA().sendTradingPost(48021, sale.getId(), total, 1);
			c.getPA().sendFrame126(ItemAssistant.getItemName(sale.getId()), start);
			start++;
			String each = sale.getPricePer() < 1000 ? "gp each" : " each";
			c.getPA().sendFrame126(Misc.format(sale.getPricePer()) + each, start);
			start++;
			c.getPA().sendFrame126(Misc.capitalize(sale.getSeller()), start);
			start++;
			c.getPA().sendFrame126((sale.getAmount() - sale.getSold()) + "/" + sale.getAmount(), start);
			start++;
			total++;
			if (total == 6) {
				// System.out.println("Reached 6 recent sales");
				break;
			}
		}
		for (int k = total; k < 6; k++) {
			c.getPA().sendTradingPost(48021, -1, k, -1);
		}
		for (int i = start; i < 48046; i++) {
			c.getPA().sendFrame126("", i);
		}
	}

	/**
	 * Loads the recent sales
	 * 
	 * @param c
	 */

	public static void loadRecent(Player c, boolean firstOpen) {
		if(firstOpen) {
			c.pageId = 1;
			c.searchId = 3;
		}
		c.saleResults = filteredList(c, getRecentListings());
		c.getPA().sendFrame126("Trading Post - Recent listings", 48019);
		c.getPA().showInterface(48000);
		displayResults(c.saleResults, c);
	}

	public static void buyListing(Player c, int slot, int amount) {
		if(!tradingPostEnabled) {
			c.sendMessage("Trading post is currently disabled!");
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		
		if (!c.getMode().isTradingPermitted(null, null)) {
			c.sendMessage("You are not permitted to make use of this.");
			return;
		}
		if(c.debugMessage)
			c.sendMessage("Slot: " + (((c.pageId - 1) * 6) + slot));
		ListedItem listing = c.saleResults.get(((c.pageId - 1) * 6) + slot);
		if(listing == null || listing.soldAll()) {
			c.sendMessage("That item has been sold or cancelled!");
			
			if(c.searchId == 1) {
				Listing.loadItemName(c, "", false);
			} else if(c.searchId == 2) {
				Listing.loadPlayerName(c, "", false);
			} else if(c.searchId == 3) {
				loadRecent(c, false);
			}
			return;
		}


		if (listing.getSeller().equalsIgnoreCase(c.playerName)) {
			c.sendMessage("[@red@Trading Post@bla@] You cannot buy your own listings.");
			return;
		}

		if (amount > listing.getRemaining())
			amount = listing.getRemaining();

		if (!c.getItems().playerHasItem(995, listing.getPricePer() * amount)) {
			c.sendMessage("[@red@Trading Post@bla@] You need atleast " + Misc.format(listing.getPricePer() * amount)
					+ " coins to buy the " + amount + "x " + ItemAssistant.getItemName(listing.getId()) + ".");
			return;
		}
		int slotsNeeded = amount;

		int saleItem = listing.getId();

		saleItem = World.getWorld().getItemHandler().getCounterpartOrSelf(saleItem);
		
		
		boolean stackable = ItemUtility.itemStackable[saleItem] || ItemUtility.itemIsNote[saleItem];
		
		if(stackable)
			slotsNeeded = 1;

		if (c.getItems().freeSlots() < slotsNeeded) {
			if(!stackable || (stackable && !c.getItems().playerHasItem(saleItem))) {
				c.sendMessage("[@red@Trading Post@bla@] You need atleast " + slotsNeeded + " free slots to buy this.");
				return;
			}
		}
		if (listing.soldAll()) { // This is the fix so players cannot purchase items that were cancelled
			c.sendMessage("This item is no longer available.");

			if(c.searchId == 1) {
				Listing.loadItemName(c, "", false);
			} else if(c.searchId == 2) {
				Listing.loadPlayerName(c, "", false);
			} else if(c.searchId == 3) {
				loadRecent(c, true);
			}

			return;
		}
		listing.sellItem(amount);
		c.getItems().deleteItem(995, listing.getPricePer() * amount);
		
		c.getItems().addItem(saleItem, amount);
		c.sendMessage("[@red@Trading Post@bla@] You succesfully purchase " + amount + "x "
				+ ItemAssistant.getItemName(saleItem) + ".");
		sendSidebar(c);
		c.getItems().resetItems(3214);
		PlayerSave.save(c);

		if(c.searchId == 1) {
			Listing.loadItemName(c, "", false);
		} else if(c.searchId == 2) {
			Listing.loadPlayerName(c, "", false);
		} else if(c.searchId == 3) {
			loadRecent(c, false);
		}



		Optional<Player> sellerOpt = PlayerHandler.getOptionalPlayer(listing.getSeller());
		sellerOpt.ifPresent(seller -> {
			if (listing.getSold() < listing.getAmount())
				seller.sendMessage("[@red@Trading Post@bla@] Sold " + listing.getSold() + "/" + listing.getAmount() + " "+ ItemAssistant.getItemName(listing.getId()) + ".");
			else
				seller.sendMessage("[@red@Trading Post@bla@] Finished selling your "
						+ ItemAssistant.getItemName(listing.getId()) + ".");
	
			PlayerSave.save(seller);
			if (seller.insidePost) {
				loadPlayersListings(seller);
			}
		});

		save(sales.get(listing.getSeller()));
				
	}

	/**
	 * Loads the sales via playerName
	 * 
	 * @param c
	 * @param playerName
	 */

	public static void loadPlayerName(Player c, String playerName, boolean firstOpen) {
		if(firstOpen) {
			c.lookup = playerName.toLowerCase();
			c.searchId = 2;
			c.pageId = 1;
		}
		c.saleResults = filteredList(c, getSalesForPlayer(c.lookup));
		c.getPA().showInterface(48000);
		c.getPA().sendFrame126("Trading Post - Searching for player: " + c.lookup.replace("_", " "), 48019);
		displayResults(c.saleResults, c);
	}
	
	public static List<ListedItem> filteredList(Player player, List<ListedItem> fullList){
	
		return fullList
				.stream()
				.filter(item -> !item.soldAll())
				.filter(item -> !item.getSeller().equalsIgnoreCase(player.getName()))
				.sorted((i1, i2) -> (int) (i2.getListedAt() - i1.getListedAt() >= 0 ? 1 : -1))
				.collect(Collectors.toList());
	}
	

	/**
	 * Loads the sales via itemName
	 * 
	 * @param c
	 * @param itemName
	 */

	public static void loadItemName(Player c, String itemName, boolean firstOpen) {
		if(firstOpen) {
			itemName = itemName.replace("_", " ");
			c.lookup = itemName;
			c.searchId = 1;
			c.pageId = 1;
		}
		c.saleResults = filteredList(c, getSalesForName(c.lookup));
		c.getPA().showInterface(48000);
		c.getPA().sendFrame126("Trading Post - Searching for item: " + c.lookup, 48019);
		displayResults(c.saleResults , c);
	}
	/**
	 * Resets all the necessary stuff;
	 * 
	 * @param c
	 */

	public static void resetEverything(Player c) {
		c.inSelecting = false;
		c.isListing = false;
		c.insidePost = false;
		c.setSidebarInterface(3, 3213);
	}

	/**
	 * Handles the opening of the interface for offering an item
	 * 
	 * @param c
	 */

	public static void openNewListing(Player c) {
		c.getPA().showInterface(48599);

		sendSidebar(c);
		c.isListing = true;
	}

	public static void sendSidebar(Player c) {
		c.setSidebarInterface(3, 48500); // backpack tab
		for (int k = 0; k < 28; k++) {
			c.getPA().sendTradingPost(48501, c.playerItems[k] - 1, k, c.playerItemsN[k]);
		}
	}

	/*
	 * 
	 * Handles the buttons of the interfaces
	 * 
	 */

	public static void postButtons(Player c, int button) {
		switch (button) {
		case 48621:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			int total = 0;
			List<ListedItem> sales = getSalesForName(c.playerName);

			for (@SuppressWarnings("unused")
			ListedItem sale : sales)
				total++;
			if (c.amDonated <= 9 && total >= 6) {
				c.sendMessage("[@red@Trading Post@bla@] You cannot have more then 6 listings as a regular player.");
				return;
			} else if (c.amDonated >= 10 && c.amDonated <= 149 && total >= 10) {
				c.sendMessage("[@red@Trading Post@bla@] You cannot have more then 10 listings as a low tier donator.");
				return;
			} else if (c.amDonated >= 150 && total >= 15) {
				c.sendMessage("[@red@Trading Post@bla@] You cannot have more then 15 listings.");
				return;
			}
			if (!c.inSelecting) {
				openNewListing(c);
				c.inSelecting = true;
				c.getPA().sendFrame106(3);
				sendSidebar(c);
			} else {
				resetEverything(c);
				c.getPA().showInterface(48600);
				sendSidebar(c);
				c.getPA().sendFrame106(3);
			}
			break;

		case 48002:
		case 15333:
			c.getPA().closeAllWindows();
			resetEverything(c);
			break;

		case 48968:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			synchronized (c) {
				c.outStream.writePacketHeader(191);
			}
			c.xInterfaceId = 191072;
			break;

		case 48971:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			synchronized (c) {
				c.outStream.writePacketHeader(192);
			}
			c.xInterfaceId = 191075;
			break;

		case 48974:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			confirmListing(c);
			break;

		case 48607:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			collectMoney(c);
			break;

		case 48618:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			loadRecent(c, true);
			break;

		case 48005:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			openPost(c, false, false);
			break;

		case 48008:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			if (c.pageId > 1)
				c.pageId--;
			 System.out.println("id: "+c.searchId+" pageId: " + c.pageId);
			switch (c.searchId) {
			case 1:
				loadItemName(c, c.lookup, false);
				break;
			case 2:
				loadPlayerName(c, c.lookup, false);
				break;
			case 3:
				loadRecent(c, false);
				break;
			}
			break;

		case 48011:
			if (!c.getMode().isTradingPermitted(null, null)) {
				c.sendMessage("You are not permitted to make use of this.");
				return;
			}
			c.pageId++;
			 System.out.println("id: "+c.searchId+" pageId: " + c.pageId);
			switch (c.searchId) {
			case 1:
				loadItemName(c, c.lookup, false);
				break;
			case 2:
				loadPlayerName(c, c.lookup, false);
				break;
			case 3:
				loadRecent(c, false);
				break;
			}
			break;

		}
	}

	/*
	 * 
	 * This method makes it so it cleans out the history and my offers. Incase you
	 * had a diffrent account with more listings.
	 * 
	 */

	public static void emptyInterface(Player c, boolean b) {
		for (int i = 0; i < 15; i++) {
			c.getPA().sendTradingPost(48847, -1, i, -1);
		}
		if (b) {
			for (int i = 48636; i < 48656; i++) {
				c.getPA().sendFrame126("", i);
			}
		}
		for (int i = 48787; i < 48847; i++) {
			c.getPA().sendFrame126("", i);
		}
	}

	/*
	 * 
	 * Turns the 100,000,000 into 100m etc.
	 * 
	 */

	private static String zerosintomills(int j) {
		if (j >= 0 && j < 1000)
			return String.valueOf(j);
		if (j >= 1000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j < 2147483647)
			return j / 1000000 + "M";
		return String.valueOf(j);
	}

	private static void updateHistory(Player c, int itemId, int amount, int price) {
		// System.out.println("itemId - " + itemId);
		// System.out.println("amount - " + amount);
		// System.out.println("price - " + price);
		c.saleItems.add(0, itemId);
		c.saleItems.remove(c.saleItems.size() - 1);
		c.saleAmount.add(0, amount);
		c.saleAmount.remove(c.saleAmount.size() - 1);
		c.salePrice.add(0, price);
		c.salePrice.remove(c.salePrice.size() - 1);
		loadHistory(c);
	}

}