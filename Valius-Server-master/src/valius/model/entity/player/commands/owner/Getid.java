package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

/**
 * Send the item IDs of all matching items to the player.
 * 
 * @author Emiel
 *
 */
public class Getid extends Command {

	@Override
	public void execute(Player c, String input) {
		if (input.length() < 3) {
			c.sendMessage("You must give at least 3 letters of input to narrow down the item.");
			return;
		}
		int results = 0;
		c.sendMessage("Searching: " + input);
		for (int j = 0; j < World.getWorld().getItemHandler().ItemList.length; j++) {
			if (results == 100) {
				c.sendMessage("100 results have been found, the maximum number of allowed results. If you cannot");
				c.sendMessage("find the item, try and enter more characters to refine the results.");
				return;
			}
			if (World.getWorld().getItemHandler().ItemList[j] != null && World.getWorld().getItemHandler().ItemList[j].itemDescription != null
					&& !World.getWorld().getItemHandler().ItemList[j].itemDescription.equalsIgnoreCase("null")) {
				if (World.getWorld().getItemHandler().ItemList[j].itemName.replace("_", " ").toLowerCase().contains(input.toLowerCase())) {
					c.sendMessage("@red@" + World.getWorld().getItemHandler().ItemList[j].itemName.replace("_", " ") + " - " + World.getWorld().getItemHandler().ItemList[j].itemId);
					results++;
				}
			}
		}
		c.sendMessage(results + " results found...");
	}
}
