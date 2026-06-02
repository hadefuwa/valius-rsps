package valius.model.entity.player.commands.donator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens the vote page in the default web browser.
 *
 * @author Patrity 
 */
public class Tp extends Command {

	@Override
	public void execute(Player c, String input) {
		if ((c.amDonated < 25) || (c.inWild()) || (c.inTrade) || (c.inGodwars())) {
			c.sendMessage("Try using this in another area.");
			return;
		}
			c.getPA().showInterface(63000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens the teleport interface");
	}

}