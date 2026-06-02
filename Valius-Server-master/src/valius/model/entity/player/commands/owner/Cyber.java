package valius.model.entity.player.commands.owner;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Toggle the Cyber Monday sale on or off.
 * 
 * @author Emiel
 *
 */
public class Cyber extends Command {

	@Override
	public void execute(Player c, String input) {
		Config.CYBER_MONDAY = !Config.CYBER_MONDAY;
		String status = Config.CYBER_MONDAY ? "On" : "Off";
		c.sendMessage("Cyber monday: " + status);
	}
}
