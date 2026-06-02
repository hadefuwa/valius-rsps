package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.Special;
import valius.util.Misc;

public class KorasiSpecial extends Special {

	public KorasiSpecial() {
		super(5.0, 1.50, 1.50, new int[] { 33370 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(1711);
		if (damage.getAmount() > 0) {
			player.getDamageQueue().add(new Damage(target, player.getCombat().magicMaxHit() + (1 + Misc.random(30)), 2, player.playerEquipment, Hitmark.HIT, CombatType.MAGE));
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		target.asNPC().gfx0(1196);
	}

}