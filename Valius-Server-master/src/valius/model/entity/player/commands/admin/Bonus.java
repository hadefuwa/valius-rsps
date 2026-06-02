package valius.model.entity.player.commands.admin;

import valius.Config;
import valius.discord.DiscordBot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * Executing bonus events by {String input}
 * 
 * @author Matt
 */

public class Bonus extends Command {

	public void execute(Player player, String input) {
		
		switch (input) {
		case "":
			player.sendMessage("@red@Usage: ::bonus xp, vote, pc, pkp, drops");
			break;
		
		case "xp":
			Config.BONUS_EVENT = Config.BONUS_EVENT ? false : true;
			GlobalMessages.send(player.playerName+" has " + (Config.BONUS_EVENT ? "enabled" : "disabled") + " DOUBLE XP!", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", player.playerName+ " has " + (Config.BONUS_EVENT ? "enabled" : "disabled") + " DOUBLE XP!");
			break;

		case "vote":
			Config.DOUBLE_VOTE_INCENTIVES = Config.DOUBLE_VOTE_INCENTIVES ? false : true;
			GlobalMessages.send(player.playerName+" has " + (Config.DOUBLE_VOTE_INCENTIVES ? "enabled" : "disabled") + " DOUBLE VOTE REWARDS!", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", player.playerName+ " has " + (Config.DOUBLE_VOTE_INCENTIVES ? "enabled" : "disabled") + " DOUBLE VOTE REWARDS");
			break;

		case "pc":
			Config.BONUS_PC = Config.BONUS_PC ? false : true;
			GlobalMessages.send(player.playerName+" has " + (Config.BONUS_PC ? "enabled" : "disabled") + " BONUS PC POINTS!", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", player.playerName+ " has " + (Config.BONUS_PC ? "enabled" : "disabled") + " BONUS PC POINTS!");
			break;

		case "pkp":
			Config.DOUBLE_PKP = Config.DOUBLE_PKP ? false : true;
			GlobalMessages.send(player.playerName+" has " + (Config.DOUBLE_PKP ? "enabled" : "disabled") + " DOUBLE PKP!", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", player.playerName+ " has " + (Config.DOUBLE_PKP ? "enabled" : "disabled") + " DOUBLE PKP!");
			break;

		case "drops":
			Config.DOUBLE_DROPS = Config.DOUBLE_DROPS ? false : true;
			GlobalMessages.send(player.playerName+" has " + (Config.DOUBLE_DROPS ? "enabled" : "disabled") + " DOUBLE DROPS!", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", player.playerName+ " has " + (Config.DOUBLE_DROPS ? "enabled" : "disabled") + " DOUBLE DROPS!");
			break;	

		case "superior":
			Config.superiorSlayerActivated = Config.superiorSlayerActivated ? false : true;
			GlobalMessages.send("Superior slayer is now " + (Config.superiorSlayerActivated ? "enabled" : "disabled") + ".", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", "Superior slayer is now " + (Config.superiorSlayerActivated ? "enabled" : "disabled") + ".");
			break;

		case "pursuit":
			Config.wildyPursuit = Config.wildyPursuit ? false : true;
			GlobalMessages.send("Wildy Pursuit is now " + (Config.wildyPursuit ? "enabled" : "disabled") + ".", GlobalMessages.MessageType.EVENT);
			DiscordBot.sendMessage("general", "Wildy Pursuit is now " + (Config.wildyPursuit ? "enabled" : "disabled") + ".");
			break;		
		}
	}

}
