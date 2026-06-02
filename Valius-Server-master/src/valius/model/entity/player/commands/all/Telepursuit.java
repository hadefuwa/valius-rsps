package valius.model.entity.player.commands.all;

import valius.model.entity.player.Player;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.model.entity.player.commands.Command;

public class Telepursuit extends Command {

	@Override
	public void execute(Player player, String input) {
		if(MonsterHunt.getCurrentLocation() != null) {
			player.getPA().spellTeleport(MonsterHunt.getCurrentLocation().getX(), MonsterHunt.getCurrentLocation().getY(), 0, false);
		} else {
			player.sendMessage("@red@[Wildy Pursuit] @bla@No monster is currently in pursuit.");
		}
	}

}
