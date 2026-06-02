package valius.net.packet.impl;

import java.util.stream.IntStream;

import valius.model.entity.player.Player;
import valius.net.packet.PacketType;

public class CapeColour implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int detailTop = c.getInStream().readInteger();
		int backgroundTop = c.getInStream().readInteger();
		int detailBottom = c.getInStream().readInteger();
		int backgroundBottom = c.getInStream().readInteger();
		
				
		c.getCompletionistCape().setColours(detailTop, backgroundTop, detailBottom, backgroundBottom);
		c.getPA().closeAllWindows();
		c.appearanceUpdateRequired = true;
		c.updateRequired = true;
	}
}