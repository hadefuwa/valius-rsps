package valius.model.minigames.CastleWars;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import valius.model.entity.player.Player;
import valius.model.entity.player.Equipment.Slot;

/**
 * 
 * @author Divine | Jan. 9, 2019 , 12:23:41 a.m.
 *
 */

public class CastleWarsLobby {

	/*
	 * Team Items
	 */
	private static int saradominHood = 4513;
	private static int saradominCape = 4514;
	private static int zamorakHood = 4515;
	private static int zamorakCape = 4516;
	
	/*
	 * gets players in lobbies
	 */
	private static List <Player> zamorakLobby = new CopyOnWriteArrayList<Player>();
	
	private static List <Player> saradominLobby = new CopyOnWriteArrayList<Player>();
	
	/*
	 * Castle wars timers (Lobby timer on first join | game length | total amount of available games
	 */
	public static int CastleWarsLobbyTimer = 120;
	
	public static int CastleWarsGameTimer = 1200;
	
	private static boolean CastleWarsActive = false;
	
	private static CastleWars[] games = new CastleWars[1];
	
	/*
	 * Joins the Saradomin team
	 */
	public static void joinSaradominLobby(Player player) {
		if (saradominLobby.contains(player)) {
			return;
		}
		if (player.getEquipment().wearingAny(Slot.HELMET) && player.getEquipment().wearingAny(Slot.CAPE)) {
			player.sendMessage("You must not be wearing anything on your head or back.");
			return;
		}
		CastleWarsLobby.saradominLobby.add(player);// adds player to lobby
		player.sendMessage("You have joined the Saradomin Team.");
	}

	/*
	 * Joins the Zamorak team
	 */
	public static void joinZamorakLobby(Player player) {
		if (zamorakLobby.contains(player)) {
			return;
		}
		if (player.getEquipment().wearingAny(Slot.HELMET) && player.getEquipment().wearingAny(Slot.CAPE)) {
			player.sendMessage("You must not be wearing anything on your head or back.");
			return;
		}
			CastleWarsLobby.zamorakLobby.add(player);// adds player to lobby
			player.sendMessage("You have joined the Zamorak Team.");
	}
	
	/*
	 * Removes players from the lobbies
	 */
/*	public static void removePlayer(Player player) {
			CastleWars.remove(player);
	}
	
	private static void create() {	
		List<Player> joining = new ArrayList<>();
		int added = 0;
		CastleWars castlewars = new CastleWars();
		addGame(castlewars);
		
		for (Player p : CastleWarsLobby.saradominLobby) {
			
			if (added > 25) {// max amount of allowed players in the saradomin team lobby
				p.sendMessage("The lobby is full at the moment, try again in a minute.");
				continue;
			}
			p.setCastleWars(castlewars);
			p.getPA().removeAllWindows();
			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), xeric.getIndex() * 4);
			p.sendMessage("Defeat the enemy team!", 255);
			joining.add(p);
			saradominLobby.remove(p);
			added++;
			CastleWars.setSaradominTeam(joining);	
		}
		
		for (Player p : CastleWarsLobby.zamorakLobby) {
			
			if (added > 25) {// max amount of allowed players in the zamorak team lobby
				p.sendMessage("The lobby is full at the moment, try again in a minute.");
				continue;
			}
			p.setXeric(xeric);
			p.getPA().removeAllWindows();
			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), xeric.getIndex() * 4);
			p.sendMessage("Defeat the enemy team!", 255);
			joining.add(p);
			zamorakLobby.remove(p);
			added++;
			CastleWars.setSaradominTeam(joining);
		}
	}
	public static void start(List<Player> lobbyPlayers) {
		List<Player> joining = new ArrayList<>();
		int added = 0;
		Xeric xeric = new Xeric();
		addGame(xeric);
		
		for (Player p : lobbyPlayers) {
			
			if (added > 25) {// max amount of allowed players in a raid at a time
				p.sendMessage("The lobby is full at the moment, try again in a minute.");
				continue;
			}
			p.xericDamage = 0;
			p.setXeric(xeric);
			p.getPA().removeAllWindows();
			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), xeric.getIndex() * 4);
			p.sendMessage("Welcome to the Trials of Xeric. Your first wave will start soon.", 255);
			joining.add(p);
			xericLobby.remove(p);
			added++;
			
		}	
		xeric.setXericTeam(joining);
		xeric.spawn();
		
	}
	
	private static int getFreeIndex() {
		for (int i = 0; i < games.length; i++) {
			if (games[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public static void addGame(Xeric xeric) {
		int freeIndex = getFreeIndex();
		games[freeIndex] = xeric;
		games[freeIndex].setIndex(freeIndex);
		xeric.setIndex(freeIndex);
	}
	
	public static void removeGame(Xeric xeric) {
		if (games[xeric.getIndex()] == null)
			return;
		games[xeric.getIndex()] = null;
		for (int i = 0; i < games.length; i++) {
			if (games[i] != null && games[i].getXericTeam().size() <= 0) {
				games[i] = null;
			}
		}
	}

	public static List<Player> getRaidLobby() {
		return xericLobby;
	}*/


}
	
	
	