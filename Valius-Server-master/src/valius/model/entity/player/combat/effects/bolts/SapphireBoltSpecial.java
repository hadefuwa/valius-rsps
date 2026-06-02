package valius.model.entity.player.combat.effects.bolts;

import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.DamageEffect;
import valius.model.entity.player.combat.range.RangeExtras;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;

public class SapphireBoltSpecial implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		int change = Misc.random((int) (damage.getAmount()));
		damage.setAmount(change);
		RangeExtras.createCombatGraphic(attacker, defender, 751, false);

		if (attacker.playerIndex > 0) {
			defender.getSkills().decreaseLevelOrMin(2, 0, Skill.PRAYER);
			defender.getPA().refreshSkill(5);
			defender.sendMessage("Your prayer has been lowered!");
			attacker.getSkills().increasLevelOrMax(2, Skill.PRAYER);
			attacker.getPA().refreshSkill(5);
		}
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		if (defender.getDefinition().getNpcName() == null) {
			return;
		}
		int change = Misc.random((int) (damage.getAmount()));
		damage.setAmount(change);
		RangeExtras.createCombatGraphic(attacker, defender, 751, false);
	}

	@Override
	public boolean isExecutable(Player operator) {
		return RangeExtras.boltSpecialAvailable(operator, 9240);
	}

}