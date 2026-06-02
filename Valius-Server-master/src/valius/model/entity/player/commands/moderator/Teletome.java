package valius.model.entity.player.commands.moderator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.Command;

/**
 * Teleport a given player to the player who issued the command.
 * 
 * @author Emiel
 */
public class Teletome extends Command {

	@Override
	public void execute(Player c, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();
			if (!c.getRights().isOrInherits(Right.ADMINISTRATOR)) {
				if (c2.inClanWars() || c2.inClanWarsSafe()) {
					c.sendMessage("@cr10@This player is currently at the pk district.");
					return;
				}
			}
			c2.getPA().movePlayer(c.getX(), c.getY(), c.getHeight());
			c2.isStuck = false;
			c2.sendMessage("You have been teleported to " + c.playerName + "");
		} else {
			c.sendMessage(input + " is offline. You can only teleport online players.");
		}
	}
}