package valius.model.entity.player.combat.effects;

import java.util.Optional;

import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.DamageEffect;
import valius.util.Misc;

public class SerpentineHelmEffect implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		attacker.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(defender));
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		defender.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(attacker));
	}

	@Override
	public boolean isExecutable(Player operator) {
		return (operator.getItems().isWearingItem(12931) || operator.getItems().isWearingItem(13199) || operator.getItems().isWearingItem(13197)) && Misc.random(5) == 1;
	}

}
