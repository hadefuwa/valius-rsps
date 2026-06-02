package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;


public class Boundaries extends Command{
	
	@Override
	public void execute(Player player, String input) {
/*		for (Boundary room : Boundary.THEATREROOMS) {
			if (Boundary.isIn(player, room)) {	
				player.sendMessage("In boundary: "+" - " + room.toString().indexOf() );
			}
		}
		
		player.sendMessage("Done.");*/
		
		for (int x = 0; x <= Boundary.THEATRE_ROOMS.length; x++) {
			String room = "none";
			boolean check = false;
			switch (x) {
				case 0:
					room = "Theatre";
					check = Boundary.isIn(player, Boundary.THEATRE);
					break;
				case 1:
					room = "Maiden";
					check = Boundary.isIn(player, Boundary.MAIDEN);
					break;
				case 2:
					room = "Bloat";
					check = Boundary.isIn(player, Boundary.BLOAT);
					break;
				case 3:
					room = "Nylocas";
					check = Boundary.isIn(player, Boundary.NYLOCAS);
					break;
				case 4:
					room = "Sotetseg";
					check = Boundary.isIn(player, Boundary.SOTETSEG);
					break;
				case 5:
					room = "Xarpus";
					check = Boundary.isIn(player, Boundary.XARPUS);
					break;
				case 6:
					room = "Verzik";
					check = Boundary.isIn(player, Boundary.VERZIK);
					break;
				case 7:
					room = "Loot";
					check = Boundary.isIn(player, Boundary.LOOT);
					break;			
			}
				player.sendMessage("Boundary: "+room+" - "+check);
		}
		
		
	}

}
