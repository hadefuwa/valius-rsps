package valius.model.entity.player.commands.admin;

import java.util.Arrays;
import java.util.Optional;

import valius.Config;
import valius.ServerState;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.world.World;

/**
 * Mac ban a player.
 * 
 * @author Emiel
 */
public class Macban extends Command {

	@Override
	public void execute(Player c, String input) {
		if (isMacAddress(input)) {
			if (c.debugMessage)
				c.sendMessage("made it here");
			banAddress(c, input);
		} else {
			if (c.debugMessage)
				c.sendMessage("made it here2");
			banPlayer(c, input);
		}

	}

	public void banPlayer(Player c, String input) {
		String[] split = input.split("-");
		if (split.length < 2) {
			c.sendMessage("To ban an online player you must give a valid reason of at least 2 characters long.");
			return;
		}
		String reason = split[1];
		if (reason.length() < 3) {
			c.sendMessage("Reason length must be at least 3 characters, it is currently " + reason.length() + " characters.");
			return;
		}
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(split[0]);
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();
			if (c2.getMacAddress().isEmpty()) {
				c.sendMessage("The players mac address is empty and therefore cannot be added to the list.");
				c.sendMessage("This happens when the client cannot determine the player address during login.");
				c.sendMessage("You are going to have to consider another possible means of action.");
				return;
			}
			Punishments punishments = World.getWorld().getPunishments();

			if (punishments.contains(PunishmentType.MAC_BAN, c2.getMacAddress())) {
				c.sendMessage("This player is already mac banned, they shouldn't be online.");
				c.sendMessage("Consider another possible means of action.");
				return;
			}
			if (c.debugMessage)
				c.sendMessage("Made it");
			punishments.add(new Punishment(PunishmentType.MAC_BAN, Long.MAX_VALUE, c2.getMacAddress()));
			punishments.add(new Punishment(PunishmentType.NET_BAN, Long.MAX_VALUE, c2.connectedFrom));
			punishments.add(new Punishment(PunishmentType.BAN, Long.MAX_VALUE, c2.playerName));
			c.sendMessage(c2.playerName + " has been mac banned with the address: " + c2.getMacAddress() + ".");
			c2.disconnected = true;
			if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
				// TODO: Log handling
			}
		} else {
			c.sendMessage(input + " is offline. Try '::macban macaddress' instead to ban offline players.");
		}
	}

	public void banAddress(Player c, String input) {
		World.getWorld().getPunishments().add(new Punishment(PunishmentType.MAC_BAN, Long.MAX_VALUE, input));
		c.sendMessage("Mac address " + input + " has been banned.");
	}

	public boolean isMacAddress(String input) {
		input = input.toUpperCase();
		if (input.length() < 17) {
			return false;
		}
		String[] splitup = input.substring(0, 17).split("-");
		if (splitup.length < 6) {
			return false;
		}
		return Arrays.stream(splitup).allMatch(s -> s.length() == 2);
	}
}
