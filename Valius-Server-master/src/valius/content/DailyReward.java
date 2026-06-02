package valius.content;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import valius.Config;
import valius.content.bonus.DoubleExperience;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.util.Misc;

/*
 * This system gives players a reward once a day
 */

public class DailyReward {

	private Player p;// player object

	LocalDateTime now = LocalDateTime.now(); // gets servers date and time
	
	DateTimeFormatter time = DateTimeFormatter.ofPattern("H"); // gets hours
	
	YearMonth yearMonthObject = YearMonth.of(now.getYear(), now.getMonthValue());// gets current year and month
	YearMonth daysLastMonth;
	
	// gets current year and month
	
	int daysInMonth; // checks how many days are in the month
	int daysInLastMonth;// checks how many days are in the month
	
	int currentTime = Integer.parseInt(time.format(now));// gets current time of day

	int[] rewards = { 1500, 1, 5, 2000000, 1, 1, 1500, 1, 2000, 4000000, 20, 1500, 1, 250, 2000, 1 };// reward amounts
	
	int[] rewardItem = { 13307, 2528, 990, 995, 6199, 2528, 13307, 12748, 13307, 995, 1464, 13307, 2528, 2996, 13307, 22000 };//reward items
	
	
	
	
	

	public DailyReward(Player p) {// Constructor that sets players data
		this.p = p;
		if(now.getMonthValue() == 1) {
			daysLastMonth = YearMonth.of(now.getYear() - 1, now.getMonthValue());
		} else {
			daysLastMonth = YearMonth.of(now.getYear(), now.getMonthValue()-1);
		}
		daysInLastMonth = daysLastMonth.lengthOfMonth(); 
		daysInMonth = yearMonthObject.lengthOfMonth();
	}

	public void getDay() {//what interface should say
		
		p.getPA().sendFrame126("Welcome Back " + Misc.capitalizeJustFirst(p.playerName) + "!", 65004);
		
		//players online
		p.getPA().sendFrame126("@gre@" + PlayerHandler.getRealPlayerCount(), 65053);
		
		
		
		//changes the color of the "day #" text to green if that day is claimed
		if (p.dailyRewardCombo >= 1) {
			p.getPA().sendFrame126("@gre@Day 1", 65015);
		} if (p.dailyRewardCombo >= 2) {
			p.getPA().sendFrame126("@gre@Day 2", 65016);
		} if (p.dailyRewardCombo >= 3) {
			p.getPA().sendFrame126("@gre@Day 3", 65017);
		} if (p.dailyRewardCombo >= 4) {
			p.getPA().sendFrame126("@gre@Day 4", 65018);
		} if (p.dailyRewardCombo >= 5) {
			p.getPA().sendFrame126("@gre@Day 5", 65019);
		} if (p.dailyRewardCombo >= 6) {
			p.getPA().sendFrame126("@gre@Day 6", 65020);
		} if (p.dailyRewardCombo >= 7) {
			p.getPA().sendFrame126("@gre@Day 7", 65021);
		} if (p.dailyRewardCombo >= 8) {
			p.getPA().sendFrame126("@gre@Day 8", 65022);
		} if (p.dailyRewardCombo >= 9) {
			p.getPA().sendFrame126("@gre@Day 9", 65034);
		} if (p.dailyRewardCombo >= 10) {
			p.getPA().sendFrame126("@gre@Day 10", 65035);
		} if (p.dailyRewardCombo >= 11) {
			p.getPA().sendFrame126("@gre@Day 11", 65036);
		} if (p.dailyRewardCombo >= 12) {
			p.getPA().sendFrame126("@gre@Day 12", 65037);
		} if (p.dailyRewardCombo >= 13) {
			p.getPA().sendFrame126("@gre@Day 13", 65038);
		} if (p.dailyRewardCombo >= 14) {
			p.getPA().sendFrame126("@gre@Day 14", 65039);
		} if (p.dailyRewardCombo >= 15) {
			p.getPA().sendFrame126("@gre@Day 15", 65040);
		} if (p.dailyRewardCombo >= 16) {
			p.getPA().sendFrame126("@gre@Day 16", 65041);
		}
		
		//daily bonus
		
		if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			p.getPA().sendFrame126("@gre@Double Droprates", 65048);
		} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			p.getPA().sendFrame126("@gre@Double Slayer Points", 65048);
		} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			p.getPA().sendFrame126("@gre@Double PK Points", 65048);
		} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			p.getPA().sendFrame126("@gre@Double Minigame Points", 65048);
		} else if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ||
				Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
				Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			p.getPA().sendFrame126("@gre@Double Experience", 65048);
		}
		
		//Bonus XP (includes combined xp)
		if (!DoubleExperience.isDoubleExperience(p) && Config.BONUS_EVENT == false && Config.BONUS_WEEKEND == false) {
			p.getPA().sendFrame126("@gre@Bonus XP (+50%)", 65049);
		} else if (DoubleExperience.isDoubleExperience(p) && Config.BONUS_XP_WOGW == false && Config.BONUS_EVENT == false) {
			p.getPA().sendFrame126("@gre@Double XP (+100%)", 65049);
		} else if (Config.BONUS_XP_WOGW == true && DoubleExperience.isDoubleExperience(p) && Config.BONUS_EVENT == false) {
			p.getPA().sendFrame126("@gre@2.5x XP (+150%)", 65049);
		} else if (Config.BONUS_EVENT == true && Config.BONUS_XP_WOGW == false && !DoubleExperience.isDoubleExperience(p) ) {
			p.getPA().sendFrame126("@gre@Double XP (+100%)", 65049);
		} else if (Config.BONUS_EVENT == true && Config.BONUS_XP_WOGW == true && !DoubleExperience.isDoubleExperience(p) ) {
			p.getPA().sendFrame126("@gre@2.5x XP (+150%)", 65049);
		} else if (Config.BONUS_EVENT == true && Config.BONUS_XP_WOGW == false && DoubleExperience.isDoubleExperience(p) ) {
			p.getPA().sendFrame126("@gre@4x XP (+200%)", 65049);
		} else {
			p.getPA().sendFrame126("@red@None", 65049);
		}
		
		//double droprate toggle
		if (Config.DOUBLE_DROPS == true) {
			p.getPA().sendFrame126("@gre@ON", 65050);
		} else {
			p.getPA().sendFrame126("@red@OFF", 65050);
		}
		
		//Event Boss
		if(MonsterHunt.getCurrentLocation() != null){
			p.getPA().sendFrame126("@gre@"+MonsterHunt.getName(),65051);
		}else{
			p.getPA().sendFrame126("@red@None",65051);
		}
		
		//Double Votes
		if (Config.DOUBLE_VOTE_INCENTIVES == true) {
			p.getPA().sendFrame126("@gre@ON", 65052);
		} else {
			p.getPA().sendFrame126("@red@OFF", 65052);
		}
		
		
		
		//Well of goodwill bonus

		if (Config.BONUS_XP_WOGW == true && Config.BONUS_PC_WOGW == false && Config.DOUBLE_DROPS == false) {
			p.getPA().sendFrame126("@gre@50% Bonus XP", 65055);
		} else if (Config.BONUS_XP_WOGW == false && Config.BONUS_PC_WOGW == false && Config.DOUBLE_DROPS == true) {
			p.getPA().sendFrame126("@gre@2x Droprates", 65055);
		} else if (Config.BONUS_XP_WOGW == true && Config.BONUS_PC_WOGW == false && Config.DOUBLE_DROPS == true) {
			p.getPA().sendFrame126("@gre@Bonus XP + 2x Droprates", 65055);
		} else if (Config.BONUS_XP_WOGW == false && Config.BONUS_PC_WOGW == true && Config.DOUBLE_DROPS == false) {
			p.getPA().sendFrame126("@gre@+5 Pest Points", 65055);
		} else {
			p.getPA().sendFrame126("@red@None", 65055);
		}
		p.getPA().sendFrame126("@gre@Bonus XP + 2x Droprates", 65055);
		
		//timer to collect rewards
		if (now.getDayOfMonth() == p.lastDayClaimed) {
			p.getPA().showInterface(65000);
			p.getPA().sendFrame126("@yel@Next Reward: " + (24 - currentTime) + " hours", 65023);
			return;
		} else {
			p.getPA().showInterface(65000);
			p.getPA().sendFrame126("@gre@Claim reward now", 65023);

		}
	}

	public void getReward() {
		
		if (now.getDayOfMonth() == p.lastDayClaimed) {//if player already claimed todays rewards
			p.getPA().closeAllWindows();
			p.sendMessage("You have already claimed todays reward. Try again tomorrow.");
			return;
		}
		
		
		
		
		if(p.dailyRewardCombo >= 15) {//after finished 16 day challenge it resets...can add bonus for doing all 10 days here
			if(p.dailyRewardCombo > 15)
				p.dailyRewardCombo = 15;
			p.getItems().addItemUnderAnyCircumstance(rewardItem[p.dailyRewardCombo], rewards[p.dailyRewardCombo]);
			p.getItems().addItemUnderAnyCircumstance(13307, 1000);
			p.getItems().addItemUnderAnyCircumstance(33447, 1);
			p.getItems().addItemUnderAnyCircumstance(33961, 1);
			p.loyaltyPoints += 25;
			p.dailyRewardCombo = 0;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You receive @blu@25</col> Loyalty points for logging in today!");
			p.sendMessage("You have claimed all 16 days your combo has been reset");
			p.getPA().closeAllWindows();
			return;
		}
		
		//if the day is the first and the last time recieved reward was last day of last month
		if(p.lastDayClaimed == daysInLastMonth && now.getDayOfMonth() == 1) {
			p.getItems().addItemUnderAnyCircumstance(rewardItem[p.dailyRewardCombo], rewards[p.dailyRewardCombo]);
			p.getItems().addItemUnderAnyCircumstance(13307, 1000);
			p.getItems().addItemUnderAnyCircumstance(33447, 1);
			p.getItems().addItemUnderAnyCircumstance(33961, 1);
			p.loyaltyPoints += 25;
			p.dailyRewardCombo += 1;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You receive @blu@25</col> Loyalty points for logging in today!");
			p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
			p.getPA().closeAllWindows();
			return;
		}
		
		//if the last day the player claimed the reward is the day before todays date will add onto players combo
		if(p.lastDayClaimed == (now.getDayOfMonth()-1) && p.lastDayClaimed != daysInMonth) {
			p.getItems().addItemUnderAnyCircumstance(rewardItem[p.dailyRewardCombo], rewards[p.dailyRewardCombo]);
			p.getItems().addItemUnderAnyCircumstance(13307, 1000);
			p.getItems().addItemUnderAnyCircumstance(33447, 1);
			p.getItems().addItemUnderAnyCircumstance(33961, 1);
			p.loyaltyPoints += 25;
			p.dailyRewardCombo += 1;
			p.lastDayClaimed = now.getDayOfMonth();
			p.sendMessage("You receive @blu@25</col> Loyalty points for logging in today!");
			p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
			p.getPA().closeAllWindows();
			return;
		} 
		
		//if player missed a day the combo ends
		else {
		p.dailyRewardCombo = 0;
		p.getItems().addItemUnderAnyCircumstance(rewardItem[p.dailyRewardCombo], rewards[p.dailyRewardCombo]);
		p.getItems().addItemUnderAnyCircumstance(13307, 1000);
		p.getItems().addItemUnderAnyCircumstance(33447, 1);
		p.getItems().addItemUnderAnyCircumstance(33961, 1);
		p.loyaltyPoints += 25;
		p.lastDayClaimed = now.getDayOfMonth();
		p.sendMessage("You receive @blu@25</col> Loyalty points for logging in today!");
		p.sendMessage("You have now logged in for ("+p.dailyRewardCombo+")days in a row!");
		p.getPA().closeAllWindows();
		}
	}

}// END OF CLASS
