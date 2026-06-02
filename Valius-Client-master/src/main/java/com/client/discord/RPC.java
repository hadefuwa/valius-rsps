package com.client.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class RPC {
    public static String FIRST_LINE = "#1 Custom OSRS Server!";
    public static String SECOND_LINE = "www.valius.net";
    public static String IMAGE = "icon512";
	private static String APPLICATION_ID = "599269980206530591";
	public static void init() {

        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = APPLICATION_ID;
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Rich Presence Loaded");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = FIRST_LINE;
        presence.state = SECOND_LINE;
        presence.largeImageKey = IMAGE;
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
	}

}
