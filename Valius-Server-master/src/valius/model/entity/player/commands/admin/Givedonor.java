package valius.model.entity.player.commands.admin;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.all.Commands;

public class Givedonor extends Commands {

	public void execute(Player c, String input) {
		int count = 0;
		for (Player p : PlayerHandler.getPlayers()) {
			if (!p.getRights().contains(Right.SAPPHIRE)) {
				p.getRights().add(Right.SAPPHIRE);
				p.getRights().updatePrimary();
			}
			if (p.amDonated < 20) {
				p.amDonated = 20;
			}
			p.sendMessage("@blu@You have been granted Sapphire donor rank ($20) by an administrator.");
			count++;
		}
		c.sendMessage("Granted Sapphire donor rank to " + count + " online player(s).");
		GlobalMessages.send("All online players have been granted Sapphire donor rank!", GlobalMessages.MessageType.NEWS);
	}

}
