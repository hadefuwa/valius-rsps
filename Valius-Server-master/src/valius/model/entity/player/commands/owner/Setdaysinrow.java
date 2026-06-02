package valius.model.entity.player.commands.owner;

import com.google.common.primitives.Ints;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;
import valius.world.World;

public class Setdaysinrow extends Command {

	@Override
	public void execute(Player c, String input) {
		String[] args = input.split(" ");
		
		if (args.length >= 1) {
			c.sendMessage(input.replace(args[0], "").trim());
			int day = Ints.tryParse(args[0]);
			Player player = PlayerHandler.getPlayer(input.replace(args[0], "").trim());
			if(player != null) {
				player.sendMessage("Your daily rewards has been set to " + day);
				player.dailyRewardCombo = day;
				c.sendMessage("Set " + player.playerName + " to " + day);
			}
		}
		
	}

}
