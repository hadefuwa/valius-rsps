package valius.net.packet.impl.objectoptions;

import valius.clip.WorldObject;
import valius.content.cannon.DwarfCannon;
import valius.content.tradingpost.Listing;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.world.World;
import valius.world.objects.GlobalObject;

/*
 * @author Matt
 * Handles all 3rd options for objects.
 */

public class ObjectOptionThree {

	public static void handleOption(final Player c, WorldObject worldObject) {
		int objectId = worldObject.getId();
		int objectX = worldObject.getX();
		int objectY = worldObject.getY();
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.resetInteractingObject();
		c.clickObjectType = 0;
		// c.sendMessage("Object type: " + objectType);
		if (World.getWorld().getHolidayController().clickObject(c, 3, objectId, objectX, objectY)) {
			return;
		}

		GlobalObject object = new GlobalObject(objectId, objectX, objectY, c.getHeight());
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 3:  "+objectId+"");
		
		switch (objectId) {

		case DwarfCannon.CANNON_OBJECT_ID:
			c.cannon.emptyFuel(object);
			break;
		case 22472:
			c.getPA().showInterface(36000);
			c.getAchievements().drawInterface(0);
			break;
		case 24101://Opens Trading Post with bank booth
		case 6943:
			//c.sendMessage("Trading post has been temporarily disabled!");
	            Listing.openPost(c, false, true);
	            break;
		case 8356://streexerics
			c.getPA().movePlayer(1311, 3614, 0);
			break;
		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getDH().sendDialogues(818, 6773);
			break;
		}
	}


}
