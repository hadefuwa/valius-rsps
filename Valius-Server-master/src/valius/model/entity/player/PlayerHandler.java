package valius.model.entity.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.ServerState;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.util.Buffer;
import valius.world.World;

@Slf4j
public class PlayerHandler {

	public static Object lock = new Object();
	public static Player players[] = new Player[Config.MAX_PLAYERS];
	public static String messageToAll = "";

	public static boolean updateAnnounced;
	public static boolean updateRunning;
	public static boolean updateCancelled;
	public static int updateSeconds;
	public static long updateStartTime;
	private boolean kickAllPlayers = false;

	public static PlayerSave save;

	public static Player getPlayer(String name) {
		for (int d = 0; d < Config.MAX_PLAYERS; d++) {
			if (PlayerHandler.players[d] != null) {
				Player o = PlayerHandler.players[d];
				if (o.playerName.equalsIgnoreCase(name)) {
					return o;
				}
			}
		}
		return null;
	}

	public static Optional<Player> getOptionalPlayer(String name) {
		return getPlayers().stream().filter(Objects::nonNull).filter(client -> client.playerName.equalsIgnoreCase(name)).findFirst();
	}

	public static Player getPlayerByLongName(long name) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] == null)
				continue;
			if (PlayerHandler.players[i].getNameAsLong() == name)
				return PlayerHandler.players[i];
		}
		return null;
	}

	public static int getPlayerID(String playerName) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Player p = PlayerHandler.players[i];
				if (p.playerName.equalsIgnoreCase(playerName)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * The next available slot between 1 and {@link Config#MAX_PLAYERS}.
	 * 
	 * @return the next slot
	 */
	public int nextSlot() {
		for (int index = 1; index < Config.MAX_PLAYERS; index++) {
			if (players[index] == null) {
				return index;
			}
		}
		return -1;
	}

	public void add(Player player) {
		players[player.getIndex()] = player;
		players[player.getIndex()].isActive = true;
		if (Config.SERVER_STATE == ServerState.PUBLIC_PRIMARY) {
			log.info("{} [{}] just logged in.", player.getName(), player.connectedFrom);
		}
	}

	public static int getPlayerCount() {
		int count = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				count++;
			}
		}
		return (count + Config.PLAYERMODIFIER);
	}

	public static int getRealPlayerCount() {
		int online = (int) ((double)PlayerHandler.getPlayers().size() * 1.3333);
		return online;
	}

	public static boolean isPlayerOn(String playerName) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (players[i] != null) {
				if (players[i].playerName.equalsIgnoreCase(playerName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Create an int array of the specified length, containing all values between 0 and length once at random positions.
	 * 
	 * @param length The size of the array.
	 * @return The randomly shuffled array.
	 */
	private int[] shuffledList(int length) {
		int[] array = new int[length];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
		Random rand = new Random();
		for (int i = 0; i < array.length; i++) {
			int index = rand.nextInt(i + 1);
			int a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
		return array;
	}

	public void process() {
		synchronized (lock) {
			if (kickAllPlayers) {
				log.info("Kicking all players!");
				nonNullStream().forEach(player -> {
					player.disconnected = true;
				});
			}
			long startTime = System.currentTimeMillis();

			List<Player> playerList = nonNullStream().filter(player -> player != null && player.initialized && player.isActive).collect(Collectors.toList());
		
			Collections.shuffle(playerList, new Random(startTime));
			
			playerList.stream().forEach(player -> {
				try {

					boolean logoutDelayPassed = (System.currentTimeMillis() - player.logoutDelay > 90000);
					boolean inDuel = Boundary.isIn(player, Boundary.DUEL_ARENA) && World.getWorld().getMultiplayerSessionListener().inSession(player, MultiplayerSessionType.DUEL);
					if (player.disconnected && (logoutDelayPassed || player.properLogout || kickAllPlayers || inDuel)) {
						try {
							if (World.getWorld().getMultiplayerSessionListener().inSession(player, MultiplayerSessionType.TRADE)) {
								World.getWorld().getMultiplayerSessionListener().finish(player, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
							}
						} catch(Exception ex) {
							ex.printStackTrace();
						}
						try {
							DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(player, MultiplayerSessionType.DUEL);
							if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST) {
								if (duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
									duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
								} else {
									Player winner = duelSession.getOther(player);
									duelSession.setWinner(winner);
									duelSession.finish(MultiplayerSessionFinalizeType.GIVE_ITEMS);
								}
							}
							if (Config.BOUNTY_HUNTER_ACTIVE) {
								if (player.getBH().hasTarget()) {
									player.getBH().setWarnings(player.getBH().getWarnings() + 1);
								}
							}
							if (player.getPA().viewingOtherBank) {
								player.getPA().resetOtherBank();
							}
						} catch(Exception ex) {
							ex.printStackTrace();
						}
						player.prelogout();
						if (PlayerSave.saveGame(player)) {
							log.info("{} [{}] just disconnected", player.playerName, player.connectedFrom);
						} else {
							log.info("Could not save for {}", player.playerName);
						}
						removePlayer(player);
						IntStream
						.range(0, players.length)
						.filter(index -> players[index] != null && players[index] == player)
						.forEach(index -> players[index] = null);
						return;
					}
					try {
						//player.preProcessing();
						player.processQueuedPackets();
						player.process();
						//player.postProcessing();
						player.getNextPlayerMovement();
					} catch(Exception ex) {
						ex.printStackTrace();
						player.disconnected = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}



			});
			
			List<Player> randomList = nonNullStream().filter(player -> player != null && player.initialized && player.isActive).collect(Collectors.toList());
			Collections.shuffle(randomList, new Random(startTime));
			
			randomList.stream().forEach(player -> {
				try {
					player.update();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			randomList.stream().forEach(player -> {
				try {
					player.clearUpdateFlags();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			if (updateRunning && !updateAnnounced) {
				updateAnnounced = true;
				World.getWorld().setGameUpdating(true);
			}

			if (updateCancelled) {
				updateCancelled = false;
				World.getWorld().setGameUpdating(false);
			}
			if (updateRunning && (System.currentTimeMillis() - updateStartTime > (updateSeconds * 1000))) {
				kickAllPlayers = true;
				if(nonNullStream().count() == 0) {
					System.exit(0);
				}
			}
		}

	}
	
	

	public void updateNPC(Player plr, Buffer str) {
		// synchronized(plr) {
		updateBlock.currentOffset = 0;

		str.createFrameVarSizeWord(65);
		str.initBitAccess();

		str.writeBits(8, plr.npcListSize);
		int size = plr.npcListSize;
		if(plr.currentRender < plr.maxRender) {
			plr.currentRender += 4;
		}
		plr.npcListSize = 0;
		for (int i = 0; i < size; i++) {
			NPC npc = plr.npcList[i];
			if(npc == null)
				continue;
			if (!plr.rebuildNPCList && plr.withinDistance(npc) && !npc.teleporting && npc.sameInstance(plr)) {
				npc.updateNPCMovement(str);
				npc.appendNPCUpdateBlock(updateBlock);
				plr.npcList[plr.npcListSize++] = npc;
			} else {
				int id = npc.getIndex();
				plr.npcInListBitmap[id >> 3] &= ~(1 << (id & 7));
				str.writeBits(1, 1);
				str.writeBits(2, 3);
			}
		}

		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			NPC npc = NPCHandler.npcs[i];
			if (npc != null) {
				int id = npc.getIndex();
				if (!plr.rebuildNPCList && (plr.npcInListBitmap[id >> 3] & (1 << (id & 7))) != 0) {

				} else if (!plr.withinDistance(npc)) {
				
				} else {
					if(!npc.sameInstance(plr))
						continue;
					plr.addNewNPC(npc, str, updateBlock);
				}
			}
		}

		plr.rebuildNPCList = false;

		if (updateBlock.currentOffset > 0) {
			str.writeBits(14, 16383);
			str.finishBitAccess();
			str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
		} else {
			str.finishBitAccess();
		}
		str.endFrameVarSizeWord();
		// }
	}

	private Buffer updateBlock = new Buffer(new byte[Config.BUFFER_SIZE]);

	public void updatePlayer(Player plr, Buffer str) {
		updateBlock.currentOffset = 0;
		if (updateRunning && !plr.updateAnnounced) {
			plr.updateAnnounced = true;
			str.writePacketHeader(114);
			str.writeWordBigEndian(updateSeconds * 50 / 30);
		}
		if (updateCancelled && plr.updateAnnounced) {
			str.writePacketHeader(114);
			str.writeWordBigEndian(0);
			plr.updateAnnounced = false;
		}
		plr.updateThisPlayerMovement(str);
		boolean saveChatTextUpdate = plr.isChatTextUpdateRequired();
		plr.setChatTextUpdateRequired(false);
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setChatTextUpdateRequired(saveChatTextUpdate);
		str.writeBits(8, plr.playerListSize);
		int size = plr.playerListSize;
		if (size >= 79) {
			size = 79;
		}
		plr.playerListSize = 0;
		boolean inInstance = plr.getInstance() != null;
		for (int i = 0; i < size; i++) {
			Player otherPlr = plr.playerList[i];
			boolean activeAndSame = otherPlr != null && players[otherPlr.getIndex()] != null && players[otherPlr.getIndex()] == plr.playerList[i];			
			if (activeAndSame && !otherPlr.isNeedsPlacement() && plr.withinDistance(otherPlr) && otherPlr.sameInstance(plr)) {
				plr.playerList[i].updatePlayerMovement(str);
				plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
				plr.playerList[plr.playerListSize++] = plr.playerList[i];
			} else {
				int id = plr.playerList[i].getIndex();
				plr.playerList[i] = null;
				plr.playerInListBitmap[id >> 3] &= ~(1 << (id & 7));
				str.writeBits(1, 1);
				str.writeBits(2, 3);
			}
		}

		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (players[i] == null || !players[i].isActive || players[i] == plr) {
				continue;
			}
			int id = players[i].getIndex();

			if ((plr.playerInListBitmap[id >> 3] & (1 << (id & 7))) != 0) {
				continue;
			}
			if (!plr.withinDistance(players[i])) {
				continue;
			}
			if(!plr.sameInstance(players[i]))
				continue;
			plr.addNewPlayer(players[i], str, updateBlock);
		}

		if (updateBlock.currentOffset > 0) {
			str.writeBits(11, 2047);
			str.finishBitAccess();
			str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
		} else {
			str.finishBitAccess();
		}

		str.endFrameVarSizeWord();
	}

	private void removePlayer(Player plr) {
		plr.destruct();
	}

	public static void executeGlobalMessage(String message) {
		Player[] clients = new Player[players.length];
		System.arraycopy(players, 0, clients, 0, players.length);
		Arrays.asList(clients).stream().filter(Objects::nonNull).forEach(player -> player.sendMessage(message));
	}

	public static void sendMessage(String message, List<Player> players) {
		for (Player player : players) {
			if (Objects.isNull(player)) {
				continue;
			}
			player.sendMessage(message);
		}
	}

	//@Deprecated
	public static List<Player> getPlayers() {
		return nonNullStream().collect(Collectors.toList());
	}
	
	public static Stream<Player> nonNullStream() {
		return Arrays.stream(players).filter(Objects::nonNull);
	}

	public static List<Player> getPlayersForNames(List<String> playerNames) {
		return playerNames
				.stream()
				.distinct()
				.filter(Objects::nonNull)
				.filter(name -> !name.isEmpty())
				.map(PlayerHandler::getPlayer)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	public static List<String> filterOffline(List<String> playerNames){
		return playerNames
				.stream()
				.distinct()
				.filter(Objects::nonNull)
				.filter(name -> !name.isEmpty())
				.map(PlayerHandler::getPlayer)
				.filter(Objects::nonNull)
				.map(plr -> plr.getName())
				.collect(Collectors.toList());
	}

	public static Optional<Player> getPlayerByIndex(int index) {
		return nonNullStream().filter(plr -> plr.getIndex() == index).findFirst();
	}

}
