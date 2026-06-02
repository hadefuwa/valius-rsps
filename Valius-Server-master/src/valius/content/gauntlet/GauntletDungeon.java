/**
 * 
 */
package valius.content.gauntlet;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.koloboke.collect.impl.Maths;

import io.netty.util.internal.MathUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import valius.clip.Region;
import valius.clip.WorldObject;
import valius.content.Fillables;
import valius.content.gauntlet.crafting.GauntletCraftables;
import valius.content.gauntlet.crafting.GauntletCraftingAction;
import valius.content.gauntlet.dialogues.ToolStorage;
import valius.content.gauntlet.gathering.GauntletHarvestAction;
import valius.content.gauntlet.gathering.GauntletResourceNodes;
import valius.content.gauntlet.monsters.GauntletMonster;
import valius.content.quest.dialogue.DialogueChain;
import valius.content.quest.dialogue.impl.CloseDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.Location;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Cooking;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.map.Palette;
import valius.model.map.PaletteTile;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * @author ReverendDread | RSPSi
 * Aug 7, 2019
 */
@Getter
@Slf4j
public class GauntletDungeon {
	
	private final Player player;

	private static final int MAP_SIZE = 7;
	private static final int CHUNK_SIZE = 8;
	private static final int ROOM_TILE_SIZE = CHUNK_SIZE * 2;
	private static final int TOTAL_MAP_SIZE = (MAP_SIZE * ROOM_TILE_SIZE);
	private final GauntletRoom[][] rooms;
	private final Palette layout;
	private final GauntletType type;
	
	private Location center;
	private Location minimum, maximum;
	
	public GauntletDungeon(Player player, GauntletType type, Location minimum) {
		this.type = type;
		this.player = player;
		this.minimum = minimum;
		this.center = new Location(minimum.getX() + ((ROOM_TILE_SIZE * 3)), minimum.getY() + ((ROOM_TILE_SIZE * 3)));
		this.maximum = new Location(minimum.getX() + TOTAL_MAP_SIZE, minimum.getY() + TOTAL_MAP_SIZE);
		this.rooms = new GauntletRoom[MAP_SIZE][MAP_SIZE];
		this.layout = new Palette(13, 13, 4);
		generateDungeon();
	}
	
	/**
	 * Generates the dungeon room layout.
	 */
	public void generateDungeon() {
		Region.deleteRegion(minimum.getRegion().getId(), 2, 2);
		Palette palette = new Palette(MAP_SIZE * 2, MAP_SIZE * 2, 4);
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				GauntletRoom room = generateRoom(x, y);
				rooms[x][y] = room;
				if (room.getRoom() == GauntletRooms.STARTER_ROOM || room.getRoom() == GauntletRooms.BOSS_ROOM) {
					if (room.getRoom() == GauntletRooms.BOSS_ROOM) {
						room.generateMonsterSpawns(this);
						room.setBoss(true);
					}
					room.setVisible(true);
				}			
				int paletteRotation = room.getRotation();
				for (int height = 0; height < 2; height++) {
					PaletteTile[][] roomChunks = {
							{	
								new PaletteTile(room.getLocation(), room.getRoom().getX(), room.getRoom().getY(), height, paletteRotation, room.isVisible()),//SW
								new PaletteTile(room.getLocation(), room.getRoom().getX() + 8, room.getRoom().getY(), height, paletteRotation, room.isVisible()), //SE
							},
							{ 
								new PaletteTile(room.getLocation(), room.getRoom().getX() + 8, room.getRoom().getY() + 8, height, paletteRotation, room.isVisible()), //NE
								new PaletteTile(room.getLocation(), room.getRoom().getX(), room.getRoom().getY() + 8, height, paletteRotation, room.isVisible()) //NW
							}
					};
					PaletteTile[][] rotatedObjects = Misc.rotatePaletteClockwise(roomChunks, room.getRotation());	
					
					int tileX = x * ROOM_TILE_SIZE / 8;
					int tileY = y * ROOM_TILE_SIZE / 8;
					room.getTiles().addAll(Stream.of(rotatedObjects).flatMap(Stream::of).collect(Collectors.toList()));				
					palette.setTile(tileX, tileY, height, rotatedObjects[0][0]);
					palette.setTile(tileX + 1, tileY, height, rotatedObjects[0][1]);
					palette.setTile(tileX + 1, tileY + 1, height, rotatedObjects[1][0]);
					palette.setTile(tileX, tileY + 1, height, rotatedObjects[1][1]);

				}

			}
		}
		Region.loadPalette(minimum, palette);
	}
	
	/**
	 * Generates the dungeon palette for the contructed map.
	 */
	public void generateDungeonPalette() {

		layout.reset();
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {					
				GauntletRoom room = rooms[x][y];				
				int paletteRotation = room.getRotation();
				for (int height = 0; height < 2; height++) {
					PaletteTile[][] roomChunks = {
							{	
								new PaletteTile(room.getRoom().getX(), room.getRoom().getY(), height, paletteRotation, room.isVisible()),//SW
								new PaletteTile(room.getRoom().getX() + 8, room.getRoom().getY(), height, paletteRotation, room.isVisible()), //SE
							},
							{ 
								new PaletteTile(room.getRoom().getX() + 8, room.getRoom().getY() + 8, height, paletteRotation, room.isVisible()), //NE
								new PaletteTile(room.getRoom().getX(), room.getRoom().getY() + 8, height, paletteRotation, room.isVisible()) //NW
							}
					};
					PaletteTile[][] rotatedObjects = Misc.rotatePaletteClockwise(roomChunks, room.getRotation());	
					Location chunkLoc = getChunkRelativeTo(room, player.getLocation());
					int tileX = chunkLoc.getX();
					int tileY = chunkLoc.getY();
					room.getTiles().addAll(Stream.of(rotatedObjects).flatMap(Stream::of).collect(Collectors.toList()));				
					layout.setTile(tileX, tileY, height, rotatedObjects[0][0]);
					layout.setTile(tileX + 1, tileY, height, rotatedObjects[0][1]);
					layout.setTile(tileX + 1, tileY + 1, height, rotatedObjects[1][0]);
					layout.setTile(tileX, tileY + 1, height, rotatedObjects[1][1]);

				}
			}
		}
	}	
	
	/**
	 * Generates a room based on the location in the layout.
	 * @param x
	 * @param y
	 * @return
	 */
	private GauntletRoom generateRoom(int x, int y) {
		boolean corner = (x == 0 && y == 0) || (x == 6 && y == 0) || (x == 0 && y == 6) || (x == 6 && y == 6);
		boolean side = (x == 0 || y == 0 || x == 6 || y == 6);
		boolean center = (x == 3 && y == 3);
		boolean boss = (x == 2 && y == 3);
		int rotation = Misc.random(3);
		double dist = Math.hypot(Math.abs(x - 3.5), Math.abs(y - 3.5));
		double monster_chance = Misc.randomDouble(GauntletMonster.isOuterBounds(x, y) ? .25D : 0D, 1D - (0.3D * (3 - dist)));
		Location location = minimum.translate(x * ROOM_TILE_SIZE, y * ROOM_TILE_SIZE, 0);
		if (center) {
			return new GauntletRoom(x, y, location, GauntletRooms.STARTER_ROOM, rotation, monster_chance);
		} else if (boss) {
			return new GauntletRoom(x, y, location, GauntletRooms.BOSS_ROOM, rotation, monster_chance);
		} else if (corner) {
			GauntletRooms roomReference = GauntletRooms.findRoomOfType(GauntletRoomType.CORNER);
			return new GauntletRoom(x, y, location, roomReference, getCorrectedRotation(roomReference, x, y), monster_chance);
		} else if (side) {
			GauntletRooms roomReference = GauntletRooms.findRoomOfType(GauntletRoomType.SIDE);
			return new GauntletRoom(x, y, location, roomReference, getCorrectedRotation(roomReference, x, y), monster_chance);
		}
		return new GauntletRoom(x, y, location, GauntletRooms.findRoomOfType(GauntletRoomType.MIDDLE), rotation, monster_chance);
	}
	
	/**
	 * Gets the adjusted rotation for rooms on the border of the layout.
	 * @param x
	 * @param y
	 * @return
	 */
	public int getCorrectedRotation(GauntletRooms room, int x, int y) {
		GauntletRoomType type = room.getType();
		if (x == 0 && y > 0 && type == GauntletRoomType.SIDE) {
			return 3;
		}
		if (x > 0 && y == 0 && type == GauntletRoomType.SIDE) {
			return 2;
		}
		if (x == 6 && y > 0 && type == GauntletRoomType.SIDE) {
			return 1;
		}
		if (x == 0 && y == 0 && type == GauntletRoomType.CORNER) {
			return 2;
		}
		if (x == 0 && y == 6 && type == GauntletRoomType.CORNER) {
			return 3;
		}
		if (x == 6 && y == 0 && type == GauntletRoomType.CORNER) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Gets a room in the specified direction from the current room being stood in.
	 * @param currentRoom The current room.
	 * @param direction The direction being opened.
	 * @return
	 */
	public Optional<GauntletRoom> getRoomInDirection(GauntletRoom currentRoom, int direction) {
		Location translated = currentRoom.getLocation();
		switch(direction) {
		case RoomDirection.EAST:
			translated = translated.translate(17, 0, 0);
			break;
		case RoomDirection.NORTH:
			translated = translated.translate(1, 17, 0);
			break;
		case RoomDirection.SOUTH:
			translated = translated.translate(1, -4, 0);
			break;
		case RoomDirection.WEST:
			translated = translated.translate(-4, 0, 0);
			break;
		
		}
		return getRoom(translated);
	}

	/**
	 * Handles clicking objects in the dungeon.
	 * @param optionIndex The option index.
	 * @param worldObject The object being clicked.
	 * @return
	 */
	public boolean clickObject(int optionIndex, WorldObject worldObject) {
		if (optionIndex == 1) {		
			GauntletResourceNodes node = GauntletResourceNodes.forObjectId(worldObject.getId());
			if (node != null) {
				GlobalObject globalObject = World.getWorld().getGlobalObjects().get(worldObject.getId(), worldObject.getX(), worldObject.getY(), worldObject.getHeight());
				if (Objects.nonNull(globalObject)) {
					World.getWorld().getEventHandler().submit(new GauntletHarvestAction(player, globalObject.asResourceNode(), node));
					return true;
				}
			}
			switch (worldObject.getId()) {
			case 36102:
			case 36101:
				if (player.getItems().hasItemOrEquipped(23861)) {
					Optional<GauntletRoom> roomOpt = getRoom(worldObject.getLocation());
					roomOpt.ifPresent(room -> {
						int requestedDir = room.directionOf(worldObject.getLocation());
						Optional<GauntletRoom> roomInDirOpt = getRoomInDirection(room, requestedDir);
						roomInDirOpt.ifPresent(roomInDir -> {
							if(!roomInDir.isVisible()) {
								room.flagOpen(requestedDir);
								doOuterLights(roomInDir);
								roomInDir.setVisible(true);
								roomInDir.generateResourceNodes(getType());
								roomInDir.generateMonsterSpawns(this);
								roomInDir.getTiles().forEach(tile -> {
									tile.setVisible(true);
									player.getPA().updatePaletteTile(layout, tile);
								});						
								doInsideLights(roomInDir);
							}
						});
					});
				} else {
					player.sendMessage("You need a Crystal sceptre to light nodes.");
				}
				return true;
			case 36062:
				DialogueChain
				.builder()
				.add(0, 
						OptionDialogue
						.builder()
						.title("Are you sure you would like to leave?")
						.option("Yes", (plr) -> 1)
						.option("No", plr -> -1)
					)
				.add(1, 
						CloseDialogue
						.builder()
						.onDialogueOpen(evt -> player.getInstance().destroy())
					)
				.build()
				.open(player);
				return true;
			case 36063:
				GauntletCraftables teleport = GauntletCraftables.TELEPORT_CRYSTAL;
				GauntletCraftables vial = GauntletCraftables.VIAL;
				GauntletCraftables helmet = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_HELM);
				GauntletCraftables body = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_BODY);
				GauntletCraftables legs = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_LEGS);
				GauntletCraftables halberd = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_HALBERD);
				GauntletCraftables staff = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_STAFF);
				GauntletCraftables bow = GauntletCraftables.findLowest(player, GauntletCraftables.PERFECTED_CRYSTAL_BOW);
				player.getMakeWidget()
				.set(teleport.getProduct(), (plr, amt) -> { //teleport crystal
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(teleport), amt));
				})
				.set(vial.getProduct(), (plr, amt) -> { //vial
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(vial), amt));
				})
				.set(helmet.getProduct(), (plr, amt) -> { //helmet
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(helmet), amt));
				})
				.set(body.getProduct(), (plr, amt) -> { //body
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(body), amt));
				})
				.set(legs.getProduct(), (plr, amt) -> { //legs
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(legs), amt));
				})
				.set(halberd.getProduct(), (plr, amt) -> { //halberd
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(halberd), amt));
				})
				.set(staff.getProduct(), (plr, amt) -> { //staff
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(staff), amt));
				})
				.set(bow.getProduct(), (plr, amt) -> { //bow
					World.getWorld().getEventHandler().submit(new GauntletCraftingAction(player, Optional.of(bow), amt));
				})
				.send();
				return true;
			case 36077:
				if (player.getItems().playerHasItem(23872) && System.currentTimeMillis() - player.alchDelay > 300) {
					player.getItems().deleteItem(23872, 1);
					player.startAnimation(896);
					if (Cooking.cookFish(player)) {
						player.getItems().addItem(23874, 1);
						player.getSkills().addExperience(1, Skill.COOKING);
						player.sendMessage("You successfully cook the " + ItemAssistant.getItemName(23874).toLowerCase());
					} else {
						player.getItems().addItem(23873, 1);
						player.sendMessage("Oops! You accidentally burnt the " + ItemAssistant.getItemName(23874).toLowerCase() + "!");
					}
					player.alchDelay = System.currentTimeMillis();
				}
				return true;
			case 36074:
				ToolStorage.open(player);
				return true;
			case 36078:
				if (player.getItems().containsAnyItem(229))
					Fillables.fill(player, new Item(229, 1), worldObject);
				break;
			case 37339:
				Optional<GauntletRoom> room = getRoomByType(GauntletRooms.BOSS_ROOM);
				if (room.isPresent()) {
					boolean inside = Boundary.isIn(player, room.get().getBoundary().inset(2));
					if (!inside) {						
						int direction = RoomDirection.getInverse(room.get().directionOf(player.getLocation()));
						Location inDirection = player.getLocation().getLocationForDirection(direction);
						player.setForceMovement(inDirection.getX(), inDirection.getY(), 10, 11, Misc.getDirectionFromInt(direction), 0x333);
						((TheGauntlet) player.getInstance()).stopwatch.stop();
					} else {
						player.sendMessage("You can't exit this room.");
					}
				}
				return true;
			case 36075: 
				//singing bowl recipes
				return true;
			case 36076:
				//egnoil potion book
				return true;
			}
		} else if(optionIndex == 2) {
			switch (worldObject.getId()) {
			case 36062:
				player.getInstance().destroy();
				return true;

			}
		}
		return false;
	}
	
	public Optional<GauntletRoom> getRoomByType(GauntletRooms type){
		return Stream.of(rooms).flatMap(Arrays::stream).filter(room -> room.getRoom() == type).findFirst();
	}
	
	public void doOuterLights(GauntletRoom openedRoom) {
		Location min = openedRoom.getLocation().translate(-2, -2, 0);
		Location max = openedRoom.getLocation().translate(ROOM_TILE_SIZE + 2, ROOM_TILE_SIZE + 2, 0);
		Boundary outerBoundary = new Boundary(min.getX(), min.getY(), max.getX(), max.getY());
		WorldObject[] lights = outerBoundary
		.insideBorderStream(2)
		.flatMap(loc -> {
			List<WorldObject> objsOnTile = Lists.newArrayList();
			Optional<GauntletRoom> room = getRoom(loc);
			if(!room.isPresent())
				return Stream.empty();
			if(!room.get().isVisible())
				return Stream.empty();
			
			Optional<WorldObject> worldObjOpt = Region.getWorldObject(36102, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36104));
			});

			worldObjOpt = Region.getWorldObject(36101, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36103));
			});
			
			worldObjOpt = Region.getWorldObject(36097, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36096));
			});
			return objsOnTile.stream();

		})
		.collect(Collectors.toList())
		.toArray(new WorldObject[0]);
		
		player.getPA().sendBulkObjects(lights);
		
	}
	
	
	
	public void doInsideLights(GauntletRoom openedRoom) {
		Location min = openedRoom.getLocation().copy();
		Location max = openedRoom.getLocation().translate(ROOM_TILE_SIZE, ROOM_TILE_SIZE, 0);
		
		Boundary innerBoundary = new Boundary(min.getX(), min.getY(), max.getX(), max.getY());
		WorldObject[] lights = innerBoundary
		.insideBorderStream(3)
		.flatMap(loc -> {
			List<WorldObject> objsOnTile = Lists.newArrayList();
			Optional<GauntletRoom> room = getRoom(loc);
			if(!room.isPresent())
				return Stream.empty();
			if(!room.get().isVisible())
				return Stream.empty();
			int dir = room.get().directionOf(loc);
			Optional<GauntletRoom> roomInDir = getRoomInDirection(room.get(), dir);
			if(roomInDir.isPresent() && !roomInDir.get().isVisible())
				return Stream.empty();
			
			Optional<WorldObject> worldObjOpt = Region.getWorldObject(36102, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36104));
			});

			worldObjOpt = Region.getWorldObject(36101, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36103));
			});
			
			worldObjOpt = Region.getWorldObject(36097, loc.getX(), loc.getY(), 1, 10);
			worldObjOpt.ifPresent(worldObj -> {
				objsOnTile.add(worldObj.copy().setId(36096));
			});
			return objsOnTile.stream();
		})
		.collect(Collectors.toList())
		.toArray(new WorldObject[0]);
		player.getPA().sendBulkObjects(lights);
	}
	
	

	/**
	 * Handles destroying the dungeon.
	 */
	public void destroy() {
		Stream.of(rooms).filter(Objects::nonNull).flatMap(Stream::of).forEach(GauntletRoom::destroy);
	}
	
	/**
	 * Used as a testing method to open a certain room.
	 * @param x
	 * @param y
	 */
	public void roomTest(int x, int y) {
		rooms[x][y].getTiles().forEach(tile -> {
			tile.setVisible(true);
			player.getPA().updatePaletteTile(layout, tile);
		});	
	}
	
	/**
	 * 
	 * @param room
	 * @param plrLocation
	 * @return
	 */
	public Location getChunkRelativeTo(GauntletRoom room, Location plrLocation) {	
		int x = room.getLocation().getRegionX() - plrLocation.getRegionX() + 6;
		int y = room.getLocation().getRegionY() - plrLocation.getRegionY() + 6;
		return new Location(x, y);
	}
	
	/**
	 * Gets a room by the location provided.
	 * @param location The location.
	 * @return
	 */
	public Optional<GauntletRoom> getRoom(Location location) {
		return Stream.of(rooms).flatMap(Arrays::stream).filter(room -> room.getBoundary().contains(location)).findFirst();
	}
	
}
