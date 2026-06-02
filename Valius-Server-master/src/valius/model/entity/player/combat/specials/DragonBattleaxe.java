package valius.model.entity.player.combat.specials;

import valius.model.entity.Entity;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.skills.Skill;

public class DragonBattleaxe extends Special {

	public DragonBattleaxe() {
		super(10.0, 1.0, 1.0, new int[] { 1377 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		Skill[] decreased = { Skill.ATTACK, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC};
		Skill[] increased = { Skill.STRENGTH };
		int totalDrain = 0;
		for (Skill skill : decreased) {
			int newLevel =  (int) (player.getSkills().getLevel(skill) * 0.9);
			if(newLevel < 1)
				newLevel = 1;
			totalDrain += player.getSkills().getLevel(skill) - newLevel;
			player.getSkills().setLevel(newLevel, skill);
		}

		for (Skill skill : increased) {
			int newLevel =  (int) player.getSkills().getLevel(skill) + 10 + (totalDrain / 4);
			if(newLevel > player.getSkills().getActualLevel(skill) + 21)
				newLevel = player.getSkills().getActualLevel(skill) + 21;
			player.getSkills().setLevel(newLevel, skill);
		}
		player.getSkills().sendRefresh();
		player.attackTimer += 1;
		player.forcedChat("Raarrrrrgggggghhhhhhh!");
		player.gfx0(246);
		player.startAnimation(1056);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
