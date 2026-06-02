package valius.content.teleportation;

import valius.Config;
import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.content.gauntlet.TheGauntlet;
import valius.model.entity.player.Player;

public class CityTeleports {
	
	public enum TeleportData {
		
		//Regular
		VARROCK(1164, 25, new int[] { 554, 1, 556, 3, 563, 1 }, Config.VARROCK_X, Config.VARROCK_Y, 63, 35),
		LUMBRIDGE(1167, 31, new int[] { 557, 1, 556, 3, 563, 1 }, Config.LUMBY_X, Config.LUMBY_Y, 64, 41),
		FALADOR(1170, 37, new int[] { 555, 1, 556, 3, 563, 1 }, Config.FALADOR_X, Config.FALADOR_Y, 65, 48),
		CAMELOT(1174, 45, new int[] { 556, 5, 563, 1, -1, -1 }, Config.CAMELOT_X, Config.CAMELOT_Y, 66, 55.5),
		ARDOUGNE(1540, 51, new int[] { 555, 2, 563, 2, -1, -1 }, Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 67, 61),
		WATCHTOWER(1541, 58, new int[] { 557, 2, 563, 2, -1, -1 }, Config.WATCHTOWER_X, Config.WATCHTOWER_Y, 68, 68),
		TROLLHEIM(7455, 61, new int[] { 554, 2, 563, 2, -1, -1 }, Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 69, 68),
		
		//Ancients
		PADDEWWA(13035, 54, new int[] { 563, 2, 554, 1, 556, 1 }, Config.PADDEWWA_X, Config.PADDEWWA_Y, 70, 64),
		SENNTISTEN(13045, 60, new int[] { 563, 2, 566, 1, -1, -1 }, Config.SENNTISTEN_X, Config.SENNTISTEN_Y, 71, 70),
		KHARYRLL(13053, 66, new int[] { 563, 2, 565, 1, -1, -1 }, Config.KHARYRLL_X, Config.KHARYRLL_Y, 72, 76),
		LASSAR(13061, 72, new int[] { 563, 2, 555, 4, -1, -1 }, Config.LASSAR_X, Config.LASSAR_Y, 73, 82),
		DAREEYAK(13069, 78, new int[] { 563, 2, 554, 3, 556, 2 }, Config.DAREEYAK_X, Config.DAREEYAK_Y, 74, 88),
		CARRALLANGAR(13079, 84, new int[] { 563, 2, 566, 2, -1, -1 }, Config.CARRALLANGAR_X, Config.CARRALLANGAR_Y, 75, 94),
		ANNAKARL(13087, 90, new int[] { 563, 2, 565, 2, -1, -1 }, Config.ANNAKARL_X, Config.ANNAKARL_Y, 76, 100),
		GHORROCK(13095, 96, new int[] { 563, 2, 555, 8, -1, -1 }, Config.GHORROCK_X, Config.GHORROCK_Y, 77, 1016),
		
		//Lunar
		MOONCLAN(30064, 69, new int[] { 9075, 2, 563, 1, 557, 2 }, Config.MOONCLAN_X, Config.MOONCLAN_Y, 78, 66),
		OURANIA(30083, 71, new int[] { 9075, 2, 563, 1, 557, 6 }, Config.OURANIA_X, Config.OURANIA_Y, 79, 69),
		WATERBIRTH(30106, 72, new int[] { 9075, 2, 563, 1, 555, 1 }, Config.WATERBIRTH_X, Config.WATERBIRTH_Y, 80, 71),
		BARBARIAN(30138, 75, new int[] { 9075, 2, 563, 2, 554, 3 }, Config.BARBARIAN_X, Config.BARBARIAN_Y, 81, 76),
		KHAZARD(30162, 78, new int[] { 9075, 2, 563, 2, 555, 4 }, Config.KHAZARD_X, Config.KHAZARD_Y, 82, 80),
		FISHING_GUILD(30226, 85, new int[] { 9075, 3, 563, 3, 555, 10 }, Config.FISHING_GUILD_X, Config.FISHING_GUILD_Y, 83, 89),
		CATHERBY(30250, 87, new int[] { 9075, 3, 563, 3, 555, 10 }, Config.CATHERBY_X, Config.CATHERBY_Y, 84, 92),
		ICE_PLATEU(30266, 89, new int[] { 9075, 3, 563, 3, 555, 8 }, Config.ICE_PLATEU_X, Config.ICE_PLATEU_Y, 85, 96);
		
		private final int buttonId;
		private final int level;
		private final int[] runes;
		private final int x;
		private final int y;
		private final int spellId;
		private final double xp;
		
		TeleportData(int buttonId, int level, int[] runes, int x, int y, int spellId, double xp) {
			this.buttonId = buttonId;
			this.level = level;
			this.runes = runes;
			this.x = x;
			this.y = y;
			this.spellId = spellId;
			this.xp = xp;
		}

		public int getButtonId() {
			return buttonId;
		}

		public int getLevel() {
			return level;
		}

		public int[] getRunes() {
			return runes;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public int getSpellId() {
			return spellId;
		}
		
		public double getXP() {
			return xp;
		}
		
	}
	
	public static void teleport(Player player, int buttonId) {
		if (player.inClanWars() || player.inClanWarsSafe()) {
			player.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
			return;
		}
		if (player.getInstance() != null && player.getInstance() instanceof TheGauntlet) {
			player.sendMessage("You can't teleport while in The Gauntlet.");
			return;
		}
		player.usingMagic = true;
		for (TeleportData data : TeleportData.values()) {
			if (data.getButtonId() == buttonId) {
				if (System.currentTimeMillis() - player.lastTeleport < 5000) {
					return;	
				}
				if (!player.getCombat().checkMagicReqs(data.getSpellId())) {
					return;
				}
				player.getPA().spellTeleport(data.getX(), data.getY(), 0, false);
				player.getPA().addSkillXP((int) data.getXP(), 6, true);
				player.lastTeleport = System.currentTimeMillis();
				switch (data) {
				case LUMBRIDGE:
					player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);
					break;
				
				case CAMELOT:
					player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
					break;
				
				case ARDOUGNE:
					player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
					break;
					
				case FALADOR:
					player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_TO_FALADOR);
					break;
					
				case TROLLHEIM:
					player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TROLLHEIM_TELEPORT);
					break;
					
				case WATERBIRTH:
					player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.WATERBIRTH_TELEPORT);
					break;
					
				case CATHERBY:
					player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CATHERY_TELEPORT);
					break;
					
				case GHORROCK:
					player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.GHORROCK);
					break;
				
				default:
					break;
				}
			}
		}
	}
}
