package valius.model.minigames.xeric;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.util.Misc;
/**
 * 
 * @author Patrity
 * 
 * 
 */
public class XericLobby {

	public static boolean xericEnabled;
	private static int activeIndex = 1;

	public static boolean lobbyActive = false;//players cannot go into other raid rooms while this is true
	
	
	public static int xericLobbyTimer = 60;// how many seconds til timer starts

	public static int timeLeft = 0;// how much time is left until raid starts
	
	
	public static void start(List<Player> lobbyPlayers) {

		Xeric xeric = new Xeric(lobbyPlayers);
		xeric.setIndex(activeIndex++);
		
		for (Player p : lobbyPlayers) {
			p.xericDamage = 0;
			p.setXeric(xeric);
			p.getPA().removeAllWindows();
			p.getPA().movePlayer((Misc.random(3) + 2715), (Misc.random(3) + 5470), xeric.getHeight());
			p.sendMessage("Welcome to the Trials of Xeric. Your first wave will start soon.", 255);
			
		}	
		xeric.spawn();
		
	}

}
	
	
	