package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.minigames.xeric.Xeric;
import valius.model.minigames.xeric.XericLobby;

/**
 * Kill a player.
 * 
 * @author Emiel
 */
public class Togglexeric extends Command {

	@Override
	public void execute(Player c, String input) {
		if (XericLobby.xericEnabled) {
			XericLobby.xericEnabled = false;
			c.sendMessage("Trials of Xeric is now DISABLED!");
		} else {
			XericLobby.xericEnabled = true;
			c.sendMessage("Trials of Xeric is now ENABLED!");
		}
		

	}
}
