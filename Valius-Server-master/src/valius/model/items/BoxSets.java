package valius.model.items;

import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | 4:28:51 a.m. | Nov. 25, 2019
 *
 */
public enum BoxSets {
	
	SUPER_SET(new int[][] { { 2437, 2443, 2441 } }, 10),
		;

	private int[][] contents;
	private int amt;

	BoxSets(int[][] contents, int amt) {
		this.contents = contents;
		this.amt = amt;
	}

	public boolean openBox(Player player) {
		int[] items = new int[4];
		for (int col = 0; col < 3; col++) {
			for (int row = 0; row < 1; row++) {
				if (player.getItems().addItem(contents[row][col], amt)) {
					items[col] = contents[row][col];
					break;
				}
			}
		}
		return false;
	}
}
