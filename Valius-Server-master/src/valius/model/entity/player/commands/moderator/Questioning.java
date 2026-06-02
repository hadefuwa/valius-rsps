package valius.model.entity.player.commands.moderator;

import java.util.Optional;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;

public class Questioning extends Command {

	@Override
	public void execute(Player player, String input) {
		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		if (optionalPlayer.isPresent()) {
			Player chosen_player = optionalPlayer.get();
			chosen_player.setX(1952);
			chosen_player.setY(4764);
			chosen_player.setHeight(1);
			chosen_player.setNeedsPlacement(true);
			player.setX(1952);
			player.setY(4768);
			player.setHeight(1);
				
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (player.disconnected) {
							container.stop();
							return;
						}
						player.turnPlayerTo(1952, 4764);
						chosen_player.turnPlayerTo(1952, 4768);
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, 3);
				
			chosen_player.isStuck = false;
			player.sendMessage("You have moved " + chosen_player.playerName + " to questioning.");
			chosen_player.sendMessage(player.playerName + " has moved you to questioning.");
		} else {
			player.sendMessage(input + " is offline. You can only teleport online players.");
		}
	}
}