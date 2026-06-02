package valius.model.entity.player.skills.hunter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import valius.clip.Region;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.SkillPets;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.hunter.trap.Trap;
import valius.model.entity.player.skills.hunter.trap.TrapProcessor;
import valius.model.entity.player.skills.hunter.trap.TrapTask;
import valius.model.entity.player.skills.hunter.trap.Trap.TrapState;
import valius.model.entity.player.skills.hunter.trap.Trap.TrapType;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * The class which holds static functionality for the hunter skill.
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 */
public final class Hunter {

	/**
	 * The mappings which contain each trap by player on the world.
	 */
	public static final Map<Player, TrapProcessor> GLOBAL_TRAPS = new HashMap<>();

	/**
	 * Retrieves the maximum amount of traps a player can lay.
	 * @param player	the player to lay a trap down for.
	 * @return a numerical value determining the amount a player can lay.
	 */
	private static int getMaximumTraps(Player player) {
		int level = player.getSkills().getLevel(Skill.HUNTER);
		return player.inWild() ? level / 20 + 2 : level / 20 + 1;

	}

	/**
	 * Attempts to abandon the specified {@code trap} for the player.
	 * @param trap		the trap that was abandoned.
	 * @param logout	if the abandon was due to the player logging out.
	 */
	public static void abandon(Player player, Trap trap, boolean logout) {
		if(GLOBAL_TRAPS.get(player) == null) {
			return;
		}
		
		if(logout) {
			GLOBAL_TRAPS.get(player).getTraps().forEach(t -> {
				t.setAbandoned(true);
				World.getWorld().getGlobalObjects().remove(t.getObject());
				World.getWorld().getGlobalObjects().remove(t.getObject().getObjectId(), t.getObject().getX(), t.getObject().getY(), t.getObject().getHeight());
				World.getWorld().getItemHandler().createGroundItem(player, t.getType().getItemId(), t.getObject().getX(), t.getObject().getY(), t.getObject().getHeight(), 1, player.getIndex());
			});
			GLOBAL_TRAPS.get(player).getTraps().clear();
		} else {
			GLOBAL_TRAPS.get(player).getTraps().remove(trap);
			trap.setAbandoned(true);
			World.getWorld().getGlobalObjects().remove(trap.getObject());
			World.getWorld().getGlobalObjects().remove(trap.getObject().getObjectId(), trap.getObject().getX(), trap.getObject().getY(), trap.getObject().getHeight());
			World.getWorld().getItemHandler().createGroundItem(player, trap.getType().getItemId(), trap.getObject().getX(), trap.getObject().getY(), trap.getObject().getHeight(), 1, player.getIndex());
			player.sendMessage("You have abandoned your trap...");
		}

		if(GLOBAL_TRAPS.get(player).getTraps().isEmpty()) {
			GLOBAL_TRAPS.get(player).setTask(Optional.empty());
			GLOBAL_TRAPS.remove(player);
		}
	}

	/**
	 * Attempts to lay down the specified {@code trap} for the specified {@code player}.
	 * @param player	the player to lay the trap for.
	 * @param trap		the trap to lay down for the player.
	 * @return {@code true} if the trap was laid, {@code false} otherwise.
	 */
	public static boolean lay(Player player, Trap trap) {
		if(!player.last_trap_layed.elapsed(1200)) {
			return false;
		}

		player.last_trap_layed.reset();
		
		if (!Boundary.isIn(player, Boundary.HUNTER_BOUNDARIES) && !Boundary.isIn(player, Boundary.DONATOR_ZONE) && !Boundary.isIn(player, Boundary.EDZ)) {
			player.sendMessage("This is not a suitable spot to place a trap.");
			return false;
		}

		GLOBAL_TRAPS.putIfAbsent(player, new TrapProcessor());

		if(!GLOBAL_TRAPS.get(player).getTask().isPresent()) {
			GLOBAL_TRAPS.get(player).setTask(new TrapTask(player));
			CycleEventHandler.getSingleton().addEvent(player, GLOBAL_TRAPS.get(player).getTask().get(), 10);
		}

		if(GLOBAL_TRAPS.get(player).getTraps().size() >= getMaximumTraps(player)) {
			player.sendMessage("You cannot lay more then " + getMaximumTraps(player) + " traps with your hunter level.");
			return false;
		}

		if(World.getWorld().getGlobalObjects().anyExists(player.getX(), player.getY(), player.getHeight())) {
			player.sendMessage("You can't lay down your trap here.");
			return false;
		}

		GLOBAL_TRAPS.get(player).getTraps().add(trap);

		trap.submit();
		player.startAnimation(827);
		player.getItems().deleteItem(trap.getType().getItemId(), 1);
		World.getWorld().getGlobalObjects().add(trap.getObject());
		//World.getWorld().getGlobalObjects().add(trap.getObject());
		if (Region.getClipping(player.getX() - 1, player.getY(), player.getHeight(), -1, 0)) {
			player.getPA().walkTo2(-1, 0);
		} else if (Region.getClipping(player.getX() + 1, player.getY(), player.getHeight(), 1, 0)) {
			player.getPA().walkTo2(1, 0);
		} else if (Region.getClipping(player.getX(), player.getY() - 1, player.getHeight(), 0, -1)) {
			player.getPA().walkTo2(0, -1);
		} else if (Region.getClipping(player.getX(), player.getY() + 1, player.getHeight(), 0, 1)) {
			player.getPA().walkTo2(0, 1);
		}
		return true;
	}

	/**
	 * Attempts to pick up the trap for the specified {@code player}.
	 * @param player	the player to pick this trap up for.
	 * @param id		the object id that was clicked.
	 * @return {@code true} if the trap was picked up, {@code false} otherwise.
	 */
	public static boolean pickup(Player player, GlobalObject object) {
		Optional<TrapType> type = TrapType.getTrapByObjectId(object.getObjectId());
		
		if (System.currentTimeMillis() - player.lastPickup < 2500)
			return false;		

		if(!type.isPresent()) {
			return false;
		}

		Trap trap = getTrap(player, object).orElse(null);

		if(trap == null) {
			return false;
		}

		if(trap.getPlayer() == null) {
			player.sendMessage("You can't pickup someone elses trap...");
			return false;
		}
		
		if(trap.getState().equals(TrapState.CAUGHT)) {
			return false;
		}

		GLOBAL_TRAPS.get(player).getTraps().remove(trap);

		if(GLOBAL_TRAPS.get(player).getTraps().isEmpty()) {
			GLOBAL_TRAPS.get(player).setTask(Optional.empty());
			GLOBAL_TRAPS.remove(player);
		}

		trap.onPickUp();
		World.getWorld().getGlobalObjects().remove(trap.getObject());
		World.getWorld().getGlobalObjects().remove(trap.getObject().getObjectId(), trap.getObject().getX(), trap.getObject().getY(), trap.getObject().getHeight());
		player.getItems().addItem(trap.getType().getItemId(), 1);
		player.startAnimation(827);
		player.lastPickup = System.currentTimeMillis();
		return true;
	}


	/**
	 * Attempts to claim the rewards of this trap.
	 * @param player		the player attempting to claim the items.
	 * @param object		the object being interacted with.
	 * @return {@code true} if the trap was claimed, {@code false} otherwise.
	 */
	public static boolean claim(Player player, GlobalObject object) {
		Trap trap = getTrap(player, object).orElse(null);
		
		if (System.currentTimeMillis() - player.lastPickup < 2500)
			return false;		

		if(trap == null) {
			player.sendMessage("You can't pickup someone elses trap...");
			return false;
		}

		if(!trap.canClaim(object)) {
			return false;
		}

		if(trap.getPlayer() == null) {
			player.sendMessage("You can't claim the rewards of someone elses trap...");
			return false;
		}

		if(!trap.getState().equals(TrapState.CAUGHT)) {
			return false;
		}
		
		double percentOfXp = (trap.experience() / 100) * 2.5;

		Arrays.stream(trap.reward()).forEach(reward -> player.getItems().addItem(reward.getId(), reward.getAmount()));

		player.getPA().addSkillXP((int) ((int) trap.experience() + (player.getItems().isWearingItem(10071) ? percentOfXp : 0)), 21, true);

		GLOBAL_TRAPS.get(player).getTraps().remove(trap);
		
		if(GLOBAL_TRAPS.get(player).getTraps().isEmpty()) {
			GLOBAL_TRAPS.get(player).setTask(Optional.empty());
			GLOBAL_TRAPS.remove(player);
		}
		
		World.getWorld().getGlobalObjects().remove(trap.getObject());
		World.getWorld().getGlobalObjects().remove(trap.getObject().getObjectId(), trap.getObject().getX(), trap.getObject().getY(), trap.getObject().getHeight());
		player.getItems().addItem(trap.getType().getItemId(), 1);
		player.startAnimation(827);
		player.lastPickup = System.currentTimeMillis();
		
		int randomGray = Misc.random(1500);
		int randomRed = Misc.random(2500);
		int randomBlack = Misc.random(3500);
		int randomGold = Misc.random(15000);
		
		if (Misc.random(25) == 0) {
			int sPoints = Misc.random(1, 5);
            player.skillPoints += sPoints;
            player.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
        }
		
		if (randomGold == 2 && !SkillPets.HUNTERGOLD.hasPet(player)) {
			PetHandler.skillPet(player, PetHandler.SkillPets.HUNTERGOLD);
		}
		switch (trap.getType().getItemId()) {
		case 10033:
			if (randomGray == 25 && !SkillPets.HUNTERGREY.hasPet(player)) {
				PetHandler.skillPet(player, PetHandler.SkillPets.HUNTERGREY);
			}
			break;
			
		case 10008:
			if (randomRed == 15 && !SkillPets.HUNTERRED.hasPet(player)) {
				PetHandler.skillPet(player, PetHandler.SkillPets.HUNTERRED);
			}
			DailyTasks.increase(player, PossibleTasks.RED_CHINCHOMPAS);
			break;
			
		case 11959:
			if (randomBlack == 8 && !SkillPets.HUNTERBLACK.hasPet(player)) {
				PetHandler.skillPet(player, PetHandler.SkillPets.HUNTERBLACK);
			}
			break;
		}
		return true;
	}


	/**
	 * Gets a trap for the specified global object given.
	 * @param player	the player to return a trap for.
	 * @param object	the object to compare.
	 * @return a trap wrapped in an optional, {@link Optional#empty()} otherwise.
	 */
	public static Optional<Trap> getTrap(Player player, GlobalObject object) {
		return !GLOBAL_TRAPS.containsKey(player) ? Optional.empty() : GLOBAL_TRAPS.get(player).getTraps().stream().filter(trap -> trap.getObject().getObjectId() == object.getObjectId() && trap.getObject().getX() == object.getX() && trap.getObject().getY() == object.getY() && trap.getObject().getHeight() == object.getHeight()).findAny();
	}
}
