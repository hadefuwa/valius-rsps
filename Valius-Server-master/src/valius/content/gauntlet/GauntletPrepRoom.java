package valius.content.gauntlet;

import valius.model.Location;
import valius.model.entity.player.Player;

public class GauntletPrepRoom {
	
	public static final Location ROOM_ENTRY = new Location(3032, 6118, 1);
	
	public static void enterRoom(Player player) {
		player.getPA().movePlayer(3032, 6118, 1);
		sendChest(player);
	}
	
	public static void sendChest(Player player) {
		player.getPA().sendConfig(2322, player.isGaunletLootAvailable() ? 5 : 0);
	}

}
