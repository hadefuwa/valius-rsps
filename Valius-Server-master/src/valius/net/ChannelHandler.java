package valius.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;

@Slf4j
public class ChannelHandler extends SimpleChannelHandler {

	private Session session = null;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (e.getMessage() instanceof Player) {
			session.setClient((Player) e.getMessage());
		} else if (e.getMessage() instanceof Packet) {
			Player client = session.getClient();
			if (client != null) {
				if (client.getPacketsReceived() < Config.MAX_INCOMING_PACKETS_PER_CYCLE) {
					client.queueMessage((Packet) e.getMessage());
				} else {
					client.logout();
					log.info("{} was kicked due to breaching amount of packets per cycle; Amount: {}",  client.playerName, client.getPacketsReceived());
					return;
				}
			}
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		if (session == null) {
			session = new Session(ctx.getChannel());
		}
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		if (session != null) {
			Player client = session.getClient();
			if (client != null) {
				client.disconnected = true;
				PlayerSave.saveGame(client);
			}
			session = null;
		}
	}

}
