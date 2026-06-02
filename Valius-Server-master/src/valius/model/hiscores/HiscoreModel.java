package valius.model.hiscores;

import java.util.Arrays;

import lombok.Getter;
import valius.model.entity.player.Player;
import valius.model.entity.player.mode.ModeType;
import valius.model.entity.player.skills.Skill;

@Getter
public class HiscoreModel {
	
	private int[] playerXP;
	private ModeType gameMode;
	
	public HiscoreModel(Player player) {
		playerXP = new int[Skill.values().length];
		Arrays.stream(Skill.values()).forEach(skill -> playerXP[skill.getId()] = player.getSkills().getExperience(skill));
		this.gameMode = player.getMode().getType();
	}
}
