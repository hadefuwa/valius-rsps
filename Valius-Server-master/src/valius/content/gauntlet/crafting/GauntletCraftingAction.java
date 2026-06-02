/**
 * 
 */
package valius.content.gauntlet.crafting;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import valius.event.Event;
import valius.model.entity.player.Player;
import valius.model.items.ItemUtility;

/**
 * @author ReverendDread
 * Aug 23, 2019
 */
@Slf4j
public class GauntletCraftingAction extends Event<Player> {

	/**
	 * The item being crafted.
	 */
	private GauntletCraftable craftable;
	private int amount;
	
	/**
	 * @param attachment
	 * @param ticks
	 */
	public GauntletCraftingAction(Player attachment, Optional<GauntletCraftables> craftable, int amount) {
		super("gauntlet_crafting", attachment, 1);
		this.craftable = craftable.orElse(null);
		this.amount = amount;
	}
	
	@Override
	public void update() {
		if (!check()) {
			stop();
			return;
		}
	}

	@Override
	public void execute() {
		if (!check()) {
			stop();
			return;
		}
		int equipSlot = -1;
		for (int[] item : craftable.getMaterials()) {
			if (attachment.getItems().isWearingItem(item[0])) {
				equipSlot = attachment.getItems().removeItemFromEquipment(item[0], item[1]);
			} else
				attachment.getItems().deleteItem2(item[0], item[1]);
		}
		
		if (equipSlot >= 0) {
			attachment.getItems().setEquipment(craftable.getProduct(), 1, equipSlot);
		} else 
			attachment.getItems().addItem(craftable.getProduct(), 1);
		attachment.sendMessage("You successfully make a " + ItemUtility.getItemName(craftable.getProduct()) + ".");
		amount--;
	}
	
	public boolean check() {
		if (craftable == null) {
			attachment.sendMessage("Unable to find craftable item.");
			return false;
		}
		if (amount <= 0) {
			return false;
		}
		if (attachment.getMovementQueue().hasSteps()) {
			return false;
		}
		if (!attachment.getItems().hasFreeSlots()) {
			attachment.sendMessage("You don't have enough inventory space to make that.");
			return false;
		}
		if (!attachment.getItems().containsItems(craftable.getMaterials())) {
			attachment.sendMessage("You don't have the required materials to craft that.");
			return false;
		}
		return true;
	}

}
