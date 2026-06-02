package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.skills.Skill;

public class DragonWarhammer extends Special {

	public DragonWarhammer() {
		super(5.0, 2.10, 1.30, new int[] { 13576 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(1378);
		player.gfx0(1292);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
			Player pTarget = ((Player) target);
			if (damage.getAmount() > 0) {
				int currentDefLvl = pTarget.getSkills().getLevel(Skill.DEFENCE);
				if (currentDefLvl > 0)
					pTarget.getSkills().setLevel(currentDefLvl / 3, Skill.DEFENCE);
					pTarget.getPA().refreshSkill(1);
			}
		}

	}

}
