package valius.model.entity.player.commands.moderator;

import valius.model.entity.player.Player;
import valius.model.entity.player.combat.pkdistrict.District;
import valius.model.entity.player.commands.Command;

public class Pk extends Command {

	@Override
	public void execute(Player player, String input) {
		switch (input) {
		case "":
			player.sendMessage("@red@Usage: ::pk start, end or check");
			break;
		
		case "start":
			District.stage(player, "start");
			break;
			
		case "end":
			District.stage(player, "end");
			break;
			
		case "check":
			for (int i = 0; i < 6; i++) {
				player.sendMessage("Checking stat "+i+": "+player.playerStats[i]+"");
			}
			break;
		}
	}

}
