package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.combat.range.RangeData;

/**
 * 
 * @author Divine | 10:28:27 a.m. | Nov. 28, 2019
 *
 */
public class InfernalStaff extends Special {

	public InfernalStaff() {
		super(2.5, 2.0, 1.80, new int[] { 33277, 33761 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
			player.startAnimation(1711);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		target.asNPC().gfx100(1676);
	}

}