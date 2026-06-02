package valius.model.entity.player.commands.owner;

import java.util.Arrays;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;

/**
 * Spawn a specific Object.
 * 
 * @author Emiel
 *
 */
public class Object extends Command {

	@Override
	public void execute(Player c, String input) {
		String[] args = input.split(" ");
		if (args.length < 2) {
			Arrays.stream(PlayerHandler.players).forEach(p -> {
				if (p != null) {
					p.getPA().object(Integer.parseInt(args[0]), c.getX(), c.getY(), 0, 10);
				}
			});
			c.sendMessage("Object: " + Integer.parseInt(args[0]) + ", Type: 10");
		} else if(args.length < 3){
			Arrays.stream(PlayerHandler.players).forEach(p -> {
				if (p != null) {
					p.getPA().object(Integer.parseInt(args[0]), c.getX(), c.getY(), Integer.parseInt(args[1]), 10);
				}
			});
			c.sendMessage("Object: " + Integer.parseInt(args[0]) + ", Type: 10");
		}  else {
			int type = Integer.parseInt(args[2]);
			Arrays.stream(PlayerHandler.players).forEach(p -> {
				if (p != null) {
					p.getPA().object(Integer.parseInt(args[0]), c.getX(), c.getY(), Integer.parseInt(args[1]), type);
				}
			});
			c.sendMessage("Object: " + Integer.parseInt(args[0]) + ", Type: " + type);
		}
	}
}
