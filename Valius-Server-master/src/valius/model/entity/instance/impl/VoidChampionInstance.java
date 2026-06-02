package valius.model.entity.instance.impl;

import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.instance.Instance;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.agility.AgilityHandler;

public class VoidChampionInstance extends Instance {
	
	private static final Location VOID_CHAMPION_SPAWN = Location.of(1577, 3950);
	private static final Boundary CHAMPION_AREA = new Boundary(1560, 3930, 1660, 4030);
	public static boolean voidChamp = false;
	

	@Override
	public boolean destroyOnEmpty() {
		return true;
	}

	@Override
	protected void onLeave(Entity entity) {
		voidChamp = false;
	}

	@Override
	protected void onEnter(Entity entity) {
		if (entity.isPlayer()) {
			AgilityHandler.delayEmote(entity.asPlayer(), "PRAY", entity.getX(), entity.getY(), getHeight(), 2);
			voidChamp = true;
		}
	}

	@Override
	protected void onDestroy() {
		voidChamp = false;
	}

	@Override
	protected void initialize() {
		NPC void_champion = NPCHandler.spawnNpc(6014, VOID_CHAMPION_SPAWN.getX(), VOID_CHAMPION_SPAWN.getY(), this.getHeight(), 1, 750, 80, 250, 600);
		void_champion.setInstance(this);
	}

	@Override
	public boolean onDeath(Entity entity) {
		if (entity.isPlayer()) {
			this.leave(entity);
			voidChamp = false;
		} else {
			entity.asNPC().needRespawn = true;
			entity.asNPC().actionTimer = 40;
			entity.asNPC().npcType = 6014;
		}
		return false;
	}

	@Override
	public void tick() {
		for(Player player : this.getPlayers()) {
			if (!Boundary.isIn(player, CHAMPION_AREA)) {
				this.leave(player);
				voidChamp = false;
			}
		}
		
	}

}
