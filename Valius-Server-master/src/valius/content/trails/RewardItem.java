package valius.content.trails;

import valius.model.items.Item;
import valius.util.Misc;

public class RewardItem extends Item {

	private int minAmount;
	private int maxAmount;
	private int amount = -1;

	public RewardItem(int id, int minAmount, int maxAmount) {
		super(id);
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
	}

	@Override
	public int getAmount() {
		if (amount < 0) {
			amount = Misc.random(maxAmount - minAmount) + minAmount;
		}
		return amount;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	@Override
	public String toString() {
		return "RewardItem [id=" + super.getId() + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount + "]";
	}

}
