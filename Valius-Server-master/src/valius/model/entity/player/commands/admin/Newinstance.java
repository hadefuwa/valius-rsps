package valius.model.entity.player.commands.admin;

import valius.model.entity.instance.impl.HydraInstance;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Newinstance extends Command {

	@Override
	public void execute(Player player, String input) {
		HydraInstance instance = new HydraInstance();
		player.setInstance(instance);
	}

}
