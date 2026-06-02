package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Opens the vote page in the default web browser.
 * 
 * @author Emiel
 */
public class Starterguide extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendString("https://valius.net/community/index.php?/topic/783-starter-guide-npc-information/", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Sends you to the Starter Guide!");
	}

}
