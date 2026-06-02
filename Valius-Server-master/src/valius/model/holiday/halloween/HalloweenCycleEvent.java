package valius.model.holiday.halloween;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.model.entity.player.GlobalMessages;
import valius.model.holiday.HolidayController;

public class HalloweenCycleEvent implements CycleEvent {

	@Override
	public void execute(CycleEventContainer container) {
		Halloween halloween = (Halloween) container.getOwner();
		if (halloween == null) {
			container.stop();
			return;
		}
		if (!HolidayController.HALLOWEEN.isActive()) {
			GlobalMessages.send("The Halloween Event is now over!", GlobalMessages.MessageType.EVENT);
			halloween.finalizeHoliday();
			container.stop();
			return;
		}
		halloween.getSearchGame().update();
	}

}
