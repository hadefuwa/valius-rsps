package valius.content;

import valius.content.achievement.AchievementTier;
import valius.model.entity.player.Player;

public class CompCapeRequirements {

	public static void executeRequirements(Player c) {
		
	int counter = 8144;
	c.getPA().sendFrame126("Completionist Requirements", counter++);
	c.getPA().sendFrame126("", counter++);
	counter++;
	
	c.getPA().sendFrame126("Untrimmed Requirements", counter++);
	//Check for Max skills
	if (c.maxRequirements(c)) {
		c.getPA().sendFrame126("@gre@ Level 99 in all skills", counter++);
	} else {
		c.getPA().sendFrame126("@red@ Level 99 in all skills", counter++);
	}
	
	//Check for Diaries
	if (c.diaryAmount <= 0) {
		c.getPA().sendFrame126("@red@ Achievement Diaries", counter++);
	} else if (c.diaryAmount >= 1 && c.diaryAmount < 10) {
		c.getPA().sendFrame126("@yel@ Achievement Diaries", counter++);
	} else if (c.diaryAmount >= 10) {
		c.getPA().sendFrame126("@gre@ Achievement Diaries", counter++);
	}
	
	//Check for Achievements
	if (!c.getAchievements().completedTier(AchievementTier.TIER_1)) {
		c.getPA().sendFrame126("@red@ Achievements", counter++);
	} else if (c.getAchievements().completedTier(AchievementTier.TIER_1)) {
		c.getPA().sendFrame126("@yel@ Achievements", counter++);
	} else if (c.getAchievements().hasCompletedAll()) {
		c.getPA().sendFrame126("@gre@ Achievements", counter ++);
	}
	
	//Check for Chambers of Xeric KC
	if (c.totalRaidsFinished <= 0) {
		c.getPA().sendFrame126("@red@ 50 Chamber of Xeric raids complete", counter++);
	} else if (c.totalRaidsFinished >= 1 && c.totalRaidsFinished < 50) {
		c.getPA().sendFrame126("@yel@ 50 Chamber of Xeric raids complete", counter++);
	} else if (c.totalRaidsFinished >= 50) {
		c.getPA().sendFrame126("@gre@ 50 Chamber of Xeric raids complete", counter++);
	}
	
	//Check for Theatre of Blood KC
	if (c.totalTheatreFinished <= 0) {
		c.getPA().sendFrame126("@red@ 25 Theatre of Blood raids complete", counter++);
	} else if (c.totalTheatreFinished >= 1 && c.totalTheatreFinished < 25) {
		c.getPA().sendFrame126("@yel@ 25 Theatre of Blood raids complete", counter++);
	} else if (c.totalTheatreFinished >= 25) {
		c.getPA().sendFrame126("@gre@ 25 Theatre of Blood raids complete", counter++);
	}
	c.getPA().sendFrame126("", counter++);
	c.getPA().showInterface(8134);
}

}
