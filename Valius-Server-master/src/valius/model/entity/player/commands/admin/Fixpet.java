package valius.model.entity.player.commands.admin;

import java.util.Optional;

import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.Pets;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.commands.Command;

public class Fixpet extends Command {

	@Override
	public void execute(Player player, String input) {

		Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayer(input);
		optionalPlayer.ifPresent(otherPlayer -> {
			int playerPet = otherPlayer.summonId;
			if(playerPet > 0) {
				Pets pet = PetHandler.forItem(playerPet + 10000);
				if(pet != null) {
					int itemId = playerPet + 10000;
					otherPlayer.getItems().addItemUnderAnyCircumstance(itemId, 1);
					otherPlayer.summonId = 0;
					otherPlayer.hasFollower = false;
					player.sendMessage("Fixed pet for " + input);
					otherPlayer.sendMessage("Your pet has been fixed!");
				} else {
					player.sendMessage("That player does not have a valid pet " + playerPet);
				}
			} else {
				player.sendMessage("That player does not have a pet!");
			}
		});
		if(!optionalPlayer.isPresent())
			player.sendMessage("Player " + input + " is not online or invalid input");
	}

}
