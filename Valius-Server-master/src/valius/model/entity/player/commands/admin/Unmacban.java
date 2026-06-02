package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.world.World;

/**
 * Remove the ban on a specific Mac address.
 * 
 * @author Emiel
 */
public class Unmacban extends Command {

	@Override
	public void execute(Player c, String input) {
		try {
			Punishments punishments = World.getWorld().getPunishments();
			Punishment punishment = punishments.getPunishment(PunishmentType.MAC_BAN, input);

			if (!punishments.contains(PunishmentType.MAC_BAN, input) || punishment == null) {
				c.sendMessage("The address " + input + " does not exist in the list.");
				return;
			}

			punishments.remove(punishment);
			c.sendMessage("The mac ban on the address; " + input + " has been lifted.");
		} catch (IndexOutOfBoundsException exception) {
			c.sendMessage("Error. Correct syntax: ::unmacban address.");
		}
	}
}
