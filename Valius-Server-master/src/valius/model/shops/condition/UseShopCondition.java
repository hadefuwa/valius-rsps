package valius.model.shops.condition;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.shops.ShopTab;

public enum UseShopCondition {

	DONATOR_SHOP((player, shopId) -> {

		if(!player.getRights().isOrInherits(Right.SAPPHIRE)) {
			player.sendMessage("You need to be a donator to buy that!");
			return false;
		}
		return true;
		}, 112),
	WILDERNESS_HARD((player, shopId) -> {
		if (!player.getDiaryManager().getWildernessDiary().hasDoneHard()) {
			player.sendMessage("You must have completed wilderness hard diaries to purchase this.");
			return false;
		}
		return true;
		}, 81),
	VARROCK_MEDIUM((player, shopId) -> {
		if (!player.getDiaryManager().getVarrockDiary().hasDoneMedium()) {
			player.sendMessage("You must have completed the varrock diary up to medium to purchase this.");
			return false;
		}
		return true;
		}, 6)
	;

	private UseShopCondition(BiFunction<Player, Integer, Boolean> buyFromShopPredicate, int... shops) {
		this.buyFromShopPredicate = buyFromShopPredicate;
		this.shops = shops;
	}
	
	private BiFunction<Player, Integer, Boolean> buyFromShopPredicate;
	private int[] shops;

	private boolean hasShop(int shop) {
		return shops != null && IntStream.of(shops).anyMatch(id -> id == shop);
	}
	
	
	public static UseShopCondition getBuyAnyFromItem(int shop) {
		return Stream.of(UseShopCondition.values()).filter(condition -> condition.buyFromShopPredicate != null && condition.hasShop(shop)).findFirst().orElse(null);
	}
	

	public static boolean valid(Player player, ShopTab shopTab) {
		UseShopCondition valid = getBuyAnyFromItem(shopTab.getId());
		if(valid != null) {
			return valid.buyFromShopPredicate.apply(player, shopTab.getId());
		}
		return true;
	}

	
	
	
}
