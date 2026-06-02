/**
 * 
 */
package valius.content.gauntlet;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.google.common.base.Stopwatch;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.clip.WorldObject;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.instance.Instance;
import valius.model.entity.player.Player;
import valius.model.items.Item;

/**
 * @author ReverendDread
 * Aug 5, 2019
 */
@Data
@Slf4j
public class TheGauntlet extends Instance {

	private final Player player; //the player
	private GauntletDungeon dungeon;
	private GauntletType type = GauntletType.NORMAL; //the dungeon difficulty
	private int[] currentRoom; //the current room coordinates the player is in.
	private long startTime; //the time the dungeon was started
	
	//Time your allowed in the dungeon in seconds.
	private static final long TIMER_DURATION = TimeUnit.MINUTES.toSeconds(10);
	
	//Warning intervals in seconds after the start of the stopwatch.
	private static final int[] WARNING_TIMES = { 300, 60 };
	
	//Stopwatch instance for dungeon timer.
	public Stopwatch stopwatch = Stopwatch.createUnstarted();
	
	private static final int[] STARTING_ITEMS = { 
		23861, //crystal sceptre
		23862, //crystal axe 
		23863, //crystal pickaxe
		23864, //crystal harpoon
		23865, //pestle and mortar
		23904, //teleport crystal
	};
	
	@Override
	protected void initialize() {

	}
	
	@Override
	public void tick() {
		
		long elapsed = stopwatch.elapsed(TimeUnit.SECONDS);
		
		//warn the player of time remaining
		if (isWarningTime(elapsed)) {
			player.sendMessage("@red@You have " + Duration.ofSeconds((TIMER_DURATION - elapsed)).toMinutes() + " minutes of prep time remaining.");
		}
		
		//time has expired, send the player to the boss
		if (elapsed >= TIMER_DURATION && stopwatch.isRunning()) {
			Location boss = new Location(dungeon.getCenter().getX() - 3, dungeon.getCenter().getY() + 8, 1);
			player.getPA().movePlayerUnconditionally(boss.getX(), boss.getY(), boss.getZ());
			player.sendMessage("You've been teleported to the boss, as your prep time has expired.");
			stopwatch.stop();
		}
		
	}
	
	@Override
	public boolean destroyOnEmpty() {
		destoryGracefully(false);
		return true;
	}
	
	@Override
	protected void onLeave(Entity entity) {
		if (entity.isPlayer())
			destoryGracefully(false);
	}
	
	@Override
	protected void onEnter(Entity entity) {
		if (entity.isPlayer()) {
			Optional<Location> locOpt = Region.findFreeRegion(2, 2);
			locOpt.ifPresent(loc -> {
				entity.asPlayer().getHealth().reset();
				entity.asPlayer().getSkills().resetToActualLevels();
				entity.asPlayer().setRunEnergy(100);		
				dungeon = new GauntletDungeon(entity.asPlayer(), type, loc);
				stopwatch.start();		
				entity.asPlayer().getPA().movePlayerUnconditionally(dungeon.getCenter().getX() + 8, dungeon.getCenter().getY() + 6, 1);
				IntStream.of(STARTING_ITEMS).forEach(item -> entity.asPlayer().getItems().addItem(item, 1));
				
			});
			
		}
	}
	
	@Override
	protected void onDestroy() {
		destoryGracefully(false);
	}
	
	@Override
	public boolean onDeath(Entity entity) {
		if (entity.isPlayer()) {
			destoryGracefully(false);
		}
		if (entity.isNPC()) {
			if (isBoss(entity.asNPC().npcType)) {
				destoryGracefully(true);
			}
			entity.setInstance(null);
		}
		return true;
	}

	private void destoryGracefully(boolean completed) {
		if (player != null) {
			player.getItems().deleteAllItems();
			player.setInstance(null);
			player.sendMessage("You've " + (completed ? "completed The Gauntlet!" : "failed to complete The Gauntlet."));
			if(completed)
				player.setGaunletLootAvailable(true);
			GauntletPrepRoom.sendChest(player);
			player.getPA().movePlayerUnconditionally(GauntletPrepRoom.ROOM_ENTRY);
		}
		//stopwatch.stop();
		dungeon.destroy();
	}

	@Override
	public boolean clickObject(Player player, WorldObject worldObject, int option) {
		return dungeon.clickObject(option, worldObject);
	}
	
	@Override
	public boolean useItemOnItem(Player player, Item used, Item usedOn) {
		if (used.getId() == 23866 && usedOn.getId() == 23865) {
			player.getItems().deleteItem(used.getId(), 10);
			player.getItems().addItem(23867, 10);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean clickItem(Player player, Item item) {
		if (item.getId() == 23904) {
			GauntletRoom room = getDungeon().getRoom(player.getLocation()).orElse(null);
			if (room != null && !room.isBoss()) {
				player.getPA().startTeleport(dungeon.getCenter().getX() + 8, dungeon.getCenter().getY() + 6, 1, "modern", false);
				player.getItems().deleteItem(23904, 1);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean groundItemsPersistent() {
		return true;
	}

	public boolean isBoss(int id) {
		return IntStream.of(9021, 9022, 9023, 9024, 9035, 9036, 9037, 9038).anyMatch(bossId -> bossId == id);
	}
	
	private boolean isWarningTime(long time) {
		return IntStream.of(WARNING_TIMES).anyMatch(warning -> warning == time);
	}
	
}
