package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.content.dailytasks.DailyTasks;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.util.Misc;

public class Dailytask extends Command {
	
	@Override
	public void execute(Player player, String input) {
		if (player.completedDailyTask == false) {
			if(player.currentTask == null)
				DailyTasks.assignTask(player);
			player.sendMessage("@blu@@cr10@ Your current daily task is: "+(player.currentTask.amount - player.totalDailyDone)+" " + Misc.capitalize(player.currentTask.name().toLowerCase().replace("_", " ")));
		} else
			player.sendMessage("@blu@@cr10@ You have already completed your daily task!");
	}
	@Override
	public Optional<String> getDescription() {
		return Optional.of("Checks your active daily task");
	}
}
