package valius.event.impl;

import java.util.concurrent.TimeUnit;

import valius.Config;
import valius.content.wogw.Wogw;
import valius.event.Event;
import valius.model.entity.player.GlobalMessages;
import valius.util.Misc;

public class BonusApplianceEvent extends Event<Object> {
	
	/**
	 * The amount of time in game cycles (600ms) that the event pulses at
	 */
	private static final int INTERVAL = Misc.toCyclesOrDefault(1, 1, TimeUnit.SECONDS);

	/**
	 * Creates a new event to cycle through messages for the entirety of the runtime
	 */
	public BonusApplianceEvent() {
		super(new String(), new Object(), INTERVAL);
	}

	@Override
	public void execute() {
		if (Wogw.EXPERIENCE_TIMER > 0) {
			Wogw.EXPERIENCE_TIMER--;
			if (Wogw.EXPERIENCE_TIMER == 1) {
				GlobalMessages.send("The well is no longer granting bonus experience!", GlobalMessages.MessageType.NEWS);
				Config.BONUS_XP_WOGW = false;
				Wogw.appendBonus();
			}
		}
		if (Wogw.PC_POINTS_TIMER > 0) {
			Wogw.PC_POINTS_TIMER--;
			if (Wogw.PC_POINTS_TIMER == 1) {
				GlobalMessages.send("The well is no longer granting bonus pc points!!", GlobalMessages.MessageType.NEWS);
				Config.BONUS_PC_WOGW = false;
				Wogw.appendBonus();
			}
		}
		if (Wogw.DOUBLE_DROPS_TIMER > 0) {
			Wogw.DOUBLE_DROPS_TIMER--;
			if (Wogw.DOUBLE_DROPS_TIMER == 1) {
				GlobalMessages.send("The well is no longer granting double drops !", GlobalMessages.MessageType.NEWS);
				Config.DOUBLE_DROPS = false;
				Wogw.appendBonus();
			}
		}
	}
}
