package valius.model.entity.player.commands.admin;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Toggletob extends Command {

	@Override
	public void execute(Player player, String input) {
		Config.theatreDisabled = !Config.theatreDisabled;
		player.sendMessage("ToB toggled " + (!Config.theatreDisabled ? "on" : "off"));
	}

}
