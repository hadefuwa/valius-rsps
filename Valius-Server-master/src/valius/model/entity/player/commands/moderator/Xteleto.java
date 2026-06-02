package valius.model.entity.player.commands.moderator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.Command;

/**
 * Teleport to a given player.
 * 
 * @author Emiel
 */
public class Xteleto extends Command {

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
			c.getPA().movePlayer(c2.getX(), c2.getY(), c2.getHeight());
		} else {
			c.sendMessage(input + " is not line. You can only teleport to online players.");
		}
	}
}
