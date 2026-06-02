package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.npc.drops.DropManager;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Droprate extends Command {

    @Override
    public void execute(Player player, String input) {
        player.forcedChat("My drop rate bonus is : " +DropManager.getModifier1(player) + "%.");
    }

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Displays your current drop rate");
	}
}


