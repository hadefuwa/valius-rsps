package valius.net.packet.impl.itemoptions;

import static valius.content.DiceHandler.DICING_AREA;

import java.util.Objects;
import java.util.Optional;

import valius.content.ChamberOfXericBox;
import valius.content.DiceHandler;
import valius.content.Packs;
import valius.content.achievement.AchievementType;
import valius.content.achievement.Achievements;
import valius.content.barrows.Barrows;
import valius.content.cannon.DwarfCannon;
import valius.content.cluescroll.ClueScrollHandler;
import valius.content.cluescroll.ClueScrollRiddle;
import valius.content.teleportation.TeleportTablets;
import valius.content.trails.MasterClue;
import valius.content.trails.RewardLevel;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.magic.NonCombatSpells;
import valius.model.entity.player.dialogue.StarSprite;
import valius.model.entity.player.dialogue.ValiusImp;
import valius.model.entity.player.skills.agility.AgilityHandler;
import valius.model.entity.player.skills.hunter.Hunter;
import valius.model.entity.player.skills.hunter.impling.Impling;
import valius.model.entity.player.skills.hunter.trap.impl.BirdSnare;
import valius.model.entity.player.skills.hunter.trap.impl.BoxTrap;
import valius.model.entity.player.skills.prayer.Bone;
import valius.model.entity.player.skills.prayer.Prayer;
import valius.model.entity.player.skills.runecrafting.Pouches;
import valius.model.entity.player.skills.runecrafting.Pouches.Pouch;
import valius.model.item.container.LootingBag;
import valius.model.item.container.RunePouch;
import valius.model.items.BoxSets;
import valius.model.items.Item;
import valius.model.minigames.raids.Raids;
import valius.model.minigames.theatre.TheatreConstants;
import valius.model.minigames.theatre.TheatreObjects;
import valius.model.minigames.xeric.XericRewards;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.duel.DuelSessionRules.Rule;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ItemOptionOne implements PacketType {
	/**
	 * Infernal Key pieces
	 */
    private int[] keyPieces = {33150, 33151, 33152};
    
    /**
     * The last planted flower
     */
    private int lastPlantedFlower;

    /**
     /**
     * If the entity is in the midst of planting mithril seeds
     */
    public static boolean plantingMithrilSeeds = false;

    /**
     * The position the mithril seed was planted
     */
    private static final int[] position = new int[2];

    /**
     * Chances of getting to array flower1
     */
    private int flowerChance = Misc.random(100);


    /**
     * Last plant for the timing of the flower
     */
    private long lastPlant = 0;
    /**
     * Array containing flower object ids without black and white flowers
     */
    private int flower[] = {
            2980, 2981, 2982,
            2983, 2984, 2985,
            2986
    };

    /**
     * Array containing flower object ids along with black and white flowers
     */
    private int flower1[] = {
            2980, 2981, 2982,
            2983, 2984, 2985,
            2986, 2987, 2988
    };

    /**
     * Draws a random flower object from the flower arrays
     * @return
     * 			the flower object chosen
     */
    private int randomFlower() {
        if (flowerChance > 29) {
            return flower[(int) (Math.random() * flower.length)];
        }
        return flower[(int) (Math.random() * flower1.length)];
    }

    public void plantMithrilSeed(Player c) {
        position[0] = c.getX();
        position[1] = c.getY();
        lastPlantedFlower = randomFlower();
        GlobalObject object1 = new GlobalObject(lastPlantedFlower, position[0], position[1], c.getHeight(), 3, 10, 120, -1);
        World.getWorld().getGlobalObjects().add(object1);
        c.getPA().walkTo(1, 0);
        c.turnPlayerTo(position[0] -1, position[1]);
        c.sendMessage("You planted a flower!");
        plantingMithrilSeeds = true;
        c.getItems().deleteItem(299, c.getItems().getItemSlot(299), 1);
    }
    public static void plantMithrilSeedRigged(Player c,int flowerId) {
        position[0] = c.getX();
        position[1] = c.getY();
        GlobalObject object1 = new GlobalObject(flowerId, position[0], position[1], c.getHeight(), 3, 10, 120, -1);
        World.getWorld().getGlobalObjects().add(object1);
        c.getPA().walkTo(1, 0);
        c.turnPlayerTo(position[0] -1, position[1]);
        c.sendMessage("You planted a flower!");
        plantingMithrilSeeds = true;
    }
    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        int interfaceId = c.getInStream().readSignedWordBigEndianA();
        int itemSlot = c.getInStream().readSignedWordA();
        int itemId = c.getInStream().readSignedWordBigEndian() & 0xFFFF;
        if (itemSlot >= c.playerItems.length || itemSlot < 0) {
            return;
        }
        if (itemId != c.playerItems[itemSlot] - 1) {
            return;
        }
        if (c.isDead || c.getHealth().getAmount() <= 0) {
            return;
        }
        if (World.getWorld().getHolidayController().clickItem(c, itemId)) {
            return;
        }
        if(c.debugMessage) {
        	c.sendMessage("Item option 1: " + itemId + " slot: " + itemSlot);
        }
        if (c.getInterfaceEvent().isActive()) {
            c.sendMessage("Please finish what you're doing.");
            return;
        }
        if (c.getBankPin().requiresUnlock()) {
            c.getBankPin().open(2);
            return;
        }
        if (c.getTutorial().isActive()) {
            c.getTutorial().refresh();
            return;
        }
		Item item = c.getItems().getItemAtSlot(itemSlot);
		if (c.getInstance() != null) {
			if (c.getInstance().clickItem(c, item))
				return;
		}
		c.getQuestManager().onItemClick(interfaceId, 1, item);
        c.lastClickedItem = itemId;
        c.getHerblore().clean(itemId);
        if (c.getFood().isFood(itemId)) {
            c.getFood().eat(itemId, itemSlot);
        } else if (c.getPotions().isPotion(itemId)) {
            c.getPotions().handlePotion(itemId, itemSlot);
        }
        
        if (ClueScrollHandler.readClue(c, item))
        	return;
        
        Optional<Bone> bone = Prayer.isOperableBone(itemId);
        if (bone.isPresent()) {
            c.getPrayer().bury(bone.get());
            return;
        }
        TeleportTablets.operate(c, itemId);
        Packs.openPack(c, itemId);
        if (LootingBag.isLootingBag(c, itemId)) {
            c.getLootingBag().openWithdrawalMode();
            return;
        }
        if (RunePouch.isRunePouch(c, itemId)) {
            c.getRunePouch().openRunePouch();
            return;
        }
        switch (itemId) {
	    	case DwarfCannon.CANNON_BASE:
	    		c.cannon.placeCannon();
        	break;
            case 2697:
                if (c.getItems().playerHasItem(2697, 1)) {
                    c.getItems().deleteItem(2697, 1);
                    c.gfx100(263);
                    c.amDonated += 10;
                    c.sendMessage("Thank you for donating! 10$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
                    return;
                }
                break;
            case 299:
                if(!Boundary.isIn(c, DICING_AREA)) {
                    c.sendMessage("You must teleport to ::dice to use these! *Gamble responsibly!*");
                    return;
                }
                if(System.currentTimeMillis() - c.lastPlant < 250) {
                    return;
                }
                c.lastPlant = System.currentTimeMillis();
                plantMithrilSeed(c);
                break;
            case 2698:
                if (c.getItems().playerHasItem(2698, 1)) {
                    c.getItems().deleteItem(2698, 1);
                    c.gfx100(263);
                    c.amDonated += 50;
                    c.sendMessage("Thank you for donating! 50$ has been added to your total credit.");
                    c.updateRank();
                    c.getPA().closeAllWindows();
                    return;
                }
            case 2699:
                if (c.getItems().playerHasItem(2699, 1)) {
                    c.getItems().deleteItem(2699, 1);
                    c.gfx100(263);
                    c.amDonated += 100;
                    c.sendMessage("Thank you for donating! 100$ has been added to your total credit.");
                    c.sendMessage("You can also use the ::bank command now!");
                    c.updateRank();
                    c.getPA().closeAllWindows();
                    return;
                }
                break;
                
            case 33930:
            case 33931:
            case 33932:
            	ValiusImp.startDialogue(c, 3410);
            	break;
            	
            case 2700:
                if (c.getItems().playerHasItem(2700, 1)) {
                    c.getItems().deleteItem(2700, 1);
                    c.gfx100(263);
                    c.amDonated += 5;
                    c.sendMessage("Thank you for donating! 5$ has been added to your total credit.");
                    c.sendMessage("You can now visit the donator zone by typing ::dz!");
                    c.updateRank();
                    c.getPA().closeAllWindows();
                }
                return;
            case 13346:
                if (c.getItems().playerHasItem(13346)) {
                    c.getUltraMysteryBox().openInterface();
                    return;
                }
                break;
                
            case 13066:
            	c.getItems().deleteItem(13066, 1);
            	BoxSets.SUPER_SET.openBox(c);
            	c.sendMessage("You open your pack and recieve 10x Super Attack, Strength and Defence potions.");
            	break;
                
           case 33269:
                if (c.getItems().playerHasItem(33269)) {
                    c.getValiusMysteryBox().openInterface();
                    return;
                }
                break;
                
           case 33941:
        	   if (c.getItems().playerHasItem(33941)) {
        			  c.getChamberOfXericBox().open();
        			  c.sendMessage("You receive a random item from the Chamber of Xeric drop table.");
    		   }
        	   break;
           case 33943:
        	   c.theatrePoints = 500;
        	   c.getItems().deleteItem(33943, 1);
        	   TheatreConstants.giveLoot(c);
 			  c.sendMessage("You receive a random item from the Theatre of Blood drop table.");
                break;
                
           case 33942:
        	   XericRewards.giveReward(5000, c);
        	   c.getItems().deleteItem(33942, 1);
  			  c.sendMessage("You receive a random item from the Trials of Xeric drop table.");
        	   break; 
        	   
           case 19837:
           case 19838:
           case 19839:
        	   if (c.getItems().playerHasAllItems(19837, 19838, 19839)) {
        		   c.getItems().deleteItem(19837,  1);
        		   c.getItems().deleteItem(19838, 1);
        		   c.getItems().deleteItem(19839, 1);
        		   c.getItems().addItem(Misc.randomElementOf(ClueScrollRiddle.MASTER_CLUES), 1);
        		   c.sendMessage("You combine your torn scroll pieces to create a Clue scroll (Master).");
        	   } else {
        		   c.sendMessage("You will need all 3 torn clue scroll pieces to create a Clue Scroll (Master).");
        	   }
        	   break;
                
           case 33668://blood mbox
        	   if (c.getItems().playerHasItem(33668)) {
        		   c.getBloodMysteryBox().openInterface();
        		   return;
        	   }
                break;
                
           case 33717://event mbox
        	   if (c.getItems().playerHasItem(33717)) {
        		   c.getEventMysteryBox().openInterface();
        		   return;
        	   }
                break;
                
           case 33669://pet mbox
        	   if (c.getItems().playerHasItem(33669)) {
        		   c.getPetMysteryBox().openInterface();
        		   return;
        	   }
        	   break;
        	   
           case 33285:
               if (c.getItems().playerHasItem(33285)) {
                   c.getEasterMysteryBox().openInterface();
                   return;
               }
               break; 
               
           case 33961:
               if (c.getItems().playerHasItem(33961)) {
                   c.getChristmasBox().openInterface();
                   return;
               }
               break; 
               
           case 33756:
        	   if (c.getItems().playerHasItem(33756)) {
        		   c.getHalloweenMysteryBox().openInterface();
        		   return;
        	   }
                
           case 33266:
               if (c.getItems().playerHasItem(33266)) {
                   c.getValentinesBox().openInterface();
                   return;
               }
               break;
               
           case 33422:
        	   if (c.getItems().playerHasItem(33422)) {
        		   c.getStarBox().openInterface();
        		   return;
        	   }
                
            case 21347:
                c.boltTips = true;
                c.arrowTips = false;
                c.javelinHeads = false;
                c.sendMessage("Your Amethyst method is now Bolt Tips!");
                break;
                
                
    		case 33773:
    			Impling.getReward(c, itemId);
    			break;
         
                
                //Bonus XP Scrolls
                //25% boost
                
                //TODO Create a class & use an enum
                //itemId, time, xpPercent
            case 33442://10 Mins
            	if (c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33442, 1)) {
            		c.getItems().deleteItem(33442, 1);
            		c.bonusXpTime25 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime25 / 100 + " Minutes</col> of @blu@25%</col> Bonus XP left.");
            	break;
            case 33443://30 Mins
            	if (c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33443, 1)) {
            		c.getItems().deleteItem(33443, 1);
            		c.bonusXpTime25 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime25 / 100 + " Minutes</col> of @blu@25%</col> Bonus XP left.");
            	break;
            case 33444://60 Mins
            	if (c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33444, 1)) {
            		c.getItems().deleteItem(33444, 1);
            		c.bonusXpTime25 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime25 / 100 + " Minutes</col> of @blu@25%</col> Bonus XP left.");
            	break;
            case 33445://120 Mins
            	if (c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33445, 1)) {
            		c.getItems().deleteItem(33445, 1);
            		c.bonusXpTime25 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime25 / 100 + " Minutes</col> of @blu@25%</col> Bonus XP left.");
            	break;
            	//50% boost
            case 33446://10 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33446, 1)) {
            		c.getItems().deleteItem(33446, 1);
            		c.bonusXpTime50 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime50 / 100 + " Minutes</col> of @blu@50%</col> Bonus XP left.");
            	break;
            case 33447://30 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33447, 1)) {
            		c.getItems().deleteItem(33447, 1);
            		c.bonusXpTime50 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime50 / 100 + " Minutes</col> of @blu@50%</col> Bonus XP left.");
            	break;
            case 33448://60 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33448, 1)) {
            		c.getItems().deleteItem(33448, 1);
            		c.bonusXpTime50 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime50 / 100 + " Minutes</col> of @blu@50%</col> Bonus XP left.");
            	break;
            case 33449://120 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33449, 1)) {
            		c.getItems().deleteItem(33449, 1);
            		c.bonusXpTime50 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime50 / 100 + " Minutes</col> of @blu@50%</col> Bonus XP left.");
            	break;
            	//75% boost
            case 33450://10 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33450, 1)) {
            		c.getItems().deleteItem(33450, 1);
            		c.bonusXpTime75 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime75 / 100 + " Minutes</col> of @blu@75%</col> Bonus XP left.");
            	break;
            case 33451://30 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33451, 1)) {
            		c.getItems().deleteItem(33451, 1);
            		c.bonusXpTime75 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime75 / 100 + " Minutes</col> of @blu@75%</col> Bonus XP left.");
            	break;
            case 33452://60 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33452, 1)) {
            		c.getItems().deleteItem(33452, 1);
            		c.bonusXpTime75 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime75 / 100 + " Minutes</col> of @blu@75%</col> Bonus XP left.");
            	break;
            case 33453://120 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33453, 1)) {
            		c.getItems().deleteItem(33453, 1);
            		c.bonusXpTime75 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime75 / 100 + " Minutes</col> of @blu@75%</col> Bonus XP left.");
            	break;
            	//100% boost
            case 33454://10 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33454, 1)) {
            		c.getItems().deleteItem(33454, 1);
            		c.bonusXpTime100 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime100 / 100 + " Minutes</col> of @blu@100%</col> Bonus XP left.");
            	break;
            case 33455://30 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33455, 1)) {
            		c.getItems().deleteItem(33455, 1);
            		c.bonusXpTime100 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime100 / 100 + " Minutes</col> of @blu@100%</col> Bonus XP left.");
            	break;
            case 33456://60 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33456, 1)) {
            		c.getItems().deleteItem(33456, 1);
            		c.bonusXpTime100 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime100 / 100 + " Minutes</col> of @blu@100%</col> Bonus XP left.");
            	break;
            case 33457://120 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime150 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33457, 1)) {
            		c.getItems().deleteItem(33457, 1);
            		c.bonusXpTime100 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime100 / 100 + " Minutes</col> of @blu@100%</col> Bonus XP left.");
            	break;
            	//150% boost
            case 33458://10 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33458, 1)) {
            		c.getItems().deleteItem(33458, 1);
            		c.bonusXpTime150 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime150 / 100 + " Minutes</col> of @blu@150%</col> Bonus XP left.");
            	break;
            case 33459://30 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33459, 1)) {
            		c.getItems().deleteItem(33459, 1);
            		c.bonusXpTime150 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime150 / 100 + " Minutes</col> of @blu@150%</col> Bonus XP left.");
            	break;
            case 33460://60 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33460, 1)) {
            		c.getItems().deleteItem(33460, 1);
            		c.bonusXpTime150 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime150 / 100 + " Minutes</col> of @blu@150%</col> Bonus XP left.");
            	break;
            case 33461://120 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime200 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33461, 1)) {
            		c.getItems().deleteItem(33461, 1);
            		c.bonusXpTime150 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime150 / 100 + " Minutes</col> of @blu@150%</col> Bonus XP left.");
            	break;
            	//200%
            case 33462://10 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33462, 1)) {
            		c.getItems().deleteItem(33462, 1);
            		c.bonusXpTime200 += 1000;
            		c.sendMessage("@blu@10 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime200 / 100 + " Minutes</col> of @blu@200%</col> Bonus XP left.");
            	break;
            case 33463://30 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33463, 1)) {
            		c.getItems().deleteItem(33463, 1);
            		c.bonusXpTime200 += 3000;
            		c.sendMessage("@blu@30 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime200 / 100 + " Minutes</col> of @blu@200%</col> Bonus XP left.");
            	break;
            case 33464://60 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33464, 1)) {
            		c.getItems().deleteItem(33464, 1);
            		c.bonusXpTime200 += 6000;
            		c.sendMessage("@blu@60 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime200 / 100 + " Minutes</col> of @blu@200%</col> Bonus XP left.");
            	break;
            case 33465://120 Mins
            	if (c.bonusXpTime25 > 0 || c.bonusXpTime50 > 0 || c.bonusXpTime75 > 0 || c.bonusXpTime100 > 0 || c.bonusXpTime150 > 0) {
            		c.sendMessage("Scrolls must have the same XP % to be stacked.");
            		break;
            	}
            	if (c.getItems().playerHasItem(33465, 1)) {
            		c.getItems().deleteItem(33465, 1);
            		c.bonusXpTime200 += 12000;
            		c.sendMessage("@blu@120 Minutes</col> has been added to your total Bonus XP time");
            	}
            	c.sendMessage("You currently have: @blu@" + c.bonusXpTime200 / 100 + " Minutes</col> of @blu@200%</col> Bonus XP left.");
            	break;
            	
            case 20724://imbued heart
                DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
                if (Objects.nonNull(session)) {
                    if (session.getRules().contains(Rule.NO_DRINKS)) {
                        c.sendMessage("Using the imbued heart with 'No Drinks' option is forbidden.");
                        return;
                    }
                }
                if (System.currentTimeMillis() - c.lastHeart < 420000) {
                    c.sendMessage("You must wait 7 minutes between each use.");
                } else {
                    c.getPA().imbuedHeart();
                    c.lastHeart = System.currentTimeMillis();
                }
                break;
                
            case 32285://Derwens heart
                DuelSession session2 = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
                if (Objects.nonNull(session2)) {
                    if (session2.getRules().contains(Rule.NO_DRINKS)) {
                        c.sendMessage("Using the heart with 'No Drinks' option is forbidden.");
                        return;
                    }
                }
                if (System.currentTimeMillis() - c.lastDerwenHeart < 420000) {
                    c.sendMessage("You must wait 7 minutes between each use.");
                } else {
                    c.getPA().derwenHeart();
                    c.lastDerwenHeart = System.currentTimeMillis();
                }
                break;
                
            case 33284://Justiciars heart
                DuelSession session3 = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
                if (Objects.nonNull(session3)) {
                    if (session3.getRules().contains(Rule.NO_DRINKS)) {
                        c.sendMessage("Using the heart with 'No Drinks' option is forbidden.");
                        return;
                    }
                }
                if (System.currentTimeMillis() - c.lastJusticiarHeart < 420000) {
                    c.sendMessage("You must wait 7 minutes between each use.");
                } else {
                    c.getPA().justiciarHeart();
                    c.lastJusticiarHeart = System.currentTimeMillis();
                }
                break;
                
            case 33283://Porazdirs heart
                DuelSession session4 = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
                if (Objects.nonNull(session4)) {
                    if (session4.getRules().contains(Rule.NO_DRINKS)) {
                        c.sendMessage("Using the heart with 'No Drinks' option is forbidden.");
                        return;
                    }
                }
                if (System.currentTimeMillis() - c.lastPorazdirHeart < 420000) {
                    c.sendMessage("You must wait 7 minutes between each use.");
                } else {
                    c.getPA().porazdirHeart();
                    c.lastPorazdirHeart = System.currentTimeMillis();
                }
                break;
                
            case 10006:
                Hunter.lay(c, new BirdSnare(c));
                break;
            case 10008:
                Hunter.lay(c, new BoxTrap(c));
                break;
            case 13249:
                if (!c.getSlayer().isCerberusRoute()) {
                    c.sendMessage("You have no clue how to navigate in here, you should find a slayer master to learn.");
                    return;
                }
                AgilityHandler.delayFade(c, "", 1310, 1237, 0, "You teleport into the cave", "and end up at the main room.", 3);
                c.getItems().deleteItem(13249, 1);
                break;

            case 13226:
                c.getHerbSack().fillSack();
                break;

            case 12020:
                c.getGemBag().fillBag();
                break;

            case 5509:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 0);
                break;
            case 5510:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 1);
                break;
            case 5512:
                Pouches.fill(c, Pouch.forId(itemId), itemId, 2);
                break;

            case 952: //Spade
                int x = c.getX();
                int y = c.getY();
                       
                c.startAnimation(831, 1);
                
                CycleEventHandler.getSingleton().addEvent(c, (container) -> {
                	c.stopAnimation();
                	container.stop();
                }, 1);
                
                if (ClueScrollHandler.dig(c))
                	return;
                
                if (Boundary.isIn(c, Barrows.GRAVEYARD)) {
                    c.getBarrows().digDown();
                }
                
                if (x == 3005 && y == 3376 || x == 2999 && y == 3375 || x == 2996 && y == 3377) {
                    if (!c.getRechargeItems().hasItem(13120)) {
                        c.sendMessage("You must have the elite falador shield to do this.");
                        return;
                    }
                    c.getPA().movePlayer(1760, 5163, 0);
                }
                break;
        }

        if (itemId == 2678) {
            c.getDH().sendDialogues(657, -1);
            return;
        }
        if (itemId == 8015 || itemId == 8014) {
            NonCombatSpells.attemptDate(c, itemId);
        }
        if (itemId == 9553) {
            c.getPotions().eatChoc(9553, -1, itemSlot, 1, true);
        }
        if (itemId == 12846) {
            c.getDH().sendDialogues(578, -1);
        }
        if (itemId == 12938) {
            if (c.getZulrahEvent().getInstancedZulrah() != null) {
                c.sendMessage("It seems your currently in the zulrah instance, relog if this is false.");
                return;
            }
            c.getItems().deleteItem(12938,  1);
            c.getDH().sendDialogues(625, -1);
            return;
        }
        /*if (itemId == 405) {
			if (c.getItems().freeSlots() < 2) {
				c.sendMessage("You need at least 2 free slots to open this.");
				return;
			}
			c.getItems().deleteItem2(itemId, 1);
			c.getItems().addItem(995, 10000 + Misc.random(90000));
			c.getItems().addItem(1624, 1 + Misc.random(9));
			c.sendMessage("You open the casket and find some treasure inside.");
		}*/
        if (itemId == 4155) {
            if (!c.getSlayer().getTask().isPresent()) {
                c.sendMessage("You do not have a task, please talk with a slayer master!");
                return;
            }
            c.sendMessage("I currently have " + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "'s to kill.");
            c.getPA().closeAllWindows();
        }
        if (itemId == 2839) {
            if (c.getSlayer().isHelmetCreatable() == true) {
                c.sendMessage("You have already learned this recipe. You have no more use for this scroll.");
                return;
            }
            if (c.getItems().playerHasItem(2839)) {
                c.getSlayer().setHelmetCreatable(true);
                c.sendMessage("You have learned the slayer helmet recipe. You can now assemble it");
                c.sendMessage("using a Black Mask, Facemask, Nose peg, Spiny helmet and Earmuffs.");
                c.getItems().deleteItem(2839, 1);
            }
        }
        
        
        /* Infernal Key Creation */
        if (itemId >= 33150 && itemId <=33152) {
		if (c.getItems().playerHasAllItems(keyPieces)) {
        	c.getItems().deleteItem(33150, 1);
        	c.getItems().deleteItem(33151, 1);
        	c.getItems().deleteItem(33152, 1);
        	c.getItems().addItemUnderAnyCircumstance(33153, 1);
        	c.getDH().sendItemStatement("You combine your Infernal Key pieces to create an @red@Infernal Key</col>!", 33153);
        } else {
        	c.sendMessage("You need all 3 pieces of the @blu@Infernal Key</col> to create this.");
        }
      }
        if (itemId == DiceHandler.DICE_BAG) {
            DiceHandler.rollDice(c);
        }
        
        if (itemId == 2697) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $10 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2698) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $50 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2699) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $100 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2700) {
            c.sendMessage("You can't redeem this scroll on yourself!");
            c.sendMessage("Use this on another player to transfer $5 to their amount donated");
            c.sendMessage("This does NOT give the other player donator points.");
			/*if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			if (c.getItems().playerHasItem(2697, 1)) {
				c.getDH().sendDialogues(4000, -1);
			}*/
        }
        if (itemId == 2701) {
            if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
                return;
            }
            if (c.getItems().playerHasItem(2701, 1)) {
                c.getDH().sendDialogues(4004, -1);
            }
        }
        if (itemId == 7509) {
            if (c.inWild() || c.inDuelArena() || Boundary.isIn(c, Boundary.DUEL_ARENA)) {
                c.sendMessage("You cannot do this here.");
                return;
            }
            if (c.getHealth().getStatus().isPoisoned() || c.getHealth().getStatus().isVenomed()) {
                c.sendMessage("You are effected by venom or poison, you should cure this first.");
                return;
            }
            if (c.getHealth().getAmount() <= 1) {
                c.sendMessage("I better not do that.");
                return;
            }
            c.forcedChat("Ow! I nearly broke a tooth!");
            c.startAnimation(829);
            // c.getHealth().reduce(1);
            c.appendDamage(1, Hitmark.HIT);
            return;
        }
        if (itemId == 10269) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10269, 1)) {
                c.getItems().addItem(995, 30000);
                c.getItems().deleteItem(10269, 1);
            }
        }
        if (itemId == 10271) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10271, 1)) {
                c.getItems().addItem(995, 10000);
                c.getItems().deleteItem(10271, 1);
            }
        }
        if (itemId == 10273) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10273, 1)) {
                c.getItems().addItem(995, 14000);
                c.getItems().deleteItem(10273, 1);
            }
        }
        if (itemId == 10275) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10275, 1)) {
                c.getItems().addItem(995, 18000);
                c.getItems().deleteItem(10275, 1);
            }
        }
        if (itemId == 10277) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10277, 1)) {
                c.getItems().addItem(995, 22000);
                c.getItems().deleteItem(10277, 1);
            }
        }
        if (itemId == 10279) {
            if (c.inWild() || c.inDuelArena()) {
                return;
            }
            if (c.getItems().playerHasItem(10279, 1)) {
                c.getItems().addItem(995, 26000);
                c.getItems().deleteItem(10279, 1);
            }
        }
		/* Mystery box */
        if (itemId == 6199)
            if (c.getItems().playerHasItem(6199)) {
                c.getMysteryBox().open();
                return;
            }
        
        /* Elder Mystery box */
        if (itemId == 33884)
            if (c.getItems().playerHasItem(33884)) {
                c.getElderMysteryBox().open();
                return;
            }
        
        //pet box
        if (itemId == 33498)
        	if (c.getItems().playerHasItem(33498)) {
        		c.getStarterPetBox().open();
        		return;
        	}

        /* Dragonhunter Mystery box */
		if (itemId == 33270) {
			if (c.getItems().playerHasItem(33270)) {
				c.getDragonHunterMBox().open();
				return;
			}
		}

        /*Infernal Mystery Box*/
        if (itemId == 33154)
            if (c.getItems().playerHasItem(33154)) {
                c.getInfernalMysteryBox().open();
                return;
            }
        
        /*Boss Caskets*/
        if (itemId == 12022)
        	if (c.getItems().playerHasItem(12022)){
        		c.getBandosCasket().open();
        		return;
        	}
        if (itemId == 12024)
        	if (c.getItems().playerHasItem(12024)){
        		c.getArmadylCasket().open();
        		return;
        	}
        if (itemId == 12026)
        	if (c.getItems().playerHasItem(12026)){
        		c.getSaradominCasket().open();
        		return;
        	}
        if (itemId == 12028)
        	if (c.getItems().playerHasItem(12028)){
        		c.getZamorakCasket().open();
        		return;
        	}
        //End of boss caskets
        
        if (itemId == 11739)
            if (c.getItems().playerHasItem(11739)) {
            	int lp = Misc.random(1, 50);
                c.loyaltyPoints += lp;
                c.sendMessage("You receive @blu@" + lp + "</col> Loyalty points.");
                c.sendMessage("You now have @blu@" + c.loyaltyPoints + " Loyalty points.");
                return;
            }
        if (itemId == 405) //Pvm Casket
            if (c.getItems().playerHasItem(405)) {
                c.getPvmCasket().open();
                return;
            }
        if (itemId == 21307) //Pursuit Crate
            if (c.getItems().playerHasItem(21307)) {
                c.getWildyCrate().open();
                return;
            }
        if (itemId == 20703) //Daily Gear Box
            if (c.getItems().playerHasItem(20703)) {
                c.getDailyGearBox().open();
                return;
            }
        if (itemId == 20791) //Daily Skilling Box
            if (c.getItems().playerHasItem(20791)) {
                c.getDailySkillBox().open();
                return;
            }
		/*if (itemId == 7310) //Skill Casket
			if (c.getItems().playerHasItem(7310)) {
				c.getSkillCasket().open();
				return;
			}*/
        if (itemId == 6542)
            if (c.getItems().playerHasItem(6542)) {
                c.getChristmasPresent().open();
                return;
            }
        if (itemId == 2714) { // Easy Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.EASY);
            c.setEasyClueCounter(c.getEasyClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getEasyClueCounter() + " easy Treasure Trails.");
        }
        if (itemId == 2802) { // Medium Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.MEDIUM);
            c.setMediumClueCounter(c.getMediumClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getMediumClueCounter() + " medium Treasure Trails.");
        }
        if (itemId == 2775) { // Hard Clue Scroll Casket
            c.getItems().deleteItem(itemId, 1);
            c.getTrails().addRewards(RewardLevel.HARD);
            c.setHardClueCounter(c.getHardClueCounter() + 1);
            c.sendMessage("@blu@You have completed " + c.getHardClueCounter() + " hard Treasure Trails.");
        }
        if (itemId == 12084) { // Elite Clue Scroll Casket
            if (c.getItems().playerHasItem(12084)) {
                c.getItems().deleteItem(itemId, 1);
                c.getTrails().addRewards(RewardLevel.ELITE);
                c.setEliteClueCounter(c.getEliteClueCounter() + 1);
                c.sendMessage("@blu@You have completed " + c.getEliteClueCounter() + " Elite Treasure Trails.");
                if (Misc.random(200) == 2 && c.getItems().getItemCount(19730, true) == 0 && c.summonId != 19730) {
                    GlobalMessages.send(c.playerName + "Received a pet Bloodhound from an Elite Cluescroll!", GlobalMessages.MessageType.LOOT);
                    c.getItems().addItemUnderAnyCircumstance(19730, 1);
                }
            }
        }
        if (itemId == 19841) { // Master Clue Scroll Casket
            if (c.getItems().playerHasItem(19841)) {
                c.getItems().deleteItem(itemId, 1);
                c.getTrails().addRewards(RewardLevel.MASTER);
                c.setMasterClueCounter(c.getMasterClueCounter() + 1);
                c.sendMessage("@blu@You have completed " + c.getMasterClueCounter() + " master Treasure Trails.");
                if (Misc.random(100) == 2 && c.getItems().getItemCount(19730, true) == 0 && c.summonId != 19730) {
                	  GlobalMessages.send(c.playerName + "Received a pet Bloodhound from a Master Cluescroll!", GlobalMessages.MessageType.LOOT);
                    c.getItems().addItemUnderAnyCircumstance(19730, 1);
                }
            }
        }
        
//        if (itemId == 2677) {
//            Achievements.increase(c, AchievementType.CLUES, 1);
//            c.getItems().deleteItem(itemId, 1);
//            c.getItems().addItem(2714, 1);
//            c.sendMessage("You've recieved a easy clue scroll casket.");
//        }
//        if (itemId == 2801) {
//            Achievements.increase(c, AchievementType.CLUES, 1);
//            c.getItems().deleteItem(itemId, 1);
//            c.getItems().addItem(2802, 1);
//            c.sendMessage("You've recieved a medium clue scroll casket.");
//        }
//        if (itemId == 2722) {
//            Achievements.increase(c, AchievementType.CLUES, 1);
//            c.getItems().deleteItem(itemId, 1);
//            c.getItems().addItem(2775, 1);
//            c.sendMessage("You've recieved a hard clue scroll casket.");
//        }
        
        /**
         * Master clue scroll
         */
//        if (itemId == 19835) {
//            MasterClue.progressScroll(c);
//        }
        if (itemId == 2528) {
            c.usingLamp = true;
            c.normalLamp = true;
            c.antiqueLamp = false;
            c.sendMessage("You rub the lamp...");
            c.getPA().showInterface(2808);
        }
		/*
		 * if (itemId == 4447) { c.usingLamp = true; c.antiqueLamp = true; c.normalLamp = false; c.sendMessage("You rub the antique lamp of 13 million experience..." );
		 * c.getPA().showInterface(2808); } if (itemId == 2528) { c.usingLamp = true; c.normalLamp = true; c.antiqueLamp = false; c.sendMessage(
		 * "You rub the lamp of 1 million experience..."); c.getPA().showInterface(2808); }
		 */

    }

}