package valius.net.packet.impl;

import java.util.Objects;

import valius.content.DiceHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.runecrafting.Pouches;
import valius.model.entity.player.skills.runecrafting.Pouches.Pouch;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.world.World;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {
	
	public static int[] starter_t1_armor = {33486, 33487, 33488, 33489, 33490};

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int wearId = c.wearId;
		wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();
		c.nextChat = 0;
		c.dialogueOptions = 0;
		
		if (!c.getItems().playerHasItem(wearId, 1)) {
			return;
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
			return;
		}
		if (c.getTutorial().isActive()) {
			c.getTutorial().refresh();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			World.getWorld().getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			c.sendMessage("You cannot remove items from your equipment whilst trading, trade declined.");
			return;
		}
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		if ((c.playerIndex > 0 || c.npcIndex > 0) && wearId != 4153 && wearId != 12848 && !c.usingMagic && !c.usingBow && !c.usingOtherRangeWeapons && !c.usingCross && !c.usingBallista)
			c.getCombat().resetPlayerAttack();
		if (c.canChangeAppearance) {
			c.sendMessage("You can't wear an item while changing appearence.");
			return;
		}
		
		if (wearId == 4155) {
			if (!c.getSlayer().getTask().isPresent()) {
				c.sendMessage("You do not have a task!");
				return;
			}
			c.sendMessage("I currently have @blu@" + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "@bla@ to kill.");
			c.getPA().closeAllWindows();
			return;
		}
		switch (wearId) {
		case 21347:
			c.boltTips = true;
			c.arrowTips = false;
			c.javelinHeads = false;
			c.sendMessage("Your Amethyst method is now Bolt Tips!");
			break;
		case 5509:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 0);
			break;
		case 5510:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 1);
			break;
		case 5512:
			Pouches.empty(c, Pouch.forId(wearId), wearId, 2);
			break;
		}
		
		/*
		 * Starter T1 Armor
		 */
		int t1_pieces = 0;
		for (int Starter_T1_Armor : starter_t1_armor) {
		if (wearId == Starter_T1_Armor) {
			t1_pieces += 1;
		}
		if (t1_pieces == 1) {
			c.sendMessage("You currently have a 1% XP boost while having this armor piece equipped.");
		}
		if (t1_pieces == 2) {
			c.sendMessage("You currently have a 2% XP boost while having this armor piece equipped.");
		}
		if (t1_pieces == 3) {
			c.sendMessage("You currently have a 3% XP boost while having this armor piece equipped.");
		}
		if (t1_pieces == 4) {
			c.sendMessage("You currently have a 4% XP boost while having this armor piece equipped.");
		}
		if (t1_pieces == 5) {
			c.sendMessage("You currently have a 5% XP boost while having this armor piece equipped.");
			c.sendMessage("You will gain bonus vote tickets while claiming rewards with this set equipped!");
		}
		}
		
		if (wearId == 20056) {
			c.forcedChat("*Hic*");//ale of the gods
		}
		if (wearId == 12639 || wearId == 12638 || wearId == 12637) {
			c.sendMessage("@pur@You feel like you could run forever while wearing this!");
		}
		if (c.wearId == 33421) {
			c.sendMessage("@pur@You receive a 5% boost to Mining while the Star sprite follows you. ");
		}
		if (c.wearId == 33549) {
			c.sendMessage("@pur@You receive a 10% boost to Drops while Solomon follows you. ");
		}
		if (c.wearId == 33491 || c.wearId == 33492 || c.wearId == 33493 || c.wearId == 33494 || c.wearId == 33495) {
			c.sendMessage("@pur@You receive a 2% boost to Drops while Wolfy follows you. ");
		}
		if (c.wearId == 33548) {
			c.sendMessage("@pur@You receive a 5% boost to Drops while Scoop follows you. ");
		}
		//starter kit boosted item messages
		if (wearId == 13121) {
			c.sendMessage("@pur@You receive a 5% boost to Thieving experience while wearing the Ardougne Cloak 1.");
		}
		if (wearId == 13122) {
			c.sendMessage("@pur@You receive a 10% boost to Thieving experience while wearing the Ardougne Cloak 2.");
		}
		if (wearId == 13123) {
			c.sendMessage("@pur@You receive a 15% boost to Thieving experience while wearing the Ardougne Cloak 3.");
		}
		if (wearId == 13124) {
			c.sendMessage("@pur@You receive a 20% boost to Thieving experience while wearing the Ardougne Cloak 4.");
		}
		if (wearId == 13137) {
			c.sendMessage("@pur@You receive a 5% boost to Agility experience while wearing the Kandarin Helm 1.");
		}
		if (wearId == 13138) {
			c.sendMessage("@pur@You receive a 10% boost to Agility experience while wearing the Kandarin Helm 2.");
		}
		if (wearId == 13139) {
			c.sendMessage("@pur@You receive a 15% boost to Agility experience while wearing the Kandarin Helm 3.");
		}
		if (wearId == 13140) {
			c.sendMessage("@pur@You receive a 20% boost to Agility experience while wearing the Kandarin Helm 4.");
		}
		if (wearId == 13104) {
			c.sendMessage("@pur@You receive a 5% boost to Smithing experience while wearing the Varrock Plate 1.");
		}
		if (wearId == 13105) {
			c.sendMessage("@pur@You receive a 10% boost to Smithing experience while wearing the Varrock Plate 2.");
		}
		if (wearId == 13106) {
			c.sendMessage("@pur@You receive a 15% boost to Smithing experience while wearing the Varrock Plate 3.");
		}
		if (wearId == 13107) {
			c.sendMessage("@pur@You receive a 20% boost to Smithing experience while wearing the Varrock Plate 4.");
		}
		if (wearId == 13112) {
			c.sendMessage("@pur@You receive a 5% boost to Firemaking experience while wearing the Morytania Legs 1.");
		}
		if (wearId == 13113) {
			c.sendMessage("@pur@You receive a 10% boost to Firemaking experience while wearing the Morytania Legs 2.");
		}
		if (wearId == 13114) {
			c.sendMessage("@pur@You receive a 15% boost to Firemaking experience while wearing the Morytania Legs 3.");
		}
		if (wearId == 13115) {
			c.sendMessage("@pur@You receive a 20% boost to Firemaking experience while wearing the Morytania Legs 4.");
		}
		if (wearId == 13117) {
			c.sendMessage("@pur@You receive a 5% boost to Prayer experience while wearing the Falador Shield 1.");
		}
		if (wearId == 13118) {
			c.sendMessage("@pur@You receive a 10% boost to Prayer experience while wearing the Falador Shield 2.");
		}
		if (wearId == 13119) {
			c.sendMessage("@pur@You receive a 15% boost to Prayer experience while wearing the Falador Shield 3.");
		}
		if (wearId == 13120) {
			c.sendMessage("@pur@You receive a 20% boost to Prayer experience while wearing the Falador Shield 4.");
		}
		if (wearId == 13133) {
			c.sendMessage("@pur@You receive a 5% boost to Runecrafting experience while wearing the Desert Amulet 1.");
		}
		if (wearId == 13134) {
			c.sendMessage("@pur@You receive a 10% boost to Runecrafting experience while wearing the Desert Amulet 2.");
		}
		if (wearId == 13135) {
			c.sendMessage("@pur@You receive a 15% boost to Runecrafting experience while wearing the Desert Amulet 3.");
		}
		if (wearId == 13136) {
			c.sendMessage("@pur@You receive a 20% boost to Runecrafting experience while wearing the Desert Amulet 4.");
		}
		if (wearId == 13125) {
			c.sendMessage("@pur@You receive a 5% boost to Farming experience while wearing the Explorer's Ring 1.");
		}
		if (wearId == 13126) {
			c.sendMessage("@pur@You receive a 10% boost to Farming experience while wearing the Explorer's Ring 2.");
		}
		if (wearId == 13127) {
			c.sendMessage("@pur@You receive a 15% boost to Farming experience while wearing the Explorer's Ring 3.");
		}
		if (wearId == 13128) {
			c.sendMessage("@pur@You receive a 20% boost to Farming experience while wearing the Explorer's Ring 4.");
		}
		if (wearId == 13129) {
			c.sendMessage("@pur@You receive a 5% boost to Fishing experience while wearing the Fremmy Sea Boots 1.");
		}
		if (wearId == 13130) {
			c.sendMessage("@pur@You receive a 10% boost to Fishing experience while wearing the Fremmy Sea Boots 2.");
		}
		if (wearId == 13131) {
			c.sendMessage("@pur@You receive a 15% boost to Fishing experience while wearing the Fremmy Sea Boots 3.");
		}
		if (wearId == 13132) {
			c.sendMessage("@pur@You receive a 20% boost to Fishing experience while wearing the Fremmy Sea Boots 4.");
		}
		if (wearId == 11136) {
			c.sendMessage("@pur@You receive a 5% boost to Crafting experience while wearing the Karamja Gloves 1.");
		}
		if (wearId == 11138) {
			c.sendMessage("@pur@You receive a 10% boost to Crafting experience while wearing the Karamja Gloves 2.");
		}
		if (wearId == 11140) {
			c.sendMessage("@pur@You receive a 15% boost to Crafting experience while wearing the Karamja Gloves 3.");
		}
		if (wearId == 13103) {
			c.sendMessage("@pur@You receive a 20% boost to Crafting experience while wearing the Karamja Gloves 4.");
		}
		
		/*
		 * Prayer boost wings
		 */
		if (wearId == 33876 || wearId == 33877 || wearId == 33878) {
			c.sendMessage("@pur@You receive a 5% boost to Prayer experience while wearing these wings.");
		}
		
		
		
		
		if (wearId == DiceHandler.DICE_BAG) {
			DiceHandler.rollDice(c);
		}
		
		if (!World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			c.getItems().wearItem(wearId, c.wearSlot);
		}
	}

}
