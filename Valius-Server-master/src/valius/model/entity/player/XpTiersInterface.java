package valius.model.entity.player;

import valius.Config;

/**
 * Opens the XP tier scaling interface using the SkillMenu widget (8714).
 * Left column: total level range. Right column: tier name + multiplier.
 */
public class XpTiersInterface {

    private static final int INTERFACE_ID = 8714;
    private static final int TITLE_LINE   = 8716;
    private static final int LEVEL_LINE   = 8720; // left column
    private static final int TEXT_LINE    = 8760; // right column

    // Gem icons to visually represent each tier (0 = empty slot for "None")
    private static final int[] TIER_ITEMS = { 0, 2349, 2355, 2357, 1444, 1617, 1631, 6573, 13342 };

    private static final String[] RANGES = {
        "0 - 499",
        "500 - 749",
        "750 - 999",
        "1000 - 1249",
        "1250 - 1499",
        "1500 - 1749",
        "1750 - 1999",
        "2000 - 2276",
        "2277 (Max)"
    };

    public static void open(Player c) {
        c.getPA().sendFrame126("@dre@XP Tier Scaling", TITLE_LINE);

        // Row 0 — no scaling tier
        c.getPA().sendFrame126("@yel@" + RANGES[0], LEVEL_LINE);
        c.getPA().sendFrame126("None  @red@1x", TEXT_LINE);

        // Rows 1-8 — Config-driven tiers
        for (int i = 0; i < Config.TOTAL_LEVEL_THRESHOLDS.length; i++) {
            String range = RANGES[i + 1];
            String name  = Config.TOTAL_LEVEL_TIER_NAMES[i];
            String mult  = String.valueOf(Config.TOTAL_LEVEL_BONUSES[i]);
            c.getPA().sendFrame126("@yel@" + range, LEVEL_LINE + i + 1);
            c.getPA().sendFrame126(tierColour(name) + name + "  @red@" + mult + "x", TEXT_LINE + i + 1);
        }

        // Blank out any remaining rows
        for (int i = 9; i < 30; i++) {
            c.getPA().sendFrame126("", LEVEL_LINE + i);
            c.getPA().sendFrame126("", TEXT_LINE + i);
        }

        // Send tier item icons to the item container
        c.outStream.createFrameVarSizeWord(53);
        c.outStream.writeWord(8847);
        c.outStream.writeWord(TIER_ITEMS.length);
        for (int itemId : TIER_ITEMS) {
            c.outStream.writeByte(0);
            c.outStream.writeByte(1);
            c.outStream.writeWordBigEndianA(itemId > 0 ? itemId + 1 : 0);
        }
        c.outStream.endFrameVarSizeWord();
        c.flushOutStream();

        c.getPA().showInterface(INTERFACE_ID);
    }

    private static String tierColour(String tier) {
        switch (tier) {
            case "Bronze":      return "@or1@";
            case "Silver":      return "@whi@";
            case "Gold":        return "@yel@";
            case "Platinum":    return "@cya@";
            case "Diamond":     return "@cya@";
            case "Dragonstone": return "@mag@";
            case "Zenyte":      return "@red@";
            case "Max":         return "@gre@";
            default:            return "";
        }
    }
}
