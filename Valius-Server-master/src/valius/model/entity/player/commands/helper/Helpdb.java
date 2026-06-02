package valius.model.entity.player.commands.helper;

import valius.content.help.HelpDatabase;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens an interface containing all help tickets.
 * 
 * @author Emiel
 */
public class Helpdb extends Command {

	@Override
	public void execute(Player c, String input) {
		HelpDatabase.getDatabase().openDatabase(c);
	}
}
