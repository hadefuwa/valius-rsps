package valius.content.teleportation.ui;

import com.google.common.primitives.Ints;

import lombok.Getter;
import valius.model.entity.player.Player;

/*
 * @author Divine
 */

@Getter
public enum CombatTeleportInfo implements TeleportInfo {
	
	ROCK_CRAB(TeleportNPCDefinition.ROCK_CRAB, "Rock Crab", "50", "@gre@No", "Easy"),
	COW(TeleportNPCDefinition.COW, "Cow", "2", "@gre@No", "Easy"),
	CHICKEN(TeleportNPCDefinition.CHICKEN, "Chicken", "1", "@gre@No", "Easy"),
	YAK(TeleportNPCDefinition.YAK, "Yak", "22", "@gre@No", "Easy"),
	BANDIT(TeleportNPCDefinition.BANDIT, "Bandit", "74", "@gre@No", "Medium"),
	ELF_WARRIOR(TeleportNPCDefinition.ELF_WARRIOR, "Elf Warrior", "90", "@gre@No", "Medium"),
	DAGANNOTH(TeleportNPCDefinition.DAGANNOTH, "Dagonnoth", "74", "@gre@No", "Medium"),
	MITHRIL_DRAGON(TeleportNPCDefinition.MITHRIL_DRAGON, "Mithiril Dragon", "304", "@gre@No", "Hard"),
	SMOKE_DEVIL(TeleportNPCDefinition.SMOKE_DEVIL, "Smoke Devil", "160", "@gre@No", "Hard"),
	RUNE_DRAGON(TeleportNPCDefinition.RUNE_DRAGON, "Rune Dragon", "380", "@gre@No", "Hard"),
	DEMONIC_GORILLA(TeleportNPCDefinition.DEMONIC_GORILLA, "Demonic Gorilla", "275", "@gre@No", "Expert"),
	CAVE_KRAKEN(TeleportNPCDefinition.CAVE_KRAKEN, "Cave Kraken", "127", "@gre@No", "Medium"),
	
	SLAYER_TOWER(TeleportNPCDefinition.SLAYER_TOWER, "Slayer Tower", "-", "@gre@No", "Medium"),
	CATACOMBS(TeleportNPCDefinition.CATACOMBS, "Catacombs", "-", "@gre@No", "Medium / Hard"),
	NIEVES_CAVE(TeleportNPCDefinition.NIEVES_CAVE, "Nieves Cave", "-", "@gre@No", "Medium"),
	RELLEKA_DUNGEON(TeleportNPCDefinition.RELLEKA_DUNGEON, "Relleka Dungeon", "-", "@gre@No", "Medium"),
	TALVERY_DUNGEON(TeleportNPCDefinition.TALVERY_DUNGEON, "Taverly Dungeon", "-", "@gre@No", "Medium"),
	BRIMHAVEN_DUNGEON(TeleportNPCDefinition.BRIMHAVEN_DUNGEON, "Brimhaven Dungeon", "-", "@gre@No", "Medium"),
	WYVERN_DUNGEON(TeleportNPCDefinition.WYVERN_DUNGEON, "Wyvern Dungeon", "140", "@gre@No", "Hard"),
	FOSSIL_ISLAND_WYVERNS(TeleportNPCDefinition.ANCIENT_WYVERN, "Fossil Island Wyverns", "210", "@gre@No", "Hard"),
	CAVE_HORRORS(TeleportNPCDefinition.CAVE_HORRORS, "Cave Horrors", "80", "@gre@No", "Medium"),
	HYDRA_DUNGEON(TeleportNPCDefinition.HYDRA_DUNGEON, "Karluum Slayer Dungeon", "194", "@gre@No", "Hard"),
	

	GOD_WARS(TeleportNPCDefinition.GOD_WARS, "God Wards Dungeon", "-", "@gre@No", "Hard"),
	ZULRAH(TeleportNPCDefinition.ZULRAH, "Zulrah", "725", "@gre@No", "Hard"),
	VORKATH(TeleportNPCDefinition.VORKATH, "Vorkath", "732", "@gre@No", "Expert"),
	SHAMAN(TeleportNPCDefinition.SHAMAN, "Lizardman Shaman", "150", "@gre@No", "Hard"),
	CERBERUS(TeleportNPCDefinition.CERBERUS, "Cerberus", "318", "@gre@No", "Expert"),
	KRAKEN(TeleportNPCDefinition.KRAKEN, "Kraken", "291", "@gre@No", "Hard"),
	SIRE(TeleportNPCDefinition.SIRE, "Abyssal Sire", "350", "@gre@No", "Hard"),
	THERMO_SMOKE(TeleportNPCDefinition.THERMO_SMOKE, "Thermonuclear Smoke Devil", "301", "@gre@No", "Hard"),
	CORP(TeleportNPCDefinition.CORP, "Corporeal Beast", "785", "@gre@No", "Expert"),
	VENENATIS(TeleportNPCDefinition.VENENATIS, "Venenatis", "464", "@dre@Yes", "Hard"),
	SCORPIA(TeleportNPCDefinition.SCORPIA, "Scorpia", "225", "@dre@Yes", "Hard"),
	CALLISTO(TeleportNPCDefinition.CALLISTO, "Callista", "470", "@dre@Yes", "Hard"),
	CHAOS_FANATIC(TeleportNPCDefinition.CHAOS_FANATIC, "Chaos Fanatic", "204", "@dre@Yes", "Hard"),
	CRAZY_ARCH(TeleportNPCDefinition.CRAZY_ARCH, "Crazy Arch", "202", "@dre@Yes", "Hard"),
	VETION(TeleportNPCDefinition.VETION, "Vetion", "454", "@dre@Yes", "Hard"),
	CHAOS_ELE(TeleportNPCDefinition.CHAOS_ELE, "Chaos Elemental", "305", "@dre@Yes", "Hard"),
	KBD(TeleportNPCDefinition.KBD, "King Black Dragon", "276", "@dre@Yes", "Hard"),
	KALPHITE_QUEEN(TeleportNPCDefinition.KALPHITE_QUEEN, "Kalphite Queen", "333", "@gre@No", "Hard"),
	DAGANNOTH_KINGS(TeleportNPCDefinition.DAGANNOTH_KINGS, "Dagannoth Kings", "303", "@gre@No", "Medium / Hard"),
	DAGANNOTH_MOTHER(TeleportNPCDefinition.DAGANNOTH_MOTHER, "Dagannoth Mother", "100", "@gre@No", "Medium / Hard"),
	
	BARRELCHEST(TeleportNPCDefinition.BARRELCHEST, "Barrelchest", "303", "@gre@No", "Medium / Hard"),
	MOLE(TeleportNPCDefinition.MOLE, "Giant Mole", "100", "@gre@No", "Medium / Hard"),
	RAIDS1(TeleportNPCDefinition.RAIDS1, "Chamber of Xeric", "-", "@gre@No", "Hard"),
	RAIDS2(TeleportNPCDefinition.RAIDS2, "Theatre of blood", "-", "@gre@No", "Hard"),
	TRIALS(TeleportNPCDefinition.TRIALS, "Trials of Xeric", "-", "@gre@No", "Hard"),
	VOID_CHAMPION(TeleportNPCDefinition.VOID_CHAMPION, "Void Champion", "500", "@gre@No", "Medium"),
	WILDY_STRYKEWYRM(TeleportNPCDefinition.WILDY_STRYKEWYRM, "Wildy Strykewyrms", "572", "@red@Yes", "Medium / Hard"),
	ICE_STRYKEWYRM(TeleportNPCDefinition.ICE_STRYKEWYRM, "Ice Strykewyrms", "572", "@gre@No", "Medium / Hard"),
	NIGHTMARE(TeleportNPCDefinition.NIGHTMARE, "Nightmare", "666", "@gre@No", "@red@Hard"),
	SOLAK(TeleportNPCDefinition.SOLAK, "Solak", "546", "@gre@No", "@red@Hard"),
	THE_GAUNTLET(TeleportNPCDefinition.THE_GAUNTLET, "The Gauntlet", "674", "@gre@No", "@red@Hard"),
	STARTER_DUNGEON(TeleportNPCDefinition.STARTER_DUNGEON, "Starter Dungeon", "10-200", "@gre@No", "Easy")

	;

	private CombatTeleportInfo(TeleportNPCDefinition npcDisplay, String name, String npcLevel, String safety, String difficulty) {
		this.name = name;
		this.npcLevel = npcLevel;
		this.safety = safety;
		this.difficulty = difficulty;
		this.npcDisplay = npcDisplay;
	}
	
	private String name, npcLevel, safety, difficulty;
	private TeleportNPCDefinition npcDisplay;
	
	
	@Override
	public void send(Player player) {
		if(npcDisplay != null)
			npcDisplay.sendNpcOnInterface(player);
		Integer npcLevelInt = Ints.tryParse(npcLevel.trim());
		if(npcLevelInt == null)
			npcLevelInt = 0;
		player.getPA().sendFrame126("Level: " + (npcLevelInt == 0 ? "<col=5990ff>" : npcLevelInt < 50 ? "@gre@" : npcLevelInt > 100 ? "@red@" : "@yel@") + npcLevel, 63230);
		player.getPA().sendFrame126("Wilderness: " + safety, 63231);
		player.getPA().sendFrame126("Difficulty: " + (difficulty.contains("Expert") ? "<col=b400a1>" : difficulty.contains("Medium") ? "@yel@" : difficulty.contains("Hard") ? "@red@" : "@gre@") + difficulty, 63232);
	}
	

}
