/**
 * 
 */
package valius.content.gauntlet.dialogues;

import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.CloseDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;

/**
 * @author ReverendDread
 * Aug 24, 2019
 */
public class ToolStorage {

	private static DialogueChain chain = DialogueChain.builder().
		add(0, OptionDialogue.builder().title("Choose a tool.")
				.option("Crystal sceptre", plr -> { return 1; })
				.option("Crystal axe", plr -> { return 2; })
				.option("Crystal pickaxe", plr -> { return 3; })
				.option("Crystal harpoon", plr -> { return 4; })
				.option("Pestle and mortar", plr -> { return 5; })
		)
		.add(1, CloseDialogue.builder().onDialogueOpen(plr -> {
			plr.getItems().addItem(23861, 1);
		}))
		.add(2, CloseDialogue.builder().onDialogueOpen(plr -> {
			plr.getItems().addItem(23862, 1);
		}))
		.add(3, CloseDialogue.builder().onDialogueOpen(plr -> {
			plr.getItems().addItem(23863, 1);
		}))
		.add(4, CloseDialogue.builder().onDialogueOpen(plr -> {
			plr.getItems().addItem(23864, 1);
		}))
		.add(5, CloseDialogue.builder().onDialogueOpen(plr -> {
			plr.getItems().addItem(23865, 1);
		}))
		.build();
	
	public static void open(Player player) {
		chain.open(player);
	}
	
}
