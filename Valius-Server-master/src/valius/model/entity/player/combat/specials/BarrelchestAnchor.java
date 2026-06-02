package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

public class BarrelchestAnchor extends Special {

	public BarrelchestAnchor() {
		super(5.0, 1.00, 1.10, new int[] { 10887 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx0(1027);
		player.startAnimation(5870);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}
}