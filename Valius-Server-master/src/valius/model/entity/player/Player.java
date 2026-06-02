package valius.model.entity.player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.google.common.collect.Queues;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import valius.Config;
import valius.clip.WorldObject;
import valius.content.BloodMysteryBox;
import valius.content.ChamberOfXericBox;
import valius.content.ChargeTrident;
import valius.content.ChristmasBox;
import valius.content.DailyGearBox;
import valius.content.DailyReward;
import valius.content.DailySkillBox;
import valius.content.DragonHunterMBox;
import valius.content.EasterMysteryBox;
import valius.content.ElderMysteryBox;
import valius.content.EventMysteryBox;
import valius.content.GIMRepository;
import valius.content.GroupIronman;
import valius.content.HalloweenMysteryBox;
import valius.content.HourlyRewardBox;
import valius.content.InfernalMysteryBox;
import valius.content.MysteryBox;
import valius.content.PetMysteryBox;
import valius.content.PvmCasket;
import valius.content.RandomEventInterface;
import valius.content.SkillCasket;
import valius.content.SkillcapePerks;
import valius.content.StarBox;
import valius.content.StarterPetBox;
import valius.content.Tutorial;
import valius.content.Tutorial.Stage;
import valius.content.UltraMysteryBox;
import valius.content.ValentinesBox;
import valius.content.ValiusMysteryBox;
import valius.content.WildyCrate;
import valius.content.achievement.AchievementHandler;
import valius.content.achievement.Achievements;
import valius.content.achievement_diary.AchievementDiary;
import valius.content.achievement_diary.AchievementDiaryManager;
import valius.content.achievement_diary.RechargeItems;
import valius.content.barrows.Barrows;
import valius.content.barrows.TunnelEvent;
import valius.content.bonus.DoubleExperience;
import valius.content.bossCaskets.ArmadylCasket;
import valius.content.bossCaskets.BandosCasket;
import valius.content.bossCaskets.SaradominCasket;
import valius.content.bossCaskets.ZamorakCasket;
import valius.content.cannon.CannonManager;
import valius.content.cannon.DwarfCannon;
import valius.content.clans.Clan;
import valius.content.dailytasks.DailyTasks;
import valius.content.dailytasks.DailyTasks.PossibleTasks;
import valius.content.dailytasks.TaskTypes;
import valius.content.explock.ExpLock;
import valius.content.gauntlet.GauntletPrepRoom;
import valius.content.godwars.God;
import valius.content.godwars.Godwars;
import valius.content.godwars.GodwarsEquipment;
import valius.content.instances.InstancedAreaManager;
import valius.content.kill_streaks.Killstreak;
import valius.content.presets.Presets;
import valius.content.prestige.PrestigeSkills;
import valius.content.quest.QuestManager;
import valius.content.quest.dialogue.Dialogue;
import valius.content.safebox.SafeBox;
import valius.content.staff.PunishmentPanel;
import valius.content.teleportation.PortalTeleports;
import valius.content.titles.Titles;
import valius.content.tradingpost.ListedItem;
import valius.content.trails.RewardLevel;
import valius.content.trails.TreasureTrails;
import valius.event.CycleEventHandler;
import valius.event.Event;
import valius.event.impl.IronmanRevertEvent;
import valius.event.impl.MinigamePlayersEvent;
import valius.event.impl.RunEnergyEvent;
import valius.event.impl.SkillRestorationEvent;
import valius.model.Location;
import valius.model.entity.Entity;
import valius.model.entity.HealthStatus;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDeathTracker;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.DemonicGorilla;
import valius.model.entity.npc.bosses.KalphiteQueen;
import valius.model.entity.npc.bosses.cerberus.Cerberus;
import valius.model.entity.npc.bosses.cerberus.CerberusLostItems;
import valius.model.entity.npc.bosses.skotizo.Skotizo;
import valius.model.entity.npc.bosses.skotizo.SkotizoLostItems;
import valius.model.entity.npc.bosses.vorkath.Vorkath;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.npc.bosses.zulrah.ZulrahLostItems;
import valius.model.entity.npc.drops.DropManager;
import valius.model.entity.npc.drops.dropTables.GemRareDropTable;
import valius.model.entity.npc.drops.dropTables.RareDropTable;
import valius.model.entity.npc.drops.dropTables.SlayerRareDropTable;
import valius.model.entity.npc.instance.InstanceSoloFight;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.PetHandler.Pets;
import valius.model.entity.player.combat.CombatAssistant;
import valius.model.entity.player.combat.DamageQueueEvent;
import valius.model.entity.player.combat.Degrade;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.magic.MagicData;
import valius.model.entity.player.combat.melee.QuickPrayers;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.model.entity.player.mode.Mode;
import valius.model.entity.player.mode.ModeType;
import valius.model.entity.player.path.Direction;
import valius.model.entity.player.skills.Agility;
import valius.model.entity.player.skills.Cooking;
import valius.model.entity.player.skills.MakeWidget;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.SkillInterfaces;
import valius.model.entity.player.skills.Skilling;
import valius.model.entity.player.skills.Smelting;
import valius.model.entity.player.skills.Smithing;
import valius.model.entity.player.skills.SmithingInterface;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.agility.impl.BarbarianAgility;
import valius.model.entity.player.skills.agility.impl.GnomeAgility;
import valius.model.entity.player.skills.agility.impl.Lighthouse;
import valius.model.entity.player.skills.agility.impl.Shortcuts;
import valius.model.entity.player.skills.agility.impl.WildernessAgility;
import valius.model.entity.player.skills.agility.impl.rooftop.RooftopArdougne;
import valius.model.entity.player.skills.agility.impl.rooftop.RooftopFalador;
import valius.model.entity.player.skills.agility.impl.rooftop.RooftopSeers;
import valius.model.entity.player.skills.agility.impl.rooftop.RooftopVarrock;
import valius.model.entity.player.skills.crafting.Crafting;
import valius.model.entity.player.skills.farming.Farming;
import valius.model.entity.player.skills.fishing.Fishing;
import valius.model.entity.player.skills.fletching.Fletching;
import valius.model.entity.player.skills.herblore.Herblore;
import valius.model.entity.player.skills.hunter.Hunter;
import valius.model.entity.player.skills.mining.Mining;
import valius.model.entity.player.skills.prayer.Prayer;
import valius.model.entity.player.skills.runecrafting.Runecrafting;
import valius.model.entity.player.skills.slayer.Slayer;
import valius.model.entity.player.skills.thieving.Thieving;
import valius.model.holiday.HolidayStages;
import valius.model.holiday.christmas.ChristmasPresent;
import valius.model.item.container.GemBag;
import valius.model.item.container.HerbSack;
import valius.model.item.container.LootingBag;
import valius.model.item.container.RunePouch;
import valius.model.items.EquipmentSet;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemCombination;
import valius.model.items.ItemUtility;
import valius.model.items.bank.Bank;
import valius.model.items.bank.BankPin;
import valius.model.lobby.Lobby;
import valius.model.lobby.LobbyManager;
import valius.model.lobby.LobbyType;
import valius.model.minigames.bounty_hunter.BountyHunter;
import valius.model.minigames.fight_cave.FightCave;
import valius.model.minigames.inferno.Inferno;
import valius.model.minigames.inferno.Tzkalzuk;
import valius.model.minigames.lighthouse.DagannothMother;
import valius.model.minigames.pest_control.PestControl;
import valius.model.minigames.pest_control.PestControlRewards;
import valius.model.minigames.pk_arena.Highpkarena;
import valius.model.minigames.pk_arena.Lowpkarena;
import valius.model.minigames.raids.RaidConstants;
import valius.model.minigames.rfd.DisposeTypes;
import valius.model.minigames.rfd.RecipeForDisaster;
import valius.model.minigames.theatre.Theatre;
import valius.model.minigames.warriors_guild.WarriorsGuild;
import valius.model.minigames.xeric.Xeric;
import valius.model.miniquests.MageArena;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.Duel;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.trade.Trade;
import valius.model.shops.ShopAssistant;
import valius.net.Packet;
import valius.net.Packet.Type;
import valius.net.outgoing.UnnecessaryPacketDropper;
import valius.net.packet.PacketHandler;
import valius.util.Buffer;
import valius.util.Misc;
import valius.util.SimpleTimer;
import valius.util.Stopwatch;
import valius.world.World;

@Slf4j
public class Player extends Entity {
	
	public boolean isPlayer() {
		return true;
	}
	
	@Getter
	private MakeWidget makeWidget = new MakeWidget(this);
	
	@Getter @Setter
	private Dialogue activeDialogue;
	
	
	@Getter
	private QuestManager questManager;
	
	@Getter
	private SkillExperience skills = new SkillExperience(this);

	public int maRound = 0;
	public boolean maOption = false, maIndeedyOption = false;

	public int lastTeleportX, lastTeleportY, lastTeleportZ;

	public MageArena mageArena = new MageArena(this);

	public MageArena getMageArena() {
		return this.mageArena;
	}
	public int[][] raidReward ={{0,0}};
	public int raidCount;
	
	@Getter
	public CompletionistCape completionistCape = new CompletionistCape(this);

	/**
	 * Overload variables
	 */

	public int homeTeleport = 50;

	public int boxCurrentlyUsing = 0;

	public int overloadTimer;
	public boolean overloadBoosted;
	
	/*
	 * cursed perk
	 */
	public boolean cursed = false;
	
	/*
	 * ToX Variables
	 */
	public int xericWaveType = 0;
	public int xericDamage = 0;
	
	/*
	 * ToB Variables
	 */
	public int theatrePoints = 0;
	public int EventBossDamage = 0;
	public int NightmareDamage = 0;
	public int SolakDamage = 0;
	public int MimicDamage = 0;
	public int JackokrakenDamage = 0;
	
	public int revHeal = 0;
	
	public int VoidKnightChampionDmg = 0;
	
	public int WildyEventBossDamage = 0;

	public int infernoWaveId = 0;
	public int infernoWaveType = 0;

	/**
	 * Variables for trading post
	 */

	public boolean debugMessage = false;
	
	@Getter @Setter
	private boolean gaunletLootAvailable;
	
	@Getter @Setter
	private boolean customRaidLootAvailable;
	/**
	 * New Daily Task Variables
	 */

	public PossibleTasks currentTask;
	public TaskTypes playerChoice;
	public boolean dailyEnabled = false, completedDailyTask;
	public int dailyTaskDate, totalDailyDone = 0;

	public ListedItem pendingItem;
	public int item, uneditItem, quantity, price, pageId = 1, searchId;
	public String lookup;
	public List<ListedItem> saleResults;
	public ArrayList<Integer> saleItems = new ArrayList<Integer>();
	public ArrayList<Integer> saleAmount = new ArrayList<Integer>();
	public ArrayList<Integer> salePrice = new ArrayList<Integer>();
	public int[] historyItems = new int[15];
	public int[] historyItemsN = new int[15];
	public int[] historyPrice = new int[15];
	
	public boolean inSelecting = false, isListing = false;

	private RechargeItems rechargeItems = new RechargeItems(this);
	/**
	 * Classes
	 */
	private ExpLock explock = new ExpLock(this);
	private PrestigeSkills prestigeskills = new PrestigeSkills(this);
	private LootingBag lootingBag = new LootingBag(this);
	private SafeBox safeBox = new SafeBox(this);

	public RechargeItems getRechargeItems() {
		return rechargeItems;
	}

	private UltraMysteryBox ultraMysteryBox= new UltraMysteryBox(this);

	public UltraMysteryBox getUltraMysteryBox() {
		return ultraMysteryBox;
	}
	
//	public BoxSets getBoxSets() {
//		return getBoxSets();
//	}
	public int teleSelected = 0;

	public boolean placeHolderWarning = false;
	public int lastPlaceHolderWarning = 0;
	public boolean placeHolders = false;
	public final Stopwatch last_trap_layed = new Stopwatch();
	@Getter @Setter private boolean running;

	public List<Integer> searchList = new ArrayList<>();

	private final QuickPrayers quick = new QuickPrayers();

	private AchievementDiary<?> diary;
	private RunePouch runePouch = new RunePouch(this);
	private HerbSack herbSack = new HerbSack(this);
	private GemBag gemBag = new GemBag(this);
	private RandomEventInterface randomEventInterface = new RandomEventInterface(this);
	private DemonicGorilla demonicGorilla = null;
	private Mining mining = new Mining(this);
	private PestControlRewards pestControlRewards = new PestControlRewards(this);
	private WarriorsGuild warriorsGuild = new WarriorsGuild(this);
	private Zulrah zulrah = new Zulrah(this);
	
	
	private NPCDeathTracker npcDeathTracker = new NPCDeathTracker(this);
	private UnnecessaryPacketDropper packetDropper = new UnnecessaryPacketDropper();
	private DamageQueueEvent damageQueue = new DamageQueueEvent(this);
	private BountyHunter bountyHunter = new BountyHunter(this);
	private MysteryBox mysteryBox = new MysteryBox(this);
	private ElderMysteryBox elderMysteryBox = new ElderMysteryBox(this);
	private StarterPetBox starterPetBox = new StarterPetBox(this);
	private RareDropTable rareDropTable = new RareDropTable(this);
	private GemRareDropTable gemRareDropTable = new GemRareDropTable(this);
	private SlayerRareDropTable slayerRareDropTable = new SlayerRareDropTable(this);
	private ValiusMysteryBox valiusMysteryBox = new ValiusMysteryBox(this);
	private ChamberOfXericBox chamberOfXericBox = new ChamberOfXericBox(this);
	private BloodMysteryBox bloodMysteryBox = new BloodMysteryBox(this);
	private EventMysteryBox eventMysteryBox = new EventMysteryBox(this);
	private PetMysteryBox petMysteryBox = new PetMysteryBox(this);
	private ValentinesBox valentinesBox = new ValentinesBox(this);
	private StarBox starBox = new StarBox(this);
	private EasterMysteryBox easterMysteryBox = new EasterMysteryBox(this);
	private ChristmasBox christmasBox = new ChristmasBox(this);
	private ChristmasPresent christmasPresent = new ChristmasPresent(this);
	private HalloweenMysteryBox halloweenMysteryBox = new HalloweenMysteryBox(this);
	private DragonHunterMBox dragonHunterMBox = new DragonHunterMBox(this);
	private InfernalMysteryBox infernalmysterybox = new InfernalMysteryBox(this);
	private BandosCasket bandoscasket = new BandosCasket(this);
	private ArmadylCasket armadylcasket = new ArmadylCasket(this);
	private ZamorakCasket zamorakcasket = new ZamorakCasket(this);
	private SaradominCasket saradomincasket = new SaradominCasket(this);
	private HourlyRewardBox hourlyRewardBox = new HourlyRewardBox(this);
	private PvmCasket pvmCasket = new PvmCasket(this);
	private Fishing fishing = new Fishing(this);
	private SkillCasket skillCasket = new SkillCasket(this);
	private WildyCrate wildyCrate = new WildyCrate(this);
	private DailyGearBox dailyGearBox = new DailyGearBox(this);
	private DailySkillBox dailySkillBox = new DailySkillBox(this);
	private GroupIronman groupIronman = new GroupIronman(this);
	private Vorkath vorkath = new Vorkath(this);//creates custom VORKATH instance for player
	private DailyReward dailyReward = new DailyReward(this);//object
	private long lastContainerSearch;
	private HolidayStages holidayStages;
	private AchievementHandler achievementHandler;
	private PlayerKill playerKills;
	private String macAddress;
	private Duel duelSession = new Duel(this);
	private Player itemOnPlayer;
	private Presets presets = null;
	private Killstreak killstreaks;
	private PunishmentPanel punishmentPanel = new PunishmentPanel(this);
	private Tutorial tutorial = new Tutorial(this);
	private Mode mode;
	private Channel session;
	private Trade trade = new Trade(this);
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combat = new CombatAssistant(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Friends friend = new Friends(this);
	private Ignores ignores = new Ignores(this);
	private Queue<Packet> queuedPackets = new ConcurrentLinkedQueue<>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private Killstreak killingStreak = new Killstreak(this);
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	private ChargeTrident chargeTrident = new ChargeTrident(this);
	private PlayerAction playerAction = new PlayerAction(this);
	private PortalTeleports portalTeleports = new PortalTeleports(this);
	private Slayer slayer;
	private Runecrafting runecrafting = new Runecrafting();
	private AgilityHandler agilityHandler = new AgilityHandler();
	private GnomeAgility gnomeAgility = new GnomeAgility();
	private WildernessAgility wildernessAgility = new WildernessAgility();
	private Shortcuts shortcuts = new Shortcuts();
	private RooftopSeers rooftopSeers = new RooftopSeers();
	private RooftopFalador rooftopFalador = new RooftopFalador();
	private RooftopVarrock rooftopVarrock = new RooftopVarrock();
	private RooftopArdougne rooftopArdougne = new RooftopArdougne();
	private BarbarianAgility barbarianAgility = new BarbarianAgility();
	private Lighthouse lighthouse = new Lighthouse();
	private Agility agility = new Agility(this);
	private Cooking cooking = new Cooking();
	private Crafting crafting = new Crafting(this);
	private Prayer prayer = new Prayer(this);
	private Smithing smith = new Smithing(this);
	private FightCave fightcave = null;
	private Xeric xeric = null;
	//private Tournament tournament = null;
	private DagannothMother dagannothMother = null;
	private RecipeForDisaster recipeForDisaster = null;
	private KalphiteQueen kq = null;
	private Cerberus cerberus = null;
	private Tzkalzuk tzkalzuk = null;
	private Skotizo skotizo = null;
//	private TournamentConstants tournamentConstants = null;
	private InstanceSoloFight soloFight = null;
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Herblore herblore = new Herblore(this);
	private Thieving thieving = new Thieving(this);
	private Fletching fletching = new Fletching(this);
	private Barrows barrows = new Barrows(this);
	private Godwars godwars = new Godwars(this);
	private TreasureTrails trails = new TreasureTrails(this);
	private Optional<ItemCombination> currentCombination = Optional.empty();
	private Skilling skilling = new Skilling(this);
	private Farming farming = new Farming(this);
	private ZulrahLostItems lostItemsZulrah;
	private CerberusLostItems lostItemsCerberus;
	private SkotizoLostItems lostItemsSkotizo;
	private List<God> equippedGodItems;
	private Titles titles = new Titles(this);
	protected RightGroup rights;
	protected static Buffer playerProps;
	public static PlayerSave save;
	public static Player cliento2;
	public Player diceHost;
	public Clan clan;
	public Smelting.Bars bar = null;
	public byte buffer[] = null;
	public Buffer inStream = null, outStream = null;
	public SimpleTimer potionTimer = new SimpleTimer();
	public int[] degradableItem = new int[Degrade.MAXIMUM_ITEMS];
	public boolean[] claimDegradableItem = new boolean[Degrade.MAXIMUM_ITEMS];
	private Entity targeted;
	public int currentRender = 0;//render max 16
	public int maxRender = 16;//how far the rendering is allowed to go
	private Equipment equipment;

	public Equipment getEquipment() {
		return equipment;
	}

	public Inferno inferno = new Inferno(this, Boundary.INFERNO, 0);

	public Inferno getInfernoMinigame() {
		return inferno;
	}

	public Inferno createInfernoInstance() {
		Boundary boundary = Boundary.INFERNO;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 4);

		inferno = new Inferno(this, boundary, height);

		return inferno;
	}
	/**
	 * Integers
	 */
	@Getter
	public int combatLevel;
	public GroupIronman getGroupIronman() {
		return groupIronman;
	}

	public int raidPoints, unfPotHerb, unfPotAmount, wrenchObject = 0, halloweenOrderNumber = 0, speed1 = -1,
			speed2 = -1, safeBoxSlots = 15, hostAmount = 3, corpDamage = 0, direction = -1, dialogueOptions = 0,
			sireHits = 0, lastMenuChosen = 0, dreamTime, unNoteItemId = 0, lootValue = 0, lowMemoryVersion = 0, emote,
			gfx, timeOutCounter = 0, hitCount = 0, curseMax = 0, hydraAttackCount = 0, countUntilPoison = 0, returnCode = 2, currentRegion = 0, diceItem, page, specRestore = 0, gwdAltar = 0,
			lastClickedItem, slayerTasksCompleted, pestControlDamage, playerRank = 0, packetSize = 0, packetType = -1,
			makeTimes, event, ratsCaught, summonId, bossKills, droppedItem = -1, kbdCount, dagannothCount, krakenCount,
			chaosCount, armaCount, bandosCount, saraCount, zammyCount, barrelCount, moleCount, callistoCount,
			venenatisCount, vetionCount, RDragonCount, ADragonCount, rememberNpcIndex, diceMin, diceMax, otherDiceId, betAmount, totalProfit,
			betsWon, betsLost, slayerPoints = 0, playTime, killStreak, day, month, YEAR,
			smeltAmount = 0, smeltEventId = 5567, waveType, achievementsCompleted, achievementPoints, fireslit,
			crabsKilled, treesCut, pkp, killcount, deathcount, votePoints, bloodPoints, amDonated, level1 = 0,
			level2 = 0, level3 = 0, treeX, treeY, homeTele = 0, homeTeleDelay = 0, lastLoginDate, playerBankPin,
			recoveryDelay = 3, attemptsRemaining = 3, lastPinSettings = -1, setPinDate = -1, changePinDate = -1,
			deletePinDate = -1, firstPin, secondPin, thirdPin, fourthPin, bankPin1, bankPin2, bankPin3, bankPin4,
			pinDeleteDateRequested, saveDelay, playerKilled, totalPlayerDamageDealt, killedBy, lastChatId = 1,
			friendSlot = 0, dialogueId, specEffect, specBarId, attackLevelReq, defenceLevelReq, strengthLevelReq,
			rangeLevelReq, magicLevelReq, followId, skullTimer, votingPoints, nextChat = 0, talkingNpc = -1,
			dialogueAction = 0, autocastId, followDistance, followId2, barrageCount = 0, delayedDamage = 0,
			delayedDamage2 = 0, pcPoints = 0, skillPoints = 0, vorkathKillCount = 0, pvmPoints = 0, bossPoints = 0, loyaltyPoints = 0,
			donatorPoints = 0, magePoints = 0, lastArrowUsed = -1, clanId = -1,
			autoRet = 0, pcDamage = 0, xInterfaceId = 0, xRemoveId = 0, xRemoveSlot = 0, tzhaarToKill = 0,
			tzhaarKilled = 0, waveId, rfdWave = 0, rfdChat = 0, rfdGloves = 0, frozenBy = 0, teleAction = 0,
			newPlayerAct = 0, bonusAttack = 0, lastNpcAttacked = 0, killCount = 0, actionTimer, rfdRound = 0,
			roundNpc = 0, desertTreasure = 0, horrorFromDeep = 0, QuestPoints = 0, doricQuest = 0, teleGrabItem,
			teleGrabX, teleGrabY, duelCount, underAttackBy, underAttackBy2, wildLevel, teleTimer, respawnTimer,
			saveTimer = 0, teleBlockLength, focusPointX = -1, focusPointY = -1, WillKeepAmt1, WillKeepAmt2,
			WillKeepAmt3, WillKeepAmt4, WillKeepItem1, WillKeepItem2, WillKeepItem3, WillKeepItem4, WillKeepItem1Slot,
			WillKeepItem2Slot, WillKeepItem3Slot, WillKeepItem4Slot, EquipStatus, faceNPC = -1, DirectionCount = 0,
			itemUsing, attempts = 3, follow2 = 0, antiqueSelect = 0, leatherType = -1, DELAY = 1250, rangeEndGFX,
			boltDamage, teleotherType, playerTradeWealth, doAmount, woodcuttingTree, stageT, dfsCount, recoilHits,
			SlaughterKills, playerDialogue, clawDelay, previousDamage, prayerId = -1, headIcon = -1, bountyIcon = 0, headIconPk = -1,
			headIconHints, specMaxHitIncrease, freezeDelay, freezeTimer = -6, teleportTimer = 0, killerId, playerIndex,
			oldPlayerIndex, lastWeaponUsed, projectileStage, crystalBowArrowCount, cBowArrowCount, cHallyCount, cShieldCount, saeldorCount, playerMagicBook, teleGfx,
			serenCharge, sirenicMaskCharge, sirenicBodyCharge, sirenicChapsCharge, crawCharge, thammaronCharge, viggoraCharge, ethereumCharge,
			teleEndAnimation, teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex, totalDamageDealt, oldNpcIndex,
			fightMode, attackTimer, npcIndex, npcClickIndex, npcType, castingSpellId, oldSpellId, spellId, hitDelay,
			bowSpecShot, clickNpcType, clickObjectType, objectXOffset, objectYOffset,
			/*objectDistance,*/ objectWidth, objectLength, itemX, itemY, itemId, myShopId, tradeStatus, tradeWith, amountGifted,
			playerAppearance[] = new int[13], apset, actionID, wearItemTimer, wearId, wearSlot, interfaceId,
			XremoveSlot, XinterfaceID, XremoveID, Xamount, fishTimer = 0, smeltType, smeltTimer = 0, attackAnim,
			animationRequest = -1, animationWaitCycles, wcTimer = 0, miningTimer = 0, castleWarsTeam,
			npcId2 = 0, playerStandIndex = 0x328, playerTurnIndex = 0x337, playerWalkIndex = 0x333,
			playerTurn180Index = 0x334, playerTurn90CWIndex = 0x335, playerTurn90CCWIndex = 0x336,
			playerRunIndex = 0x338, playerHat = 0, playerCape = 1, playerAmulet = 2, playerWeapon = 3, playerChest = 4,
			playerShield = 5, playerLegs = 7, playerHands = 9, playerFeet = 10, playerRing = 12, playerArrows = 13,
			playerAttack = 0, playerDefence = 1, playerStrength = 2, playerHitpoints = 3, playerRanged = 4,
			playerPrayer = 5, playerMagic = 6, playerCooking = 7, playerWoodcutting = 8, playerFletching = 9,
			playerFishing = 10, playerFiremaking = 11, playerMining = 14, playerHerblore = 15, playerAgility = 16,
			playerThieving = 17, playerSlayer = 18, playerFarming = 19, playerRunecrafting = 20, playerHunting = 21, fletchingType,
			getHeightLevel, mapRegionX, mapRegionY, currentX, currentY, playerSE = 0x328,
			playerSEW = 0x333, playerSER = 0x334, npcListSize = 0, poimiX = 0, poimiY = 0,
			playerListSize = 0, wQueueReadPtr = 0, wQueueWritePtr = 0, teleportToX = -1, teleportToY = -1,
			pitsStatus = 0, safeTimer = 0, mask100var1 = 0, mask100var2 = 0, face = -1, FocusPointX = -1,
			FocusPointY = -1, newWalkCmdSteps = 0, tablet = 0, wellItem = -1, wellItemPrice = -1;
	private int chatTextColor = 0, chatTextEffects = 0, dragonfireShieldCharge, runEnergy = 100, lastEnergyRecovery,
			x1 = -1, y1 = -1, x2 = -1, y2 = -1, privateChat, shayPoints;
	private int arenaPoints;
	private int toxicStaffOfTheDeadCharge;
	private int toxicBlowpipeCharge;
	private int toxicBlowpipeAmmo;
	private int toxicBlowpipeAmmoAmount;
	private int serpentineHelmCharge;
	private int tridentCharge;
	private int toxicTridentCharge;
	private int sangStaffCharge;
	private int arcLightCharge;
	public int runningDistanceTravelled;
	private int interfaceOpen;

	public final int walkingQueueSize = 50;
	public static int playerCrafting = 12, playerSmithing = 13;
	protected int numTravelBackSteps = 0, packetsReceived;
	
	/*
	 * dailyReward ints for playersave
	 */
	public int lastDayClaimed;//last day of month player claimed daily reward
	public int dailyRewardCombo;//for how many days in a row player has logged on


	/**
	 * Arrays
	 */
	public ArrayList<int[]> coordinates;
	private int[] farmingSeedId = new int[14];
	private int[] farmingTime = new int[14];
	private int[] originalFarmingTime = new int[14];
	private int[] farmingState = new int[14];
	private int[] farmingHarvest = new int[14];
	public int[] halloweenRiddleGiven = new int[10], halloweenRiddleChosen = new int[10],
			masterClueRequirement = new int[4], waveInfo = new int[3], keepItems = new int[4], keepItemsN = new int[4],
			voidStatus = new int[5], itemKeptId = new int[4], pouches = new int[4], playerStats = new int[8],
			playerBonus = new int[12], death = new int[4], twoHundredMil = new int[21], woodcut = new int[7],
			farm = new int[2], playerEquipment = new int[14], playerEquipmentN = new int[14],
			damageTaken = new int[Config.MAX_PLAYERS], purchasedTeleport = new int[3],
			runeEssencePouch = new int[3], pureEssencePouch = new int[3];
	public int[] prestigeLevel = new int[25];
	public boolean[] skillLock = new boolean[25];
	public int playerItems[] = new int[28], playerItemsN[] = new int[28], bankItems[] = new int[Config.BANK_SIZE],
			bankItemsN[] = new int[Config.BANK_SIZE];
	public int counters[] = new int[20], raidsDamageCounters[] = new int[10];

	public boolean maxCape[] = new boolean[5];

	public int walkingQueueX[] = new int[walkingQueueSize], walkingQueueY[] = new int[walkingQueueSize];
	private int newWalkCmdX[] = new int[walkingQueueSize], newWalkCmdY[] = new int[walkingQueueSize];
	protected int travelBackX[] = new int[walkingQueueSize], travelBackY[] = new int[walkingQueueSize];
	public static final int maxPlayerListSize = Config.MAX_PLAYERS, maxNPCListSize = NPCHandler.maxNPCs;

	public Player playerList[] = new Player[maxPlayerListSize];
	public int[][] playerSkillProp = new int[20][15];
	public final int[] POUCH_SIZE = { 3, 6, 9, 12 };
	public static int[] ranks = new int[11];

	public boolean receivedStarter = false;

	/**
	 * Strings
	 */
	public String CERBERUS_ATTACK_TYPE = "";

	public String getATTACK_TYPE() {
		return CERBERUS_ATTACK_TYPE;
	}

	public void setATTACK_TYPE(String aTTACK_TYPE) {
		CERBERUS_ATTACK_TYPE = aTTACK_TYPE;
	}

	public String wrenchUsername = "", wogwOption = "", forcedText = "null", connectedFrom = "", quizAnswer = "",
			globalMessage = "", playerName = null, playerName2 = null, playerPass = null, date, clanName, properName,
			bankPin = "", lastReported = "", currentTime, barType = "", playerTitle = "", rottenPotatoOption = "";
	private String lastClanChat = "", revertOption = "";
	public static String[] rankPpl = new String[11];

	/**
	 * Booleans
	 */
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14], playerSkilling = new boolean[20],
			clanWarRule = new boolean[10], duelRule = new boolean[22];
	public boolean teleportingToDistrict = false,
			
			morphed = false,
			
			isIdle = false,
			
			boneOnAltar = false,
			
			dropRateInKills = true,
			
			droppingItem = false,
			
			acceptAid = false,
			
			settingUnnoteAmount = false,
			
			settingLootValue = false,
			
			didYouKnow = true, yellChannel = true,
			
			documentGraphic = false,
			
			documentAnimation = false,
			
			inProcess = false,
			
			isStuck = false,
			
			isBusyFollow = false,
			
			hasOverloadBoost,
			
			needsNewTask = false,
			
			keepTitle = false,
			
			killTitle = false,
			
			hide = false,
			
			settingMin,
			
			settingMax,
			
			settingBet,
			
			playerIsCrafting,
			
			viewingLootBag = false,
			
			addingItemsToLootBag = false,
			
			viewingRunePouch = false,
			
			hasFollower = false,
			
			updateItems = false,
			
			claimedReward,
			
			craftDialogue,
			
			battlestaffDialogue,
			
			braceletDialogue,
			
			isAnimatedArmourSpawned,
			
			isSmelting = false,
			
			hasEvent,
			
			expLock = false,
			
			buyingX, leverClicked = false,
			
			isBanking = true,
			
			inSafeBox = false,
			
			isCooking = false,
			
			initialized = false,
			
			disconnected = false,
			
			ruleAgreeButton = false,
			
			rebuildNPCList = false,
			
			isActive = false,
			
			isKicked = false,
			
			isSkulled = false,
			
			friendUpdate = false,
			
			newPlayer = false,
			
			hasMultiSign = false,
			
			saveCharacter = false,
			
			mouseButton = false,
			
			splitChat = false,
			
			chatEffects = true,
			
			nextDialogue = false,
			
			autocasting = false,
			
			usedSpecial = false,
			
			mageFollow = false,
			
			dbowSpec = false,
			
			craftingLeather = false,
			
			properLogout = false,
			
			secDbow = false,
			
			maxNextHit = false,
			
			ssSpec = false,
			
			vengOn = false,
			
			addStarter = false,
			
			startPack = false,
			
			accountFlagged = false,
			
			msbSpec = false,
			
			dtOption = false,
			
			dtOption2 = false,
			
			doricOption = false,
			
			doricOption2 = false,
			
			caOption2 = false,
			
			caOption2a = false,
			
			caOption4a = false,
			
			caOption4b = false,
			
			caOption4c = false,
			
			caPlayerTalk1 = false,
			
			horrorOption = false,
			
			rfdOption = false,
			
			inDt = false,
			
			inHfd = false,
			
			disableAttEvt = false,
			
			AttackEventRunning = false,
			
			npcindex, spawned = false,
			
			hasBankPin,
			
			enterdBankpin,
			
			firstPinEnter,
			
			requestPinDelete,
			
			secondPinEnter,
			
			thirdPinEnter,
			
			fourthPinEnter,
			
			hasBankpin,
			
			appearanceUpdateRequired = true,
			
			isDead = false,
			
			randomEvent = false,
			
			FirstClickRunning = false,
			
			WildernessWarning = false,
			
			storing = false, 
			
			canChangeAppearance = false,
			
			mageAllowed, 
			
			isFullBody = false,
			
			isFullHelm = false, 
			
			isFullMask = false,
			
			isOperate,
			
			usingLamp = false,
			
			normalLamp = false,
			
			antiqueLamp = false,
			
			setPin = false,
			
			teleporting,
			
			isWc,
			
			wcing,
			
			usingROD = false,
			
			multiAttacking,
			
			rangeEndGFXHeight,
			
			playerFletch,
			
			playerIsFletching,
			
			playerIsMining,
			
			playerIsFiremaking, 
			
			playerIsHunting,
			
			playerIsFishing,
			
			playerIsCooking, 
			
			below459 = true,
			
			defaultWealthTransfer,
			
			updateInventory,
			
			oldSpec,
			
			stopPlayerSkill,
			
			playerStun,
			
			stopPlayerPacket,
			
			usingClaws,
			
			playerBFishing,
			
			finishedBarbarianTraining,
			
			ignoreDefence,
			
			secondFormAutocast,
			
			usingArrows,
			
			usingOtherRangeWeapons,
			
			usingCross,
			
			usingBallista, 
			
			magicDef,
			
			spellSwap,
			
			recoverysSet,
			
			protectItem = false,
			
			doubleHit,
			
			usingSpecial,
			
			npcDroppingItems,
			
			usingRangeWeapon,
			
			usingBow,
			
			usingMagic, 
			
			usingAirSpells, 
			
			usingWaterSpells,
			
			usingFireSpells,
			
			usingMelee,
			
			magicFailed, 
			
			oldMagicFailed,
			
			isMoving,
			
			walkingToItem,
			
			isShopping,
			
			updateShop,
			
			forcedChatUpdateRequired,
			
			inDuel,
			
			tradeAccepted,
			
			goodTrade,
			
			inTrade,
			
			tradeRequested, 
			
			tradeResetNeeded,
			
			tradeConfirmed, 
			
			tradeConfirmed2, 
			
			canOffer,
			
			acceptTrade,
			
			acceptedTrade,
			
			smeltInterface, 
			
			patchCleared,
			
			saveFile = false,
			
			usingGlory = false, 
			
			usingSkills = false,
			
			isRunning2 = true,
			 
			takeAsNote,
			 
			inCwGame,
			 
			inCwWait,
			 
			isNpc,
			 
			seedPlanted = false,
			 
			seedWatered = false,
			
			patchCleaned = false,
			
			patchRaked = false,
			
			inPits = false,
			
			bankNotes = false,
			
			isRunning = true,
			
			inSpecMode = false,
			
			didTeleport = false, 
			
			mapRegionDidChange = false, 
			
			createItems = false,
			
			slayerHelmetEffect,
			
			inArdiCC,
			
			attackSkill = false, 
			
			strengthSkill = false,
			
			defenceSkill = false,
			
			mageSkill = false,
			
			rangeSkill = false,
			
			prayerSkill = false,
			
			healthSkill = false,
			
			usingChin,
			
			infoOn = false,
			
			pkDistrict = false,
			
			crystalDrop = false,
			
			hourlyBoxToggle = true,
			
			fracturedCrystalToggle = true,
			
			boltTips = false,
			
			arrowTips = false,
			
			javelinHeads = false;
	
	
	private boolean incentiveWarning,
	
			dropWarning = true,
			
			chatTextUpdateRequired = false,
			
			newWalkCmdIsRunning = false,
			
			dragonfireShieldActive,
			
			forceMovement,
			
			invisible, 
			
			godmode,
			
			safemode,
			
			trading,
			
			stopPlayer,
			
			isBusy = false,
			
			isBusyHP = false,
			
			forceMovementActive = false;

	public boolean insidePost = false;
	
	public boolean isBerserk = false;
	
	public boolean perkOn = false;

	/**
	 * @return the forceMovement
	 */
	public boolean isForceMovementActive() {
		return forceMovementActive;
	}

	protected boolean graphicMaskUpdate0x100 = false, faceUpdateRequired = false, faceNPCupdate = false;

	private final AchievementDiaryManager diaryManager = new AchievementDiaryManager(this);

	public int visibility = 0;
	/**
	 * Longs
	 */
	public long wogwDonationAmount, lastAuthClaim, totalGorillaDamage, totalGorillaHitsDone, totalMissedGorillaHits,
			lastImpling, lastWheatPass, lastRoll, lastCloseOfInterface, lastPerformedEmote, lastPickup, lastTeleport,
			lastMarkDropped, lastObstacleFail, lastForceMovement, lastDropTableSelected, lastDropTableSearch,
			lastDamageCalculation, lastBankDeposit, lastBankDeposit2, buySlayerTimer, buyPestControlTimer,
			fightCaveLeaveTimer, infernoLeaveTimer, lastFire, lastMove, bonusXpTime, yellDelay, craftingDelay,
			lastSmelt = 0, lastMysteryBox, DailyRecieved, lastYell, diceDelay, lastChat, lastRandom, lastCaught = 0, lastAttacked,
			lastTargeted, homeTeleTime, lastDagChange = -1, reportDelay, lastPlant, objectTimer, npcTimer, lastEss,
			lastClanMessage, lastCast, miscTimer, lastFlower, waitTime, saveButton = 0, lastButton, jailEnd, muteEnd,
			marketMuteEnd, lastReport = 0, stopPrayerDelay, prayerDelay, lastAntifirePotion, antifireDelay, lastSuperAntifirePotion, SuperantifireDelay,
			staminaDelay, lastRunRecovery, rangeDelay, stuckTime, friends[] = new long[200],
			lastUpdate = System.currentTimeMillis(), lastPlayerMove = System.currentTimeMillis(),
			lastHeart = 0, lastDerwenHeart = 0, lastJusticiarHeart = 0, lastPorazdirHeart = 0,
			lastSpear = System.currentTimeMillis(), lastProtItem = System.currentTimeMillis(),
			dfsDelay = System.currentTimeMillis(), lastVeng = System.currentTimeMillis(),
			foodDelay = System.currentTimeMillis(), switchDelay = System.currentTimeMillis(),
			potDelay = System.currentTimeMillis(), teleGrabDelay = System.currentTimeMillis(),
			protMageDelay = System.currentTimeMillis(), protMeleeDelay = System.currentTimeMillis(),
			protRangeDelay = System.currentTimeMillis(), lastAction = System.currentTimeMillis(),
			lastThieve = System.currentTimeMillis(), lastLockPick = System.currentTimeMillis(),
			alchDelay = System.currentTimeMillis(), specCom = System.currentTimeMillis(),
			specDelay = System.currentTimeMillis(), duelDelay = System.currentTimeMillis(),
			teleBlockDelay = System.currentTimeMillis(), godSpellDelay = System.currentTimeMillis(),
			singleCombatDelay = System.currentTimeMillis(), singleCombatDelay2 = System.currentTimeMillis(),
			reduceStat = System.currentTimeMillis(), restoreStatsDelay = System.currentTimeMillis(),
			logoutDelay = System.currentTimeMillis(), buryDelay = System.currentTimeMillis(),
			cerbDelay = System.currentTimeMillis(), cleanDelay = System.currentTimeMillis(),
			wogwDelay = System.currentTimeMillis();
	
	public long bonusXpTime25;//bonus XP scrolls
	public long bonusXpTime50;
	public long bonusXpTime75;
	public long bonusXpTime100;
	public long bonusXpTime150;
	public long bonusXpTime200;
	
	private long revertModeDelay, experienceCounter, bestZulrahTime, lastIncentive, lastOverloadBoost, nameAsLong,
			lastDragonfireShieldAttack;

	/**
	 * Others
	 */
	public ArrayList<String> killedPlayers = new ArrayList<String>(), lastConnectedFrom = new ArrayList<String>();
	public double specAmount = 0, specAccuracy = 1, specDamage = 1, prayerPoint = 1.0, crossbowDamage;
	public byte playerInListBitmap[] = new byte[(Config.MAX_PLAYERS + 7) >> 3],
			npcInListBitmap[] = new byte[(NPCHandler.maxNPCs + 7) >> 3],
			cachedPropertiesBitmap[] = new byte[(Config.MAX_PLAYERS + 7) >> 3];
	private byte chatText[] = new byte[4096], chatTextSize = 0;
	public NPC npcList[] = new NPC[maxNPCListSize];
	public ArrayList<Integer> attackedPlayers = new ArrayList<Integer>();
	private Map<Integer, TinterfaceText> interfaceText = new HashMap<>();

	@Override
	public String toString() {
		return "player[" + playerName + "]";
	}

	public Player(int index, String name, Channel channel) {
		super(index, name);
		this.session = channel;
		rights = new RightGroup(this, Right.PLAYER);

		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i = 0; i < playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i = 0; i < Config.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i = 0; i < Config.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}

		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 0; // head
		playerAppearance[2] = 18;// Torso
		playerAppearance[3] = 26; // arms
		playerAppearance[4] = 33; // hands
		playerAppearance[5] = 36; // legs
		playerAppearance[6] = 42; // feet
		playerAppearance[7] = 10; // beard
		playerAppearance[8] = 0; // hair colour
		playerAppearance[9] = 0; // torso colour
		playerAppearance[10] = 0; // legs colour
		playerAppearance[11] = 0; // feet colour
		playerAppearance[12] = 0; // skin colour

		apset = 0;
		actionID = 0;

		playerEquipment[playerHat] = -1;
		playerEquipment[playerCape] = -1;
		playerEquipment[playerAmulet] = -1;
		playerEquipment[playerChest] = -1;
		playerEquipment[playerShield] = -1;
		playerEquipment[playerLegs] = -1;
		playerEquipment[playerHands] = -1;
		playerEquipment[playerFeet] = -1;
		playerEquipment[playerRing] = -1;
		playerEquipment[playerArrows] = -1;
		playerEquipment[playerWeapon] = -1;

		setX(3093);
		setY(3508);
		setHeight(1);
		
		setLastKnownLocation(new Location(-1, -1, -1));

		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
		// synchronized(this) {
		outStream = new Buffer(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;

		inStream = new Buffer(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
		// }
	}

	public Player getClient(String name) {
		for (Player p : PlayerHandler.players) {
			if (p != null && p.playerName.equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	private Bank bank;

	public Bank getBank() {
		if (bank == null)
			bank = new Bank(this);
		return bank;
	}

	private BankPin pin;

	public BankPin getBankPin() {
		if (pin == null)
			pin = new BankPin(this);
		return pin;
	}

	public void sendMessage(String s, int color) {
		// synchronized (this) {
		if (getOutStream() != null) {
			s = "<col=" + color + ">" + s + "</col>";
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}
	}

	public Player getClient(int id) {
		return PlayerHandler.players[id];
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected || outStream.currentOffset == 0)
			return;

		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED, ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;
	}
	
	public void flushOutput() {
		
	}

	public class TinterfaceText {
		public int id;
		public String currentState;

		public TinterfaceText(String s, int id) {
			this.currentState = s;
			this.id = id;
		}

	}

	public boolean checkPacket126Update(String text, int id) {
		if (interfaceText.containsKey(id)) {
			TinterfaceText t = interfaceText.get(id);
			if (text.equals(t.currentState)) {
				return false;
			}
		}
		interfaceText.put(id, new TinterfaceText(text, id));
		return true;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		message = message.substring(0, 1).toUpperCase() + message.substring(1);
		clan = clan.substring(0, 1).toUpperCase() + clan.substring(1);
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public int VERIFICATION = 0;

	public void resetRanks() {
		for (int i = 0; i < 10; i++) {
			ranks[i] = 0;
			rankPpl[i] = "";
		}
	}

	public void highscores() {
		getPA().sendFrame126("Valius - Top PKers Online", 6399); // Title
		for (int i = 0; i < 10; i++) {
			if (ranks[i] > 0) {
				getPA().sendFrame126("Rank " + (i + 1) + ": " + rankPpl[i] + " - Kills: " + ranks[i] + "", 6402 + i);
			}
		}
		getPA().showInterface(6308);
		flushOutStream();
		resetRanks();
	}
	
	private boolean updatedHs = false;

	public void prelogout() {
		if (getInstance() != null) {
			getInstance().destroy();
		}
	}
	
	public void destruct() {
		Hunter.abandon(this, null, true);
		if (session == null) {
			return;
		}
		if (combatLevel >= 100) {
			if (Highpkarena.getState(this) != null) {
				Highpkarena.removePlayer(this, true);
			}
		} else if (combatLevel >= 80 && combatLevel <= 99) {
			if (Lowpkarena.getState(this) != null) {
				Lowpkarena.removePlayer(this, true);
			}
		}
		if (getXeric() != null) {
			getXeric().removePlayer(this);
		}
		if (getRaidsInstance() != null) {
			getRaidsInstance().logout(this);
		}
		if (zulrah.getInstancedZulrah() != null) {
			InstancedAreaManager.getSingleton().disposeOf(zulrah.getInstancedZulrah());
		}
		if (dagannothMother != null) {
			InstancedAreaManager.getSingleton().disposeOf(dagannothMother);
		}
		if (recipeForDisaster != null) {
			InstancedAreaManager.getSingleton().disposeOf(recipeForDisaster);
		}
		if (cerberus != null) {
			InstancedAreaManager.getSingleton().disposeOf(cerberus);
		}
		if (tzkalzuk != null) {
			InstancedAreaManager.getSingleton().disposeOf(tzkalzuk);
		}
		if (skotizo != null) {
			InstancedAreaManager.getSingleton().disposeOf(skotizo);
		}
		if (Vorkath.inVorkath(this)) {
			getPA().movePlayer(2272, 4052, 0);
		}
		if (getPA().viewingOtherBank) {
			getPA().resetOtherBank();
		}
		if (Boundary.isIn(this, PestControl.GAME_BOUNDARY)) {
			PestControl.removeGameMember(this);
		}
		if (Boundary.isIn(this, PestControl.LOBBY_BOUNDARY)) {
			PestControl.removeFromLobby(this);
		}
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;

		if (disconnected == true) {
			saveCharacter = true;
		}
		World.getWorld().getMultiplayerSessionListener().removeOldRequests(this);
		if (clan != null) {
			clan.removeMember(playerName);
		}
		World.getWorld().getEventHandler().stop(this);
		CycleEventHandler.getSingleton().stopEvents(this);
		getFriends().notifyFriendsOfUpdate();
		
		if(Config.enableHiscores) {
			
			 try {
					if (getRights().getPrimary().isOrInherits(Right.ADMINISTRATOR) || getRights().getPrimary().isOrInherits(Right.OWNER) || getRights().getPrimary().isOrInherits(Right.GAME_DEVELOPER) || getRights().getPrimary().isOrInherits(Right.YOUTUBER)) {
						log.info("{} [{}] will not be saved to the highscores.", this.getName(), this.getRights().getPrimary());
					} 
					//regular ironman
					else if(getMode().isIronman() && !getRights().isOrInherits(Right.EXTREME) && !getRights().isOrInherits(Right.CLASSIC) && !getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getMode().isIronman() && getRights().isOrInherits(Right.EXTREME)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Extreme Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getMode().isIronman() && getRights().isOrInherits(Right.CLASSIC)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Classic Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getMode().isIronman() && getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Elite Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					//ultimate ironman
					else if(getMode().isUltimateIronman() && !getRights().isOrInherits(Right.EXTREME) && !getRights().isOrInherits(Right.CLASSIC) && !getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Ultimate Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getMode().isUltimateIronman() && getRights().isOrInherits(Right.EXTREME)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Extreme Ultimate Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getMode().isUltimateIronman() && getRights().isOrInherits(Right.CLASSIC)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Classic Ultimate Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getMode().isUltimateIronman() && getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Elite Ultimate Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					//hardcore ironman
					else if(getMode().isHcIronman() && !getRights().isOrInherits(Right.EXTREME) && !getRights().isOrInherits(Right.CLASSIC) && !getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Hardcore Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getMode().isHcIronman() && (getRights().isOrInherits(Right.EXTREME))) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Extreme Hardcore Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getMode().isHcIronman() && (getRights().isOrInherits(Right.CLASSIC))) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Classic Hardcore Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getMode().isHcIronman() && (getRights().isOrInherits(Right.ELITE))) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Elite Hardcore Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					//group ironman
					else if (getRights().isOrInherits(Right.GROUP_IRONMAN)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Group Ironman", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					//regular
					else if (getRights().isOrInherits(Right.EXTREME) && !getRights().isOrInherits(Right.IRONMAN) && !getRights().isOrInherits(Right.HC_IRONMAN) && !getRights().isOrInherits(Right.ULTIMATE_IRONMAN) && !getRights().isOrInherits(Right.GROUP_IRONMAN)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Extreme", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else if (getRights().isOrInherits(Right.CLASSIC) && !getRights().isOrInherits(Right.IRONMAN) && !getRights().isOrInherits(Right.HC_IRONMAN) && !getRights().isOrInherits(Right.ULTIMATE_IRONMAN)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Classic", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getRights().isOrInherits(Right.ELITE) && !getRights().isOrInherits(Right.IRONMAN) && !getRights().isOrInherits(Right.HC_IRONMAN) && !getRights().isOrInherits(Right.ULTIMATE_IRONMAN)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Elite", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					}
					
					else if (getMode().isRegular() && !getRights().isOrInherits(Right.CLASSIC) && !getRights().isOrInherits(Right.EXTREME) && !getRights().isOrInherits(Right.ELITE)) {
						com.everythingrs.hiscores.Hiscores.update("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",  "Normal Mode", this.playerName, this.playerRank, this.skills.experienceToArray(), debugMessage);
					} 
					
					else {
						this.sendMessage("You are not any game mode...Please inform an admin");
					}
	            } catch(Exception ex) {
	            	ex.printStackTrace();
	            }
		}
		disconnected = true;
		// logoutDelay = Long.MAX_VALUE;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		playerListSize = 0;
		for (int i = 0; i < maxPlayerListSize; i++)
			playerList[i] = null;
		
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public void sendMessage(String s) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.writePacketHeader(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}

	}

	public int diaryAmount = 0;

	public int amountOfDiariesComplete() {
		if (getDiaryManager().getVarrockDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getArdougneDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getDesertDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getFaladorDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getFremennikDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getKandarinDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getKaramjaDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getLumbridgeDraynorDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getMorytaniaDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getWesternDiary().hasDoneAll())
			diaryAmount += 1;
		if (getDiaryManager().getWildernessDiary().hasDoneAll())
			diaryAmount += 1;

		return diaryAmount;
	}

	public void refreshQuestTab(int i) {

	}

	public void loadDiaryTab() {

	}

	private enum RankUpgrade {
		SAPPHIRE(Right.SAPPHIRE, 5),
		EMERALD(Right.EMERALD, 25),
		RUBY(Right.RUBY, 50),
		DIAMOND(Right.DIAMOND, 100),
		DRAGONSTONE(Right.DRAGONSTONE, 200),
		ONYX(Right.ONYX, 500),
		ZENYTE(Right.ZENYTE, 1000);

		/**
		 * The rights that will be appended if upgraded
		 */
		private final Right rights;

		/**
		 * The amount required for the upgrade
		 */
		private final int amount;

		private RankUpgrade(Right rights, int amount) {
			this.rights = rights;
			this.amount = amount;
		}
	}

	public void initialize() {
		try {

			questManager = new QuestManager(this);
			loadDiaryTab();
			graceSum();
			Achievements.checkIfFinished(this);
			getPA().loadQuests();
			setStopPlayer(false);
			//setNeedsPlacement(true);
			getPlayerAction().setAction(false);
			setNeedsPlacement(true);
			getPA().sendFrame126(runEnergy + "%", 149);
			isFullHelm = ItemUtility.isFullHat(playerEquipment[playerHat]);
			isFullMask = ItemUtility.isFullMask(playerEquipment[playerHat]);
			isFullBody = ItemUtility.isFullBody(playerEquipment[playerChest]);
			getPA().sendFrame36(173, isRunning() ? 1 : 0);
			getPA().setConfig(427, acceptAid ? 1 : 0);
			/**
			 * Welcome messages
			 */
			sendMessage("<img=16>@blu@Welcome to " + Config.SERVER_NAME + ".");
			sendMessage("<img=16>@blu@Make sure to join our ::Discord chat for Giveaways & to keep up to date on the server!");
			sendMessage("<img=16>@blu@Make sure to ::vote every day for awesome rewards. [NEW: VOTE for Donator!]");
			sendMessage("<img=16>@blu@Type ::Dperks to view the benefits of donating & ::Donate to donate for tokens!");
			//sendMessage("<img=16>@blu@<shad>Donator token purchases are now 25% OFF until July 31st!");
			if (DoubleExperience.isDoubleExperience(this)) {	
			sendMessage("@red@DOUBLE EXP IS: @gre@ ACTIVE!");
			}
			if (Config.BONUS_XP_WOGW == true) {
				sendMessage("@red@50% BONUS EXP IS: @gre@ ACTIVE!");
				}
			if (Config.DOUBLE_DROPS == true) {
				sendMessage("@red@DOUBLE DROPS ARE: @gre@ ACTIVE!");
				}
			if (Config.BONUS_PC == true || Config.BONUS_PC_WOGW) {
				sendMessage("@red@BONUS PEST CONTROL POINTS ARE: @gre@ ACTIVE!");
				}
			if (getSlayer().superiorSpawned) {
				getSlayer().superiorSpawned = false;
			}
			
			hourlyRewardBox.startEvent();//starts event/timer
			
			/**
			 * Log in alerts
			 */
			if (playerName.equalsIgnoreCase("eggy") && (getRights().isOrInherits(Right.OWNER))) {
				GlobalMessages.send("<img=2> Community Manager " + playerName + " has logged in.", GlobalMessages.MessageType.NEWS);
			} else if (getRights().isOrInherits(Right.GAME_DEVELOPER) || getRights().isOrInherits(Right.OWNER)) {
				GlobalMessages.send("<img=2> Developer " + playerName + " has logged in.", GlobalMessages.MessageType.NEWS);
			} else if (getRights().isOrInherits(Right.ADMINISTRATOR)) {
				GlobalMessages.send("<img=2> Administrator " + playerName + " has logged in.", GlobalMessages.MessageType.NEWS);
			} else if (getRights().isOrInherits(Right.MODERATOR)) {
				GlobalMessages.send("<img=0> Player Moderator " + playerName + " has logged in.", GlobalMessages.MessageType.NEWS);
			} else if (getRights().isOrInherits(Right.HELPER)) {
				GlobalMessages.send("<img=10> Player Support " + playerName + " has logged in.", GlobalMessages.MessageType.NEWS);
			}
			
			// checkWellOfGoodwillTimers();

			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN)) {
				ArrayList<RankUpgrade> orderedList = new ArrayList<>(Arrays.asList(RankUpgrade.values()));
				orderedList.sort((one, two) -> Integer.compare(two.amount, one.amount));
				orderedList.stream().filter(r -> amDonated >= r.amount).findFirst().ifPresent(rank -> {
					RightGroup rights = getRights();
					Right right = rank.rights;
					if (!rights.contains(right)) {
						sendMessage("@blu@Congratulations, your rank has been upgraded to " + right.toString() + ".");
						sendMessage("@blu@This rank is hidden, but you will have all it's perks.");
						rights.add(right);
					}
				});
			}
			//if (!Config.local) {
			//	PlayersOnline.createCon();
			//	PlayersOnline.online(this);
			//}
			combatLevel = calculateCombatLevel();
			outStream.writePacketHeader(249);
			outStream.writeByteA(1); // 1 for members, zero for free
			outStream.writeWordBigEndianA(getIndex());
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (j == getIndex())
					continue;
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);
			}

			getPA().handleWeaponStyle();
			accountFlagged = getPA().checkForFlags();
			getPA().sendFrame36(108, 0);
			getPA().resetCinematicCamera(); // reset screen
			setSidebarInterface(0, 2423);
			setSidebarInterface(1, 13917); // Skilltab > 3917
			setSidebarInterface(2, 10220); // 638
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 15608);
			setSidebarInterface(13, 47500);
			switch (playerMagicBook) {
			case 0:
				setSidebarInterface(6, 938); // modern
				break;

			case 1:
				setSidebarInterface(6, 838); // ancient
				break;

			case 2:
				setSidebarInterface(6, 29999); // ancient
				break;
			}

			if (hasFollower) {
				if (summonId > 0) {
					Pets pet = PetHandler.forItem(summonId);
					if (pet != null) {
						PetHandler.spawn(this, pet, true, false);
					}
				}
			}
			if (splitChat) {
				getPA().sendFrame36(502, 1);
				getPA().sendFrame36(287, 1);
			}
			setSidebarInterface(7, 18128);
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			setSidebarInterface(11, 42500); // wrench tab
			setSidebarInterface(12, 147); // run tab
			getPA().showOption(4, 0, "Follow", 3);
			getPA().showOption(5, 0, "Trade with", 4);
			// getPA().showOption(6, 0, nu, 5);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon],
					ItemAssistant.getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
			getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows], playerEquipmentN[playerArrows], playerArrows);
			getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
			getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
			getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
			getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
			getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
			getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon], playerEquipmentN[playerWeapon], playerWeapon);
			getCombat().getPlayerAnimIndex(ItemAssistant.getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPlayerAssistant().updateQuestTab();
			if (getPrivateChat() > 2) {
				setPrivateChat(0);
			}

			
			
			outStream.writePacketHeader(221);
			outStream.writeByte(2);

			outStream.writePacketHeader(206);
			outStream.writeByte(0);
			outStream.writeByte(getPrivateChat());
			outStream.writeByte(0);
			getFriends().sendList();
			getIgnores().sendList();
			
			GauntletPrepRoom.sendChest(this);

			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			
			World.getWorld().getPlayerHandler().updatePlayer(this, outStream);
			World.getWorld().getPlayerHandler().updateNPC(this, outStream);
			flushOutStream();
			getMovementQueue().handleRegionChange();
			updateQuestTab();
			skills.sendRefresh();
			getPA().sendFrame126("Combat Level: " + combatLevel + "", 3983);
			getPA().sendFrame126("Total level:", 19209);
			getPA().sendFrame126(skills.getTotalLevel() + "", 3984);
			getPA().resetFollow();
			getPA().clearClanChat();
			getPA().resetFollow();
			getPA().setClanData();
			updateRank();
			if (startPack == false) {
				getRights().remove(Right.IRONMAN);
				getRights().remove(Right.ULTIMATE_IRONMAN);
				getRights().remove(Right.HC_IRONMAN);
				startPack = true;
				World.getWorld().getClanManager().getHelpClan().addMember(this);
				tutorial.setStage(Stage.START);
				mode = Mode.forType(ModeType.REGULAR);
			} else {
				if (mode == null && tutorial.getStage() == null) {
					mode = Mode.forType(ModeType.REGULAR);
					tutorial.autoComplete();
				}
				World.getWorld().getClanManager().joinOnLogin(this);
			}
			if (tutorial.isActive()) {
				tutorial.refresh();
			}
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
			addEvents();
			if (Config.BOUNTY_HUNTER_ACTIVE) {
				bountyHunter.updateTargetUI();
			}
			health.setMaximum(skills.getActualLevel(Skill.HITPOINTS));
			BankPin pin = getBankPin();
			if (pin.requiresUnlock()) {
				pin.open(2);
			} else {
				if (!tutorial.isActive()) {
					dailyReward.getDay();
				}
			}
			
			initialized = true;
			
			if (health.getAmount() < 10) {
				health.setAmount(10);
			}
			int[] ids = new int[Skill.length()];
			for (int skillId = 0; skillId < ids.length; skillId++) {
				ids[skillId] = skillId;
			}
			if (experienceCounter > 0L) {
				playerAssistant.sendExperienceDrop(false, experienceCounter, ids);
			}
			
			rechargeItems.onLogin();
			for (int i = 0; i < getQuick().getNormal().length; i++) {
				if (getQuick().getNormal()[i]) {
					getPA().sendConfig(QuickPrayers.CONFIG + i, 1);
				} else {
					getPA().sendConfig(QuickPrayers.CONFIG + i, 0);
				}
			}
			CannonManager.checkForCannon(this);
			DailyTasks.complete(this);
			DailyTasks.assignTask(this);
			checkLocationOnLogin();
			questManager.sendQuests();
			GIMRepository.onLogin(this);
			makeWidget.onLogin();
		} catch (Exception e) {
			log.warn("Error while initializing player " + this.getName(),  e);
		}
	}

	public void updateQuestTab(){
		getPA().sendFrame126("@cr11@@or1@ Players online : @gre@"+PlayerHandler.getRealPlayerCount(),10407);
		getPA().sendFrame126("@cr22@@or1@ Wilderness count : @gre@"+Boundary.entitiesInArea(Boundary.WILDERNESS),10408);
		if(MonsterHunt.getCurrentLocation() != null){
			getPA().sendFrame126("@cr19@@or1@Current event : @gre@"+MonsterHunt.getName(),10409);
		}else{
			getPA().sendFrame126("@cr19@@or1@Current event : @red@None",10409);
		}
	
		if (Config.BONUS_XP_WOGW == true && !Config.BONUS_PC_WOGW && !Config.DOUBLE_DROPS) {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @gre@+50% Bonus XP", 10410);
		} else if (!Config.BONUS_XP_WOGW && !Config.BONUS_PC_WOGW && Config.DOUBLE_DROPS == true) {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @gre@2x Droprates", 10410);
		} else if (Config.BONUS_XP_WOGW == true && Config.BONUS_PC_WOGW == false && Config.DOUBLE_DROPS == true) {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @gre@+50% XP + 2x DR", 10410);
		} else if (Config.BONUS_XP_WOGW == false && Config.BONUS_PC_WOGW == true && Config.DOUBLE_DROPS == false) {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @gre@+5 Pest Points", 10410);
		} else if (Config.BONUS_XP_WOGW == true && Config.BONUS_PC_WOGW == true && Config.DOUBLE_DROPS == true) {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @gre@+50% XP + 2x DR + +5 PC", 10410);
		} else {
			getPA().sendFrame126("@cr18@@or1@ WOGW : @red@None",10410);
		}
		if (DoubleExperience.isDoubleExperience(this)) {	
			getPA().sendFrame126("@cr18@@or1@ Double XP : @gre@ON",10411);
		} else {
			getPA().sendFrame126("@cr18@@or1@ Double XP : @red@OFF",10411);
		}
		long milliseconds = (long) playTime * 600;
		long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
			long hours = TimeUnit.MILLISECONDS.toHours(milliseconds - TimeUnit.DAYS.toMillis(days));
			long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours));
			long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
		String time = days + " days, " + hours + " hrs";
		getPA().sendFrame126("@or1@@cr20@ Time Played = @gre@"+time,10225);
		getPA().sendFrame126("@or1@@cr1@ Player Rank = @gre@"+getRights().getPrimary().toString(),10226);
		if (getRights().isOrInherits(Right.EXTREME)) {
			getPA().sendFrame126("@or1@@cr16@ Game Mode = @gre@Extreme (15x)", 10227);
		} else if (getRights().isOrInherits(Right.CLASSIC)) {
			getPA().sendFrame126("@or1@@cr16@ Game Mode = @gre@Classic (5x)", 10227);
		} else if (getRights().isOrInherits(Right.ELITE)) {
			getPA().sendFrame126("@or1@@cr16@ Game Mode = @gre@Elite (2x)", 10227);
		} else if (getRights().isOrInherits(Right.GROUP_IRONMAN)) {
			getPA().sendFrame126("@or1@@cr24@ Game Mode = @gre@Group Ironman (5x)", 10227);
		} else {
			getPA().sendFrame126("@or1@@cr16@ Game Mode = @gre@Normal (30x)", 10227);
		}
		getPA().sendFrame126("@or1@@cr18@ Player Drop Bonus = @gre@" + DropManager.getModifier1(this) + "%",10228);
		getPA().sendFrame126("@or1@@cr15@ Player Title = @gre@"+titles.getCurrentTitle(),10229);
		getPA().sendFrame126("@or1@@cr21@ KDR = @gre@"+ (double)(this.deathcount == 0 ? this.killcount + this.deathcount : this.killcount/this.deathcount),10230);
		getPA().sendFrame126("@or1@@cr8@ Amount donated = @gre@$" + this.amDonated,10231);
		getPA().sendFrame126("@or1@@cr22@  PK Points = @gre@" +this.pkp,10232);
		getPA().sendFrame126("@or1@@cr22@  Slayer Points = @gre@" +this.getSlayer().getPoints(),10233);
		getPA().sendFrame126("@or1@@cr17@ PC points = @gre@" +this.pcPoints,10234);
		getPA().sendFrame126("@or1@@cr19@ Skill points = @gre@" + this.skillPoints,10235);
		getPA().sendFrame126("@or1@@cr18@ Vote points = @gre@" + this.votePoints,10236);
		getPA().sendFrame126("@or1@@cr22@  PVM points = @gre@" + this.pvmPoints,10237);
		getPA().sendFrame126("@or1@@cr22@  Boss points = @gre@" + this.bossPoints,10238);
		getPA().sendFrame126("@or1@@cr9@ Loyalty points = @gre@" + this.loyaltyPoints,10239);


		getPA().sendFrame126("@or1@View the forums",47514);
		getPA().sendFrame126("@or1@Join our Discord",47515);
		getPA().sendFrame126("@or1@Buy Valius Tokens",47516);
		getPA().sendFrame126("@or1@View our Youtube Channel",47517);
		getPA().sendFrame126("@or1@View community guides ",47518);
		getPA().sendFrame126("@or1@Valius Price Guide ",47519);

	}
	public void addEvents() {
		World.getWorld().getEventHandler().submit(new MinigamePlayersEvent(this));
		World.getWorld().getEventHandler().submit(new SkillRestorationEvent(this));
		World.getWorld().getEventHandler().submit(new IronmanRevertEvent(this, 50));
		World.getWorld().getEventHandler().submit(new RunEnergyEvent(this, 1));
		CycleEventHandler.getSingleton().addEvent(this, bountyHunter, 1);
		CycleEventHandler.getSingleton().addEvent(CycleEventHandler.Event.PLAYER_COMBAT_DAMAGE, this, damageQueue, 1,
				true);
	}

	public void update() {
		World.getWorld().getPlayerHandler().updatePlayer(this, outStream);
		World.getWorld().getPlayerHandler().updateNPC(this, outStream);
		flushOutStream();

	}

	public void wildyWarning() {
		getPA().showInterface(1908);
	}

	/**
	 * Update {@link #equippedGodItems}, which is a list of all gods of which the
	 * player has at least 1 item equipped.
	 */
	
	public void updateGodItems() {
		equippedGodItems = new ArrayList<>();
		for (God god : God.values()) {
			for (Integer itemId : GodwarsEquipment.EQUIPMENT.get(god)) {
				if (getItems().isWearingItem(itemId)) {
					equippedGodItems.add(god);
					break;
				}
			}
		}
	}

	public List<God> getEquippedGodItems() {
		return equippedGodItems;
	}

	public void logout() {
		if (!isIdle && underAttackBy2 > 0) {
			sendMessage("You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (underAttackBy > 0) {
			sendMessage("You can't log out until 10 seconds after the end of combat.");
			return;
		}
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(this,
				MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST) {
			if (duelSession.getStage().getStage() >= MultiplayerSessionStage.FURTHER_INTERATION) {
				sendMessage("You are not permitted to logout during a duel. If you forcefully logout you will");
				sendMessage("lose all of your staked items, if any, to your opponent.");
				return;
			}
		}
		if (this.clan != null) {
			this.clan.removeMember(playerName);
		}
		if (Vorkath.inVorkath(this)) {
			this.getPA().movePlayer(2272, 4052, 0);
		}
		if (getPA().viewingOtherBank) {
			getPA().resetOtherBank();
		}
		
		if(this.getItems().isWearingItem(22516)) {
			this.getItems().setEquipment(-1, 0, playerWeapon);
		}
		
		if(this.getItems().playerHasItem(22516)) {
			this.getItems().deleteItem2(22516, 28);
		}
		
		cannon.onLogout();
		
	
		if (System.currentTimeMillis() - logoutDelay > 5000) {
			Hunter.abandon(this, null, true);
			outStream.writePacketHeader(109);
			flushOutStream();
			if (skotizo != null)
				skotizo.end(DisposeTypes.INCOMPLETE);
			CycleEventHandler.getSingleton().stopEvents(this);
			properLogout = true;
			disconnected = true;
			ConnectedFrom.addConnectedFrom(this, connectedFrom);
		}
	}

	public int totalRaidsFinished;
	public int totalXericFinished;
	public int totalTheatreFinished;

	public boolean hasClaimedRaidChest;

	public int[] SLAYER_HELMETS = { 11864, 11865, 19639, 19641, 19643, 19645, 19647, 19649, 21888, 21890, 21264, 21266 };
	public int[] IMBUED_SLAYER_HELMETS = { 11865, 19641, 19645, 19649, 21890, 21266 };

	public int[] GRACEFUL = { 11850, 11852, 11854, 11856, 11858, 11860, 13579, 13581, 13583, 13585, 13587, 13589, 13591,
			13593, 13595, 13597, 13599, 13601, 13603, 13605, 13607, 13609, 13611, 13613, 13615, 13617, 13619, 13621,
			13623, 13625, 13627, 13629, 13631, 13633, 13635, 13637, 13667, 13669, 13671, 13673, 13675, 13677, 21061,
			21064, 21067, 21070, 21073, 21076 };

	private boolean wearingGrace() {
		return getItems().isWearingAnyItem(GRACEFUL);
	}

	public int graceSum = 0;

	public void graceSum() {
		graceSum = 0;
		for (int grace : GRACEFUL) {
			if (getItems().isWearingItem(grace)) {
				graceSum++;
			}
		}
		if (SkillcapePerks.AGILITY.isWearing(this) || SkillcapePerks.isWearingMaxCape(this)) {
			graceSum++;
		}
	}

	public int olmType, leftClawType, rightClawType;

	public boolean leftClawDead;
	public boolean rightClawDead;

	public boolean hasSpawnedOlm;
	
	public DwarfCannon cannon = new DwarfCannon(this);

	public void process() {
		farming.farmingProcess();
		cannon.process();
		if (isRunning() && runEnergy <= 0) {
			setRunning(false);
			playerAssistant.sendFrame126(Integer.toString(runEnergy) + "%", 149);
			playerAssistant.setConfig(173, 0);
		}

		if (staminaDelay > 0) {
			staminaDelay--;
		}

		if (gwdAltar > 0) {
			gwdAltar--;
		}
		if (gwdAltar == 1) {
			sendMessage("You can now operate the godwars prayer altar again.");
		}

		if (isRunning() && runningDistanceTravelled > (wearingGrace() ? 1 + graceSum : staminaDelay != -1 ? 0.85 + (this.getSkills().getLevel(Skill.AGILITY) / 60)
																										: 0.35 + (this.getSkills().getLevel(Skill.AGILITY) / 60))) {
			this.getSkills().getLevel(Skill.AGILITY);
			if (!(getItems().isWearingItem(12637) || getItems().isWearingItem(12638) || getItems().isWearingItem(12639))) {//halos
				runningDistanceTravelled = 0;
				runEnergy -= 1;
				playerAssistant.sendFrame126(Integer.toString(runEnergy) + "%", 149);
			}
		}
		
		//Bonus XP scrolls
		if (bonusXpTime25 > 0) {
			bonusXpTime25--;
		}
		if (bonusXpTime25 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 25% XP boost");
		}
		if (bonusXpTime25 == 1) {
			sendMessage("@blu@Your 25% XP boost is now over.");
		}
		
		if (bonusXpTime50 > 0) {
			bonusXpTime50--;
		}
		if (bonusXpTime50 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 50% XP boost");
		}
		if (bonusXpTime50 == 1) {
			sendMessage("@blu@Your 50% XP boost is now over.");
		}
		
		if (bonusXpTime75 > 0) {
			bonusXpTime75--;
		}
		if (bonusXpTime75 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 75% XP boost");
		}
		if (bonusXpTime75 == 1) {
			sendMessage("@blu@Your 75% XP boost is now over.");
		}
		
		if (bonusXpTime100 > 0) {
			bonusXpTime100--;
		}
		if (bonusXpTime100 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 100% XP boost");
		}
		if (bonusXpTime100 == 1) {
			sendMessage("@blu@Your your 100% XP boost is now over.");
		}
		
		if (bonusXpTime150 > 0) {
			bonusXpTime150--;
		}
		if (bonusXpTime150 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 150% XP boost");
		}
		if (bonusXpTime150 == 1) {
			sendMessage("@blu@Your 150% XP boost is now over.");
		}
		
		if (bonusXpTime200 > 0) {
			bonusXpTime200--;
		}
		if (bonusXpTime200 == 60) {
			sendMessage("@blu@You have 1 Minute left of your 200% XP boost");
		}
		if (bonusXpTime200 == 1) {
			sendMessage("@blu@Your 200% XP boost is now over.");
		}
		//END bonus XP scrolls

		if (updateItems) {
			itemAssistant.updateItems();
		}
		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}
		if (bonusXpTime > 0) {
			bonusXpTime--;
		}
		if (bonusXpTime == 1) {
			sendMessage("@blu@Your time is up. Your XP is no longer boosted by the voting reward.");
		}
		
		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
		}
		if (Boundary.isIn(this, Zulrah.BOUNDARY) && getZulrahEvent().isInToxicLocation()) {
			appendDamage(1 + Misc.random(3), Hitmark.VENOM);
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (hitDelay > 0) {
			hitDelay--;
		}

		getAgilityHandler().agilityProcess(this);

		if (specRestore > 0) {
			specRestore--;
		}

		if (rangeDelay > 0) {
			rangeDelay--;
		}
		if (playTime < Integer.MAX_VALUE && !isIdle) {
			playTime++;
		}

		//getPA().sendFrame126("@or1@Players Online: @gre@" + PlayerHandler.getPlayerCount() + "", 10222);
		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += 1;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		getCombat().handlePrayerDrain();
		if (System.currentTimeMillis() - singleCombatDelay > 5000) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 5000) {
			underAttackBy2 = 0;
		}
		if (hasOverloadBoost) {
			if (System.currentTimeMillis() - lastOverloadBoost > 15000) {
				getPotions().doOverloadBoost();
				lastOverloadBoost = System.currentTimeMillis();
			}
		}

		if (Boundary.isIn(this, Boundary.CHRISTMAS)) {
				getPA().walkableInterface(11877);
		} else {
		if (inWild() && Boundary.isIn(this, Boundary.SAFEPK)) {
			int modY = getY() > 6400 ? getY() - 6400 : getY();
			wildLevel = (((modY - 3520) / 8) + 1);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
			if (Config.BOUNTY_HUNTER_ACTIVE && !inClanWars()) {
				getPA().walkableInterface(28000);
				getPA().sendFrame171(1, 28070);
				getPA().sendFrame171(0, 196);
			} else {
				getPA().walkableInterface(197);
			}
		} else if (inWild() && !inClanWars() && !Boundary.isIn(this, Boundary.SAFEPK)) {
			int modY = getY() > 6400 ? getY() - 6400 : getY();
			wildLevel = (((modY - 3520) / 8) + 1);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
			if (Config.BOUNTY_HUNTER_ACTIVE && !inClanWars()) {
				getPA().walkableInterface(28000);
				getPA().sendFrame171(1, 28070);
				getPA().sendFrame171(0, 196);
			} else {
				getPA().walkableInterface(197);
			}

			// } else if (Boundary.isIn(this, Boundary.SKELETAL_MYSTICS)) {
			// getPA().walkableInterface(42300);
		} else if (inClanWars() && inWild()) {
			getPA().showOption(3, 0, "Attack", 1);
			getPA().walkableInterface(197);
			getPA().sendFrame126("@yel@3-126", 199);
			wildLevel = 126;
		} else if (Boundary.isIn(this, Boundary.SCORPIA_LAIR)) {
			getPA().sendFrame126("@yel@Level: 54", 199);
			// getPA().walkableInterface(197);
			wildLevel = 54;
		} else if (getItems().isWearingItem(10501, 3) && !inWild()) {
			getPA().showOption(3, 0, "Throw-At", 1);
		} else if (inEdgeville()) {
			if (Config.BOUNTY_HUNTER_ACTIVE) {
				if (bountyHunter.hasTarget()) {
					getPA().walkableInterface(28000);
					getPA().sendFrame171(0, 28070);
					getPA().sendFrame171(1, 196);
					bountyHunter.updateOutsideTimerUI();
				} else {
					getPA().walkableInterface(-1);
				}
			} else {
				getPA().sendFrame99(0);
				getPA().walkableInterface(-1);
				getPA().showOption(3, 0, "Null", 1);
			}
			getPA().showOption(3, 0, "null", 1);
		} else if (Boundary.isIn(this, PestControl.LOBBY_BOUNDARY)) {
			getPA().walkableInterface(21119);
			PestControl.drawInterface(this, "lobby");
		} else if (Boundary.isIn(this, PestControl.GAME_BOUNDARY)) {
			getPA().walkableInterface(21100);
			PestControl.drawInterface(this, "game");
		} else if ((inDuelArena() || Boundary.isIn(this, Boundary.DUEL_ARENA))) {
			getPA().walkableInterface(201);
			if (Boundary.isIn(this, Boundary.DUEL_ARENA)) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
			wildLevel = 126;
		} else if (barrows.inBarrows()) {
			barrows.drawInterface();
			getPA().walkableInterface(27500);
		} else if (inGodwars()) {
			godwars.drawInterface();
			getPA().walkableInterface(16210);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		} else if (Boundary.isIn(this, Boundary.SKOTIZO_BOSSROOM)) {
			getPA().walkableInterface(29230);
		} else {
			boolean inLobby = Stream.of(LobbyType.values()).anyMatch(lobbyType -> {
				Optional<Lobby> lobby = LobbyManager.get(lobbyType);
				if(lobby.isPresent())
					return lobby.get().inLobby(this);
				return false;
			});
			if(!inLobby) {
				getPA().walkableInterface(-1);
				getPA().showOption(3, 0, "Null", 1);
			}
		}
		if (Boundary.isIn(this, Barrows.TUNNEL)) {
			if (!World.getWorld().getEventHandler().isRunning(this, "barrows_tunnel")) {
				World.getWorld().getEventHandler().submit(new TunnelEvent("barrows_tunnel", this, 1));
			}
			getPA().sendFrame99(2);
		} else {
			if (World.getWorld().getEventHandler().isRunning(this, "barrows_tunnel")) {
				World.getWorld().getEventHandler().stop(this, "barrows_tunnel");
			}
			getPA().sendFrame99(0);
		}

		if (getMode().isGroupIronman() && getGroupIronman().isLeader() && !getGroupIronman().isTeamFull()) {
			getPA().showOption(2, 0, "Invite to group", 1);
		}
		
		if (Boundary.isIn(this, Boundary.PURO_PURO)) {
			getPA().sendFrame99(2);
		}

		if (Boundary.isIn(this, Boundary.ICE_PATH)) {
			getPA().sendFrame99(2);
			if (getRunEnergy() > 0)
				setRunEnergy(0);
			if (getHeight() > 0)
				getPA().icePath();
		}

		if (!inWild()) {
			wildLevel = 0;
		}
		if(Boundary.isIn(this, Boundary.EDGEVILLE_PERIMETER) && !Boundary.isIn(this, Boundary.EDGE_BANK) && getHeight() == 8){
			wildLevel=126;
		}
		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}
		if (!inMulti() && inWild())
			getPA().sendFrame70(30, 0, 196);
		else if (inMulti() && inWild())
			getPA().sendFrame70(0, 0, 196);
		if (this.skullTimer > 0) {
			--skullTimer;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}
		}

		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(getX(), getY(), PlayerHandler.players[frozenBy].getX(),
						PlayerHandler.players[frozenBy].getY(), 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1) {
					teleTimer = 0;
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}
		
		if (getTheatreInstance() != null) {
			getTheatreInstance().process(this);
		}

//		if (followId > 0) {
//			getPA().followPlayer();
//		} else if (followId2 > 0) {
//			getPA().followNpc();
//		}
		if (targeted != null) {
			if (distanceToPoint(targeted.getX(), targeted.getY()) > 16) {
				getPA().sendEntityTarget(0, targeted);
				targeted = null;
			}
		}
		if (attackTimer <= 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		}
		if (underAttackBy <= 0 && underAttackBy2 <= 0 && !inMulti() && lastAttacked < System.currentTimeMillis() - 4000
				&& lastTargeted < System.currentTimeMillis() - 4000) {
			if (!isIdle) {
				Optional<NPC> nearNPC = Stream.of(NPCHandler.npcs)
						.filter(Objects::nonNull)
						.filter(npc -> isTargetableBy(npc))
						.filter(npc -> npc.killerId != index)
						.filter(npc -> Misc.distanceToPoint(getX(), getY(), npc.getX(), npc.getY()) <= 50)
						.sorted((firstNPC, secondNPC) -> firstNPC.getLocation().getDistance(getLocation()) < secondNPC.getLocation().getDistance(getLocation()) ? -1 : 1)
						.findFirst();
				nearNPC.ifPresent(closestNPC -> {
					closestNPC.killerId = getIndex();
					underAttackBy = closestNPC.getIndex();
					underAttackBy2 = closestNPC.getIndex();
					lastTargeted = System.currentTimeMillis();
				});
			
			}
		}
		eventQueue.forEach(r -> r.run());
		eventQueue.clear();
	}

	public boolean isTargetableBy(NPC npc) {
		return !npc.isDead && World.getWorld().getNpcHandler().isAggressive(npc.getIndex(), false) && !npc.underAttack
				&& npc.killerId <= 0 && npc.getHeight() == getHeight();
	}

	public Buffer getInStream() {
		return inStream;
	}

	public int getPacketType() {
		return packetType;
	}

	public int getPacketSize() {
		return packetSize;
	}

	public Buffer getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ChargeTrident getCT() {
		return chargeTrident;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public CombatAssistant getCombat() {
		return combat;
	}

	public Killstreak getStreak() {
		return killingStreak;
	}

	public Channel getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public boolean checkBusy() {
		/*
		 * if (getCombat().isFighting()) { return true; }
		 */
		if (isBusy) {
			// actionAssistant.sendMessage("You are too busy to do that.");
		}
		return isBusy;
	}

	public boolean checkBusyHP() {
		return isBusyHP;
	}

	public boolean checkBusyFollow() {
		return isBusyFollow;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyFollow(boolean isBusyFollow) {
		this.isBusyFollow = isBusyFollow;
	}

	public void setBusyHP(boolean isBusyHP) {
		this.isBusyHP = isBusyHP;
	}

	public boolean isBusyHP() {
		return isBusyHP;
	}

	public boolean isBusyFollow() {
		return isBusyFollow;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public SkillInterfaces getSI() {
		return skillInterfaces;
	}

	public int getRuneEssencePouch(int index) {
		return runeEssencePouch[index];
	}

	public void setRuneEssencePouch(int index, int runeEssencePouch) {
		this.runeEssencePouch[index] = runeEssencePouch;
	}

	public int getPureEssencePouch(int index) {
		return pureEssencePouch[index];
	}

	public void setPureEssencePouch(int index, int pureEssencePouch) {
		this.pureEssencePouch[index] = pureEssencePouch;
	}

	public Slayer getSlayer() {
		if (slayer == null) {
			slayer = new Slayer(this);
		}
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public Agility getAgility() {
		return agility;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public Barrows getBarrows() {
		return barrows;
	}

	public Godwars getGodwars() {
		return godwars;
	}

	public TreasureTrails getTrails() {
		return trails;
	}

	public GnomeAgility getGnomeAgility() {
		return gnomeAgility;
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	public WildernessAgility getWildernessAgility() {
		return wildernessAgility;
	}

	public Shortcuts getAgilityShortcuts() {
		return shortcuts;
	}

	public RooftopSeers getRoofTopSeers() {
		return rooftopSeers;
	}

	public RooftopFalador getRoofTopFalador() {
		return rooftopFalador;
	}

	public RooftopVarrock getRoofTopVarrock() {
		return rooftopVarrock;
	}

	public RooftopArdougne getRoofTopArdougne() {
		return rooftopArdougne;
	}

	public Lighthouse getLighthouse() {
		return lighthouse;
	}

	public BarbarianAgility getBarbarianAgility() {
		return barbarianAgility;
	}

	public AgilityHandler getAgilityHandler() {
		return agilityHandler;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public FightCave getFightCave() {
		if (fightcave == null)
			fightcave = new FightCave(this);
		return fightcave;
	}
	
	//public void setTournament(Tournament tournament) {
	//	this.tournament = tournament;
	//}

	public DagannothMother getDagannothMother() {
		return dagannothMother;
	}

	public DemonicGorilla getDemonicGorilla() {
		return demonicGorilla;
	}

	public RecipeForDisaster getrecipeForDisaster() {
		return recipeForDisaster;
	}

	public Cerberus getCerberus() {
		return cerberus;
	}
	
	public Vorkath getVorkath() {//VORKATH player instance
		return vorkath;
	}
	
	public Fishing getFishing() {//fishing player instance
		return fishing;
	}

	public Tzkalzuk getInferno() {
		return tzkalzuk;
	}

	public Skotizo getSkotizo() {
		return skotizo;
	}

	public InstanceSoloFight getSoloFight() {
		return soloFight;
	}

	public DagannothMother createDagannothMotherInstance() {
		Boundary boundary = Boundary.LIGHTHOUSE;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeight(boundary);

		dagannothMother = new DagannothMother(this, boundary, height);

		return dagannothMother;
	}

	public RecipeForDisaster createRecipeForDisasterInstance() {
		Boundary boundary = Boundary.RFD;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 2);

		recipeForDisaster = new RecipeForDisaster(this, boundary, height);

		return recipeForDisaster;
	}

	public Cerberus createCerberusInstance() {
		Boundary boundary = Boundary.BOSS_ROOM_WEST;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 4);

		cerberus = new Cerberus(this, boundary, height);

		return cerberus;
	}

	public Tzkalzuk createTzkalzukInstance() {
		Boundary boundary = Boundary.INFERNO;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 4);

		tzkalzuk = new Tzkalzuk(this, boundary, height);

		return tzkalzuk;
	}

	public Skotizo createSkotizoInstance() {
		Boundary boundary = Boundary.SKOTIZO_BOSSROOM;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 4);

		skotizo = new Skotizo(this, boundary, height);

		return skotizo;
	}

	public InstanceSoloFight createSoloFight() {
		Boundary boundary = Boundary.FIGHT_ROOM;

		int height = InstancedAreaManager.getSingleton().getNextOpenHeightCust(boundary, 4);

		soloFight = new InstanceSoloFight(this, boundary, height);

		return soloFight;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public int getPrestigePoints() {
		return prestigePoints;
	}
	/*
	 * public Fletching getFletching() { return fletching; }
	 */

	public Prayer getPrayer() {
		return prayer;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		packetsReceived++;
		queuedPackets.add(arg1);
	}

	public boolean processQueuedPackets() {
		Packet p = null;
		int processed = 0;
		packetsReceived = 0;
		while ((p = queuedPackets.poll()) != null) {
			if (processed > Config.MAX_INCOMING_PACKETS_PER_CYCLE) {
				break;
			}
			inStream.currentOffset = 0;
			packetType = p.getOpcode();
			packetSize = p.getLength();
			inStream.buffer = p.getPayload().array();
			if (packetType > 0) {
				PacketHandler.processPacket(this, packetType, packetSize);
				processed++;
			}
		}
		return true;
	}

	public void correctCoordinates() {
		if (Boundary.isIn(this, PestControl.GAME_BOUNDARY)) {
			setX(2657);
			setY(2639);
			setHeight(0);
		}
		if (Boundary.isIn(this, Boundary.XERIC) || Boundary.isIn(this, Boundary.XERIC_LOBBY) || Boundary.isIn(this, Boundary.THEATRE) || Boundary.isIn(this, Boundary.THEATRE_LOBBY)) {
			setX(3050);
			setY(9950);
			setHeight(0);
			getItems().deleteItem(22516, 28);
			if (getItems().isWearingItem(22516)) {
				getItems().deleteEquipment(-1, EquipmentSlot.WEAPON.getIndex());
			}
		}
		if (Boundary.isIn(this, Boundary.FIGHT_CAVE)) {
			setHeight(getIndex() * 4);
			sendMessage("Wave " + (this.waveId + 1) + " will start in approximately 5-10 seconds. ");
			getFightCave().spawn();
		}
		if (Boundary.isIn(this, Zulrah.BOUNDARY)) {
			setX(Config.EDGEVILLE_X);
			setY(Config.EDGEVILLE_Y);
			setHeight(0);
		}
	}

	public void updateRank() {
		if (amDonated <= 0) {
			amDonated = 0;
		}
		if (amDonated >= 5 && amDonated < 25) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.SAPPHIRE);
				
				sendMessage("Your hidden donator rank is now active.");
			} else {
				getRights().setPrimary(Right.SAPPHIRE);
				sendMessage("Please relog to receive your donator rank.");
			}
		}
		if (amDonated >= 25 && amDonated < 50) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN)  || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.EMERALD);
				sendMessage("Your hidden super donator rank is now active.");
			} else {
				getRights().setPrimary(Right.EMERALD);
				sendMessage("Please relog to receive your super donator rank.");
			}
		}
		if (amDonated >= 50 && amDonated < 100) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.RUBY);
				sendMessage("Your hidden extreme donator rank is now active.");
			} else {
				getRights().setPrimary(Right.RUBY);
				sendMessage("Please relog to receive your extreme donator rank.");
			}
		}
		if (amDonated >= 100 && amDonated < 200) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.DIAMOND);
				sendMessage("Your hidden donator rank is now active.");
			} else {
				getRights().setPrimary(Right.DIAMOND);
				sendMessage("Please relog to receive your donator rank.");
			}
		}
		if (amDonated > 100) {
			//sendMessage("@red@You can now use the ::BANK command!");
		}
		if (amDonated >= 200 && amDonated < 500) {
			if (getRights().isOrInherits(Right.IRONMAN)  || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.DRAGONSTONE);
				sendMessage("Your hidden super donator rank is now active.");
			} else {
				getRights().setPrimary(Right.DRAGONSTONE);
				sendMessage("Please relog to receive your super donator rank.");
			}
		}
		if (amDonated >= 500 && amDonated < 1000) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.ONYX);
				sendMessage("Your hidden extreme donator rank is now active.");
			} else {
				getRights().setPrimary(Right.ONYX);
				sendMessage("Please relog to receive your extreme donator rank.");
			}
		}
		if (amDonated >= 1000) {
			if (getRights().isOrInherits(Right.IRONMAN) || getRights().isOrInherits(Right.GROUP_IRONMAN) || getRights().isOrInherits(Right.ULTIMATE_IRONMAN) || getRights().isOrInherits(Right.HC_IRONMAN) || getRights().isOrInherits(Right.HELPER) || getRights().isOrInherits(Right.MODERATOR)) {
				getRights().add(Right.ZENYTE);
				sendMessage("Your hidden legendary donator rank is now active.");
			} else {
				getRights().setPrimary(Right.ZENYTE);
				sendMessage("Please relog to receive your legendary donator rank.");
			}
		}
		sendMessage("Your updated total amount donated is now $" + amDonated + ".");
	}

	public int getPrivateChat() {
		return privateChat;
	}

	public Friends getFriends() {
		return friend;
	}

	public Ignores getIgnores() {
		return ignores;
	}

	public void setPrivateChat(int option) {
		this.privateChat = option;
	}

	public Trade getTrade() {
		return trade;
	}

	public AchievementHandler getAchievements() {
		if (achievementHandler == null)
			achievementHandler = new AchievementHandler(this);
		return achievementHandler;
	}

	public HolidayStages getHolidayStages() {
		if (holidayStages == null) {
			holidayStages = new HolidayStages();
		}
		return holidayStages;
	}

	public long getLastContainerSearch() {
		return lastContainerSearch;
	}

	public void setLastContainerSearch(long lastContainerSearch) {
		this.lastContainerSearch = lastContainerSearch;
	}

	public MysteryBox getMysteryBox() {
		return mysteryBox;
	}
	
	public ElderMysteryBox getElderMysteryBox() {
		return elderMysteryBox;
	}
	
	public StarterPetBox getStarterPetBox() {
		return starterPetBox;
	}
	
	public RareDropTable getRareDropTable() {
		return rareDropTable;
	}
	
	public GemRareDropTable getGemRareDropTable() {
		return gemRareDropTable;
	}
	
	public SlayerRareDropTable getSlayerRareDropTable() {
		return slayerRareDropTable;
	}
	
	public ValiusMysteryBox getValiusMysteryBox() {
		return valiusMysteryBox;
	}
	
	public ChamberOfXericBox getChamberOfXericBox() {
		return chamberOfXericBox;
	}
	
	public BloodMysteryBox getBloodMysteryBox() {
		return bloodMysteryBox;
	}
	
	public EventMysteryBox getEventMysteryBox() {
		return eventMysteryBox;
	}
	
	public PetMysteryBox getPetMysteryBox() {
		return petMysteryBox;
	}
	
	public EasterMysteryBox getEasterMysteryBox() {
		return easterMysteryBox;
	}
	
	public ChristmasBox getChristmasBox() {
		return christmasBox;
	}
	
	public HalloweenMysteryBox getHalloweenMysteryBox() {
		return halloweenMysteryBox;
	}
	
	public ValentinesBox getValentinesBox() {
		return valentinesBox;
	}
	
	public StarBox getStarBox() {
		return starBox;
	}
	
	public DragonHunterMBox getDragonHunterMBox() {
		return dragonHunterMBox;
	}
	
	public InfernalMysteryBox getInfernalMysteryBox() {
		return infernalmysterybox;
	}
	
	public BandosCasket getBandosCasket() {
		return bandoscasket;
	}
	
	public SaradominCasket getSaradominCasket() {
		return saradomincasket;
	}
	
	public ZamorakCasket getZamorakCasket() {
		return zamorakcasket;
	}
	
	public ArmadylCasket getArmadylCasket() {
		return armadylcasket;
	}
	
	public HourlyRewardBox getHourlyRewardBox() {
		return hourlyRewardBox;
	}

	public PvmCasket getPvmCasket() {
		return pvmCasket;
	}

	public SkillCasket getSkillCasket() {
		return skillCasket;
	}

	public WildyCrate getWildyCrate() {
		return wildyCrate;
	}

	public DailyGearBox getDailyGearBox() {
		return dailyGearBox;
	}

	public DailySkillBox getDailySkillBox() {
		return dailySkillBox;
	}

	public ChristmasPresent getChristmasPresent() {
		return christmasPresent;
	}

	public DamageQueueEvent getDamageQueue() {
		return damageQueue;
	}

	public final int[] BOWS = { 23901, 23902, 23903, 23983, 23985, 19481, 19478, 12788, 9185, 21902, 11785, 21012, 33117, 33114, 839, 845, 847, 851, 855, 859, 841, 843, 849,
			853, 857, 12424, 861, 4212, 4214, 4215, 12765, 12766, 12767, 12768, 11235, 4216, 4217, 4218, 4219, 4220,
			4221, 33908, 33909, 33910, 33911, 33914, 33922, 4222, 4223, 4734, 6724, 20997, 33812, 33813, 33891, 33814, 33752, 33754, 33763, 33671, 33594, 33119, 33529, 33531, 33536, 33424, 33281, 22333, 22547, 22550, 33781, 33782, 33783, 33030, 33116, 33139, 33140, 33141, 33124, 33094, 33525, 33551, 33562, 33578 };
	public final int[] ARROWS = { 9341, 4160, 11959, 33466, 10033, 10034, 882, 883, 884, 885, 886, 887, 888, 889, 890, 891,
			892, 893, 4740, 5616, 5617, 5618, 5619, 5620, 5621, 5622, 5623, 5624, 5625, 5626, 5627, 9139, 9140, 9141,
			9142, 9143, 11875, 21316, 21326, 9144, 9145, 9240, 9241, 9242, 9243, 9244, 9245, 9286, 9287, 9288, 9289,
			9290, 9291, 9292, 9293, 9294, 9295, 9296, 9297, 9298, 9299, 9300, 9301, 9302, 9303, 9304, 9305, 9306, 11212,
			11227, 11228, 11229 };
	public final int[] CRYSTAL_BOWS = { 4212, 4214, 4215, 4216, 4217, 4218, 4219, 4220, 4221, 4222, 4223 };
	public final int[] NO_ARROW_DROP = { 23901,  23902, 23903, 23983, 11959, 10033, 10034, 4212, 4214, 4215, 4216, 4217, 4218, 4219, 4220, 4221,
			4222, 4223, 4734, 4934, 4935, 4936, 4937, 33030, 33908, 33909, 33910, 33911, 33914, 33922, 33466, 22550, 33781, 33782, 33783, 22547, 22550, 22547 };
	public final int[] OTHER_RANGE_WEAPONS = { 11959, 10033, 10034, 800, 801, 802, 803, 804, 805, 20849, 806, 807, 808,
			809, 810, 811, 812, 813, 814, 815, 816, 817, 825, 826, 827, 828, 829, 830, 831, 832, 833, 834, 835, 836,
			863, 864, 865, 866, 867, 868, 869, 870, 871, 872, 873, 874, 875, 876, 4934, 4935, 4936, 4937, 5628, 5629,
			5630, 5632, 5633, 5634, 5635, 5636, 5637, 5639, 5640, 5641, 5642, 5643, 5644, 5645, 5646, 5647, 5648, 5649,
			5650, 5651, 5652, 5653, 5654, 5655, 5656, 5657, 5658, 5659, 5660, 5661, 5662, 5663, 5664, 5665, 5666, 5667,
			6522, 11230, 22804, 33466, 33005, 33006 };
	public int compostBin = 0;
	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = { 250000, 250000, 250000, 500000, 500000, 500000 };
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = { 1153, 1157, 1161, 1542, 1543, 1562 };
	public boolean[] canUseReducingSpell = { true, true, true, true, true, true };
	public final int[] FIRE_SPELLS = { 1158, 1169, 1181, 1189, 1192, 12901, 12919, 12911, 12929 };
	public boolean usingPrayer;
	public final int[] PRAYER_DRAIN_RATE = { 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500 };
	public final int[] PRAYER_LEVEL_REQUIRED = { 1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 37, 40, 43,
			44, 45, 46, 49, 52, 55, 60, 70, 74, 77 };
	public final int[] PRAYER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28 };
	public final String[] PRAYER_NAME = { "Thick Skin", "Burst of Strength", "Clarity of Thought", "Sharp Eye",
			"Mystic Will", "Rock Skin", "Superhuman Strength", "Improved Reflexes", "Rapid Restore", "Rapid Heal",
			"Protect Item", "Hawk Eye", "Mystic Lore", "Steel Skin", "Ultimate Strength", "Incredible Reflexes",
			"Protect from Magic", "Protect from Missiles", "Protect from Melee", "Eagle Eye", "Mystic Might",
			"Retribution", "Redemption", "Smite", "Preserve", "Chivalry", "Piety", "Rigour", "Augury" };
	
	public final int[] PRAYER_GLOW = { 83, 84, 85, 720, 721, 86, 87, 88, 89, 90, 91, 722, 723, 92, 93, 94, 95, 96, 97,
            724, 725, 98, 99, 100, 728, 726, 727, 730, 732 };
	public boolean isSelectingQuickprayers = false;
	public final int[] PRAYER_HEAD_ICONS = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 0,
			-1, -1, 3, 5, 4, -1, -1, -1, -1, -1 };
	public boolean[] prayerActive = { false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false };

	// Used by farming processor to not update the object every click
	// Created an array of booleans based on the patch number, not using an array
	// for each patch creates graphic glitches. - Tyler
	public boolean[] farmingLagReducer = new boolean[Farming.MAX_PATCHES];
	public boolean[] farmingLagReducer2 = new boolean[Farming.MAX_PATCHES];
	public boolean[] farmingLagReducer3 = new boolean[Farming.MAX_PATCHES];
	public boolean[] farmingLagReducer4 = new boolean[Farming.MAX_PATCHES];

	public Farming getFarming() {
		return farming;
	}

	public int getFarmingSeedId(int index) {
		return farmingSeedId[index];
	}

	public void setFarmingSeedId(int index, int farmingSeedId) {
		this.farmingSeedId[index] = farmingSeedId;
	}

	public int getFarmingTime(int index) {
		return this.farmingTime[index];
	}

	public int getOriginalFarmingTime(int index) { // originalFarming
		return this.originalFarmingTime[index];
	}

	public void setFarmingTime(int index, int farmingTime) {
		this.farmingTime[index] = farmingTime;
	}

	public void setOriginalFarmingTime(int index, int originalFarmingTime) {// originalFarmingTime
		this.originalFarmingTime[index] = originalFarmingTime;
	}

	public int getFarmingState(int index) {
		return farmingState[index];
	}

	public void setFarmingState(int index, int farmingState) {
		this.farmingState[index] = farmingState;
	}

	public int getFarmingHarvest(int index) {
		return farmingHarvest[index];
	}

	public void setFarmingHarvest(int index, int farmingHarvest) {
		this.farmingHarvest[index] = farmingHarvest;
	}

	/**
	 * Retrieves the bounty hunter instance for this client object. We use lazy
	 * initialization because we store values from the player save file in the
	 * bountyHunter object upon login. Without lazy initialization the value would
	 * be overwritten.
	 * 
	 * @return the bounty hunter object
	 */
	public BountyHunter getBH() {
		if (Objects.isNull(bountyHunter)) {
			bountyHunter = new BountyHunter(this);
		}
		return bountyHunter;
	}

	public UnnecessaryPacketDropper getPacketDropper() {
		return packetDropper;
	}

	public Optional<ItemCombination> getCurrentCombination() {
		return currentCombination;
	}

	public void setCurrentCombination(Optional<ItemCombination> combination) {
		this.currentCombination = combination;
	}

	public PlayerKill getPlayerKills() {
		if (Objects.isNull(playerKills)) {
			playerKills = new PlayerKill();
		}
		return playerKills;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpAddress() {
		return connectedFrom;
	}

	public void setIpAddress(String ipAddress) {
		this.connectedFrom = ipAddress;
	}

	public int getMaximumHealth() {
		int base = skills.getActualLevel(Skill.HITPOINTS);
		if (EquipmentSet.GUTHAN.isWearingBarrows(this) && getItems().isWearingItem(12853)) {
			base += 10;
		}
		return base;
	}

	public int getMaximumPrayer() {
		return skills.getActualLevel(Skill.PRAYER);
	}

	public Duel getDuel() {
		return duelSession;
	}

	public void setItemOnPlayer(Player player) {
		this.itemOnPlayer = player;
	}

	public Player getItemOnPlayer() {
		return itemOnPlayer;
	}

	public Skilling getSkilling() {
		return skilling;
	}

	public Presets getPresets() {
		if (presets == null) {
			presets = new Presets(this);
		}
		return presets;
	}

	public Killstreak getKillstreak() {
		if (killstreaks == null) {
			killstreaks = new Killstreak(this);
		}
		return killstreaks;
	}

	/**
	 * Returns the single instance of the {@link NPCDeathTracker} class for this
	 * player.
	 * 
	 * @return the tracker clas
	 */
	public NPCDeathTracker getNpcDeathTracker() {
		return npcDeathTracker;
	}
	
	/**
	 * The zulrah event
	 * 
	 * @return event
	 */
	public Zulrah getZulrahEvent() {
		return zulrah;
	}

	/**
	 * The single {@link WarriorsGuild} instance for this player
	 * 
	 * @return warriors guild
	 */
	public WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}

	/**
	 * The single instance of the {@link PestControlRewards} class for this player
	 * 
	 * @return the reward class
	 */
	public PestControlRewards getPestControlRewards() {
		return pestControlRewards;
	}

	public Mining getMining() {
		return mining;
	}

	public PunishmentPanel getPunishmentPanel() {
		return punishmentPanel;
	}

	public void faceNPC(int index) {
		faceNPC = index;
		faceNPCupdate = true;
		updateRequired = true;
	}

	public void appendFaceNPCUpdate(Buffer str) {
		str.writeWordBigEndian(faceNPC);
	}

	public void ResetKeepItems() {
		WillKeepAmt1 = -1;
		WillKeepItem1 = -1;
		WillKeepAmt2 = -1;
		WillKeepItem2 = -1;
		WillKeepAmt3 = -1;
		WillKeepItem3 = -1;
		WillKeepAmt4 = -1;
		WillKeepItem4 = -1;
	}

	public void StartBestItemScan(Player c) {
		if (c.isSkulled && !c.prayerActive[10]) {
			ItemKeptInfo(c, 0);
			return;
		}
		FindItemKeptInfo(c);
		ResetKeepItems();
		BestItem1(c);
	}

	public void FindItemKeptInfo(Player c) {
		if (isSkulled && c.prayerActive[10])
			ItemKeptInfo(c, 1);
		else if (!isSkulled && !c.prayerActive[10])
			ItemKeptInfo(c, 3);
		else if (!isSkulled && c.prayerActive[10])
			ItemKeptInfo(c, 4);
	}

	public void ItemKeptInfo(Player c, int Lose) {
		for (int i = 17109; i < 17131; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.getPA().sendFrame126("Items you will keep on death:", 17104);
		c.getPA().sendFrame126("Items you will lose on death:", 17105);
		c.getPA().sendFrame126("Player Information", 17106);
		c.getPA().sendFrame126("Max items kept on death:", 17107);
		c.getPA().sendFrame126("~ " + Lose + " ~", 17108);
		c.getPA().sendFrame126("The normal amount of", 17111);
		c.getPA().sendFrame126("items kept is three.", 17112);
		switch (Lose) {
		case 0:
		default:
			c.getPA().sendFrame126("Items you will keep on death:", 17104);
			c.getPA().sendFrame126("Items you will lose on death:", 17105);
			c.getPA().sendFrame126("You're marked with a", 17111);
			c.getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			c.getPA().sendFrame126("items you keep from", 17113);
			c.getPA().sendFrame126("three to zero!", 17114);
			break;
		case 1:
			c.getPA().sendFrame126("Items you will keep on death:", 17104);
			c.getPA().sendFrame126("Items you will lose on death:", 17105);
			c.getPA().sendFrame126("You're marked with a", 17111);
			c.getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			c.getPA().sendFrame126("items you keep from", 17113);
			c.getPA().sendFrame126("three to zero!", 17114);
			c.getPA().sendFrame126("However, you also have", 17115);
			c.getPA().sendFrame126("the @red@Protect @lre@Items prayer", 17116);
			c.getPA().sendFrame126("active, which saves you", 17117);
			c.getPA().sendFrame126("one extra item!", 17118);
			break;
		case 3:
			c.getPA().sendFrame126("Items you will keep on death(if not skulled):", 17104);
			c.getPA().sendFrame126("Items you will lose on death(if not skulled):", 17105);
			c.getPA().sendFrame126("You have no factors", 17111);
			c.getPA().sendFrame126("affecting the items you", 17112);
			c.getPA().sendFrame126("keep.", 17113);
			break;
		case 4:
			c.getPA().sendFrame126("Items you will keep on death(if not skulled):", 17104);
			c.getPA().sendFrame126("Items you will lose on death(if not skulled):", 17105);
			c.getPA().sendFrame126("You have the @red@Protect", 17111);
			c.getPA().sendFrame126("@red@Item @lre@prayer active,", 17112);
			c.getPA().sendFrame126("which saves you one", 17113);
			c.getPA().sendFrame126("extra item!", 17114);
			break;
		}
	}

	public void BestItem1(Player c) {
		int BestValue = 0;
		int NextValue = 0;
		int ItemsContained = 0;
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerItems[ITEM] - 1));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerItems[ITEM] - 1;
					WillKeepItem1Slot = ITEM;
					if (playerItemsN[ITEM] > 2 && !c.prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerItemsN[ITEM] > 3 && c.prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerEquipment[EQUIP]));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerEquipment[EQUIP];
					WillKeepItem1Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 && !c.prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerEquipmentN[EQUIP] > 3 && c.prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 1 && (WillKeepAmt1 < 3 || (c.prayerActive[10] && WillKeepAmt1 < 4))) {
			BestItem2(c, ItemsContained);
		}
	}

	public void BestItem2(Player c, int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerItems[ITEM] - 1;
					WillKeepItem2Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - WillKeepAmt1 && !c.prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerItemsN[ITEM] > 3 - WillKeepAmt1 && c.prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerEquipment[EQUIP];
					WillKeepItem2Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - WillKeepAmt1 && !c.prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1 && c.prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 2
				&& (WillKeepAmt1 + WillKeepAmt2 < 3 || (c.prayerActive[10] && WillKeepAmt1 + WillKeepAmt2 < 4))) {
			BestItem3(c, ItemsContained);
		}
	}

	public void BestItem3(Player c, int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerItems[ITEM] - 1;
					WillKeepItem3Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - (WillKeepAmt1 + WillKeepAmt2) && !c.prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerItemsN[ITEM] > 3 - (WillKeepAmt1 + WillKeepAmt2) && c.prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerEquipment[EQUIP];
					WillKeepItem3Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - (WillKeepAmt1 + WillKeepAmt2) && !c.prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1 && c.prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 3 && c.prayerActive[10]
				&& ((WillKeepAmt1 + WillKeepAmt2 + WillKeepAmt3) < 4)) {
			BestItem4(c);
		}
	}

	public void BestItem4(Player c) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)
						&& !(ITEM == WillKeepItem3Slot && playerItems[ITEM] - 1 == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerItems[ITEM] - 1;
					WillKeepItem4Slot = ITEM;
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(ShopAssistant.getItemShopValue(playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)
						&& !(EQUIP + 28 == WillKeepItem3Slot && playerEquipment[EQUIP] == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerEquipment[EQUIP];
					WillKeepItem4Slot = EQUIP + 28;
				}
			}
		}
	}

	/**
	 * A method for updating the items a player keeps on death
	 */
	public void updateItemsOnDeath() {
		if (!isSkulled) { // what items to keep
			itemAssistant.keepItem(0, true);
			itemAssistant.keepItem(1, true);
			itemAssistant.keepItem(2, true);
		}
		if (prayerActive[10] && System.currentTimeMillis() - lastProtItem > 700) {
			itemAssistant.keepItem(3, true);
		}
	}

	/**
	 * Determines if the player should keep the item on death
	 * 
	 * @param itemId
	 *            the item to be kept
	 * @return true if the player keeps the item on death, otherwise false
	 */
	public boolean keepsItemOnDeath(int itemId) {
		return WillKeepItem1 == itemId || WillKeepItem2 == itemId || WillKeepItem3 == itemId || WillKeepItem4 == itemId;
	}

	public boolean isAutoButton(int button) {
		for (int j = 0; j < MagicData.autocastIds.length; j += 2) {
			if (MagicData.autocastIds[j] == button)
				return true;
		}
		return false;
	}

	public void assignAutocast(int button) {
		for (int j = 0; j < MagicData.autocastIds.length; j++) {
			if (MagicData.autocastIds[j] == button) {
				Player c = PlayerHandler.players[this.getIndex()];
				autocasting = true;
				autocastId = MagicData.autocastIds[j + 1];
				c.getPA().sendFrame36(108, 1);
				c.setSidebarInterface(0, 328);
				c = null;
				break;
			}
		}
	}

	public int getLocalX() {
		return getX() - 8 * getMapRegionX();
	}

	public int getLocalY() {
		return getY() - 8 * getMapRegionY();
	}

	public String getSpellName(int id) {
		switch (id) {
		case 0:
			return "Air Strike";
		case 1:
			return "Water Strike";
		case 2:
			return "Earth Strike";
		case 3:
			return "Fire Strike";
		case 4:
			return "Air Bolt";
		case 5:
			return "Water Bolt";
		case 6:
			return "Earth Bolt";
		case 7:
			return "Fire Bolt";
		case 8:
			return "Air Blast";
		case 9:
			return "Water Blast";
		case 10:
			return "Earth Blast";
		case 11:
			return "Fire Blast";
		case 12:
			return "Air Wave";
		case 13:
			return "Water Wave";
		case 14:
			return "Earth Wave";
		case 15:
			return "Fire Wave";
		case 32:
			return "Shadow Rush";
		case 33:
			return "Smoke Rush";
		case 34:
			return "Blood Rush";
		case 35:
			return "Ice Rush";
		case 36:
			return "Shadow Burst";
		case 37:
			return "Smoke Burst";
		case 38:
			return "Blood Burst";
		case 39:
			return "Ice Burst";
		case 40:
			return "Shadow Blitz";
		case 41:
			return "Smoke Blitz";
		case 42:
			return "Blood Blitz";
		case 43:
			return "Ice Blitz";
		case 44:
			return "Shadow Barrage";
		case 45:
			return "Smoke Barrage";
		case 46:
			return "Blood Barrage";
		case 47:
			return "Ice Barrage";
		default:
			return "Select Spell";
		}
	}

	public boolean fullVoidRange() {
		// return playerEquipment[playerHat] == 11664 && playerEquipment[playerLegs] ==
		// 8840 || playerEquipment[playerLegs] == 13073 && playerEquipment[playerChest]
		// == 8839
		// || playerEquipment[playerChest] == 13072 && playerEquipment[playerHands] ==
		// 8842;

		if (getItems().isWearingItem(11664) && getItems().isWearingItem(8840) && getItems().isWearingItem(8839)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(11664) && getItems().isWearingItem(13073) && getItems().isWearingItem(13072)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(11664) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}

	public boolean fullVoidMage() {
		// return playerEquipment[playerHat] == 11663 && playerEquipment[playerLegs] ==
		// 8840 || playerEquipment[playerLegs] == 13073 && playerEquipment[playerChest]
		// == 8839
		// || playerEquipment[playerChest] == 13072 && playerEquipment[playerHands] ==
		// 8842;

		if (getItems().isWearingItem(11663) && getItems().isWearingItem(8840) && getItems().isWearingItem(8839)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(11663) && getItems().isWearingItem(13073) && getItems().isWearingItem(13072)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(11663) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}

	public boolean fullVoidMelee() {
		if (getItems().isWearingItem(11665) && getItems().isWearingItem(8840) && getItems().isWearingItem(8839)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(11665) && getItems().isWearingItem(13073) && getItems().isWearingItem(13072)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}
//supreme void
	public boolean fullVoidSupremeMelee() {
		if (getItems().isWearingItem(11665) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		if (getItems().isWearingItem(33367) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}
	public boolean fullVoidSupremeRange() {
		if (getItems().isWearingItem(33367) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}
	public boolean fullVoidSupremeMage() {
		if (getItems().isWearingItem(33367) && getItems().isWearingItem(33368) && getItems().isWearingItem(33369)
				&& getItems().isWearingItem(8842)) {
			return true;
		}
		return false;
	}
	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */
	public boolean inArea(int x, int y, int x1, int y1) {
		if (x > x && x < x1 && y < y && y > y1) {
			return true;
		}
		return false;
	}

	public boolean Area(final int x1, final int x2, final int y1, final int y2) {
		return (getX() >= x1 && getX() <= x2 && getY() >= y1 && getY() <= y2);
	}

	public boolean inBank() {
		return Area(3090, 3099, 3487, 3500) || Area(3089, 3090, 3492, 3498) || Area(3248, 3258, 3413, 3428)
				|| Area(3179, 3191, 3432, 3448) || Area(2944, 2948, 3365, 3374) || Area(2942, 2948, 3367, 3374)
				|| Area(2944, 2950, 3365, 3370) || Area(3008, 3019, 3352, 3359) || Area(3017, 3022, 3352, 3357)
				|| Area(3203, 3213, 3200, 3237) || Area(3212, 3215, 3200, 3235) || Area(3215, 3220, 3202, 3235)
				|| Area(3220, 3227, 3202, 3229) || Area(3227, 3230, 3208, 3226) || Area(3226, 3228, 3230, 3211)
				|| Area(3227, 3229, 3208, 3226) || Area(3025, 3032, 3374, 3384) || Area(3088, 3101, 3507, 3516);
	}
	
	public boolean inOlmRoom() {//checks to see if player is in olm room
		return (getX() > 3200 && getX() < 3260 && getY() > 5710 && getY() < 5770);
	}
	
	public boolean inRaidLobby() {//checks to see if player is in the raid lobby
		if (Boundary.isIn(this, Boundary.RAIDS_LOBBY))  {
			return true;
		}
		return false;
	}
	public boolean inXericLobby() {
		if (Boundary.isIn(this, Boundary.XERIC_LOBBY)) {
			return true;
		}
		return false;
	}

	public boolean isInJail() {
		if (getX() >= 2066 && getX() <= 2108 && getY() >= 4452 && getY() <= 4478) {
			return true;
		}
		return false;
	}

	public boolean inClanWars() {
		if (getX() > 3272 && getX() < 3391 && getY() > 4759 && getY() < 4863) {
			return true;
		}
		return false;
	}

	public boolean inClanWarsSafe() {
		if (getX() > 3263 && getX() < 3390 && getY() > 4735 && getY() < 4761) {
			return true;
		}
		return false;
	}
	public boolean inRaids() {
		return (getX() > 3210 && getX() < 3368 && getY() > 5137 && getY() < 5759);
	}

	public boolean inRaidsMountain() {
		return (getX() > 1219 && getX() < 1259 && getY() > 3542 && getY() < 3577);

	}
	public boolean inWild() {
		if (inClanWars())
			return true;
		if (Boundary.isIn(this, Boundary.LOBBY)) {
			return false;
		}
		if(Boundary.isIn(this, Boundary.EDGEVILLE_PERIMETER) && !Boundary.isIn(this, Boundary.EDGE_BANK) && getHeight() == 8){
			return true;
		}
		if (Boundary.isIn(this, Boundary.SAFEPK))
			return true;
		if (Boundary.isIn(this, Boundary.WILDERNESS_PARAMETERS)) {
			return true;
		}
		return false;
	}

	public boolean inEdgeville() {
		return (getX() > 3040 && getX() < 3200 && getY() > 3460 && getY() < 3519);
	}

	public boolean maxRequirements(Player c) {
		int amount = 0;
		for (int i = 0; i <= 21; i++) {
			if (c.getSkills().getActualLevel(Skill.forId(i)) >= 99) {
				amount++;
			}
			if (amount == 22) {
				return true;
			}
		}
		return false;
	}

	public boolean maxedCertain(Player c, int min, int max) {
		int amount = 0;
		int total = min + max;
		for (int i = min; i <= max; i++) {
			if (skills.getActualLevel(Skill.forId(i)) >= 99) {
				amount++;
			}
			if (amount == total) {
				return true;
			}
		}
		return false;
	}

	public boolean maxedSkiller(Player c) {
		int amount = 0;
		for (int id = 0; id <= 6; id++) {
			if (c.getSkills().getActualLevel(Skill.forId(id)) <= 1 && id != 3) {
				amount++;
			}
		}
		for (int i = 7; i <= 22; i++) {
			if (c.getSkills().getActualLevel(Skill.forId(i)) >= 99) {
				amount++;
			}
		}
		if (amount == 22) {
			return true;
		}
		return false;
	}

	//TODO Remove these
	public boolean arenas() {
		if (getX() > 3331 && getX() < 3391 && getY() > 3242 && getY() < 3260) {
			return true;
		}
		return false;
	}

	public boolean inDuelArena() {
		if ((getX() > 3322 && getX() < 3394 && getY() > 3195 && getY() < 3291)
				|| (getX() > 3311 && getX() < 3323 && getY() > 3223 && getY() < 3248)) {
			return true;
		}
		return false;
	}
	public boolean inRevs() {
		return (getX() > 3143 && getX() < 3262 && getY() > 10053 && getY() < 10231);
	}

	public boolean inMulti() {
		if (Boundary.isIn(this, Zulrah.BOUNDARY) || Boundary.isIn(this, Boundary.CORPOREAL_BEAST_LAIR)
				|| Boundary.isIn(this, Boundary.EVENT_AREAS) 
				|| Boundary.isIn(this, Boundary.KRAKEN_CAVE) || Boundary.isIn(this, Boundary.SCORPIA_LAIR)
				|| Boundary.isIn(this, Boundary.CERBERUS_BOSSROOMS) || Boundary.isIn(this, Boundary.INFERNO)
				|| Boundary.isIn(this, Boundary.SKOTIZO_BOSSROOM) || Boundary.isIn(this, Boundary.LIZARDMAN_CANYON)
				|| Boundary.isIn(this, Boundary.BANDIT_CAMP_BOUNDARY) || Boundary.isIn(this, Boundary.COMBAT_DUMMY)
				|| Boundary.isIn(this, Boundary.TEKTON) || Boundary.isIn(this, Boundary.SKELETAL_MYSTICS)
				|| Boundary.isIn(this, Boundary.RAIDS) || Boundary.isIn(this, Boundary.OLM) || Boundary.isIn(this, Boundary.THEATRE)
				|| Boundary.isIn(this, Boundary.ICE_DEMON) || Boundary.isIn(this, Boundary.XERIC) || Boundary.isIn(this, Boundary.CATACOMBS)  || Boundary.isIn(this, Boundary.SMOKE_DEVILS)) {
			return true;
		}
		if(inRevs()) {
			return true;
		}
		if (Boundary.isIn(this, Boundary.KALPHITE_QUEEN) && getHeight() == 0) {
			return true;
		}
		if ((getX() >= 3136 && getX() <= 3327 && getY() >= 3519 && getY() <= 3607)
				|| (getX() >= 3190 && getX() <= 3327 && getY() >= 3648 && getY() <= 3839)
				|| (getX() >= 2261 && getX() <= 2283 && getY() >= 4054 && getY() <= 4076)//vorkath
				|| (getX() >= 3200 && getX() <= 3390 && getY() >= 3840 && getY() <= 3967)
				|| (getX() >= 2992 && getX() <= 3007 && getY() >= 3912 && getY() <= 3967)
				|| (getX() >= 2946 && getX() <= 2959 && getY() >= 3816 && getY() <= 3831)
				|| (getX() >= 3008 && getX() <= 3199 && getY() >= 3856 && getY() <= 3903)
				|| (getX() >= 2824 && getX() <= 2944 && getY() >= 5258 && getY() <= 5369)
				|| (getX() >= 3008 && getX() <= 3071 && getY() >= 3600 && getY() <= 3711)
				|| (getX() >= 3072 && getX() <= 3327 && getY() >= 3608 && getY() <= 3647)
				|| (getX() >= 2624 && getX() <= 2690 && getY() >= 2550 && getY() <= 2619)
				|| (getX() >= 2371 && getX() <= 2422 && getY() >= 5062 && getY() <= 5117)
				|| (getX() >= 2896 && getX() <= 2927 && getY() >= 3595 && getY() <= 3630)
				|| (getX() >= 2892 && getX() <= 2932 && getY() >= 4435 && getY() <= 4464)
				|| (getX() >= 2256 && getX() <= 2287 && getY() >= 4680 && getY() <= 4711)
				|| (getX() >= 2962 && getX() <= 3006 && getY() >= 3621 && getY() <= 3659)
				|| (getX() >= 3155 && getX() <= 3214 && getY() >= 3755 && getY() <= 3803)
				|| (getX() >= 1889 && getX() <= 1912 && getY() >= 4396 && getY() <= 4413)
				|| (getX() >= 3717 && getX() <= 3772 && getY() >= 5765 && getY() <= 5820)
				|| (getX() >= 3154 && getX() <= 3182 && getY() >= 4303 && getY() <= 4327)
				|| (getX() >= 3341 && getX() <= 3378 && getY() >= 4760 && getY() <= 4853)) {
			return true;
		}
		return false;
	}

	public boolean inGodwars() {
		return Boundary.isIn(this, Godwars.GODWARS_AREA);
	}

	public boolean checkFullGear(Player c) {
		int amount = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[0] >= 0) {
				amount++;
			}
			if (amount == c.playerEquipment.length) {
				return true;
			}
		}
		return false;
	}

	public void updateshop(int i) {
		Player p = PlayerHandler.players[getIndex()];
		p.getShops().resetShop(i);
	}

	public void println_debug(String str) {
		log.debug("[player-{}][User: {}]: {}", getIndex(), playerName, str);
	}

	public void println(String str) {
		log.info("[player-{}][User: {}]: {}", getIndex(), playerName, str);
	}

	public boolean WithinDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((objectX + i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if ((objectX - i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isWithinDistance() {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Player other = (Player) PlayerHandler.players[i];

				int deltaX = other.getX() - getX(), deltaY = other.getY() - getY();
				return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
			}
		}
		return false;
	}

	public boolean withinDistance(Player otherPlr) {
		if (getHeight() != otherPlr.getHeight())
			return false;
		int deltaX = otherPlr.getX() - getX(), deltaY = otherPlr.getY() - getY();
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(NPC npc) {//brb
		if (getHeight() != npc.getHeight())
			return false;
		if (npc.needRespawn == true)
			return false;
		int deltaX = npc.getX() - getX(), deltaY = npc.getY() - getY();
		//return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;   original render
		return deltaX <= currentRender-1 && deltaX >= -currentRender && deltaY <= currentRender-1 && deltaY >= -currentRender;
	}

	public void resetWalkingQueue() {
//		wQueueReadPtr = wQueueWritePtr = 0;
//
//		for (int i = 0; i < walkingQueueSize; i++) {
//			walkingQueueX[i] = currentX;
//			walkingQueueY[i] = currentY;
//		}
		getMovementQueue().reset();
	}

	public void addToWalkingQueue(int x, int y) {
		// if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
//		if(Region.isBlocked(x, y, height))
//			return;
//		int next = (wQueueWritePtr + 1) % walkingQueueSize;
//		if (next == wQueueWritePtr)
//			return;
//		walkingQueueX[wQueueWritePtr] = x;
//		walkingQueueY[wQueueWritePtr] = y;
//		wQueueWritePtr = next;
		// }
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return Misc.goodDistance(objectX, objectY, playerX, playerY, distance);
	}
	
	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int width, int length) {
		return Misc.goodDistance(objectX, objectY, playerX, playerY, width, length);
	}

	public boolean isWithinDistance(Location other, int dist) {
		int deltaX = other.getX() - getX(), deltaY = other.getY() - getY();
		return deltaX <= dist && deltaX >= -dist && deltaY <= dist && deltaY >= -dist;
	}

	/**
	 * Checks the combat distance to see if the player is in an appropriate location
	 * based on the attack style.
	 * 
	 * @param attacker
	 * @param target
	 * @return
	 */
	public boolean checkCombatDistance(Player attacker, Player target) {
		int distance = Misc.distanceBetween(attacker, target);
		int required_distance = this.getDistanceRequired();
		return (this.usingMagic || this.usingRangeWeapon || this.usingBow || this.autocasting || this.usingBallista) && distance <= required_distance ? true : (this.usingMelee && getMovementQueue().isMoving() && distance <= required_distance ? true : distance == 1 && (this.freezeTimer <= 0 || this.getX() == target.getX() || this.getY() == target.getY()));
	}

	public int getDistanceRequired() {
		return !this.usingMagic && !this.usingBallista && !this.usingRangeWeapon && !usingBow && !this.autocasting ? (getMovementQueue().isMoving() ? 3 : 1) : 9;
	}

	public int otherDirection;
	public boolean invincible;

	public boolean isRunning() {
		return isRunning || (isRunning2 && getMovementQueue().isMoving());
	}
	
	public void setRunning(boolean running) {
		this.running = running;
		this.isRunning = running;
		this.isRunning2 = running;
		getPA().setConfig(173, running ? 1 : 0);
	}

	public void getNextPlayerMovement() {
		getMovementQueue().process();
	}
	
	public boolean hasMoved() {
		return getX() != getPreviousLocation().getX() || getY() != getPreviousLocation().getY();
	}
	
	public void checkLocationOnLogin() {

		if (Boundary.isIn(this, PestControl.GAME_BOUNDARY)) {
			getPA().movePlayerUnconditionally(2657, 2639, 0);
		}
	
		if (Boundary.isIn(this, Boundary.FIGHT_CAVE)) {
			getPA().movePlayerUnconditionally(2401, 5087, (getIndex() + 1) * 4);
			sendMessage("Wave " + (this.waveId + 1) + " will start in approximately 5-10 seconds. ");
			getFightCave().spawn();
		}
		if (Boundary.isIn(this, Zulrah.BOUNDARY)) {
			getPA().movePlayerUnconditionally(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
		}
		Theatre.onLogin(this);
		for(LobbyType lobbyType : LobbyType.values()) {
			LobbyManager.get(lobbyType)
			.ifPresent(lobby -> {
				if(lobby.inLobby(this)) {
					if(lobby.canJoin(this))
						lobby.attemptJoin(this);
					else
						getPA().movePlayerUnconditionally(3049, 9951, 0);//TODO Make this independent for all lobbies
				}
			});
		}
		if (Boundary.isIn(this, Boundary.RAIDS) || Boundary.isIn(this, Boundary.OLM)) {
			RaidConstants.checkLogin(this);
		}
		
		//TODO Add other instance teleporting here
	}

	public void postTeleportProcessing() {
		if (inGodwars()) {
			if (equippedGodItems == null) {
				updateGodItems();
			}
		} else if (equippedGodItems != null) {
			equippedGodItems = null;
			godwars.initialize();
		}
	}

	public void updateThisPlayerMovement(Buffer str) {
		if (isNeedsPlacement()) {
			getMovementQueue().handleRegionChange();
		}

		str.createFrameVarSizeWord(81);
		str.initBitAccess();
		
		if (isNeedsPlacement()) {
			str.writeBits(1, 1);
			str.writeBits(2, 3);
			str.writeBits(2, getHeight());
			str.writeBits(1, 1);
			str.writeBits(1, (updateRequired) ? 1 : 0);
			str.writeBits(7, getLocation().getLocalY(getLastKnownLocation()));
			str.writeBits(7, getLocation().getLocalX(getLastKnownLocation()));
			return;
		}

		if (getWalkingDirection().getId() == -1) {
			if (updateRequired) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else {
			str.writeBits(1, 1);
			if (getRunningDirection().getId() == -1) {
				str.writeBits(2, 1);
				str.writeBits(3, getWalkingDirection().getId());
				str.writeBits(1, updateRequired ? 1 : 0);
			} else {
				str.writeBits(2, 2);
				str.writeBits(3, getWalkingDirection().getId());
				str.writeBits(3, getRunningDirection().getId());
				str.writeBits(1, updateRequired ? 1 : 0);
			}
		}

	}

	public void updatePlayerMovement(Buffer str) {
		// synchronized(this) {
		if (getWalkingDirection().getId() == -1) {
			if (updateRequired || isChatTextUpdateRequired()) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else
				str.writeBits(1, 0);
		} else if (getRunningDirection().getId() == -1) {
			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, getWalkingDirection().getId());
			str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1 : 0);
		} else {

			str.writeBits(1, 1);
			str.writeBits(2, 2);
			str.writeBits(3, getWalkingDirection().getId());
			str.writeBits(3, getRunningDirection().getId());
			str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1 : 0);
		}

	}

	public void addNewNPC(NPC npc, Buffer str, Buffer updateBlock) {
		// synchronized(this) {
		int id = npc.getIndex();
		npcInListBitmap[id >> 3] |= 1 << (id & 7);
		npcList[npcListSize++] = npc;

		str.writeBits(14, id);

		int z = npc.getY() - getY();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = npc.getX() - getX();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);

		str.writeBits(1, 0);
		str.writeBits(14, npc.npcType);

		boolean savedUpdateRequired = npc.updateRequired;
		npc.updateRequired = true;
		npc.appendNPCUpdateBlock(updateBlock);
		npc.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
	}

	public void addNewPlayer(Player plr, Buffer str, Buffer updateBlock) {
		if (playerListSize >= 79) {
			return;
		}
		int id = plr.getIndex();
		playerInListBitmap[id >> 3] |= 1 << (id & 7);
		playerList[playerListSize++] = plr;
		str.writeBits(11, id);
		str.writeBits(1, 1);
		boolean savedFlag = plr.isAppearanceUpdateRequired();
		boolean savedUpdateRequired = plr.updateRequired;
		plr.setAppearanceUpdateRequired(true);
		plr.updateRequired = true;
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setAppearanceUpdateRequired(savedFlag);
		plr.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
		int z = plr.getY() - getY();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = plr.getX() - getX();
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
	}

	protected void appendPlayerAppearance(Buffer str) {
		playerProps.currentOffset = 0;
		playerProps.writeByte(playerAppearance[0]);
		StringBuilder sb = new StringBuilder(titles.getCurrentTitle());
		if (titles.getCurrentTitle().equalsIgnoreCase("None")) {
			sb.delete(0, sb.length());
		}
		playerProps.writeString(sb.toString());
		sb = new StringBuilder(rights.getPrimary().getColor());
		if (titles.getCurrentTitle().equalsIgnoreCase("None")) {
			sb.delete(0, sb.length());
		}
		playerProps.writeString(sb.toString());
		playerProps.writeByte(getHealth().getStatus().getMask());
		playerProps.writeByte(headIcon);
		playerProps.writeByte(headIconPk);
		if (isNpc == false) {
			if (playerEquipment[playerHat] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerHat]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerCape] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerCape]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerAmulet] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerWeapon] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerChest] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerChest]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[2]);
			}

			if (playerEquipment[playerShield] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerShield]);
			} else {
				playerProps.writeByte(0);
			}

			if (!ItemUtility.isFullBody(playerEquipment[playerChest])) {
				playerProps.writeWord(0x100 + playerAppearance[3]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerLegs] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[5]);
			}

			if (!ItemUtility.isFullHat(playerEquipment[playerHat]) && !ItemUtility.isFullMask(playerEquipment[playerHat])) {
				playerProps.writeWord(0x100 + playerAppearance[1]);
			} else {
				playerProps.writeByte(0);
			}

			if (playerEquipment[playerHands] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerHands]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[4]);
			}

			if (playerEquipment[playerFeet] > 1) {
				playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
			} else {
				playerProps.writeWord(0x100 + playerAppearance[6]);
			}

			if (playerAppearance[0] != 1 && !ItemUtility.isFullMask(playerEquipment[playerHat])) {
				playerProps.writeWord(0x100 + playerAppearance[7]);
			} else {
				playerProps.writeByte(0);
			}
		} else {
			playerProps.writeWord(-1);
			playerProps.writeWord(npcId2);
		}
		playerProps.writeByte(playerAppearance[8]);
		playerProps.writeByte(playerAppearance[9]);
		playerProps.writeByte(playerAppearance[10]);
		playerProps.writeByte(playerAppearance[11]);
		playerProps.writeByte(playerAppearance[12]);
		playerProps.writeWord(playerStandIndex); // standAnimIndex
		playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
		playerProps.writeWord(playerWalkIndex); // walkAnimIndex
		playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
		playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
		playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
		playerProps.writeWord(playerRunIndex); // runAnimIndex
		playerProps.writeQWord(Misc.playerNameToInt64(playerName));
		playerProps.writeByte(invisible ? 1 : 0);
		combatLevel = calculateCombatLevel();
		playerProps.writeByte(combatLevel); // combat level
		playerProps.writeByte(rights.getPrimary().getValue());
		playerProps.writeWord(0);
		String leader = getGroupIronman().getLeader();
		playerProps.writeQWord(leader == null ? 0 : Misc.playerNameToInt64(leader));
		if(completionistCape.wearingCape() && completionistCape.getOverrides() != null && completionistCape.coloursNotDefault()) {
			playerProps.writeByte(1);
			completionistCape.forEach(playerProps::writeDWord);
		} else {
			playerProps.writeByte(0);
		}
		str.writeByteC(playerProps.currentOffset);
		str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
	}

	public int calculateCombatLevel() {
		int j = skills.getActualLevel(Skill.ATTACK);
		int k = skills.getActualLevel(Skill.DEFENCE);
		int l = skills.getActualLevel(Skill.STRENGTH);
		int i1 = skills.getActualLevel(Skill.HITPOINTS);
		int j1 = skills.getActualLevel(Skill.PRAYER);
		int k1 = skills.getActualLevel(Skill.RANGED);
		int l1 = skills.getActualLevel(Skill.MAGIC);
		int combatLevel = (int) (((k + i1) + Math.floor(j1 / 2)) * 0.24798D) + 1;
		double d = (j + l) * 0.32500000000000001D;
		double d1 = Math.floor(k1 * 1.5D) * 0.32500000000000001D;
		double d2 = Math.floor(l1 * 1.5D) * 0.32500000000000001D;
		if (d >= d1 && d >= d2) {
			combatLevel += d;
		} else if (d1 >= d && d1 >= d2) {
			combatLevel += d1;
		} else if (d2 >= d && d2 >= d1) {
			combatLevel += d2;
		}
		return combatLevel;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}

	protected void appendPlayerChatText(Buffer str) {
		str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8) + (getChatTextEffects() & 0xFF));
		str.writeByte(rights.getPrimary().getValue());
		str.writeByteC(getChatTextSize());
		str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);

	}

	public void forcedChat(String text) {
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}

	public void appendForcedChat(Buffer str) {
		// synchronized(this) {
		str.writeString(forcedText);
	}

	public void appendMask100Update(Buffer str) {
		// synchronized(this) {
		str.writeWordBigEndian(mask100var1);
		str.writeDWord(mask100var2);

	}

	public void gfx(int gfx, int height) {
		mask100var1 = gfx;
		mask100var2 = 65536 * height;
		graphicMaskUpdate0x100 = true;
		updateRequired = true;
	}

	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		graphicMaskUpdate0x100 = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 65536;
		graphicMaskUpdate0x100 = true;
		updateRequired = true;
	}

	public boolean wearing2h() {
		Player c = this;
		String s = ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h"))
			return true;
		if (s.contains("bulwark") || s.contains("elder maul") || s.contains("stunning hammer"))
			return true;
		if (s.contains("godsword") || s.contains("k'ril"))
			return true;

		return false;
	}

	/**
	 * Animations
	 **/
	public void startAnimation(int animId) {
		// if (wearing2h() && animId == 829)
		// return;
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}

	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void stopAnimation() {
		animationRequest = 65535;
		animationWaitCycles = 0;
		updateRequired = true;
	}

	public void appendAnimationRequest(Buffer str) {
		// synchronized(this) {
		str.writeWordBigEndian((animationRequest == -1) ? 65535 : animationRequest);
		str.writeByteC(animationWaitCycles);

	}

	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceUpdate(Buffer str) {
		// synchronized(this) {
		str.writeWordBigEndian(face);

	}

	public void turnPlayerTo(int pointX, int pointY) {
		FocusPointX = 2 * pointX + 1;
		FocusPointY = 2 * pointY + 1;
		updateRequired = true;
	}

	private void appendSetFocusDestination(Buffer str) {
		// synchronized(this) {
		str.writeWordBigEndianA(FocusPointX);
		str.writeWordBigEndian(FocusPointY);

	}

	@Override
	public void appendDamage(int damage, Hitmark h) {
		lastAttacked = System.currentTimeMillis();
		if (damage <= 0) {
			damage = 0;
			h = Hitmark.MISS;
		}
		if (getHealth().getAmount() - damage < 0) {
			damage = getHealth().getAmount();
		}
		if (teleTimer <= 0) {
			if (!invincible)
				getHealth().reduce(damage);
			if (!hitUpdateRequired) {
				hitUpdateRequired = true;
				hitDiff = damage;
				hitmark1 = h;
			} else if (!hitUpdateRequired2) {
				hitUpdateRequired2 = true;
				hitDiff2 = damage;
				hitmark2 = h;
			}
		} else {
			if (hitUpdateRequired) {
				hitUpdateRequired = false;
			}
			if (hitUpdateRequired2) {
				hitUpdateRequired2 = false;
			}
		}
		updateRequired = true;
	}

	@Override
	protected void appendHitUpdate(Buffer str) {
		str.writeByte(hitDiff);
		if (hitmark1 == null) {
			str.writeByteA(0);
		} else {
			str.writeByteA(hitmark1.getId());
		}
		if (getHealth().getAmount() <= 0) {
			isDead = true;
		}
		str.writeByteC(getHealth().getAmount());
		str.writeByte(getHealth().getMaximum());
	}

	@Override
	protected void appendHitUpdate2(Buffer str) {
		str.writeByte(hitDiff2);
		if (hitmark2 == null) {
			str.writeByteS(0);
		} else {
			str.writeByteS(hitmark2.getId());
		}
		if (getHealth().getAmount() <= 0) {
			isDead = true;
		}
		str.writeByte(getHealth().getAmount());
		str.writeByteC(getHealth().getMaximum());
	}
	

	/**
	 * Direction, 2 = South, 0 = North, 3 = West, 2 = East?
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param speed1
	 * @param speed2
	 * @param direction
	 * @param emote
	 */
	private int xOffsetWalk, yOffsetWalk;
	public int dropSize = 0;
	public boolean canUpdateHighscores = true;
	public boolean zukDead = false;
	public boolean sellingX;
	public boolean firstBankLogin = true;
	public int currentPrestigeLevel, prestigeNumber;
	public boolean canPrestige = false;
	public int prestigePoints;
	public boolean newStarter = false;

	public boolean updateAnnounced;


	/**
	 * 0 North 1 East 2 South 3 West
	 */
	public void setForceMovement(int xOffset, int yOffset, int speedOne, int speedTwo, String directionSet,
			int animation) {
		if (isForceMovementActive() || forceMovement) {
			return;
		}
		forceMovementActive = true;
		stopMovement();
		xOffsetWalk = xOffset - getX();
		yOffsetWalk = yOffset - getY();
		playerStandIndex = animation;
		playerRunIndex = animation;
		playerWalkIndex = animation;
		setAppearanceUpdateRequired(true);
		getPA().requestUpdates();
		World.getWorld().getEventHandler().submit(new Event<Player>("force_movement", this, 2) {

			@Override
			public void execute() {
				if (attachment == null || attachment.disconnected) {
					super.stop();
					return;
				}
				attachment.updateRequired = true;
				attachment.forceMovement = true;
				attachment.x1 = getLocalX();
				attachment.y1 = getLocalY();
				attachment.x2 = getLocalX() + xOffsetWalk;
				attachment.y2 = getLocalY() + yOffsetWalk;
				attachment.speed1 = speedOne;
				attachment.speed2 = speedTwo;
				attachment.direction = directionSet == "NORTH" ? 0
						: directionSet == "EAST" ? 1 : directionSet == "SOUTH" ? 2 : directionSet == "WEST" ? 3 : 0;
				super.stop();
			}
		});
		World.getWorld().getEventHandler()
				.submit(new Event<Player>("force_movement", this, Math.abs(xOffsetWalk) + Math.abs(yOffsetWalk)) {

					@Override
					public void execute() {
						if (attachment == null || attachment.disconnected) {
							super.stop();
							return;
						}
						forceMovementActive = false;
						attachment.getPA().movePlayer(xOffset, yOffset, attachment.getHeight());
						if (attachment.playerEquipment[attachment.playerWeapon] == -1) {
							attachment.playerStandIndex = 0x328;
							attachment.playerTurnIndex = 0x337;
							attachment.playerWalkIndex = 0x333;
							attachment.playerTurn180Index = 0x334;
							attachment.playerTurn90CWIndex = 0x335;
							attachment.playerTurn90CCWIndex = 0x336;
							attachment.playerRunIndex = 0x338;
						} else {
							attachment.getCombat().getPlayerAnimIndex(ItemUtility
									.getItemName(attachment.playerEquipment[attachment.playerWeapon]).toLowerCase());
						}
						forceMovement = false;
						super.stop();
					}
				});
	}

	public void appendMask400Update(Buffer str) {
		str.writeByteS(x1);
		str.writeByteS(y1);
		str.writeByteS(x2);
		str.writeByteS(y2);
		str.writeWordBigEndianA(speed1);
		str.writeWordA(speed2);
		str.writeByteS(direction);
	}

	public void appendPlayerUpdateBlock(Buffer str) {
		if (!updateRequired && !isChatTextUpdateRequired())
			return;
		int updateMask = 0;

		if (forceMovement) {
			updateMask |= 0x400;
		}

		if (graphicMaskUpdate0x100) {
			updateMask |= 0x100;
		}

		if (animationRequest != -1) {
			updateMask |= 8;
		}

		if (forcedChatUpdateRequired) {
			updateMask |= 4;
		}

		if (isChatTextUpdateRequired()) {
			updateMask |= 0x80;
		}

		if (isAppearanceUpdateRequired()) {
			updateMask |= 0x10;
		}

		if (faceUpdateRequired) {
			updateMask |= 1;
		}

		if (FocusPointX != -1) {
			updateMask |= 2;
		}

		if (hitUpdateRequired) {
			updateMask |= 0x20;
		}

		if (hitUpdateRequired2) {
			updateMask |= 0x200;
		}
		
		if(soundEffect != -1) {
			updateMask |= 0x400;
		}

		if (updateMask >= 0x100) {
			updateMask |= 0x40;
			str.writeByte(updateMask & 0xFF);
			str.writeByte(updateMask >> 8);
		} else {
			str.writeByte(updateMask);
		}

		if (forceMovement) {
			appendMask400Update(str);
		}

		if (graphicMaskUpdate0x100) {
			appendMask100Update(str);
		}

		if (animationRequest != -1) {
			appendAnimationRequest(str);
		}

		if (forcedChatUpdateRequired) {
			appendForcedChat(str);
		}

		if (isChatTextUpdateRequired()) {
			appendPlayerChatText(str);
		}

		if (faceUpdateRequired) {
			appendFaceUpdate(str);
		}

		if (isAppearanceUpdateRequired()) {
			appendPlayerAppearance(str);
		}

		if (FocusPointX != -1) {
			appendSetFocusDestination(str);
		}

		if (hitUpdateRequired) {
			appendHitUpdate(str);
		}

		if (hitUpdateRequired2) {
			appendHitUpdate2(str);
		}
		

		if(soundEffect != -1) {
			appendSoundUpdate(str);
		}

	}

	public void clearUpdateFlags() {
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setWalkingDirection(Direction.NONE);
		setRunningDirection(Direction.NONE);
		setNeedsPlacement(false);
		hitUpdateRequired = false;
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		graphicMaskUpdate0x100 = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		faceUpdateRequired = false;
		forceMovement = false;
		face = 65535;
	}

	public void stopMovement() {
//		if (teleportToX <= 0 && teleportToY <= 0) {
//			teleportToX = getX();
//			teleportToY = getY();
//		}
//		newWalkCmdSteps = 0;
//		getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
//		getNextPlayerMovement();
		getMovementQueue().reset();
		getMovementQueue().resetFollowing();
	}

//	public void preProcessing() {
//		newWalkCmdSteps = 0;
//	}

	public int setPacketsReceived(int packetsReceived) {
		return packetsReceived;
	}

	public int getPacketsReceived() {
		return packetsReceived;
	}

//	public void postProcessing() {
//		if (newWalkCmdSteps > 0) {
//			int firstX = getNewWalkCmdX()[0], firstY = getNewWalkCmdY()[0];
//
//			int lastDir = 0;
//			boolean found = false;
//			numTravelBackSteps = 0;
//			int ptr = wQueueReadPtr;
//			int dir = Misc.direction(currentX, currentY, firstX, firstY);
//			if (dir != -1 && (dir & 1) != 0) {
//				do {
//					lastDir = dir;
//					if (--ptr < 0)
//						ptr = walkingQueueSize - 1;
//
//					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
//					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
//					dir = Misc.direction(walkingQueueX[ptr], walkingQueueY[ptr], firstX, firstY);
//					if (lastDir != dir) {
//						found = true;
//						break;
//					}
//
//				} while (ptr != wQueueWritePtr);
//			} else
//				found = true;
//
//			if (!found)
//				println_debug("Fatal: couldn't find connection vertex! Dropping packet.");
//			else {
//				wQueueWritePtr = wQueueReadPtr;
//
//				addToWalkingQueue(currentX, currentY);
//
//				if (dir != -1 && (dir & 1) != 0) {
//
//					for (int i = 0; i < numTravelBackSteps - 1; i++) {
//						addToWalkingQueue(travelBackX[i], travelBackY[i]);
//					}
//					int wayPointX2 = travelBackX[numTravelBackSteps - 1],
//							wayPointY2 = travelBackY[numTravelBackSteps - 1];
//					int wayPointX1, wayPointY1;
//					if (numTravelBackSteps == 1) {
//						wayPointX1 = currentX;
//						wayPointY1 = currentY;
//					} else {
//						wayPointX1 = travelBackX[numTravelBackSteps - 2];
//						wayPointY1 = travelBackY[numTravelBackSteps - 2];
//					}
//
//					dir = Misc.direction(wayPointX1, wayPointY1, wayPointX2, wayPointY2);
//					if (dir == -1 || (dir & 1) != 0) {
//						println_debug("Fatal: The walking queue is corrupt! wp1=(" + wayPointX1 + ", " + wayPointY1
//								+ "), " + "wp2=(" + wayPointX2 + ", " + wayPointY2 + ")");
//					} else {
//						dir >>= 1;
//						found = false;
//						int x = wayPointX1, y = wayPointY1;
//						while (x != wayPointX2 || y != wayPointY2) {
//							x += Misc.directionDeltaX[dir];
//							y += Misc.directionDeltaY[dir];
//							if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
//								found = true;
//								break;
//							}
//						}
//						if (!found) {
//							println_debug("Fatal: Internal error: unable to determine connection vertex!" + "  wp1=("
//									+ wayPointX1 + ", " + wayPointY1 + "), wp2=(" + wayPointX2 + ", " + wayPointY2
//									+ "), " + "first=(" + firstX + ", " + firstY + ")");
//						} else
//							addToWalkingQueue(wayPointX1, wayPointY1);
//					}
//				} else {
//					for (int i = 0; i < numTravelBackSteps; i++) {
//						addToWalkingQueue(travelBackX[i], travelBackY[i]);
//					}
//				}
//
//				for (int i = 0; i < newWalkCmdSteps; i++) {
//					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
//				}
//
//			}
//
//			isRunning = isNewWalkCmdIsRunning() || isRunning2;
//		}
//	}

	public int getMapRegionX() {
		return getLastKnownLocation().getRegionX();
	}

	public int getMapRegionY() {
		return getLastKnownLocation().getRegionY();
	}

	public boolean inPcBoat() {
		return getX() >= 2660 && getX() <= 2663 && getY() >= 2638 && getY() <= 2643;
	}

	public boolean inPcGame() {
		return getX() >= 2624 && getX() <= 2690 && getY() >= 2550 && getY() <= 2619;
	}

	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}

	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}

	public int getHitDiff() {
		return hitDiff;
	}

	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}

	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}

	public int getChatTextEffects() {
		return chatTextEffects;
	}

	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}

	public byte getChatTextSize() {
		return chatTextSize;
	}

	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}

	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}

	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}

	public byte[] getChatText() {
		return chatText;
	}

	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}

	public int getChatTextColor() {
		return chatTextColor;
	}

//	public void setNewWalkCmdX(int newWalkCmdX[]) {
//		this.newWalkCmdX = newWalkCmdX;
//	}
//
//	public int[] getNewWalkCmdX() {
//		return newWalkCmdX;
//	}
//
//	public void setNewWalkCmdY(int newWalkCmdY[]) {
//		this.newWalkCmdY = newWalkCmdY;
//	}
//
//	public int[] getNewWalkCmdY() {
//		return newWalkCmdY;
//	}
//
//	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
//		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
//	}
//
//	public boolean isNewWalkCmdIsRunning() {
//		return newWalkCmdIsRunning;
//	}

	public boolean getRingOfLifeEffect() {
		return maxCape[0];
	}

	public boolean setRingOfLifeEffect(boolean effect) {
		return maxCape[0] = effect;
	}

	public boolean getFishingEffect() {
		return maxCape[1];
	}

	public boolean setFishingEffect(boolean effect) {
		return maxCape[1] = effect;
	}

	public boolean getMiningEffect() {
		return maxCape[2];
	}

	public boolean setMiningEffect(boolean effect) {
		return maxCape[2] = effect;
	}

	public boolean getWoodcuttingEffect() {
		return maxCape[3];
	}

	public boolean setWoodcuttingEffect(boolean effect) {
		return maxCape[3] = effect;
	}

	public int getSkeletalMysticDamageCounter() {
		return raidsDamageCounters[0];
	}

	public void setSkeletalMysticDamageCounter(int damage) {
		this.raidsDamageCounters[0] = damage;
	}

	public int getTektonDamageCounter() {
		return raidsDamageCounters[1];
	}

	public void setTektonDamageCounter(int damage) {
		this.raidsDamageCounters[1] = damage;
	}

	public int getIceDemonDamageCounter() {
		return raidsDamageCounters[2];
	}

	public void setIceDemonDamageCounter(int damage) {
		this.raidsDamageCounters[2] = damage;
	}

	public int getGlodDamageCounter() {
		return raidsDamageCounters[3];
	}

	public void setGlodDamageCounter(int damage) {
		this.raidsDamageCounters[3] = damage;
	}

	public int getIceQueenDamageCounter() {
		return raidsDamageCounters[4];
	}

	public void setIceQueenDamageCounter(int damage) {
		this.raidsDamageCounters[4] = damage;
	}

	public int getAntiSantaDamageCounter() {
		return raidsDamageCounters[5];
	}

	public void setAntiSantaDamageCounter(int damage) {
		this.raidsDamageCounters[5] = damage;
	}
	
	public void assignClueCounter(RewardLevel rewardLevel) {
		switch (rewardLevel) {
		case EASY:
			counters[0]++;
		case MEDIUM:
			counters[1]++;
		case HARD:
			counters[2]++;
		case MASTER:
			counters[3]++;
		default:
			break;
		}
	}

	public int getClueCounter(RewardLevel rewardLevel) {
		switch (rewardLevel) {
		case EASY:
			return counters[0];
		case MEDIUM:
			return counters[1];
		case HARD:
			return counters[2];
		case MASTER:
			return counters[3];
		default:
			return 0;
		}
	}

	public int getEasyClueCounter() {
		return counters[0];
	}

	public void setEasyClueCounter(int counters) {
		this.counters[0] = counters;
	}

	public int getMediumClueCounter() {
		return counters[1];
	}

	public void setMediumClueCounter(int counters) {
		this.counters[1] = counters;
	}

	public int getHardClueCounter() {
		return counters[2];
	}

	public void setHardClueCounter(int counters) {
		this.counters[2] = counters;
	}

	public int getMasterClueCounter() {
		return counters[3];
	}

	public void setMasterClueCounter(int counters) {
		this.counters[3] = counters;
	}

	public int getBarrowsChestCounter() {
		return counters[4];
	}

	public void setBarrowsChestCounter(int counters) {
		this.counters[4] = counters;
	}

	public int getDuelWinsCounter() {
		return counters[5];
	}

	public void setDuelWinsCounter(int counters) {
		this.counters[5] = counters;
	}

	public int getDuelLossCounter() {
		return counters[6];
	}

	public void setDuelLossCounter(int counters) {
		this.counters[6] = counters;
	}

	public int getHalloweenOrderCount() {
		return counters[7];
	}

	public void setHalloweenOrderCount(int counters) {
		this.counters[7] = counters;
	}
	
	public int getEliteClueCounter() {
		return counters[8];
	}

	public void setEliteClueCounter(int counters) {
		this.counters[8] = counters;
	}

	public boolean samePlayer() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == getIndex())
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}
			}
		}
		return false;
	}

	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();
	}

	public String getLastClanChat() {
		return lastClanChat;
	}

	public void setLastClanChat(String founder) {
		lastClanChat = founder;
	}

	public long getNameAsLong() {
		return nameAsLong;
	}

	public void setNameAsLong(long hash) {
		this.nameAsLong = hash;
	}

	public boolean isStopPlayer() {
		return stopPlayer;
	}

	public void setStopPlayer(boolean stopPlayer) {
		this.stopPlayer = stopPlayer;
	}

	public int getFace() {
		return this.getIndex() + '\u8000';
	}

	public int getLockIndex() {
		return -this.getIndex() - 1;
	}

	public boolean isDead() {
		return getHealth().getAmount() <= 0 || this.isDead;
	}

	public void healPlayer(int heal) {
		getHealth().increase(heal);
	}

	int maxLevel() {
		return 99;
	}

	public void sendGraphic(int id, int height) {
		if (height == 0) {
			this.gfx0(id);
		}

		if (height == 100) {
			this.gfx100(id);
		}

	}

	public boolean protectingRange() {
		return this.prayerActive[17];
	}

	public boolean protectingMagic() {
		return this.prayerActive[16];
	}

	public boolean protectingMelee() {
		return this.prayerActive[18];
	}

	public void setTrading(boolean trading) {
		this.trading = trading;
	}

	public boolean isTrading() {
		return this.trading;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public boolean inGodmode() {
		return godmode;
	}

	public void setGodmode(boolean godmode) {
		this.godmode = godmode;
	}

	public boolean inSafemode() {
		return safemode;
	}

	public void setSafemode(boolean safemode) {
		this.safemode = safemode;
	}

	public PortalTeleports getPortalTeleports() {
		return portalTeleports;
	}

	public void setDragonfireShieldCharge(int charge) {
		this.dragonfireShieldCharge = charge;
	}

	public int getDragonfireShieldCharge() {
		return dragonfireShieldCharge;
	}

	public void setLastDragonfireShieldAttack(long lastAttack) {
		this.lastDragonfireShieldAttack = lastAttack;
	}

	public long getLastDragonfireShieldAttack() {
		return lastDragonfireShieldAttack;
	}

	public boolean isDragonfireShieldActive() {
		return dragonfireShieldActive;
	}

	public void setDragonfireShieldActive(boolean dragonfireShieldActive) {
		this.dragonfireShieldActive = dragonfireShieldActive;
	}

	/**
	 * Retrieves the rights for this player.
	 * 
	 * @return the rights
	 */
	public RightGroup getRights() {
		if (rights == null) {
			rights = new RightGroup(this, Right.PLAYER);
		}
		return rights;
	}

	/**
	 * Returns a single instance of the Titles class for this player
	 * 
	 * @return the titles class
	 */
	public Titles getTitles() {
		if (titles == null) {
			titles = new Titles(this);
		}
		return titles;
	}

	public RandomEventInterface getInterfaceEvent() {
		return randomEventInterface;
	}

	/**
	 * Modifies the current interface open
	 * 
	 * @param interfaceOpen
	 *            the interface id
	 */
	public void setInterfaceOpen(int interfaceOpen) {
		this.interfaceOpen = interfaceOpen;
	}

	/**
	 * The interface that is opened
	 * 
	 * @return the interface id
	 */
	public int getInterfaceOpen() {
		return interfaceOpen;
	}

	/**
	 * Determines whether a warning will be shown when dropping an item.
	 * 
	 * @return True if it's the case, False otherwise.
	 */
	public boolean showDropWarning() {
		return dropWarning;
	}

	/**
	 * Change whether a warning will be shown when dropping items.
	 * 
	 * @param shown
	 *            True in case a warning must be shown, False otherwise.
	 */
	public void setDropWarning(boolean shown) {
		dropWarning = shown;
	}

	public boolean getHourlyBoxToggle() {
		return hourlyBoxToggle;
	}

	public void setHourlyBoxToggle(boolean toggle) {
		hourlyBoxToggle = toggle;
	}

	public boolean getFracturedCrystalToggle() {
		return fracturedCrystalToggle;
	}

	public void setFracturedCrystalToggle(boolean toggle1) {
		fracturedCrystalToggle = toggle1;
	}

	public long setBestZulrahTime(long bestZulrahTime) {
		return this.bestZulrahTime = bestZulrahTime;
	}

	public long getBestZulrahTime() {
		return bestZulrahTime;
	}

	public ZulrahLostItems getZulrahLostItems() {
		if (lostItemsZulrah == null) {
			lostItemsZulrah = new ZulrahLostItems(this);
		}
		return lostItemsZulrah;
	}

	public CerberusLostItems getCerberusLostItems() {
		if (lostItemsCerberus == null) {
			lostItemsCerberus = new CerberusLostItems(this);
		}
		return lostItemsCerberus;
	}

	public SkotizoLostItems getSkotizoLostItems() {
		if (lostItemsSkotizo == null) {
			lostItemsSkotizo = new SkotizoLostItems(this);
		}
		return lostItemsSkotizo;
	}

	public int getArcLightCharge() {
		return arcLightCharge;
	}

	public void setArcLightCharge(int chargeArc) {
		this.arcLightCharge = chargeArc;
	}

	public int getToxicBlowpipeCharge() {
		return toxicBlowpipeCharge;
	}

	public void setToxicBlowpipeCharge(int charge) {
		this.toxicBlowpipeCharge = charge;
	}

	public int getToxicBlowpipeAmmo() {
		return toxicBlowpipeAmmo;
	}

	public int getToxicBlowpipeAmmoAmount() {
		return toxicBlowpipeAmmoAmount;
	}

	public void setToxicBlowpipeAmmoAmount(int amount) {
		this.toxicBlowpipeAmmoAmount = amount;
	}

	public void setToxicBlowpipeAmmo(int ammo) {
		this.toxicBlowpipeAmmo = ammo;
	}

	public int getSerpentineHelmCharge() {
		return this.serpentineHelmCharge;
	}

	public void setSerpentineHelmCharge(int charge) {
		this.serpentineHelmCharge = charge;
	}

	public int getTridentCharge() {
		return tridentCharge;
	}

	public void setTridentCharge(int tridentCharge) {
		this.tridentCharge = tridentCharge;
	}

	public int getToxicTridentCharge() {
		return toxicTridentCharge;
	}

	public void setToxicTridentCharge(int toxicTridentCharge) {
		this.toxicTridentCharge = toxicTridentCharge;
	}
	
	public int getSangStaffCharge() {
		return sangStaffCharge;
	}

	public void setSangStaffCharge(int sangStaffCharge) {
		this.sangStaffCharge = sangStaffCharge;
	}

	public Fletching getFletching() {
		return fletching;
	}

	public long getLastIncentive() {
		return lastIncentive;
	}

	public void setLastIncentive(long lastIncentive) {
		this.lastIncentive = lastIncentive;
	}

	public boolean receivedIncentiveWarning() {
		return this.incentiveWarning;
	}

	public void updateIncentiveWarning() {
		this.incentiveWarning = true;
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	public Mode getMode() {
		return mode;
	}
	public Mode setMode(Mode mode) {
		return this.mode = mode;
	}

	public String getRevertOption() {
		return revertOption;
	}

	public void setRevertOption(String revertOption) {
		this.revertOption = revertOption;
	}

	public long getRevertModeDelay() {
		return revertModeDelay;
	}

	public void setRevertModeDelay(long revertModeDelay) {
		this.revertModeDelay = revertModeDelay;
	}

	/**
	 * 
	 * @param skillId
	 * @param amount
	 */
	public void replenishSkill(int skillId, int amount) {
		if (skillId < 0 || skillId > Skill.length() - 1) {
			return;
		}
		int current = skills.getLevel(Skill.forId(skillId));
		skills.setLevelOrActual(current++, Skill.forId(skillId));
		skills.sendRefresh();
	}

	public void setArenaPoints(int arenaPoints) {
		this.arenaPoints = arenaPoints;
	}

	public int getArenaPoints() {
		return arenaPoints;
	}

	public void setShayPoints(int shayPoints) {
		this.shayPoints = shayPoints;
	}

	public int getShayPoints() {
		return shayPoints;
	}

	public void setRaidPoints(int raidPoints) {
		this.raidPoints = raidPoints;
	}

	public int getRaidPoints() {
		return raidPoints;
	}

	static {
		playerProps = new Buffer(new byte[100]);
	}

	@Override
	public boolean susceptibleTo(HealthStatus status) {
		if (getItems().isWearingItem(12931, playerHat) || getItems().isWearingItem(13199, playerHat)
				|| getItems().isWearingItem(13197, playerHat)) {
			return false;
		}
		return true;
	}

	public int getToxicStaffOfTheDeadCharge() {
		return toxicStaffOfTheDeadCharge;
	}

	public void setToxicStaffOfTheDeadCharge(int toxicStaffOfTheDeadCharge) {
		this.toxicStaffOfTheDeadCharge = toxicStaffOfTheDeadCharge;
	}

	public long getExperienceCounter() {
		return experienceCounter;
	}

	public void setExperienceCounter(long experienceCounter) {
		this.experienceCounter = experienceCounter;
	}

	public int getRunEnergy() {
		return runEnergy;
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = runEnergy;
	}

	public int getLastEnergyRecovery() {
		return lastEnergyRecovery;
	}

	public void setLastEnergyRecovery(int lastEnergyRecovery) {
		this.lastEnergyRecovery = lastEnergyRecovery;
	}

	public Entity getTargeted() {
		return targeted;
	}

	public void setTargeted(Entity targeted) {
		this.targeted = targeted;
	}

	public LootingBag getLootingBag() {
		return lootingBag;
	}

	public PrestigeSkills getPrestige() {
		return prestigeskills;
	}

	public ExpLock getExpLock() {
		return explock;
	}

	public void setLootingBag(LootingBag lootingBag) {
		this.lootingBag = lootingBag;
	}

	public SafeBox getSafeBox() {
		return safeBox;
	}

	public void setSafeBox(SafeBox safeBox) {
		this.safeBox = safeBox;
	}

	public RunePouch getRunePouch() {
		return runePouch;
	}

	public void setRunePouch(RunePouch runePouch) {
		this.runePouch = runePouch;
	}

	public HerbSack getHerbSack() {
		return herbSack;
	}

	public void setHerbSack(HerbSack herbSack) {
		this.herbSack = herbSack;
	}

	public GemBag getGemBag() {
		return gemBag;
	}

	public void setGemBag(GemBag gemBag) {
		this.gemBag = gemBag;
	}

	public AchievementDiary<?> getDiary() {
		return diary;
	}

	public void setDiary(AchievementDiary<?> diary) {
		this.diary = diary;
	}

	public AchievementDiaryManager getDiaryManager() {
		return diaryManager;
	}

	public KalphiteQueen getKq() {
		return kq;
	}

	public void setKq(KalphiteQueen kq) {
		this.kq = kq;
	}

	public QuickPrayers getQuick() {
		return quick;
	}

	@Getter
	private ArrayDeque<Runnable> eventQueue = Queues.newArrayDeque();

	@Override
	public int getSize() {
		return 1;
	}
	
	@Setter public boolean allowMapUpdate = true;

	public boolean allowMapUpdate() {
		return allowMapUpdate;
	}
	
	@Getter @Setter
	private Optional<WorldObject> interactingObject = Optional.empty();


	public void resetInteractingObject() {
		interactingObject = Optional.empty();
	}
	
	public void deactivateProtectionPrayers() {
		for (int i = 0; i < prayerActive.length; i++) {
			if (i == 16 || i == 17 || i == 18)
				prayerActive[i] = false;
		}
	}
	//16, 17, 18
}
