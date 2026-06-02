package valius.clip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import valius.util.Buffer;


public final class ObjectDef {

	public static ObjectDef forID(int i) {
		
		if (i == -1) {
			return null;
		}
		
		if (i > streamIndices.length)
			i = streamIndices.length - 2;

		if (i == 25913 || i == 25916 || i == 25917)
			i = 15552;

		for (int j = 0; j < 20; j++)
			if (cache[j].type == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef objectDef = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		objectDef.setDefaults();
		objectDef.readValues(stream);
		objectDef.type = i;
		if (i >= 26281 && i <= 26290) {
			objectDef.actions = new String[] { "Choose", null, null, null, null };
		}
		switch (i) {
		case 21309:
		case 21306:
			objectDef.actions = new String[] { null, null, null, null, null };
			break;

		case 8207:
			objectDef.actions = new String[] { "Care-To", null, null, null, null };
			objectDef.name = "Herb Patch";
			break;
		case 22472:
			objectDef.actions = new String[] { "Daily Rewards", "Drop Tables", "Achievements", null, null };
			objectDef.name = "Valius Database";
			break;
			case 26811:
				objectDef.actions = new String[] { "Open", null, null, null, null };
				objectDef.name = "Vote Shop";

				break;
		case 8210:
			objectDef.actions = new String[] { "Rake", null, null, null, null };
			objectDef.name = "Herb Patch";
			break;
		case 29150:
			objectDef.actions = new String[] { "Venerate", null, null, null, null };
			break;
		case 11869:
			objectDef.actions = new String[] { "Pick-up", null, null, null, null };
			objectDef.name = "Dwarf Multicannon (collapsed)";
			objectDef.obstructsGround = false;
			objectDef.impenetrable = false;
			objectDef.hollow = true;
			break;
		case 29709://Santa shop
			objectDef.actions = new String[] { "Exchange", null, null, null, null };
			objectDef.length = 2;
			objectDef.width = 2;
			break;
		case 14832://varrock rooftop wall jump
			objectDef.length = 3;
			objectDef.width = 3;
			break;
		case 15609://ardy jump gap
		case 15610://ardy gap 2
		case 15611:
		case 15612:
			objectDef.length = 3;
			objectDef.width = 3;
			break;
		case 34773:
			objectDef.length = 3;
			objectDef.width = 3;
			break;
		case 29335://nightmare chest
			objectDef.length = 3;
			objectDef.width = 3;
			break;
		case 17068://lumby below graveyard shortcut
			objectDef.length = 6;
			objectDef.width = 6;
			break;
		case 27288://Hween shop chest
			objectDef.actions = new String[] { "Open", null, null, null, null };
			objectDef.name = "Halloween Chest";
			break;
		case 31621:
		case 33393:
			objectDef.actions = new String[] { "Teleport", "Previous", null, null, null };
			objectDef.name = "Valius Teleporter";

			objectDef.modelIds[0] = 36053;
			objectDef.hasActions = true;
			objectDef.castsShadow = false;
			break;
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
		case 23319:
			objectDef.width = 1;
			objectDef.length = 1;
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
		case 24101:
			objectDef.actions[2] = "Trading Post";
			break;
		case 20951:
		case 20950:
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
			
		case 29338:
			objectDef.name = "Dark Altar";
			objectDef.actions = new String[] { "Summon", null, null, null, null };
			break;
			
		case 29337:
			objectDef.name = "Wilderness chest";
			objectDef.actions = new String[] { "Unlock", null, null, null, null };
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
			break;
		case 33114:
			objectDef.name = "Event Boss Chest";
			objectDef.actions = new String[] {"Open", null, null, null, null};
			break;
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

	
		}
		
		return objectDef;
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
	}
	public static int totalObjects;

	public static void unpackConfig() throws IOException {
		stream = new Buffer(Files.readAllBytes(new File("./data/world/object/loc.dat").toPath()));
		Buffer stream = new Buffer(Files.readAllBytes(new File("./data/world/object/loc.idx").toPath()));
		totalObjects = stream.readUnsignedWord();
		streamIndices = new int[totalObjects];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}
		cache = new ObjectDef[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new ObjectDef();
		//dumpList();
	}


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
							modelIds[k1] = stream.readUnsignedWord();
							modelTypes[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.currentOffset += len * 3;
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
							modelIds[l1] = stream.readUnsignedWord();
					} else {
						stream.currentOffset += len * 2;
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
				animation = stream.readUnsignedWord();
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
					modifiedModelColors[i2] = stream.readUnsignedWord();
					originalModelColors[i2] = stream.readUnsignedWord();
				}
			} else if (type == 41) {
				int i1 = stream.readUnsignedByte();
				originalTexture = new short[i1];
				modifiedTexture = new short[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					originalTexture[i2] = (short) stream.readUnsignedWord();
					modifiedTexture[i2] = (short) stream.readUnsignedWord();
				}
			} else if (type == 60)
				mapFunction = stream.readUnsignedWord();
			else if (type == 62)
				inverted = true;
			else if (type == 64)
				castsShadow = false;
			else if (type == 65)
				scaleX = stream.readUnsignedWord();
			else if (type == 66)
				scaleY = stream.readUnsignedWord();
			else if (type == 67)
				scaleZ = stream.readUnsignedWord();
			else if (type == 68)
				mapscene = stream.readUnsignedWord();
			else if (type == 69)
				surroundings = stream.readUnsignedByte();
			else if (type == 70)
				translateX = stream.readSignedWord();
			else if (type == 71)
				translateY = stream.readSignedWord();
			else if (type == 72)
				translateZ = stream.readSignedWord();
			else if (type == 73)
				obstructsGround = true;
			else if (type == 74)
				hollow = true;
			else if (type == 75)
				supportItems = stream.readUnsignedByte();
			else if (type == 77 || type == 92) {
				varbit = stream.readUnsignedWord();
				if (varbit == 65535)
					varbit = -1;
				
				varp = stream.readUnsignedWord();
				if (varp == 65535)
					varp = -1;
				
				int var3 = -1;
				if(type == 92) {
					var3 = stream.readUnsignedWord();
					if(var3 == 65535)
						var3 = -1;
				}
				
				
				int count = stream.readUnsignedByte();
				morphisms = new int[count + 2];
				for (int j2 = 0; j2 <= count; j2++) {
					morphisms[j2] = stream.readUnsignedWord();
					if (morphisms[j2] == 65535)
						morphisms[j2] = -1;
				}
				morphisms[count + 1] = var3;
				
			} else if(type == 78) {//TODO Figure out what these do in OSRS
				//First short = ambient sound
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			} else if(type == 79) {
				stream.currentOffset += 5;
				int len = stream.readSignedByte();
				stream.currentOffset += (len * 2);
			} else if(type == 81) {
				stream.readUnsignedByte();
			} else if(type == 82) {
				stream.readUnsignedWord();
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

	private ObjectDef() {
		type = -1;
	}

	private short[] originalTexture;
	private short[] modifiedTexture;
	public boolean obstructsGround;
	private byte lightDiffusion;
	private byte ambientLighting;
	private int translateX;
	public String name;
	private int scaleZ;
	public int width;
	private int translateY;
	public int mapFunction;
	private int[] originalModelColors;
	private int scaleX;
	public int varp;
	private boolean inverted;
	public static boolean lowMem;
	private static Buffer stream;
	public int type;
	public static int[] streamIndices;
	public boolean impenetrable;
	public int mapscene;
	public int morphisms[];
	public int supportItems;
	public int length;
	public boolean contouredGround;
	public boolean occludes;
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
	public int animation;
	private static ObjectDef[] cache;
	private int translateZ;
	private int[] modifiedModelColors;
	public String actions[];
}
