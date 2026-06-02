package valius.model.entity.player.commands.owner;

import valius.Server;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Serverstatus extends Command {

	@Override
	public void execute(Player c, String input) {
		System.out.println(Server.getStatus());
		c.sendMessage("See console for server status information.");
	}

}
