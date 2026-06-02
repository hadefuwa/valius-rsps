package valius.content.tradingpost;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class PlayerListings {
	
	private LinkedList<ListedItem> itemsForSale = Lists.newLinkedList();
	
	public boolean offer(ListedItem item) {
		if(!itemsForSale.contains(item)) {
			return itemsForSale.add(item);
		}
		return false;
	}
	
	public boolean cancel(int slot) {
		return true;
	}
	
	public List<ListedItem> getSalesMatching(String itemName){
		return itemsForSale.stream().filter(saleItem -> saleItem.getName().toLowerCase().contains(itemName.toLowerCase())).collect(Collectors.toList());
	}
	
	public List<ListedItem> getSalesMatching(int itemId){
		return itemsForSale.stream().filter(saleItem -> saleItem.getId() == itemId).collect(Collectors.toList());
	}

	public ListedItem getBySlot(int slot) {
		return slot >= itemsForSale.size() ? null : itemsForSale.get(slot);
	}
	
	public boolean removeBySlot(int slot) {
		return itemsForSale.remove(slot) != null;
	}

	public void remove(ListedItem sale) {
		itemsForSale.remove(sale);
	}

}
