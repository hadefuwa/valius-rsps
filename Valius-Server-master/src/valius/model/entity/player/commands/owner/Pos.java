package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Show the current position.
 * 
 * @author Emiel
 *
 */
public class Pos extends Command {

	@Override
	public void execute(Player player, String input) {
		player.sendMessage("Current coordinates x: " + player.getX() + " y:" + player.getY() + " h:" + player.getHeight());
	}
}
