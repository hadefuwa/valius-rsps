package valius.model.entity.player.commands.admin;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Togglehiscore extends Command {

	@Override
	public void execute(Player player, String input) {
		Config.enableHiscores = !Config.enableHiscores;
		player.sendMessage("Hiscores toggled " + (Config.enableHiscores ? "on" : "off"));
	}

}
