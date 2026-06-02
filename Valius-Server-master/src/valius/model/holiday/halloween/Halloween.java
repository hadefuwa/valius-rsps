package valius.model.holiday.halloween;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import valius.event.CycleEvent;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.holiday.Holiday;
import valius.util.Misc;
import valius.world.World;

public class Halloween extends Holiday {

	private HalloweenSearchGame searchGame = new HalloweenSearchGame();

	public Halloween(String name, Calendar start, Calendar end, CycleEvent event) {
		super(name, start, end, event);
	}
	
	int timer = 0;

	@SuppressWarnings("deprecation")
	@Override
	public void initializeHoliday() {
		NPCHandler.spawnNpc(5567, 3088, 3495, 0, 0, 0, 0, 0, 0);
		CycleEventHandler.getSingleton().addEvent(this, super.event, (int) Misc.toCycles(10, TimeUnit.MINUTES));
		searchGame.update();
		timer++;
		
		if (timer == 3) {
			searchGame.updateZombies();
			timer = 0;
		}
	}

	@Override
	public void finalizeHoliday() {
		System.out.println("Holiday Event finalized.");
		World.getWorld().getGlobalObjects().remove(searchGame.chest);
		for (int npcId : Arrays.asList(5567, 2527, 2528, 2529, 2530)) {
			NPC npc = NPCHandler.getNpc(npcId);
			if (npc == null) {
				continue;
			}
			npc.setX(0);
			npc.setY(0);
			npc.makeX = 0;
			npc.makeY = 0;
			npc.updateRequired = true;
		}
		for (Player player : PlayerHandler.players) {
			if (player == null) {
				continue;
			}
			Player client = player;
			World.getWorld().getGlobalObjects().updateRegionObjects(client);
		}
	}

	@Override
	public boolean completed(Player player) {
		return false;
	}

	@Override
	public int getMinimumStage() {
		return 0;
	}

	@Override
	public int getMaximumStage() {
		return 6;
	}

	@Override
	public boolean clickNpc(Player player, int type, int npcId) {
		switch (npcId) {
		case 5567:
			player.getDH().sendDialogues(505, npcId);
			return true;
		}
		return false;
	}

	@Override
	public boolean clickObject(Player player, int type, int objectId, int x, int y) {
		switch (objectId) {
		case HalloweenSearchGame.CHEST_ID:
			searchGame.receive(player, x, y);
			return true;
		}
		return false;
	}

	@Override
	public boolean clickButton(Player player, int buttonId) {
		int action = player.dialogueAction;
		if (player.getHolidayStages().getStage("Halloween") == 0) {
			if (buttonId == 9167 && action == 513) {
				System.out.println("working 1");
				player.getHolidayStages().setStage("Halloween", 1);
				System.out.println("working 2");
				player.getDH().sendDialogues(514, 5567);
				return true;
			}
			if (buttonId == 9168 && action == 513) {
				player.getHolidayStages().setStage("Halloween", 1);
				player.getDH().sendDialogues(515, 5567);
				return true;
			}
			if (buttonId == 9169 && action == 513) {
				player.getHolidayStages().setStage("Halloween", 1);
				player.getDH().sendDialogues(514, 5567);
				return true;
			}
		} else if (player.getHolidayStages().getStage("Halloween") >= 5) {
			if (buttonId == 9167 && action == 535) {
				player.getDH().sendDialogues(536, 5567);
				return true;
			}
			if (buttonId == 9168 && action == 535) {
				player.getDH().sendDialogues(537, 5567);
				return true;
			}
			if (buttonId == 9169 && action == 535) {
				player.getPA().closeAllWindows();
				return true;
			}
			action = 0;
		}
		return false;
	}

	@Override
	public boolean clickItem(Player player, int itemId) {
		if (itemId == 611) {
			if (player.getHolidayStages().getStage("Halloween") >= 4) {
				searchGame.operateLocator(player);
				return true;
			}
		}
		return false;
	}

	@Override
	public void receive(Player player) {
		for (int i = 0; i < 6; i++) {
			if (player.getItems().freeSlots() > 0) {
				player.getItems().addItem(9920 + i, 1);
			} else {
				player.getItems().sendItemToAnyTab(9920 + i, 1);
			}
		}
		player.getPA().closeAllWindows();
		player.nextChat = -1;
	}

	public HalloweenSearchGame getSearchGame() {
		return searchGame;
	}

}
