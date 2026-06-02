package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

public class Droptable extends Command {

	@Override
	public void execute(Player c, String input) {
//		if (Config.SERVER_STATE != ServerState.PRIVATE || Config.SERVER_STATE != ServerState.PUBLIC_SECONDARY) {
//			c.sendMessage("You can only do this on the private server, not on the local server.");
//			return;
//		}
		c.getBank().getCurrentBankTab().getItems().clear();
		int npcId = Integer.parseInt(input.split("-")[0]);
		int amount = Integer.parseInt(input.split("-")[1]);
		World.getWorld().getDropManager().test(c, npcId, amount);
	}

}
