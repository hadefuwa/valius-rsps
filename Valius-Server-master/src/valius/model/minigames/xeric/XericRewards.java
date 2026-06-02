/* Distributes loot after completion of Trials of Xeric.
 * Author @Patrity
 */
package valius.model.minigames.xeric;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.ItemDefinition;
import valius.util.Misc;
/**
 * 
 * @author Patrity
 * 
 */
public class XericRewards {
	
	int qty;

	public static void giveReward(int dmg, Player player) {
		int roll = Misc.random(100);
		player.totalRaidsFinished++;
		player.sendMessage("You have now completed " + player.totalRaidsFinished + " Trials of Xeric!");
		if (dmg > 9999) {
			if (roll >= 93) {
				rareDrop(player);
			} else {
				commonDrop(player);
			}
		}

		if (dmg >= 8000 && dmg <= 9999) {
			if (roll >= 95) {
				rareDrop(player);
			} else {
				commonDrop(player);
			}
		}

		if (dmg >= 5000 && dmg <= 7999) {
			if (roll >= 97) {
				rareDrop(player);
			} else {
				commonDrop(player);
			}
		}

		if (dmg <= 4999 ) {
			if (roll >= 99) {
				rareDrop(player);
			} else {
				commonDrop(player);
			}
		}
	}

	public static final int rareDropItem[] = { 
			33021,//torva full helm
			33022,//torva platebdy
			33023,//torva platelegs
			33024,//pernix cowl
			33025,//pernix body
			33026,//pernix chaps
			33027,//virtus mask
			33028,//virtus robe top
			33029,//virtus robe legs
			33030,//zartye bow
			33470//twisted staff
};
	public static final int commonDropItem[] = {
			208,//ranarr
			212,//avantoe
			1748,//black d'hide
			537,//dragon bones
			452,//runite ore
			450,//addy ore
			1618,//uncut diamond
			560,//death rune
			1632,//uncut dragonstone
			11212,//dragon arrow
			11230,// dragon dart
			565,//blood rune
			8783,//mahogany planks
			566,//soul rune
			1514,//magic logs
			13440,// raw angler fish
			1780,//flax
			995,//coins
			13307,//blood money
			220,//torstol
			3050,//toadflax
			3052,//snapdragon
			454,//coal
			441,//iron ore
			1957//onion
			
			
};

	public static void rareDrop(Player player) {
		int rareitem = Misc.random(rareDropItem.length - 1);
		player.getItems().addItemUnderAnyCircumstance(rareDropItem[rareitem], 1);
		GlobalMessages.send(player.playerName + " has received a " + ItemDefinition.forId(rareDropItem[rareitem]).getName()  + " from Trials of Xeric!", GlobalMessages.MessageType.LOOT);
	}


	public static void commonDrop(Player player) {
		int qty = (100 + Misc.random(80));
		int commonitem = Misc.random(commonDropItem.length - 1);
		int drop = commonDropItem[commonitem];
		if (drop == 13307) {
			qty = (1000 + Misc.random(1000));
		}
		if (drop == 995) {
			qty = (1000000 + Misc.random(750000));
		}
		if (drop == 1618) {
			qty = (30 + Misc.random(50));
		}
		if (drop == 1532) {
			qty = (30 + Misc.random(20));
		}
		if (drop == 1957) {
			qty = 1;
			GlobalMessages.send(player.playerName+" has received THE ONION as a reward from Trials of Xeric", GlobalMessages.MessageType.LOOT);
		} else {
			player.sendMessage("You have received @red@" + ItemDefinition.forId(drop).getName()
					+ " x" + qty + "@bla@ as a reward from Trials of Xeric!");
		}
		player.getItems().addItemUnderAnyCircumstance(drop, qty);
		
		//Casket for every 10 raids complete
		 for (int chest_interval = 10; chest_interval <= player.totalXericFinished; chest_interval += 10) {
				if (player.totalXericFinished % 10 == 0) {
					player.getItems().addItemUnderAnyCircumstance(33942, 1);
					player.sendMessage("You receive a Casket for Finishing your raid.");
				//System.out.println("chest interval:" + chest_interval + "");
				return;
			} else {
				int progress = player.totalXericFinished % 10;
				player.sendMessage("You will receive a Trial of Xeric casket in @blu@" + Math.subtractExact(10, progress) + "/10</col> more raids.");
				return;
			}
		}
	}
}