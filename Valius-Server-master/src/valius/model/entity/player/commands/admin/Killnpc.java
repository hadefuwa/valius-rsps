package valius.model.entity.player.commands.admin;

import com.google.common.primitives.Ints;

import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.commands.Command;
import valius.model.entity.player.commands.owner.Npc;

public class Killnpc extends Command {

	@Override
	public void execute(Player player, String input) {
		try {
			int id = Ints.tryParse(input.trim());
			NPCHandler.nonNullStream()
			.filter(npc -> npc.withinDistance(player, 20))
			.filter(npc -> npc.npcType == id)
			.forEach(npc -> npc.appendDamage(player, npc.getHealth().getAmount(), Hitmark.HIT));;
		} catch(Exception ex) {
			NPCHandler.nonNullStream()
			.filter(npc -> npc.withinDistance(player, 20))
			.forEach(npc -> npc.appendDamage(player, npc.getHealth().getAmount(), Hitmark.HIT));;
		}
	}

}
