package test;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.items.bank.BankTab;

@Slf4j
public class ReplacePlayerItems {
	
	public static void main(String[] args) {
		if(true)
			return;//Don't re run this
		for(String fileName : new File(Config.CHARACTER_SAVE_DIRECTORY).list()) {
			fileName = fileName.replace(".txt", "").trim();
			Player player = PlayerSave.loadGame(fileName);
			if(player != null) {
				int invCount = 0;
				player.playerName = fileName;
				player.playerName2 = player.playerName;
				for(int index = 0;index<player.playerItems.length;index++) {
					if(player.playerItems[index] >= 22990) {
						player.playerItems[index] += 10000;
						invCount++;
					}
				}
				
				int[] bankChange = new int[1];
				for(int index = 0;index<player.getBank().getBankTab().length;index++) {
					BankTab tab = player.getBank().getBankTab()[index];
					
					if(tab != null) {
						tab.getItems().stream().forEach(bankItem -> {
							if(bankItem != null && bankItem.getId() >= 22990) {
								bankItem.setId(bankItem.getId() + 10000);
								bankChange[0]++;
							}
						});
					}
				}
				
				int equipChange = 0;
				for(int index = 0;index<player.playerEquipment.length;index++) {
					if(player.playerEquipment[index] >= 22990) {
						player.playerEquipment[index] += 10000;
						equipChange++;
					}
				}
				int[] lootItems = new int[1];
				player.getLootingBag().items.stream().forEach(item -> {
					if(item != null && item.getId() >= 22990) {
						item.set(item.getId() + 10000, item.getAmount());
						lootItems[0]++;
					}
				});
				
				log.info("Replaced {} inventory items, {} bank items, {} equipment items, {} lootbag items", invCount, bankChange[0], equipChange, lootItems[0]);
				log.info("Saving {}: success? ", player.playerName2, PlayerSave.saveGame(player, false));
			} else {
				log.info("Player {} null", fileName);
			}
		}
	}

}
