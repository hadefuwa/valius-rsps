package valius.model.entity.player.commands.admin;

import valius.content.tradingpost.Listing;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Toggletp extends Command {

	@Override
	public void execute(Player player, String input) {
		Listing.tradingPostEnabled = !Listing.tradingPostEnabled;
		player.sendMessage("Trading post " + (Listing.tradingPostEnabled ? "@gre@enabled" : "@red@disabled"));
	}

}
