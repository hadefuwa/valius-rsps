package valius.model.entity.player.skills.mining;

import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.world.World;

/**
 * The {@link Mining} class will manage all operations that the mining skill entails.
 * 
 * @author Jason MacKeigan
 * @date Feb 18, 2015, 5:09:38 PM
 */
public class Mining {
	

	private static final int MINIMUM_EXTRACTION_TIME = 2;

	/**
	 * The player that this {@link Mining} object is created for
	 */
	private final Player player;

	/**
	 * Constructs a new mining class for a singular player
	 * 
	 * @param player the player this class is being created for
	 */
	public Mining(Player player) {
		this.player = player;
	}

	/**
	 * This function allows a singular player to start mining if possible
	 * 
	 * @param objectId the object the player is trying to mine from
	 * @param location the location of the object
	 */
	public void mine(int objectId, Location location) {
		Mineral mineral = Mineral.forObjectId(objectId);
		if (mineral == null) {
			return;
		}
		if (player.getSkills().getLevel(Skill.MINING) < mineral.getLevel()) {
			player.sendMessage("You need a mining level of at least " + mineral.getLevel() + " to mine this.");
			return;
		}
		if (World.getWorld().getGlobalObjects().exists(Mineral.EMPTY_VEIN, location.getX(), location.getY(), location.getZ()) && mineral.isDepletable()) {
			player.sendMessage("This vein contains no more minerals.");
			return;
		}
		Pickaxe pickaxe = Pickaxe.getBestPickaxe(player);
		if (pickaxe == null) {
			player.sendMessage("You need a pickaxe to mine this.");
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.getDH().sendStatement("You have no more free slots.");
			player.nextChat = -1;
			return;
		}
		int levelReduction = (int) Math.floor(player.getSkills().getLevel(Skill.MINING) / 10);
		int pickaxeReduction = pickaxe.getExtractionReduction();
		int extractionTime = mineral.getExtractionRate() - (levelReduction + pickaxeReduction);
		if (extractionTime < MINIMUM_EXTRACTION_TIME) {
			extractionTime = MINIMUM_EXTRACTION_TIME;
		}
		player.sendMessage("You swing your pickaxe.");
		player.startAnimation(pickaxe.getAnimation());
		player.turnPlayerTo(location.getX(), location.getY());
		player.getSkilling().stop();
		player.getSkilling().setSkill(Skill.MINING);
		World.getWorld().getEventHandler().submit(new MiningEvent(player, objectId, location, mineral, pickaxe, extractionTime));
	}

	/**
	 * This function allows a singular player to start mining on an npc if possible
	 * 
	 * @param npc the non playable character we're mining from
	 * @param mineral the mineral we're going to obtain from mining
	 * @param location the location of the npc and or mineral
	 */
	public void mine(NPC npc, Mineral mineral, Location location) {
		if (npc == null || npc.isDead) {
			player.sendMessage("This contains no more minerals.");
			return;
		}
		Pickaxe pickaxe = Pickaxe.getBestPickaxe(player);
		if (player.getSkills().getLevel(Skill.MINING) < 85) {
			player.sendMessage("You need a mining level of at least 85 to mine this.");
			return;
		}
		if (pickaxe == null) {
			player.sendMessage("You need a pickaxe to mine this vein.");
			return;
		}
		if (player.getItems().freeSlots() == 0) {
			player.getDH().sendStatement("You have no more free slots.");
			player.nextChat = -1;
			return;
		}
		int levelReduction = (int) Math.floor(player.getSkills().getLevel(Skill.MINING) / 10);
		int pickaxeReduction = pickaxe.getExtractionReduction();
		int extractionTime = mineral.getExtractionRate() - (levelReduction + pickaxeReduction);
		if (extractionTime < MINIMUM_EXTRACTION_TIME) {
			extractionTime = MINIMUM_EXTRACTION_TIME;
		}
		player.sendMessage("You swing your pickaxe at the rock.");
		player.startAnimation(pickaxe.getAnimation());
		player.turnPlayerTo(location.getX(), location.getY());
		player.getSkilling().stop();
		player.getSkilling().setSkill(Skill.MINING);
		World.getWorld().getEventHandler().submit(new MiningEvent(player, npc, location, mineral, pickaxe, extractionTime));
	}

}
