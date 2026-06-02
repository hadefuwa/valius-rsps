/**
 * 
 */
package valius.model.entity.player.commands.owner;

import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * @author ReverendDread
 * Mar 30, 2019
 */
public class Wildybossspawn extends Command {

	/* (non-Javadoc)
	 * @see ethos.model.entity.player.commands.Command#execute(ethos.model.entity.player.Player, java.lang.String)
	 */
	@Override
	public void execute(Player player, String input) {
		WildernessBossHandler.spawnBoss();
	}

}
