package valius.model.entity.player.skills;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.world.World;

public class Skilling {

	Player player;

	private Optional<Skill> skill = Optional.empty();

	public Skilling(Player player) {
		this.player = player;
	}

	public void stop() {
		World.getWorld().getEventHandler().stop(player, "skilling");
		skill = Optional.empty();
	}

	public boolean isSkilling() {
		return skill.isPresent();
	}

	public Skill getSkill() {
		return skill.orElse(null);
	}

	public void setSkill(Skill skill) {
		this.skill = Optional.of(skill);
	}

}
