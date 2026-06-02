package valius.model.entity.player.commands.admin;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.entity.player.skills.Skill;

public class Hpray extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.inGodmode()) {
			c.getHealth().setMaximum(c.getSkills().getActualLevel(Skill.HITPOINTS));
			c.getHealth().reset();
			c.getSkills().setLevel(c.getSkills().getActualLevel(Skill.PRAYER), Skill.PRAYER);
			c.getPA().refreshSkill(Config.PRAYER);
			c.specAmount = 10.0;
			c.getPA().requestUpdates();
			c.setSafemode(false);
			c.setGodmode(false);
			c.sendMessage("Mode is now: Off");
		} else {
			c.getHealth().setMaximum(9999);
			c.getHealth().reset();
			c.getSkills().setLevel(9999, Skill.PRAYER);
			c.getPA().refreshSkill(Config.PRAYER);
			c.specAmount = 9999;
			c.getPA().requestUpdates();
			c.setSafemode(true);
			c.setGodmode(true);
			c.sendMessage("Mode is now: On");
		}
	}
}
