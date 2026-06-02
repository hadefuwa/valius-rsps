package valius.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import valius.content.cluescroll.ClueScrollRiddle;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemCombination;
import valius.util.Misc;


public class MasterClueScroll extends ItemCombination {

	public MasterClueScroll(Item outcome, Optional<List<Item>> revertedItems, Item[] items) {
		super(outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		super.items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(Misc.randomElementOf(ClueScrollRiddle.MASTER_CLUES), 1);
		player.getDH().sendItemStatement("You combined the scroll pieces and make a Clue scroll (master).", 33985);
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Once the Bow is combined with the Dye", "there is no going back. The items cannot be reverted.");
	

	}

}