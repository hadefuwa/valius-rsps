package valius.model.items;

import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;

import valius.world.World;

/**
 * Represents an item that exists within the game. Items hold identification and value, and have the potential to contain other information as well.
 * 
 * @author Jason MacKeigan
 * @date Oct 20, 2014, 2:55:17 PM
 */
public class Item {

	private int id, amount, slot;

	private boolean stackable;

	/**
	 * Constructs a new game item with an id and amount of 0
	 * 
	 * @param id the id of the item
	 * @param amount the amount of the item
	 */
	public Item(int id) {
		this.id = id;
		this.amount = 1;
		if (ItemUtility.itemStackable[id]) {
			stackable = true;
		}
	}
	
	/**
	 * Constructs a new game item with an id and amount
	 * 
	 * @param id the id of the item
	 * @param amount the amount of the item
	 */
	public Item(int id, int amount) {
		this(id);
		this.amount = amount;
	}

	public Item(int id, int amount, int slot) {
		this(id, amount);
		this.amount = amount;
		this.slot = slot;
	}

	/**
	 * Attempts to return the same item with a randomized amount.
	 * 
	 * @return the same item with a randomized amount
	 */
	public Item randomizedAmount() {
		if (amount == 1) {
			return this;
		}

		return new Item(id, RandomUtils.nextInt(1, amount));
	}

	/**
	 * Returns a new GameItem object with a new id and amount
	 * 
	 * @param id the item id
	 * @param amount the item amount
	 * @return a new game item
	 */
	public Item set(int id, int amount) {
		return new Item(id, amount);
	}

	/**
	 * Retries the item id for the game item
	 * 
	 * @return the item id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void changeDrop(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	/**
	 * Retrieves the amount of the game item
	 * 
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of the game item
	 * 
	 * @param the amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * The slot the game item exists in the container
	 * 
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * Determines if the item is stackable
	 * 
	 * @return true if the item is stackable, false if it is not.
	 */
	public boolean isStackable() {
		return stackable;
	}
	
	public Optional<ItemList> getDefinition() {
		if(id < 0 || id > World.getWorld().getItemHandler().ItemList.length)
			return Optional.empty();
		return Optional.ofNullable(World.getWorld().getItemHandler().ItemList[id]);
	}
	
	public String getName() {
		String name = "null";
		Optional<ItemList> defOpt = getDefinition();
		if(defOpt.isPresent())
			name = defOpt.get().itemName;
		return name;
	}

	@Override
	public String toString() {
		return "GameItem [id=" + id + ", amount=" + amount + ", slot=" + slot + ", stackable=" + stackable + "]";
	}
	
	/** ITEM RARITY **/
	public ItemRarity rarity;

	public Item setRarity(ItemRarity rarity) {
		this.rarity = rarity;
		return this;
	}
	
	public ItemRarity getRarity() {
		return this.rarity;
	}

	public void incrementAmount(int amount) {
		this.amount += amount;
	}
}