package valius.model.entity.player.commands.all;

import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler.WildernessBossNpcs;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Wildyboss extends Command {
	@Override
	public void execute(Player player, String input) {
		if (WildernessBossHandler.getCurrentLocation() != null) {
			player.getPA().spellTeleport(WildernessBossHandler.getCurrentLocation().getX() + 3, WildernessBossHandler.getCurrentLocation().getY() + 3, 0, false);
			player.sendMessage("You have been teleported to " + WildernessBossNpcs.getRandom().getBossName() + ".");
		} else {
			player.sendMessage("@red@[Wilderness Boss] @bla@No Wilderness boss is currently in active.");
		}
		
	}

}