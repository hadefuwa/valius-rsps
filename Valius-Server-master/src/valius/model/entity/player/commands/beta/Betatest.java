package valius.model.entity.player.commands.beta;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Betatest extends Command {

	@Override
	public void execute(Player player, String input) {
		player.sendMessage("Correct!");
	}

}
