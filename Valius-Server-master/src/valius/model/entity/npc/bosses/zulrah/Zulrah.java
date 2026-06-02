package valius.model.entity.npc.bosses.zulrah;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import valius.content.instances.InstancedArea;
import valius.content.instances.InstancedAreaManager;
import valius.content.instances.SingleInstancedArea;
import valius.content.instances.impl.SingleInstancedZulrah;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.bosses.zulrah.impl.CreateToxicStageOne;
import valius.model.entity.npc.bosses.zulrah.impl.MageStageEight;
import valius.model.entity.npc.bosses.zulrah.impl.MageStageFive;
import valius.model.entity.npc.bosses.zulrah.impl.MageStageThree;
import valius.model.entity.npc.bosses.zulrah.impl.MeleeStageSix;
import valius.model.entity.npc.bosses.zulrah.impl.MeleeStageTen;
import valius.model.entity.npc.bosses.zulrah.impl.MeleeStageTwo;
import valius.model.entity.npc.bosses.zulrah.impl.RangeStageEleven;
import valius.model.entity.npc.bosses.zulrah.impl.RangeStageFour;
import valius.model.entity.npc.bosses.zulrah.impl.RangeStageNine;
import valius.model.entity.npc.bosses.zulrah.impl.RangeStageSeven;
import valius.model.entity.npc.bosses.zulrah.impl.SpawnZulrahStageZero;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.CombatType;
import valius.model.entity.player.commands.owner.Object;
import valius.server.data.SerializablePair;
import valius.util.Misc;
import valius.world.World;

public class Zulrah {

	/**
	 * The minion snake npc id
	 */
	public static final int SNAKELING = 2045;

	/**
	 * The relative lock for this event
	 */
	private final Object EVENT_LOCK = new Object();

	/**
	 * The player associated with this event
	 */
	private final Player player;

	/**
	 * The single instance of zulrah
	 */
	private SingleInstancedArea zulrahInstance;

	/**
	 * The boundary of zulrah's location
	 */
	public static final Boundary BOUNDARY = new Boundary(2248, 3059, 2283, 3084);

	/**
	 * The zulrah npc
	 */
	private NPC npc;

	/**
	 * The current stage of zulrah
	 */
	private int stage;

	/**
	 * Determines if the npc is transforming or not.
	 */
	private boolean transforming;

	/**
	 * The stopwatch for tracking when the zulrah npc fight starts.
	 */
	private Stopwatch stopwatch = Stopwatch.createUnstarted();

	/**
	 * A mapping of all the stages
	 */
	private Map<Integer, ZulrahStage> stages = new HashMap<>();

	/**
	 * Creates a new Zulrah event for the player
	 * 
	 * @param player the player
	 */
	public Zulrah(Player player) {
		this.player = player;
		stages.put(0, new SpawnZulrahStageZero(this, player));
		stages.put(1, new CreateToxicStageOne(this, player));
		stages.put(2, new MeleeStageTwo(this, player));
		stages.put(3, new MageStageThree(this, player));
		stages.put(4, new RangeStageFour(this, player));
		stages.put(5, new MageStageFive(this, player));
		stages.put(6, new MeleeStageSix(this, player));
		stages.put(7, new RangeStageSeven(this, player));
		stages.put(8, new MageStageEight(this, player));
		stages.put(9, new RangeStageNine(this, player));
		stages.put(10, new MeleeStageTen(this, player));
		stages.put(11, new RangeStageEleven(this, player));
	}

	public void initialize() {
		if (zulrahInstance != null) {
			InstancedAreaManager.getSingleton().disposeOf(zulrahInstance);
		}
		int height = InstancedAreaManager.getSingleton().getNextOpenHeight(BOUNDARY);
		zulrahInstance = new SingleInstancedZulrah(player, BOUNDARY, height);
		InstancedAreaManager.getSingleton().add(height, zulrahInstance);
		if (zulrahInstance == null) {
			player.getDH().sendStatement("The zulrah boss is currently being played by a high amount", "of players. Please try again shortly.");
			player.nextChat = -1;
			return;
		}
		stage = 0;
		stopwatch = Stopwatch.createStarted();
		player.getPA().removeAllWindows();
		player.getPA().sendScreenFade("Welcome to Zulrah's shrine", 1, 5);
		CycleEventHandler.getSingleton().addEvent(EVENT_LOCK, stages.get(0), 1);
	}

	/**
	 * Determines if the player is standing in a toxic location
	 * 
	 * @return true of the player is in a toxic location
	 */
	public boolean isInToxicLocation() {
		for (int x = player.getX() - 1; x < player.getX() + 1; x++) {
			for (int y = player.getY() - 1; y < player.getY() + 1; y++) {
				if (World.getWorld().getGlobalObjects().exists(11700, x, y, player.getHeight())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Stops the zulrah instance and concludes the events
	 */
	public void stop() {
		CycleEventHandler.getSingleton().stopEvents(EVENT_LOCK);
		if (stage < 1) {
			return;
		}
		stopwatch.stop();
		long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		long best = player.getBestZulrahTime();
		String duration = best < (60_000 * 60) ? Misc.toFormattedMS(time) : Misc.toFormattedHMS(time);
		player.sendMessage("Fight duration: <col=CC0000>" + duration + "</col> " + (time < player.getBestZulrahTime() ? "(New personal best)" : "") + ".");
		if (time < player.getBestZulrahTime()) {
			player.setBestZulrahTime(time);
		}
		SerializablePair<String, Long> globalBest = World.getWorld().getServerData().getZulrahTime();
		if (globalBest.getFirst() == null || globalBest.getSecond() == null || time < globalBest.getSecond() && globalBest.getSecond() != 0) {
			GlobalMessages.send(player.playerName+" has set the new best time against Zulrah with "+duration+"!", GlobalMessages.MessageType.NEWS);
			if (globalBest.getFirst() != null && globalBest.getSecond() != null) {
				GlobalMessages.send("The previous record was set by "+globalBest.getFirst() +" with a time of " + Misc.toFormattedMS(globalBest.getSecond()), GlobalMessages.MessageType.NEWS);
			}
			World.getWorld().getServerData().setSerializablePair(new SerializablePair<>(player.playerName, time));
		}
		zulrahInstance.onDispose();
		InstancedAreaManager.getSingleton().disposeOf(zulrahInstance);
		zulrahInstance = null;

	}

	public void changeStage(int stage, CombatType combatType, ZulrahLocation location) {
		this.stage = stage;
		CycleEventHandler.getSingleton().stopEvents(EVENT_LOCK);
		CycleEventHandler.getSingleton().addEvent(EVENT_LOCK, stages.get(stage), 1);
		if (stage == 1) {
			return;
		}
		int type = combatType == CombatType.MELEE ? 2043 : combatType == CombatType.MAGE ? 2044 : 2042;
		npc.startAnimation(5072);
		npc.attackTimer = 8;
		transforming = true;
		player.getCombat().resetPlayerAttack();
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				player.getCombat().resetPlayerAttack();
				if (container.getTotalTicks() == 2) {
					npc.requestTransform(6709);
				} else if (container.getTotalTicks() == 3) {
					npc.setX(location.getLocation().x);
					npc.setY(location.getLocation().y);
					player.rebuildNPCList = true;
				} else if (container.getTotalTicks() == 5) {
					npc.requestTransform(type);
					npc.startAnimation(5071);
					npc.faceEntity(player.getIndex());
					transforming = false;
					container.stop();
				}
			}

		}, 1);
	}

	/**
	 * Determines if any of the events alive contains the event lock
	 * 
	 * @return true if any of the events are active with this as the owner
	 */
	public boolean isActive() {
		return CycleEventHandler.getSingleton().isAlive(EVENT_LOCK);
	}

	/**
	 * The {@link SingleInstancedArea} object for this class
	 * 
	 * @return the zulrah instance
	 */
	public InstancedArea getInstancedZulrah() {
		return zulrahInstance;
	}

	/**
	 * The reference to zulrah, the npc
	 * 
	 * @return the reference to zulrah
	 */
	public NPC getNpc() {
		return npc;
	}

	/**
	 * The instance of the Zulrah {@link NPC}
	 * 
	 * @param npc the zulrah npc
	 */
	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	/**
	 * The stage of the zulrah event
	 * 
	 * @return the stage
	 */
	public int getStage() {
		return stage;
	}

	/**
	 * Determines if the NPC is transforming or not
	 * 
	 * @return {@code true} if the npc is in a transformation stage
	 */
	public boolean isTransforming() {
		return transforming;
	}

}
