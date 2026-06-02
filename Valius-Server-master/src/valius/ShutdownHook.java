package valius;

import lombok.extern.slf4j.Slf4j;
import valius.content.GIMRepository;
import valius.content.tradingpost.Listing;
import valius.content.wogw.Wogw;
import valius.world.World;

/**
 * A thread which will be started when the server is being shut down. Although in most cases the Thread will be started, it cannot be guaranteed.
 * 
 * @author Emiel
 *
 */
@Slf4j
public class ShutdownHook extends Thread {

	private boolean hasExecuted;
	
	public void run() {
		if(hasExecuted)
			return;
		hasExecuted = true;
		log.warn("Executing shutdown hook!");
		World.getWorld().onShutdown();
		Listing.save();
		Wogw.save();
		GIMRepository.save();
	}
}
