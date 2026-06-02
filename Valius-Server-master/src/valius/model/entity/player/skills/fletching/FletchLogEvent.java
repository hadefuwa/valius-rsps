package valius.model.entity.player.skills.fletching;

import valius.content.SkillcapePerks;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.western_provinces.WesternDiaryEntry;
import valius.event.Event;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

public class FletchLogEvent extends Event<Player> {

	private FletchableLog fletch;

	private int amount;

	public FletchLogEvent(Player attachment, int ticks, FletchableLog fletch, int amount) {
		super("skilling", attachment, ticks);
		this.fletch = fletch;
		this.amount = amount;
	}

	@Override
	public void execute() {
		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			stop();
			return;
		}
		if (attachment.getSkills().getLevel(Skill.FLETCHING) < fletch.getLevel()) {
			attachment.sendMessage("You need a fletching level of " + fletch.getLevel() + " to do this.");
			stop();
			return;
		}
		if (!attachment.getItems().playerHasItem(fletch.getItemId())) {
			attachment.sendMessage("You have run out of logs.");
			stop();
			return;
		}
		amount--;
		attachment.startAnimation(1248);
		Achievements.increase(attachment, AchievementType.FLETCH, 1);
		attachment.getItems().deleteItem2(fletch.getItemId(), 1);
		switch (fletch.getProduct()) {
		case 70:
			if (Boundary.isIn(attachment, Boundary.CATHERBY_BOUNDARY)) {
				attachment.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.FLETCH_MAGIC_BOW);
			}
			break;
			
		case 54:
			if (Boundary.isIn(attachment, Boundary.GNOME_STRONGHOLD_BOUNDARY)) {
				attachment.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.FLETCH_OAK_SHORT_WEST);
			}
			break;
		}
		if (fletch.getProduct() == 52) {
			int amount = 15;
			if (fletch.getItemId() == 1521) {
				amount*= 2;
			}
			if (fletch.getItemId() == 1519) {
				amount*= 3;
			}
			if (fletch.getItemId() == 1517) {
				amount*= 4;
			}
			if (fletch.getItemId() == 1515) {
				amount*= 5;
			}
			if (fletch.getItemId() == 1513) {
				amount*= 6;
			}
			if (SkillcapePerks.FLETCHING.isWearing(attachment) || SkillcapePerks.isWearingMaxCape(attachment)) {
				amount+= 10;
			}
			attachment.getItems().addItem(fletch.getProduct(), amount);
		} else {
			attachment.getItems().addItem(fletch.getProduct(), 1);
		}
		
		attachment.getPA().addSkillXP(fletch.getExperience(), Skill.FLETCHING.getId(), true);
		if (amount <= 0) {
			stop();
			return;
		}
	}

	@Override
	public void stop() {
		super.stop();
		if (attachment != null && !attachment.disconnected && attachment.getSession() != null) {
			attachment.stopAnimation();
		}
	}
}
