/**
 * 
 */
package valius.model.entity.npc.combat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import valius.model.entity.npc.NPC;
import valius.model.entity.player.combat.CombatType;
import valius.model.items.ItemDefinition;

/**
 * @author ReverendDread
 * Apr 8, 2019
 */
@Slf4j
public class NPCCombatStats {
	
	//Stored stats for npc's bonuses.
	@Getter private static Map<Integer, NPCCombatStats> stats = Maps.newHashMap();
	
	@Getter private int npcId;
	
	//Base stats
	@Getter private int attack;
	@Getter private int strength;
	@Getter private int defence;
	@Getter private int ranged;
	@Getter private int magic;
	
	//Defences
	@Getter private int stabAttack;
	@Getter private int slashAttack;
	@Getter private int crushAttack;
	@Getter private int magicAttack;
	@Getter private int rangedAttack;
	
	//Defences
	@Getter private int stabDefence;
	@Getter private int slashDefence;
	@Getter private int crushDefence;
	@Getter private int magicDefence;
	@Getter private int rangeDefence;
	
	//Extras
	@Getter private int rangedStrength;
	@Getter private int magicStrength;
	
	public NPCCombatStats() {
		this.attack = 1;
		this.strength = 1;
		this.defence = 1;
		this.ranged = 1;
		this.magic = 1;
	}
	
	/**
	 * Initializes npc bonuses.
	 * @throws IOException 
	 * @throws JsonSyntaxException 
	 */
	public static void load()  { //TODO
		System.out.println("Loading npc combat stats...");
		try(FileReader fr = new FileReader(new File("./Data/json/npc_stats.json"))){
			List<NPCCombatStats> list = new Gson().fromJson(fr, new TypeToken<List<NPCCombatStats>>(){}.getType());
			list.stream().filter(Objects::nonNull).forEach(set -> stats.put(set.npcId, set));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Loaded {} npc stats sets.", stats.size());
	}
	
	/**
	 * Gets the npcs attack for the desired style.
	 * @param npc
	 * @param type
	 * @return
	 */
	public int getAttackForStyle(NPC npc, CombatType type) {
		switch (type) {
			case MAGE:
			case DRAGON_FIRE:
			case ACID:
				return getMagic();
			case MELEE:
				return getAttack();
			case RANGE:
			case CANNON:
				return getRanged();
			default:
				return -1;
		}
	}
	
	/**
	 * Gets the npcs defence against the desired style.
	 * @param npc
	 * @param type
	 * @return
	 */
	@SuppressWarnings("incomplete-switch")
	public int getDefenceForStyle(NPC npc, CombatType type) {	
		double defence = 0;
		switch (type) {
			case MAGE:
				defence = (int) ((getDefence() * 0.3) + (2 * getMagicDefence()));
				break;
			case MELEE:
				defence = getDefence(); //TODO add support for different melee types
				break;
			case RANGE:
				defence = getRangedAttack();
				break;
		}
		return (int) Math.round(defence);
	}
	
	/**
	 * Gets bonuses for the desired npc.
	 * @param npcId
	 * @return
	 */
	public static final NPCCombatStats getStatsFor(int npcId) {
		NPCCombatStats set = stats.get(npcId);
		return set == null ? new NPCCombatStats() : set;
	}
	
}
