package valius.content.quest.impl;

import lombok.extern.slf4j.Slf4j;
import valius.clip.WorldObject;
import valius.content.quest.Quest;
import valius.content.quest.QuestInfo;
import valius.model.NamedValueMap;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.items.Item;

@Slf4j
@QuestInfo(
		amountOfStages = 10, 
		questIdentifier = 0, 
		questTabDisplay = "Example Quest"
		)

public class ExampleQuest extends Quest {

	@Override
	public NamedValueMap getDefaultVars() {
		NamedValueMap vals = new NamedValueMap();
		vals.add("testItem", 1);
		return vals;
	}

	@Override
	public void objectClicked(Player player, int option, WorldObject globalObject) {
		log.info("Object click: {}, {} ", option, globalObject);
	}

	@Override
	public void clickItem(Player player, int interfaceId, int option, Item item) {
		log.info("Item click: {}, {}, {}", option, interfaceId, item);
		
	}

	@Override
	public void clickInterface(Player player, int button) {
		log.info("Button click: {}", button);
		
	}

	@Override
	public void clickNpc(Player player, int option, NPC npc) {
		log.info("Npc click [{}]: {}, {}", option, npc.npcType, npc.getLocation());
	}

	@Override
	public void itemOnItem(Player player, Item first, Item second) {
		log.info("Item on Item: {}, {}", first, second);
		
	}

	@Override
	public void itemOnNpc(Player player, Item item, NPC npc) {
		log.info("Item on NPC: {}, {}", npc.npcType, item);
		
		
	}

	@Override
	public void itemOnObject(Player player, Item item, WorldObject object) {
		log.info("Item on Object: {}, {}", item, object);
		
	}

	@Override
	public void itemOnPlayer(Player player, Item item, Player otherPlayer) {
		log.info("Item on Player: {}, {}", otherPlayer.getName(), item);
		
	}

	@Override
	public void killedNpc(Player player, NPC npc) {
		log.info("Kill NPC: {}, {}", npc.npcType, npc.getLocation());
		
	}

	@Override
	public void regionChange(Player player) {
		log.info("Region change: {}", player.getLocation());
		
	}

}
