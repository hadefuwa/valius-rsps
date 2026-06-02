package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;
import valius.model.entity.player.commands.Command;

public class Saveall extends Command{

	@Override
	public void execute(Player player, String input) {
		player.sendMessage("Begin saving all");
		PlayerHandler.nonNullStream().forEach(player2 -> {
			if (PlayerSave.saveGame(player2)) {
				System.out.println("Game saved for player " + player2.playerName);
			} else {
				System.out.println("Could not save for " + player2.playerName);
			}
		});
	}

	
	
}
