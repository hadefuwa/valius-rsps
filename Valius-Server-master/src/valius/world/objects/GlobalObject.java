package valius.world.objects;

import java.awt.Rectangle;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import valius.clip.ObjectDef;
import valius.model.Location;
import valius.model.entity.player.Boundary;
import valius.model.map.ResourceNode;
import valius.util.Misc;

/**
 * A global object is a visual model that is viewed by all players within a region. This class represents the identification value, x and y position, as well as the height of the
 * object.
 * 
 * A key factor is the ticks remaining. The ticksRemaining variable represents how many game ticks this object will remain visible for. If the value is negative the object will
 * remain indefinitly. On the flip side, if the value is positive then every tick the total remaining will reduce by one until it hits zero.
 * 
 * @author Jason MacKeigan
 * @date Dec 17, 2014, 6:18:20 PM
 */
public class GlobalObject {

	private int id;

	private int x;

	private int y;

	private int height;

	private int face;

	@Setter
	private int ticksRemaining;

	private int restoreId;

	private int type;

	public GlobalObject(int id, int x, int y, int height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.height = height;
		this.type = 10;
	}

	public GlobalObject(int id, int x, int y, int height, int face) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.height = height;
		this.face = face;
		this.type = 10;
	}

	public GlobalObject(int id, int x, int y, int height, int face, int type) {
		this(id, x, y, height, face);
		this.type = type;
	}

	public GlobalObject(int id, int x, int y, int height, int face, int type, int ticksRemaining) {
		this(id, x, y, height, face, type);
		this.ticksRemaining = ticksRemaining;
	}

	public GlobalObject(int id, int x, int y, int height, int face, int type, int ticksRemaining, int restoreId) {
		this(id, x, y, height, face, type, ticksRemaining);
		this.restoreId = restoreId;
	}

	public GlobalObject(int id, Location p) {
		this(id, p.getX(), p.getY(), p.getZ(), 0, 10, -1, 0);
	}

	public void removeTick() {
		this.ticksRemaining--;
	}

	public int getObjectId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getFace() {
		return face;
	}

	public int getTicksRemaining() {
		return ticksRemaining;
	}

	public int getRestoreId() {
		return restoreId;
	}
	
	public Location getLocation() {
		return new Location(x, y, height);
	}

	public int getType() {
		return type;
	}
	
	public boolean exists() {
		return getTicksRemaining() > 0;
	}
	
	public boolean collides(Location other) {
		ObjectDef def = ObjectDef.forID(this.id);
		Boundary boundary = new Boundary(new Rectangle(x, y, def.length, def.width));
		return Boundary.isIn(other, boundary);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + face;
		result = prime * result + height;
		result = prime * result + id;
		result = prime * result + restoreId;
		result = prime * result + type;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlobalObject other = (GlobalObject) obj;
		if (face != other.face)
			return false;
		if (height != other.height)
			return false;
		if (id != other.id)
			return false;
		if (restoreId != other.restoreId)
			return false;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public ResourceNode asResourceNode() {
		return (ResourceNode) this;
	}

}
