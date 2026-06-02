package valius.content.quest.dialogue;

import valius.model.entity.player.Player;

public abstract class Dialogue {
	
	protected int nextDialogue = -1;
	protected transient DialogueChain chain;
	
	public void apply(Player player) {
		player.setActiveDialogue(this);
	}
	
	public Dialogue nextDialogue(int nextDialogueId) {
		this.nextDialogue = nextDialogueId;
		return this;
	}
	
	public void moveForward(Player player) {
		chain.get(nextDialogue).apply(player);
	}

}
