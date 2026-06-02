package valius.model.entity.player;

public class PlayerAction {

	Player c;

	public PlayerAction(Player c) {
		this.c = c;
	}

	private boolean inAction = false;
	private boolean canWalk = true;
	private boolean canEat = true;

	public boolean setAction(boolean action) {
		return inAction = action;
	}

	public boolean checkAction() {
		return inAction;
	}

	public void canWalk(boolean walk) {
		canWalk = walk;
	}

	public boolean canWalk() {
		return canWalk;
	}

	public boolean canEat(boolean eat) {
		return canEat = eat;
	}

	public boolean checkEating() {
		return canEat;
	}
}
