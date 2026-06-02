package valius.model.shops;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.GlobalMessages.MessageType;
import valius.util.Misc;

public class FlashSale {

	private static final long MIN_TIME_BETWEEN_SALE = TimeUnit.MINUTES.toMillis(60);
	private static final long MAX_TIME_BETWEEN_SALE = TimeUnit.MINUTES.toMillis(90);
	public static final long SALE_TIME = TimeUnit.MINUTES.toMillis(30);
	public static final long END_SALE_ANNOUNCE = TimeUnit.MINUTES.toMillis(10);
	private static final int MINIMUM_ITEM_PRICE = 3;
	private static boolean endSaleAnnounced;
	public static FlashSaleDiscount discount;
	
	public static boolean flashSaleEnabled;
	 
	
	private static TabbedShop applyTo = TabbedShop.DONATOR_STORE;
	private static long lastSale = -1;
	private static long nextSale = -1;
	
	public static void onTick() {
		if(nextSale == -1) {
			nextSale = System.currentTimeMillis() + Misc.random(MIN_TIME_BETWEEN_SALE, MAX_TIME_BETWEEN_SALE);
		}
		if(discount != null) {//Sale active
			if(lastSale + SALE_TIME < System.currentTimeMillis()) {
				endSale();
			} else if(!endSaleAnnounced && lastSale + (SALE_TIME - END_SALE_ANNOUNCE) < System.currentTimeMillis()) {
				endSaleAnnounced = true;

				GlobalMessages.send(TimeUnit.MILLISECONDS.toMinutes(END_SALE_ANNOUNCE) + " minutes left on the current flash sale! Get " + discount.getDiscountPercentage() + "% off select items in the donation store!", MessageType.DONATION);//TODO Change messages
			}
		} else {
			if(nextSale < System.currentTimeMillis()) {
				generateSales();
			}
		}
	}
	
	public static void generateSales() {
		
		int amt = Misc.random(2, 5);
		discount = FlashSaleDiscount.getType(amt);
		List<ShopTab> shopTabs = applyTo.getShops();
		List<ShopItem> items = shopTabs.stream().flatMap(tab -> tab.getItems().stream()).filter(item -> item.getCost() >= MINIMUM_ITEM_PRICE).collect(Collectors.toList());
		
		Collections.shuffle(items);
		
		items.stream().limit(amt).forEach(item -> { 
			item.setCost((int) Math.floor(item.getCost() * ((100.0 - discount.getDiscountPercentage()) / 100.0) ));
			item.applyFlag((int) discount.getDiscountPercentage());
		});
		
		applyTo.flagShopUpdate();
		lastSale = System.currentTimeMillis();
		
		GlobalMessages.send("A new Flash sale has started! Get " + discount.getDiscountPercentage() + "% off select items in the donation store!", MessageType.DONATION);//TODO Change messages
	}
	
	public static void endSale() {

		applyTo.resetTabs();
		applyTo.flagShopUpdate();
		endSaleAnnounced = false;
		discount = null;
		nextSale = System.currentTimeMillis() + Misc.random(MIN_TIME_BETWEEN_SALE, MAX_TIME_BETWEEN_SALE);
		GlobalMessages.send("The Flash sale has ended!", MessageType.DONATION);
	}


}
