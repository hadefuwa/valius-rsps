package valius.model.items;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import valius.model.items.item_combinations.*;

public enum ItemCombinations {
	
	BLOOD_JUSTICIAR_HELM(new BloodJusticiarHelm(new Item(33375), Optional.of(Arrays.asList(new Item(33947), new Item(22326))), new Item[] { new Item(33947), new Item(22326) })),
	
	BLOOD_JUSTICIAR_PLATE(new BloodJusticiarPlate(new Item(33376), Optional.of(Arrays.asList(new Item(33948), new Item(22327))), new Item[] { new Item(33948), new Item(22327) })),
	
	BLOOD_JUSTICIAR_LEGS(new BloodJusticiarLegs(new Item(33377), Optional.of(Arrays.asList(new Item(33949), new Item(22328))), new Item[] { new Item(33949), new Item(22328) })),
	
	BLOOD_BOOGIE_BOW(new BloodBoogieBow(new Item(33536), Optional.of(Arrays.asList(new Item(33950), new Item(33531))), new Item[] { new Item(33950), new Item(33531) })),
	
	BLOOD_SCYTHE_OF_VITUR(new BloodScytheOfVitur(new Item(33380), Optional.of(Arrays.asList(new Item(33951), new Item(22325))), new Item[] { new Item(33951), new Item(22325) })),
	
	BLOOD_OBLITERATION(new BloodObliteration(new Item(33535), Optional.of(Arrays.asList(new Item(33952), new Item(33530))), new Item[] { new Item(33952), new Item(33530) })),
	
	GILDED_GHRAZI(new GildedGhrazi(new Item(33672), Optional.of(Arrays.asList(new Item(33953), new Item(22324))), new Item[] { new Item(33953), new Item(22324) })),
	
	GILDED_SANG(new GildedSang(new Item(33673), Optional.of(Arrays.asList(new Item(33954), new Item(22323))), new Item[] { new Item(33954), new Item(22323) })),
	
	GILDED_TWISTED_BOW(new GildedTwistedBow(new Item(33671), Optional.of(Arrays.asList(new Item(33955), new Item(20997))), new Item[] { new Item(33955), new Item(20997) })),
	
	SERENGODBOWBLOOD(new SerenGodbowBlood(new Item(33909), Optional.of(Arrays.asList(new Item(33911), new Item(33924))), new Item[] { new Item(33911), new Item(33924) })),
	
	SERENGODBOWTHIRDAGE(new SerenGodbowThirdage(new Item(33922), Optional.of(Arrays.asList(new Item(33911), new Item(33925))), new Item[] { new Item(33911), new Item(33925) })),
	
	SERENGODBOWSHADOW(new SerenGodbowShadow(new Item(33914), Optional.of(Arrays.asList(new Item(33911), new Item(33926))), new Item[] { new Item(33911), new Item(33926) })),
	
	SERENGODBOWICE(new SerenGodbowIce(new Item(33910), Optional.of(Arrays.asList(new Item(33911), new Item(33927))), new Item[] { new Item(33911), new Item(33927) })),
	
	SERENGODBOWBARROWS(new SerenGodbowBarrows(new Item(33908), Optional.of(Arrays.asList(new Item(33911), new Item(33928))), new Item[] { new Item(33911), new Item(33928) })),
	
	BLOOD_TWISTEDBOW(new BloodTwistedBow(new Item(33424), Optional.of(Arrays.asList(new Item(20997), new Item(33899))), new Item[] { new Item(20997), new Item(33899) })),
	
	MASTER_CLUE_SCROLL(new MasterClueScroll(new Item(33895), Optional.empty(), new Item[] { new Item(19837), new Item(19838), new Item(19839)})),
	
	SEREN_GODBOW(new SerenGodbow(new Item (33911), Optional.empty(), new Item[] { new Item (33915), new Item (33916)})),
	
	FLAMBURST_SWORD_FURY(new FlamburstSwordFury(new Item (33815), Optional.empty(), new Item[] { new Item (33834), new Item (33833)})),
	
	FLAMBURST_SWORD_GRACE(new FlamburstSwordOfGrace(new Item (33816), Optional.empty(), new Item[] { new Item (33883), new Item (33815)})),
	
	FLAMBURST_SWORD_NATURE(new FlamburstSwordOfNature(new Item (33818), Optional.empty(), new Item[] { new Item (33882), new Item (33815)})),
	
	FLAMBURST_BOW_FURY(new FlamburstBowOfFury(new Item (33812), Optional.empty(), new Item[] { new Item (33831), new Item (33832)})),
	
	FLAMBURST_BOW_GRACE(new FlamburstBowOfGrace(new Item (33813), Optional.empty(), new Item[] { new Item (33883), new Item (33812)})),
	
	FLAMBURST_BOW_NATURE(new FlamburstBowOfNature(new Item (33814), Optional.empty(), new Item[] { new Item (33882), new Item (33812)})),
	
	FLAMBURST_STAFF_FURY(new FlamburstStaffOfFury(new Item (33819), Optional.empty(), new Item[] { new Item (33835), new Item (33836)})),
	
	FLAMBURST_STAFF_GRACE(new FlamburstStaffOfGrace(new Item (33820), Optional.empty(), new Item[] { new Item (33883), new Item (33819)})),
	
	FLAMBURST_STAFF_NATURE(new FlamburstStaffOfNature(new Item (33821), Optional.empty(), new Item[] { new Item (33882), new Item (33819)})),
	
	SHADOW_SS(new ShadowSs(new Item (33787), Optional.empty(), new Item[] { new Item (33790), new Item (12831)})),
	
	REFLECTIVE_SS(new ReflectiveSs(new Item (33788), Optional.empty(), new Item[] { new Item (33791), new Item (12831)})),
	
	SIPHON_SS(new SiphonSs(new Item (33789), Optional.empty(), new Item[] { new Item (33792), new Item (12831)})),
	
	RING_OF_FORTUNE( new RingOfFortune( new Item (33798), Optional.empty(), new Item[] { new Item (33767), new Item (12785)})),
	
	LUCK_OF_DWARVES( new LuckOfDwarves( new Item (33799), Optional.empty(), new Item[] { new Item (33768), new Item (33798)})),
	
	HAZELMERES_SIGNET( new HazelmeresSignet( new Item (33800), Optional.empty(), new Item[] { new Item (33769), new Item (33799)})),
	
	CRAWS_BLUE( new CrawsBlue( new Item (33781), Optional.empty(), new Item[] { new Item (33770), new Item (22550)})),
	
	CRAWS_RED( new CrawsRed( new Item (33782), Optional.empty(), new Item[] { new Item (33771), new Item (22550)})),
	
	CRAWS_GREEN( new CrawsGreen( new Item (33783), Optional.empty(), new Item[] { new Item (33772), new Item (22550)})),
	
	VIGGORA_BLUE( new ViggoraBlue( new Item (33778), Optional.empty(), new Item[] { new Item (33770), new Item (22545)})),
	
	VIGGORA_RED( new ViggoraRed( new Item (33779), Optional.empty(), new Item[] { new Item (33771), new Item (22545)})),
	
	VIGGORA_GREEN( new ViggoraGreen( new Item (33780), Optional.empty(), new Item[] { new Item (33772), new Item (22545)})),
	
	THAM_BLUE( new ThamBlue( new Item (33784), Optional.empty(), new Item[] { new Item (33770), new Item (22555)})),
	
	THAM_RED( new ThamRed( new Item (33785), Optional.empty(), new Item[] { new Item (33771), new Item (22555)})),
	
	THAM_GREEN( new ThamGreen( new Item (33786), Optional.empty(), new Item[] { new Item (33772), new Item (22555)})),
	
	FROSTBITE_GROT_HELM(new FrostbiteGrotHelm(new Item(33565), Optional.of(Arrays.asList(new Item(33569), new Item(33571))), new Item[] { new Item(33569), new Item(33571) })),
	
	FROSTBITE_GROT_TOP(new FrostbiteGrotTop(new Item(33563), Optional.of(Arrays.asList(new Item(33567), new Item(33571))), new Item[] { new Item(33567), new Item(33571) })),
	
	FROSTBITE_GROT_BOTTOMS(new FrostbiteGrotLegs(new Item(33564), Optional.of(Arrays.asList(new Item(33568), new Item(33571))), new Item[] { new Item(33568), new Item(33571) })),
	
	FROSTBITE_TBOW(new FrostbiteTbow(new Item(33562), Optional.of(Arrays.asList(new Item(20997), new Item(33570))), new Item[] { new Item(20997), new Item(33570) })),
	
	SUPERIOR_TBOW(new SuperiorTbow(new Item(33119), Optional.of(Arrays.asList(new Item(20997), new Item(33566))), new Item[] { new Item(20997), new Item(33566) })),

	STRYKEBOW(new Strykebow(new Item(33551), Optional.of(Arrays.asList(new Item(33554), new Item(11235))), new Item[] { new Item(33554), new Item(11235) })),
	
	STAFF_OF_DARKNESS(new StaffOfDarkness(new Item(33553), Optional.of(Arrays.asList(new Item(33555), new Item(22296))), new Item[] { new Item(33555), new Item(22296) })),
	
	STARGAZE_PICKAXE(new StargazePickaxe(new Item(33546), Optional.of(Arrays.asList(new Item(33545), new Item(33547))), new Item[] { new Item(33545), new Item(33547) })),

	SARADOMINS_BLESSED_SWORD(new SaradominsBlessedSword(new Item(12809), Optional.of(Arrays.asList(new Item(12804))), new Item[] { new Item(12804), new Item(11838) })),

	AMULET_OF_FURY(new AmuletOfFury(new Item(12436), Optional.of(Arrays.asList(new Item(6585), new Item(12526))), new Item[] { new Item(6585), new Item(12526) })),

	AMULET_OF_TORTURE(new AmuletOfTorture(new Item(20366), Optional.of(Arrays.asList(new Item(19553), new Item(20062))), new Item[] { new Item(19553), new Item(20062) })),

	NECKLACE_OF_ANGUISH(new NecklaceOfAnguish(new Item(22249), Optional.of(Arrays.asList(new Item(19547), new Item(22246))), new Item[] { new Item(19547), new Item(22246) })),

	DRAGON_BOOTS(new DragonBoots(new Item(22234), Optional.of(Arrays.asList(new Item(11840), new Item(22231))), new Item[] { new Item(11840), new Item(22231) })),

	DRAGONFIRE_WARD(new DragonfireWard(new Item(22002), Optional.empty(), new Item[] { new Item(22006), new Item(1540) })),
	
	BRIMSTONE_RING(new BrimstoneRing(new Item(22975), Optional.empty(), new Item[] { new Item(22969), new Item(22973), new Item(22971) })),
	
	DRAGON_PLATEBODY(new DragonPlatebody(new Item(21892), Optional.of(Arrays.asList(new Item(3140), new Item(22103), new Item(22097))), new Item[] { new Item(3140), new Item(22103), new Item(22097) })),

	BLUE_DARK_BOW(new BlueDarkBow(new Item(12765), Optional.empty(), new Item[] { new Item(11235), new Item(12757) })),

	GREEN_DARK_BOW(new GreenDarkBow(new Item(12766), Optional.empty(), new Item[] { new Item(11235), new Item(12759) })),

	YELLOW_DARK_BOW(new YellowDarkBow(new Item(12767), Optional.empty(), new Item[] { new Item(11235), new Item(12761) })),

	WHITE_DARK_BOW(new WhiteDarkBow(new Item(12768), Optional.empty(), new Item[] { new Item(11235), new Item(12763) })),

	MALEDICTION_WARD(new MaledictionWard(new Item(11924), Optional.empty(), new Item[] { new Item(11931), new Item(11932), new Item(11933) })),
	
	MALEDICTION_WARD_OR(new MaledictionWardOr(new Item(12806), Optional.of(Arrays.asList(new Item(11924))), new Item[] { new Item(11924), new Item(12802) })),

	ODIUM_WARD(new OdiumWard(new Item(11926), Optional.empty(), new Item[] { new Item(11928), new Item(11929), new Item(11930) })),
	
	ODIUM_WARD_OR(new OdiumWardOr(new Item(12807), Optional.of(Arrays.asList(new Item(11926))), new Item[] { new Item(11926), new Item(12802) })), 
	
	STEAM_STAFF(new SteamStaff(new Item(12796), Optional.of(Arrays.asList(new Item(11789))), new Item[] { new Item(11789), new Item(12798) })),

	GRANITE_MAUL(new GraniteMaul(new Item(12848), Optional.of(Arrays.asList(new Item(4153))), new Item[] { new Item(4153), new Item(12849) })),

	DRAGON_PICKAXE(new DragonPickaxe(new Item(12797), Optional.of(Arrays.asList(new Item(11920))), new Item[] { new Item(12800), new Item(11920) })),

	BLESSED_SPIRIT_SHIELD(new BlessedSpiritShield(new Item(12831), Optional.empty(), new Item[] { new Item(12829), new Item(12833) })),

	ARCANE_SPIRIT_SHIELD(new ArcaneSpiritShield(new Item(12825), Optional.empty(), new Item[] { new Item(12827), new Item(12831) })),

	KODAI_WAND(new KodaiWand(new Item(21006), Optional.empty(), new Item[] { new Item(21043), new Item(6914) })),
	
	DIVINE_SPIRIT_SHIELD(new DivineSpiritShield(new Item(32991), Optional.empty(), new Item[] { new Item(32993), new Item(12831) })),

	ELYSIAN_SPIRIT_SHIELD(new ElysianSpiritShield(new Item(12817), Optional.empty(), new Item[] { new Item(12819), new Item(12831) })),

	SPECTRAL_SPIRIT_SHIELD(new SpectralSpiritShield(new Item(12821), Optional.empty(), new Item[] { new Item(12823), new Item(12831) })),
	
	DRAGON_HUNTER_LANCE(new DragonHunterLance(new Item(22978), Optional.empty(), new Item[] { new Item(11889), new Item(22966) })),

	TENTACLE_WHIP(new TentacleWhip(new Item(12006), Optional.of(Arrays.asList(new Item(12004))), new Item[] { new Item(12004), new Item(4151) })),

	HOLY_BOOK(new HolyBook(new Item(3840), Optional.empty(), 
			new Item[] { new Item(3839), new Item(3827), new Item(3828), new Item(3829), new Item(3830) })),

	UNHOLY_BOOK(new UnholyBook(new Item(3842), Optional.empty(),
			new Item[] { new Item(3841), new Item(3831), new Item(3832), new Item(3833), new Item(3834) })),

	BALANCE_BOOK(new BalanceBook(new Item(3844), Optional.empty(),
			new Item[] { new Item(3843), new Item(3835), new Item(3836), new Item(3837), new Item(3838) })),
	
	BOOK_OF_LAW(new BookOfLaw(new Item(12610), Optional.empty(),
			new Item[] { new Item(12609), new Item(12617), new Item(12618), new Item(12619), new Item(12620) })),
	
	BOOK_OF_WAR(new BookOfWar(new Item(12608), Optional.empty(),
			new Item[] { new Item(12607), new Item(12613), new Item(12614), new Item(12615), new Item(12616) })),
	
	BOOK_OF_DARKNESS(new BookOfDarkness(new Item(12612), Optional.empty(),
			new Item[] { new Item(12611), new Item(12621), new Item(12622), new Item(12623), new Item(12624) })),

	RING_OF_WEALTH_IMBUED(new RingOfWealthImbued(new Item(12785), Optional.empty(), new Item[] { new Item(2572), new Item(12783) })),

	ETERNAL_BOOTS(new EternalBoots(new Item(13235), Optional.empty(), new Item[] { new Item(13227), new Item(6920) })),

	PEGASIAN_BOOTS(new PegasianBoots(new Item(13237), Optional.empty(), new Item[] { new Item(13229), new Item(2577) })),

	PRIMORDIAL_BOOTS(new PrimordialBoots(new Item(13239), Optional.empty(), new Item[] { new Item(13231), new Item(11840) })),

	INFERNAL_AXE(new InfernalAxe(new Item(13241), Optional.empty(), new Item[] { new Item(13233), new Item(6739) })),

	INFERNAL_PICKAXE(new InfernalPickaxe(new Item(13243), Optional.empty(), new Item[] { new Item(13233), new Item(11920) })),

	FROZEN_ABYSSAL_WHIP(new FrozenAbyssalWhip(new Item(12774), Optional.empty(), new Item[] { new Item(12769), new Item(4151) })),

	DRAGON_DEFENDER(new DragonDefender(new Item(19722), Optional.empty(), new Item[] { new Item(12954), new Item(20143) })),
	
	BLUE_INFERNAL_CAPE(new BlueInfernalCape(new Item(33143), Optional.empty(), new Item[] { new Item(33161), new Item(21295) })),
	
	GREEN_INFERNAL_CAPE(new GreenInfernalCape(new Item(33146), Optional.empty(), new Item[] { new Item(33162), new Item(21295) })),
	
	PURPLE_INFERNAL_CAPE(new PurpleInfernalCape(new Item(33147), Optional.empty(), new Item[] { new Item(33163), new Item(21295) })),

	BLUE_FIRE_CAPE(new BlueFirecape(new Item(33148), Optional.empty(), new Item[] { new Item(33167), new Item(6570) })),
	
	GREEN_FIRE_CAPE(new GreenFirecape(new Item(33144), Optional.empty(), new Item[] { new Item(33165), new Item(6570) })),
	
	PURPLE_FIRE_CAPE(new PurpleFirecape(new Item(33142), Optional.empty(), new Item[] { new Item(33164), new Item(6570) })),
	
	RED_FIRE_CAPE(new RedFirecape(new Item(33145), Optional.empty(), new Item[] { new Item(33166), new Item(6570) })),
	
	AVERNIC_DEFENDER( new AvernicDefender( new Item (22322), Optional.empty(), new Item[] { new Item (12954), new Item (22477)})),
	
	VOLCANIC_ABYSSAL_WHIP(new VolcanicAbyssalWhip(new Item(12773), Optional.empty(), new Item[] { new Item(12771), new Item(4151) }));

	private ItemCombination itemCombination;

	private ItemCombinations(ItemCombination itemCombination) {
		this.itemCombination = itemCombination;
	}

	public ItemCombination getItemCombination() {
		return itemCombination;
	}

	static final Set<ItemCombinations> COMBINATIONS = Collections.unmodifiableSet(EnumSet.allOf(ItemCombinations.class));

	public static List<ItemCombinations> getCombinations(Item item1, Item item2) {
		return COMBINATIONS.stream().filter(combos -> combos.getItemCombination().itemsMatch(item1, item2)).collect(Collectors.toList());
	}

	public static Optional<ItemCombination> isRevertable(Item item) {
		Predicate<ItemCombinations> itemMatches = ic -> ic.getItemCombination().getRevertItems().isPresent() && ic.getItemCombination().getOutcome().getId() == item.getId();
		Optional<ItemCombinations> revertable = COMBINATIONS.stream().filter(itemMatches).findFirst();
		if (revertable.isPresent() && revertable.get().getItemCombination().isRevertable()) {
			return Optional.of(revertable.get().getItemCombination());
		}
		return Optional.empty();
	}

}
