package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Coords extends Command {

	@Override
	public void execute(Player c, String input) {
		c.sendMessage("Your coordinates are: " + c.getX() + ", " + c.getY() + " " + c.getHeight() + "");
	}

}
