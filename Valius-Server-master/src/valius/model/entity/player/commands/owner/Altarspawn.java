package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Update the shops.
 * 
 * @author Emiel
 *
 */
public class Altarspawn extends Command {

	@Override
	public void execute(Player player, String input) {
		player.getSkotizo().skotizoSpecials();
	}
}
