package valius.model.items.item_combinations;

import java.util.List;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.items.ItemCombination;

public class InfernalAxe extends ItemCombination {

	public InfernalAxe(Item outcome, Optional<List<Item>> revertedItems, Item[] items) {
		super(outcome, revertedItems, items);
	}

	@Override
	public void combine(Player player) {
		if (player.getSkills().getLevel(Skill.FIREMAKING) < 85) {
			player.sendMessage("You must have a firemaking level of at least 85 to do this.");
			return;
		}
		items.forEach(item -> player.getItems().deleteItem2(item.getId(), item.getAmount()));
		player.getItems().addItem(outcome.getId(), outcome.getAmount());
		//emote 4512
		player.startAnimation(4512);
		player.getDH().sendItemStatement("You combined the items and created an infernal pickaxe.", outcome.getId());
		player.setCurrentCombination(Optional.empty());
		player.nextChat = -1;
	}

	@Override
	public void showDialogue(Player player) {
		player.getDH().sendStatement("Combining these are final.", "You cannot revert this.");
	}

}
