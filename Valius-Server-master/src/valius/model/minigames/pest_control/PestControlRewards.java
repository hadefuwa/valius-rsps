package valius.model.minigames.pest_control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import valius.content.achievement_diary.western_provinces.WesternDiaryEntry;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.items.ItemDefinition;
import valius.util.Misc;

public class PestControlRewards {
	/**
	 * The player that will be managing their pest control rewards
	 */
	private Player player;

	/**
	 * The current reward the player has
	 */
	private Reward reward;

	/**
	 * The level required to purchase experience
	 */
	static final int REQUIRED_LEVEL = 40;

	/**
	 * Creats a new class for the signified player. A new class is created for each player because there are members of the class that are required for each individual player.
	 * 
	 * @param player the player this class is being created for.
	 */
	public PestControlRewards(Player player) {
		this.player = player;
	}

	/**
	 * Displays the reward interface
	 */
	public void showInterface() {
		player.getPA().sendFrame70(0, -100, 37100);
		player.getPA().sendString("Unselected", 37003);
		player.getPA().sendFrame126(Misc.insertCommas(Integer.toString(player.pcPoints)) + " Pts", 37007);
		player.getPA().showInterface(37000);
	}

	public boolean click(int buttonId) {
		if (buttonId == 37002) {
			if (reward == null) {
				player.sendMessage("You must select an option first before confirming.");
			} else {
				reward.purchase(player);
				player.getPA().sendFrame126(Misc.insertCommas(Integer.toString(player.pcPoints)) + " Pts", 37007);
			}
			return true;
		}
		for (RewardButton button : RewardButton.values()) {
			if (button.buttonId == buttonId) {
				if (!player.getMode().isRewardSelectable(button)) {
					player.sendMessage("Your mode is not able to select this reward.");
					return false;
				}
				reward = button.reward;
				player.getPA().sendFrame70(button.xOffset, button.yOffset, 37100);
				player.getPA().sendString(reward.getCost() + " " + (reward.getCost() == 1 ? "point" : "points"), 37003);
				return true;

			}
		}
		return false;
	}

	public enum RewardButton {
		ATTACK_EXPERIENCE_1(37014, 0, 0, new ExperienceReward(1, Skill.ATTACK.getId(), 100)), 
		ATTACK_EXPERIENCE_10(37015, 0, 0, new ExperienceReward(10, Skill.ATTACK.getId(), 100)), 
		ATTACK_EXPERIENCE_100(37016, 0, 0, new ExperienceReward(100, Skill.ATTACK.getId(), 100)), 
		DEFENCE_EXPERIENCE_1(37019, 0, 40, new ExperienceReward(1, Skill.DEFENCE.getId(), 100)), 
		DEFENCE_EXPERIENCE_10(37020, 0, 40, new ExperienceReward(10, Skill.DEFENCE.getId(), 100)), 
		DEFENCE_EXPERIENCE_100(37021, 0, 40, new ExperienceReward(100, Skill.DEFENCE.getId(), 100)), 
		MAGIC_EXPERIENCE_1(37024, 0, 80, new ExperienceReward(1, Skill.MAGIC.getId(), 100)), 
		MAGIC_EXPERIENCE_10(37025, 0, 80, new ExperienceReward(10, Skill.MAGIC.getId(), 100)), 
		MAGIC_EXPERIENCE_100(37026, 0, 80, new ExperienceReward(100, Skill.MAGIC.getId(), 100)), 
		PRAYER_EXPERIENCE_1(37029, 0, 120, new ExperienceReward(1, Skill.PRAYER.getId(), 100)), 
		PRAYER_EXPERIENCE_10(37030, 0, 120, new ExperienceReward(10, Skill.PRAYER.getId(), 100)), 
		PRAYER_EXPERIENCE_100(37031, 0, 120, new ExperienceReward(100, Skill.PRAYER.getId(), 100)), 
		STRENGTH_EXPERIENCE_1(37034, 210, 0, new ExperienceReward(1, Skill.STRENGTH.getId(), 100)), 
		STRENGTH_EXPERIENCE_10(37035, 210, 0, new ExperienceReward(10, Skill.STRENGTH.getId(), 100)), 
		STRENGTH_EXPERIENCE_100(37036, 210, 0, new ExperienceReward(100, Skill.STRENGTH.getId(), 100)), 
		RANGE_EXPERIENCE_1(37039, 210, 40, new ExperienceReward(1, Skill.RANGED.getId(), 100)), 
		RANGE_EXPERIENCE_10(37040, 210, 40, new ExperienceReward(10, Skill.RANGED.getId(), 100)), 
		RANGE_EXPERIENCE_100(37041, 210, 40, new ExperienceReward(100, Skill.RANGED.getId(), 100)), 
		HITPOINTS_EXPERIENCE_1(37044, 210, 80, new ExperienceReward(1, Skill.HITPOINTS.getId(), 100)), 
		HITPOINTS_EXPERIENCE_10(37045, 210, 80, new ExperienceReward(10, Skill.HITPOINTS.getId(), 100)), 
		HITPOINTS_EXPERIENCE_100(37046, 210, 80, new ExperienceReward(100, Skill.HITPOINTS.getId(), 100)), 
		HERB_PACK(37053, 0, 180, new PackReward(30, PackReward.HERB_PACK)), 
		SEED_PACK(37056, 0, 220, new PackReward(15, PackReward.SEED_PACK)),
		MINERAL_PACK(37059, 210, 180, new PackReward(15, PackReward.MINERAL_PACK)), 
		VOID_MACE(37062, 0, 280, new ItemReward(160, new Item(8841))), 
		VOID_KNIGHT_ROBE(37065, 0, 320, new ItemReward(175, new Item(8840))), 
		VOID_MAGE_HELM(37068, 0, 360, new ItemReward(150, new Item(11663))), 
		VOID_MELEE_HELM(37071, 0, 400, new ItemReward(150, new Item(11665))), 
		VOID_KNIGHT_TOP(37074, 210, 280, new ItemReward(175, new Item(8839))), 
		VOID_KNIGHT_GLOVES(37077, 210, 320, new ItemReward(110, new Item(8842))), 
		VOID_RANGE_HELM(37080, 210, 360, new ItemReward(150, new Item(11664))), 
		FIGHTER_TORSO(37083, 210, 400, new ItemReward(300, new Item(10551))), 
		BARROWS_GLOVES(37086, 0, 440, new ItemReward(80, new Item(7462))), 
		FIGHTER_HAT(37089, 210, 440, new ItemReward(60, new Item(10548)))
		;

		/**
		 * The button for this reward
		 */
		private final int buttonId;

		/**
		 * The x and y offset of the button
		 */
		private final int xOffset, yOffset;

		/**
		 * The reward
		 */
		private final Reward reward;

		/**
		 * The button with an id and reward associated with it
		 * 
		 * @param buttonId the button id
		 * @param reward the reward
		 */
		private RewardButton(int buttonId, int xOffset, int yOffset, Reward reward) {
			this.buttonId = buttonId;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.reward = reward;
		}

		public Reward getReward() {
			return reward;
		}

	}

	static abstract class Reward {

		/**
		 * The cost of the reward
		 */
		protected final int cost;

		/**
		 * Creates a new reward with an initial cost
		 * 
		 * @param cost the cost of the reward
		 */
		Reward(int cost) {
			this.cost = cost;
		}

		/**
		 * The procedure for purchasing an item
		 */
		abstract void purchase(Player player);

		/**
		 * The cost of the purchase
		 * 
		 * @return the cost in points
		 */
		public int getCost() {
			return cost;
		}
	}

	static class ExperienceReward extends Reward {
		/**
		 * The skill id that will be given the experience
		 */
		private int skillId;

		/**
		 * The default experience for the reward
		 */
		private int experience;

		/**
		 * Creates a new reward with just a cost
		 * 
		 * @param cost the cost of the reward
		 */
		ExperienceReward(int cost) {
			super(cost);
		}

		/**
		 * Creates a new reward with a cost and skillId
		 * 
		 * @param cost the cost of the reward
		 * @param skillId the skill obtaining the experience
		 */
		ExperienceReward(int cost, int skillId, int experience) {
			super(cost);
			this.skillId = skillId;
			this.experience = experience;
		}

		@Override
		public void purchase(Player player) {
			if (System.currentTimeMillis() - player.buyPestControlTimer < 5500) {
				return;
			}
			if (player.pcPoints < cost) {
				player.sendMessage("You do not have the pest control points to purchase this experience.");
				return;
			}
			if (player.getSkills().getActualLevel(Skill.forId(skillId)) < REQUIRED_LEVEL) {
				player.sendMessage("You need a level of " + REQUIRED_LEVEL + " to purchase this experience.");
				return;
			}
			player.buyPestControlTimer = System.currentTimeMillis();
			player.pcPoints -= cost;
			player.refreshQuestTab(3);
			player.getPA().addSkillXP(experience, skillId, true);
			player.sendMessage("You have received some experience in exchange for " + cost + " points.");
		}
	}

	static class ItemReward extends Reward {

		/**
		 * The item received as the reward
		 */
		private Item item;

		/**
		 * Creates a new item reward with an initial cost
		 * 
		 * @param cost the cost of the item reward
		 */
		ItemReward(int cost) {
			super(cost);
		}

		/**
		 * Creates a new item reward with an initial cost as well as the item given
		 * 
		 * @param cost the cost of the reward
		 * @param item the item given to the player
		 */
		ItemReward(int cost, Item item) {
			super(cost);
			this.item = item;
		}

		@Override
		void purchase(Player player) {
			if (System.currentTimeMillis() - player.buyPestControlTimer < 5500) {
				return;
			}
			if (player.pcPoints < cost) {
				player.sendMessage("You do not have the pest control points to purchase this experience.");
				return;
			}
			if (player.getItems().freeSlots() == 0) {
				player.sendMessage("You need at least one free slot to purchase this item reward.");
				return;
			}
			player.pcPoints -= cost;
			player.refreshQuestTab(3);
			player.buyPestControlTimer = System.currentTimeMillis();
			switch (item.getId()) {
			case 10551:
				player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.FIGHTER_TORSO);
				break;

			case 10548:
				player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.FIGHTER_HAT);
				break;
			}
			player.getItems().addItem(item.getId(), item.getAmount());
			ItemDefinition itemDef = ItemDefinition.forId(item.getId());
			String name = itemDef == null ? "a item" : itemDef.getName();
			player.sendMessage("You have received a " + name + " in exchange for " + cost + " pc points.");
		}

	}

	static class PackReward extends Reward {

		/**
		 * An array of items that are obtainable from the herb pack
		 */
		static final Item[] HERB_PACK = { new Item(200, 30), new Item(202, 30), new Item(204, 30), new Item(206, 24), new Item(208, 15),
				new Item(210, 21), new Item(212, 18), new Item(214, 15), new Item(216, 15), new Item(218, 15), new Item(220, 10) };

		/**
		 * An array of items that are obtainable from the mineral pack
		 */
		static final Item[] MINERAL_PACK = { new Item(437, 50), new Item(439, 50), new Item(441, 40), new Item(445, 35), new Item(454, 30),
				new Item(448, 25), new Item(450, 10), new Item(452, 5) };

		static final Item[] SEED_PACK = { new Item(5291, 15), new Item(5292, 15), new Item(5293, 15), new Item(5294, 15), new Item(5295, 5),
				new Item(5296, 5), new Item(5297, 10), new Item(5298, 10), new Item(5299, 7), new Item(5300, 5), new Item(5301, 6), new Item(5302, 4),
				new Item(5303, 4), new Item(5304, 3), };

		/**
		 * The pack of items
		 */
		private Item[] pack;

		/**
		 * Creates a new reward with a determined cost
		 * 
		 * @param cost the cost of the reward
		 */
		PackReward(int cost) {
			super(cost);
		}

		/**
		 * Creates a new reward with an initial cost and pack of items
		 * 
		 * @param cost the cost of the reward
		 * @param pack the pack fo items received from the reward
		 */
		PackReward(int cost, Item[] pack) {
			super(cost);
			this.pack = pack;
		}

		@Override
		void purchase(Player player) {
			if (System.currentTimeMillis() - player.buyPestControlTimer < 5000) {
				return;
			}
			if (player.pcPoints < cost) {
				player.sendMessage("You do not have the pest control points to purchase this experience.");
				return;
			}
			if (player.getItems().freeSlots() < 5) {
				player.sendMessage("You need at least 5 free slots to purchase this pack.");
				return;
			}
			if (player.getMode().isIronman() || player.getMode().isUltimateIronman() || player.getMode().isHcIronman() || player.getMode().isGroupIronman()) {
				player.sendMessage("Iron man are currently unable to purchase rewards from pest control.");
				return;
			}
			player.pcPoints -= cost;
			player.refreshQuestTab(3);
			player.buyPestControlTimer = System.currentTimeMillis();
			int amount = 4 + Misc.random(1);
			List<Item> list = new ArrayList<>(Arrays.asList(pack));
			List<Item> receive = new ArrayList<>(amount);
			while (amount-- > 0) {
				Item item = list.get(Misc.random(list.size() - 1));
				item.setAmount(1 + Misc.random(item.getAmount()));
				receive.add(item);
				list.remove(item);
			}
			receive.forEach(item -> player.getItems().addItem(item.getId(), item.getAmount()));
		}
	}

}
