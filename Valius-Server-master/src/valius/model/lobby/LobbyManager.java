package valius.model.lobby;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import lombok.extern.java.Log;

@Log
public class LobbyManager {

	public static void initializeLobbies() {
		Stream.of(LobbyType.values()).forEach(lobbyType -> {
			try {
				Lobby lobby = lobbyType.getLobbyClass().newInstance();
				lobby.startTimer();
				lobbies.add(lobby);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		log.info("Initialized " + lobbies.size() + " lobbies.");
	}
	
	public static Optional<Lobby> get(LobbyType lobbyType) {
		return lobbies.stream().filter(lobby -> lobby.getClass() == lobbyType.getLobbyClass()).findFirst();
	}
	
	private static List<Lobby> lobbies = Lists.newArrayList();
}
