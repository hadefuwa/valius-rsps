/**
 * 
 */
package valius.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemCombination;


public class ViggoraRed extends ItemCombination {

	public ViggoraRed(Item outcome, Optional<List<Item>> revertedItems, Item[] items) {
		super(outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		if (player.viggoraCharge < 2500) {
			player.sendMessage("You need a fully charged Viggoras chainmace to do this. (2500 Charges = Full)");
			return;
		}
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendItemStatement("You combined the items and created the Viggoras chainmace (Burning).", 33779);
		player.viggoraCharge = 0;
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Once the Shard is combined with the Viggoras chainmace", "there is no going back. The items cannot be reverted.");
	

	}

}