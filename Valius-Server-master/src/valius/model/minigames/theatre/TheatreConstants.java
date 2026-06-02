package valius.model.minigames.theatre;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.ItemDefinition;
import valius.model.items.ItemRarity;
import valius.model.items.LootItem;
import valius.util.Misc;

/**
 * @author Patrity, ReverendDread, RSPSi
 *
 */

public class TheatreConstants {


	private static final LootItem[] rewards = {
			//Common drops
			LootItem.of(246, 52, 120, ItemRarity.COMMON), //zammy wine
			LootItem.of(1374, 4, 8, ItemRarity.COMMON), //rune baxe
			LootItem.of(1128, 4, 8, ItemRarity.COMMON), //rune plate
			LootItem.of(1114, 4, 8, ItemRarity.COMMON), //rune chain
			LootItem.of(1776, 300, 460, ItemRarity.COMMON), //molten glass
			LootItem.of(22447, 60, 120, ItemRarity.COMMON), //Vial of Blood
			LootItem.of(560, 600, 1200, ItemRarity.COMMON), // deathrune
			LootItem.of(565, 800, 1200, ItemRarity.COMMON), // blood rune
			LootItem.of(1939, 700, 1200, ItemRarity.COMMON), // swamp tar
			LootItem.of(1392, 22, 34, ItemRarity.COMMON), // battlestaff
			LootItem.of(560, 300, 1200, ItemRarity.COMMON), // deathrune
			LootItem.of(450, 180, 300, ItemRarity.COMMON), // addy ore
			LootItem.of(452, 106, 144, ItemRarity.COMMON), // rune ore
			LootItem.of(454, 750, 1200, ItemRarity.COMMON), // coal
			LootItem.of(445, 500, 700, ItemRarity.COMMON), // gold ore
			LootItem.of(208, 50, 80, ItemRarity.COMMON), // rannar
			LootItem.of(3050, 56, 78, ItemRarity.COMMON), // toadflax
			LootItem.of(210, 52, 110, ItemRarity.COMMON), // irit
			LootItem.of(212, 38, 70, ItemRarity.COMMON), // avantoe
			LootItem.of(214, 46, 96, ItemRarity.COMMON), // Kwuarm
			LootItem.of(218, 40, 96, ItemRarity.COMMON), // deathrune		
			//Rare drops
			LootItem.of(22477, 1, 1, ItemRarity.RARE), // avernic defender
			LootItem.of(22324, 1, 1, ItemRarity.RARE), // rapier
			LootItem.of(22323, 1, 1, ItemRarity.RARE), // staff
			LootItem.of(22325, 1, 1, ItemRarity.RARE), // scythe
			LootItem.of(22326, 1, 1, ItemRarity.RARE), // helm
			LootItem.of(22327, 1, 1, ItemRarity.RARE), // chest
			LootItem.of(22328, 1, 1, ItemRarity.RARE), // legs	
			LootItem.of(33468, 1, 1, ItemRarity.RARE), // gloves	
			LootItem.of(33467, 1, 1, ItemRarity.RARE), // boots
			LootItem.of(33581, 1, 1, ItemRarity.RARE), // Blood Perk twisted cbow
			LootItem.of(33768, 1, 1, ItemRarity.RARE), // Gem of the dwarves
	};
	
	public static void giveLoot(Player p) {
		if (p.theatrePoints == -1) {
			p.sendMessage("You have already been rewarded!");
			return;
		}
		LootItem rare = getRare();
		LootItem common = getCommon();
		LootItem common2 = getCommon();
		int coinAmnt = Misc.random(5000000, 8000000);
		int coins = 995;
		if (p.theatrePoints > 500) {
			if (Misc.random(1,100) > 96) {
				p.getItems().addItemUnderAnyCircumstance(rare.getId(), Misc.random(rare.getMin(), rare.getMax()));
				GlobalMessages.send(p.playerName+" received a "+ItemDefinition.forId(rare.getId()).getName()+" from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
			} else {
				p.getItems().addItemUnderAnyCircumstance(common.getId(), Misc.random(common.getMin(), common.getMax()));
			}
		}
		if (p.theatrePoints <= 500 && p.theatrePoints > 400) {
			if (Misc.random(1,100) > 97) {
				p.getItems().addItemUnderAnyCircumstance(rare.getId(), Misc.random(rare.getMin(), rare.getMax()));
				GlobalMessages.send(p.playerName+" received a "+ItemDefinition.forId(rare.getId()).getName()+" from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
			} else {
				p.getItems().addItemUnderAnyCircumstance(common.getId(), Misc.random(common.getMin(), common.getMax()));
			}			
		}
		if (p.theatrePoints <= 400 && p.theatrePoints > 300) {
			if (Misc.random(1,100) > 97) {
				p.getItems().addItemUnderAnyCircumstance(rare.getId(), Misc.random(rare.getMin(), rare.getMax()));
				GlobalMessages.send(p.playerName+" received a "+ItemDefinition.forId(rare.getId()).getName()+" from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
			} else {
				p.getItems().addItemUnderAnyCircumstance(common.getId(), Misc.random(common.getMin(), common.getMax()));
			}
		}
		if (p.theatrePoints <= 300 && p.theatrePoints > 200) {
			if (Misc.random(1,100) > 97) {
				p.getItems().addItemUnderAnyCircumstance(rare.getId(), Misc.random(rare.getMin(), rare.getMax()));
				GlobalMessages.send(p.playerName+" received a "+ItemDefinition.forId(rare.getId()).getName()+" from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
			} else {
				p.getItems().addItemUnderAnyCircumstance(common.getId(), Misc.random(common.getMin(), common.getMax()));
			}
		}
		if (p.theatrePoints <= 200 && p.theatrePoints >= 10) {
			if (Misc.random(1,100) > 98) {
				p.getItems().addItemUnderAnyCircumstance(rare.getId(), Misc.random(rare.getMin(), rare.getMax()));
				GlobalMessages.send(p.playerName+" received a "+ItemDefinition.forId(rare.getId()).getName()+" from Theatre of Blood!", GlobalMessages.MessageType.LOOT);
			} else {
				p.getItems().addItemUnderAnyCircumstance(common.getId(), Misc.random(common.getMin(), common.getMax()));
			}
		}
		if (p.theatrePoints < 10) {
			p.sendMessage("You did not do enough damage to be rewarded for this raid!");
			p.sendMessage("Have an onion!");
			p.getItems().addItemUnderAnyCircumstance(1957, 1);
			p.theatrePoints = -1;
			return;
		}
		p.getItems().addItemUnderAnyCircumstance(common2.getId(), Misc.random(common2.getMin(), common2.getMax()));
		p.getItems().addItemUnderAnyCircumstance(coins, coinAmnt);
		p.getItems().addItemUnderAnyCircumstance(1965, 1);
		p.getItems().addItemUnderAnyCircumstance(13307, Misc.random(1000, 2000));
		p.theatrePoints = -1;
		
		//Casket for every 10 raids complete
		 for (int chest_interval = 10; chest_interval <= p.totalTheatreFinished; chest_interval += 10) {
				if (p.totalTheatreFinished % 10 == 0) {
					p.getItems().addItemUnderAnyCircumstance(33943, 1);
					p.sendMessage("You receive a Casket for Finishing your raid.");
				//System.out.println("chest interval:" + chest_interval + "");
				return;
			} else {
				int progress = p.totalTheatreFinished % 10;
				p.sendMessage("You will receive a Theatre of Blood casket in @blu@" + Math.subtractExact(10, progress) + "/10</col> more raids.");
				return;
			}
		}
}
	private static final LootItem getCommon() {
		return Misc.randomTypeOfList(
				Stream.of(rewards).filter(loot -> loot.getRarity() == ItemRarity.COMMON).collect(Collectors.toList()));
	}

	private static final LootItem getRare() {
		return Misc.randomTypeOfList(
				Stream.of(rewards).filter(loot -> loot.getRarity() == ItemRarity.RARE).collect(Collectors.toList()));
	}
	
	public static void capeCheck(Player p) {

		if (p.totalTheatreFinished == 10) {
			p.getItems().addItemUnderAnyCircumstance(22494, 1);
			p.sendMessage("You have been awarded a Sinhaza Shroud for completing 10 rounds!");
		}
		if (p.totalTheatreFinished == 25) {
			p.getItems().addItemUnderAnyCircumstance(22496, 1);
			p.sendMessage("You have been awarded a Sinhaza Shroud for completing 25 rounds!");
		}
		if (p.totalTheatreFinished == 50) {
			p.getItems().addItemUnderAnyCircumstance(22498, 1);
			p.sendMessage("You have been awarded a Sinhaza Shroud for completing 50 rounds!");
		}
		if (p.totalTheatreFinished == 100) {
			p.getItems().addItemUnderAnyCircumstance(22500, 1);
			p.sendMessage("You have been awarded a Sinhaza Shroud for completing 100 rounds!");
		}
		if (p.totalTheatreFinished == 250) {
			p.getItems().addItemUnderAnyCircumstance(22502, 1);
			p.sendMessage("You have been awarded a Sinhaza Shroud for completing 250 rounds!");
		}
	
		
	}

}
