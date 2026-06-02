package valius.model.lobby.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.lobby.Lobby;
import valius.model.minigames.raids.Raids;
import valius.util.Misc;

public class ChambersOfXericLobby extends Lobby {

	public ChambersOfXericLobby() {
		
	}
	
	@Override
	public void onJoin(Player player) {
		player.getPA().movePlayer((3060+Misc.random(-3,3)), (9936+Misc.random(-3,3)));
		player.sendMessage("You are now in the lobby for Chambers of Xeric.");
		String timeLeftString = formattedTimeLeft();
		player.sendMessage("Raid starts in: " + timeLeftString);
	}

	@Override
	public void onLeave(Player player) {
		player.getPA().movePlayer(3059, 9947, 0);
		player.sendMessage("@red@You have left the Chambers of Xeric.");
	}

	@Override
	public boolean canJoin(Player player) {
		if(player.getCombatLevel() < 50) {
			player.sendMessage("You need a combat level of at least 50 to join this raid!");
			return false;
		}
		boolean accountInLobby = getFilteredPlayers()
	            .stream()
	            .anyMatch(lobbyPlr -> lobbyPlr.getMacAddress().equalsIgnoreCase(player.getMacAddress()));
		if (accountInLobby) {
			player.sendMessage("You already have an account in the lobby!");
			return false;
		}
		return true;
	}

	@Override
	public long waitTime() {
		return 60000;
	}

	@Override
	public int capacity() {
		return 22;
	}

	@Override
	public String lobbyFullMessage() {
		return "Chambers of Xeric is currently full!";
	}

	@Override
	public boolean shouldResetTimer() {
		return this.getWaitingPlayers().isEmpty();
	}

	@Override
	public void onTimerFinished(List<Player> lobbyPlayers) {
		Map<String, Player> macFilter = Maps.newConcurrentMap();

		lobbyPlayers.stream().forEach(plr -> macFilter.put(plr.getMacAddress(), plr));

		Raids raid = new Raids();
		raid.startRaid(Lists.newArrayList(macFilter.values()));

		lobbyPlayers.stream().filter(plr -> !macFilter.values().contains(plr)).forEach(plr -> {
			plr.sendMessage("You had a different account in this lobby, you will be added to the next one");
			onJoin(plr);
		});
	}

	@Override
	public void onTimerUpdate(Player player) {/*
		long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(timeLeft);
		if(secondsLeft % 10 == 0 || secondsLeft < 5) {
			player.sendMessage("Raid starts in: " + timeLeftString);*/
		String timeLeftString = formattedTimeLeft();
		player.getEventQueue().add( () -> { 
				player.getPA().sendFrame126("Raid begins in: @gre@" + timeLeftString, 6570);
				player.getPA().sendFrame126("", 6571);
				player.getPA().sendFrame126("", 6572);
				player.getPA().sendFrame126("", 6664);
				player.getPA().walkableInterface(6673);
			}
		);
		//}
	}

	@Override
	public Boundary getBounds() {
		return Boundary.RAIDS_LOBBY;
	}

}
