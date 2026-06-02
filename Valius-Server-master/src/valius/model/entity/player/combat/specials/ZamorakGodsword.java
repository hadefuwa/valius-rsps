package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;

/**
 * @author Jason MacKeigan
 * @date Apr 8, 2015, 2015, 10:45:54 AM
 */
public class ZamorakGodsword extends Special {

	public ZamorakGodsword() {
		super(5.0, 1.75, 1.10, new int[] { 11808 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(7642);
		player.gfx0(1210);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (damage.getAmount() > 0) {
			if (target instanceof Player) {
				Player p = (Player) target;
				p.freezeTimer = 20;
				p.gfx0(369);
			} else if (target instanceof NPC) {
				NPC npc = (NPC) target;
				npc.freezeTimer = 20;
				npc.gfx0(369);
			}
		}
	}

}
