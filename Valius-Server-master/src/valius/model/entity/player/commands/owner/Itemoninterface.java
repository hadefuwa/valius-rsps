package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Open a specific interface.
 * 
 * @author Emiel
 *
 */
public class Itemoninterface extends Command {

	@Override
	public void execute(Player c, String input) {
		try {
			int a = Integer.parseInt(input);
			//c.getPA().showInterface(a);
			c.getPA().itemOnInterface(a, 1, 64503, 0);
			c.getPA().showInterface(64500);
		} catch (Exception e) {
			c.sendMessage("::interface ####");
		}
	}
}
