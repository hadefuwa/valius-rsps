package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens the vote page in the default web browser.
 * 
 * @author Emiel
 */
public class Discord extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendString("https://discord.gg/UQABRTC", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Invites you to our Discord server");
	}

}
