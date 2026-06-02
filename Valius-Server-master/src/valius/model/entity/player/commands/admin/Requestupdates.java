package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.entity.player.skills.Skill;

public class Requestupdates extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendFrame126("" + c.getSkills().getLevel(Skill.PRAYER) + "/" + c.getSkills().getActualLevel(Skill.PRAYER) + "", 687);// Prayer
	}

}
