package valius.content.donations;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.util.Misc;

public class DonationFunctions {
	
	/**
	 * Calculates the amount of bonus tokens the player will get based on donation amount
	 * @param tokenCount The amount of tokens donated for
	 * @return The amount of bonus tokens to award
	 */
	public static int calculateBonusTokens(int tokenCount) {
		double bonus = tokenCount >= 100 ? 0.20 : tokenCount <= 10 ? tokenCount <= 5 ? 0 : 0.10 : 0.15; 
		return (int) (tokenCount >= 75 ? Math.ceil(bonus * tokenCount) : Math.round(bonus * tokenCount));
	}
	
	/**
	 * Gives the player extra rewards depending on donation amount
	 * @param player The player claiming the reward
	 * @param tokenCount The amount of tokens donated for (before bonus tokens are added)
	 */
	public static void giveBonusRewards(Player player, int tokenCount) {
		if (tokenCount >= 5) {
			player.getItems().addItemUnderAnyCircumstance(6199, 1);
			GlobalMessages.send(player.playerName + " has just received a FREE Mystery Box for donating $5+", GlobalMessages.MessageType.DONATION);
		}
		if (tokenCount >= 25) {
			player.getItems().addItemUnderAnyCircumstance(33269, 1);
			GlobalMessages.send(player.playerName + " has just received a FREE Valius Mystery Box for donating $25+", GlobalMessages.MessageType.DONATION);
		}
		if (tokenCount >= 50) {
			player.getItems().addItemUnderAnyCircumstance(13346, 1);
			GlobalMessages.send(player.playerName + " has just received a FREE Raid Mystery Box for donating $50+", GlobalMessages.MessageType.DONATION);
		}
		
		
		/*
		 * xp boost when donating
		 */
		if (tokenCount >= 1 && tokenCount <= 5) {
			player.bonusXpTime += 1800;
			player.sendMessage("@red@You receive 30 minutes of bonus XP for donating $1 to $5");
			}
			if (tokenCount >= 6 && tokenCount <= 10) {
				player.bonusXpTime += 3600;
				player.sendMessage("@red@You receive 1 hour of bonus XP for donating $6 to $10");
			}
			if (tokenCount >= 11 && tokenCount <= 20) {
				player.bonusXpTime += 7200;
				player.sendMessage("@red@You receive 2 hours of bonus XP for donating $11 to $20");
			}
			if (tokenCount >= 21 && tokenCount <= 50) {
				player.bonusXpTime += 18000;
				player.sendMessage("@red@You receive 5 hours of bonus XP for donating $21 to $50");
			}
			if (tokenCount >= 51 && tokenCount <= 149) {
				player.bonusXpTime += 54000;
				player.sendMessage("@red@You receive  15 hours of bonus XP for donating $51 to $150");
			}
			if (tokenCount >= 150) {
				player.bonusXpTime += 84000;
				player.sendMessage("@red@You receive  24 hours of bonus XP for donating more than $150");
			}
			
			/*
			 * Loyalty points when donating
			 */
			int loyaltyReward = tokenCount * 10;
			player.loyaltyPoints += loyaltyReward;
			player.sendMessage("You receive @blu@" + loyaltyReward + "</col> Loyalty points for donating." );
			
			GlobalMessages.send(player.playerName+ " has donated for "+ tokenCount +" Valius Tokens! Thank you!", GlobalMessages.MessageType.DONATION);
			
	}
}
