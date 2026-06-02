package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Empty the inventory of the player.
 * 
 * @author Emiel
 */
public class Empty extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().removeAllItems();
		c.sendMessage("You empty your inventory.");
	}
}
