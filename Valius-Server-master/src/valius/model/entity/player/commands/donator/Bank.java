package valius.model.entity.player.commands.donator;

import valius.content.gauntlet.TheGauntlet;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Open the banking interface.
 * 
 * @author Emiel
 */
public class Bank extends Command {

	@Override
	public void execute(Player c, String input) {
		if ((c.amDonated < 100) || (c.inWild()) || c.getInstance() != null && c.getInstance() instanceof TheGauntlet || (c.inTrade) || (Boundary.isIn(c, Boundary.RAIDS)) || (Boundary.isIn(c, Boundary.XERIC)) || (Boundary.isIn(c, Boundary.THEATRE)) || (c.inGodwars())) {
			c.sendMessage("Try using this in another area.");
			return;
		}
		if (c.amDonated > 99) {
		c.getPA().openUpBank();
		}
	}
}
