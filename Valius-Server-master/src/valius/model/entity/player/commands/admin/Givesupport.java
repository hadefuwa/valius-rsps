package valius.model.entity.player.commands.admin;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.all.Commands;

public class Givesupport extends Commands {
	
	public void execute(Player c, String input) {
		String[] args = input.split("-");
		if (args.length != 1) {
			c.sendMessage("The correct format is '::givesupport-name'.");
			return;
		}
		Player player = PlayerHandler.getPlayer(args[0]);
		if (player == null) {
			c.sendMessage("The player '" + args[0] + "' could not be found, try again.");
			return;
		}
		if (player.getRights().isOrInherits(Right.HELPER)) {
			c.sendMessage("That player is already a member of the player support team.");
			return;
		}
		player.getRights().add(Right.HELPER);
		player.getRights().updatePrimary();
		c.sendMessage("You have promoted to Player Support.");
		GlobalMessages.send("" + args[0] + " has joined the Player Support team! Congratulations", GlobalMessages.MessageType.NEWS);
	}

}