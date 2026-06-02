package valius.model.entity.player.commands.all;

import java.util.Map.Entry;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

import java.util.Optional;

/**
 * Shows a list of commands.
 * 
 * @author Emiel
 *
 */
public class Commands extends Command {

	@Override
	public void execute(Player c, String input) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		int counter = 8144;
		c.getPA().sendFrame126("@dre@Valius Commands", counter++);
		c.getPA().sendFrame126("", counter++);
		counter++; // 8146 is already being used
		counter = sendCommands(c, "all", counter);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("::Help - Sends a ticket to all ONLINE staff members", counter++);
		c.getPA().sendFrame126("::Vote - Sends you to the voting page", counter++);
		c.getPA().sendFrame126("::Reward 1 - Claims all Vote rewards", counter++);
		c.getPA().sendFrame126("::Mobile - A guide to play Valius on any mobile device", counter++);
		c.getPA().sendFrame126("::Starterguide - A guide for new players to learn the ropes", counter++);
		c.getPA().sendFrame126("::Donate - Sends you to the Donation page", counter++);
		c.getPA().sendFrame126("::Claim - Used to claim donation rewards", counter++);
		c.getPA().sendFrame126("::Dperks - Sends you to the Donation Info page", counter++);
		c.getPA().sendFrame126("::Discord - Sends you an Invite to our Discord", counter++);
		c.getPA().sendFrame126("::Highscores - Takes you to the highscores page", counter++);
		c.getPA().sendFrame126("::Forums - Sends you to the Forum main page", counter++);
		c.getPA().sendFrame126("::Explock - Locks XP gains", counter++);
		c.getPA().sendFrame126("::Toggledrop - Toggles the message when droping items", counter++);
		c.getPA().sendFrame126("::Home - Teleports you to the home area", counter++);
		c.getPA().sendFrame126("::Forums - Takes you to the Forums main page", counter++);
		c.getPA().sendFrame126("::Rules - Sends you to the Rules page", counter++);
		c.getPA().sendFrame126("::Staff - See all online staff members", counter++);
		c.getPA().sendFrame126("::Stuck - use this to send a message to ALL online staff", counter++);
		c.getPA().sendFrame126("::Stuckraids - Use this if oyu get stuck inside raids 1", counter++);
		c.getPA().sendFrame126("", counter++);
		c.getPA().sendFrame126("@dre@Donators Only", counter++);
		//noinspection UnusedAssignment
		counter = sendCommands(c, "donator", counter);
		c.getPA().showInterface(8134);
	}

	public int sendCommands(Player player, String rank, int counter) {
		for (Entry<String, Command> entry : valius.net.packet.impl.Commands.COMMAND_MAP.entrySet()) {
			if (entry.getKey().contains("." + rank.toLowerCase() + ".")) {
				if (entry.getValue().isHidden()) {
					continue;
				}
				String command = entry.getValue().getClass().getSimpleName().toLowerCase();
				if (entry.getValue().getParameter().isPresent()) {
					command += " @dre@" + entry.getValue().getParameter().get() + "@bla@";
				}
				String description = entry.getValue().getDescription().orElse("No description");
				player.getPA().sendFrame126("@blu@::" + command + "@bla@ - " + description, counter);
				counter++;
			}
		}
		return counter;
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Shows a list of all commands");
	}

}
