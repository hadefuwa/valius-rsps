package valius.model.entity.npc.combat.impl.xeric;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.Graphic;
import valius.model.entity.npc.combat.Hit;
import valius.model.entity.npc.combat.Projectile;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Hitmark;

@ScriptSettings(
	npcNames = { "Deathly Mage" },
	npcIds = { 7560 }
)

public class DeathlyMage extends CombatScript {

	@Override
	public int attack(NPC npc, Entity target) {
		int damage = getRandomMaxHit(npc, target, CombatType.MAGE, 35);
		npc.startAnimation(7855);
		handleHit(npc, target, CombatType.MAGE, new Projectile(1465, 40, 40, 0, 100, 0, 50), new Graphic (1028, 0), new Hit(Hitmark.HIT, damage, 4));
		return 5;
	}

	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}
	
	@Override
	public boolean ignoreProjectileClipping() {
		return true;
	}

}
