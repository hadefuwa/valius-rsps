package valius.model.shops;

import valius.model.entity.player.Player;
import valius.util.Misc;

public enum Currency {
	COINS, BLOOD_MONEY, MAGE_ARENA_POINTS, ASSAULT_POINTS, RAIDS_POINTS, PK_POINTS, BOSS_POINTS, PVM_POINTS, SLAYER_POINTS, PRESTIGE_POINTS,
	SKILL_POINTS, DONATOR_TOKENS, BOUNTIES, ACHIEVEMENT_POINTS, MARKS_OF_GRACE, LOYALTY_POINTS,
	TOKKUL, VOTE_POINTS, THEATRE_POINTS, PUMPKINS, ORNAMENTS,
	;
	
	public int getPossible(Player player, int cost) {
		double costDbl = cost * 1.0;
		double amount = 0.0;
		switch(this) {
		case THEATRE_POINTS:
			amount = player.theatrePoints / costDbl;
			break;
		case PUMPKINS:
			amount = player.getItems().getItemAmount(1960) / costDbl;
			break;
		case ORNAMENTS:
			amount = player.getItems().getItemAmount(33962) / costDbl;
			break;
		case TOKKUL:
			amount = player.getItems().getItemAmount(6529) / costDbl;
			break;
		case VOTE_POINTS:
			//amount = player.getItems().getItemAmount(1464) / cost;
			amount = player.votePoints / costDbl;
			break;
		case LOYALTY_POINTS:
			amount = player.loyaltyPoints / costDbl;
			break;
		case ASSAULT_POINTS:
			amount = player.getShayPoints() / costDbl;
			break;
		case BLOOD_MONEY:
			amount = player.getItems().getItemAmount(13307) / costDbl;
			break;
		case BOSS_POINTS:
			amount = player.bossPoints / costDbl;
			break;
		case DONATOR_TOKENS:
			amount = player.getItems().getItemAmount(8800) / costDbl;
			break;
		case COINS:
			amount = player.getItems().getItemAmount(995) / costDbl;
			break;
		case MAGE_ARENA_POINTS:
			amount = player.getArenaPoints() / costDbl;
			break;
		case PK_POINTS:
			//amount = player.getItems().getItemAmount(2996) / costDbl;
			amount = player.pkp / costDbl;
			break;
		case PRESTIGE_POINTS:
			amount = player.prestigePoints / costDbl;
			break;
		case PVM_POINTS:
			amount = player.pvmPoints / costDbl;
			break;
		case RAIDS_POINTS:
			amount = player.raidPoints / costDbl;
			break;
		case SKILL_POINTS:
			amount = player.skillPoints / costDbl;
			break;
		case SLAYER_POINTS:
			amount = player.getSlayer().getPoints() / costDbl;
			break;
		case BOUNTIES:
			amount = player.getBH().getBounties() / costDbl;
			break;
		case MARKS_OF_GRACE:
			amount = player.getItems().getItemAmount(11849) / costDbl;
			break;
		case ACHIEVEMENT_POINTS:
			amount = player.getAchievements().getPoints() / costDbl;
			break;
		
		default:
			break;
		
		}
		return (int) Math.floor(amount);
	}
	
	public boolean removeAmount(Player player, int itemId, int amountOfItem, int costPerItem) {
		int totalCost = amountOfItem * costPerItem;
		System.out.println(getPossible(player, costPerItem));
		if(getPossible(player, costPerItem) < amountOfItem) {
			return false;
		}
		switch(this) {
		case THEATRE_POINTS:
			player.theatrePoints -= totalCost;
			break;
		case PUMPKINS:
			player.getItems().deleteItem2(1960, totalCost);
			break;
		case ORNAMENTS:
			player.getItems().deleteItem2(33962, totalCost);
			break;
		case TOKKUL:
			player.getItems().deleteItem2(6529, totalCost);
			break;
		case VOTE_POINTS:
			player.votePoints -= totalCost;
			break;
		case LOYALTY_POINTS:
			player.loyaltyPoints -= totalCost;
			break;
		case ASSAULT_POINTS:
			player.setShayPoints(player.getShayPoints() - totalCost);
			break;
		case BLOOD_MONEY:
			player.getItems().deleteItem2(13307, totalCost);
			break;
		case BOSS_POINTS:
			player.bossPoints -= totalCost;
			break;
		case DONATOR_TOKENS:
			player.getItems().deleteItem2(8800, totalCost);
			if(!(itemId >= 2697 && itemId <= 2700)) {
				player.amDonated += totalCost;
				player.updateRank();
			}
			break;
		case COINS:
			player.getItems().deleteItem2(995, totalCost);
			break;
		case MAGE_ARENA_POINTS:
			player.setArenaPoints(player.getArenaPoints() - totalCost);
			amountOfItem = player.getArenaPoints() / costPerItem;
			break;
		case PK_POINTS:
			player.pkp -= totalCost;
			break;
		case PRESTIGE_POINTS:
			player.prestigePoints -= totalCost;
			break;
		case PVM_POINTS:
			player.pvmPoints -= totalCost;
			break;
		case RAIDS_POINTS:
			player.raidPoints -= totalCost;
			break;
		case SKILL_POINTS:
			player.skillPoints -= totalCost;
			break;
		case SLAYER_POINTS:
			player.getSlayer().setPoints(player.getSlayer().getPoints()-  totalCost);
			break;
		case BOUNTIES:
			player.getBH().setBounties(player.getBH().getBounties() - totalCost);
			break;
		case MARKS_OF_GRACE:
			player.getItems().deleteItem2(11849, totalCost);
			break;
		case ACHIEVEMENT_POINTS:
			player.getAchievements().setPoints(player.getAchievements().getPoints() - totalCost);
			break;
		default:
			return false;
		
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return Misc.capitalizeJustFirst(name().replaceAll("_", " "));
	}
	
	public String toPlural(int amount) {
		String norm = toString().toLowerCase();
		if(norm.endsWith("s")) {
			norm = norm.substring(0, norm.length() - 1);
		}
		return norm + (amount > 1 && !toString().endsWith("y") ? "s" : "");
	}

	public int getLeft(Player player) {
		switch(this) {
		case VOTE_POINTS:
			return player.votePoints;
		case LOYALTY_POINTS:
			return player.loyaltyPoints;
		case ASSAULT_POINTS:
			return player.getShayPoints();
		case BOSS_POINTS:
			return player.bossPoints;
		case MAGE_ARENA_POINTS:
			return player.getArenaPoints();
		case PK_POINTS:
			return player.pkp;
		case PRESTIGE_POINTS:
			return player.prestigePoints;
		case PVM_POINTS:
			return player.pvmPoints;
		case RAIDS_POINTS:
			return player.raidPoints;
		case SKILL_POINTS:
			return player.skillPoints;
		case THEATRE_POINTS:
			return player.theatrePoints;
		case SLAYER_POINTS:
			return player.getSlayer().getPoints();
		case BOUNTIES:
			return player.getBH().getBounties();
		case ACHIEVEMENT_POINTS:
			return player.getAchievements().getPoints();
		default:
			return -1;
		}
	}
	

}
