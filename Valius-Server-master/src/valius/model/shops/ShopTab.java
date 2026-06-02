package valius.model.shops;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemList;
import valius.model.items.ItemUtility;
import valius.model.shops.condition.ShopBuyCondition;
import valius.model.shops.condition.ShopDisplayCondition;
import valius.model.shops.condition.UseShopCondition;
import valius.util.Misc;
import valius.util.ShopTypeAdapter;
import valius.world.World;

@Getter
@Slf4j
public class ShopTab {
	
	
	public static void loadShops() {
		activeTabs.clear();
		Gson gson = new Gson();
		File shopJsonFolder = new File("./data/json/shops/");
		
		for(File jsonFile : shopJsonFolder.listFiles()) {
			try(FileReader fr = new FileReader(jsonFile)){
	    		Type listType = new TypeToken<List<ShopTab>>() {}.getType();
	    		List<ShopTab> shopTabs = gson.fromJson(fr, listType);
	    		shopTabs.stream().forEach(shop -> {
	    			shop.resetItems();
	    			if(activeTabs.stream().filter(otherShop -> otherShop.getId() == shop.getId()).findAny().isPresent()){
	    				log.warn("Duplicate shop id: {} ", shop.getId());
	    			}
	    			activeTabs.add(shop);
	    			shop.defaultItems.addAll(shop.items.stream().map(item -> item.copy()).collect(Collectors.toList()));
	    			
	    		});
	    		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("Error parsing {}", jsonFile);
			}
		}
		log.info("Loaded {} shops", activeTabs.size());
	}
	
	public static void toJson() {
		File shopJsonFolder = new File("./data/json/shops/");
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ShopItem.class, new ShopTypeAdapter()).create();
		for(TabbedShop shop : TabbedShop.values()) {
			try(FileWriter fw = new FileWriter(new File(shopJsonFolder, shop.name().toLowerCase() + ".json"))) {
				List<ShopTab> shopTabs = shop.getShops();
				gson.toJson(shopTabs, fw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	private void resetItems() {
		for(ShopItem item : items) {
			if(item == null) {
				System.out.println(this.getName() + " has null item");
			}
			item.setCurrentAmount(item.getDefaultAmount());
		}
	}

	@Getter
	private static List<ShopTab> activeTabs = Lists.newArrayList();
	
	public ShopTab() {
		
	}
	
	public static Optional<ShopTab> getShopTab(int shopId) {
		return activeTabs.stream().filter(tab -> tab.id == shopId).findFirst();
	}
	
	private int id;
	private List<ShopItem> items = Lists.newArrayList();
	private List<ShopItem> defaultItems = Lists.newArrayList();
	private String name;
	private double sellModifier, buyModifier;
	private boolean canSellTo;
	private Currency currency = Currency.COINS;
	private boolean generalStore;
	@Setter
	private boolean forceUpdate;
	
	public boolean restore() {
		
		boolean updated = false;
		for(ShopItem item : Lists.newArrayList(items)) {
			if(item.getCurrentAmount() != item.getDefaultAmount()) {
				item.setCurrentAmount(item.getCurrentAmount() + (item.getDefaultAmount() > item.getCurrentAmount() ? 1 : -1));
				if(item.getDefaultAmount() == 0 && item.getCurrentAmount() == 0) {
					items.remove(item);
				}
				updated = true;
			}
		}
		return forceUpdate || updated;
		
	}
	
	private List<ShopItem> filtered(Player player){
		return items.stream().filter(item -> ShopDisplayCondition.shouldDisplay(player, item.getId())).collect(Collectors.toList());
	}

	public void sendItems(Player player, int index) {
		List<ShopItem> items = filtered(player);
		player.getPA().sendString(name, 64210 + index);
		player.getOutStream().createFrameVarSizeWord(53);
		player.getOutStream().writeWord(TabbedShop.BASE_TAB_INVENTORY + index);//TODO get tab inv id
		player.getOutStream().writeWord(items.size());
		for(ShopItem item : items) {
			player.getOutStream().writeByte(item.getFlag());
			if (item.getCurrentAmount() > 254) {
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord_v2(item.getCurrentAmount());
			} else {
				player.getOutStream().writeByte(item.getCurrentAmount());
			}
			player.getOutStream().writeWordBigEndianA(item.getId() + 1);
		}
		player.getOutStream().endFrameVarSizeWord();
		player.flushOutStream();
	}

	public boolean hasItem(int itemID) {
		return items.stream().anyMatch(item -> item.getId() == itemID);
	}
	
	public int getExistingIndex(int itemId) {
		return IntStream.range(0, items.size()).filter(id -> items.get(id).getId() == itemId).findFirst().orElse(-1);
	}
	
	public void buyItem(Player player, int slot, int amount) {
		ShopItem item = items.get(slot);
		if(item == null)
			return;
		
		boolean canBuyItem = ShopBuyCondition.canBuy(player, item.getId());
		
		if(!canBuyItem) {
			return;
		}

		if(item.getCurrentAmount() == 0)
			return;
		
		int freeSlots = player.getItems().freeSlots();
		boolean stackable = ItemUtility.itemStackable[item.getId()] || ItemUtility.itemIsNote[item.getId()];
		int existingAmount = player.getItems().itemAmount(item.getId());
		boolean hasItem = existingAmount > 0;
		
		if(amount > item.getCurrentAmount())
			amount = item.getCurrentAmount();
		
		if(!stackable) {
			if(freeSlots < 1) {
				player.sendMessage("Not enough space in your inventory.");
				return;
			}
			if(amount > freeSlots)
				amount = freeSlots;
		} else if(!hasItem && freeSlots < 1) {
			player.sendMessage("Not enough space in your inventory.");
			return;
		}
		
		if(existingAmount + amount > Integer.MAX_VALUE)
			amount = Integer.MAX_VALUE - existingAmount;
		
		
		int cost = (int) (player.getMode().getModifiedShopPrice(id, item.getId(), item.getCost()) * this.getBuyModifier());

		
		int playerCurrency = currency.getPossible(player, cost);
		
		if(amount > playerCurrency) {
			amount = playerCurrency / cost;
		}
		
		if(amount <= 0) {
			player.sendMessage("You do not have enough " + currency.toPlural(2) + " to buy this item. " + playerCurrency);
			return;
		}

		boolean success = currency.removeAmount(player, item.getId(), amount, cost);
		if(success) {
			player.getItems().addItem(item.getId(), amount);
			item.setCurrentAmount(item.getCurrentAmount() - amount);
	
			sendItems(player, player.getShops().getActiveShopTab());
			player.getItems().resetItems(3823);
			if(currency != Currency.COINS) {
				int amountLeft = currency.getLeft(player);
				if(amountLeft >= 0) {
					player.sendMessage("You have " + amountLeft + " " + currency.toPlural(amountLeft) + " left.");
				}
			}
		} else {
		}
	}

	public int getItemAtSlot(Player player, int slot) {
		List<ShopItem> items = filtered(player);
		if(slot < 0 || slot >= items.size())
			return 0;
		return items.get(slot).getId();
	}
	
	public String checkSellPrice(Player player, int itemId) {
		if(!canSellTo) {
			return "You can't sell items to this store.";
		}
		
		int existingItemIndex = this.getExistingIndex(itemId);
		if(!generalStore && existingItemIndex < 0) {
			return "You can't sell that item to this store.";
		} else {
			ShopItem item;
			if(existingItemIndex >= 0) {
				item = this.items.get(existingItemIndex);
			} else {
				item = new ShopItem(itemId, 1);
			}
			ItemList itemList = World.getWorld().getItemHandler().getItemList(item.getId());
			int cost = (int) (item.getCost() * this.getSellModifier());
			String toKorM = Misc.format(cost) + " (" + (cost >= 1000000 ? Misc.to1Decimal((cost / 1000000.0)) + "M" : Misc.to1Decimal((cost / 1000.0)) + "K") + ")";
			return "@red@" + (itemList != null ? itemList.itemName : "null" ) + "</col>: shop will buy for @blu@"  + (cost >= 1000 ? toKorM : cost) + "</col> " + currency.toPlural(cost);
		}
	
	}
	
	public String checkPrice(Player player, int slot) {
		List<ShopItem> items = filtered(player);
		if(slot < 0 || slot >= items.size())
			return "That is not a valid item";
		ShopItem item = items.get(slot);
		int cost = (int) (item.getCost() * this.getBuyModifier());
		String toKorM = Misc.format(cost) + " (" + (cost >= 1000000 ? Misc.to1Decimal((cost / 1000000.0)) + "M" : Misc.to1Decimal((cost / 1000.0)) + "K") + ")";
		return "@red@" + ItemAssistant.getItemName(item.getId()) + "</col> currently costs @blu@" + (item.getCost() >= 1000 ? toKorM : item.getCost()) + "</col> " + currency.toPlural(item.getCost()) + ".";
	}

	public void sellItem(Player player, int itemId, int amount) {
		
		if(!canSellTo) {
			player.sendMessage("You can't sell anything to this store!");
			return;
		}
		
		boolean canSellAny = UseShopCondition.valid(player, this);
		
		if(!canSellAny) {
			return;
		}
		if(!hasItem(itemId) && !generalStore) {
			player.sendMessage("You can't sell that to this store!");
			return;
		}
		
		
		
		if(currency == Currency.COINS) {
			int hasAmount = player.getItems().getItemAmount(itemId);
			
			if(hasAmount < amount)
				amount = hasAmount;
			
			int freeSlots = player.getItems().freeSlots();
			boolean hasItem = player.getItems().itemAmount(996) > 0;
			
			if(freeSlots < 1 && !hasItem) {
				player.sendMessage("Not enough space in your inventory.");
				return;
			}

			ItemList itemList = World.getWorld().getItemHandler().ItemList[itemId];
			int cost = (int) (itemList == null ? 1 : itemList.ShopValue);
	
			int totalCost = (int) ((amount * cost) * this.getSellModifier());
	
			player.getItems().deleteItem2(itemId, amount);
			player.getItems().addItem(995, totalCost);
			ShopItem item = new ShopItem(itemId, 0);
			int index = getExistingIndex(itemId);
			if(index != -1) {
				item = items.get(index);
			}
			item.setCurrentAmount(item.getCurrentAmount() + amount);
			
			if(index != -1) {
				items.set(index, item);
			} else {
				items.add(item);
			}
	
			sendItems(player, player.getShops().getActiveShopTab());
			player.getItems().resetItems(3823);
		}
	}

	public void reset() {
		items.clear();
		items.addAll(defaultItems.stream().map(item -> item.copy()).collect(Collectors.toList()));
	}
}
