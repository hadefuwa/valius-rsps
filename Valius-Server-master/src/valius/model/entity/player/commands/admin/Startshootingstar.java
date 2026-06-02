package valius.model.entity.player.commands.admin;

import valius.content.falling_stars.FallingStars;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Startshootingstar extends Command {

	@Override
	public void execute(Player player, String input) {
		FallingStars.getCycleEventContainer().getEvent().execute(FallingStars.getCycleEventContainer());
	}
}
