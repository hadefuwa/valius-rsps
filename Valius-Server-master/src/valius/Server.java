package valius;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import lombok.extern.slf4j.Slf4j;
import valius.event.CycleEventHandler;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;
import valius.model.minigames.pk_arena.Highpkarena;
import valius.model.minigames.pk_arena.Lowpkarena;
import valius.net.PipelineFactory;
import valius.util.date.GameCalendar;
import valius.util.log.Logger;
import valius.world.World;

/**
 * The main class needed to start the server.
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30 Revised by Shawn Notes by Shawn
 */
@Slf4j
public class Server {

	
	/**
	 * Represents our calendar with a given delay using the TimeUnit class
	 */
	private static GameCalendar calendar = new valius.util.date.GameCalendar(
			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), "GMT-3:00");


	/**
	 * Sleep mode of the server.
	 */
	public static boolean sleeping;
	
	/**
	 * Calls the rate in which an event cycles.
	 */
	public static final int cycleRate;


	/**
	 * Calls in which the server was last saved.
	 */
	public static long lastMassSave = System.currentTimeMillis();

	private static long sleepTime;

	/**
	 * Used to identify the server port.
	 */
	public static int serverlistenerPort;

	

	/**
	 * Handles the main game processing.
	 */
	private static final ScheduledExecutorService GAME_THREAD = Executors.newSingleThreadScheduledExecutor();

	private static final ScheduledExecutorService IO_THREAD = Executors.newSingleThreadScheduledExecutor();

	static {
		serverlistenerPort = Config.SERVER_STATE.getPort();
		cycleRate = 600;
		sleepTime = 0;
	}

	private static Throwable lastThrowable = null;
	private static final Runnable SERVER_TASKS = () -> {
		try {
			World.getWorld().tick();
			Highpkarena.process();
			Lowpkarena.process();
			//XericLobby.process();
			CycleEventHandler.getSingleton().process();
		
		} catch (Throwable t) {
			if(lastThrowable == null || !lastThrowable.getMessage().equalsIgnoreCase(t.getMessage())) {
				lastThrowable = t;
				t.printStackTrace();
			}
			log.warn("Server tasks threw an error! {}", t.getMessage());
			PlayerHandler.nonNullStream().forEach(PlayerSave::save);
		}
	};

	private static final Runnable IO_TASKS = () -> {
		try {
			// TODO tasks(players online, etc)
		} catch (Throwable t) {
			log.warn("IO tasks threw an error! {}", t);
		}
	};

	public static void main(java.lang.String args[]) {
		try {
			long startTime = System.currentTimeMillis();
			
			World.getWorld().init();

			Runtime.getRuntime().addShutdownHook(new ShutdownHook());
			
			long endTime = System.currentTimeMillis();
			World.getWorld().setWorldLoaded(true);
			bindPorts();
			long elapsed = endTime - startTime;
			
			String prefix = World.getWorld().isBetaWorld() ? "BETA" : World.getWorld().isLocalWorld() ? "LOCAL" : "LIVE";
			log.info("[" + prefix + "] Valius has successfully started up in {} milliseconds.", elapsed);
			//DiscordBot.startupMessage();
			GAME_THREAD.scheduleAtFixedRate(SERVER_TASKS, 0, 600, TimeUnit.MILLISECONDS);
			IO_THREAD.scheduleAtFixedRate(IO_TASKS, 0, 30, TimeUnit.SECONDS);
		
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the sleep mode timer and puts the server into sleep mode.
	 */
	public static long getSleepTimer() {
		return sleepTime;
	}
	
	/**
	 * Java connection. Ports.
	 */
	private static void bindPorts() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(serverlistenerPort));
	}

	public static GameCalendar getCalendar() {
		return calendar;
	}

	public static String getStatus() {
		return "IO_THREAD\n" + "\tShutdown? " + IO_THREAD.isShutdown() + "\n" + "\tTerminated? "
				+ IO_THREAD.isTerminated();
	}
}