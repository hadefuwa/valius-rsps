package valius.net.packet.impl.itemoptions;

import java.util.Objects;
import java.util.Optional;

import valius.content.teleportation.TeleportTablets;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.entity.player.combat.Degrade;
import valius.model.entity.player.combat.Degrade.DegradableItem;
import valius.model.item.container.RunePouch;
import valius.model.items.Item;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemOptionThree implements PacketType {

	@SuppressWarnings("unused")
	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readSignedWordBigEndianA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int itemId = c.getInStream().readSignedWordA();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (RunePouch.isRunePouch(c, itemId)) {
			c.getRunePouch().emptyBagToInventory();
			return;
		}

		Item item = c.getItems().getItemAtSlot(slot);
		c.getQuestManager().onItemClick(interfaceId, 3, item);
		
		TeleportTablets.operate(c, itemId);
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		Optional<DegradableItem> d = DegradableItem.forId(itemId);
		if (d.isPresent()) {
			Degrade.checkPercentage(c, itemId);
			return;
		}
		switch (itemId) {
		case 20716:
			if(c.getItems().playerHasItem(20718)) {
				
			}
			break;
    	case 227:
    		c.getItems().deleteItem(227, 1);
    		c.getItems().addItem(229, 1);
    		break;
    		
    		
    		/*
    		 * ethereum bracelet dismantle
    		 */
    	case 21817:
    		c.getItems().deleteItem(21817, 1);
    		c.getItems().addItem(21820, 250);
    		break;
    		
		case 21347:
			c.boltTips = false;
			c.arrowTips = false;
			c.javelinHeads = true;
			c.sendMessage("Your Amethyst method is now Javelin Heads!");
			break;
		case 13125:
		case 13126:
		case 13127:
			if (c.getRunEnergy() < 100) {
				if (c.getRechargeItems().useItem(itemId)) {
					c.getRechargeItems().replenishRun(50);
				}
			} else {
				c.sendMessage("You already have full run energy.");
				return;
			}
			break;
			
		case 13128:
			if (c.getRunEnergy() < 100) {
				if (c.getRechargeItems().useItem(itemId)) {
					c.getRechargeItems().replenishRun(100);
				}
			} else {
				c.sendMessage("You already have full run energy.");
				return;
			}
			break;
			
		case 13226:
			c.getHerbSack().check();
			break;
			
		case 12020:
			c.getGemBag().withdrawAll();
			break;
		
		case 12902: //Toxic staff dismantle
			if (!c.getItems().playerHasItem(12902))
				return;
			if (c.getItems().freeSlots() < 2)
				return;
			
			c.getItems().deleteItem(12902, 1);
			c.getItems().addItem(12932, 1);
			c.getItems().addItem(11791, 1);
			c.sendMessage("You dismantle your toxic staff of the dead.");
			break;
			
		case 12900: //Toxic trident dismantle
			if (!c.getItems().playerHasItem(12900))
				return;
			if (c.getItems().freeSlots() < 2)
				return;
			
			c.getItems().deleteItem(12900, 1);
			c.getItems().addItem(12932, 1);
			c.getItems().addItem(11907, 1);
			c.sendMessage("You dismantle your toxic trident.");
			break;
			
		case 11283:
		case 33115:
			if (c.getDragonfireShieldCharge() == 0) {
				c.sendMessage("Your dragonfire shield has no charge.");
				return;
			}
			c.setDragonfireShieldCharge(0);
			c.sendMessage("Your dragonfire shield has been emptied.");
			break;
		case 13196:
		case 13198:
			c.getItems().deleteItem2(itemId, 1);
			c.getItems().addItem(12929, 1);
			c.getItems().addItem(itemId == 13196 ? 13200 : 13201, 1);
			c.sendMessage("You revoke the mutagen from the helmet.");
			break;
		case 11907:
		case 12899:
			int charge = itemId == 11907 ? c.getTridentCharge() : c.getToxicTridentCharge();
			if (charge <= 0) {
				if (itemId == 12899) {
					if (c.getToxicTridentCharge() == 0) {
						if (c.getItems().freeSlots() > 1) {
							c.getItems().deleteItem(12899, 1);
							c.getItems().addItem(12932, 1);
							c.getItems().addItem(11907, 1);
							c.sendMessage("You dismantle your Trident of the swamp.");
							return;
						} else {
							c.sendMessage("You need at least 2 inventory spaces to dismantle the trident.");
							return;
						}
					}
				} else {
					c.sendMessage("Your trident currently has no charge.");
					return;
				}
			}
			
			if (c.getItems().freeSlots() < 3) {
				c.sendMessage("You need at least 3 free slots for this.");
				return;
			}
			c.getItems().addItem(554, 5 * charge);
			c.getItems().addItem(560, 1 * charge);
			c.getItems().addItem(562, 1 * charge);
			
			if (itemId == 12899) {
				c.getItems().addItem(12934, 10 * charge);
			}
			
			if (itemId == 11907) {
				c.setTridentCharge(0);
			} else {
				c.setToxicTridentCharge(0);
			}
			c.sendMessage("You revoke " + charge + " charges from the trident.");
			break;

		case 12926:
			if (c.getToxicBlowpipeAmmo() == 0 || c.getToxicBlowpipeAmmoAmount() == 0) {
				c.sendMessage("You have no ammo in the pipe.");
				return;
			}
			if (c.getItems().addItem(c.getToxicBlowpipeAmmo(), c.getToxicBlowpipeAmmoAmount())) {
				c.setToxicBlowpipeAmmoAmount(0);
				c.sendMessage("You unload the pipe.");
			}
			break;
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			 //c.getPA().ROD();
			c.getPA().spellTeleport(3304, 3130, 0, false);
			break;
		case 11968:
		case 11970:
		case 11105:
		case 11107:
		case 11109:
		case 11111:
			c.getPA().handleSkills(itemId);
			c.isOperate = true;
			c.itemUsing = itemId;
			break;
		case 1712:
			c.getPA().handleGlory(itemId);
			c.isOperate = true;
			c.itemUsing = itemId;
			break;

		case 1710:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;

		case 1708:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;

		case 1706:
			c.getPA().handleGlory(itemId);
			c.itemUsing = itemId;
			c.isOperate = true;
			break;
			
		case 19639:
		case 19641:
		case 19643:
		case 19645:
		case 19647:
		case 19649:
			c.getSlayer().revertHelmet(itemId);
			break;
			
		case 13280:
		case 113238:
			if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
				for (int i = 8144; i < 8195; i++) {
					c.getPA().sendFrame126("", i);
				}
				c.getPA().sendFrame126("               @dre@Max Cape Features", 8144);
				
				c.getPA().sendFrame126("While wielding the cape you will:", 8145);
				
				c.getPA().sendFrame126("Have a chance of saving ammo.", 8148);
				c.getPA().sendFrame126("Deplete run energy slower.", 8149);
				c.getPA().sendFrame126("Get more herbs & faster growth time.", 8150);
				c.getPA().sendFrame126("Have less chance of burning food", 8151);
				c.getPA().sendFrame126("Have 20% of saving a bar while smithing.", 8152);
				c.getPA().sendFrame126("Have 20% of saving a herb while mixing potions.", 8153);
				c.getPA().sendFrame126("Regenerate 2 hitpoints instead of 1 at a time.", 8154);
				c.getPA().sendFrame126("Get 5+ restore per prayer/super restore sip.", 8155);
				c.getPA().sendFrame126("Get double seeds while thieving the master farmer.", 8156);
				c.getPA().sendFrame126("Be able to operate for additional options.", 8157);

				c.getPA().showInterface(8134);
			break;

		default:
			if (c.getRights().isOrInherits(Right.OWNER)) {
				Misc.println("[DEBUG] Item Option #3-> Item id: " + itemId);
			}
			break;
		}

	}

}
