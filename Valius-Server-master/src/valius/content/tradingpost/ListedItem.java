package valius.content.tradingpost;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import valius.model.items.ItemList;
import valius.world.World;

@Getter
@Setter
@EqualsAndHashCode
public class ListedItem {

	private String seller; 
	private int id, amount;
	private int pricePer;
	private int pendingCash;
	private int sold;
	private long listedAt;
	
	public ListedItem(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public boolean soldAll() {
		return sold >= amount;
	}
	
	public boolean canBuy(int buyAmt) {
		return !soldAll() && getRemaining() <= buyAmt;
	}
	
	public int getRemaining() {
		return amount - sold;
	}
	
	public boolean buy(int buyAmt) {
		if(canBuy(buyAmt)) {
			sold += buyAmt;
			pendingCash += buyAmt * pricePer;
		}
		return false;
	}

	public String getName() {
		ItemList itemList = World.getWorld().getItemHandler().getItemList(id);
		
		return itemList == null ? "null" : itemList.itemName == null ? "null" : itemList.itemName;
	}
	
	public void resetPendingCash() {
		pendingCash = 0;
	}

	public void sellItem(int amount) {
		this.pendingCash += amount * pricePer;
		this.sold += amount;
	}

	@Override
	public String toString() {
		return "ListedItem [seller=" + seller + ", id=" + id + ", amount=" + amount + ", pricePer=" + pricePer
				+ ", pendingCash=" + pendingCash + ", sold=" + sold + ", listedAt=" + listedAt + "]";
	}
	
	

}
