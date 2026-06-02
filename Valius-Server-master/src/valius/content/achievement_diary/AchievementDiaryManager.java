package valius.content.achievement_diary;

import valius.content.achievement_diary.ardougne.ArdougneAchievementDiary;
import valius.content.achievement_diary.desert.DesertAchievementDiary;
import valius.content.achievement_diary.falador.FaladorAchievementDiary;
import valius.content.achievement_diary.fremennik.FremennikAchievementDiary;
import valius.content.achievement_diary.kandarin.KandarinAchievementDiary;
import valius.content.achievement_diary.karamja.KaramjaAchievementDiary;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorAchievementDiary;
import valius.content.achievement_diary.morytania.MorytaniaAchievementDiary;
import valius.content.achievement_diary.varrock.VarrockAchievementDiary;
import valius.content.achievement_diary.western_provinces.WesternAchievementDiary;
import valius.content.achievement_diary.wilderness.WildernessAchievementDiary;
import valius.model.entity.player.Player;

public final class AchievementDiaryManager {

	private final Player player;

	private final VarrockAchievementDiary varrockDiary;
	private final ArdougneAchievementDiary ardougneDiary;
	private final FaladorAchievementDiary faladorDiary;
	private final LumbridgeDraynorAchievementDiary lumbridgeDraynorDiary;
	private final KaramjaAchievementDiary karamjaDiary;
	private final WildernessAchievementDiary wildernessDiary;
	private final MorytaniaAchievementDiary morytaniaDiary;
	private final KandarinAchievementDiary kandarinDiary;
	private final FremennikAchievementDiary fremennikDiary;
	private final WesternAchievementDiary westernDiary;
	private final DesertAchievementDiary desertDiary;

	public AchievementDiaryManager(Player player) {
		this.player = player;

		varrockDiary = new VarrockAchievementDiary(player);
		ardougneDiary = new ArdougneAchievementDiary(player);
		faladorDiary = new FaladorAchievementDiary(player);
		lumbridgeDraynorDiary = new LumbridgeDraynorAchievementDiary(player);
		karamjaDiary = new KaramjaAchievementDiary(player);
		wildernessDiary = new WildernessAchievementDiary(player);
		morytaniaDiary = new MorytaniaAchievementDiary(player);
		kandarinDiary = new KandarinAchievementDiary(player);
		fremennikDiary = new FremennikAchievementDiary(player);
		westernDiary = new WesternAchievementDiary(player);
		desertDiary = new DesertAchievementDiary(player);
	}
	
	public VarrockAchievementDiary getVarrockDiary() {
		return varrockDiary;
	}
	
	public ArdougneAchievementDiary getArdougneDiary() {
		return ardougneDiary;
	}
	
	public FaladorAchievementDiary getFaladorDiary() {
		return faladorDiary;
	}

	public LumbridgeDraynorAchievementDiary getLumbridgeDraynorDiary() {
		return lumbridgeDraynorDiary;
	}

	public KaramjaAchievementDiary getKaramjaDiary() {
		return karamjaDiary;
	}

	public WildernessAchievementDiary getWildernessDiary() {
		return wildernessDiary;
	}

	public MorytaniaAchievementDiary getMorytaniaDiary() {
		return morytaniaDiary;
	}

	public KandarinAchievementDiary getKandarinDiary() {
		return kandarinDiary;
	}

	public FremennikAchievementDiary getFremennikDiary() {
		return fremennikDiary;
	}

	public WesternAchievementDiary getWesternDiary() {
		return westernDiary;
	}

	public DesertAchievementDiary getDesertDiary() {
		return desertDiary;
	}

	public Player getPlayer() {
		return player;
	}
}
