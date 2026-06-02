package valius.model.entity.player.commands.helper;
import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.world.World;

/**
 * Unmute a given player.
 * 
 * @author Emiel
 */
public class Unmute extends Command {

	@Override
	public void execute(Player c, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();

			Punishment punishment = World.getWorld().getPunishments().getPunishment(PunishmentType.MUTE, c2.playerName);

			if (punishment == null) {
				c.sendMessage("This player is not muted.");
				return;
			}

			World.getWorld().getPunishments().remove(punishment);
			c2.muteEnd = 0;
			c.sendMessage(c2.playerName + " has been unmuted.");
			c2.sendMessage("@red@You have been unmuted by " + c.playerName + ".");
			// TODO: Log handling
		}
	}
}
