package valius.model.entity.npc.bosses.zulrah;

import valius.event.CycleEvent;
import valius.model.entity.player.Player;

public abstract class ZulrahStage implements CycleEvent {

	protected Zulrah zulrah;

	protected Player player;

	public ZulrahStage(Zulrah zulrah, Player player) {
		this.zulrah = zulrah;
		this.player = player;
	}

}
