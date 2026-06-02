package valius.model.entity.npc.combat.impl.general;

import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.combat.CombatScript;
import valius.model.entity.npc.combat.ScriptSettings;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.util.Misc;

/**
 * 
 * @author Divine Mar. 17, 2019 4:25:20 a.m.
 */

@ScriptSettings
(npcNames = { "Galvek" },
 npcIds = {})

public class Galvek extends CombatScript {

	/*
	 * Handles Combat for Galvek (non-Javadoc)
	 * 
	 * @see
	 * ethos.model.entity.npc.combat.CombatScript#attack(ethos.model.entity.npc.NPC,
	 * ethos.model.entity.Entity)
	 */
	@Override
	public int attack(NPC npc, Entity target) {
		int randomAttack = Misc.random(1, 10);// change to a switch statement
		boolean transform1 = (int) npc.getHealth().getAmount() <= 900;// Change to half health

		if (transform1) {
			transform1(npc);
		}

		switch (randomAttack) {
		case 1:
			smallFireball(npc, (Player) target);//TODO start/end gfx & time
			break;
		case 2:
			//dragonfire
			break;
		case 3:
			//blue electric orb
			break;
		case 4:
			//white fire
			break;
		case 5:
			//red orb
			break;
		case 6:
			//purple mage(either deactivates prayer or drains stats)
			break;
		case 7:
			//whatever case 7 doesnt do (drain stats or deactivate prayer. check notes!)
			break;
		case 8:
			//rock throw (check if phase 4 is active, if not do not use this. add the method for this above the switch
			break;
		}
		return 4;
	}

	/*
	 * Transforms Galvek to his second form
	 */
	void transform1(NPC npc) {
		npc.startAnimation(0);// replace with transform animation
		npc.requestTransform(2);// replace with new Id
		npc.appendTransformUpdate(null);
	}

	void smallFireball(NPC npc, Player player) {
		npc.startAnimation(1);// set correct animation id
		int damage = getRandomMaxHit(npc, player, CombatType.MAGE, Misc.random(40-66));
		if (player == null) {
			return;
		}
		//groundSpell(npc, player, 1, 2, "galvek", 3);// 1 = start gfx, 2 = end gfx, 3 = time projectile sits on the ground
	}
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ethos.model.entity.npc.combat.CombatScript#getAttackDistance(ethos.model.
	 * entity.npc.NPC)
	 */
	@Override
	public int getAttackDistance(NPC npc) {
		return 8;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ethos.model.entity.npc.combat.CombatScript#ignoreProjectileClipping()
	 */
	@Override
	public boolean ignoreProjectileClipping() {
		return false;
	}

}
