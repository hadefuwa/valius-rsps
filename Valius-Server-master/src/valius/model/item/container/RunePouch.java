package valius.model.item.container;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.items.Item;

public class RunePouch extends AdditionalInventory {

	public static final int RUNE_POUCH_ID = 12791;
	private static final boolean CHECK_FOR_POUCH = true;

	private static final int START_RUNE_INVENTORY_INTERFACE = 29908;
	private static final int END_RUNE_INVENTORY_INTERFACE = 29910;
	
	private static final int START_BAG_INVENTORY_INTERFACE = 29880;
	private static final int END_BAG_INVENTORY_INTERFACE = 29907;
	

	private int enterAmountItem = -1;
	private int enterAmountInterface = -1;

	public RunePouch(Player player) {
		this.player = player;
	}

	public int[] runes = new int[] { 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566};

	public void onDeath(Player o, String entity) {
		if (o == null) {
			return;
		}
		handleDeath(player,entity);
		sendPouchRuneInventory();
		PlayerSave.saveGame(player);
	}


	public static boolean isRunePouch(Player player, int itemId) {
		return itemId == RUNE_POUCH_ID;
	}

	public boolean handleButton(int buttonId) {
		if (buttonId == 29877) {
			closePouchInterface();
			return true;
		}
		return false;
	}

	public void openRunePouch() {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return;
		}
		if (!configurationPermitted()) {
			player.sendMessage("You cannot do this right now.");
			return;
		}
		onClose();
		sendUpdates();
		player.getPA().showInterface(29875);
		player.viewingRunePouch = true;
	}

	public void emptyBagToInventory() {
		withdrawItems();
	}
	
	private boolean withdrawRunesFromBag(int id, int amount) {
		boolean hasSpace = player.getItems().freeSlots() > 0 || player.getItems().playerHasItem(id);
		if(!hasSpace) {
			player.sendMessage("You don't have enough space to do that!");
			return false;
		}
		int existingCount = getCountInBag(id);
		if (amount > existingCount) {
			amount = existingCount;
		}
		Optional<Item> itemOpt = getItemInBag(id);
		if(itemOpt.isPresent()) {
			Item item = itemOpt.get();
			item.incrementAmount(-amount);
			if(item.getAmount() <= 0)
				items.remove(item);
			player.getItems().addItem(item.getId(), amount);
			sendUpdates();
			return true;
		}
	
		return false;
	}
	
	private boolean removeRunesFromBag(int id, int amount) {
	
		int existingCount = getCountInBag(id);
		if (amount > existingCount) {
			amount = existingCount;
		}
		Optional<Item> item = getItemInBag(id);
		if(item.isPresent()) {
			item.get().incrementAmount(-amount);
			if(item.get().getAmount() <= 0)
				items.remove(item.get());
			sendPouchRuneInventory();
			return true;
		}
	
		return false;
	}
	

	public boolean finishEnterAmount(int amount) {
		return handleClickItem(this.enterAmountItem, amount, this.enterAmountInterface);
	}

	public boolean handleClickItem(int id, int amount, int interfaceId) {
		if (!player.viewingRunePouch) {
			return false;
		}
		if (!configurationPermitted()) {
			player.sendMessage("You cannot do this right now.");
			return false;
		}
		player.sendMessage("Add item " + id);
		if (interfaceId >= START_RUNE_INVENTORY_INTERFACE && interfaceId <= END_RUNE_INVENTORY_INTERFACE) {
			return withdrawRunesFromBag(id, amount);
		} else if(interfaceId >= START_BAG_INVENTORY_INTERFACE && interfaceId <= END_BAG_INVENTORY_INTERFACE) {
			addRunesFromInventory(id, amount);
			return true;
		}
		return false;
	}

	private Optional<Item> getItemInBag(int id) {
		return items.stream().filter(bagItem -> bagItem.getId() == id).findFirst();
	}
	
	private boolean pouchContainsItem(int id) {
		return getItemInBag(id).isPresent();
	}
	
	public int getCountInBag(int itemId) {
		Optional<Item> foundItem = getItemInBag(itemId);
		return foundItem.isPresent() ? foundItem.get().getAmount() : 0;
	}

	public boolean pouchContainsItem(int id, int amount) {
		return items.stream().anyMatch(bagItem -> bagItem.getId() == id && bagItem.getAmount() >= amount);
	}

	public boolean hasRunes(int runes, int amount) {
		if(!player.getItems().playerHasItem(RUNE_POUCH_ID)){
			return false;
		}
		return (pouchContainsItem(runes, amount));
	}

	public boolean hasRunes(int[] runes, int[] runeAmounts) {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return false;
		}
		for (int i = 0; i < runes.length; i++) {
			if (!pouchContainsItem(runes[i], runeAmounts[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean deleteRunesOnCast(int runes, int runeAmounts) {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return false;
		}
		if (!hasRunes(runes, runeAmounts)) {
			return false;
		}
		removeRunesFromBag(runes, runeAmounts);
		return true;
	}

	public boolean deleteRunesOnCast(int[] runes, int[] runeAmounts) {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return false;
		}
		if (!hasRunes(runes, runeAmounts)) {
			return false;
		}
		for (int i = 0; i < runes.length; i++) {
			removeRunesFromBag(runes[i], runeAmounts[i]);
		}
		return true;
	}

	public boolean addRunesFromInventory(int id, int amount) {
		if (id <= 0 || amount <= 0) {
			return false;
		}
		
		if (!configurationPermitted()) {
			player.sendMessage("You cannot do this right now.");
			return false;
		}
	
		if (id == RUNE_POUCH_ID) {
			player.sendMessage("Don't be silly.");
			return false;
		}
		if (!(id >= 554 && id <= 566) && id != 9075) {
			player.sendMessage("You can only store runes in a rune pouch.");
			return false;
		}
		if (items.size() >= 3 && !(pouchContainsItem(id))) {
			player.sendMessage("Your pouch cannot hold more than 3 different runes.");
			return false;
		}
		int inventoryCount = player.getItems().getItemCount(id);
		if (amount > inventoryCount) {
			amount = inventoryCount;
		}

		player.getItems().deleteItem(id, amount);
		
		Optional<Item> itemOpt = getItemInBag(id);
		
		if(itemOpt.isPresent()) {
			itemOpt.get().incrementAmount(amount);
		} else {
			Item item = new Item(id, amount);
			items.add(item);
		}
		

		sendUpdates();
		return true;
	}

	private void closePouchInterface() {
		onClose();
	}

	public void requestPouchInterface() {
		if (!configurationPermitted()) {
			player.sendMessage("You cannot do this right now.");
			return;
		}
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return;
		}
		openRunePouch();
	}

	private void onClose() {
		player.viewingRunePouch = false;
		player.getPA().closeAllWindows();
	}

	public void onLogin() {
		sendPouchRuneInventory();
	}

	private void sendPouchRuneInventory() {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return;
		}

		StringBuilder sendSpells =new StringBuilder("#");

		for (int i = 0; i < 3; i++) {
			int id = 0;
			int amt = 0;

			if (i < items.size()) {
				Item item = items.get(i);
				if (item != null) {
					id = item.getId();
					amt = item.getAmount();
				}
			}

			if (id <= 0) {
				id = -1;
			}
			player.getPA().sendFrame34a(START_RUNE_INVENTORY_INTERFACE + i, id, 0 , amt);
			//PlayerFunction.itemOnInterface(c, START_ITEM_INTERFACE + i, 0, id, amt);
			if (id == -1)
				id = 0;
			if (i == 2) {
				sendSpells.append(id).append(":").append(amt);
			} else {
				sendSpells.append(id).append(":").append(amt).append("-");
			}
		}
		sendSpells.append("$");
		player.getPA().sendFrame126(sendSpells.toString(), 49999);
	}

	private void sendRunePouchInventory() {
		if (!player.getItems().playerHasItem(RUNE_POUCH_ID) && CHECK_FOR_POUCH) {
			return;
		}
		for (int i = 0; i < player.playerItems.length; i++) {
			int itemId =  player.playerItems[i];
			int slotCount = player.playerItemsN[i];
			
			player.getPA().sendFrame34a(START_BAG_INVENTORY_INTERFACE + i, itemId - 1, 0 , slotCount);
		}
	}

	private void sendSidebarInventory() {
		player.getItems().resetItems(3214);
		player.getPA().requestUpdates();
	}
	
	private void sendUpdates() {
		sendSidebarInventory();
		sendRunePouchInventory();
		sendPouchRuneInventory();
	}
	
	public void setEnterAmountVariables(int itemId, int interfaceId) {
		this.enterAmountItem = itemId;
		this.enterAmountInterface = interfaceId;
	}
}
