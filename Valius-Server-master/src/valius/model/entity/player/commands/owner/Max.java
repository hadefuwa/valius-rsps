package valius.model.entity.player.commands.owner;

import valius.model.entity.player.Player;
import valius.model.entity.player.SkillExperience;
import valius.model.entity.player.commands.Command;
import valius.model.entity.player.skills.Skill;

public class Max extends Command {

	@Override
	public void execute(Player player, String input) {
		if(input.length() > 0) {
			player.getSkills().setLevel(99, Skill.values());
			player.getSkills().setExperience(200000000, Skill.values());
			player.getSkills().sendRefresh();
		} else {
			player.getSkills().setLevel(99, Skill.values());
			player.getSkills().setExperience(SkillExperience.getExperienceForLevel(99), Skill.values());
			player.getSkills().sendRefresh();
		}
	}

}
