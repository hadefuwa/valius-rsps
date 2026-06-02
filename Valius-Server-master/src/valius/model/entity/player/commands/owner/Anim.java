package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Anim extends Command {

	@Override
	public void execute(Player player, String input) {
		int id = Integer.parseInt(input);
		player.startAnimation(id);
	}

}
