package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens the experience lock interface
 * 
 * @author Tyler
 */
public class Explock extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getExpLock().OpenExpLock();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Interface to manage Exp Locks");
	}

}
