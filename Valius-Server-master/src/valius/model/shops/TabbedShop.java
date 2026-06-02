package valius.model.shops;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;

public enum TabbedShop {
	
	SUPPLIES("General Supplies", 2, 114),//general store, food & pots, fancy clothes shop
	IRONMAN("Ironman Shops", 41),//ironman general store, ironman Herblore supplies
	COMBAT("Combat Supplies", 8, 4, 6, 113),//melee supplies, range supplies, magic supplies, food and pots
	PVP("Player killing Reward Shops", 80, 12),//Bounty, PKP
	SKILLCAPES("Skillcapes", 17, 171, 121), //skillcape, 200m cape
	POINTS("Point Reward Shops", 125, 126, 123, 119),//pvm points, boss points, skill points, blood money
	AVA("Ava's Devices", 124),
	RFD_GLOVES("Culinaromancer's Shop", 14),
	DONATOR_STORE("Donator Shops", 133, 131, 130, 132, 134),
	SLAYER_STORE("Slayer Store", 44),
	PRESTIGE_STORE("Prestige Store", 120),
	ACHIEVEMENT_STORE("Achievement Shop", 78),
	GRACE_STORE("Grace's Graceful Store", 18),
	VOTE_STORE("Vote Shop", 77),
	ASSAULT_STORE("Shayzien Shop", 82),
	MAGE_STORE("Magic Shop", 5),
	SKILLING_STORE("Skilling Store", 7),
	FARMING_STORE("Farming Store", 16),
	CRAFTING_SHOP("Crafting Shop", 20), 
	HERBLORE_SHOP("Herblore Shop", 21), 
	FISHING_STORE("Fishing Store", 22), 
	ALECKS_HUNTER_EMPORIUM("Aleck's Hunter Emporium", 23), 
	CRAFTING_SHOP2("Crafting shop", 25), 
	SIGMUNDS_SHOP("Sigmunds shop", 26), 
	TOKKUL_SHOP("Tokkul Shop", 29), 
	KOLODIONS_STORE("Kolodions Store", 40), 
	ZAFFS_MAGIC_EMPORIUM("Zaff's Magic Emporium", 47), 
	RANGE_SUPPLIES("Range Supplies", 48), 
	PEST_CONTROL_SHOP("Pest Control Shop", 75), 
	HOLIDAY_SHOP("Holiday Shop", 79), 
	OZIACHS_STORE("Oziachs store", 81), 
	DONATORS_COIN_SHOP("Donator's Coin Shop", 112), 
	JOSSIKS_STORE("Jossiks Store", 122),
	THEATRE_SHOP("Theatre of Blood supplies", 135),
	LIMITED_SHOP("limited Time Shops", 137, 138),
	CHRISTMAS_SHOP("Christmas Shop", 140),
	
	;

	private TabbedShop(String title, int... shopIds) {
		this.title = title;
		this.shopIds = shopIds;
	}
	
	@Getter
	private String title;
	
	@Getter
	private int[] shopIds;
	
	public static Stream<TabbedShop> stream(){
		return Stream.of(TabbedShop.values());
	}
	
	private static final int TAB_COUNT = 5;
	public static final int BASE_TAB_INVENTORY = 64255;
	public static final int MAX_TAB_INVENTORY = 64259;

	public void sendShops(Player player) {
		for (int index = 0; index < shopIds.length; index++) {
			int shopId = shopIds[index];
			int currentIndex = index;
			Optional<ShopTab> shopTabOpt = ShopTab.getShopTab(shopId);
			
			shopTabOpt.ifPresent( shopTab -> shopTab.sendItems(player, currentIndex));
			
		
		}
		for(int index = shopIds.length;index<TAB_COUNT;index++) {
			sendEmpty(player, index);
		}
	}
	
	private static void sendEmpty(Player player, int index) {
		player.getPA().sendString("", 64210 + index);

		player.getOutStream().writePacketHeader(72);
		player.getOutStream().writeWordBigEndian(BASE_TAB_INVENTORY + index);//TODO get tab inv id
		player.flushOutStream();
		
	}
	
	public static Optional<TabbedShop> getTabbedShopByTab(ShopTab shopTab) {
		return TabbedShop.stream().filter(shop -> shop.getShops().contains(shopTab)).findFirst();
	}

	public Optional<ShopTab> getShop(int index){
		if(index >= shopIds.length)
			return null;
		return ShopTab.getShopTab(shopIds[index]);
	}
	
	public static Optional<TabbedShop> getShopById(int shopId){
		return Stream.of(TabbedShop.values()).filter(tabbedShop -> IntStream.of(tabbedShop.shopIds).filter(id -> shopId == id).count() > 0).findFirst();
	}
	
	public List<ShopTab> getShops(){
		return IntStream.of(shopIds).mapToObj(ShopTab::getShopTab).filter(optional -> optional.isPresent()).map(opt -> opt.get()).collect(Collectors.toList());
	}

	public boolean sellsItem(int itemID, int index) {
		return getShop(index).filter(shopTab -> shopTab.hasItem(itemID)).isPresent();
	}

	public void buyItem(Player player, int slot, int amount) {
		Optional<ShopTab> shopTabOpt = getShop(player.getShops().getActiveShopTab());
		shopTabOpt.ifPresent(shopTab -> shopTab.buyItem(player, slot, amount));
		
	}
	
	public void flagShopUpdate() {
		getShops().forEach(shop -> shop.setForceUpdate(true));
	}

	public void resetTabs() {
		getShops().forEach(tab -> tab.reset());
	}
}
