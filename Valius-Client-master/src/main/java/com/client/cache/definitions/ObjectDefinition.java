package com.client.cache.definitions;

import java.io.FileWriter;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.client.Client;
import com.client.cache.Archive;
import com.client.cache.animation.Frame;
import com.client.cache.config.VarBit;
import com.client.cache.graphics.Sprite;
import com.client.cache.graphics.font.RSFont;
import com.client.cache.provider.ResourceProvider;
import com.client.collection.Cache;
import com.client.io.Buffer;
import com.client.misc.LightPosition;
import com.client.model.Model;
import com.client.utilities.ReflectionUtil;

public final class ObjectDefinition {

	public void applyTexturing(Model m, int id) {
		if (id == 26764)
			m.setTexture(26);
		switch(id) {
		case 20951:
		case 20950:
			m.setTexture(43);
			break;
		}
	}

	public static ObjectDefinition forID(int i) {
		if (i > streamIndices.length)
			i = streamIndices.length - 2;

		if (i == 25913 || i == 25916 || i == 25917)
			i = 15552;

		for (int j = 0; j < 20; j++)
			if (cache[j].id == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition objectDef = cache[cacheIndex];
		stream.position = streamIndices[i];
		objectDef.setDefaults();
		objectDef.readValues(stream);
		objectDef.id = i;
		if(i >= 30000) {
			objectDef.delaysShading = false;
		}
		if (i >= 26281 && i <= 26290) {
			objectDef.actions = new String[] { "Choose", null, null, null, null };
		}
		switch (i) {
		case 249:
			objectDef.lightPos = new LightPosition(0, 0, -15);
			objectDef.ambientLighting = 0;
			objectDef.lightDiffusion = 50;
			objectDef.delaysShading = true;
			objectDef.castsShadow = false;
			break;
		case 18532:		
			objectDef.setDefaults();
			objectDef.id = 18532;
			objectDef.modelTypes = null;
			objectDef.modelIds = new int[] {5314};
			objectDef.hasActions = true;
			objectDef.solid = true;
			objectDef.mapscene = -1;
			objectDef.delaysShading = true;
			objectDef.modifiedModelColors = new int[] {5921, 5673};
			objectDef.originalModelColors = new int[] {5551, 5547};
			objectDef.actions = new String[] { "Cast-from", null, null, null, null };
			objectDef.name = "Railing";
			break;
		case 4976:
		case 253:
		case 9453:
			objectDef.delaysShading = true;
			break;
		case 11869:
			objectDef.actions = new String[] { "Pick-up", null, null, null, null };
			objectDef.name = "Dwarf Multicannon (collapsed)";
			objectDef.obstructsGround = false;
			objectDef.obstructsGround = false;
			objectDef.impenetrable = false;
			objectDef.hollow = true;
			break;
		case 28449:
			objectDef.actions = new String[] { "Awaken Knight", null, null, null, null };
			objectDef.name = "Champions grave";
			objectDef.description = "The resting place of the Champion of the Void Knights.";
			break;
		case 21309:
		case 21306:
			objectDef.actions = new String[] { null, null, null, null, null };
			break;

		case 8207:
			objectDef.actions = new String[] { "Care-To", null, null, null, null };
			objectDef.name = "Herb Patch";
			break;
		case 4004:
			objectDef.actions = new String[] { "Donate-To", null, null, null, null };
			objectDef.name = "Well of Goodwill";
			break;
		case 22472:
			objectDef.actions = new String[] { "Daily Rewards", "Drop Tables", "Achievements", null, null };
			objectDef.name = "Valius Database";
			break;
			case 26811:
				objectDef.actions = new String[] { "Open", null, null, null, null };
				objectDef.name = "Vote Shop";
				objectDef.overheadTextHeight = 200;
				objectDef.overheadText = "Vote Point Shop (Vote)";
				objectDef.overheadColour = 0xff0000;
				objectDef.overheadShadow = 0xffff00;
				objectDef.overheadTextOpacity = 0.8f;
				objectDef.font = Client.instance.newSmallFont;
				break;
		case 8210:
			objectDef.actions = new String[] { "Rake", null, null, null, null };
			objectDef.name = "Herb Patch";
			break;
		case 29150:
			objectDef.actions = new String[] { "Venerate", null, null, null, null };
			break;
		case 29709://Santa shop
			objectDef.actions = new String[] { "Exchange", null, null, null, null };
			objectDef.length = 2;
			objectDef.width = 2;
			break;
		case 27288://Hween shop chest
			objectDef.modelIds = new int[] { 61429 };
			objectDef.actions = new String[] { "Open", "Teleport to Halloween Boss", null, null, null };
			objectDef.name = "Halloween Chest";
			break;
		case 27289://training area chest
			objectDef.modelIds = new int[] { 61508 };
			objectDef.actions = new String[] { "Unlock", null, null, null, null };
			objectDef.name = "Occult Chest";
			break;
		case 31621:
		case 33393:
			objectDef.actions = new String[] { "Teleport", "Previous", null, null, null };
			objectDef.name = "Valius Teleporter";
			objectDef.overheadTextHeight = 450;
			objectDef.overheadText = "Valius Teleporter";
			objectDef.overheadTextOpacity = 0.8f;
			objectDef.font = Client.instance.newSmallFont;
			objectDef.overheadColour = 0xff0000;
			objectDef.overheadShadow = 0xffff00;
			
			objectDef.modelIds[0] = 36053;
			objectDef.hasActions = true;
			objectDef.castsShadow = false;
			break;
			
		case 33395:
			objectDef.name = "Thermo Portal";
			objectDef.modelIds = new int[] { 60721, 60722 };
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			objectDef.hasActions = true;
			objectDef.castsShadow = false;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
			
		case 21000:
			objectDef.name = "Bandos Crate";
			objectDef.modelIds[0] = 60723;
			objectDef.actions = new String[] { "test", null, null, null, null };
			objectDef.hasActions = true;
			objectDef.castsShadow = false;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
			
		case 7453:
		case 7461:
		case 7454:
		case 7455:
		case 7456:
		case 7457:
		case 7458:
		case 7459:
		case 7460:
			objectDef.overheadSprite = new Sprite("/interfaces/prestige/playerstats/skills/img 14");
			objectDef.overheadSprite.setTransparency(255, 255, 255);
			objectDef.overheadSpriteHeight = 200;
			objectDef.overheadSpriteOpacity = 0.8f;
			break;
			
			//objectDef.overheadTextHeight = 300;
			//objectDef.overheadText = "Steal me daddy";
			//objectDef.overheadColour = 0xff0000;
			//objectDef.overheadShadow = 0xffff00;
			//objectDef.overheadTextOpacity = 0.8f;
			//objectDef.font = Client.instance.newSmallFont;
		case 31618:
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			objectDef.name = "Minigames Lobby";
			break;
		case 8139:
		case 8140:
		case 8141:
		case 8142:
			objectDef.actions = new String[] { "Inspect", null, null, null, null };
			objectDef.name = "Herbs";
			break;
	
		case 3840:
			objectDef.actions = new String[5];
			objectDef.actions[0] = "Fill";
			objectDef.actions[1] = "Empty-From";
			objectDef.name = "Compost Bin";
			break;
			case 172:
				objectDef.name = "Ckey chest";
			break;
			case 12309:
				objectDef.actions = new String[5];
				objectDef.actions[0] = "Bank";
				objectDef.actions[1] = "Buy gloves";
				objectDef.actions[2] = null;
				objectDef.name = "Chest (Barrows Gloves minigame)";
				break;
		case 29333:
			objectDef.name = "Trading post";
			objectDef.actions[0] = "View";
			objectDef.modelIds[0] = 60884;
			objectDef.animation = 4006;
			objectDef.delaysShading = false;
			objectDef.description = "Buy and sell items with players here!";
			break;
			
		case 29334:
			objectDef.name = "Completionist cape rack";
			objectDef.actions = new String[] { "Requirements", "Claim Completionist cape", null, null, null };
			objectDef.modelIds[0] = 60953;
			objectDef.delaysShading = false;
			objectDef.description = "Claim your completionist cape or check requirements here!";
			break;
		case 29335:
            objectDef.name = "Nightmare Chest";
            objectDef.actions = new String[] {"Open", null, null, null, null};
            objectDef.modelIds = new int[] { 60960 };
            objectDef.animation = 7823;
            objectDef.width = 3;
            objectDef.length = 3;
            break;
            
		case 29336:
			objectDef.name = "Limited Packs";
			objectDef.actions = new String[] { "Check", null, null, null, null };
			objectDef.modelIds[0] = 60984;
			objectDef.animation = 4006;
			objectDef.delaysShading = false;
			objectDef.description = "Takes you to the Limited item Packs!";
			break;
			
		case 29337:
            objectDef.name = "Wilderness Chest";
            objectDef.actions = new String[] {"Unlock", null, null, null, null};
            objectDef.modelIds[0] = 50152;
            objectDef.animation = 6646;
            objectDef.width = 2;
            objectDef.length = 2;
            break;
            
		case 29338:
            objectDef.name = "Dark Altar";
            objectDef.actions = new String[] {"Summon", null, null, null, null};
            objectDef.modelIds[0] = 36229;
            objectDef.animation = -1;
            objectDef.width = 2;
            objectDef.length = 2;
            objectDef.description = "Summon the Dark lord here!";
            break;
			
		case 20951:
		case 20950:/*
			objectDef.originalTexture = new short[] {8617, 8497, 8501, 7481, 8493};
			objectDef.modifiedTexture = new short[] {43, 43, 43, 43, 43};*/
			objectDef.lightPos = new LightPosition(0, 0, -15);
			objectDef.ambientLighting = 0;
			objectDef.lightDiffusion = 50;
			break;
		case 1750:
			objectDef.modelIds = new int[] { 8131, };
			objectDef.name = "Willow";
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapscene = 3;
			break;

		case 26782:
			objectDef.actions = new String[] { "Recharge", null, null, null, null };
			break;

		case 29878:
			objectDef.name = "Well of Goodwill";
			objectDef.actions = new String[] { "Contribute to", null, null, null, null };
			break;
			
		case 1751:
			objectDef.modelIds = new int[] { 8037, 8040, };
			objectDef.name = "Oak";
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.ambientLighting = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapscene = 1;
			break;

		case 7814:
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;

		case 8356:
			objectDef.actions = new String[] { "Teleport", "Minigames Lobby", null, null, null };
			break;

		case 28900:
			objectDef.actions = new String[] { "Teleport", "Recharge Crystals", null, null, null };
			break;

		case 28837:
			objectDef.actions = new String[] { "Set Destination", null, null, null, null };
			break;

		case 7811:
			objectDef.name = "District Supplies";
			objectDef.actions = new String[] { "Blood Money", "Free", "Quick-Sets", null, null };
			break;

		case 1752:
			objectDef.modelIds = new int[] { 4146, };
			objectDef.name = "Hollow tree";
			objectDef.ambientLighting = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.originalModelColors = new int[] { 13592, 10512, };
			objectDef.modifiedModelColors = new int[] { 7056, 6674, };
			objectDef.mapscene = 0;
			break;
		case 4873:
			objectDef.name = "Wilderness Lever";
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.ambientLighting = 25;
			objectDef.actions = new String[] { "Enter Deep Wildy", null, null, null, null };
			objectDef.mapscene = 3;
			break;
		case 29735:
			objectDef.name = "Basic Slayer Dungeon";
			break;
		case 2544:
			objectDef.name = "Dagannoth Manhole";
			break;
		case 12355:
			objectDef.name = "RFD Minigame Portal";
			break;
		case 29345:
			objectDef.name = "Training Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29346:
			objectDef.name = "Wilderness Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29347:
			objectDef.name = "Boss Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29349:
			objectDef.name = "Mini-Game Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 4155:
			objectDef.name = "Zul Andra Portal";
			break;
		case 2123:
			objectDef.name = "Mt. Quidamortem Slayer Dungeon";
			break;
		case 4150:
			objectDef.name = "Warriors Guild Mini-game Portal";
			break;
		case 11803:
			objectDef.name = "Donator Slayer Dungeon";
			break;
		case 4151:
			objectDef.name = "Barrows Mini-game Portal";
			break;
		case 1753:
			objectDef.modelIds = new int[] { 8157, };
			objectDef.name = "Yew";
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.ambientLighting = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapscene = 3;
			break;

		case 6943:
			objectDef.modelIds = new int[] { 1270, };
			objectDef.name = "Bank booth";
			objectDef.impenetrable = false;
			objectDef.ambientLighting = 25;
			objectDef.lightDiffusion = 25;
			objectDef.actions = new String[] { null, "Bank", "Collect", null, null };
			break;

		case 25016:
		case 25017:
		case 25018:
		case 25029:
			objectDef.actions = new String[] { "Push-Through", null, null, null, null };
			break;

		case 19038:
			objectDef.actions = new String[] { null, null, null, null, null };
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.scaleZ = 500; // Width
			objectDef.scaleX = 500; // Thickness
			objectDef.scaleY = 400; // Height
			break;

		case 18826:
		case 18819:
		case 18818:
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.scaleZ = 200; // Width
			objectDef.scaleX = 200; // Thickness
			objectDef.scaleY = 100; // Height
			break;

		case 27777:
			objectDef.name = "Gangplank";
			objectDef.actions = new String[] { "Travel to CrabClaw Isle", null, null, null, null };
			objectDef.width = 1;
			objectDef.length = 1;
			objectDef.scaleZ = 80; // Width
			objectDef.scaleX = 80; // Thickness
			objectDef.scaleY = 250; // Height
			break;
		case 13641:
			objectDef.name = "Teleportation Device";
			objectDef.actions = new String[] { "Quick-Teleport", null, null, null, null };
			objectDef.width = 1;
			objectDef.length = 1;
			objectDef.scaleZ = 80; // Width
			objectDef.scaleX = 80; // Thickness
			objectDef.scaleY = 250; // Height
			break;
		case 23306:
			objectDef.name = "plant";
			objectDef.modelIds = new int[] { 51174 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 32687:
			objectDef.name = "Supporting Pillar";
			objectDef.delaysShading = false;
			break;
		case 32688:
			objectDef.delaysShading = false;
			break;
		case 32689:
			objectDef.delaysShading = false;
			break;
		case 23319:
			objectDef.name = "Infernal Chest";
			objectDef.modelIds = new int[] { 59805 };
			objectDef.actions = new String[] { "Open", null, null, null, null };
			objectDef.animation = 3117;
			objectDef.originalTexture = new short[] { 9023 };
			objectDef.modifiedTexture = new short[] { 59 };
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23307:
			objectDef.modelIds = new int[] { 1105 };
			objectDef.modelTypes = new int[] {22};
			objectDef.overheadTextHeight = 250;
			objectDef.overheadText = "Chambers of Xeric Lobby";
			objectDef.overheadTextOpacity = 0.9f;
			objectDef.font = Client.instance.newSmallFont;
			objectDef.overheadColour = 0xffffff;
			objectDef.overheadShadow = 0x000000;
			break; 
		case 23308:
            objectDef.modelTypes = new int[] {22};
            objectDef.modelIds = new int[] {1105};
            objectDef.overheadText = "Trials of Xeric Lobby";
            objectDef.overheadShadow = 0x000000;
            objectDef.overheadTextHeight = 250;
            objectDef.overheadColour = 0xFFFFFF;
            objectDef.overheadTextOpacity = 0.8f;
            objectDef.font = Client.instance.newSmallFont;
			break; 
		case 23310:
			objectDef.modelIds = new int[] { 1105 };
			objectDef.modelTypes = new int[] {22};
			objectDef.overheadTextHeight = 250;
			objectDef.overheadText = "Theatre of Blood Lobby";
			objectDef.overheadTextOpacity = 0.9f;
			objectDef.font = Client.instance.newSmallFont;
			objectDef.overheadColour = 0xffffff;
			objectDef.overheadShadow = 0x000000;
			break;
		case 23311:
			objectDef.modelIds = new int[] { 1105 };
			objectDef.modelTypes = new int[] {22};
			objectDef.overheadTextHeight = 200;
			objectDef.overheadText = "Bank";
			objectDef.overheadTextOpacity = 0.9f;
			objectDef.font = Client.instance.newSmallFont;
			objectDef.overheadColour = 0xffffff;
			objectDef.overheadShadow = 0x000000;
			break;
		case 7435:
		objectDef.modifiedModelColors = new int [] {231, 63684, 59945, 60312, 61332, 60304};
		objectDef.originalModelColors = new int [] {38798, 38798, 0, 38798, 38798, 0};
	    break;
		case 7436:
			objectDef.modifiedModelColors = new int [] {231, 63684, 59945, 60312, 61332, 60304};
			objectDef.originalModelColors = new int [] {38798, 38798, 0, 38798, 38798, 0};
		    break;
	    case 7437:
		objectDef.modifiedModelColors = new int [] {231, 63684, 59945, 60312, 61332, 60304};
		objectDef.originalModelColors = new int [] {38798, 38798, 0, 38798, 38798, 0};
	    break;
	    case 14861:
	    	objectDef.modifiedModelColors = new int [] {5943, 6067};
			objectDef.originalModelColors = new int [] {38798, 38798};	
	    	break;
	    case 6808:
	    	objectDef.modifiedModelColors = new int [] {35471};
			objectDef.originalModelColors = new int [] {40590};	
	    	break;
	    case 840:
	    	objectDef.modifiedModelColors = new int [] {35471};
			objectDef.originalModelColors = new int [] {40590};	
	    	break;
	    case 7138:
	    	objectDef.modifiedModelColors = new int [] {35471};
			objectDef.originalModelColors = new int [] {40590};	
	    	break;
	    case 25680:
	    	objectDef.modifiedModelColors = new int [] {926, 0, 9666};
			objectDef.originalModelColors = new int [] {38798, 0, 0};	
	    	break;
	    case 25679:
	    	objectDef.modifiedModelColors = new int [] {926, 0, 9666};
			objectDef.originalModelColors = new int [] {38798, 0, 0};
	    	break;
	    case 25678:
	    	objectDef.modifiedModelColors = new int [] {926, 0, 9666};
			objectDef.originalModelColors = new int [] {38798, 0, 0};	
	    break;
		/*case 23307:
			objectDef.name = "Presents";
			objectDef.modelIds = new int[] { 51180 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break; 
		case 23308:
			objectDef.name = "Unicorn sculpture";
			objectDef.modelIds = new int[] { 51185 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break; 
		case 23309:
			objectDef.name = "Table";
			objectDef.modelIds = new int[] { 51189 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23310:
			objectDef.name = "Plant";
			objectDef.modelIds = new int[] { 51195 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;*/
		case 33114:
			objectDef.name = "Event Boss Chest";
			objectDef.actions = new String[] {"Open", null, null, null, null};
			break;/*
		case 23311:
			objectDef.name = "Table";
			objectDef.modelIds = new int[] { 51196 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			brea*/
		case 23312:
			objectDef.name = "Presents";
			objectDef.modelIds = new int[] { 51199 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23313:
			objectDef.name = "Christmas tree";
			objectDef.modelIds = new int[] { 51204 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23314:
			objectDef.name = "Minataur sculpture";
			objectDef.modelIds = new int[] { 51205 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23315:
			objectDef.name = "Present";
			objectDef.modelIds = new int[] { 51218 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23316:
			objectDef.name = "Dragon sculpture";
			objectDef.modelIds = new int[] { 51219 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23317:
			objectDef.name = "Cupboard";
			objectDef.modelIds = new int[] { 51223 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 23318:
			objectDef.name = "Snowman";
			objectDef.modelIds = new int[] { 51224 };
			objectDef.animation = -1;
			objectDef.width = 1;
			objectDef.length = 1;
			//objectDef.scaleZ = 60; // Width
			//objectDef.scaleX = 60; // Thickness
			//objectDef.scaleY = 60; // Height
			break;
		case 17196:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60501 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
			
		case 17197:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60502, 60503 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
			
		case 17198:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60499 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
			
		case 17199:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60505 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
		
		case 17200:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60500 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
		
		case 17201:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60498 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
		
		case 17202:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60497 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
			
		case 17203:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60503 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
			
		case 17204:
			objectDef.name = "Fallen star";
			objectDef.modelIds = new int[] { 60504 };
			objectDef.animation = -1;
			objectDef.width = 2;
			objectDef.length = 2;
			objectDef.ambientLighting = 25;
			objectDef.scaleZ = 35; // Width
		    objectDef.scaleX = 35; // Thickness
			objectDef.scaleY = 35; // Height
			objectDef.actions = new String[] {"Mine", "Prospect", null, null, null};
			break;
		
		case 17205:
			objectDef.name = "Slayer Chest";
			objectDef.modelIds = new int[] { 60511 };
			objectDef.actions = new String[] { "Open", null, null, null, null };
			objectDef.animation = -1;
			break;
		
		case 17206:
			objectDef.name = "Slayer Stand";
			objectDef.modelIds = new int[] { 60454 };
			objectDef.actions = new String[] { "Open", null, null, null, null };
			objectDef.animation = -1;
			break;
			
			
		case 11700:
			objectDef.modelIds = new int[] { 4086 };
			objectDef.name = "Venom";
			objectDef.width = 3;
			objectDef.length = 3;
			objectDef.solid = false;
			objectDef.contouredGround = true;
			objectDef.animation = 1261;
			objectDef.modifiedModelColors = new int[] { 31636 };
			objectDef.originalModelColors = new int[] { 10543 };
			objectDef.scaleX = 160;
			objectDef.scaleY = 160;
			objectDef.scaleZ = 160;
			objectDef.actions = new String[5];
			// objectDef.description = new String(
			// "It's a cloud of venomous smoke that is extremely toxic.");
			break;

		case 11601: // 11601
			objectDef.originalTexture = new short[] { 2 };
			objectDef.modifiedTexture = new short[] { 46 };
			break;
			
		case 7677:
			objectDef.modelTypes = new int[] {22};
			objectDef.modelIds = new int[] {1105};
			objectDef.overheadText = "Test Lobby Text";
			objectDef.overheadShadow = 0x000000;
			objectDef.overheadTextHeight = 100;
			objectDef.overheadColour = 0xFFFFFF;
			objectDef.overheadTextOpacity = 0.8f;
			objectDef.font = Client.instance.newSmallFont;
			
			break;
			
		}
		
		if (Client.debugModels) {

			if (objectDef.name == null || objectDef.name.equalsIgnoreCase("null"))
				objectDef.name = "test";

			objectDef.hasActions = true;
		}
		return objectDef;
	}

	public static void dumpList() {
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/Desktop/object_data.json");
			fw.write("[\n");
			for (int i = 0; i < totalObjects; i++) {
				ObjectDefinition def = ObjectDefinition.forID(i);
				String output = "[\"" + StringUtils.join(def.actions, "\", \"") + "\"],";

				String finalOutput = "	{\n" + "		\"id\": " + def.id + ",\n		" + "\"name\": \"" + def.name
						+ "\",\n		\"models\": " + Arrays.toString(def.modelIds) + ",\n		\"actions\": "
						+ output.replaceAll(", \"\"]", ", \"Examine\"]").replaceAll("\"\"", "null")
								.replace("[\"null\"]", "[null, null, null, null, \"Examine\"]")
								.replaceAll(", \"Remove\"", ", \"Remove\", \"Examine\"")
						+ "	\n		\"width\": " + def.scaleZ + "\n	},";
				fw.write(finalOutput.replaceAll("\"name\": \"null\",", "\"name\": null,"));

				// .replaceAll("\"name\": \"null\",", "\"name\": null,")
				fw.write(System.getProperty("line.separator"));
			}
			fw.write("]");
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void applyTexture(Model model, int id) {
		switch (id) {
		case 26764:// Venenatis Webs
			model.setTexture(26);
			break;
		}
	}

	private void setDefaults() {
		modelIds = null;
		modelTypes = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		// originalTexture = null;
		// modifiedTexture = null;
		width = 1;
		length = 1;
		solid = true;
		impenetrable = true;
		hasActions = false;
		contouredGround = false;
		delaysShading = false;
		occludes = false;
		animation = -1;
		decorDisplacement = 16;
		ambientLighting = 0;
		lightDiffusion = 0;
		actions = null;
		mapFunction = -1;
		mapscene = -1;
		inverted = false;
		castsShadow = true;
		scaleX = 128;
		scaleY = 128;
		scaleZ = 128;
		surroundings = 0;
		translateX = 0;
		translateY = 0;
		translateZ = 0;
		obstructsGround = false;
		hollow = false;
		supportItems = -1;
		varbit = -1;
		varp = -1;
		morphisms = null;
		
		this.modifiedTexture = null;
		this.originalTexture = null;
		
		this.overheadColour = 0;
		this.overheadSpriteHeight = 0;
		this.overheadTextHeight = 0;
		this.overheadSpriteHeight = 0;
		this.overheadShadow = 0;
		this.overheadSprite = null;
		this.overheadText = null;
		this.overheadSpriteOpacity = 1.0f;
		this.overheadTextOpacity = 1.0f;
		this.lightPos = null;
	}

	public void method574(ResourceProvider class42_sub1) {
		if (modelIds == null)
			return;
		for (int j = 0; j < modelIds.length; j++)
			class42_sub1.method560(modelIds[j] & 0xffff, 0);
	}

	public static void nullLoader() {
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public static int totalObjects;

	public static void unpackConfig(Archive streamLoader) {
		stream = new Buffer(streamLoader.getDataForName("loc.dat"));
		Buffer stream = new Buffer(streamLoader.getDataForName("loc.idx"));
		totalObjects = stream.readShort();
		streamIndices = new int[totalObjects];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.readShort();
		}
		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new ObjectDefinition();
		//dumpList();
	}

	public boolean method577(int i) {
		if (modelTypes == null) {
			if (modelIds == null)
				return true;
			if (i != 10)
				return true;
			boolean flag1 = true;
			Model model = (Model) mruNodes2.insertFromCache(id);
			for (int k = 0; k < modelIds.length; k++)
				flag1 &= Model.method463(modelIds[k] & 0xffff);
			
			return flag1;
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		for (int j = 0; j < modelTypes.length; j++)
			if (modelTypes[j] == i)
				return Model.method463(modelIds[j] & 0xffff);
	
		return true;
	}

	public Model modelAt(int type, int rotation, int k, int l, int i1, int j1, int animId) {
		Model model = method581(type, animId, rotation);
		if (model == null)
			return null;
		if (contouredGround || delaysShading)
			model = new Model(contouredGround, delaysShading, model);
		if (contouredGround) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.numVertices; i2++) {
				int j2 = model.vertexX[i2];
				int k2 = model.vertexZ[i2];
				int l2 = k + ((l - k) * (j2 + 64)) / 128;
				int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
				int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
				model.vertexY[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (modelIds == null)
			return true;
		boolean flag1 = true;
		for (int i = 0; i < modelIds.length; i++)
			flag1 &= Model.method463(modelIds[i] & 0xffff);
		return flag1;
	}

	public ObjectDefinition getMorphed() {
		int morphismIndex = -1;
		if (varbit != -1) {
			VarBit varBit = VarBit.cache[varbit];
			int setting = varBit.setting;
			int low = varBit.low;
			int high = varBit.high;
			int mask = Client.BIT_MASK[high - low];
			morphismIndex = clientInstance.variousSettings[setting] >> low & mask;
		} else if (varp != -1)
			morphismIndex = clientInstance.variousSettings[varp];
		
		int var2 = -1;
		if (morphismIndex >= 0 || morphismIndex < morphisms.length)
			var2 = morphisms[morphismIndex];
		else
			var2 = morphisms[morphisms.length - 1];
		
		return var2 == -1 ? null : ObjectDefinition.forID(var2);
	}

	private Model method581(int type, int anim, int rotation) {
		Model model = null;
		long l1;
		if (modelTypes == null) {
			if (type != 10)
				return null;
			l1 = (id << 6) + rotation + ((long) (anim + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if (model_1 != null)
				return model_1;
			if (modelIds == null)
				return null;
			boolean flag1 = inverted ^ (rotation > 3);
			int k1 = modelIds.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = modelIds[i2];
				if (flag1)
					l2 += 0x10000;
				model = (Model) mruNodes1.insertFromCache(l2);
				if (model == null) {
					model = Model.method462(l2 & 0xffff);
					if (model == null)
						return null;
					if (flag1)
						model.method477();
					mruNodes1.removeFromCache(model, l2);
				}
				if (k1 > 1)
					aModelArray741s[i2] = model;
			}

			if (k1 > 1)
				model = new Model(k1, aModelArray741s);
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < modelTypes.length; j1++) {
				if (modelTypes[j1] != type)
					continue;
				i1 = j1;
				break;
			}

			if (i1 == -1)
				return null;
			l1 = (id << 8) + (i1 << 3) + rotation + ((long) (anim + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if (model_2 != null)
				return model_2;
			int j2 = modelIds[i1];
			boolean flag3 = inverted ^ (rotation > 3);
			if (flag3)
				j2 += 0x10000;
			model = (Model) mruNodes1.insertFromCache(j2);
			if (model == null) {
				model = Model.method462(j2 & 0xffff);
				if (model == null)
					return null;
				if (flag3)
					model.method477();
				mruNodes1.removeFromCache(model, j2);
			}
		}
		
		boolean flag;
		flag = scaleX != 128 || scaleY != 128 || scaleZ != 128;
		boolean flag2;
		flag2 = translateX != 0 || translateY != 0 || translateZ != 0;
		Model model_3 = new Model(modifiedModelColors == null && modifiedTexture == null, Frame.method532(anim),
				rotation == 0 && anim == -1 && !flag && !flag2, model);
	
		if (anim != -1) {
			model_3.method469();
			model_3.method470(anim);
			model_3.faceGroups = null;
			model_3.vertexGroups = null;
		}
		if(type == 4 && rotation > 3) {
			model_3.method474(256);
			model_3.method475(45, 0, -45);
		}
		rotation &= 3;
		if(rotation == 1) {
			model_3.rotate90Degrees();
		} else if(rotation == 2) {
			model_3.rotate180Degrees();
		} else if(rotation == 3) {
			model_3.rotate270Degrees();
		}
		this.applyTexturing(model_3, this.id);
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++)
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);

		}
		if (modifiedTexture != null) {
			for (int k2 = 0; k2 < modifiedTexture.length; k2++) {
				if(originalTexture[k2] < 100) {
					model_3.overrideTexture(originalTexture[k2], modifiedTexture[k2]);
				} else {
					model_3.setTexture(originalTexture[k2], modifiedTexture[k2]);
				}
			}

		}
		if (flag)
			model_3.method478(scaleX, scaleZ, scaleY);
		if (flag2)
			model_3.method475(translateX, translateY, translateZ);
		// model_3.method479(64 + aByte737, 768 + aByte742 * 5, -50, -10, -50,
		// !aBoolean769);
		// ORIGINAL^

		if(lightPos != null) {
			model_3.method479(64 + ambientLighting, 768 + lightDiffusion * 5, lightPos.x, lightPos.z, lightPos.y, !delaysShading);
		} else {
			model_3.method479(64 + ambientLighting, 768 + lightDiffusion * 5, -50, -10, -50, !delaysShading);
		}


		if (supportItems == 1)
			model_3.itemDropHeight = model_3.modelHeight;
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}

	/*
	 * private void readValues(Stream stream) { int i = -1; label0: do { int j; do {
	 * j = stream.readUnsignedByte(); if (j == 0) break label0; if (j == 1) { int k
	 * = stream.readUnsignedByte(); if (k > 0) if (anIntArray773 == null || lowMem)
	 * { anIntArray776 = new int[k]; anIntArray773 = new int[k]; for (int k1 = 0; k1
	 * < k; k1++) { anIntArray773[k1] = stream.readUnsignedWord(); anIntArray776[k1]
	 * = stream.readUnsignedByte(); }
	 * 
	 * } else { stream.currentOffset += k * 3; } } else if (j == 2) name =
	 * stream.readString(); else if (j == 3) description = stream.readBytes(); else
	 * if (j == 5) { int l = stream.readUnsignedByte(); if (l > 0) if (anIntArray773
	 * == null || lowMem) { anIntArray776 = null; anIntArray773 = new int[l]; for
	 * (int l1 = 0; l1 < l; l1++) anIntArray773[l1] = stream.readUnsignedWord();
	 * 
	 * } else { stream.currentOffset += l * 2; } } else if (j == 14) anInt744 =
	 * stream.readUnsignedByte(); else if (j == 15) anInt761 =
	 * stream.readUnsignedByte(); else if (j == 17) aBoolean767 = false; else if (j
	 * == 18) aBoolean757 = false; else if (j == 19) { i =
	 * stream.readUnsignedByte(); if (i == 1) hasActions = true; } else if (j == 21)
	 * aBoolean762 = true; else if (j == 22) aBoolean769 = false; else if (j == 23)
	 * aBoolean764 = true; else if (j == 24) { animation =
	 * stream.readUnsignedWord(); if (animation == 65535) animation = -1; } else if
	 * (j == 28) anInt775 = stream.readUnsignedByte(); else if (j == 29) aByte737 =
	 * stream.readSignedByte(); else if (j == 39) aByte742 =
	 * stream.readSignedByte(); else if (j >= 30 && j < 35) { if (actions == null)
	 * actions = new String[5]; actions[j - 30] = stream.readString(); if (actions[j
	 * - 30].equalsIgnoreCase("hidden")) actions[j - 30] = null; } else if (j == 40)
	 * { int i1 = stream.readUnsignedByte(); modifiedModelColors = new int[i1];
	 * originalModelColors = new int[i1]; for (int i2 = 0; i2 < i1; i2++) {
	 * modifiedModelColors[i2] = stream.readUnsignedWord(); originalModelColors[i2]
	 * = stream.readUnsignedWord(); } } else if (j == 41) { int j2 =
	 * stream.readUnsignedByte(); modifiedTexture = new short[j2]; originalTexture =
	 * new short[j2]; for (int k = 0; k < j2; k++) { modifiedTexture[k] = (short)
	 * stream.readUnsignedWord(); originalTexture[k] = (short)
	 * stream.readUnsignedWord(); }
	 * 
	 * } else if (j == 60) anInt746 = stream.readUnsignedWord(); else if (j == 62)
	 * aBoolean751 = true; else if (j == 64) aBoolean779 = false; else if (j == 65)
	 * thickness = stream.readUnsignedWord(); else if (j == 66) height =
	 * stream.readUnsignedWord(); else if (j == 67) width =
	 * stream.readUnsignedWord(); else if (j == 68) anInt758 =
	 * stream.readUnsignedWord(); else if (j == 69) anInt768 =
	 * stream.readUnsignedByte(); else if (j == 70) anInt738 =
	 * stream.readSignedWord(); else if (j == 71) anInt745 =
	 * stream.readSignedWord(); else if (j == 72) anInt783 =
	 * stream.readSignedWord(); else if (j == 73) aBoolean736 = true; else if (j ==
	 * 74) { aBoolean766 = true; } else { if (j != 75) continue; anInt760 =
	 * stream.readUnsignedByte(); } continue label0; } while (j != 77); anInt774 =
	 * stream.readUnsignedWord(); if (anInt774 == 65535) anInt774 = -1; anInt749 =
	 * stream.readUnsignedWord(); if (anInt749 == 65535) anInt749 = -1; int j1 =
	 * stream.readUnsignedByte(); childrenIDs = new int[j1 + 1]; for (int j2 = 0; j2
	 * <= j1; j2++) { childrenIDs[j2] = stream.readUnsignedWord(); if
	 * (childrenIDs[j2] == 65535) childrenIDs[j2] = -1; }
	 * 
	 * } while (true); if (i == -1 && name != "null" && name != null) { hasActions =
	 * anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
	 * if (actions != null) hasActions = true; } if (aBoolean766) { aBoolean767 =
	 * false; aBoolean757 = false; } if (anInt760 == -1) anInt760 = aBoolean767 ? 1
	 * : 0; }
	 */

	public void readValues(Buffer stream) {
		int flag = -1;
		do {
			int type = stream.readUnsignedByte();
			if (type == 0)
				break;
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (modelIds == null || lowMem) {
						modelTypes = new int[len];
						modelIds = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							modelIds[k1] = stream.readShort();
							modelTypes[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.position += len * 3;
					}
				}
			} else if (type == 2)
				name = stream.readString();
			else if (type == 3)
				description = stream.readString();
			else if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (modelIds == null || lowMem) {
						modelTypes = null;
						modelIds = new int[len];
						for (int l1 = 0; l1 < len; l1++)
							modelIds[l1] = stream.readShort();
					} else {
						stream.position += len * 2;
					}
				}
			} else if (type == 14)
				width = stream.readUnsignedByte();
			else if (type == 15)
				length = stream.readUnsignedByte();
			else if (type == 17)
				solid = false;
			else if (type == 18)
				impenetrable = false;
			else if (type == 19)
				hasActions = (stream.readUnsignedByte() == 1);
			else if (type == 21)
				contouredGround = true;
			else if (type == 22)
				delaysShading = true;
			else if (type == 23)
				occludes = true;
			else if (type == 24) { // Object Animations
				animation = stream.readShort();
				if (animation == 65535)
					animation = -1;
			} else if (type == 28)
				decorDisplacement = stream.readUnsignedByte();
			else if (type == 29)
				ambientLighting = stream.readSignedByte();
			else if (type == 39)
				lightDiffusion = stream.readSignedByte();
			else if (type >= 30 && type < 39) {
				if (actions == null)
					actions = new String[9];
				actions[type - 30] = stream.readString();
				if (actions[type - 30].equalsIgnoreCase("hidden"))
					actions[type - 30] = null;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				modifiedModelColors = new int[i1];
				originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = stream.readShort();
					originalModelColors[i2] = stream.readShort();
				}
			} else if (type == 41) {
				int i1 = stream.readUnsignedByte();
				originalTexture = new short[i1];
				modifiedTexture = new short[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					originalTexture[i2] = (short) stream.readShort();
					modifiedTexture[i2] = (short) stream.readShort();
				}
			} else if (type == 60)
				mapFunction = stream.readShort();
			else if (type == 62)
				inverted = true;
			else if (type == 64)
				castsShadow = false;
			else if (type == 65)
				scaleX = stream.readShort();
			else if (type == 66)
				scaleY = stream.readShort();
			else if (type == 67)
				scaleZ = stream.readShort();
			else if (type == 68)
				mapscene = stream.readShort();
			else if (type == 69)
				surroundings = stream.readUnsignedByte();
			else if (type == 70)
				translateX = stream.readSignedShort();
			else if (type == 71)
				translateY = stream.readSignedShort();
			else if (type == 72)
				translateZ = stream.readSignedShort();
			else if (type == 73)
				obstructsGround = true;
			else if (type == 74)
				hollow = true;
			else if (type == 75)
				supportItems = stream.readUnsignedByte();
			else if (type == 77 || type == 92) {
				varbit = stream.readShort();
				if (varbit == 65535)
					varbit = -1;
				
				varp = stream.readShort();
				if (varp == 65535)
					varp = -1;
				
				int var3 = -1;
				if(type == 92) {
					var3 = stream.readShort();
					if(var3 == 65535)
						var3 = -1;
				}
				
				
				int count = stream.readUnsignedByte();
				morphisms = new int[count + 2];
				for (int j2 = 0; j2 <= count; j2++) {
					morphisms[j2] = stream.readShort();
					if (morphisms[j2] == 65535)
						morphisms[j2] = -1;
				}
				morphisms[count + 1] = var3;
				
			} else if(type == 78) {//TODO Figure out what these do in OSRS
				//First short = ambient sound
				stream.readShort();
				stream.readUnsignedByte();
			} else if(type == 79) {
				stream.position += 5;
				int len = stream.readSignedByte();
				stream.position += (len * 2);
			} else if(type == 81) {
				stream.readUnsignedByte();
			} else if(type == 82) {
				stream.readShort();
			} else if(type == 249) {
				
			}
		} while (true);
		if (flag == -1 && name != "null" && name != null) {
			hasActions = modelIds != null && (modelTypes == null || modelTypes[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (hollow) {
			solid = false;
			impenetrable = false;
		}
		if (supportItems == -1)
			supportItems = solid ? 1 : 0;
	}

	private ObjectDefinition() {
		id = -1;
	}

	private short[] originalTexture;
	private short[] modifiedTexture;
	public boolean obstructsGround;
	@SuppressWarnings("unused")
	private byte lightDiffusion;
	@SuppressWarnings("unused")
	private byte ambientLighting;
	private int translateX;
	public String name;
	private int scaleZ;
	private static final Model[] aModelArray741s = new Model[4];
	public int width;
	private int translateY;
	public int mapFunction;
	private int[] originalModelColors;
	private int scaleX;
	public int varp;
	private boolean inverted;
	public static boolean lowMem;
	private static Buffer stream;
	public int id;
	public static int[] streamIndices;
	public boolean impenetrable;
	public int mapscene;
	public int morphisms[];
	public int supportItems;
	public int length;
	public boolean contouredGround;
	public boolean occludes;
	public static Client clientInstance;
	private boolean hollow;
	public boolean solid;
	public int surroundings;
	private boolean delaysShading;
	private static int cacheIndex;
	private int scaleY;
	public int[] modelIds;
	public int varbit;
	public int decorDisplacement;
	private int[] modelTypes;
	public String description;
	public boolean hasActions;
	public boolean castsShadow;
	public static Cache mruNodes2 = new Cache(30);
	public int animation;
	private static ObjectDefinition[] cache;
	private int translateZ;
	private int[] modifiedModelColors;
	public static Cache mruNodes1 = new Cache(500);
	public String actions[];
	
	public Sprite overheadSprite;
	public float overheadSpriteOpacity = 1.0f;
	public int overheadSpriteHeight;
	
	public String overheadText;
	public int overheadTextHeight;
	public int overheadColour;
	public float overheadTextOpacity = 1.0f;
	public int overheadShadow;
	public RSFont font;
	
	public LightPosition lightPos;
}
