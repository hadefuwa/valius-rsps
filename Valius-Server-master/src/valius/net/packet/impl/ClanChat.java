package valius.net.packet.impl;

import valius.discord.DiscordBot;
import valius.model.entity.player.Player;
import valius.net.packet.PacketType;
import valius.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readLong());
		DiscordBot.sendMessage("cc-logs", c.playerName+": "+textSent);
		textSent = textSent.replaceAll("_", " ");
	}
}