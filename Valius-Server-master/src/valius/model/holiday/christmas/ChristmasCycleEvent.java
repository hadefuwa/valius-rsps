package valius.model.holiday.christmas;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.model.entity.player.GlobalMessages;
import valius.model.holiday.HolidayController;

public class ChristmasCycleEvent implements CycleEvent {

	@Override
	public void execute(CycleEventContainer container) {
		Christmas christmas = (Christmas) container.getOwner();
		if (christmas == null) {
			container.stop();
			return;
		}
		if (!HolidayController.CHRISTMAS.isActive()) {
			GlobalMessages.send("The Christmas event is officially over. Enjoy the rest of your Holidays.", GlobalMessages.MessageType.EVENT);
			christmas.finalizeHoliday();
			container.stop();
			return;
		}
		christmas.getSnowball().update();
		christmas.getMinion().update();
			
		
	}

}
