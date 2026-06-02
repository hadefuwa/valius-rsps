package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

public class VestaSpear extends Special {

	public VestaSpear() {
		super(3.5, 1.25, 1.10, new int[] { 13905 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx0(1240);
		player.startAnimation(7294);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
