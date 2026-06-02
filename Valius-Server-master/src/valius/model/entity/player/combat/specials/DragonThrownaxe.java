package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

public class DragonThrownaxe extends Special {

	public DragonThrownaxe() {
		super(2.5, 2.50, 2.50, new int[] { 20849 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(806);
		player.gfx100(1317);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
		
		}

	}
}
