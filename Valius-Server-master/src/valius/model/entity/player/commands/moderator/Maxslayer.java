package valius.model.entity.player.commands.moderator;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;

public class Maxslayer extends Command {
	
	@Override
	public void execute(Player c, String input) {
		Optional<Player> op = PlayerHandler.nonNullStream().filter(Objects::nonNull).max(Comparator.comparing(client -> client.getSlayer().getPoints()));
		op.ifPresent(player -> c.sendMessage("Highest slayer points: "+player.playerName+" - "+player.getSlayer().getPoints()));
	}

}
