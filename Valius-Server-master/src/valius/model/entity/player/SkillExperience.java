package valius.model.entity.player;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import valius.model.entity.player.skills.Skill;
import valius.util.Experience;

public class SkillExperience {

	private final Player player;

	public SkillExperience(Player player) {
		this.player = player;
		experience[Skill.HITPOINTS.getId()] = 1300;
		levels[Skill.HITPOINTS.getId()] = 10;
		resetToActualLevels();
	}

	private int[] experience = new int[Skill.values().length];

	private int[] levels = new int[Skill.values().length];

	public int getExperience(Skill skill) {
		return experience[skill.getId()];
	}

	public int getLevel(Skill skill) {
		return levels[skill.getId()];
	}

	public int getActualLevel(Skill skill) {
		return getLevelForExp(getExperience(skill));
	}

	public void normalizeLevel(Skill... skills) {
		for(Skill skill : skills) {
			int currentLevel = getLevel(skill);
			int actualLevel = getActualLevel(skill);
			if(currentLevel < actualLevel) {
				levels[skill.getId()] += 1;
			} else if(currentLevel > actualLevel){
				levels[skill.getId()] -= 1;
			}
		}
	}

	public void increaseLevel(int increase, Skill... skills) {
		for(Skill skill : skills)
			levels[skill.getId()] += increase;
	}

	public void decreaseLevel(int decrease, Skill... skills) {
		for(Skill skill : skills)
			levels[skill.getId()] -= decrease;
	}

	public void decreaseLevelOrMin(int decrease, Skill... skills) {
		decreaseLevelOrMin(decrease, 1, skills);
	}
	
	public void decreaseLevelOrMin(int decrease, int min, Skill... skills) {
		for(Skill skill : skills) {
			levels[skill.getId()] -= decrease;
			if(levels[skill.getId()] < 1)
				levels[skill.getId()] = 1;
		}
	}

	public void setLevel(int level, Skill... skills) {
		for(Skill skill : skills)
			levels[skill.getId()] = level;
	}

	public void addExperience(int add, Skill... skills) {
		for(Skill skill : skills)
			experience[skill.getId()] += add;
	}

	public static int getExperienceForLevel(int level) {
		return level == 1 ? 0 : Experience.LEVEL_XP[level - 2];
	}

	public static int getLevelForExp(int experience) {
		OptionalInt foundLevel = IntStream.range(0, Experience.LEVEL_XP.length)
				.filter(expMin -> Experience.LEVEL_XP[expMin] - 1 >= experience)
				.findFirst();
		return foundLevel.orElse(98) + 1;
	}

	public int[] experienceToArray() {
		return experience;
	}

	public void resetToActualLevels() {
		Skill.stream().forEach(skill -> setLevel(getActualLevel(skill), skill));
	}

	public void sendRefresh() {
		Skill.stream().forEach(skill -> {
			player.getPA().refreshSkill(skill.getId());
			player.getPA().setSkillLevel(skill.getId(), getLevel(skill), getExperience(skill));
		});
	}

	public void setLevelOrActual(int level, Skill skill) {
		if(level > getActualLevel(skill))
			level = getActualLevel(skill);
		setLevel(level, skill);

	}

	public void setExperience(int experienceNum, Skill... skills) {
		for(Skill skill : skills)
			experience[skill.getId()] = experienceNum;

	}

	public void increasLevelOrMax(int increase, Skill... skills) {
		for(Skill skill : skills) {
			levels[skill.getId()] += increase;
			if(levels[skill.getId()] > getActualLevel(skill))
				levels[skill.getId()] = getActualLevel(skill);
		}
	}

	public int getTotalLevel() {
		return Skill.stream().mapToInt((skill) -> getActualLevel(skill)).sum();
	}

	public int getTotalExperience() {
		// TODO Auto-generated method stub
		return Skill.stream().mapToInt((skill) -> getExperience(skill)).sum();
	}

	public void resetToActualLevel(Skill... skills) {
		for(Skill skill : skills)
			levels[skill.getId()] = getActualLevel(skill);
	}
	public void setLevelOrMin(int level, Skill... skills) {
		setLevelOrMin(level, 1, skills);
		
	}
	public void setLevelOrMin(int level, int min, Skill... skills) {
		if(level < min)
			level = min;
		for(Skill skill : skills)
			levels[skill.getId()] = level;
	}

	public void sendRefresh(Skill skill) {
		player.getPA().refreshSkill(skill.getId());
		player.getPA().setSkillLevel(skill.getId(), getLevel(skill), getExperience(skill));
	}

}
