/**
 * 
 */
package valius.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemCombination;

/**
 * @author ReverendDread
 * Apr 6, 2019
 */
public class OdiumWard extends ItemCombination {

	public OdiumWard(Item outcome, Optional<List<Item>> revertedItems, Item[] items) {
		super(outcome, revertedItems, items);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ethos.model.items.ItemCombination#combine(ethos.model.entity.player.Player)
	 */
	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(super.outcome.getId(), super.outcome.getAmount());
		player.getDH().sendItemStatement("You combined the items and created the Odium ward.", outcome.getId());
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	/* (non-Javadoc)
	 * @see ethos.model.items.ItemCombination#showDialogue(ethos.model.entity.player.Player)
	 */
	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Are you sure you'd like to combine the shards?", "This cannot be reversed!");
	}

}
