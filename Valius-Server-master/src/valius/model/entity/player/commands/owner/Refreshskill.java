package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Refreshskill extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().refreshSkill(Integer.parseInt(input));
	}

}
