package valius.model.entity.player.commands.admin;

import java.util.Optional;

import valius.content.cannon.CannonManager;
import valius.content.cannon.DwarfCannon;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;

public class Resetcannon extends Command {

	@Override
	public void execute(Player player, String input) {
		Optional<Player> plrOpt = PlayerHandler.getOptionalPlayer(input.toLowerCase().trim());
		if(!plrOpt.isPresent()) {
			player.sendMessage("Player " + input + " is not online");
			return;
		}
		
		plrOpt.ifPresent(otherPlr -> {
			otherPlr.cannon = new DwarfCannon(otherPlr);
			CannonManager.register(otherPlr, otherPlr.cannon);
			player.sendMessage("Reset cannon for " + input);
			otherPlr.sendMessage("Your cannon has been reset!");
		});
	}

}
