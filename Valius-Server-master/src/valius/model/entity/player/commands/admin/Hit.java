package valius.model.entity.player.commands.admin;

import com.google.common.primitives.Ints;

import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.commands.Command;

public class Hit extends Command {

	@Override
	public void execute(Player player, String input) {
		String[] parts = input.split(" ");

		int hitAmt = 0;
		int hitmark = 0;
		if(parts.length > 1) {
			hitAmt = Ints.tryParse(parts[0]);
			hitmark = Ints.tryParse(parts[1]);
		} else {
			hitAmt = Ints.tryParse(parts[0]);
		}
		
		player.appendDamage(hitAmt, Hitmark.byId(hitmark));
	}

}
