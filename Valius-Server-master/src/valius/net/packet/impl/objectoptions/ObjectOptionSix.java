package valius.net.packet.impl.objectoptions;

import valius.model.entity.player.Player;
import valius.world.World;

public class ObjectOptionSix {
	
	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.clickObjectType = 0;
		
		switch (objectType) {
		
		case 8356://piscdocks
			c.getPA().movePlayer(1806, 3689, 0);
			break;
		}
	}

}
