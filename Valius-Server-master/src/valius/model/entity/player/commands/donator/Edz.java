package valius.model.entity.player.commands.donator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Teleports the player to the Extreme donator zone.
 * 
 * @author Divine
 */
public class Edz extends Command {

	@Override
	public void execute(Player c, String input) {
		if (c.inTrade || c.inDuel || c.inWild() || c.amDonated < 100) {
			c.sendMessage("You cannot use this teleport.");
			return;
		}
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.sendMessage("@cr10@This player is currently at the pk district.");
			return;
		}
		if ((c.amDonated >= 100) || (!c.inWild()) || (!c.inTrade) || (!c.inGodwars())) {
		c.getPA().spellTeleport(3259, 2784, 0, false);
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to elite donator zone");
	}

}

