package valius.model.entity.player.commands.owner;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.model.multiplayer_session.MultiplayerSession;
import valius.world.World;

public class Removetrade extends Command {

	@Override
	public void execute(Player player, String input) {
		Optional<Player> otherPlr = PlayerHandler.getOptionalPlayer(input.toLowerCase().replace("::removetrade", "").trim());
		if(otherPlr.isPresent()) {
			MultiplayerSession sess = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(otherPlr.get());
			if(sess != null) {
				World.getWorld().getMultiplayerSessionListener().remove(sess);
				player.sendMessage("Closed session");
			} else {
				player.sendMessage("no sess found");
			}
		} else {
			player.sendMessage("No player found");
		}
	}

}
