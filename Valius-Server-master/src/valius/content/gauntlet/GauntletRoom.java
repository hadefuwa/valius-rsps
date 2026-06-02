/**
 * 
 */
package valius.content.gauntlet;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.content.gauntlet.gathering.GauntletResourceNodes;
import valius.content.gauntlet.monsters.GauntletMonster;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.map.PaletteTile;
import valius.model.map.ResourceNode;
import valius.util.Misc;
import valius.world.World;

/**
 * @author ReverendDread | RSPSi
 * Aug 7, 2019
 */
@Data @Slf4j
public class GauntletRoom {

	private final int x, y;
	private final Location location;
	private final GauntletRooms room; //the room reference
	private final int rotation; //the rooms rotation
	private boolean visible; //if the room is visible
	private boolean boss; //if the room is the boss room
	private List<ResourceNode> nodes = Lists.newArrayList();
	private List<PaletteTile> tiles = Lists.newArrayList();
	private List<NPC> npcs = Lists.newArrayList();
	private static final int NODE_LIMIT = 4;
	private final double monster_chance;
	
	/**
	 * Generates the resource nodes in the room.
	 */
	public void generateResourceNodes(GauntletType type) {
		Boundary boundary = getBoundary();
		for (int x = boundary.getMinimumX() + 3; x < boundary.getMaximumX() - 3; x++) {
			for (int y = boundary.getMinimumY() + 3; y < boundary.getMaximumY() - 3; y++) {	
				if (!Region.isBlocked(x, y, 1) && nodes.size() < NODE_LIMIT) {
					int chance = Misc.random(1000);
					if (chance <= 25) {
						GauntletResourceNodes nodeData = GauntletResourceNodes.getRandomNode();
						ResourceNode node = new ResourceNode(nodeData.getObjectId(), nodeData.getHarvestAmount(), Location.of(x, y, 1));
						World.getWorld().getGlobalObjects().add(node);
						nodes.add(node);
					}
				}
				
			}
		}
	}
	
	/**
	 * Generates the monsters in the room.
	 * @param type
	 */
	public void generateMonsterSpawns(GauntletDungeon dungeon) {
		Boundary boundary = getBoundary();		
		if (getRoom() == GauntletRooms.BOSS_ROOM) {
			NPC npc = NPCHandler.spawn(9021, boundary.getMinimumX() + 6, boundary.getMinimumY() + 6, 1, 1, 600, 10, 50, 50, true);
			npc.setInstance(dungeon.getPlayer().getInstance());
			npcs.add(npc);
			return;
		}
		BiFunction<GauntletMonster, Location, Boolean> validSpawn = (monster, loc) -> {
			NPCDefinitions def = NPCDefinitions.get(monster.getId());
			if(def == null)
				return false;
			int size = def.getSize();
			if(size > 1) {
				for(int sizeX = 0;sizeX < size;sizeX++) {
					for(int sizeY = 0;sizeY < size; sizeY++) {
						if(Region.isBlocked(loc.getX() + sizeX, loc.getY() + sizeY, 1)) {
							return false;
						}
					}
				}
				return true;
			} else {
				return !Region.isBlocked(loc.getX(), loc.getY(), 1);
			}
		};
		List<GauntletMonster> monsters = GauntletMonster.getRandomSpawn(this, dungeon.getType());
		if (monsters.isEmpty()) {
			return;
		}
		int maxSpawns = GauntletMonster.getMaxiumumForType(monsters.get(0).getMonsterType());
		for (Location spot : Misc.shuffle(boundary.stream().collect(Collectors.toList()))) {
			GauntletMonster monster = Misc.randomTypeOfList(monsters);
			if (npcs.size() < maxSpawns) {
				if(!validSpawn.apply(monster, spot))
					continue;
				NPC npc = NPCHandler.spawn(monster.getId(), spot.getX(), spot.getY(), 1, 1, monster.getHp(), 10, 50, 50, true);
				npc.setInstance(dungeon.getPlayer().getInstance());
				npcs.add(npc);	
			} else {
				break;
			}
		}
	}
	
	public int directionOf(Location other) {	
		Location north = location.translate(8, 14, 0);
		Location south = location.translate(8, 0, 0);
		Location east = location.translate(14, 8, 0);
		Location west = location.translate(0, 8, 0);
		Location closest = Stream.of(north, east, south, west).sorted((loc1, loc2) -> Integer.compare(other.getDistance(loc1), other.getDistance(loc2))).findFirst().orElse(north);	
		if (closest == north) {
			return RoomDirection.NORTH;
		} else if (closest == east) {
			return RoomDirection.EAST;
		} else if (closest == south) {
			return RoomDirection.SOUTH;
		} else {
			return RoomDirection.WEST;
		}
	}
	
	/**
	 * Handles the room being destoryed.
	 */
	public void destroy() {
		nodes.stream().filter(Objects::nonNull).forEach(node -> World.getWorld().getGlobalObjects().remove(node));
		npcs.stream().filter(Objects::nonNull).forEach(NPCHandler::destroy);
	}
	
	/**
	 * Checks if the room can be opened from the requested direction.
	 * @param requestedDir The requested direction.
	 * @return
	 */
	public boolean flagOpen(int requestedDir) {
		if (room.hasDirection(requestedDir, rotation)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the boundary of the room.
	 * @return
	 */
	public Boundary getBoundary() {
		return new Boundary(location.getX(), location.getY(), location.getX() + 16, location.getY() + 16);
	}
	
	public Location getCenter() {
		return location.translate(8, 8, 0);
	}
	
}
