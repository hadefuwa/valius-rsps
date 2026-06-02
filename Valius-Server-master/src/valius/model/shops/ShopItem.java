package valius.model.shops;

import lombok.Getter;
import lombok.Setter;
import valius.model.items.ItemList;
import valius.world.World;

@Getter
@Setter
public class ShopItem {
	
	public ShopItem(int id, int defaultAmount) {
		this.id = id;
		this.defaultAmount = defaultAmount;
		this.currentAmount = defaultAmount;
	}

	
	private int id, defaultAmount, cost;
	
	private transient int currentAmount;
	
	private transient int flag;
	
	public void setup() {
		this.currentAmount = this.defaultAmount;
	}
	
	public int getStoredCost() {
		return cost;
	}
	public int getCost() {
		if(cost > 0)
			return cost;
		
		ItemList itemList = World.getWorld().getItemHandler().ItemList[id];
		return (int) (itemList == null ? 1 : itemList.ShopValue);
		
	}

	public void applyFlag(int i) {
		flag |= i;
	}
	
	public void removeFlag(int i) {
		flag &= i;
	}
	
	public void resetFlag() {
		flag = 0;
	}
	
	public ShopItem copy() {
		ShopItem copy = new ShopItem(id, defaultAmount);
		if(cost > 0)
			copy.setCost(cost);
		copy.setup();
		return copy;
	}
	

}
