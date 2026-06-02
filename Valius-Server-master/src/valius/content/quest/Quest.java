package valius.content.quest;

import lombok.extern.slf4j.Slf4j;
import valius.clip.WorldObject;
import valius.model.NamedValueMap;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.items.Item;

@Slf4j
public abstract class Quest {
	
	private QuestInfo info;
	
	public Quest() {
		if(getClass().isAnnotationPresent(QuestInfo.class))
			info = getClass().getAnnotation(QuestInfo.class);
		else
			log.error("{} is missing QuestInfo annotation", getClass().getName());
	}
	
	public long getIdentifier() {
		return info.questIdentifier();
	}

	public String getName() {
		return info.questTabDisplay();
	}
	
	public void setAttribute(Player player, String key, Object value) {
		player.getQuestManager().setAttribute(this.getClass(), key, value);
	}
	
	public <T> T getAttribute(Player player, String key, T defaultVal) {
		return player.getQuestManager().getAttribute(this.getClass(), key, defaultVal);
	}

	public abstract NamedValueMap getDefaultVars();

	public abstract void objectClicked(Player player, int option, WorldObject globalObject);

	public abstract void clickItem(Player player, int interfaceId, int option, Item item);

	public abstract void clickInterface(Player player, int button);

	public abstract void clickNpc(Player player, int option, NPC npc);
	
	public abstract void itemOnItem(Player player, Item first, Item second);
	
	public abstract void itemOnNpc(Player player, Item item, NPC npc);

	public abstract void itemOnObject(Player player, Item item, WorldObject object);
	
	public abstract void itemOnPlayer(Player player, Item item, Player otherPlayer);
	
	public abstract void killedNpc(Player player, NPC npc);
	
	public abstract void regionChange(Player player);
	
}
