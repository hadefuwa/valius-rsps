package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Start the update timer and update the server.
 * 
 * @author Emiel
 *
 */
public class Resetstats extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getSkills().resetToActualLevels();
		c.getSkills().sendRefresh();
	}
}
