package valius.model.entity.player.mode;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import valius.model.entity.player.Player;
import valius.model.items.GroundItem;
import valius.model.minigames.pest_control.PestControlRewards.RewardButton;

public abstract class Mode {

	/**
	 * A list of all {@link Mode}'s that exist in the game.
	 */
	private static final List<Mode> MODES = ImmutableList
			.copyOf(Arrays.asList(new RegularMode(ModeType.REGULAR), new IronmanMode(ModeType.IRON_MAN), new UltimateIronmanMode(ModeType.ULTIMATE_IRON_MAN), new HcIronmanMode(ModeType.HC_IRON_MAN), new OsrsMode(ModeType.OSRS), new ClassicMode(ModeType.CLASSIC), new EliteMode(ModeType.ELITE), new GroupIronman(ModeType.GROUP_IRONMAN)));

	/**
	 * The type of mode
	 */
	protected final ModeType type;

	/**
	 * Creates a new mode for a specific type
	 * 
	 * @param type the type of mode
	 */
	public Mode(ModeType type) {
		this.type = type;
	}

	/**
	 * Determines whether or not the player can access trading operations
	 * @param player TODO
	 * @param other TODO
	 * @return {@code true} if the player can access trading operations, otherwise {@code false}
	 */
	public abstract boolean isTradingPermitted(Player player, Player other);

	/**
	 * Determines whether or not the player can access staking operations
	 * 
	 * @return {@code true} if the player can access staking operations, otherwise {@code false}
	 */
	public abstract boolean isStakingPermitted();

	/**
	 * Determines if the player is permitted to pickup items on the ground that is not theirs
	 * @param player TODO
	 * @param item TODO
	 * @return {@code true} if the player can pickup items that are not theirs, otherwise {@code false}
	 */
	public abstract boolean isItemScavengingPermitted(Player player, GroundItem item);

	/**
	 * Determines if the player gains combat experience whilst in PVP
	 * 
	 * @return {@code true} if the player can gain combat experience, otherwise {@code false}
	 */
	public abstract boolean isPVPCombatExperienceGained();

	/**
	 * Determines if the player is permitted to claim items or other rewards from donating.
	 * 
	 * @return {@code true} if the player can claim donated items or rewards, otherwise {@code false}
	 */
	public abstract boolean isDonatingPermitted();

	/**
	 * Determines if the player's game mode allows them to claim a particular package
	 * 
	 * @param packageName the name of the package
	 * @return {@code true} if the package can be claimed, otherwise {@code false}
	 */
	public abstract boolean isVotingPackageClaimable(String packageName);

	/**
	 * Determines if a particular shop, by identification, is accessible
	 * 
	 * @param shopId the identification value of the shop
	 * @return {@code true} if the shop can be accessed, otherwise {@code false}
	 */
	public abstract boolean isShopAccessible(int shopId);

	/**
	 * Determines if a particular item from a shop is purchasable
	 * 
	 * @param shopId the shop identification value
	 * @param itemId the item id attempting to be purchased
	 * @return {@code true} if the item can be purchased from the specific shop, otherwise false
	 */
	public abstract boolean isItemPurchasable(int shopId, int itemId);

	/**
	 * Determines if a particular item can be sold to a shop
	 * 
	 * @param shopId the shop id
	 * @param itemId the item id
	 * @return {@code true} if the item can be sold to a specific shop, otherwise {@code false}
	 */
	public abstract boolean isItemSellable(int shopId, int itemId);

	/**
	 * Determines if the particular reward from the pest control mini-game is selectable and ultimately purchasable.
	 * 
	 * @param reward the reward we're trying to select
	 * @return {@code true} if the reward can be selected.
	 */
	public abstract boolean isRewardSelectable(RewardButton reward);

	/**
	 * The price of an item from a shop
	 * 
	 * @param shopId the shop the item is being bought from
	 * @param itemId the id of the item being bought
	 * @param price the original price of the item
	 * @return the modified price of the item
	 */
	public int getModifiedShopPrice(int shopId, int itemId, int price) {
		return price;
	}

	/**
	 * Determines if banking is permitted for this type of game mode.
	 * 
	 * @return {@code true} if the mdoe is allowed or permitted to use the banking system.
	 */
	public abstract boolean isBankingPermitted();

	/**
	 * The type of Mode this is
	 * 
	 * @return the mode type
	 */
	public ModeType getType() {
		return type;
	}

	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#IRON_MAN}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isIronman() {
		return type == ModeType.IRON_MAN;
	}

	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#ULTIMATE_IRON_MAN}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isUltimateIronman() {
		return type == ModeType.ULTIMATE_IRON_MAN;
	}
	
	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#HARDCORE_IRON_MAN}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isHcIronman() {
		return type == ModeType.HC_IRON_MAN;
	}

	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#NONE}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isRegular() {
		return type == ModeType.REGULAR;
	}

	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#OSRS}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isOsrs() {
		return type == ModeType.OSRS;
	}
	
	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#CLASSIC}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isClassic() {
		return type == ModeType.CLASSIC;
	}

	/**
	 * Returns a particular {@link Mode} for the given {@link ModeType}.
	 * 
	 * @param type the type of mode
	 * @return the {@link Mode} for the type, or null if no mode exists for the type.
	 */
	public static Mode forType(ModeType type) {
		return MODES.stream().filter(mode -> mode.getType().equals(type)).findFirst().orElse(null);
	}

	/**
	 * Determines if the {@link #type} is equal to {@link ModeType#GROUP_IRONMAN}
	 * 
	 * @return {@code true} if the player is of this type, otherwise {@code false}
	 */
	public boolean isGroupIronman() {
		return type == ModeType.GROUP_IRONMAN;
	}

}
