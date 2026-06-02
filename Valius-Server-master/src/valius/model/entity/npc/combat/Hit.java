package valius.model.entity.npc.combat;

import lombok.Getter;
import lombok.Setter;
import valius.model.entity.player.combat.Hitmark;

/**
 * Represents a hit in npc combat, used for combat scripts.
 * @author ReverendDread
 * Mar 9, 2019
 */
@Getter @Setter
public class Hit {

	private Hitmark type;
	private int damage, delay;
	private boolean ignoresPrayer;
	
	public Hit(Hitmark type, int damage, int delay) {
		this(type, damage, delay, false);
	}
	
	public Hit(Hitmark type, int damage, int delay, boolean ignoresPrayer) {
		this.type = type;
		this.damage = damage;
		this.delay = delay;
		this.ignoresPrayer = ignoresPrayer;
	}
	
}
