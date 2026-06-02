package valius.model.shops;

import java.util.stream.Stream;

import lombok.Getter;

public enum FlashSaleDiscount {
	TWENTY_FIVE_PERCENT(25),
	TWENTY_PERCENT(20),
	FIFTEEN_PERCENT(15),
	TEN_PERCENT(10);
	
	private FlashSaleDiscount(double discountPercentage){
		this.discountPercentage = discountPercentage;
	}
	
	@Getter
	double discountPercentage;
	
	static FlashSaleDiscount getType(int noItemsOnSale) {
		return stream().filter(saleType -> saleType.ordinal() == noItemsOnSale - 2).findFirst().orElse(TEN_PERCENT);
	}
	
	static Stream<FlashSaleDiscount> stream(){
		return Stream.of(FlashSaleDiscount.values());
	}
}