package valius.net.packet.impl;

import java.util.Objects;

import valius.Config;
import valius.ServerState;
import valius.discord.DiscordBot;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;
import valius.net.packet.PacketType;
import valius.punishments.PunishmentType;
import valius.util.Misc;
import valius.util.log.PlayerLogging;
import valius.util.log.PlayerLogging.LogType;
import valius.world.World;

/**
 * Private messaging, friends etc
 **/
public class PrivateMessaging implements PacketType {

	public final int ADD_FRIEND = 188, SEND_PM = 126, REMOVE_FRIEND = 215, CHANGE_PM_STATUS = 95, REMOVE_IGNORE = 74,
			ADD_IGNORE = 133;

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		switch (packetType) {

		case ADD_FRIEND:
			c.getFriends().add(c.getInStream().readLong());
			break;

		case SEND_PM:
			if (System.currentTimeMillis() < c.muteEnd) {
				c.sendMessage("You are muted for breaking a rule.");
				return;
			}
			if (World.getWorld().getPunishments().contains(PunishmentType.NET_MUTE, c.connectedFrom)) {
				c.sendMessage("Your entire network has been muted. Other players cannot see your message.");
				return;
			}
			c.muteEnd = 0;
			final long recipient = c.getInStream().readLong();
			int pm_message_size = packetSize - 8;
			final byte pm_chat_message[] = new byte[pm_message_size];
			c.getInStream().readBytes(pm_chat_message, pm_message_size, 0);
			c.getFriends().sendPrivateMessage(recipient, pm_chat_message);
			if (Objects.nonNull(PlayerHandler.getPlayerByLongName(recipient)) && Objects.nonNull(c)) {
				//System.out.println(c.playerName + " PM: " + Misc.decodeMessage(pm_chat_message, pm_chat_message.length));
				if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
					// TODO: Log handling
				}
			}
			PlayerLogging.write(LogType.PRIVATE_CHAT, c, "Recipient = " + PlayerHandler.getPlayerByLongName(recipient).playerName + ", message = " + Misc.decodeMessage(pm_chat_message, pm_chat_message.length));
			DiscordBot.sendMessage("pm-logs", "["+c.playerName+" -> "+PlayerHandler.getPlayerByLongName(recipient).playerName+"] : " + Misc.decodeMessage(pm_chat_message, pm_chat_message.length));
			break;

		case REMOVE_FRIEND:
			c.getFriends().remove(c.getInStream().readLong());
			break;

		case REMOVE_IGNORE:
			c.getIgnores().remove(c.getInStream().readLong());
			break;

		case CHANGE_PM_STATUS:
			c.getInStream().readUnsignedByte();
			c.setPrivateChat(c.getInStream().readUnsignedByte());
			c.getInStream().readUnsignedByte();
			c.getFriends().notifyFriendsOfUpdate();
			break;

		case ADD_IGNORE:
			c.getIgnores().add(c.getInStream().readLong());
			break;

		}

	}
}
