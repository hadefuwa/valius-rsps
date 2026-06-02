package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Force the player to perform a given emote.
 * @author Emiel
 * 
 * And log if args extend to 2.
 * @author Matt
 *
 */
public class Emote extends Command {

	@Override
	public void execute(Player c, String input) {
		String[] args = input.split("-");
		if (args.length != 2) {
			c.startAnimation(Integer.parseInt(args[0]));
			c.getPA().requestUpdates();
			c.sendMessage("Performing emote: " + Integer.parseInt(args[0]));
			c.emote = Integer.parseInt(args[0]);
		} else {
			
			c.startAnimation(Integer.parseInt(args[0]));
			c.getPA().requestUpdates();
			c.sendMessage("Performing emote: " + Integer.parseInt(args[0]));
			c.sendMessage("Logging info: Emote: "+ args[0] +" Description: "+ args[1]);
			c.getPA().logEmote(Integer.parseInt(args[0]), args[1]);
		}
	}
}
