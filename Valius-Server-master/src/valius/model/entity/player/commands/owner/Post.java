package valius.model.entity.player.commands.owner;

import valius.content.tradingpost.Listing;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Post extends Command {

	@Override
	public void execute(Player c, String input) {
		Listing.openPost(c, false, true);
	}
}
