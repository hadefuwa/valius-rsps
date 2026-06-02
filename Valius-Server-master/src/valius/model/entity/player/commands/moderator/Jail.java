package valius.model.entity.player.commands.moderator;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Jail a given player.
 * 
 * @author Emiel
 */
public class Jail extends Command {

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
			long jailEnd = 0;
			if (duration == 0) {
				jailEnd = Long.MAX_VALUE;
			} else {
				jailEnd = System.currentTimeMillis() + duration * 1000 * 60;
			}
			String reason = args[2];

			Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(name);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
					c.sendMessage("The player is in a trade, or duel. You cannot do this at this time.");
					return;
				}
				if(c.playerName == c2.playerName){
					c.sendMessage("You can not jail yourself.");
					return;
				}
				if (c2.inClanWars() || c2.inClanWarsSafe()) {
					c.sendMessage("@cr10@This player is currently at the pk district.");
					return;
				}
				c2.setX(1952);
				c2.setY(4466);
				c2.setHeight(0);
				c2.setNeedsPlacement(true);
				c2.jailEnd = jailEnd;
				if (duration == 0) {
					c2.sendMessage("@red@You have been permanently jailed by " + c.playerName + " .");
					c.sendMessage("Permanently jailed " + c2.playerName + ".");
					// TODO: Log handling

				} else {
					c2.sendMessage("@red@You have been jailed by " + c.playerName + " for " + duration + " minutes.");
					c2.sendMessage("@red@Type ::unjail after having served your time to be unjailed.");
					c.sendMessage("Successfully jailed " + c2.playerName + " for " + duration + " minutes.");
					// TODO: Log handling
				}
			} else {
				c.sendMessage(name + " is not online. You can only jail online players.");
			}
		} catch (Exception e) {
			c.sendMessage("Error. Correct syntax: ::jail-player-duration-reason");
		}
	}
}
