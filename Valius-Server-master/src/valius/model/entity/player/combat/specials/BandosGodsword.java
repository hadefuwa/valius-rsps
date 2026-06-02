package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.skills.Skill;

public class BandosGodsword extends Special {

	public BandosGodsword() {
		super(5.0, 1.35, 1.21, new int[] { 11804 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(7642);
		player.gfx0(1212);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
			Player pTarget = (Player) target;
			if (damage.getAmount() > 0) {
				if (pTarget.getSkills().getLevel(Skill.DEFENCE) > 0)
					pTarget.getSkills().decreaseLevelOrMin(pTarget.getSkills().getLevel(Skill.DEFENCE) / 3, Skill.DEFENCE);
					pTarget.getPA().refreshSkill(1);
			}
		}
	}

}
