package valius.content.quest.dialogue.impl;

import java.util.function.Consumer;

import valius.content.quest.dialogue.Dialogue;
import valius.model.entity.player.Player;

public class CloseDialogue extends Dialogue {

	private transient Consumer<Player> onOpenEvent;
	
	public CloseDialogue onDialogueOpen(Consumer<Player> event) {
		this.onOpenEvent = event;
		return this;
	}
	
	public static CloseDialogue builder() {
		return new CloseDialogue();
	}

	
	@Override
	public void apply(Player player) {
		if(onOpenEvent != null)
			onOpenEvent.accept(player);
		player.setActiveDialogue(null);
		player.getPA().closeAllWindows();
	}
}
