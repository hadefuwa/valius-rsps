package valius.model.entity.player.commands.admin;

import valius.content.cannon.DwarfCannon;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Spawncannon extends Command {

	@Override
	public void execute(Player player, String input) {
		player.getItems().addItem(DwarfCannon.CANNON_BASE, 1);
		player.getItems().addItem(DwarfCannon.CANNON_STAND, 1);
		player.getItems().addItem(DwarfCannon.CANNON_BARRELS, 1);
		player.getItems().addItem(DwarfCannon.CANNON_FURNACE, 1);
	}

}
