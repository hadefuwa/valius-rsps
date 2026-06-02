package valius.model.entity.player.commands.all;


import java.util.Objects;
import java.util.Optional;

import valius.Config;
import valius.content.ReferralEvent;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * 
 * @author Divine | Jan. 31, 2019 , 5:41:21 a.m.
 *
 */

public class Referral extends Command {

	@Override
	public void execute(Player player, String input) {

		if (!ReferralEvent.get().isEventActive()) {
			player.sendMessage("There is currently no Referral event running.");
			return;
		}

		if (player.getMacAddress().isEmpty()) {
			player.sendMessage("An error has occured. Please talk to an Administrator.");
			return;
		}

		player.sendMessage("Your Referral ID: " + input);
		ReferralEvent.get().checkReferral(player, input);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Type ::Referral ID to receive your reward!");
	}
}
