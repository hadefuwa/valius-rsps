package valius.model.lobby.impl;

import java.util.List;

import valius.Config;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.lobby.Lobby;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

public class TheatreOfBloodLobby extends Lobby {
	
	
	@Override
	public void onJoin(Player player) {
		
		player.getPA().movePlayer((3060+Misc.random(-2,2)), (9970+Misc.random(-2,2)));
		player.sendMessage("Welcome to the Theatre of Blood Lobby.");
		player.sendMessage("The raid will begin in "+ formattedTimeLeft() + "!");		
		
	}
	
	@Override
	public void onLeave(Player player) {
		player.getPA().movePlayer(3050, 9950);
		System.out.println(player.playerName+" ADDED TO TOB LOBBY");
	}

	@Override
	public boolean canJoin(Player player) {
		if(Config.theatreDisabled) {
			player.sendMessage("Theatre of Blood is currently disabled!");
			return false;
		}
		if (player.getCombatLevel() < 90) {
			player.sendMessage("You need a combat level of 90 to join Theatre of Blood!");
			return false;
		}
		return true;
	}

	@Override
	public void onTimerFinished(List<Player> lobbyPlayers) {
		// TODO Disable timer from counting down unless players are present
		Theatre.start(lobbyPlayers);
		
	}

	@Override
	public void onTimerUpdate(Player player) {
		String timeLeftString = formattedTimeLeft();
		player.getPA().sendFrame126("Raid begins in: @gre@" + timeLeftString, 6570);
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
		return Boundary.THEATRE_LOBBY;
	}
	
	

}
