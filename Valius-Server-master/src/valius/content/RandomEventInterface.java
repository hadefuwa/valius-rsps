package valius.content;

import java.util.concurrent.TimeUnit;

import valius.Config;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemDefinition;
import valius.util.Misc;
import valius.world.World;

public class RandomEventInterface implements CycleEvent {

	/**
	 * The amount of time the event starts at
	 */
	private static final int MAXIMUM_TIME = 100;

	private static final long EXECUTION_DELAY = TimeUnit.MINUTES.toMillis(10);

	/**
	 * The player this random event was created for
	 */
	private final Player player;

	/**
	 * The combination of items the player would have to choose from
	 */
	private Items combination;

	/**
	 * The correct item the player must select
	 */
	private Item correctItem;

	/**
	 * The time remaining for the event
	 */
	private int time;

	/**
	 * Determines if the random event is active
	 */
	private boolean active;

	/**
	 * The time in milliseconds the event was last executed
	 */
	private long lastExecuted;

	/**
	 * Creates a new {@link RandomEventInterface} for the {@link Player}.
	 * 
	 * @param player the player this is created for
	 */
	public RandomEventInterface(Player player) {
		this.player = player;
	}

	/**
	 * Creates a new event by randomly selecting a combination of items and choosing the item the player must select. The time is reset and the state of the event is set to active.
	 */
	public void execute() {
		if (player.wildLevel >= 30) {
			return;
		}
		lastExecuted = System.currentTimeMillis();
		active = true;
		time = MAXIMUM_TIME;
		combination = Items.values()[Misc.random(Items.values().length - 1)];
		correctItem = combination.items[Misc.random(combination.items.length - 1)];
		draw();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 1);
	}

	/**
	 * Determines if the event can be executed on the player.
	 * 
	 * @return {@code true} if the conditions for execution are met.
	 */
	public boolean isExecutable() {
		if (System.currentTimeMillis() - lastExecuted < EXECUTION_DELAY) {
			return false;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(player)) {
			return false;
		}
		if (player.playerIndex > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Manages button clicks on the interface
	 * 
	 * @param buttonId the button id clicked
	 */
	public void clickButton(int buttonId) {
		if (!(buttonId >= 33312 && buttonId <= 33318)) {
			return;
		}
		int slot = buttonId == 33312 ? 0 : buttonId == 33315 ? 1 : buttonId == 33318 ? 2 : -1;
		if (slot != -1) {
			Item item = combination.items[slot];
			if (item.getId() != correctItem.getId()) {
				player.getSkilling().stop();
				player.sendMessage("Incorrect, you have been sent home.");
				player.getPA().movePlayer(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
			}
			active = false;
			player.getPA().removeAllWindows();
			CycleEventHandler.getSingleton().stopEvents(this);
		}
	}

	/**
	 * Draws the information on the interface
	 */
	public void draw() {
		ItemDefinition definition = ItemDefinition.forId(correctItem.getId());
		player.getPA().sendFrame126("Click the '" + definition.getName() + "'", 33302);
		int frame = 33311;
		for (Item item : combination.items) {
			player.getPA().sendFrame34a(frame, item.getId(), 0, 1);
			frame += 3;
		}
		// int yOffset = -115 + Misc.random(115 * 2);
		// int xOffset = -97 + Misc.random(97 * 2);
		player.getPA().showInterface(33300);
		// player.getPA().sendFrame70(xOffset, yOffset, 33300);
	}

	@Override
	public void execute(CycleEventContainer container) {
		time--;
		long millis = (long) ((time * .6) * 1000L);
		long second = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
		long minute = TimeUnit.MILLISECONDS.toMinutes(millis);
		player.getPA().sendFrame126(String.format("%2d:%02d", minute, second, millis), 33303);
		if (player.getInterfaceOpen() != 33300) {
			draw();
		}
		if (player.getSkilling().isSkilling()) {
			player.getSkilling().stop();
		}
		if (time <= 0) {
			active = false;
			player.getSkilling().stop();
			player.sendMessage("Incorrect, you have been sent to Home.");
			player.getPA().movePlayer(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
			container.stop();
		}
	}

	/**
	 * Determines if the random event interface is active
	 * 
	 * @return true if the event is active, otherwise false.
	 */
	public boolean isActive() {
		return active;
	}

	private enum Items {
		SCIMITAR(new Item(1321), new Item(1323), new Item(1325)), 
		DAGGER(new Item(1209), new Item(1211), new Item(1213)), 
		TWO_HANDED(new Item(1315), new Item(1317), new Item(1319)), 
		KITESHIELD(new Item(2659), new Item(2675), new Item(2667)), 
		DEFENDER(new Item(8849), new Item(8850), new Item(12954));

		/**
		 * An array of items that will be sent to the interface. One item the player must select.
		 */
		private final Item[] items;

		/**
		 * Constructs a new Items object containing an array of {@link Item} objects
		 * 
		 * @param items the array of items
		 */
		private Items(Item... items) {
			this.items = items;
		}
	}

}
