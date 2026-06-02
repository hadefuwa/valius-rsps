package valius.content.quest.dialogue;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import lombok.extern.slf4j.Slf4j;
import valius.content.quest.dialogue.impl.CloseDialogue;
import valius.content.quest.dialogue.impl.NpcDialogue;
import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.content.quest.dialogue.impl.PlayerDialogue;
import valius.content.quest.dialogue.impl.StatementDialogue;
import valius.model.entity.player.Player;

@Slf4j
public class DialogueChain {
	
	private static RuntimeTypeAdapterFactory<Dialogue> adapter = RuntimeTypeAdapterFactory
             .of(Dialogue.class, "classType")
             .registerSubtype(CloseDialogue.class, "CloseDialogue")
             .registerSubtype(PlayerDialogue.class, "PlayerDialogue")
             .registerSubtype(NpcDialogue.class, "NpcDialogue")
             .registerSubtype(StatementDialogue.class, "StatementDialogue")
             .registerSubtype(OptionDialogue.class, "OptionDialogue");

	public static DialogueChain builder() {
		return new DialogueChain();
	}
	private Map<Integer, Dialogue> chain = Maps.newConcurrentMap();
	private int base;
	
	public DialogueChain build() {
		chain.values().stream().forEach(dialogue -> dialogue.chain = this);
		return this;
	}
	
	public DialogueChain add(int id, Dialogue dialogue) {
		if(chain.containsKey(id)) {
			log.warn("DialogueChain has a conflicting id {}!", id);
		}
		if(chain.isEmpty())
			base = id;
		chain.put(id, dialogue);
		return this;
	}
	
	public void open(Player player) {
		chain.get(base).apply(player);
	}
	
	public Dialogue get(int next) {
		return Optional.ofNullable(chain.get(next)).orElse(new CloseDialogue());
	}
	
	public void encode(File file) {
		try(FileWriter fw = new FileWriter(file)){
			Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();
			gson.toJson(chain, fw);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static DialogueChain load(File file) {
		try(FileReader fr = new FileReader(file)){
			Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();

			Type listType = new TypeToken<Map<Integer, Dialogue>>() {}.getType();;
			DialogueChain chain = builder();
			chain.chain = gson.fromJson(fr, listType);
			return chain;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return builder();
	}

	
}
