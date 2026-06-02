package valius.content.teleportation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.western_provinces.WesternDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.content.teleportation.ui.CombatTeleportInfo;
import valius.content.teleportation.ui.TeleportInfo;
import valius.content.teleportation.ui.TeleportNPCDefinition;
import valius.model.Location;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossSpawn;
import valius.model.entity.player.Player;
import valius.util.Misc;

public class PortalTeleports {
	
	private Tab currentTab = Tab.MONSTERS;
	private Teleport activeTeleport;
	private Player player;
	private static final int BEGIN_TELEPORT_BUTTON = 63120;
	
	private static final int BEGIN_TELEPORT_BUTTONS = 63131;
	private static final int END_TELEPORT_BUTTONS = 63170;
	private boolean confirmShown;
	

	public PortalTeleports(Player player) {
		this.player = player;
	}
	
	public boolean canTeleport(Player player) {
		if (player.wildLevel > 20) {
			player.sendMessage("You must be below lvl 20 wilderness to teleport");
			return false;
		}
		if (System.currentTimeMillis() - player.lastTeleport < 3500) {
			return false;	
		}
		if (player.inClanWars() || player.inClanWarsSafe()) {
			player.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
			return false;
		}
		if(player.getInterfaceOpen() != 63000)
			return false;
		return true;
	}
	
	public boolean handleTabClick(int buttonId) {
		Optional<Tab> possibleTab = Tab.getMatching(buttonId);
		if(possibleTab.isPresent()) {
			this.currentTab = possibleTab.get();
			activeTeleport = null;
			sendCurrentTab();
		}
		return false;
	}
	
	public void openInterface() {
		this.currentTab = Tab.MONSTERS;
		this.activeTeleport = null;
		confirmShown = false;
		this.sendCurrentTab();
		player.getPA().showInterface(63000);
	}
	
	private void sendCurrentTab() {
		List<Teleport> teleports = Teleport.getMatching(currentTab);
		Tab.stream().forEach(tab -> {
			String prefix = tab == currentTab ? "@yel@" : "";

			player.getPA().sendString(prefix + Misc.capitalize(tab.name().toLowerCase()), tab.buttonId);
		});
		if(!teleports.isEmpty()) {
			player.getPA().sendFrame126("Select Teleport", 63120);
			TeleportInfo.reset(player);
			IntStream
			.range(BEGIN_TELEPORT_BUTTONS, END_TELEPORT_BUTTONS + 1)
			.filter(id -> !teleports.stream().anyMatch(teleport -> teleport.getButtonId() == id))
			.forEach(id -> player.getPA().sendStringNoUpdate("", id));

			player.getPA().sendStringNoUpdate("", 63119);
			teleports.stream().forEach(teleport -> {
				String teleportText = teleport.text;
				if(teleport == Teleport.WILDY_BOSS) {
					if(WildernessBossHandler.getActiveBoss() == null)
						return;
					else {
						teleportText = "<col=ff0000>[EVENT]</col> " + WildernessBossHandler.getActiveBoss().getBossName();
					}
				}
				if(activeTeleport == teleport && teleport.getTeleInfo() != null) {
					teleport.getTeleInfo().send(player);
					player.getPA().sendStringNoUpdate("@yel@" + teleportText, teleport.buttonId);
				} else {
					player.getPA().sendStringNoUpdate(teleportText, teleport.buttonId);
				}
			});
			player.getPA().flush();
		}
	}
	
	public boolean handleButton(int buttonId) {
		Optional<Teleport> possibleTeleport = Teleport.getMatching(currentTab, buttonId);
		if(possibleTeleport.isPresent()) {

			confirmShown = false;
			Teleport teleport = possibleTeleport.get();
			if(activeTeleport != teleport) {
				TeleportInfo.reset(player);
				if(teleport.getTeleInfo() != null) {
					teleport.getTeleInfo().send(player);
				}
				String teleportText = teleport.text;
				String oldTeleport = activeTeleport == null ? "" : activeTeleport.text;
				if(teleport == Teleport.WILDY_BOSS) {
					if(WildernessBossHandler.getActiveBoss() != null)
						teleportText = "<col=ff0000>[EVENT]</col> @yel@" + WildernessBossHandler.getActiveBoss().getBossName();
					
				}
				if(activeTeleport == Teleport.WILDY_BOSS) {
					if(WildernessBossHandler.getActiveBoss() != null)
						oldTeleport = "<col=ff0000>[EVENT]</col> " + WildernessBossHandler.getActiveBoss().getBossName();
					
				}
				player.getPA().sendFrame126("@yel@" + teleportText, teleport.buttonId);
				player.getPA().sendFrame126(teleportText, 63119);
				if(activeTeleport != null)
					player.getPA().sendFrame126(oldTeleport, activeTeleport.buttonId);
					player.getPA().sendFrame126("Confirm", 63120);
				
			}
			confirmShown = false;
			activeTeleport = teleport;
			return true;
		}
		if(buttonId == BEGIN_TELEPORT_BUTTON) {
			if(activeTeleport == null) {
				player.sendMessage("You need to select a teleport first!");
				return true;
			}
			if(activeTeleport.requiresConfirm() && !confirmShown) {
				confirmShown = true;
				player.getPA().sendFrame126("@red@WILD! Confirm?", BEGIN_TELEPORT_BUTTON);
				return true;
			}
			if (canTeleport(player)) {
				confirmShown = false;
				checkAchievements();
				if(activeTeleport == Teleport.WILDY_BOSS) {
					if(WildernessBossHandler.getActiveBoss() != null) {
						Location location = WildernessBossHandler.getCurrentLocation().getAsLocation();
						player.getPA().startTeleport(location.getX(), location.getY(), location.getZ(), "interface", false);
					}
				} else {
					player.getPA().startTeleport(activeTeleport.getLocation().getX(), activeTeleport.getLocation().getY(), activeTeleport.getLocation().getZ(), "interface", false);
				}
				return true;
			}
		}
		return false;
	}
	
	private void checkAchievements() {
		if(activeTeleport == Teleport.CATHERBY) {
			player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CATHERY_TELEPORT);					
		}
		if(activeTeleport == Teleport.LUMBRIDGE) {
			player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);			
		}
		if (activeTeleport == Teleport.CAMELOT) {
			player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
		}
		if (activeTeleport == Teleport.ARDOUGNE) {
			player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
		}
		if (activeTeleport == Teleport.FALADOR) {
			player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_TO_FALADOR);
		}
		if (activeTeleport == Teleport.PEST_CONTROL) {
			player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PEST_CONTROL_TELEPORT);
		}
		
	}

	enum Teleport {
		/* Monsters & Slayer Teleports */
		STARTER_DUNGEON("Starter Dungeon", Tab.MONSTERS, 63131, new Location(3298, 9500, 0), false, CombatTeleportInfo.STARTER_DUNGEON),
		ROCK_CRABS("Rock Crabs", Tab.MONSTERS, 63132, new Location(2673, 3710, 0), false, CombatTeleportInfo.ROCK_CRAB),
		COWS("Cows", Tab.MONSTERS, 63133, new Location(3260, 3272, 0), false, CombatTeleportInfo.COW),
		
		CHICKENS("Chickens", Tab.MONSTERS, 63134, new Location(3234, 3293, 0), false, CombatTeleportInfo.CHICKEN),
		YAKS("Yaks", Tab.MONSTERS, 63135, new Location(2326, 3801, 0), false, CombatTeleportInfo.YAK),
		
		BANDITS("Bandits", Tab.MONSTERS, 63136, new Location(3176, 2987, 0), false, CombatTeleportInfo.BANDIT),
		ELF_WARRIORS("Elf Warriors", Tab.MONSTERS, 63137, new Location(2897, 2725, 0), false, CombatTeleportInfo.ELF_WARRIOR),
		
		DAGANNOTHS("Dagannoths", Tab.MONSTERS, 63138, new Location(2442, 10147, 0), false, CombatTeleportInfo.DAGANNOTH),
		MITHRIL_DRAGONS("Mithril Dragons", Tab.MONSTERS, 63139, new Location(1746, 5323, 0), false, CombatTeleportInfo.MITHRIL_DRAGON),
		
		SMOKE_DEVILS("Smoke Devils", Tab.MONSTERS, 63140, new Location(2404, 9415, 0), false, CombatTeleportInfo.SMOKE_DEVIL),
		RUNE_AND_ADDY_DRAGONS("Rune Dragons", Tab.MONSTERS, 63141, new Location(1568, 5075, 0), false, CombatTeleportInfo.RUNE_DRAGON),
		
		DEMONIC_GORILLAS("Demonic Gorillas", Tab.MONSTERS, 63142, new Location(2130, 5647, 0), CombatTeleportInfo.DEMONIC_GORILLA),
		CAVE_KRAKEN("Cave Krakens", Tab.MONSTERS, 63143, new Location(2277, 10001, 0), false, CombatTeleportInfo.CAVE_KRAKEN),
		
		SLAYER_TOWER("Slayer Tower", Tab.MONSTERS, 63144, new Location(3428, 3538, 0), false, CombatTeleportInfo.SLAYER_TOWER),
		CATACOMBS("Catacombs", Tab.MONSTERS, 63145, new Location(1661, 10049, 0), false, CombatTeleportInfo.CATACOMBS),

		NIEVES_CAVE("Stronghold Cave", Tab.MONSTERS, 63146, new Location(2431, 3425, 0), false, CombatTeleportInfo.NIEVES_CAVE),
		RELLIKA_DUNGEON("Relleka Dungeon", Tab.MONSTERS, 63147, new Location(2805, 10001, 0), false, CombatTeleportInfo.RELLEKA_DUNGEON),

		
		TALVERY_DUNGEON("Talvery Dungeon", Tab.MONSTERS, 63148, new Location(2884, 9796, 0), false, CombatTeleportInfo.TALVERY_DUNGEON),
		BRIMHAVEN_DUNGEON("Brimhaven Dungeon", Tab.MONSTERS, 63149, new Location(2710, 9564, 0), false, CombatTeleportInfo.BRIMHAVEN_DUNGEON),

		WYVERN_DUNGEON("Wyvern Dungeon", Tab.MONSTERS, 63150, new Location(3029, 9582, 0), false, CombatTeleportInfo.WYVERN_DUNGEON),
		HYDRA_DUNGEON("Karuulm Dungeon", Tab.MONSTERS, 63151, new Location(1311, 10205, 0), false, CombatTeleportInfo.HYDRA_DUNGEON),
		
		FOSSIL_ISLAND_WYVERNS("Fossil Island Wyverns", Tab.MONSTERS, 63152, new Location(3596, 10291), false, CombatTeleportInfo.FOSSIL_ISLAND_WYVERNS),
		CAVE_HORRORS("Cave Horrors", Tab.MONSTERS, 63153, new Location(3745, 9397), false, CombatTeleportInfo.CAVE_HORRORS),
		
		
		/* Boss teleports */ 
		GOD_WARS("God Wars Dungeon", Tab.BOSSES, 63131, new Location(2882, 5311, 2), false, CombatTeleportInfo.GOD_WARS),
		ZULRAH("Zulrah", Tab.BOSSES, 63132, new Location(2202, 3056, 0), false, CombatTeleportInfo.ZULRAH),
		
		VORKATH("Vorkath", Tab.BOSSES, 63133, new Location(2272, 4050, 0), false, CombatTeleportInfo.VORKATH),
		LIZARD_SHAMAN("Lizard Shaman", Tab.BOSSES, 63134, new Location(1469, 3687, 0), false, CombatTeleportInfo.SHAMAN),
		
		CERBERUS("Cerberus", Tab.BOSSES, 63135, new Location(2873, 9847, 0), false, CombatTeleportInfo.CERBERUS),
		KRAKEN("Kraken", Tab.BOSSES, 63136, new Location(2280, 10016, 0), false, CombatTeleportInfo.KRAKEN),
		
		ABYSSAL_SIRE("Abyssal Sire", Tab.BOSSES, 63137, new Location(3039, 4788, 0), false, CombatTeleportInfo.SIRE),
		THERMO_SMOKE_DEVIL("Therm SmokeDevil", Tab.BOSSES, 63138, new Location(2376, 9452, 0), false, CombatTeleportInfo.THERMO_SMOKE),
		
		CORP_BEAST("Corporeal Beast", Tab.BOSSES, 63139, new Location(2964, 4382, 2), false, CombatTeleportInfo.CORP),
		VENENATIS("Venenatis", Tab.BOSSES, 63140, new Location(3345, 3754, 0), true, CombatTeleportInfo.VENENATIS),
		
		SCORPIA("Scorpia", Tab.BOSSES, 63141, new Location(3233, 3947, 0), true, CombatTeleportInfo.SCORPIA),
		CALLISTO("Callisto", Tab.BOSSES, 63142, new Location(3325, 3845, 0), true, CombatTeleportInfo.CALLISTO),
		
		CRAZY_ARCH("Crazy Arch", Tab.BOSSES, 63143, new Location(2984, 3713, 0), true, CombatTeleportInfo.CRAZY_ARCH),
		CHAOS_FAN("Chaos Fanatic", Tab.BOSSES, 63144, new Location(2981, 3836, 0), true, CombatTeleportInfo.CHAOS_FANATIC),
		
		VETION("Vet'ion", Tab.BOSSES, 63145, new Location(3176, 3782, 0), true, CombatTeleportInfo.VETION),
		CHAOS_ELEMENTAL("Chaos Elemental", Tab.BOSSES, 63146, new Location(3281, 3910, 0), true, CombatTeleportInfo.CHAOS_ELE),
		
		KING_BLACK_DRAGON("King Black Dragon", Tab.BOSSES, 63147, new Location(2271, 4680, 0), false, CombatTeleportInfo.KBD),
		KALPHITE_QUEEN("Kalphite Queen", Tab.BOSSES, 63148, new Location(3508, 9493, 0), false, CombatTeleportInfo.KALPHITE_QUEEN),
		
		DAGGANNOTH_KINGS("Dagganoth Kings", Tab.BOSSES, 63149, new Location(1913, 4367, 0), false, CombatTeleportInfo.DAGANNOTH_KINGS),
		DAGANNOTH_MOTHER("Dagannoth Mother", Tab.BOSSES, 63150, new Location(2508, 3643, 0), false, CombatTeleportInfo.DAGANNOTH_MOTHER),
		
		BARRELCHEST("Barrelchest", Tab.BOSSES, 63151, new Location(1229, 3497, 0), false, CombatTeleportInfo.BARRELCHEST),
		GIANT_MOLE("Giant Mole", Tab.BOSSES, 63152, new Location(3078, 3924, 0), true, CombatTeleportInfo.MOLE),
		
		VOID_CHAMPION("Void Champion", Tab.BOSSES, 63153, new Location(1578, 3952, 0), false, CombatTeleportInfo.VOID_CHAMPION),
		NIGHTMARE("Nightmare", Tab.BOSSES, 63154, new Location(1884, 4948, 2), false, CombatTeleportInfo.NIGHTMARE),
		
		SOLAK("Solak", Tab.BOSSES, 63155, new Location(3180, 9758, 0), false, CombatTeleportInfo.SOLAK),
		
		
		
		
		/* Minigame Teleports */
		RAIDS1("Chamber of Xeric", Tab.MINIGAMES, 63131, new Location(3059, 9947, 0), false, CombatTeleportInfo.RAIDS1),
		RAIDS2("Theatre of Blood", Tab.MINIGAMES, 63132, new Location(3059, 9956, 0), false, CombatTeleportInfo.RAIDS2),
		
		CUSTOM_RAID1("Trials of Xeric", Tab.MINIGAMES, 63133, new Location(3040, 9947, 0), false, CombatTeleportInfo.TRIALS),
		THE_GAUNTLET("The Gauntlet", Tab.MINIGAMES, 63134, new Location(3036, 6126, 1), false, CombatTeleportInfo.THE_GAUNTLET),
		
		FIGHT_CAVES("Fight Caves", Tab.MINIGAMES, 63135, new Location(2445, 5176, 0)),
		INFERNO("The Inferno", Tab.MINIGAMES, 63136, new Location(2494, 5107, 0)),
		
		PEST_CONTROL("Pest Control", Tab.MINIGAMES, 63137, new Location(2660, 2648, 0)),
		BARROWS("Barrows", Tab.MINIGAMES, 63138, new Location(3565, 3316, 0)),
		
		DUEL_ARENA("Duel Arena", Tab.MINIGAMES, 63139, new Location(3365, 3266, 0)),
		WARRIORS_GUILD("Warriors Guild", Tab.MINIGAMES, 63140, new Location(2874, 3546, 0)),
		
		MAGE_ARENA("Mage's Arena", Tab.MINIGAMES, 63141, new Location(2541, 4716, 0)),
		SHAYZIEN_ASSAULT("Shayzien Assault", Tab.MINIGAMES, 63142, new Location(1461, 3689, 0)),
		
		CLAN_WARS("Clan Wars", Tab.MINIGAMES, 63143, new Location(3059, 9947, 0)),
		
		
		/* Skilling Teleports */
		MINING_GUILD("Mining Guild", Tab.SKILLING, 63131, new Location(3046, 9756, 0)),
		WOODCUTTING_GUILD("Woodcutting Guild", Tab.SKILLING, 63132, new Location(1659, 3504, 0)),

		LANDS_END("Lands End", Tab.SKILLING, 63133, new Location(1504, 3412, 0)),
		FISHERMANS_COVE("Fishermans cove", Tab.SKILLING, 63134, new Location(1721, 3464, 0)),
		
		FARMING_PATCHES("Farming", Tab.SKILLING, 63135, new Location(3003, 3376, 0)),
		HUNTER("Hunter", Tab.SKILLING, 63136, new Location(1582, 3433, 0)),
		
		PURO_PURO("Puro Puro", Tab.SKILLING,  63137, new Location(2592, 4317, 0)),
		DEEP_SEA_FISHING("Deep Sea Fishing", Tab.SKILLING, 63138, new Location (1889, 4824, 1)),
		
		
		/* PVP Teleports */

		REVENANTS("Revenants", Tab.WILDERNESS, 63131, new Location(3128, 3833, 0), true),
		WILDERNESS_RESOURCE("Resource Area", Tab.WILDERNESS, 63132, new Location(3184, 3947, 0), true),
		
		MAGES_BANK("Mages Bank", Tab.WILDERNESS, 63133, new Location(2539, 4716, 0), true),
		SKELETAL_WYVERNS("Skeletal Wyverns", Tab.WILDERNESS, 63134, new Location(2963, 3916, 0), true),
		
		EAST_DRAGONS("East Dragons", Tab.WILDERNESS, 63135, new Location(3351, 3659, 0), true),
		WEST_DRAGONS("West Dragons", Tab.WILDERNESS, 63136, new Location(2976, 3591, 0), true),
		
		FOUNTAIN_OF_RUNE("Rune Fountain", Tab.WILDERNESS, 63137, new Location(3347, 3872, 0), true),
		WILDERNESS_VOLCANO("Wildy Volcano", Tab.WILDERNESS, 63138, new Location(3366, 3935, 0), true),
		
		CHAOS_ALTAR("Chaos Altar", Tab.WILDERNESS, 63139, new Location(3236, 3628, 0), true),
		LAVA_DRAGONS("Lava Dragons", Tab.WILDERNESS, 63140, new Location(3204, 3812, 0), true),
		
		DARK_CASTLE("Dark Castle", Tab.WILDERNESS, 63141, new Location(3010, 3631, 0), true),
		TREE_ENT("Tree Ent", Tab.WILDERNESS, 63142, new Location(3303, 3626, 0), true),
		
		RUINS("The Ruins", Tab.WILDERNESS, 63143, new Location(3287, 3899, 0), true),
		EARTH_WARRIORS("Earth Warriors", Tab.WILDERNESS, 63144, new Location(3229, 3778, 0), true),

		WILDY_BOSS("<col=ff0000>[EVENT]</col>", Tab.WILDERNESS, 63145, new Location(3333, 3333, 0), true),
		WILDY_WYRMS("Wildy StyrkeWyrms", Tab.WILDERNESS, 63146, new Location(3097, 3814), true, CombatTeleportInfo.WILDY_STRYKEWYRM),
		
		ICE_STRYKEWYRMS("Ice StyrkeWyrms", Tab.WILDERNESS, 63147, new Location(2777, 3837), false, CombatTeleportInfo.ICE_STRYKEWYRM),
		
		/* City Teleports */
		LUMBRIDGE("Lumbridge", Tab.CITIES, 63131, new Location(3222, 3218, 0)),
		VARROCK("Varrock", Tab.CITIES, 63132, new Location(3210, 3424, 0)),
		
		EDGEVILLE("Edgeville", Tab.CITIES, 63133, new Location(3094, 3500, 0)),
		FALADOR("Falador", Tab.CITIES, 63134, new Location(2964, 3378, 0)),
		
		ZEAH("Zeah", Tab.CITIES, 63135, new Location(1784, 3690, 0)),
		CAMELOT("Camelot", Tab.CITIES, 63136, new Location(2757, 3477, 0)),
		
		CATHERBY("Catherby", Tab.CITIES, 63137, new Location(2822, 3438, 0)),
		ARDOUGNE("Ardougne", Tab.CITIES, 63138, new Location(2661, 3306, 0)),
		
		TAVERLY("Taverly", Tab.CITIES, 63139, new Location(2928, 3451, 0)),
		NEITIZNOT("Neitiznot", Tab.CITIES, 63140, new Location(2321, 3804, 0)),
		
		YANILLE("Yanille", Tab.CITIES, 63141, new Location(2606, 3099, 0)),
		KARAMJA("Karamja", Tab.CITIES, 63142, new Location(2950, 3147, 0)),
		
		AL_KHARID("Al Kharid", Tab.CITIES, 63143, new Location(3292, 3176, 0)),
		LLETYA("Lletya", Tab.CITIES, 63144, new Location(2331, 3171, 0))

		
		;
		
		private Teleport(String text, Tab tab, int buttonId, Location location, boolean requireConfirm, TeleportInfo teleInfo) {
			this.text = text;
			this.tab = tab;
			this.buttonId = buttonId;
			this.location = location;
			this.requireConfirm = requireConfirm;
			this.teleInfo = teleInfo;
		}
		

		private Teleport(String text, Tab tab, int buttonId, Location location, boolean requireConfirm) {
			this(text, tab, buttonId, location, requireConfirm, null);
		}
		private Teleport(String text, Tab tab, int buttonId) {
			this(text, tab, buttonId, Location.of(0, 0, 0), false, null);
		}
		
		private Teleport(String text, Tab tab, int buttonId, Location location) {
			this(text, tab, buttonId, location, false, null);
		}
		
		private Teleport(String text, Tab tab, int buttonId, Location location, TeleportInfo teleInfo) {
			this(text, tab, buttonId, location, false, teleInfo);
		}
		
		private String text;
		private int buttonId;
		private Location location;
		private Tab tab;
		private boolean requireConfirm;
		private TeleportInfo teleInfo;
		
		public int getButtonId() {
			return buttonId;
		}

		public Location getLocation() {
			return location;
		}
		
		public Tab getTab() {
			return tab;
		}
	
		public String getText() {
			return text;
		}
		
		public boolean requiresConfirm() {
			return requireConfirm;
		}
		
		public TeleportInfo getTeleInfo() {
			return teleInfo;
		}

		public static List<Teleport> getMatching(int buttonId){
			return Arrays.stream(Teleport.values())
					.filter(teleport -> teleport.buttonId == buttonId)
					.collect(Collectors.toList());
		}
		
		public static List<Teleport> getMatching(Tab tab){
			return Arrays.stream(Teleport.values())
					.filter(teleport -> teleport.tab == tab)
					.collect(Collectors.toList());
		}
		
		public static Optional<Teleport> getMatching(Tab tab, int buttonId){
			return Arrays.stream(Teleport.values())
					.filter(teleport -> !teleport.getLocation().equals(Location.of(0, 0, 0)))
					.filter(teleport -> teleport.buttonId == buttonId && teleport.tab == tab)
					.findFirst();
		}
	}
	
	enum Tab {
		MONSTERS(63055), BOSSES(63056), MINIGAMES(63057), SKILLING(63058), WILDERNESS(63059), CITIES(63060);
		
		private Tab(int id) {
			this.buttonId = id;
		}
		
		private int buttonId;
		
		public int getId() {
			return buttonId;
		}
		
		public static Optional<Tab> getMatching(int buttonId){
			return Arrays.stream(Tab.values())
					.filter(tab -> tab.buttonId == buttonId)
					.findFirst();
		}
		
		public static Stream<Tab> stream(){
			return Stream.of(Tab.values());
		}
	}
}
