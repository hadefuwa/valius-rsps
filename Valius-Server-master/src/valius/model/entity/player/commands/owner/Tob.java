package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.minigames.theatre.Theatre;

/**
 * Executing bonus events by {String input}
 * 
 * @author Matt
 */

public class Tob extends Command {

	public void execute(Player player, String input) {
			if(player.getTheatreInstance() == null) {
				player.sendMessage("You need to be in a ToB instance to do that!");
				return;
			}
			Theatre theatre = player.getTheatreInstance();
		switch (input) {
		case "":
			player.sendMessage("@red@Usage: ::tob maiden, bloat, nylocas, sotetseg, verzik");
			break;
	/*	
		case "maiden":
			if (!theatre.maidenDead()) {
		
				player.killedMaiden = true;
				player.sendMessage("Killed Maiden: true");
			} else {
				player.killedMaiden = false;
				player.sendMessage("Killed Maiden: false");
			}
			break;

		case "bloat":
			if (!player.killedBloat) {
				player.killedBloat = true;
				player.sendMessage("Killed Bloat: true");
			} else {
				player.killedBloat = false;
				player.sendMessage("Killed Bloat: false");
			}
			break;

		case "nylocas":
			if (!player.killedNylocas) {
				player.killedNylocas = true;
				player.sendMessage("Killed Nylocas: true");
			} else {
				player.killedNylocas = false;
				player.sendMessage("Killed Nylocas: false");
			}
			break;

		case "sotetseg":
			if (!player.killedSotetseg) {
				player.killedSotetseg = true;
				player.sendMessage("Killed Sotetseg: true");
			} else {
				player.killedSotetseg = false;
				player.sendMessage("Killed Sotetseg: false");
			}
			break;

		case "xarpus":
			if (!player.killedXarpus) {
				player.killedXarpus = true;
				player.sendMessage("Killed Xarpus: true");
			} else {
				player.killedXarpus = false;
				player.sendMessage("Killed Xarpus: false");
			}
			break;	

		case "verzik":
			if (!player.killedVerzik) {
				player.killedVerzik = true;
				player.sendMessage("Killed Verzik: true");
			} else {
				player.killedVerzik = false;
				player.sendMessage("Killed Verzik: false");
			}
			break;
	*/
		}
	}

}
