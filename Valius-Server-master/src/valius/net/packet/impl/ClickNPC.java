package valius.net.packet.impl;

import java.util.stream.IntStream;

import valius.Config;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.pets.PetHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.combat.Hitmark;
import valius.model.entity.player.combat.magic.MagicData;
import valius.model.items.ItemAssistant;
import valius.net.packet.PacketType;
import valius.net.packet.impl.npcoptions.NpcOptionFour;
import valius.net.packet.impl.npcoptions.NpcOptionOne;
import valius.net.packet.impl.npcoptions.NpcOptionThree;
import valius.net.packet.impl.npcoptions.NpcOptionTwo;
import valius.util.Misc;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {
	public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155, SECOND_CLICK = 17, THIRD_CLICK = 21,
			FOURTH_CLICK = 18;

	@Override
	public void processPacket(final Player c, int packetType, int packetSize) {
		c.npcIndex = 0;
		c.npcClickIndex = 0;
		c.playerIndex = 0;
		c.clickNpcType = 0;
		c.getPA().resetFollow();
		c.followId2 = c.npcIndex;
		c.getPA().followNpc();
		if (c.isForceMovementActive()) {
			return;
		}
		if (c.isForceMovementActive()) {
			return;
		}
		if (c.teleTimer > 0) {
			return;
		}
		switch (packetType) {

		/**
		 * Attack npc melee or range
		 **/
		case ATTACK_NPC:
			if (c.morphed) {
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 11907) {
				c.usingMagic = true;
				c.autocasting = true;
				c.spellId = 52;
			}
			if (c.playerEquipment[c.playerWeapon] == 22516) {
				c.usingMagic = true;
				c.autocasting = true;
				c.spellId = 98;
			}
			//Sang staff
			if (c.playerEquipment[c.playerWeapon] == 22323 || c.playerEquipment[c.playerWeapon] == 33673) {
				c.usingMagic = true;
				c.autocasting = true;

				int passive = Misc.random(1, 6);

				if (passive < 6) {
					c.spellId = 99;
				} else {
					c.spellId = 100;
					c.healPlayer(c.getHitmark().getId() / 2);
				}
			}

			if (c.playerEquipment[c.playerWeapon] == 12899) {
				c.usingMagic = true;
				c.autocasting = true;
				c.spellId = 53;
			}

			if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}
			c.npcIndex = c.getInStream().readUnsignedWordA();
			if (c.npcIndex >= NPCHandler.npcs.length || c.npcIndex < 0) {
				return;
			}
			if (NPCHandler.npcs[c.npcIndex] == null) {
				c.npcIndex = 0;
				break;
			}

			if (NPCHandler.npcs[c.npcIndex].getHealth().getMaximum() == 0) {
				c.npcIndex = 0;
				break;
			}
			if (NPCHandler.npcs[c.npcIndex] == null) {
				break;
			}
			if (c.getBankPin().requiresUnlock()) {
				c.getBankPin().open(2);
				return;
			}
			if (c.getTutorial().isActive()) {
				c.getTutorial().refresh();
				return;
			}
			if (c.autocastId > 0)
				c.autocasting = true;
			if (!c.autocasting && c.spellId > 0) {
				c.spellId = 0;
			}
			c.faceUpdate(c.npcIndex);
			c.usingMagic = false;
			boolean usingBow = false;
			boolean usingOtherRangeWeapons = false;
			boolean usingArrows = false;
			boolean usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 21902 || c.playerEquipment[c.playerWeapon] == 33094 || c.playerEquipment[c.playerWeapon] == 11785 || c.playerEquipment[c.playerWeapon] == 33124
					|| c.playerEquipment[c.playerWeapon] == 33117 || c.playerEquipment[c.playerWeapon] == 21012 || c.playerEquipment[c.playerWeapon] == 33114 || c.playerEquipment[c.playerWeapon] == 33578;
			if ((c.playerEquipment[c.playerWeapon] >= 4214 && (c.playerEquipment[c.playerWeapon] <= 4223)))
				usingBow = true;
			for (int bowId : c.BOWS) {
				if (c.playerEquipment[c.playerWeapon] == bowId) {
					usingBow = true;
					if (bowId == 19481 || bowId == 19478) {
						usingBow = false;
						c.usingBallista = true;
					}
					for (int arrowId : c.ARROWS) {
						if (c.playerEquipment[c.playerArrows] == arrowId) {
							usingArrows = true;
						}
					}
				}
			}
			for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
				if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
					usingOtherRangeWeapons = true;
				}
			}

			if ((usingBow || c.autocasting) && c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(),
					NPCHandler.npcs[c.npcIndex].getY(), 7)) {
				c.stopMovement();
			}

			if ((c.usingBallista || usingOtherRangeWeapons || c.playerEquipment[c.playerWeapon] == 11907 || c.playerEquipment[c.playerWeapon] == 22516 || c.playerEquipment[c.playerWeapon] == 22323
					|| c.playerEquipment[c.playerWeapon] == 12899 || c.playerEquipment[c.playerWeapon] == 33673)
					&& c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(),
							NPCHandler.npcs[c.npcIndex].getY(), 6)) {
				c.stopMovement();
			}
			if (c.getItems().isWearingAnyItem(33030) && c.usingArrows && c.getCombat().properBolts()) {
				c.sendMessage("You cannot use ammo with a Zaryte bow.");
				return;
			}
			if (c.getItems().isWearingAnyItem(33908, 33909, 33910, 33911, 33914, 33922) && c.usingArrows && c.getCombat().properBolts()) {
				c.sendMessage("You cannot use ammo with a Seren godbow.");
				return;
			}
			if (!usingCross && !usingArrows && usingBow && c.playerEquipment[c.playerWeapon] < 4212
					&& c.playerEquipment[c.playerWeapon] > 4223 && !usingCross) {
				c.sendMessage("You have run out of arrows!");
				break;
			}
			if (!c.getCombat().correctBowAndArrows() && Config.CORRECT_ARROWS && usingBow
					&& !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 4734
					&& c.playerEquipment[c.playerWeapon] != 9185 && c.playerEquipment[c.playerWeapon] != 11785
					&& c.playerEquipment[c.playerWeapon] != 9185 && c.playerEquipment[c.playerWeapon] != 33124
					&& c.playerEquipment[c.playerWeapon] != 9185 && c.playerEquipment[c.playerWeapon] != 33578
					&& c.playerEquipment[c.playerWeapon] != 21012 && c.playerEquipment[c.playerWeapon] != 19481
					&& c.playerEquipment[c.playerWeapon] != 19478 && c.playerEquipment[c.playerWeapon] != 33094
					&& c.playerEquipment[c.playerWeapon] != 33114 && c.playerEquipment[c.playerWeapon] != 21902
					&& c.playerEquipment[c.playerWeapon] != 22550 && c.playerEquipment[c.playerWeapon] != 22547
					&& c.playerEquipment[c.playerWeapon] != 33781 && c.playerEquipment[c.playerWeapon] != 33782
					&& c.playerEquipment[c.playerWeapon] != 33908
					&& c.playerEquipment[c.playerWeapon] != 33909 && c.playerEquipment[c.playerWeapon] != 33910
					&& c.playerEquipment[c.playerWeapon] != 33911 && c.playerEquipment[c.playerWeapon] != 33914
					&& c.playerEquipment[c.playerWeapon] != 33922 && c.playerEquipment[c.playerWeapon] != 33783
					&& c.playerEquipment[c.playerWeapon] != 33030 && c.playerEquipment[c.playerWeapon] != 33117) {
				c.sendMessage("You can't use "
						+ ItemAssistant.getItemName(c.playerEquipment[c.playerArrows]).toLowerCase() + "'s with a "
						+ ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + ".");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.playerEquipment[c.playerWeapon] == 9185 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 11785 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 33124 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 33578 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 21012 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 33117 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 21902 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 33114 && !c.getCombat().properBolts()
					|| c.playerEquipment[c.playerWeapon] == 33094 && !c.getCombat().properBolts()) {
				c.sendMessage("You must use bolts with a crossbow.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return;
			}

			if (c.followId > 0) {
				c.getPA().resetFollow();
			}

			if (c.attackTimer <= 0) {
				c.getCombat().attackNpc(c.npcIndex);
				c.attackTimer++;
			}

			c.followId2 = c.npcIndex;

			//			if (!Boundary.isIn(c, Boundary.INFERNO)) //who the fuck put this here lol
			//				c.stopMovement();
			break;

			/**
			 * Attack npc with magic
			 **/
		case MAGE_NPC:
			if (c.morphed) {
				return;
			}
			if (!c.mageAllowed) {
				c.mageAllowed = true;
				c.sendMessage("I can't reach that.");
				break;
			}
			c.npcIndex = c.getInStream().readSignedWordBigEndianA();
			int castingSpellId = c.getInStream().readSignedWordA();
			c.usingMagic = false;

			if (c.npcIndex >= NPCHandler.npcs.length || c.npcIndex < 0 || NPCHandler.npcs[c.npcIndex] == null) {
				break;
			}

			if (NPCHandler.npcs[c.npcIndex].getHealth().getMaximum() == 0
					|| NPCHandler.npcs[c.npcIndex].npcType == 944) {
				c.sendMessage("You can't attack this npc.");
				break;
			}

			for (int i = 0; i < MagicData.MAGIC_SPELLS.length; i++) {
				if (castingSpellId == MagicData.MAGIC_SPELLS[i][0]) {
					c.spellId = i;
					c.usingMagic = true;
					break;
				}
			}
			if (castingSpellId == 1171) { // crumble undead
				boolean isUndead = IntStream.of(Config.UNDEAD_IDS)
						.anyMatch(id -> id == NPCHandler.npcs[c.npcIndex].npcType);
				if (!isUndead) {
					c.sendMessage("You can only attack undead monsters with this spell.");
					c.getCombat().resetPlayerAttack();
					c.usingMagic = false;
					c.stopMovement();
					return;
				}
				if(NPCHandler.npcs[c.npcIndex].npcType == 8062) {//vorkath crab crumble undead custom effects
					int x = NPCHandler.npcs[c.npcIndex].getX();
					int y = NPCHandler.npcs[c.npcIndex].getY();
					c.turnPlayerTo(x, y);
					int endCoordX = x;
					int endCoordY = y;
					int targetY = (c.getX() - (int) endCoordX) * -1;
					int targetX = (c.getY() - (int) endCoordY) * -1;
					c.getItems().deleteItem(557, 2);
					c.getItems().deleteItem(556, 2);
					c.getItems().deleteItem(562, 1);
					c.startAnimation(724);
					c.getPA().createPlayersProjectile2(c.getX(), c.getY(), targetX, targetY, 50, 80,145,c.getHeight(),c.getHeight(), -1, 65, 0);
					NPCHandler.npcs[c.npcIndex].appendDamage(200, Hitmark.HIT);
					c.npcIndex = 0;
					c.getVorkath().crabSpawn = false;
					c.freezeTimer = 0;//after crab is dead you can walk again
					return;
				}
			}

			if (c.autocasting)
				c.autocasting = false;

			if (c.usingMagic || c.playerEquipment[c.playerWeapon] == 11907 || c.playerEquipment[c.playerWeapon] == 22516 || c.playerEquipment[c.playerWeapon] == 22323
					|| c.playerEquipment[c.playerWeapon] == 12899 || c.playerEquipment[c.playerWeapon] == 33673) {
				if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(),
						NPCHandler.npcs[c.npcIndex].getY(), 6)) {
					c.stopMovement();
				}
				if (c.attackTimer <= 0) {
					c.getCombat().attackNpc(c.npcIndex);
					c.attackTimer++;
				}
			}

			break;

		case FIRST_CLICK:
		{
			c.npcClickIndex = c.inStream.readSignedWordBigEndian();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			c.faceUpdate(c.npcClickIndex);
			c.followId2 = c.npcClickIndex;
			c.getPA().followNpc();
			if (c.debugMessage) {
				c.sendMessage("[DEBUG] NPC Option #1-> Click index: " + c.npcClickIndex + ", NPC Id: " + c.npcType);
			}
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			if (c.npcType == 8026) {
				c.stopMovement();
				c.faceUpdate(0);
				c.getPA().resetFollow();
				c.getPA().playerWalk(2272, 4061);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.getX() == 2272 && c.getY() == 4061) {
							c.turnPlayerTo(2272, 4062);
							c.startAnimation(827);
							c.getVorkath().poke(c, NPCHandler.npcs[c.npcClickIndex]);
							container.stop();
						}
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
				return;
			}
			NPC npc = NPCHandler.npcs[c.npcClickIndex];
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getSize())) {
				c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				npc.faceEntity(c.getIndex());
				c.faceUpdate(0);
				c.getPA().resetFollow();
				//NpcOptionOne.handleOption(c, c.npcType);
				NpcOptionOne.handleOption(c, npc);
			} else {
				c.clickNpcType = 1;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 1) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(),
									NPCHandler.npcs[c.npcClickIndex].getSize())) {
								c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
								c.faceUpdate(0);
								c.getPA().resetFollow();
								NpcOptionOne.handleOption(c, npc);
								container.stop();
							}
						}
						if (c.clickNpcType == 0 || c.clickNpcType > 1)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
		}
		break;

		case SECOND_CLICK:
		{
			c.npcClickIndex = c.inStream.readUnsignedWordBigEndianA();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			c.faceUpdate(c.npcClickIndex);
			c.followId2 = c.npcClickIndex;
			c.getPA().followNpc();
			if (c.debugMessage) {
				c.sendMessage("[DEBUG] NPC Option #2-> Click index: " + c.npcClickIndex + ", NPC Id: " + c.npcType);
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;

			NPC npc = NPCHandler.npcs[c.npcClickIndex];
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getSize())) {
				c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
				if (c.npcType == 6637 || c.npcType == 6638) {
					PetHandler.metamorphosis(c, c.npcType);
				}
				c.faceUpdate(0);
				c.getPA().resetFollow();
				NpcOptionTwo.handleOption(c, npc);
			} else {
				c.clickNpcType = 2;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 2) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(), 1)) {
								c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
								c.faceUpdate(0);
								c.getPA().resetFollow();
								NpcOptionTwo.handleOption(c, npc);
								container.stop();
							}
						}
						if (c.clickNpcType < 2 || c.clickNpcType > 2)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
		}
		break;

		case THIRD_CLICK:
		{
			c.npcClickIndex = c.inStream.readSignedWord();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			c.faceUpdate(c.npcClickIndex);
			c.followId2 = c.npcClickIndex;
			c.getPA().followNpc();
			if (c.debugMessage) {
				c.sendMessage("[DEBUG] NPC Option #3-> Click index: " + c.npcClickIndex + ", NPC Id: " + c.npcType);
			}
			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			NPC npc = NPCHandler.npcs[c.npcClickIndex];
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getSize())) {
				c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				if (c.npcType == 2130 || c.npcType == 2131 || c.npcType == 2132) {
					PetHandler.metamorphosis(c, c.npcType);
				}
				NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
				c.faceUpdate(0);
				c.getPA().resetFollow();
				NpcOptionThree.handleOption(c, npc);
			} else {
				c.clickNpcType = 3;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 3) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(), 1)) {
								c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
								c.faceUpdate(0);
								c.getPA().resetFollow();
								NpcOptionThree.handleOption(c, npc);
								container.stop();
							}
						}
						if (c.clickNpcType < 3)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
		}
		break;

		case FOURTH_CLICK:
		{
			c.npcClickIndex = c.inStream.readSignedWordBigEndian();
			if (c.npcClickIndex >= NPCHandler.npcs.length || c.npcClickIndex < 0) {
				break;
			}
			c.faceUpdate(c.npcClickIndex);
			c.followId2 = c.npcClickIndex;
			c.getPA().followNpc();
			if (c.debugMessage) {
				c.sendMessage("[DEBUG] NPC Option #4-> Click index: " + c.npcClickIndex + ", NPC Id: " + c.npcType);
			}
			if (c.getInterfaceEvent().isActive()) {
				c.sendMessage("Please finish what you're doing.");
				return;
			}

			if (NPCHandler.npcs[c.npcClickIndex] != null)
				c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
			NPC npc = NPCHandler.npcs[c.npcClickIndex];
			if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(),
					c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getSize())) {
				c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
				NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
				c.faceUpdate(0);
				c.getPA().resetFollow();
				NpcOptionFour.handleOption(c, npc);
			} else {
				c.clickNpcType = 4;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if ((c.clickNpcType == 4) && NPCHandler.npcs[c.npcClickIndex] != null) {
							if (c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcClickIndex].getX(),
									NPCHandler.npcs[c.npcClickIndex].getY(),
									NPCHandler.npcs[c.npcClickIndex].getSize())) {
								c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(),
										NPCHandler.npcs[c.npcClickIndex].getY());
								NPCHandler.npcs[c.npcClickIndex].faceEntity(c.getIndex());
								c.faceUpdate(0);
								c.getPA().resetFollow();
								NpcOptionFour.handleOption(c, npc);
								container.stop();
							}
						}
						if (c.clickNpcType < 4)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickNpcType = 0;
					}
				}, 1);
			}
		}
		break;
		}

	}
}
