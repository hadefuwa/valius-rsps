package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.combat.range.Arrow;
import valius.model.entity.player.combat.range.RangeData;

public class DarkBow extends Special {

	public DarkBow() {
		super(5.5, 1.40, 1.5, new int[] { 12765, 12766, 12767, 12768, 11235 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		int projectile = Arrow.matchesMaterial(player.playerEquipment[player.playerArrows], Arrow.DRAGON) ? 1099 : 1101;
		player.startAnimation(426);
		player.projectileStage = 1;
		player.gfx100(player.getCombat().getRangeStartGFX());
		if (player.playerIndex > 0 && target instanceof Player) {
			player.getItems().dropArrowPlayer();
			RangeData.fireProjectilePlayer(player, (Player) target, 50, 100, projectile, 60, 31, 53, 25);
			RangeData.fireProjectilePlayer(player, (Player) target, 50, 100, projectile, 60, 31, 63, 25);
		} else if (player.npcIndex > 0 && target instanceof NPC) {
			player.getItems().dropArrowNpc((NPC) target);
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 100, projectile, 60, 31, 53, 25);
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 100, projectile, 60, 31, 63, 25);
		}
		player.getItems().deleteArrow();
		player.getItems().deleteArrow();
		if (player.fightMode == 2)
			player.attackTimer--;
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
