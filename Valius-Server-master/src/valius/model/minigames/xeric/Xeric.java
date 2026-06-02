package valius.model.minigames.xeric;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Setter;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.util.Misc;

/**
 * 
 * @author Patrity, Arithium, Fox News
 * 
 */
public class Xeric {

	private int killsRemaining;
	private int activeWave = -1;
	@Setter
	private int index;

	
	private List <Player> team = new ArrayList<Player>();
	private List<NPC> npcSpawns = new ArrayList<>();
	
	public Xeric(List<Player> lobbyPlayers) {
		team.addAll(lobbyPlayers);
	}

	public void spawn() {
		activeWave++;
		if (activeWave >= XericWaveConstants.MAX_WAVE) {
			stop();
			return;
		}
		filterTeam();
		if (team.size() == 0) {
			System.err.println("Size of team is 0.");
			destroy();
			return;
		}
		if (activeWave != 0 && activeWave < XericWaveConstants.MAX_WAVE) {
			for (Player player : team) 
				player.sendMessage("You are now on wave " + (activeWave + 1) + " of " + XericWaveConstants.MAX_WAVE + ". - Total damage done: " + player.xericDamage + ".", 255);
		}
		killAllSpawns();
		final int[] spawnVariables = XericWaveConstants.LEVEL[activeWave];
		final Xeric instance = this;
		CycleEventHandler.getSingleton().addEvent(instance, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				// TODO Auto-generated method stub
				killsRemaining = spawnVariables.length;

				for (int i = 0; i < killsRemaining; i++) {
					int npcType = spawnVariables[i];
					Location near = Misc.randomTypeOfList(team).getLocation().getRandomNear(NPCDefinitions.get(npcType).getSize(), 5);
					if(near == null) {
						int index = Misc.random(XericWaveConstants.SPAWN_DATA.length - 1);
						int x = (XericWaveConstants.SPAWN_DATA[index][0] + (Misc.random(-3,3)));
						int y = (XericWaveConstants.SPAWN_DATA[index][1] + (Misc.random(-3,3)));
						near = Location.of(x, y, getHeight());
					}
					NPC npc = NPCHandler.spawn(npcType, near.getX(), near.getY(), getHeight(), 1, XericWaveConstants.getHp(npcType), XericWaveConstants.getMax(npcType), XericWaveConstants.getAtk(npcType), XericWaveConstants.getDef(npcType), true);
					npc.setNoRespawn(true);
					npc.setXeric(instance);
					System.out.println("NPC spawned at " + near.toString());
					npcSpawns.add(npc);

				}
				container.stop();
			}
		}, 5);
	}
	
	public void stop() {
		for (Player p : team) {
			if(p == null)
				continue;
			XericRewards.giveReward(p.xericDamage, p);
			p.getPA().movePlayer(3050, 9950, 0);
			p.getDH().sendStatement("Congratulations on finishing the Trials of Xeric!");
			p.nextChat = 0;
			p.setRunEnergy(100);
			p.setXeric(null);
		}
		destroy();
	}
	
	public void destroy() {
		killAllSpawns();
		npcSpawns.clear();
		team.clear();
	}

	public void leaveGame(Player player, boolean death) {
		player.getPA().movePlayer(3050, 9950, 0);
		player.getDH().sendStatement((death ? "Unfortunately you died" : "You've left the game") + " on wave " + (player.getXeric().activeWave + 1) + ". Better luck next time.");
		player.nextChat = 0;
		removePlayer(player);
	}

	public void killAllSpawns() {

		for (NPC npc : getSpawns()) {
			if (npc != null) {
				NPCHandler.npcs[npc.getIndex()] = null;
			}
		}
		for (int i = 0; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (Boundary.isIn(NPCHandler.npcs[i], Boundary.XERIC) && NPCHandler.npcs[i].getHeight() == getHeight()) {
					NPCHandler.npcs[i] = null;

				}
			}
		}
	}
	
	public static void drawInterface(Player p) {//draws the interface for how much time is left in lobby
		int seconds = XericLobby.xericLobbyTimer;
		p.getPA().sendFrame126("Raid begins in: @gre@"+(seconds - (XericLobby.timeLeft %seconds)), 6570);
		p.getPA().sendFrame126("", 6571);
		p.getPA().sendFrame126("", 6572);
		p.getPA().sendFrame126("", 6664);
		p.getPA().walkableInterface(6673);
	}

	public void filterTeam() {
		List<Player> filtered = team.stream().filter(Objects::nonNull).filter(plr -> plr.getSession().isConnected()).collect(Collectors.toList());
		team.clear();
		team.addAll(filtered);
	}
	
	public List<NPC> getSpawns() {
		return npcSpawns;
	}

	public void setXericTeam(List<Player> list) {
		this.team = new ArrayList<>(list);
	}
	public void removePlayer(Player player) {
		
		team.remove(player);
		player.setXeric(null);
		if (team.size() == 0) {
			destroy();
		}
		
	}

	public int getHeight() {
		return index * 4;
	}

	public void decrementKills() {
		killsRemaining--;
		if(killsRemaining <= 0) {
			spawn();
		}
	}

	public void killed(NPC npc) {
		npc.setXeric(null);
		this.npcSpawns.remove(npc);
		this.decrementKills();
	}
}
