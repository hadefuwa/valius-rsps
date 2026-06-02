package valius.model.entity.npc.drops;
import java.util.stream.IntStream;

import valius.content.gauntlet.TheGauntlet;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.GlobalMessages.MessageType;
import valius.model.entity.player.Player;
import valius.util.Misc;
import valius.world.World;

/*
 * @author Patrity
 */

public class OtherDrops {
	
	
	public static void applyOtherDrops(Player player, Location location, NPC npc, int npcLevel) {
		
		 
		int rareDropTable = Misc.random(1, 150);
		int gemRareDropTable = Misc.random(1, 100);
		
		if (player.getInstance() != null && player.getInstance() instanceof TheGauntlet) {
			return;
		}

		/*
		 * Rare Drop Tables
		 */
		if (rareDropTable == 5) {
			player.getRareDropTable().getDrop();
		}
		if (gemRareDropTable == 5) {
			if (Misc.random(500) == 5) {
				player.getItems().addItemUnderAnyCircumstance(19496, 1);
				GlobalMessages.send("" + Misc.capitalizeJustFirst(player.getName())	+ " got very lucky and received an Uncut zenyte from the Gem Rare drop table.",	GlobalMessages.MessageType.LOOT);
			} else {
				player.getGemRareDropTable().getDrop();
			}
			
			if (Misc.random(600) == 5) {// Mystery Box
				World.getWorld().getItemHandler().createGroundItem(player, 6199, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a Mystery Box!");
				GlobalMessages.send(
						player.playerName + " has just received a Mystery Box from " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(100) == 5) { // Blood Money
				World.getWorld().getItemHandler().createGroundItem(player, 13307, location.getX(), location.getY(),
						location.getZ(), Misc.random(50, 250), player.getIndex());
				player.sendMessage("@mag@The boss drops some Blood Money.");
			}
		}
		if (Misc.random(2000) == 5 && npcLevel >= 60) {// Valius Mystery Box
			World.getWorld().getItemHandler().createGroundItem(player, 33269, location.getX(), location.getY(),
					location.getZ(), 1, player.getIndex());
			player.sendMessage("@mag@The monster drops a Valius Mystery Box!");
			GlobalMessages.send(
					player.playerName + " has just received a Valius Mystery Box from a " + npc.getName() + "!",
					GlobalMessages.MessageType.LOOT);
		}
		//Bonus XP Scrolls
		if (Misc.random(1, 1500) == 5) {
			World.getWorld().getItemHandler().createGroundItem(player, 33442, location.getX(), location.getY(),
					location.getZ(), 1, player.getIndex());
			player.sendMessage("@mag@The monster drops a 25% Bonus XP Scroll for 10 minutes.");
		}
		
		int randomNum2 = Misc.random(1, 1050);
		if (randomNum2 == 5) {
			switch (Misc.random(3)) { // Infernal Key Pieces
			case 0:
				World.getWorld().getItemHandler().createGroundItem(player, 33150, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
				GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 1 from "
						+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
				break;
			case 1:
				World.getWorld().getItemHandler().createGroundItem(player, 33151, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
				GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 2 from "
						+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
				break;
			case 2:
				World.getWorld().getItemHandler().createGroundItem(player, 33152, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
				GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 3 from "
						+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
				break;

			}
		}
		
		/*
		 * Random Boss Drops
		 */
		if (!IntStream.of(Points.bosses).noneMatch(bossId -> npc.npcType == bossId)) {
			int randomNum = Misc.random(1, 300);
			if (randomNum == 5) {
				switch (Misc.random(3)) { // Infernal Key Pieces
				case 0:
					World.getWorld().getItemHandler().createGroundItem(player, 33150, location.getX(), location.getY(),
							location.getZ(), 1, player.getIndex());
					player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
					GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 1 from "
							+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
					break;
				case 1:
					World.getWorld().getItemHandler().createGroundItem(player, 33151, location.getX(), location.getY(),
							location.getZ(), 1, player.getIndex());
					player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
					GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 2 from "
							+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
					break;
				case 2:
					World.getWorld().getItemHandler().createGroundItem(player, 33152, location.getX(), location.getY(),
							location.getZ(), 1, player.getIndex());
					player.sendMessage("@mag@The boss drops an Infernal Key Piece.");
					GlobalMessages.send(player.playerName + " has just received a Infernal Key Piece 3 from "
							+ npc.getName() + "!", GlobalMessages.MessageType.LOOT);
					break;

				}
			}
			
			//Bonus XP Scrolls
			if (Misc.random(1, 400) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33442, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 25% Bonus XP Scroll for 10 minutes.");
			}
			if (Misc.random(1, 550) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33443, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 50% Bonus XP Scroll for 10 minutes.");
			}
			if (Misc.random(1, 600) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33444, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 75% Bonus XP Scroll for 10 minutes.");
			}
			if (Misc.random(1, 750) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33445, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 100% Bonus XP Scroll for 10 minutes.");
			}
			if (Misc.random(1, 400) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33446, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 25% Bonus XP Scroll for 30 minutes.");
			}
			if (Misc.random(1, 550) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33447, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 50% Bonus XP Scroll for 30 minutes.");
			}
			if (Misc.random(1, 600) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33448, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 75% Bonus XP Scroll for 30 minutes.");
			}
			if (Misc.random(1, 750) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33449, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 100% Bonus XP Scroll for 30 minutes.");
			}
			if (Misc.random(1, 5000) == 5) {
				World.getWorld().getItemHandler().createGroundItem(player, 33456, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a 100% Bonus XP Scroll for 60 minutes.");
			}
			
			if (Misc.random(2000) == 5) { // Infernal Mbox
				World.getWorld().getItemHandler().createGroundItem(player, 33154, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a Infernal Mystery Box.");
				GlobalMessages.send(
						player.playerName + " has just received a Infernal Mystery Box from " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(25) == 5) { // Blood Money
				World.getWorld().getItemHandler().createGroundItem(player, 13307, location.getX(), location.getY(),
						location.getZ(), Misc.random(50, 250), player.getIndex());
				player.sendMessage("@mag@The boss drops some Blood Money.");
			}
			if (Misc.random(4000) == 5) { // Valius Tokens
				World.getWorld().getItemHandler().createGroundItem(player, 8800, location.getX(), location.getY(),
						location.getZ(), Misc.random(1, 2), player.getIndex());
				player.sendMessage("@mag@The monster drops some Valius Tokens! (Upgrade Donator Rank with these.)");
				GlobalMessages.send(
						player.playerName + " has just received some Valius Tokens from " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(450) == 5) {// Mystery Box
				World.getWorld().getItemHandler().createGroundItem(player, 6199, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a Mystery Box!");
				GlobalMessages.send(
						player.playerName + " has just received a Mystery Box from " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}
			if (Misc.random(1500) == 5) {// Valius Mystery Box
				World.getWorld().getItemHandler().createGroundItem(player, 33269, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a Valius Mystery Box!");
				GlobalMessages.send(
						player.playerName + " has just received a Valius Mystery Box from " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}
			
			/*
			 * Hazelmeres signet 33769
			 */
			if (player.getItems().isWearingItem(33799) && npcLevel >= 100) {
			if (Misc.random(1, 30_000) == 5) {
					player.getItems().addItemUnderAnyCircumstance(33769, 1);
					GlobalMessages.send("" + player.playerName + " has received a Hazelmere's Signet as a drop! (1:30,000 chance)", GlobalMessages.MessageType.LOOT);
				}
			}
			
			/*
			 * Christmas
			 */
			int boss_orn_amt = Misc.random(5, 25);
			int other_orn_amt = Misc.random(1, 5);
			int fboss_orn_amt = Misc.random(3, 13);
			int fother_orn_amt = Misc.random(1, 3);
			
			if (IntStream.of(Points.bosses).anyMatch(id -> id == npc.npcType)) {
				if (player.summonId == 33963) {
					player.getItems().addItemUnderAnyCircumstance(33962, boss_orn_amt);
					player.sendMessage("You receive an extra " + boss_orn_amt + " Ornaments while Santa follows you.");
				}
				if (player.summonId == 33966) {
					player.getItems().addItemUnderAnyCircumstance(33962, fboss_orn_amt);
					player.sendMessage("You receive an extra " + fboss_orn_amt + " Ornaments while Frosty follows you.");
				}
				player.getItems().addItemUnderAnyCircumstance(33962, Misc.random(5, 25));
			} else {
				if (player.summonId == 33963) {
				player.getItems().addItemUnderAnyCircumstance(33962, other_orn_amt);
				player.sendMessage("You receive an extra " + other_orn_amt + " Ornaments while Santa follows you.");
				}
				if (player.summonId == 33966) {
					player.getItems().addItemUnderAnyCircumstance(33962, fother_orn_amt);
					player.sendMessage("You receive an extra " + fother_orn_amt + " Ornaments while Frosty follows you.");
				}
				player.getItems().addItemUnderAnyCircumstance(33962, Misc.random(1, 5));
			}
			
			
/*			if (Misc.random(100) == 5) {// Chocolate Egg
				World.getWorld().getItemHandler().createGroundItem(player, 7928, location.getX(), location.getY(),
						location.getZ(), 1, player.getIndex());
				player.sendMessage("@mag@The monster drops a Chocolate Egg");
				GlobalMessages.send(
						player.playerName + " has just received a Chocolate Egg from a " + npc.getName() + "!",
						GlobalMessages.MessageType.LOOT);
			}*/

		}

	}

}
