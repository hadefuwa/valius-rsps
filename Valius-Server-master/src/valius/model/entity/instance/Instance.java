package valius.model.entity.instance;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import lombok.Getter;
import valius.clip.WorldObject;
import valius.model.entity.Entity;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.items.GroundItem;
import valius.model.items.Item;

/**
 * Represents an instance
 * @author James
 *
 */
public abstract class Instance {
	
	private static int INSTANCE_INDEX = 2;
	
	public Instance() {
		this.id = INSTANCE_INDEX++;
		InstanceManager.register(this);
	}
	

	/**
	 * The unique ID of this instance
	 */
	@Getter
	private int id;

	/**
	 * A flag to tell the {@link InstanceManager} to destroy this instance on the next tick
	 */
	@Getter
	private boolean awaitingDestroy;

	/**
	 * Whether this instance has been destroyed
	 */
	@Getter
	private boolean destroyed;
	
	/**
	 * Destroys the instance, kills all npcs and calls {@code onDestroy()}
	 */
	public void destroy() {
		destroyed = true;
		killNPCs();
		onDestroy();
	}
	
	/**
	 * Returns the height of this instance
	 * @return The instance ID * 4
	 */
	public int getHeight() {
		return id * 4;
	}

	
	private List<Entity> entities = Lists.newArrayList();
	
	
	/**
	 * @return All entities of type {@link Player} in this instance
	 */
	public List<Player> getPlayers(){
		return entities.stream().filter(e -> e.isPlayer()).map(e -> e.asPlayer()).collect(Collectors.toList());
	}

	/**
	 * @return All entities of type {@link NPC} in this instance
	 */
	public List<NPC> getNPCs(){
		return entities.stream().filter(e -> e.isNPC()).map(e -> e.asNPC()).collect(Collectors.toList());
	}
	
	public void killNPCs() {
		getNPCs().stream().forEach(NPCHandler::destroy);
	}
	
	public static boolean is(Entity entity, Class<?> instance) {
		return entity.getInstance() != null && instance != null && entity.getInstance().getClass().isAssignableFrom(instance);
	}
	
	/**
	 * Indicates that this instance should destroy if there are no players present
	 * @return true if the instance should destroy
	 */
	public abstract boolean destroyOnEmpty();
	
	/**
	 * Indicates whether ground items in this instance last for the lifetime of this instance
	 * @return true if items do not despawn
	 */
	public boolean groundItemsPersistent() {
		return false;
	}
	
	/**
	 * Adds the entity to this instance if it isn't already and calls {@code onEnter}
	 * @param entity The entity to add to this instance
	 */
	public void enter(Entity entity) {
		if(!entities.contains(entity)) {
			onEnter(entity);
			entities.add(entity);
		}
	}
	
	/**
	 * If the entity is in this instance, this method will remove the entity, call {@code onLeave}, set the entities instance to null 
	 * and destroys the instance if {@code destroyOnEmpty()} returns true and there are no more players present.
	 * @param entity The entity to remove from this instance
	 */
	public void leave(Entity entity) {
		if(entities.contains(entity)) {
			entities.remove(entity);
			onLeave(entity);
			entity.setInstance(null);
			if(destroyOnEmpty() && getPlayers().isEmpty()) {
				awaitingDestroy = true;
			}
		}
	}

	/**
	 * Handles clicking on objects inside the instance
	 * @param player The player that clicked on the object
	 * @param worldObject The world object that was clicked. Guaranteed to exist in the world.
	 * @param option The index of the right click option
	 * @return true if the click was handled.
	 */
	public boolean clickObject(Player player, WorldObject worldObject, int option) {
		return false;
	}
	
	/**
	 * Handles using an item on another item.
	 * @param player The player that is using the items.
	 * @param used The item using.
	 * @param usedOn The item being used on.
	 * @return true if the item is handled.
	 */
	public boolean useItemOnItem(Player player, Item used, Item usedOn) {
		return false;
	}
	
	/**
	 * Handles clicking on items inside the instance.
	 * @param player The player that clicked on the item.
	 * @param item The item being clicked on.
	 * @return true if the item was handled.
	 */
	public boolean clickItem(Player player, Item item) {
		return false;
	}
	
	/**
	 * Called when an entity is removed from this instance using  {@code leave}
	 * @param entity The entity that is leaving
	 */
	protected abstract void onLeave(Entity entity);
	
	/**
	 * Called when the entity is added to the instance using {@code enter}
	 * @param entity
	 */
	protected abstract void onEnter(Entity entity);
	
	/**
	 * Called when the instance is destroyed
	 */
	protected abstract void onDestroy();
	
	/**
	 * Called when this instance is registered successfully in {@link InstanceManager}
	 * This can only happen once per instance.
	 */
	protected abstract void initialize();
	
	/**
	 * Called when an entity dies inside this instance
	 * @param entity The entity that died
	 * @return false if the entity should continue to drop their items
	 */
	public abstract boolean onDeath(Entity entity);
	
	/**
	 * Called once per game tick
	 */
	public abstract void tick();
	
}
