package valius.model.entity.player.commands.helper;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.world.World;

/**
 * Forces a given player to log out.
 * 
 * @author Emiel
 */
public class Mute extends Command {

	@SuppressWarnings("unused")
	@Override
	public void execute(Player c, String input) {
		try {
			String[] args = input.split("-");
			if (args.length != 3) {
				throw new IllegalArgumentException();
			}
			String name = args[0];
			int duration = Integer.parseInt(args[1]);
			long muteEnd;
			if (duration == 0) {
				muteEnd = Long.MAX_VALUE;
			} else {
				muteEnd = System.currentTimeMillis() + duration * 1000 * 60;
			}
			String reason = args[2];

			Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(name);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				c2.muteEnd = muteEnd;
				Punishment punishment = new Punishment(PunishmentType.MUTE, muteEnd, c2.playerName);
				World.getWorld().getPunishments().add(punishment);
				if (duration == 0) {
					c2.sendMessage("@red@You have been permanently muted by: " + c.playerName + ".");
					c.sendMessage("Successfully permanently " + c2.playerName + " for " + duration + " minutes.");
					// TODO: Log handling
				} else {
					c2.sendMessage("@red@You have been muted by: " + c.playerName + " for " + duration + " minutes");
					c.sendMessage("Successfully muted " + c2.playerName + " for " + duration + " minutes.");
					// TODO: Log handling
				}
			} else {
				c.sendMessage(name + " is not online. You can only mute online players.");
			}
		} catch (Exception e) {
			c.sendMessage("Error. Correct syntax: ::mute-player-duration-reason.");
		}
	}
}
