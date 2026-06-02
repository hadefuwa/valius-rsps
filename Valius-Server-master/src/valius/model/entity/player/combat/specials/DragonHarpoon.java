package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.skills.Skill;

public class DragonHarpoon extends Special {
	public DragonHarpoon() {
		super(10.0, 2.0, 2.0, new int[] { 21028 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.forcedChat("Here fishy fishies!");
		player.gfx0(246);
		player.getSkills().increaseLevel(3, Skill.FISHING);
		player.getPA().refreshSkill(Skill.FISHING.getId());
	}


	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}
}
