package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.Special;

public class DragonClaws extends Special {

	public DragonClaws() {
		super(5.0, 1.5, 1.2, new int[] { 20784 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(7514);
		player.gfx0(1282);
		int halvedHit = damage.getAmount() == 0 ? 0 : damage.getAmount() / 2;
		int finalHit = halvedHit == 0 ? 0 : halvedHit / 2;
		player.getDamageQueue().add(new Damage(target, halvedHit, player.hitDelay, player.playerEquipment, halvedHit > 0 ? Hitmark.HIT : Hitmark.MISS, CombatType.MELEE));
		player.getDamageQueue().add(new Damage(target, finalHit, player.hitDelay + 1, player.playerEquipment, finalHit > 0 ? Hitmark.HIT : Hitmark.MISS, CombatType.MELEE));
		player.getDamageQueue().add(new Damage(target, finalHit, player.hitDelay + 1, player.playerEquipment, finalHit > 0 ? Hitmark.HIT : Hitmark.MISS, CombatType.MELEE));
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
