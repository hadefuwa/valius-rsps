package valius.model.entity.player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import valius.model.items.Item;

public class Equipment {

	/**
	 * A mapping for items worn by a player for their each respective equipment slot.
	 */
	private Map<Slot, Item> equipment = new HashMap<>();

	/**
	 * Creates a new {@link Equipment} object with the given information. Currently, this is information created from the player upon initialization.
	 * 
	 * @param equipment an array of item id values and item amount values.
	 */
	public Equipment(Item[] equipment) {
		for (int index = 0; index < equipment.length; index++) {
			Slot slot = Slot.valueOf(index);
			if (slot != null) {
				this.equipment.put(slot, equipment[index]);
			}
		}
	}

	/**
	 * Updates the equipment slot for the given item. If the mapping does not contain the slot, the new value is inserted. If the key exists, then the item is updated. This is
	 * principle of the put function.
	 * 
	 * @param slot the equipment slot for the item.
	 * @param item the new item to be updated.
	 */
	public void update(Slot slot, Item item) {
		equipment.put(slot, item);
	}

	/**
	 * Retains the item for the given slot.
	 * 
	 * @param slot the equipment slot we get the game item from.
	 * @return the item for the slot or a NullPointerException if no item exists for that slot.
	 */
	public Item getItem(Slot slot) {
		if (!equipment.containsKey(slot)) {
			return new Item(-1, 0);
		}
		return equipment.get(slot);
	}
	
	public Item getWeapon() {
		return getItem(Slot.WEAPON);
	}

	/**
	 * Determines if the player is wearing any items for the given slot.
	 * 
	 * @param slot the slot where the item should be worn.
	 * @param items an array of items the player must be wearing one of.
	 * @return {@code} true if the player is wearing at least one of the items.
	 */
	public boolean wearingAny(Slot slot, Item... items) {
		
		return equipment.values().stream().anyMatch(equippedItem -> {
			for(Item item : items) {
				if(item.getId() == equippedItem.getId())
					return true;
			}
			return false;
		});
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		equipment.entrySet().forEach(e -> {
			Item item = e.getValue();
			if (item != null) {
				sb.append(item.getId() + ", " + item.getAmount() + "\n");
			}
		});
		return sb.toString();
	}

	public enum Slot {
		HELMET(0), CAPE(1), AMULET(2), WEAPON(3), CHEST(4), SHIELD(5), LEGS(7), HANDS(9), FEET(10), RING(12), AMMO(13);

		private final int slot;

		private Slot(int slot) {
			this.slot = slot;
		}

		public int getSlot() {
			return slot;
		}

		public static final Slot valueOf(int slot) throws NullPointerException {
			return Stream.of(values()).filter(s -> s.slot == slot).findFirst().orElse(null);
		}
	}
}
