package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * 
 * @author Divine | Jan. 12, 2019 , 1:54:41 a.m.
 *
 */
public class Dperks extends Command {

	@Override
	public void execute(Player c, String input) {
		c.getPA().sendString("https://valius.net/community/index.php?/topic/147-donator-ranks-perks/", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Invites you to our Discord server");
	}

}
