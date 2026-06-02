package valius.model.holiday.christmas;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import valius.Config;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.util.Misc;

public class AntisantaMinion {

	/**
	 * The id of the minion
	 */
	static final int MINION_ID = 4999;

	/**
	 * The x and y location of the npcs
	 */
	private int xLocation, yLocation;

	/**
	 * The spawn locations
	 */
	private static final int[][] MINION_SPAWNS = new int[][] { 
		{ 3037, 3489 }, { 3042, 3487 }, { 3045, 3485 }, { 3045, 3481 }, { 3037, 3482 }, 
		{ 3040, 3474 }, { 3041, 3463 }, { 3049, 3461 }, { 3060, 3463 }, { 3053, 3467 }, 
		{ 3057, 3473 }, { 3051, 3465 }, };

	/**
	 * Updates and spawns the minions
	 */
	public List<NPC> spawns = new ArrayList<>();
	int t = 0;
	
	public void update() {
		t++;
		if (t == 10) {
			GlobalMessages.send("Anti-Santa has spawned more minions! Defeat them to help Santa!", GlobalMessages.MessageType.EVENT);
			t = 0;
		}
		killAllSpawns();
		for (int i = 0; i < Config.AMOUNT_OF_SANTA_MINIONS; i++) {
			generateLocation();
			NPC npc = NPCHandler.spawn(MINION_ID, xLocation, yLocation, 0, 1, 130, 4, 50, 60, true);
			npc.setNoRespawn(true);
			spawns.add(npc);

		}

		@SuppressWarnings("unused")
		int index = 0;
		for (int i = 0; i < MINION_SPAWNS.length; i++) {
			if (xLocation == MINION_SPAWNS[i][0] && yLocation == MINION_SPAWNS[i][1]) {
				index = i + 1;
			}
		}
	}

	public void killAllSpawns() {

		for (NPC npc : getSpawns()) {
			if (npc != null) {
				NPCHandler.npcs[npc.getIndex()] = null;
			}
		}
		for (int i = 0; i < NPCHandler.npcs.length; i++) {
			if (NPCHandler.npcs[i] != null && NPCHandler.npcs[i].npcId == 4999) {
				if (Boundary.isIn(NPCHandler.npcs[i], Boundary.CHRISTMAS) && NPCHandler.npcs[i].getHeight() == 0) {
					NPCHandler.npcs[i] = null;

				}
			}
		}
	}

	public List<NPC> getSpawns() {
		return spawns;
	}

	/**
	 * Generates a new, random location for the container.
	 */
	private void generateLocation() {
		int oldX = xLocation;
		int oldY = yLocation;
		int attempts = 0;
		while (oldX == xLocation && oldY == yLocation && attempts++ < 50) {
			int index = Misc.random(MINION_SPAWNS.length - 1);
			int locX = MINION_SPAWNS[index][0];
			int locY = MINION_SPAWNS[index][1];
			if (locX != oldX && locY != oldY) {
				xLocation = locX;
				yLocation = locY;
				break;
			}
		}
	}

	/**
	 * Returns the base location
	 * 
	 * @return
	 */
	public Point getLocation() {
		return new Point(xLocation, yLocation);
	}

}

