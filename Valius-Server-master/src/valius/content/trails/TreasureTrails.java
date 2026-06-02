package valius.content.trails;

import java.util.List;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.ItemUtility;
import valius.world.World;

public class TreasureTrails {

	private Player player;

	public TreasureTrails(Player player) {
		this.player = player;
	}

	public void addRewards(RewardLevel difficulty) {
		//int rights = player.getRights().getPrimary().getValue() - 1;
		List<RewardItem> rewards = CasketRewards.getRandomRewards(difficulty);
		for (RewardItem item : rewards) {
			if (ItemUtility.getItemName(item.getId()).contains("3rd") || 
				item.getId() == 2577 || 
				ItemUtility.getItemName(item.getId()).contains("mage's")) {
				GlobalMessages.send(player.playerName + "</col> received <col=255>" + ItemUtility.getItemName(item.getId()) + "</col> from a Treasure Trail.", GlobalMessages.MessageType.LOOT);
			}
			if (!player.getItems().addItem(item.getId(), item.getAmount())) {
				World.getWorld().getItemHandler().createGroundItem(player, item.getId(), player.getX(), player.getY(), player.getHeight(), item.getAmount());
			}
		}
		displayRewards(rewards);
	}

	public void displayRewards(List<RewardItem> rewards) {
		player.outStream.createFrameVarSizeWord(53);
		player.outStream.writeWord(6963);
		player.outStream.writeWord(rewards.size());
		for (int i = 0; i < rewards.size(); i++) {
			player.outStream.writeByte(0);
			if (player.playerItemsN[i] > 254) {
				player.outStream.writeByte(255);
				player.outStream.writeDWord_v2(rewards.get(i).getAmount());
			} else {
				player.outStream.writeByte(rewards.get(i).getAmount());
			}
			if (rewards.size() > 0) {
				player.outStream.writeWordBigEndianA(rewards.get(i).getId() + 1);
			} else {
				player.outStream.writeWordBigEndianA(0);
			}
		}
		player.outStream.endFrameVarSizeWord();
		player.flushOutStream();
		player.getPA().showInterface(6960);
	}
}
