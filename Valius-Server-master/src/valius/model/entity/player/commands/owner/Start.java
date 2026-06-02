package valius.model.entity.player.commands.owner;

import valius.content.events.WildernessEscape;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Give a certain amount of an item to a player.
 * 
 * @author Emiel
 */
public class Start extends Command {

	@Override
	public void execute(Player c, String input) {
		WildernessEscape WE = new WildernessEscape(c);
		WE.startEvent();
	}
}
