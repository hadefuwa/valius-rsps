package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.world.World;

public class Unnetmute extends Command {

	@Override
	public void execute(Player c, String input) {
		try {
			String[] arguments = input.split("-");

			if (arguments.length < 2) {
				throw new IllegalArgumentException("Invalid arguments. Correct format; ::unnetmute-ip-reason");
			}
			final String ip = arguments[0];
			final String reason = arguments[1];

			if (reason.length() < 10) {
				throw new IllegalArgumentException("The reason must be at least 10 characters.");
			}

			Punishment punishment = World.getWorld().getPunishments().getPunishment(PunishmentType.NET_MUTE, ip);

			if (punishment == null) {
				c.sendMessage("A punishment cannot be found for this network IP: " + ip);
				return;
			}
			c.sendMessage("The IP '" + ip + "' has been removed from the list of muted networks.");
			World.getWorld().getPunishments().remove(punishment);
			// TODO: Log handling
		} catch (IllegalArgumentException iae) {
			c.sendMessage(iae.getMessage());
		}
	}

}
