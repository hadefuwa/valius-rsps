package valius.model.entity.player.commands.admin;

import com.google.common.primitives.Ints;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Regiontele extends Command {

	@Override
	public void execute(Player player, String input) {
		try {
			int regionId = Ints.tryParse(input.trim());
			int regionX = (regionId >> 8) & 0xFF;
			int regionY = regionId & 0xFF;
			player.getPA().movePlayerUnconditionally(regionX * 64, regionY * 64, player.getHeight());
		} catch (Exception e) {
			player.sendMessage("Correct format is ::regiontele regionId");
		}

	}

}
