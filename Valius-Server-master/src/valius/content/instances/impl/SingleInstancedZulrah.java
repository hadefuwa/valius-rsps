package valius.content.instances.impl;

import valius.content.instances.SingleInstancedArea;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.world.World;

public class SingleInstancedZulrah extends SingleInstancedArea {

	public SingleInstancedZulrah(Player player, Boundary boundary, int height) {
		super(player, boundary, height);
	}

	@Override
	public void onDispose() {
		Zulrah zulrah = player.getZulrahEvent();
		if (zulrah.getNpc() != null) {
			NPCHandler.kill(zulrah.getNpc().npcType, height);
		}
		World.getWorld().getGlobalObjects().remove(11700, height);
		NPCHandler.kill(Zulrah.SNAKELING, height);
	}

}
