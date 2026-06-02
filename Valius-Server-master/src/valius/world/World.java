package valius.world;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Charsets;

import lombok.Getter;
import lombok.Setter;
import valius.backup.BackupHandler;
import valius.cache.definitions.IdentityKit;
import valius.clip.MapIndexLoader;
import valius.clip.ObjectDef;
import valius.clip.Region;
import valius.clip.doors.DoorDefinition;
import valius.content.GIMRepository;
import valius.content.ReferralEvent;
import valius.content.clans.ClanManager;
import valius.content.falling_stars.FallingStars;
import valius.content.godwars.GodwarsEquipment;
import valius.content.godwars.GodwarsNPCs;
import valius.content.quest.QuestManager;
import valius.content.tradingpost.Listing;
import valius.content.trails.CasketRewards;
import valius.content.wogw.Wogw;
import valius.discord.DiscordBot;
import valius.event.EventHandler;
import valius.event.impl.BonusApplianceEvent;
import valius.event.impl.DidYouKnowEvent;
import valius.event.impl.SkeletalMysticEvent;
import valius.event.impl.WheatPortalEvent;
import valius.model.entity.instance.InstanceManager;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.EventBoss.EventBossHandler;
import valius.model.entity.npc.bosses.wildernessboss.WildernessBossHandler;
import valius.model.entity.npc.combat.CombatScriptHandler;
import valius.model.entity.npc.combat.NPCCombatStats;
import valius.model.entity.npc.drops.DropManager;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.monsterhunt.MonsterHunt;
import valius.model.hiscores.HiscoreHandler;
import valius.model.holiday.HolidayController;
import valius.model.items.ItemDefinition;
import valius.model.lobby.LobbyManager;
import valius.model.minigames.FightPits;
import valius.model.multiplayer_session.MultiplayerSessionListener;
import valius.net.packet.impl.Commands;
import valius.punishments.PunishmentCycleEvent;
import valius.punishments.Punishments;
import valius.server.data.ServerData;
import valius.util.WipeLines;
import valius.world.event.CyclicEventManager;
import valius.world.objects.GlobalObjects;

@Getter
public class World {

	public static World getWorld() {
		return singleton;
	}

	private static World singleton = new World();

	private World() {
	}

	public void onShutdown() {
		ReferralEvent.get().saveEvent();
	}

	private static final ScheduledExecutorService ONLINE_COUNT = Executors.newSingleThreadScheduledExecutor();

	public void init() throws Exception {
		checkForLocal();
		IdentityKit.unpack();
		punishments.initialize();
		holidayController.initialize();
		ReferralEvent.get().loadEvent();
		CombatScriptHandler.init();
		npcHandler.init();
		dropManager.read();
		//Listing.loadNextSale();
//		if(!betaWorld) {
//			if(!localWorld) {
//				DiscordBot.initialize();
//			} else {
//				DiscordBot.initializeLocal();
//			}
//		} else {
//			BetaConfiguration.load();
//		}
		GIMRepository.load();
		Listing.load();
		Wogw.init();
		ItemDefinition.load();
		DoorDefinition.load();
		NPCCombatStats.load();
		GodwarsEquipment.load();
		GodwarsNPCs.load();
		CasketRewards.read();
		ObjectDef.unpackConfig();
		MapIndexLoader.load();
		//Region.loadHardcodedObjects();
		globalObjects.loadGlobalObjectFile();

		Commands.initializeCommands();
		LobbyManager.initializeLobbies();

		MonsterHunt.spawnNPC();
		EventBossHandler.spawnNPC();
		WildernessBossHandler.spawnNPC();
		FallingStars.newStar();
		QuestManager.init();

		eventHandler.submit(new DidYouKnowEvent());
		eventHandler.submit(new WheatPortalEvent());
		eventHandler.submit(new BonusApplianceEvent());
		eventHandler.submit(new SkeletalMysticEvent());
		eventHandler.submit(new PunishmentCycleEvent(punishments, 50));
		

		BackupHandler.begin();
		if(!localWorld && !betaWorld) {
			ONLINE_COUNT.scheduleAtFixedRate(() -> {
				try {
					Files.write(new File("C:/xampp/htdocs/online.txt").toPath(), (PlayerHandler.getRealPlayerCount() + "").getBytes(Charsets.UTF_8));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}, 10, 30, TimeUnit.SECONDS);


		}
	}

	public void tick() {
		eventHandler.process();
		serverData.processQueue();	
		itemHandler.process();
		playerHandler.process();
		npcHandler.process();
		shopHandler.process();
		globalObjects.pulse();
		InstanceManager.tick();
	}

	/**
	 * Contains data which is saved between sessions.
	 */
	private ServerData serverData = new ServerData();

	/**
	 * Calls the usage of player items.
	 */
	private ItemHandler itemHandler = new ItemHandler();

	/**
	 * Handles logged in players.
	 */
	private PlayerHandler playerHandler = new PlayerHandler();

	/**
	 * Handles global NPCs.
	 */
	private NPCHandler npcHandler = new NPCHandler();

	/**
	 * Handles global shops.
	 */
	private ShopHandler shopHandler = new ShopHandler();

	/**
	 * Handles the fightpits minigame.
	 */
	private FightPits fightPits = new FightPits();


	private Punishments punishments = new Punishments();

	private DropManager dropManager = new DropManager();

	/**
	 * A class that will manage game events
	 */
	private EventHandler eventHandler = new EventHandler();

	private HiscoreHandler hiscores = new HiscoreHandler();


	private HolidayController holidayController = new HolidayController();

	private MultiplayerSessionListener multiplayerSessionListener = new MultiplayerSessionListener();

	private GlobalObjects globalObjects = new GlobalObjects();

	private CyclicEventManager cyclicEventManager = new CyclicEventManager();

	/**
	 * ClanChat Added by Valiant
	 */
	public ClanManager clanManager = new ClanManager();

	@Setter
	private boolean gameUpdating;

	private boolean localWorld, betaWorld;

	@Getter
	@Setter
	private boolean worldLoaded;

	public void reloadShops() {
		shopHandler = new ShopHandler();
	}

	public void reloadNPCHandler() {
		npcHandler = new NPCHandler();
	}

	public void checkForLocal() {
		try(InputStream in = new URL( "http://checkip.amazonaws.com" ).openStream();) {
			String ip = IOUtils.toString( in , Charsets.UTF_8).trim();
			InetAddress liveAddress = InetAddress.getByName("server.valius.net"); 
			InetAddress betaAddress = InetAddress.getByName("beta.valius.net"); 
			betaWorld = ip.equalsIgnoreCase(betaAddress.getHostAddress().trim());
			localWorld = !betaWorld && !ip.equalsIgnoreCase(liveAddress.getHostAddress().trim());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
