package valius.content;

import valius.model.entity.player.Player;

public class LimitedChest {
	
	public int CHEST = 1;
	
	public int[] PACK_ITEMS = {1, 2};
	
	public void OpenPack(Player p) {
		for (int PackItems : PACK_ITEMS) {
			p.getItems().addItemUnderAnyCircumstance(PackItems, 1);
		}
	}

}
