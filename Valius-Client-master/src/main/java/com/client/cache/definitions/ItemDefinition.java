package com.client.cache.definitions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import com.client.Client;
import com.client.cache.Archive;
import com.client.cache.graphics.Sprite;
import com.client.collection.Cache;
import com.client.draw.raster.Raster;
import com.client.draw.raster.Rasterizer3D;
import com.client.io.Buffer;
import com.client.model.Model;

public final class ItemDefinition {

	public static void applyTexturing(Model model, int id) {
		switch (id) {
		case 22000: // test model, not actually going to use this (it's the red phat)
			// System.out.println("Lava phat-------------------------");
			model.setTexture(40); // sets the texture to all faces. model.setTexture(40, face) for a specific
									// face;
			// Model.printModelColours(model); //a method for getting all the model faces to
			// display
			// System.out.println("Lava phat-------------------------");
			break;

		case 22001: // test model, not actually going to use this (it's the red phat)
			// System.out.println("Infernal phat-------------------------");
			model.setTexture(59); // sets the texture to all faces. model.setTexture(40, face) for a specific
									// face;
			// Model.printModelColours(model); //a method for getting all the model faces to
			// display
			// System.out.println("Infernal phat-------------------------");
			break;
		case 33142:// firecape
			model.overrideTexture(40, 68);
			break;
		case 33144:// firecape
			model.overrideTexture(40, 69);
			break;
		case 33145:// firecape
		case 33630:
			model.overrideTexture(40, 56);
			break;
			
		case 33623:// fire max cape
			model.overrideTexture(40, 68);
			model.overrideTexture(31, 68);
			break;
		case 33625:// fire max cape
			model.overrideTexture(40, 69);
			model.overrideTexture(31, 69);
			break;
		case 33626:// fire max cape
			model.overrideTexture(40, 56);
			model.overrideTexture(31, 56);
			break;
		case 33624:// fire max cape
			model.overrideTexture(40, 63);
			model.overrideTexture(31, 63);
			break;
		case 33759:
			model.overrideTexture(59, 117);
			
		case 33143:// blue inferno\
		case 33627:
			model.overrideTexture(59, 64);
			break;
		case 33146:// green inferno
		case 33628:
			model.overrideTexture(59, 65);
			break;
			
		case 33274:
			model.overrideTexture(59, 77);
			model.overrideTexture(40, 86);
			break;
			
		case 33147:// purple inferno
		case 33580:
		case 33629:
			model.overrideTexture(59, 72);
			break;
		case 33148:// firecape
			model.overrideTexture(40, 63);
			break;
		case 33164:// firecape mix
			model.overrideTexture(40, 68);
			break;
		case 33594:
		case 33595:
		case 33597:
		case 33598:
		case 33599:
		case 33600:
			model.overrideTexture(59, 97);
			break;
		case 33165:// firecape mix
			model.overrideTexture(40, 69);
			break;
		case 33166:// firecape mix
			model.overrideTexture(40, 56);
			break;
		case 33167:// firecape mix
			model.overrideTexture(40, 63);
			break;
		case 33161:// blue inferno mix
			model.overrideTexture(40, 64);
			break;
		case 33162:// green inferno mix
			model.overrideTexture(40, 65);
			break;
		case 33163:// purple inferno mix
			model.overrideTexture(40, 72);
			break;
		case 33150:
			model.setTexture(59);
			break;
		case 33151:
			model.setTexture(59);
			break;
		case 33152:
			model.setTexture(59);
			break;
		case 33153:
			model.setTexture(59);
			break;
			
		case 33557:
			model.overrideTexture(59, 67);
			break;
			
			
			//upgrade rev weapons texture ids needed
		    case 33778://F variants
			case 33781:
			case 33784:
			model.overrideTexture(40, 116);
			break;
			
			//upgrade rev weapons texture ids needed
		    case 33779://B variants
			case 33782:
			case 33785:
			model.overrideTexture(40, 117);
			break;
			
			//upgrade rev weapons texture ids needed
		    case 33780://T variants
			case 33783:
			case 33786:
			model.overrideTexture(40, 118);
			break;
			
		 case 33341:
			case 33342:
			case 33343:
			case 33344:
			case 33345:
			model.overrideTexture(52, 73);
			model.overrideTexture(59, 73);
			break;
			
			case 33331:
			case 33332:
			case 33334:
			case 33335:
			case 33336:
			model.overrideTexture(57, 82);
			model.overrideTexture(59, 82);
			break;
			case 33516:
			case 33517:
			case 33518:
			case 33519:
			case 33520:
			model.overrideTexture(57, 78);
			model.overrideTexture(59, 78);
			break;
		case 33286:
			model.overrideTexture(40, 79);
			break;
		case 33154:
			model.overrideTexture(63, 59);
			break;
		case 33285:
			model.overrideTexture(63, 79);
			break;
		case 33338:
			model.overrideTexture(55, 92);
			break;
		case 33339:
			model.overrideTexture(55, 92);
			break;
		case 33340:
			model.overrideTexture(55, 92);
			break;
		case 33313:
			model.overrideTexture(52, 77);
			model.overrideTexture(59, 77);
			break;
		case 33314:
			model.overrideTexture(52, 77);
			model.overrideTexture(59, 77);
			break;
		case 33315:
			model.overrideTexture(52, 77);
			model.overrideTexture(59, 77);
			break;
		case 33316:
			model.overrideTexture(52, 77);
			model.overrideTexture(59, 77);
			break;
		case 33317:
			model.overrideTexture(52, 77);
			model.overrideTexture(59, 77);
			break;
		case 33353:
			model.overrideTexture(91, 78);
			break;
		case 33358:
			model.overrideTexture(724, 78);
			break;
		case 33359:
			model.overrideTexture(714, 78);
			break;
		case 33347:
			model.overrideTexture(40, 78);
			model.overrideTexture(59, 78);
			break;
		case 33348:
			model.overrideTexture(40, 78);
			model.overrideTexture(59, 78);
			break;
		case 33349:
			model.overrideTexture(40, 78);
			model.overrideTexture(59, 78);
			break;
		case 32991:
			model.overrideTexture(91, 78);
			break;
		case 33380:
			model.overrideTexture(40, 66);
			break;
		case 33375:
			model.overrideTexture(55, 66);
			break;
		case 33376:
			model.overrideTexture(55, 66);
			break;
		case 33377:
			model.overrideTexture(55, 66);
			break;
		case 33378:
			model.overrideTexture(55, 66);
			break;
		case 33379:
			model.overrideTexture(55, 66);
			break;
		case 33404:
			model.overrideTexture(59, 78);
			break;
		case 33405:
			model.setTexture(56);
			break;
		case 33406:
			model.setTexture(69);
			break;
		case 33407:
			model.setTexture(63);
			break;
		case 33408:
			model.setTexture(68);
			break;
		case 33409:
			model.setTexture(64);
			break;
		case 33410:
			model.setTexture(65);
			break;
		case 33411:
			model.setTexture(72);
			break;
		case 33412:
			model.setTexture(76);
			break;
		case 33413:
			model.setTexture(78);
			break;
		case 33414:
			model.setTexture(66);
			break;
		case 33415:
			model.setTexture(73);
			break;
		case 33416:
			model.setTexture(71);
			break;
		case 33417:
			model.setTexture(83);
			break;
		case 33418:
			model.setTexture(67);
			break;
		case 33419:
			model.setTexture(70);
			break;
		case 33420:
			model.setTexture(74);
			break;
		case 33422:
			model.replaceTexture(63, 67);
			break;
			
		case 33546:
			model.replaceTexture(59, 67);
			break;

		case 33438:
			model.overrideTexture(63, 66);
			model.overrideTexture(75, 66);
			model.overrideTexture(73, 66);
			model.overrideTexture(64, 66);
			model.overrideTexture(91, 66);
			break;

		case 33370:
			model.replaceTexture(291, 66);
			model.replaceTexture(258, 66);
			model.replaceTexture(207, 66);
			model.replaceTexture(236, 66);
			model.replaceTexture(-1, 66);
			break;
		case 33439:
			model.overrideTexture(59, 66);
			break;
		case 33440:
			model.setTexture(66);
			break;
		case 33437:
			model.replaceTexture(202, 66);
			break;
		case 33346:
			model.overrideTexture(63, 78);
			break;
		case 33322:
		case 33323:
		case 33324:
		case 33325:
			model.overrideTexture(59, 82);
			break;
		case 33083:
			model.overrideTexture(59, 77);
			break;
		case 33337:
			model.overrideTexture(59, 66);
			break;
		case 33326:
		case 33327:
		case 33328:
		case 33329:
			model.overrideTexture(52, 81);//71
			break;
			
		case 33538:
			model.overrideTexture(59, 62);
			break;
			
		case 33717:
			model.overrideTexture(59, 87);
			break;
			
			//blood perks
		case 33668:
		case 33526:
		case 33527:
		case 33535:
		case 33578:
		case 33581:
			model.overrideTexture(63, 66);
			model.overrideTexture(40, 66);
			model.overrideTexture(59, 66);
			break;
		case 33536://boogie bow textured
			model.overrideTexture(59, 66);
			break;
			
		case 33547://perk box stargaze pickaxe
			model.overrideTexture(59,  67);
			break;
			
		case 33658:
		case 33659:
		case 33660:
		case 33661:
		case 33662:
		case 33663:
			model.overrideTexture(59,  62);
			break;
			
		case 33558://valius cape
			model.overrideTexture(59, 63);
			break;
			
		case 33559://stunning broadsider sword
		case 33572:
			model.overrideTexture(59, 81);
			break;
			
		case 33561://void longsword infernal
		case 33573:
			model.overrideTexture(59, 74);
			break;
			
		case 33563:
			model.overrideTexture(53, 63);
			model.overrideTexture(59, 63);
			break;
			
		case 33564://grotesque
			model.overrideTexture(53, 63);
			model.overrideTexture(59, 63);
			break;
			
		case 33565:
			model.overrideTexture(53, 63);
			model.overrideTexture(59, 63);
			break;
			
		case 33669:
			model.overrideTexture(63, 70);
			break;
			
		case 33570:
		case 33571:
			model.overrideTexture(59, 63);
			model.overrideTexture(59, 63);
			break;
			
		case 33566:
			model.overrideTexture(59, 2);
			break;
			
			/*
			 * halloween
			 */
			//spooky
		case 33733:
		case 33734:
		case 33735:
		case 33736:
		case 33737:
		case 33747:
		case 33752:
		case 33754:
		case 33755:
		case 33758:
			model.overrideTexture(59, 111);
			
			//glowing
		case 33728:
		case 33729:
		case 33730:
		case 33731:
		case 33732:
		case 33749:
		case 33757:
			model.overrideTexture(59, 114);
			
			//flashing
		case 33738:
		case 33739:
		case 33740:
		case 33741:
		case 33742:
			model.overrideTexture(59, 115);
			break;
			
			//lit jack o bat
		case 33748:
			model.overrideTexture(59, 112);
			break;
		
		case 33756:
			model.overrideTexture(63, 104);
			break;
			
		case 33884://elder mbox
			model.overrideTexture(63, 89);
			break;
		}
	}

	public static void unpackConfig(final Archive streamLoader) {
		stream = new Buffer(streamLoader.getDataForName("obj.dat"));
		Buffer stream = new Buffer(streamLoader.getDataForName("obj.idx"));
		// stream = new Buffer(FileOperations.readFile(Signlink.getCacheDirectory() +
		// "/data/obj.dat"));
		// final Buffer stream = new
		// Buffer(FileOperations.readFile(Signlink.getCacheDirectory() +
		// "/data/obj.idx"));

		totalItems = stream.readShort();
		System.out.println("total items: " + totalItems);
		streamIndices = new int[totalItems + 1000];
		int i = 2;
		for (int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.readShort();
		}

		cache = new ItemDefinition[10];
		for (int index = 0; index < 10; index++) {
			cache[index] = new ItemDefinition();
		}
		// itemDump();
	}

	public static ItemDefinition forID(int itemId) {
		for (int j = 0; j < 10; j++) {
			if (cache[j].id == itemId) {
				return cache[j];
			}
		}
		int streamItemId = itemId;
		if (itemId == -1)
			streamItemId = 0;
		if (itemId >= streamIndices.length)
			streamItemId = 0;

		cacheIndex = (cacheIndex + 1) % 10;
		final ItemDefinition itemDef = cache[cacheIndex] = new ItemDefinition();
		stream.position = streamIndices[streamItemId];
		itemDef.id = itemId;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		
		if(itemId >= streamIndices.length)
			itemDef.name = "undefined";
		customItems(itemDef);
		customItems2(itemDef);
		customItems3(itemDef);

		if (itemDef.certTemplateID != -1) {
			itemDef.toNote();
		}

		if (itemDef.opcode140 != -1) {
			itemDef.method2789(forID(itemDef.opcode140), forID(itemDef.opcode139));
		}

		if (itemDef.opcode149 != -1) {
			itemDef.method2790(forID(itemDef.opcode149), forID(itemDef.opcode148));
		}

		switch (itemId) {

		}
		return itemDef;
	}

	private static void customItems(ItemDefinition itemDef) {
		// ItemDefinition itemDef = forID(itemId);

		switch (itemDef.id) {

		case 11864:
		case 11865:
		case 19639:
		case 19641:
		case 19643:
		case 19645:
		case 19647:
		case 19649:
		case 23073:
		case 23075:
			itemDef.equipActions[2] = "Log";
			itemDef.equipActions[1] = "Check";
			break;

		case 13136:
			itemDef.equipActions[2] = "Elidinis";
			itemDef.equipActions[1] = "Kalphite Hive";
			break;
		case 2550:
			itemDef.equipActions[2] = "Check";
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			itemDef.equipActions[1] = "Edgeville";
			itemDef.equipActions[2] = "Karamja";
			itemDef.equipActions[3] = "Draynor";
			itemDef.equipActions[4] = "Al-Kharid";
			break;

		case 22000:
			itemDef.name = "Lava partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A lava partyhat.";
			break;

		case 22001:
			itemDef.name = "Infernal partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An Infernal partyhat.";
			break;

		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566: // Ring of duelling
			itemDef.equipActions[2] = "Shantay Pass";
			itemDef.equipActions[1] = "Clan wars";
			break;

		case 21307:
			itemDef.name = "Pursuit crate";
			break;

		case 12792:
			itemDef.name = "Graceful recolor kit";
			break;

		case 12022:
			itemDef.name = "Bandos Casket";
			itemDef.description = "100% chance for an item off the Bandos gwds rare drop table.";
			break;

		case 12024:
			itemDef.name = "Armadyl Casket";
			itemDef.description = "100% chance for an item off the Armadyl gwds rare drop table.";
			break;

		case 12026:
			itemDef.name = "Saradomin Casket";
			itemDef.description = "100% chance for an item off the Saradomin gwds rare drop table.";
			break;

		case 12028:
			itemDef.name = "Zamorak Casket";
			itemDef.description = "100% chance for an item off the Zamorak gwds rare drop table.";
			break;

		case 964:
			itemDef.name = "Pet Petie";
			break;

		case 20853:
			itemDef.name = "Deep Sea Bait";
			itemDef.stackable = true;
			break;

		/*
		 * case 17014: itemDef.name = "Dragon flail"; itemDef.modelId = 50083;
		 * itemDef.modelZoom = 1440; itemDef.modelRotation2 = 272;
		 * itemDef.modelRotation1 = 352; itemDef.modelOffset1 = 32;
		 * //itemDef.modelOffset2 = 0; itemDef.maleModel = 50083; itemDef.femaleModel =
		 * 50083; itemDef.anInt164 = -1; itemDef.anInt188 = -1; itemDef.aByte205 = -8;
		 * itemDef.groundOptions = new String[] { null, null, "Take", null, null };
		 * itemDef.inventoryOptions = new String[] { "Wear", null, null, null, "Drop" };
		 * itemDef.description = "An Ancient Dragon Flail."; break;
		 */

		case 33272:
			itemDef.name = "Justiciar's Longsword";
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 65472;
			itemDef.modelZoom = 1726;
			itemDef.modelRotation1 = 1576;
			itemDef.modelRotation2 = 242;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			// itemDef.anInt204 = 0;
			// itemDef.aByte205 = -12;
			// itemDef.aByte154 = 0;
			itemDef.maleModel = 65465;
			itemDef.femaleModel = 65465;
			itemDef.description = "An ancient longsword received from the Trial of Flames.";
			break;

		case 33168:
			itemDef.name = "Justiciar kiteshield";
			itemDef.modelId = 65471;
			itemDef.modelZoom = 1600;
			itemDef.modelRotation2 = 250;
			itemDef.modelRotation1 = 300;
			itemDef.modelOffset1 = -4;
			itemDef.modelOffset2 = -4;
			itemDef.maleModel = 65473;
			itemDef.femaleModel = 65474;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An ancient kiteshield. Part of the Justiciar set.";
			break;

		case 2996:
			itemDef.name = "PKP Ticket";
			itemDef.inventoryOptions = new String[5];
			itemDef.description = "Exchange this for a PK Point.";
			break;
		case 13226:
			itemDef.name = "Herb Sack";
			itemDef.inventoryOptions = new String[5];
			itemDef.description = "A sack for storing grimy herbs.";
			break;
		case 13346:
			itemDef.name = "Raid Mystery Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.description = "Open for chances to receive Raid items & other awesome rewards.";
			break;
		case 8800:
			itemDef.name = "Donator Tokens";
			itemDef.modelId = 31624;
			// itemDef.stackAmounts = new int[] { 2, 3, 50, 100, 500000, 1000000, 2500000,
			// 10000000, 100000000, 0 };//amount the model will change at
			// itemDef.stackIDs = new int[] { 8801, 8802, 8803, 8804, 8805, 8806, 8807,
			// 8808, 8809, 0 };//new item id to grab the model from
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 853;
			itemDef.modelRotation2 = 1885;
			itemDef.modelRotation1 = 0;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 3;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8801:
			itemDef.name = "Donator Tokens";
			itemDef.modelId = 15344;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8802:
			itemDef.name = "Donator Tokens";
			itemDef.modelId = 15345;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8803:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15346;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8804:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15347;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8805:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15348;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8806:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15349;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8807:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15350;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8808:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15351;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 8809:
			itemDef.name = "Donator Coins";
			itemDef.modelId = 15352;
			itemDef.stackable = true;
			itemDef.modifiedModelColors = new int[] { 5813, 9139 };
			itemDef.originalModelColors = new int[] { 22450, 22450 };
			itemDef.modelZoom = 550;
			itemDef.modelRotation2 = 1764;
			itemDef.modelRotation1 = 202;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.description = "Tokens for the Donator Store.";
			break;
		case 15098:
			itemDef.name = "Dice (up to 100)";
			itemDef.description = "A 100-sided dice.";
			itemDef.modelId = 31223;
			itemDef.modelZoom = 1104;
			itemDef.modelRotation2 = 215;
			itemDef.modelRotation1 = 94;
			itemDef.modelOffset2 = -5;
			itemDef.modelOffset1 = -18;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Public-roll";
			itemDef.inventoryOptions[2] = null;
			itemDef.name = "Dice (up to 100)";
			itemDef.anInt196 = 15;
			itemDef.anInt184 = 25;
			break;

		case 32991:
			itemDef.name = "Divine spirit shield";
			itemDef.description = "An ethereal shield with an divine sigil attached to it.";
			itemDef.modelId = 50001;
			itemDef.maleModel = 50002;
			itemDef.femaleModel = 50002;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation2 = 1050;
			itemDef.modelRotation1 = 396;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = -8;
			break;
		case 32992:
			itemDef.name = "Rainbow spirit shield";
			itemDef.description = "An ethereal shield with all 4 sigils attached to it.";
			itemDef.modelId = 50004;
			itemDef.maleModel = 50005;
			itemDef.femaleModel = 50005;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation2 = 1050;
			itemDef.modelRotation1 = 396;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32993:
			itemDef.name = "Divine sigil";
			itemDef.description = "A sigil in the shape of a divine symbol.";
			itemDef.modelId = 50003;
			itemDef.modelZoom = 848;
			itemDef.modelRotation1 = 267;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			break;
		case 33060:
			itemDef.name = "Barrows Sword";
			itemDef.description = "A sword glowing with otherworldy energy.";
			itemDef.modelId = 22325;
			itemDef.maleModel = 50010;
			itemDef.femaleModel = 50010;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation2 = 1050;
			itemDef.modelRotation1 = 396;
			itemDef.modelOffset2 = 4;
			itemDef.modelOffset1 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = -8;
			break;
		case 32994:
			itemDef.name = "Statius's platebody";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42602;
			itemDef.maleModel = 35951;
			itemDef.femaleModel = 35964;
			itemDef.modelZoom = 1312;
			itemDef.modelRotation1 = 272;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -2;
			itemDef.modelOffset2 = 39;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32995:
			itemDef.name = "Statius's platelegs";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42590;
			itemDef.maleModel = 35947;
			itemDef.femaleModel = 35961;
			itemDef.modelZoom = 1625;
			itemDef.modelRotation1 = 355;
			itemDef.modelRotation2 = 2046;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32996:
			itemDef.name = "Statius's full helm";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42596;
			itemDef.maleModel = 35943;
			itemDef.femaleModel = 35958;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 2039;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32997:
			itemDef.name = "Statius's warhammer";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42577;
			itemDef.maleModel = 35968;
			itemDef.femaleModel = 35968;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 507;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 7;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32998:
			itemDef.name = "Vesta's chainbody";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42593;
			itemDef.maleModel = 35953;
			itemDef.femaleModel = 35965;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 32999:
			itemDef.name = "Vesta's plateskirt";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42581;
			itemDef.maleModel = 35950;
			itemDef.femaleModel = 35960;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33000:
			itemDef.name = "Vesta's longsword";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42597;
			itemDef.maleModel = 35969;
			itemDef.femaleModel = 35969;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 738;
			itemDef.modelRotation2 = 1985;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.maleOffset = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33001:
			itemDef.name = "Vesta's spear";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42599;
			itemDef.maleModel = 35973;
			itemDef.femaleModel = 35973;
			itemDef.modelZoom = 2022;
			itemDef.modelRotation1 = 480;
			itemDef.modelRotation2 = 15;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.maleOffset = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33002:
			itemDef.name = "Morrigan's leather body";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42578;
			itemDef.maleModel = 35954;
			itemDef.femaleModel = 35963;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 545;
			itemDef.modelRotation2 = 2;
			itemDef.modelOffset1 = -2;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33003:
			itemDef.name = "Morrigan's leather chaps";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42603;
			itemDef.maleModel = 35948;
			itemDef.femaleModel = 35959;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 482;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33004:
			itemDef.name = "Morrigan's coif";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42583;
			itemDef.maleModel = 35945;
			itemDef.femaleModel = 35956;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 537;
			itemDef.modelRotation2 = 5;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33005:
			itemDef.name = "Morrigan's javelin";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42592;
			itemDef.stackable = true;
			itemDef.maleModel = 42613;
			itemDef.femaleModel = 42613;
			itemDef.modelZoom = 1872;
			itemDef.modelRotation1 = 282;
			itemDef.modelRotation2 = 2009;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.maleOffset = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33006:
			itemDef.name = "Morrigan's throwing axe";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42582;
			itemDef.stackable = true;
			itemDef.maleModel = 42611;
			itemDef.femaleModel = 42611;
			itemDef.modelZoom = 976;
			itemDef.modelRotation1 = 672;
			itemDef.modelRotation2 = 2024;
			itemDef.modelOffset1 = -5;
			itemDef.maleOffset = -8;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33007:
			itemDef.name = "Zuriels robe top";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42591;
			itemDef.maleModel = 35952;
			itemDef.femaleModel = 35966;
			itemDef.modelZoom = 1373;
			itemDef.modelRotation1 = 373;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33008:
			itemDef.name = "Zuriels robe bottom";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42588;
			itemDef.maleModel = 35949;
			itemDef.femaleModel = 35962;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33009:
			itemDef.name = "Zuriels hood";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42604;
			itemDef.maleModel = 35944;
			itemDef.femaleModel = 35957;
			itemDef.modelZoom = 720;
			itemDef.modelRotation1 = 28;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33010:
			itemDef.name = "Zuriels staff";
			itemDef.description = "This item degrades in combat, and will turn to dust.";
			itemDef.modelId = 42595;
			itemDef.maleModel = 35971;
			itemDef.femaleModel = 35971;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 366;
			itemDef.modelRotation2 = 3;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33011:
			itemDef.name = "Craw's bow (u)";
			itemDef.description = "This bow once belonged to a formidable follower of Armadyl.";
			itemDef.modelId = 35777;
			itemDef.maleModel = 35768;
			itemDef.femaleModel = 35768;
			itemDef.modelZoom = 1979;
			itemDef.modelRotation1 = 1463;
			itemDef.modelRotation2 = 510;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33012:
			itemDef.name = "Craw's bow";
			itemDef.description = "This bow once belonged to a formidable follower of Armadyl.";
			itemDef.modelId = 35777;
			itemDef.maleModel = 35769;
			itemDef.femaleModel = 35769;
			itemDef.modelZoom = 1979;
			itemDef.modelRotation1 = 1463;
			itemDef.modelRotation2 = 510;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33013:
			itemDef.name = "Thammaron's sceptre (u)";
			itemDef.description = "A mighty sceptre used in long forgotten battles.";
			itemDef.modelId = 35776;
			itemDef.maleModel = 35772;
			itemDef.femaleModel = 35772;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33014:
			itemDef.name = "Thammaron's sceptre";
			itemDef.description = "A mighty sceptre used in long forgotten battles.";
			itemDef.modelId = 35776;
			itemDef.maleModel = 35773;
			itemDef.femaleModel = 35773;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33015:
			itemDef.name = "Viggora's chainmace (u)";
			itemDef.description = "An ancient chainmace with a peculiar mechanism that allows for varying attack styles.";
			itemDef.modelId = 35778;
			itemDef.maleModel = 35770;
			itemDef.femaleModel = 35770;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 944;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33016:
			itemDef.name = "Viggora's chainmace";
			itemDef.description = "An ancient chainmace with a peculiar mechanism that allows for varying attack styles.";
			itemDef.modelId = 35778;
			itemDef.maleModel = 35771;
			itemDef.femaleModel = 35771;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 944;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33018:
			itemDef.name = "Amulet of avarice";
			itemDef.description = "A hauntingly beautiful amulet bearing the shape of a skull.";
			itemDef.modelId = 35779;
			itemDef.maleModel = 35766;
			itemDef.femaleModel = 35766;
			itemDef.modelZoom = 420;
			itemDef.modelRotation1 = 191;
			itemDef.modelRotation2 = 86;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33019:
			itemDef.name = "Completionist cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modelId = 65270;
			itemDef.maleModel = 65297;
			itemDef.femaleModel = 65316;
			itemDef.modelZoom = 1316;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 24;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33020:
			itemDef.name = "Completionist cape (t)";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modelId = 65258;
			itemDef.maleModel = 65295;
			itemDef.femaleModel = 65328;
			itemDef.modelZoom = 1316;
			itemDef.modelRotation1 = 252;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 24;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33021:
			itemDef.name = "Torva full helm";
			itemDef.description = "An ancient warrior's full helm.";
			itemDef.modelId = 62714;
			itemDef.maleModel = 62738;
			itemDef.femaleModel = 62738;
			itemDef.modelZoom = 672;
			itemDef.modelRotation1 = 85;
			itemDef.modelRotation2 = 1867;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33022:
			itemDef.name = "Torva platebody";
			itemDef.description = "An ancient warrior's platebody.";
			itemDef.modelId = 62699;
			itemDef.maleModel = 62746;
			itemDef.femaleModel = 62746;
			itemDef.modelZoom = 1506;
			itemDef.modelRotation1 = 473;
			itemDef.modelRotation2 = 2042;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33023:
			itemDef.name = "Torva platelegs";
			itemDef.description = "An ancient warrior's platelegs.";
			itemDef.modelId = 62701;
			itemDef.maleModel = 62740;
			itemDef.femaleModel = 62740;
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 474;
			itemDef.modelRotation2 = 2045;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33024:
			itemDef.name = "Pernix cowl";
			itemDef.description = "An ancient warrior's cowl.";
			itemDef.modelId = 62693;
			itemDef.maleModel = 62739;
			itemDef.femaleModel = 62739;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 532;
			itemDef.modelRotation2 = 14;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33025:
			itemDef.name = "Pernix body";
			itemDef.description = "An ancient warrior's leather body.";
			itemDef.modelId = 62709;
			itemDef.maleModel = 62744;
			itemDef.femaleModel = 62744;
			itemDef.modelZoom = 1378;
			itemDef.modelRotation1 = 485;
			itemDef.modelRotation2 = 2042;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33026:
			itemDef.name = "Pernix chaps";
			itemDef.description = "An ancient warrior's chaps.";
			itemDef.modelId = 62695;
			itemDef.maleModel = 62741;
			itemDef.femaleModel = 62741;
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 504;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33027:
			itemDef.name = "Virtus mask";
			itemDef.description = "An ancient warrior's mask.";
			itemDef.modelId = 62710;
			itemDef.maleModel = 62736;
			itemDef.femaleModel = 62736;
			itemDef.modelZoom = 928;
			itemDef.modelRotation1 = 406;
			itemDef.modelRotation2 = 2041;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33028:
			itemDef.name = "Virtus robe top";
			itemDef.description = "An ancient warrior's robe top.";
			itemDef.modelId = 62704;
			itemDef.maleModel = 62748;
			itemDef.femaleModel = 62748;
			itemDef.modelZoom = 1122;
			itemDef.modelRotation1 = 488;
			itemDef.modelRotation2 = 3;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33029:
			itemDef.name = "Virtus robe legs";
			itemDef.description = "An ancient warrior's robe legs.";
			itemDef.modelId = 62700;
			itemDef.maleModel = 62742;
			itemDef.femaleModel = 62742;
			itemDef.modelZoom = 1740;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 2045;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33030:
			itemDef.name = "Zaryte bow";
			itemDef.description = "An ancient warrior's bow.";
			itemDef.modelId = 62692;
			itemDef.maleModel = 62750;
			itemDef.femaleModel = 62750;
			itemDef.modelZoom = 1703;
			itemDef.modelRotation1 = 221;
			itemDef.modelRotation2 = 404;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -13;
			itemDef.maleOffset = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33083:
			itemDef.name = "Tokhaar-kal";
			itemDef.description = "	A cape made of ancient, enchanted obsidian.";
			itemDef.modelId = 52073;
			itemDef.maleModel = 52072;
			itemDef.femaleModel = 52071;
			itemDef.modelZoom = 1615;
			itemDef.modelRotation1 = 339;
			itemDef.modelRotation2 = 192;
			itemDef.modelOffset1 = -4;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33089:
			itemDef.name = "Chaotic maul";
			itemDef.description = "A maul used to claim life from those who don't deserve it.";
			itemDef.modelId = 54286;
			itemDef.maleModel = 56294;
			itemDef.femaleModel = 56294;
			itemDef.modelZoom = 1447;
			itemDef.modelRotation1 = 525;
			itemDef.modelRotation2 = 350;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -7;
			itemDef.femaleOffset = -7;
			break;
		case 33094:
			itemDef.name = "Chaotic crossbow";
			itemDef.description = "A small crossbow, only effective at short distance.";
			itemDef.modelId = 54331;
			itemDef.maleModel = 56307;
			itemDef.femaleModel = 56307;
			itemDef.modelZoom = 1028;
			itemDef.modelRotation1 = 249;
			itemDef.modelRotation2 = 2021;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -54;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -7;
			itemDef.femaleOffset = -7;
			break;
		case 33095:
			itemDef.name = "Chaotic staff";
			itemDef.description = "This staff makes destructive spells more powerful.";
			itemDef.modelId = 54367;
			itemDef.maleModel = 56286;
			itemDef.femaleModel = 56286;
			itemDef.modelZoom = 1711;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 391;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -7;
			itemDef.femaleOffset = -7;
			break;
		case 33096:
			itemDef.name = "Chaotic kiteshield";
			itemDef.description = "A large metal shield.";
			itemDef.modelId = 54358;
			itemDef.maleModel = 56038;
			itemDef.femaleModel = 56038;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 276;
			itemDef.modelRotation2 = 1101;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33031:
			itemDef.name = "Chaotic rapier";
			itemDef.description = "A razor-sharp rapier.";
			itemDef.modelId = 54197;
			itemDef.maleModel = 56252;
			itemDef.femaleModel = 56252;
			itemDef.modelZoom = 1425;
			itemDef.modelRotation1 = 540;
			itemDef.modelRotation2 = 1370;
			itemDef.modelOffset1 = 9;
			itemDef.modelOffset2 = 13;
			itemDef.maleOffset = -12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -7;
			itemDef.femaleOffset = -7;
			break;
		case 33032:
			itemDef.name = "Chaotic longsword";
			itemDef.description = "A razor-sharp longsword.";
			itemDef.modelId = 54204;
			itemDef.maleModel = 56237;
			itemDef.femaleModel = 56237;
			itemDef.modelZoom = 1650;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1300;
			// itemDef.aByte154 = -14;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -7;
			itemDef.femaleOffset = -7;
			break;
		case 33097:
			itemDef.name = "Sword of Valius";
			itemDef.description = "A razor-sharp longsword.";
			itemDef.modelId = 53091;
			itemDef.maleModel = 53092;
			itemDef.femaleModel = 53092;
			itemDef.modelZoom = 2007;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33098:
			itemDef.name = "Valius longsword";
			itemDef.description = "A razor-sharp 2h sword.";
			itemDef.modelId = 53093;
			itemDef.maleModel = 53094;
			itemDef.femaleModel = 53094;
			itemDef.modelZoom = 4007;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 2047;
			itemDef.maleOffset = -8;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33099:
			itemDef.name = "White scimitar";
			itemDef.description = "A razor-sharp scimitar.";
			itemDef.modelId = 53097;
			itemDef.maleModel = 53098;
			itemDef.femaleModel = 53098;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 312;
			itemDef.modelRotation2 = 0;
			// itemDef.aByte154 = -14;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33100:
			itemDef.name = "White kiteshield";
			itemDef.description = "a heavy kiteshield.";
			itemDef.modelId = 53095;
			itemDef.maleModel = 53096;
			itemDef.femaleModel = 53096;
			itemDef.modelZoom = 1616;
			itemDef.modelRotation1 = 303;
			itemDef.modelRotation2 = 180;
			// itemDef.aByte154 = -14;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33033:
			itemDef.name = "Agility master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 677, 801, 43540, 43543, 43546, 43549, 43550, 43552, 43554, 43558,
					43560, 43575 };
			itemDef.modelId = 50030;
			itemDef.maleModel = 50031;
			itemDef.femaleModel = 50031;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33034:
			itemDef.name = "Attack master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 7104, 9151, 911, 914, 917, 920, 921, 923, 925, 929, 931, 946 };
			itemDef.modelId = 50032;
			itemDef.maleModel = 50033;
			itemDef.femaleModel = 50033;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33035:
			itemDef.name = "Construction master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 6061, 5945, 6327, 6330, 6333, 6336, 6337, 6339, 6341, 6345, 6347,
					6362 };
			itemDef.modelId = 50034;
			itemDef.maleModel = 50035;
			itemDef.femaleModel = 50035;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33036:
			itemDef.name = "Cooking master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 920, 920, 51856, 51859, 51862, 51865, 51866, 51868, 51870, 51874,
					51876, 51891 };
			itemDef.modelId = 50036;
			itemDef.maleModel = 50037;
			itemDef.femaleModel = 50037;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33037:
			itemDef.name = "Crafting master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 9142, 9152, 4511, 4514, 4517, 4520, 4521, 4523, 4525, 4529, 4531,
					4546 };
			itemDef.modelId = 50038;
			itemDef.maleModel = 50039;
			itemDef.femaleModel = 50039;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33038:
			itemDef.name = "Defence master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 10460, 10473, 41410, 41413, 41416, 41419, 41420, 41422, 41424,
					41428, 41430, 41445 };
			itemDef.modelId = 50040;
			itemDef.maleModel = 50041;
			itemDef.femaleModel = 50041;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33039:
			itemDef.name = "Farming master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 14775, 14792, 22026, 22029, 22032, 22035, 22036, 22038, 22040,
					22044, 22046, 22061 };
			itemDef.modelId = 50042;
			itemDef.maleModel = 50043;
			itemDef.femaleModel = 50043;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33040:
			itemDef.name = "Firemaking master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 8125, 9152, 4015, 4018, 4021, 4024, 4025, 4027, 4029, 4033, 4035,
					4050 };
			itemDef.modelId = 50044;
			itemDef.maleModel = 50045;
			itemDef.femaleModel = 50045;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33041:
			itemDef.name = "Fishing master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 9144, 9152, 38202, 38205, 38208, 38211, 38212, 38214, 38216,
					38220, 38222, 38237 };
			itemDef.modelId = 50046;
			itemDef.maleModel = 50047;
			itemDef.femaleModel = 50047;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33042:
			itemDef.name = "Fletching master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 6067, 9152, 33670, 33673, 33676, 33679, 33680, 33682, 33684,
					33688, 33690, 33705 };
			itemDef.modelId = 50048;
			itemDef.maleModel = 50049;
			itemDef.femaleModel = 50049;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33043:
			itemDef.name = "Herblore master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 9145, 9156, 22414, 22417, 22420, 22423, 22424, 22426, 22428,
					22432, 22434, 22449 };
			itemDef.modelId = 50050;
			itemDef.maleModel = 50051;
			itemDef.femaleModel = 50051;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33044:
			itemDef.name = "Hitpoints master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 818, 951, 8291, 8294, 8297, 8300, 8301, 8303, 8305, 8309, 8311,
					8319 };
			itemDef.modelId = 50052;
			itemDef.maleModel = 50053;
			itemDef.femaleModel = 50053;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			itemDef.femaleOffset = 4;
			break;
		case 33045:
			itemDef.name = "Hunter master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 5262, 6020, 8472, 8475, 8478, 8481, 8482, 8484, 8486, 8490, 8492,
					8507 };
			itemDef.modelId = 50054;
			itemDef.maleModel = 50055;
			itemDef.femaleModel = 50055;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33046:
			itemDef.name = "Magic master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 43569, 43685, 6336, 6339, 6342, 6345, 6346, 6348, 6350, 6354,
					6356, 6371 };
			itemDef.modelId = 50056;
			itemDef.maleModel = 50057;
			itemDef.femaleModel = 50057;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33047:
			itemDef.name = "Mining master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 36296, 36279, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
					10404, 10406, 10421 };
			itemDef.modelId = 50058;
			itemDef.maleModel = 50059;
			itemDef.femaleModel = 50059;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33048:
			itemDef.name = "Prayer master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 9163, 9168, 117, 120, 123, 126, 127, 127, 127, 127, 127, 127 };
			itemDef.modelId = 50060;
			itemDef.maleModel = 50061;
			itemDef.femaleModel = 50061;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33049:
			itemDef.name = "Range master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 3755, 3998, 15122, 15125, 15128, 15131, 15132, 15134, 15136,
					15140, 15142, 15157 };
			itemDef.modelId = 50062;
			itemDef.maleModel = 50063;
			itemDef.femaleModel = 50063;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33050:
			itemDef.name = "Runecrafting master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			// 4 //7 //10 //13 //14//16//18//22 //24//39
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 9152, 8128, 10318, 10321, 10324, 10327, 10328, 10330, 10332,
					10336, 10338, 10353 };
			itemDef.modelId = 50064;
			itemDef.maleModel = 50065;
			itemDef.femaleModel = 50065;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33051:
			itemDef.name = "Slayer master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modifiedModelColors = new int[] { 57022, 48811 };
			itemDef.originalModelColors = new int[] { 912, 920 };
			itemDef.modelId = 50066;
			itemDef.maleModel = 50067;
			itemDef.femaleModel = 50067;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33052:
			itemDef.name = "Smithing master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 8115, 9148, 10386, 10389, 10392, 10395, 10396, 10398, 10400,
					10404, 10406, 10421 };
			itemDef.modelId = 50068;
			itemDef.maleModel = 50069;
			itemDef.femaleModel = 50069;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33053:
			itemDef.name = "Strength master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 935, 931, 27538, 27541, 27544, 27547, 27548, 27550, 27552, 27556,
					27558, 27573 };
			itemDef.modelId = 50070;
			itemDef.maleModel = 50071;
			itemDef.femaleModel = 50071;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33054:
			itemDef.name = "Thieving master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 11, 0, 58779, 58782, 58785, 58788, 58789, 57891, 58793, 58797,
					58799, 58814 };
			itemDef.modelId = 50072;
			itemDef.maleModel = 50073;
			itemDef.femaleModel = 50073;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33055:
			itemDef.name = "Woodcutting master cape";
			itemDef.description = "	A cape worn by those who've overachieved.";
			itemDef.modifiedModelColors = new int[] { 57022, 48811, 2, 1029, 1032, 11, 12, 14, 16, 20, 22, 2 };
			itemDef.originalModelColors = new int[] { 25109, 24088, 6693, 6696, 6699, 6702, 6703, 6705, 6707, 6711,
					6713, 6728 };
			itemDef.modelId = 50074;
			itemDef.maleModel = 50075;
			itemDef.femaleModel = 50075;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
		case 33057:
			itemDef.name = "Abyssal Scythe";
			itemDef.description = "	A Scythe recieved from the Trials of Xeric CUSTOM RAID.";
			itemDef.modelId = 50081;
			itemDef.maleModel = 50080;
			itemDef.femaleModel = 50080;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.maleOffset = -12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33090:
			itemDef.name = "Goliath gloves (Black)";
			itemDef.description = "	A pair of gloves earned with blood.";
			itemDef.modelId = 50108;
			itemDef.maleModel = 50100;
			itemDef.femaleModel = 50101;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33091:
			itemDef.name = "Goliath gloves (Red)";
			itemDef.description = "	A pair of gloves earned with blood.";
			itemDef.modelId = 50108;
			itemDef.maleModel = 50102;
			itemDef.femaleModel = 50103;
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 65046, 65051, 65056 };
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33092:
			itemDef.name = "Goliath gloves (White)";
			itemDef.description = "	A pair of gloves earned with blood.";
			itemDef.modelId = 50108;
			itemDef.maleModel = 50104;
			itemDef.femaleModel = 50105;
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 64585, 64590, 64595 };
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33093:
			itemDef.name = "Goliath gloves (Yellow)";
			itemDef.description = "	A pair of gloves earned with blood.";
			itemDef.modelId = 50108;
			itemDef.maleModel = 50106;
			itemDef.femaleModel = 50107;
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 9767, 9772, 9777 };
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 40;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 12639:
		case 12637:
		case 12638:
			itemDef.description = "Provides players with infinite run energy!";
			break;
		case 33056:
			itemDef.name = "Events cape (slayer)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 933, 0, 0, 0, 0 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33081:
			itemDef.name = "Events cape (agility)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 669, 43430, 43430, 43430, 43430 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33080:
			itemDef.name = "Events cape (attack)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 9926, 1815, 1815, 1815, 1815 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33059:
			itemDef.name = "Events cape (construction)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 6967, 6343, 6343, 6343, 6343 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33061:
			itemDef.name = "Events cape (cooking)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 1819, 49685, 49685, 49685, 49685 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33062:
			itemDef.name = "Events cape (crafting)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 7994, 4516, 4516, 4516, 4516 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33063:
			itemDef.name = "Events cape (defence)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 39367, 10472, 10472, 10472, 10472 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33064:
			itemDef.name = "Events cape (farming)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10698, 19734, 19734, 19734, 19734 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33065:
			itemDef.name = "Events cape (firemaking)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10059, 4922, 4922, 4922, 4922 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33066:
			itemDef.name = "Events cape (fishing)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10047, 36165, 36165, 36165, 36165 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33067:
			itemDef.name = "Events cape (fletching)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10047, 31500, 31500, 31500, 31500 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33068:
			itemDef.name = "Events cape (herblore)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10051, 20889, 20889, 20889, 20889 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33069:
			itemDef.name = "Events cape (hitpoints)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 1836, 8296, 8296, 8296, 8296 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33070:
			itemDef.name = "Events cape (hunter)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 6916, 8477, 8477, 8477, 8477 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33071:
			itemDef.name = "Events cape (magic)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 43556, 6339, 6339, 6339, 6339 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33072:
			itemDef.name = "Events cape (mining)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 34111, 10391, 10391, 10391, 10391 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33073:
			itemDef.name = "Events cape (prayer)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 9927, 2169, 2169, 2169, 2169 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33074:
			itemDef.name = "Events cape (range)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 3626, 20913, 20913, 20913, 20913 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33075:
			itemDef.name = "Events cape (runecrafting)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10047, 10323, 10323, 10323, 10323 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33076:
			itemDef.name = "Events cape (smithing)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 10044, 5412, 5412, 5412, 5412 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33077:
			itemDef.name = "Events cape (strength)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 1819, 30487, 30487, 30487, 30487 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33078:
			itemDef.name = "Events cape (thieveing)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 8, 57636, 57636, 57636, 57636 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33079:
			itemDef.name = "Events cape (woodcutting)";
			itemDef.description = "events cape.";
			itemDef.modifiedModelColors = new int[] { 38333, 127, 107, 115, 90 };
			itemDef.originalModelColors = new int[] { 26007, 6570, 6570, 6570, 6570 };
			itemDef.modelId = 34418;
			itemDef.maleModel = 34271;
			itemDef.femaleModel = 34288;
			itemDef.modelZoom = 1960;
			itemDef.modelRotation1 = 528;
			itemDef.modelRotation2 = 1583;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33101:
			itemDef.name = "Vorkath platebody";
			itemDef.description = "Vorkath armour.";
			itemDef.modelId = 53100;
			itemDef.maleModel = 53099;
			itemDef.femaleModel = 53099;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33102:
			itemDef.name = "Vorkath platelegs";
			itemDef.description = "Vorkath armour.";
			itemDef.modelId = 53102;
			itemDef.maleModel = 53101;
			itemDef.femaleModel = 53101;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33103:
			itemDef.name = "Vorkath boots";
			itemDef.description = "Vorkath armour.";
			itemDef.modelId = 53104;
			itemDef.maleModel = 53103;
			itemDef.femaleModel = 53103;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33104:
			itemDef.name = "Vorkath gloves";
			itemDef.description = "Vorkath armour.";
			itemDef.modelId = 53106;
			itemDef.maleModel = 53105;
			itemDef.femaleModel = 53105;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33105:
			itemDef.name = "Vorkath helmet";
			itemDef.description = "Vorkath armour.";
			itemDef.modelId = 53108;
			itemDef.maleModel = 53107;
			itemDef.femaleModel = 53107;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33106:
			itemDef.name = "Tekton helmet";
			itemDef.description = "Tekton armour.";
			itemDef.modelId = 53118;
			itemDef.maleModel = 53117;
			itemDef.femaleModel = 53117;
			itemDef.modelZoom = 724;
			itemDef.modelRotation1 = 81;
			itemDef.modelRotation2 = 1670;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33107:
			itemDef.name = "Tekton platebody";
			itemDef.description = "Tekton armour.";
			itemDef.modelId = 53110;
			itemDef.maleModel = 53109;
			itemDef.femaleModel = 53109;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33108:
			itemDef.name = "Tekton platelegs";
			itemDef.description = "Tekton armour.";
			itemDef.modelId = 53112;
			itemDef.maleModel = 53111;
			itemDef.femaleModel = 53111;
			itemDef.modelZoom = 1550;
			itemDef.modelRotation1 = 344;
			itemDef.modelRotation2 = 186;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33109:
			itemDef.name = "Tekton gloves";
			itemDef.description = "Tekton armour.";
			itemDef.modelId = 53116;
			itemDef.maleModel = 53115;
			itemDef.femaleModel = 53115;
			itemDef.modelZoom = 830;
			itemDef.modelRotation1 = 536;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33110:
			itemDef.name = "Tekton boots";
			itemDef.description = "Tekton armour.";
			itemDef.modelId = 53114;
			itemDef.maleModel = 53113;
			itemDef.femaleModel = 53113;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33111:
			itemDef.name = "Anti-santa scythe";
			itemDef.description = "Legend says this is the biggest arse scratcher around.";
			itemDef.modelId = 57002;
			itemDef.maleModel = 57001;
			itemDef.femaleModel = 57001;
			itemDef.modelZoom = 3224;
			itemDef.modelRotation1 = 539;
			itemDef.modelRotation2 = 714;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33112:
			itemDef.name = "Dominion staff";
			itemDef.description = "Dominion staff.";
			itemDef.modelId = 59029;
			itemDef.maleModel = 59305;
			itemDef.femaleModel = 59305;
			itemDef.modelZoom = 1872;
			itemDef.modelRotation1 = 288;
			itemDef.modelRotation2 = 1685;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33113:
			itemDef.name = "Dominion sword";
			itemDef.description = "Dominion sword.";
			itemDef.modelId = 59832;
			itemDef.maleModel = 59306;
			itemDef.femaleModel = 59306;
			itemDef.modelZoom = 1829;
			itemDef.modelRotation1 = 513;
			itemDef.modelRotation2 = 546;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33114:
			itemDef.name = "Dominion crossbow";
			itemDef.description = "Dominion crossbow.";
			itemDef.modelId = 59839;
			itemDef.maleModel = 59304;
			itemDef.femaleModel = 59304;
			itemDef.modelZoom = 1490;
			itemDef.modelRotation1 = 362;
			itemDef.modelRotation2 = 791;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33115:
			itemDef.name = "Dragonfire Shield (e)";
			itemDef.description = "unamed shield.";
			itemDef.modelId = 53120;
			itemDef.maleModel = 53119;
			itemDef.femaleModel = 53119;
			itemDef.modelZoom = 2022;
			itemDef.modelRotation1 = 540;
			itemDef.modelRotation2 = 123;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[] { null, "Wear", "Inspect", "Empty", "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33116:
			itemDef.name = "Zilyana's longbow";
			itemDef.description = "A bow belonged to Zilyana.";
			itemDef.modelId = 53122;
			itemDef.maleModel = 53121;
			itemDef.femaleModel = 53121;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 636;
			itemDef.modelRotation2 = 1010;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33117:
			itemDef.name = "Black dragon hunter crossbow";
			itemDef.description = "Black dragon hunter crossbow.";
			itemDef.modelId = 53124;
			itemDef.maleModel = 53123;
			itemDef.femaleModel = 53123;
			itemDef.modelZoom = 1554;
			itemDef.modelRotation1 = 636;
			itemDef.modelRotation2 = 1010;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33118:
			itemDef.name = "Vorkath blowpipe";
			itemDef.description = "Vorkath blowpipe.";
			itemDef.modelId = 53126;
			itemDef.maleModel = 53125;
			itemDef.femaleModel = 53125;
			itemDef.modelZoom = 1158;
			itemDef.modelRotation1 = 768;
			itemDef.modelRotation2 = 189;
			itemDef.modelOffset1 = -7;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33119:
			itemDef.name = "Superior twisted bow";
			itemDef.description = "An upgraded twisted bow.";
			itemDef.modelId = 53128;
			itemDef.maleModel = 53127;
			itemDef.femaleModel = 53127;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.maleOffset = -4;
			itemDef.femaleOffset = -4;
			break;
		case 33123:
			itemDef.name = "Staff of sliske";
			itemDef.description = "Staff of sliske.";
			itemDef.modelId = 59234;
			itemDef.maleModel = 59233;
			itemDef.femaleModel = 59233;
			itemDef.modelZoom = 1872;
			itemDef.modelRotation1 = 288;
			itemDef.modelRotation2 = 1685;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33124:
			itemDef.name = "Twisted crossbow";
			itemDef.description = "Twisted crossbow.";
			itemDef.modelId = 62777;
			itemDef.maleModel = 62776;
			itemDef.femaleModel = 62776;
			itemDef.modelZoom = 926;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 258;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33125:
			itemDef.name = "Present";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = true;
			itemDef.modelId = 2426;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			itemDef.modifiedModelColors = new int[] { 22410, 2999 };
			itemDef.originalModelColors = new int[] { 933, 24410 };
			itemDef.description = "Santa's stolen present";
			break;
		case 33126:
			itemDef.name = "Christmas tree branch";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = true;
			itemDef.modelId = 2412;
			itemDef.modelZoom = 940;
			itemDef.modelRotation1 = 268;
			itemDef.modelRotation2 = 152;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = -21;
			itemDef.modifiedModelColors = new int[] { 11144 };
			itemDef.originalModelColors = new int[] { 6047 };
			itemDef.description = "Enter examine here.";
			break;
		case 33127:
			itemDef.name = "Kbd gloves";
			itemDef.description = "Kbd gloves.";
			itemDef.modifiedModelColors = new int[] { 33085 };
			itemDef.originalModelColors = new int[] { 1060 };
			itemDef.modelId = 53106;
			itemDef.maleModel = 53105;
			itemDef.femaleModel = 53105;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33128:
			itemDef.name = "Kbd boots";
			itemDef.description = "Kbd boots.";
			itemDef.modifiedModelColors = new int[] { 33198, 33202, 33206, 33215, 33210 };
			itemDef.originalModelColors = new int[] { 1060, 1061, 1063, 1064, 1065 };
			itemDef.modelId = 53104;
			itemDef.maleModel = 53103;
			itemDef.femaleModel = 53103;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33129:
			itemDef.name = "Kbd platelegs";
			itemDef.description = "Kbd platelegs.";
			itemDef.modelId = 59994;
			itemDef.maleModel = 59995;
			itemDef.femaleModel = 59995;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33130:
			itemDef.name = "Kbd platebody";
			itemDef.description = "Kbd platebody.";
			itemDef.modelId = 59998;
			itemDef.maleModel = 59999;
			itemDef.femaleModel = 59999;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33131:
			itemDef.name = "Kbd helmet";
			itemDef.description = "Kbd helmet.";
			itemDef.modelId = 59996;
			itemDef.maleModel = 59997;
			itemDef.femaleModel = 59997;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33132:
			itemDef.name = "Kbd cape";
			itemDef.description = "Kbd cape.";
			itemDef.modelId = 59992;
			itemDef.maleModel = 59993;
			itemDef.femaleModel = 59993;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33133:
			itemDef.name = "Anti-imp pet";
			itemDef.description = "Anti-imp pet.";
			itemDef.modelId = 45294;
			itemDef.modelZoom = 1500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33134:
			itemDef.name = "Anti-santa pet";
			itemDef.description = "Anti-santa pet.";
			itemDef.modelId = 29030;
			itemDef.modelZoom = 653;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 1966;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33135:
			itemDef.name = "Bandos mask";
			itemDef.description = "Bandos helmet.";
			itemDef.modelId = 59987;
			itemDef.maleModel = 59991;
			itemDef.femaleModel = 59991;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33136:
			itemDef.name = "Armadyl mask";
			itemDef.description = "Armadyl mask.";
			itemDef.modelId = 59986;
			itemDef.maleModel = 59990;
			itemDef.femaleModel = 59990;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33137:
			itemDef.name = "Zamorak mask";
			itemDef.description = "Zamorak mask.";
			itemDef.modelId = 59985;
			itemDef.maleModel = 59989;
			itemDef.femaleModel = 59989;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33138:
			itemDef.name = "Saradomin mask";
			itemDef.description = "Saradomin mask.";
			itemDef.modelId = 59984;
			itemDef.maleModel = 59988;
			itemDef.femaleModel = 59988;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33139:
			itemDef.name = "Zamarok godbow";
			itemDef.description = "Zamarok godbow.";
			itemDef.modelId = 60553;
			itemDef.maleModel = 60560;
			itemDef.femaleModel = 60560;
			itemDef.modelZoom = 2100;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33140:
			itemDef.name = "Saradomin godbow";
			itemDef.description = "Saradomin godbow.";
			itemDef.modelId = 60555;
			itemDef.maleModel = 60554;
			itemDef.femaleModel = 60554;
			itemDef.modelZoom = 2100;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33141:
			itemDef.name = "Bandos godbow";
			itemDef.description = "Bandos godbow.";
			itemDef.modelId = 60559;
			itemDef.maleModel = 60558;
			itemDef.femaleModel = 60558;
			itemDef.modelZoom = 2100;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33142:
			itemDef.name = "Fire cape (purple)";
			itemDef.description = "Fire cape (purple).";
			itemDef.modelId = 9631;
			itemDef.maleModel = 9638;
			itemDef.femaleModel = 9640;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33148:
			itemDef.name = "Fire cape (cyan)";
			itemDef.description = "Fire cape (cyan).";
			itemDef.modelId = 9631;
			itemDef.maleModel = 9638;
			itemDef.femaleModel = 9640;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33144:
			itemDef.name = "Fire cape (green)";
			itemDef.description = "Fire cape (green).";
			itemDef.modelId = 9631;
			itemDef.maleModel = 9638;
			itemDef.femaleModel = 9640;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33145:
			itemDef.name = "Fire cape (red)";
			itemDef.description = "Fire cape (red).";
			itemDef.modelId = 9631;
			itemDef.maleModel = 9638;
			itemDef.femaleModel = 9640;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33143:
			itemDef.name = "Infernal cape (blue)";
			itemDef.description = "Infernal cape (blue).";
			itemDef.modifiedModelColors = new int[] { 5056, 5066, 924, 3005 };
			itemDef.originalModelColors = new int[] { 39851, 39851, 39851, 39851 };
			itemDef.modelId = 33144;
			itemDef.maleModel = 33103;
			itemDef.femaleModel = 33111;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33146:
			itemDef.name = "Infernal cape (green)";
			itemDef.description = "Infernal cape (green).";
			itemDef.modifiedModelColors = new int[] { 5056, 5066, 924, 3005 };
			itemDef.originalModelColors = new int[] { 21167, 21167, 21167, 21167 };
			itemDef.modelId = 33144;
			itemDef.maleModel = 33103;
			itemDef.femaleModel = 33111;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33147:
			itemDef.name = "Infernal cape (purple)";
			itemDef.description = "Infernal cape (purple).";
			itemDef.modifiedModelColors = new int[] { 5056, 5066, 924, 3005 };
			itemDef.originalModelColors = new int[] { 53160, 53160, 53160, 53160 };
			itemDef.modelId = 33144;
			itemDef.maleModel = 33103;
			itemDef.femaleModel = 33111;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33150:
			itemDef.name = "Infernal key piece 1";
			itemDef.description = "Infernal key piece 1.";
			itemDef.modelId = 61001;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 534;
			itemDef.modelRotation2 = 222;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Combine";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33151:
			itemDef.name = "Infernal key piece 2";
			itemDef.description = "Infernal key piece 2.";
			itemDef.modelId = 61002;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 534;
			itemDef.modelRotation2 = 222;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Combine";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33152:
			itemDef.name = "Infernal key piece 3";
			itemDef.description = "Infernal key piece 3.";
			itemDef.modelId = 61003;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 534;
			itemDef.modelRotation2 = 222;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Combine";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33153:
			itemDef.name = "Infernal key";
			itemDef.description = "Infernal key.";
			itemDef.modelId = 61111;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 534;
			itemDef.modelRotation2 = 222;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33154:
			itemDef.name = "Infernal mystery box";
			itemDef.description = "Infernal mystery box.";
			itemDef.modelId = 61110;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33155:
			itemDef.name = "Ethereal sword (red)";
			itemDef.description = "Ethereal sword (red).";
			itemDef.modelId = 61005;
			itemDef.maleModel = 61004;
			itemDef.femaleModel = 61004;
			itemDef.modelZoom = 1645;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33156:
			itemDef.name = "Ethereal sword (blue)";
			itemDef.description = "Ethereal sword (blue).";
			itemDef.modelId = 61006;
			itemDef.maleModel = 61007;
			itemDef.femaleModel = 61007;
			itemDef.modelZoom = 1645;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33157:
			itemDef.name = "Ethereal sword (green)";
			itemDef.description = "Ethereal sword (green).";
			itemDef.modelId = 61008;
			itemDef.maleModel = 61009;
			itemDef.femaleModel = 61009;
			itemDef.modelZoom = 1645;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33158:
			itemDef.name = "Dagon' hai top";
			itemDef.description = "An elite dark mages robes.";
			itemDef.modelId = 60317;
			itemDef.maleModel = 43614;
			itemDef.femaleModel = 43689;
			itemDef.anInt188 = 44594;
			itemDef.anInt164 = 43681;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 536;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33159:
			itemDef.name = "Dagon' hai hat";
			itemDef.description = "An elite dark mages hat.";
			itemDef.modelId = 60319;
			itemDef.maleModel = 60318;
			itemDef.femaleModel = 60318;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.modelZoom = 1373;
			itemDef.modelRotation1 = 98;
			itemDef.modelRotation2 = 1988;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33160:
			itemDef.name = "Dagon' hai robe";
			itemDef.description = "An elite dark mages robe.";
			itemDef.modelId = 60321;
			itemDef.maleModel = 60320;
			itemDef.femaleModel = 60320;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.modelZoom = 2216;
			itemDef.modelRotation1 = 572;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 14;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33161:
			itemDef.name = "Blue infernal cape mix";
			itemDef.description = "Changes the color of the Infernal Cape to Blue.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33162:
			itemDef.name = "Green infernal cape mix";
			itemDef.description = "Changes the color of the Infernal Cape to Green.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33163:
			itemDef.name = "Purple infernal cape mix";
			itemDef.description = "Changes the color of the Infernal Cape to Purple.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33164:
			itemDef.name = "Purple firecape mix";
			itemDef.description = "Changes the color of the firecape to purple.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33165:
			itemDef.name = "Cyan firecape mix";
			itemDef.description = "Changes the color of the firecape to cyan.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33166:
			itemDef.name = "Green firecape mix";
			itemDef.description = "Changes the color of the firecape to green.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33167:
			itemDef.name = "Red firecape mix";
			itemDef.description = "Changes the color of the firecape to red.";
			itemDef.modelId = 8956;
			itemDef.modelZoom = 842;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Use";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33169:
			itemDef.name = "K'ril robe top";
			itemDef.description = "A top worn by magic-using followers of Zamorak.";
			itemDef.modelId = 62558;
			itemDef.maleModel = 62559;
			itemDef.femaleModel = 62559;
			itemDef.modelZoom = 1358;
			itemDef.modelRotation1 = 514;
			itemDef.modelRotation2 = 2041;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33170:
			itemDef.name = "K'ril robe bottom";
			itemDef.description = "A robe worn by magic-using followers of Zamorak.";
			itemDef.modelId = 62553;
			itemDef.maleModel = 62554;
			itemDef.femaleModel = 62554;
			itemDef.modelZoom = 1690;
			itemDef.modelRotation1 = 435;
			itemDef.modelRotation2 = 9;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33171:
			itemDef.name = "K'ril hat";
			itemDef.description = "A hat worn by magic-using followers of Zamorak.";
			itemDef.modelId = 62551;
			itemDef.maleModel = 62552;
			itemDef.femaleModel = 62552;
			itemDef.modelZoom = 1236;
			itemDef.modelRotation1 = 118;
			itemDef.modelRotation2 = 10;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33172:
			itemDef.name = "K'ril swords";
			itemDef.description = "Sheath & Unsheath this sword in the Equipment tab. Hits an enemy twice.";
			itemDef.equipActions[2] = "Sheath";
			itemDef.modelId = 62556;
			itemDef.maleModel = 62557;
			itemDef.femaleModel = 62557;
			itemDef.modelZoom = 1650;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1300;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33173:
			itemDef.name = "K'ril swords (sheathed)";
			itemDef.description = "Sheath & Unsheath this sword in the Equipment tab. Hits an enemy twice.";
			itemDef.equipActions[2] = "Unsheath";
			itemDef.modelId = 62556;
			itemDef.maleModel = 62556;
			itemDef.femaleModel = 62556;
			itemDef.modelZoom = 1650;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 1300;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33174:
			itemDef.name = "Pet demonic gorilla";
			itemDef.description = "none.";
			itemDef.modelId = 31241;
			itemDef.modelZoom = 16000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33175:
			itemDef.name = "Pet crawling hand";
			itemDef.description = "none.";
			itemDef.modelId = 5071;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33176:
			itemDef.name = "Pet cave bug";
			itemDef.description = "none.";
			itemDef.modelId = 23854;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33177:
			itemDef.name = "Pet cave crawler";
			itemDef.description = "none.";
			itemDef.modelId = 5066;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33178:
			itemDef.name = "Pet banshee";
			itemDef.description = "none.";
			itemDef.modelId = 5063;
			itemDef.modelZoom = 3200;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33179:
			itemDef.name = "Pet cave slime";
			itemDef.description = "none.";
			itemDef.modelId = 5786;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33180:
			itemDef.name = "Pet rockslug";
			itemDef.description = "none.";
			itemDef.modelId = 5084;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33181:
			itemDef.name = "Pet cockatrice";
			itemDef.description = "none.";
			itemDef.modelId = 5070;
			itemDef.modelZoom = 3200;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33182:
			itemDef.name = "Pet pyrefiend";
			itemDef.description = "none.";
			itemDef.modelId = 5083;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33183:
			itemDef.name = "Pet basilisk";
			itemDef.description = "none.";
			itemDef.modelId = 5064;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33184:
			itemDef.name = "Pet infernal mage";
			itemDef.modifiedModelColors = new int[] { -26527, -24618, -25152, -25491, 119 };
			itemDef.originalModelColors = new int[] { 924, 148, 0, 924, 924 };
			itemDef.description = "none.";
			itemDef.modelId = 5047;
			itemDef.modelZoom = 3940;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 84;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33185:
			itemDef.name = "Pet bloodveld";
			itemDef.description = "none.";
			itemDef.modelId = 5065;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33186:
			itemDef.name = "Pet jelly";
			itemDef.description = "none.";
			itemDef.modelId = 5081;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33187:
			itemDef.name = "Pet turoth";
			itemDef.description = "none.";
			itemDef.modelId = 5086;
			itemDef.modelZoom = 2600;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33188:
			itemDef.name = "Pet aberrant spectre";
			itemDef.description = "none.";
			itemDef.modelId = 5085;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 450;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33189:
			itemDef.name = "Pet dust devil";
			itemDef.description = "none.";
			itemDef.modelId = 5076;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33190:
			itemDef.name = "Pet kurask";
			itemDef.description = "none.";
			itemDef.modelId = 5082;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33191:
			itemDef.name = "Pet skeletal wyvern";
			itemDef.description = "none.";
			itemDef.modelId = 10350;
			itemDef.modelZoom = 1104;
			itemDef.modelRotation1 = 27;
			itemDef.modelRotation2 = 1634;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33192:
			itemDef.name = "Pet garygoyle";
			itemDef.description = "none.";
			itemDef.modelId = 5078;
			itemDef.modelZoom = 4000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33193:
			itemDef.name = "Pet nechryael";
			itemDef.description = "none.";
			itemDef.modelId = 5074;
			itemDef.modelZoom = 4000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33194:
			itemDef.name = "Pet abyssal demon";
			itemDef.description = "none.";
			itemDef.modelId = 5062;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33195:
			itemDef.name = "Pet dark beast";
			itemDef.description = "none.";
			itemDef.modelId = 26395;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33196:
			itemDef.name = "Pet night beast";
			itemDef.description = "none.";
			itemDef.modelId = 32933;
			itemDef.modelZoom = 7000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33197:
			itemDef.name = "Pet greater abyssal demon";
			itemDef.description = "none.";
			itemDef.modelId = 32921;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33198:
			itemDef.name = "Pet crushing hand";
			itemDef.description = "none.";
			itemDef.modelId = 32922;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33199:
			itemDef.name = "Pet chasm crawler";
			itemDef.description = "none.";
			itemDef.modelId = 32918;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33200:
			itemDef.name = "Pet screaming banshee";
			itemDef.description = "none.";
			itemDef.modelId = 32823;
			itemDef.modelZoom = 5500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33201:
			itemDef.name = "Pet twisted banshee";
			itemDef.description = "none.";
			itemDef.modelId = 32847;
			itemDef.modelZoom = 5500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33202:
			itemDef.name = "Pet giant rockslug";
			itemDef.description = "none.";
			itemDef.modelId = 32919;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33203:
			itemDef.name = "Pet cockathrice";
			itemDef.description = "none.";
			itemDef.modelId = 32920;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33204:
			itemDef.name = "Pet flaming pyrelord";
			itemDef.description = "none.";
			itemDef.modelId = 32923;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33205:
			itemDef.name = "Pet monstrous basilisk";
			itemDef.description = "none.";
			itemDef.modelId = 32924;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33206:
			itemDef.name = "Pet malevolent mage";
			itemDef.description = "none.";
			itemDef.modelId = 32929;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33207:
			itemDef.name = "Pet insatiable bloodveld";
			itemDef.description = "none.";
			itemDef.modelId = 32926;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33208:
			itemDef.name = "Pet insatiable mutated bloodveld";
			itemDef.description = "none.";
			itemDef.modelId = 32925;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33209:
			itemDef.name = "Pet vitreous jelly";
			itemDef.description = "none.";
			itemDef.modelId = 32852;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33210:
			itemDef.name = "Pet vitreous warped jelly";
			itemDef.description = "none.";
			itemDef.modelId = 32917;
			itemDef.modelZoom = 6000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33211:
			itemDef.name = "Pet cave abomination";
			itemDef.description = "none.";
			itemDef.modelId = 32935;
			itemDef.modelZoom = 5500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33212:
			itemDef.name = "Pet abhorrent spectre";
			itemDef.description = "none.";
			itemDef.modelId = 32930;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33213:
			itemDef.name = "pet repugnant spectre";
			itemDef.description = "none.";
			itemDef.modelId = 32926;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33214:
			itemDef.name = "Pet choke devil";
			itemDef.description = "none.";
			itemDef.modelId = 32927;
			itemDef.modelZoom = 6000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33215:
			itemDef.name = "Pet king kurask";
			itemDef.description = "none.";
			itemDef.modelId = 32934;
			itemDef.modelZoom = 8000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33217:
			itemDef.name = "Pet nuclear smoke devil";
			itemDef.description = "none.";
			itemDef.modelId = 32928;
			itemDef.modelZoom = 5500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33218:
			itemDef.name = "Pet marble gargoyle";
			itemDef.description = "none.";
			itemDef.modelId = 34251;
			itemDef.modelZoom = 8000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33219:
			itemDef.name = "Pet nechryarch";
			itemDef.description = "none.";
			itemDef.modelId = 32932;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33220:
			itemDef.name = "Pet Patrity";
			itemDef.description = "none.";
			itemDef.modelId = 32035;
			itemDef.modelZoom = 653;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1535;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33221:
			itemDef.name = "Pet xarpus";
			itemDef.description = "none.";
			itemDef.modelId = 35383;
			itemDef.modelZoom = 14000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33222:
			itemDef.name = "Pet nyclocas vasilias";
			itemDef.description = "none.";
			itemDef.modelId = 35182;
			itemDef.modelZoom = 12000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33223:
			itemDef.name = "Pet pestilent bloat";
			itemDef.description = "none.";
			itemDef.modelId = 35404;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33224:
			itemDef.name = "Pet maiden of sugadinti";
			itemDef.description = "none.";
			itemDef.modelId = 35385;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33225:
			itemDef.name = "Pet lizardman shaman";
			itemDef.description = "none.";
			itemDef.modelId = 4039;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33226:
			itemDef.name = "Pet abyssal sire";
			itemDef.description = "none.";
			itemDef.modelId = 29477;
			itemDef.modelZoom = 9000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33227:
			itemDef.name = "Pet black demon";
			itemDef.description = "none.";
			itemDef.modelId = 31984;
			itemDef.modelZoom = 1104;
			itemDef.modelRotation1 = 144;
			itemDef.modelRotation2 = 1826;
			itemDef.modelOffset1 = 7;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 11802:
		case 11804:
		case 11806:
		case 11808:
			itemDef.equipActions[2] = "Sheath";
			break;// godsword sheathing operating

		case 33228:
			itemDef.name = "Pet greater demon";
			itemDef.description = "none.";
			itemDef.modelId = 32015;
			itemDef.modelZoom = 902;
			itemDef.modelRotation1 = 216;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = -16;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33229:
			itemDef.name = "Armadyl godsword (sheathed)";
			itemDef.description = "Armadyl godsword (sheathed)";
			itemDef.equipActions[2] = "Unsheath";
			itemDef.modelId = 28075;
			itemDef.maleModel = 62683;
			itemDef.femaleModel = 62683;
			itemDef.modelZoom = 1957;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 494;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33230:
			itemDef.name = "Bandos godsword (sheathed)";
			itemDef.description = "Bandos godsword (sheathed)";
			itemDef.equipActions[2] = "Unsheath";
			itemDef.modelId = 28059;
			itemDef.maleModel = 62684;
			itemDef.femaleModel = 62684;
			itemDef.modelZoom = 1957;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 494;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33231:
			itemDef.name = "Saradomin godsword (sheathed)";
			itemDef.description = "Saradomin godsword (sheathed)";
			itemDef.equipActions[2] = "Unsheath";
			itemDef.modelId = 28070;
			itemDef.maleModel = 62685;
			itemDef.femaleModel = 62685;
			itemDef.modelZoom = 1957;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 494;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33232:
			itemDef.name = "Zamorak godsword (sheathed)";
			itemDef.description = "Zamorak godsword (sheathed)";
			itemDef.equipActions[2] = "Unsheath";
			itemDef.modelId = 28060;
			itemDef.maleModel = 62686;
			itemDef.femaleModel = 62686;
			itemDef.modelZoom = 1957;
			itemDef.modelRotation1 = 498;
			itemDef.modelRotation2 = 494;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33233:
			itemDef.name = "Pet revenant imp";
			itemDef.description = "none.";
			itemDef.modelId = 34156;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33234:
			itemDef.name = "Pet revenant goblin";
			itemDef.description = "none.";
			itemDef.modelId = 34262;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33235:
			itemDef.name = "Pet revenant pyrefiend";
			itemDef.description = "none.";
			itemDef.modelId = 34142;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33236:
			itemDef.name = "Pet revenant hobgoblin";
			itemDef.description = "none.";
			itemDef.modelId = 34157;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33237:
			itemDef.name = "Pet revenant cyclops";
			itemDef.description = "none.";
			itemDef.modelId = 34155;
			itemDef.modelZoom = 4500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33238:
			itemDef.name = "Pet revenant hellhound";
			itemDef.description = "none.";
			itemDef.modelId = 34143;
			itemDef.modelZoom = 3500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33239:
			itemDef.name = "Pet revenant demon";
			itemDef.description = "none.";
			itemDef.modelId = 32015;
			itemDef.modifiedModelColors = new int[] { 1690, 910, 912, 1814, 1938 };
			itemDef.originalModelColors = new int[] { 43078, 43078, 43078, 43078, 43078, 43078 };
			itemDef.modelZoom = 902;
			itemDef.modelRotation1 = 216;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = -16;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33240:
			itemDef.name = "Pet revenant ork";
			itemDef.description = "none.";
			itemDef.modelId = 34154;
			itemDef.modelZoom = 3500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33242:
			itemDef.name = "Pet revenant dark beast";
			itemDef.description = "none.";
			itemDef.modelId = 34158;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33243:
			itemDef.name = "Pet revenant knight";
			itemDef.description = "none.";
			itemDef.modelId = 34145;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33244:
			itemDef.name = "Pet revenant dragon";
			itemDef.description = "none.";
			itemDef.modelId = 34163;
			itemDef.modelZoom = 7500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33245:
			itemDef.name = "Pet glob";
			itemDef.description = "none.";
			itemDef.modelId = 26074;
			itemDef.modelZoom = 10000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33246:
			itemDef.name = "Pet ice queen";
			itemDef.description = "none.";
			itemDef.modelId = 104;
			itemDef.modifiedModelColors = new int[] { 41, 61, 4550, 12224, 25238, 6798 };
			itemDef.originalModelColors = new int[] { -22052, -26150, -24343, -22052, -22052, -23327 };
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33247:
			itemDef.name = "Pet enraged tarn";
			itemDef.description = "none.";
			itemDef.modelId = 60322;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33248:
			itemDef.name = "Pet jaltok-jad";
			itemDef.description = "none.";
			itemDef.modelId = 33012;
			itemDef.modelZoom = 12000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33249:
			itemDef.name = "Pet rune dragon";
			itemDef.description = "none.";
			itemDef.modelId = 34668;
			itemDef.modelZoom = 2541;
			itemDef.modelRotation1 = 83;
			itemDef.modelRotation2 = 1826;
			itemDef.modelOffset1 = -97;
			itemDef.modelOffset2 = 9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33271:
			itemDef.name = "Moo";
			itemDef.description = "cow goes moo.";
			itemDef.modelId = 23889;
			itemDef.modelZoom = 2541;
			itemDef.modelRotation1 = 83;
			itemDef.modelRotation2 = 1826;
			itemDef.modelOffset1 = -97;
			itemDef.modelOffset2 = 9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33250:
			itemDef.name = "Swift gloves (Black)";
			itemDef.description = "Watch my speedy hands!";
			itemDef.modelId = 62655;
			itemDef.maleModel = 62657;
			itemDef.femaleModel = 62658;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33251:
			itemDef.name = "Swift gloves (Red)";
			itemDef.description = "Watch my speedy hands!";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 65046, 65051, 65056 };
			itemDef.modelId = 62655;
			itemDef.maleModel = 62659;
			itemDef.femaleModel = 62660;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33252:
			itemDef.name = "Swift gloves (White)";
			itemDef.description = "Watch my speedy hands!";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 64585, 64590, 64595 };
			itemDef.modelId = 62655;
			itemDef.maleModel = 62661;
			itemDef.femaleModel = 62662;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33253:
			itemDef.name = "Swift gloves (Yellow)";
			itemDef.description = "Watch my speedy hands!";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 9767, 9772, 9777 };
			itemDef.modelId = 62655;
			itemDef.maleModel = 62663;
			itemDef.femaleModel = 62664;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33254:
			itemDef.name = "Spellcaster gloves (Black)";
			itemDef.description = "	Some pretty fantastical gloves.";
			itemDef.modelId = 62656;
			itemDef.maleModel = 62665;
			itemDef.femaleModel = 62666;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33255:
			itemDef.name = "Spellcaster gloves (Red)";
			itemDef.description = "	Some pretty fantastical gloves.";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 65046, 65051, 65056 };
			itemDef.modelId = 62656;
			itemDef.maleModel = 62667;
			itemDef.femaleModel = 62668;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33256:
			itemDef.name = "Spellcaster gloves (White)";
			itemDef.description = "	Some pretty fantastical gloves.";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 64585, 64590, 64595 };
			itemDef.modelId = 62656;
			itemDef.maleModel = 62669;
			itemDef.femaleModel = 62670;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33257:
			itemDef.name = "Spellcaster gloves (Yellow)";
			itemDef.description = "	Some pretty fantastical gloves.";
			itemDef.modifiedModelColors = new int[] { 10, 15, 20 };
			itemDef.originalModelColors = new int[] { 9767, 9772, 9777 };
			itemDef.modelId = 62656;
			itemDef.maleModel = 62671;
			itemDef.femaleModel = 62672;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 512;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33258:
			itemDef.name = "Tekton longsword";
			itemDef.description = "Tekton longsword.";
			itemDef.modelId = 62682;
			itemDef.maleModel = 62681;
			itemDef.femaleModel = 62681;
			itemDef.modelZoom = 1445;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33259:
			itemDef.name = "Pet wyrm";
			itemDef.description = "none.";
			itemDef.modelId = 36922;
			itemDef.modelZoom = 2547;
			itemDef.modelRotation1 = 97;
			itemDef.modelRotation2 = 1820;
			itemDef.modelOffset1 = -7;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33260:
			itemDef.name = "Pet drake";
			itemDef.description = "none.";
			itemDef.modelId = 36160;
			itemDef.modelZoom = 6500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33261:
			itemDef.name = "Pet wyrm";
			itemDef.description = "none.";
			itemDef.modelId = 36922;
			itemDef.modelZoom = 2547;
			itemDef.modelRotation1 = 97;
			itemDef.modelRotation2 = 1820;
			itemDef.modelOffset1 = -7;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33262:
			itemDef.name = "Valentines Balloon";
			itemDef.description = "none.";
			itemDef.modelId = 62766;
			itemDef.maleModel = 62767;
			itemDef.femaleModel = 62767;
			itemDef.modelZoom = 2200;
			itemDef.modelRotation1 = 270;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33263:
			itemDef.name = "Cupid bow";
			itemDef.description = "none.";
			itemDef.modelId = 62768;
			itemDef.maleModel = 62769;
			itemDef.femaleModel = 62769;
			itemDef.modelZoom = 1072;
			itemDef.modelRotation1 = 127;
			itemDef.modelRotation2 = 103;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33264:
			itemDef.name = "Halo and horns";
			itemDef.description = "none.";
			itemDef.modelId = 62771;
			itemDef.maleModel = 62770;
			itemDef.femaleModel = 62770;
			itemDef.modelZoom = 550;
			itemDef.modelRotation1 = 228;
			itemDef.modelRotation2 = 141;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33265:
			itemDef.name = "Heart";
			itemDef.description = "none.";
			itemDef.modelId = 62782;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33266:
			itemDef.name = "Valentines mystery box";
			itemDef.description = "You make me hard.";
			itemDef.modelId = 62773;
			itemDef.modelZoom = 464;
			itemDef.modelRotation1 = 423;
			itemDef.modelRotation2 = 1928;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33267:
			itemDef.name = "Staff of adoration";
			itemDef.description = "none.";
			itemDef.modelId = 62774;
			itemDef.maleModel = 62775;
			itemDef.femaleModel = 62775;
			itemDef.modelZoom = 1579;
			itemDef.modelRotation1 = 660;
			itemDef.modelRotation2 = 48;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33268:
			itemDef.name = "Valentines crossbow";
			itemDef.description = "none.";
			itemDef.modelId = 62778;
			itemDef.maleModel = 62779;
			itemDef.femaleModel = 62779;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 258;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33269:
			itemDef.name = "Valius Mystery Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 2426;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			itemDef.modifiedModelColors = new int[] { 22410, 2999 };
			itemDef.originalModelColors = new int[] { 2130, 38693 };
			itemDef.description = "Chances at several unqiue items found only in this box! (ex: Tekton Armor)";
			break;
		case 33270:
			itemDef.name = "Dragon Hunter Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 2426;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			itemDef.modifiedModelColors = new int[] { 22410, 2999 };
			itemDef.originalModelColors = new int[] { 926, 1050 };
			itemDef.description = "Chances for items that give bonuses toward dragons. (ex: Dragonhunter Lance)";
			break;
		case 33273:
			itemDef.name = "Ancient sword";
			itemDef.description = "none.";
			itemDef.modelId = 60201;
			itemDef.maleModel = 60200;
			itemDef.femaleModel = 60200;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33274:
			itemDef.name = "Armadyl staff";
			itemDef.description = "none.";
			itemDef.modelId = 60203;
			itemDef.maleModel = 60202;
			itemDef.femaleModel = 60202;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33275:
			itemDef.name = "Bork axe";
			itemDef.description = "none.";
			itemDef.modelId = 60205;
			itemDef.maleModel = 60204;
			itemDef.femaleModel = 60204;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33276:
			itemDef.name = "Bree bow";
			itemDef.description = "none.";
			itemDef.modelId = 60207;
			itemDef.maleModel = 60206;
			itemDef.femaleModel = 60206;
			itemDef.modelZoom = 1700;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33277:
			itemDef.name = "Infernal staff";
			itemDef.description = "none.";
			itemDef.modelId = 60209;
			itemDef.maleModel = 60208;
			itemDef.femaleModel = 60208;
			itemDef.modelZoom = 2200;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33278:
			itemDef.name = "Infernal longsword";
			itemDef.description = "none.";
			itemDef.modelId = 60211;
			itemDef.maleModel = 60210;
			itemDef.femaleModel = 60210;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33279:
			itemDef.name = "Necrolord staff";
			itemDef.description = "none.";
			itemDef.modelId = 60213;
			itemDef.maleModel = 60212;
			itemDef.femaleModel = 60212;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33280:
			itemDef.name = "Insert name here";
			itemDef.description = "none.";
			itemDef.modelId = 60215;
			itemDef.maleModel = 60214;
			itemDef.femaleModel = 60214;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33281:
			itemDef.name = "Infernal bow";
			itemDef.description = "none.";
			itemDef.modelId = 60219;
			itemDef.maleModel = 60218;
			itemDef.femaleModel = 60218;
			itemDef.modelZoom = 3334;
			itemDef.modelRotation1 = 533;
			itemDef.modelRotation2 = 1294;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33282:
			itemDef.name = "Infernal warhammer";
			itemDef.description = "none.";
			itemDef.modelId = 60221;
			itemDef.maleModel = 60220;
			itemDef.femaleModel = 60220;
			itemDef.modelZoom = 2512;
			itemDef.modelRotation1 = 317;
			itemDef.modelRotation2 = 1988;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = 45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33283:
			itemDef.name = "Imbued Porazdir's heart";
			itemDef.modelId = 32298;
			itemDef.modelZoom = 1168;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 1690;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 60826, 59796, 54544, 58904, 54561 };
			itemDef.originalModelColors = new int[] { 13263, 13014, 13243, 13000, 13275 };
			itemDef.inventoryOptions = new String[] { "Invigorate", null, null, null, "Drop" };
			itemDef.description = "Boosts your strength for a period of time.";
			// itemDef.aByte205 = 3;
			break;

		case 33284:
			itemDef.name = "Imbued Justiciar's heart";
			itemDef.modelId = 32298;
			itemDef.modelZoom = 1168;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 1690;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 60826, 59796, 54544, 58904, 54561 };
			itemDef.originalModelColors = new int[] { 31661, 31418, 31661, 31167, 31445 };
			itemDef.inventoryOptions = new String[] { "Invigorate", null, null, null, "Drop" };
			itemDef.description = "Boosts your Defence for a period of time.";
			// itemDef.aByte205 = 3;
			break;

		case 32285:
			itemDef.name = "Imbued Derwen's heart";
			itemDef.modelId = 32298;
			itemDef.modelZoom = 1168;
			itemDef.modelRotation1 = 96;
			itemDef.modelRotation2 = 1690;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 60826, 59796, 54544, 58904, 54561 };
			itemDef.originalModelColors = new int[] { 926, 930, 936, 940, 950 };
			itemDef.inventoryOptions = new String[] { "Invigorate", null, null, null, "Drop" };
			itemDef.description = "Boosts your Attack for a period of time.";
			// itemDef.aByte205 = 3;
			break;

		case 32286:
			itemDef.name = "Bronze fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 5652, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32287:
			itemDef.name = "Iron fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 33, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32288:
			itemDef.name = "Steel fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 61, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32289:
			itemDef.name = "Black fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 0, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32290:
			itemDef.name = "Mithril fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 43297, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32291:
			itemDef.name = "Adamant fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 21662, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32292:
			itemDef.name = "Rune fishing rod";
			itemDef.description = "Used for Bait and Fly fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 36133, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32293:
			itemDef.name = "Dragon fishing rod";
			itemDef.description = "Used for Bait and Fly fishing. Can also be used for Deep sea fishing.";
			itemDef.modelId = 36128;
			itemDef.maleModel = 36317;
			itemDef.femaleModel = 36312;
			itemDef.modelZoom = 1853;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 27;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -1;
			itemDef.modifiedModelColors = new int[] { 59499, 24 };
			itemDef.originalModelColors = new int[] { 924, 9152 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32294:
			itemDef.name = "Raw eel";
			itemDef.description = "Slimy";
			itemDef.modelId = 6856;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 296;
			itemDef.modelRotation2 = 352;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = 42;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32295:
			itemDef.name = "Burnt eel";
			itemDef.description = "none.";
			itemDef.modelId = 6856;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 296;
			itemDef.modelRotation2 = 352;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = 42;
			itemDef.modifiedModelColors = new int[] { 8485, 14622, 12589 };
			itemDef.originalModelColors = new int[] { 8724, 3226, 9754 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32296:
			itemDef.name = "Eel";
			itemDef.inventoryOptions = new String[] { "Eat", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 6856;
			itemDef.modelZoom = 1440;
			itemDef.modelRotation1 = 296;
			itemDef.modelRotation2 = 352;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = 42;
			itemDef.modifiedModelColors = new int[] { 8485, 14622, 8386, 12589 };
			itemDef.originalModelColors = new int[] { 8088, 6032, 57, 2960 };
			itemDef.description = "None";
			break;

		case 32297:
			itemDef.name = "Raw baron shark";
			itemDef.description = "none.";
			itemDef.modelId = 2594;
			itemDef.modelZoom = 1860;
			itemDef.modelRotation1 = 344;
			itemDef.modelRotation2 = 12;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 12;
			itemDef.modifiedModelColors = new int[] { 103, 103 };
			itemDef.originalModelColors = new int[] { 7756, 5349 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32298:
			itemDef.name = "Burnt baron shark";
			itemDef.description = "none.";
			itemDef.modelId = 2594;
			itemDef.modelZoom = 1860;
			itemDef.modelRotation1 = 344;
			itemDef.modelRotation2 = 12;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 12;
			itemDef.modifiedModelColors = new int[] { 61, 103 };
			itemDef.originalModelColors = new int[] { 28, 41 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32299:
			itemDef.name = "Baron shark";
			itemDef.inventoryOptions = new String[] { "Eat", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 2594;
			itemDef.modelZoom = 1860;
			itemDef.modelRotation1 = 344;
			itemDef.modelRotation2 = 12;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 12;
			itemDef.modifiedModelColors = new int[] { 61, 103 };
			itemDef.originalModelColors = new int[] { 8109, 4795 };
			itemDef.description = "None";
			break;

		case 32300:
			itemDef.name = "Raw cavefish";
			itemDef.description = "none.";
			itemDef.modelId = 60223;
			itemDef.modelZoom = 1284;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1907;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32301:
			itemDef.name = "Burnt cavefish";
			itemDef.description = "none.";
			itemDef.modelId = 60227;
			itemDef.modelZoom = 1284;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1907;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 32302:
			itemDef.name = "Cavefish";
			itemDef.inventoryOptions = new String[] { "Eat", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 60228;
			itemDef.modelZoom = 1284;
			itemDef.modelRotation1 = 499;
			itemDef.modelRotation2 = 1907;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 4;
			itemDef.description = "None";
			break;

		case 32303:
			itemDef.name = "Dragonfire visage (e)";
			itemDef.description = "none.";
			itemDef.modelId = 26456;
			itemDef.modelZoom = 1697;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 152;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = -5;
			itemDef.modifiedModelColors = new int[] { 45, 41, 33, 24, 20, 57, 22, 37 };
			itemDef.originalModelColors = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33285:
			itemDef.name = "Easter Mystery Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 61110;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			// itemDef.modifiedModelColors = new int[] {22410, 2999};
			// itemDef.originalModelColors = new int[] {35321, 350};
			itemDef.description = "Chances for all sorts of Easter Items!";
			break;

		case 33286:
			itemDef.name = "Easter Cape";
			itemDef.inventoryOptions = new String[] { null, "wear", null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 9631;
			itemDef.maleModel = 9638;
			itemDef.femaleModel = 9640;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "An  Easter Cape.";
			break;

		case 33287:
			itemDef.name = "Pet Easter Bunny";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 37239;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "You've captured the Easter bunny!";
			break;

		case 33288:
			itemDef.name = "Pet Choco";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 37239;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "mmm... a chocolate bunny";
			itemDef.modifiedModelColors = new int[] { 2378 };
			itemDef.originalModelColors = new int[] { 7079 };
			break;

		case 33289:
			itemDef.name = "Pet Milkie";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 37239;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "mmm... a chocolate bunny";
			itemDef.modifiedModelColors = new int[] { 2378 };
			itemDef.originalModelColors = new int[] { 6040 };
			break;

		case 33290:
			itemDef.name = "Pet Goldie";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 37239;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "oh wow... a rare golden bunny!";
			itemDef.modifiedModelColors = new int[] { 2378 };
			itemDef.originalModelColors = new int[] { 9152 };
			break;

		case 33291:
			itemDef.name = "Pet Blue";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 37239;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "A blue bunny... kinda looks like the easter bunny!";
			itemDef.modifiedModelColors = new int[] { 2378 };
			itemDef.originalModelColors = new int[] { 35321 };
			break;

		case 33292:
			itemDef.name = "Crazed bunny";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 23901;
			itemDef.modelZoom = 500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "What a bloody mess...";
			itemDef.modifiedModelColors = new int[] { 5413, 5417, 5421 };
			itemDef.originalModelColors = new int[] { 935, 111, 127 };
			break;

		case 33293:
			itemDef.name = "Peter Rabbit";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 28602;
			itemDef.modelZoom = 500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1931;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "Hi Peter!";
			break;

		case 33294:
			itemDef.name = "Chocolate Chicken";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 35150;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1731;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "a chocolate chicken";
			itemDef.modifiedModelColors = new int[] { 947 };
			itemDef.originalModelColors = new int[] { 8128 };
			break;

		case 33295:
			itemDef.name = "Chocolate Chicken";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 35150;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1731;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "a chocolate chicken";
			itemDef.modifiedModelColors = new int[] { 947, 8301 };
			itemDef.originalModelColors = new int[] { 8128, 25511 };
			break;

		case 33296:
			itemDef.name = "Chocolate Chicken";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 35150;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1731;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "a chocolate chicken";
			itemDef.modifiedModelColors = new int[] { 947, 8301 };
			itemDef.originalModelColors = new int[] { 8128, 38835 };
			break;

		case 33297:
			itemDef.name = "Chocolate Chicken";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 35150;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 1731;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.description = "a chocolate chicken";
			itemDef.modifiedModelColors = new int[] { 947, 8301 };
			itemDef.originalModelColors = new int[] { 8128, 947 };
			break;

		case 33305:
			itemDef.name = "$10 bond";
			itemDef.description = "$10 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 84, 84, 84, 84, 84, 84, 84 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33306:
			itemDef.name = "$25 bond";
			itemDef.description = "$25 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 87, 87, 87, 87, 87, 87, 87 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33307:
			itemDef.name = "$50 bond";
			itemDef.description = "$50 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 65, 65, 65, 65, 65, 65, 65 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33308:
			itemDef.name = "$100 bond";
			itemDef.description = "$25 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 75, 75, 75, 75, 75, 75, 75 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33309:
			itemDef.name = "$200 bond";
			itemDef.description = "$200 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 88, 88, 88, 88, 88, 88, 88 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33310:
			itemDef.name = "$500 bond";
			itemDef.description = "$500 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 85, 85, 85, 85, 85, 85, 85 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33311:
			itemDef.name = "$1000 bond";
			itemDef.description = "$1000 bond.";
			itemDef.modelId = 29210;
			itemDef.modelZoom = 2300;
			itemDef.modelRotation1 = 512;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.modifiedTextureColors = new int[] { 86, 86, 86, 86, 86, 86, 86 };
			itemDef.originalTextureColors = new int[] { 22451, 20416, 22181, 22449, 22305, 21435, 22464 };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Redeem";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33312:
			itemDef.name = "Armadyl battlestaff";
			itemDef.description = "none.";
			itemDef.modelId = 60332;
			itemDef.maleModel = 60333;
			itemDef.femaleModel = 60333;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 225;
			itemDef.modelRotation2 = 1994;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;

		case 33313:
			itemDef.name = "Colossal platebody";
			itemDef.description = "Colossal platebody.";
			itemDef.modelId = 60323;
			itemDef.maleModel = 60324;
			itemDef.femaleModel = 60324;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33314:
			itemDef.name = "Colossal platelegs";
			itemDef.description = "Colossal platelegs.";
			itemDef.modelId = 60325;
			itemDef.maleModel = 60326;
			itemDef.femaleModel = 60326;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33315:
			itemDef.name = "Colossal boots";
			itemDef.description = "Colossal boots.";
			itemDef.modelId = 60329;
			itemDef.maleModel = 60329;
			itemDef.femaleModel = 60329;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33316:
			itemDef.name = "Colossal gloves";
			itemDef.description = "Colossal gloves.";
			itemDef.modelId = 60330;
			itemDef.maleModel = 60331;
			itemDef.femaleModel = 60331;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33317:
			itemDef.name = "Colossal helmet";
			itemDef.description = "Colossal helmet.";
			itemDef.modelId = 60327;
			itemDef.maleModel = 60328;
			itemDef.femaleModel = 60328;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;

		case 33318:
			itemDef.name = "Polypore staff";
			itemDef.description = "none.";
			itemDef.modelId = 60334;
			itemDef.maleModel = 60335;
			itemDef.femaleModel = 60335;
			itemDef.modelZoom = 3750;
			itemDef.modelRotation1 = 1454;
			itemDef.modelRotation2 = 997;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;

		case 33319:
			itemDef.name = "Ganodermic platebody";
			itemDef.description = "Ganodermic platebody.";
			itemDef.modelId = 60338;
			itemDef.maleModel = 60339;
			itemDef.femaleModel = 60339;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33320:
			itemDef.name = "Ganodermic platelegs";
			itemDef.description = "Ganodermic platelegs.";
			itemDef.modelId = 60340;
			itemDef.maleModel = 60341;
			itemDef.femaleModel = 60341;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33321:
			itemDef.name = "Ganodermic helmet";
			itemDef.description = "Ganodermic helmet.";
			itemDef.modelId = 60336;
			itemDef.maleModel = 60337;
			itemDef.femaleModel = 60337;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;

		case 33322:
			itemDef.name = "Grotesque platebody";
			itemDef.description = "Grosteq platebody.";
			itemDef.modelId = 60347;
			itemDef.maleModel = 60348;
			itemDef.femaleModel = 60348;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33323:
			itemDef.name = "Grotesque platelegs";
			itemDef.description = "Grosteq platelegs.";
			itemDef.modelId = 60349;
			itemDef.maleModel = 60350;
			itemDef.femaleModel = 60350;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33324:
			itemDef.name = "Grotesque helmet";
			itemDef.description = "Grosteqc helmet.";
			itemDef.modelId = 60345;
			itemDef.maleModel = 60346;
			itemDef.femaleModel = 60346;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;

		case 33325:
			itemDef.name = "Grotesque cape";
			itemDef.description = "Grosteq cape.";
			itemDef.modelId = 60351;
			itemDef.maleModel = 60352;
			itemDef.femaleModel = 60352;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33326:
			itemDef.name = "Stunning Hammer";
			itemDef.description = "Has a chance to stun an enemy.";
			itemDef.modelId = 60353;
			itemDef.maleModel = 60354;
			itemDef.femaleModel = 60354;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1985;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;

		case 33327:
			itemDef.name = "Stunning Katagon platebody";
			itemDef.description = "has a chance to stun an enemy.";
			itemDef.modelId = 60356;
			itemDef.maleModel = 60357;
			itemDef.femaleModel = 60357;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33328:
			itemDef.name = "Stunning Katagon platelegs";
			itemDef.description = "has a chance to stun an enemy.";
			itemDef.modelId = 60358;
			itemDef.maleModel = 60359;
			itemDef.femaleModel = 60359;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33329:
			itemDef.name = "Stunning Katagon helmet";
			itemDef.description = "has a chance to stun an enemy.";
			itemDef.modelId = 60360;
			itemDef.maleModel = 60361;
			itemDef.femaleModel = 60361;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.maleOffset = 3;
			break;

		case 33331:
			itemDef.name = "Ancient platebody";
			itemDef.description = "Ancient platebody.";
			itemDef.modelId = 60366;
			itemDef.maleModel = 60367;
			itemDef.femaleModel = 60367;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33332:
			itemDef.name = "Ancient platelegs";
			itemDef.description = "Ancient platelegs.";
			itemDef.modelId = 60368;
			itemDef.maleModel = 60369;
			itemDef.femaleModel = 60369;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33334:
			itemDef.name = "Ancient boots";
			itemDef.description = "Ancient boots.";
			itemDef.modelId = 60372;
			itemDef.maleModel = 60372;
			itemDef.femaleModel = 60372;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33335:
			itemDef.name = "Ancient gloves";
			itemDef.description = "Ancient gloves.";
			itemDef.modelId = 60370;
			itemDef.maleModel = 60371;
			itemDef.femaleModel = 60371;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
		case 33336:
			itemDef.name = "Ancient helmet";
			itemDef.description = "Ancient helmet.";
			itemDef.modelId = 60364;
			itemDef.maleModel = 60365;
			itemDef.femaleModel = 60365;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;

		case 33341:
			itemDef.name = "Vanguard helmet";
			itemDef.description = "none.";
			itemDef.modelId = 60391;
			itemDef.maleModel = 60392;
			itemDef.femaleModel = 60392;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;

		case 33342:
			itemDef.name = "Vanguard platebody";
			itemDef.description = "none.";
			itemDef.modelId = 60393;
			itemDef.maleModel = 60394;
			itemDef.femaleModel = 60394;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33343:
			itemDef.name = "Vanguard platelegs";
			itemDef.description = "none.";
			itemDef.modelId = 60395;
			itemDef.maleModel = 60396;
			itemDef.femaleModel = 60396;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33344:
			itemDef.name = "Vanguard boots";
			itemDef.description = "none.";
			itemDef.modelId = 60398;
			itemDef.maleModel = 60398;
			itemDef.femaleModel = 60398;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33345:
			itemDef.name = "Vanguard gloves";
			itemDef.description = "none.";
			itemDef.modelId = 60373;
			itemDef.maleModel = 60397;
			itemDef.femaleModel = 60397;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33346:
			itemDef.name = "Celestial staff of light";
			itemDef.description = "none.";
			itemDef.modelId = 60401;
			itemDef.maleModel = 60402;
			itemDef.femaleModel = 60402;
			itemDef.modelZoom = 3200;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 101 };
			itemDef.originalModelColors = new int[] { 12 };
			itemDef.maleOffset = -6;
			itemDef.femaleOffset = -6;
			break;

		case 33347:
			itemDef.name = "Hood of sorrow";
			itemDef.description = "none.";
			itemDef.modelId = 60438;
			itemDef.maleModel = 60403;
			itemDef.femaleModel = 60403;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33348:
			itemDef.name = "Celestial robe top";
			itemDef.description = "none.";
			itemDef.modelId = 60404;
			itemDef.maleModel = 60405;
			itemDef.femaleModel = 60405;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33349:
			itemDef.name = "Celestial robe legs";
			itemDef.description = "none.";
			itemDef.modelId = 60406;
			itemDef.maleModel = 60407;
			itemDef.femaleModel = 60407;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33350:
			itemDef.name = "Primal 2h sword";
			itemDef.description = "none.";
			itemDef.modelId = 60408;
			itemDef.maleModel = 60409;
			itemDef.femaleModel = 60409;
			itemDef.modelZoom = 1701;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -3;
			itemDef.modelRotation2 = 1529;
			itemDef.modelRotation1 = 1713;
			itemDef.modelRotationY = 898;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -7;
			itemDef.maleOffset = -7;
			// itemDef.aByte205 = 3;
			break;

		case 33353:
			itemDef.name = "Primal longsword";
			itemDef.description = "none.";
			itemDef.modelId = 60417;
			itemDef.maleModel = 60418;
			itemDef.femaleModel = 60418;
			itemDef.modelZoom = 1616;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 1793;
			itemDef.modelRotation1 = 1473;
			itemDef.modelRotationY = 1121;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -7;
			itemDef.maleOffset = -7;
			// itemDef.aByte205 = 3;
			break;

		case 33354:
			itemDef.name = "Primal maul";
			itemDef.description = "none.";
			itemDef.modelId = 60419;
			itemDef.maleModel = 60420;
			itemDef.femaleModel = 60420;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 525;
			itemDef.modelRotation2 = 350;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -7;
			itemDef.maleOffset = -7;
			// itemDef.aByte205 = 3;
			break;

		case 33359:
			itemDef.name = "Primal rapier";
			itemDef.description = "Good for fighting the ...";
			itemDef.modelId = 60433;
			itemDef.maleModel = 60433;
			itemDef.femaleModel = 60433;
			itemDef.modelZoom = 1300;
			itemDef.modelRotation1 = 1401;
			itemDef.modelRotation2 = 1724;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 15;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -7;
			itemDef.maleOffset = -7;
			// itemDef.aByte205 = 3;
			break;

		case 33360:
			itemDef.name = "Primal spear";
			itemDef.description = "Good for fighting the Corperal Beast.";
			itemDef.modelId = 60434;
			itemDef.maleModel = 60435;
			itemDef.femaleModel = 60435;
			itemDef.modelZoom = 1711;
			itemDef.modelRotation1 = 485;
			itemDef.modelRotation2 = 391;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -10;
			itemDef.maleOffset = -10;
			// itemDef.aByte205 = 3;
			break;

		case 33361:
			itemDef.name = "Primal warhammer";
			itemDef.description = "none.";
			itemDef.modelId = 60436;
			itemDef.maleModel = 60437;
			itemDef.femaleModel = 60437;
			itemDef.modelZoom = 1330;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 148;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.femaleOffset = -7;
			itemDef.maleOffset = -7;
			// itemDef.aByte205 = 3;
			break;

		case 33362:
			itemDef.name = "Chitin helmet";
			itemDef.description = "none.";
			itemDef.modelId = 60445;
			itemDef.maleModel = 60446;
			itemDef.femaleModel = 60446;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33364:
			itemDef.name = "Chitin platebody";
			itemDef.description = "none.";
			itemDef.modelId = 60447;
			itemDef.maleModel = 60448;
			itemDef.femaleModel = 60448;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33365:
			itemDef.name = "Chitin platelegs";
			itemDef.description = "none.";
			itemDef.modelId = 60449;
			itemDef.maleModel = 60450;
			itemDef.femaleModel = 60450;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33366:
			itemDef.name = "Chitin cape";
			itemDef.description = "none.";
			itemDef.modelId = 60443;
			itemDef.maleModel = 60444;
			itemDef.femaleModel = 60444;
			itemDef.modelZoom = 1500;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 2031;
			itemDef.modelOffset1 = -4;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33367:
			itemDef.name = "Supreme void helmet";
			itemDef.description = "none.";
			itemDef.modelId = 60467;
			itemDef.maleModel = 60464;
			itemDef.femaleModel = 60464;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33368:
			itemDef.name = "Supreme void robe top";
			itemDef.description = "none.";
			itemDef.modelId = 60468;
			itemDef.maleModel = 60465;
			itemDef.femaleModel = 60465;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33369:
			itemDef.name = "Supreme void robe";
			itemDef.description = "none.";
			itemDef.modelId = 60469;
			itemDef.maleModel = 60466;
			itemDef.femaleModel = 60466;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33370:
			itemDef.name = "Korasi's sword";
			itemDef.description = "none.";
			itemDef.modelId = 60471;
			itemDef.maleModel = 60470;
			itemDef.femaleModel = 60470;
			itemDef.modelZoom = 1744;
			itemDef.modelRotation1 = 330;
			itemDef.modelRotation2 = 1505;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = -4;
			itemDef.femaleOffset = -4;
			break;

		case 33371:
			itemDef.name = "Spiked slayer helmet";
			itemDef.description = "none.";
			itemDef.modelId = 60475;
			itemDef.maleModel = 60476;
			itemDef.femaleModel = 60476;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33372:
			itemDef.name = "Slayer platebody";
			itemDef.description = "none.";
			itemDef.modelId = 60478;
			itemDef.maleModel = 60479;
			itemDef.femaleModel = 60479;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33373:
			itemDef.name = "Slayer platelegs";
			itemDef.description = "none.";
			itemDef.modelId = 60474;
			itemDef.maleModel = 60477;
			itemDef.femaleModel = 60477;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33374:
			itemDef.name = "Slayer boots";
			itemDef.description = "none.";
			itemDef.modelId = 60472;
			itemDef.maleModel = 60473;
			itemDef.femaleModel = 60473;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 164;
			itemDef.modelRotation2 = 156;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33375:
			itemDef.name = "Blood Justiciar helmet";
			itemDef.description = "none.";
			itemDef.modelId = 60482;
			itemDef.maleModel = 60483;
			itemDef.femaleModel = 60483;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33376:
			itemDef.name = "Blood Justiciar platebody";
			itemDef.description = "none.";
			itemDef.modelId = 60484;
			itemDef.maleModel = 60485;
			itemDef.femaleModel = 60485;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33377:
			itemDef.name = "Blood justiciar platelegs";
			itemDef.description = "none.";
			itemDef.modelId = 60486;
			itemDef.maleModel = 60487;
			itemDef.femaleModel = 60487;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33378:
			itemDef.name = "Blood justiciar boots";
			itemDef.description = "none.";
			itemDef.modelId = 60488;
			itemDef.maleModel = 60488;
			itemDef.femaleModel = 60488;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 164;
			itemDef.modelRotation2 = 156;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33379:
			itemDef.name = "Blood justiciar gloves";
			itemDef.description = "none.";
			itemDef.modelId = 60489;
			itemDef.maleModel = 60490;
			itemDef.femaleModel = 60490;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33380:
			itemDef.name = "Blood scythe of vitur";
			itemDef.description = "none.";
			itemDef.modelId = 60480;
			itemDef.maleModel = 60481;
			itemDef.femaleModel = 60481;
			itemDef.modelZoom = 3850;
			itemDef.modelRotation1 = 727;
			itemDef.modelRotation2 = 997;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33381:
			itemDef.name = "Skiller cape";
			itemDef.description = "Skiller cape.";
			itemDef.modelId = 60494;
			itemDef.maleModel = 60493;
			itemDef.femaleModel = 60492;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33382:
			itemDef.name = "Justiciar faceguard (zamorak)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { 926, 926, 926, 926 };
			// itemDef.aByte205 = 3;
			break;

		case 33383:
			itemDef.name = "Justiciar faceguard (guthix)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { 21939, 21945, 21952, 21954 };
			// itemDef.aByte205 = 3;
			break;

		case 33384:
			itemDef.name = "Justiciar faceguard (saradomin)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { 43929, 43929, 43929, 43929 };
			// itemDef.aByte205 = 3;
			break;

		case 33385:
			itemDef.name = "Justiciar faceguard (ancient)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { -10854, -10860, -10872, -10874 };
			// itemDef.aByte205 = 3;
			break;

		case 33387:
			itemDef.name = "Justiciar faceguard (bandos)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { 7062, 7062, 7062, 7062 };
			// itemDef.aByte205 = 3;
			break;

		case 33389:
			itemDef.name = "Justiciar faceguard (armadyl)";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 4550 };
			itemDef.originalModelColors = new int[] { 10467, 10474, 10482, 10484 };
			// itemDef.aByte205 = 3;
			break;

		case 33390:
			itemDef.name = "Justiciar chestguard (zamorak)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 926, 926, 926, 926 };
			// itemDef.aByte205 = 3;
			break;

		case 33391:
			itemDef.name = "Justiciar chestguard (guthix)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 21939, 21945, 21952, 21954 };
			// itemDef.aByte205 = 3;
			break;

		case 33392:
			itemDef.name = "Justiciar chestguard (saradomin)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 43929, 43929, 43929, 43929 };
			// itemDef.aByte205 = 3;
			break;

		case 33393:
			itemDef.name = "Justiciar chestguard (ancient)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { -10854, -10860, -10872, -10874 };
			// itemDef.aByte205 = 3;
			break;

		case 33394:
			itemDef.name = "Justiciar chestguard (bandos)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 7062, 7062, 7062, 7062 };
			// itemDef.aByte205 = 3;
			break;

		case 33395:
			itemDef.name = "Justiciar chestguard (armadyl)";
			itemDef.description = "none.";
			itemDef.modelId = 35750;
			itemDef.maleModel = 35359;
			itemDef.femaleModel = 35368;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 432;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 10467, 10474, 10482, 10484 };
			// itemDef.aByte205 = 3;
			break;

		case 33396:
			itemDef.name = "Justiciar legguards (zamorak)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 926, 926, 926, 926 };
			// itemDef.aByte205 = 3;
			break;

		case 33397:
			itemDef.name = "Justiciar legguards (guthix)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 21939, 21945, 21952, 21954 };
			// itemDef.aByte205 = 3;
			break;

		case 33398:
			itemDef.name = "Justiciar legguards (saradomin)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 43929, 43929, 43929, 43929 };
			// itemDef.aByte205 = 3;
			break;

		case 33399:
			itemDef.name = "Justiciar legguards (ancient)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { -10854, -10860, -10872, -10874 };
			// itemDef.aByte205 = 3;
			break;

		case 33400:
			itemDef.name = "Justiciar legguards (bandos)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 7062, 7062, 7062, 7062 };
			// itemDef.aByte205 = 3;
			break;

		case 33401:
			itemDef.name = "Justiciar legguards (armadyl)";
			itemDef.description = "none.";
			itemDef.modelId = 35752;
			itemDef.maleModel = 35356;
			itemDef.femaleModel = 35357;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 468;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 6709, 6736, 6602, 6699 };
			itemDef.originalModelColors = new int[] { 10467, 10474, 10482, 10484 };
			// itemDef.aByte205 = 3;
			break;

		case 33402:
			itemDef.name = "Pet andy";
			itemDef.description = "none.";
			itemDef.modelId = 50169;
			itemDef.modelZoom = 1500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33403:
			itemDef.name = "Pet mod divine";
			itemDef.description = "none.";
			itemDef.modelId = 14283;
			itemDef.modelZoom = 3500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33404:
			itemDef.name = "Celestial fairy";
			itemDef.description = "none.";
			itemDef.modelId = 60491;
			itemDef.modelZoom = 3500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 947 };
			itemDef.modifiedTextureColors = new int[] { 78 };
			itemDef.modifiedModelColors = new int[] { 937, 11200 };
			itemDef.originalModelColors = new int[] { 42663, 41883 };
			// itemDef.aByte205 = 3;
			break;

		case 33405:
			itemDef.name = "Lava partyhat (red)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A lava partyhat.";
			break;

		case 33406:
			itemDef.name = "Lava partyhat (green)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A lava partyhat.";
			break;

		case 33407:
			itemDef.name = "Lava partyhat (cyan)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A lava partyhat.";
			break;

		case 33408:
			itemDef.name = "Lava partyhat (purple)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A lava partyhat.";
			break;

		case 33409:
			itemDef.name = "Infernal partyhat (blue)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An Infernal partyhat.";
			break;

		case 33410:
			itemDef.name = "Infernal partyhat (green)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An Infernal partyhat.";
			break;

		case 33411:
			itemDef.name = "Infernal partyhat (purple)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An Infernal partyhat.";
			break;

		case 33412:
			itemDef.name = "Infernal partyhat (rainbow)";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "An Infernal partyhat.";
			break;

		case 33413:
			itemDef.name = "Celestial partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33414:
			itemDef.name = "Blood partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33415:
			itemDef.name = "Shadow partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33416:
			itemDef.name = "Light blue partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33417:
			itemDef.name = "Easter partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33418:
			itemDef.name = "Dark sparkle partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33419:
			itemDef.name = "Rainbow partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33420:
			itemDef.name = "Fire partyhat";
			itemDef.modelId = 2635;
			itemDef.modelZoom = 440;
			itemDef.modelRotation2 = 1852;
			itemDef.modelRotation1 = 76;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.maleModel = 187;
			itemDef.femaleModel = 363;
			// itemDef.anInt164 = -1;
			// itemDef.anInt188 = -1;
			// itemDef.aByte205 = -8;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "none.";
			break;

		case 33421:
			itemDef.name = "Pet star sprite";
			itemDef.description = "none.";
			itemDef.modelId = 60506;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33422:
			itemDef.name = "Stargaze Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 61110;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			// itemDef.modifiedModelColors = new int[] {22410, 2999};
			// itemDef.originalModelColors = new int[] {35321, 350};
			itemDef.description = "none";
			break;

		case 33423:
			itemDef.name = "Star Dust";
			itemDef.inventoryOptions = new String[] { "Exchange", null, null, null, "Drop" };
			itemDef.stackable = true;
			itemDef.modelId = 60496;
			itemDef.modelZoom = 2086;
			itemDef.modelRotation1 = 279;
			itemDef.modelRotation2 = 1994;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 47;
			// itemDef.modifiedModelColors = new int[] {22410, 2999};
			// itemDef.originalModelColors = new int[] {35321, 350};
			itemDef.description = "none";
			break;

		case 33424:
			itemDef.name = "Blood twisted bow";
			itemDef.description = "none.";
			itemDef.modelId = 32799;
			itemDef.maleModel = 32674;
			itemDef.femaleModel = 32674;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 10318, 10334 };
			itemDef.modifiedTextureColors = new int[] { 66, 66 };
			itemDef.modifiedModelColors = new int[] { 14236, 13223 };
			itemDef.originalModelColors = new int[] { 926, 926 };
			// itemDef.aByte205 = 3;
			break;

		case 33425:
			itemDef.name = "Celestial crow";
			itemDef.description = "none.";
			itemDef.modelId = 60507;
			itemDef.modelZoom = 1000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 10382 };
			itemDef.modifiedTextureColors = new int[] { 78 };
			itemDef.modifiedModelColors = new int[] { 10378, 10502 };
			itemDef.originalModelColors = new int[] { 0, 0 };
			// itemDef.aByte205 = 3;
			break;

		case 33426:
			itemDef.name = "Celestial penguin";
			itemDef.description = "none.";
			itemDef.modelId = 60508;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 10343 };
			itemDef.modifiedTextureColors = new int[] { 78 };
			itemDef.modifiedModelColors = new int[] { 16, 12, 20, 24, 8, 10332, 10337 };
			itemDef.originalModelColors = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			// itemDef.aByte205 = 3;
			break;

		case 33427:
			itemDef.name = "Celestial snake";
			itemDef.description = "none.";
			itemDef.modelId = 60509;
			itemDef.modelZoom = 1800;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 10644, 10512 };
			itemDef.modifiedTextureColors = new int[] { 78, 78 };
			itemDef.modifiedModelColors = new int[] { 10413, 10405, 10524 };
			itemDef.originalModelColors = new int[] { 0, 0, 0 };
			// itemDef.aByte205 = 3;
			break;

		case 33428:
			itemDef.name = "Celestial scorpion";
			itemDef.description = "none.";
			itemDef.modelId = 60510;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 142 };
			itemDef.modifiedTextureColors = new int[] { 78 };
			itemDef.modifiedModelColors = new int[] { 4884, 4636, 3974, 4525, 4645 };
			itemDef.originalModelColors = new int[] { 0, 0, 0, 0, 0 };
			// itemDef.aByte205 = 3;
			break;

		case 33429:
			itemDef.name = "Armadyl dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 10467, 10474, 10482, 10484 };
			break;

		case 33430:
			itemDef.name = "Guthix dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 21939, 21945, 21952, 21954 };
			break;

		case 33431:
			itemDef.name = "Zamorak dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 926, 926, 926, 926 };
			break;

		case 33432:
			itemDef.name = "Ancient dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { -10854, -10860, -10872, -10874 };
			break;

		case 33433:
			itemDef.name = "Bandos dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 7062, 7062, 7062, 7062 };
			break;

		case 33434:
			itemDef.name = "Saradomin dye";
			itemDef.description = "none.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 43929, 43929, 43929, 43929 };
			break;

		case 33435:
			itemDef.name = "Celestial egg";
			itemDef.description = "none.";
			itemDef.modelId = 7171;
			itemDef.modelZoom = 550;
			itemDef.modelRotation1 = 76;
			itemDef.modelRotation2 = 16;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalTextureColors = new int[] { 476 };
			itemDef.modifiedTextureColors = new int[] { 78 };
			itemDef.inventoryOptions = new String[] { "Hatch", null, null, null, "Drop" };
			itemDef.stackable = true;
			break;

		case 33436:
			itemDef.name = "Elite void top (placeholder)";
			itemDef.description = "none.";
			itemDef.modelId = 10586;
			itemDef.maleModel = 10687;
			itemDef.anInt188 = 10681;
			itemDef.femaleModel = 10694;
			itemDef.anInt164 = 10688;
			itemDef.modelZoom = 1221;
			itemDef.modelRotation1 = 459;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			// itemDef.originalTextureColors = new int [] { 695};
			// itemDef.modifiedTextureColors = new int [] { 66};
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33437:
			itemDef.name = "Elite void robe (placeholder)";
			itemDef.description = "none.";
			itemDef.modelId = 60528;
			itemDef.maleModel = 60526;
			itemDef.femaleModel = 60527;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 525;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 1;
			// itemDef.originalTextureColors = new int [] { 695};
			// itemDef.modifiedTextureColors = new int [] { 66};
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33438:
			itemDef.name = "Blood chest";
			itemDef.description = "none.";
			itemDef.modelId = 60516;
			itemDef.modelZoom = 2640;
			itemDef.modelRotation1 = 114;
			itemDef.modelRotation2 = 1883;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;

		case 33439:
			itemDef.name = "Blood bird";
			itemDef.description = "none.";
			itemDef.modelId = 60517;
			itemDef.modelZoom = 2768;
			itemDef.modelRotation1 = 141;
			itemDef.modelRotation2 = 1790;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = -13;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.originalTextureColors = new int[] { 1946, 2983, 6084, 2735, 5053, 6082, 4013, 2733, 4011, 2880,
					8150 };
			itemDef.modifiedTextureColors = new int[] { 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66 };
			break;

		case 33440:
			itemDef.name = "Blood Death";
			itemDef.description = "none.";
			itemDef.modelId = 60441;
			itemDef.modelZoom = 16000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33442:
			itemDef.name = "10 Min XP boost (25%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Claim", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33446:
			itemDef.name = "10 Min XP boost (50%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Claim", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33450:
			itemDef.name = "10 Min XP boost (75%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Claim", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33454:
			itemDef.name = "10 Min XP boost (100%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33458:
			itemDef.name = "10 Min XP boost (150%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33462:
			itemDef.name = "10 Min XP boost (200%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33443:
			itemDef.name = "30 Min XP boost (25%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33447:
			itemDef.name = "30 Min XP boost (50%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33451:
			itemDef.name = "30 Min XP boost (75%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33455:
			itemDef.name = "30 Min XP boost (100%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33459:
			itemDef.name = "30 Min XP boost (150%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33463:
			itemDef.name = "30 Min XP boost (200%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33444:
			itemDef.name = "60 Min XP boost (25%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33448:
			itemDef.name = "60 Min XP boost (50%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33452:
			itemDef.name = "60 Min XP boost (75%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33456:
			itemDef.name = "60 Min XP boost (100%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33460:
			itemDef.name = "60 Min XP boost (150%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33464:
			itemDef.name = "60 Min XP boost (200%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33445:
			itemDef.name = "120 Min XP boost (25%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33449:
			itemDef.name = "120 Min XP boost (50%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33453:
			itemDef.name = "120 Min XP boost (75%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33457:
			itemDef.name = "120 Min XP boost (100%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33461:
			itemDef.name = "120 Min XP boost (150%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33465:
			itemDef.name = "120 Min XP boost (200%)";
			itemDef.description = "none.";
			itemDef.modelId = 60531;
			itemDef.modelZoom = 1488;
			itemDef.modelRotation1 = 246;
			itemDef.modelRotation2 = 1721;
			itemDef.modelOffset1 = -11;
			itemDef.modelOffset2 = -45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.originalTextureColors = new int[] { 54302, 54312, 54307, 54297, 54315, 54310, 54305, 54287, 55292,
					54300, 10281, 10274 };
			itemDef.modifiedTextureColors = new int[] { 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67, 67 };
			// itemDef.aByte205 = 3;
			break;

		case 33466:
			itemDef.name = "Deathtouched dart";
			itemDef.description = "none.";
			itemDef.modelId = 60534;
			itemDef.maleModel = 60533;
			itemDef.femaleModel = 60533;
			itemDef.modelZoom = 1053;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 0;
			itemDef.stackable = true;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33467:
			itemDef.name = "Justiciar boots";
			itemDef.description = "none.";
			itemDef.modelId = 60535;
			itemDef.maleModel = 60535;
			itemDef.femaleModel = 60535;
			itemDef.modelZoom = 789;
			itemDef.modelRotation1 = 164;
			itemDef.modelRotation2 = 156;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33468:
			itemDef.name = "Justiciar gloves";
			itemDef.description = "none.";
			itemDef.modelId = 31022;
			itemDef.maleModel = 31006;
			itemDef.femaleModel = 31013;
			itemDef.modelZoom = 592;
			itemDef.modelRotation1 = 636;
			itemDef.modelRotation2 = 2015;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 123, 70 };
			itemDef.originalModelColors = new int[] { 6736, 59441 };
			// itemDef.aByte205 = 3;
			break;

		case 33469:
			itemDef.name = "Magic mushroom";
			itemDef.description = "offers a 10% droprate increase while this pet follows you.";
			itemDef.modelId = 60532;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 6;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33470:
			itemDef.name = "Twisted staff";
			itemDef.description = "none.";
			itemDef.modelId = 60538;
			itemDef.maleModel = 60539;
			itemDef.femaleModel = 60539;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33471:
			itemDef.name = "Cowboy hat";
			itemDef.description = "none.";
			itemDef.modelId = 60540;
			itemDef.maleModel = 60541;
			itemDef.femaleModel = 60541;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 108;
			itemDef.modelRotation2 = 1535;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33472:
			itemDef.name = "Stick";
			itemDef.description = "Careful of that chub.";
			itemDef.modelId = 60545;
			itemDef.maleModel = 60545;
			itemDef.femaleModel = 60545;
			itemDef.modelZoom = 3000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33473:
			itemDef.name = "#1 Tob cape";
			itemDef.description = "Reward to the first to complete tob solo and in a team.";
			itemDef.modelId = 60551;
			itemDef.maleModel = 60550;
			itemDef.femaleModel = 60550;
			itemDef.modelZoom = 2295;
			itemDef.modelRotation1 = 367;
			itemDef.modelRotation2 = 1212;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33474:
			itemDef.name = "Supreme void upgrade kit";
			itemDef.description = "none.";
			itemDef.modelId = 4847;
			itemDef.modelZoom = 1310;
			itemDef.modelRotation1 = 163;
			itemDef.modelRotation2 = 73;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { -32709, 10295, 10304, 10287, 10275, 10283 };
			itemDef.originalModelColors = new int[] { 10, 10, 10, 10, 10, 10 };
			// itemDef.aByte205 = 3;
			break;

		case 33475:
			itemDef.name = "Hunter's penguin";
			itemDef.description = "the one and only's pet.";
			itemDef.modelId = 60548;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 6;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33476:
			itemDef.name = "Chef Harambe";
			itemDef.description = "I like to dip my balls in a deep fryer.";
			itemDef.modelId = 60921;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 6;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33477:
			itemDef.name = "Void knight champion jr";
			itemDef.description = "none.";
			itemDef.modelId = 60463;
			itemDef.modelZoom = 14000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 6;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33478:
			itemDef.name = "Custom pet token";
			itemDef.description = "Trade this to corey after filling out a form on the forums post custom pets";
			itemDef.modelId = 13838;
			itemDef.modelZoom = 530;
			itemDef.modelRotation1 = 415;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33479:
			itemDef.name = "Mr jaycorr";
			itemDef.description = "The autistic one.";
			itemDef.modelId = 60592;
			itemDef.modelZoom = 7500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33480:
			itemDef.name = "Broom broom";
			itemDef.description = "none.";
			itemDef.modelId = 60593;
			itemDef.maleModel = 60593;
			itemDef.femaleModel = 60593;
			itemDef.modelZoom = 2700;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33481:
			itemDef.name = "Test";
			itemDef.description = "none.";
			itemDef.modelId = 35751;
			itemDef.maleModel = 35349;
			itemDef.femaleModel = 35361;
			itemDef.modelZoom = 777;
			itemDef.modelRotation1 = 22;
			itemDef.modelRotation2 = 1972;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 11773:
		case 11771:
		case 11770:
		case 11772:
			itemDef.anInt196 += 45;
			break;
		case 22610:
			itemDef.name = "Vesta's spear (deg)";
			break;
		case 22614:
			itemDef.name = "Vesta's longsword (deg)";
			break;
		case 22616:
			itemDef.name = "Vesta's chainbody (deg)";
			break;
		case 22619:
			itemDef.name = "Vesta's plateskirt (deg)";
			break;
		case 22622:
			itemDef.name = "Statius's warhammer (deg)";
			break;
		case 22625:
			itemDef.name = "Statius's full helm (deg)";
			break;
		case 22628:
			itemDef.name = "Statius's platebody (deg)";
			break;
		case 22631:
			itemDef.name = "Statius's platelegs (deg)";
			break;
		case 22638:
			itemDef.name = "Morrigan's coif (deg)";
			break;
		case 22641:
			itemDef.name = "Morrigan's leather body (deg)";
			break;
		case 22644:
			itemDef.name = "Morrigan's leather chaps (deg)";
			break;
		case 22647:
			itemDef.name = "Zuriel's staff (deg)";
			break;
		case 22650:
			itemDef.name = "Zuriel's hood (deg)";
			break;
		case 22653:
			itemDef.name = "Zuriel's robe top (deg)";
			break;
		case 22656:
			itemDef.name = "Zuriel's robe bottom (deg)";
			break;

		case 13303:
			itemDef.name = "Event Key (Tarn)";
			itemDef.description = "Use this to open the Event Boss chest.";
			itemDef.modifiedModelColors = new int[] { 8128 };
			itemDef.originalModelColors = new int[] { 933 };
			break;
		case 13302:
			itemDef.name = "Event Key (Graardor)";
			itemDef.description = "Use this to open the Event Boss chest.";
			itemDef.modifiedModelColors = new int[] { 8128 };
			itemDef.originalModelColors = new int[] { 933 };
			break;
		case 13305:
			itemDef.name = "Tastey-Looking Key";
			itemDef.description = "Use this to open the Event Boss chest.";
			itemDef.modifiedModelColors = new int[] { 8128 };
			itemDef.originalModelColors = new int[] { 933 };
			break;
		case 2697:
			itemDef.name = "$10 Scroll";
			itemDef.description = "Get donor status at a cheaper cost!";
			break;
		case 2698:
			itemDef.name = "$50 Scroll";
			itemDef.description = "Read this scroll to be rewarded with the Super Donator status.";
			break;
		case 2699:
			itemDef.name = "$100 Donator";
			itemDef.description = "Read this scroll to be rewarded with the Extreme Donator status.";
			break;
		case 2700:
			itemDef.name = "$5 Scroll";
			itemDef.description = "Read this scroll to be rewarded with the Legendary Donator status.";
			break;
		case 1464:
			itemDef.name = "Vote ticket";
			itemDef.description = "This ticket can be exchanged for a voting point.";
			break;

		case 11739:
			itemDef.name = "Daily reward box";
			itemDef.description = "Open this box for a daily reward.";
			break;

		case 13066:// super set
		case 12873:// barrows
		case 12875:
		case 12877:
		case 12879:
		case 12881:
		case 12883:
		case 12789:// clue box
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			break;
		}
		
	}
		
	private static void customItems2(ItemDefinition itemDef) {

		// new items here
		switch (itemDef.id) {

		case 33482:
			itemDef.name = "Valius platebody (T2)";
			itemDef.description = "An upgraded set of Valius armor.";
			itemDef.modelId = 60617;
			itemDef.maleModel = 60610;
			itemDef.femaleModel = 60616;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33483:
			itemDef.name = "Valius platelegs (T2)";
			itemDef.description = "An upgraded set of Valius armor.";
			itemDef.modelId = 60614;
			itemDef.maleModel = 60615;
			itemDef.femaleModel = 60615;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33484:
			itemDef.name = "Valius boots (T2)";
			itemDef.description = "An upgraded set of Valius armor.";
			itemDef.modelId = 60611;
			itemDef.maleModel = 60611;
			itemDef.femaleModel = 60611;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33485:
			itemDef.name = "Valius gloves (T2)";
			itemDef.description = "An upgraded set of Valius armor.";
			itemDef.modelId = 60612;
			itemDef.maleModel = 13307;
			itemDef.femaleModel = 13307;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {8656, 10394};
			itemDef.originalModelColors = new int [] {180, 127};
			// itemDef.aByte205 = 3;
			break;
		case 33486:
			itemDef.name = "Valius helmet (T1)";
			itemDef.description = "An upgraded set of Valius armor.";
			itemDef.modelId = 60956;
			itemDef.maleModel = 60940;
			itemDef.femaleModel = 60946;
			itemDef.modelZoom = 1135;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -53;
			itemDef.modelRotation2 = 760;
			itemDef.modelRotation1 = 1033;
			itemDef.modelRotationY = 1019;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33487:
			itemDef.name = "Valius platebody (T1)";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60957;
			itemDef.maleModel = 60944;
			itemDef.anInt188 = 60943;
			itemDef.anInt164 = 60949;
			itemDef.femaleModel = 60950;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33488:
			itemDef.name = "Valius platelegs (T1)";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60954;
			itemDef.maleModel = 60942;
			itemDef.femaleModel = 60948;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33489:
			itemDef.name = "Valius boots (T1)";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60955;
			itemDef.maleModel = 60945;
			itemDef.femaleModel = 60951;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33490:
			itemDef.name = "Valius gloves (T1)";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60958;
			itemDef.maleModel = 60941;
			itemDef.femaleModel = 60947;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -28;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33497:
			itemDef.name = "Valius helmet (T2)";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60619;
			itemDef.maleModel = 60620;
			itemDef.femaleModel = 60620;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33491:
			itemDef.name = "Wolfy (green)";
			itemDef.description = "none.";
			itemDef.modelId = 60597;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 15;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
			itemDef.originalModelColors = new int [] {11029, 11028, 11030, 11031, 11032, 11033, 11034, 926, 926, 926, 926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33492:
			itemDef.name = "Wolfy (blue)";
			itemDef.description = "none.";
			itemDef.modelId = 60597;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 15;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
			itemDef.originalModelColors = new int [] {-22766, -22768, -22769, -22770, -22771, -22772, -22773, 926, 926, 926, 926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33493:
			itemDef.name = "Wolfy (red)";
			itemDef.description = "none.";
			itemDef.modelId = 60597;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 15;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
			itemDef.originalModelColors = new int [] {-110, -111, -112, -113, -114, -115, -116, 926, 926, 926, 926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33494:
			itemDef.name = "Wolfy (black)";
			itemDef.description = "none.";
			itemDef.modelId = 60597;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 15;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
			itemDef.originalModelColors = new int [] {10, 11, 12, 13, 14, 15, 16, 926, 926, 926, 926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33495:
			itemDef.name = "Wolfy (gold)";
			itemDef.description = "none.";
			itemDef.modelId = 60597;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 15;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
			itemDef.originalModelColors = new int [] {9033, 9034, 9035 ,9036, 9037, 9038, 9039, 926, 926, 926, 926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33496:
			itemDef.name = "Tortle";
			itemDef.description = "Belongs to the legendary fox.";
			itemDef.modelId = 60618;
			itemDef.modelZoom = 7000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			

		case 33498:
			itemDef.name = "Dragon Pet Box";
			itemDef.description = "none.";
			itemDef.modelId = 60621;
			itemDef.modelZoom = 2640;
			itemDef.modelRotation1 = 114;
			itemDef.modelRotation2 = 1883;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;

		case 33499:
			itemDef.name = "Robin hood hat (Black)";
			itemDef.description = "A Robin hood hat dyed black.";
			itemDef.modelId = 60622;
			itemDef.maleModel = 60623;
			itemDef.femaleModel = 60624;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33500:
			itemDef.name = "Robin hood hat (Blue)";
			itemDef.description = "A Robin hood hat dyed blue.";
			itemDef.modelId = 60625;
			itemDef.maleModel = 60626;
			itemDef.femaleModel = 60627;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33501:
			itemDef.name = "Robin hood hat (Black)";
			itemDef.description = "A Robin hood hat dyed black.";
			itemDef.modelId = 60628;
			itemDef.maleModel = 60629;
			itemDef.femaleModel = 60630;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33502:
			itemDef.name = "Robin hood hat (Black)";
			itemDef.description = "A Robin hood hat dyed black.";
			itemDef.modelId = 60631;
			itemDef.maleModel = 60632;
			itemDef.femaleModel = 60633;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33503:
			itemDef.name = "Robin hood hat (Black)";
			itemDef.description = "A Robin hood hat dyed black.";
			itemDef.modelId = 60634;
			itemDef.maleModel = 60635;
			itemDef.femaleModel = 60636;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33504:
			itemDef.name = "Robin hood hat (Black)";
			itemDef.description = "A Robin hood hat dyed black.";
			itemDef.modelId = 60637;
			itemDef.maleModel = 60638;
			itemDef.femaleModel = 60639;
			itemDef.modelZoom = 650;
			itemDef.modelRotation1 = 2044;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
//			dinhs armour -
//			helm wear - 53129
//			helm drop - 53130
//			body wear - 53131
//			body drop - 53132
//			legs wear - 53133
//			legs drop - 53134	
		case 33505:
			itemDef.name = "Xeric's helmet";
			itemDef.description = "A helmet received in Chambers of Xeric.";
			itemDef.modelId = 53130;
			itemDef.maleModel = 53129;
			itemDef.femaleModel = 53129;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33506:
			itemDef.name = "Xeric's platebody";
			itemDef.description = "A platebody received in Chambers of Xeric.";
			itemDef.modelId = 53132;
			itemDef.maleModel = 53131;
			itemDef.femaleModel = 53131;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -2;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33507:
			itemDef.name = "Xeric's platelegs";
			itemDef.description = "A set of armored legs received in Chambers of Xeric.";
			itemDef.modelId = 53134;
			itemDef.maleModel = 53133;
			itemDef.femaleModel = 53133;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Drop" };
			// itemDef.aByte205 = 3;
			break;
		case 33508:
			itemDef.name = "Dice (up to 100)";
			itemDef.description = "A 100-sided dice.";
			itemDef.modelId = 60646;
			itemDef.modelZoom = 1104;
			itemDef.modelRotation2 = 215;
			itemDef.modelRotation1 = 94;
			itemDef.modelOffset2 = -10;
			itemDef.modelOffset1 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Public-roll";
			itemDef.inventoryOptions[2] = null;
			itemDef.name = "Dice (up to 100)";
			itemDef.anInt196 = 15;
			itemDef.anInt184 = 25;
			break;
			
		case 33509:
			itemDef.name = "Twisted jester mask";
			itemDef.description = "none.";
			itemDef.modelId = 60657;
			itemDef.maleModel = 60665;
			itemDef.femaleModel = 60658;
			itemDef.modelZoom = 1200;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33510:
			itemDef.name = "Twisted jester body";
			itemDef.description = "none.";
			itemDef.modelId = 60668;
			itemDef.maleModel = 60666;
			itemDef.femaleModel = 60667;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33511:
			itemDef.name = "Twisted jester legs";
			itemDef.description = "none.";
			itemDef.modelId = 60659;
			itemDef.maleModel = 60663;
			itemDef.femaleModel = 60664;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33512:
			itemDef.name = "Twisted jester boots";
			itemDef.description = "none.";
			itemDef.modelId = 60669;
			itemDef.maleModel = 60669;
			itemDef.femaleModel = 60669;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33513:
			itemDef.name = "Twisted jester gloves";
			itemDef.description = "You can upgrade this armor to T2 at 1500 total level.";
			itemDef.modelId = 60655;
			itemDef.maleModel = 60656;
			itemDef.femaleModel = 60656;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {8656, 10394};
			itemDef.originalModelColors = new int [] {41496, 12};
			// itemDef.aByte205 = 3;
			break;
			
		case 33514:
			itemDef.name = "Twisted jester cape";
			itemDef.description = "none.";
			itemDef.modelId = 60654;
			itemDef.maleModel = 60662;
			itemDef.femaleModel = 60662;
			itemDef.modelZoom = 2500;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33515:
			itemDef.name = "Sack of effigys";
			itemDef.description = "increases the chances of receiving an Elite cluescroll or Master clue piece.";
			itemDef.modelId = 60660;
			itemDef.maleModel = 60661;
			itemDef.femaleModel = 60661;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 948;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 6;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 5;
			break;
			
		case 33516:
			itemDef.name = "Ancient platebody";
			itemDef.description = "Ancient platebody.";
			itemDef.modelId = 60366;
			itemDef.maleModel = 60367;
			itemDef.femaleModel = 60367;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33517:
			itemDef.name = "Ancient platelegs";
			itemDef.description = "Ancient platelegs.";
			itemDef.modelId = 60368;
			itemDef.maleModel = 60369;
			itemDef.femaleModel = 60369;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33518:
			itemDef.name = "Ancient boots";
			itemDef.description = "Ancient boots.";
			itemDef.modelId = 60372;
			itemDef.maleModel = 60372;
			itemDef.femaleModel = 60372;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33519:
			itemDef.name = "Ancient gloves";
			itemDef.description = "Ancient gloves.";
			itemDef.modelId = 60370;
			itemDef.maleModel = 60371;
			itemDef.femaleModel = 60371;
			itemDef.modelZoom = 855;
			itemDef.modelRotation1 = 215;
			itemDef.modelRotation2 = 94;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = -32;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33520:
			itemDef.name = "Ancient helmet";
			itemDef.description = "Ancient helmet.";
			itemDef.modelId = 60364;
			itemDef.maleModel = 60365;
			itemDef.femaleModel = 60365;
			itemDef.modelZoom = 1010;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33521:
			itemDef.name = "Slayer key (tier 1)";
			itemDef.description = "Use this at the slayer chest at home.";
			itemDef.modelId = 35039;
			itemDef.modelZoom = 1296;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 20;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33522:
			itemDef.name = "Slayer key (tier 2)";
			itemDef.description = "Use this at the slayer chest at home.";
			itemDef.modelId = 35039;
			itemDef.modelZoom = 1296;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 20;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {127};
			itemDef.originalModelColors = new int [] {926};
			// itemDef.aByte205 = 3;
			break;
			
		case 33523:
			itemDef.name = "Slayer key (tier 3)";
			itemDef.description = "Use this at the slayer chest at home.";
			itemDef.modelId = 35039;
			itemDef.modelZoom = 1296;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 20;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {127};
			itemDef.originalModelColors = new int [] {46782};
			// itemDef.aByte205 = 3;
			break;
			
		case 33524:
			itemDef.name = "Slayer key (tier 4)";
			itemDef.description = "Use this at the slayer chest at home.";
			itemDef.modelId = 35039;
			itemDef.modelZoom = 1296;
			itemDef.modelRotation1 = 471;
			itemDef.modelRotation2 = 20;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int [] {127};
			itemDef.originalModelColors = new int [] {14264};
			// itemDef.aByte205 = 3;
			break;
			
				case 33330:
				itemDef.name = "Fire shield";
				itemDef.description = "A large metal shield.";
				itemDef.modelId = 60362;
				itemDef.maleModel = 60363;
				itemDef.femaleModel = 60363;
				itemDef.modelZoom = 1488;
				itemDef.modelRotation1 = 276;
				itemDef.modelRotation2 = 1101;
				itemDef.modelOffset1 = -5;
				itemDef.modelOffset2 = 0;
				itemDef.inventoryOptions = new String[5];
				itemDef.inventoryOptions[1] = "Wear";
				itemDef.inventoryOptions[2] = null;
				break;
			
				case 33525:
					itemDef.name = "Cursed Twisted bow";
					itemDef.description = "a Twisted bow with a curse.";
					itemDef.modelId = 32799;
					itemDef.maleModel = 32674;
					itemDef.femaleModel = 32674;
					itemDef.modelZoom = 2000;
					itemDef.modelRotation1 = 720;
					itemDef.modelRotation2 = 1500;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.originalTextureColors = new int[] { 10318, 10334 };
					itemDef.modifiedTextureColors = new int[] { 82, 82 };
					itemDef.modifiedModelColors = new int[] { 14236, 13223 };
					itemDef.originalModelColors = new int[] { 49830, 49830 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33337:
					itemDef.name = "Bleeding Broadsider sword";
					itemDef.description = "none.";
					itemDef.modelId = 60373;
					itemDef.maleModel = 60374;
					itemDef.femaleModel = 60374;
					itemDef.modelZoom = 1900;
					itemDef.modelRotation1 = 1549;
					itemDef.modelRotation2 = 1791;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33338:
					itemDef.name = "Tetsu helmet";
					itemDef.description = "Tetsu helmet.";
					itemDef.modelId = 60379;
					itemDef.maleModel = 60380;
					itemDef.femaleModel = 60380;
					itemDef.modelZoom = 1010;
					itemDef.modelRotation1 = 16;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33339:
					itemDef.name = "Tetsu platebody";
					itemDef.description = "Tetsu platebody.";
					itemDef.modelId = 60375;
					itemDef.maleModel = 60376;
					itemDef.femaleModel = 60376;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 566;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
				case 33340:
					itemDef.name = "Tetsu platelegs";
					itemDef.description = "Tetsu platelegs.";
					itemDef.modelId = 60377;
					itemDef.maleModel = 60378;
					itemDef.femaleModel = 60378;
					itemDef.modelZoom = 1753;
					itemDef.modelRotation1 = 562;
					itemDef.modelRotation2 = 1;
					itemDef.modelOffset1 = 11;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33158:
					itemDef.name = "Dagon' hai top";
					itemDef.description = "An elite dark mages robes.";
					itemDef.modelId = 60317;
					itemDef.maleModel = 43614;
					itemDef.femaleModel = 43689;
					itemDef.anInt188 = 44594;
					itemDef.anInt164 = 43681;
					itemDef.modelZoom = 1697;
					itemDef.modelRotation1 = 536;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 7;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
				case 33159:
					itemDef.name = "Dagon' hai hat";
					itemDef.description = "An elite dark mages hat.";
					itemDef.modelId = 60319;
					itemDef.maleModel = 60318;
					itemDef.femaleModel = 60318;
					itemDef.anInt188 = -1;
					itemDef.anInt164 = -1;
					itemDef.modelZoom = 1373;
					itemDef.modelRotation1 = 98;
					itemDef.modelRotation2 = 1988;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
				case 33160:
					itemDef.name = "Dagon' hai robe";
					itemDef.description = "An elite dark mages robe.";
					itemDef.modelId = 60321;
					itemDef.maleModel = 60320;
					itemDef.femaleModel = 60320;
					itemDef.anInt188 = -1;
					itemDef.anInt164 = -1;
					itemDef.modelZoom = 2216;
					itemDef.modelRotation1 = 572;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 14;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;//ancestral^^^
					
				case 21347:
					itemDef.inventoryOptions = new String[] { null, "enable Arrow-Tips", "enable Bolt-Tips", "enable Javelin-Heads", "Drop" };
					break;
					
				case 33526://60673
					itemDef.name = "Abyssal Whip (Blood)";
					itemDef.description = "Perk Effects: 1/? chance to recover 50% of your hit to your HP.";
					itemDef.modelId = 5455;
					itemDef.maleModel = 8950;
					itemDef.femaleModel = 8950;
					itemDef.modelZoom = 900;
					itemDef.modelRotation1 = 280;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
				case 33527:
					itemDef.name = "@pur@Perk: Blood";
					itemDef.description = "Perk Effects: 1:5 chance to recover 50% of your hit to your HP.";
					itemDef.modelId = 60673;
					itemDef.maleModel = 8950;
					itemDef.femaleModel = 8950;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset1 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33528:
					itemDef.name = "Annihilation";
					itemDef.description = "has a 1:8 chance to go Berserk while fighting demons and boosts damage by 50%";
					itemDef.modelId = 60700;
					itemDef.maleModel = 60701;
					itemDef.femaleModel = 60701;
					itemDef.modelZoom = 1616;
					itemDef.modelRotation1 = 273;
					itemDef.modelRotation2 = 213;
					itemDef.modelOffset1 = -5;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33529:
					itemDef.name = "Decimation";
					itemDef.description = "has a 1:8 chance to go Berserk while fighting demons and boosts damage by 50%";
					itemDef.modelId = 60702;
					itemDef.maleModel = 60703;
					itemDef.femaleModel = 60703;
					itemDef.modelZoom = 1382;
					itemDef.modelRotation1 = 148;
					itemDef.modelRotation2 = 808;
					itemDef.modelOffset1 = 5;
					itemDef.modelOffset2 = -13;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33530:
					itemDef.name = "Obliteration";
					itemDef.description = "has a 1:8 chance to go Berserk while fighting demons and boosts damage by 50%";
					itemDef.modelId = 60704;
					itemDef.maleModel = 60705;
					itemDef.femaleModel = 60705;
					itemDef.modelZoom = 2829;
					itemDef.modelRotation1 = 310;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 4;
					itemDef.modelOffset2 = 4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33531:
					itemDef.name = "Boogie Bow";
					itemDef.description = "has a chance to double hit the enemy + a special attack to hit 4x";
					itemDef.modelId = 60707;
					itemDef.maleModel = 60706;
					itemDef.femaleModel = 60706;
					itemDef.modelZoom = 3298;
					itemDef.modelRotation1 = 364;
					itemDef.modelRotation2 = 1266;
					itemDef.modelOffset1 = -20;
					itemDef.modelOffset2 = 36;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33532:
					itemDef.name = "Royal bolts";
					itemDef.description = "none.";
					itemDef.modelId = 60708;
					itemDef.modelZoom = 1220;
					itemDef.modelRotation1 = 216;
					itemDef.modelRotation2 = 100;
					itemDef.modelOffset1 = 6;
					itemDef.modelOffset2 = -29;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33533:
					itemDef.name = "Royal crossbow (unforged)";
					itemDef.description = "none.";
					itemDef.modelId = 60709;
					itemDef.modelZoom = 1250;
					itemDef.modelRotation1 = 269;
					itemDef.modelRotation2 = 2007;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 29;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33534:
					itemDef.name = "Royal crossbow";
					itemDef.description = "none.";
					itemDef.modelId = 60710;
					itemDef.maleModel = 60711;
					itemDef.femaleModel = 60711;
					itemDef.modelZoom = 1250;
					itemDef.modelRotation1 = 269;
					itemDef.modelRotation2 = 2007;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 29;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -2;
					itemDef.femaleOffset = -2;
					break;
					
				case 33535:
					itemDef.name = "Obliteration (Blood)";
					itemDef.description = "@pur@Perk: A 1:5 chance to heal you for 50% of your hit.";
					itemDef.modelId = 60725;
					itemDef.maleModel = 60720;
					itemDef.femaleModel = 60720;
					itemDef.modelZoom = 2829;
					itemDef.modelRotation1 = 310;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 4;
					itemDef.modelOffset2 = 4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -4;
					itemDef.femaleOffset = -4;
					break;
					
				case 33536:
					itemDef.name = "Boogie Bow (Blood)";
					itemDef.description = "@pur@Perk: A 1:5 chance to heal you for 50% of your hits.";
					itemDef.modelId = 60726;
					itemDef.maleModel = 60723;
					itemDef.femaleModel = 60723;
					itemDef.modelZoom = 3298;
					itemDef.modelRotation1 = 364;
					itemDef.modelRotation2 = 1266;
					itemDef.modelOffset1 = -20;
					itemDef.modelOffset2 = 36;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33537:
					itemDef.name = "Necrolord staff (Blood)";
					itemDef.description = "none.";
					itemDef.modelId = 60213;
					itemDef.maleModel = 60212;
					itemDef.femaleModel = 60212;
					itemDef.modelZoom = 1900;
					itemDef.modelRotation1 = 1549;
					itemDef.modelRotation2 = 1791;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33538:
					itemDef.name = "Siren Bow";
					itemDef.description = "none.";
					itemDef.modelId = 60726;
					itemDef.maleModel = 60727;
					itemDef.femaleModel = 60728;
					itemDef.modelZoom = 3298;
					itemDef.modelRotation1 = 364;
					itemDef.modelRotation2 = 1266;
					itemDef.modelOffset1 = -20;
					itemDef.modelOffset2 = 36;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33539:
					itemDef.name = "Starter Cape";
					itemDef.description = "Raised the damage of melee weapons by 3% while worn.";
					itemDef.modelId = 60741;
					itemDef.maleModel = 60742;
					itemDef.femaleModel = 60743;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 279;
					itemDef.modelRotation2 = 948;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = 24;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 6;
					itemDef.femaleOffset = 6;
					break;
					
				case 33540:
					itemDef.name = "Starter Hood";
					itemDef.description = "Raises accuracy of melee weapons by 2% while worn.";
					itemDef.modelId = 60744;
					itemDef.maleModel = 60745;
					itemDef.femaleModel = 60746;
					itemDef.modelZoom = 760;
					itemDef.modelRotation1 = 11;
					itemDef.modelRotation2 = 81;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 1;
					itemDef.femaleOffset = 1;
					break;
					
				case 33351:
					itemDef.name = "Primal boots";
					itemDef.description = "none.";
					itemDef.modelId = 60412;
					itemDef.maleModel = 60413;
					itemDef.femaleModel = 60413;
					itemDef.modelZoom = 789;
					itemDef.modelRotation1 = 164;
					itemDef.modelRotation2 = 156;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33352:
					itemDef.name = "Primal kiteshield";
					itemDef.description = "none.";
					itemDef.modelId = 60415;
					itemDef.maleModel = 60416;
					itemDef.femaleModel = 60416;
					itemDef.modelZoom = 2434;
					itemDef.modelRotation1 = 458;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = 9;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33355:
					itemDef.name = "Primal platelegs";
					itemDef.description = "none.";
					itemDef.modelId = 60421;
					itemDef.maleModel = 60422;
					itemDef.femaleModel = 60422;
					itemDef.modelZoom = 1730;
					itemDef.modelRotation1 = 525;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 7;
					itemDef.modelOffset2 = 3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33356:
					itemDef.name = "Primal full helm";
					itemDef.description = "none.";
					itemDef.modelId = 60424;
					itemDef.maleModel = 60425;
					itemDef.femaleModel = 60425;
					itemDef.modelZoom = 921;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
					
				case 33357:
					itemDef.name = "Primal gloves";
					itemDef.description = "none.";
					itemDef.modelId = 60427;
					itemDef.maleModel = 60428;
					itemDef.femaleModel = 60428;
					itemDef.modelZoom = 930;
					itemDef.modelRotation1 = 420;
					itemDef.modelRotation2 = 828;
					itemDef.modelOffset1 = 3;
					itemDef.modelOffset2 = -7;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					//itemDef.aByte205 = 3;
					break;
					
				case 33358:
					itemDef.name = "Primal platebody";
					itemDef.description = "none.";
					itemDef.modelId = 60429;
					itemDef.maleModel = 60430;
					itemDef.femaleModel = 60430;
					itemDef.modelZoom = 1447;
					itemDef.modelRotation1 = 485;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 13;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
					
				case 33542:
					itemDef.name = "Stargaze Ring (Melee)";
					itemDef.description = "This ring provides a 10% Droprate boost while fighting Wyrms + a chance for the pet!";
					itemDef.modelId = 60758;
					itemDef.modelZoom = 400;
					itemDef.modelRotation1 = 700;
					itemDef.modelRotation2 = 1108;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33543:
					itemDef.name = "Stargaze Ring (Range)";
					itemDef.description = "This ring provides a 10% Droprate boost while fighting Wyrms + a chance for the pet!";
					itemDef.modelId = 60759;
					itemDef.modelZoom = 400;
					itemDef.modelRotation1 = 700;
					itemDef.modelRotation2 = 1108;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33544:
					itemDef.name = "Stargaze Ring (Magic)";
					itemDef.description = "This ring provides a 10% Droprate boost while fighting Wyrms + a chance for the pet!";
					itemDef.modelId = 60760;
					itemDef.modelZoom = 400;
					itemDef.modelRotation1 = 700;
					itemDef.modelRotation2 = 1108;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					// itemDef.aByte205 = 3;
					break;
					
				case 33545:
					itemDef.name = "Starsprite pickaxe";
					itemDef.description = "This pickaxe provides a 3% XP boost while mining any rock.";
					itemDef.modelId = 60765;
					itemDef.maleModel = 60766;
					itemDef.femaleModel = 60766;
					itemDef.modelZoom = 1512;
					itemDef.modelRotation1 = 1368;
					itemDef.modelRotation2 = 1650;
					itemDef.modelOffset1 = -8;
					itemDef.modelOffset2 = 12;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					// itemDef.aByte205 = 3;
					break;
					
				case 33546:
					itemDef.name = "Stargaze pickaxe";
					itemDef.description = "This pickaxe provides a 6% xp boost while mining any rock.";
					itemDef.modelId = 60767;
					itemDef.maleModel = 60768;
					itemDef.femaleModel = 60768;
					itemDef.modelZoom = 1512;
					itemDef.modelRotation1 = 1368;
					itemDef.modelRotation2 = 1650;
					itemDef.modelOffset1 = -8;
					itemDef.modelOffset2 = 12;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					// itemDef.aByte205 = 3;
					break;
					
				case 33547:
					itemDef.name = "Stargaze Pickaxe (Perk)";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.certID = 33546;
					itemDef.certTemplateID = 33921;
					itemDef.modelZoom = 1512;
					itemDef.modelRotation1 = 1368;
					itemDef.modelRotation2 = 1650;
					itemDef.modelOffset1 = -8;
					itemDef.modelOffset2 = 12;
					itemDef.description = "use this on the Crystal pickaxe to create the Stargaze pickaxe!";
					break;
					
				case 33548:
					itemDef.name = "Scoop";
					itemDef.description = "A pet exclusively from the Limited Time shop!";
					itemDef.modelId = 65486;
					itemDef.modelZoom = 2250;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33549:
					itemDef.name = "Mini solomon";
					itemDef.description = "A pet exclusively from the Limited Time shop!";
					itemDef.modelId = 60670;
					itemDef.modelZoom = 2500;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33551:
					itemDef.name = "Strykebow";
					itemDef.description = "Can be charged during combat to release a devistating attack.";
					itemDef.modelId = 60872;
					itemDef.maleModel = 60871;
					itemDef.femaleModel = 60871;
					itemDef.modelZoom = 2000;
					itemDef.modelRotation1 = 339;
					itemDef.modelRotation2 = 1818;
					itemDef.modelOffset1 = -5;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wield";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33552:
					itemDef.name = "Stryke whip";
					itemDef.description = "Can be charged during combat to release a devistating attack.";
					itemDef.modelId = 60870;
					itemDef.maleModel = 60869;
					itemDef.femaleModel = 60869;
					itemDef.modelZoom = 900;
					itemDef.modelRotation1 = 280;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33553:
					itemDef.name = "Staff of darkness";
					itemDef.description = "Can be charged during combat to release a devistating attack.";
					itemDef.modelId = 60856;
					itemDef.maleModel = 60855;
					itemDef.femaleModel = 60855;
					itemDef.modelZoom = 4178;
					itemDef.modelRotation1 = 444;
					itemDef.modelRotation2 = 1259;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = -2;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33554:
					itemDef.name = "Wyrm scalp";
					itemDef.description = "Use this on a Dark bow to create the Strykebow!";
					itemDef.modelId = 60874;
					itemDef.modelZoom = 976;
					itemDef.modelRotation1 = 498;
					itemDef.modelRotation2 = 1993;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33555:
					itemDef.name = "Wyrm Heart";
					itemDef.description = "Use this on a Staff of light to create the Staff of darkness!";
					itemDef.modelId = 60873;
					itemDef.modelZoom = 789;
					itemDef.modelRotation1 = 273;
					itemDef.modelRotation2 = 1283;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = -46;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33556:
					itemDef.name = "Searing ashes";
					itemDef.description = "Ashes of a Strykewyrm.";
					itemDef.modelId = 2742;
					itemDef.modelZoom = 1210;
					itemDef.modelRotation1 = 264;
					itemDef.modelRotation2 = 1892;
					itemDef.modelOffset1 = -6;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[0] = "Scatter";
					itemDef.inventoryOptions[2] = null;
					itemDef.modifiedModelColors = new int [] {82};
					itemDef.originalModelColors = new int [] {926};
					// itemDef.aByte205 = 3;
					break;
					
				case 33557:
					itemDef.name = "Searing ashes";
					itemDef.toNote();
					itemDef.description = "Ashes of a Strykewyrm noted.";
					itemDef.modelId = 2742;
					itemDef.modelZoom = 1210;
					itemDef.modelRotation1 = 264;
					itemDef.modelRotation2 = 1892;
					itemDef.modelOffset1 = -6;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[2] = null;
					itemDef.modifiedModelColors = new int [] {82};
					itemDef.originalModelColors = new int [] {926};
					// itemDef.aByte205 = 3;
					break;
					
				case 33558:
					itemDef.name = "Valius cape";
					itemDef.description = "A cape created for Valius players.";
					itemDef.modelId = 60843;
					itemDef.maleModel = 60843;
					itemDef.femaleModel = 60843;
					itemDef.modelZoom = 1416;
					itemDef.modelRotation1 = 252;
					itemDef.modelRotation2 = 1020;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = 24;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 0;
					itemDef.femaleOffset = 0;
					break;
					
				case 33559:
					itemDef.name = "Stunning Broadsider sword";
					itemDef.description = "none.";
					itemDef.modelId = 60373;
					itemDef.maleModel = 60374;
					itemDef.femaleModel = 60374;
					itemDef.modelZoom = 1900;
					itemDef.modelRotation1 = 1549;
					itemDef.modelRotation2 = 1791;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33560:
					itemDef.name = "Venombite Blade";
					itemDef.description = "none.";
					itemDef.modelId = 58110;
					itemDef.maleModel = 58110;
					itemDef.femaleModel = 58110;
					itemDef.modelZoom = 500;
					itemDef.modelRotation1 = 1549;
					itemDef.modelRotation2 = 1791;
					itemDef.modelOffset1 = 30;
					itemDef.modelOffset2 = -10;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = -7;
					itemDef.femaleOffset = -7;
					break;
					
				case 33561:
					itemDef.name = "Corrupt longsword";
					itemDef.description = "A corrupt longsword, enchanted by Zamorak himself.";
					itemDef.modelId = 60880;
					itemDef.maleModel = 60879;
					itemDef.femaleModel = 60879;
					itemDef.modelZoom = 1900;
					itemDef.modelRotation1 = 1549;
					itemDef.modelRotation2 = 1791;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33562:
					itemDef.name = "Frostbite Twisted bow";
					itemDef.description = "a Twisted bow embeded with the power of the Galaxy.";
					itemDef.modelId = 32799;
					itemDef.maleModel = 32674;
					itemDef.femaleModel = 32674;
					itemDef.modelZoom = 2000;
					itemDef.modelRotation1 = 720;
					itemDef.modelRotation2 = 1500;
					itemDef.modelOffset1 = -3;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.originalTextureColors = new int[] { 10318, 10334 };
					itemDef.modifiedTextureColors = new int[] { 63, 63 };
					itemDef.modifiedModelColors = new int[] { 14236, 13223 };
					itemDef.originalModelColors = new int[] { 37693, 37693 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33563:
					itemDef.name = "Frostbite Grotesque platebody";
					itemDef.description = "Grosteq platebody.";
					itemDef.modelId = 60347;
					itemDef.maleModel = 60348;
					itemDef.femaleModel = 60348;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 566;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
				case 33564:
					itemDef.name = "Frostbite Grotesque platelegs";
					itemDef.description = "Grosteq platelegs.";
					itemDef.modelId = 60349;
					itemDef.maleModel = 60350;
					itemDef.femaleModel = 60350;
					itemDef.modelZoom = 1753;
					itemDef.modelRotation1 = 562;
					itemDef.modelRotation2 = 1;
					itemDef.modelOffset1 = 11;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
				case 33565:
					itemDef.name = "Frostbite Grotesque helmet";
					itemDef.description = "Grosteqc helmet.";
					itemDef.modelId = 60345;
					itemDef.maleModel = 60346;
					itemDef.femaleModel = 60346;
					itemDef.modelZoom = 1010;
					itemDef.modelRotation1 = 16;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
					
				case 33566:
					itemDef.name = "Twisted bow upgrade kit";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the Twisted bow to create the Superior twisted bow!";
					break;
					
				case 33567:
					itemDef.name = "Grotesque platebody";
					itemDef.description = "Grosteq platebody.";
					itemDef.modelId = 60347;
					itemDef.maleModel = 60348;
					itemDef.femaleModel = 60348;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 566;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
				case 33568:
					itemDef.name = "Grotesque platelegs";
					itemDef.description = "Grosteq platelegs.";
					itemDef.modelId = 60349;
					itemDef.maleModel = 60350;
					itemDef.femaleModel = 60350;
					itemDef.modelZoom = 1753;
					itemDef.modelRotation1 = 562;
					itemDef.modelRotation2 = 1;
					itemDef.modelOffset1 = 11;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
				case 33569:
					itemDef.name = "Grotesque helmet";
					itemDef.description = "Grosteqc helmet.";
					itemDef.modelId = 60345;
					itemDef.maleModel = 60346;
					itemDef.femaleModel = 60346;
					itemDef.modelZoom = 1010;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -4;
					itemDef.modelRotation2 = 122;
					itemDef.modelRotation1 = 49;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.maleOffset = 3;
					break;
					
				case 33570:
					itemDef.name = "Frostbite perk (Twisted bow)";
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					itemDef.certID = 33562;
					itemDef.certTemplateID = 33921;
					itemDef.description = "use this on the Twisted bow to add the Frostbite perk!";
					break;
					
				case 33571:
					itemDef.name = "Frostbite perk (Grotesque)";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "Use this to add the frostbite perk to a single piece of Grotesque armor!";
					break;
					
				case 33572:
					itemDef.name = "Stunning Perk (Broadsider sword)";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "Use this to add the Stunning perk to a broadsider sword!";
					break;
					
				case 33573:
					itemDef.name = "Infernal perk (Void longsword)";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "Use this to add the Infernal perk to a Void longsword!";
					break;
					
					
				case 33574:
					itemDef.name = "Primal Guardian";
					itemDef.description = "Darren's custom pet.";
					itemDef.modelId = 60931;
					itemDef.modelZoom = 6800;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 270;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33575:
					itemDef.name = "Bad rocky";
					itemDef.description = "Life Hax's custom pet.";
					itemDef.modelId = 60920;
					itemDef.modelZoom = 2010;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.modelRotation2 = 343;
					itemDef.modelRotation1 = 86;
					itemDef.modelRotationY = 48;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33576:
					itemDef.name = "Pet wildy strykewyrm";
					itemDef.description = "A pet dropped by a wildy strykewyrm.";
					itemDef.modelId = 60835;
					itemDef.modelZoom = 7800;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 270;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33577:
					itemDef.name = "Pet ice strykewyrm";
					itemDef.description = "A pet dropped by a ice strykewyrm.";
					itemDef.modelId = 60833;
					itemDef.modelZoom = 7800;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 270;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33578:
					itemDef.name = "Blood Twisted crossbow";
					itemDef.description = "Twisted crossbow.";
					itemDef.modelId = 60939;
					itemDef.maleModel = 60938;
					itemDef.femaleModel = 60938;
					itemDef.modelZoom = 926;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 3;
					itemDef.modelRotation2 = 244;
					itemDef.modelRotation1 = 443;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33579:
					itemDef.name = "Odin's raven";
					itemDef.description = "Odin's custom pet.";
					itemDef.modelId = 60882;
					itemDef.modelZoom = 6800;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 270;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33580:
					itemDef.name = "Infernal Max cape (Purple)";//blue and green as well
					itemDef.description = "A Maxcape combined with an Infernal Dye.";
					itemDef.modelId = 33145;
					itemDef.femaleModel = 33114;
					itemDef.maleModel = 33102;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33581:
					itemDef.name = "Blood Twisted Crossbow (Perk)";
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					itemDef.certID = 33578;
					itemDef.certTemplateID = 33921;
					itemDef.description = "A Skin for the Twisted crossbow that offers healing abilities.";
					break;
					
				case 33582:
					itemDef.name = "Bloodknight Helmet";
					itemDef.description = "A Helmet with a terrifying skeletal face!";
					itemDef.modelId = 60925;
					itemDef.maleModel = 60926;
					itemDef.femaleModel = 60923;
					itemDef.modelZoom = 863;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -7;
					itemDef.modelRotation2 = 184;
					itemDef.modelRotation1 = 24;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33583:
					itemDef.name = "Bloodknight platebody";
					itemDef.description = "A bloodknights platebody.";
					itemDef.modelId = 60930;
					itemDef.maleModel = 60934;
					itemDef.femaleModel = 60922;
					itemDef.modelZoom = 1240;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 16;
					itemDef.modelRotation2 = 0;
					itemDef.modelRotation1 = 528;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33584:
					itemDef.name = "Bloodknight platelegs";
					itemDef.description = "A bloodknights platelegs.";
					itemDef.modelId = 60927;
					itemDef.maleModel = 60928;
					itemDef.femaleModel = 60924;
					itemDef.modelZoom = 1620;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 0;
					itemDef.modelRotation2 = 0;
					itemDef.modelRotation1 = 528;
					itemDef.modelRotationY = 24;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33585:
					itemDef.name = "Bloodknight Whip";
					itemDef.description = "A bloodknights Weapon.";
					itemDef.modelId = 60929;
					itemDef.maleModel = 60935;
					itemDef.femaleModel = 60935;
					itemDef.modelZoom = 1875;
					itemDef.modelOffset1 = 25;
					itemDef.modelOffset2 = 148;
					itemDef.modelRotation2 = 1946;
					itemDef.modelRotation1 = 306;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33586:
					itemDef.name = "Corrupt Void Longsword";
					itemDef.description = "The blade of a corrupt Void knight.";
					itemDef.modelId = 60937;
					itemDef.maleModel = 60936;
					itemDef.femaleModel = 60936;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33587:
					itemDef.name = "Battlemage helm";
					itemDef.description = "";
					itemDef.modelId = 44704;
					itemDef.maleModel = 44767;
					itemDef.femaleModel = 45219;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
			
				case 33588:
					itemDef.name = "Battlemage top";
					itemDef.description = "";
					itemDef.modelId = 44631;
					itemDef.maleModel = 44818;
					itemDef.femaleModel = 45279;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33589:
					itemDef.name = "Battlemage bottoms";
					itemDef.description = "";
					itemDef.modelId = 44672;
					itemDef.maleModel = 44775;
					itemDef.femaleModel = 45234;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33590:
					itemDef.name = "Battlemage gloves";
					itemDef.description = "";
					itemDef.modelId = 44672;
					itemDef.maleModel = 44762;
					itemDef.femaleModel = 60936;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33591:
					itemDef.name = "Battlemage boots";
					itemDef.description = "";
					itemDef.modelId = 44672;
					itemDef.maleModel = 45204;
					itemDef.femaleModel = 60936;
					itemDef.modelZoom = 2048;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33592:
	                itemDef.name = "Nightmare key";
	                itemDef.description = "Droppped by the Nightmare boss. Used to open the Nightmare chest.";
	                itemDef.modelId = 60961;
	                itemDef.modelZoom = 710;
	                itemDef.modelRotation1 = 348;
	                itemDef.modelRotation2 = 55;
	                itemDef.modelOffset1 = -1;
	                itemDef.modelOffset2 = 0;
	                itemDef.inventoryOptions = new String[5];
	                itemDef.inventoryOptions[1] = null;
	                itemDef.inventoryOptions[2] = null;
	                break;
	                
			    case 33593:
	                itemDef.name = "Pet Nightmare";
	                itemDef.description = "Droppped by the Nightmare boss.";
	                itemDef.modelId = 60959;
	                itemDef.modelZoom = 6000;
	                itemDef.modelRotation1 = 348;
	                itemDef.modelRotation2 = 55;
	                itemDef.modelOffset1 = -1;
	                itemDef.modelOffset2 = 0;
	                itemDef.inventoryOptions = new String[5];
	                itemDef.inventoryOptions[1] = null;
	                itemDef.inventoryOptions[2] = null;
	                break;
	                
			    case 33594:
					itemDef.name = "Toxic Noxious bow";
					itemDef.description = "1:3 chance to cause 5-12 Venom damage for the duriation of a fight.";
					itemDef.modelId = 60973;
					itemDef.maleModel = 60983;
					itemDef.femaleModel = 60983;
					itemDef.modelZoom = 2400;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -13;
					itemDef.modelRotation2 = 781;
					itemDef.modelRotation1 = 466;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33595:
					itemDef.name = "Toxic Noxious scythe";
					itemDef.description = "1:3 chance to cause 5-12 Venom damage for the duriation of a fight.";
					itemDef.modelId = 60976;
					itemDef.maleModel = 60977;
					itemDef.femaleModel = 60977;
					itemDef.modelZoom = 2700;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = 61;
					itemDef.modelRotation2 = 656;
					itemDef.modelRotation1 = 271;
					itemDef.modelRotationY = 365;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33597:
					itemDef.name = "Toxic Noxious helmet";
					itemDef.description = "1-3 Toxic Thorn damage per piece equipped when an monster hits a player.";
					itemDef.modelId = 60974;
					itemDef.maleModel = 60975;
					itemDef.femaleModel = 60975;
					itemDef.modelZoom = 1010;
					itemDef.modelRotation1 = 16;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33598:
					itemDef.name = "Toxic Noxious platebody";
					itemDef.description = "1-3 Toxic Thorn damage per piece equipped when an monster hits a player.";
					itemDef.modelId = 60978;
					itemDef.maleModel = 60979;
					itemDef.femaleModel = 60979;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 566;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33599:
					itemDef.name = "Toxic Noxious platelegs";
					itemDef.description = "1-3 Toxic Thorn damage per piece equipped when an monster hits a player.";
					itemDef.modelId = 60980;
					itemDef.maleModel = 60981;
					itemDef.femaleModel = 60981;
					itemDef.modelZoom = 1753;
					itemDef.modelRotation1 = 562;
					itemDef.modelRotation2 = 1;
					itemDef.modelOffset1 = 11;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
			    case 33600:
					itemDef.name = "Toxic Noxious snake";
					itemDef.description = "A Snake with a 10% Boost to droprates.";
					itemDef.modelId = 61193;
					itemDef.modelZoom = 2048;
					itemDef.modelRotation1 = 270;
					itemDef.modelRotation2 = 537;
					itemDef.modelOffset1 = 417;
					itemDef.modelOffset2 = 123;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = null;
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33601:
					itemDef.name = "Group Ironman helmet";
					itemDef.description = "A helmet to indicate you are a group ironman player.";
					itemDef.modelId = 60971;
					itemDef.maleModel = 60972;
					itemDef.femaleModel = 60965;
					itemDef.modelZoom = 1135;
					itemDef.modelOffset1 = -16;
					itemDef.modelOffset2 = 6;
					itemDef.modelRotation2 = 1041;
					itemDef.modelRotation1 = 1537;
					itemDef.modelRotationY = 866;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;

				case 33602:
					itemDef.name = "Group Ironman platebody";
					itemDef.description = "A playebody to indicate you are a group ironman player";
					itemDef.modelId = 60970;
					itemDef.maleModel = 60964;
					itemDef.anInt188 = 60963;
					itemDef.anInt164 = 60967;
					itemDef.femaleModel = 60968;
					itemDef.modelZoom = 1513;
					itemDef.modelRotation1 = 566;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 2;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
				case 33603:
					itemDef.name = "Group Ironman platelegs";
					itemDef.description = "A pair of platelegs to indicate you are a group ironman player";
					itemDef.modelId = 60969;
					itemDef.maleModel = 60962;
					itemDef.femaleModel = 60966;
					itemDef.modelZoom = 1763;
					itemDef.modelOffset1 = -5;
					itemDef.modelOffset2 = -2;
					itemDef.modelRotation2 = 1;
					itemDef.modelRotation1 = 562;
					itemDef.modelRotationY = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
					
				case 33604:
					itemDef.name = "3rd age platelegs (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modelId = 20223;
					itemDef.maleModel = 20154;
					itemDef.femaleModel = 20206;
					itemDef.anInt188 = 20120;
					itemDef.anInt164 = 20172;
					itemDef.modelZoom = 1190;
					itemDef.modelRotation1 = 476;
					itemDef.modelRotation2 = 0;
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 7117 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33605:
					itemDef.name = "3rd age platebody (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modelId = 20223;
					itemDef.maleModel = 20154;
					itemDef.femaleModel = 20206;
					itemDef.anInt188 = 20120;
					itemDef.anInt164 = 20172;
					itemDef.modelZoom = 1190;
					itemDef.modelRotation1 = 476;
					itemDef.modelRotation2 = 0;
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 7117 };
					// itemDef.aByte205 = 3;
					break;
			
				case 33606:
					itemDef.name = "3rd age full helmet (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33607:
					itemDef.name = "3rd age kiteshield (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33608:
					itemDef.name = "3rd age range top (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.copyOf(forID(10330));
					itemDef.modifiedModelColors = new int[] { 3239, 3243, 3235, 3247 };
					itemDef.originalModelColors = new int[] { 8622, 8622, 8622, 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33609:
					itemDef.name = "3rd age range legs (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 3239, 3243, 3235, 3247 };
					itemDef.originalModelColors = new int[] { 8622, 8622, 8622, 8622 };
					// itemDef.aByte205 = 3;
					break;
			
				case 33610:
					itemDef.name = "3rd age range coif (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 3239, 3243, 3235, 3247 };
					itemDef.originalModelColors = new int[] { 8622, 8622, 8622, 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33611:
					itemDef.name = "3rd age robe top (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 0 };
					itemDef.originalModelColors = new int[] { 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33612:
					itemDef.name = "3rd age robe (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 8622 };
					// itemDef.aByte205 = 3;
					break;
			
				case 33613:
					itemDef.name = "3rd age mage hat (superior)";
					itemDef.description = "A superior version of 3rd age";
					itemDef.modifiedModelColors = new int[] { 33030 };
					itemDef.originalModelColors = new int[] { 8622 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33614:
					itemDef.name = "3rd age gloves";
					itemDef.description = "A pair of gloves thought lost in the 3rd age";
					itemDef.modelId = 13631;
					itemDef.maleModel = 13307;
					itemDef.femaleModel = 13319;
					itemDef.modelZoom = 789;
		            itemDef.modelRotation1 = 609;
		            itemDef.modelRotation2 = 111;
		            itemDef.modelOffset1 = 0;
		            itemDef.modelOffset2 = -1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					itemDef.modifiedModelColors = new int[] { 8656, 10394 };
					itemDef.originalModelColors = new int[] { 33030, 32844 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33615:
					itemDef.name = "White dragonhide";
					itemDef.description = "";
					itemDef.copyOf(forID(1747));
					itemDef.modifiedModelColors = new int[] { 22416, 22424 };
					itemDef.originalModelColors = new int[] { 127, 138 };
					// itemDef.aByte205 = 3;
					break;
					
				case 33616:
					itemDef.name = "Nightmare ashes";
					itemDef.description = "Ashes of a Nightmare creature.";
					itemDef.modelId = 2742;
					itemDef.modelZoom = 1210;
					itemDef.modelRotation1 = 264;
					itemDef.modelRotation2 = 1892;
					itemDef.modelOffset1 = -6;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[0] = "Scatter";
					itemDef.inventoryOptions[2] = null;
					itemDef.modifiedModelColors = new int [] {82};
					itemDef.originalModelColors = new int [] {50086};
					// itemDef.aByte205 = 3;
					break;
					
				case 33617:
					itemDef.name = "Infernal urn";
					itemDef.description = "Automatically scatters ashes.";
					itemDef.modelId = 1;
					itemDef.modelZoom = 976;
					itemDef.modelRotation1 = 138;
					itemDef.modelRotation2 = 1796;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -7;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[0] = "Scatter";
					itemDef.inventoryOptions[2] = null;
					itemDef.modifiedModelColors = new int [] {82};
					itemDef.originalModelColors = new int [] {50086};
					// itemDef.aByte205 = 3;
					break;
					
				case 33618:
					itemDef.name = "Chest key";
					itemDef.description = "A key used to open the limited time chest";
					itemDef.copyOf(forID(4184));
					// itemDef.aByte205 = 3;
					break;
					
				case 33619:
					itemDef.name = "Dragon samurai platebody";
					itemDef.description = "";
					itemDef.modelId = 1;
					itemDef.maleModel = 1;
					itemDef.femaleModel = 1;
					itemDef.modelZoom = 1312;
					itemDef.modelRotation1 = 272;
					itemDef.modelRotation2 = 2047;
					itemDef.modelOffset1 = -2;
					itemDef.modelOffset2 = 39;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
				case 33620:
					itemDef.name = "Dragon samurai platelegs";
					itemDef.description = "";
					itemDef.modelId = 1;
					itemDef.maleModel = 1;
					itemDef.femaleModel = 1;
					itemDef.modelZoom = 1625;
					itemDef.modelRotation1 = 355;
					itemDef.modelRotation2 = 2046;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -11;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
				case 33621:
					itemDef.name = "Dragon samurai full helm";
					itemDef.description = "";
					itemDef.modelId = 1;
					itemDef.maleModel = 1;
					itemDef.femaleModel = 1;
					itemDef.modelZoom = 789;
					itemDef.modelRotation1 = 96;
					itemDef.modelRotation2 = 2039;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = -7;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33622:
					itemDef.name = "Dragon samurai katana";
					itemDef.description = "";
					itemDef.modelId = 1;
					itemDef.maleModel = 1;
					itemDef.femaleModel = 1;
					itemDef.modelZoom = 1897;
					itemDef.modelRotation1 = 308;
					itemDef.modelRotation2 = 520;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33623:
					itemDef.name = "Fire max cape(purple)";
					itemDef.description = "";
					itemDef.modelId = 32815;
					itemDef.maleModel = 32820;
					itemDef.femaleModel = 32821;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33624:
					itemDef.name = "Fire max cape(cyan)";
					itemDef.description = "";
					itemDef.modelId = 32815;
					itemDef.maleModel = 32820;
					itemDef.femaleModel = 32821;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33625:
					itemDef.name = "Fire max cape(green)";
					itemDef.description = "";
					itemDef.modelId = 32815;
					itemDef.maleModel = 32820;
					itemDef.femaleModel = 32821;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					// itemDef.aByte205 = 3;
					break;
					
				case 33626:
					itemDef.name = "Fire max cape(red)";
					itemDef.description = "";
					itemDef.modelId = 32815;
					itemDef.maleModel = 32820;
					itemDef.femaleModel = 32821;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33627:
					itemDef.name = "Infernal max cape(blue)";
					itemDef.description = "";
					itemDef.modelId = 33145;
					itemDef.maleModel = 33102;
					itemDef.femaleModel = 33114;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
				case 33628:
					itemDef.name = "Infernal max cape(green)";
					itemDef.description = "";
					itemDef.modelId = 33145;
					itemDef.maleModel = 33102;
					itemDef.femaleModel = 33114;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
				case 33629:
					itemDef.name = "Infernal max cape(purple)";
					itemDef.description = "";
					itemDef.modelId = 33145;
					itemDef.maleModel = 33102;
					itemDef.femaleModel = 33114;
					itemDef.modelZoom = 2232;
					itemDef.modelRotation1 = 687;
					itemDef.modelRotation2 = 27;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -5;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					itemDef.inventoryOptions[2] = null;
					break;
					
				case 33634:
					itemDef.name = "Ahrim's hood(cursed)";
					itemDef.modelZoom = 730;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 2036;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.modelId = 61132;
					itemDef.maleModel = 61134;
					itemDef.femaleModel = 61133;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33635:
					itemDef.name = "Ahrim's staff(cursed)";
					itemDef.modelId = 61131;
					itemDef.maleModel = 61187;
					itemDef.femaleModel = 61187;
					itemDef.modelZoom = 1550;
					itemDef.modelRotation1 = 224;
					itemDef.modelRotation2 = 116;
					itemDef.modelOffset1 = -4;
					itemDef.modelOffset2 = -19;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33636:
					itemDef.name = "Ahrim's robetop(cursed)";
					itemDef.modelId = 61137;
					itemDef.maleModel = 61139;
					itemDef.femaleModel = 61138;
					itemDef.modelZoom = 1250;
					itemDef.modelRotation1 = 468;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33637:
					itemDef.name = "Ahrim's robeskirt(cursed)";
					itemDef.modelId = 61140;
					itemDef.maleModel = 61136;
					itemDef.femaleModel = 61135;
					itemDef.modelZoom = 1730;
					itemDef.modelRotation1 = 504;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = -4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33638:
					itemDef.name = "Dharok's helm(cursed)";
					itemDef.modelId = 61142;
					itemDef.maleModel = 61144;
					itemDef.femaleModel = 61143;
					itemDef.modelZoom = 660;
					itemDef.modelRotation1 = 96;
					itemDef.modelRotation2 = 128;
					itemDef.modelOffset1 = 5;
					itemDef.modelOffset2 = -8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33639:
					itemDef.name = "Dharok's greataxe(cursed)";
					itemDef.modelId = 61141;
					itemDef.maleModel = 6703;
					itemDef.femaleModel = 6703;
					itemDef.modelZoom = 1710;
					itemDef.modelRotation1 = 280;
					itemDef.modelRotation2 = 2004;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 51;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33640:
					itemDef.name = "Dharok's platebody(cursed)";
					itemDef.modelId = 61145;
					itemDef.maleModel = 61147;
					itemDef.femaleModel = 61146;
					itemDef.modelZoom = 1260;
					itemDef.modelRotation1 = 464;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 4;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33641:
					itemDef.name = "Dharok's platelegs(cursed)";
					itemDef.modelId = 61148;
					itemDef.maleModel = 61150;
					itemDef.femaleModel = 61149;
					itemDef.modelZoom = 1660;
					itemDef.modelRotation1 = 456;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 1;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33642:
					itemDef.name = "Guthan's helm(cursed)";
					itemDef.modelId = 61158;
					itemDef.maleModel = 61154;
					itemDef.femaleModel = 61188;
					itemDef.modelZoom = 860;
					itemDef.modelRotation1 = 2012;
					itemDef.modelRotation2 = 188;
					itemDef.modelOffset1 = 17;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33643:
					itemDef.name = "Guthan's warspear(cursed)";
					itemDef.modelId = 61189;
					itemDef.maleModel = 6710;
					itemDef.femaleModel = 6710;
					itemDef.modelZoom = 1470;
					itemDef.modelRotation1 = 176;
					itemDef.modelRotation2 = 116;
					itemDef.modelOffset1 = -2;
					itemDef.modelOffset2 = -16;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33644:
					itemDef.name = "Guthan's platebody(cursed)";
					itemDef.modelId = 61155;
					itemDef.maleModel = 61157;
					itemDef.femaleModel = 61156;
					itemDef.modelZoom = 960;
					itemDef.modelRotation1 = 412;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 8;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33645:
					itemDef.name = "Guthan's chainskirt(cursed)";
					itemDef.modelId = 61151;
					itemDef.maleModel = 61153;
					itemDef.femaleModel = 61152;
					itemDef.modelZoom = 1720;
					itemDef.modelRotation1 = 468;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33646:
					itemDef.name = "Karil's coif(cursed)";
					itemDef.modelId = 61161;
					itemDef.maleModel = 61159;
					itemDef.femaleModel = 61162;
					itemDef.modelZoom = 690;
					itemDef.modelRotation1 = 144;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -3;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33647:
					itemDef.name = "Karil's crossbow(cursed)";
					itemDef.modelId = 61160;
					itemDef.maleModel = 61166;
					itemDef.femaleModel = 61166;
					itemDef.modelZoom = 1160;
					itemDef.modelRotation1 = 288;
					itemDef.modelRotation2 = 888;
					itemDef.modelOffset1 = -4;
					itemDef.modelOffset2 = 39;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33648:
					itemDef.name = "Karil's leathertop(cursed)";
					itemDef.modelId = 61164;
					itemDef.maleModel = 61165;
					itemDef.femaleModel = 61169;
					itemDef.modelZoom = 1380;
					itemDef.modelRotation1 = 556;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 3;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33649:
					itemDef.name = "Karil's leatherskirt(cursed)";
					itemDef.modelId = 61163;
					itemDef.maleModel = 61168;
					itemDef.femaleModel = 61167;
					itemDef.modelZoom = 1120;
					itemDef.modelRotation1 = 512;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33650:
					itemDef.name = "Torag's helm(cursed)";
					itemDef.modelId = 61171;
					itemDef.maleModel = 61173;
					itemDef.femaleModel = 61172;
					itemDef.modelZoom = 660;
					itemDef.modelRotation1 = 0;
					itemDef.modelRotation2 = 212;
					itemDef.modelOffset1 = 11;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33651:
					itemDef.name = "Torag's hammers(cursed)";
					itemDef.modelId = 61190;
					itemDef.maleModel = 6708;
					itemDef.femaleModel = 6708;
					itemDef.modelZoom = 1620;
					itemDef.modelRotation1 = 272;
					itemDef.modelRotation2 = 1212;
					itemDef.modelOffset1 = -16;
					itemDef.modelOffset2 = -9;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33652:
					itemDef.name = "Torag's platebody(cursed)";
					itemDef.modelId = 61170;
					itemDef.maleModel = 61176;
					itemDef.femaleModel = 61175;
					itemDef.modelZoom = 1310;
					itemDef.modelRotation1 = 432;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 2;
					itemDef.modelOffset2 = 0;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33653:
					itemDef.name = "Torag's platelegs(cursed)";
					itemDef.modelId = 61177;
					itemDef.maleModel = 61174;
					itemDef.femaleModel = 61178;
					itemDef.modelZoom = 1720;
					itemDef.modelRotation1 = 468;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33654:
					itemDef.name = "Verac's helm(cursed)";
					itemDef.modelId = 61185;
					itemDef.maleModel = 61186;
					itemDef.femaleModel = 61192;
					itemDef.modelZoom = 810;
					itemDef.modelRotation1 = 28;
					itemDef.modelRotation2 = 208;
					itemDef.modelOffset1 = 5;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33655:
					itemDef.name = "Verac's flail(cursed)";
					itemDef.modelId = 61191;
					itemDef.maleModel = 6705;
					itemDef.femaleModel = 6705;
					itemDef.modelZoom = 1440;
					itemDef.modelRotation1 = 272;
					itemDef.modelRotation2 = 352;
					itemDef.modelOffset1 = 0;
					itemDef.modelOffset2 = 32;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33656:
					itemDef.name = "Verac's brassard(cursed)";
					itemDef.modelId = 61180;
					itemDef.maleModel = 61182;
					itemDef.femaleModel = 61181;
					itemDef.modelZoom = 1210;
					itemDef.modelRotation1 = 464;
					itemDef.modelRotation2 = 36;
					itemDef.modelOffset1 = 3;
					itemDef.modelOffset2 = 1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
			    
				case 33657:
					itemDef.name = "Verac plateskirt(cursed)";
					itemDef.modelId = 61184;
					itemDef.maleModel = 61179;
					itemDef.femaleModel = 61183;
					itemDef.modelZoom = 1060;
					itemDef.modelRotation1 = 504;
					itemDef.modelRotation2 = 0;
					itemDef.modelOffset1 = -1;
					itemDef.modelOffset2 = -1;
					itemDef.inventoryOptions = new String[5];
					itemDef.inventoryOptions[1] = "Wear";
					break;
					
				case 33658:
					itemDef.name = "Cursed Ahrims box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Ahrims armor to upgrade to Cursed Ahrims!";
					break;
			   
				case 33659:
					itemDef.name = "Cursed Dharoks box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Dharoks armor to upgrade to Cursed Dharoks!";
					break;
					
				case 33660:
					itemDef.name = "Cursed Guthans box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Guthans armor to upgrade to Cursed Guthans!";
					break;
					
				case 33661:
					itemDef.name = "Cursed Karils box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Karils armor to upgrade to Cursed Karils!";
					break;
					
				case 33662:
					itemDef.name = "Cursed Torags box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Torags armor to upgrade to Cursed Torags!";
					break;
					
				case 33663:
					itemDef.name = "Cursed Veracs box";
					itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
					itemDef.stackable = false;
					itemDef.modelId = 60673;
					itemDef.modelZoom = 1158;
					itemDef.modelRotation1 = 121;
					itemDef.modelRotation2 = 1966;
					itemDef.modelOffset2 = -4;
					itemDef.description = "use this on the a piece of Veracs armor to upgrade to Cursed Veracs!";
					break;
				 case 33664:
						itemDef.name = "Ice bow";
						itemDef.description = "Special attack that deals frostbite damage depending on time frozen.";
						itemDef.modelId = 60986;
						itemDef.maleModel = 60985;
						itemDef.femaleModel = 60985;
						itemDef.modelZoom = 2400;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -13;
						itemDef.modelRotation2 = 781;
						itemDef.modelRotation1 = 466;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						// itemDef.aByte205 = 3;
						break;
				 case 33665:
						itemDef.name = "Frozen bow (sheath)";
						itemDef.description = "Unsheath this weapon to use it in combat.";
						itemDef.modelId = 60986;
						itemDef.maleModel = 60987;
						itemDef.femaleModel = 60987;
						itemDef.modelZoom = 2400;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -13;
						itemDef.modelRotation2 = 781;
						itemDef.modelRotation1 = 466;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						// itemDef.aByte205 = 3;
						break;
					case 33666:
						itemDef.name = "Frozen sword";
						itemDef.description = "special attack freezes targets and causes frostbite dmg depending on time frozen.";
						itemDef.modelId = 60994;
						itemDef.maleModel = 60995;
						itemDef.femaleModel = 60995;
						itemDef.modelZoom = 1650;
						itemDef.modelRotation1 = 498;
						itemDef.modelRotation2 = 1300;
						// itemDef.aByte154 = -14;
						itemDef.modelOffset1 = 3;
						itemDef.modelOffset2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = -7;
						itemDef.femaleOffset = -7;
						break;
					case 33667:
						itemDef.name = "Frozen sword (offhand)";
						itemDef.description = "while wearing with the frozen sword, 50% more frostbite dmg dealt.";
						itemDef.modelId = 60994;
						itemDef.maleModel = 60993;
						itemDef.femaleModel = 60993;
						itemDef.modelZoom = 1650;
						itemDef.modelRotation1 = 498;
						itemDef.modelRotation2 = 1300;
						// itemDef.aByte154 = -14;
						itemDef.modelOffset1 = 3;
						itemDef.modelOffset2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = -7;
						itemDef.femaleOffset = -7;
						break;
						
					case 33668:
						itemDef.name = "Blood Mystery Box";
						itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
						itemDef.stackable = false;
						itemDef.modelId = 61110;
						itemDef.modelZoom = 1180;
						itemDef.modelRotation1 = 160;
						itemDef.modelRotation2 = 172;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = -14;
						// itemDef.modifiedModelColors = new int[] {22410, 2999};
						// itemDef.originalModelColors = new int[] {35321, 350};
						itemDef.description = "Chances for Bloodknight armor, Dragonfire shield upgrades & the necrolord staff.";
						break;
						
					case 33669:
						itemDef.name = "Pet Mystery Box";
						itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
						itemDef.stackable = false;
						itemDef.modelId = 61110;
						itemDef.modelZoom = 1180;
						itemDef.modelRotation1 = 160;
						itemDef.modelRotation2 = 172;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = -14;
						// itemDef.modifiedModelColors = new int[] {22410, 2999};
						// itemDef.originalModelColors = new int[] {35321, 350};
						itemDef.description = "Chances for all sorts of Pets!";
						break;
						
					case 33670:
						itemDef.name = "Odin";
						itemDef.description = "Hunter's custom pet.";
						itemDef.modelId = 61194;
						itemDef.modelZoom = 6800;
						itemDef.modelRotation1 = 0;
						itemDef.modelRotation2 = 270;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = null;
						itemDef.inventoryOptions[2] = null;
						// itemDef.aByte205 = 3;
						break;
						
					case 33671:
						itemDef.name = "Gilded Twisted bow";
						itemDef.description = "A gold trimmed Twisted bow.";
						itemDef.modelId = 61209;
						itemDef.maleModel = 61208;
						itemDef.femaleModel = 61208;
						itemDef.modelZoom = 2000;
						itemDef.modelRotation1 = 720;
						itemDef.modelRotation2 = 1500;
						itemDef.modelOffset1 = -3;
						itemDef.modelOffset2 = 1;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33672:
						itemDef.name = "Gilded Ghrazi rapier";
						itemDef.description = "A gold trimmed Ghrazi rapier.";
						itemDef.modelId = 61213;
						itemDef.maleModel = 61212;
						itemDef.femaleModel = 61210;
						itemDef.modelZoom = 2064;
						itemDef.modelRotation1 = 0;
						itemDef.modelRotation2 = 1603;
						itemDef.modelRotationY = 552;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = -18;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33673:
						itemDef.name = "Gilded Sanguinesti staff";
						itemDef.description = "A gold trimmed Sanguinesti staff.";
						itemDef.modelId = 61214;
						itemDef.maleModel = 61211;
						itemDef.femaleModel = 61211;
						itemDef.modelZoom = 2258;
						itemDef.modelRotation1 = 552;
						itemDef.modelRotation2 = 1558;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 3;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions = new String[] { null, "Wield", "Check", "Charge", "Uncharge", "Drop" };
						itemDef.inventoryOptions[2] = null;
						break;
						
//					case 33674://Needs animations
//						itemDef.name = "Lucidum Helmet";
//						itemDef.description = "A helmet only found in the Event Mbox.";
//						itemDef.modelId = 61340;
//						itemDef.maleModel = 61337;
//						itemDef.femaleModel = 61337;
//						itemDef.modelZoom = 735;
//						itemDef.modelOffset1 = 0;
//						itemDef.modelOffset2 = -2;
//						itemDef.modelRotation2 = 0;
//						itemDef.modelRotation1 = 502;
//						itemDef.modelRotationY = 168;
//						itemDef.inventoryOptions = new String[5];
//						itemDef.inventoryOptions[1] = "Wear";
//						itemDef.inventoryOptions[2] = null;
//						break;
						
					case 33674:
						itemDef.name = "Lucidum platebody";//yel
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61296;
						itemDef.maleModel = 61240;
						itemDef.femaleModel = 61280;
						itemDef.anInt188 = 61236;
						itemDef.anInt164 = 61272;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33675:
						itemDef.name = "Lucidum platebody";//black
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61297;
						itemDef.maleModel = 61241;
						itemDef.femaleModel = 61281;
						itemDef.anInt188 = 61237;
						itemDef.anInt164 = 61273;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33676:
						itemDef.name = "Lucidum platebody";//blue
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61298;
						itemDef.maleModel = 61242;
						itemDef.femaleModel = 61282;
						itemDef.anInt188 = 61232;
						itemDef.anInt164 = 61274;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33677:
						itemDef.name = "Lucidum platebody";//green
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61299;
						itemDef.maleModel = 61243;
						itemDef.femaleModel = 61283;
						itemDef.anInt188 = 61233;
						itemDef.anInt164 = 61275;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33678:
						itemDef.name = "Lucidum platebody";//orange
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61302;
						itemDef.maleModel = 61245;
						itemDef.femaleModel = 61284;
						itemDef.anInt188 = 61234;
						itemDef.anInt164 = 61276;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33679:
						itemDef.name = "Lucidum platebody";//purple
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61300;
						itemDef.maleModel = 61244;
						itemDef.femaleModel = 61285;
						itemDef.anInt188 = 61238;
						itemDef.anInt164 = 61277;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33680:
						itemDef.name = "Lucidum platebody";//red
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61301;
						itemDef.maleModel = 61246;
						itemDef.femaleModel = 61286;
						itemDef.anInt188 = 61235;
						itemDef.anInt164 = 61278;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33681:
						itemDef.name = "Lucidum platebody";//white
						itemDef.description = "A platebody only found in the Event Mbox.";
						itemDef.modelId = 61303;
						itemDef.maleModel = 61247;
						itemDef.femaleModel = 61287;
						itemDef.anInt188 = 61239;
						itemDef.anInt164 = 61279;
						itemDef.modelZoom = 1284;
						itemDef.modelOffset1 = 1;
						itemDef.modelOffset2 = -5;
						itemDef.modelRotation1 = 512;
						itemDef.modelRotation2 = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33683:
						itemDef.name = "Lucidum platelegs";//yellow
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61320;
						itemDef.maleModel = 61224;
						itemDef.femaleModel = 61264;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33684:
						itemDef.name = "Lucidum platelegs";//black
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61321;
						itemDef.maleModel = 61225;
						itemDef.femaleModel = 61265;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33685:
						itemDef.name = "Lucidum platelegs";//blue
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61322;
						itemDef.maleModel = 61226;
						itemDef.femaleModel = 61266;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33686:
						itemDef.name = "Lucidum platelegs";//green
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61323;
						itemDef.maleModel = 61227;
						itemDef.femaleModel = 61267;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33687:
						itemDef.name = "Lucidum platelegs";//purple
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61325;
						itemDef.maleModel = 61229;
						itemDef.femaleModel = 61269;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33688:
						itemDef.name = "Lucidum platelegs";//orange
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61324;
						itemDef.maleModel = 61228;
						itemDef.femaleModel = 61268;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33689:
						itemDef.name = "Lucidum platelegs";//red
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61326;
						itemDef.maleModel = 61230;
						itemDef.femaleModel = 61270;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33690:
						itemDef.name = "Lucidum platelegs";//white
						itemDef.description = "platelegs only found in the Event Mbox.";
						itemDef.modelId = 61327;
						itemDef.maleModel = 61231;
						itemDef.femaleModel = 61271;
						itemDef.modelZoom = 1537;
						itemDef.modelOffset1 = 5;
						itemDef.modelOffset2 = 4;
						itemDef.modelRotation1 = 560;
						itemDef.modelRotation2 = 225;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33691:
						itemDef.name = "Lucidum gloves";//yellow
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61304;
						itemDef.maleModel = 61216;
						itemDef.femaleModel = 61256;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33692:
						itemDef.name = "Lucidum gloves";//black
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61305;
						itemDef.maleModel = 61217;
						itemDef.femaleModel = 61257;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33693:
						itemDef.name = "Lucidum gloves";//blue
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61306;
						itemDef.maleModel = 61218;
						itemDef.femaleModel = 61258;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33694:
						itemDef.name = "Lucidum gloves";//green
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61307;
						itemDef.maleModel = 61219;
						itemDef.femaleModel = 61260;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33695:
						itemDef.name = "Lucidum gloves";//orange
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61308;
						itemDef.maleModel = 61220;
						itemDef.femaleModel = 61261;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33696:
						itemDef.name = "Lucidum gloves";//purple
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61309;
						itemDef.maleModel = 61221;
						itemDef.femaleModel = 61262;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33697:
						itemDef.name = "Lucidum gloves";//red
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61310;
						itemDef.maleModel = 61222;
						itemDef.femaleModel = 61259;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33698:
						itemDef.name = "Lucidum gloves";//white
						itemDef.description = "gloves only found in the Event Mbox.";
						itemDef.modelId = 61311;
						itemDef.maleModel = 61223;
						itemDef.femaleModel = 61263;
						itemDef.modelZoom = 400;
						itemDef.modelOffset1 = -1;
						itemDef.modelOffset2 = -3;
						itemDef.modelRotation1 = 24;
						itemDef.modelRotation2 = 354;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33699:
						itemDef.name = "Lucidum boots";//yellow
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61312;
						itemDef.maleModel = 61248;
						itemDef.femaleModel = 61288;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33700:
						itemDef.name = "Lucidum boots";//black
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61313;
						itemDef.maleModel = 61249;
						itemDef.femaleModel = 61289;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33701:
						itemDef.name = "Lucidum boots";//blue
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61314;
						itemDef.maleModel = 61250;
						itemDef.femaleModel = 61290;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33702:
						itemDef.name = "Lucidum boots";//green
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61315;
						itemDef.maleModel = 61252;
						itemDef.femaleModel = 61291;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33703:
						itemDef.name = "Lucidum boots";//orange
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61316;
						itemDef.maleModel = 61253;
						itemDef.femaleModel = 61292;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33704:
						itemDef.name = "Lucidum boots";//purple
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61317;
						itemDef.maleModel = 61254;
						itemDef.femaleModel = 61293;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33705:
						itemDef.name = "Lucidum boots";//red
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61318;
						itemDef.maleModel = 61251;
						itemDef.femaleModel = 61294;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33706:
						itemDef.name = "Lucidum boots";//white
						itemDef.description = "boots only found in the Event Mbox.";
						itemDef.modelId = 61319;
						itemDef.maleModel = 61255;
						itemDef.femaleModel = 61295;
						itemDef.modelZoom = 653;
						itemDef.modelOffset1 = 7;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation1 = 201;
						itemDef.modelRotation2 = 423;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33707:
						itemDef.name = "Elymas staff";//yellow
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61333;
						itemDef.maleModel = 61328;
						itemDef.femaleModel = 61328;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33708:
						itemDef.name = "Elymas staff";//black
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61344;
						itemDef.maleModel = 61363;
						itemDef.femaleModel = 61363;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33709:
						itemDef.name = "Elymas staff";//blue
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61346;
						itemDef.maleModel = 61365;
						itemDef.femaleModel = 61365;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33710:
						itemDef.name = "Elymas staff";//green
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61347;
						itemDef.maleModel = 61366;
						itemDef.femaleModel = 61366;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33711:
						itemDef.name = "Elymas staff";//orange
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61348;
						itemDef.maleModel = 61367;
						itemDef.femaleModel = 61367;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33712:
						itemDef.name = "Elymas staff";//purple
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61349;
						itemDef.maleModel = 61368;
						itemDef.femaleModel = 61368;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33713:
						itemDef.name = "Elymas staff";//red
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61350;
						itemDef.maleModel = 61369;
						itemDef.femaleModel = 61369;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33714:
						itemDef.name = "Elymas staff";//white
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61351;
						itemDef.maleModel = 61370;
						itemDef.femaleModel = 61370;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33715:
						itemDef.name = "Elymas staff";//light blue
						itemDef.description = "A powerful staff only found in the event mbox.";
						itemDef.modelId = 61345;
						itemDef.maleModel = 61364;
						itemDef.femaleModel = 61364;
						itemDef.modelZoom = 1490;
						itemDef.modelOffset1 = -5;
						itemDef.modelOffset2 = 2;
						itemDef.modelRotation1 = 148;
						itemDef.modelRotation2 = 1400;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 33716:
						itemDef.name = "Elymas cape";//light blue
						itemDef.description = "A cape only found in the event mbox.";
						itemDef.modelId = 61336;
						itemDef.maleModel = 61335;
						itemDef.femaleModel = 61335;
						itemDef.modelZoom = 2048;
						itemDef.modelOffset1 = -10;
						itemDef.modelOffset2 = 15;
						itemDef.modelRotation2 = 1042;
						itemDef.modelRotation1 = 516;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
					case 7809:
						itemDef.name = "Anger staff";
						break;
						
					case 33717:
						itemDef.name = "Event Mystery Box";
						itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
						itemDef.stackable = false;
						itemDef.modelId = 61110;
						itemDef.modelZoom = 1180;
						itemDef.modelRotation1 = 160;
						itemDef.modelRotation2 = 172;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = -14;
						// itemDef.modifiedModelColors = new int[] {22410, 2999};
						// itemDef.originalModelColors = new int[] {35321, 350};
						itemDef.description = "Chances for some Event box only equipment.";
						break;
						
					case 33718:
						itemDef.name = "Yearly Mimic";
						itemDef.description = "Received from the 1 year Mimic boss.";
						itemDef.modelId = 61195;
						itemDef.modelZoom = 8500;
						itemDef.modelRotation1 = 0;
						itemDef.modelRotation2 = 270;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = 25;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = null;
						itemDef.inventoryOptions[2] = null;
						// itemDef.aByte205 = 3;
						break;
						
					case 33719:
						itemDef.name = "Godly Sphere";
						itemDef.description = "This back piece makes you fly!";
						itemDef.modelId = 61372;
						itemDef.maleModel = 61371;
						itemDef.femaleModel = 61371;
						itemDef.modelZoom = 2048;
						itemDef.modelOffset1 = -20;
						itemDef.modelOffset2 = -30;
						itemDef.modelRotation2 = 216;
						itemDef.modelRotation1 = 425;
						itemDef.modelRotationY = 73;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33720:
						itemDef.name = "Saphire mystery box";
						itemDef.description = "A Sapphire mystery box.";
						itemDef.modelId = 61393;
						itemDef.modelZoom = 1180;
						itemDef.modelRotation1 = 160;
						itemDef.modelRotation2 = 172;
						itemDef.modelOffset1 = 0;
						itemDef.modelOffset2 = -14;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[0] = "Open";
						itemDef.inventoryOptions[1] = null;
						itemDef.inventoryOptions[2] = null;
						// itemDef.aByte205 = 3;
						break;
						
					case 33721:
						itemDef.name = "Green dragon wings";
						itemDef.description = "The wings of a green dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61416;
						itemDef.maleModel = 61415;
						itemDef.femaleModel = 61415;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33722:
						itemDef.name = "Blue dragon wings";
						itemDef.description = "The wings of a blue dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61414;
						itemDef.maleModel = 61413;
						itemDef.femaleModel = 61413;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33723:
						itemDef.name = "Red dragon wings";
						itemDef.description = "The wings of a red dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61424;
						itemDef.maleModel = 61410;
						itemDef.femaleModel = 61410;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33724:
						itemDef.name = "Black dragon wings";
						itemDef.description = "The wings of a black dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61412;
						itemDef.maleModel = 61411;
						itemDef.femaleModel = 61411;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33725:
						itemDef.name = "White dragon wings";
						itemDef.description = "The wings of a white dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61426;
						itemDef.maleModel = 61425;
						itemDef.femaleModel = 61425;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33726:
						itemDef.name = "Lava dragon wings";
						itemDef.description = "The wings of a lava dragon! rumors are they allow you to fly.";
						itemDef.modelId = 61423;
						itemDef.maleModel = 61417;
						itemDef.femaleModel = 61417;
						itemDef.modelZoom = 2700;
						itemDef.modelOffset1 = 4;
						itemDef.modelOffset2 = 19;
						itemDef.modelRotation2 = 1030;
						itemDef.modelRotation1 = 449;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						itemDef.maleOffset = 3;
						break;
						
					case 33727:
						itemDef.name = "3rd age boots";
						itemDef.description = "boots dropped by a 3rd age dragon.";
						itemDef.modelId = 61419;
						itemDef.maleModel = 61419;
						itemDef.femaleModel = 61418;
						itemDef.modelZoom = 776;
						itemDef.modelOffset1 = 3;
						itemDef.modelOffset2 = -16;
						itemDef.modelRotation2 = 200;
						itemDef.modelRotation1 = 138;
						itemDef.modelRotationY = 0;
						itemDef.inventoryOptions = new String[5];
						itemDef.inventoryOptions[1] = "Wear";
						itemDef.inventoryOptions[2] = null;
						break;
						
				
		}
	}
	
	private static void customItems3(ItemDefinition itemDef) {

		// new items here
		switch (itemDef.id) {
		case 33728:
			itemDef.name = "Glowing skeleton body";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61449;
			itemDef.maleModel = 61442;
			itemDef.femaleModel = 61448;
			itemDef.anInt188 = 61437;
			itemDef.anInt164 = 61443;
			itemDef.modelZoom = 1178;
			itemDef.modelRotation1 = 526;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33729:
			itemDef.name = "Glowing skeleton legs";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61453;
			itemDef.maleModel = 61441;
			itemDef.femaleModel = 61447;
			itemDef.modelZoom = 1632;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotationY = 152;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33730:
			itemDef.name = "Glowing skeleton boots";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61450;
			itemDef.maleModel = 61438;
			itemDef.femaleModel = 61444;
			itemDef.modelZoom = 724;
			itemDef.modelRotation1 = 138;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33731:
			itemDef.name = "Glowing skeleton gloves";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61451;
			itemDef.maleModel = 61439;
			itemDef.femaleModel = 61445;
			itemDef.modelZoom = 595;
			itemDef.modelRotation1 = 553;
			itemDef.modelRotation2 = 1978;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33732:
			itemDef.name = "Glowing skeleton mask";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61452;
			itemDef.maleModel = 61440;
			itemDef.femaleModel = 61446;
			itemDef.modelZoom = 465;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 111;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33733:
			itemDef.name = "Spooky skeleton body";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61449;
			itemDef.maleModel = 61442;
			itemDef.femaleModel = 61448;
			itemDef.anInt188 = 61437;
			itemDef.anInt164 = 61443;
			itemDef.modelZoom = 1178;
			itemDef.modelRotation1 = 526;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33734:
			itemDef.name = "Spooky skeleton legs";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61453;
			itemDef.maleModel = 61441;
			itemDef.femaleModel = 61447;
			itemDef.modelZoom = 1632;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotationY = 152;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33735:
			itemDef.name = "Spooky skeleton boots";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61450;
			itemDef.maleModel = 61438;
			itemDef.femaleModel = 61444;
			itemDef.modelZoom = 724;
			itemDef.modelRotation1 = 138;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33736:
			itemDef.name = "Spooky skeleton gloves";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61451;
			itemDef.maleModel = 61439;
			itemDef.femaleModel = 61445;
			itemDef.modelZoom = 595;
			itemDef.modelRotation1 = 553;
			itemDef.modelRotation2 = 1978;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33737:
			itemDef.name = "Spooky skeleton mask";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61452;
			itemDef.maleModel = 61440;
			itemDef.femaleModel = 61446;
			itemDef.modelZoom = 465;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 111;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33738:
			itemDef.name = "Flashing skeleton body";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61449;
			itemDef.maleModel = 61442;
			itemDef.femaleModel = 61448;
			itemDef.anInt188 = 61437;
			itemDef.anInt164 = 61443;
			itemDef.modelZoom = 1178;
			itemDef.modelRotation1 = 526;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33739:
			itemDef.name = "Flashing skeleton legs";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61453;
			itemDef.maleModel = 61441;
			itemDef.femaleModel = 61447;
			itemDef.modelZoom = 1632;
			itemDef.modelRotation1 = 567;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotationY = 152;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = 3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33740:
			itemDef.name = "Flashing skeleton boots";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61450;
			itemDef.maleModel = 61438;
			itemDef.femaleModel = 61444;
			itemDef.modelZoom = 724;
			itemDef.modelRotation1 = 138;
			itemDef.modelRotation2 = 138;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33741:
			itemDef.name = "Flashing skeleton gloves";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61451;
			itemDef.maleModel = 61439;
			itemDef.femaleModel = 61445;
			itemDef.modelZoom = 595;
			itemDef.modelRotation1 = 553;
			itemDef.modelRotation2 = 1978;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 7;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
		case 33742:
			itemDef.name = "Flashing skeleton mask";
			itemDef.description = "A reward from the halloween mbox.";
			itemDef.modelId = 61452;
			itemDef.maleModel = 61440;
			itemDef.femaleModel = 61446;
			itemDef.modelZoom = 465;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 111;
			itemDef.modelRotationY = 2047;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33743:
			itemDef.name = "Jack-o-ring";
			itemDef.description = "A reward from the halloween mbox. turns players into a jack-o-bat!";
			itemDef.modelId = 61436;
			itemDef.modelZoom = 474;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.modelRotation2 = 1218;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33744:
			itemDef.name = "Spooky Jack-o-ring";
			itemDef.description = "A reward from the halloween mbox. turns players into a spooky jack-o-bat!";
			itemDef.modelId = 61435;
			itemDef.modelZoom = 474;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.modelRotation2 = 1218;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33745:
			itemDef.name = "Jack-o-Kraken";
			itemDef.description = "Received from the halloween event mbox 2019.";
			itemDef.modelId = 61428;
			itemDef.modelZoom = 9500;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 25;
			itemDef.modelRotation2 = 47;
			itemDef.modelRotation1 = 73;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33746:
			itemDef.name = "Jack-o-Bat";
			itemDef.description = "Received from the halloween event mbox 2019.";
			itemDef.modelId = 61434;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 25;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33747:
			itemDef.name = "Spooky Jack-o-Bat";
			itemDef.description = "Received from the halloween event mbox 2019.";
			itemDef.modelId = 61432;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 25;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33748:
			itemDef.name = "Lit Jack-o-Bat";
			itemDef.description = "Received from the halloween event mbox 2019.";
			itemDef.modelId = 61432;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 25;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33749:
			itemDef.name = "Glowing Jack-o-Bat";
			itemDef.description = "Received from the halloween event mbox 2019.";
			itemDef.modelId = 61432;
			itemDef.modelZoom = 8500;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 270;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 25;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33752:
			itemDef.name = "Spooky Twisted bow";
			itemDef.description = "a Twisted bow from the halloween event mbox 2019.";
			itemDef.modelId = 61455;
			itemDef.maleModel = 61454;
			itemDef.femaleModel = 61454;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 720;
			itemDef.modelRotation2 = 1500;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 14236, 13223 };
			itemDef.originalModelColors = new int[] { 49830, 49830 };
			// itemDef.aByte205 = 3;
			break;
			
//	    case 33753:
//			itemDef.name = "Spooky Noxious Staff";
//			itemDef.description = "A powerful staff found in a Halloween mbox.";
//			itemDef.modelId = 61456;
//			itemDef.maleModel = 60983;
//			itemDef.femaleModel = 60983;
//			itemDef.modelZoom = 2400;
//			itemDef.modelOffset1 = 1;
//			itemDef.modelOffset2 = -13;
//			itemDef.modelRotation2 = 781;
//			itemDef.modelRotation1 = 466;
//			itemDef.modelRotationY = 0;
//			itemDef.inventoryOptions = new String[5];
//			itemDef.inventoryOptions[1] = "Wear";
//			itemDef.inventoryOptions[2] = null;
//			// itemDef.aByte205 = 3;
//			break;
			
	    case 33754:
			itemDef.name = "Spooky Noxious bow";
			itemDef.description = "A powerful bow found in a Halloween mbox.";
			itemDef.modelId = 61457;
			itemDef.maleModel = 61460;
			itemDef.femaleModel = 61460;
			itemDef.modelZoom = 2400;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -13;
			itemDef.modelRotation2 = 781;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
	    case 33755:
			itemDef.name = "Spooky Noxious scythe";
			itemDef.description = "A powerful scythe found in a Halloween mbox.";
			itemDef.modelId = 61458;
			itemDef.maleModel = 61459;
			itemDef.femaleModel = 61459;
			itemDef.modelZoom = 2750;
			itemDef.modelOffset1 = 26;
			itemDef.modelOffset2 = 61;
			itemDef.modelRotation2 = 668;
			itemDef.modelRotation1 = 234;
			itemDef.modelRotationY = 808;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33756:
			itemDef.name = "Halloween Mystery Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 61110;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			// itemDef.modifiedModelColors = new int[] {22410, 2999};
			// itemDef.originalModelColors = new int[] {35321, 350};
			itemDef.description = "Chances for Halloween themed items such as the Spooky Noxious weapons.";
			break;
			
		case 33757:
			itemDef.name = "Glowing H'ween mask";
			itemDef.modelId = 61461;
			itemDef.modelZoom = 730;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 516;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -10;
			itemDef.maleModel = 61462;
			itemDef.femaleModel = 61463;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A glowing hween from the hween mbox.";
			break;
			
		case 33758:
			itemDef.name = "Spooky H'ween mask";
			itemDef.modelId = 61461;
			itemDef.modelZoom = 730;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 516;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -10;
			itemDef.maleModel = 61462;
			itemDef.femaleModel = 61463;
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "A spooky hween from the hween mbox.";
			break;
			
		case 33759:
			itemDef.name = "Zamorak";
			itemDef.description = "Hehe's custom pet.";
			itemDef.modelId = 61464;
			itemDef.modelZoom = 3548;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 172;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33760:
			itemDef.name = "Mini Dup";
			itemDef.description = "Dup's custom pet.";
			itemDef.modelId = 60925;
			itemDef.modelZoom = 863;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -7;
			itemDef.modelRotation2 = 184;
			itemDef.modelRotation1 = 24;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33761:
			itemDef.name = "Infernal staff (i)";
			itemDef.description = "none.";
			itemDef.modelId = 61203;
			itemDef.maleModel = 61204;
			itemDef.femaleModel = 61204;
			itemDef.modelZoom = 2200;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33762:
			itemDef.name = "Infernal longsword (i)";
			itemDef.description = "none.";
			itemDef.modelId = 61205;
			itemDef.maleModel = 61206;
			itemDef.femaleModel = 61206;
			itemDef.modelZoom = 1900;
			itemDef.modelRotation1 = 1549;
			itemDef.modelRotation2 = 1791;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33763:
			itemDef.name = "Infernal bow (i)";
			itemDef.description = "none.";
			itemDef.modelId = 61199;
			itemDef.maleModel = 61200;
			itemDef.femaleModel = 61200;
			itemDef.modelZoom = 3334;
			itemDef.modelRotation1 = 533;
			itemDef.modelRotation2 = 1294;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;

		case 33764:
			itemDef.name = "Infernal warhammer (i)";
			itemDef.description = "none.";
			itemDef.modelId = 61201;
			itemDef.maleModel = 61202;
			itemDef.femaleModel = 61202;
			itemDef.modelZoom = 2512;
			itemDef.modelRotation1 = 317;
			itemDef.modelRotation2 = 1988;
			itemDef.modelOffset1 = -8;
			itemDef.modelOffset2 = 45;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33765:
			itemDef.name = "Infernal weapon enhancement kit";
			itemDef.inventoryOptions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 60673;
			itemDef.modelZoom = 1158;
			itemDef.modelRotation1 = 121;
			itemDef.modelRotation2 = 1966;
			itemDef.modelOffset2 = -4;
			itemDef.description = "Offers enhanced stats over the original Infernal Weapons dropped by Enraged Graardor.";
			break;
			
		case 33766:
			itemDef.name = "Riley";
			itemDef.description = "S C A R E D's custom pet.";
			itemDef.modelId = 61465;
			itemDef.modelZoom = 6548;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 172;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33767:
			itemDef.name = "Gem of fortune";
			itemDef.description = "Combine this with a ring of wealth (i).";
			itemDef.modelId = 3176;
			itemDef.modelZoom = 1390;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 100;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {49554};
			itemDef.modifiedModelColors = new int[] {1500};
			// itemDef.aByte205 = 3;
			break;
			
		case 33768:
			itemDef.name = "Gem of the dwarves";
			itemDef.description = "Combine this with a ring of fortune.";
			itemDef.modelId = 3176;
			itemDef.modelZoom = 1390;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 100;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {10};
			itemDef.modifiedModelColors = new int[] {1500};
			// itemDef.aByte205 = 3;
			break;
			
		case 33769:
			itemDef.name = "Hazelmere's gem";
			itemDef.description = "Combine this with a luck of the dwarves.";
			itemDef.modelId = 3176;
			itemDef.modelZoom = 1390;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 100;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {127};
			itemDef.modifiedModelColors = new int[] {1500};
			// itemDef.aByte205 = 3;
			break;
			
		case 33770:
			itemDef.name = "Blue flame shard";
			itemDef.description = "Can be combined with wilderness weapons.";
			itemDef.modelId = 11047;
			itemDef.modelZoom = 1374;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 94;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {34232, 35244};
			itemDef.modifiedModelColors = new int[] {51515, 51484};
			// itemDef.aByte205 = 3;
			break;
			
		case 33771:
			itemDef.name = "Burning shard";
			itemDef.description = "Can be combined with wilderness weapons.";
			itemDef.modelId = 11047;
			itemDef.modelZoom = 1374;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 94;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {63403, 64422};
			itemDef.modifiedModelColors = new int[] {51515, 51484};
			// itemDef.aByte205 = 3;
			break;
			
		case 33772:
			itemDef.name = "Toxic shard";
			itemDef.description = "Can be combined with wilderness weapons.";
			itemDef.modelId = 11047;
			itemDef.modelZoom = 1374;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 94;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {20135, 20119};
			itemDef.modifiedModelColors = new int[] {51515, 51484};
			// itemDef.aByte205 = 3;
			break;
			
		case 33773:
			itemDef.name = "Revenant impling jar";
			itemDef.description = "Contains revenant loot.";
			itemDef.modelId = 50162;
			itemDef.modelZoom = 789;
			itemDef.modelOffset1 = 20;
			itemDef.modelOffset2 = -17;
			itemDef.modelRotation2 = 2047;
			itemDef.modelRotation1 = 97;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
			
		case 33774:
			itemDef.name = "Vet'ion wilderness key";
			itemDef.description = "Can be used on the wilderness chest.";
			itemDef.modelId = 50140;
			itemDef.modelZoom = 1616;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 357;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33775:
			itemDef.name = "Shadow beast wilderness key";
			itemDef.description = "Can be used on the wilderness chest.";
			itemDef.modelId = 50121;
			itemDef.modelZoom = 1616;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 357;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33776:
			itemDef.name = "Callisto wilderness key";
			itemDef.description = "Can be used on the wilderness chest.";
			itemDef.modelId = 50123;
			itemDef.modelZoom = 1616;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 357;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33777:
			itemDef.name = "Venantis wilderness key";
			itemDef.description = "Can be used on the wilderness chest.";
			itemDef.modelId = 50161;
			itemDef.modelZoom = 1616;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 357;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33778:
			itemDef.name = "Viggora's chainmace (Blue Flame)";
			itemDef.description = "none.";
			itemDef.modelId = 50131;
			itemDef.maleModel = 50132;
			itemDef.femaleModel = 50132;
			itemDef.modelZoom = 1460;
			itemDef.modelRotation1 = 1495;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33779:
			itemDef.name = "Viggora's chainmace (Burning)";
			itemDef.description = "none.";
			itemDef.modelId = 50131;
			itemDef.maleModel = 50132;
			itemDef.femaleModel = 50132;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 1495;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33780:
			itemDef.name = "Viggora's chainmace (Toxic)";
			itemDef.description = "none.";
			itemDef.modelId = 50131;
			itemDef.maleModel = 50132;
			itemDef.femaleModel = 50132;
			itemDef.modelZoom = 1360;
			itemDef.modelRotation1 = 1495;
			itemDef.modelRotation2 = 256;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33781:
			itemDef.name = "Craw's bow (Blue Flame)";
			itemDef.description = "none.";
			itemDef.modelId = 50126;
			itemDef.maleModel = 50127;
			itemDef.femaleModel = 50127;
			itemDef.modelZoom = 1979;
			itemDef.modelRotation1 = 1463;
			itemDef.modelRotation2 = 510;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33782:
			itemDef.name = "Craw's bow (Burning)";
			itemDef.description = "none.";
			itemDef.modelId = 50126;
			itemDef.maleModel = 50127;
			itemDef.femaleModel = 50127;
			itemDef.modelZoom = 1979;
			itemDef.modelRotation1 = 1463;
			itemDef.modelRotation2 = 510;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33783:
			itemDef.name = "Craw's bow (Toxic)";
			itemDef.description = "none.";
			itemDef.modelId = 50126;
			itemDef.maleModel = 50127;
			itemDef.femaleModel = 50127;
			itemDef.modelZoom = 1979;
			itemDef.modelRotation1 = 1463;
			itemDef.modelRotation2 = 510;
			itemDef.modelOffset1 = -3;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33784:
			itemDef.name = "Thammaron sceptre (Blue Flame)";
			itemDef.description = "none.";
			itemDef.modelId = 50146;
			itemDef.maleModel = 50147;
			itemDef.femaleModel = 50147;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {43950, 43955, 43960};
			itemDef.modifiedModelColors = new int[] {282, 416, 426,};
			// itemDef.aByte205 = 3;
			break;
			
		case 33785:
			itemDef.name = "Thammaron sceptre (Burning)";
			itemDef.description = "none.";
			itemDef.modelId = 50146;
			itemDef.maleModel = 50147;
			itemDef.femaleModel = 50147;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {63397, 63400, 63403};
			itemDef.modifiedModelColors = new int[] {926, 927, 928,};
			// itemDef.aByte205 = 3;
			break;
			
		case 33786:
			itemDef.name = "Thammaron sceptre (Toxic)";
			itemDef.description = "none.";
			itemDef.modelId = 50146;
			itemDef.maleModel = 50147;
			itemDef.femaleModel = 50147;
			itemDef.modelZoom = 2105;
			itemDef.modelRotation1 = 400;
			itemDef.modelRotation2 = 1020;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {23192, 23196, 23199};
			itemDef.modifiedModelColors = new int[] {282, 416, 426,};
			// itemDef.aByte205 = 3;
			break;
			
		case 33787:
			itemDef.name = "Shadow spirit shield";
			itemDef.description = "none.";
			itemDef.modelId = 50142;
			itemDef.maleModel = 50143;
			itemDef.femaleModel = 50143;
			itemDef.modelZoom = 1789;
			itemDef.modelRotation1 = 457;
			itemDef.modelRotation2 = 1035;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33788:
			itemDef.name = "Reflective spirit shield";
			itemDef.description = "none.";
			itemDef.modelId = 50128;
			itemDef.maleModel = 50129;
			itemDef.femaleModel = 50129;
			itemDef.modelZoom = 1789;
			itemDef.modelRotation1 = 457;
			itemDef.modelRotation2 = 1035;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33789:
			itemDef.name = "Siphon spirit shield";
			itemDef.description = "none.";
			itemDef.modelId = 50144;
			itemDef.maleModel = 50145;
			itemDef.femaleModel = 50145;
			itemDef.modelZoom = 1789;
			itemDef.modelRotation1 = 457;
			itemDef.modelRotation2 = 1035;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 11;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33790:
			itemDef.name = "Shadow sigil";
			itemDef.description = "none.";
			itemDef.modelId = 50165;
			itemDef.modelZoom = 1068;
			itemDef.modelRotation1 = 303;
			itemDef.modelRotation2 = 1620;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 27;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33791:
			itemDef.name = "Reflective sigil";
			itemDef.description = "none.";
			itemDef.modelId = 50163;
			itemDef.modelZoom = 1068;
			itemDef.modelRotation1 = 303;
			itemDef.modelRotation2 = 1620;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 27;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33792:
			itemDef.name = "Siphon sigil";
			itemDef.description = "none.";
			itemDef.modelId = 50166;
			itemDef.modelZoom = 1068;
			itemDef.modelRotation1 = 303;
			itemDef.modelRotation2 = 1620;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 27;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33793:
			itemDef.name = "Shadow lord platebody";
			itemDef.description = "Belongs to a dark creature.";
			itemDef.modelId = 50155;
			itemDef.maleModel = 50156;
			itemDef.femaleModel = 50156;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
			
		case 33794:
			itemDef.name = "Shadow lord platelegs";
			itemDef.description = "Belongs to a dark creature.";
			itemDef.modelId = 50157;
			itemDef.maleModel = 50158;
			itemDef.femaleModel = 50158;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33795:
			itemDef.name = "Shadow lord helmet";
			itemDef.description = "Belongs to a dark creature.";
			itemDef.modelId = 50153;
			itemDef.maleModel = 50154;
			itemDef.femaleModel = 50154;
			itemDef.modelZoom = 810;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
			
		case 33796:
			itemDef.name = "Shadow lord greataxe";
			itemDef.description = "Belongs to a dark creature.";
			itemDef.modelId = 50124;
			itemDef.maleModel = 50125;
			itemDef.femaleModel = 50125;
			itemDef.modelZoom = 2200;
			itemDef.modelRotation1 = 578;
			itemDef.modelRotation2 = 1258;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
			
		case 33797:
			itemDef.name = "Shadow lord battleaxe";
			itemDef.description = "Belongs to a dark creature.";
			itemDef.modelId = 50150;
			itemDef.maleModel = 50151;
			itemDef.femaleModel = 50151;
			itemDef.modelZoom = 2200;
			itemDef.modelRotation1 = 578;
			itemDef.modelRotation2 = 1258;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
			
		case 33805:
			itemDef.name = "Shadow lord kiteshield";
			itemDef.description = "none.";
			itemDef.modelId = 50159;
			itemDef.maleModel = 50160;
			itemDef.femaleModel = 50160;
			itemDef.modelZoom = 1516;
			itemDef.modelRotation1 = 304;
			itemDef.modelRotation2 = 998;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33798:
			itemDef.name = "Ring of fortune";
			itemDef.description = "Tier 2 luck!";
			itemDef.modelId = 2677;
			itemDef.modelZoom = 830;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.modifiedModelColors = new int[] {127, 9152};
			itemDef.originalModelColors = new int[] {49564, 10};
			break;
			
		case 33799:
			itemDef.name = "Luck of the dwarves";
			itemDef.description = "Tier 3 luck!";
			itemDef.modelId = 2677;
			itemDef.modelZoom = 830;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {10, 8893};
			itemDef.modifiedModelColors = new int[] {127, 9152};
			// itemDef.aByte205 = 3;
			break;
			
		case 33800:
			itemDef.name = "Hazelmere's signet ring";
			itemDef.description = "Tier 4 luck!";
			itemDef.modelId = 50130;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33801:
			itemDef.name = "Pet derwen";
			itemDef.description = "A useful pet in the wilderness.";
			itemDef.modelId = 34517;
			itemDef.modelZoom = 7000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33802:
			itemDef.name = "Pet justiciar zachariah";
			itemDef.description = "A useful pet in the wilderness.";
			itemDef.modelId = 34511;
			itemDef.modelZoom = 4500;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33803:
			itemDef.name = "Pet porazdir";
			itemDef.description = "A useful pet in the wilderness.";
			itemDef.modelId = 34514;
			itemDef.modelZoom = 7000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33804:
			itemDef.name = "Pet shadow critter";
			itemDef.description = "Can be used on the wilderness chest.";
			itemDef.modelId = 50141;
			itemDef.modelZoom = 4304;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33806:
			itemDef.name = "Ring of fortune (i)";
			itemDef.description = "Tier 2 luck!";
			itemDef.modelId = 2677;
			itemDef.modelZoom = 830;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.originalModelColors = new int[] {49564, 20};
			itemDef.modifiedModelColors = new int[] {127, 9152};
			break;
			
		case 33807:
			itemDef.name = "Luck of the dwarves (i)";
			itemDef.description = "Tier 3 luck!";
			itemDef.modelId = 2677;
			itemDef.modelZoom = 830;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {10, 11200};
			itemDef.modifiedModelColors = new int[] {127, 9152};
			// itemDef.aByte205 = 3;
			break;
			
		case 33809:
			itemDef.name = "Hazelmere's signet ring (i)";
			itemDef.description = "Tier 4 luck!";
			itemDef.modelId = 50130;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 322;
			itemDef.modelRotation2 = 135;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = 1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.originalModelColors = new int[] {11200, 11205, 11210, 11215, 11230, 127};
			itemDef.modifiedModelColors = new int[] {8620, 8872, 8630, 7741, 8635, 99};
			// itemDef.aByte205 = 3;
			break;
			
		case 33812:
			itemDef.name = "Flamburst bow of fury";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61466;
			itemDef.maleModel = 61469;
			itemDef.femaleModel = 61469;
			itemDef.modelZoom = 1704;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 8;
			itemDef.modelRotation2 = 246;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33813:
			itemDef.name = "Flamburst bow of grace";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61467;
			itemDef.maleModel = 61470;
			itemDef.femaleModel = 61470;
			itemDef.modelZoom = 1532;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33814:
			itemDef.name = "flamburst bow of nature";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61468;
			itemDef.maleModel = 61471;
			itemDef.femaleModel = 61471;
			itemDef.modelZoom = 1532;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33815:
			itemDef.name = "Flamburst sword of fury";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61472;
			itemDef.maleModel = 61474;
			itemDef.femaleModel = 61474;
			itemDef.modelZoom = 1434;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 800;
			itemDef.modelRotation1 = 334;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33816:
			itemDef.name = "flamburst sword of grace";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61477;
			itemDef.maleModel = 61475;
			itemDef.femaleModel = 61475;
			itemDef.modelZoom = 1434;
			itemDef.modelOffset1 = 8;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 430;
			itemDef.modelRotation1 = 334;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33818:
			itemDef.name = "flamburst sword of nature";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61473;
			itemDef.maleModel = 61476;
			itemDef.femaleModel = 61476;
			itemDef.modelZoom = 1434;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 518;
			itemDef.modelRotation1 = 334;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33819:
			itemDef.name = "Flamburst staff";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61478;
			itemDef.maleModel = 61481;
			itemDef.femaleModel = 61481;
			itemDef.modelZoom = 2400;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 306;
			itemDef.modelRotation1 = 481;
			itemDef.modelRotationY = 12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33820:
			itemDef.name = "Blue flamburst staff";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61479;
			itemDef.maleModel = 61482;
			itemDef.femaleModel = 61482;
			itemDef.modelZoom = 2600;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 1404;
			itemDef.modelRotation1 = 481;
			itemDef.modelRotationY = 12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33821:
			itemDef.name = "Green flamburst staff";
			itemDef.description = "Special attack: causes the enemy to explode for several hits of dmg.";
			itemDef.modelId = 61480;
			itemDef.maleModel = 61483;
			itemDef.femaleModel = 61483;
			itemDef.modelZoom = 2600;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 997;
			itemDef.modelRotation1 = 481;
			itemDef.modelRotationY = 12;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
			//fury, nature, grace
			//Set bonuses
			//Fury aura: 1:10 chance to Block 5% of damage and sends it back at the monster causing damage equivelent to the damage that was blocked
			//Nature aura: 1:8 chance to Block 8% of damage and poisons the monster with a strong poison causing 4-8 dmg each time it effects to monster 
			//Grace aura: 1:6 chance to Block 10% of damage and adds it directly to the players health as well as restoring 1-3 prayer points when activated
			//Offers 2% to droprates per piece (6% for the set)
		case 33822:
			itemDef.name = "Occult helmet of Fury";
			itemDef.description = "";
			itemDef.modelId = 61484;
			itemDef.maleModel = 61487;
			itemDef.femaleModel = 61487;
			itemDef.modelZoom = 654;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 155;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.maleOffset = 7;
			itemDef.femaleOffset = 7;
			break;
			
		case 33823:
			itemDef.name = "Occult helmet of Nature";
			itemDef.description = "";
			itemDef.modelId = 61486;
			itemDef.maleModel = 61489;
			itemDef.femaleModel = 61489;
			itemDef.modelZoom = 654;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 155;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.maleOffset = 7;
			itemDef.femaleOffset = 7;
			break;
			
		case 33824:
			itemDef.name = "Occult helmet of Grace";
			itemDef.description = "";
			itemDef.modelId = 61485;
			itemDef.maleModel = 61488;
			itemDef.femaleModel = 61488;
			itemDef.modelZoom = 654;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 467;
			itemDef.modelRotationY = 122;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			itemDef.maleOffset = 7;
			itemDef.femaleOffset = 7;
			break;
			
		case 33825:
			itemDef.name = "Occult platebody of Fury";
			itemDef.description = "";
			itemDef.modelId = 61490;
			itemDef.maleModel = 61493;
			itemDef.femaleModel = 61493;
			itemDef.modelZoom = 1380;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33826:
			itemDef.name = "Occult platebody of Nature";
			itemDef.description = "";
			itemDef.modelId = 61492;
			itemDef.maleModel = 61495;
			itemDef.femaleModel = 61495;
			itemDef.modelZoom = 1380;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33827:
			itemDef.name = "Occult platebody of Grace";
			itemDef.description = "";
			itemDef.modelId = 61491;
			itemDef.maleModel = 61494;
			itemDef.femaleModel = 61494;
			itemDef.modelZoom = 1380;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33828:
			itemDef.name = "Occult platelegs of Fury";
			itemDef.description = "";
			itemDef.modelId = 61496;
			itemDef.maleModel = 61499;
			itemDef.femaleModel = 61499;
			itemDef.modelZoom = 1700;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33829:
			itemDef.name = "Occult platelegs of Nature";
			itemDef.description = "";
			itemDef.modelId = 61498;
			itemDef.maleModel = 61501;
			itemDef.femaleModel = 61501;
			itemDef.modelZoom = 1700;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33830:
			itemDef.name = "Occult platelegs of Grace";
			itemDef.description = "";
			itemDef.modelId = 61497;
			itemDef.maleModel = 61500;
			itemDef.femaleModel = 61500;
			itemDef.modelZoom = 1700;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -1;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 529;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33831:
			itemDef.name = "Flamburst bow string";
			itemDef.description = "Used with the Flamburst bow (unstrung) to create the Flamburst bow!";
			itemDef.modelId = 2552;
			itemDef.modelZoom = 530;
			itemDef.modelRotation1 = 145;
			itemDef.modelRotation2 = 1881;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			itemDef.modifiedModelColors = new int[] {7467, 7479};
			itemDef.originalModelColors = new int[] {675, 620};
			// itemDef.aByte205 = 3;
			break;
			
		case 33832:
			itemDef.name = "Flamburst bow (unstrung)";
			itemDef.description = "Used with a Flamburst bow string to create the FLamburst bow!";
			itemDef.modelId = 61507;
			itemDef.modelZoom = 1778;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 789;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			// itemDef.aByte205 = 3;
			break;
			
		case 33833:
			itemDef.name = "Flamburst blade";
			itemDef.description = "Used with a Flamburst hilt to create the Flamburst sword!";
			itemDef.modelId = 61503;
			itemDef.modelZoom = 1223;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 1404;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			// itemDef.aByte205 = 3;
			break;
			
		case 33834:
			itemDef.name = "Flamburst hilt";
			itemDef.description = "Used with a Flamburst blade to create the Flamburst sword!";
			itemDef.modelId = 61504;
			itemDef.modelZoom = 964;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 1270;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			// itemDef.aByte205 = 3;
			break;
			
		case 33835:
			itemDef.name = "Flamburst staff handle";
			itemDef.description = "Used with a Flamburst staff head to create the Flamburst staff!";
			itemDef.modelId = 61505;
			itemDef.modelZoom = 1606;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 1763;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 0;
			// itemDef.aByte205 = 3;
			break;
			
		case 33836:
			itemDef.name = "Flamburst staff head";
			itemDef.description = "Used with a Flamburst staff handle to create the Flamburst staff!";
			itemDef.modelId = 61506;
			itemDef.modelZoom = 1248;
			itemDef.modelOffset1 = -5;
			itemDef.modelOffset2 = 10;
			itemDef.modelRotation2 = 1850;
			itemDef.modelRotation1 = 519;
			itemDef.modelRotationY = 86;
			// itemDef.aByte205 = 3;
			break;
			
		case 33872:
			itemDef.name = "Sword of edicts";
			itemDef.groundOptions = new String[] { null, null, "Take", null, null };
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50148;
			itemDef.maleModel = 50149;
			itemDef.femaleModel = 50149;
			itemDef.modelZoom = 1726;
			itemDef.modelRotation1 = 1576;
			itemDef.modelRotation2 = 242;
			itemDef.modelOffset2 = 5;
			itemDef.modelOffset1 = 4;
			itemDef.description = "Sword of guthix.";
			break;
			
		case 33873:
			itemDef.name = "Pet s c a r e d";
			itemDef.description = "Hehes custom pet.";
			itemDef.modelId = 50167;
			itemDef.modelZoom = 4000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33874:
			itemDef.name = "Pet sunerwatt";
			itemDef.description = "Darrens custom pet.";
			itemDef.modelId = 50168;
			itemDef.modelZoom = 4000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 19841:
			itemDef.name = "Casket (Master)";
			break;
			
		case 33875:
			itemDef.name = "Occult cape";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61510;
			itemDef.maleModel = 61509;
			itemDef.femaleModel = 61509;
			itemDef.modelZoom = 2048;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 511;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.description = "A cape boosting droprates by 5%.";
			break;
			
		case 33876:
			itemDef.name = "Nature Wings";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61512;
			itemDef.maleModel = 61511;
			itemDef.femaleModel = 61511;
			itemDef.modelZoom = 1911;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 1502;
			itemDef.modelRotationY = 0;
			itemDef.description = "Raises XP gained while training prayer by 5%";
			break;
			
		case 33877:
			itemDef.name = "Fury Wings";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61514;
			itemDef.maleModel = 61513;
			itemDef.femaleModel = 61513;
			itemDef.modelZoom = 1911;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 1502;
			itemDef.modelRotationY = 0;
			itemDef.description = "Raises XP gained while training prayer by 5%";
			break;
			
		case 33878:
			itemDef.name = "Grace Wings";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61516;
			itemDef.maleModel = 61515;
			itemDef.femaleModel = 61515;
			itemDef.modelZoom = 1911;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 1502;
			itemDef.modelRotationY = 0;
			itemDef.description = "Raises XP gained while training prayer by 5%";
			break;
			
		case 33879:
			itemDef.name = "Boots of Fury";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61517;
			itemDef.maleModel = 61517;
			itemDef.femaleModel = 61518;
			itemDef.modelZoom = 762;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelRotation2 = 282;
			itemDef.modelRotation1 = 159;
			itemDef.modelRotationY = 0;
			itemDef.description = "Players will take 5% less damage from all monsters that are not considered a boss.";
			break;
			
		case 33880:
			itemDef.name = "Boots of Grace";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61519;
			itemDef.maleModel = 61519;
			itemDef.femaleModel = 61520;
			itemDef.modelZoom = 762;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelRotation2 = 282;
			itemDef.modelRotation1 = 159;
			itemDef.modelRotationY = 0;
			itemDef.description = "Players will take 5% less damage from all monsters that are not considered a boss.";
			break;
			
		case 33881:
			itemDef.name = "Boots of Nature";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61521;
			itemDef.maleModel = 61521;
			itemDef.femaleModel = 61522;
			itemDef.modelZoom = 762;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelRotation2 = 282;
			itemDef.modelRotation1 = 159;
			itemDef.modelRotationY = 0;
			itemDef.description = "Players will take 5% less damage from all monsters that are not considered a boss.";
			break;
			
		case 33882:
			itemDef.name = "Nature dye";
			itemDef.description = "applies the Nature perk and recolor to a Fury weapon.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 21939, 21945, 21952, 21954 };
			break;
			
		case 33883:
			itemDef.name = "Grace dye";
			itemDef.description = "applies the Grace perk and recolor to a Fury weapon.";
			itemDef.modelId = 60513;
			itemDef.modelZoom = 809;
			itemDef.modelRotation1 = 90;
			itemDef.modelRotation2 = 2047;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -9;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 30814, 30809, 30799, 30804 };
			itemDef.originalModelColors = new int[] { 43929, 43929, 43929, 43929 };
			break;
			
		case 33884:
			itemDef.name = "Elder Mystery Box";
			itemDef.inventoryOptions = new String[] { "Open", null, null, null, "Drop" };
			itemDef.stackable = false;
			itemDef.modelId = 61110;
			itemDef.modelZoom = 1180;
			itemDef.modelRotation1 = 160;
			itemDef.modelRotation2 = 172;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -14;
			// itemDef.modifiedModelColors = new int[] {22410, 2999};
			// itemDef.originalModelColors = new int[] {35321, 350};
			itemDef.description = "Chances for Prayer boosting Wings, Dyes and The Occult cape!";
			break;
			
		case 33885:
			itemDef.name = "Pet Elder troll";
			itemDef.description = "A pet received from the Elder troll.";
			itemDef.modelId = 35830;
			itemDef.modelZoom = 6000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33886:
			itemDef.name = "Pet Mutated rat";
			itemDef.description = "A pet received from the Mutated rat.";
			itemDef.modelId = 26073;
			itemDef.modelZoom = 5000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -90;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33887:
			itemDef.name = "Pet Undead warrior";
			itemDef.description = "A pet received from the Undead warrior.";
			itemDef.modelId = 24188;
			itemDef.modelZoom = 4000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33888:
			itemDef.name = "Pet Shaded beast";
			itemDef.description = "A pet received from the Shaded beast.";
			itemDef.modelId = 37294;
			itemDef.modelZoom = 5000;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33889:
			itemDef.name = "Pet Dark centaur";
			itemDef.description = "A pet received from the Dark centaur.";
			itemDef.modelId = 61523;
			itemDef.modelZoom = 4300;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 123;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotationY = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33890:
			itemDef.name = "Starter cape";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61525;
			itemDef.maleModel = 61524;
			itemDef.femaleModel = 61524;
			itemDef.modelZoom = 2750;
			itemDef.modelOffset1 = -21;
			itemDef.modelOffset2 = 35;
			itemDef.modelRotation2 = 1020;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.description = "A cape providing a 3% boost to droprates.";
			break;
			
		case 33891:
			itemDef.name = "Starter bow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61527;
			itemDef.maleModel = 61526;
			itemDef.femaleModel = 61526;
			itemDef.modelZoom = 1850;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 267;
			itemDef.modelRotation1 = 564;
			itemDef.modelRotationY = 0;
			itemDef.description = "This bow will grow more powerful as you level up your Range level.";
			break;
			
		case 33892:
			itemDef.name = "Starter shield";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61529;
			itemDef.maleModel = 61528;
			itemDef.femaleModel = 61528;
			itemDef.modelZoom = 1283;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 0;
			itemDef.modelRotation1 = 500;
			itemDef.modelRotationY = 0;
			itemDef.description = "This shield has a 1:10 chance to block 10% of incoming damage.";
			break;
			
		case 33893:
			itemDef.name = "Starter rapier";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61531;
			itemDef.maleModel = 61530;
			itemDef.femaleModel = 61530;
			itemDef.modelZoom = 1603;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 721;
			itemDef.modelRotation1 = 466;
			itemDef.modelRotationY = 0;
			itemDef.description = "This rapier will grow more powerful as you level up your Attack level.";
			break;
			
		case 33894:
			itemDef.name = "Starter wand";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 61533;
			itemDef.maleModel = 61532;
			itemDef.femaleModel = 61532;
			itemDef.modelZoom = 1011;
			itemDef.modelOffset1 = 4;
			itemDef.modelOffset2 = 5;
			itemDef.modelRotation2 = 1264;
			itemDef.modelRotation1 = 687;
			itemDef.modelRotationY = 0;
			itemDef.description = "This wand will grow more powerful as you level up your Magic level.";
			break;
			
		case 33895:
			itemDef.name = "Clue scroll (master)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Read";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 37183;
			itemDef.modelZoom = 1010;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 672;
			itemDef.modelRotation1 = 360;
			itemDef.modelRotationY = 0;
			itemDef.description = "A Master level clue scroll.";
			break;
			
		case 33896:
			itemDef.name = "Clue scroll (master)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Read";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 37183;
			itemDef.modelZoom = 1010;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 672;
			itemDef.modelRotation1 = 360;
			itemDef.modelRotationY = 0;
			itemDef.description = "A Master level clue scroll.";
			break;
			
		case 33897:
			itemDef.name = "Clue scroll (master)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Read";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 37183;
			itemDef.modelZoom = 1010;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 672;
			itemDef.modelRotation1 = 360;
			itemDef.modelRotationY = 0;
			itemDef.description = "A Master level clue scroll.";
			break;
			
		case 33898:
			itemDef.name = "Clue scroll (master)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Read";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 37183;
			itemDef.modelZoom = 1010;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = 0;
			itemDef.modelRotation2 = 672;
			itemDef.modelRotation1 = 360;
			itemDef.modelRotationY = 0;
			itemDef.description = "A Master level clue scroll.";
			break;
			
		case 33899:
			itemDef.name = "Blood twisted bow (Perk)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33424;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Twisted bow that offers healing abilities";
			break;
			
		case 33921:
			itemDef.modelId = 50200;
			itemDef.modelZoom = 800;
			itemDef.modelRotation1 = 552;
			itemDef.modelRotation2 = 28;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 2;
			break;
			
		case 33900:
			itemDef.name = "Baby onyx dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {-22008, -22013, -22011, -22016, -27459, -27450};
			break;
			
		case 33901:
			itemDef.name = "Baby Sapphire dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {-21585, -21590, -21595, -21570, -27459, -27450};
			break;
			
		case 33902:
			itemDef.name = "Baby emerald dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {22443, 22447, 22449, 22451, -27459, -27450};
			break;
			
		case 33903:
			itemDef.name = "Baby ruby dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {931, 935, 940, 943, -27459, -27450};
			break;
			
		case 33904:
			itemDef.name = "Baby diamond dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {127, 125, 123, 120, -27459, -27450};
			break;
			
		case 33905:
			itemDef.name = "Baby dragonstone dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {-14425, -14430, -14435, -14440, -27459, -27450};
			break;
			
		case 33906:
			itemDef.name = "Baby Zenyte dragon";
			itemDef.modelId = 17413;
			itemDef.modelZoom = 2048;
			itemDef.modelRotation1 = 49;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {910, 912, 1938, 1814, 2588, 2469};
		    itemDef.originalModelColors = new int[] {5056, 5060, 5065, 5070, -27459, -27450};
			break;
			
		case 33907:
			itemDef.name = "Pet Ambassador";
			itemDef.modelId = 50180;
			itemDef.modelZoom = 8000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33908:
			itemDef.name = "Barrows Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50181;
			itemDef.maleModel = 50184;
			itemDef.femaleModel = 50184;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33909:
			itemDef.name = "Blood Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50182;
			itemDef.maleModel = 50183;
			itemDef.femaleModel = 50183;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33910:
			itemDef.name = "Ice Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50185;
			itemDef.maleModel = 50186;
			itemDef.femaleModel = 50186;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33911:
			itemDef.name = "Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50187;
			itemDef.maleModel = 50188;
			itemDef.femaleModel = 50188;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			break;
			
		case 33912:
			itemDef.name = "Sirenic scale";
			itemDef.modelId = 50189;
			itemDef.modelZoom = 665;
			itemDef.modelRotation1 = 152;
			itemDef.modelRotation2 = 1895;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 10;
			itemDef.stackable = true;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "Use this on Algarum thread dropped by Solak to create Sirenic armor.";
			break;
			
		case 33913:
			itemDef.name = "Algarum thread";
			itemDef.modelId = 50203;
			itemDef.modelZoom = 835;
			itemDef.modelRotation1 = 578;
			itemDef.modelRotation2 = 1535;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "Use this on Sirenic scales dropped by Solak to create Sirenic armor.";
			break;
			
		case 33914:
			itemDef.name = "Shadow Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50190;
			itemDef.maleModel = 50201;
			itemDef.femaleModel = 50201;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33915:
			itemDef.name = "Seren godbow stock";
			itemDef.modelId = 50191;
			itemDef.modelZoom = 1184;
			itemDef.modelRotation1 = 493;
			itemDef.modelRotation2 = 1640;
			itemDef.modelRotationY = 1011;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "Use this on a Seren godbow (unf) to create a Seren godbow (uncharged).";
			break;
			
		case 33916:
			itemDef.name = "Seren godbow (unf)";
			itemDef.modelId = 50192;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.description = "Use this on a Seren godbow stock to create a Seren godbow (uncharged).";
			break;
			
		case 33917:
			itemDef.name = "Sirenic boots";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50193;
			itemDef.maleModel = 50193;
			itemDef.femaleModel = 50193;
			itemDef.modelZoom = 862;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelRotation2 = 282;
			itemDef.modelRotation1 = 159;
			itemDef.modelRotationY = 0;
			itemDef.description = "T90 range armor dropped by Solak.";
			break;
			
		case 33918:
			itemDef.name = "Sirenic Mask";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50194;
			itemDef.maleModel = 50196;
			itemDef.femaleModel = 51096;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.maleOffset = 3;
			break;
			
		case 33919:
			itemDef.name = "Sirenic body";
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.modelId = 50198;
			itemDef.maleModel = 50199;
			itemDef.femaleModel = 50199;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			itemDef.maleOffset = 3;
			break;
			
		case 33920:
			itemDef.name = "Sirenic chaps";
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.modelId = 50197;
			itemDef.maleModel = 50195;
			itemDef.femaleModel = 50195;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			// itemDef.aByte205 = 3;
			break;
			
		case 33922:
			itemDef.name = "Third-age Seren godbow";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50202;
			itemDef.maleModel = 50205;
			itemDef.femaleModel = 50205;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33923:
			itemDef.name = "Pet Solak";
			itemDef.modelId = 50204;
			itemDef.modelZoom = 8000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33924:
			itemDef.name = "blood Seren godbow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33909;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A cosmetic recolor for the Seren godbow.";
			break;
			
		case 33925:
			itemDef.name = "Thirdage Seren godbow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33922;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A cosmetic recolor for the Seren godbow.";
			break;
			
		case 33926:
			itemDef.name = "Shadow Seren godbow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33914;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A cosmetic recolor for the Seren godbow.";
			break;
			
		case 33927:
			itemDef.name = "Ice Seren godbow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33910;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A cosmetic recolor for the Seren godbow.";
			break;
			
		case 33928:
			itemDef.name = "Barrows Seren godbow";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33908;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A cosmetic recolor for the Seren godbow.";
			break;
			
		case 33929:
			itemDef.name = "Sirenic gloves";
			itemDef.description = "T90 range gloves dropped by solak.";
			itemDef.modelId = 13631;
			itemDef.maleModel = 13307;
			itemDef.femaleModel = 13319;
			itemDef.modelZoom = 789;
            itemDef.modelRotation1 = 609;
            itemDef.modelRotation2 = 111;
            itemDef.modelOffset1 = 0;
            itemDef.modelOffset2 = -1;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] { 8656, 10394 };
			itemDef.originalModelColors = new int[] { 44928,40611  };
			// itemDef.aByte205 = 3;
			break;
			
		case 33930:
			itemDef.name = "Valius imp (melee)";
			itemDef.modelId = 50206;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -49;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Change Boost";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33931:
			itemDef.name = "Valius imp (range)";
			itemDef.modelId = 50206;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -49;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Change Boost";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {1563, 1568, 1685, 1687, 411, 416, 1692, 420, 2711, 1811};
			itemDef.originalModelColors = new int[] { 23449, 23444, 23442, 23440, 23438, 23436, 23434, 23432, 23430, 23428, 23426  };
			break;
			
		case 33932:
			itemDef.name = "Valius imp (mage)";
			itemDef.modelId = 50206;
			itemDef.modelZoom = 2000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -49;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Change Boost";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {1563, 1568, 1685, 1687, 411, 416, 1692, 420, 2711, 1811};
			itemDef.originalModelColors = new int[] { 38590, 38588, 38586, 38584, 38582, 38580, 38578, 38576, 38574, 38572, 38570};
			break;
			
		case 33933:
			itemDef.name = "Sirenic Mask (Uncharged)";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50194;
			itemDef.maleModel = 50196;
			itemDef.femaleModel = 51096;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.description = "none";
			itemDef.maleOffset = 3;
			break;
			
		case 33934:
			itemDef.name = "Sirenic body (Uncharged)";
			itemDef.description = "none.";
			itemDef.modelId = 50198;
			itemDef.maleModel = 50199;
			itemDef.femaleModel = 50199;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
			
		case 33935:
			itemDef.name = "Sirenic chaps (Uncharged)";
			itemDef.description = "none.";
			itemDef.modelId = 50197;
			itemDef.maleModel = 50195;
			itemDef.femaleModel = 50195;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;
			
		case 33936:
			itemDef.name = "Seren godbow (uncharged)";
			itemDef.description = "An intricate bow of pure crystal.";
			itemDef.modelId = 50187;
			itemDef.maleModel = 50188;
			itemDef.femaleModel = 50188;
			itemDef.modelZoom = 1948;
			itemDef.modelRotation1 = 484;
			itemDef.modelRotation2 = 1054;
			itemDef.modelOffset1 = 5;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			break;
			
		case 33940:
			itemDef.name = "Sirenic boots";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50193;
			itemDef.maleModel = 50193;
			itemDef.femaleModel = 50193;
			itemDef.modelZoom = 862;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -15;
			itemDef.modelRotation2 = 282;
			itemDef.modelRotation1 = 159;
			itemDef.modelRotationY = 0;
			itemDef.description = "T90 range armor dropped by Solak.";
			break;
			
		case 33937:
			itemDef.name = "Sirenic Mask";
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			itemDef.inventoryOptions[2] = null;
			itemDef.modelId = 50194;
			itemDef.maleModel = 50196;
			itemDef.femaleModel = 51096;
			itemDef.modelZoom = 900;
			itemDef.modelRotation1 = 16;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 2;
			itemDef.modelOffset2 = -4;
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.maleOffset = 3;
			itemDef.modifiedModelColors = new int[] {43274, 43282, 39344, 37156, 38159, 31217, 39316, 39203, 38314, 38320, 38304, 35922, 39325, 38306, 38336, 38324, 39326, 38329, 38325, 38341, 38311, 38335, 38290, 38298, 40356, 36220};
			itemDef.originalModelColors = new int[] {910, 912, 914, 916, 918, 920, 922, 915, 916, 909, 908, 907, 901, 909, 910, 911, 912, 916, 907, 909, 902, 901, 900, 903, 915, 0};
			break;
			
		case 33938:
			itemDef.name = "Sirenic body";
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.modelId = 50198;
			itemDef.maleModel = 50199;
			itemDef.femaleModel = 50199;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			itemDef.maleOffset = 3;
			break;
			
		case 33939:
			itemDef.name = "Sirenic chaps";
			itemDef.description = "T90 range armor dropped by Solak.";
			itemDef.modelId = 50197;
			itemDef.maleModel = 50195;
			itemDef.femaleModel = 50195;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions = new String[] { null, "Wear", null, null, "Uncharge" };
			// itemDef.aByte205 = 3;
			break;
			
		case 33941:
			itemDef.name = "Chambers of xeric chest";
			itemDef.description = "Contains all chambers of xeric loot.";
			itemDef.modelId = 35439;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {6986, 6852, 7120};
			itemDef.originalModelColors = new int[] {28826, 28830, 28836};
			// itemDef.aByte205 = 3;
			break;
			
		case 33942:
			itemDef.name = "Trials of xeric chest";
			itemDef.description = "Contains all trials of xeric loot.";
			itemDef.modelId = 35439;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {6986, 6852, 7120};
			itemDef.originalModelColors = new int[] {51605, 51610, 51615};
			// itemDef.aByte205 = 3;
			break;
			
		case 33943:
			itemDef.name = "Theatre of blood chest";
			itemDef.description = "Contains all theatre of blood loot.";
			itemDef.modelId = 35439;
			itemDef.modelZoom = 5000;
			itemDef.modelRotation1 = 0;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = 0;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[0] = "Open";
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {6986, 6852, 7120};
			itemDef.originalModelColors = new int[] {910, 915, 920};
			break;
			
			
		case 33944:
			itemDef.name = "White d'hide body";
			itemDef.description = "Made from 100% real dragonhide.";
			itemDef.modelId = 2745;
			itemDef.maleModel = 314;
			itemDef.femaleModel = 477;
			itemDef.modelZoom = 1030;
			itemDef.modelRotation1 = 488;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.modelOffset2 = -4;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {22416, 22424};
			itemDef.originalModelColors = new int[] {110, 70};
			break;
			
		case 33945:
			itemDef.name = "White d'hide chaps";
			itemDef.description = "Made from 100% real dragonhide.";
			itemDef.modelId = 2507;
			itemDef.maleModel = 258;
			itemDef.femaleModel = 423;
			itemDef.modelZoom = 1720;
			itemDef.modelRotation1 = 488;
			itemDef.modelRotation2 = 224;
			itemDef.modelOffset1 = 7;
			itemDef.modelOffset2 = 5;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {22416, 22424};
			itemDef.originalModelColors = new int[] {110, 70};
			break;
		case 33946:
			itemDef.name = "White d'hide vamp";
			itemDef.description = "Made from 100% real dragonhide.";
			itemDef.modelId = 2656;
			itemDef.maleModel = 178;
			itemDef.femaleModel = 355;
			itemDef.modelZoom = 740;
			itemDef.modelRotation1 = 196;
			itemDef.modelRotation2 = 1784;
			itemDef.modelOffset1 = 7;
			itemDef.modelOffset2 = -33;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wield";
			itemDef.inventoryOptions[2] = null;
			itemDef.modifiedModelColors = new int[] {8472, 8720, 22416};
			itemDef.originalModelColors = new int[] {110, 70, 110};
			break;
			
		case 33947:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33375;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Justiciar helmet offering healing abilities.";
			break;
			
		case 33948:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33376;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Justiciar platebody offering healing abilities.";
			break;
			
		case 33949:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33377;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Justiciar platelegs offering healing abilities.";
			break;
			
		case 33950:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33536;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Boogie bow offering healing abilities.";
			break;
			
		case 33951:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33380;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Scythe of Vitur offering healing abilities.";
			break;
			
		case 33952:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33535;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Skin for the Obliteration staff offering healing abilities.";
			break;
			
		case 33953:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33672;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Cosmetic Skin for the Ghrazi rapier.";
			break;
			
		case 33954:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33673;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Cosmetic Skin for the Sanguinetti staff.";
			break;
			
		case 33955:
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = null;
			itemDef.inventoryOptions[2] = null;
			itemDef.certID = 33671;
			itemDef.certTemplateID = 33921;
			itemDef.description = "A Cosmetic Skin for the Twisted bow.";
			break;
			
		case 33956:
			itemDef.name = "Christmas Top";
			itemDef.description = "A top from the 2019 Christmas event on Valius.";
			itemDef.modelId = 50225;
			itemDef.maleModel = 50217;
			itemDef.femaleModel = 50218;
			itemDef.modelZoom = 1513;
			itemDef.modelRotation1 = 566;
			itemDef.modelRotation2 = 0;
			itemDef.modelOffset1 = 1;
			itemDef.modelOffset2 = -8;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			itemDef.maleOffset = 3;
			break;
		case 33957:
			itemDef.name = "Christmas legs";
			itemDef.description = "A pair of pants from the 2019 Christmas event on Valius.";
			itemDef.modelId = 50224;
			itemDef.maleModel = 50216;
			itemDef.femaleModel = 50215;
			itemDef.modelZoom = 1753;
			itemDef.modelRotation1 = 562;
			itemDef.modelRotation2 = 1;
			itemDef.modelOffset1 = 11;
			itemDef.modelOffset2 = -3;
			itemDef.inventoryOptions = new String[5];
			itemDef.inventoryOptions[1] = "Wear";
			itemDef.inventoryOptions[2] = null;
			// itemDef.aByte205 = 3;
			break;



case 33958:
	itemDef.name = "Snowman mask";
	itemDef.description = "A mask from the 2019 Christmas event on Valius.";
	itemDef.modelId = 50226;
	itemDef.maleModel = 50222;
	itemDef.femaleModel = 50222;
	itemDef.modelZoom = 1010;
	itemDef.modelRotation1 = 16;
	itemDef.modelRotation2 = 0;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = "Wear";
	itemDef.inventoryOptions[2] = null;
	itemDef.maleOffset = 3;
	break;
	
case 33959:
	itemDef.name = "Santa's wand";
	itemDef.description = "A wand from the 2019 Christmas event on Valius.";
	itemDef.modelId = 50232;
	itemDef.maleModel = 50231;
	itemDef.femaleModel = 50231;
	itemDef.modelZoom = 950;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 0;
	itemDef.modelRotation1 = 16;
	itemDef.modelRotationY = 801;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = "Wear";
	itemDef.inventoryOptions[2] = null;
	itemDef.maleOffset = 3;
	break;
	
case 33960:
	itemDef.name = "Santa's rapier";
	itemDef.description = "A rapier from the 2019 Christmas event on Valius.";
	itemDef.modelId = 50230;
	itemDef.maleModel = 50229;
	itemDef.femaleModel = 50229;
	itemDef.modelZoom = 1010;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 0;
	itemDef.modelRotation1 = 28;
	itemDef.modelRotationY = 259;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = "Wear";
	itemDef.inventoryOptions[2] = null;
	itemDef.maleOffset = 3;
	break;
	
case 33961:
	itemDef.name = "Christmas present";
	itemDef.description = "Open this to receive some christmas rewards.";
	itemDef.modelId = 50228;
	itemDef.modelZoom = 874;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 24;
	itemDef.modelRotation1 = 126;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[0] = "Open";
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	itemDef.maleOffset = 3;
	break;
	
case 33962:
	itemDef.name = "Christmas ornament";
	itemDef.description = "Christmas 2019 currency.";
	itemDef.stackable = true;
	itemDef.modelId = 50227;
	itemDef.modelZoom = 750;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 0;
	itemDef.modelRotation1 = 16;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	itemDef.maleOffset = 3;
	break;
	
case 33963:
	itemDef.name = "Mini Santa";
	itemDef.description = "up to 100% more Christmas ornaments when killing bosses/monsters while following.";
	itemDef.modelId = 50221;
	itemDef.modelZoom = 4000;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 135;
	itemDef.modelRotation1 = 0;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	break;
	
case 33964:
	itemDef.name = "Pet Rudolph";
	itemDef.description = "up to 25% more Christmas ornaments while skilling.";
	itemDef.modelId = 50220;
	itemDef.modelZoom = 4000;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 135;
	itemDef.modelRotation1 = 0;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	break;
	
case 33965:
	itemDef.name = "Pet jolly Rudolph";
	itemDef.description = "up to 25% more Ornaments while skilling + 1:4 chance to receive Presents from hourly rewards.";
	itemDef.modelId = 50220;
	itemDef.modelZoom = 4000;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 135;
	itemDef.modelRotation1 = 0;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	break;
	
case 33966:
	itemDef.name = "Pet Frosty";
	itemDef.description = "up to 50% more Ornaments while killing bosses or monsters.";
	itemDef.modelId = 50223;
	itemDef.modelZoom = 4000;
	itemDef.modelOffset1 = 2;
	itemDef.modelOffset2 = -4;
	itemDef.modelRotation2 = 135;
	itemDef.modelRotation1 = 0;
	itemDef.modelRotationY = 0;
	itemDef.inventoryOptions = new String[5];
	itemDef.inventoryOptions[1] = null;
	itemDef.inventoryOptions[2] = null;
	break;
			
		}
		
	}

	void method2789(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		modelZoom = var1.modelZoom * 1;
		modelRotation1 = 1 * var1.modelRotation1;
		modelRotation2 = 1 * var1.modelRotation2;
		modelRotationY = 1 * var1.modelRotationY;
		modelOffset1 = 1 * var1.modelOffset1;
		modelOffset2 = var1.modelOffset2 * 1;
		originalModelColors = var2.originalModelColors;
		modifiedModelColors = var2.modifiedModelColors;
		// originalTextureColors = var2.originalTextureColors;
		// modifiedTextureColors = var2.modifiedTextureColors;
		name = var2.name;
		membersObject = var2.membersObject;
		stackable = var2.stackable;
		maleModel = 1 * var2.maleModel;
		anInt188 = 1 * var2.anInt188;
		anInt185 = 1 * var2.anInt185;
		femaleModel = var2.femaleModel * 1;
		anInt164 = var2.anInt164 * 1;
		anInt162 = 1 * var2.anInt162;
		anInt175 = 1 * var2.anInt175;
		anInt166 = var2.anInt166 * 1;
		anInt197 = var2.anInt197 * 1;
		anInt173 = var2.anInt173 * 1;
		team = var2.team * 1;
		groundOptions = var2.groundOptions;
		inventoryOptions = new String[5];
		equipActions = new String[5];
		if (null != var2.inventoryOptions) {
			for (int var4 = 0; var4 < 4; ++var4) {
				inventoryOptions[var4] = var2.inventoryOptions[var4];
			}
		}

		inventoryOptions[4] = "Discard";
		value = 0;
	}

	void method2790(ItemDefinition var1, ItemDefinition var2) {
		modelId = var1.modelId * 1;
		modelZoom = 1 * var1.modelZoom;
		modelRotation1 = var1.modelRotation1 * 1;
		modelRotation2 = var1.modelRotation2 * 1;
		modelRotationY = var1.modelRotationY * 1;
		modelOffset1 = 1 * var1.modelOffset1;
		modelOffset2 = var1.modelOffset2 * 1;
		originalModelColors = var1.originalModelColors;
		modifiedModelColors = var1.modifiedModelColors;
		originalTextureColors = var1.originalTextureColors;
		modifiedTextureColors = var1.modifiedTextureColors;
		stackable = var1.stackable;
		name = var2.name;
		value = 0;
	}

	/*
	 * private void readValues(Stream stream) { while(true) { int opcode =
	 * stream.readUnsignedByte(); if(opcode == 0) return; if(opcode == 1) modelId =
	 * stream.readUnsignedWord(); else if(opcode == 2) name = stream.readString();
	 * else if(opcode == 3) description = stream.readString(); else if(opcode == 4)
	 * modelZoom = stream.readUnsignedWord(); else if(opcode == 5) modelRotation1 =
	 * stream.readUnsignedWord(); else if(opcode == 6) modelRotation2 =
	 * stream.readUnsignedWord(); else if(opcode == 7) { modelOffset1 =
	 * stream.readUnsignedWord(); if(modelOffset1 > 32767) modelOffset1 -= 0x10000;
	 * } else if(opcode == 8) { modelOffset2 = stream.readUnsignedWord();
	 * if(modelOffset2 > 32767) modelOffset2 -= 0x10000; } else if(opcode == 11)
	 * stackable = true; else if(opcode == 12) value = stream.readDWord(); else
	 * if(opcode == 16) membersObject = true; else if(opcode == 23) { maleModel =
	 * stream.readUnsignedWord(); aByte205 = stream.readSignedByte(); } else if
	 * (opcode == 24) anInt188 = stream.readUnsignedWord(); else if (opcode == 25) {
	 * femaleModel = stream.readUnsignedWord(); aByte154 = stream.readSignedByte();
	 * } else if (opcode == 26) anInt164 = stream.readUnsignedWord(); else if(opcode
	 * >= 30 && opcode < 35) { if(groundOptions == null) groundOptions = new
	 * String[5]; groundOptions[opcode - 30] = stream.readString();
	 * if(groundOptions[opcode - 30].equalsIgnoreCase("hidden"))
	 * groundOptions[opcode - 30] = null; } else if(opcode >= 35 && opcode < 40) {
	 * if(inventoryOptions == null) inventoryOptions = new String[5];
	 * inventoryOptions[opcode - 35] = stream.readString(); } else if(opcode == 40)
	 * { int size = stream.readUnsignedByte(); originalModelColors = new int[size];
	 * modifiedModelColors = new int[size]; for(int index = 0; index < size;
	 * index++) { originalModelColors[index] = stream.readUnsignedWord();
	 * modifiedModelColors[index] = stream.readUnsignedWord(); } } else if(opcode ==
	 * 41) { int size = stream.readUnsignedByte(); originalTextureColors = new
	 * short[size]; modifiedTextureColors = new short[size]; for(int index = 0;
	 * index < size; index++) { originalTextureColors[index] = (short)
	 * stream.readUnsignedWord(); modifiedTextureColors[index] = (short)
	 * stream.readUnsignedWord(); } } else if(opcode == 65) { searchableItem = true;
	 * } else if(opcode == 78) anInt185 = stream.readUnsignedWord(); else if(opcode
	 * == 79) anInt162 = stream.readUnsignedWord(); else if(opcode == 90) anInt175 =
	 * stream.readUnsignedWord(); else if(opcode == 91) anInt197 =
	 * stream.readUnsignedWord(); else if(opcode == 92) anInt166 =
	 * stream.readUnsignedWord(); else if(opcode == 93) anInt173 =
	 * stream.readUnsignedWord(); else if(opcode == 95) anInt204 =
	 * stream.readUnsignedWord(); else if(opcode == 97) certID =
	 * stream.readUnsignedWord(); else if(opcode == 98) certTemplateID =
	 * stream.readUnsignedWord(); else if (opcode >= 100 && opcode < 110) { if
	 * (stackIDs == null) { stackIDs = new int[10]; stackAmounts = new int[10]; }
	 * stackIDs[opcode - 100] = stream.readUnsignedWord(); stackAmounts[opcode -
	 * 100] = stream.readUnsignedWord(); } else if(opcode == 110) anInt167 =
	 * stream.readUnsignedWord(); else if(opcode == 111) anInt192 =
	 * stream.readUnsignedWord(); else if(opcode == 112) anInt191 =
	 * stream.readUnsignedWord(); else if(opcode == 113) anInt196 =
	 * stream.readSignedByte(); else if(opcode == 114) anInt184 =
	 * stream.readSignedByte() * 5; else if(opcode == 115) team =
	 * stream.readUnsignedByte(); else if (opcode == 139) opcode139 =
	 * stream.readUnsignedWord(); else if (opcode == 140) opcode140 =
	 * stream.readUnsignedWord(); else if (opcode == 148) opcode148 =
	 * stream.readUnsignedWord(); else if (opcode == 149) opcode149 =
	 * stream.readUnsignedWord(); else { System.out.println("Error loading item " +
	 * id + ", opcode " + opcode); } } }
	 */

	private void readValues(Buffer stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				return;
			if (opcode == 1)
				modelId = stream.readShort();
			else if (opcode == 2)
				name = stream.readString();
			else if (opcode == 3)
				description = stream.readString();
			else if (opcode == 4)
				modelZoom = stream.readShort();
			else if (opcode == 5)
				modelRotation1 = stream.readShort();
			else if (opcode == 6)
				modelRotation2 = stream.readShort();
			else if (opcode == 7) {
				modelOffset1 = stream.readShort();
				if (modelOffset1 > 32767)
					modelOffset1 -= 0x10000;
			} else if (opcode == 8) {
				modelOffset2 = stream.readShort();
				if (modelOffset2 > 32767)
					modelOffset2 -= 0x10000;
			} else if (opcode == 11)
				stackable = true;
			else if (opcode == 12)
				value = stream.readInt();
			else if (opcode == 16)
				membersObject = true;
			else if (opcode == 23) {
				maleModel = stream.readShort();
				maleOffset = stream.readSignedByte();
			} else if (opcode == 24)
				anInt188 = stream.readShort();
			else if (opcode == 25) {
				femaleModel = stream.readShort();
				femaleOffset = stream.readSignedByte();
			} else if (opcode == 26)
				anInt164 = stream.readShort();
			else if (opcode >= 30 && opcode < 35) {
				if (groundOptions == null)
					groundOptions = new String[5];
				groundOptions[opcode - 30] = stream.readString();
				if (groundOptions[opcode - 30].equalsIgnoreCase("hidden"))
					groundOptions[opcode - 30] = null;
			} else if (opcode >= 35 && opcode < 40) {
				if (inventoryOptions == null)
					inventoryOptions = new String[5];
				inventoryOptions[opcode - 35] = stream.readString();
			} else if (opcode == 40) {
				int size = stream.readUnsignedByte();
				originalModelColors = new int[size];
				modifiedModelColors = new int[size];
				for (int index = 0; index < size; index++) {
					originalModelColors[index] = stream.readShort();
					modifiedModelColors[index] = stream.readShort();
				}
			} else if (opcode == 41) {
				int size = stream.readUnsignedByte();
				originalTextureColors = new int[size];
				modifiedTextureColors = new int[size];
				for (int index = 0; index < size; index++) {
					originalTextureColors[index] = stream.readShort();
					modifiedTextureColors[index] = stream.readShort();
				}
			} else if (opcode == 65) {
				searchableItem = true;
			} else if (opcode == 78)
				anInt185 = stream.readShort();
			else if (opcode == 79)
				anInt162 = stream.readShort();
			else if (opcode == 90)
				anInt175 = stream.readShort();
			else if (opcode == 91)
				anInt197 = stream.readShort();
			else if (opcode == 92)
				anInt166 = stream.readShort();
			else if (opcode == 93)
				anInt173 = stream.readShort();
			else if (opcode == 95)
				modelRotationY = stream.readShort();
			else if (opcode == 97)
				certID = stream.readShort();
			else if (opcode == 98)
				certTemplateID = stream.readShort();
			else if (opcode >= 100 && opcode < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[opcode - 100] = stream.readShort();
				stackAmounts[opcode - 100] = stream.readShort();
			} else if (opcode == 110)
				anInt167 = stream.readShort();
			else if (opcode == 111)
				anInt192 = stream.readShort();
			else if (opcode == 112)
				anInt191 = stream.readShort();
			else if (opcode == 113)
				anInt196 = stream.readSignedByte();
			else if (opcode == 114)
				anInt184 = stream.readSignedByte() * 5;
			else if (opcode == 115)
				team = stream.readUnsignedByte();
			else if (opcode == 139)
				opcode139 = stream.readShort();
			else if (opcode == 140)
				opcode140 = stream.readShort();
			else if (opcode == 148)
				opcode148 = stream.readShort();
			else if (opcode == 149)
				opcode149 = stream.readShort();
			else {
				// System.out.println("Error loading item " + id + ", opcode " + opcode);
			}
		}
	}

	public int opcode139, opcode140, opcode148, opcode149;

	public static void nullLoader() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		return flag;
	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1)
			return null;
		Model model = Model.method462(k);
		if (l != -1) {
			Model model_1 = Model.method462(l);
			Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
			model = new Model(2, aclass30_sub2_sub4_sub6s);
		}
		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);

		}
		return model;
	}

	public boolean method195(int j) {
		int k = maleModel;
		int l = anInt188;
		int i1 = anInt185;
		if (j == 1) {
			k = femaleModel;
			l = anInt164;
			i1 = anInt162;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		if (i1 != -1 && !Model.method463(i1))
			flag = false;
		return flag;
	}

	public Model method196(int i) {
		int j = maleModel;
		int k = anInt188;
		int l = anInt185;
		if (i == 1) {
			j = femaleModel;
			k = anInt164;
			l = anInt162;
		}
		if (j == -1)
			return null;
		Model model = Model.method462(j);
		if (k != -1)
			if (l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_1, model_3 };
				model = new Model(3, aclass30_sub2_sub4_sub6_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model aclass30_sub2_sub4_sub6s[] = { model, model_2 };
				model = new Model(2, aclass30_sub2_sub4_sub6s);
			}
		if (i == 0 && maleOffset != 0)
			model.method475(0, maleOffset, 0);
		if (i == 1 && femaleOffset != 0)
			model.method475(0, femaleOffset, 0);
		applyTexturing(model, id);
		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);

		}
		if (modifiedTextureColors != null && modifiedTextureColors.length > 0) {
			boolean texturesSame = Arrays.stream(modifiedTextureColors).sum()
					/ modifiedTextureColors[0] == modifiedTextureColors.length;

			if (texturesSame)
				model.setTexture(originalTextureColors, modifiedTextureColors[0]);
			else {
				for (int l2 = 0; l2 < modifiedTextureColors.length; l2++) {
					if (originalTextureColors[l2] >= Rasterizer3D.textureAmount) {
						model.setTexture(originalTextureColors[l2], modifiedTextureColors[l2]);
					} else {
						model.overrideTexture(originalTextureColors[l2], modifiedTextureColors[l2]);
					}
				}
			}
		}
		return model;
	}

	private void setDefaults() {
		// equipActions = new String[6];
		equipActions = new String[] { "Remove", null, "Operate", null, null };
		modelId = 0;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		modifiedTextureColors = null;
		originalTextureColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		modelRotationY = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundOptions = new String[5];
		inventoryOptions = new String[5];
		maleModel = -1;
		anInt188 = -1;
		maleOffset = 0;
		femaleModel = -1;
		anInt164 = -1;
		femaleOffset = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		team = 0;

		opcode140 = -1;
		opcode139 = -1;
		opcode148 = -1;
		opcode149 = -1;

		searchableItem = false;
	}

	public static void dumpBonuses() {
		int[] bonuses = new int[14];
		int bonus = 0;
		int amount = 0;
		for (int i = 21304; i < totalItems; i++) {
			ItemDefinition item = ItemDefinition.forID(i);
			URL url;
			try {
				try {
					try {
						url = new URL("http://2007.runescape.wikia.com/wiki/" + item.name.replaceAll(" ", "_"));
						URLConnection con = url.openConnection();
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String line;
						BufferedWriter writer = new BufferedWriter(new FileWriter("item.cfg", true));
						while ((line = in.readLine()) != null) {
							try {
								if (line.contains("<td style=\"text-align: center; width: 35px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "")
											.replace("\"\"", "")
											.replace("<td style=\"text-align: center; width: 35px;\">", "");
									bonuses[bonus] = Integer.parseInt(line);
									bonus++;
								} else if (line.contains("<td style=\"text-align: center; width: 30px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "").replace("%", "")
											.replace("<td style=\"text-align: center; width: 30px;\">", "");
									bonuses[bonus] = Integer.parseInt(line);
									bonus++;
								}
							} catch (NumberFormatException e) {

							}
							if (bonus >= 13)
								bonus = 0;
							// in.close();
						}
						in.close();
						writer.write("item	=	" + i + "	" + item.name.replace(" ", "_") + "	"
								+ item.description.replace(" ", "_") + "	" + item.value + "	" + item.value + "	"
								+ item.value + "	" + bonuses[0] + "	" + bonuses[1] + "	" + bonuses[2] + "	"
								+ bonuses[3] + "	" + bonuses[4] + "	" + bonuses[5] + "	" + bonuses[6] + "	"
								+ bonuses[7] + "	" + bonuses[8] + "	" + bonuses[9] + "	" + bonuses[10] + "	"
								+ bonuses[13]);
						bonuses[0] = bonuses[1] = bonuses[2] = bonuses[3] = bonuses[4] = bonuses[5] = bonuses[6] = bonuses[7] = bonuses[8] = bonuses[9] = bonuses[10] = bonuses[13] = 0;
						writer.newLine();
						amount++;
						writer.close();
					} catch (NullPointerException e) {

					}
				} catch (FileNotFoundException e) {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done dumping " + amount + " item bonuses!");
	}

	public static void dumpBonus() {
		final int[] wikiBonuses = new int[18];
		int bonus = 0;
		int amount = 0;
		System.out.println("Starting to dump item bonuses...");
		for (int i = 20000; i < totalItems; i++) {
			ItemDefinition item = ItemDefinition.forID(i);
			try {
				try {
					try {
						final URL url = new URL(
								"https://oldschool.runescape.wiki/w/" + item.name.replaceAll(" ", "_"));
						URLConnection con = url.openConnection();
						con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String line;
						writer = new BufferedWriter(new FileWriter("item.cfg", true));
						while ((line = in.readLine()) != null) {
							try {
								if (line.contains("<td style=\"text-align: center; width: 35px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "")
											.replace("\"\"", "")
											.replace("<td style=\"text-align: center; width: 35px;\">", "");
									wikiBonuses[bonus] = Integer.parseInt(line);
									bonus++;
								} else if (line.contains("<td style=\"text-align: center; width: 30px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "").replace("%", "")
											.replace("<td style=\"text-align: center; width: 30px;\">", "");
									wikiBonuses[bonus] = Integer.parseInt(line);
									bonus++;
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							in.close();
							writer.write("item	=	" + i + "	" + item.name.replace(" ", "_") + "	"
									+ "It's_a_" + item.name.replace(" ", "_") + "." + "	" + item.value + "	" + item.value
									+ "	" + item.value + "	" + wikiBonuses[0] + "	" + wikiBonuses[1] + "	"
									+ wikiBonuses[2] + "	" + wikiBonuses[3] + "	" + wikiBonuses[4] + "	"
									+ wikiBonuses[5] + "	" + wikiBonuses[6] + "	" + wikiBonuses[7] + "	"
									+ wikiBonuses[8] + "	" + wikiBonuses[9] + "	" + wikiBonuses[10] + "	"
									+ wikiBonuses[13]);
							amount++;
							wikiBonuses[0] = wikiBonuses[1] = wikiBonuses[2] = wikiBonuses[3] = wikiBonuses[4] = wikiBonuses[5] = wikiBonuses[6] = wikiBonuses[7] = wikiBonuses[8] = wikiBonuses[9] = wikiBonuses[10] = wikiBonuses[11] = wikiBonuses[13] = 0;
							writer.newLine();
							writer.close();
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Done dumping " + amount + " item bonuses!");
		}
	}

	public static void dumpItemDefs() {
		final int[] wikiBonuses = new int[18];
		int bonus = 0;
		int amount = 0;
		int value = 0;
		int slot = -1;
		// Testing Variables just so i know format is correct
		String fullmask = "false";
		// boolean stackable1 = false;
		String stackable = "false";
		// boolean noteable1 = false;
		String noteable = "true";
		// boolean tradeable1 = false;
		String tradeable = "true";
		// boolean wearable1 = false;
		String wearable = "true";
		String showBeard = "true";
		String members = "true";
		boolean twoHanded = false;
		System.out.println("Starting to dump item definitions...");
		for (int i = 23000; i < totalItems; i++) {
			ItemDefinition item = ItemDefinition.forID(i);
			try {
				try {
					try {
						final URL url = new URL(
								"https://oldschool.runescape.wiki/w/" + item.name.replaceAll(" ", "_"));
						URLConnection con = url.openConnection();
						con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String line;
						writer = new BufferedWriter(new FileWriter("itemDefs.json", true));
						while ((line = in.readLine()) != null) {
							try {
								if (line.contains("<td style=\"text-align: center; width: 35px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "")
											.replace("\"\"", "")
											.replace("<td style=\"text-align: center; width: 35px;\">", "");
									wikiBonuses[bonus] = Integer.parseInt(line);
									bonus++;
								} else if (line.contains("<td style=\"text-align: center; width: 30px;\">")) {
									line = line.replace("</td>", "").replace("%", "").replace("?", "").replace("%", "")
											.replace("<td style=\"text-align: center; width: 30px;\">", "");
									wikiBonuses[bonus] = Integer.parseInt(line);
									bonus++;
								}
								if (line.contains("<div id=\"GEPCalcResult\" style=\"display:inline;\">")) {
									line = line.replace("</div>", "").replace("%", "").replace("?", "").replace("%", "")
											.replace("<div id=\"GEPCalcResult\" style=\"display:inline;\">", "");
									value = Integer.parseInt(line);
								}

							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							in.close();
							// fw.write("ItemID: "+itemDefinition.id+" - "+itemDefinition.name);
							// fw.write(System.getProperty("line.separator"));
							// writer.write("[\n");
							writer.write("  {\n\t\"id\": " + item.id + ",\n\t\"name\": \"" + item.name
									+ "\",\n\t\"desc\": \"" + "It's a test." + "\",\n\t\"value\": "
									+ value + ",\n\t\"dropValue\": " + value + ",\n\t\"bonus\": [\n\t  "
									+ wikiBonuses[0] + ",\n\t  " + wikiBonuses[1] + ",\n\t  " + wikiBonuses[2]
									+ ",\n\t  " + wikiBonuses[3] + ",\n\t  " + wikiBonuses[4] + ",\n\t  "
									+ wikiBonuses[5] + ",\n\t  " + wikiBonuses[6] + ",\n\t  " + wikiBonuses[7]
									+ ",\n\t  " + wikiBonuses[8] + ",\n\t  " + wikiBonuses[9] + ",\n\t  "
									+ wikiBonuses[10] + ",\n\t  " + wikiBonuses[13] + "\n\t],\n\t\"slot\": " + slot
									+ ",\n\t\"fullmask\": " + fullmask + ",\n\t\"stackable\": " + stackable
									+ ",\n\t\"noteable\": " + noteable + ",\n\t\"tradeable\": " + tradeable
									+ ",\n\t\"wearable\": " + wearable + ",\n\t\"showBeard\": " + showBeard
									+ ",\n\t\"members\": " + members + ",\n\t\"twoHanded\": " + twoHanded
									+ ",\n\t\"requirements\": [\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0,\n\t  0\n\t]\n  },\n");
							/*
							 * writer.write("item = " + i + "	" + item.name.replace(" ", "_") + "	" +
							 * item.description.replace(" ", "_") + "	" + item.value + "	" + item.value +
							 * "	" + item.value + "	" + wikiBonuses[0] + "	" + wikiBonuses[1] + "	" +
							 * wikiBonuses[2] + "	" + wikiBonuses[3] + "	" + wikiBonuses[4] + "	" +
							 * wikiBonuses[5] + "	" + wikiBonuses[6] + "	" + wikiBonuses[7] + "	" +
							 * wikiBonuses[8] + "	" + wikiBonuses[9] + "	" + wikiBonuses[10] + "	" +
							 * wikiBonuses[13]);
							 */
							amount++;
							wikiBonuses[0] = wikiBonuses[1] = wikiBonuses[2] = wikiBonuses[3] = wikiBonuses[4] = wikiBonuses[5] = wikiBonuses[6] = wikiBonuses[7] = wikiBonuses[8] = wikiBonuses[9] = wikiBonuses[10] = wikiBonuses[11] = wikiBonuses[13] = 0;
							writer.newLine();
							writer.close();
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Done dumping " + amount + " item definitions!");
		}
	}

	public static void itemDump() {
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/Desktop/ItemList179.txt");
			for (int i = 0; i < totalItems; i++) {
				ItemDefinition item = ItemDefinition.forID(i);
				fw.write(i + ": " + item.name + "\n");
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void dumpColors() {
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/Desktop/item_data.json");
			for (int i = 19000; i < totalItems; i++) {
				ItemDefinition itemDefinition = ItemDefinition.forID(i);
				fw.write("id: " + itemDefinition.id + " - " + itemDefinition.name + "\n");
				fw.write("model id: " + itemDefinition.modelId + "\n");
				fw.write("male model id: " + itemDefinition.anInt188 + "\n");
				fw.write("female model id: " + itemDefinition.anInt164 + "\n");
				fw.write("modified color: " + Arrays.toString(itemDefinition.modifiedModelColors) + "\n");
				fw.write("original color: " + Arrays.toString(itemDefinition.originalModelColors) + "\n\n");
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Done dumping item colors!");
	}

	public static void dumpStackableList() {
		try {
			File file = new File("stackables.dat");

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < totalItems; i++) {
					ItemDefinition definition = forID(i);
					if (definition != null) {
						writer.write(definition.id + "\t" + definition.stackable);
						writer.newLine();
					} else {
						writer.write(i + "\tfalse");
						writer.newLine();
					}
				}
			}

			System.out.println("Finished dumping noted items definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int[] unNoteable = {};

	public static void dumpNotes() {
		try {
			FileOutputStream out = new FileOutputStream(new File("notes.dat"));
			for (int j = 0; j < totalItems; j++) {
				ItemDefinition item = ItemDefinition.forID(j);
				for (int i = 0; i < totalItems; i++)
					if (j == unNoteable[i] + 1)
						out.write(0);
					else
						out.write(item.certTemplateID != -1 ? 0 : 1);
			}
			out.write(-1);
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void dumpStackable() {
		try {
			FileOutputStream out = new FileOutputStream(new File("stackable.dat"));
			for (int j = 0; j < totalItems; j++) {
				ItemDefinition item = ItemDefinition.forID(j);
				out.write(item.stackable ? 1 : 0);
			}
			out.write(-1);
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void dumpNotableList() {
		try {
			File file = new File("note_id.dat");

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < totalItems; i++) {
					ItemDefinition definition = ItemDefinition.forID(i);
					if (definition != null) {
						if (definition.certTemplateID == -1 && definition.certID != -1) {
							writer.write(definition.id + "\t" + definition.certID);
							writer.newLine();
						}
					} else {
						writer.write(i + "\t-1");
						writer.newLine();
					}
				}
			}

			System.out.println("Finished dumping noted items definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void toNote() {
		ItemDefinition itemDef = forID(certTemplateID);
		modelId = itemDef.modelId;
		modelZoom = itemDef.modelZoom;
		modelRotation1 = itemDef.modelRotation1;
		modelRotation2 = itemDef.modelRotation2;

		modelRotationY = itemDef.modelRotationY;
		modelOffset1 = itemDef.modelOffset1;
		modelOffset2 = itemDef.modelOffset2;
		modifiedModelColors = itemDef.modifiedModelColors;
		originalModelColors = itemDef.originalModelColors;
		ItemDefinition itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name == null ? 'z' : itemDef_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			s = "an";
		  if (certTemplateID == 799)
	            description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".");
	        else 
	            name = itemDef_1.name == null ? "z" : itemDef_1.name + " (Skin)";
		stackable = true;
	}

	public static Sprite getSmallSprite(int itemId) {
		ItemDefinition itemDef = forID(itemId);
		Model model = itemDef.method201(1);
		if (model == null) {
			return null;
		}
		Sprite sprite1 = null;
		if (itemDef.certTemplateID != -1) {
			sprite1 = getSprite(itemDef.certID, 10, -1);
			if (sprite1 == null) {
				return null;
			}
		}
		Sprite enabledSprite = new Sprite(18, 18);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int ai[] = Rasterizer3D.anIntArray1472;
		int ai1[] = Raster.pixels;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.topX;
		int l2 = Raster.bottomX;
		int i3 = Raster.topY;
		int j3 = Raster.bottomY;
		Rasterizer3D.aBoolean1464 = false;
		Raster.initDrawingArea(18, 18, enabledSprite.myPixels);
		Raster.method336(18, 0, 0, 0, 18);
		Rasterizer3D.method364();
		int k3 = (int) (itemDef.modelZoom * 1.6D);
		int l3 = Rasterizer3D.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer3D.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.modelRotationY, itemDef.modelRotation1, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite1.maxWidth;
			int j6 = sprite1.maxHeight;
			sprite1.maxWidth = 18;
			sprite1.maxHeight = 18;
			sprite1.drawSprite(0, 0);
			sprite1.maxWidth = l5;
			sprite1.maxHeight = j6;
		}
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.anIntArray1472 = ai;
		Rasterizer3D.aBoolean1464 = true;

		enabledSprite.maxWidth = 18;
		enabledSprite.maxHeight = 18;

		return enabledSprite;
	}

	public static Sprite getSprite(int itemId, int itemAmount, int highlightColor, int width, int height) {
		if (highlightColor == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(itemId);
			if (sprite != null && sprite.maxHeight != itemAmount && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}
			/*
			 * if (sprite != null) return sprite;
			 */
		}
		ItemDefinition itemDef = forID(itemId);
		if (itemDef.stackIDs == null)
			itemAmount = -1;
		if (itemAmount > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (itemAmount >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];

			if (i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1, width, height);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode140 != -1) {
			sprite = getSprite(itemDef.opcode139, itemAmount, -1, width, height);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode149 != -1) {
			sprite = getSprite(itemDef.opcode148, itemAmount, -1, width, height);
			if (sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(width, height);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int ai[] = Rasterizer3D.anIntArray1472;
		int ai1[] = Raster.pixels;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.topX;
		int l2 = Raster.bottomX;
		int i3 = Raster.topY;
		int j3 = Raster.bottomY;
		Rasterizer3D.aBoolean1464 = false;
		Raster.initDrawingArea(width, height, sprite2.myPixels);
		Raster.method336(width, 0, 0, 0, height);
		Rasterizer3D.method364();
		if (itemDef.opcode149 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		int k3 = itemDef.modelZoom / (width / 32);
		if (highlightColor == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (highlightColor > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer3D.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer3D.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.modelRotationY, itemDef.modelRotation1, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		if (itemDef.opcode140 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = width;
			sprite.maxHeight = height;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		for (int i5 = width - 1; i5 >= 0; i5--) {
			for (int j4 = height - 1; j4 >= 0; j4--)
				if (sprite2.myPixels[i5 + j4 * width] == 0)
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * width] > 1)
						sprite2.myPixels[i5 + j4 * width] = 1;
					else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * width] > 1)
						sprite2.myPixels[i5 + j4 * width] = 1;
					else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * width] > 1)
						sprite2.myPixels[i5 + j4 * width] = 1;
					else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * width] > 1)
						sprite2.myPixels[i5 + j4 * width] = 1;

		}

		if (highlightColor > 0) {
			for (int j5 = width - 1; j5 >= 0; j5--) {
				for (int k4 = height - 1; k4 >= 0; k4--)
					if (sprite2.myPixels[j5 + k4 * width] == 0)
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * width] == 1)
							sprite2.myPixels[j5 + k4 * width] = highlightColor;
						else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * width] == 1)
							sprite2.myPixels[j5 + k4 * width] = highlightColor;
						else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * width] == 1)
							sprite2.myPixels[j5 + k4 * width] = highlightColor;
						else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * width] == 1)
							sprite2.myPixels[j5 + k4 * width] = highlightColor;

			}

		} else if (highlightColor == 0) {
			for (int k5 = width - 1; k5 >= 0; k5--) {
				for (int l4 = height - 1; l4 >= 0; l4--)
					if (sprite2.myPixels[k5 + l4 * width] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * width] > 0)
						sprite2.myPixels[k5 + l4 * width] = 0x302020;

			}

		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = width;
			sprite.maxHeight = height;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (highlightColor == 0)
			mruNodes1.removeFromCache(sprite2, itemId);
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.anIntArray1472 = ai;
		Rasterizer3D.aBoolean1464 = true;
		if (itemDef.stackable)
			sprite2.maxWidth = width + 1;
		else
			sprite2.maxWidth = width;
		sprite2.maxHeight = itemAmount;
		return sprite2;
	}
	
	public static Sprite getSpriteOnInventory(ItemDefinition itemDef, int itemAmount, int highlightColor) {
		
		if (itemDef.stackIDs == null)
			itemAmount = -1;
		if (itemAmount > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (itemAmount >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];

			if (i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode140 != -1) {
			sprite = getSprite(itemDef.opcode139, itemAmount, -1);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode149 != -1) {
			sprite = getSprite(itemDef.opcode148, itemAmount, -1);
			if (sprite == null)
				return null;
		}
		Sprite sprite3 = new Sprite(249, 335);
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int ai[] = Rasterizer3D.anIntArray1472;
		int ai1[] = Raster.pixels;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.topX;
		int l2 = Raster.bottomX;
		int i3 = Raster.topY;
		int j3 = Raster.bottomY;
		Rasterizer3D.aBoolean1464 = false;
		Raster.initDrawingArea(32, 32, sprite2.myPixels);
		Raster.method336(32, 0, 0, 0, 32);
		Rasterizer3D.method364();
		if (itemDef.opcode149 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		int k3 = itemDef.modelZoom;
		if (highlightColor == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (highlightColor > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer3D.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer3D.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.modelRotationY, itemDef.modelRotation1, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		if (itemDef.opcode140 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--)
				if (sprite2.myPixels[i5 + j4 * 32] == 0)
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;

		}

		if (highlightColor > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (sprite2.myPixels[j5 + k4 * 32] == 0)
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;

			}

		} else if (highlightColor == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--)
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;

			}

		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		Raster.initDrawingArea(335, 249, sprite3.myPixels);
		Raster.method336(249, 0, 0, 0, 335);
		Rasterizer3D.method364();
		
		Client.instance.tabAreaFixed.drawSprite(0, 0);
		sprite2.drawSprite(45, 45);
		
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.anIntArray1472 = ai;
		Rasterizer3D.aBoolean1464 = true;
		if (itemDef.stackable)
			sprite2.maxWidth = 33;
		else
			sprite2.maxWidth = 32;
		sprite2.maxHeight = itemAmount;
		return sprite3;
	}

	public static Sprite getSprite(int itemId, int itemAmount, int highlightColor) {
		if (highlightColor == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(itemId);
			if (sprite != null && sprite.maxHeight != itemAmount && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}
			/*
			 * if (sprite != null) return sprite;
			 */
		}
		ItemDefinition itemDef = forID(itemId);
		if (itemDef.stackIDs == null)
			itemAmount = -1;
		if (itemAmount > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (itemAmount >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];

			if (i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode140 != -1) {
			sprite = getSprite(itemDef.opcode139, itemAmount, -1);
			if (sprite == null)
				return null;
		} else if (itemDef.opcode149 != -1) {
			sprite = getSprite(itemDef.opcode148, itemAmount, -1);
			if (sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int ai[] = Rasterizer3D.anIntArray1472;
		int ai1[] = Raster.pixels;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.topX;
		int l2 = Raster.bottomX;
		int i3 = Raster.topY;
		int j3 = Raster.bottomY;
		Rasterizer3D.aBoolean1464 = false;
		Raster.initDrawingArea(32, 32, sprite2.myPixels);
		Raster.method336(32, 0, 0, 0, 32);
		Rasterizer3D.method364();
		if (itemDef.opcode149 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		int k3 = itemDef.modelZoom;
		if (highlightColor == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (highlightColor > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Rasterizer3D.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer3D.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.modelRotationY, itemDef.modelRotation1, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		if (itemDef.opcode140 != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--)
				if (sprite2.myPixels[i5 + j4 * 32] == 0)
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;

		}

		if (highlightColor > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (sprite2.myPixels[j5 + k4 * 32] == 0)
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;
						else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = highlightColor;

			}

		} else if (highlightColor == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--)
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;

			}

		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (highlightColor == 0)
			mruNodes1.removeFromCache(sprite2, itemId);
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.anIntArray1472 = ai;
		Rasterizer3D.aBoolean1464 = true;
		if (itemDef.stackable)
			sprite2.maxWidth = 33;
		else
			sprite2.maxWidth = 32;
		sprite2.maxHeight = itemAmount;
		return sprite2;
	}

	public Model method201(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];

			if (j != -1)
				return forID(j).method201(1);
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if (model != null)
			return model;
		model = Model.method462(modelId);
		if (model == null)
			return null;
		applyTexturing(model, id);
		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128)
			model.method478(anInt167, anInt191, anInt192);
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}

		if (modifiedTextureColors != null && modifiedTextureColors.length > 0) {
			boolean texturesSame = Arrays.stream(modifiedTextureColors).sum()
					/ modifiedTextureColors[0] == modifiedTextureColors.length;

			if (texturesSame)
				model.setTexture(originalTextureColors, modifiedTextureColors[0]);
			else {
				for (int l = 0; l < modifiedTextureColors.length; l++) {
					if (originalTextureColors[l] >= Rasterizer3D.textureAmount) {
						model.setTexture(originalTextureColors[l], modifiedTextureColors[l]);
					} else {
						model.overrideTexture(originalTextureColors[l], modifiedTextureColors[l]);
					}
				}
			}
		}
		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];

			if (j != -1)
				return forID(j).method202(1);
		}
		Model model = Model.method462(modelId);
		if (model == null)
			return null;
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		return model;
	}

	public ItemDefinition() {
		id = -1;
	}

	private byte femaleOffset;
	public int value;
	public int[] originalModelColors;
	public int[] modifiedModelColors;

	private int[] originalTextureColors;
	private int[] modifiedTextureColors;

	public int id;
	public static Cache mruNodes1 = new Cache(100);
	public static Cache mruNodes2 = new Cache(50);

	public boolean membersObject;
	private int anInt162;
	public int certTemplateID;
	private int anInt164;
	public int maleModel;
	private int anInt166;
	private int anInt167;
	public String groundOptions[];
	public int modelOffset1;
	public String name;
	private static ItemDefinition[] cache;
	private int anInt173;
	public int modelId;
	private int anInt175;
	public boolean stackable;
	public String description;
	public int certID;
	private static int cacheIndex;
	public int modelZoom;
	private static Buffer stream;
	private int anInt184;
	private int anInt185;
	private int anInt188;
	public String inventoryOptions[];
	public String equipActions[];
	public int modelRotation1;
	private int anInt191;
	private int anInt192;
	private int[] stackIDs;
	public int modelOffset2;
	private static int[] streamIndices;
	private int anInt196;
	private int anInt197;
	public int modelRotation2;
	public int femaleModel;
	private int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int modelRotationY;
	private byte maleOffset;
	public boolean searchableItem;
	private static BufferedWriter writer;

	public static Sprite getSmallSprite(int itemId, int sizeX, int sizeY) {
		ItemDefinition itemDef = forID(itemId);
		Model model = itemDef.method201(1);
		if (model == null) {
			return null;
		}
		Sprite sprite1 = null;
		if (itemDef.certTemplateID != -1) {
			sprite1 = getSprite(itemDef.certID, 10, -1);
			if (sprite1 == null) {
				return null;
			}
		}
		Sprite enabledSprite = new Sprite(sizeX, sizeY);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int ai[] = Rasterizer3D.anIntArray1472;
		int ai1[] = Raster.pixels;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.topX;
		int l2 = Raster.bottomX;
		int i3 = Raster.topY;
		int j3 = Raster.bottomY;
		Rasterizer3D.aBoolean1464 = false;
		Raster.initDrawingArea(sizeX, sizeY, enabledSprite.myPixels);
		Raster.method336(sizeX, 0, 0, 0, sizeY);
		Rasterizer3D.method364();
		int k3 = (int) ((double) itemDef.modelZoom * (32.0D / (double) sizeX));
		int l3 = Rasterizer3D.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Rasterizer3D.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.modelRotationY, itemDef.modelRotation1, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite1.maxWidth;
			int j6 = sprite1.maxHeight;
			sprite1.maxWidth = sizeX;
			sprite1.maxHeight = sizeY;
			sprite1.drawSprite(0, 0);
			sprite1.maxWidth = l5;
			sprite1.maxHeight = j6;
		}
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setDrawingArea(j3, k2, l2, i3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.anIntArray1472 = ai;
		Rasterizer3D.aBoolean1464 = true;
		enabledSprite.maxWidth = sizeX;
		enabledSprite.maxHeight = sizeY;
		return enabledSprite;
	}

	public void copyOf(ItemDefinition other) {
		this.opcode139 = other.opcode139;
		this.opcode140 = other.opcode140;
		this.opcode148 = other.opcode148;
		this.opcode149 = other.opcode149;
		this.femaleOffset = other.femaleOffset;
		this.value = other.value;
		this.originalModelColors = other.originalModelColors;
		this.modifiedModelColors = other.modifiedModelColors;
		this.originalTextureColors = other.originalTextureColors;
		this.modifiedTextureColors = other.modifiedTextureColors;
		this.id = other.id;
		this.membersObject = other.membersObject;
		this.anInt162 = other.anInt162;
		this.certTemplateID = other.certTemplateID;
		this.anInt164 = other.anInt164;
		this.maleModel = other.maleModel;
		this.anInt166 = other.anInt166;
		this.anInt167 = other.anInt167;
		this.groundOptions = other.groundOptions;
		this.modelOffset1 = other.modelOffset1;
		this.name = other.name;
		this.anInt173 = other.anInt173;
		this.modelId = other.modelId;
		this.anInt175 = other.anInt175;
		this.stackable = other.stackable;
		this.description = other.description;
		this.certID = other.certID;
		this.modelZoom = other.modelZoom;
		this.anInt184 = other.anInt184;
		this.anInt185 = other.anInt185;
		this.anInt188 = other.anInt188;
		this.inventoryOptions = other.inventoryOptions;
		this.equipActions = other.equipActions;
		this.modelRotation1 = other.modelRotation1;
		this.anInt191 = other.anInt191;
		this.anInt192 = other.anInt192;
		this.stackIDs = other.stackIDs;
		this.modelOffset2 = other.modelOffset2;
		this.anInt196 = other.anInt196;
		this.anInt197 = other.anInt197;
		this.modelRotation2 = other.modelRotation2;
		this.femaleModel = other.femaleModel;
		this.stackAmounts = other.stackAmounts;
		this.team = other.team;
		this.modelRotationY = other.modelRotationY;
		this.maleOffset = other.maleOffset;
		this.searchableItem = other.searchableItem;
	}
	
	

}
