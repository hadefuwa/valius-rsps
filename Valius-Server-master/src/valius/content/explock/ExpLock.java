package valius.content.explock;

import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

public class ExpLock {

	public Player player;

	public ExpLock(Player player) {
		this.player = player;
	}

	public void OpenExpLock() { // Refreshes all text lines before showing the interface - Looks better
		for (int j = 0; j < 7; j++) {
			if (player.skillLock[j]) {
				player.getPA().sendFrame126("@red@Locked", 37536 + j); //Locked skill update text
				player.getPA().sendFrame126("@red@"+ player.getSkills().getActualLevel(Skill.forId(j)) +"", 37544 + j); // Update skill level text
			} else {
				player.getPA().sendFrame126("@gre@Unlocked", 37536 + j); //Locked skill update text
				player.getPA().sendFrame126("@gre@"+ player.getSkills().getActualLevel(Skill.forId(j)) +"", 37544 + j); // Update skill level text
			}
		}

		player.getPA().showInterface(37500);
	}

	public void ToggleLock(int i) { // Refreshes all text lines before showing the interface - Looks better
		if (!player.skillLock[i]) {
			player.skillLock[i] = true;
			player.getPA().sendFrame126("@red@Locked", 37536 + i); //Locked skill update text
			player.getPA().sendFrame126("@red@"+player.getSkills().getActualLevel(Skill.forId(i)) +"", 37544 + i); // Update skill level text
		} else {
			player.skillLock[i] = false;
			player.getPA().sendFrame126("@gre@Unlocked", 37536 + i); //Locked skill update text
			player.getPA().sendFrame126("@gre@"+ player.getSkills().getActualLevel(Skill.forId(i)) +"", 37544 + i); // Update skill level text
		}
	}

	public boolean ExpLockClicking(Player c, int id) {
		switch (id) {
		case 37511:
			ToggleLock(0);
			return true;
		case 37512:
			ToggleLock(1);
			return true;
		case 37513:
			ToggleLock(2);
			return true;
		case 37514:
			ToggleLock(3);
			return true;
		case 37515:
			ToggleLock(4);
			return true;
		case 37516:
			ToggleLock(5);
			return true;
		case 37517:
			ToggleLock(6);
			return true;
		case 37502:
			player.getPA().closeAllWindows();
			return true;

		}
		return false;
	}

}
