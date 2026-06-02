package valius.net.packet.impl.objectoptions;

import valius.clip.WorldObject;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.world.World;

public class ObjectOptionFive {
	
	public static void handleOption(final Player c, WorldObject worldObject) {
		int objectId = worldObject.getId();
		int objectX = worldObject.getX();
		int objectY = worldObject.getY();
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.resetInteractingObject();
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 5:  "+objectId+"");
		
		switch (objectId) {		
		}
	}

}
