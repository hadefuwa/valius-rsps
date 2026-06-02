package valius.model.entity.player.skills.woodcutting;

import java.util.Optional;

import valius.clip.Region;
import valius.clip.WorldObject;
import valius.content.SkillcapePerks;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.desert.DesertDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.event.Event;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.SkillPets;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.firemake.Firemaking;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class WoodcuttingEvent extends Event<Player> {
	private Tree tree;
	private Hatchet hatchet;
	private int objectId, x, y, chops;
	
	private int[] lumberjackOutfit = { 10933, 10939, 10940, 10941 };

	public WoodcuttingEvent(Player player, Tree tree, Hatchet hatchet, int objectId, int x, int y) {
		super("skilling", player, 1);
		this.tree = tree;
		this.hatchet = hatchet;
		this.objectId = objectId;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		double osrsExperience;
		double experience;
		int pieces = 0;
		pieces=handleOutfit(pieces);
		osrsExperience = tree.getExperience() + tree.getExperience() / 10 * pieces;
		experience = tree.getExperience() + tree.getExperience() / 10 * pieces;
		if (canChop()) return;
		randomBloodMoney(attachment);
		if (Misc.random(25) == 0) {
			int sPoints = Misc.random(1, 5);
            attachment.skillPoints += sPoints;
            attachment.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
        }
		
		if (Misc.random(1, 10000) == 5) {
			attachment.getItems().addItemUnderAnyCircumstance(13322, 1);
			GlobalMessages.send("" + attachment.playerName + " Has received a pet Beaver while woodcutting!", GlobalMessages.MessageType.LOOT);
		}
		
		if (Misc.random(300) == 0 && attachment.getInterfaceEvent().isExecutable()) {
			attachment.getInterfaceEvent().execute();
			super.stop();
			return;
		}
		chops++;
		int chopChance = 1 + (int) (tree.getChopsRequired() * hatchet.getChopSpeed());
		if (Boundary.isIn(attachment, Boundary.WOODCUTTING_GUILD_BOUNDARY)){
			chopChance *= 1.5;
		}
		if (Misc.random(tree.getChopdownChance()) == 0 || tree.equals(Tree.NORMAL) && Misc.random(chopChance) == 0) {
			int face = 0;
			Optional<WorldObject> worldObject = Region.getWorldObject(objectId, x, y, attachment.getLocation().getZ() % 4, 10);
			if (worldObject.isPresent()) {
				face = worldObject.get().getFace();
			}
			int stumpId = 0;
			if (tree.equals(Tree.REDWOOD)) {
				face = (attachment.getX() < 1568) ? 1 : (attachment.getX() > 1573) ? 3 : (attachment.getY() < 3480) ? 0 : 2;
				attachment.sendMessage("objectId: "+objectId);
				if (objectId == 29668)
					stumpId = 29669;
				else if (objectId == 29670)
					stumpId = 29671;
			}
			World.getWorld().getGlobalObjects().add(new GlobalObject(tree.equals(Tree.REDWOOD) ? stumpId : tree.getStumpId(), x, y, attachment.getHeight(), face, 10, tree.getRespawnTime(), objectId));
			
			attachment.getItems().addItem(tree.getWood(), 1);
			attachment.getPA().addSkillXP((int) (attachment.getRights().isOrInherits(Right.EXTREME) ? osrsExperience : experience) , Skill.WOODCUTTING.getId(), true);
			Achievements.increase(attachment, AchievementType.WOODCUT, 1);
			handleRewards();
			super.stop();
			return;
		}
		if (!tree.equals(Tree.NORMAL)) {
			if (Misc.random(chopChance) == 0 || chops >= tree.getChopsRequired()) {
				chops = 0;
				int random = Misc.random(4);
				attachment.getPA().addSkillXP((int) (attachment.getRights().isOrInherits(Right.EXTREME) ? osrsExperience : experience) , Skill.WOODCUTTING.getId(), true);
				Achievements.increase(attachment, AchievementType.WOODCUT, 1);
				if ((attachment.getItems().isWearingItem(13241) || attachment.getItems().playerHasItem(13241)) && random == 2) {
					Firemaking.lightFire(attachment, tree.getWood(), "infernal_axe");
					return;
				}
				handleDiary(tree);
				handleWildernessRewards();
				attachment.getItems().addItem(tree.getWood(), SkillcapePerks.WOODCUTTING.isWearing(attachment) || (SkillcapePerks.isWearingMaxCape(attachment) && attachment.getWoodcuttingEffect()) && Misc.random(2) == 1 ? 2 : 1);
			}
		}
		if (super.getElapsedTicks() % 4 == 0) {
			attachment.startAnimation(hatchet.getAnimation());
		}
	}

	private int handleOutfit(int pieces) {

		for (int aLumberjackOutfit : lumberjackOutfit) {
			if (attachment.getItems().isWearingItem(aLumberjackOutfit)) {
				pieces+=2;
			}
		}
		return pieces;
	}

	private boolean canChop() {

		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			super.stop();
			return true;
		}
		if (!attachment.getItems().playerHasItem(hatchet.getItemId()) && !attachment.getItems().isWearingItem(hatchet.getItemId())) {
			attachment.sendMessage("Your axe has disappeared.");
			super.stop();
			return true;
		}
		if (attachment.getSkills().getLevel(Skill.WOODCUTTING) < hatchet.getLevelRequired()) {
			attachment.sendMessage("You no longer have the level required to operate this hatchet.");
			super.stop();
			return true;
		}
		if (attachment.getItems().freeSlots() == 0) {
			attachment.sendMessage("You have run out of free inventory space.");
			super.stop();
			return true;
		}
		return false;
	}

	private void handleWildernessRewards() {

		if (Boundary.isIn(attachment, Boundary.RESOURCE_AREA)) {
			if (Misc.random(20) == 5) {
				int randomAmount = Misc.random(30) + 10;
				attachment.sendMessage("You received " + randomAmount + " blood money while woodcutting!");
				attachment.getItems().addItem(13307, randomAmount);
			}
		}
	}

	private void handleDiary(Tree tree) {
		switch (tree) {
			case MAGIC:
				if (Boundary.isIn(attachment, Boundary.AL_KHARID_BOUNDARY)) {
					attachment.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.CHOP_MAGIC_AL);
				}
				if (Boundary.isIn(attachment, Boundary.RESOURCE_AREA_BOUNDARY)) {
					attachment.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MAGIC_LOG_WILD);
				}
				if (Boundary.isIn(attachment, Boundary.SEERS_BOUNDARY)) {
					attachment.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CUT_MAGIC_SEERS);
				}
				DailyTasks.increase(attachment, PossibleTasks.MAGIC_LOGS);
				break;
			case MAPLE:
				break;
			case NORMAL:
				break;
			case OAK:
				if (Boundary.isIn(attachment, Boundary.RELLEKKA_BOUNDARY)) {
					attachment.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.CHOP_OAK_FREM);
				}
				break;
			case WILLOW:
				if (Boundary.isIn(attachment, Boundary.FALADOR_BOUNDARY)) {
					attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.CHOP_WILLOW);
				}
				if (Boundary.isIn(attachment, Boundary.DRAYNOR_BOUNDARY)) {
					attachment.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.CHOP_WILLOW_DRAY);
				}
				break;
			case YEW:
				if (Boundary.isIn(attachment, Boundary.FALADOR_BOUNDARY)) {
					attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.CHOP_YEW);
				}
				if (Boundary.isIn(attachment, Boundary.VARROCK_BOUNDARY)) {
					attachment.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.YEWS_AND_BURN);
				}
				DailyTasks.increase(attachment, PossibleTasks.YEW_LOGS);
				break;
			case TEAK:
				if (Boundary.isIn(attachment, Boundary.DESERT_BOUNDARY)) {
					attachment.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.CHOP_TEAK);
				}
				break;
			default:
				break;

		}
	}

	private void handleRewards() {
		if (Misc.random(250) == 10) {
				attachment.getItems().addItemUnderAnyCircumstance(19712, 1);
				attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
			}
		
		if (Misc.random(500) == 10) {
			attachment.getItems().addItemUnderAnyCircumstance(19714, 1);
			attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
		}
		
		if (Misc.random(750) == 10) {
			attachment.getItems().addItemUnderAnyCircumstance(19716, 1);
			attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
		}
	
		if (Misc.random(12000) == 5555) {
			attachment.getItems().addItemUnderAnyCircumstance(lumberjackOutfit[Misc.random(lumberjackOutfit.length - 1)], 1);
			attachment.sendMessage("You notice a lumberjack piece falling from the tree and pick it up.");
		}
		if (Misc.random(tree.getPetChance()) / 2 == 10) {
			attachment.getItems().addItemUnderAnyCircumstance(19716, 1);
			attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
		}
		if (Misc.random(tree.getPetChance()) == 2 && SkillPets.WOODCUTTING.hasPet(attachment)) {
			PetHandler.skillPet(attachment, SkillPets.WOODCUTTING);
		}
	}

	@Override
	public void stop() {
		super.stop();
		if (attachment != null) {
			attachment.startAnimation(65535);
		}
	}
	
	public void randomBloodMoney(Player player) {
		int bloodMoneyChance = Misc.random(200);
		int bloodMoneyAmt = 10 + player.getSkills().getActualLevel(Skill.WOODCUTTING) + Misc.random(50, 100);
		
		if (bloodMoneyChance == 1) {
			player.getItems().addItemUnderAnyCircumstance(13307, bloodMoneyAmt);
			player.sendMessage("@pur@You pick up " + bloodMoneyAmt + " Blood money.");
		}
	}

}
