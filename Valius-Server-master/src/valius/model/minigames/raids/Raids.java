package valius.model.minigames.raids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import valius.clip.WorldObject;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.model.Location;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.skills.Skill;
import valius.model.items.ItemDefinition;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * Author @ Goon_
 * www.rune-server.com
 */

public class Raids {

	@Getter
	private long lastActivity = -1;
	private Map<String, Long> playerLeftAt = Maps.newConcurrentMap();
	private Map<String, Integer> raidPlayers = Maps.newConcurrentMap();
	private Map<String, Integer> activeRoom = Maps.newConcurrentMap();
	
	private int groupPoints;

	public void filterPlayers() {
		raidPlayers.entrySet()
		.stream()
		.filter(entry -> !PlayerHandler.getOptionalPlayer(entry.getKey()).isPresent())
		.forEach(entry -> raidPlayers.remove(entry.getKey()));
	}
	
	public void removePlayer(Player player) {
		raidPlayers.remove(player.getName().toLowerCase());
		groupPoints = raidPlayers.entrySet().stream().mapToInt((val) -> val.getValue()).sum();
		if(raidPlayers.isEmpty()) {
			lastActivity = System.currentTimeMillis();
		}
	}
	
	public List<Player> getPlayers(){
		List<Player> activePlayers = Lists.newArrayList();
		filterPlayers();
		raidPlayers.keySet().stream().forEach(playerName -> {
			PlayerHandler.getOptionalPlayer(playerName).ifPresent(player -> activePlayers.add(player));
		});
		return activePlayers;
	}

	/**
	 * Add points
	 */
	public int addPoints(Player player, int points) {
		if(!raidPlayers.containsKey(player.getName().toLowerCase()))
			return 0;
		int currentPoints = raidPlayers.getOrDefault(player.getName().toLowerCase(), 0);
		raidPlayers.put(player.getName().toLowerCase(), currentPoints + points);
		groupPoints = raidPlayers.entrySet().stream().mapToInt((val) -> val.getValue()).sum();
		return currentPoints + points;
	}
	
	public int getGroupPoints() {
		return groupPoints;
	}


	public int currentHeight;

	/**
	 * The current path
	 */
	private int path;

	/**
	 * The current way
	 */
	private int way;

	/**
	 * Current room
	 */
	public int currentRoom;

	/**
	 * Monster spawns (No Double Spawning)
	 */
	public boolean lizards = false;
	public boolean vasa = false;
	public boolean vanguard = false;
	public boolean ice = false;
	public boolean chest = false;
	public boolean mystic = false;
	public boolean tekton = false;
	public boolean mutta = false;
	public boolean archers = false;
	public boolean olm = false;
	public boolean olmDead = false;
	public boolean rightHand = false;
	public boolean leftHand = false;
	


	/**
	 * The door location of the current paths
	 */
	private ArrayList<Location> roomPaths= new ArrayList<Location>();

	/**
	 * The names of the current rooms in path
	 */
	private  ArrayList<String> roomNames = new ArrayList<String>();


	/**
	 * Current monsters needed to kill
	 */
	private int mobAmount = 0;


	/**
	 * Gets the start location for the path
	 * @return path
	 */
	public Location getStartLocation() {
		switch(path) {
		case 0:
			return RaidRooms.STARTING_ROOM_2.doorLocation;
		}
		return RaidRooms.STARTING_ROOM.doorLocation;
	}
	
	public Location getOlmWaitLocation() {
		switch(path) {
		case 0:
			return RaidRooms.ENERGY_ROOM.doorLocation;
		}
		return RaidRooms.ENERGY_ROOM_2.doorLocation;
	}
	/**
	 * Handles raid rooms
	 * @author Goon
	 *
	 */
	public enum RaidRooms{
		STARTING_ROOM("start_room",1,0,new Location(3299,5189)),
		LIZARDMEN_SHAMANS("lizardmen",1,0,new Location(3308,5208)),
		SKELETAL_MYSTIC("skeletal",1,0,new Location(3312,5217,1)),
		VASA_NISTIRIO("vasa",1,0,new Location(3312,5279)),
		VANGUARDS("vanguard",1,0,new Location(3312,5311)),
		ICE_DEMON("ice",1,0,new Location(3313,5346)),
		CHEST_ROOM("chest",1,0,new Location(3311,5374)),
		//SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		//ARCHERS_AND_MAGERS("archer",1,0,new Location(3309,5340,1)),
		MUTTADILE("muttadile",1,0,new Location(3311,5309,1)),
		TEKTON("tekton",1,0,new Location(3310,5277,1)),
		ENERGY_ROOM("energy",1,0,new Location(3275,5159)),
		OLM_ROOM_WAIT("olm_wait",1,0,new Location(3232,5721)),
		OLM_ROOM("olm",1,0,new Location(3232,5730)),

		STARTING_ROOM_2("start_room",1,1,new Location(3299,5189)),
		MUTTADILE_2("muttadile",1,1,new Location(3311,5309,1)),
		VASA_NISTIRIO_2("vasa",1,1,new Location(3312,5279)),
		VANGUARDS_2("vanguard",1,1,new Location(3312,5311)),
		ICE_DEMON_2("ice",1,1,new Location(3313,5346)),
		//ARCHERS_AND_MAGERS_2("archer",1,1,new Location(3309,5340,1)),
		CHEST_ROOM_2("chest",1,1,new Location(3311,5374)),
		//SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		SKELETAL_MYSTIC_2("skeletal",1,1,new Location(3312,5217,1)),
		TEKTON_2("tekton",1,1,new Location(3310,5277,1)),
		LIZARDMEN_SHAMANS_2("lizardmen",1,1,new Location(3308,5208)),
		ENERGY_ROOM_2("energy",1,1,new Location(3275,5159)),
		OLM_ROOM_WAIT_2("olm_wait",1,1,new Location(3232,5721)),
		OLM_ROOM_2("olm",1,1,new Location(3232,5730));

		/**
		 STARTING_ROOM_2("start_room",1,new Location(3299,5189)),
		 LIZARDMEN_SHAMANS_2("lizardmen",1,new Location(3308,5208)),
		 VASA_NISTIRIO_2("vasa",1,new Location(3312,5279)),
		 VANGUARDS_2("vanguard",1,new Location(3312,5311)),
		 ICE_DEMON_2("ice",1,new Location(3313,5346)),
		 CHEST_ROOM_2("chest",1,new Location(3311,5374)),
		 //SCAVENGER_ROOM_2("scavenger",1,new Location(3343,5217,1)),
		 SKELETAL_MYSTIC("skeletal",1,new Location(3312,5217,1)),
		 TEKTON_2("tekton",1,new Location(3310,5277,1)),
		 MUTTADILE_2("muttadile",1,new Location(3311,5309,1)),
		 ARCHERS_AND_MAGERS_2("archer",1,new Location(3309,5340,1)),
		 ENERGY_ROOM_2("energy",1,new Location(3275,5159)),
		 OLM_ROOM_WAIT_2("olm_wait",1,new Location(3232,5721)),
		 OLM_ROOM_2("olm",1,new Location(3232,5730));
		 **/

		private Location doorLocation;
		private int path;
		private int way;
		private String roomName;

		private RaidRooms(String name,int path1,int way1,Location door) {
			doorLocation=door;
			roomName=name;
			path=path1;
			way=way1;

		}

		public Location getDoor() {
			return doorLocation;
		}

		public int getPath() {
			return path;
		}
		public int getWay() {
			return way;
		}
		public String getRoomName() {
			return roomName;
		}


	}

	/**
	 * Starts the raid.
	 */
	public void startRaid(List<Player> players) {//Initializes the raid

		currentHeight = RaidConstants.currentRaidHeight;
		RaidConstants.currentRaidHeight += 4;
		
		path = 1;
		way= Misc.random(1);
		for(RaidRooms room : RaidRooms.values()) {
			if(room.getWay() == way) {
				roomNames.add(room.getRoomName());
				roomPaths.add(room.getDoor());
			}
		}
		for (Player lobbyPlayer : players) {//gets all players in lobby
			
			if(lobbyPlayer == null)
				continue;
			if(!lobbyPlayer.inRaidLobby()) {
				lobbyPlayer.sendMessage("You were not in the lobby you have been removed from the raid queue.");
				continue;
			}
			raidPlayers.put(lobbyPlayer.getName().toLowerCase(), 0);
			activeRoom.put(lobbyPlayer.getName().toLowerCase(), 0);
			lobbyPlayer.setRaidsInstance(this);
			
			lobbyPlayer.getPA().movePlayer(getStartLocation().getX(),getStartLocation().getY(), currentHeight);
			lobbyPlayer.sendMessage("@red@The raid has now started! Good Luck! type ::leaveraid to leave!");
			lobbyPlayer.sendMessage("[TEMP] @blu@If you get stuck in a wall, type ::stuckraids to be sent back to room 1!");


		}
		RaidConstants.raidGames.add(this);
	}
	


	public boolean hadPlayer(Player player) {
		long leftAt = playerLeftAt.getOrDefault(player.getName().toLowerCase(), (long) -1);
		
		return leftAt > 0;
	}
	
	public boolean login(Player player) {
		long leftAt = playerLeftAt.getOrDefault(player.getName().toLowerCase(), (long) -1);
		if(leftAt > 0) {
			playerLeftAt.remove(player.getName().toLowerCase());
			if(System.currentTimeMillis() - leftAt <= 60000) {
				raidPlayers.put(player.getName().toLowerCase(), 0);
				player.setRaidsInstance(this);
				player.sendMessage("@red@You rejoin the raid!");
				lastActivity = -1;
				return true;
			}
		}
		
		return false;
	}

	public void logout(Player player) {
		player.setRaidsInstance(null);
		removePlayer(player);
		playerLeftAt.put(player.getName().toLowerCase(), System.currentTimeMillis());
	}
	
	public void resetRoom(Player player) {
		this.activeRoom.put(player.getName().toLowerCase(), 0);
	}

	/**
	 * Kill all spawns for the raid leader if left
	 * @param player
	 */
	public void killAllSpawns() {
		NPCHandler.kill(currentHeight, currentHeight + 3, 
					394, 3341, 7563, 7566, 7585, 7560,
					7544, 7573, 7604, 7606, 7605, 7559,
					7527, 7528, 7529, 7553, 7554, 7555
				);
	}

	/**
	 * Leaves the raid.
	 * @param player
	 */
	public void leaveGame(Player player) {
		if (System.currentTimeMillis() - player.infernoLeaveTimer < 15000) {
			player.sendMessage("You cannot leave yet, wait a couple of seconds and try again.");
			return;
		}
		player.sendMessage("@red@You have left the Chambers of Xeric.");
		player.getPA().movePlayer(3050, 9952, 0);
		player.setRaidsInstance(null);
		removePlayer(player);
		player.specRestore = 120;
		player.specAmount = 10.0;
		player.setRunEnergy(100);
		player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
		player.getSkills().resetToActualLevel(Skill.PRAYER);
		player.getHealth().removeAllStatuses();
		player.getHealth().reset();
		player.getPA().refreshSkill(5);
	}





	int[] rarerewards = {22296, 21000, 21009, 21028, 20849, 21031, 22296, 33505, 33506, 33507, 21000, 21009, 21028, 20849, 21031, 20997, 20784, 21006, 21015,  21012, 21018, 21021, 21024, 20784, 21006, 21015, 21012, 21018, 21021, 21024, 33124, 21000 };
	int[] commonrewards = {2528, 13307,537,995,892,11212,11230,208,210,212,214,3052,216,2486,218,220, 448, 450, 452, 1632, 10248}; //{item, maxAmount}

	
	/**
	 * Handles giving the raid reward
	 */
	public void giveReward(Player player) {
		int rewardChance = Misc.random(100);
		int olmletChance = Misc.random(64);
		
		if (player.raidCount == 10)
			player.getItems().addItemUnderAnyCircumstance(22388, 1);
		if (player.raidCount == 25)
			player.getItems().addItemUnderAnyCircumstance(22390, 1);
		if (player.raidCount == 50)
			player.getItems().addItemUnderAnyCircumstance(22392, 1);
		if (player.raidCount == 100)
			player.getItems().addItemUnderAnyCircumstance(22394, 1);
		if (player.raidCount == 250) {
			player.getItems().addItemUnderAnyCircumstance(22396, 1);
			GlobalMessages.send(player.playerName + " has completed 250 Raids and obtained the Xeric's Champion Cape!", GlobalMessages.MessageType.NEWS);
		}

		if(rewardChance > 97) {
			if (olmletChance == 1 && player.summonId == -1) {
				player.summonId = 7519;
			} else if (player.summonId > 0) {
				player.getItems().addItemUnderAnyCircumstance(20851, 1);
			}
			giveRareReward(player);
		} else {
			giveCommonReward(player);
		}
		player.getItems().addItemUnderAnyCircumstance(995, Misc.random(2500000, 5000000));
		player.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1000, 2000));
		
		//Casket for every 10 raids complete
		 for (int chest_interval = 10; chest_interval <= player.raidCount; chest_interval += 10) {
				if (player.raidCount % 10 == 0) {
					player.getItems().addItemUnderAnyCircumstance(33941, 1);
					player.sendMessage("You receive a Casket for Finishing your raid.");
				//System.out.println("chest interval:" + chest_interval + "");
				return;
			} else {
				int progress = player.raidCount % 10;
				player.sendMessage("You will receive a Chamber of Xeric casket in @blu@" + Math.subtractExact(10, progress) + "/10</col> more raids.");
				return;
			}
		}
	}

	/**
	 * Handles giving a rare reward.
	 */

	public void giveRareReward(Player player) {
		//p.gfx0(1368);
		int rareitem = 0;
		rareitem = Misc.random(rarerewards.length-1);
		if(rareitem < 0) {
			rareitem = Misc.random(rarerewards.length);
		}
		player.raidReward[0][0] = rarerewards[rareitem];
		GlobalMessages.send(player.playerName + " has received a rare item: "+ ItemDefinition.forId(player.raidReward[0][0]).getName() + " from Raids!", GlobalMessages.MessageType.LOOT);
		if(player.raidReward[0][0] == 20849) {
			player.raidReward[0][1] = 500;
		}else {
			player.raidReward[0][1] = 1;
		}

		//p.getItems().addItem(player.raidReward[0][0], player.raidReward[0][1]);
	}
	/**
	 * Handles giving a common reward
	 */
	public void giveCommonReward(Player player) {
		//p.gfx0(277);
		int commonitem = 0;
		commonitem = Misc.random(commonrewards.length-1);
		player.raidReward[0][0] = commonrewards[commonitem];

		switch(player.raidReward[0][0]) {
		case 13307://bloodmoney
			player.raidReward[0][1] = 1000 + Misc.random(1000);
			break;
		case 537://dragon bone
			player.raidReward[0][1] = 75 + Misc.random(150);
			break;
		case 995://gold coins
			player.raidReward[0][1] = 1000000 + Misc.random(750000);
			break;
		case 892://rune arrow
			player.raidReward[0][1] = 250 + Misc.random(1500);
			break;
		case 11212://dragon arrow
			player.raidReward[0][1] = 150 + Misc.random(350);
			break;
		case 11230://dragon dart
			player.raidReward[0][1] = 100 + Misc.random(200);
			break;
		case 208://grimy rannar
			player.raidReward[0][1] = 50 + Misc.random(84);
			break;
		case 210://grimy irit
			player.raidReward[0][1] = 75 + Misc.random(268);
			break;
		case 212://grimy avantoe
			player.raidReward[0][1] = 50 + Misc.random(154);
			break;
		case 214://grimy kwuarm
			player.raidReward[0][1] = 50 + Misc.random(123);
			break;
		case 3052://grimy snapdragon
			player.raidReward[0][1] = 30 + Misc.random(61);
			break;
		case 216://grimy cadatine
			player.raidReward[0][1] = 50 + Misc.random(119);
			break;
		case 2486://grimy lantadyme
			player.raidReward[0][1] = 60 + Misc.random(246);
			break;
		case 218://grimy dwarf weed
			player.raidReward[0][1] = 50 + Misc.random(216);
			break;
		case 220://grimy torsol
			player.raidReward[0][1] = 50 + Misc.random(153);
			break;
		case 448://mithril ore
			player.raidReward[0][1] = Misc.random(500);
			break;
		case 450://adamant ore
			player.raidReward[0][1] = Misc.random(229);
			break;
		case 452://runite ore
			player.raidReward[0][1] = Misc.random(47);
			break;
		case 10248://hard casket
			player.raidReward[0][1] = 1;
			break;
		case 1632://dragonstone uncut
			player.raidReward[0][1] = 20 + Misc.random(10);
			break;
		case 560://death rune
			player.raidReward[0][1] = 50 + Misc.random(225);
			break;
		case 565://blood rune
			player.raidReward[0][1] = 25 + Misc.random(200);
			break;
		case 566://soul runes
			player.raidReward[0][1] = 200 + Misc.random(6554);
			break;
		case 8781://teak planks
			player.raidReward[0][1] = 150 + Misc.random(1350);
			break;
		case 8783://mahogany planks
			player.raidReward[0][1] = 100 + Misc.random(650);
			break;
		case 13440://raw anglerfish
			player.raidReward[0][1] = 125 + Misc.random(250);
			break;
		case 1514://magic logs
			player.raidReward[0][1] = 75 + Misc.random(225);
			break;
		default:
			player.raidReward[0][1]=1;
			break;

		}
	}

	final int OLM = 7554;
	final int OLM_RIGHT_HAND= 7553;
	final int OLM_LEFT_HAND = 7555;

	public void handleMobDeath(Player killer, int npcType) {

		int height = currentHeight;
		
		mobAmount -= 1;
		switch(npcType) {
		case OLM:
			/*
			 * Crystal & Olm removal after olm's death
			 */
			olmDead = true;
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3233, 5751, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3232, 5749, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3232, 5750, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3233, 5749, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3233, 5750, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(-1, 3233, 5750, currentHeight, 3, 10));
			//World.getWorld().getGlobalObjects().remove(new GlobalObject(29881, 3220, 5738, currentHeight, 3, 10));
			//World.getWorld().getGlobalObjects().add(new GlobalObject(30028, 3233, 5751, getHeight(p), 4, 10)); chest

			getPlayers().stream().forEach(player -> {
				player.getPA().sendPlayerObjectAnimation(player, 3220, 5738, 7348, 10, 3, currentHeight);
				player.sendMessage("@red@Congratulations you have defeated The Great Olm and completed the raid!");
				player.sendMessage("@red@Please go up the stairs beyond the Crystals to get your reward " );
			});
			return;

		case OLM_RIGHT_HAND:
			rightHand = true;
			if(leftHand == true) {
				getPlayers().stream().forEach(player ->	player.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable."));
				World.getWorld().getGlobalObjects().add(new GlobalObject(29888, 3220, 5733, currentHeight, 3, 10));
			}else {
				getPlayers().stream().forEach(player ->	player.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!"));
			}
			//World.getWorld().getGlobalObjects().remove(new GlobalObject(29887, 3220, 5733, currentHeight, 3, 10));

			//World.getWorld().getGlobalObjects().add(new GlobalObject(29888, 3220, 5733, currentHeight, 3, 10));
			getPlayers().stream()
			.forEach(otherPlr -> {
				otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, 3220, 5733, 7352, 10, 3, currentHeight);
				if(leftHand) {
					otherPlr.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable.");
				} else {
					otherPlr.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!");
				}
			});
		
			return;
		case OLM_LEFT_HAND:
			leftHand = true;
			World.getWorld().getGlobalObjects().remove(new GlobalObject(29884, 3220, 5743, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(29885, 3220, 5743, currentHeight, 3, 10));
			getPlayers().stream()
			.forEach(otherPlr -> {
				otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, 3220, 5743, 7360, 10, 3, currentHeight);
				if(rightHand) {
					otherPlr.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable.");
				} else {
					otherPlr.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!");
				}
			
			});
			if(rightHand == true) {
				World.getWorld().getGlobalObjects().remove(new GlobalObject(29884, 3220, 5743, currentHeight, 3, 10));
				World.getWorld().getGlobalObjects().add(new GlobalObject(29885, 3220, 5743, currentHeight, 3, 10));
				getPlayers().stream().forEach(player ->	player.sendMessage("@red@ You have defeated both of The Great Olm's hands he is now vulnerable."));
			}else {
				getPlayers().stream().forEach(player ->	player.sendMessage("@red@ You have defeated one of The Great Olm's hands destroy the other one quickly!"));
			}
			return;
		}
		if(killer != null) {
			int randomPoints = Misc.random(500);
			int newPoints = addPoints(killer, randomPoints);
		
			killer.sendMessage("@red@You receive "+ randomPoints +" points from killing this monster.");
			killer.sendMessage("@red@You now have "+ newPoints +" points.");
		}
		if(mobAmount <= 0) {
			getPlayers().stream().forEach(player ->	player.sendMessage("@red@The room has been cleared and you are free to pass."));
			roomSpawned = false;
		}else {
			getPlayers().stream().forEach(player ->	player.sendMessage("@red@There are "+ mobAmount+" enemies remaining."));
		}
	}
	/**
	 * Spawns npc for the current room
	 * @param currentRoom The room
	 */
	public void spawnNpcs(int currentRoom) {

		int height = currentHeight;

		switch(roomNames.get(currentRoom)) {
		case "lizardmen":
			if(lizards) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7573, 3274, 5262, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
				NPCHandler.spawn(7573, 3282, 5266, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
				NPCHandler.spawn(7573, 3275, 5269, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7573, 3307,5265, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
				NPCHandler.spawn(7573, 3314,5265, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
				NPCHandler.spawn(7573, 3314,5261, height, 1, 350, 25, 300, 300,true).setRaidsInstance(this);
			}
			lizards = true;
			mobAmount+=3;
			break;
		case "vasa":
			if(vasa) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7566, 3280,5295, height, -1, 650, 25, 250, 300,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7566, 3311,5295, height, -1, 650, 25, 250, 300,true).setRaidsInstance(this);
			}
			vasa = true;
			mobAmount+=1;
			break;
		case "vanguard":
			if(vanguard) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7527, 3277,5326, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this);// melee vanguard
				NPCHandler.spawn(7528, 3277,5332, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this); // range vanguard
				NPCHandler.spawn(7529, 3285,5329, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this); // magic vanguard
			}else {
				NPCHandler.spawn(7527, 3310,5324, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this); // melee vanguard
				NPCHandler.spawn(7528, 3310,5331, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this); // range vanguard
				NPCHandler.spawn(7529, 3316,5331, height, -1, 300, 25, 140, 200,true).setRaidsInstance(this);// magic vanguard
			}
			vanguard = true;
			mobAmount+=3;
			break;
		case "ice":
			if(ice) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7585, 3273,5365, height, -1, 750, 45, 350, 300,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7585, 3310,5367, height, -1, 750, 45, 350, 300,true).setRaidsInstance(this);
			}
			ice = true;
			mobAmount+=1;
			break;
		case "skeletal":
			if(mystic) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7604, 3279,5271, height+1, -1, 250, 25, 400, 250,true).setRaidsInstance(this);
				NPCHandler.spawn(7605, 3290,5268, height+1, -1, 250, 25, 500, 250,true).setRaidsInstance(this);
				NPCHandler.spawn(7606, 3279,5264, height+1, -1, 250, 25, 400, 250,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7604, 3318,5262,height+1, -1, 250, 25, 400, 250,true).setRaidsInstance(this);
				NPCHandler.spawn(7605, 3307,5258, height+1, -1, 250, 25, 500, 250,true).setRaidsInstance(this);
				NPCHandler.spawn(7606, 3301,5262, height+1, -1, 250, 25, 400, 250,true).setRaidsInstance(this);
			}
			mobAmount+=3;
			mystic = true;
			break;
		case "tekton":
			if(tekton) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7544, 3280,5295, height+1, -1, 1200, 45, 450, 300,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7544, 3310, 5293, height+1, -1, 1200, 45, 450, 300,true).setRaidsInstance(this);
			}
			mobAmount+=1;
			tekton = true;
			break;
		case "muttadile":
			if(mutta) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7563, 3276,5331, height + 1, 1, 750, 25, 400, 400,true).setRaidsInstance(this);
			}else {
				NPCHandler.spawn(7563, 3308,5331, height + 1, 1, 750, 25, 400, 400,true).setRaidsInstance(this);
			}
			mobAmount+=1;
			mutta = true;
			break;
		case "archer":
			if(archers) {
				return;
			}
			if(path == 0) {
				NPCHandler.spawn(7559, 3287,5364, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3287,5363, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3285,5363, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3285,5364, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly ranger

				NPCHandler.spawn(7560, 3286,5369, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3284,5369, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3286,5370, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3284,5370, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly mager
			}else {
				NPCHandler.spawn(7559, 3319,5363, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3317,5363, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3317,5364, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly ranger
				NPCHandler.spawn(7559, 3319,5364, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly ranger

				NPCHandler.spawn(7560, 3318,5370, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3318,5369, height + 1, -1, 150, 25, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3316,5369, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly mager
				NPCHandler.spawn(7560, 3316,5370, height + 1, -1, 150, 30, 100, 100,true).setRaidsInstance(this); // deathly mager
			}
			archers = true;
			mobAmount+=8;
			break;
		case "olm":
			if(olm) {
				return;
			}

			World.getWorld().getGlobalObjects().add(new GlobalObject(29884, 3220, 5743, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(29887, 3220, 5733, currentHeight, 3, 10));
			World.getWorld().getGlobalObjects().add(new GlobalObject(29881, 3220, 5738, currentHeight, 3, 10));
			getPlayers().stream()
			.forEach(otherPlr -> {
				otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, 3220, 5733, 7350, 10, 3, currentHeight);
				otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, 3220, 5743, 7354, 10, 3, currentHeight);
				otherPlr.getPA().sendPlayerObjectAnimation(otherPlr, 3220, 5738, 7335, 10, 3, currentHeight);
			});
			NPCHandler.spawn(7553, 3223, 5733, height, -1, 500, 33, 272, 272,false).setRaidsInstance(this); // left claw
			NPCHandler.spawn(7554, 3223, 5738, height, -1, 1600, 33, 272, 272,true).setRaidsInstance(this); // olm head
			NPCHandler.spawn(7555, 3223, 5742, height, -1, 500, 33, 272, 272,false).setRaidsInstance(this); // right claw

			olm = true;
			mobAmount+=3;
			break;
			default:
				roomSpawned = false;
				
				break;
		}
		
	}
	/**
	 * Handles object clicking for raid objects
	 * @param player The player
	 * @param objectId The object id
	 * @return
	 */
	public boolean handleObjectClick(Player player, WorldObject worldObject) {
		int objectId = worldObject.getId();
		int objectX = worldObject.getX();
		int objectY = worldObject.getY();
		switch(objectId) {
		case 29789://First entrance
		case 29734:
		case 29879:
		case 27122:
		case 4909:
			nextRoom(player);
			return true;
		case 30066:

			return true;

		case 29778:
			if(!olmDead) {
				if(objectX == 3298 && objectY == 5185) {
					player.getDH().sendDialogues(10000, -1);
					return true;
				}
				player.sendMessage("You need to complete the raid!");
				return true;
			}
			if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
				return true;
			}
			player.lastMysteryBox = System.currentTimeMillis();
			giveReward(player);
			player.getItems().addItemUnderAnyCircumstance(player.raidReward[0][0], player.raidReward[0][1]);
			player.raidCount+=1;
			DailyTasks.increase(player, PossibleTasks.SKELETAL_MYSTICS_RAID);
			DailyTasks.increase(player, PossibleTasks.TEKTON_RAID);
			player.sendMessage("@red@You receive your reward." );
			player.sendMessage("@red@You have completed "+player.raidCount+" raids." );
			leaveGame(player);
			break;

		case 30028:
			player.getPA().showInterface(57000);

			return true;
		}
		return false;
	}
	
	private boolean roomSpawned;
	/**
	 * Goes to the next room, Handles spawning etc.
	 */
	public void nextRoom(Player player) {
		//player.sendMessage("nextroom3");
		if(activeRoom.getOrDefault(player.getName().toLowerCase(), 0) == currentRoom && mobAmount > 0) {
			player.sendMessage("You need to defeat the current room before moving on!");
			return;
		}
		if(!roomSpawned) {
			currentRoom+=1;
			roomSpawned = true;
			spawnNpcs(currentRoom);
		}
		int playerRoom = activeRoom.getOrDefault(player.getName().toLowerCase(), 0) + 1;
		player.getPA().movePlayer(roomPaths.get(playerRoom).getX(),roomPaths.get(playerRoom).getY(),roomPaths.get(playerRoom).getZ() == 1 ? currentHeight + 1 :currentHeight);
		activeRoom.put(player.getName().toLowerCase(), playerRoom);

	}
}

