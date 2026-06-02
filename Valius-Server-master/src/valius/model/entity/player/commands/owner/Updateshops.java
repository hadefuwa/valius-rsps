package valius.model.entity.player.commands.owner;


import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Update the shops.
 * 
 * @author Emiel
 *
 */
public class Updateshops extends Command {

	@Override
	public void execute(Player player, String input) {
		World.getWorld().reloadShops();
		GlobalMessages.send(player.playerName + " has made a live update to the shops!", GlobalMessages.MessageType.NEWS);
	}
}
