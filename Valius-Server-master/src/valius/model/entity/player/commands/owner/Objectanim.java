package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * Spawn a specific Object.
 * 
 * @author Emiel
 *
 */
public class Objectanim extends Command {

	@Override
	public void execute(Player c, String input) {
		String[] args = input.split(" ");
			World.getWorld().getGlobalObjects().add(new GlobalObject(Integer.parseInt(args[0]), c.getX(), c.getY(), c.getHeight(), 0, 10, -1, -1)); 
			c.getPA().sendPlayerObjectAnimation(c, c.getX(), c.getY(), Integer.parseInt(args[1]), 10, 0, c.getHeight());
	}
}
