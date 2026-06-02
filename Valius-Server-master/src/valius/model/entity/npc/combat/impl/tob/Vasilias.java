package valius.model.entity.npc.combat.impl.tob;

import java.util.List;
import java.util.stream.Stream;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.minigames.theatre.Theatre;
import valius.util.Misc;

/**
 * 
 * @author ReverendDread
 * Apr 20, 2019
 */
@ScriptSettings(
	npcIds = { 8355, 8356, 8357 }
)
public class Vasilias extends CombatScript {

	private Form form = new Form(8355, CombatType.MELEE, CombatType.MAGE);
	private static final int MELEE_ANIM = 7989, MAGIC_ANIM = 7989, RANGED_ANIM = 7999;
	private static final Projectile MAGIC_PROJ = new Projectile(1580, 25, 30, 0, 100, 0, 50);
	private static final Projectile RANGED_PROJ = new Projectile(1560, 25, 30, 0, 100, 0, 50);
	private static final Boundary ARENA = new Boundary(3290, 4243, 3301, 4254);
	private int attacks = 0;
	
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
		List<Entity> targets = getPossibleTargets(npc, true);
//		int lock = 0;
//		while (!Boundary.isIn(target, ARENA) && lock++ < 50) {
//			target = targets.get(Misc.random(0, targets.size() - 1));
//		}
		int damage = getRandomMaxHit(npc, target, form.getStyle(), 70);
		switch (npc.npcType) {
			case 8356:
				if (target.isPlayer()) {
					if (target.asPlayer().protectingMagic())
						damage = (damage / 3);
					npc.startAnimation(MAGIC_ANIM);				
					handleHit(npc, target, CombatType.MAGE, MAGIC_PROJ, new Hit(Hitmark.HIT, damage, 3));
				}	
				break;
			case 8355:
				if (target.isPlayer()) {
					if (target.asPlayer().protectingMelee())
						damage = (damage / 3);
					npc.startAnimation(MELEE_ANIM);
					handleHit(npc, target, CombatType.MELEE, new Hit(Hitmark.HIT, damage, 2));
				}	
				break;
			case 8357:
				if (target.isPlayer()) {
					if (target.asPlayer().protectingRange())
						damage = (damage / 3);
					npc.startAnimation(RANGED_ANIM);
					handleHit(npc, target, CombatType.RANGE, RANGED_PROJ, new Hit(Hitmark.HIT, damage, 3));
				}		
				break;
			default:
				break;
		}
		attacks--;
		return 4;
	}
	
	@Override
	public void process(NPC npc, Entity target) {
		if (canTransform()) {
			attacks = 3;
			CycleEvent event = new CycleEvent() {
				
				@Override
				public void execute(CycleEventContainer container) {
					form = getNextForm();
					npc.requestTransform(form.getId());
					npc.attackTimer = 3;
					container.stop();
				}
				
			};
			CycleEventHandler.getSingleton().addEvent(-1, npc, event, 2);
		}
		npc.freezeTimer = 0;
		super.process(npc, target);
	}
	
	@Override
	public void handleRecievedHit(NPC npc, Entity source, Damage damage) {
		if (form.getStyle() != damage.getCombatType() && damage.getAmount() > 0) {
			damage.setHitmark(Hitmark.HEAL_PURPLE);
			source.appendDamage((int) (damage.getAmount() * 0.75), Hitmark.HIT);
		}
	}
	
	@Override
	public void handleDeath(NPC npc, Entity source) {
		List<Entity> players = getPossibleTargets(npc, true);
		players.forEach(player -> {
			//player.asPlayer().killedNylocas = true;
			player.asPlayer().sendMessage("You have defeated Vasilias!");			
			Theatre instance = player.asPlayer().getTheatreInstance();
			if (instance != null) {
				player.asPlayer().sendMessage("Current time: "+instance.getTimeElapsed()+".");
			}
			player.asPlayer().sendMessage("You now have "+player.asPlayer().theatrePoints+" points!");
		});
	}
	
	/**
	 * Checks if vasilia can tranform again.
	 * @return
	 */
	private boolean canTransform() {
		return attacks <= 0;
	}
	
	private Form getNextForm() {
		Form next = form;
		while (next.getId() == form.getId()) {
			switch (Misc.random(0, 2)) {
				case 0:
					next = new Form(8355, CombatType.MELEE, CombatType.MAGE);
					break;
				case 1:
					next = new Form(8356, CombatType.MAGE, CombatType.RANGE);
					break;
				case 2:
					next = new Form(8357, CombatType.RANGE, CombatType.MELEE);
					break;
				default:
					next = new Form(8355, CombatType.MELEE, CombatType.MAGE);
			}
		}
		return next;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return form.getStyle() == CombatType.MELEE ? 2 : 8;
	}

	/* (non-Javadoc)
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}
	
	@Data
	protected class Form {
		
		private final int id;
		private final CombatType style;
		private final CombatType weakness;
		
	}
	
}
