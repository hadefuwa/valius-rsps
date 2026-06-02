package valius.model.minigames.bounty_hunter.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import valius.event.CycleEventContainer;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.minigames.bounty_hunter.BountyHunter;
import valius.model.minigames.bounty_hunter.Target;
import valius.model.minigames.bounty_hunter.TargetEvent;
import valius.model.minigames.bounty_hunter.TargetState;
import valius.util.Misc;

/**
 * 
 * @author Jason MacKeigan
 * @date Nov 13, 2014, 2:27:05 PM
 */
public class TargetSelector extends TargetEvent {

	public TargetSelector(BountyHunter bountyHunter) {
		super(bountyHunter);
	}

	@Override
	public void execute(CycleEventContainer container) {
		BountyHunter bh = super.bountyHunter;
		Player player = bh.getPlayer();
		if (!isExecutable()) {
			container.stop();
			return;
		}
		Predicate<Player> viableTarget = t -> t != null && t != player && t.inWild() && !Boundary.isIn(t, Boundary.SAFEPK) && !t.getBH().hasTarget() && t.wildLevel > 0 && !t.getBH().getTargetState().isPenalized()
				&& !t.getBH().getTargetState().hasKilledRecently() && !player.getPlayerKills().killedRecently(t.connectedFrom)
				&& !t.getPlayerKills().killedRecently(player.connectedFrom) && !t.isInvisible() && !t.connectedFrom.equalsIgnoreCase(player.connectedFrom) && !t.inClanWars() && !t.getRights().isOrInherits(Right.ADMINISTRATOR);
		List<Player> possibleTargets = new ArrayList<>(1);
		for (int levelOffset = 0; levelOffset < 10; levelOffset++) {
			final int level = levelOffset;
			possibleTargets = PlayerHandler.nonNullStream().filter(viableTarget.and(t -> Misc.combatDifference(player, t) <= level)).collect(Collectors.toList());
			if (possibleTargets.size() > 0) {
				break;
			}
		}
		if (possibleTargets.size() <= 0) {
			return;
		}
		Optional<Player> randomTarget = Optional.of(possibleTargets.get(Misc.random(possibleTargets.size() - 1)));
		randomTarget.ifPresent(target -> {
			assignTarget(player, target);
			assignTarget(target, player);
			container.stop();
			return;
		});
	}

	@Override
	public void stop() {
		if (Objects.nonNull(bountyHunter.getPlayer())) {
			bountyHunter.setTargetState(TargetState.NONE);
		}
	}

	/**
	 * Determines if the selection event should be executed based on some conditions.
	 * 
	 * @return if true, the event will start. Otherwise, the event should come to a halt.
	 */
	public boolean isExecutable() {
		BountyHunter bh = super.bountyHunter;
		Player player = bh.getPlayer();
		if (Objects.isNull(bh) || Objects.isNull(player) || player.disconnected) {
			return false;
		}
		if (bh.getTargetState().hasKilledRecently() || bh.getTargetState().isPenalized() || player.isInvisible()
				|| !player.inWild() || player.inClanWars() || bh.hasTarget() || Boundary.isIn(player, Boundary.SAFEPK)) {
			return false;
		}
		return true;
	}

	private void assignTarget(Player player, Player target) {
		player.getBH().setTargetState(TargetState.SELECTED);
		player.getBH().setTarget(new Target(target.playerName));
		player.getBH().updateTargetUI();
		player.sendMessage("<col=FF0000>You've been assigned a target: " + Misc.capitalize(target.playerName) + "</col>");
		player.getPA().createPlayerHints(10, target.getIndex());
	}
}
