package valius.net.packet.impl;

import valius.model.entity.player.Player;
import valius.net.packet.PacketType;
import valius.util.Misc;

public class Report implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		String player = Misc.longToReportPlayerName(c.inStream.readQWord2()).replaceAll("_", " ");
		byte rule = (byte) c.inStream.readUnsignedByte();
	}

}