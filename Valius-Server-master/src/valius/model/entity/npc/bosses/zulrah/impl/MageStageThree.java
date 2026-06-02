package valius.model.entity.npc.bosses.zulrah.impl;

import valius.event.CycleEventContainer;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.bosses.zulrah.ZulrahLocation;
import valius.model.entity.npc.bosses.zulrah.ZulrahStage;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;

public class MageStageThree extends ZulrahStage {

	public MageStageThree(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || zulrah.getNpc() == null || zulrah.getNpc().isDead || player == null || player.isDead
				|| zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		zulrah.getNpc().setFacePlayer(true);
		if (zulrah.getNpc().totalAttacks > 5) {
			player.getZulrahEvent().changeStage(4, CombatType.RANGE, ZulrahLocation.WEST);
			zulrah.getNpc().totalAttacks = 0;
			container.stop();
			return;
		}
	}

}
