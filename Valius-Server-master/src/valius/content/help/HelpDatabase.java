package valius.content.help;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateUtils;

import valius.Server;
import valius.model.entity.player.Player;
import valius.util.Misc;

/**
 * 
 * @author Jason MacKeigan
 * @date Nov 5, 2014, 04:39:16 AM
 */
public class HelpDatabase {

	/**
	 * The maximum amount requests allowed in the database at once
	 */
	private static final int REQUEST_LIMIT = 20;

	/**
	 * Static, singular instance of the HelpDatabase class
	 */
	private static HelpDatabase database = new HelpDatabase();

	/**
	 * A collection of all of the available requests in the game
	 */
	private List<HelpRequest> requests = new ArrayList<>();

	/**
	 * Adds a help request to the list of current open requests
	 * 
	 * @param request the request being added
	 */
	public void add(HelpRequest request) {
		if (Objects.nonNull(request)) {
			requests.add(request);
		}
	}

	/**
	 * View a certain request by clicking on a button.
	 * 
	 * @param player the player viewing the request
	 * @param buttonId the button id
	 */
	public void view(Player player, int buttonId) {
		Optional<Button> button = EnumSet.allOf(Button.class).stream().filter(b -> b.viewId == buttonId).findFirst();
		if (!button.isPresent()) {
			return;
		}
		if (requests.size() == 0) {
			player.sendMessage("There are currently no requests to view.");
			return;
		}
		if (requests.size() <= button.get().ordinal()) {
			player.sendMessage("There is no help request here, this is empty.");
			return;
		}
		HelpRequest request = requests.get(button.get().ordinal());
		if (Objects.isNull(request)) {
			player.sendMessage("The request you're trying to view cannot be found.");
			return;
		}
		player.getPA().sendFrame126(Misc.capitalize(request.getName()) + " [" + request.getDate() + "]", 59556);
		String[] output = WordUtils.wrap(request.getMessage(), 60).split("\n");
		for (int i = 0; i < output.length; i++) {
			player.getPA().sendFrame126(output[i], 59557 + i);
		}
		for (int i = output.length; i < 4; i++) {
			player.getPA().sendFrame126("", 59557 + i);
		}
	}

	/**
	 * Deletes a help request from the database by clicking a button
	 * 
	 * @param player the player deleting the request
	 * @param buttonId the button id
	 */
	public void delete(Player player, int buttonId) {
		Optional<Button> button = EnumSet.allOf(Button.class).stream().filter(b -> b.removeId == buttonId).findFirst();
		if (!button.isPresent()) {
			return;
		}
		if (requests.size() == 0) {
			player.sendMessage("There are currently no requests to remove.");
			return;
		}
		if (requests.size() <= button.get().ordinal()) {
			player.sendMessage("There is no help request here, this is empty.");
			return;
		}
		HelpRequest request = requests.get(button.get().ordinal());
		if (Objects.isNull(request)) {
			player.sendMessage("The request you're trying to delete cannot be found.");
			return;
		}
		Date future = DateUtils.addMinutes(request.getDate(), 1);
		future = DateUtils.addSeconds(future, 30);
		if (future.after(Server.getCalendar().getInstance().getTime())) {
			player.sendMessage("Help requests must remain alive for 1 minute and 30 seconds before deleting.");
			return;
		}
		requests.remove(request);
		openDatabase(player);
	}

	/**
	 * Updates all of the limits in the interfaces fairly efficiently
	 * 
	 * @param player the player requesting the elements be updated
	 */
	public void updateElements(Player player) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
		for (int i = 0; i < requests.size(); i++) {
			HelpRequest request = requests.get(i);
			player.getPA().sendString(Misc.capitalize(request.getName()) + " " + format.format(request.getDate()), 59573 + (i * 4));
			player.getPA().sendFrame171(0, 59571 + (i * 4));
		}
	}

	/**
	 * Opens the database (the interface) for the player requesting it
	 * 
	 * @param player the player requesting the database be opened
	 */
	public void openDatabase(Player player) {
		updateElements(player);
		for (int i = 0; i < 5; i++)
		player.getPA().sendFrame126("", 59556 + i);
		player.getPA().showInterface(59550);
	}

	/**
	 * Determines if the player is able to request help based on a predicate
	 * 
	 * @param player the player requesting help
	 * @return true if they can, otherwise false
	 */
	public boolean requestable(Player player) {
		if (requests.size() >= REQUEST_LIMIT) {
			player.sendMessage("We're currently experiencing a high volume of tickets right now.");
			player.sendMessage("You may have to submit a request at a later date.");
			return false;
		}
		/*if (requests.stream().anyMatch(unrequestable(player))) {
			player.sendMessage("You either have an open request, or someone on your IP does.");
			player.sendMessage("You cannot send a request until the current one is cleared.");
			return false;
		}*/
		return true;
	}

	/**
	 * Determines if the player is un-requestable based on some characteristics
	 * 
	 * @param player the player
	 * @return the predicate
	 */
	@SuppressWarnings("unused")
	private Predicate<HelpRequest> unrequestable(Player player) {
		return req -> req.getName().equals(player.playerName) || req.getProtocol().equals(player.connectedFrom);
	}

	/**
	 * Returns a single instance of the HelpDatabase class
	 * 
	 * @return the help database
	 */
	public static HelpDatabase getDatabase() {
		return database;
	}

	private enum Button {
		SLOT_1(59574, 59575), 
		SLOT_2(59578, 59580), 
		SLOT_3(59582, 59585), 
		SLOT_4(59586, 59590), 
		SLOT_5(59590, 59595), 
		SLOT_6(59594, 59600), 
		SLOT_7(59598, 59605), 
		SLOT_8(59602, 59610), 
		SLOT_9(59606, 59615), 
		SLOT_10(59610, 59620), 
		SLOT_11(59614, 59625), 
		SLOT_12(59618, 59630), 
		SLOT_13(59622, 59635), 
		SLOT_14(59626, 59640), 
		SLOT_15(59630, 59645), 
		SLOT_16(59634, 59650), 
		SLOT_17(59638, 59655), 
		SLOT_18(59642, 59660), 
		SLOT_19(59646, 59665), 
		SLOT_20(59648, 59670), 
		;

		private int viewId, removeId;

		private Button(int viewId, int removeId) {
			this.viewId = viewId;
			this.removeId = removeId;
		}
	}
}
