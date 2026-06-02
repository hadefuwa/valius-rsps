package valius.model.entity.npc.combat.impl.tob;

import java.util.List;
import java.util.stream.Collectors;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

/**
 * 
 * @author ReverendDread
 * Apr 20, 2019
 */
@ScriptSettings(
	npcIds = { 8388 }
)
public class Sotetseg extends CombatScript {

	//walk anim - 8136
	
	private static final Projectile RED_CUBE = new Projectile(1606, 35, 30, 1, 200, 0, 50);
	private static final Projectile BLACK_CUBE = new Projectile(1607, 35, 30, 0, 200, 0, 50);
	private static final Projectile BLUE_CUBE = new Projectile(1609, 35, 30, 0, 110, 0, 50);
	private static final Projectile RED_BALL = new Projectile(1604, 35, 30, 1, 350, 0, 150);
	private static final Graphic RED_BALL_SPLASH = new Graphic(1605);
	private static final Boundary ARENA = new Boundary(3273, 4308, 3287, 4334);
	private static final int ATTACK = 8139;
	
	@Override
	public void init(NPC npc) {
		npc.getHealth().isNotSusceptibleTo(HealthStatus.POISON);
		npc.getHealth().isNotSusceptibleTo(HealthStatus.VENOM);
		npc.setNoRespawn(true);
		npc.setNeverWalkHome(true);
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC, ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		if (Misc.random(0, 10) == 0) { //big spec
			npc.startAnimation(ATTACK - 1);
			sendProjectile(npc, target, RED_BALL);
			CycleEvent event = new CycleEvent() {

				int cycle = 0;
				int inRange = 0;
				List<Entity> targets = 
						getPossibleTargets(npc, true)
						/*.stream()
						.filter(entity -> entity.getTheatreInstance() == npc.getTheatreInstance())
						.collect(Collectors.toList())*/;
				int damage = targets.size() > 2 ? Misc.random(135, 150) : Misc.random(55, 70);
				
				@Override
				public void execute(CycleEventContainer container) {
					if (cycle >= 12) {
						if (npc.isDead) {
							container.stop();
							return;
						}
						for (Entity entity : targets) {
							if (entity.isPlayer() && !entity.asPlayer().isDead && entity.withinDistance(target, 1)) {
								inRange++;
							}
						}
						for (Entity entity : targets) {
							if (entity.isPlayer() && !entity.asPlayer().isDead && entity.withinDistance(target, 1)) {
								handleHit(npc, entity, CombatType.SPECIAL, new Hit(Hitmark.HIT, (int) (damage / inRange), 0, true));
								entity.asPlayer().gfx(RED_BALL_SPLASH.getId(), RED_BALL_SPLASH.getHeight());
							}
						}
						container.stop();
					}
					cycle++;
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
			return 9;
		}
		
		npc.startAnimation(ATTACK);
		//Normal attack
		CycleEvent event = new CycleEvent() {

			int cycle = 0;
			List<Entity> chainable = getPossibleTargets(npc, true);
			
			@Override
			public void execute(CycleEventContainer container) {
				if (npc.isDead) {
					container.stop();
					return;
				}
				if (cycle == 0) {
					sendProjectile(npc, target, RED_CUBE);
					int damage = getRandomMaxHit(npc, target, CombatType.MAGE, 50);
					if (target.isPlayer()) {
						if (target.asPlayer().protectingMagic())
							damage = (damage / 2);
						handleHit(npc, target, CombatType.MAGE, new Hit(Hitmark.HIT, damage, 6));
					}
				} else if (cycle == 5 && chainable.size() > 1) {
					Entity chain = Misc.randomTypeOfList(chainable
							.stream()
							.filter(player -> Boundary.isIn(player, ARENA))
							.filter(player -> player != target)
							.collect(Collectors.toList()));
					if(chain != null) {
						int damage = getRandomMaxHit(npc, chain, CombatType.RANGE, 50);
						if (chain.isPlayer() && target.isPlayer() && !chain.asPlayer().isDead) {
							sendProjectile(target, chain, BLACK_CUBE);
							if (chain.asPlayer().protectingRange())
								damage = (damage / 2);
							handleHit(npc, chain, CombatType.RANGE, new Hit(Hitmark.HIT, damage, 4));
						}
					}
					container.stop();
				}
				cycle++;
			}
			
		};
		CycleEventHandler.getSingleton().addEvent(-1, npc, event, 1);
		return 5;
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		List<Player> players = npc.getTheatreInstance().getPlayers();
		players.forEach(player -> {
			//player.asPlayer().killedSotetseg = true;
			player.sendMessage("You have defeated Sotetseg!");			
			Theatre instance = player.asPlayer().getTheatreInstance();
			if (instance != null) {
				player.asPlayer().sendMessage("Current time: "+instance.getTimeElapsed()+".");
			}
			player.asPlayer().sendMessage("You now have "+player.theatrePoints+" points!");
		});
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 16;
	}
	
	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getFollowDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getFollowDistance(NPC npc) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}
	
	@Override
	public boolean isAggressive(NPC npc) {
		return true;
	}

}
