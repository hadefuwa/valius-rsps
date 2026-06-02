package valius.model.entity.player.commands.admin;

import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Starteventboss extends Command {

	@Override
	public void execute(Player player, String input) {
		EventBossHandler.getCycleEventContainer().getEvent().execute(EventBossHandler.getCycleEventContainer());
	}

}
