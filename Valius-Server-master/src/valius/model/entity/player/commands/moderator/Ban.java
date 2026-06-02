package valius.model.entity.player.commands.moderator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.Command;
import valius.model.multiplayer_session.MultiplayerSession;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.world.World;

/**
 * Ban a given player.
 * 
 * @author Emiel
 */
public class Ban extends Command {

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
			String reason = args[2];
			long banEnd;
			if (duration == 0) {
				banEnd = Long.MAX_VALUE;
			} else {
				banEnd = System.currentTimeMillis() + duration * 1000 * 60;
			}
			Punishments punishments = World.getWorld().getPunishments();
			if (punishments.contains(PunishmentType.BAN, name)) {
				c.sendMessage("This player is already banned.");
				return;
			}
			World.getWorld().getPunishments().add(new Punishment(PunishmentType.BAN, banEnd, name));
			Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(name);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				if (!c.getRights().isOrInherits(Right.OWNER) && c2.getRights().isOrInherits(Right.ADMINISTRATOR)) {
					c.sendMessage("You cannot ban this player.");
					return;
				}
				if (World.getWorld().getMultiplayerSessionListener().inAnySession(c2)) {
					MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c2);
					session.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
				}
				c2.properLogout = true;
				c2.disconnected = true;

				return;
			}
			if (duration == 0) {
				c.sendMessage(name + " has been permanently banned.");
				// TODO: Log handling
			} else {
				c.sendMessage(name + " has been banned for " + duration + " minute(s).");
				// TODO: Log handling
			}
		} catch (Exception e) {
			c.sendMessage("Correct usage: ::ban-player-duration-reason (0 as duration for permanent)");
		}
	}
}
