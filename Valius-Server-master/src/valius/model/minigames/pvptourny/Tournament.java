package valius.model.minigames.pvptourny;
//package ethos.model.minigames.pvptourny;
//
//import java.util.ArrayList;
//
//import ethos.world.World;
//import ethos.event.CycleEvent;
//import ethos.event.CycleEventContainer;
//import ethos.event.CycleEventHandler;
//import ethos.event.Event;
//import ethos.model.Location;
//import ethos.model.entity.npc.NPC;
//import ethos.model.entity.npc.NPCHandler;
//import ethos.model.entity.player.GlobalMessages;
//import ethos.model.entity.player.Player;
//import ethos.model.entity.player.Equipment.Slot;
//import ethos.model.entity.player.GlobalMessages.MessageType;
//import ethos.model.entity.player.skills.Skill;
//import ethos.util.Misc;
//
///**
// * 
// *  @author ReverendDread | Wrote the base for this
// * @author Divine | Feb. 10, 2019 , 2:29:46 a.m.
// *
// */
//
//public class Tournament {
//	
//	public Tournament() {
//	}
//	
//
//	/** Players in the lobby */
//	private ArrayList<Player> players;
//	
//	/** The host of the tournament */
//	private Player host;
//	
//	/** If the tournament has started */
//	private boolean started;
//	
//	/** If the tournament has finished */
//	private boolean finished;
//	
//	/** Type of mode the tournament is in */
//	private int mode;
//	
//	/** Delay till tournament starts. */
//	private int delay;
//	
//
//	
//	public static int[][] SPAWNS = {
//			{ 3480, 3240, 4 },
//			{ 3524, 3240, 4 },
//			{ 3524, 3240, 4 },
//			{ 3483, 3233, 4 },
//			{ 3533, 3217, 4 }
//			
//	};
//	
//	/**
//	 * Constructor for PvPTournament
//	 * @param mode
//	 * 			the mode.
//	 */
//	public Tournament(final Player host, int mode, int delay) {
//		this.mode = mode;
//		this.delay = delay;
//		this.host = host;
//		this.players = new ArrayList<Player>();
//		startLobbyTask();
//	}
//	
//	/**
//	 * Processes lobby shit.
//	 */
//	private final void startLobbyTask() {
//		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
//			@Override
//			public void execute(CycleEventContainer event) {
//				if (players.size() < 2) {
//					GlobalMessages.send("<img=1>[PvP Tournament] @red@The Tournament has been canceled due to not enough players.", GlobalMessages.MessageType.EVENT);
//					players.forEach((player) -> {
//						exit(player, false);
//					});
//					stop();
//					return;
//				}
//				players.forEach((player) -> {
//					player.setTournamentConstants(new TournamentConstants());
//				});
//				started = true;
//				stop();
//			}
//			
//		}, 16);
//	}
//	
//	/**
//	 * Handles giving the winner their reward/rewards.
//	 * @param player
//	 */
//	public final void reward(final Player player) {
//		player.getItems().addItemUnderAnyCircumstance(6199, 1);
//	}
//	
//	/**
//	 * Called when a player is attempting to join the tournament lobby.
//	 * @param player
//	 * 				the player joining.
//	 */
//	public static final void join(final Player player) {
//		if (World.getTournament() == null) {
//			player.sendMessage("There is currently no active tournament.");
//			return;
//		}
//		if (player.getItems().isWearingItems()) {
//			player.sendMessage("You can't bring your own gear into this minigame.");
//			return;
//		}
//		if (player.getItems().freeSlots() != 28) {
//			player.sendMessage("You can't bring your own items into this minigame.");
//			return;
//		}
//		
//		if (World.getTournament().hasStarted()) {
//			player.sendMessage("You can't join the tournament because it's already started.");
//			return;
//		}
//		player.getPA().spellTeleport(3345, 3213, 4, false);
//		player.setTournamentConstants(new TournamentConstants());
//		World.getTournament().getPlayers().add(player);
//		player.sendMessage("You enter the Tournament Queue.");
//	}
//	
//	/**
//	 * Handles the event of a player killing another player.
//	 * @param player
//	 * 				the player.
//	 */
//	public final void handleKill(final Player killed, final Player player) {
//		player.sendMessage("You've killed " + killed.getName() + ", they didn't stand a chance.");
//		killed.sendMessage("You've been slayen by " + player.getName() + ", and lost the tournament.");
//		restockGear(player);
//		exit(killed, false);
//		if (players.size() == 1) {
//			GlobalMessages.send("<img=1>[PvP Tournament] @red@" + player.getName() + " has won the Tournament!", GlobalMessages.MessageType.EVENT);
//			exit(player, false);
//			reward(player);
//			finished = true;
//			return;
//		}
//		player.sendMessage("Your gear has been restocked for getting a kill.");
//		GlobalMessages.send("<img=1>[PvP Tournament]@red@ There is now " + (players.size()) + " players left alive!" + player.currentRegion + "", GlobalMessages.MessageType.EVENT);
//	}
//	
//	/**
//	 * Called when a player exits the lobby.
//	 * @param force TODO
//	 */
//	public final void exit(final Player player, boolean force) {
//		if (!force)
//			players.remove(player);
//		player.getItems().deleteAllItems();
//		player.getItems().deleteEquipment();
//		player.setTournamentConstants(ControllerManager.DEFAULT_CONTROLLER);
//		player.getPA().spellTeleport(3086, 3493, 0, false);
//		player.getSkills().resetToActualLevels();//TODO set stats upon entering depending on tournament type
//	}
//	
//	/**
//	 * Forces the tournament to end.
//	 * @param player
//	 * 				the player ending the tournament.
//	 */
//	public final void forceEnd(final Player player) {
//		if (World.getTournament().isFinished()) {
//			GlobalMessages.send("The tournament has already finished.", GlobalMessages.MessageType.EVENT);
//			return;
//		}
//		players.forEach((p) -> {
//			exit(p, true);
//		});
//		players.clear();
//		finished = true;
//		GlobalMessages.send("<img=1>[PvP Tournament]@red@ The Tournament has been forcefully ended by the host.", GlobalMessages.MessageType.EVENT);
//	}
//	
//	/**
//	 * Restocks a players gear.
//	 * @param player
//	 */
//	public final void restockGear(final Player player) {
//		int helmet = 0;
//		int cape = 1;
//		int amulet = 2;
//		int weapon = 3;
//		int chest =  4;
//		int shield = 5;
//		int legs = 7;
//		int hands = 9;
//		int feet = 10;
//		int ring = 12;
//		int arrows = 13;
//		
//		if (player.getHealth().getAmount() < player.getMaximumHealth()) {
//			player.getHealth().reset();
//		}
//			player.getSkills().resetToActualLevel(Skill.PRAYER);
//		switch (World.getTournament().getMode()) {
//				
//			case 0: //rune
//				//Clear the equipment for the new items.
//				player.getItems().deleteEquipment();
//				player.getItems().setEquipment(4587, 1, weapon);
//				player.getItems().setEquipment(1127, 1, chest);
//				player.getItems().setEquipment(1079, 1, legs);
//				player.getItems().setEquipment(11840, 1, feet);
//				player.getItems().setEquipment(3751, 1, helmet);
//				player.getItems().setEquipment(8850, 1, shield);
//				player.getItems().setEquipment(6570, 1, cape);
//				player.getItems().setEquipment(1740, 1, amulet);
//				player.getItems().setEquipment(2550, 1, ring);
//				player.getItems().setEquipment(7462, 1, hands);
//				//Clear the inventory for new items.
//				player.getItems().deleteAllItems();
//				player.getItems().addItem(2436, 1); //super attack
//				player.getItems().addItem(2440, 1); //super strength
//				player.getItems().addItem(2442, 1); //super defence
//				player.getItems().addItem(5698, 1); //dds p++
//				for (int i = 0; i < 5; i++)
//					player.getItems().addItem(6685, 1); //saradomin brew
//				for (int i = 0; i < 5; i++)
//					player.getItems().addItem(3024, 1); //super restore
//				for (int i = 0; i < 14; i++) 
//					player.getItems().addItem(385, 1); //sharks
//				break;
//				
//			case 1: //dh	
//				break;
//				
//			case 2: //tribrid		
//				break;
//		}
//	}
//	
//	/**
//	 * Gets the tournament mode.
//	 * @return
//	 */
//	public int getMode() {
//		return mode;
//	}
//	
//	/**
//	 * Gets the host of the tournament.
//	 * @return
//	 */
//	public Player getHost() {
//		return host;
//	}
//	
//	/**
//	 * Checks if the tournament is finished.
//	 * @return
//	 */
//	public boolean isFinished() {
//		return finished;
//	}
//	
//	/**
//	 * Checks if the tournament has started.
//	 * @return
//	 */
//	public boolean hasStarted() {
//		return started;
//	}
//	
//	/**
//	 * Gets a random location for a player to spawn.
//	 * @return
//	 */
//	public int getRandomSpawn(Player player) {
//		int index = Misc.random(Tournament.SPAWNS.length -1);
//		int x = (SPAWNS[index][0] + (Misc.random(-3, 3)));
//		int y = (SPAWNS[index][1] + (Misc.random(-3, 3)));
//		int z = (SPAWNS[index][2]);
//		player.getPA().movePlayer(x, y, z);
//		return index;
//	}
//	
//	/**
//	 * Gets the players list.
//	 * @return
//	 */
//	public ArrayList<Player> getPlayers() {
//		return players;
//	}
//	
//	/**
//	 * Launches a PvP Tournament.
//	 * @param player
//	 * 			the player launching it.
//	 * @param mode
//	 * 			the mode for the tournament.
//	 */
//	public static void launch(final Player player, int mode, int delay) {
//		if (mode < 0 || mode > 2) //change greater than value if more modes are added.
//			mode = 0;
//		if (delay < 30)
//			delay = 30;
//		if (World.getTournament() != null && !World.getTournament().isFinished()) {
//			player.sendMessage("You can't start a tournament when one is already in progress.");
//			return;
//		}
//		World.setTournament(new Tournament(player, mode, delay));
//		Tournament.join(player);
//		GlobalMessages.send("<img=1>[PvP Tournament] A Tournament is starting soon! Join through the red portal at home.", GlobalMessages.MessageType.EVENT);
//	}
//
//}
