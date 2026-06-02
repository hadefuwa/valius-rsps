package valius.content.staff;

import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.net.packet.impl.Commands;

/**
 * Class handling the actions within the staff control
 * @author Matt - https://www.rune-server.org/members/matt%27/
 *
 * @date 13 okt. 2016
 */

public class StaffControl {

	/**
	 * Points to the username options are being used towards
	 */
	public static String username = "";

	public static boolean isUsingControl = false;

	/**
	 * Loads various possible options to perform on a player
	 * @param player
	 * @param option		The options we are going to load
	 */
	public static String[] options = { 
			"Kick", "Quick Jail", "Quick Ban", "Quick Mute", 
			"Teleport To", "Teleport To Me", "Move Home", "Move To Questioning",
			"Check Bank", "Check Inventory", "Check If Bank-Pin", "Check Stats", "Check Coordinates",
			"Back"
	};
	public static void loadOnPlayerOptions(Player player) {
		isUsingControl = true;
		emptyList(player);
		int line = 45005;
		for (int i = 0; i < options.length; i++) {
			player.getPA().sendFrame126("@or2@" + options[i], line);
			line++;
		}
	}

	public static void clickActions(Player player, int actionButton) {
		String split = "-", space = " ";
		Player chosen_player = PlayerHandler.getPlayer(username);
		if (!isUsingControl) {
			return;
		}
		switch (actionButton) {
		case 45005:
			Commands.executeCommand(player, "kick" + space + username, "helper");
			break;

		case 45006:
			Commands.executeCommand(player, "jail" + split + username + split + "1440" + split + "Quick jail 'staff-control'", "moderator");
			break;

		case 45007:
			Commands.executeCommand(player, "ban" + split + username + split + "1440" + split + "Quick ban 'staff-control'", "moderator");
			break;

		case 45008:
			Commands.executeCommand(player, "mute" + split + username + split + "1440" + split + "Quick mute 'staff-control'", "moderator");
			break;

		case 45009:
			Commands.executeCommand(player, "xteleto" + space + username, "moderator");
			break;

		case 45010:
			Commands.executeCommand(player, "teletome" + space + username, "moderator");
			break;

		case 45011:
			Commands.executeCommand(player, "unjail" + space + username, "moderator");
			break;

		case 45012:
			Commands.executeCommand(player, "questioning" + space + username, "moderator");
			break;

		case 45013:
			Commands.executeCommand(player, "checkbank" + space + username, "admin");
			break;

		case 45014:
			Commands.executeCommand(player, "checkinventory" + space + username, "moderator");
			break;

		case 45015:
			player.getDH().sendStatement(username + " "+ (chosen_player.getBankPin().getPin().length() > 0 ? "@gre@does@bla@" : "@red@does not@bla@") +" have a bank-pin set");
			break;

		case 45016:
			//TODO: check stats
			break;

		case 45017:
			player.sendMessage("Player Coordinates: X: " + chosen_player.getX() + " Y: " + chosen_player.getY() + " H: " + chosen_player.getHeight());
			break;

		case 45018:
			goBack(player);
			break;

		}
	}

	public static void emptyList(Player player) {
		int line = 45005;
		for (int i = 1; i < 200; i++) {
			player.getPA().sendFrame126("", line);
			line++;
		}
	}

	/**
	 * Handles the action of going back
	 * @param player
	 */
	public static void goBack(Player player) {
		emptyList(player);
		isUsingControl = false;
		player.getPA().sendFrame126("<col=0xFF981F>Players", 45254);
		int line = 45005;
		player.getPA().sendFrame126("Online: " + PlayerHandler.getRealPlayerCount(), 45001);
		for (Player p : PlayerHandler.players) {
			if (p == null)
				continue;
			player.getPA().sendFrame126("@or2@" + p.playerName + "  -  " + p.getHealth().getAmount() + "/" + p.getHealth().getMaximum(), line);
			line++;
		}
	}

}
