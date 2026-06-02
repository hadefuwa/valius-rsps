package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.AttackNPC;
import valius.model.entity.player.combat.AttackPlayer;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

public class DragonDagger extends Special {

	public DragonDagger() {
		super(2.5, 1.50, 1.10, new int[] { 1215, 1231, 5680, 5698 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx100(252);
		player.startAnimation(1062);
		if (target instanceof NPC) {
			AttackNPC.calculateCombatDamage(player, (NPC) target, CombatType.MELEE, null);
		} else if (target instanceof Player) {
			AttackPlayer.calculateCombatDamage(player, (Player) target, CombatType.MELEE, null);
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
