package valius.model.entity.npc.bosses.zulrah.impl;

import valius.event.CycleEventContainer;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.bosses.zulrah.ZulrahLocation;
import valius.model.entity.npc.bosses.zulrah.ZulrahStage;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.world.World;

public class SpawnZulrahStageZero extends ZulrahStage {

	public SpawnZulrahStageZero(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || player == null || player.isDead || zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		int cycle = container.getTotalTicks();
		if (cycle == 8) {
			player.getPA().sendScreenFade("", -1, 4);
			player.getPA().movePlayer(2268, 3069, zulrah.getInstancedZulrah().getHeight());
		}
		if (cycle == 13) {
			World.getWorld().getNpcHandler().spawnNpc(player, 2042, 2266, 3072, zulrah.getInstancedZulrah().getHeight(), -1, 500, 41, 500, 500, false, false);
			NPC npc = NPCHandler.getNpc(2042, 2266, 3072, zulrah.getInstancedZulrah().getHeight());
			if (npc == null) {
				player.sendMessage("Something went wrong, please contact staff.");
				container.stop();
				return;
			}
			zulrah.setNpc(npc);
			npc.setFacePlayer(false);
			npc.faceEntity(player.getIndex());
			npc.startAnimation(5073);
		}
		if (cycle == 18) {
			zulrah.changeStage(1, CombatType.RANGE, ZulrahLocation.NORTH);
			container.stop();
		}
	}

}
