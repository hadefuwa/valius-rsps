package valius.model.entity.npc.bosses;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Damage;
import valius.util.Misc;

public class CorporealBeast {
	
	/**
	 * Manages the core stage to avoid multiple spawns
	 */
	public static int stage = 0;
	
	/**
	 * Checks wether or not a core is alive, else spawns when corp goes below certain hp
	 */
	public static void checkCore() {
		NPC corp = NPCHandler.getNpc(319);
		if (corp == null) {
			return;
		}

		List<NPC> core = Arrays.asList(NPCHandler.npcs);
		if (core.stream().filter(Objects::nonNull).anyMatch(n -> n.npcType == 320 && !n.isDead && n.getHealth().getAmount() > 0)) {
			return;
		}

		int currentHealth = corp.getHealth().getAmount();

		if (currentHealth < 1600 && stage == 0) {
			spawnCore();
			stage = 1;
		}
		if (currentHealth < 1000 && stage == 1) {
			spawnCore();
			stage = 2;
		}
		if (currentHealth < 450 && stage == 2) {
			spawnCore();
			stage = 3;
		}
	}
	
	/**
	 * Spawns one core at a set pair of coordinates
	 */
	private static void spawnCore() {
		NPCHandler.spawnNpc(320, 2984, 4389, 2, 0, 35, 13, 100, 120);
	}

	/**
	 * Checks the player attacks towards the beast
	 * 
	 * @param attacker
	 * @param damage
	 */
	public static void attack(Player attacker, Damage damage) {
		NPC corp = NPCHandler.getNpc(319);
		int change = Misc.random((int) (damage.getAmount() / 2));
		
		if (attacker.getItems().isWearingItem(11824) || attacker.getItems().isWearingItem(11889) || attacker.getItems().isWearingItem(33528) || attacker.getItems().isWearingItem(33529)
				|| attacker.getItems().isWearingItem(33530) || attacker.getItems().isWearingItem(33535) || attacker.getItems().isWearingItem(19675) || attacker.lastWeaponUsed == 33466
				|| attacker.getItems().isWearingItem(33531) || attacker.getItems().isWearingItem(33536) || attacker.getItems().isWearingItem(33360)) {
			return;
		} else {
			if (corp.getHealth().getAmount() > 50)
				damage.setAmount(change);
		}
	}
}
