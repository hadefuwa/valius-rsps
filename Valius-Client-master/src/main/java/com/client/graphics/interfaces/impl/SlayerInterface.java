package com.client.graphics.interfaces.impl;

import com.client.cache.graphics.font.TextDrawingArea;
import com.client.graphics.interfaces.RSInterface;

public class SlayerInterface extends RSInterface
{

	public static void initializeInterfaces(TextDrawingArea[] tda) {
		unlockInterface(tda);
		extendInterface(tda);
		buyInterface(tda);
		taskInterface(tda);
		infoInterface(tda);
	}
	
	public static void infoInterface(TextDrawingArea[] tda) {
		RSInterface rsinterface = addInterface(47900);
		addSprite(47901, 0, "Interfaces/Slayer interface/Info");
		
		addHoverButton(47902, "Interfaces/Slayer interface/CLOSE", 1, 21, 21, "Close", -1, 47903, 3);
		addHoveredButton(47903, "Interfaces/Slayer interface/CLOSE", 2, 21, 21, 47904);
		
		addHoverText(47905, "Back", "Back", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(47906, "Confirm", "Confirm", tda, 0, 0xFF9900, true, true, 57);
		
		rsinterface.totalChildren(15);
		
		int y = 104;
		
		for (int i = 0; i < 10; i++) {
			addText(47907 + i, "", tda, i == 0? 1 : 0, 0xFF9900, true, true);
			
			rsinterface.child(i+5, 47907 + i, 246, y);
			y += 15;
		}
		
		rsinterface.child(0, 47901, 12, 20);
		rsinterface.child(1, 47902, 472, 27);
		rsinterface.child(2, 47903, 472, 27);
		
		rsinterface.child(3, 47905, 161, 265);
		rsinterface.child(4, 47906, 279, 265);
		
	}
	
	public static void buyInterface(TextDrawingArea[] tda) {
		RSInterface rsinterface = addInterface(47700);
		addSprite(47701, 0, "Interfaces/Slayer interface/Buy/buy");
		
		addHoverButton(47702, "Interfaces/Slayer interface/CLOSE", 1, 21, 21, "Close", -1, 47703, 3);
		addHoveredButton(47703, "Interfaces/Slayer interface/CLOSE", 2, 21, 21, 47704);
		
		addHoverText(27405, "Unlock", "Unlock", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27406, "Extend", "Extend", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27407, "Buy", "Buy", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27408, "Task", "Task", tda, 0, 0xFF9900, true, true, 57);
		
		addText(48505, "1300", tda, 0, 0xFF9900, true, true);
		
		addToItemGroup(47706, 5, 4, 60, 40, true, "Info","Buy 1","Buy 5","Buy 10", "Buy 50", "Buy 100");
		
		rsinterface.totalChildren(9);
		rsinterface.child(0, 47701, 12, 20);
		rsinterface.child(1, 47702, 472, 27);
		rsinterface.child(2, 47703, 472, 27);
		rsinterface.child(3, 48505, 470, 61);
		rsinterface.child(4, 47706, 58, 81);
		
		rsinterface.child(5, 27405, 52-15, 61);
		rsinterface.child(6, 27406, 134-12, 61);
		rsinterface.child(7, 27407, 228-19, 61);
		rsinterface.child(8, 27408, 308-14, 61);
	}
	
	public static void taskInterface(TextDrawingArea[] tda) {
		RSInterface rsinterface = addInterface(47800);
		addSprite(47801, 0, "Interfaces/Slayer interface/Task/task");
		
		addHoverButton(47802, "Interfaces/Slayer interface/CLOSE", 1, 21, 21, "Close", -1, 47803, 3);
		addHoveredButton(47803, "Interfaces/Slayer interface/CLOSE", 2, 21, 21, 47804);
		
		addHoverText(27405, "Unlock", "Unlock", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27406, "Extend", "Extend", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27407, "Buy", "Buy", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27408, "Task", "Task", tda, 0, 0xFF9900, true, true, 57);
		
		addText(48505, "1300", tda, 0, 0xFF9900, true, true);
		
		addText(48809, "Black Demons x230", tda, 1, 0xFFFFFF, true, true);
		
		addHoverText(48810, "Cancel task", "Cancel", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48811, "Block task", "Block", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48812, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48813, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48814, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48815, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48816, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(48817, "Unblock task", "Unblock task", tda, 0, 0xFF9900, true, true, 57);
		
		addText(48818, "", tda, 1, 0xFFFFFF, true, true);
		addText(48819, "", tda, 1, 0xFFFFFF, true, true);
		addText(48820, "", tda, 1, 0xFFFFFF, true, true);
		addText(48821, "", tda, 1, 0xFFFFFF, true, true);
		addText(48822, "", tda, 1, 0xFFFFFF, true, true);
		addText(48823, "", tda, 1, 0xFFFFFF, true, true);
		
		
		rsinterface.totalChildren(23);
		rsinterface.child(0, 47801, 12, 20);
		rsinterface.child(1, 47802, 472, 27);
		rsinterface.child(2, 47803, 472, 27);
		rsinterface.child(3, 48505, 470, 61);
		
		rsinterface.child(4, 27405, 52-15, 61);
		rsinterface.child(5, 27406, 134-12, 61);
		rsinterface.child(6, 27407, 228-19, 61);
		rsinterface.child(7, 27408, 308-14, 61);
		
		rsinterface.child(8, 48809, 149, 144);
		rsinterface.child(9, 48810, 310, 144);
		rsinterface.child(10, 48811, 410, 144);
		rsinterface.child(11, 48812, 384, 186);
		rsinterface.child(12, 48813, 384, 208);
		rsinterface.child(13, 48814, 384, 230);
		rsinterface.child(14, 48815, 384, 252);
		rsinterface.child(15, 48816, 384, 274);
		rsinterface.child(16, 48817, 384, 296);
		rsinterface.child(17, 48818, 254, 186);
		rsinterface.child(18, 48819, 254, 208);
		rsinterface.child(19, 48820, 254, 230);
		rsinterface.child(20, 48821, 254, 252);
		rsinterface.child(21, 48822, 254, 274);
		rsinterface.child(22, 48823, 254, 296);
	}
	
	public static void unlockInterface(TextDrawingArea[] tda) {
		RSInterface rsinterface = addInterface(47500);
		addSprite(47501, 0, "Interfaces/Slayer interface/Unlock/1/unlock");
		
		addHoverButton(47502, "Interfaces/Slayer interface/CLOSE", 1, 21, 21, "Close", -1, 47503, 3);
		addHoveredButton(47503, "Interfaces/Slayer interface/CLOSE", 2, 21, 21, 47504);
		
		addHoverText(27405, "Unlock", "Unlock", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27406, "Extend", "Extend", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27407, "Buy", "Buy", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27408, "Task", "Task", tda, 0, 0xFF9900, true, true, 57);
		
		addText(48505, "1300", tda, 0, 0xFF9900, true, true);
		
		rsinterface.totalChildren(9);
		rsinterface.child(0, 47501, 12, 20);
		rsinterface.child(1, 47502, 472, 27);
		rsinterface.child(2, 47503, 472, 27);
		rsinterface.child(3, 48505, 470, 61);
		rsinterface.child(4, 47506, 16, 76);
		
		rsinterface.child(5, 27405, 52-15, 61);
		rsinterface.child(6, 27406, 134-12, 61);
		rsinterface.child(7, 27407, 228-19, 61);
		rsinterface.child(8, 27408, 308-14, 61);
		
		RSInterface scrollInterface = addTabInterface(47506);
		scrollInterface.scrollPosition = 0;
		scrollInterface.contentType = 0;
		scrollInterface.width = 280+182;
		scrollInterface.height = 216;
		scrollInterface.scrollMax = 850;
		int x = 7, y = 11;
		int amountOfLines = 57;
		scrollInterface.totalChildren(amountOfLines);
		
		int index = 47507;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 1, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 1, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 880);
		
		scrollInterface.child(0, index-4, x, y);
		scrollInterface.child(1, index-3, x, y);
		scrollInterface.child(2, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 2, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 2, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 881);
		
		x+=227;
		
		scrollInterface.child(3, index-4, x, y);
		scrollInterface.child(4, index-3, x, y);
		scrollInterface.child(5, index-1, x+42, y+15);
		
		y+=77;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 3, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 3, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 882);
		
		scrollInterface.child(6, index-4, x, y);
		scrollInterface.child(7, index-3, x, y);
		scrollInterface.child(8, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 4, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 4, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 883);
		
		x+=227;
		
		scrollInterface.child(9, index-4, x, y);
		scrollInterface.child(10, index-3, x, y);
		scrollInterface.child(11, index-1, x+42, y+15);
		
		y+=77;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 5, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 5, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 884);
		
		scrollInterface.child(12, index-4, x, y);
		scrollInterface.child(13, index-3, x, y);
		scrollInterface.child(14, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 6, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 6, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 885);
		
		x+=227;
		
		scrollInterface.child(15, index-4, x, y);
		scrollInterface.child(16, index-3, x, y);
		scrollInterface.child(17, index-1, x+42, y+15);
		
		y+=87;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 7, 224, 64, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 7, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 886);
		
		scrollInterface.child(18, index-4, x, y);
		scrollInterface.child(19, index-3, x, y);
		scrollInterface.child(20, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 8, 224, 64, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 8, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 887);
		
		x+=227;
		
		scrollInterface.child(21, index-4, x, y);
		scrollInterface.child(22, index-3, x, y);
		scrollInterface.child(23, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 9, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 9, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 888);
		
		scrollInterface.child(24, index-4, x, y);
		scrollInterface.child(25, index-3, x, y);
		scrollInterface.child(26, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 10, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 10, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 889);
		
		x+=227;
		
		scrollInterface.child(27, index-4, x, y);
		scrollInterface.child(28, index-3, x, y);
		scrollInterface.child(29, index-1, x+42, y+15);
		
		y+=77;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 11, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 11, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 890);
		
		scrollInterface.child(30, index-4, x, y);
		scrollInterface.child(31, index-3, x, y);
		scrollInterface.child(32, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 12, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 12, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 891);
		
		x+=227;
		
		scrollInterface.child(33, index-4, x, y);
		scrollInterface.child(34, index-3, x, y);
		scrollInterface.child(35, index-1, x+42, y+15);
		
		y+=87;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 13, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 13, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 892);
		
		scrollInterface.child(36, index-4, x, y);
		scrollInterface.child(37, index-3, x, y);
		scrollInterface.child(38, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 14, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 14, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 893);
		
		x+=227;
		
		scrollInterface.child(39, index-4, x, y);
		scrollInterface.child(40, index-3, x, y);
		scrollInterface.child(41, index-1, x+42, y+15);
		
		y+=87;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 15, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 15, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 894);
		
		scrollInterface.child(42, index-4, x, y);
		scrollInterface.child(43, index-3, x, y);
		scrollInterface.child(44, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 16, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 16, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 895);
		
		x+=227;
		
		scrollInterface.child(45, index-4, x, y);
		scrollInterface.child(46, index-3, x, y);
		scrollInterface.child(47, index-1, x+42, y+15);
		
		y+=77;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 17, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 17, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 896);
		
		scrollInterface.child(48, index-4, x, y);
		scrollInterface.child(49, index-3, x, y);
		scrollInterface.child(50, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 18, 224, 84, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 18, 224, 84, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 897);
		
		x+=227;
		
		scrollInterface.child(51, index-4, x, y);
		scrollInterface.child(52, index-3, x, y);
		scrollInterface.child(53, index-1, x+42, y+15);
		
		y+=87;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Unlock/1/unlock", 19, 224, 74, "Unlock", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Unlock/2/unlock", 19, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 898);
		
		scrollInterface.child(54, index-4, x, y);
		scrollInterface.child(55, index-3, x, y);
		scrollInterface.child(56, index-1, x+42, y+15);
	}
	
	public static void extendInterface(TextDrawingArea[] tda) {
		RSInterface rsinterface = addInterface(47600);
		addSprite(47601, 0, "Interfaces/Slayer interface/Extend/1/extend");
		
		addHoverButton(47602, "Interfaces/Slayer interface/CLOSE", 1, 21, 21, "Close", -1, 47603, 3);
		addHoveredButton(47603, "Interfaces/Slayer interface/CLOSE", 2, 21, 21, 47604);
		
		addHoverText(27405, "Unlock", "Unlock", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27406, "Extend", "Extend", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27407, "Buy", "Buy", tda, 0, 0xFF9900, true, true, 57);
		addHoverText(27408, "Task", "Task", tda, 0, 0xFF9900, true, true, 57);
		
		addText(48505, "1300", tda, 0, 0xFF9900, true, true);
		
		rsinterface.totalChildren(9);
		rsinterface.child(0, 47601, 12, 20);
		rsinterface.child(1, 47602, 472, 27);
		rsinterface.child(2, 47603, 472, 27);
		rsinterface.child(3, 48505, 470, 61);
		rsinterface.child(4, 47606, 16, 76);
		
		rsinterface.child(5, 27405, 52-15, 61);
		rsinterface.child(6, 27406, 134-12, 61);
		rsinterface.child(7, 27407, 228-19, 61);
		rsinterface.child(8, 27408, 308-14, 61);
		
		RSInterface scrollInterface = addTabInterface(47606);
		scrollInterface.scrollPosition = 0;
		scrollInterface.contentType = 0;
		scrollInterface.width = 280+182;
		scrollInterface.height = 216;
		scrollInterface.scrollMax = 770;
		int x = 7, y = 11;
		int amountOfLines = 63;
		scrollInterface.totalChildren(amountOfLines);
		
		int index = 47607;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 1, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 1, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 899);
		
		scrollInterface.child(0, index-4, x, y);
		scrollInterface.child(1, index-3, x, y);
		scrollInterface.child(2, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 2, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 2, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 900);
		
		x+=227;
		
		scrollInterface.child(3, index-4, x, y);
		scrollInterface.child(4, index-3, x, y);
		scrollInterface.child(5, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 3, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 3, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 901);
		
		scrollInterface.child(6, index-4, x, y);
		scrollInterface.child(7, index-3, x, y);
		scrollInterface.child(8, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 4, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 4, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 902);
		
		x+=227;
		
		scrollInterface.child(9, index-4, x, y);
		scrollInterface.child(10, index-3, x, y);
		scrollInterface.child(11, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 5, 224, 74, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 5, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 903);
		
		scrollInterface.child(12, index-4, x, y);
		scrollInterface.child(13, index-3, x, y);
		scrollInterface.child(14, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 6, 224, 74, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 6, 224, 74, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 904);
		
		x+=227;
		
		scrollInterface.child(15, index-4, x, y);
		scrollInterface.child(16, index-3, x, y);
		scrollInterface.child(17, index-1, x+42, y+15);
		
		y+=77;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 7, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 7, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 905);
		
		scrollInterface.child(18, index-4, x, y);
		scrollInterface.child(19, index-3, x, y);
		scrollInterface.child(20, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 8, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 8, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 906);
		
		x+=227;
		
		scrollInterface.child(21, index-4, x, y);
		scrollInterface.child(22, index-3, x, y);
		scrollInterface.child(23, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 9, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 9, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 907);
		
		scrollInterface.child(24, index-4, x, y);
		scrollInterface.child(25, index-3, x, y);
		scrollInterface.child(26, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 10, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 10, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 908);
		
		x+=227;
		
		scrollInterface.child(27, index-4, x, y);
		scrollInterface.child(28, index-3, x, y);
		scrollInterface.child(29, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 11, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 11, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 909);
		
		scrollInterface.child(30, index-4, x, y);
		scrollInterface.child(31, index-3, x, y);
		scrollInterface.child(32, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 12, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 12, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 910);
		
		x+=227;
		
		scrollInterface.child(33, index-4, x, y);
		scrollInterface.child(34, index-3, x, y);
		scrollInterface.child(35, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 13, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 13, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 911);
		
		scrollInterface.child(36, index-4, x, y);
		scrollInterface.child(37, index-3, x, y);
		scrollInterface.child(38, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 14, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 14, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 912);
		
		x+=227;
		
		scrollInterface.child(39, index-4, x, y);
		scrollInterface.child(40, index-3, x, y);
		scrollInterface.child(41, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 15, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 15, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 913);
		
		scrollInterface.child(42, index-4, x, y);
		scrollInterface.child(43, index-3, x, y);
		scrollInterface.child(44, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 16, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 16, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 914);
		
		x+=227;
		
		scrollInterface.child(45, index-4, x, y);
		scrollInterface.child(46, index-3, x, y);
		scrollInterface.child(47, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 17, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 17, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 915);
		
		scrollInterface.child(48, index-4, x, y);
		scrollInterface.child(49, index-3, x, y);
		scrollInterface.child(50, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 18, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 18, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 916);
		
		x+=227;
		
		scrollInterface.child(51, index-4, x, y);
		scrollInterface.child(52, index-3, x, y);
		scrollInterface.child(53, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 19, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 19, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 917);
		
		scrollInterface.child(54, index-4, x, y);
		scrollInterface.child(55, index-3, x, y);
		scrollInterface.child(56, index-1, x+42, y+15);
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 20, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 20, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 918);
		
		x+=227;
		
		scrollInterface.child(57, index-4, x, y);
		scrollInterface.child(58, index-3, x, y);
		scrollInterface.child(59, index-1, x+42, y+15);
		
		y+=67;
		x=7;
		
		addHoverButton(index++, "Interfaces/Slayer interface/Extend/1/extend", 21, 224, 64, "Extend", 0, index, 1);
		addHoveredButton(index++, "Interfaces/Slayer interface/Extend/2/extend", 21, 224, 64, index++);
		addSprite(index++, 1, 2, "Interfaces/Slayer interface/check", 1, 919);
		
		scrollInterface.child(60, index-4, x, y);
		scrollInterface.child(61, index-3, x, y);
		scrollInterface.child(62, index-1, x+42, y+15);
		
	}
	
}