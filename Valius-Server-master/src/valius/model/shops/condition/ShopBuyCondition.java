package valius.model.shops.condition;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

public enum ShopBuyCondition {

	RFD_GLOVES((player, itemId) -> {
			int rfdStage = itemId - 7457;
			if(itemId >= 7461) {
				rfdStage += 1;
			}
			if(player.rfdGloves > rfdStage) {
				
				return true;
			} else {
				
				return false;
			}
		}, 7458, 7459, 7460, 7461, 7462),
	
	SKILL_CAPE((player, itemId) -> {
		int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 9948 };

		int skillId = IntStream.range(0, skillCapes.length).filter(index -> skillCapes[index] == itemId).findFirst().orElse(-1);
		if(skillId >= 0) {
			Skill skill = Skill.forId(skillId);
			if(player.getSkills().getActualLevel(skill) >= 99) {
				return true;
			} else {
				player.sendMessage("You need 99 " + skill.toString() + " to buy this!");
				return false;
			}
		}
	
		return false;
	}, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 9948),
	
	TRIMMED_SKILL_CAPE((player, itemId) -> {
        int[] skillCapes = { 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9949 };
 
        int skillId = IntStream.range(0, skillCapes.length).filter(index -> skillCapes[index] == itemId).findFirst().orElse(-1);
        if(skillId >= 0) {
            Skill skill = Skill.forId(skillId);
            if(player.getSkills().getActualLevel(skill) >= 99) {
                return true;
            } else {
                player.sendMessage("You need 99 " + skill.toString() + " to buy this!");
                return false;
            }
        }
   
        return false;
    }, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9949),

	
	MAX_CAPE((player, itemId) -> {
		int[] skillCapes = { 33034, 33038, 33053, 33044, 33049, 33048, 33046, 33036, 33055, 33042, 33041, 33040, 33037, 33052, 33047, 33043, 33033, 33054, 33051, 33039, 33050, 33045, 33035, };

		int skillId = IntStream.range(0, skillCapes.length).filter(index -> skillCapes[index] == itemId).findFirst().orElse(-1);
		if(skillId >= 0) {
			Skill skill = Skill.forId(skillId);
			if(player.getSkills().getExperience(skill) >= 20_000_0000) {
				return true;
			} else {
				player.sendMessage("You need 200M XP in " + skill.toString() + " to buy this!");
				return false;
			}
		}
	
		return false;
	}, 33034, 33038, 33053, 33044, 33049, 33048, 33046, 33036, 33055, 33042, 33041, 33040, 33037, 33052, 33047, 33043, 33033, 33054, 33051, 33039, 33050, 33045, 33035),
	

	RUNE_POUCH((player, itemId) -> {
		if (player.getItems().getItemCount(12791, true) > 0) {
			player.sendMessage("It seems like you already have one of these.");
			return false;
		}
		return true;
		}, 12791),
	
	LOOTING_BAG((player, itemId) -> {
		if (player.getItems().getItemCount(11941, true) > 0) {
			player.sendMessage("It seems like you already have one of these.");
			return false;
		}
		return true;
		}, 11941),
	HERB_SACK((player, itemId) -> {
		if (player.getItems().getItemCount(13226, true) > 0) {
			player.sendMessage("You already have a herb sack, theres no need for another.");
			return false;
		}
		return true;
		}, 13226),
	GEM_BAG((player, itemId) -> {
		if (player.getItems().getItemCount(12020, true) > 0) {
			player.sendMessage("You already have a gem bag. theres no need for another.");
			return false;
		}
		return true;
		}, 12020),
	
	AVAS((player, itemId) -> {
		player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.ATTRACTOR);
		return true;
		}, 10498),
	
	
	
	;

	private ShopBuyCondition(BiFunction<Player, Integer, Boolean> buyPredicate, int... validItems) {
		this.buyPredicate = buyPredicate;
		this.itemIds = validItems;
	}
	
	private BiFunction<Player, Integer, Boolean> buyPredicate;

	private int[] itemIds;
	

	private boolean hasItem(int itemId) {
		return itemIds != null && IntStream.of(itemIds).anyMatch(id -> id == itemId);
	}
	
	public static ShopBuyCondition getFromItem(int itemId) {
		return Stream.of(ShopBuyCondition.values()).filter(condition -> condition.buyPredicate != null && condition.hasItem(itemId)).findFirst().orElse(null);
	}

	

	public static boolean canBuy(Player player, int itemId) {
		ShopBuyCondition valid = getFromItem(itemId);
		if(valid != null) {
			return valid.buyPredicate.apply(player, itemId);
		}
		return true;
	}
	
	
	
	
}
