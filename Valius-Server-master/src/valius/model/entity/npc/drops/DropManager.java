package valius.model.entity.npc.drops;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import valius.content.SkillcapePerks;
import valius.content.cluescroll.ClueScrollRiddle;
import valius.content.gauntlet.TheGauntlet;
import valius.content.godwars.Godwars;
import valius.model.Location;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCDefinitions;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.skills.slayer.SlayerMaster;
import valius.model.entity.player.skills.slayer.Task;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemUtility;
import valius.util.Misc;
import valius.world.World;

public class DropManager {

	
	public static int AMOUNT_OF_TABLES = 0;

	private static final Comparator<Integer> COMPARE_NAMES =(o1, o2) -> {
		String name1 = NPCDefinitions.get(o1).getNpcName();
		String name2 = NPCDefinitions.get(o2).getNpcName();
		return name1.compareToIgnoreCase(name2);
	};

	private Map<List<Integer>, TableGroup> groups = new HashMap<>();

	private List<Integer> ordered = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public void read() {
		JSONParser parser = new JSONParser();
		try {
			fileReader = new FileReader("./Data/json/npc_droptable.json");
			JSONArray data = (JSONArray) parser.parse(fileReader);
			for (Object aData : data) {
				JSONObject drop=(JSONObject) aData;
				List<Integer> npcIds=new ArrayList<>();
				if (drop.get("npc_id") instanceof JSONArray) {
					JSONArray idArray=(JSONArray) drop.get("npc_id");
					idArray.forEach(id -> npcIds.add(((Long) id).intValue()));
				} else {
					npcIds.add(((Long) drop.get("npc_id")).intValue());
				}
				TableGroup group=new TableGroup(npcIds);
				for (TablePolicy policy : TablePolicy.POLICIES) {
					if (!drop.containsKey(policy.name().toLowerCase())) {
						continue;
					}
					JSONObject dropTable=(JSONObject) drop.get(policy.name().toLowerCase());
					Table table=new Table(policy, ((Long) dropTable.get("accessibility")).intValue());
					JSONArray tableItems=(JSONArray) dropTable.get("items");
					for (Object tableItem : tableItems) {
						JSONObject item=(JSONObject) tableItem;
						int id=((Long) item.get("item")).intValue();
						int minimumAmount=((Long) item.get("minimum")).intValue();
						int maximumAmount=((Long) item.get("maximum")).intValue();
						table.add(new Drop(npcIds, id, minimumAmount, maximumAmount));
					}
					group.add(table);
				}
				groups.put(npcIds, group);
			}
			ordered.clear();

			for (TableGroup group : groups.values()) {
				if (group.getNpcIds().size() == 1) {
					ordered.add(group.getNpcIds().get(0));
					continue;
				}
				for (int id : group.getNpcIds()) {
					String name = NPCDefinitions.get(id).getNpcName();
					if (ordered.stream().noneMatch(i -> NPCDefinitions.get(i).getNpcName().equals(name))) {
						ordered.add(id);
					}
				}
			}

			ordered.sort(COMPARE_NAMES);
			Misc.println("Loaded " + ordered.size() + " drop tables.");
			AMOUNT_OF_TABLES = ordered.size();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attempts to create a drop for a player after killing a non-playable character
	 * 
	 * @param player the player receiving a possible drop
	 * @param npc the npc dropping the items
	 */
	static boolean test = false;
	
	public void testOpen(Player player) {
		for(int i = 0; i < 100; i++) {
			player.getPA().sendFrame126("", (33008  + i));
		}
		for (int index = 0; index < ordered.size(); index++) {
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(ordered.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.getPA().showInterface(33000);
	}
	public void create(Player player, NPC npc, Location location, int repeats) {
		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npc.npcType)).findFirst();
		
		
		/*
		 * Misc Drops & Points
		 */
		int npcLevel = npc.getDefinition().getNpcCombat();		
		Points.applyPvmPoints(player, npc, location);
		Points.applyBossPoints(player, npc, npcLevel);
		OtherDrops.applyOtherDrops(player, location, npc, npcLevel);
				
		
		group.ifPresent(g -> {
			double modifier = getModifier(player);
			List<Item> drops = g.access(player, modifier, repeats);

			for (Item item : drops) {
				boolean drop = true;
				if (item.getId() == 536) {
					if (player.getRechargeItems().hasItem(13111) && player.inWild()) {
						item.changeDrop(537, item.getAmount());
					}
				}
				if (item.getId() == 6529) {
					if (player.getRechargeItems().hasItem(11136)) {
						item.changeDrop(6529, (int) (item.getAmount() * 1.20));
					}
					if (player.getRechargeItems().hasItem(11138)) {
						item.changeDrop(6529, (int) (item.getAmount() * 1.50));
					}
					if (player.getRechargeItems().hasItem(11140)) {
						item.changeDrop(6529, (int) (item.getAmount() * 1.70));
					}
					if (player.getRechargeItems().hasItem(13103)) {
						item.changeDrop(6529, (int) (item.getAmount() * 1.90));
					}
				}
				if (item.getId() == 6729 && player.getRechargeItems().hasItem(13132)) {
					item.changeDrop(6730, item.getAmount());
				}
				if (item.getId() == 13233 && !Boundary.isIn(player, Boundary.CERBERUS_BOSSROOMS)) {
					//player.sendMessage("@red@Something hot drops from the body of your vanquished foe");
				}
				if (IntStream.of(Points.bosses).anyMatch(id -> id == npc.npcType)) {
					if (player.getInstance() != null && player.getInstance() instanceof TheGauntlet) {
						return;
					}
					item.getDefinition().ifPresent(itemList -> {
						if(itemList.ShopValue > 50000) {
							PlayerHandler.nonNullStream()
							.filter(p -> p.distanceToPoint(player.getX(), player.getY()) < 10 && p.getHeight() == player.getHeight())
							.forEach(p -> {
								if (item.getAmount() > 1) {
									p.sendMessage("@red@[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " received a drop: " + Misc.format(item.getAmount()) + " x " + ItemUtility.getItemName(item.getId()) + ".");
								} else {
									p.sendMessage("@red@[Loot Bot] " + Misc.formatPlayerName(player.playerName) + " received a drop: " + ItemUtility.getItemName(item.getId()) + ".");
								}
							});
						}
					});
				}
				
				if (player.getItems().isWearingItem(33800) && Misc.random(100) == 1) {
					player.sendMessage("<col=ff7000>The power of Hazelmere blesses your " + item.getName() + ", doubling it before your very eyes!</col>");
					World.getWorld().getItemHandler().createGroundItem(player, item.getId(), location.getX(), location.getY(), location.getZ(), item.getAmount(), player.getIndex());
				}
				
				if (item.getId() == 995 && player.getItems().isWearingLuckRing()) {
					player.getItems().addItem(item.getId(), item.getAmount());
					drop = false;
				}
				
				if (drop)
					World.getWorld().getItemHandler().createGroundItem(player, item.getId(), location.getX(), location.getY(), location.getZ(), item.getAmount(), player.getIndex());
				
			}
			
			if (npc.npcType == 8028) {
				player.vorkathKillCount += 1;
				if (player.vorkathKillCount != 0 && (player.vorkathKillCount % 50) == 0) {
					player.getItems().addItemUnderAnyCircumstance(21907, 1);
					player.sendMessage("You receive Vorkaths head!");
					player.vorkathKillCount = 0;
				}
			}
			
			/**
			 * Looting bag and rune pouch
			 */
			if (npc.inWild()) {
				switch (Misc.random(60)) {
				case 2:
					if (player.getItems().getItemCount(11941, true) < 1) {
						World.getWorld().getItemHandler().createGroundItem(player, 11941, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					}
					break;
					
				case 8:
					if (player.getItems().getItemCount(12791, true) < 1) {
						World.getWorld().getItemHandler().createGroundItem(player, 12791, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					}
					break;
				}
			}
			/**
			 * Slayer's staff enchantment and Emblems
			 */
			Optional<Task> task = player.getSlayer().getTask();
			Optional<SlayerMaster> myMaster = SlayerMaster.get(player.getSlayer().getMaster());
			task.ifPresent(t -> {
			String name = npc.getDefinition().getNpcName().toLowerCase().replaceAll("_", " ");
			
				if (name.equals(t.getPrimaryName()) || ArrayUtils.contains(t.getNames(), name)) {
					myMaster.ifPresent(m -> {
						if (npc.inWild() && m.getId() == 7663) {
							int slayerChance = 650;
							int emblemChance = 100;
							if (Misc.random(emblemChance) == 1) {
								World.getWorld().getItemHandler().createGroundItem(player, 12746, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
							}
							if (Misc.random(slayerChance) == 1) {
								World.getWorld().getItemHandler().createGroundItem(player, 21257, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
							}
						}
					});
				}
			});
			
			/*
			 * PVM Caskets
			 */
			if (npc.npcType == 2790 || npc.npcType == 2805) {
				if (Misc.random(100) == 1) {
					World.getWorld().getItemHandler().createGroundItem(player, 405, location.getX(), location.getY(),location.getZ(), 1, player.getIndex());
					player.sendMessage("You have received a PVM casket drop!");
				}
			} else {
				if (Misc.random(40) == 1) {
					World.getWorld().getItemHandler().createGroundItem(player, 405, location.getX(), location.getY(),location.getZ(), 1, player.getIndex());
					player.sendMessage("You have received a PVM casket drop!");					
				}
			}
				
			/**
			 * Clue scrolls
			 */
			int chance = player.getRechargeItems().hasItem(13118) ? 142 : player.getRechargeItems().hasItem(13119) ? 135 : player.getRechargeItems().hasItem(13120) ? 120 : 150;
			if (Misc.random(chance) == 1) {
				player.sendMessage("@pur@You sense a @red@clue scroll @pur@being dropped to the ground.");
				if (npc.getDefinition().getNpcCombat() > 0 && npc.getDefinition().getNpcCombat() <= 50) {
					World.getWorld().getItemHandler().createGroundItem(player, Misc.randomElementOf(ClueScrollRiddle.EASY_CLUES), location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@You sense an Easy clue scroll being dropped to the ground.");
				} 
				if (npc.getDefinition().getNpcCombat() > 50 && npc.getDefinition().getNpcCombat() <= 100) {
					World.getWorld().getItemHandler().createGroundItem(player, Misc.randomElementOf(ClueScrollRiddle.MEDIUM_CLUES), location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@You sense a Medium clue scroll being dropped to the ground.");
				} 
				if (npc.getDefinition().getNpcCombat() > 100 && npc.getDefinition().getNpcCombat() <= 200) {
					World.getWorld().getItemHandler().createGroundItem(player, Misc.randomElementOf(ClueScrollRiddle.HARD_CLUES), location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@You sense a Hard clue scroll being dropped to the ground.");
				}
				if (npc.getDefinition().getNpcCombat() > 200 && !IntStream.of(Points.bosses).noneMatch(bossId -> npc.npcType == bossId)) {
					World.getWorld().getItemHandler().createGroundItem(player, Misc.randomElementOf(ClueScrollRiddle.ELITE_CLUES), location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					player.sendMessage("@pur@You sense an Elite clue scroll being dropped to the ground.");
				}
			}
			
			/**
			 * Master cluescroll pieces
			 */
			
			int masterPiece = Misc.random(1, 500);
			final int[] MASTER_PIECES = {19837, 19838, 19839};
			if (!IntStream.of(Points.bosses).noneMatch(bossId -> npc.npcType == bossId) && masterPiece == 5) {
				player.sendMessage("@pur@You sense a Master clue scroll piece being dropped to the ground.");
				World.getWorld().getItemHandler().createGroundItem(player, Misc.randomElementOf(MASTER_PIECES), location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				
			}
			
			
			/**
			 * Runecrafting pouches
			 
			if (Misc.random(100) == 10) {
				if (npc.getDefinition().getNpcCombat() >= 70 && npc.getDefinition().getNpcCombat() <= 100 && player.getItems().getItemCount(5509, true) == 1 && player.getItems().getItemCount(5510, true) != 1) {
					World.getWorld().getItemHandler().createGroundItem(player, 5510, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					//player.sendMessage("@pur@You sense an upgraded Runecrafting Pouch!");
				} else if (npc.getDefinition().getNpcCombat() > 100 && player.getItems().getItemCount(5510, true) == 1 && player.getItems().getItemCount(5512, true) != 1) {
					World.getWorld().getItemHandler().createGroundItem(player, 5512, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
					//player.sendMessage("@pur@You sense an upgraded Runecrafting Pouch!");
				}
			}*/

			/**
			 * Crystal keys
			 */
			int CKeyChance = Misc.random(30);
			
			if (CKeyChance == 1) {
				player.sendMessage("@pur@You sense a @red@crystal key loop @pur@being dropped to the ground.");
				World.getWorld().getItemHandler().createGroundItem(player, 987, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
			} else if (CKeyChance == 2) { 
				player.sendMessage("@pur@You sense a @red@crystal key tooth @pur@being dropped to the ground.");
				World.getWorld().getItemHandler().createGroundItem(player, 985, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
			}
			
			if (player.kbdCount == 1000) {
				player.getItems().addItemUnderAnyCircumstance(33132, 1);
				GlobalMessages.send("@red@" + player.playerName + " has received the KBD cape for 1000 kills!", GlobalMessages.MessageType.LOOT);
			}
			
			
			
			/**
			 * Ecumenical Keys
			 */
			if (Boundary.isIn(npc, Boundary.WILDERNESS_GOD_WARS_BOUNDARY)) {
				if (Misc.random(60 + 10 * player.getItems().getItemCount(Godwars.KEY_ID, true)) == 1) {
					/**
					 * Key will not drop if player owns more than 3 keys already
					 */
					int key_amount = player.getDiaryManager().getWildernessDiary().hasCompleted("ELITE") ? 6 : 3;
					if (player.getItems().getItemCount(Godwars.KEY_ID, true) > key_amount) {
						return;
					}
					World.getWorld().getItemHandler().createGroundItem(player, Godwars.KEY_ID, npc.getX(), npc.getY(), player.getHeight(), 1, player.getIndex());
					player.sendMessage("@pur@An Ecumenical Key drops from your foe.");
				}
			}
			
			/**
			 * Dark Light
			 */
			if (Boundary.isIn(npc, Boundary.CATACOMBS)) {
				if (Misc.random(1000) == 1) {
					World.getWorld().getItemHandler().createGroundItem(player, 6746, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
				}
			}
			
			/**
			 * Dark totem Pieces
			 */
			if (Boundary.isIn(npc, Boundary.CATACOMBS)) {
				switch (Misc.random(40)) {
				case 1:
					if (player.getItems().getItemCount(19679, false) < 1) {
						World.getWorld().getItemHandler().createGroundItem(player, 19679, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;
					
				case 2:
					if (player.getItems().getItemCount(19681, false) < 1) {
						World.getWorld().getItemHandler().createGroundItem(player, 19681, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;
				
				
				case 3:
					if (player.getItems().getItemCount(19683, false) < 1) {
						World.getWorld().getItemHandler().createGroundItem(player, 19683, location.getX(), location.getY(), location.getZ(), 1, player.getIndex());
						player.sendMessage("@red@A surge of dark energy fills your body as you notice something on the ground.");
					}
					break;
				}
			}
		});
	}

	private double getModifier(Player player) {
		double modifier = 1.0;		
		//+ 2% for every $50 spent passed $1000
		if(player.getRights().contains(Right.EXTREME)){
			modifier -= .05;
		} 
		if(player.getRights().contains(Right.CLASSIC) || player.getRights().contains(Right.ELITE)) {
			modifier -= .10;
		} 
		if(player.getRights().contains(Right.IRONMAN)) {
			modifier -= .03;
		} 
		if(player.getRights().contains(Right.ULTIMATE_IRONMAN)) {
			modifier -= .10;
		} 
		if(player.getRights().contains(Right.HC_IRONMAN)) {
			modifier -= .05;
		}
		if (player.getItems().isWearingAnyItem(33033, 33034, 33035, 33036, 33037, 33038, 33039, 33040, 33041, 33042, 33043, 33044, 33045, 33046, 33047,
											   33048, 33049, 33050, 33051, 33052, 33053, 33054, 33055)) {
			modifier -= .05;
		}
		if (player.getItems().isWearingAnyItem(13280, 20760, 13329, 13337, 21898, 13331, 13333, 13335, 21285, 21776, 21784, 21781)) {
			modifier -= .07;
		}
		if (player.getItems().isWearingItem(2572)) {
			modifier -= .03;
		} else if (player.getItems().isWearingItem(12785)) {
			modifier -= .05;
		} else if (player.getItems().isWearingItem(773)) {
			modifier -= 500;
		}
		if (player.getName().equals("zachtx")) {
			modifier -= .20;
		} 
		
		if (player.amDonated > 1000){
		int mod = -1;
		for (int donated = 0; donated <= (player.amDonated - 1000); donated++) {
			if ((donated % 50 == 0)) {
				mod++;
			}
			if ((player.amDonated >= 2000)) {
				 mod = 20;
			}
		}
	    modifier += 0.01 * mod;
	}	
		return modifier;
	}
	public static double getModifier1(Player player) {
		int modifier = 0;		
		if (player.getItems().isWearingItem(2572)) {
			modifier += 3;
		} else if (player.getItems().isWearingItem(12785)) {
			modifier += 15;
		} else if (player.getItems().isWearingItem(773)) {
			modifier += 500;
		} else if (player.getItems().isWearingItem(33719)) {
			modifier += 10;
		}
		if (player.getItems().isWearingItem(33798)) {//ring of fortune
			modifier += 16;
		}
		if (player.getItems().isWearingItem(33799)) {//luck of dwarves
			modifier += 17;
		}
		if (player.getItems().isWearingItem(33800)) {//hazelmeres signet
			modifier += 20;
		}
		if (player.getItems().isWearingItem(33875)) {//Occult cape
			modifier += 5;
		}
		if (player.getItems().isWearingItem(33890)) {//Starter cape
			modifier += 3;
		}
		if (player.getItems().isWearingAnyItem(21776, 21780, 21784, 33033, 33034, 33035, 33036, 33037, 33038, 33039, 33040, 33041, 33042, 33043, 33044, 33045, 33046, 33047,
				   33048, 33049, 33050, 33051, 33052, 33053, 33054, 33055)) {
			modifier += 5;
		}
		if (player.getItems().isWearingAnyItem(13280, 20760, 13329, 13337, 21898, 13331, 13333, 13335, 21285, 21776, 21784, 21781)) {
			modifier += 7;
		}
		if (player.getItems().isWearingAnyItem(33822, 33823, 33824, 33825, 33826, 33827, 33828, 33829, 33830)) {//starter dungeon armor
			modifier += 2;
		}
		if (player.getRights().contains(Right.IRONMAN)) {
			modifier += 3;
		}
		if (player.getRights().contains(Right.ULTIMATE_IRONMAN)) {
			modifier += 10;
		}
		if (player.getRights().contains(Right.HC_IRONMAN)) {
			modifier += 5;
		}
		if (player.getRights().contains(Right.EXTREME)) {
			modifier += 5;
		}
		if (player.getRights().contains(Right.CLASSIC)) {
			modifier += 10;
		}
		if (player.getRights().contains(Right.ELITE)) {
			modifier += 15;
		}
		if (player.getRights().contains(Right.GROUP_IRONMAN)) {
			modifier += 5;
		}
		if (player.summonId == 9959) {
			modifier += 15;
		}else if (player.summonId == 13326) {
			modifier += 10;
		
	} else if (player.summonId == 33907 || player.summonId == 33579 || player.summonId == 33759 || player.summonId == 33760 || player.summonId == 33766 || player.summonId == 33600 || player.summonId == 33574 || player.summonId == 33575 || player.summonId == 33439 || player.summonId == 33549 || player.summonId == 33440 || player.summonId == 33469 || player.summonId == 33428 || player.summonId == 33476 || player.summonId == 33475 ) {
		modifier += 10;
	} else if (player.summonId == 33491 || player.summonId == 33492 || player.summonId == 33493 || player.summonId == 33494 || player.summonId == 33495) {
		modifier += 2;
	} else if (player.summonId == 33930 || player.summonId == 33931 || player.summonId == 33932) {
		modifier += 5;
	} else if (player.summonId == 33900 || player.summonId == 33901 || player.summonId == 33902 || player.summonId == 33903 || player.summonId == 33904 || player.summonId == 33905 || player.summonId == 33906) {
		modifier += 6;
	}
		if (player.getRights().contains(Right.ZENYTE)) {
			modifier += 35;
		} else if (player.getRights().contains(Right.ONYX)) {
			modifier += 25;
		} else if (player.getRights().contains(Right.DRAGONSTONE)) {
			modifier += 20;
		} else if (player.getRights().contains(Right.DIAMOND)) {
			modifier += 15;
		} else if (player.getRights().contains(Right.RUBY)) {
			modifier += 10;
		} else if (player.getRights().contains(Right.EMERALD)) {
			modifier += 7;
		} else if (player.getRights().contains(Right.SAPPHIRE)) {
			modifier += 5;
		}
		if(player.amDonated > 1000){
			int mod = -1;
			for (int donated = 0; donated <= (player.amDonated - 1000); donated++) {
				if ((donated % 50 == 0)) {
					mod++;
				}
				if ((player.amDonated >= 2000)) {
					 mod = 20;
				}
			}
		    modifier += 1 * mod;
		}	
		
		return modifier;
	}
	/**
	 * Clears the interface of all parts.
	 * 
	 * Used on searching and initial load.
	 * @param player
	 */
	public void clear(Player player) {
		for(int i = 0; i < 150; i++) {
			player.getPA().sendFrame126("", 33008 + i);
		}
		
		player.getPA().sendFrame126("", 43110);
		player.getPA().sendFrame126("", 43111);
		player.getPA().sendFrame126("", 43112);
		player.getPA().sendFrame126("", 43113);
		
		for(int i = 0;i<80;i++){
			player.getPA().itemOnInterface(-1, 0, 34010+i, 0);
			player.getPA().sendString("", 34200+i);
			player.getPA().sendString("", 34300+i);
			player.getPA().sendString("", 34100+i);
			player.getPA().sendString("", 34400+i);
		}
		player.searchList.clear();
	}

	public void open2(Player player) {
		clear(player);

		for (int index = 0; index < ordered.size(); index++) {
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(ordered.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.getPA().showInterface(33000);
	}

	/**
	 * Searchers after the player inputs a npc name
	 * @param player
	 * @param name
	 */
	public void search(Player player, String name) {
		if(name.matches("^-'(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
			player.sendMessage("You may not search for alphabetical and numerical combinations.");
			return;
		}
		if (System.currentTimeMillis() - player.lastDropTableSearch < TimeUnit.SECONDS.toMillis(5)) {
			player.sendMessage("You can only do this once every 5 seconds.");
			return;
		}
		player.lastDropTableSearch = System.currentTimeMillis();
		
		clear(player);

		List<Integer> definitions = ordered.stream().filter(Objects::nonNull).filter(def -> NPCDefinitions.get(def).getNpcName() != null).filter(def -> NPCDefinitions.get(def).getNpcName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());

		if(definitions.isEmpty()) {
			definitions = ordered.stream().filter(Objects::nonNull).collect(Collectors.toList());
			List<Integer> npcs = new ArrayList<>();
			int count = 0;
			for(Integer index : definitions) {
				Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(NPCDefinitions.get(index).getNpcId())).findFirst();
				if(group.isPresent()) {
					TableGroup g = group.get();
					
					for(TablePolicy policy : TablePolicy.values()) {
						Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
						if(table.isPresent()) {
							for(Drop drop : table.get()) {
								if(drop == null) {
									continue;
								}
								
								if(ItemAssistant.getItemName(drop.getItemId()).toLowerCase().contains(name.toLowerCase())) {
									npcs.add(index);
									player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(NPCDefinitions.get(index).getNpcId()).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + count);
									count++;
								}
							}
						}
					}
				}

			}
			
			player.searchList = npcs;
			return;
			
		}
		
		for(int index = 0; index < definitions.size(); index++) {
			if(index >= 150) {
				break;
			}
			player.getPA().sendFrame126(StringUtils.capitalize(NPCDefinitions.get(definitions.get(index)).getNpcName().toLowerCase().replaceAll("_", " ")), 33008 + index);
		}

		player.searchList = definitions;
	}

	/**
	 * Loads the selected npc choosen by the player to view their drops
	 * @param player
	 * @param button
	 */
	public void select(Player player, int button) {
		int listIndex;
		
		//So the idiot client dev didn't organize the buttons in a singulatiry order. So i had to shift around the id's
		//so if you have 50 npcs in the search you can click them all fine
		if(button <= 33023) {
			listIndex = button - 33008;
		} else {
			listIndex = (33023 - 33008) + 1 + button - 33024;
		}
		if(player.searchList.isEmpty()) {
			if (listIndex < 0 || listIndex > ordered.size() - 1) {
				return;
			}
		} else {
			if (listIndex < 0 || listIndex > player.searchList.size() - 1) {
				return;
			}
		}
	

		//Finding NPC ID
		int npcId = player.searchList.isEmpty() ? ordered.get(listIndex) : player.searchList.get(listIndex);
		
		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npcId)).findFirst();

		//If the group in the search area contains this NPC
		group.ifPresent(g -> {
			if (System.currentTimeMillis() - player.lastDropTableSelected < TimeUnit.SECONDS.toMillis(5)) {
				player.sendMessage("You can only do this once every 5 seconds.");
				return;
			}

			//Loads the definition and maxhit/aggressiveness to display
			NPCDefinitions npcDef = NPCDefinitions.get(npcId);
			
			player.getPA().sendFrame126("Health: @whi@" + npcDef.getNpcHealth(), 43110);
			player.getPA().sendFrame126("Combat Level: @whi@" + npcDef.getNpcCombat(), 43111);
			if(NPCHandler.getNpc(npcId) != null){
				player.getPA().sendFrame126("Max Hit: @whi@" + NPCHandler.getNpc(npcId).maxHit, 43112);
			} else {
				player.getPA().sendFrame126("Max Hit: @whi@?", 43112);
			}
			player.getPA().sendFrame126("Aggressive: @whi@" + (World.getWorld().getNpcHandler().isAggressive(npcId, true) ? "true" : "false"), 43113);
			
			player.lastDropTableSelected = System.currentTimeMillis();
			
			double modifier = getModifier(player);
			
			//Iterates through all 5 drop table's (Found in TablePolicy -> Enum)
			for (TablePolicy policy : TablePolicy.POLICIES) {
				Optional<Table> table = g.stream().filter(t -> t.getPolicy() == policy).findFirst();
				if (table.isPresent()) {
					double chance = (1.0 /(table.get().getAccessibility() * modifier)) * 100D;
					int in_kills = (int) (100 / chance);
					if (chance > 100.0) {
						chance = 100.0;
					}
					if (in_kills == 0) {
						in_kills = 1;
					}
					
					//Updates the interface with all new information
					updateAmounts(player, policy, table.get(), in_kills);
				} else {
					updateAmounts(player, policy, new ArrayList<>(), -10);
				}
			}
			
			//If the game has displayed all drops and there are empty slots that haven't been filled, clear them
			if(player.dropSize < 80) {
				for(int i = player.dropSize;i<80;i++){
					player.getPA().sendString("", 34200+i);
					player.getPA().itemOnInterface(-1, 0, 34010+i, 0);
					player.getPA().sendString("", 34300+i);
					player.getPA().sendString("", 34100+i);
					player.getPA().sendString("", 34400+i);
				}
			}
			player.dropSize = 0;
		});
	}

	/**
	 * Updates the interface for the selected NPC
	 * @param player
	 * @param policy
	 * @param drops
	 * @param kills
	 */
	private void updateAmounts(Player player, TablePolicy policy, List<Drop> drops, int kills) {
		
		//Iterates through all drops in that catagory
		for (int index = 0; index < drops.size(); index++) {
			Drop drop = drops.get(index);
			int minimum = drop.getMinimumAmount();
			int maximum = drop.getMaximumAmount();
			int frame = (34200 + player.dropSize + index);//collumnOffset + (index * 2);
			
			//if max = min, just send the max
			if (minimum == maximum) {
				player.getPA().sendString(Misc.getValueWithoutRepresentation(drop.getMaximumAmount()), frame);
			} else {
				player.getPA().sendString(Misc.getValueWithoutRepresentation(drop.getMinimumAmount()) + " - " + Misc.getValueWithoutRepresentation(drop.getMaximumAmount()), frame);
			}
			player.getPA().itemOnInterface(drop.getItemId(), 1, 34010+player.dropSize + index, 0);
			player.getPA().sendString(Misc.optimizeText(policy.name().toLowerCase()), 34300+player.dropSize + index);
			player.getPA().sendString(World.getWorld().getItemHandler().getItemList(drop.getItemId()).itemName, 34100 + player.dropSize + index);
			if(kills == -10){
				player.getPA().sendString(1 + "/?", 34400 + player.dropSize + index);
			} else {
				player.getPA().sendString(1 + "/"+kills, 34400 + player.dropSize + index);
			}
		}
		
		player.dropSize += drops.size();
	}

	static int amountt = 0;

	private FileReader fileReader;

	/**
	 * Testing droptables of chosen npcId
	 * @param player		The player who is testing the droptable
	 * @param npcId			The npc who of which the player is testing the droptable from
	 * @param amount		The amount of times the player want to grab a drop from the npc droptable
	 */
	public void test(Player player, int npcId, int amount) {
		Optional<TableGroup> group = groups.values().stream().filter(g -> g.getNpcIds().contains(npcId)).findFirst();

		amountt = amount;

		while (amount-- > 0) {
			group.ifPresent(g -> {
				List<Item> drops = g.access(player, 1.0, 1);

				for (Item item : drops) {
					player.getItems().addItemToBank(item.getId(), item.getAmount());
				}
			});
		}
		player.sendMessage("Completed " + amountt + " drops from " + World.getWorld().getNpcHandler().getNpcName(npcId) + ".");
	}


}
