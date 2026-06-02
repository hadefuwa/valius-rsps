package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.commands.Command;

/**
 * Kill a player.
 * 
 * @author Emiel
 */
public class Kill extends Command {

	@Override
	public void execute(Player c, String input) {
		Player player = PlayerHandler.getPlayer(input);
		if (!c.playerName.equalsIgnoreCase("ryan")) {
			return;
		}
		if (player == null) {
			c.sendMessage("Player is null.");
			return;
		}
		player.appendDamage(player.getHealth().getMaximum(), Hitmark.HIT);
		player.sendMessage("You have been merked");
	}
}
