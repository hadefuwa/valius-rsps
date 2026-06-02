package valius.model.entity.player.commands.admin;

import java.util.stream.Collectors;

import com.google.common.primitives.Ints;

import valius.model.entity.player.EquipmentSlot;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Setequip extends Command {

	@Override
	public void execute(Player player, String input) {
		String[] parts = input.split(" ");
		try {
			EquipmentSlot slot = EquipmentSlot.valueOf(parts[0].toUpperCase());
			if(slot != null) {
				player.playerEquipment[slot.getIndex()] = Ints.tryParse(parts[1]);
				player.playerEquipmentN[slot.getIndex()] = 1;
				player.getItems().updateSlot(slot.getIndex());
			}
		} catch(Exception ex) { 

			player.sendMessage("Valid: " + EquipmentSlot.stream().map(slot2 -> slot2.name().toLowerCase()).collect(Collectors.joining(", ")));
		}
		
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
	}

}
