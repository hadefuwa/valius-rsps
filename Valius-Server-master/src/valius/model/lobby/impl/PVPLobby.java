package valius.model.lobby.impl;

import java.util.List;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.lobby.Lobby;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

public class PVPLobby extends Lobby {
	
	
	@Override
	public void onJoin(Player player) {
		
		player.getPA().movePlayer((3040+Misc.random(-2,2)), (9967+Misc.random(-2,2)));
		player.sendMessage("Welcome to the PvP Tournament Lobby.");
		player.sendMessage("The Tournament will begin in "+ formattedTimeLeft() + "!");		
		
	}
	
	@Override
	public void onLeave(Player player) {
		player.getPA().movePlayer(3050, 9950);
		System.out.println(player.playerName+" ADDED TO PVP Tourney LOBBY");
	}

	@Override
	public boolean canJoin(Player player) {
		return true;
	}

	@Override
	public void onTimerFinished(List<Player> lobbyPlayers) {
		// TODO Disable timer from counting down unless players are present
	//	TournamentLobby.start(lobbyPlayers);
		
	}

	@Override
	public void onTimerUpdate(Player player) {
		String timeLeftString = formattedTimeLeft();
		player.getPA().sendFrame126("Tournament begins in: @gre@" + timeLeftString, 6570);
		player.getPA().sendFrame126("", 6571);
		player.getPA().sendFrame126("", 6572);
		player.getPA().sendFrame126("", 6664);
		player.getPA().walkableInterface(6673);
	}

	@Override
	public long waitTime() {
		return 15000;
	}

	@Override
	public int capacity() {
		return 5;
	}

	@Override
	public String lobbyFullMessage() {
		// TODO Auto-generated method stub
		return "The lobby is currently full! Please wait for the next game!";
	}

	@Override
	public boolean shouldResetTimer() {
		return this.getWaitingPlayers().isEmpty();
	}

	@Override
	public Boundary getBounds() {
		return Boundary.TOURNY_LOBBY;
	}
	
	

}
