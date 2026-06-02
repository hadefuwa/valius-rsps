package com.client.cache.definitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//
//import org.apache.commons.io.FileUtils;
import java.util.Arrays;

import javax.swing.text.html.parser.Entity;

import com.client.Client;
import com.client.cache.Archive;
import com.client.cache.animation.Frame;
import com.client.cache.config.VarBit;
import com.client.cache.graphics.Sprite;
import com.client.collection.Cache;
import com.client.config.Configuration;
import com.client.draw.raster.Rasterizer3D;
import com.client.io.Buffer;
import com.client.map.objects.FloatingSprite;
import com.client.map.objects.FloatingText;
import com.client.model.Model;

public final class NpcDefinition {
	
	public void applyTexturing(Model m, int id) {
		switch (id) {
		case 6015://easter bunny
		m.overrideTexture(59, 78);
		//m.setTexture(59);
		break;
		case 6019:
		m.overrideTexture(40, 78);
		break;
		case 3804:
			m.setTexture(66);
			break;
		case 3803:
			m.overrideTexture(59, 66);
			break;
		case 3825:
			m.overrideTexture(59, 67);
			break;
			
		case 3835:
			m.overrideTexture(59, 97);
			break;
			
		case 3387:
			m.overrideTexture(40, 86);
			m.overrideTexture(59, 77);
			break;
			
			
		case 3838:
			m.overrideTexture(59, 101);
			break;
			
		case 3840:
			m.overrideTexture(59, 117);
			break;
			
		case 3830:
		case 3829:
			m.overrideTexture(59, 66);
			break;
			
		case 3848:
			m.overrideTexture(59, 111);
			break;
			
		case 3849:
			m.overrideTexture(59, 112);
			break;
			
		case 3850:
			m.overrideTexture(59, 114);
			break;
		}
	}
	public static NpcDefinition forID(int i) {
		for (int j = 0; j < 20; j++)
			if (cache[j].interfaceType == i)
				return cache[j];

		if(i >= streamIndices.length) {
			return forID(0);
		}
		anInt56 = (anInt56 + 1) % 20;
		NpcDefinition entityDef = cache[anInt56] = new NpcDefinition();
		stream.position = streamIndices[i];
		entityDef.interfaceType = i;
		entityDef.readValues(stream);
		if (i == 1524 || i == 3913 || i == 4712 || i == 3417 || i ==3657 || i == 1520) {
			FloatingSprite floatingSprite = new FloatingSprite();
			floatingSprite.sprite = new Sprite("/interfaces/prestige/playerstats/skills/img 10");
			floatingSprite.spriteHeight = 200;
			floatingSprite.spriteOpacity = 0.6f;
		}
		if ( i == 17) {
			entityDef.actions = new String[] {"Talk-to", null, "Travel", "Nardah" , null};
		}
		if ( i == 3257) {
			entityDef.actions = new String[] {"Pickpocket", null, "Trade", null , null};
		}
		if ( i == 3936) {
			entityDef.actions = new String[] {"Talk-to", null, "Jatizso", "Neitiznot" , null};
		}
		if ( i == 4712) {
			entityDef.actions = new String[] {"Fish", null, "Big Net", null , null};
		}
		if(i == 4306) {
			entityDef.name = "Wise Old Man (Skillcapes)";
			entityDef.actions = new String[] {"Trade", null, null, null, null};
		}
		if(i == 401) {
			entityDef.name = "Turael (Low Level tasks)";
		}
		if(i == 402) {
			entityDef.name = "Mazchna (Mid Level tasks)";
		}
		if(i == 405) {
			entityDef.name = "Duradel (High Level tasks)";
		}
		if(i == 6797) {
			entityDef.name = "Nieve (Boss Tasks)";
		}
		if (i == 2814) {
			entityDef.name = "Iron man Supplies";
			entityDef.description = "A shop specifically for iron men.";
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Ironman Shopkeeper";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;
			FloatingSprite floatingSprite = new FloatingSprite();
			floatingSprite.sprite = new Sprite("/interfaces/prestige/playerstats/skills/img 10");
			floatingSprite.spriteHeight = 200;
			floatingSprite.spriteOpacity = 0.6f;
		}if(i==3218 || i ==3217){
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
		}
		if (i == 5449) {
			entityDef.actions = new String[5];
			entityDef.actions[0] = "Talk-to";
			entityDef.actions[2] = "Decant";
			entityDef.actions[3] = "Clean Herbs";
		}
		if(i==2662){//pvm shop
			entityDef.name = "PVM Point Shop";
			entityDef.combatLevel = 0;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Trade", null, null, null, null };
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Pvm Point Shop";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;
		}
		if(i==7586){// ice fiend
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==7597){ //lizard
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==7576){ // air crab
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==7577){ //fire crab
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==7578){ //earth crab
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==7579){ //water crab
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if(i==8278){//range shop
			entityDef.name = "Range Shop";
			entityDef.actions = new String[] { "Trade", null, null, null, null };
		}
		
		if(i==6781){//skill point shop
			entityDef.name = "Skill Point Shop";
			entityDef.actions = new String[] { "Trade", null, null, null, null };
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Skill Point Shop";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;
		}
		if(i==8122){//point shop
			entityDef.name = "Point Reward Shops";
			entityDef.actions = new String[] { "Trade", null, null, null, null };
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Point Reward Shops";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;

		}
		if(i==3216){//point shop
			entityDef.name = "Combat Supply Shop";
			entityDef.actions = new String[] { "Trade", null, null, null, null };
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Combat Supply Shop";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;

		}
		if(i==534){//outfit shop
			entityDef.name = "Fancy Outfit Shop";
		}
		if(i==2578){//imbue rings
			entityDef.name = "Brother Jared (Ring Imbues)";
		}
		if(i==2813){
			entityDef.name = "General Supplies";
			entityDef.description = "Buy combat supplies, food and potions here.";
		}
		if(i==3461){//bm shop
			entityDef.name = "Bloodmoney Shop";
		}
		if(i==5567){//grim reaper
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
		}
		if(i==4625){
			entityDef.name = "Donator shop";
			entityDef.actions = new String[] { "Talk-to", "Trade", null, null, null };
		}
		if(i==7204){
			entityDef.name = "Donator shop";
			entityDef.actions = new String[] { "Talk-to", null, "Trade", null, null };
			FloatingText floatingText = new FloatingText();
			floatingText.font = Client.instance.newRegularFont;
			floatingText.overheadText = "Donator Shop";
			floatingText.textColor = 0xFFFFFF;
			floatingText.textShadow = 0x000000;
			floatingText.textOpacity = 0.9f;
			floatingText.textHeight = 200;
			entityDef.floatingText = floatingText;
		}
		if(i==8280){
			entityDef.name = "Damien (200M Capes)";
			entityDef.actions = new String[] { "Trade", "null", null, null, null };
		}
		if(i==7041){
			entityDef.name = "Ticket Exchange";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "He can exchange your vote & pkp tickets so you can spend them in the shop.";
		}
		if(i==7303){
			entityDef.name = "Watson (Clue Scroll Exchange)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Exchange clue scrolls here for elite clue scrolls.";
		}
		if(i==1909){
			entityDef.name = "Daily Task Manager";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Recieve daily pvm/skilling tasks from her.";
		}
		if(i==2989){
			entityDef.name = "Prestige Manager";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Talk to him for anything to do with Prestiging.";
		}
		if(i==6559){
			entityDef.name = "Mandrith (PKP Shop)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "He can exchange your vote & pkp tickets so you can spend them in the shop.";
		}
		if(i==4423){
			entityDef.name = "Jossik (Prayer book merchant)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Buy damaged prayer books from Jossik to put your pages into.";
		}
		if(i==3219){
			entityDef.name = "Cooking tutor (Food shop)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "You can buy food from this shop.";
		}
		if(i==7456){
			entityDef.name = "Perdu (Armour Repairs)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Talk to him to repair any broken/degraded armour.";
		}
		if(i==7663){
			entityDef.name = "Kristillia (Wilderness Slayer)";
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.description = "Talk to her to receive a wilderness slayer task.";
		}

		if (i == 5314) {
			entityDef.name = "Mystical Wizard";
			entityDef.actions = new String[] { "Teleport", "Pickup", "Open Bank", "Daily Boost", null };
			entityDef.description = "Teleporting and banking? pretty useful.";
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;
		}

		if (i == 8026) {
			entityDef.name = "Vorkath";
			// entityDef.combatLevel = 732;
			entityDef.models = new int[] { 35023 };
			entityDef.standAnim = 7946;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Poke", null, null, null, null };
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
		}
		if (i == 8027) {
			entityDef.name = "Vorkath";
			entityDef.combatLevel = 732;
			entityDef.models = new int[] { 35023 };
			entityDef.standAnim = 7950;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, null, null, null, null };
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
		}
		if (i == 8028) {
			entityDef.name = "Vorkath";
			entityDef.combatLevel = 732;
			entityDef.models = new int[] { 35023 };
			entityDef.standAnim = 7948;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
		}

		if (i == 5001) {
			entityDef.name = "Anti-santa";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 28975, 28984, 28982, 28980, 28977, 28988, 57001 };
			entityDef.standAnim = 2065;
			entityDef.walkAnim = 2064;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 300;
			entityDef.resizeX = 300;
		}
		if (i == 6477) {
			entityDef.name = "Enraged Tarn";
			entityDef.combatLevel = 500;
			entityDef.models = new int[] { 60322 };
			entityDef.standAnim = 5616;
			entityDef.walkAnim = 5615;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 220;
			entityDef.resizeX = 220;
		}
		if (i == 5000) {
			entityDef.name = "Tiny anti-santa";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 28975, 28984, 28982, 28980, 28977, 28988, 57001 };
			entityDef.standAnim = 2065;
			entityDef.walkAnim = 2064;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}

		if (i == 5670) {
			entityDef.name = "Pet demonic gorilla";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 31241 };
			entityDef.standAnim = 7230;
			entityDef.walkAnim = 7233;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;
		}
		
		if (i == 8321) {
			entityDef.name = "Pet cow";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 23889 };
			entityDef.standAnim = 5852;
			entityDef.walkAnim = 5848;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;
		}

		if (i == 5648) {
			entityDef.name = "Pet crawling hand";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5071 };
			entityDef.standAnim = 1588;
			entityDef.walkAnim = 1589;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 90;
			entityDef.resizeX = 90;
		}

		if (i == 5649) {
			entityDef.name = "Pet cave bug";
			entityDef.combatLevel = 0;
			entityDef.originalColors = new int[] { 1585, 431, 551, 8088 };
			entityDef.newColors = new int[] { 1699, 4766, 545, 7062 };
			entityDef.models = new int[] { 23854 };
			entityDef.standAnim = 6078;
			entityDef.walkAnim = 6077;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 90;
			entityDef.resizeX = 90;
		}

		if (i == 5650) {
			entityDef.name = "Pet cave crawler";
			entityDef.combatLevel = 0;
			entityDef.originalColors = new int[] { 21656, 21652, 8375};
			entityDef.newColors = new int[] { 16536, 14610, 5442};
			entityDef.models = new int[] { 5066, 5067, 5069 };
			entityDef.standAnim = 226;
			entityDef.walkAnim = 225;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 90;
			entityDef.resizeX = 90;
		}

		if (i == 5651) {
			entityDef.name = "Pet banshee";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5063,5079 };
			entityDef.standAnim = 1522;
			entityDef.walkAnim = 1521;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;
		}

		if (i == 5652) {
			entityDef.name = "Pet cave slime";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5786 };
			entityDef.standAnim = 1790;
			entityDef.walkAnim = 1788;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;
		}

		if (i == 5653) {
			entityDef.name = "Pet rockslug";
			entityDef.combatLevel = 0;
			entityDef.originalColors = new int[] {6829};
			entityDef.newColors = new int[] { 21782};
			entityDef.models = new int[] { 5084 };
			entityDef.standAnim = 1566;
			entityDef.walkAnim = 1564;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 110;
			entityDef.resizeX = 110;
		}

		if (i == 5654) {
			entityDef.name = "Pet cockatrice";// haha it has cock in its name.
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5070 };
			entityDef.standAnim = 1561;
			entityDef.walkAnim = 1559;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;
		}

		if (i == 5655) {
			entityDef.name = "Pet pyrefiend";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5083 };
			entityDef.standAnim = 1578;
			entityDef.walkAnim = 1579;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
		}

		if (i == 5656) {
			entityDef.name = "Pet basilisk";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5064 };
			entityDef.standAnim = 1545;
			entityDef.walkAnim = 1544;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;
		}

		if (i == 5657) {
			entityDef.name = "Pet infernal mage";
			entityDef.originalColors = new int[] { 4550, 8741, 7952, 6798, 5404, 4626 };
			entityDef.newColors = new int[] { 8514, 10644, 11142, 10529, 8720, 7566 };
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 230, 250, 292, 170, 179, 5105, 185, 5036, 317, 5103, 5099, 5106 };
			entityDef.standAnim = 813;
			entityDef.walkAnim = 1205;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}

		if (i == 5658) {
			entityDef.name = "Pet bloodveld";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5065 };
			entityDef.standAnim = 1551;
			entityDef.walkAnim = 1549;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}

		if (i == 5659) {
			entityDef.name = "Pet jelly";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5081 };
			entityDef.standAnim = 1583;
			entityDef.walkAnim = 1584;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;

		}

		if (i == 5660) {
			entityDef.name = "Pet turoth";
			entityDef.originalColors = new int[] { 10357, 5351 };
			entityDef.newColors = new int[] { 317, 181 };
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5086 };
			entityDef.standAnim = 1594;
			entityDef.walkAnim = 1593;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;

		}

		if (i == 5661) {
			entityDef.name = "Pet aberrant spectre";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5085 };
			entityDef.standAnim = 1506;
			entityDef.walkAnim = 1505;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5662) {
			entityDef.name = "Pet dust devil";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5076, 5077 };
			entityDef.standAnim = 1556;
			entityDef.walkAnim = 1554;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;

		}

		if (i == 5663) {
			entityDef.name = "Pet kurask";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5082 };
			entityDef.standAnim = 1511;
			entityDef.walkAnim = 1510;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;

		}

		if (i == 5665) {
			entityDef.name = "Pet skeletal wyvern";
			entityDef.originalColors = new int[] { 103, 82 , 61, 41, 0 };
			entityDef.newColors = new int[] { -25506, -24362, -24374, -24391, -27475 };
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 10351, 10354, 10356, 21832  };
			entityDef.standAnim = 2984;
			entityDef.walkAnim = 2982;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5666) {
			entityDef.name = "Pet gargyole";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5078, 5079  };
			entityDef.standAnim = 1516;
			entityDef.walkAnim = 1515;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}

		if (i == 5667) {
			entityDef.name = "Pet nechryael";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5074  };
			entityDef.standAnim = 1527;
			entityDef.walkAnim = 1526;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}

		if (i == 5668) {
			entityDef.name = "Pet abyssal demon";
			entityDef.originalColors = new int[] { 4015 };
			entityDef.newColors = new int[] { 528 };
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 5062};
			entityDef.standAnim = 1536;
			entityDef.walkAnim = 1534;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5669) {
			entityDef.name = "Pet dark beast";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 26395};
			entityDef.standAnim = 2730;
			entityDef.walkAnim = 2729;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;

		}

		if (i == 5693) {
			entityDef.name = "Pet night beast";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32933};
			entityDef.standAnim = 2730;
			entityDef.walkAnim = 2729;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5671) {
			entityDef.name = "Pet greater abyssal demon";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32921};
			entityDef.standAnim = 1536;
			entityDef.walkAnim = 1534;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5672) {
			entityDef.name = "Pet crushing hand";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32922};
			entityDef.standAnim = 1588;
			entityDef.walkAnim = 1589;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}


		if (i == 5673) {
			entityDef.name = "Pet chasm crawler";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32918};
			entityDef.standAnim = 226;
			entityDef.walkAnim = 225;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}

		if (i == 5674) {
			entityDef.name = "Pet screaming banshee";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32823};
			entityDef.standAnim = 7545;
			entityDef.walkAnim = 1104;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5675) {
			entityDef.name = "Pet twisted banshee";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32847};
			entityDef.standAnim = 7545;
			entityDef.walkAnim = 1104;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5676) {
			entityDef.name = "Pet giant rockslug";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32919};
			entityDef.standAnim = 1566;
			entityDef.walkAnim = 1564;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5677) {
			entityDef.name = "Pet cockathrice";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32920};
			entityDef.standAnim = 1561;
			entityDef.walkAnim = 1559;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;

		}

		if (i == 5678) {
			entityDef.name = "Pet flaming pyrelord";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32923};
			entityDef.standAnim = 1578;
			entityDef.walkAnim = 1579;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}


		if (i == 5679) {
			entityDef.name = "Pet monstrous basilisk";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32924};
			entityDef.standAnim = 1545;
			entityDef.walkAnim = 1544;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5680) {
			entityDef.name = "Pet malevolent mage";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32929};
			entityDef.standAnim = 813;
			entityDef.walkAnim = 1205;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;

		}

		if (i == 5681) {
			entityDef.name = "Pet insatiable bloodveld";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32926};
			entityDef.standAnim = 1551;
			entityDef.walkAnim = 1549;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}

		if (i == 5682) {
			entityDef.name = "Pet insatiable mutated bloodveld";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32925};
			entityDef.standAnim = 1551;
			entityDef.walkAnim = 1549;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}

		if (i == 5683) {
			entityDef.name = "Pet vitreous jelly";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32852};
			entityDef.standAnim = 1583;
			entityDef.walkAnim = 1584;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5684) {
			entityDef.name = "Pet vitreous warped jelly";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32917};
			entityDef.standAnim = 1583;
			entityDef.walkAnim = 1584;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}


		if (i == 5685) {
			entityDef.name = "Pet cave abomination";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32935};
			entityDef.standAnim = 4231;
			entityDef.walkAnim = 4236;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}


		if (i == 5686) {
			entityDef.name = "Pet abhorrent spectre";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32930};
			entityDef.standAnim = 6371;
			entityDef.walkAnim = 6372;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5687) {
			entityDef.name = "Pet repugnant spectre";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32931};
			entityDef.standAnim = 6371;
			entityDef.walkAnim = 6372;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}


		if (i == 5688) {
			entityDef.name = "Pet choke devil";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32927};
			entityDef.standAnim = 1556;
			entityDef.walkAnim = 1554;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;

		}


		if (i == 5689) {
			entityDef.name = "Pet king kurask";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32934};
			entityDef.standAnim = 4231;
			entityDef.walkAnim = 4236;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}


		if (i == 5690) {
			entityDef.name = "Pet nuclear smoke devil";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32928};
			entityDef.standAnim = 1829;
			entityDef.walkAnim = 1828;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5691) {
			entityDef.name = "Pet marble gargoyle";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 34251};
			entityDef.standAnim = 7810;
			entityDef.walkAnim = 7810;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5692) {
			entityDef.name = "Pet nechryarch";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 32932};
			entityDef.standAnim = 6371;
			entityDef.walkAnim = 6372;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5694) {
			entityDef.name = "Pet Patrity";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 235, 253, 315, 173, 176, 60541, 31899, 275, 181};
			entityDef.standAnim = 3040;
			entityDef.walkAnim = 3039;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;

		}


		if (i == 5695) {
			entityDef.name = "Pet xarpus";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 35383};
			entityDef.standAnim = 8058;
			entityDef.walkAnim = 8058;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 25;
			entityDef.resizeX = 25;

		}


		if (i == 5696) {
			entityDef.name = "Pet nyclocas vasilias";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 35182};
			entityDef.standAnim = 7988;
			entityDef.walkAnim = 7987;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5697) {
			entityDef.name = "Pet pestilent bloat";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 35404};
			entityDef.standAnim = 8080;
			entityDef.walkAnim = 8081;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5698) {
			entityDef.name = "Pet maiden of sugadinti";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 35385};
			entityDef.standAnim = 8090;
			entityDef.walkAnim = 8090;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 30;
			entityDef.resizeX = 30;

		}

		if (i == 5700) {
			entityDef.name = "Pet lizardman shaman";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 4039};
			entityDef.standAnim = 7191;
			entityDef.walkAnim = 7195;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5701) {
			entityDef.name = "Pet abyssal sire";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 29477};
			entityDef.standAnim = 4533;
			entityDef.walkAnim = 4534;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 25;
			entityDef.resizeX = 25;

		}

		if (i == 5702) {
			entityDef.name = "Pet black demon";
			entityDef.combatLevel = 0;
			entityDef.originalColors = new int[] { 910, 912 , 1938, 1814, 1690, 0 };
			entityDef.newColors = new int[] { 898, 4, 8, 12, 16, 5206 };
			entityDef.models = new int[] {17375, 17391, 17384, 17399, 17390 };
			entityDef.standAnim = 66;
			entityDef.walkAnim = 63;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5703) {
			entityDef.name = "Pet greater demon";
			entityDef.combatLevel = 0;
			entityDef.originalColors = new int[] { 1690, 1814 , 1938, 912, 910, 0 };
			entityDef.newColors = new int[] { 1447, 1571, 1566, 540, 538, 7357 };
			entityDef.models = new int[] {17375, 17391, 17384, 17399, 17390 };
			entityDef.standAnim = 66;
			entityDef.walkAnim = 63;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5704) {
			entityDef.name = "Pet revenant imp";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34156};
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 110;
			entityDef.resizeX = 110;

		}

		if (i == 5705) {
			entityDef.name = "Pet revenant goblin";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34262};
			entityDef.standAnim = 6181;
			entityDef.walkAnim = 6180;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;

		}

		if (i == 5706) {
			entityDef.name = "Pet revenant pyrefiend";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34142};
			entityDef.standAnim = 1578;
			entityDef.walkAnim = 1579;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 55;
			entityDef.resizeX = 55;

		}

		if (i == 5707) {
			entityDef.name = "Pet revenant hobgoblin";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34157};
			entityDef.standAnim = 166;
			entityDef.walkAnim = 162;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;

		}

		if (i == 5708) {
			entityDef.name = "Pet revenant cyclops";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34155};
			entityDef.standAnim = 4650;
			entityDef.walkAnim = 4649;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 45;
			entityDef.resizeX = 45;

		}

		if (i == 5709) {
			entityDef.name = "Pet revenant hellhound";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34143};
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6583;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 55;
			entityDef.resizeX = 55;

		}

		if (i == 5710) {
			entityDef.name = "Pet revenant demon";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34161, 34162, 34160, 34159, 17390};
			entityDef.standAnim = 66;
			entityDef.walkAnim = 63;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 35;
			entityDef.resizeX = 35;

		}

		if (i == 5711) {
			entityDef.name = "Pet revenant ork";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34154};
			entityDef.standAnim = 4318;
			entityDef.walkAnim = 4319;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 65;
			entityDef.resizeX = 65;

		}

		if (i == 5712) {
			entityDef.name = "Pet revenant dark beast";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34158};
			entityDef.standAnim = 2730;
			entityDef.walkAnim = 2729;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 35;
			entityDef.resizeX = 35;

		}

		if (i == 5713) {
			entityDef.name = "Pet revenant knight";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34145};
			entityDef.standAnim = 813;
			entityDef.walkAnim = 1205;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;

		}

		if (i == 5714) {
			entityDef.name = "Pet revenant dragon";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34163};
			entityDef.standAnim = 90;
			entityDef.walkAnim = 79;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5715) {
			entityDef.name = "Pet glod";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {26074};
			entityDef.standAnim = 6504;
			entityDef.walkAnim = 6505;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 25;
			entityDef.resizeX = 25;

		}

		if (i == 5716) {
			entityDef.name = "Pet ice queen";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {379, 368, 468, 440, 353, 339};
			entityDef.originalColors = new int[] { 41, 61, 4550, 12224, 25238, 6798 };
			entityDef.newColors = new int[] { -22052, -26150, -24343, -22052, -22052, -23327 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;

		}

		if (i == 5717) {
			entityDef.name = "Pet enraged tarn";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60322 };
			entityDef.standAnim = 5616;
			entityDef.walkAnim = 5615;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}

		if (i == 5718) {
			entityDef.name = "Pet jalTok-jad";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 33012 };
			entityDef.standAnim = 7589;
			entityDef.walkAnim = 7588;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;

		}

		if (i == 5719) {
			entityDef.name = "Pet rune dragon";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {34668, 24587, 24582, 17423};
			entityDef.originalColors = new int[] { -27471, -27479, -27446, -27364, -27359, -22256 };
			entityDef.newColors = new int[] { -26441, -26445, -28464, -25303, -27479, -25442 };
			entityDef.standAnim = 90;
			entityDef.walkAnim = 79;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;

		}
		if (i == 5458) {
			entityDef.name = "Pet wyrm";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {36166};
			entityDef.standAnim = 8266;
			entityDef.walkAnim = 8266;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", "Transform", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 35;
			entityDef.resizeX = 35;

		}
		if (i == 5459) {
			entityDef.name = "Pet drake";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {36160};
			entityDef.standAnim = 8274;
			entityDef.walkAnim = 8273;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 35;
			entityDef.resizeX = 35;

		}
		if (i == 5460) {
			entityDef.name = "Pet wyrm";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {36167};
			entityDef.standAnim = 8267;
			entityDef.walkAnim = 8267;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", "Transform", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 35;
			entityDef.resizeX = 35;

		}
		if (i == 5461) {
			entityDef.name = "Heart";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {62772};
			entityDef.standAnim = 3117;
			entityDef.walkAnim = 3117;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;

		}
		if (i == 5462) {
			entityDef.name = "Enraged general graardor";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {60216, 60881};
			entityDef.standAnim = 7017;
			entityDef.walkAnim = 7016;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 150;
			entityDef.resizeX = 150;

		}
		if (i == 4999) {
			entityDef.name = "Anti-santa imp";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 45294 };
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = true;
			entityDef.getDegreesToTurn = 64;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
		}
		if (i == 4998) {
			entityDef.name = "Anti-santa imp";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 45294 };
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = false;
			entityDef.getDegreesToTurn = 64;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
		}
		if (i == 4996) {
			entityDef.name = "Santa claus";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 28976, 28983, 28981, 28979, 28989, 28978 };
			entityDef.dialogueModels = new int [] {29016};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
			entityDef.originalColors = new int []  { 127 };
			entityDef.newColors = new int [] { 115 };
		}
		if (i == 5571) {
			entityDef.name = "Zombie Follower";
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.resizeY = 75;
			entityDef.resizeX = 75;
		}
		if (i == 5570) {
			entityDef.name = "Zombie Follower";
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.resizeY = 75;
			entityDef.resizeX = 75;
		}
		if (i == 5569) {
			entityDef.name = "Zombie Follower";
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.resizeY = 75;
			entityDef.resizeX = 75;
		}
		if (i == 5568) {
			entityDef.name = "Zombie Follower";
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.resizeY = 75;
			entityDef.resizeX = 75;
		}
		if (i == 3291) {
			entityDef.name = "Petie";
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.resizeY = 90;
			entityDef.resizeX = 90;
		}
		
		if (i == 6000) {
			entityDef.name = "Pet Easter Bunny";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {37239};
			entityDef.standAnim = 8316;
			entityDef.walkAnim = 8315;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}
		if (i == 6001) {
			entityDef.name = "Pet Choco";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {37239};
			entityDef.standAnim = 8316;
			entityDef.walkAnim = 8315;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.originalColors = new int []  { 2378 };
			entityDef.newColors = new int [] { 7079 };
		}
		if (i == 6002) {
			entityDef.name = "Pet Milkie";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {37239};
			entityDef.standAnim = 8316;
			entityDef.walkAnim = 8315;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.originalColors = new int []  { 2378 };
			entityDef.newColors = new int [] { 6040 };
		}
		if (i == 6010) {
			entityDef.name = "Pet Blue";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {37239};
			entityDef.standAnim = 8316;
			entityDef.walkAnim = 8315;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.originalColors = new int []  { 2378 };
			entityDef.newColors = new int [] { 35321 };
		}
		if (i == 6003) {
			entityDef.name = "Pet Goldie";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {37239};
			entityDef.standAnim = 8316;
			entityDef.walkAnim = 8315;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.originalColors = new int []  { 2378 };
			entityDef.newColors = new int [] { 9152 };
		}
		if (i == 6004) {
			entityDef.name = "Crazed Bunny";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {23901};
			entityDef.standAnim = 6089;
			entityDef.walkAnim = 6088;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
			entityDef.originalColors = new int []  { 5413, 5417, 5421 };
			entityDef.newColors = new int [] { 935, 111, 127 };
		}
		if (i == 6005) {
			entityDef.name = "Peter Rabbit";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {28602};
			entityDef.standAnim = 6089;
			entityDef.walkAnim = 6088;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 120;
			entityDef.resizeX = 120;
			entityDef.originalColors = new int []  { 2378 };
			entityDef.newColors = new int [] { 9152 };
		}
		if (i == 6006) {
			entityDef.name = "Chocolate Chicken";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {35150};
			entityDef.standAnim = 8318;
			entityDef.walkAnim = 8326;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;
			entityDef.originalColors = new int []  { 947 };
			entityDef.newColors = new int [] { 8128 };
		}
		if (i >= 5538 && i <= 5543) {
			entityDef.standAnim = 0;
		}
		if (i == 6007) {
			entityDef.name = "Chocolate Chicken";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {35150};
			entityDef.standAnim = 8318;
			entityDef.walkAnim = 8326;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;
			entityDef.originalColors = new int []  { 947, 8301 };
			entityDef.newColors = new int [] { 8128, 947 };
		}
		if (i == 6008) {
			entityDef.name = "Chocolate Chicken";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {35150};
			entityDef.standAnim = 8318;
			entityDef.walkAnim = 8326;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;
			entityDef.originalColors = new int []  { 947, 8301 };
			entityDef.newColors = new int [] { 8128, 25511 };
		}
		if (i == 6009) {
			entityDef.name = "Chocolate Chicken";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {35150};
			entityDef.standAnim = 8318;
			entityDef.walkAnim = 8326;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 85;
			entityDef.resizeX = 85;
			entityDef.originalColors = new int []  { 947, 8301 };
			entityDef.newColors = new int [] { 8128, 38835 };
		}
		if (i == 6011) {
			entityDef.name = "Angel of Death";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] { 60310 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
		}
		if (i == 6012) {
			entityDef.name = "Giant Warmonger";
			entityDef.combatLevel = 1212;
			entityDef.models = new int[] { 60439 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
		}
		
		if (i == 6013) {
			entityDef.name = "Death";
			entityDef.combatLevel = 1708;
			entityDef.models = new int[] { 60441 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;
		}
		
		if (i == 6014) {
			entityDef.name = "Void Knight Champion";
			entityDef.combatLevel = 500;
			entityDef.models = new int[] { 60463 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 50;
			entityDef.resizeX = 50;
		}
		if (i == 6015) {
			entityDef.name = "Celestial Fairy";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60491 };
			entityDef.standAnim = 112;
			entityDef.walkAnim = 106;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalTextureColors = new int []  {  947};
			entityDef.modifiedTextureColors = new int [] { 78};
			entityDef.originalColors = new int []  { 937, 11200};
			entityDef.newColors = new int [] { 42663, 41883 };
		}
		if (i == 6016) {
			entityDef.name = "Andy";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50169 };
			entityDef.standAnim = 2166;
			entityDef.walkAnim = 2165;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 2380, 2372};
			entityDef.newColors = new int [] { 2376, 2368 };
			entityDef.resizeY = 200;
			entityDef.resizeX = 200;
			entityDef.size = 2;
		}
		if (i == 6017) {
			entityDef.name = "Mod Divine";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 14283 };
			entityDef.standAnim = 3822;
			entityDef.walkAnim = 3825;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.size = 1;
		}
		
		if (i == 6018) {
			entityDef.name = "Star Sprite";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60506 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
            entityDef.onMinimap = false;
		}
		
		if (i == 6019) {
			entityDef.name = "Celestial Dragon";
			entityDef.combatLevel = 289;
			entityDef.models = new int[] { 28300, 28301, 28302, 17423 };
			entityDef.standAnim = 90;
			entityDef.walkAnim = 79;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 110;
			entityDef.resizeX = 110;
			entityDef.originalColors = new int []  { 16, 142 };
			entityDef.newColors = new int [] { 41631, 41634 };
		}
		if (i == 6020) {
            entityDef.name = "Scoop";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 65486 };
            entityDef.dialogueModels = new int[] { 65486 };
            entityDef.standAnim = 3040;
            entityDef.walkAnim = 3039;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Pick-up", null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
        }
		if (i == 3798) {
			entityDef.name = "Celestial Crow";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60507 };
			entityDef.standAnim = 6786;
			entityDef.walkAnim = 6784;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 220;
			entityDef.resizeX = 220;
			entityDef.originalTextureColors = new int []  {  10382};
			entityDef.modifiedTextureColors = new int [] { 78};
			entityDef.originalColors = new int []  { 10378, 10502 };
			entityDef.newColors = new int [] { 0, 0 };
		}
		
		if (i == 3799) {
			entityDef.name = "Celestial Penguin";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60508 };
			entityDef.standAnim = 5668;
			entityDef.walkAnim = 5666;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalTextureColors = new int []  {  10343 };
			entityDef.modifiedTextureColors = new int [] { 78};
			entityDef.originalColors = new int []  { 16, 12, 20 , 24, 8, 10332, 10337  };
			entityDef.newColors = new int [] { 0, 0, 0 , 0 , 0, 0 , 0};
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}
		
		if (i == 3801) {
			entityDef.name = "Celestial Snake";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60509 };
			entityDef.standAnim = 277;
			entityDef.walkAnim = 274;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalTextureColors = new int []  {  10644, 10512 };
			entityDef.modifiedTextureColors = new int [] { 78, 78};
			entityDef.originalColors = new int []  { 10413, 10405, 10524 };
		    entityDef.newColors = new int [] { 0, 0, 0};
		}
		
		if (i == 3802) {
			entityDef.name = "Celestial Scorpion";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60510 };
			entityDef.standAnim = 6252;
			entityDef.walkAnim = 6253;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.originalTextureColors = new int []  {  142};
			entityDef.modifiedTextureColors = new int [] { 78};
			entityDef.originalColors = new int []  { 4884, 4636, 3974, 4525, 4645 };
		    entityDef.newColors = new int [] { 0, 0, 0, 0 , 0};
		}
		
		if (i == 3803) {
			entityDef.name = "Blood Bird";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60517 };
			entityDef.standAnim = 6809;
			entityDef.walkAnim = 6808;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalTextureColors = new int []  { 1946, 2983, 6084, 2735, 5053, 6082, 4013, 2733, 4011, 2880, 8150};
			entityDef.modifiedTextureColors = new int [] { 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66};
		}
		
		if (i == 3804) {
			entityDef.name = "Blood Death";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60441 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 15;
			entityDef.resizeX = 15;
		}
		if (i == 3805) {
			entityDef.name = "Magic mushroom";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60532 };
			entityDef.standAnim = 3324;
			entityDef.walkAnim = 3322;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}
		if (i == 3806) {
			entityDef.name = "Hunter's Penguin";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60548 };
			entityDef.standAnim = 5668;
			entityDef.walkAnim = 5666;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}
		
		if (i == 3808) {
			entityDef.name = "Chef Harambe";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60921};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;
		}
		
		if (i == 3809) {
			entityDef.name = "Void Knight Chamption Jr";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60463 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
		}
		
		if (i == 3810) {
			entityDef.name = "Pet Jaycorr";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60592 };
			entityDef.standAnim = 3828;
			entityDef.walkAnim = 3828;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;
		}
		
		
		if (i == 3811) {
			entityDef.name = "Test";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 35349, 35371, 35359, 35356, 31006, 60535, 60545, 31227 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
		}
		
		if (i == 3812) {
			entityDef.name = "Wolfy (green)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60597 };
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6556;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
		    entityDef.newColors = new int [] {11029, 11028, 11030, 11031, 11032, 11033, 11034, 926, 926, 926, 926};
		}
		
		if (i == 3813) {
			entityDef.name = "Wolfy (blue)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60597 };
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6556;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
		    entityDef.newColors = new int [] {-22766, -22768, -22769, -22770, -22771, -22772, -22773, 926, 926, 926, 926};
		}
		
		if (i == 3814) {
			entityDef.name = "Wolfy (red)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60597 };
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6556;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
		    entityDef.newColors = new int [] {-110, -111, -112, -113, -114, -115, -116, 926, 926, 926, 926};
		}
		
		if (i == 3815) {
			entityDef.name = "Wolfy (Black)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60597 };
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6556;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
		    entityDef.newColors = new int [] {10, 11, 12, 13, 14, 15, 16, 926, 926, 926, 926};
		}
		
		if (i == 3816) {
			entityDef.name = "Wolfy (gold)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60597 };
			entityDef.standAnim = 6580;
			entityDef.walkAnim = 6556;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 39319, 3074, 3083, 3090, 3086, 3081, 3077, 11064, 10058, 11052, 9779};
		    entityDef.newColors = new int [] {9033, 9034, 9035 ,9036, 9037, 9038, 9039, 926, 926, 926, 926};
		}
		
		if (i == 3817) {
			entityDef.name = "Tortle";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60618 };
			entityDef.standAnim = 3952;
			entityDef.walkAnim = 3953;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 45;
			entityDef.resizeX = 45;
			
		}
		
		if (i == 3818) {
			entityDef.name = "Clarkeh (Valius Armor Exchange)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60610, 60615, 60611, 13307, 29616, 53092, 60620 };
			entityDef.standAnim = 2065;
			entityDef.walkAnim = 2064;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk-to", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 668, 675, 784, 815, 673};
		    entityDef.newColors = new int [] {115, 115, 115, -21612, -21612};
		}
		
		if (i == 3819) {
			entityDef.name = "Limited Time Shops";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60670 };
			entityDef.standAnim = 4424;
			entityDef.walkAnim = 4426;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk-to", null, "Trade", null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
			entityDef.description = "Items sold in his shop are only sold for a Limited amount of time. get them while you can!";
		}
		
		if (i == 3820) {
			entityDef.name = "Mini Solomon";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60670 };
			entityDef.standAnim = 4424;
			entityDef.walkAnim = 4426;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk-to", null, "Pick-up", null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 100;
			entityDef.resizeX = 100;
			entityDef.description = "ooooOOooOooOOOOOooo....";
            entityDef.onMinimap = false;
		}
		
		if (i == 3825) {
			entityDef.name = "Odin's raven";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60882 };
			entityDef.standAnim = 5021;
			entityDef.walkAnim = 5022;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
            entityDef.onMinimap = false;
            entityDef.resizeY = 40;
         	entityDef.resizeX = 40;
            entityDef.size = 1;
		}
		
		if (i == 3824) {
			entityDef.name = "Mound of dirt";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {4143};
			entityDef.standAnim = 1290;
			entityDef.walkAnim = 1289;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 200;
			entityDef.resizeX = 200;
			entityDef.size = 2;
		}
		
		if (i == 3822) {
			entityDef.name = "Lava strykewyrm";
			entityDef.combatLevel = 562;
			entityDef.models = new int[] {60835};
			entityDef.standAnim = 5070;
			entityDef.walkAnim = 5070;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
		}
		
		if (i == 3832) {
			entityDef.name = "Pet wildy strykewyrm";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {60835};
			entityDef.standAnim = 5070;
			entityDef.walkAnim = 5070;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
            entityDef.resizeY = 35;
            entityDef.resizeX = 35;
		}
		
		if (i == 3829) {
			entityDef.name = "Pet ice strykewyrm";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {60833};
			entityDef.standAnim = 5070;
			entityDef.walkAnim = 5070;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
            entityDef.resizeY = 35;
            entityDef.resizeX = 35;
		}
		
		if (i == 3830) {
			entityDef.name = "Ice strykewyrm";
			entityDef.combatLevel = 562;
			entityDef.models = new int[] {60833};
			entityDef.standAnim = 5070;
			entityDef.walkAnim = 5070;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
		}
		
		if (i == 3831) {
			entityDef.name = "Mound of dirt";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {4143};
			entityDef.standAnim = 1290;
			entityDef.walkAnim = 1289;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 200;
			entityDef.resizeX = 200;
			entityDef.size = 2;
		}
		
		if (i == 3823) {
			entityDef.name = "Dragonkin";
			entityDef.combatLevel = 360;
			entityDef.models = new int[] {60800};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
		}
		
		if (i== 3826) {
            entityDef.name = "Bad rocky";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 60920 };
            entityDef.standAnim = 7315;
            entityDef.walkAnim = 7316;
            entityDef.turn180Anim = 7316;
            entityDef.turn90ACWAnim = 7316;
            entityDef.turn90CWAnim = 7316;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.resizeY = 110;
            entityDef.resizeX = 110;
        }
		
		if (i== 3827) {
            entityDef.name = "Primal Guardian";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 60931 };
            entityDef.standAnim = 6219;
            entityDef.walkAnim = 6222;
            entityDef.turn180Anim = 6222;
            entityDef.turn90ACWAnim = 6222;
            entityDef.turn90CWAnim = 6222;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.resizeY = 40;
            entityDef.resizeX = 40;
        }
		
		if (i == 3821) {
			entityDef.name = "Star Sprite";
			entityDef.dialogueModels = new int[] {60506};
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 60506 };
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Talk to", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 120;
			entityDef.resizeX = 120;
		}
		
		if (i == 3833) {
			entityDef.name = "Nightmare";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] {60959};
			entityDef.standAnim = 7191;
			entityDef.walkAnim = 7195;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
			entityDef.size = 3;
		}
		
		if (i == 3834) {
			entityDef.name = "Pet Nightmare";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] {60959};
			entityDef.standAnim = 7191;
			entityDef.walkAnim = 7195;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pickup", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 40;
			entityDef.resizeX = 40;
			entityDef.size = 1;
		}
		
		if (i == 3835) {
			entityDef.name = "Noxious Snake";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] {61193};
			entityDef.standAnim = 3535;
			entityDef.walkAnim = 3537;
			entityDef.onMinimap = false;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pickup", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
			entityDef.size = 1;
		}
		
		if (i == 3836) {
			entityDef.name = "3rd Age Ranger";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 20136, 20130, 20145, 20162, 20126, 28622, 28483, 10344, 1 };;//replace 1 with boots id
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
		}
		
		if (i == 3837) {
			entityDef.name = "3rd Age Mage";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 13307, 20160, 20124, 20143, 20135, 20151, 28619, 10698, 28483, 1};//replace 1 with boots id
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
		}
		
		if (i == 3845) {
			entityDef.name = "3rd Age Warrior";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 13307, 20138, 20154, 20120, 20134, 20167, 28483 ,28618};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;//replace 1 with boots id
			entityDef.getDegreesToTurn = 64;
			entityDef.originalColors = new int []  { 8656, 10394 };
		    entityDef.newColors = new int [] {33030, 32844};
			entityDef.size = 1;
		}
		
		
		if (i == 3838) {
			entityDef.name = "White Dragon";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 61420, 61421, 61422};//replace 1 with white dragon id
			entityDef.standAnim = 7870;
			entityDef.walkAnim = 7870;
			entityDef.turn180Anim = 7870;
			entityDef.turn90ACWAnim = 7870;
			entityDef.turn90CWAnim = 7870;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			entityDef.resizeY = 180;
			entityDef.resizeX = 180;
		}
		
		if (i == 3839) {
			entityDef.name = "Dragon Samurai";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 1, 2};//replace 1 with dragon samurai id and 2 with katana id
			entityDef.standAnim = 7851;
			entityDef.walkAnim = 7852;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
		}
		
		if (i == 3840) {
			entityDef.name = "Zamorak";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 61464};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 70;
			entityDef.resizeX = 70;
		}
		
		if (i == 3841) {
			entityDef.name = "Odin";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 61194 };//
			entityDef.standAnim = 5831;
			entityDef.walkAnim = 5832;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 60;
			entityDef.resizeX = 60;
		}
		
		if (i == 3842) {
			entityDef.name = "Yearly Mimic";
			entityDef.combatLevel = 365;
			entityDef.models = new int[] { 61195 };
			entityDef.standAnim = 8307;
			entityDef.walkAnim = 8306;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 4;
		}
		
		if (i == 3843) {
			entityDef.name = "Pet yearly mimic";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61195 };
			entityDef.standAnim = 8307;
			entityDef.walkAnim = 8306;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}

		if (i == 3844) {
			entityDef.name = "Mini dup";
			entityDef.combatLevel = 126;
			entityDef.models = new int[] { 60926, 60934, 60928, 29250, 50100, 33102, 31766, 35371};
			entityDef.standAnim = 808;
			entityDef.walkAnim = 819;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 80;
			entityDef.resizeX = 80;
		}
		
		if (i == 3845) {
			entityDef.name = "Jack-o-Kraken";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] { 61428 };
			entityDef.standAnim = 3989;
			entityDef.walkAnim = 3989;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 4;
		}
		
		if (i == 3846) {
			entityDef.name = "Pet Jack-o-Kraken";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61428 };
			entityDef.standAnim = 3989;
			entityDef.walkAnim = 3989;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}
		
		if (i == 3847) {
			entityDef.name = "Pet Jack-o-Bat";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61434 };
			entityDef.standAnim = 5021;
			entityDef.walkAnim = 5022;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}
		
		if (i == 3848) {
			entityDef.name = "Spooky Pet Jack-o-Bat";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61432 };
			entityDef.standAnim = 5021;
			entityDef.walkAnim = 5022;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}
		
		if (i == 3849) {
			entityDef.name = "Lit Pet Jack-o-Bat";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61432 };
			entityDef.standAnim = 5021;
			entityDef.walkAnim = 5022;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}
		
		if (i == 3850) {
			entityDef.name = "Glowing Pet Jack-o-Bat";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 61432 };
			entityDef.standAnim = 5021;
			entityDef.walkAnim = 5022;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "pickup", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 20;
			entityDef.resizeX = 20;
			entityDef.onMinimap = false;
		}
		
		if (i == 3851) {
            entityDef.name = "Riley";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 61465 };
            entityDef.standAnim = 3040;
            entityDef.walkAnim = 3039;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 20;
            entityDef.resizeX = 20;
        }
		
		if (i == 3371) {
			entityDef.name = "Shadow Beast";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] { 50141 };
			entityDef.standAnim = 1678;
			entityDef.walkAnim = 1684;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 4;
			entityDef.resizeY = 300;
            entityDef.resizeX = 300;
		}
		
		if (i == 3372) {
			entityDef.name = "Revenant Lizardman";
			entityDef.combatLevel = 150;
			entityDef.models = new int[] { 50137 };
			entityDef.standAnim = 7191;
			entityDef.walkAnim = 7195;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			entityDef.resizeY = 110;
            entityDef.resizeX = 110;
			
		}
		
		if (i == 3373) {
			entityDef.name = "Revenant Wyvern";
			entityDef.combatLevel = 139;
			entityDef.models = new int[] { 50133 };
			entityDef.standAnim = 7650;
			entityDef.walkAnim = 7650;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			 entityDef.resizeY = 128;
	            entityDef.resizeX = 128;
			
		}
		
		if (i == 3374) {
			entityDef.name = "Revenant Bloodveld";
			entityDef.combatLevel = 202;
			entityDef.models = new int[] { 50135 };
			entityDef.standAnim = 1551;
			entityDef.walkAnim = 1549;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			 entityDef.resizeY = 128;
	            entityDef.resizeX = 128;
			
		}
		
		if (i == 3375) {
			entityDef.name = "Revenant Smokedevil";
			entityDef.combatLevel = 280;
			entityDef.models = new int[] { 50138 };
			entityDef.standAnim = 1829;
			entityDef.walkAnim = 1828;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			 entityDef.resizeY = 128;
	            entityDef.resizeX = 128;
			
		}
		
		if (i == 3386) {
			entityDef.name = "Revenant Abyssal Demon";
			entityDef.combatLevel = 246;
			entityDef.models = new int[] { 50134 };
			entityDef.standAnim = 1536;
			entityDef.walkAnim = 1534;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 80;
            entityDef.resizeX = 80;
			
		}
		
		if (i == 3377) {
			entityDef.name = "Revenant Dagannoth";
			entityDef.combatLevel = 280;
			entityDef.models = new int[] { 50139 };
			entityDef.standAnim = 2850;
			entityDef.walkAnim = 2849;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			entityDef.resizeY = 180;
            entityDef.resizeX = 180;
            
		}
		
		if (i == 3378) {
			entityDef.name = "Revenant Gorak";
			entityDef.combatLevel = 280;
			entityDef.models = new int[] { 50136 };
			entityDef.standAnim = 4299;
			entityDef.walkAnim = 4298;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			entityDef.resizeY = 100;
            entityDef.resizeX = 100;
            
		}
		
		if (i == 3384) {
			entityDef.name = "Revenant Impling";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 26368 };
			entityDef.standAnim = 6613;
			entityDef.walkAnim = 6614;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Catch", null, null, null, null };;
			entityDef.originalColors = new int []  { 3788, 712, 3767, 4056, 1600, 4909, 1713, 1955, 1736, 796, 908, 784 };
		    entityDef.newColors = new int [] {40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 150;
            entityDef.resizeX = 150;
            
		}
		
		if (i == 3385) {
            entityDef.name = "Pet Derwen";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 34517 };
            entityDef.standAnim = 7845;
            entityDef.walkAnim = 7847;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 50;
            entityDef.resizeX = 50;
        }
		
		if (i == 3379) {
            entityDef.name = "Pet Justiciar Zachariah";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 34511, 34510, 34512 };
            entityDef.standAnim = 7851;
            entityDef.walkAnim = 7852;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 60;
            entityDef.resizeX = 60;
        }
		
		if (i == 3380) {
            entityDef.name = "Pet Porazdir";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 34514 };
            entityDef.standAnim = 7837;
            entityDef.walkAnim = 7839;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 45;
            entityDef.resizeX = 45;
        }
		
		if (i == 3381) {
            entityDef.name = "Shadow Critter";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 50141 };
            entityDef.standAnim = 1678;
            entityDef.walkAnim = 7974;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 64;
            entityDef.resizeX = 64;
        }
		
		if (i == 3382) {
			entityDef.name = "Shadow Energy Core";
			entityDef.combatLevel = 85;
			entityDef.models = new int[] { 50115 };
			entityDef.standAnim = 1688;
			entityDef.walkAnim = 1688;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			 entityDef.resizeY = 160;
	            entityDef.resizeX = 160;
            
		}
		
		if (i == 3383) {
			entityDef.name = "Shadow Lord";
			entityDef.combatLevel = 666;
			entityDef.models = new int[] { 32930, 50122 };
			entityDef.standAnim = 6371;
			entityDef.walkAnim = 6372;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 130;
            entityDef.resizeX = 130;
            entityDef.originalColors = new int []  { 17069, 8755, 8631, 8635, 9901, 10149 };
		    entityDef.newColors = new int [] {10, 12, 14, 16, 18, 20};
            
		}
		if (i == 3389) {
            entityDef.name = "Pet s c a r e d";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 50167 };
            entityDef.standAnim = 3815;
            entityDef.walkAnim = 3819;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 70;
            entityDef.resizeX = 70;
        }
		
		if (i == 3390) {
            entityDef.name = "Dark centaur";
            entityDef.combatLevel = 50;
            entityDef.models = new int[] { 61523 };
            entityDef.standAnim = 4311;
            entityDef.walkAnim = 4310;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { null, "Attack", null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
        }
		
		if (i == 3391) {
            entityDef.name = "Pet Elder troll";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 35830 };
            entityDef.standAnim = 8154;
            entityDef.walkAnim = 8150;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 60;
            entityDef.resizeX = 60;
        }
		
		if (i == 3392) {
            entityDef.name = "Pet Mutated rat";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 26073 };
            entityDef.standAnim = 6511;
            entityDef.walkAnim = 6510;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 60;
            entityDef.resizeX = 60;
        }
		
		if (i == 3393) {
            entityDef.name = "Pet Undead warrior";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 24188, 24050 };
            entityDef.standAnim = 6113;
            entityDef.walkAnim = 6112;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 75;
            entityDef.resizeX = 75;
        }
		
		if (i == 3394) {
            entityDef.name = "Pet Shaded beast";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 37294 };
            entityDef.standAnim = 5616;
            entityDef.walkAnim = 5615;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 60;
            entityDef.resizeX = 60;
        }
		
		if (i == 3395) {
            entityDef.name = "Pet Dark centaur";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 61523 };
            entityDef.standAnim = 4311;
            entityDef.walkAnim = 4310;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 75;
            entityDef.resizeX = 75;
        }
		
		if (i == 3396) {
            entityDef.name = "The Starter Dungeon Guide";
            entityDef.combatLevel = 126;
            entityDef.models = new int[] { 61487, 61493, 61509, 61474, 61499, 61517, 50100 };
            entityDef.dialogueModels =  new int[] { 61487 } ;
            entityDef.standAnim = 6284;
            entityDef.walkAnim = 6284;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk to", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
        }
		
		if (i == 8428) {
            entityDef.name = "Elder troll";
            entityDef.combatLevel = 25;
		}
		
		if (i == 5126) {
            entityDef.name = "Mutated rat";
            entityDef.combatLevel = 10;
		}
		
		if (i == 4500) {
            entityDef.name = "Undead warrior";
            entityDef.combatLevel = 100;
		}
		
		if (i == 8709) {
            entityDef.name = "Shaded beast";
            entityDef.combatLevel = 200;
		}
		
		if (i == 3387) {
            entityDef.name = "Sunerwatt";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 50168 };
            entityDef.standAnim = 3159;
            entityDef.walkAnim = 3159;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Pick-up", null, null, null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 1;
            entityDef.onMinimap = false;
            entityDef.resizeY = 128;
            entityDef.resizeX = 128;
        }
		
		if (i == 3397) {
			entityDef.name = "Baby Dragon (Onyx)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {-22008, -22013, -22011, -22016, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
		}
		
		if (i == 3398) {
			entityDef.name = "Baby Dragon (Sapphire)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {-21585, -21590, -21595, -21570, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
		}
		
		if (i == 3405) {
			entityDef.name = "Baby Dragon (Emerald)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {22443, 22447, 22449, 22451, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
		}
		
		if (i == 3400) {
			entityDef.name = "Baby Dragon (Ruby)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {931, 935, 940, 943, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
		}
		
		if (i == 3401) {
			entityDef.name = "Baby Dragon (Diamond)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {127, 125, 123, 120, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
		}
		
		if (i == 3402) {
			entityDef.name = "Baby Dragon (Dragonstone)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {-14425, -14430, -14435, -14440, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
            
		}
		
		if (i == 3404) {
			entityDef.name = "Baby Dragon (Zenyte)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 17413, 17427, 17421, 17424 };
			entityDef.standAnim = 27;
			entityDef.walkAnim = 21;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 2;
			entityDef.resizeY = 105;
            entityDef.resizeX = 105;
            entityDef.originalColors = new int []  { 910, 912, 1938, 1814, 2588, 2469 };
		    entityDef.newColors = new int [] {5056, 5060, 5065, 5070, -27459, -27450};
		    entityDef.resizeY = 80;
            entityDef.resizeX = 80;
            
		}
		
		if (i == 3406) {
			entityDef.name = "Pet Ambassador";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50180 };
			entityDef.standAnim = 4424;
			entityDef.walkAnim = 4426;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 30;
            entityDef.resizeX = 30;
            
		}
		
		if (i == 3407) {
			entityDef.name = "Solak";
			entityDef.combatLevel = 546;
			entityDef.models = new int[] { 50204 };
			entityDef.standAnim = 5893;
			entityDef.walkAnim = 5892;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { null, "Attack", null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 3;
			entityDef.resizeY = 220;
            entityDef.resizeX = 220;
            
		}
		
		if (i == 3408) {
			entityDef.name = "Pet solak";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50204 };
			entityDef.standAnim = 5893;
			entityDef.walkAnim = 5892;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 45;
            entityDef.resizeX = 45;
            
		}
		
		if (i == 3409) {
			entityDef.name = "Valius Imp (Range)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50206 };
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
            entityDef.originalColors = new int []  { 1563, 1568, 1685, 1687, 411, 416, 1692, 420, 2711, 1811};
		    entityDef.newColors = new int [] {23449, 23444, 23442, 23440, 23438, 23436, 23434, 23432, 23430, 23428, 23426};
		    entityDef.resizeY = 140;
            entityDef.resizeX = 140;
            
            
		}
		
		if (i == 3410) {
			entityDef.name = "Valius Imp (Melee)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50206 };
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 140;
            entityDef.resizeX = 140;

            
		}
		
		if (i == 3411) {
			entityDef.name = "Valius Imp (Mage)";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50206 };
			entityDef.standAnim = 171;
			entityDef.walkAnim = 168;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };;
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
			entityDef.resizeY = 140;
            entityDef.resizeX = 140;
            entityDef.originalColors = new int []  { 1563, 1568, 1685, 1687, 411, 416, 1692, 420, 2711, 1811};
		    entityDef.newColors = new int [] {38590, 38588, 38586, 38584, 38582, 38580, 38578, 38576, 38574, 38572, 38570};
            
		}
		
		if (i == 3412 ) {
			entityDef.name = "pet jolly Rudolph";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50219, 50220 };
			entityDef.standAnim = 6374;
			entityDef.walkAnim = 6373;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
            entityDef.onMinimap = false;
			entityDef.resizeY = 70;
            entityDef.resizeX = 70;
		}



if (i == 3416 ) {
			entityDef.name = "Pet Rudolph";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50220 };
			entityDef.standAnim = 6374;
			entityDef.walkAnim = 6373;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.size = 1;
            entityDef.onMinimap = false;
			entityDef.resizeY = 70;
            entityDef.resizeX = 70;
		}


if (i == 3413) {
            entityDef.name = "Santa Claus";
            entityDef.combatLevel = 0;
            entityDef.models = new int[] { 50221 };
            entityDef.standAnim = 808;
            entityDef.walkAnim = 819;
            entityDef.onMinimap = true;
            entityDef.actions = new String[5];
            entityDef.actions = new String[] { "Talk-to", null, "Trade", null, null };
            entityDef.getDegreesToTurn = 64;
            entityDef.size = 3;
            entityDef.onMinimap = false;
        }

if (i == 3418) {
    entityDef.name = "mini Santa Claus";
    entityDef.combatLevel = 0;
    entityDef.models = new int[] { 50221 };
    entityDef.standAnim = 808;
    entityDef.walkAnim = 819;
    entityDef.onMinimap = true;
    entityDef.actions = new String[5];
    entityDef.actions = new String[] { "Pick-up", null, null, null, null };
    entityDef.getDegreesToTurn = 64;
    entityDef.size = 1;
    entityDef.onMinimap = false;
	entityDef.resizeY = 70;
    entityDef.resizeX = 70;
}

if (i == 3414) {
			entityDef.name = "Mini frosty";
			entityDef.combatLevel = 0;
			entityDef.models = new int[] { 50223 };
			entityDef.standAnim = 3324;
			entityDef.walkAnim = 3322;
			 entityDef.turn180Anim = 3322;
	            entityDef.turn90ACWAnim = 3322;
	            entityDef.turn90CWAnim = 3322;
			entityDef.onMinimap = true;
			entityDef.actions = new String[5];
			entityDef.actions = new String[] { "Pick-up", null, null, null, null };
			entityDef.getDegreesToTurn = 64;
			entityDef.resizeY = 70;
			entityDef.resizeX = 55;
		}

if (i == 3415) {
    entityDef.name = "Nick";
    entityDef.combatLevel = 0;
    entityDef.models = new int[] { 50214 };
    entityDef.standAnim = 808;
    entityDef.walkAnim = 819;
    entityDef.onMinimap = true;
    entityDef.actions = new String[5];
    entityDef.actions = new String[] { "Talk-to", null, null, null, null };
    entityDef.getDegreesToTurn = 64;
    entityDef.size = 3;
    entityDef.onMinimap = false;
    entityDef.resizeY = 110;
	entityDef.resizeX = 110;
}

		
		
		
		if (i==7413){//Chocolate Chicken (Not Pet)
			entityDef.name = "Combat dummy";
			entityDef.actions = new String[] { null, "Check Max-hit", null, null, null };
			entityDef.description = "Attack this dummy to get your max hit with any weapon!";
		}
		
		if (i == 8355) {
			entityDef.standAnim = 7988;
			entityDef.walkAnim = 7987;
		}
		if (i == 8354) {
			entityDef.standAnim = 7988;
			entityDef.walkAnim = 7987;
		}
		
		if (i==8683){//Chocolate Chicken (Not Pet)
			entityDef.actions = new String[] { null, null, null, null, null };
			entityDef.description = "You must use Eggs recieved form skilling, pvm and the Chocolate Chicken event boss on these";
		}

		if(i==7144){
	entityDef.headIcon = 0;
		}
		if(i==963){
			entityDef.headIcon = 6;
		}
		if(i==7145){
	entityDef.headIcon = 1;
		}
		if(i==7146){
	entityDef.headIcon = 2;
		}
		return entityDef;
	}

	public static int totalAmount;

	public static void unpackConfig(Archive streamLoader) {
		stream = new Buffer(streamLoader.getDataForName("npc.dat"));
		Buffer stream = new Buffer(streamLoader.getDataForName("npc.idx"));
		totalAmount = stream.readShort();
		streamIndices = new int[totalAmount];
		int i = 2;
		for (int j = 0; j < totalAmount; j++) {
			streamIndices[j] = i;
			i += stream.readShort();
		}

		cache = new NpcDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new NpcDefinition();
		for (int index = 0; index < totalAmount; index++) {
			NpcDefinition ed = forID(index);
			if (ed == null)
				continue;
			if (ed.name == null)
				continue;
		}
	}

	/*
	 * public void readValues(Stream stream) { do { int i =
	 * stream.readUnsignedByte(); if (i == 0) return; if (i == 1) { int j =
	 * stream.readUnsignedByte(); models = new int[j]; for (int j1 = 0; j1 < j;
	 * j1++) models[j1] = stream.readUnsignedWord();
	 *
	 * } else if (i == 2) name = stream.readString(); else if (i == 3) description =
	 * stream.readString(); else if (i == 12) squareLength =
	 * stream.readSignedByte(); else if (i == 13) standAnim =
	 * stream.readUnsignedWord(); else if (i == 14) walkAnim =
	 * stream.readUnsignedWord(); else if (i == 17) { walkAnim =
	 * stream.readUnsignedWord(); anInt58 = stream.readUnsignedWord(); anInt83 =
	 * stream.readUnsignedWord(); anInt55 = stream.readUnsignedWord(); if (anInt58
	 * == 65535) { anInt58 = -1; } if (anInt83 == 65535) { anInt83 = -1; } if
	 * (anInt55 == 65535) { anInt55 = -1; } } else if (i >= 30 && i < 40) { if
	 * (actions == null) actions = new String[5]; actions[i - 30] =
	 * stream.readString(); if (actions[i - 30].equalsIgnoreCase("hidden"))
	 * actions[i - 30] = null; } else if (i == 40) { int k =
	 * stream.readUnsignedByte(); originalColors = new int[k]; newColors = new
	 * int[k]; for (int k1 = 0; k1 < k; k1++) { originalColors[k1] =
	 * stream.readUnsignedWord(); newColors[k1] = stream.readUnsignedWord(); }
	 *
	 * } else if (i == 60) { int l = stream.readUnsignedByte(); dialogueModels = new
	 * int[l]; for (int l1 = 0; l1 < l; l1++) dialogueModels[l1] =
	 * stream.readUnsignedWord();
	 *
	 * } else if (i == 90) stream.readUnsignedWord(); else if (i == 91)
	 * stream.readUnsignedWord(); else if (i == 92) stream.readUnsignedWord(); else
	 * if (i == 93) minimapDot = false; else if (i == 95) combatLevel =
	 * stream.readUnsignedWord(); else if (i == 97) anInt91 =
	 * stream.readUnsignedWord(); else if (i == 98) anInt86 =
	 * stream.readUnsignedWord(); else if (i == 99) aBoolean93 = true; else if (i ==
	 * 100) anInt85 = stream.readSignedByte(); else if (i == 101) anInt92 =
	 * stream.readSignedByte() * 5; else if (i == 102) anInt75 =
	 * stream.readUnsignedByte(); else if (i == 103) getDegreesToTurn =
	 * stream.readUnsignedByte(); else if (i == 106) { anInt57 =
	 * stream.readUnsignedWord(); if (anInt57 == 65535) anInt57 = -1; anInt59 =
	 * stream.readUnsignedWord(); if (anInt59 == 65535) anInt59 = -1; int i1 =
	 * stream.readUnsignedByte(); childrenIDs = new int[i1 + 1]; for (int i2 = 0; i2
	 * <= i1; i2++) { childrenIDs[i2] = stream.readUnsignedWord(); if
	 * (childrenIDs[i2] == 65535) childrenIDs[i2] = -1; }
	 *
	 * } else if (i == 107) aBoolean84 = false; } while (true); }
	 */
	private void readValues(Buffer stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				return;
			if (opcode == 1) {
				int j = stream.readUnsignedByte();
				models = new int[j];
				for (int j1 = 0; j1 < j; j1++)
					models[j1] = stream.readShort();

			} else if (opcode == 2)
				name = stream.readString();
			else if (opcode == 3)
				description = stream.readString();
			else if (opcode == 12)
				size = stream.readSignedByte();
			else if (opcode == 13)
				standAnim = stream.readShort();
			else if (opcode == 14)
				walkAnim = stream.readShort();
			else if (opcode == 17) {
				walkAnim = stream.readShort();
				turn180Anim = stream.readShort();
				turn90CWAnim = stream.readShort();
				turn90ACWAnim = stream.readShort();
				if (turn180Anim == 65535) {
					turn180Anim = -1;
				}
				if (turn90CWAnim == 65535) {
					turn90CWAnim = -1;
				}
				if (turn90ACWAnim == 65535) {
					turn90ACWAnim = -1;
				}
			} else if (opcode >= 30 && opcode < 40) {
				if (actions == null)
					actions = new String[5];
				actions[opcode - 30] = stream.readString();
				if (actions[opcode - 30].equalsIgnoreCase("hidden"))
					actions[opcode - 30] = null;
			} else if (opcode == 40) {
				int k = stream.readUnsignedByte();
				originalColors = new int[k];
				newColors = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					originalColors[k1] = stream.readShort();
					newColors[k1] = stream.readShort();
				}
			} else if(opcode == 41) {
				int k = stream.readUnsignedByte();
				originalTextureColors = new int[k];
				modifiedTextureColors = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					originalTextureColors[k1] = stream.readShort();
					modifiedTextureColors[k1] = stream.readShort();
				}
			} else if (opcode == 60) {
				int l = stream.readUnsignedByte();
				dialogueModels = new int[l];
				for (int l1 = 0; l1 < l; l1++)
					dialogueModels[l1] = stream.readShort();

			} else if (opcode == 93)
				onMinimap = false;
			else if (opcode == 95)
				combatLevel = stream.readShort();
			else if (opcode == 97)
				resizeX = stream.readShort();
			else if (opcode == 98)
				resizeY = stream.readShort();
			else if (opcode == 99)
				hasRenderPriority = true;
			else if (opcode == 100)
				ambient = stream.readSignedByte();
			else if (opcode == 101)
				contrast = stream.readSignedByte();
			else if (opcode == 102)
				headIcon = stream.readShort();
			else if (opcode == 103)
				getDegreesToTurn = stream.readShort();
			else if (opcode == 106 || opcode == 118) {
				varbitIndex = stream.readShort();
				if (varbitIndex == 65535)
					varbitIndex = -1;
				varpIndex = stream.readShort();
				if (varpIndex == 65535)
					varpIndex = -1;
				int var3 = -1;
				if(opcode == 118)
					var3 = stream.readShort();
				int i1 = stream.readUnsignedByte();
				configs = new int[i1 + 2];
				for (int i2 = 0; i2 <= i1; i2++) {
					configs[i2] = stream.readShort();
					if (configs[i2] == 65535)
						configs[i2] = -1;
				}
				configs[i1 + 1] = var3;
			} else if (opcode == 107)
				isClickable = false;
			
			}
	}
	

	private int[] originalTextureColors;
	private int[] modifiedTextureColors;
	
	private int dialogueZoom = 128;
	private int dialogueOffsetX = 0;
	private int dialogueOffsetY = 0;

	public Model method160() {
		if (configs != null) {
			NpcDefinition entityDef = method161();
			if (entityDef == null)
				return null;
			else
				return entityDef.method160();
		}
		if (dialogueModels == null) {
			return null;
		}
		boolean flag1 = false;
		for (int i = 0; i < dialogueModels.length; i++)
			if (!Model.method463(dialogueModels[i]))
				flag1 = true;

		if (flag1)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[dialogueModels.length];
		for (int j = 0; j < dialogueModels.length; j++)
			aclass30_sub2_sub4_sub6s[j] = Model.method462(dialogueModels[j]);

		Model model;
		if (aclass30_sub2_sub4_sub6s.length == 1)
			model = aclass30_sub2_sub4_sub6s[0];
		else
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		this.applyTexturing(model, (int) this.interfaceType);
		if (originalColors != null)
			for (int k = 0; k < originalColors.length; k++)
				model.replaceColor(originalColors[k], newColors[k]);
		if(dialogueZoom != 128)
			model.method478(dialogueZoom, dialogueZoom, dialogueZoom);
		
		if(dialogueOffsetX != 0 || dialogueOffsetY != 0) {
			model.offset(dialogueOffsetX, dialogueOffsetY);
		}

		return model;
	}

	public NpcDefinition method161() {
		int j = -1;
		if (varbitIndex != -1 && varbitIndex <= 2113) {
			VarBit varBit = VarBit.cache[varbitIndex];
			int k = varBit.setting;
			int l = varBit.low;
			int i1 = varBit.high;
			int j1 = Client.BIT_MASK[i1 - l];
			j = clientInstance.variousSettings[k] >> l & j1;
		} else if (varpIndex != -1)
			j = clientInstance.variousSettings[varpIndex];
		if (j < 0 || j >= configs.length || configs[j] == -1)
			return null;
		else
			return forID(configs[j]);
	}

	public Model method164(int j, int k, int ai[]) {
		if (configs != null) {
			NpcDefinition entityDef = method161();
			if (entityDef == null)
				return null;
			else
				return entityDef.method164(j, k, ai);
		}
		Model model = (Model) mruNodes.insertFromCache(interfaceType);
		if (model == null) {
			boolean flag = false;
			for (int i1 = 0; i1 < models.length; i1++)
				if (!Model.method463(models[i1]))
					flag = true;

			if (flag)
				return null;
			Model aclass30_sub2_sub4_sub6s[] = new Model[models.length];
			for (int j1 = 0; j1 < models.length; j1++)
				aclass30_sub2_sub4_sub6s[j1] = Model.method462(models[j1]);

			if (aclass30_sub2_sub4_sub6s.length == 1)
				model = aclass30_sub2_sub4_sub6s[0];
			else
				model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
			this.applyTexturing(model, (int) this.interfaceType);
			if (originalColors != null) {
				for (int k1 = 0; k1 < originalColors.length; k1++)
					model.replaceColor(originalColors[k1], newColors[k1]);

			}
			if(modifiedTextureColors != null) {
				boolean texturesSame = Arrays.stream(modifiedTextureColors).sum() / modifiedTextureColors[0] == modifiedTextureColors.length;

				if(texturesSame)
					model.setTexture(originalTextureColors, modifiedTextureColors[0]);
				else {
					for (int l = 0; l < modifiedTextureColors.length; l++) {
						if(originalTextureColors[l] >= Rasterizer3D.textureAmount) {
							model.setTexture(originalTextureColors[l], modifiedTextureColors[l]);
						} else {
							model.overrideTexture(originalTextureColors[l], modifiedTextureColors[l]);
						}
					}
				}
			}
			model.method469();
			model.method479(64 + ambient, 850 + contrast, -30, -50, -30, true);
			// model.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
			mruNodes.removeFromCache(model, interfaceType);
		}
		Model model_1 = Model.EMPTY_MODEL;
		model_1.method464(model, Frame.method532(k) & Frame.method532(j));
		if (k != -1 && j != -1)
			model_1.method471(ai, j, k);
		else if (k != -1)
			model_1.method470(k);
		if (resizeX != 128 || resizeY != 128)
			model_1.method478(resizeX, resizeX, resizeY);
		model_1.method466();
		model_1.faceGroups = null;
		model_1.vertexGroups = null;
		if (size == 1)
			model_1.aBoolean1659 = true;
		return model_1;
	}

	private NpcDefinition() {
		turn90ACWAnim = -1;
		varbitIndex = walkAnim;
		turn180Anim = walkAnim;
		varpIndex = walkAnim;
		combatLevel = -1;
		anInt64 = 1834;
		walkAnim = -1;
		size = 1;
		headIcon = -1;
		standAnim = -1;
		interfaceType = -1L;
		getDegreesToTurn = 32;
		turn90CWAnim = -1;
		isClickable = true;
		resizeY = 128;
		onMinimap = true;
		resizeX = 128;
		hasRenderPriority = false;
	}

	public static void nullLoader() {
		mruNodes = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public static void dumpList() {
		try {
			File file = new File(System.getProperty("user.home") + "/Desktop/npcList179 " + Configuration.dumpID + ".txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < totalAmount; i++) {
					NpcDefinition definition = forID(i);
					if (definition != null) {
						writer.write("Id: " + i + "\t" + "\n"
					+ "Name: " + definition.name + "\t" + "\n"
					+ "Stand Anim: " + definition.standAnim + "\t" + "\n"
					+ "Walk Anim: " + definition.walkAnim + "\t" + "\n"
					);
						if(definition.models != null) { 
							writer.write("ModelIDs: " + definition.models.length);
							writer.write(System.getProperty("line.separator"));
							for(int range = 0; range < definition.models.length; range++) {
								writer.write("ModelID "+ range +": " + definition.models[range]);
								writer.write(System.getProperty("line.separator"));
							}
						}
						if(definition.originalColors != null) {
							writer.write("Total Original Colors: [" + definition.originalColors.length + "]");
							writer.write(System.getProperty("line.separator"));
							for(int range = 0; range < definition.originalColors.length; range++) {
								writer.write("Original Color ["+ range +"]: " + definition.originalColors[range] + ";");
								writer.write(System.getProperty("line.separator"));
							}
						}
						if(definition.newColors != null) {
							writer.write("Total New Colors: ["+definition.newColors.length + "]");
							writer.write(System.getProperty("line.separator"));
							for(int range = 0; range < definition.newColors.length; range++) {
								writer.write("New Model Color ["+range+"]: "+definition.newColors[range]);
								writer.write(System.getProperty("line.separator"));
							}
						}
						writer.newLine();
					}
				}
					
			}

			System.out.println("Finished dumping npc definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dumpcfg() {
		try {
			File file = new File(System.getProperty("user.home") + "/Desktop/npcCfg179 " + Configuration.dumpID + ".txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < totalAmount; i++) {
					NpcDefinition definition = forID(i);
					if (definition != null) {
						writer.write("npc = " + i + "\t" + definition.name + "\t" + definition.combatLevel + "\t" + "0");
						writer.newLine();
					}
				}
			}

			System.out.println("Finished dumping npc definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dumpSizes() {
		try {
			File file = new File(System.getProperty("user.home") + "/Desktop/npcSizes179.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < totalAmount; i++) {
					NpcDefinition definition = forID(i);
					if (definition != null) {
						writer.write(i + "	" + definition.size);
						writer.newLine();
					}
				}
			}

			System.out.println("Finished dumping npc definitions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public int turn90ACWAnim;
	public static int anInt56;
	public int varbitIndex;
	public int turn180Anim;
	public int varpIndex;
	public static Buffer stream;
	public int combatLevel;
	public final int anInt64;
	public String name;
	public String actions[];
	public int walkAnim;
	public byte size;
	public int[] newColors;
	public static int[] streamIndices;
	public int[] dialogueModels;
	public int headIcon;
	public int[] originalColors;
	public int standAnim;
	public long interfaceType;
	public int getDegreesToTurn;
	public static NpcDefinition[] cache;
	public static Client clientInstance;
	public int turn90CWAnim;
	public boolean isClickable;
	public int ambient;
	public int resizeY;
	public boolean onMinimap;
	public int configs[];
	public String description;
	public int resizeX;
	public int contrast;
	public boolean hasRenderPriority;
	public int[] models;
	public static Cache mruNodes = new Cache(30);
	public FloatingText floatingText;
	public FloatingSprite floatingSprite;
}