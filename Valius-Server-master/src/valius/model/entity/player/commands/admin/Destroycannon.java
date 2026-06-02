package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Destroycannon extends Command {

	@Override
	public void execute(Player player, String input) {
		player.cannon.destroy();
	}

}
