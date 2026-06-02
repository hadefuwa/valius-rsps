package valius.world.event;

import java.util.List;

import com.google.common.collect.Lists;

public class CyclicEventManager {
	
	private List<CyclicEvent> events = Lists.newArrayList();
	
	public void wakeCycles() {
		events.parallelStream().forEach(event -> event.wake());
	}
	
	public void register(CyclicEvent event) {
		events.add(event);
	}

	public void unregister(CyclicEvent cyclicEvent) {
		events.remove(cyclicEvent);
	}

}
