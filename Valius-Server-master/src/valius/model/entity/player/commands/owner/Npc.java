package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Spawn a specific Npc.
 * 
 * @author Emiel
 *
 */
public class Npc extends Command {

	@Override
	public void execute(Player c, String input) {
		int newNPC = Integer.parseInt(input);
		if (newNPC > 0) {
			//World.getWorld().getNpcHandler().newNPC(newNPC, c.getX(), c.getY(), c.getHeight(), 0, 0, 0, 0, 0);
			World.getWorld().getNpcHandler().spawnNpc3(c, newNPC, c.getX(), c.getY(), c.getHeight(), 0, 2000, 7, 70, 70, false, false, false);
			//NPCHandler.spawn(newNPC, c.getX(),c.getY(), c.getHeight(), 1, 120, 20, 100, 100, true);
			c.sendMessage("You spawn a Npc.");
		} else {
			c.sendMessage("No such NPC.");
		}
	}
}
