package valius.model.entity.instance.impl;

import valius.event.CycleEvent;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.instance.Instance;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.agility.AgilityHandler;

public class HydraInstance extends Instance {

	private static final Location HYDRA_SPAWN = Location.of(1364, 10265);
	private static final Boundary HYDRA_ROOM = new Boundary(1356, 10257, 1377, 10278);
	private static final Boundary HYDRA_WALKWAY = new Boundary(1345, 10224, 1380, 10280);
	
	@Override
	public boolean destroyOnEmpty() {
		return true;
	}

	@Override
	protected void onLeave(Entity entity) {
		
	}

	@Override
	protected void onEnter(Entity entity) {
		if (entity.isPlayer()) {
			AgilityHandler.delayEmote(entity.asPlayer(), "JUMP", entity.getX(), entity.getY() + 2, getHeight(), 2);
		}
	}

	@Override
	protected void onDestroy() {
	}	

	@Override
	protected void initialize() {
		System.out.println("Init");
		NPC hydra = NPCHandler.spawnNpc(8615, HYDRA_SPAWN.getX(), HYDRA_SPAWN.getY(), this.getHeight(), 1, 1100, 0, 100, 100);
		hydra.setInstance(this);
	}

	@Override
	public void tick() {
		for(Player player : this.getPlayers()) {
			if (!Boundary.isIn(player, HYDRA_ROOM, HYDRA_WALKWAY)) {
				this.leave(player);
			}
		}
	}

	@Override
	public boolean onDeath(Entity entity) {
		if (entity.isPlayer()) {
			leave(entity);
		} else {
			entity.asNPC().needRespawn = true;
			entity.asNPC().actionTimer = 42; //~25 seconds
			entity.asNPC().npcType = 8615;
		}
		return false;
	}

}
