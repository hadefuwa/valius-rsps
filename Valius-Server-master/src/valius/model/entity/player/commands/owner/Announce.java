package valius.model.entity.player.commands.owner;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
/**
 * debug global message system.
 * 
 * @author Patrity
 */
public class Announce extends Command{

	@Override
	public void execute(Player c, String input) {
		String [] args = input.split("-");
		if (args.length != 2 ) {
			c.sendMessage("Usage: ::announce-[MESSAGE]-[TYPE]");
			c.sendMessage("Types: NEWS, LOOT, EVENT, DONATION, NONE");
			return;
		}
		if (args[1].equalsIgnoreCase("news"))
			GlobalMessages.send(args[0], GlobalMessages.MessageType.NEWS);
		else if (args[1].equalsIgnoreCase("loot"))
			GlobalMessages.send(args[0], GlobalMessages.MessageType.LOOT);
		else if (args[1].equalsIgnoreCase("event"))
			GlobalMessages.send(args[0], GlobalMessages.MessageType.EVENT);
		else if (args[1].equalsIgnoreCase("donation"))
			GlobalMessages.send(args[0], GlobalMessages.MessageType.DONATION);
		else if (args[1].equalsIgnoreCase("none"))
			GlobalMessages.send(args[0], GlobalMessages.MessageType.NONE);
		else
			c.sendMessage("::announce Failed");
	}

}
