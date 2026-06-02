package valius.model.lobby;

import java.util.stream.Stream;

import lombok.Getter;
import valius.model.lobby.impl.ChambersOfXericLobby;
import valius.model.lobby.impl.TheatreOfBloodLobby;
import valius.model.lobby.impl.TrialsOfXericLobby;

public enum LobbyType {
	CHAMBERS_OF_XERIC(ChambersOfXericLobby.class),
	TRIALS_OF_XERIC(TrialsOfXericLobby.class),
	THEATRE_OF_BLOOD(TheatreOfBloodLobby.class)
	
	;
	
	private LobbyType(Class<? extends Lobby> lobbyClass) {
		this.lobbyClass = lobbyClass;
	}
	
	@Getter
	private Class<? extends Lobby> lobbyClass;

	public static Stream<LobbyType> stream() {
		// TODO Auto-generated method stub
		return Stream.of(LobbyType.values());
	}
}
