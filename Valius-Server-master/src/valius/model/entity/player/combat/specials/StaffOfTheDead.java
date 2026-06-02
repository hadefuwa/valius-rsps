package valius.model.entity.player.combat.specials;

import valius.event.impl.StaffOfTheDeadEvent;
import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.world.World;

public class StaffOfTheDead extends Special {

	public StaffOfTheDead() {
		super(10.0, 1.0, 1.0, new int[] { 11791, 12904 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx(1228, 255);
		World.getWorld().getEventHandler().stop(player, "staff_of_the_dead");
		World.getWorld().getEventHandler().submit(new StaffOfTheDeadEvent(player));
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (damage.getAmount() > 1) {
			player.gfx(1229, 355);
			damage.setAmount(damage.getAmount() / 2);
		}
	}

}
