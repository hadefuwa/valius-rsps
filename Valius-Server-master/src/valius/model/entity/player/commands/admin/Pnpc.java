package valius.model.entity.player.commands.admin;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Transform a given player into an npc.
 * 
 * @author Emiel
 *
 */
public class Pnpc extends Command {

	@Override
	public void execute(Player c, String input) {
		int npc = Integer.parseInt(input);
		
		if (npc > 10000) {
			c.sendMessage("Max npc id is: 10000");
			return;
		}
		if(npc == -1) {
			c.npcId2 = 0;
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		} else {
			c.npcId2 = npc;
			c.isNpc = true;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
	}
}
