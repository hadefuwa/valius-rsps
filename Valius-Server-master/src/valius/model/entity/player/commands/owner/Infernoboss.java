package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Infernoboss extends Command {

	@Override
	public void execute(Player c, String input) {
		c.createTzkalzukInstance();
		c.getInferno().initiateTzkalzuk();
	}

}

