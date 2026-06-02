package valius.model.shops;

import java.util.Optional;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;
import valius.Config;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemList;
import valius.model.items.ItemUtility;
import valius.world.ShopHandler;
import valius.world.World;

public class ShopAssistant {

	@Getter
	private TabbedShop activeShop;
	@Setter	
	@Getter
	private int activeShopTab = 0;

	private Player c;

	public ShopAssistant(Player client) {
		this.c = client;
	}

	public boolean shopSellsItem(int itemID) {
		if(activeShop != null) {
			//c.sendMessage("tab shop sell check found");
			return activeShop.sellsItem(itemID, activeShopTab);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Shops
	 **/

	public void openShop(int ShopID) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (!c.getMode().isShopAccessible(ShopID)) {
			c.sendMessage("Your game mode does not permit you to access this shop.");
			c.getPA().closeAllWindows();
			return;
		}
		if (c.viewingLootBag || c.addingItemsToLootBag || c.viewingRunePouch) {
			c.sendMessage("You should stop what you are doing before opening a shop.");
			return;
		}
		c.nextChat = 0;
		c.dialogueOptions = 0;

		c.getItems().resetItems(3823);

		Optional<TabbedShop> tabbedShop = TabbedShop.getShopById(ShopID);
		if(tabbedShop.isPresent()) {
			c.getPA().sendFrame126(tabbedShop.get().getTitle(), 64105);
			c.getPA().sendFrame248(64100, 3822);

			activeShop = tabbedShop.get();
			activeShopTab = 0;
			resetShop(ShopID);
			c.isShopping = true;
			c.myShopId = ShopID;
			return;
		}
		activeShop = null;
		activeShopTab = 0;
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		if(activeShop != null) {
			activeShop.sendShops(c);
			return;
		}
	}

	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;

		ItemList itemList = World.getWorld().getItemHandler().ItemList[ItemID];

		if (itemList != null) {
			ShopValue = itemList.ShopValue;
		}

		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public static int getItemShopValue(int itemId) {
		if (itemId < 0) {
			return 0;
		}

		ItemList itemList = World.getWorld().getItemHandler().ItemList[itemId];

		if (itemList != null) {
			return (int) itemList.ShopValue;
		}

		return 0;
	}

	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		if(this.activeShop != null) {
			Optional<ShopTab> shopTabOpt = activeShop.getShop(activeShopTab);
			shopTabOpt.ifPresent(shopTab -> c.sendMessage(shopTab.checkPrice(c, removeSlot)));
			return;
		}
	}

	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		boolean CANNOT_SELL = IntStream.of(Config.ITEM_SELLABLE).anyMatch(sellable -> sellable == removeId);
		if (c.myShopId != 116 && c.myShopId != 115) {
			if (CANNOT_SELL) {
				c.sendMessage("You can't sell " + ItemAssistant.getItemName(removeId).toLowerCase() + ".");
				return;
			}
		}
		if(this.activeShop != null) {
			Optional<ShopTab> shopTabOpt = activeShop.getShop(activeShopTab);
			shopTabOpt.ifPresent(shopTab -> c.sendMessage(shopTab.checkSellPrice(c, removeId)));
			return;
		}
	}

	/**
	 * Selling items back to a store
	 * @param itemID	
	 * 					itemID that is being sold
	 * @param fromSlot
	 * 					fromSlot the item currently is located in
	 * @param amount
	 * 					amount that is being sold
	 * @return
	 * 					true is player is allowed to sell back to the store,
	 * 					else false
	 */
	public boolean sellItem(int itemID, int fromSlot, int amount) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return false;
		}
		if (!c.getMode().isItemSellable(c.myShopId, itemID)) {
			c.sendMessage("Your game mode does not permit you to sell this item to the shop.");
			return false;
		}
		if (itemID == 863 || itemID == 11230 || itemID == 869 || itemID == 868 || itemID == 867 || itemID == 866 || itemID == 4740 || itemID == 9244 || itemID == 11212
				|| itemID == 892 || itemID == 9194 || itemID == 9243 || itemID == 9242 || itemID == 9241 || itemID == 9240 || itemID == 9239 || itemID == 882 || itemID == 884
				|| itemID == 886 || itemID == 888 || itemID == 890 | itemID == 995) {
			c.sendMessage("You can't sell this item.");
			return false;
		}


		boolean CANNOT_SELL = IntStream.of(Config.ITEM_SELLABLE).anyMatch(sellable -> sellable == itemID);
		if (CANNOT_SELL) {
			c.sendMessage("You can't sell " + ItemAssistant.getItemName(itemID).toLowerCase() + ".");
			return false;
		}



		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if(activeShop != null) {
				activeShop.getShop(activeShopTab).ifPresent(activeShop -> activeShop.sellItem(c, itemID, amount));
				return true;
			}
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}

		if (ItemUtility.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	/**
	 * Buying item(s) from a store
	 * @param itemID
	 * 					itemID that the player is buying
	 * @param fromSlot
	 * 					fromSlot the items is currently located in
	 * @param amount
	 * 					amount of items the player is buying
	 * @param j 
	 * @return
	 * 					true if the player is allowed to buy the item(s),
	 * 					else false
	 */
	public boolean buyItem(int interfaceId, int itemID, int fromSlot, int amount) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			if (c.debugMessage)
				c.sendMessage("rekt1");
			return false;
		}
		if (!c.getMode().isItemPurchasable(c.myShopId, itemID)) {
			c.sendMessage("Your game mode does not allow you to buy this item.");
			return false;
		}

		if (!shopSellsItem(itemID))
			return false;

		if (amount > 0) {
			if(activeShop != null) {
				activeShop.buyItem(c, fromSlot, amount);
				checkAchievements(itemID, c);
				return true;
			}
			return true;
		}
		return false;
	}
	
	private void checkAchievements(int item, Player player) {
		switch (item) {
			case 7462:
				player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PURCHASE_BARROW_GLOVES);
				break;
		}
			
	}

}
