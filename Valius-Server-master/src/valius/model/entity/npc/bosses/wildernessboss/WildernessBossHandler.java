package valius.model.entity.npc.bosses.wildernessboss;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;

import lombok.Getter;
import valius.discord.DiscordBot;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.PlayerHandler;
import valius.util.Misc;

/**
 * 
 * @author Created by Crank Mar 9, 2019
 *
 * 6:32:47 PM
 */

public class WildernessBossHandler {

	@Getter
	public enum WildernessBossNpcs {
		
		JUSTICIAR(7858, "Justiciar Zachariah", 5000, 43, 500, 600),
		DERWEN(7859, "Derwen", 5000, 43, 250, 500),
		PORAZDIR(7860, "Porazdir", 5000, 43, 250, 400);
		//COLOSSAL_CHICKEN(8679, "Colossal Chicken", 10000, 5, 50, 800);
		
		private final int npcId;
		
		private final String bossName;
		
		private final int hp;
		
		private final int maxHit;
		
		private final int attack;
		
		private final int defence;
		
		private WildernessBossNpcs(final int npcId, final String bossName, final int hp, final int maxHit, final int attack, final int defence) {
			this.npcId = npcId;
			this.bossName = bossName;
			this.hp = hp;
			this.maxHit = maxHit;
			this.attack = attack;
			this.defence = defence;
		}
		
		public static WildernessBossNpcs getRandom() {
			return WildernessBossNpcs.values()[Misc.random(0, WildernessBossNpcs.values().length - 1)];
		}
	}
		
		private static WildernessBossHandler owner = new WildernessBossHandler();
		
		@Getter
		private static WildernessBossSpawn currentLocation;
		
		@Getter
		private static WildernessBossNpcs activeBoss;
		
		@Getter
		private static NPC activeNPC;
		
		@Getter
		private static CycleEvent cycleEvent;
		
		@Getter
		private static CycleEventContainer cycleEventContainer;
		
		public static int generateTime() {
			return Misc.toRandomCycle(20, 40, TimeUnit.MINUTES);
		}
		
		public static void spawnNPC() {
			cycleEvent = new CycleEvent() {


				@Override
				public void execute(CycleEventContainer container) {
					if(activeBoss != null) {
						destroyBoss();
						container.setTick(Misc.toRandomCycle(20, 40, TimeUnit.MINUTES));
						return;
					}
					spawnBoss();

					//PlayerHandler.getOptionalPlayer("Mod Divine").ifPresent(plr -> plr.getPlayerAssistant().movePlayer(Location.of(currentLocation.getX(), currentLocation.getY(), 0)));
					container.setTick(generateTime());

				}
			};
			cycleEventContainer = CycleEventHandler.getSingleton().addEvent(owner, cycleEvent, generateTime());
		}     
		
		public static void destroyBoss() {
			if(activeBoss == null)
				return;
			NPCHandler.kill(activeBoss.npcId, 0);
			activeBoss = null;
		}
		
		public static void spawnBoss() {
			currentLocation = WildernessBossSpawn.generateLocation();
			activeBoss = WildernessBossNpcs.getRandom();

			activeNPC = NPCHandler.spawnNpc(activeBoss.getNpcId(), currentLocation.getX(), currentLocation.getY(), 0, 1, activeBoss.getHp(), activeBoss.getMaxHit(), activeBoss.getAttack(), activeBoss.getDefence()/*, false*/);

			GlobalMessages.send(activeBoss.getBossName() + " has appeared near " + currentLocation.getLocationName() + ".", GlobalMessages.MessageType.EVENT_BOSS);
			GlobalMessages.send("Type ::Wildyboss to quickly teleport to the boss!", GlobalMessages.MessageType.NONE);
			DiscordBot.sendMessage("[WILDERNESS BOSS] ", activeBoss.getBossName() + " has appeared near " + currentLocation.getLocationName() + ".");

		}

		public static Boundary getActiveBoundary() {
			return currentLocation.getBoundary();
		}

		public static void giveRewards() {
			List<String> givenToIP = Lists.newArrayList();
			
			PlayerHandler.nonNullStream().filter(p -> p.WildyEventBossDamage > 0).forEach(p -> { 
				if(!givenToIP.contains(p.connectedFrom)) {
					if (p.WildyEventBossDamage >= 100) {
						WildernessBossRewards.execute(p);
						givenToIP.add(p.connectedFrom);
					} else if (p.WildyEventBossDamage < 100) {
						p.sendMessage("You must deal @red@100+</col> damage to receive a reward!");
					}
				} 
				p.WildyEventBossDamage = 0;
			});
		}
}
