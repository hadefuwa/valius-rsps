package valius.model.entity.player.skills;

import java.util.stream.Stream;

import valius.util.Misc;

public enum Skill {
		ATTACK(0), 
		DEFENCE(1), 
		STRENGTH(2), 
		HITPOINTS(3), 
		RANGED(4), 
		PRAYER(5), 
		MAGIC(6), 
		COOKING(7), 
		WOODCUTTING(8), 
		FLETCHING(9), 
		FISHING(10), 
		FIREMAKING(11), 
		CRAFTING(12), 
		SMITHING(13), 
		MINING(14), 
		HERBLORE(15), 
		AGILITY(16), 
		THIEVING(17), 
		SLAYER(18), 
		FARMING(19), 
		RUNECRAFTING(20),
		HUNTER(21),
		CONSTRUCTION(22),
	;

	private int id;

	private Skill(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		String name = name().toLowerCase();
		return Misc.capitalize(name);
	}

	public static Skill forId(int id) {
		return Stream.of(Skill.values()).filter(s -> s.id == id).findFirst().orElse(null);
	}
	
	public static Skill[] getCombatSkills() {
		return Stream.of(Skill.values()).filter(skill -> skill.getId() <= 6).toArray(Skill[]::new);
	}
	
	public static Skill[] getNonCombatSkills() {
		return Stream.of(Skill.values()).filter(skill -> skill.getId() > 6).toArray(Skill[]::new);
	}
	
	public static Skill[] getNormalizingSkills() {
		return Stream.of(Skill.values()).filter(skill -> skill != Skill.PRAYER && skill != Skill.HITPOINTS).toArray(Skill[]::new);
	}
	
	public static Skill[] getAllButHitpoints() {
		return Stream.of(Skill.values()).filter(skill -> skill != Skill.HITPOINTS).toArray(Skill[]::new);
	}

	public static final int MAXIMUM_SKILL_ID = 22;

	public static Stream<Skill> stream() {
		return Stream.of(Skill.values());
	}

	public static int length() {
		return Skill.values().length;
	}
}
