package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.content.donations.DonateRequest;
import valius.content.donations.Donation;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.entity.player.commands.Command;

/**
 * Auto Donation System / https://EverythingRS.com
 * @author Genesis
 *
 */

public class Claim extends Command {

	@Override
	public void execute(Player player, String input) {

		DonateRequest request = new DonateRequest(player,
				items -> {
					for(Donation item : items) {
						if(player.disconnected)
							break;
						item.claim(player);
					}
					PlayerSave.saveGame(player);
					player.sendMessage("Thank you for donating!");
				},
				failStatus -> {
					switch(failStatus) {
					case CONNECT_ERROR:
						player.sendMessage("Failed to connect to site. Please report this to an admin!");
						break;
					case DATABASE_CONNECT_ERROR:
						player.sendMessage("Failed to connect to database. Please report this to an admin!");
						break;
					case NOTHING_TO_CLAIM:
						player.sendMessage("You have no items to claim.");
						break;
					default:
						break;
					
					}
				});
		request.begin();
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Checks for pending donations");
	}
}
