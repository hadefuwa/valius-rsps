package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

public class Shove extends Special {

	public Shove() {
		super(2.5, 1.00, 1.00, new int[] { 1249, 1263, 5716, 5730, 11824, 11889 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(405);
		player.gfx100(253);
		if (target instanceof Player) {
			Player other = (Player) target;
			if (player.playerIndex > 0) {
				other.getPA().getSpeared(player.getX(), player.getY(), 1);
				other.gfx0(80);
			}
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
