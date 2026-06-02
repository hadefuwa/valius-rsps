package valius.content.barrows;

import valius.event.Event;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;

public class BrotherEvent extends Event<Player> {

	public BrotherEvent(Player attachment, int ticks) {
		super(attachment, ticks);
	}

	@Override
	public void execute() {
		if (attachment == null || !attachment.getBarrows().getActive().isPresent() || attachment.getBarrows().getActive().get().isDefeated()) {
			stop();
			return;
		}
		attachment.getBarrows().getActive().ifPresent(brother -> {
			if (brother.getNPC() == null) {
				stop();
			} else {
				NPC npc = brother.getNPC();
				if (attachment.distanceToPoint(npc.getX(), npc.getY(), npc.getHeight()) > 20) {
					stop();
				}
			}
		});
		if ((getElapsedTicks() + 1) % 30 == 0) {
			attachment.getBarrows().drainPrayer();
		}
	}

	@Override
	public void stop() {
		if (attachment == null) {
			super.stop();
			return;
		}
		attachment.getBarrows().getActive().ifPresent(brother -> {
			brother.setActive(false);
			NPC npc = brother.getNPC();
			if (npc != null) {
				NPCHandler.npcs[npc.getIndex()] = null;
			}
		});
		super.stop();
	}

}
