package valius.net.packet.impl.npcoptions;

import java.util.concurrent.TimeUnit;

import valius.content.PotionMixing;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.karamja.KaramjaDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.content.achievement_diary.wilderness.WildernessDiaryEntry;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.npc.pets.Probita;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerAssistant;
import valius.model.entity.player.PlayerAssistant.PointExchange;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.thieving.Thieving.Pickpocket;
import valius.world.World;

/*
 * @author Matt
 * Handles all 2nd options on non playable characters.
 */

public class NpcOptionTwo {

	public static void handleOption(Player player, NPC npc) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(player)) {
			return;
		}
		player.clickNpcType = 0;
		player.rememberNpcIndex = player.npcClickIndex;
		player.npcClickIndex = 0;

		/*
		 * if(Fishing.fishingNPC(c, npcType)) { Fishing.fishingNPC(c, 2, npcType);
		 * return; }
		 */
		// if (PetHandler.talktoPet(player, npcType))
		// return;
		int npcType = npc.npcType;
		if (PetHandler.isPet(npcType)) {
			if (PetHandler.getOptionForNpcId(npcType) == "second") {
				if (PetHandler.pickupPet(player, npcType, true))
					return;
			}
		}
		player.getQuestManager().onNpcClick(2, npc);
		if (World.getWorld().getHolidayController().clickNpc(player, 2, npcType)) {
			return;
		}
		switch (npcType) {
		case 7204:
			player.getShops().openShop(130);
			player.sendMessage("@blu@ Please type ::donate to go to the donation store. 1 Donator Token is $1. ");
			player.sendMessage("@blu@ Type @pur@::dperks</col> to go to the donation store. 1 Donator Token is $1. ");
			player.sendMessage("@blu@Anything above at once will have a 100% chance to receive a @pur@Mystery Box</col>!");
			player.sendMessage("@blu@Anything above $25 at once will have a 100% chance to receive a @pur@Valius Box</col>!");
			player.sendMessage("@blu@Anything above $50 at once will have a 100% chance to receive a @pur@Ultra Mystery Box</col>!");
			player.sendMessage("@blu@You will receive 20% bonus exp for certain amount of time depening on the amount spent");
			break;
			
		case 3819://limited time shops
			player.getShops().openShop(137);
			player.sendMessage("@blu@ Please type ::donate to go to the donation store. 1 Donator Token is $1. ");
			player.sendMessage("@blu@ Type @pur@::dperks</col> to go to the donation store. 1 Donator Token is $1. ");
			player.sendMessage("@blu@Anything above at once will have a 100% chance to receive a @pur@Mystery Box</col>!");
			player.sendMessage("@blu@Anything above $25 at once will have a 100% chance to receive a @pur@Valius Box</col>!");
			player.sendMessage("@blu@Anything above $50 at once will have a 100% chance to receive a @pur@Ultra Mystery Box</col>!");
			player.sendMessage("@blu@You will receive 20% bonus exp for certain amount of time depening on the amount spent");
			break;
			
		case 1306:
			if (player.getItems().isWearingItems()) {
				player.sendMessage("You must remove your equipment before changing your appearance.");
				player.canChangeAppearance = false;
			} else {
				player.getPA().showInterface(3559);
				player.canChangeAppearance = true;
			}
			break;
		case 7690:
			player.getInfernoMinigame().gamble();
			break;
		case 1909:
			player.getDH().sendDialogues(901, 1909);
			break;
		case 5449:
			PotionMixing.decantInventory(player);
			player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.POTION_DECANT);
			break;
		case 2989:
			player.getShops().openShop(120);
			break;
			
		case 3413:
			player.getShops().openShop(140);
			break;

		case 3307:
			player.getPA().showInterface(37700);
			player.sendMessage("Set different colors for specific items for easier looting!");
			break;

		case 4321:
			int totalBlood = player.getItems().getItemAmount(13307);
			if (totalBlood >= 1) {
				player.getPA().exchangeItems(PointExchange.BLOOD_POINTS, 13307, totalBlood);
			}
			break;

		case 822:
			player.getShops().openShop(81);
			break;

		case 7520:
			player.getDH().sendDialogues(855, 7520);
			break;

		case 6774:
			player.getShops().openShop(117);
			break;
		case 5314:
			if (player.lastTeleportX == 0) {
				player.sendMessage("You haven't teleported anywhere recently.");
			} else {
				player.getPA().startTeleport(player.lastTeleportX, player.lastTeleportY, player.lastTeleportZ, "modern", false);
			}
			break;
		case 3254:
			player.getPA().showInterface(65000);
			/*
			 * player.getPA().showInterface(62100); int startId = 62107; for (final
			 * teleports t : teleports.values()) { for(int i = 0; i <
			 * t.getTeleports().length; i++) { TeleportOption tele = t.getTeleports()[i];
			 * player.getPA().sendFrame126("<shad=-1>"+tele.getName(), startId); startId++;
			 * } } for(int i = startId; i < 62197; i++) { player.getPA().sendFrame126("",
			 * i); break;}
			 */
		case 6773:
			if (!player.pkDistrict) {
				player.sendMessage("You cannot do this right now.");
				return;
			}
			if (player.inClanWarsSafe()) {
				player.getSafeBox().openSafeBox();
			}
			break;

		case 4407:
			player.getShops().openShop(19);
			break;

		case 2040:
			if (player.getZulrahLostItems().size() > 0) {
				player.getDH().sendDialogues(642, 2040);
				player.nextChat = -1;
			} else {
				if (player.getZulrahEvent().isActive()) {
					player.getDH().sendStatement("It seems that a zulrah instance for you is already created.",
							"If you think this is wrong then please re-log.");
					player.nextChat = -1;
					return;
				}
				player.getZulrahEvent().initialize();
			}
			break;

		case 17: // Rug merchant - Bedabin Camp
			if (!player.getDiaryManager().getDesertDiary().hasCompleted("HARD")) {
				player.getDH().sendNpcChat1(
						"You must have completed all hard diaries here in the desert \\n to use this location.", 17,
						"Rug Merchant");
				return;
			}
			player.startAnimation(2262);
			AgilityHandler.delayFade(player, "NONE", 3180, 3043, 0, "You step on the carpet and take off...",
					"at last you end up in bedabin camp.", 3);
			break;

		case 3077:
			long milliseconds = (long) player.playTime * 600;
			long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
			long hours = TimeUnit.MILLISECONDS.toHours(milliseconds - TimeUnit.DAYS.toMillis(days));
			String time = days + " days and " + hours + " hours.";
			player.getDH().sendNpcChat1("You've been playing Valius for " + time, 3077, "Hans");
			player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.HANS);
			break;

		case 3680:
			AgilityHandler.delayFade(player, "NONE", 2674, 3274, 0, "The sailor brings you onto the ship.",
					"and you end up in ardougne.", 3);
			player.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.SAIL_TO_ARDOUGNE);
			break;

		case 5034:
			player.getPA().startTeleport(2929, 4813, 0, "modern", false);
			player.getDiaryManager().getLumbridgeDraynorDiary()
					.progress(LumbridgeDraynorDiaryEntry.TELEPORT_ESSENCE_LUM);
			break;

		case 5906:
			Probita.cancellationOfPreviousPet(player);
			break;

		case 2180:
			player.getDH().sendDialogues(70, 2180);
			break;

		case 401:
		case 402:
		case 405:
		case 7663:
			player.getDH().sendDialogues(3304, npcType);
			break;
		case 6797: // Nieve
			if (player.getSkills().getLevel(Skill.SLAYER) < 90) {
				player.getDH().sendNpcChat1("You must have a slayer level of atleast 90 weakling.", 6797, "Nieve");
				return;
			} else {
				player.getDH().sendDialogues(3304, player.npcType);
			}
			break;
		case 5919: // Grace
			player.getShops().openShop(18);
			break;
		case 311:
			player.getDH().sendDialogues(661, 311);
			break;
		case 4423: // Jossik
			player.getShops().openShop(13);
			break;
		case 6747:
			player.getShops().openShop(77);
			break;
		case 2184:
			player.getShops().openShop(29);
			break;
		case 2580:
			player.getPA().startTeleport(3039, 4835, 0, "modern", false);
			player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.ABYSS_TELEPORT);
			player.dialogueAction = -1;
			player.teleAction = -1;
			break;
		case 3936:
			AgilityHandler.delayFade(player, "NONE", 2421, 3781, 0, "You board the boat...", "And end up in Jatizso",
					3);
			player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_JATIZSO);
			break;
		case 3894:
			player.getShops().openShop(26);
			break;
		case 3078:
			player.getThieving().steal(Pickpocket.MAN, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3550:
			player.getThieving().steal(Pickpocket.MENAPHITE_THUG, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 6094:
			player.getThieving().steal(Pickpocket.GNOME, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3106:
			player.getThieving().steal(Pickpocket.HERO, NPCHandler.npcs[player.rememberNpcIndex]);
			break;
		case 3257:
			if(player.getMode().isIronman() || player.getMode().isUltimateIronman() || player.getMode().isHcIronman() || player.getMode().isGroupIronman()){
				player.sendMessage("You cannot open this shop as an Ironman player.");
			}else{
				player.getShops().openShop(16);
			}
			break;
		case 637:
			player.getShops().openShop(6);
			break;
		case 3219:
			player.getShops().openShop(113);
			break;
		case 534:
			//if (Boundary.isIn(player, Boundary.VARROCK_BOUNDARY)) {
				player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.DRESS_FOR_SUCESS);
		//	}
			player.getShops().openShop(114);
			break;
		case 732:
			player.getShops().openShop(16);
			break;
		case 5809:
			player.getShops().openShop(20);
			break;
		case 315:
			player.getShops().openShop(80);
			player.sendMessage("@blu@ You have @bla@" + player.getBH().getBounties() + "</col> Bounty hunter points.");
			break;
		case 6599:
			player.getShops().openShop(79);
			break;
		case 3341:
			PlayerAssistant.refreshSpecialAndHealth(player);
			break;
		case 403:
			player.getDH().sendDialogues(12001, -1);
			break;
		case 3216:
			player.getShops().openShop(8);
			break;
		case 2578:
			player.getDH().sendDialogues(2400, -1);
			break;
			/*
			 * FISHING
			 *    startFishing(fishing spot id,fishing tool item id);
			 */
			case 3657://cage and harpoon
				player.getFishing().startFishing(npcType,311);
				break;
			case 3417://lure&bait
				player.getFishing().startFishing(npcType,307);
				break;
			case 3913://smallnet
			player.getFishing().startFishing(npcType,307);
			break;
			case 4712://manta
				player.getFishing().startFishing(npcType, 305);
				break;
			case 1520://shark
				player.getFishing().startFishing(npcType,311);
				break;
		case 532:
			player.getShops().openShop(47);
			break;
		case 1599:
			player.getShops().openShop(10);
			player.sendMessage("You currently have @red@" + player.getSlayer().getPoints() + " @bla@slayer points.");
			break;
		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 394:
		case 567: // Banker
		case 766:
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
		case 2200:
			player.getPA().openUpBank();
			break;

		case 1785:
			player.getShops().openShop(8);
			break;

		case 3218:// magic supplies
			player.getShops().openShop(6);
			break;
		case 3217:// range supplies
			player.getShops().openShop(48);
			break;
		case 3796:
			player.getShops().openShop(6);
			break;

		case 1860:
			player.getShops().openShop(6);
			break;

		case 519:
			player.getShops().openShop(7);
			break;

		case 548:
			player.getDH().sendDialogues(69, player.npcType);
			break;

		case 2258:

			break;

		case 1045: // Santa
			int stage = player.getHolidayStages().getStage("Christmas");

			if (player.getItems().isWearingItems()) {
				player.sendMessage("You cannot bring your armour here, you'd freeze to the ground.");
				return;
			}

			if (stage > 1) {
				player.getPA().startTeleport(2833, 3804, 0, "modern", false);
			} else {
				player.sendMessage("I should perhaps speak to santa first.");
			}
			break;

		case 2814:
			if (player.getMode().isIronman() || player.getMode().isUltimateIronman() || player.getMode().isHcIronman() || player.getMode().isGroupIronman()) {
				player.getShops().openShop(41);
			} else {
				player.sendMessage("You must be an Iron Man to access this shop.");
			}
			break;
		case 2813:
			player.getShops().openShop(2);
			break;

		case 528:
			player.getShops().openShop(9);
			break;

		}
	}

}
