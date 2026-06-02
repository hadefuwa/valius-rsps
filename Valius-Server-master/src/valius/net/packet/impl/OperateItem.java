package valius.net.packet.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import valius.Config;
import valius.content.WeaponSheathing;
import valius.event.impl.WheatPortalEvent;
import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.npc.bosses.zulrah.Zulrah;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerAssistant;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.combat.Damage;
import valius.model.entity.player.combat.Degrade;
import valius.model.entity.player.combat.Degrade.DegradableItem;
import valius.model.entity.player.combat.effects.DragonfireShieldEffect;
import valius.model.entity.player.skills.Skill;
import valius.model.items.ItemAssistant;
import valius.model.items.ItemDefinition;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

public class OperateItem implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int actionRow = c.getInStream().readUnsignedWord(); // the row of the action
		int slot = c.getInStream().readUnsignedWord(); // the row of the action
		int sentItemId = c.getInStream().readInteger(); //the item's id

		if(slot < 0 || slot >= c.playerEquipment.length)
			return;

		int itemId = c.playerEquipment[slot];
		
		if(itemId == -1 || sentItemId != itemId) {
			return;
		}
		
		if(c.getRights().isOrInherits(Right.OWNER)) {
			c.sendMessage("Operate Item - itemId: " + itemId + "slot: " + slot + " action: " + actionRow);
			c.sendMessage("Item at slot " + c.playerEquipment[slot]);
		}
		

			ItemDefinition def = ItemDefinition.forId(itemId);
			Optional<DegradableItem> d = DegradableItem.forId(itemId);
			if (d.isPresent()) {
				Degrade.checkPercentage(c, itemId);
				return;
			}
			switch (itemId) {
			case 9948:
			case 9949:
				if (WheatPortalEvent.xLocation > 0 && WheatPortalEvent.yLocation > 0) {
					c.getPA().spellTeleport(WheatPortalEvent.xLocation + 1, WheatPortalEvent.yLocation + 1, 0, false);
				} else {
					c.sendMessage("There is currently no portal available, wait 5 minutes.");
					return;
				}
				break;
				
				/*
				 * Weapon Sheathing
				 */
			case 33172://Krils sword
			case 33173://Krils sheath
				
			case 11802://Armadyl godsword
			case 33229://Armadyl sheath
				
			case 11806://Saradomin godsword
			case 33231://Saradomin sheath
				
			case 11804://Bandos godsword
			case 33230://Bandos sheath
				
			case 11808://Zamorak godsword
			case 33232://Zamorak sheath
				WeaponSheathing.operate(c, itemId);
				break;
				
				
				//check for ether
			case 21816:
				c.sendMessage("You currently have " + c.ethereumCharge + " charges left in your Bracelet of ethereum.");
				break;
			case 22550:
				c.sendMessage("You currently have " + c.crawCharge + " charges left in your Craws bow.");
				break;
			case 22555:
				c.sendMessage("You currently have " + c.thammaronCharge + " charges left in your Thammarons staff.");
				break;
			case 22545:
				c.sendMessage("You currently have " + c.viggoraCharge + " charges left in your Viggoras chainmace.");
				break;
				
			case 12904:
				c.sendMessage("The toxic staff of the dead has " + c.getToxicStaffOfTheDeadCharge() + " charges remaining.");
				break;

				case 13660:
					if(c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
						c.sendMessage("You can't teleport above " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
						return;
					}
					c.getPA().showInterface(63000);
					return;
			case 13199:
			case 13197:
				c.sendMessage("The " + def.getName() + " has " + c.getSerpentineHelmCharge() + " charges remaining.");
				break;
			case 19675:
				c.sendMessage("Your Arclight has "+ c.getArcLightCharge() +" charges remaining.");
			break;
			case 11907:
			case 12899:
				int charge = itemId == 11907 ? c.getTridentCharge() : c.getToxicTridentCharge();
				c.sendMessage("The " + def.getName() + " has " + charge + " charges remaining.");
				break;
			case 22323:
			case 33673:
				int sangcharge = c.getSangStaffCharge();
				c.sendMessage("The " + def.getName() + " has " + sangcharge + " charges remaining.");
				break;
			case 12926:
				def = ItemDefinition.forId(c.getToxicBlowpipeAmmo());
				c.sendMessage("The blowpipe has " + c.getToxicBlowpipeAmmoAmount() + " " + def.getName() + " and " + c.getToxicBlowpipeCharge() + " charge remaining.");
				break;
			case 12931:
				def = ItemDefinition.forId(itemId);
				if (def == null) {
					return;
				}
				c.sendMessage("The " + def.getName() + " has " + c.getSerpentineHelmCharge() + " charge remaining.");
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

			case 13117:
				if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
					if (c.getRechargeItems().useItem(itemId)) {
						c.getRechargeItems().replenishPrayer(4);
					}
				} else {
					c.sendMessage("You already have full prayer points.");
					return;
				}
				break;
			case 13118:
				if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
					if (c.getRechargeItems().useItem(itemId)) {
						c.getRechargeItems().replenishPrayer(2);
					}
				} else {
					c.sendMessage("You already have full prayer points.");
					return;
				}
				break;
			case 13119:
			case 13120:
				if (c.getSkills().getLevel(Skill.PRAYER) < c.getSkills().getActualLevel(Skill.PRAYER)) {
					if (c.getRechargeItems().useItem(itemId)) {
						c.getRechargeItems().replenishPrayer(1);
					}
				} else {
					c.sendMessage("You already have full prayer points.");
					return;
				}
				break;
			case 13111:
				if (c.getRechargeItems().useItem(itemId)) {
					c.getPA().spellTeleport(3236, 3946, 0, false);
				}
				break;
			case 10507:
				if (c.getItems().isWearingItem(10507)) {
					if (System.currentTimeMillis() - c.lastPerformedEmote < 2500)
						return;
					c.startAnimation(6382);
					c.gfx0(263);
					c.lastPerformedEmote = System.currentTimeMillis();
				}
				break;

			case 20243:
					if (System.currentTimeMillis() - c.lastPerformedEmote < 2500)
						return;
					c.startAnimation(7268);
					c.lastPerformedEmote = System.currentTimeMillis();
				break;

			case 4212:
			case 4214:
			case 4215:
			case 4216:
			case 4217:
			case 4218:
			case 4219:
			case 4220:
			case 4221:
			case 4222:
			case 4223:
				c.sendMessage("You currently have " + (250 - c.crystalBowArrowCount) + " charges left before degradation to " + (c.playerEquipment[3] == 4223 ? "Crystal seed" : ItemAssistant.getItemName(c.playerEquipment[3] + 1)));
				break;

			case 4202:
			case 9786:
			case 9787:
				PlayerAssistant.ringOfCharosTeleport(c);
				break;

			case 11283:
			case 11284:
				if (Boundary.isIn(c, Zulrah.BOUNDARY) || Boundary.isIn(c, Boundary.CERBERUS_BOSSROOMS)) {
					return;
				}
				DragonfireShieldEffect dfsEffect = new DragonfireShieldEffect();
				if (c.npcIndex <= 0 && c.playerIndex <= 0) {
					return;
				}
				if (c.getHealth().getAmount() <= 0 || c.isDead) {
					return;
				}
				if (dfsEffect.isExecutable(c)) {
					Damage damage = new Damage(Misc.random(25));
					if (c.playerIndex > 0) {
						Player target = PlayerHandler.players[c.playerIndex];
						if (Objects.isNull(target)) {
							return;
						}
						c.attackTimer = 7;
						dfsEffect.execute(c, target, damage);
						c.setLastDragonfireShieldAttack(System.currentTimeMillis());
					} else if (c.npcIndex > 0) {
						NPC target = NPCHandler.npcs[c.npcIndex];
						if (Objects.isNull(target)) {
							return;
						}
						c.attackTimer = 7;
						dfsEffect.execute(c, target, damage);
						c.setLastDragonfireShieldAttack(System.currentTimeMillis());
					}
				}
				break;
				
			case 33115:
				if (Boundary.isIn(c, Zulrah.BOUNDARY) || Boundary.isIn(c, Boundary.CERBERUS_BOSSROOMS)) {
					return;
				}
				DragonfireShieldEffect dfseEffect = new DragonfireShieldEffect();
				if (c.npcIndex <= 0 && c.playerIndex <= 0) {
					return;
				}
				if (c.getHealth().getAmount() <= 0 || c.isDead) {
					return;
				}
				if (dfseEffect.isExecutable(c)) {
					Damage damage = new Damage(Misc.random(50));
					if (c.playerIndex > 0) {
						Player target = PlayerHandler.players[c.playerIndex];
						if (Objects.isNull(target)) {
							return;
						}
						c.attackTimer = 7;
						dfseEffect.execute(c, target, damage);
						c.setLastDragonfireShieldAttack(System.currentTimeMillis());
					} else if (c.npcIndex > 0) {
						NPC target = NPCHandler.npcs[c.npcIndex];
						if (Objects.isNull(target)) {
							return;
						}
						c.attackTimer = 7;
						dfseEffect.execute(c, target, damage);
						c.setLastDragonfireShieldAttack(System.currentTimeMillis());
					}
				}
				break;

				/**
				 * Max capes
				 */
			case 13280:
			case 13329:
			case 13337:
			case 21898:
			case 21285:
			case 13331:
			case 13333:
			case 13335:
			case 20760:
				c.getDH().sendDialogues(76, 1);
				break;

				/**
				 * Crafting cape
				 */
			case 9780:
			case 9781:
				c.getPA().startTeleport(2936, 3283, 0, "modern", false);
				break;
				

				/**
				 * Magic skillcape
				 */
			case 9762:
			case 9763:
				if (!Boundary.isIn(c, Boundary.EDGEVILLE_PERIMETER)) {
					c.sendMessage("This cape can only be operated within the edgeville perimeter.");
					return;
				}
				if (c.inWild()) {
					return;
				}
					if (c.playerMagicBook == 0) {
						c.playerMagicBook = 1;
						c.setSidebarInterface(6, 838);
						c.autocasting = false;
						c.sendMessage("An ancient wisdomin fills your mind.");
						c.getPA().resetAutocast();
					} else if (c.playerMagicBook == 1) {
						c.sendMessage("You switch to the lunar spellbook.");
						c.setSidebarInterface(6, 29999);
						c.playerMagicBook = 2;
						c.autocasting = false;
						c.autocastId = -1;
						c.getPA().resetAutocast();
					} else if (c.playerMagicBook == 2) {
						c.setSidebarInterface(6, 938);
						c.playerMagicBook = 0;
						c.autocasting = false;
						c.sendMessage("You feel a drain on your memory.");
						c.autocastId = -1;
						c.getPA().resetAutocast();
					}
					break;
				case 13136:
					switch (actionRow) {
						case 1:
							c.getPA().spellTeleport(3484, 9510, 2, false);
							break;
						case 2:
							c.getPA().spellTeleport(3426, 2927, 0, false);
							break;
						case 3:
							c.getPA().spellTeleport(3304, 2789, 0, false);
							break;
					}
					break;
				case 13078:
					switch (actionRow) {
					case 2:
						c.getPA().spellTeleport(3304, 2789, 0, false);
						break;
					}
				case 1712:
				case 1710:
				case 1708:
				case 1706:
					switch (actionRow) {
					case 1:
						c.getPA().startTeleport(3087, 3493, 0, "glory",false);

						break;
					case 2:
						c.getPA().startTeleport(2925, 3173, 0, "glory",false);
						break;
					case 3:
						c.getPA().startTeleport(3079, 3250, 0, "glory",false);
						break;
					case 4:
						c.getPA().startTeleport(3293, 3176, 0,"glory", false);
						break;
					}
					break;

				case 23073:
				case 23075:
				case 21890:
				case 21888:
				case 21266:
				case 21264:
					
				case 11864:
				case 11865:
				case 19639:
				case 19641:
				case 19643:
				case 19645:
				case 19647:
				case 19649:
					switch (actionRow) {
						case 1:
							if (!c.getSlayer().getTask().isPresent()) {
								c.sendMessage("You do not have a task!");
								return;
							}
							c.sendMessage("I currently have @blu@" + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "@bla@ to kill.");
							c.getPA().closeAllWindows();
							break;
						case 2:
							if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
								return;
							}
//				c.getDH().sendDialogues(12000, -1);
							for (int i = 8144; i < 8195; i++) {
								c.getPA().sendFrame126("", i);
							}
							int[] frames = { 8149, 8150, 8151, 8152, 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164, 8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173,
									8174, 8175, 8176, 8177, 8178, 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194 };
							c.getPA().sendFrame126("@dre@Kill Tracker for @blu@" + c.playerName + "", 8144);
							c.getPA().sendFrame126("", 8145);
							c.getPA().sendFrame126("@blu@Total kills@bla@ - " + c.getNpcDeathTracker().getTotal() + "", 8147);
							c.getPA().sendFrame126("", 8148);
							int frameIndex = 0;
							for (Map.Entry<String, Integer> entry : c.getNpcDeathTracker().getTracker().entrySet()) {
								if (entry == null) {
									continue;
								}
								if (frameIndex > frames.length - 1) {
									break;
								}
								if (entry.getValue() > 0) {
									c.getPA().sendFrame126("@blu@" + Misc.capitalize(entry.getKey().toLowerCase()) + ": @red@" + entry.getValue(), frames[frameIndex]);
									frameIndex++;
								}
							}
							c.getPA().showInterface(8134);
							break;
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
					switch (actionRow) {
						case 1:
							c.getPA().spellTeleport(3370, 3157, 0, false);
							break;
						case 2:
							c.getPA().spellTeleport(2441, 3088, 0, false);
							break;
						case 3:
							c.getPA().spellTeleport(3304, 3130, 0, false);
							break;
					}
					break;

				default:
					c.sendMessage("Nothing interesting happens..");
					break;
			}
		}
	}
