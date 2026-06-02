package valius.net.packet.impl.objectoptions.impl;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.util.Misc;

/**
 * 
 * @author Divine | 5:27:39 p.m. | Jul. 28, 2019
 *
 */

/*
 * Handles Checking the cape rack, requirements and claiming an untrimmed or trimmed completionist cape
 */
public class CompCapeRack {

	//Cape Id's
	public static int UNTRIMMED_COMP_CAPE = 33019;
	public static int TRIMMED_COMP_CAPE = 33020;

	//interaction with the cape rack
	public static void handleCapeRackInteraction(Player p) {
		if (p.getItems().playerHasItem(UNTRIMMED_COMP_CAPE) ||
				p.getItems().isWearingItem(UNTRIMMED_COMP_CAPE) ||
					p.getItems().bankContains(UNTRIMMED_COMP_CAPE)) {
			handleTrimmedCape(p);
		} else
			handleUntrimmedCape(p);
	}

	//Requirements for receiving an untrimmed cape
	private static void handleUntrimmedCape(Player p) {
		if (p.getItems().freeSlots() < 2) {
			p.sendMessage("You need atleast two free slots to claim this cape.");
			return;
		}
		
		if (p.maxRequirements(p) && p.getAchievements().hasCompletedAll() && p.diaryAmount >= 10 && p.totalTheatreFinished >= 25
				&& p.totalRaidsFinished >= 50) {
			p.getItems().addItem(UNTRIMMED_COMP_CAPE, 1);
			p.sendMessage("You claim the Completionist cape.");
			GlobalMessages.send("" + Misc.capitalizeJustFirst(p.playerName) + " Has just Claimed their Completionist cape!",
					GlobalMessages.MessageType.COMPLETIONIST);
		} else {
			p.sendMessage("You must meet all of the requirements to claim the Completionist cape!");
		}
	}
	
	//Requirements for receiving a trimmed cape
	private static void handleTrimmedCape(Player p) {
		if (p.getItems().freeSlots() < 2) {
			p.sendMessage("You need atleast two free slots to claim this cape.");
			return;
		}

		if (p.maxRequirements(p) && p.getAchievements().hasCompletedAll() && p.diaryAmount >= 10 && p.totalTheatreFinished >= 100
				&& p.totalRaidsFinished >= 100) {
			if (p.getItems().isWearingItem(UNTRIMMED_COMP_CAPE, 1)) {
				p.getItems().removeItem(UNTRIMMED_COMP_CAPE, 1);
			}
			p.getItems().deleteItem(UNTRIMMED_COMP_CAPE, 1);
			p.getItems().addItem(TRIMMED_COMP_CAPE, 1);
			p.sendMessage("You claim the Trimmed Completionist cape.");
		} else {
			p.sendMessage("You must meet all of the requirements to claim the Trimmed Completionist cape!");
		}
	}
}
