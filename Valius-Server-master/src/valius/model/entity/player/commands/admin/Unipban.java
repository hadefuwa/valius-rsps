package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.world.World;

public class Unipban extends Command {

	@Override
	public void execute(Player c, String input) {
		if (input.isEmpty()) {
			c.sendMessage("You must enter a valid IP address.");
			return;
		}
		String[] args = input.split("-");
		String ipToUnban = args[0];
		
		Punishments punishments = World.getWorld().getPunishments();
		Punishment punishment = punishments.getPunishment(PunishmentType.NET_BAN, ipToUnban);

		if (!punishments.contains(PunishmentType.NET_BAN, ipToUnban) || punishment == null) {
			c.sendMessage("This IP address is not banned.");
			return;
		}

		punishments.remove(punishment);
		c.sendMessage("The IP '" + input + "' has been removed from the IP ban list.");
	}

}
