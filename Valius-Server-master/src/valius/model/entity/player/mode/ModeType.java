package valius.model.entity.player.mode;

import valius.util.Misc;

public enum ModeType {

	REGULAR, IRON_MAN, ULTIMATE_IRON_MAN, HC_IRON_MAN, OSRS, CLASSIC, ELITE, GROUP_IRONMAN;

	@Override
	public String toString() {
		return Misc.capitalize(name().toLowerCase());
	}
}
