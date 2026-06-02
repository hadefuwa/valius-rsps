/**
 * 
 */
package valius.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemCombination;


public class RingOfFortune extends ItemCombination {

	public RingOfFortune(Item outcome, Optional<List<Item>> revertedItems, Item[] items) {
		super(outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendItemStatement("You combined the items and created the Ring of Fortune.", 33798);
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Once the Ring of Wealth is combined with the Gem of Fortune", "there is no going back. The items cannot be reverted.");
	

	}

}