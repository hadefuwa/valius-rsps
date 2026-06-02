package valius.model.entity.npc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NPCSpawns {

	/**
	 * All the npc spawns.
	 */
	@Getter
	private static ArrayList<NPCSpawns> spawns = Lists.newArrayList();
	
	/**
	 * Loading the NPCSpawns.
	 *
	 * @param file
	 *            The file to be read
	 * @throws java.io.FileNotFoundException
	 */
	public static void loadNPCSpawns() {
		Gson gson = new Gson();
		spawns.clear();

		try (FileReader reader = new FileReader("./Data/json/npc_spawns.json")) {
			List<NPCSpawns> npcs = gson.fromJson(reader, new TypeToken<ArrayList<NPCSpawns>>() {}.getType());
			spawns.addAll(npcs);
			log.info("Loaded {} npc spawns", spawns.size());
		} catch (IOException e) {
			log.warn("Failed to load NPC spawns!");
		}
		/*
		
		try(FileReader fr = new FileReader("F:/npc-179-181.json")){
			Map<Integer, Integer> map = gson.fromJson(fr, new TypeToken<Map<Integer, Integer>>() {}.getType());
			map.entrySet().stream().forEach(entry -> {
				spawns.stream().filter(spawn -> spawn.npcId == entry.getKey()).forEach(spawn -> spawn.npcId = entry.getValue());
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		toJson();*/

	}
	/**
	 * NPCSpawns, each represents a single npc spawn in the server which is held
	 * within the array {@link #spawns}.
	 *
	 * This was based off of Galkon's ItemDefinitions, therefore it will be heavily
	 * similar in a lot of ways.
	 *
	 * @Author Jesse (Sk8rdude461)
	 * @Author Galkon
	 */


	/**
	 * The Id of the npc. Commonly referred to as npcType.
	 */
	private int npcId;

	/**
	 * The home x-pos to the npc
	 */
	private int xPos;

	/**
	 * The home y-pos to the npc
	 */
	private int yPos;

	/**
	 * The height level where the npc is spawned
	 */
	private int height;

	/**
	 * The Walking type for the npc.
	 */
	private int walkType;

	/**
	 * The health(Constitution) of the npc.
	 */
	private int health;

	/**
	 * The highest possible hit for the npc.
	 */
	private int maxHit;

	/**
	 * The attack level of the npc.
	 */
	private int attack;

	/**
	 * The defence level of the npc.
	 */
	private int defence;

	/**
	 * Extra info I added so you can tell where the npc is spawned.
	 */
	private String name;

	public NPCSpawns(int npcid, int xPos, int yPos, int height, int walkType, int health, int maxHit, int attack,
			int defence, String name) {
		this.npcId = npcid;
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
		this.walkType = walkType;
		this.health = health;
		this.maxHit = maxHit;
		this.attack = attack;
		this.defence = defence;
		this.name = name;
	}

	public static void toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try(FileWriter fw = new FileWriter("./data/json/npc_spawns.json")) {
			gson.toJson(spawns, fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}