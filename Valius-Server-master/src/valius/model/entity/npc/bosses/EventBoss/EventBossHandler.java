package valius.model.entity.npc.bosses.EventBoss;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import valius.discord.DiscordBot;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * @author Divine
 */
public class EventBossHandler {

	@Getter
	public enum EventBosses {
		
		TARN(6477, "Enraged Tarn", 10000, 35, 150, 450),
		ANTI_SANTA(5001, "Anti-Santa", 5000, 10, 100, 1000),
		GRAARDOR(5462, "Enraged Graardor", 10000, 35, 250, 600);

		private final int npcId;

		private final String bossName;

		private final int hp;

		private final int maxHit;

		private final int attack;

		private final int defence;

		private EventBosses(final int npcId, final String bossName, final int hp, final int maxHit, final int attack, final int defence) {
			this.npcId = npcId;
			this.bossName = bossName;
			this.hp = hp;
			this.maxHit = maxHit;
			this.attack = attack;
			this.defence = defence;
		}

		public static EventBosses getRandom() {
			return EventBosses.values()[Misc.random(0, EventBosses.values().length - 1)];
		}
		
	}

	@Getter
	private static EventBossSpawns currentLocation;

	@Getter
	private static EventBosses activeBoss;
	
	@Getter
	private static NPC activeNPC;
	
	@Getter
	private static CycleEvent cycleEvent;
	
	@Getter
	private static CycleEventContainer cycleEventContainer;

	public static void spawnNPC() {
		cycleEvent = new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if(activeBoss != null) {
					destroyBoss();
					container.setTick(Misc.toRandomCycle(30, 60, TimeUnit.MINUTES));//10 sec delay before respawning
					return;
				}
				container.setTick(generateTime());
				spawnBoss();
			}
		};
		cycleEventContainer = CycleEventHandler.getSingleton().addEvent(EventBossHandler.class, cycleEvent, generateTime());
	}    

	public static void destroyBoss() {
		if(activeBoss == null)
			return;
		NPCHandler.kill(activeBoss.npcId, 0);
		activeBoss = null;
	}

	public static void destroyEventChests() {
		  World.getWorld().getGlobalObjects().remove(new GlobalObject(EventBossChest.EVENT_CHEST, 3177, 3007, 0, 0, 11));
		  World.getWorld().getGlobalObjects().remove(new GlobalObject(EventBossChest.EVENT_CHEST, 3541, 3303, 0, 2, 11));
		  World.getWorld().getGlobalObjects().remove(new GlobalObject(EventBossChest.EVENT_CHEST, 2939, 3426, 0, 0, 11));
		  World.getWorld().getGlobalObjects().remove(new GlobalObject(EventBossChest.EVENT_CHEST, 2796, 3454, 0, 0, 11));
		  World.getWorld().getGlobalObjects().remove(new GlobalObject(EventBossChest.EVENT_CHEST, 2331, 3833, 0, 0, 11));
	}

	public static void spawnBoss() {
		currentLocation = EventBossSpawns.generateLocation();
		activeBoss = EventBosses.getRandom();
		destroyEventChests();
		activeNPC = NPCHandler.spawnNpc(activeBoss.getNpcId(), currentLocation.getX(), currentLocation.getY(), 0, 1, activeBoss.getHp(), activeBoss.getMaxHit(), activeBoss.getAttack(), activeBoss.getDefence()/*, false*/);
		GlobalMessages.send(activeBoss.getBossName() + " has appeared near " + currentLocation.getLocationName() + ".", GlobalMessages.MessageType.EVENT_BOSS);
		DiscordBot.sendMessage("event-bosses", activeBoss.getBossName() + " has appeared near " + currentLocation.getLocationName() + ".");
	}
	
	public static int generateTime(){
		return Misc.toRandomCycle(30, 60, TimeUnit.MINUTES);
	}
	
	public static Boundary getActiveBoundary() {
		return currentLocation.getBoundary();
	}

}