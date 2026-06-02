package valius.clip;

import lombok.Data;
import lombok.Getter;
import valius.model.Location;
import valius.world.objects.GlobalObject;

@Data
public class WorldObject {

	@Getter
	public int x, y, height, id, type, face;

	public WorldObject(int id, int x, int y, int height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.height = height;
	}

	public WorldObject(int id, int x, int y, int height, int face) {
		this(id, x, y, height);
		this.face = face;
		this.type = 10;
	}

	public WorldObject(int id, int x, int y, int height, int type, int face) {
		this(id, x, y, height);
		this.face = face;
		this.type = type;
	}
	
	public Location getLocation() {
		return Location.of(x, y, height);
	}
	
	public WorldObject setId(int id) {
		this.id = id;
		return this;
	}
	

	public WorldObject copy() {
		WorldObject worldObject = new WorldObject(id, x, y, height, type, face);
		return worldObject;
	}
	
	public WorldObject copy(Location newLocation) {
		WorldObject worldObject = new WorldObject(id, newLocation.getX(), newLocation.getY(), newLocation.getZ(), type, face);
		return worldObject;
	}

	public GlobalObject toGlobalObject() {
		return new GlobalObject(id, x, y, height, face, type);
	}
	

}