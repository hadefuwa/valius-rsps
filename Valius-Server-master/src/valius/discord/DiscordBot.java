package valius.discord;

import java.util.Optional;

import com.darichey.discord.Command;
import com.darichey.discord.CommandListener;
import com.darichey.discord.CommandRegistry;
import com.darichey.discord.limiter.RoleLimiter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;
import valius.content.tradingpost.Listing;
import valius.event.CycleEventHandler;
import valius.model.entity.player.ConnectedFrom;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;
import valius.world.World;

public class DiscordBot {
	
	
	private static IDiscordClient cli;
	private final static String BOT_PREFIX = ";;";
	private final static long GUILD_ID = 283490325522022401L;
	private final static long DISCORD_ADMIN_ROLE = 510267224951488515L;
	
	private static IDiscordClient getBuiltDiscordClient(String token){
		return new ClientBuilder()
                .withToken(token)
                .build();
	}
	
	
	
	public static void initializeLocal() {
		Logger logger = (Logger) Discord4J.LOGGER;
		logger.setLevel(Level.INFO);
		cli = getBuiltDiscordClient(System.getenv().getOrDefault("DISCORD_BOT_TOKEN_LOCAL", ""));
		setupCommands(true);
		cli.login();
		DiscordManager.init();
		
	}
	
	public static void initialize() {
		Logger logger = (Logger) Discord4J.LOGGER;
		logger.setLevel(Level.INFO);
		cli = getBuiltDiscordClient(System.getenv().getOrDefault("DISCORD_BOT_TOKEN", ""));
		setupCommands(false);
		cli.login();
		DiscordManager.init();
	}
	
	public static void setupCommands(boolean localMode) {

		CommandRegistry registry = new CommandRegistry(BOT_PREFIX);
		
		if(!localMode) {
			Command playerCount = Command.builder()
					.onCalled(ctx -> {
	
						double online = (double)PlayerHandler.getPlayers().size() * 1.3333;
						String players = "There are currently `"+(int)online+"` players online.";
						ctx.getChannel().sendMessage(players);
					})
					.build();
			Command kickPlayer = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						ctx.getChannel().sendMessage(attemptKickPlayer(ctx.getMessage().getContent()));
					})
					.build();
			
			Command toggleTP = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						Listing.tradingPostEnabled = !Listing.tradingPostEnabled;
						ctx.getChannel().sendMessage("Trading post " + (Listing.tradingPostEnabled ? "ENABLED" : "DISABLED"));
					})
					.build();
			
			Command getStatsPlayer = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						ctx.getChannel().sendMessage(getStats(ctx.getMessage().getContent()));
					})
					.build();
			Command getTotalExpPlayer = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						ctx.getChannel().sendMessage(getTotalExp(ctx.getMessage().getContent()));
					})
					.build();
	
			Command listCommands = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						String available = "```Commands\n";
						ctx.getChannel().sendMessage(available);
					})
					.build();
	
			
			registry.register(playerCount, "players");
			registry.register(kickPlayer, "kick");
			registry.register(toggleTP, "toggletp");
			registry.register(getTotalExpPlayer, "totalexp");
			registry.register(getStatsPlayer, "stats");
			registry.register(listCommands, "commands");
		} else {
			Command localTest = Command.builder()
					.limiter(new RoleLimiter(DISCORD_ADMIN_ROLE))
					.onCalled(ctx -> {
						ctx.getChannel().sendMessage("RUNNING LOCAL MODE: " + System.getProperty("user.name"));
					})
					.build();
			registry.register(localTest, "localtest");
		}
		
		cli.getDispatcher().registerListener(new CommandListener(registry));
	}

	
	private static String getTotalExp(String command) {//these are the custom commands
		String username = extractUsername(command);
		Optional<Player> playerOpt = PlayerHandler.getOptionalPlayer(username);
		//if(player == null)
		//	player = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(username));
		if(!playerOpt.isPresent()){
			return username+" was not found in our database.";
		}		
		return "**"+Misc.capitalize(username)+"** has total exp of **"+playerOpt.get().getSkills().getTotalExperience()+"**";
	}

	private static String getStats(String command) {
		String username = extractUsername(command);
		
		Optional<Player> playerOpt = PlayerHandler.getOptionalPlayer(username);

		if(playerOpt.isPresent()) {
			Player player = playerOpt.get();
			StringBuilder sb = new StringBuilder("");
			String playerName = "__**"+ Misc.capitalize(player.playerName)+"**__ Statistics :wrench:\n";
			sb.append("__*Combat level:*__ **"+player.combatLevel+"**\n");
			sb.append("__*Total level:*__ **"+player.getSkills().getTotalLevel()+"** \n");
			sb.append("```");
			for (Skill s : Skill.values()) {
				sb.append(s.toString() + ": " + player.getSkills().getActualLevel(s));
				
			}
				
			return playerName+sb+"```";
		}

		return username+" was not found in our database.";
		
	}
	
	private static String extractUsername(String command) {
		String[] splited = command.split(" ");//or rather kick command sorry lol
		return command.replace(splited[0], "").trim();
	}
	
	private static String attemptKickPlayer(String command) {//a restart command from your staff and only staff can do this
		String username = extractUsername(command);

		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(username);
		if (optionalPlayer.isPresent()) {
			Player c2 = optionalPlayer.get();
			if (World.getWorld().getMultiplayerSessionListener().inAnySession(c2)) {
				return "The player is in a trade, or duel. You cannot do this at this time.";
			}
			c2.outStream.writePacketHeader(109);
			CycleEventHandler.getSingleton().stopEvents(c2);
			c2.properLogout = true;			
			c2.disconnected = true;
			c2.logoutDelay = Long.MAX_VALUE;
			ConnectedFrom.addConnectedFrom(c2, c2.connectedFrom);
			return "Kicked " + c2.playerName;
		}

		return "Player " + username + " is not online. You can only kick online players!";

	}
	
	
	
	public static void sendMessage(String channelName, String message) {
		if (cli == null) return; // bot not initialised - skip silently
		Optional<IChannel> channelOpt = getChannelByName(channelName);
		channelOpt.ifPresent(channel -> {
			if(channel.getModifiedPermissions(cli.getOurUser()).contains(Permissions.SEND_MESSAGES))//stops permission error spam
				DiscordManager.sendMessage(channel, message);
			});
	}
	
	public static Optional<IChannel> getChannelByName(String channelName) {
		if(cli == null)
			return Optional.empty();
		for(IChannel channel : cli.getChannels()) {
			if(channel.getName().equalsIgnoreCase(channelName)) {
				return Optional.ofNullable(channel);
			}
		}
		return Optional.empty();
	}

	public static IDiscordClient getDiscordClient() {
		return cli;
	}
	

}
