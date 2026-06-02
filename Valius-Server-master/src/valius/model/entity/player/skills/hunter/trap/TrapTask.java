package valius.model.entity.player.skills.hunter.trap;

import java.util.ArrayList;
import java.util.List;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.hunter.Hunter;
import valius.model.entity.player.skills.hunter.trap.Trap.TrapState;
import valius.util.RandomGen;

/**
 * Represents a single task which will run for each trap.
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 *
 */
public final class TrapTask implements CycleEvent {

	/**
	 * The player this task is dependant of.
	 */
	private final Player player;

	/**
	 * The random generator which will generate random values.
	 */
	private final RandomGen gen = new RandomGen();

	/**
	 * The trap this task is running for.
	 */
	public List<Trap> trap = new ArrayList<>();

	/**
	 * Constructs a new {@link TrapTask}.
	 * @param player	{@link #player}.
	 */
	public TrapTask(Player player) {
		this.player = player;
	}

	@Override
	public void update(CycleEventContainer container) {
		if(Hunter.GLOBAL_TRAPS.get(player) == null || !Hunter.GLOBAL_TRAPS.get(player).getTask().isPresent() || Hunter.GLOBAL_TRAPS.get(player).getTraps().isEmpty()) {
			container.stop();
			return;
		}
		
		for(Trap trap : trap) {
			boolean withinDistance = player.getHeight() == trap.getObject().getHeight() && Math.abs(player.getX() - trap.getObject().getX()) <= 15 && Math.abs(player.getY() - trap.getObject().getY()) <= 15;
			if(!withinDistance && !trap.isAbandoned()) {
				Hunter.abandon(player, trap, false);
			}
		}

	}
	
	@Override
	public void execute(CycleEventContainer container) {
		trap.clear();
		if (trap.isEmpty()) {
			TrapProcessor tp = Hunter.GLOBAL_TRAPS.get(player);
			if(tp != null)
				trap.addAll(tp.getTraps());
		}
		Trap trap = gen.random(this.trap);

		if(!Hunter.getTrap(player, trap.getObject()).isPresent() || !trap.getState().equals(TrapState.PENDING)) {
			return;
		}

		trap.onSequence(container);
	}
}
