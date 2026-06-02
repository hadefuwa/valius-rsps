package valius.net.packet.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import valius.Config;
import valius.ServerState;
import valius.clip.Region;
import valius.content.gauntlet.TheGauntlet;
import valius.content.gauntlet.crafting.GauntletCraftables;
import valius.discord.DiscordBot;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.SkillExperience;
import valius.model.entity.player.commands.Command;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.multiplayer_session.MultiplayerSession;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.shops.Currency;
import valius.model.shops.ShopTab;
import valius.model.shops.TabbedShop;
import valius.net.packet.PacketType;
import valius.punishments.Punishment;
import valius.punishments.PunishmentType;
import valius.punishments.Punishments;
import valius.util.Misc;
import valius.util.log.PlayerLogging;
import valius.util.log.PlayerLogging.LogType;
import valius.world.World;

/**
 * Commands
 **/
public class Commands implements PacketType {

	public final String NO_ACCESS = "You do not have the right.";

	public static final Map<String, Command> COMMAND_MAP = new TreeMap<>();

	public static boolean executeCommand(Player c, String playerCommand, String commandPackage) {
		String commandName = Misc.findCommand(playerCommand);
		String commandInput = Misc.findInput(playerCommand);
		String className;

		if (commandName.length() <= 0) {
			return true;
		} else if (commandName.length() == 1) {
			className = commandName.toUpperCase();
		} else {
			className = Character.toUpperCase(commandName.charAt(0)) + commandName.substring(1).toLowerCase();
		}
		try {
			String path = "valius.model.entity.player.commands." + commandPackage + "." + className;
			if (!COMMAND_MAP.containsKey(path)) {
				initialize(path);
			}
			COMMAND_MAP.get(path).execute(c, commandInput);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (Exception e) {
			c.sendMessage("Error while executing the following command: " + playerCommand);
			e.printStackTrace();
			return true;
		}
	}

	private static void initialize(String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		COMMAND_MAP.clear();
		Class<?> commandClass = Class.forName(path);
		Object instance = commandClass.newInstance();
		if (instance instanceof Command) {
			Command command = (Command) instance;
			COMMAND_MAP.putIfAbsent(path, command);
		}
	}

	public static void initializeCommands() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassPath classPath = ClassPath.from(Commands.class.getClassLoader());
		String[] packages = {"valius.model.entity.player.commands.admin", "valius.model.entity.player.commands.all", "valius.model.entity.player.commands.donator",
				"valius.model.entity.player.commands.helper", "valius.model.entity.player.commands.moderator", "valius.model.entity.player.commands.owner", "valius.model.entity.player.commands.beta"};

		for (String pack : packages) {
			for (ClassInfo classInfo : classPath.getTopLevelClasses(pack)) {
				initialize(classInfo.getName());
			}
		}
	}

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}

		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c) && !c.getRights().isOrInherits(Right.OWNER)) {
			c.sendMessage("You cannot execute a command whilst trading, or dueling.");
			return;
		}

		boolean isManagment = c.getRights().isOrInherits(Right.OWNER);
		boolean isModerator = c.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER, Right.MODERATOR);

		if(isManagment) {
			if(playerCommand.equalsIgnoreCase("missingshops")) {
				ShopTab.getActiveTabs().stream().forEach(shop -> {
					if(!TabbedShop.getShopById(shop.getId()).isPresent()) {
						System.out.println(shop.getName().replace(" ", "_").toUpperCase() + "(\"" + shop.getName() + "\", " + shop.getId() + "), ");
					}
				});
			}
			if(playerCommand.equalsIgnoreCase("currencyCheck")) {
				ShopTab.getActiveTabs().stream().forEach(shop -> {
					if(shop.getCurrency() == Currency.COINS)
						return;
					boolean[] hasValidCosts = {true};
					shop.getItems().stream().forEach(item -> {
						if(item.getStoredCost() == 0)
							hasValidCosts[0] = false;
					});
					if(!hasValidCosts[0])
						System.out.println("Shop has incorrect values " + shop.getId() + ": " + shop.getName());
				});
			}
			if(playerCommand.equalsIgnoreCase("givhat")) {
				Stream.of(GauntletCraftables.ATTUNED_CRYSTAL_HELM.getMaterials()).flatMap(Stream::of).forEach(item -> c.getItems().addItem(item[0], item[1]));
			}
			if (playerCommand.startsWith("glow")) {
				String[] args = playerCommand.split(" ");
				c.getPA().sendFrame36(c.PRAYER_GLOW[Integer.parseInt(args[1])], Integer.parseInt(args[2]));
			}
			if (playerCommand.startsWith("config")) {
				String[] args = playerCommand.split(" ");
				c.getPA().sendFrame36(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			}
			if (playerCommand.equals("tobstop")) {
				//Theatre.stop();
			}
			if (playerCommand.equals("tobgames")) {
				//Theatre.listGames();
			}
			if (playerCommand.equals("forcedc")) {
				c.disconnected = true;
			}
			
			if (playerCommand.equalsIgnoreCase("zalcano")) {
				c.getPA().movePlayerUnconditionally(3033, 6048, 0);
			}

			if (playerCommand.equalsIgnoreCase("rt")) {		
				c.getPA().movePlayerUnconditionally(3088, 3488, 0);
				c.setInstance(null);
				CycleEventHandler.getSingleton().addEvent(new CycleEventContainer(0, c, new CycleEvent() {
					
					@Override
					public void execute(CycleEventContainer container) {
						c.sendMessage("Entering instance");
						c.setInstance(new TheGauntlet(c));
						c.sendMessage("Instace status: " + (c.getInstance() == null));
						container.stop();
					}
					
				}, 1));
			}
			
			if (playerCommand.startsWith("chunkinfo")) {
				c.sendMessage("X: " + c.getLocation().getXInChunk() + ", Y: " + c.getLocation().getYInChunk());
			}
			
			if(playerCommand.startsWith("roomtest")) {
				if(c.getInstance() != null && c.getInstance() instanceof TheGauntlet) {
					TheGauntlet gauntlet = (TheGauntlet) c.getInstance();
					String[] args = playerCommand.split(" ");
					gauntlet.getDungeon().roomTest(Ints.tryParse(args[1]), Ints.tryParse(args[2]));
				}
			}
			
			if(playerCommand.equalsIgnoreCase("myplayers")) {
				PlayerHandler.nonNullStream().forEach(plr -> System.out.println(plr.getIndex()));
				Stream.of(c.playerList).forEach(id -> System.out.println(id));
			}

			if (playerCommand.startsWith("con")) {
				String[] args = playerCommand.split(" ");
				c.getPA().sendConfig(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			}
			
			if (playerCommand.equalsIgnoreCase("testshit")) {
				//c.getItems().removeItemFromEquipment(4151, 1);
			}

			if (playerCommand.startsWith("str")) {
				String[] args = playerCommand.split(" ");
				c.getPA().sendFrame126(playerCommand.replace(args[0], "").replace(args[1], "").trim(), Integer.parseInt(args[1]));
			}

			if (playerCommand.equals("ge")) {
				c.getPA().showInterface(25000);
			}

			if (playerCommand.equals("sell")) {
				c.getPA().showInterface(25650);
			}
			
			if(playerCommand.startsWith("citf")) {
				int itfId = Integer.parseInt(playerCommand.replace("citf", "").trim());
				c.getPA().sendFrame164(itfId);
			}
			if(playerCommand.equalsIgnoreCase("maketest")) {
				c.getMakeWidget()
				.set(23886, (plr, amount) -> {
					plr.sendMessage("you want to make " + amount + " whipz");
				})
				.set(23889, (plr, amount) -> {
					plr.sendMessage("you want to make " + amount + " ngrs");
				})
				.set(23892, (plr, amount) -> {
					plr.sendMessage("you want to make " + amount + " cocks");
				})
				.send();
			}
			if(playerCommand.equals("sitems")) {
				List<Item> items = Lists.newArrayList();
				for(int i = 0;i<53;i++) {
					items.add(new Item(4151, i + 1));
				}
				c.getPA().sendItems(c, 63115, items, items.size());
			}

			if(playerCommand.startsWith("snpc")) {

				String[] args = playerCommand.split(" ");
				int npcId = Ints.tryParse(args[1]);
				c.getPA().sendNPCOnInterface(63116, npcId);
			}
			
			if(playerCommand.startsWith("snc")) {
				String[] args = playerCommand.split(" ");
				int modelZoom = Ints.tryParse(args[1]);
				int rotation1 = Ints.tryParse(args[2]);
				int rotation2 = Ints.tryParse(args[3]);
				c.getPA().sendInterfaceModelSettings(63116, modelZoom, rotation1, rotation2);
			}

			if(playerCommand.equalsIgnoreCase("rndskills")) {
				Stream.of(Skill.values()).forEach(skill -> {
					int rnd = Misc.random(99);
					int points = 0;
					int output = 0;
					for (int lvl = 1; lvl <= rnd; lvl++) {
						points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
						output = (int) Math.floor(points / 4);
						
					}
					c.getSkills().setExperience(output, skill);
				});
			}
			//if(playerCommand.equalsIgnoreCase("hiscoretest")) {
			//	HiscoreHandler handler = new HiscoreHandler();
			//	handler.prepare(c);
			//	handler.uploadHiscores();
			//}
			if(playerCommand.equalsIgnoreCase("initdiscord")) {
				DiscordBot.initialize();
			}
			if(playerCommand.equalsIgnoreCase("testdiscord")) {
				String channelId = "test-channel";
				for(int i = 0;i<10;i++)
					DiscordBot.sendMessage(channelId, "TEST " + i);
			}


			if (playerCommand.equals("buy")) {
				c.getPA().showInterface(25600);
			}

			if (playerCommand.equals("master")) {
				if (!isManagment && !Config.BETA_MODE) {
					c.sendMessage(NO_ACCESS);
					return;
				}
				c.getSkills().setExperience(SkillExperience.getExperienceForLevel(99), Skill.getCombatSkills());
				c.getSkills().setLevel(99, Skill.getCombatSkills());
			}

			if (playerCommand.equals("max")) {
				if (!isManagment && !Config.BETA_MODE) {
					c.sendMessage(NO_ACCESS);
					return;
				}

				c.getSkills().setExperience(SkillExperience.getExperienceForLevel(99), Skill.values());
				c.getSkills().setLevel(99, Skill.values());
			}
		}

		if (playerCommand.startsWith("changepass")) {
			if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
				// TODO: Log handling
			}
		} else {
			if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
				// TODO: Log handling
			}
		}
		if (playerCommand.startsWith("/")) {
			if (World.getWorld().getPunishments().contains(PunishmentType.MUTE, c.playerName) || World.getWorld().getPunishments().contains(PunishmentType.NET_BAN, c.connectedFrom)) {
				c.sendMessage("You are muted for breaking a rule.");
				return;
			}
			if (c.clan != null) {
				
				c.clan.sendChat(c, playerCommand);
				PlayerLogging.write(LogType.PUBLIC_CHAT, c, "Clan spoke = " + playerCommand);
				return;
			}
			c.sendMessage("You can only do this in a clan chat..");
			return;
		}
		
		if (playerCommand.startsWith("tileinfo")) {
			boolean blocked = false;
			for (int dir = 0; dir < 7; dir++) {
				if (!Region.canShoot(c.getX(), c.getY(), c.getHeight(), dir))
					blocked = true;
			}
			c.sendMessage("Blocked ? " + blocked);
		}
		/*if (playerCommand.startsWith("item")) {
            try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 40000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);		
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item id amount.");
				}
			} catch(Exception e) {

			}
		}*/




		/*boolean hasClaimed = false;
        if (playerCommand.equals("rewardnoob")) {
			if (!hasClaimed) {
				c.getRaids().generateLoot(1000);
				hasClaimed = true;
			}

		}*/

		if (playerCommand.startsWith("teletome")) {
			if (!isModerator) {
				c.sendMessage(NO_ACCESS);
				return;
			}



			try {
				String target = playerCommand.replace("teletome ", "");
				Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(target);
				if (optionalPlayer.isPresent()) {
					Player c2 = optionalPlayer.get();
					c2.setX(c.getX());
					c2.setY(c.getY());
					c2.setHeight(c.getHeight());
					c2.setNeedsPlacement(true);
					c.sendMessage("You have teleported " + c2.playerName + " to you.");
					c2.sendMessage("You have been teleported to " + c.playerName + ".");
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		

		if (playerCommand.startsWith("topic")) {
			String args = playerCommand.substring(7);
			c.getPA()
			.sendFrame126(
					"https://valius.net/community/index.php?/topic/"
							+ args, 12000);
		}
		if (playerCommand.startsWith("thread")) {
			String args = playerCommand.substring(7);
			c.getPA()
			.sendFrame126(
					"https://valius.net/community/index.php?/forum/"
							+ args, 12000);
		}
		if (playerCommand.startsWith("highscores")) {

			c.getPA()
			.sendFrame126(
					"http://valius.everythingrs.com/services/hiscores", 12000);
		}
		if (playerCommand.startsWith("vote")) {

			c.getPA()
			.sendFrame126(
					"http://valius.everythingrs.com/services/vote", 12000);
		}
		if (playerCommand.startsWith("donate")) {

			c.getPA()
			.sendFrame126(
					"https://valius.net/community/index.php?/donate/", 12000);
		}
		if (playerCommand.startsWith("forum")) {

			c.getPA()
			.sendFrame126(
					"https://valius.net/community", 12000);
		}
		if (playerCommand.startsWith("spawns")) {
			World.getWorld().reloadNPCHandler();
			c.sendMessage("@blu@Reloaded NPCs");
		}

		if (playerCommand.equals("rights")) {

			c.sendMessage("isOwner: "+c.getRights().contains(Right.OWNER));
			c.sendMessage("isAdmin: "+c.getRights().contains(Right.ADMINISTRATOR));
			c.sendMessage("isManagment: "+isManagment);
			c.sendMessage("isMod: "+c.getRights().contains(Right.MODERATOR));
			c.sendMessage("isPlayer: "+c.getRights().contains(Right.PLAYER));
		}

		//we already have an interface command btw *facepalm*
		/*if (playerCommand.equals("interface")) {
        	if (!c.getRights().isOrInherits(Right.OWNER)) {
        		c.sendMessage(NO_ACCESS);
        		return;
        	}

        	String[] args = playerCommand.split(" ");
        	if (args.length < 1) {
        		c.sendMessage("Improper usage! ::interface [id]");
        		return;
        	}
        	int id = Integer.parseInt(args[1]);
        	c.getPA().showInterface(id);
        	c.sendMessage("Attempting to open interface #"+id);
        }*/


		if (playerCommand.startsWith("giverights")) {
			if (!c.getRights().isOrInherits(Right.OWNER) && !c.getName().equalsIgnoreCase("mod divine")) {
				c.sendMessage(NO_ACCESS);
				return;
			}

			try {

				String[] args = playerCommand.split(" ");
				int right = Integer.parseInt(args[1]);
				String target = playerCommand.substring(args[0].length()+1+args[1].length()).trim();
				boolean found = false;

				for (Player p : World.getWorld().getPlayerHandler().players) {
					if (p == null)
						continue;

					if (p.playerName.equalsIgnoreCase(target)) {
						p.getRights().setPrimary(Right.get(right));
						p.sendMessage("Your rights have changed. Please relog.");
						found = true;
						break;
					}
				}


				if (found) {
					c.sendMessage("Set "+target+"'s rights to: "+right);
				} else {
					c.sendMessage("Couldn't change \""+target+"\"'s rights. Player not found.");
				}

			} catch (Exception e) {
				c.sendMessage("Improper usage! ::giverights [id] [target]"); 
			}

		}

		if (playerCommand.startsWith("sf126")) {
			if (!isManagment) {
				c.sendMessage(NO_ACCESS);
				return;
			}
			try {

				String[] args = playerCommand.split(" ");
				int id = Integer.parseInt(args[1]);
				String msg = playerCommand.substring(args[0].length()+1+args[1].length()).trim()+"";

				c.getPA().sendFrame126(msg, id);

			} catch (Exception e) {
				c.sendMessage("Invalid usage! ::sf126 [id] [string]");
			}

		}

		if (playerCommand.startsWith("item")) {
			if (!isManagment && !Config.BETA_MODE) {
				c.sendMessage(NO_ACCESS);
				return;
			}


			try {
				String[] args = playerCommand.split(" ");
				if (args.length >= 2) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = 1;
					if (args.length > 2) {
						newItemAmount = Integer.parseInt(args[2]);
					} 

					if ((newItemID <= 40000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item id amount.");
				}
			} catch (Exception e) {

			}
		}

		if (playerCommand.startsWith("voted")) {
			//Our vote API from EverythingRS.com
			//By default this will work with Project Insanity sources, but it is very easy to make it
			//work with anything (Vencillio/RuneSource, Hyperion, Matrix, etc)
			//Things you will need to change in order to make it work with a different server are:
			//1. "String playerName = c.playerName" . Change that to whatever your source uses to fetch the username
			//2. "c.sendMessage" . Change that to how the server sends the player message packet.
			//3. "c.getItems().addItem" . Change that to how the server handles adding a new item.
			//And that's it. After tweaking those 3 things, you can get it to work with any source.
			//If you want me to personally add the code for a specific server, please leave a post on our thread
			//And we will personally add the snippet for your current server base
			String[] args = playerCommand.split(" ");
			new Thread() {
				public void run() {
					try {
						int id = Integer.parseInt(args[1]);
						String playerName = c.playerName;
						final String request = com.everythingrs.vote.Vote.validate("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9", playerName, id);
						String[][] errorMessage = {
								{"error_invalid", "@red@There was an error processing your request."},
								{"error_non_existent_server", "@red@This server is not registered at EverythingRS."},
								{"error_invalid_reward", "@red@The reward you're trying to claim doesn't exist"},
								{"error_non_existant_rewards", "@red@This server does not have any rewards set up yet."},
								{"error_non_existant_player", "@red@There is not record of user " + playerName + " make sure to vote first"},
								{"not_enough", "@red@You do not have enough vote points to recieve this item"}};
						for (String[] message : errorMessage) {
							if (request.equalsIgnoreCase(message[0])) {
								c.sendMessage(message[1]);
								return;
							}
						}
						if (request.startsWith("complete")) {
							int item = Integer.valueOf(request.split("_")[1]);
							int amount = Integer.valueOf(request.split("_")[2]);
							String itemName = request.split("_")[3];
							int remainingPoints = Integer.valueOf(request.split("_")[4]);
							c.getItems().addItem(item, amount);
							c.sendMessage("You have recieved the item " + itemName + ". You have " + remainingPoints
									+ " points left.");

						}
					} catch (Exception e) {
						c.sendMessage("@red@Services are currently offline. Please try again later.");
						e.printStackTrace();
					}
				}
			}.start();
		}

		if (playerCommand.startsWith("movehome")) {

			if (!isManagment) {
				c.sendMessage(NO_ACCESS);
				return;
			}

			try {

				String target = playerCommand.replace("movehome ", "");
				Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(target);
				if (optionalPlayer.isPresent()) {
					Player c2 = optionalPlayer.get();
					c2.setX(Config.EDGEVILLE_X);
					c2.setY(Config.EDGEVILLE_Y);
					c2.setHeight(0);
					c2.setNeedsPlacement(true);
					c.sendMessage("You have teleported " + c2.playerName + " to home.");
					c2.sendMessage("You have been teleported home by " + c.playerName + ".");
				}

			} catch (Exception e) {
				c.sendMessage("Invalid usage! ::movehome [target]");
			}

		}

		if (playerCommand.startsWith("teleto")) {

			if (!isManagment) {
				c.sendMessage(NO_ACCESS);
				return;
			}

			try {

				String target = playerCommand.replace("teleto ", "");
				Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(target);
				if (optionalPlayer.isPresent()) {
					Player c2 = optionalPlayer.get();
					c.setX(c2.getX());
					c.setY(c2.getY());
					c.setHeight(c2.getHeight());
					c.setNeedsPlacement(true);
					c.sendMessage("You have teleported to " + c2.playerName + ".");
					c2.sendMessage("You have been teleported to by " + c.playerName + ".");
				}

			} catch (Exception e) {
				c.sendMessage("Invalid usage! ::teleto [target]");
			}

		}

		if (playerCommand.startsWith("ban") && !playerCommand.startsWith("bank")) {
			if (!isManagment) {
				c.sendMessage(NO_ACCESS);
				return;
			}
			try {
				String[] args = playerCommand.split(" ");


				/*
                //durations lul?
                int duration = 0;

                if (Misc.tryParseInt(args[args.length-1])) {
                	duration = Integer.parseInt(args[args.length-1]);
                };
				 */

				//String name = playerCommand.substring(args[0].length()+1+args[1].length()).trim();

				StringBuilder sb = new StringBuilder();
				sb.append(playerCommand);
				Misc.deleteFromSB(sb, args[0]);
				String target = sb.toString().trim();
				System.out.println("target: "+target);



				Punishments punishments = World.getWorld().getPunishments();
				if (punishments.contains(PunishmentType.BAN, target)) {
					c.sendMessage(target+" is already banned.");
					return;
				}
				World.getWorld().getPunishments().add(new Punishment(PunishmentType.BAN, 0, target));
				Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(target);
				if (optionalPlayer.isPresent()) {
					Player c2 = optionalPlayer.get();
					if (c2 == null) {
						return;
					}

					/* @TODO FIX THIS TOMORROW
                    if (!c2.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER) || !c.getRights().isOrInherits(Right.OWNER)) {
                        c.sendMessage("You cannot ban this player.");
                        return;
                    }
					 */

					if (World.getWorld().getMultiplayerSessionListener().inAnySession(c2)) {
						MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c2);
						session.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
					}
					c2.properLogout = true;
					c2.disconnected = true;
					c.sendMessage(target+" was permenantly banned.");

					return;
				}

			} catch (Exception e) {
				c.sendMessage("Correct usage. ::ban [target] [duration] (no duration = perm)");
			}
		}

		if(c.getRights().isOrInherits(Right.HELPER)) {
			DiscordBot.sendMessage("staff-command-logs", c.playerName + ": " + playerCommand);
		}
		PlayerLogging.write(LogType.COMMAND, c, c.playerName + " typed command " + playerCommand + " at X: " + c.getX() + " Y:" + c.getY());

		if (c.getRights().isOrInherits(Right.OWNER) && executeCommand(c, playerCommand, "owner")) {
			return;
		} else if (c.getRights().isOrInherits(Right.ADMINISTRATOR) && executeCommand(c, playerCommand, "admin")) {
			return;
		} else if (c.getRights().isOrInherits(Right.MODERATOR) && executeCommand(c, playerCommand, "moderator")) {
			return;
		} else if (c.getRights().isOrInherits(Right.HELPER) && executeCommand(c, playerCommand, "helper")) {
			return;
		} else if (c.getRights().isOrInherits(Right.SAPPHIRE) && executeCommand(c, playerCommand, "donator")) {
			return;
		} else if (World.getWorld().isBetaWorld() && c.getRights().isOrInherits(Right.BETA_TESTER) && executeCommand(c, playerCommand, "beta")) {
			return;
		} else if (executeCommand(c, playerCommand, "all")) {
			return;
		}

	}
}
