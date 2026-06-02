package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens the help interface.
 * 
 * @author Emiel
 */
public class Bug extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().closeAllWindows();
		c.getPA().showInterface(59525);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Creates a bug ticket");
	}

}
