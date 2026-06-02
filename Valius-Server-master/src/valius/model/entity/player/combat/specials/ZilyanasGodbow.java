package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.combat.range.RangeData;
import valius.util.Misc;

/**
 * 
 * @author Divine | 10:28:27 a.m. | Nov. 28, 2019
 *
 */
public class ZilyanasGodbow extends Special {

	public ZilyanasGodbow() {
		super(2.5, 1.5, 1.50, new int[] { 33116 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.bowSpecShot = 1;
		player.getItems().deleteArrow();
		player.getItems().deleteArrow();
		player.startAnimation(1074);
		player.projectileStage = 1;
		if (player.fightMode == 2) {
			player.attackTimer--;
		}
		int damage2 = player.getCombat().rangeMaxHit();
		player.usingBow = true;
		player.rangeItemUsed = player.playerEquipment[player.playerArrows];
		if (player.playerIndex > 0 && target instanceof Player) {
			player.getDamageQueue().add(new Damage(target, damage2, 1, player.playerEquipment, damage2 > 0 ? Hitmark.HIT : Hitmark.MISS, CombatType.RANGE));
			RangeData.fireProjectilePlayer(player, (Player) target, 50, 70, 301, 43, 31, 37, 10);
		} else if (player.npcIndex > 0 && target instanceof NPC) {
			player.getDamageQueue().add(new Damage(target, damage2, 1, player.playerEquipment, damage2 > 0 ? Hitmark.HIT : Hitmark.MISS, CombatType.RANGE));
			RangeData.fireProjectileNpc(player, (NPC) target, 50, 70, 301, 43, 31, 37, 10);
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
