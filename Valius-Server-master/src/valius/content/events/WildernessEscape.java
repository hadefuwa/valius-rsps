package valius.content.events;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.world.World;
import valius.world.objects.GlobalObject;

/*
 * This class handles all data for the Server Event "The Great Wilderness Escape" -idea by AL
 */

public class WildernessEscape {
	/*
	 * Player that hosts event object
	 */
	public static Player host;
	
	public static List <Player> participants = new ArrayList<Player>();//all players participating in event | added into list by attacking host in combat assistant
	
	/*
	 * Boolean if the event is active or not
	 */

	public static boolean eventActive = false;// is the event is current active or not
	
	 /*
	 * If player has reached the current checkpoint or not
	 */
	private static boolean[] reachedCheckpoint = { false,false,false,false,false,false,false,false };
	
	public static int currentCheckpoint = 0;
	
	/*
	 * Constructor
	 */

	public WildernessEscape(Player player) {
		WildernessEscape.host = player;
	}
	
	public enum CHECKPOINTS {//All checkpoint data
		VOLCANO(3360,3932,0,"Volcano Bridge"),
		CALLISTO(3312,3840,1,"Callisto"),
		VENENATIS(3341,3738,2,"Venenatis"),
		VETION(3186,3791,3,"Vetion"),
		MINE(3085,3764,4,"Wild Mines"),
		ARCHAEOLOGIST(2981,3848,5,"Archaeologist"),
		CEMETARY(2980,3763,6,"Cemetary"),
		DITCH(3084,3528,7,"Wilderness ditch");
		
		int absX;
		int absY;
		int checkpoint;
		String location;
		
		CHECKPOINTS(int absX, int absY, int checkpoint, String location){
			this.absX = absX;
			this.absY = absY;
			this.checkpoint = checkpoint;
			this.location = location;
		}
	}

	/*
	 * Starts event
	 */
	public void startEvent() {
		if (eventActive == true) {
			host.sendMessage("Event is already running");
			return;
		}
		startTime();
		spawnCheckpoints();
		}
	
	
/*
 * Player attempts to claim checkpoint
 */
	public static void executeCheckpoint() {
		if (eventActive == false) {
			host.sendMessage("Event is not active");
			return;
		}
		
		for (CHECKPOINTS checkpoints: CHECKPOINTS.values()) {
			if(host.getX() >= checkpoints.absX-2 && host.getX() <= checkpoints.absX +2 && host.getY() >= checkpoints.absY-2 && host.getY() <= checkpoints.absY+2){
				if(checkpoints.checkpoint != 0 && reachedCheckpoint[checkpoints.checkpoint-1] == false) {
				return;
				}
				if(currentCheckpoint == 7) {
					PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName+" is at the last checkpoint!", PlayerHandler.getPlayers());
					host.sendMessage("CROSS THE WILDERNESS DITCH");
					host.gfx100(287);
					World.getWorld().getGlobalObjects().remove(860, checkpoints.absX, checkpoints.absY, 0);
					resetEvent();
					return;
				}
				
				if(reachedCheckpoint[checkpoints.checkpoint] == false) {
					currentCheckpoint += 1;
					reachedCheckpoint[checkpoints.checkpoint] = true;
					host.gfx100(287);
					World.getWorld().getGlobalObjects().remove(860, checkpoints.absX, checkpoints.absY, 0);
					PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName+" has just reached the @red@("+checkpoints.location+") checkpoint ", PlayerHandler.getPlayers());
				}
				}
				if(checkpoints.ordinal() == currentCheckpoint) {
				PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName+" is heading to the @red@("+checkpoints.location+") checkpoint", PlayerHandler.getPlayers());
				host.sendMessage("@blu@[EVENT]@bla@start heading to the @blu@("+checkpoints.location+") checkpoint");
				}
		
		}
		
		
	}
	
	public static void hostWins() {
		host.sendMessage("You have won the event!");
		PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName+" has crossed the ditch! Event is now over", PlayerHandler.getPlayers());
	}
	
	/*
	 * When host dies event is ended and rewards are handed out
	 */
	public static void handleDeath() {
		host.isDead = false;
		host.getHealth().setAmount(host.getHealth().getMaximum());
		host.sendMessage("You lost and did not make it out of the Wilderness in time!");
		PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName+" has been defeated! Event is now over", PlayerHandler.getPlayers());
		host.getPA().movePlayer(3360,3932);
		rewardPlayers();
		resetEvent();
	}
	
	
	private static void startTime() {//timer for how long the event can last for
		if(eventActive == true) {
			host.sendMessage("Event is currently active or is on cooldown... try again in a few minutes...");
			return;
		}
		eventActive = true;
		PlayerHandler.sendMessage("@red@[EVENT]@bla@"+host.playerName.toUpperCase()+" has started the Wilderness Escape event! Kill him to get rewards!", PlayerHandler.getPlayers());
		CycleEventHandler.getSingleton().addEvent(host, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if(eventActive == false) {
					container.stop();//stops actual event
					stop();
					return;
				}
				handleDeath();
				container.stop();//stops actual event
				stop();//resets player
			}
				@Override
				public void stop() {

					return;
				}
			}, 1600 );//120 = minute | 1700 = 14-15 minutes
	}
	/*
	 * Rewards given to players
	 */
	private static void rewardPlayers() {
		for(Player player: participants) {
			player.getItems().addItem(995, 10000);
			player.sendMessage("@red@[EVENT]@bla@You have participated in the Wilderness Escape event and have been given a reward!");
		}
	}
/*
 * Refreshes event
 */
	private static void resetEvent() {
		eventActive = false;
		host = null;
		Arrays.fill(reachedCheckpoint, false);
		currentCheckpoint = 0;
		participants.clear();
		for (CHECKPOINTS checkpoints: CHECKPOINTS.values()) {
			World.getWorld().getGlobalObjects().remove(860, checkpoints.absX, checkpoints.absY, 0);
		}
	}

	
	/*
	 * Spawn flags at checkpoints | flags despawn after 15 minutes
	 */
	private void spawnCheckpoints() {
		for (CHECKPOINTS checkpoints: CHECKPOINTS.values()) {
			World.getWorld().getGlobalObjects().add(new GlobalObject(860, checkpoints.absX, checkpoints.absY, 0, 2, 10, 1500, Integer.MAX_VALUE));
		}
	}


}// END OF CLASS
