package valius.model.minigames.pvptourny;
//package ethos.model.minigames.pvptourny;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import ethos.model.entity.player.Player;
//import ethos.util.Misc;
///**
// * 
// * @author Patrity
// * 
// * 
// */
//public class TournamentLobby {
//
//	public static boolean lobbyActive = false;//players cannot go into other raid rooms while this is true
//	
//	private static List <Player> tournamentLobby = new CopyOnWriteArrayList<Player>();//gets players in lobby
//	
//	public static int tournamentobbyTimer = 60;// how many seconds til timer starts
//
//	public static int timeLeft = 0;// how much time is left until raid starts
//	
//	private static Tournament[] games = new Tournament[500];
//	
//	public static void joinTournamentLobby(Player player) {//attempt to join raid lobby area
//		//conditions
//		if (tournamentLobby.contains(player)) {
//			return;
//		}
//		
//		tournamentLobby.add(player);//adds player to lobby
//		player.getDH().sendNpcChat("You are now in the lobby for Pvp Tournament. The Raid will be starting soon.");
//	}
//	
//	public static void removePlayer(Player player) {
//		tournamentLobby.remove(player);
//	}
//	
//	private static void create() {	
//		List<Player> joining = new ArrayList<>();
//		int added = 0;
//		Tournament tournament = new Tournament();
//		addGame(tournament);
//		
//		for (Player p : TournamentLobby.tournamentLobby) {
//			
//			if (added < 2) {// if there are less than 2 players in the game
//				p.sendMessage("There needs to be 2+ players to start the Tournament.");
//				return;
//			}
//			p.xericDamage = 0;
//			p.setTournament(tournament);//finish player loading for lobby in player class
//			p.getPA().removeAllWindows();
//			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), tournament.getIndex() * 4);
//			p.sendMessage("Welcome to the Trials of Xeric. Your first wave will start soon.", 255);
//			joining.add(p);
//			tournamentLobby.remove(p);
//			added++;
//			
//		}	
//		tournament.setTournamentPlayers(joining);
//	}
//	public static void start(List<Player> lobbyPlayers) {
//		List<Player> joining = new ArrayList<>();
//		int added = 0;
//		Tournament tournament = new Tournament();
//		addGame(tournament);
//		
//		for (Player p : lobbyPlayers) {
//			
//			if (added > 7) {// max amount of allowed players in a raid at a time
//				p.sendMessage("The lobby is full at the moment, try again in a minute.");
//				continue;
//			}
//			p.xericDamage = 0;
//			p.setXeric(tournament);
//			p.getPA().removeAllWindows();
//			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), xeric.getIndex() * 4);
//			p.sendMessage("Welcome to the Trials of Xeric. Your first wave will start soon.", 255);
//			joining.add(p);
//			tournamentLobby.remove(p);
//			added++;
//			
//		}	
//		tournament.setTournamentPlayers(joining);
//		
//	}
//	
//	private static int getFreeIndex() {
//		for (int i = 0; i < games.length; i++) {
//			if (games[i] == null) {
//				return i;
//			}
//		}
//		return -1;
//	}
//	
//	public static void addGame(Tournament tournament) {
//		int freeIndex = getFreeIndex();
//		games[freeIndex] = tournament;
//		games[freeIndex].setIndex(freeIndex);
//		tournament.setIndex(freeIndex);
//	}
//	
//	public static void removeGame(Tournament tournament) {
//		if (games[tournament.getIndex()] == null)
//			return;
//		games[tournament.getIndex()] = null;
//		for (int i = 0; i < games.length; i++) {
//			if (games[i] != null && games[i].getTournamentPlayers().size() <= 0) {
//				games[i] = null;
//			}
//		}
//	}
//
//	public static List<Player> getRaidLobby() {
//		return tournamentLobby;
//	}
//
//
//}
//	
//	