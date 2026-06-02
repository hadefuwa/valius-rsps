package valius.net.login;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.Optional;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.event.CycleEventHandler;
import valius.model.entity.player.ConnectedFrom;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;
import valius.model.entity.player.Right;
import valius.net.PacketBuilder;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.util.ISAACCipher;
import valius.util.Misc;
import valius.util.log.PlayerLogging;
import valius.util.log.PlayerLogging.LogType;
import valius.world.BetaConfiguration;
import valius.world.World;

@Slf4j
public class RS2LoginProtocol extends FrameDecoder {

	private static final BigInteger RSA_MODULUS = new BigInteger("113231744792566966668233153140552718806215630012861714609315544614963685486670717622726284444775375044230132523445256175345480009974713841551325089956578052235636990704096826013085472396244300989858839509305267364300906285402924258822250393599022520711952614564075514282600041378609782976087341703499695818663");

	private static final BigInteger RSA_EXPONENT = new BigInteger("111294933740604448249094508334251551560510643192524836478228636066916410032643864479386245003504135966692528138765119272945884619719107783654579199454398743327817783884277523377869067003043502453014864147212339697754291567176941387946529426017119713278439969174239500143375213636545886737003385619291981903617");

	private static final int CONNECTED = 0;
	private static final int LOGGING_IN = 1;
	private int state = CONNECTED;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!channel.isConnected()) {
			return null;
		}
		switch (state) {
		case CONNECTED:
			if (buffer.readableBytes() < 2)
				return null;
			int request = buffer.readUnsignedByte();
			if (request != 14) {
				log.warn("Invalid login request: {}", request);
				channel.close();
				return null;
			}
			buffer.readUnsignedByte();
			channel.write(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
			state = LOGGING_IN;
			return null;

		case LOGGING_IN:
			@SuppressWarnings("unused")
			int loginType = -1, loginPacketSize = -1, loginEncryptPacketSize = -1;
			if (2 <= buffer.capacity()) {
				loginType = buffer.readByte() & 0xff; // should be 16 or 18
				loginPacketSize = buffer.readByte() & 0xff;
				loginEncryptPacketSize = loginPacketSize - (36 + 1 + 1 + 2);
				if (loginPacketSize <= 0 || loginEncryptPacketSize <= 0) {
					log.warn("Zero or negative login size.");
					channel.close();
					return false;
				}
			}

			/**
			 * Read the magic id.
			 */
			if (loginPacketSize <= buffer.capacity()) {
				int magic = buffer.readByte() & 0xff;
				int version = buffer.readUnsignedShort();
				if (magic != 255) {
					System.out.println("Wrong magic id.");
					channel.close();
					return false;
				}
				@SuppressWarnings("unused")
				int lowMem = buffer.readByte() & 0xff;

				/**
				 * Pass the CRC keys.
				 */
				for (int i = 0; i < 9; i++) {
					buffer.readInt();
				}
				loginEncryptPacketSize--;
				if (loginEncryptPacketSize != (buffer.readByte() & 0xff)) {
					log.warn("Encrypted size mismatch.");
					channel.close();
					return false;
				}

				ChannelBuffer rsaBuffer = buffer.readBytes(loginEncryptPacketSize);
				BigInteger bigInteger = new BigInteger(rsaBuffer.array());
				bigInteger = bigInteger.modPow(RSA_EXPONENT, RSA_MODULUS);
				rsaBuffer = ChannelBuffers.wrappedBuffer(bigInteger.toByteArray());
				if ((rsaBuffer.readByte() & 0xff) != 10) {
					log.warn("Encrypted id != 10.");
					sendReturnCode(channel, 23);
					channel.close();
					return false;
				}
				final long clientHalf = rsaBuffer.readLong();
				final long serverHalf = rsaBuffer.readLong();

				int uid = rsaBuffer.readInt();

				if (uid == 0 || uid == 99735086) {
					channel.close();
					return false;
				}
				final String name = Misc.getRS2String(rsaBuffer); //Misc.formatPlayerName(Misc.getRS2String(rsaBuffer));
				final String pass = Misc.getRS2String(rsaBuffer);
				final String macAddress = Misc.getRS2String(rsaBuffer);
				final String identity = Misc.getRS2String(rsaBuffer);
				final int[] isaacSeed = { (int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf };
				final ISAACCipher inCipher = new ISAACCipher(isaacSeed);
				for (int i = 0; i < isaacSeed.length; i++)
					isaacSeed[i] += 50;
				final ISAACCipher outCipher = new ISAACCipher(isaacSeed);
				channel.getPipeline().replace("decoder", "decoder", new RS2Decoder(inCipher));
				return login(channel, inCipher, outCipher, version, name, pass, macAddress, identity);
			}
		}
		return null;

	}

	private static Player login(Channel channel, ISAACCipher inCipher, ISAACCipher outCipher, int version, String name, String pass, String macAddress, String identity) {
		int returnCode = 2;

		if(!World.getWorld().isWorldLoaded()) {
			returnCode = 11;
			sendReturnCode(channel, returnCode);
			return null;
		}
		if (!name.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		if (name.length() > 12) {
			returnCode = 8;
		}

		if (!PlayerSave.playerExists(name)) {
			String lowercaseName = name.toLowerCase();
			if (/*lowercaseName.contains("mod") || */lowercaseName.contains("admin")) {
				returnCode = 3;
			}
		}

		Punishments punishments = World.getWorld().getPunishments();

		int slot = World.getWorld().getPlayerHandler().nextSlot();
		Player player = new Player(slot, name, channel);
		player.playerName = name;
		player.playerName2 = player.playerName;
		player.playerPass = pass;
		player.setNameAsLong(Misc.playerNameToInt64(player.playerName));
		player.outStream.packetEncryption = outCipher;
		player.saveCharacter = false;
		player.isActive = true;
		player.connectedFrom = ((InetSocketAddress) channel.getRemoteAddress()).getAddress().getHostAddress();
		player.setMacAddress(macAddress);
		if (player.connectedFrom.equalsIgnoreCase("71.84.206.79")||player.connectedFrom.equalsIgnoreCase("5.102.242.85")||player.connectedFrom.equalsIgnoreCase("103.212.223.81")|| player.connectedFrom.equalsIgnoreCase("95.195.219.42")){
			log.info("User {} has been ip banned and blocked from logging in", player.playerName);
			player.playerPass = "sdfsdfsdfsdfdsff";
			returnCode = 7;
		}
		if (slot == -1) {
			returnCode = 7;
			player.saveFile = false;
		}

		if (punishments.contains(PunishmentType.BAN, name) || punishments.contains(PunishmentType.MAC_BAN, macAddress)
				|| punishments.contains(PunishmentType.NET_BAN, player.connectedFrom)) {
			returnCode = 4;
		}
		if (version != Config.CLIENT_VERSION) {
			System.out.println(player.playerName+ " - Player version: " +version+" - Server version: "+Config.CLIENT_VERSION);
			returnCode = 6;
		}
		if (player.playerName.endsWith(" ")) {
			returnCode = 3;
		}
		if (player.playerName.startsWith(" ")) {
			returnCode = 3;
		}
		if (player.playerName.contains("  ")) {
			returnCode = 3;
		}
		if (identity.length() < 2)
			log.warn("Identity length mismatch for {} - identity: {}", player.playerName, identity);
		
		if (PlayerHandler.isPlayerOn(name)) {
			Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(name);
			if (optionalPlayer.isPresent()) {
				Player c2 = optionalPlayer.get();
				if (!World.getWorld().getMultiplayerSessionListener().inAnySession(c2) && c2.playerIndex == 0 && !c2.getSession().isConnected()) {
					PlayerLogging.write(LogType.DC_LOG, c2, c2.playerName + " had dced at at X: "+c2.getX() +" Y:"+c2.getY()+" H: "+c2.getHeight());
					c2.outStream.writePacketHeader(109);
					CycleEventHandler.getSingleton().stopEvents(c2);
					c2.properLogout = true;
					c2.disconnected = true;
					ConnectedFrom.addConnectedFrom(c2, c2.connectedFrom);
					returnCode = 25;
				} else {
					returnCode = 5;
				}
			}
		}
		if (PlayerHandler.getPlayerCount() >= Config.MAX_PLAYERS) {
			returnCode = 7;
		}
		if (World.getWorld().isGameUpdating()) {
			long timePassed = (System.currentTimeMillis() - PlayerHandler.updateStartTime);
			long timeLeft = (PlayerHandler.updateSeconds * 1000) - timePassed;
			if(timeLeft < 60000)
				returnCode = 14;
		}

		if (returnCode == 2) {
			int load = PlayerSave.loadGame(player, player.playerName, player.playerPass);
			if (load == 0)
				player.addStarter = true;
			else if (load == 3) {
				returnCode = 3;
				player.saveFile = false;
			} else {
				for (int i = 0; i < player.playerEquipment.length; i++) {
					if (player.playerEquipment[i] == 0) {
						player.playerEquipment[i] = -1;
						player.playerEquipmentN[i] = 0;
					}
				}
			}
		}
		
		if(World.getWorld().isBetaWorld()) {
			if(!BetaConfiguration.validBetaUser(player.playerName)) {
				System.out.println("Invalid beta user");
				returnCode = 12;
			} else {
				if(!player.getRights().contains(Right.BETA_TESTER))
					player.getRights().add(Right.BETA_TESTER);
			}
		}
		if (returnCode == 2) {
			World.getWorld().getPlayerHandler().add(player);
			player.saveFile = true;
			player.saveCharacter = true;
			player.packetType = -1;
			player.packetSize = 0;
			final PacketBuilder bldr = new PacketBuilder();
			bldr.put((byte) 2);
			bldr.put((byte) player.getRights().getPrimary().getValue());
			bldr.put((byte) 0);
			channel.write(bldr.toPacket());
		} else {
			sendReturnCode(channel, returnCode);
			return null;
		}
		synchronized (PlayerHandler.lock) {
			player.initialize();
			player.initialized = true;
		}
		return player;
	}

	public static void sendReturnCode(final Channel channel, final int code) {
		channel.write(new PacketBuilder().put((byte) code).toPacket()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(final ChannelFuture arg0) throws Exception {
				arg0.getChannel().close();
			}
		});
	}

}
