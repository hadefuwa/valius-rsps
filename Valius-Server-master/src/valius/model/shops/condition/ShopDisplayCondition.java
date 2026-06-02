package valius.model.shops.condition;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import valius.model.entity.player.Player;

public enum ShopDisplayCondition {

	RFD_GLOVES((player, itemId) -> {
			int rfdStage = itemId - 7457;
			if(itemId >= 7461) {
				rfdStage += 1;
			}
			return player.rfdGloves > rfdStage;
		}, 7458, 7459, 7460, 7461, 7462)
	
	;

	private ShopDisplayCondition(BiFunction<Player, Integer, Boolean> displayPredicate, int... validItems) {
		this.displayPredicate = displayPredicate;
		this.itemIds = validItems;
	}
	
	private BiFunction<Player, Integer, Boolean> displayPredicate;

	private int[] itemIds;
	

	private boolean hasItem(int itemId) {
		return itemIds != null && IntStream.of(itemIds).anyMatch(id -> id == itemId);
	}
	
	public static ShopDisplayCondition getFromItem(int itemId) {
		return Stream.of(ShopDisplayCondition.values()).filter(condition -> condition.displayPredicate != null && condition.hasItem(itemId)).findFirst().orElse(null);
		
	}
	
	public static boolean shouldDisplay(Player player, int itemId) {
		ShopDisplayCondition valid = getFromItem(itemId);
		if(valid != null) {
			return valid.displayPredicate.apply(player, itemId);
		}
		return true;
	}
	
	
	
}
