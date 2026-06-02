package valius.event.impl;

import valius.content.SkillcapePerks;
import valius.event.Event;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

public class SkillRestorationEvent extends Event<Player> {

	public SkillRestorationEvent(Player attachment) {
		super(attachment, 100);
	}

	@Override
	public void execute() {
		if (attachment.isDead || attachment.getHealth().getAmount() <= 0) {
			return;
		}
		attachment.getHealth().increase(SkillcapePerks.HITPOINTS.isWearing(attachment) || SkillcapePerks.isWearingMaxCape(attachment) ? 2 : 1);
		if (attachment.hasOverloadBoost) {
			return;
		}
		attachment.getSkills().normalizeLevel(Skill.getNormalizingSkills());
		attachment.getSkills().sendRefresh();
	}

}
