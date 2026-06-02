package valius.model.entity.player.skills.firemake;

import valius.clip.Region;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.SkillPets;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.mode.ModeType;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class Firemaking {
	
	public static int[] pyromancerOutfit = { 20704, 20706, 20708, 20710 };
	
	public static void lightFire(final Player player, final int logUsed, final String usage) {
		double osrsExperience = 0;
		double regExperience = 0;
		int pieces = 0;
		for (int i = 0; i < pyromancerOutfit.length; i++) {
			if (player.getItems().isWearingItem(pyromancerOutfit[i])) {
				pieces++;
			}
		}
		final int[] time = new int[3];
		final int[] coords = new int[2];
		final LogData log = LogData.getLogData(player, logUsed);
		if(log == null)
			return;
		final int level = log.getlevelRequirement();
		final String name = log.name().toLowerCase().replaceAll("_", " ");

		if (Region.getClipping(player.getX(), player.getY(), player.getHeight()) != 0
				|| World.getWorld().getGlobalObjects().anyExists(player.getX(), player.getY(), player.getHeight()) || player.inBank()
				|| Boundary.isIn(player, Boundary.DUEL_ARENA) || Boundary.isIn(player, Boundary.HALLOWEEN_ORDER_MINIGAME)) {
			player.sendMessage("You cannot light a fire here.");
			return;
		}
		if (player.getSkills().getLevel(Skill.FIREMAKING) < level) {
			player.sendMessage("You need a firemaking level of at least " + level + " to light the " + name + ".");
			return;
		}
		if (System.currentTimeMillis() - player.lastFire < 1200) {
			return;
		}
		if (player.playerIsFiremaking) {
			return;
		}
		if (log.getlogId() == logUsed) {
			if (usage != "infernal_axe") {
				if (!player.getItems().playerHasItem(logUsed)) {
					player.sendMessage("You do not have anymore of this log.");
					return;
				}
			}

			coords[0] = player.getX();
			coords[1] = player.getY();

			if (usage == "tinderbox") {
				if (System.currentTimeMillis() - player.lastFire > 3000) {
					player.startAnimation(733);
					time[0] = 4;
					time[1] = 3;
				} else {
					time[0] = 1;
					time[1] = 2;
				}
				
				player.playerIsFiremaking = true;
				if (log.getlogId() == 1521) {
					player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.BURN_OAK);
				}
				player.getItems().deleteItem(log.getlogId(), player.getItems().getItemSlot(log.getlogId()), 1);
				World.getWorld().getItemHandler().createGroundItem(player, log.getlogId(), coords[0], coords[1], player.getHeight(), 1,
						player.getIndex());

				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						World.getWorld().getGlobalObjects().add(new GlobalObject(5249, coords[0], coords[1], player.getHeight(), 0, 10, 50, -1));
						World.getWorld().getItemHandler().removeGroundItem(player, log.getlogId(), coords[0], coords[1], player.getHeight(), false);
						player.playerIsFiremaking = false;
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, time[0]);

				if (Region.getClipping(player.getX() - 1, player.getY(), player.getHeight(), -1, 0)) {
					player.getPA().walkTo(-1, 0);
				} else if (Region.getClipping(player.getX() + 1, player.getY(), player.getHeight(), 1, 0)) {
					player.getPA().walkTo(1, 0);
				} else if (Region.getClipping(player.getX(), player.getY() - 1, player.getHeight(), 0, -1)) {
					player.getPA().walkTo(0, -1);
				} else if (Region.getClipping(player.getX(), player.getY() + 1, player.getHeight(), 0, 1)) {
					player.getPA().walkTo(0, 1);
				}
				Achievements.increase(player, AchievementType.FIRE, 1);
				if (log.getlogId() == 1515)
					DailyTasks.increase(player, PossibleTasks.LIGHT_YEW_LOGS);
				if (log.getlogId() == 1513)
					DailyTasks.increase(player, PossibleTasks.LIGHT_MAGIC_LOGS);
				CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						player.startAnimation(65535);
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, time[1]);
				player.turnPlayerTo(player.getX() + 1, player.getY());
				player.lastFire = System.currentTimeMillis();
			}
			/**
			 * Experience calculation
			 */
			osrsExperience = log.getExperience() + log.getExperience() / 10 * pieces;
			regExperience = log.getExperience() + log.getExperience() / 10 * pieces;
			
			if (usage == "infernal_axe") {
				player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience / 2 : regExperience / 2), 11, true);
			} else {
				player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience : regExperience), 11, true);
			}
			if (Misc.random(25) == 0) {
				int sPoints = Misc.random(1, 5);
                player.skillPoints += sPoints;
                player.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
            }
			if (Misc.random(6000) == 1) {
				if (!SkillPets.FIREMAKING.hasPet(player)) {
					PetHandler.skillPet(player, SkillPets.FIREMAKING);
				}

			}
			player.sendMessage("You light the " + name + ".");
		}
	}
}
