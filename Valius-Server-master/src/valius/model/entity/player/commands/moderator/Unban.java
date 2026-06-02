package valius.model.entity.player.commands.moderator;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.world.World;

/**
 * Unbans a given player.
 * 
 * @author Emiel
 */
public class Unban extends Command {

	@Override
	public void execute(Player c, String input) {
		Punishments punishments = World.getWorld().getPunishments();
		Punishment punishment = punishments.getPunishment(PunishmentType.BAN, input);

		if (!punishments.contains(PunishmentType.BAN, input) || punishment == null) {
			c.sendMessage("A punishment could not be found for: " + input);
			return;
		}

		punishments.remove(punishment);
		c.sendMessage("You have successfully removed " + input + " from the ban list.");
		// TODO: Log handling
	}
}
