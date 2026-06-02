package valius.net.packet.impl;

import valius.model.entity.player.Player;
import valius.net.packet.PacketType;
import valius.world.World;

public class MapRegionFinish implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		World.getWorld().getItemHandler().reloadItems(c);
		World.getWorld().getGlobalObjects().updateRegionObjects(c);
		c.getQuestManager().onRegionChange();
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}
	}

}
