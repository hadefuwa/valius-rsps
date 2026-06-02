package valius.model.entity.player.commands.helper;

import java.util.Optional;

import valius.Config;
import valius.ServerState;
import valius.event.CycleEventHandler;
import valius.model.entity.player.ConnectedFrom;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Forces a given player to log out.
 * 
 * @author Emiel
 */
public class Kick extends Command {

	@Override
	public void execute(Player c, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();
			if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				c.sendMessage("The player is in a trade, or duel. You cannot do this at this time.");
				return;
			}
			c2.outStream.writePacketHeader(109);
			CycleEventHandler.getSingleton().stopEvents(c2);
			c2.properLogout = true;			
			c2.disconnected = true;
			c2.logoutDelay = Long.MAX_VALUE;
			ConnectedFrom.addConnectedFrom(c2, c2.connectedFrom);
			c.sendMessage("Kicked " + c2.playerName);
			if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
				// TODO: Log handling
			}
		} else {
			c.sendMessage(input + " is not online. You can only kick online players.");
		}
	}
}
