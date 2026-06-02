package valius.net.packet;

import valius.model.entity.player.Player;

public interface PacketType {
	public void processPacket(Player c, int packetType, int packetSize);
}
