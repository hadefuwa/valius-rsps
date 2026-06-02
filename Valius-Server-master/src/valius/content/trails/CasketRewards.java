package valius.content.trails;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import valius.model.items.Item;
import valius.model.items.ItemRarity;
import valius.util.Misc;

public class CasketRewards {

	private static Map<RewardLevel, Map<ItemRarity, List<RewardItem>>> rewards;
	private static FileReader fileReader;

	public static Map<RewardLevel, Map<ItemRarity, List<RewardItem>>> getRewards() {
		return rewards;
	}

	public static List<RewardItem> getRandomRewards(RewardLevel difficulty) {
		List<RewardItem> result =new ArrayList<>();

		int rolls = 1 + Misc.random(2);

		if (difficulty == RewardLevel.MEDIUM) {
			rolls += 1;
		} else if (difficulty == RewardLevel.HARD) {
			rolls += 2;
		}

		double random = Math.random();
		while (random > 0.05) {
			random = Math.random();
		}
		result.add(getReward(random, difficulty));

		for (int i = 0; i < rolls; i++) {
			RewardItem reward = getReward(Math.random(), difficulty);
			boolean flag = false;
			for (RewardItem element : result) {
				if (element.getId() == reward.getId()) {
					element.setMinAmount(element.getMinAmount() + reward.getMinAmount());
					element.setMaxAmount(element.getMaxAmount() + reward.getMaxAmount());
					flag = true;
					break;
				}
			}
			if (!flag) {
				result.add(reward);
			}
		}
		Collections.shuffle(result);
		return result;
	}

	private static RewardItem getReward(double roll, RewardLevel level) {
		List<RewardItem> rewardList;
		if (roll <= 0.001 && containsReward(level, ItemRarity.VERY_RARE)) {
			rewardList = rewards.get(level).get(ItemRarity.VERY_RARE);
		} else if (roll <= 0.009 && containsReward(level, ItemRarity.RARE)) {
			rewardList = rewards.get(level).get(ItemRarity.RARE);
		} else if (roll <= 0.07 && containsReward(level, ItemRarity.UNCOMMON)) {
			rewardList = rewards.get(level).get(ItemRarity.UNCOMMON);
		} else {
			rewardList = rewards.get(level).get(ItemRarity.COMMON);
		}
		if (rewardList.size() == 1) {
			return rewardList.get(0);
		} else {
			return rewardList.get(Misc.random(rewardList.size() - 2));
		}
	}

	private static boolean containsReward(RewardLevel level, ItemRarity rarity) {
		List<RewardItem> rewardList = rewards.get(level).get(rarity);
		return rewardList != null && rewardList.size() > 0;
	}

	public static void read() {
		rewards =new HashMap<>();
		JSONParser parser = new JSONParser();
		try {
			fileReader = new FileReader("./Data/json/clue_rewards.json");
			JSONArray data = (JSONArray) parser.parse(fileReader);
			for (Object aData : data) {
				JSONObject item=(JSONObject) aData;
				int id=((Long) item.get("item_id")).intValue();
				int minAmount=((Long) item.get("min_amount")).intValue();
				int maxAmount=((Long) item.get("max_amount")).intValue();
				RewardItem reward=new RewardItem(id, minAmount, maxAmount);
				ItemRarity rarity=ItemRarity.valueOf(((String) item.get("rarity")).toUpperCase());
				RewardLevel level=RewardLevel.valueOf(((String) item.get("reward_level")).toUpperCase());
				
				if (level ==RewardLevel.MASTER) {
					add(reward, level, rarity);
				} else if (level==RewardLevel.SHARED) {
					add(reward, RewardLevel.EASY, rarity);
					add(reward, RewardLevel.MEDIUM, rarity);
					add(reward, RewardLevel.HARD, rarity);
					add(reward, RewardLevel.ELITE, rarity);
					add(reward, RewardLevel.MASTER, rarity);
				} else {
					add(reward, level, rarity);
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private static void add(RewardItem item, RewardLevel level, ItemRarity rarity) {
		if (rewards.containsKey(level)) {
			if (rewards.get(level).containsKey(rarity)) {
				rewards.get(level).get(rarity).add(item);
			} else {
				List<RewardItem> list =new ArrayList<>();
				list.add(item);
				rewards.get(level).put(rarity, list);
			}
		} else {
			List<RewardItem> list =new ArrayList<>();
			list.add(item);
			Map<ItemRarity, List<RewardItem>> map =new HashMap<>();
			map.put(rarity, list);
			rewards.put(level, map);
		}
	}
}
