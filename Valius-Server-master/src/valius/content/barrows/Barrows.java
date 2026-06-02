package valius.content.barrows;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import valius.Config;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.morytania.MorytaniaDiaryEntry;
import valius.content.barrows.brothers.Ahrim;
import valius.content.barrows.brothers.Brother;
import valius.content.barrows.brothers.Dharok;
import valius.content.barrows.brothers.Guthan;
import valius.content.barrows.brothers.Karil;
import valius.content.barrows.brothers.Torag;
import valius.content.barrows.brothers.Verac;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerAssistant;
import valius.model.entity.player.skills.Skill;
import valius.model.items.EquipmentSet;
import valius.util.Misc;
import valius.world.World;

public class Barrows {

	public static final Boundary GRAVEYARD = new Boundary(3537, 3268, 3583, 3397, 0);
	private static final Boundary TOMBS = new Boundary(3533, 9677, 3580, 9721, 3);
	public static final Boundary TUNNEL = new Boundary(3523, 9666, 3579, 9723, 0);
	private static final Boundary[] AREA = { GRAVEYARD, TOMBS, TUNNEL };

	public static final double SPECIAL_CHANCE = 0.2;

	private Player player;

	private ArrayList<Brother> brothers;

	private int monstersKilled;

	private boolean completed;

	public Barrows(Player player) {
		this.player = player;
		initialize();
		reset();
	}

	public void initialize() {
		completed = false;
	}

	public void reset() {
		brothers =new ArrayList<>();
		brothers.add(new Ahrim(player));
		brothers.add(new Dharok(player));
		brothers.add(new Guthan(player));
		brothers.add(new Karil(player));
		brothers.add(new Torag(player));
		brothers.add(new Verac(player));
		brothers.get(new Random().nextInt(brothers.size())).setFinalBrother(true);
		monstersKilled = 0;
	}

	/**
	 * Draws the interface that shows which brothers the player has killed whilst in the barrow minigame.
	 */
	public void drawInterface() {
		PlayerAssistant assistant = player.getPA();
		for (Brother brother : brothers) {
			String text = brother.isDefeated() ? "<str=0>" + brother.getName() + "</str>" : brother.getName();
			String color = brother.isDefeated() ? "CC0000" : "FD851A";
			assistant.sendFrame126("<col=" + color + ">" + text + "</col>", brother.getFrameId());
		}
		assistant.sendFrame126(Integer.toString(getKillCount()), 27509);
	}

	/**
	 * Determines whether the player is within the Barrows area.
	 * 
	 * @return True if the player is in the Barrows area, false otherwise.
	 */
	public boolean inBarrows() {
		return Boundary.isIn(player, AREA);
	}

	/**
	 * Return an ArrayList of all brothers.
	 * 
	 * @return ArrayList of all brothers.
	 */
	public ArrayList<Brother> getBrothers() {
		return brothers;
	}

	/**
	 * Get the instance of a Barrows brother by their id.
	 * 
	 * @param npcId The NPC id of the Barrows brother.
	 * @return The instance of the brother.
	 */
	public Optional<Brother> getBrother(int npcId) {
		return brothers.stream().filter(brother -> brother.getId() == npcId).findFirst();
	}

	/**
	 * Get the instance of a Barrows brother by their name.
	 * 
	 * @param name The name of the Barrows brother.
	 * @return The instance of the brother.
	 */
	public Optional<Brother> getBrother(String name) {
		return brothers.stream().filter(brother -> brother.getName().toLowerCase().equals(name.toLowerCase())).findFirst();
	}

	/**
	 * Test whether a given id matches that of a brother.
	 * 
	 * @param npcId The id which we want to test.
	 * @return True in case the id matches a brother, false otherwise.
	 */
	public boolean isBrother(int npcId) {
		return brothers.stream().anyMatch(brother -> brother.getId() == npcId);
	}

	/**
	 * Get the Barrows brother which will appear in the hidden tunnel.
	 * 
	 * @return The instance of the Barrows brother.
	 */
	public Optional<Brother> getLastBrother() {
		return brothers.stream().filter(Brother::isFinal).findFirst();
	}

	/**
	 * Set a given brother to final, and all others to non-final.
	 * 
	 * @param name The name of the brother which is to be set to final.
	 */
	public void setLastBrother(String name) {
		brothers.forEach(brother -> {
			if (brother.getName().toLowerCase().equals(name.toLowerCase())) {
				brother.setFinalBrother(true);
			} else if (brother.isFinal()) {
				brother.setFinalBrother(false);
			}
		});
	}

	/**
	 * Handle the spawning of a brother by their coffin id.
	 * 
	 * @param coffinId The id of the coffin.
	 */
	public void spawnBrother(int coffinId) {
		Optional<Brother> brother = brothers.stream().filter(b -> b.getCoffinId() == coffinId).findFirst();
		brother.ifPresent(Brother::handleSpawn);
	}

	/**
	 * Try to dig down the mound.
	 */
	public void digDown() {
		brothers.stream().anyMatch(Brother::digDown);
	}

	/**
	 * Move up the stairs of a sarcophagus.
	 * 
	 * @param stairsId The object id of the stairs.
	 */
	public void moveUpStairs(int stairsId) {
		Optional<Brother> brother = brothers.stream().filter(b -> b.getStairsId() == stairsId).findFirst();
		brother.ifPresent(Brother::moveUpStairs);
	}

	/**
	 * Returns the instance of a currently spawned brother.
	 * 
	 * @return The instance of the brother.
	 */
	public Optional<Brother> getActive() {
		return brothers.stream().filter(brother -> !brother.isDefeated() && brother.isActive()).findFirst();
	}

	/**
	 * Count how many brothers have been killed since starting the current Barrows instance.
	 * 
	 * @return The total kill count.
	 */
	private int countDeadBrothers() {
		return brothers.stream().filter(Brother::isDefeated).collect(Collectors.toList()).size();
	}

	/**
	 * Increase the amount of tunnel monsters killed by 1.
	 */
	public void increaseMonsterKilled() {
		monstersKilled++;
	}

	/**
	 * Set how many monsters have been killed in the tunnel.
	 * 
	 * @param amount The amount of monsters that have been killed in the tunnel.
	 */
	public void setMonstersKilled(int amount) {
		monstersKilled = amount;
	}

	/**
	 * Returns the amount of monsters killed in the hidden tunnel.
	 * 
	 * @return The amount of monsters killed in the hidden tunnel.
	 */
	public int getMonsterKillCount() {
		return monstersKilled;
	}

	/**
	 * Return the total killcount of the current Barrows instance.
	 * 
	 * @return The total killcount.
	 */
	public int getKillCount() {
		return monstersKilled + countDeadBrothers();
	}

	/**
	 * Return whether the Barrows minigame is completed or not.
	 * 
	 * @return True in case the event is completed, false otherwise.
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * Set whether the minigame has been completed or not.
	 * 
	 * @param completed True in case the minigame is completed, false otherwise.
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * Drains prayer from the Player, depending on how many brothers are killed.
	 */
	void drainPrayer() {
		int newPrayer = player.getSkills().getLevel(Skill.PRAYER) - (8 + countDeadBrothers());
		player.getSkills().setLevelOrMin(newPrayer, 0, Skill.PRAYER);
		player.getPA().refreshSkill(Config.PRAYER);
	}
	
	public void test() {
		Optional<Brother> Ahrim = player.getBarrows().getBrother(1672);
		Optional<Brother> Dharok = player.getBarrows().getBrother(1673);
		Optional<Brother> Guthan  = player.getBarrows().getBrother(1674);
		Optional<Brother> Karil = player.getBarrows().getBrother(1675);
		Optional<Brother> Torag = player.getBarrows().getBrother(1676);
		Optional<Brother> Verac = player.getBarrows().getBrother(1677);
		completed = true;
		setCompleted(true);
		RewardList rewardList = new RewardList();
		setMonstersKilled(25);
		if (Ahrim.isPresent()&&Dharok.isPresent()&&Guthan.isPresent()&&Karil.isPresent()&&Torag.isPresent()&&Verac.isPresent()){
			Ahrim.get().handleDeath();
			Dharok.get().handleDeath();
			Guthan.get().handleDeath();
			Karil.get().handleDeath();
			Torag.get().handleDeath();
			Verac.get().handleDeath();
		}
		for (int i = 0; i < 12; i++) {
			player.getPA().itemOnInterface(-1, -1, 64503, i);
		}
		brothers.stream().filter(Brother::isDefeated).forEach(br -> rewardList.addAll(br.getRewards()));
		giveRewards(rewardList);
		reset();
	}

	/**
	 * Handle clicking the chest in the hidden tunnel.
	 */
	public void useChest() {
		getLastBrother().ifPresent(brother -> {
			if (!completed) {
				if (!brother.isDefeated()) {
					brother.spawnBrother();
				} else {
					completed = true;
					RewardList rewardList = new RewardList();
					brothers.stream().filter(Brother::isDefeated).forEach(br -> rewardList.addAll(br.getRewards()));
					player.setBarrowsChestCounter(player.getBarrowsChestCounter() + 1);
					player.sendMessage("@blu@You have completed "+ player.getBarrowsChestCounter() + " barrows rounds.");
					for (int i = 0; i < 12; i++) {
						player.getPA().itemOnInterface(-1, -1, 64503, i);
					}
					giveRewards(rewardList);
					Achievements.increase(player, AchievementType.BARROWS_RUNS, 1);
					player.sendMessage("The tunnel starts to collapse as you search the chest.");
					if (EquipmentSet.AHRIM.isWearingBarrows(player) || EquipmentSet.KARIL.isWearingBarrows(player)
							|| EquipmentSet.DHAROK.isWearingBarrows(player)
							|| EquipmentSet.VERAC.isWearingBarrows(player)
							|| EquipmentSet.GUTHAN.isWearingBarrows(player)
							|| EquipmentSet.TORAG.isWearingBarrows(player)) {
							player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.BARROWS_CHEST);
					}
					reset();
				}
			} else {
				player.sendMessage("The chest has already been emptied.");
			}
		});
	}

	/**
	 * Give the player a certain amount of rewards from the minigame.
	 * 
	 * @param rewardList The list of rewards which to choose from.
	 */
	private void giveRewards(RewardList rewardList) {
		ArrayList<RewardItem> randomRewards = getWeightedRewards(rewardList, 6);
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unused")
		int gotBarrows = 0;
		int spot = 0;
		player.getPA().showInterface(64500);
		for (RewardItem item : randomRewards) {
			int itemId = item.getId();
			int amount = item.getAmount();
			boolean morytaniaLegs = player.getRechargeItems().hasAnyItem(13114, 13115);
			if (item.getRarityLevel() == RewardLevel.COMMON) {
				if (morytaniaLegs) {
					amount = amount + amount / 2;
				}
			}
			//spot++;
			if (!player.getItems().addItem(itemId, amount)) {
				World.getWorld().getItemHandler().createGroundItem(player, itemId, player.getX(), player.getY(), player.getHeight(), amount);
			}
			player.getPA().itemOnInterface(itemId, amount, 64503, spot);
			spot++;
			if (item.getRarityLevel() == RewardLevel.RARE) {
				player.getDiaryManager().getMorytaniaDiary().progress(MorytaniaDiaryEntry.BARROWS_PIECE);
				gotBarrows = 1;
			}
			if (Misc.random(1, 200) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33060, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Barrows sword from Barrows!", GlobalMessages.MessageType.LOOT);
			}
			
			if (Misc.random(1, 30) == 5) {
				player.sendMessage("test");
				int cursedRandom = Misc.random(1, 6);
			switch(cursedRandom) {
			
			case 1:
				World.getWorld().getItemHandler().createGroundItem(player, 33658, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Ahrims box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
			case 2:
				World.getWorld().getItemHandler().createGroundItem(player, 33659, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Dharoks box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
			case 3:
				World.getWorld().getItemHandler().createGroundItem(player, 33660, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Guthans box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
			case 4:
				World.getWorld().getItemHandler().createGroundItem(player, 33661, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Karils box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
			case 5:
				World.getWorld().getItemHandler().createGroundItem(player, 33662, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Torags box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
			case 6:
				World.getWorld().getItemHandler().createGroundItem(player, 33663, player.getX(), player.getY(), player.getHeight(), 1);
				GlobalMessages.send("" + player.playerName + " has received a Cursed Veracs box from Barrows!", GlobalMessages.MessageType.LOOT);
			break;
		}
	}
			sb.append(item.getId()).append(", ");
		}
	}
	
	/**
	 * Select a certain amount of items from the given list, using weighted selection.
	 * 
	 * @param rewardList The list of items which to choose from.
	 * @param amount The amount of items which is to be chosen.
	 * @return A list containing all randomly chosen items.
	 */
	private ArrayList<RewardItem> getWeightedRewards(RewardList rewardList, int amount) {
		ArrayList<RewardItem> randomRewards =new ArrayList<>();
		int totalWeight = rewardList.getTotalWeight(getKillCount());

		for (int i = 0; i < amount; i++) {
			double random = Math.random() * totalWeight;
			int index = 0;
			for (int n = 0; n < rewardList.size(); n++) {
				switch (rewardList.get(n).getRarityLevel()) {
				case COMMON:
					random -= rewardList.firstTierRarity(getKillCount());
					break;
				default:
					random -= rewardList.get(n).getRarityLevel().getRarity();
					break;
				}
				if (random <= 0.0d) {
					index = n;
					break;
				}
			}
			RewardItem reward = rewardList.get(index);
			boolean flag = false;
			for (RewardItem item : randomRewards) {
				if (reward.getId() == item.getId()) {
					item.setMinAmount(reward.getMinAmount() + item.getMinAmount());
					item.setMaxAmount(reward.getMaxAmount() + item.getMaxAmount());
					flag = true;
					break;
				}
			}
			if (!flag) {
				randomRewards.add(reward);
			}
		}
		return randomRewards;
	}
	
	
}
