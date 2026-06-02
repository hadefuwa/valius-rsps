package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.model.entity.player.commands.Command;

public class Wildypursuit extends Command {

	@Override
	public void execute(Player c, String input) {
		MonsterHunt.spawnNPC();
	}

}

