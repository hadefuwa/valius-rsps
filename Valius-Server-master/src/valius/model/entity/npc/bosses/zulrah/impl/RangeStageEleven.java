package valius.model.entity.npc.bosses.zulrah.impl;

import java.util.Arrays;

import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.bosses.zulrah.DangerousEntity;
import valius.model.entity.npc.bosses.zulrah.DangerousLocation;
import valius.model.entity.npc.bosses.zulrah.SpawnDangerousEntity;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.bosses.zulrah.ZulrahLocation;
import valius.model.entity.npc.bosses.zulrah.ZulrahStage;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;

public class RangeStageEleven extends ZulrahStage {

	private int finishedAttack;

	public RangeStageEleven(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || zulrah.getNpc() == null || zulrah.getNpc().isDead || player == null || player.isDead
				|| zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		int ticks = container.getTotalTicks();
		if (zulrah.getNpc().totalAttacks >= 5 && finishedAttack == 0) {
			finishedAttack = ticks;
			zulrah.getNpc().attackTimer = 20;
			zulrah.getNpc().setFacePlayer(false);
			CycleEventHandler.getSingleton().addEvent(player, new SpawnDangerousEntity(zulrah, player, Arrays.asList(DangerousLocation.values()), DangerousEntity.TOXIC_SMOKE, 40),
					1);
		}
		if (finishedAttack > 0) {
			zulrah.getNpc().setFacePlayer(false);
			if (ticks - finishedAttack == 18) {
				zulrah.getNpc().setFacePlayer(false);
				zulrah.getNpc().totalAttacks = 0;
				zulrah.changeStage(2, CombatType.MELEE, ZulrahLocation.NORTH);
				container.stop();
			}
		}
	}
}
