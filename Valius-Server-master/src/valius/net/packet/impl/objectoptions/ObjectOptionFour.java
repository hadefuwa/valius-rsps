package valius.net.packet.impl.objectoptions;

import valius.clip.WorldObject;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.world.World;

public class ObjectOptionFour {
	
	public static void handleOption(final Player c, WorldObject worldObject) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.clickObjectType = 0;
		
		int objectId = worldObject.getId();

		c.resetInteractingObject();
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 4:  "+objectId+"");
		
		switch (objectId) {
		
		case 8356://streehosidius
			c.getPA().movePlayer(1679, 3541, 0);
			break;
		}
	}

}
