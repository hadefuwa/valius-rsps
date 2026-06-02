package valius.model.entity.npc.pets;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import valius.clip.Region;
import valius.discord.DiscordBot;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerSave;
import valius.model.entity.player.skills.Skill;
import valius.util.Misc;
import valius.world.World;

public class PetHandler {

    /**
     * A {@link Set} of {@link Pets} that represent non-playable characters that a
     * player entity can drop and interact with.
     */
    private static final Set<Pets> PETS = Collections.unmodifiableSet(EnumSet.allOf(Pets.class));

    private static final ImmutableSet<Integer> PET_IDS = ImmutableSet.of(12650, 12649, 12651, 12652, 12644, 12645,
            12643, 11995, 15568, 12653, 12655, 13178, 12646, 13179, 13177, 12921, 13181, 12816, 12647);

    public static boolean ownsAll(Player player) {
        int amount = 0;
        for (int pets2 : PET_IDS) {
            if (player.getItems().getItemCount(pets2, false) > 0 || player.summonId == pets2) {
                amount++;
            }
            if (amount == PET_IDS.size()) {
                return true;
            }
        }
        return false;
    }

    public static enum Pets {
    	
		MINI_SANTA(33963, 3418, "Mini Santa", 1000, "first"),
		
		MINI_FROSTY(33966, 3414, "Mini Frosty", 1000, "first"),
		
		RUDOLPH(33964, 3416, "Rudolph", 1000, "first"),
		
		JOLLY_RUDOLPH(33965, 3412, "Jolly Rudolph", 1000, "first"),
    	
		MELEE_IMP(33930, 3410, "Valius Imp (Melee)", 1000, "first"),
		
		RANGE_IMP(33931, 3409, "Valius Imp (Range)", 1000, "first"),
		
		MAGE_IMP(33932, 3411, "Valius Imp (Mage)", 1000, "first"),
		
		AMBASADOR(33907, 3406, "Ambasador", 1000, "first"),
		
		SOLAK(33923, 3408, "Solak", 500, "first"),
		
		SAPPHIRE_BABY_DRAGON(33901, 3398, "Sapphire baby dragon", 1000, "first"),
		
		EMERALD_BABY_DRAGON(33902, 3405, "Emerald baby dragon", 1000, "first"),
		
		RUBY_BABY_DRAGON(33903, 3400, "Ruby baby dragon", 1000, "first"),
		
		DIAMOND_BABY_DRAGON(33904, 3401, "Diamond baby dragon", 1000, "first"),
		
		DRAGONSTONE_BABY_DRAGON(33905, 3402, "Dragonstone baby dragon", 1000, "first"),
		
		ONYX_BABY_DRAGON(33900, 3397, "Onyx baby dragon", 1000, "first"),
		
		ZENYTE_BABY_DRAGON(33906, 3404, "Zenyte baby dragon", 1000, "first"),
    	
		MINION(33766, 3851, "Riley", 1000, "first"),

		MINI_DUP(33760, 3844, "Mini Dup", 1000, "first"),

		ZAMORAK(33759, 3840, "Zamorak", 1000, "first"),

		JACKOKRAKEN(33745, 3846, "Jack-0-kraken", 1000, "first"),

		JACKOBAT(33746, 3847, "Jack-o-bat", 1000, "first"),

		SPOOKY_JACKOBAT(33747, 3848, "Spooky Jack-o-bat", 1000, "first"),

		LIT_JACKOBAT(33748, 3849, "Lit Jack-o-bat", 1000, "first"),

		GLOWING_JACKOBAT(33749, 3850, "Jack-o-bat", 1000, "first"),

		GAUNTLET(23757, 8729, "Youngllef", 1000, "first"),

		YEARLY_MIMIC(33718, 3843, "Yearly Mimic", 1000, "first"),

		HUNTERS_ODIN(33670, 3841, "Odin", 1000, "first"),

		TOXIC_SNAKE(33600, 3835, "Pet Toxic Snake", 1000, "first"),

		NIGHTMARE(33593, 3834, "Pet Nightmare", 1000, "first"),

		ODINS_ROC(33579, 3825, "Odin's Roc", 1000, "first"),

		EASTER_BUNNY(33287, 6000, "Easter Bunny", 1000, "first"),

		CHOCO_BUNNY(33288, 6001, "Choco Bunny", 1000, "first"),

		MILKIE_BUNNY(33289, 6002, "Milkie Bunny", 1000, "first"),

		GOLDEN_BUNNY(33290, 6003, "Golden Bunny", 1000, "first"),

		BLUE_BUNNY(33291, 6010, "Blue Bunny", 1000, "first"),

		CRAZED_BUNNY(33292, 6004, "Crazed Bunny", 1000, "first"),

		PETER_RABBIT(33293, 6005, "Peter Rabbit", 1000, "first"),

		CHOCO_CHICKEN(33294, 6006, "Choco Chicken", 1000, "first"),

		CHOCO_CHICKEN2(33295, 6007, "Choco Chicken", 1000, "first"),

		CHOCO_CHICKEN3(33296, 6008, "Choco Chicken", 1000, "first"),

		CHOCO_CHICKEN4(33297, 6009, "Choco Chicken", 1000, "first"),

		ANDY(33402, 6016, "", 1000, "first"),

		STARSPRITE(33421, 6018, "", 1000, "first"),

		SCOOP(33548, 6020, "Scoop", 1000, "second"),

		SOLOMON(33549, 3820, "", 1000, "first"),

		DIVINE(33403, 6017, "", 1000, "first"),

		FAIRY(33404, 6015, "", 1000, "first"),

		BLOODBIRD(33439, 3803, "", 1000, "first"),

		BLOODEATH(33440, 3804, "", 1000, "first"),

		JAYCORR(33479, 3810, "", 1000, "first"),

		TORTLE(33496, 3817, "", 1000, "first"),

		WOLFYGREEN(33491, 3812, "", 1000, "first"),

		WOLFBLUE(33492, 3813, "", 1000, "first"),

		WOLFRED(33493, 3814, "", 1000, "first"),

		WOLFBLACK(33494, 3815, "", 1000, "first"),

		WOLFGOLD(33495, 3816, "", 1000, "first"),

		TEST(33481, 3811, "", 1000, "first"),

		MAGIC(33469, 3805, "", 1000, "first"),

		CROW(33425, 3798, "", 1000, "first"),

		PENGUIN(33426, 3799, "", 1000, "first"),

		SNAKE(33427, 3801, "", 1000, "first"),

		SCORPION(33428, 3802, "", 1000, "first"),

		HYDRA_GREEN(22746, 8492, "AlchemicalHydra", 1000, "first"),

		HYDRA_BLUE(22748, 8493, "AlchemicalHydra", 1000, "first"),

		HYDRA_RED(22750, 8494, "AlchemicalHydra", 1000, "first"),

		HYDRA_BLACK(22752, 8495, "Alchemical Hydra", 1000, "first"),

		PET_MOO(33271, 8321, "Moo", 1000, "first"),

		DEMONIC_GORILLA(33174, 5670, "Demonic Gorilla", 1000, "first"),

		CRAWLING_HAND(33175, 5648, "Crawling Hand", 1000, "first"),

		CAVE_BUG(33176, 5649, "Cave Bug", 1000, "first"),

		CAVE_CRAWLER(33177, 5650, "Cave Crawler", 1000, "first"),

		BANSHEE(33178, 5651, "Banshee", 1000, "first"),

		CAVE_SLIME(33179, 5652, "Cave Slime", 1000, "first"),

		ROCK_SLUG(33180, 5653, "Rockslug", 1000, "first"),

		COCKATRICE(33181, 5654, "Cockatrice", 1000, "first"),

		PYREFRIEND(33182, 5655, "Pyrefiend", 1000, "first"),

		BASILISK(33183, 5656, "Basilisk", 1000, "first"),

		INFERNAL_MAGE(33184, 5657, "Infernal Mage", 1000, "first"),

		BLOODVELD(33185, 5658, "Bloodveld", 1000, "first"),

		JELLY(33186, 5659, "Jelly", 1000, "first"),

		TUROTH(33187, 5660, "Turoth", 1000, "first"),

		ABERRANTSPECTRE(33188, 5661, "Aberrant Spectre", 1000, "first"),

		DUST_DEVIL(33189, 5662, "Dust Devil", 1000, "first"),

		KURASK(33190, 5663, "Kurask", 1000, "first"),

		SKELETAL_WYVERN(33191, 5665, "Skeletal Wyvern", 1000, "first"),

		GARGOYLE(33192, 5666, "Gargoyle", 1000, "first"),

		NECHRYAEL(33193, 5667, "Nechryael", 1000, "first"),

		ABYSSAL_DEMON(33194, 5668, "Abyssal Demon", 1000, "first"),

		DARK_BEAST(33195, 5669, "Dark Beast", 1000, "first"),

		NIGHT_BEAST(33196, 5693, "Night Beast", 100, "first"),

		GREATER_ABYSSAL_DEMON(33197, 5671, "Greater Abyssal Demon", 100, "first"),

		CRUSHING_HAND(33198, 5672, "Crushing Hand", 100, "first"),

		CHASM_CRAWLER(33199, 5673, "Chasm Crawler", 100, "first"),

		SCREAMING_BANSHEE(33200, 5674, "Screaming Banshee", 100, "first"),

		TWISTED_BANSHEE(33201, 5675, "Twisted Banshee", 100, "first"),

		GIANT_ROCKSLUG(33202, 5676, "Giant Rockslug", 100, "first"),

		COCKATHRICE(33203, 5677, "Cockathrice", 100, "first"),

		FLAMING_PYRELORD(33204, 5678, "Flaming Pyrelord", 100, "first"),

		MONSTROUS_BASILISK(33205, 5679, "Monstrous Basilisk", 100, "first"),

		MALEVOLENT_MAGE(33206, 5680, "Night Beast", 100, "first"),

		INSATIABLE_BLOODVELD(33207, 5681, "Insatiable Bloodveld", 100, "first"),

		INSATIABLE_MUTATED_BLOODVELD(33208, 5682, "Insatiable Mutated Bloodveld", 100, "first"),

		VITREOUS_JELLY(33209, 5683, "Vitreous Jelly", 100, "first"),

		VITREOUS_WARPED_JELLY(33210, 5684, "Vitreous Warped Jelly", 100, "first"),

		CAVE_ABOMINATION(33211, 5685, "Cave Abomination", 100, "first"),

		ABHORRENT_SPECTRE(33212, 5686, "Abhorrent Spectre", 100, "first"),

		REPUGNANT_SPECTRE(33213, 5687, "Repugnant Spectre", 100, "first"),

		CHOKE_DEVIL(33214, 5688, "Choke Devil", 100, "first"),

		KING_KURASK(33215, 5689, "King Kurask", 100, "first"),

		NUCLEAR_SMOKE_DEVIL(33217, 5690, "Nuclear Smoke Devil", 100, "first"),

		MARBLE_GARGOYLE(33218, 5691, "Marble Gargoyle", 100, "first"),

		NECHRYARCH(33219, 5692, "Nechryarch", 100, "first"),

		PATRITY(33220, 5694, "Patrity", 100, "first"),

		XARPUS(33221, 5695, "Xarpus", 1000, "first"),

		HARAMBE(33476, 3808, "", 1000, "first"),

		HUNTER(33475, 3806, "", 1000, "first"),

		VOIDKNIGHTCHAMP(33477, 3809, "Void Knight Champion", 1000, "first"),

		NYCLOCAS_VASILIAS(33222, 5696, "Nyclocas Vasilias", 1000, "first"),

		PESTILENT_BLOAT(33223, 5697, "Pestilent Bloat", 1000, "first"),

		MAIDEN_OF_SUGDINTI(33224, 5698, "Maiden Of Sugadinti", 1000, "first"),

		LIZARDMAN_SHAMAN(33225, 5700, "Lizardman Shaman", 1000, "first"),

		ABYSSAL_SIRE(33226, 5701, "Abyssal Sire", 1000, "first"),

		BLACK_DEMON(33227, 5702, "Black Demon", 1000, "first"),

		GREATER_DEMON(33228, 5703, "Greater Demon", 1000, "first"),

		REVENANT_IMP(33233, 5704, "Revenant Imp", 1000, "first"),

		REVENANT_GOBLIN(33234, 5705, "Revenant Goblin", 1000, "first"),

		REVENANT_PYREFIEND(33235, 5706, "Revenant Pyrefiend", 1000, "first"),

		REVENANT_HOBGOBLIN(33236, 5707, "Revenant Hobgoblin", 1000, "first"),

		REVENANT_CYCLOPS(33237, 5708, "Revenant Cyclops", 1000, "first"),

		REVENANT_HELLHOUND(33238, 5709, "Revenant Hellhound", 1000, "first"),

		REVENANT_DEMON(33239, 5710, "Revenant Demon", 1000, "first"),

		REVENANT_ORK(33240, 5711, "Revenant Ork", 1000, "first"),

		REVENANT_DARK_BEAST(33242, 5712, "Revenant Dark Beast", 1000, "first"),

		REVENANT_KNIGHT(33243, 5713, "Revenant Knight", 1000, "first"),

		REVENANT_Dragon(33244, 5714, "Revenant Dragon", 1000, "first"),

		GLOD(33245, 5715, "Glod", 700, "first"),

		ICE_QUEEN(33246, 5716, "Ice Queen", 700, "first"),

		ENRAGED_TARN(33247, 5717, "Enraged Tarn", 700, "first"),

		OLMLET(20851, 7519, "", 1000, "second"),

		JALTOK_JAD(33248, 5718, "", 1000, "first"),

		RUNE_DRAGON(33249, 5719, "Rune Dragon", 1000, "first"),

		WYRM(33259, 5458, "Wyrm", 1000, "first"),

		DRAKE(33260, 5459, "Drake", 1000, "first"),

		WYRM1(33261, 5460, "Wyrm", 1000, "first"),

		VALENTINESHEART(33265, 5461, "", 1000, "first"),

		ANTI_SANTA(33134, 5000, "Tiny anti-santa", -1, "first"),

		FROST_IMP(33133, 4998, "", -1, "first"),

		GRAARDOR(12650, 6632, "General Graardor", 500, "second"), // item id, npc id, name, drop rate, pickup option
																	// (first click, second click option, etc)

		KREE(12649, 6643, "Kree Arra", 500, "second"),

		ZILLY(12651, 6633, "Commander Zilyana", 500, "second"),

		TSUT(12652, 6634, "Kril Tsutsaroth", 500, "second"),

		PRIME(12644, 6627, "Dagannoth Prime", 500, "second"),

		REX(12645, 6630, "Dagannoth Rex", 500, "second"),

		SUPREME(12643, 6628, "Dagannoth Supreme", 500, "second"),

		CHAOS(11995, 5907, "Chaos Elemental", 500, "first"),

		CHAOS_FANATIC(11995, 4444, "Chaos Fanatic", 500, "first"),

		KBD(12653, 6636, "King Black Dragon", 500, "second"),

		KRAKEN(12655, 6640, "Kraken", 500, "second"),

		CALLISTO(13178, 5558, "Callisto", 500, "second"),

		MOLE(12646, 6651, "Giant Mole", 500, "second"),

		VETION(13179, 5559, "Vetion", 500, "third"),

		VENENATIS(13177, 5557, "Venenatis", 500, "second"),

		DEVIL(12648, 6639, "Thermonuclear Smoke Devil", 500, "second"),

		ZULRAH(12921, 2130, "Zulrah", 600, "second"),

		TZREK_JAD(13225, 5892, "Tztok-Jad", 110, "second"),

		PRIMAL_GUARDIAN(33574, 3827, "Primal guardian", 500, "first"),

		BAD_ROCK(33575, 3826, "Bad Rocky", 500, "first"),

		WILDYSWYRM(33576, 3832, "Wildy Strykewyrm", 500, "first"),

		ICESWYRM(33577, 3829, "Bad Rocky", 500, "first"),

		HELLPUPPY(13247, 3099, "Cerberus", 600, "second"),
		
		SKOTOS(21273, 425, "Skotizo", 700, "second"),
		
		ZULRAH2(12921, 2131, "", -1, "second"), 
		
		ZULRAH3(12921, 2132, "", -1, "second"),
		
		HELL_CAT(7582, 1625, "", -1, "first"), 
		
		VORKI(21992, 8029, "Vorkath", 500, "second"),
		
		DEATH_JR_RED(12840, 5568, "Zombie", 800, "first"), 
		
		DEATH_JR_BLUE(12840, 5570, "", -1, "first"),
		
		DEATH_JR_GREEN(12840, 5571, "", -1, "first"), 
		
		DEATH_JR_BLACK(12840, 5569, "", -1, "first"),
		
		POSTIE_PETIE(964, 3291, "", -1, "first"), 
		
		SANTA_JR(9958, 1047, "", -1, "first"),
		
		ANTI_SANTA_JR(9959, 1048, "Anti-Santa", 500, "first"), 
		
		SCORPIA(13181, 5561, "Scorpia", 500, "second"),
		
		DARK_CORE(12816, 388, "Corporeal beast", 500, "second"),
		
		KALPHITE_PRINCESS(12647, 6637, "Kalphite Queen", 500, "third"),
		
		KALPHITE_PRINCESS_TWO(12647, 6638, "", -1, "third"), 
		
		HERON(13320, 6715, "", -1, "second"),
		
		ROCK_GOLEM(13321, 7439, "", -1, "second"), 
		
		ROCK_GOLEM_TIN(21187, 7440, "", -1, "second"),
		
		ROCK_GOLEM_COPPER(21188, 7441, "", -1, "second"), 
		
		ROCK_GOLEM_IRON(21189, 7442, "", -1, "second"),
		
		ROCK_GOLEM_COAL(21192, 7445, "", -1, "second"), 
		
		ROCK_GOLEM_GOLD(21193, 7446, "", -1, "second"),
		
		ROCK_GOLEM_MITHRIL(21194, 7447, "", -1, "second"),
		
		ROCK_GOLEM_ADAMANT(21196, 7449, "", -1, "second"),
		
		ROCK_GOLEM_RUNE(21197, 7450, "", -1, "second"), 
		
		BEAVER(13322, 6717, "", -1, "second"),
		
		KITTEN(1555, 5591, "", -1, "first"),
		
		KITTEN_ONE(1556, 5592, "", -1, "first"),
		
		KITTEN_TWO(1557, 5593, "", -1, "first"), 
		
		KITTEN_THREE(1558, 5594, "", -1, "first"),
		
		KITTEN_FOUR(1559, 5595, "", -1, "first"), 
		
		KITTEN_FIVE(1560, 5596, "", -1, "first"),
		
		RED_CHINCHOMPA(13323, 6718, "", -1, "second"), 
		
		GRAY_CHINCHOMPA(13324, 6719, "", -1, "second"),
		
		BLACK_CHINCHOMPA(13325, 6720, "", -1, "second"), 
		
		GOLD_CHINCHOMPA(13326, 6721, "", -1, "second"),
		
		GIANT_SQUIRREL(20659, 7351, "", -1, "second"), 
		
		TANGLEROOT(20661, 7352, "", -1, "second"),
		
		ROCKY(20663, 7353, "", -1, "second"), 
		
		RIFT_GUARDIAN_FIRE(20665, 7354, "", -1, "second"),
		
		RIFT_GUARDIAN_AIR(20667, 7355, "", -1, "second"), 
		
		RIFT_GUARDIAN_MIND(20669, 7356, "", -1, "second"),
		
		RIFT_GUARDIAN_WATER(20671, 7357, "", -1, "second"), 
		
		RIFT_GUARDIAN_EARTH(20673, 7358, "", -1, "second"),
		
		RIFT_GUARDIAN_BODY(20675, 7359, "", -1, "second"), 
		
		RIFT_GUARDIAN_COSMIC(20677, 7360, "", -1, "second"),
		
		RIFT_GUARDIAN_CHAOS(20679, 7361, "", -1, "second"), 
		
		RIFT_GUARDIAN_NATURE(20681, 7362, "", -1, "second"),
		
		RIFT_GUARDIAN_LAW(20683, 7363, "", -1, "second"), 
		
		RIFT_GUARDIAN_DEATH(20685, 7364, "", -1, "second"),
		
		RIFT_GUARDIAN_SOUL(20687, 7365, "", -1, "second"),
		
		RIFT_GUARDIAN_ASTRAL(20689, 7366, "", -1, "second"),
		
		RIFT_GUARDIAN_BLOOD(20691, 7367, "", -1, "second"), 
		
		ABYSSAL_ORPHAN(13262, 5883, "", -1, "second"),
		
		BLOODHOUND(19730, 6296, "", -1, "second"), 
		
		PHOENIX(20693, 7368, "", -1, "second"),
		
		PUPPADILE(22376, 8201, "", -1, "second"), 
		
		TEKTINY(22378, 8202, "", -1, "second"),
		
		VANGUARD(22380, 8203, "", -1, "second"),
		
		VASA_MINIRO(22382, 8204, "", -1, "second"),
		
		VESPINA(22384, 8200, "", -1, "second"), 
		
		LIL_ZIK(22473, 8337, "", -1, "second"),
		
		MINI_JUSTICIAR(33802, 3379, "", -1, "first"),
		
		MINI_DERWEN(33801, 3385, "", -1, "first"),
        
		MINI_PORAZDIR(33803, 3380, "", -1, "first"),
		
		SHADOW_BEAST(33804, 3381, "Shadow Beast", 500, "first"),
		
		EDDY(33873, 3389, "", -1, "first"),
		
		SUNERWATT(33874, 3387, "", -1, "first"),
		
		ELDER_TROLL(33885, 3391, "", -1, "first"),
		
		MUTATED_RAT(33886, 3392, "", -1, "first"),
		
		UNDEAD_WARRIOR(33887, 3393, "", -1, "first"),
		
		SHADED_BEAST(33888, 3394, "", -1, "first"),
		
		DARK_CENTUAR(33889, 3395, "", -1, "first")
		
        ;

        private final int itemId;

        private final int npcId;

        private final String parent;

        private final int droprate;

        private final String pickupOption;

        private Pets(int itemId, int npcId, String parent, int droprate, String pickupOption) {
            this.itemId = itemId;
            this.npcId = npcId;
            this.parent = parent;
            this.droprate = droprate;
            this.pickupOption = pickupOption;
        }

        public int getNpcId() {
        	return npcId;
        }
        
    }

    public static Pets forItem(int id) {
        for (Pets t : Pets.values()) {
            if (t.itemId == id) {
                return t;
            }
        }
        return null;
    }

    public static Pets forNpc(int id) {
        for (Pets t : Pets.values()) {
            if (t.npcId == id) {
                return t;
            }
        }
        return null;
    }

    public static boolean isPet(int npcId) {
        for (Pets t : Pets.values()) {
            if (t.npcId == npcId) {
                return true;
            }
        }
        return false;
    }

    public static String getOptionForNpcId(int npcId) {
        return forNpc(npcId).pickupOption;
    }

    public static int getItemIdForNpcId(int npcId) {
        return forNpc(npcId).itemId;
    }

    public static int getNPCIdForItemId(int itemId) {
        return forItem(itemId).npcId;
    }

    public static boolean spawnable(Player player, Pets pet, boolean ignore) {
        if (pet == null) {
            return false;
        }

        if (player.hasFollower && !ignore) {
            return false;
        }

        if (Boundary.isIn(player, Boundary.DUEL_ARENA)) {
            player.sendMessage("You cannot drop your pet here.");
            return false;
        }

        if (!player.getItems().playerHasItem(pet.itemId) && !ignore) {
            return false;
        }
        return true;
    }

    public static void spawn(Player player, Pets pet, boolean ignore, boolean ignoreAll) {
        if (!ignoreAll) {
            if (!spawnable(player, pet, ignore)) {
                return;
            }
        }
        int offsetX = 0;
        int offsetY = 0;
        if (Region.getClipping(player.getX() - 1, player.getY(), player.getHeight(), -1, 0)) {
            offsetX = -1;
        } else if (Region.getClipping(player.getX() + 1, player.getY(), player.getHeight(), 1, 0)) {
            offsetX = 1;
        } else if (Region.getClipping(player.getX(), player.getY() - 1, player.getHeight(), 0, -1)) {
            offsetY = -1;
        } else if (Region.getClipping(player.getX(), player.getY() + 1, player.getHeight(), 0, 1)) {
            offsetY = 1;
        }

        if (pet.itemId == 12840 && !ignore) {
            player.getItems().deleteItem2(pet.itemId, 1);
            player.hasFollower = true;
            player.summonId = pet.itemId;
            PlayerSave.saveGame(player);
            int randomDeath = Misc.random(3);
            switch (randomDeath) {
                case 0:
                    World.getWorld().getNpcHandler().spawnNpc3(player, 5568, player.getX() + offsetX, player.getY() + offsetY,
                            player.getHeight(), 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 1:
                    World.getWorld().getNpcHandler().spawnNpc3(player, 5569, player.getX() + offsetX, player.getY() + offsetY,
                            player.getHeight(), 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 2:
                    World.getWorld().getNpcHandler().spawnNpc3(player, 5570, player.getX() + offsetX, player.getY() + offsetY,
                            player.getHeight(), 0, 0, 0, 0, 0, true, false, true);
                    break;

                case 3:
                    World.getWorld().getNpcHandler().spawnNpc3(player, 5571, player.getX() + offsetX, player.getY() + offsetY,
                            player.getHeight(), 0, 0, 0, 0, 0, true, false, true);
                    break;
            }
        } else {
            if (!ignoreAll) {
                player.getItems().deleteItem2(pet.itemId, 1);
            }
            player.hasFollower = true;
            player.summonId = pet.itemId;
            PlayerSave.saveGame(player);
            World.getWorld().getNpcHandler().spawnNpc3(player, pet.npcId, player.getX() + offsetX, player.getY() + offsetY,
                    player.getHeight(), 0, 0, 0, 0, 0, true, false, true);
            if (!ignore) {
            }
        }
}

    public static boolean pickupPet(Player player, int npcId, boolean item) {
        Pets pets = forNpc(npcId);
        if (pets != null) {
            int itemId = pets.itemId;
            if (!item) {
                NPCHandler.npcs[player.rememberNpcIndex].setX(0);
                NPCHandler.npcs[player.rememberNpcIndex].setY(0);
                NPCHandler.npcs[player.rememberNpcIndex] = null;
                player.summonId = -1;
                player.hasFollower = false;
                return true;
            } else {
                if (NPCHandler.npcs[player.rememberNpcIndex].spawnedBy == player.getIndex()) {
                    if (player.getItems().freeSlots() > 0) {
                        NPCHandler.npcs[player.rememberNpcIndex].setX(0);
                        NPCHandler.npcs[player.rememberNpcIndex].setY(0);
                        NPCHandler.npcs[player.rememberNpcIndex] = null;
                        player.startAnimation(827);
                        player.getItems().addItem(itemId, 1);
                        player.summonId = -1;
                        player.hasFollower = false;
                        player.sendMessage("You pick up your pet.");
                        return true;
                    } else {
                        player.sendMessage("You do not have enough inventory space to do this.");
                        return false;
                    }
                } else {
                    player.sendMessage("This is not your pet.");
                    return false;
                }
            }
        }
        return false;
    }

	public static void skillPet(Player player, SkillPets pet) {
		Skill skill = pet.getSkill();
		String petName = pet.getPetName();
		int petID = pet.getItemId();

		GlobalMessages.send(player.playerName + " has received the " + skill + " skill pet, " + petName, GlobalMessages.MessageType.LOOT);
		player.getItems().addItemUnderAnyCircumstance(petID, 1);

	}
	@Getter
    public enum SkillPets {
    	AGILITY(Skill.AGILITY, "Squirrel", 20659),
    	FARMING(Skill.FARMING, "Tangleroot", 20661),
    	FIREMAKING(Skill.FIREMAKING,"Phoenix",20693),
    	HUNTERGREY(Skill.HUNTER, "Grey Chinchompa", 13324),
    	HUNTERRED(Skill.HUNTER, "Red Chinchompa", 13325),
    	HUNTERBLACK(Skill.HUNTER, "Black Chinchompa", 13325),
    	HUNTERGOLD(Skill.HUNTER, "Golden Chinchompa", 13326),
    	MINING(Skill.MINING, "Rock Golem", 13321),
    	RUNECRAFTING(Skill.RUNECRAFTING, "Rift Guardian", 20667),
    	THIEVING(Skill.THIEVING, "Rocky", 20663),
    	WOODCUTTING(Skill.WOODCUTTING,"Beaver", 13322)
    	;
    	
    	private SkillPets (Skill skill, String petName, int petID) {
    		this.skill = skill;
    		this.petName = petName;
    		this.itemId = petID;
    	}
    	private Skill skill;
    	private String petName;
    	private int itemId;
    	
		public boolean hasPet(Player player) {
			return player.getItems().getItemCount(itemId, false) > 0 || player.summonId == itemId;
		}
    	
    }

    public static void receive(Player player, NPC npc) {
        if (npc == null) {
            return;
        }

        Optional<Pets> pet = PETS.stream().filter(p -> p.parent.equalsIgnoreCase(npc.getDefinition().getNpcName()))
                .findFirst();

        pet.ifPresent(p -> {
            if (player.getItems().getItemCount(p.itemId, false) > 0 || player.summonId == p.itemId) {
                return;
            }

            int rights = player.getRights().getPrimary().getValue() - 1;
            if (RandomUtils.nextInt(0, p.droprate) == 1) {
                player.getItems().addItemUnderAnyCircumstance(p.itemId, 1);
                spawn(player, p, false, false);
                GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " has received a pet drop from " + p.parent + ".", GlobalMessages.MessageType.LOOT);
                DiscordBot.sendMessage("valius-bot", "[Loot Bot] " + Misc.formatPlayerName(player.playerName)
                        + " has received a pet drop from " + p.parent + ".");
            }
        });

    }

    /**
     * Handles metamorphosis of the npc of choice
     *
     * @param player the player performing the metamorphosis
     * @param npcId  the npc to metamorphose
     */
    public static void metamorphosis(Player player, int npcId) {
        Pets pets = forNpc(npcId);
        if (npcId < 1) {
            return;
        }
        if (pets != null) {
            if (NPCHandler.npcs[player.npcClickIndex].spawnedBy != player.getIndex()) {
                player.sendMessage("This is not your pet.");
                return;
            }
            switch (npcId) {
                case 2130:
                case 2131:
                case 2132:
                    NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 2132 ? npcId - 2 : npcId + 1);
                    break;

                case 6637:
                case 6638:
                    NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId == 6638 ? 6637 : 6638);
                    break;

                // case 6718:
                // case 6719:
                // case 6720:
                // case 6721:
                // int randomGold = Misc.random(15_000);
                // NPCHandler.npcs[player.npcClickIndex].requestTransform(randomGold == 15 ?
                // 6721 : npcId == 6721 ? 6718 : npcId == 6720 ? npcId - 2 : npcId + 1);
                // break;
            }
        }
    }

	/*
     * public static void recolor(Player player, int npcId, int itemId) { Pets pets
	 * = forNpc(npcId); if (npcId < 1) { return; } if (pets != null) { if
	 * (NPCHandler.npcs[player.npcClickIndex].spawnedBy != player.getIndex()) {
	 * player.sendMessage("This is not your pet."); return; } switch (npcId) { case
	 * 7439: switch (itemId) { case 438:
	 * NPCHandler.npcs[player.npcClickIndex].requestTransform(7440); break; } break;
	 * 
	 * /*case 6637: NPCHandler.npcs[player.npcClickIndex].requestTransform(npcId ==
	 * 6638 ? 6637 : 6638); break;
	 * 
	 * } } }
	 */

    public static boolean talktoPet(Player c, int npcId) {
        Pets pets = forNpc(npcId);
        if (pets != null) {
            if (NPCHandler.npcs[c.rememberNpcIndex].spawnedBy == c.getIndex()) {
                if (npcId == 4441) {
                    c.getDH().sendDialogues(14000, 3200);
                }
                if (npcId == 4439) {
                    c.getDH().sendDialogues(14003, 3200);
                }
                if (npcId == 4440) {
                    c.getDH().sendDialogues(14006, 3200);
                }
                if (npcId == 4446) {
                    c.getDH().sendDialogues(14009, 3200);
                }
                if (npcId == 4442) {
                    c.getDH().sendDialogues(14011, 3200);
                }
                if (npcId == 4438) {
                    c.getDH().sendDialogues(14014, 3200);
                }
            } else {
                c.sendMessage("This is not your pet.");
            }
            return true;
        } else {
            return false;
        }
    }

}