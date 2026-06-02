/**
 * 
 */
package valius.model.entity.player.commands.admin;

import valius.model.entity.npc.combat.CombatScriptHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * @author ReverendDread
 * Mar 9, 2019
 */
public class Reload extends Command {

	/* (non-Javadoc)
	 * @see ethos.model.entity.player.commands.Command#execute(ethos.model.entity.player.Player, java.lang.String)
	 */
	@Override
	public void execute(Player player, String input) {
		CombatScriptHandler.getScripts().clear();
		CombatScriptHandler.init();
		player.sendMessage("Scripts reloaded.");
	}

}
