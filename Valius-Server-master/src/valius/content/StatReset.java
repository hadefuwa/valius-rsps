package valius.content;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.SkillExperience;
import valius.model.entity.player.skills.Skill;

/**
 * Reset an individual player skill for a cost.
 * 
 * @author Matt
 *
 */
public class StatReset {

	/**
	 * Item id of what the player will be paying with
	 */
	private static int VALUE = 995;
	
	/**
	 * The amount the player will pay
	 */
	private static int SKILL_RESET_COST = 4_000_000;

	/**
	 * Tries to execute and reset a chosen skill
	 * 
	 * @param player The player executing for
	 * @param skillId The skill id which is being reset
	 */
	public static void execute(Player player, int skillId) {
		player.getPA().removeAllWindows();
		player.nextChat = -1;
		Skill skill = Skill.forId(skillId);
		if (player.getItems().isWearingItems()) {
			player.getDH().sendNpcChat1("Warrior, you must remove your equipment..", player.talkingNpc, "Combat Instructor");
			return;
		}
		if (Boundary.isIn(player, Boundary.DUEL_ARENA) || Boundary.isIn(player, Boundary.FIGHT_CAVE)) {
			player.getDH().sendNpcChat2("You cannot do this whilst in this area.", "Please finish what you're doing.", player.talkingNpc, "Combat Instructor");
			return;
		}
		if (player.getSkills().getLevel(skill) == 1) {
			player.getDH().sendNpcChat2("You are already level 1 in this skill, it is not", "recommended that you do this.", player.talkingNpc, "Combat Instructor");
			return;
		}
		if (!player.getItems().playerHasItem(VALUE, SKILL_RESET_COST)) {
			player.getDH().sendNpcChat1("Warrior, you do not seem to have 4M GP..", player.talkingNpc, "Combat Instructor");
			return;
		}
		player.getItems().deleteItem(VALUE, SKILL_RESET_COST); 
		if (skillId != 3) {
			player.getSkills().setLevel(1, skill);
			player.getSkills().setExperience(SkillExperience.getExperienceForLevel(1), skill);
		} else {
			player.getSkills().setLevel(10, skill);
			player.getSkills().setExperience(SkillExperience.getExperienceForLevel(10), skill);
		}
		player.getPA().refreshSkill(skillId);
	}

}
