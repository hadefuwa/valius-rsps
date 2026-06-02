package valius.content.prestige;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.SkillExperience;
import valius.model.entity.player.skills.Skill;

public class PrestigeSkills {

	public Player player;

	public PrestigeSkills(Player player) {
		this.player = player;
	}

	public final int MAX_PRESTIGE = 10;

	public int points = 1; // This is the base prestige points given

	public void openPrestige() { // Refreshes all text lines before showing the interface - Looks better
		for (int j = 0; j < 22; j++) {
			player.getPA().sendFrame126(""+player.prestigeLevel[j]+"", 37400 + j); // Update Current Prestige on interface
		}
		registerClick(0);
		player.getPA().showInterface(37300);
	}

	public void openShop() {
		player.sendMessage("@blu@ You have "+player.getPrestigePoints()+" prestige points.");
		player.getShops().openShop(120);
	}

	public void registerClick(int i) {
		player.prestigeNumber = i;
		player.currentPrestigeLevel = player.prestigeLevel[player.prestigeNumber];
		player.canPrestige = (player.getSkills().getActualLevel(Skill.forId(i)) == 99) ? true : false; //Update global canPrestige boolean
		String canPrestige = ((player.canPrestige == true) ? "@gre@Yes" : "@red@No"); // String version for interface Yes or No
		player.getPA().sendFrame126(Config.SKILL_NAME[player.prestigeNumber], 37307); // Update Skill Name
		player.getPA().sendFrame126("Current Prestige: @whi@"+player.currentPrestigeLevel, 37308); // Update Current Prestige in box
		player.getPA().sendFrame126("Reward: @whi@"+(points + ((player.currentPrestigeLevel + 1)))+" Points", 37309); // Update Reward
		player.getPA().sendFrame126("Can Prestige: "+ canPrestige, 37390); // Update If you can prestige
	}

	public void prestige() {
		if (player.currentPrestigeLevel == MAX_PRESTIGE) { // Change to prestige master
			player.sendMessage("You are the max prestige level in this skill!");
			return;
		}
		if (player.prestigeNumber <= 6 && player.getItems().isWearingItems()) { // Change to prestige master
			player.getDH().sendNpcChat1("You must remove your equipment to prestige this stat.", 2989, "Ak-Haranu");
			return;
		}
		player.canPrestige = (player.getSkills().getActualLevel(Skill.forId(player.prestigeNumber)) == 99) ? true : false; //Update global canPrestige boolean
		if (player.canPrestige) { // If the skill is 99
			if (player.VERIFICATION == 0) { // Verification Check
				player.sendMessage("@red@Please click prestige again to confirm prestiging of the "+Config.SKILL_NAME[player.prestigeNumber]+" skill.");
				player.VERIFICATION++;
				return;
			}
			player.VERIFICATION = 0;
			if (player.prestigeNumber != 3) { // If not Hitpoints
				player.getSkills().setLevel(1, Skill.forId(player.prestigeNumber));
				player.getSkills().setExperience(0, Skill.forId(player.prestigeNumber));
				player.getPA().setSkillLevel(player.prestigeNumber, 1, player.getPA().getXPForLevel(1));
				player.getPA().refreshSkill(player.prestigeNumber); // Refresh skills
			} else { // Hitpoints should be 10
				player.getSkills().setLevel(10, Skill.forId(player.prestigeNumber));
				player.getSkills().setExperience(SkillExperience.getExperienceForLevel(10), Skill.forId(player.prestigeNumber));
				player.getPA().setSkillLevel(player.prestigeNumber, 10, player.getPA().getXPForLevel(10));
				player.getPA().refreshSkill(player.prestigeNumber); // Refresh skills
			}
			if (player.prestigeNumber <= 6) {
				player.combatLevel = player.calculateCombatLevel();
				player.getPA().sendFrame126("Combat Level: " + player.combatLevel + "", 3983);
			}
			player.prestigePoints+= points + ((player.currentPrestigeLevel + 1));
			player.prestigeLevel[player.prestigeNumber] += 1;
			registerClick(player.prestigeNumber);
			player.getPA().sendFrame126(""+player.prestigeLevel[player.prestigeNumber]+"", 37400 + player.prestigeNumber); // Update Current Prestige on interface
		} else {
			player.sendMessage("You cannot prestige "+Config.SKILL_NAME[player.prestigeNumber]+" you need to gain "+ (99 -  player.getSkills().getActualLevel(Skill.forId(player.prestigeNumber))) +" more "+Config.SKILL_NAME[player.prestigeNumber]+" levels.");
		}
	}

	public boolean prestigeClicking(Player c, int id) {
		if (id != 37391)
			player.VERIFICATION = 0;
		switch (id) {
		case 37311:
			registerClick(0);
			return true;
		case 37312:
			registerClick(1);
			return true;
		case 37313:
			registerClick(2);
			return true;
		case 37314:
			registerClick(3);
			return true;
		case 37315:
			registerClick(4);
			return true;
		case 37316:
			registerClick(5);
			return true;
		case 37317:
			registerClick(6);
			return true;
		case 37318:
			registerClick(7);
			return true;
		case 37319:
			registerClick(8);
			return true;
		case 37320:
			registerClick(9);
			return true;
		case 37321:
			registerClick(10);
			return true;
		case 37322:
			registerClick(11);
			return true;
		case 37323:
			registerClick(12);
			return true;
		case 37324:
			registerClick(13);
			return true;
		case 37325:
			registerClick(14);
			return true;
		case 37326:
			registerClick(15);
			return true;
		case 37327:
			registerClick(16);
			return true;
		case 37328:
			registerClick(17);
			return true;
		case 37329:
			registerClick(18);
			return true;
		case 37330:
			registerClick(19);
			return true;
		case 37331:
			registerClick(20);
			return true;
		case 37332:
			registerClick(21);
			return true;
		case 37391:
			prestige();
			return true;
		case 37302:
			player.getPA().closeAllWindows();
			return true;

		}
		return false;
	}
}