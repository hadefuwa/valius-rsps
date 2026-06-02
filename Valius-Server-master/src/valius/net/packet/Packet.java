package valius.net.packet;

import valius.model.entity.player.Player;

/**
 * Packet interface.
 * 
 * @author Graham
 * 
 */
public interface Packet {

	public void handlePacket(Player client, int packetType, int packetSize);

}
