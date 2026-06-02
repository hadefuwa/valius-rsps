package valius.model.entity.player.commands.admin;

import valius.content.ReferralEvent;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

/**
 * 
 * @author Divine | Jan. 31, 2019 , 5:41:04 a.m.
 *
 */

public class Re extends Command {

	@Override
	public void execute(Player player, String input) {
		
		String[] args = input.split(" ");
		
		switch (args[0]) {
		
		case "":
			player.sendMessage("@red@Usage: ::re set ID");
			player.sendMessage("@red@Usage: ::re end/check/set");
			break;
			
		case "end":
			ReferralEvent.get().end();
			player.sendMessage("@red@Referral Event has been turned off");
			break;
			
		case "check":
			player.sendMessage("Referral ID: " + ReferralEvent.get().getReferralId());
			break;
			
		case "set":
			ReferralEvent.get().setRefferalId(args[1]);
			ReferralEvent.get().start();
			player.sendMessage("Referral set: " + args[1]);
			break;
		case "resetmac":
			ReferralEvent.get().resetMac();
			break;
		}
	}
}
