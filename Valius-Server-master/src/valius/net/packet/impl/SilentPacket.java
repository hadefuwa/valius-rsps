package valius.net.packet.impl;

import valius.model.entity.player.Player;
import valius.net.packet.PacketType;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {

	}
}
