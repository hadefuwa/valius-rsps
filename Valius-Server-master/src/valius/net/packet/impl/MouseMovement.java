package valius.net.packet.impl;

import valius.model.entity.player.Player;
import valius.net.packet.PacketType;

public class MouseMovement implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		if (c.isIdle)
			c.isIdle = false;
		//c.sendMessage("Tits1");
	}
}