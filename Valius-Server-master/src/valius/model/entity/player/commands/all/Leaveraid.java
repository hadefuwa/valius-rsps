package valius.model.entity.player.commands.all;

import valius.model.Location;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.minigames.raids.Raids;

public class Leaveraid extends Command {

	@Override
	public void execute(Player player, String input) {
		Raids raidInstance = player.getRaidsInstance();
		if(raidInstance != null) {
			player.sendMessage("@blu@You are now leaving the raid...");
			raidInstance.leaveGame(player);
		} else {
			player.sendMessage("@red@You need to be in a raid to do this...");
		}
	}

}
