/**
 * 
 */
package valius.model.entity.player.mode;

import java.util.Objects;

import valius.model.entity.player.Player;
import valius.model.items.GroundItem;
import valius.model.minigames.pest_control.PestControlRewards.RewardButton;

/**
 * @author ReverendDread
 * Aug 8, 2019
 */
public class GroupIronman extends Mode {

	/**
	 * @param type
	 */
	public GroupIronman(ModeType type) {
		super(type);
	}

	@Override
	public boolean isTradingPermitted(Player player, Player other) {
		if (player == null || other == null) {
			return false;
		}
		return player.getGroupIronman().isTeamate(other);
	}

	@Override
	public boolean isStakingPermitted() {
		return false;
	}

	@Override
	public boolean isItemScavengingPermitted(Player player, GroundItem item) {
		if (Objects.isNull(player) || Objects.isNull(item))
			return false;
		return player.getGroupIronman().isTeamate(item.getController());
	}

	@Override
	public boolean isPVPCombatExperienceGained() {
		return true;
	}

	@Override
	public boolean isDonatingPermitted() {
		return true;
	}

	@Override
	public boolean isVotingPackageClaimable(String packageName) {
		return Mode.forType(ModeType.IRON_MAN).isVotingPackageClaimable(packageName);
	}

	@Override
	public boolean isShopAccessible(int shopId) {
		return Mode.forType(ModeType.IRON_MAN).isShopAccessible(shopId);
	}

	@Override
	public boolean isItemPurchasable(int shopId, int itemId) {
		return Mode.forType(ModeType.IRON_MAN).isItemPurchasable(shopId, itemId);
	}

	@Override
	public boolean isItemSellable(int shopId, int itemId) {
		return Mode.forType(ModeType.IRON_MAN).isItemSellable(shopId, itemId);
	}

	@Override
	public boolean isRewardSelectable(RewardButton reward) {
		return Mode.forType(ModeType.IRON_MAN).isRewardSelectable(reward);
	}

	@Override
	public boolean isBankingPermitted() {
		return true;
	}
	
	@Override
	public int getModifiedShopPrice(int shopId, int itemId, int price) {
		return Mode.forType(ModeType.IRON_MAN).getModifiedShopPrice(shopId, itemId, price);
	}

}
