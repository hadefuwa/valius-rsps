/**
 * 
 */
package valius.model.entity.player.commands.owner;

import valius.content.falling_stars.FallingStars;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * @author Patrity
 *
 */
public class Star extends Command {

	/* (non-Javadoc)
	 * @see ethos.model.entity.player.commands.Command#execute(ethos.model.entity.player.Player, java.lang.String)
	 */
	@Override
	public void execute(Player player, String input) {
		player.sendMessage(input);
		if(input.startsWith("destroy")) {
			FallingStars.destroyStar();
			return;
		}
		//FallingStars.newStar();
		FallingStars.initialSpawn();
		//FallingStars.getCycleEventContainer().getEvent().execute(FallingStars.getCycleEventContainer());
		
	}

}
