package valius.content.quest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import valius.clip.WorldObject;
import valius.model.NamedValueMap;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.util.Misc;

@Slf4j
public class QuestManager {
	
	private static final String QUEST_IMPL_DIR = "valius.content.quest.impl";
	private static final String QUEST_SAVE_DIR = "./data/quests/";
	private static List<Quest> defaultQuests = Lists.newArrayList();
	private static Type listType = new TypeToken<Map<Long, NamedValueMap>>() {}.getType();;
	private static Gson gson;
	
	public static void init() {
		File questDir = new File(QUEST_SAVE_DIR);
		questDir.mkdirs();
		gson = new GsonBuilder().setPrettyPrinting().create();
		defaultQuests.clear();
		try {
			Misc.getClasses(QUEST_IMPL_DIR)
			.stream()
			.filter(Objects::nonNull)
			.filter(c -> !c.isAnonymousClass())
			//.filter(c -> c.equals(CombatScript.class))
			.forEach(c -> {
				if(!c.isAnnotationPresent(QuestInfo.class)) {
					log.warn("{} is missing QuestInfo annotation!", c.getCanonicalName());
					return;
				}				
				try {
					Quest quest = (Quest) c.newInstance();
					QuestInfo settings = quest.getClass().getAnnotation(QuestInfo.class);

					log.info("Loading quest {} [{}]", settings.questTabDisplay(), settings.questIdentifier());
					defaultQuests.add(quest);
					
				} catch(Exception ex) {
					log.warn("Failed to initialize {}", c.getName());
				}
			});

			log.info("Loaded " + defaultQuests.size() + " quests!");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private Stream<Quest> getQuests(){
		return defaultQuests.stream().filter(quest -> map.keySet().contains(quest.getIdentifier()));
	}
	
	private static Quest getQuest(Class<? extends Quest> questClass) {
		Quest foundQuest = defaultQuests.stream().filter(quest -> quest.getClass().equals(questClass)).findFirst().orElse(null);
		return foundQuest;
	}
	
	
	private static long getQuestIdentifier(Class<? extends Quest> questClass) {
		Quest foundQuest = defaultQuests.stream().filter(quest -> quest.getClass().equals(questClass)).findFirst().orElse(null);
		if(foundQuest != null)
			return foundQuest.getIdentifier();
		return -1L;
	}
	

	private Map<Long, NamedValueMap> map = Maps.newConcurrentMap();
	private final Player player;
	
	public QuestManager(Player player) {
		this.player = player;
		load();
	}
	
	public void onObjectClick(int option, WorldObject globalObject) {
		getQuests().forEach(quest -> quest.objectClicked(player, option, globalObject));
	}
	
	public void onItemCombine(Item first, Item second) {
		getQuests().forEach(quest -> quest.itemOnItem(player, first, second));
	}
	
	public void onItemOnNpc(Item item, NPC npc) {
		getQuests().forEach(quest -> quest.itemOnNpc(player, item, npc));
	}
	
	public void onItemOnObject(Item item, WorldObject object) {
		getQuests().forEach(quest -> quest.itemOnObject(player, item, object));
	}
	
	public void onItemOnPlayer(Item item, Player player) {
		getQuests().forEach(quest -> quest.itemOnPlayer(player, item, player));
	}
	

	public void onNpcClick(int option, NPC npc) {
		getQuests().forEach(quest -> quest.clickNpc(player, option, npc));
	}
	
	public void onItemClick(int interfaceId, int option, Item item) {
		getQuests().forEach(quest -> quest.clickItem(player, interfaceId, option, item));
	}
	
	public void onInterfaceButton(int button) {
		getQuests().forEach(quest -> quest.clickInterface(player, button));
	}
	
	public void onNpcKilled(NPC npc) {
		getQuests().forEach(quest -> quest.killedNpc(player, npc));
	}
	
	public void onRegionChange() {
		getQuests().forEach(quest -> quest.regionChange(player));
	}

	public void sendQuests() {
		
	}
	
	public void startQuest(Class<? extends Quest> questClass) {
		Quest quest = getQuest(questClass);
		if(quest == null) {
			log.warn("Attempted to start a quest that doesn't exist! {}", questClass.getName());
			return;
		}
		if(map.containsKey(quest.getIdentifier())) {
			log.warn("{} attempted to start quest {} but has already started it!", player.getName().toLowerCase(), quest.getName());
			return;
		}
		NamedValueMap defaultVals = quest.getDefaultVars();
		defaultVals.add("started", true);
		map.put(quest.getIdentifier(), defaultVals);
	}
	

	public <T> T getAttribute(Class<? extends Quest> questClass, String key, T defaultVal) {
		NamedValueMap defaultVals = map.get(getQuestIdentifier(questClass));
		if(defaultVals != null)
			return defaultVals.get(key, defaultVal);
		return defaultVal;
	}
	
	public void setAttribute(Class<? extends Quest> questClass, String key, Object value) {
		NamedValueMap defaultVals = map.get(getQuestIdentifier(questClass));
		if(defaultVals != null)
			defaultVals.add(key, value);
	}
	
	public void complete(Class<? extends Quest> questClass) {
		NamedValueMap defaultVals = map.get(getQuestIdentifier(questClass));
		defaultVals.clear();
		defaultVals.add("completed", true);
	}
	
	public boolean hasCompleted(Class<? extends Quest> questClass) {
		NamedValueMap values = map.get(getQuestIdentifier(questClass));
		if(values != null) {
			return values.get("completed", false);
		}
		return false;
	}
	
	public boolean hasStarted(Class<? extends Quest> questClass) {
		NamedValueMap values = map.get(getQuestIdentifier(questClass));
		if(values != null) {
			return values.get("started", false) || values.get("completed", false);
		}
		return false;
	}
	
	
	public void load() {
		File questSave = new File(QUEST_SAVE_DIR + player.getName().toLowerCase() + ".json");
		if(questSave.exists()) {
			try(FileReader fr = new FileReader(questSave)){
				map = gson.fromJson(fr, listType);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		File questSave = new File(QUEST_SAVE_DIR + player.getName().toLowerCase() + ".json");
		if(questSave.exists()) {
			try(FileWriter fw = new FileWriter(questSave)){
				gson.toJson(map, fw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
