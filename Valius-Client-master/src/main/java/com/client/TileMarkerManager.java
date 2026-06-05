package com.client;

import com.client.draw.raster.Raster;
import java.io.*;
import java.util.*;

/**
 * Tile marking feature — press Shift+M to mark/unmark the tile you are standing on.
 * Press again to cycle through 7 colours; one more press removes the mark.
 * Marks are saved to ValiusCache1/tile_markers.txt and survive restarts.
 */
public class TileMarkerManager {

    private static final Map<String, Integer> markedTiles = new LinkedHashMap<>();
    private static boolean loaded = false;

    /** Set to true by the right-click "Mark tile" action; cleared after the tile pick resolves. */
    static boolean pendingMark = false;

    private static final File SAVE_FILE = new File(
            System.getProperty("user.home") + "/ValiusCache1/tile_markers.txt");

    private static final int[] COLORS = {
        0x00FF00, // green
        0xFF0000, // red
        0xFFFF00, // yellow
        0x00FFFF, // cyan
        0xFF00FF, // magenta
        0xFF8800, // orange
        0xFFFFFF, // white
    };

    /** Called from Shift+M in GameKeyListener. Marks/cycles/removes the player's current tile. */
    public static void toggleCurrentTile() {
        if (Client.myPlayer == null) return;
        int worldX = (Client.myPlayer.x >> 7) + Client.baseX;
        int worldY = (Client.myPlayer.y >> 7) + Client.baseY;
        toggle(worldX, worldY);
    }

    static void toggle(int worldX, int worldY) {
        String key = worldX + "," + worldY;
        if (markedTiles.containsKey(key)) {
            int cur = markedTiles.get(key);
            int idx = colorIndex(cur);
            if (idx >= COLORS.length - 1) {
                markedTiles.remove(key);
            } else {
                markedTiles.put(key, COLORS[idx + 1]);
            }
        } else {
            markedTiles.put(key, COLORS[0]);
        }
        save();
    }

    private static int colorIndex(int color) {
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i] == color) return i;
        }
        return COLORS.length - 1;
    }

    /**
     * Draw all marked tile outlines into the pixel buffer.
     * Call this each frame after sceneGraph.renderScene() but before writeBackgroundTexture().
     */
    public static void drawMarkers(Client client) {
        if (!loaded) {
            load();
            loaded = true;
        }
        if (markedTiles.isEmpty() || Client.myPlayer == null) return;
        for (Map.Entry<String, Integer> entry : markedTiles.entrySet()) {
            drawTile(client, entry.getKey(), entry.getValue());
        }
    }

    private static void drawTile(Client client, String key, int color) {
        String[] parts = key.split(",");
        int worldX = Integer.parseInt(parts[0]);
        int worldY = Integer.parseInt(parts[1]);

        // Convert world tile to local scene internal units (128 units per tile).
        // Inset 4 units from each edge so corner coords stay in calcEntityScreenPos's
        // valid range of [128, 13056] even for tiles at the scene boundary.
        int lx = (worldX - Client.baseX) * 128;
        int ly = (worldY - Client.baseY) * 128;

        int[][] corners = {
            {lx + 4,   ly + 4},
            {lx + 124, ly + 4},
            {lx + 124, ly + 124},
            {lx + 4,   ly + 124},
        };

        int[] sx = new int[4];
        int[] sy = new int[4];

        for (int i = 0; i < 4; i++) {
            client.calcEntityScreenPos(corners[i][0], 0, corners[i][1]);
            if (client.spriteDrawX == -1) return; // corner off-screen — skip whole tile
            sx[i] = client.spriteDrawX;
            sy[i] = client.spriteDrawY;
        }

        Raster.drawLineBetween(sx[0], sy[0], sx[1], sy[1], color);
        Raster.drawLineBetween(sx[1], sy[1], sx[2], sy[2], color);
        Raster.drawLineBetween(sx[2], sy[2], sx[3], sy[3], color);
        Raster.drawLineBetween(sx[3], sy[3], sx[0], sy[0], color);
    }

    private static void save() {
        try {
            SAVE_FILE.getParentFile().mkdirs();
            try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
                for (Map.Entry<String, Integer> entry : markedTiles.entrySet()) {
                    pw.println(entry.getKey() + "," + entry.getValue());
                }
            }
        } catch (IOException ignored) {}
    }

    private static void load() {
        markedTiles.clear();
        if (!SAVE_FILE.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        markedTiles.put(parts[0] + "," + parts[1], Integer.parseInt(parts[2]));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException ignored) {}
    }
}
