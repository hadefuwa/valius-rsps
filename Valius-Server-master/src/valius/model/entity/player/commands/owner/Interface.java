package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Open a specific interface.
 * 
 * @author Emiel
 *
 */
public class Interface extends Command {

	@Override
	public void execute(Player c, String input) {
		try {
			int a = Integer.parseInt(input);
			c.getPA().showInterface(a);
		} catch (Exception e) {
			c.sendMessage("::interface ####");
		}
	}
}
