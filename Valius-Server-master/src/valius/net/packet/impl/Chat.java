package valius.net.packet.impl;

import valius.Config;
import valius.ServerState;
import valius.discord.DiscordBot;
import valius.model.entity.player.Player;
import valius.net.packet.PacketType;
import valius.punishments.PunishmentType;
import valius.util.Misc;
import valius.util.log.PlayerLogging;
import valius.util.log.PlayerLogging.LogType;
import valius.world.World;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
		c.setChatTextSize((byte) (c.packetSize - 2));
		c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);

		if (World.getWorld().getPunishments().contains(PunishmentType.NET_MUTE, c.connectedFrom)) {
			c.sendMessage("Your entire network has been muted. Other players cannot see your message.");
			return;
		}

		if (World.getWorld().getPunishments().contains(PunishmentType.MUTE, c.playerName)) {
			c.sendMessage("You are currently muted. Other players cannot see your message.");
			return;
		}

		if (System.currentTimeMillis() < c.muteEnd) {
			c.sendMessage("You are currently muted. Other players cannot see your message.");
			return;
		}
		String message = Misc.decodeMessage(c.getChatText(), c.getChatTextSize());
		DiscordBot.sendMessage("chat-logs", c.playerName+": "+message);
		
		PlayerLogging.write(LogType.PUBLIC_CHAT, c, "Spoke = "+message);
		
		if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
			//TODO public chat logging
		}
		c.setChatTextUpdateRequired(true);
	}
}
