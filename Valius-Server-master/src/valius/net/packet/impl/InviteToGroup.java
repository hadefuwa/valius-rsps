/**
 * 
 */
package valius.net.packet.impl;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.net.packet.PacketType;

/**
 * @author ReverendDread
 * Aug 10, 2019
 */
public class InviteToGroup implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int playerIndex = c.getInStream().readUnsignedWordBigEndian();
		Optional<Player> other = PlayerHandler.getPlayerByIndex(playerIndex);
		if (other.isPresent()) {
			c.getGroupIronman().invite(other.get());
		}
	}

}
