package valius.net.packet.impl;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import valius.Config;
import valius.content.BloodMysteryBox;
import valius.content.ChristmasBox;
import valius.content.DailyReward;
import valius.content.EasterMysteryBox;
import valius.content.EventMysteryBox;
import valius.content.HalloweenMysteryBox;
import valius.content.PetMysteryBox;
import valius.content.PlayerEmotes;
import valius.content.StarBox;
import valius.content.UltraMysteryBox;
import valius.content.ValentinesBox;
import valius.content.ValiusMysteryBox;
import valius.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import valius.content.achievement_diary.falador.FaladorDiaryEntry;
import valius.content.achievement_diary.fremennik.FremennikDiaryEntry;
import valius.content.achievement_diary.kandarin.KandarinDiaryEntry;
import valius.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import valius.content.achievement_diary.varrock.VarrockDiaryEntry;
import valius.content.gauntlet.TheGauntlet;
import valius.content.help.HelpDatabase;
import valius.content.staff.StaffControl;
import valius.content.teleportation.CityTeleports;
import valius.content.tradingpost.Listing;
import valius.content.wogw.Wogw;
import valius.model.entity.npc.drops.DropManager;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.combat.Special;
import valius.model.entity.player.combat.Specials;
import valius.model.entity.player.combat.magic.LunarSpells;
import valius.model.entity.player.combat.magic.MagicData;
import valius.model.entity.player.combat.magic.NonCombatSpells;
import valius.model.entity.player.combat.melee.QuickPrayers;
import valius.model.entity.player.skills.Cooking;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.Smelting;
import valius.model.entity.player.skills.crafting.BattlestaveMaking;
import valius.model.entity.player.skills.crafting.BraceletMaking;
import valius.model.entity.player.skills.crafting.GlassBlowing;
import valius.model.entity.player.skills.crafting.LeatherMaking;
import valius.model.entity.player.skills.crafting.Tanning;
import valius.model.entity.player.skills.crafting.CraftingData.tanningData;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.model.items.bank.BankItem;
import valius.model.items.bank.BankTab;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.model.multiplayer_session.duel.DuelSessionRules.Rule;
import valius.model.shops.ShopAssistant;
import valius.net.packet.PacketType;
import valius.net.packet.impl.dialogueoptions.FiveOptions;
import valius.net.packet.impl.dialogueoptions.FourOptions;
import valius.net.packet.impl.dialogueoptions.ThreeOptions;
import valius.net.packet.impl.dialogueoptions.TwoOptions;
import valius.util.Misc;
import valius.util.log.PlayerLogging;
import valius.util.log.PlayerLogging.LogType;
import valius.world.World;

/**
 * Clicking most buttons
 *
 */
public class ClickingButtons implements PacketType {

	@Override
	public void processPacket(final Player c, int packetType, int packetSize) {
		int actionButtonId = c.getInStream().readInteger();
		if (c.debugMessage) {
			c.sendMessage("actionbutton: " + actionButtonId + ", DialogueID: " + c.dialogueAction);
			System.out.println("BUTTON " + actionButtonId);
		}
		if (c.playerName.equalsIgnoreCase("patrity")) {
			c.sendMessage("Action Button: "+actionButtonId);
		}
		if (c.isDead || c.getHealth().getAmount() <= 0) {
			return;
		}
		if (c.isIdle) {
			if (c.debugMessage)
				c.sendMessage("You are no longer in idle mode.");
			c.isIdle = false;
		}
		if(c.getMakeWidget().onButtonClick(actionButtonId))
			return;
		if(c.getPortalTeleports().handleButton(actionButtonId)) {
			return;
		}
		if(c.getPortalTeleports().handleTabClick(actionButtonId)) {
			return;
		}
		if (c.getLootingBag().handleButton(actionButtonId)) {
			return;
		}
		if (c.getPrestige().prestigeClicking(c, actionButtonId)) {
			return;
		}
		if (c.getExpLock().ExpLockClicking(c, actionButtonId)) {
			return;
		}
		if (c.getRunePouch().handleButton(actionButtonId)) {
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.getInterfaceEvent().clickButton(actionButtonId);
			return;
		}
		if (!c.getTutorial().isActive() && c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.getSlayer().onActionButton(actionButtonId)) {
			return;
		}

		if (actionButtonId == 42419) {
			c.getTutorial().getIronmanInterface().confirm();
			return;
		}
		
		c.getQuestManager().onInterfaceButton(actionButtonId);

		if (actionButtonId == 42402 || actionButtonId == 42403 || actionButtonId == 42423 
				|| actionButtonId == 42404 || actionButtonId == 42405 || actionButtonId == 42406 || actionButtonId == 42419) {
			c.getTutorial().getIronmanInterface().click(actionButtonId);
			return;
		}

		if (actionButtonId == 2471) {
			ThreeOptions.handleOption1(c);
			return;
		}
		if (actionButtonId == 2472) {
			ThreeOptions.handleOption2(c);
			return;
		}

		//if (actionButtonId == 9169) {
		//ThreeOptions.handleOption3(c);
		//	return;
		//}

		// (TeleportationInterface.actions(c, actionButtonId)) {
		// return;
		// }
		Listing.postButtons(c, actionButtonId);


		/** Achievement Buttons **/
		if (actionButtonId >= 36037 && actionButtonId <= 36095) {
			c.getAchievements().viewAchievement(actionButtonId, c.getAchievements().currentInterface);
			return;
		}

		/** Drop Manager Buttons **/
		if (actionButtonId >= 33008 && actionButtonId <= 33137) {
			World.getWorld().getDropManager().select(c, actionButtonId);
			return;
		}

		/*
		 * if (actionButtonId >= 175205 && actionButtonId <= 176149) { int id = 175204;
		 * if (!StaffControl.isUsingControl) { StaffControl.loadOnPlayerOptions(c);
		 * StaffControl.username = p.playerName;
		 * c.getPA().sendFrame126("<col=0xFF981F>Player: " + p.playerName, 45254); }
		 * c.setSidebarInterface(2, 45000); }
		 */
		if (actionButtonId >= 59574 && actionButtonId <= 59670) {
			HelpDatabase.getDatabase().view(c, actionButtonId);
			HelpDatabase.getDatabase().delete(c, actionButtonId);
			return;
		}
		// if (BattlestaveMaking.handleActions(c, actionButtonId)) {
		// return;
		// }
		/*
		 * if (actionButtonId >= 166035 && actionButtonId < 166035 +
		 * DropManager.AMOUNT_OF_TABLES) { World.getWorld().getDropManager().select(c,
		 * actionButtonId); return; }
		 */
		if (actionButtonId == 3880) {
			c.getDH().sendDialogues(68, -1);
			return;
		}
		if (actionButtonId == 3881) {
			World.getWorld().getDropManager().open2(c);
			return;
		}
		if (actionButtonId == 42519) {
			c.getPA().removeAllWindows();
			return;
		}
		c.getPestControlRewards().click(actionButtonId);
		if (c.getTitles().click(actionButtonId)) {
			return;
		}
		if (c.battlestaffDialogue) {
			BattlestaveMaking.craftBattlestave(c, actionButtonId);
		}
		if (c.craftDialogue) {
			LeatherMaking.craftLeather(c, actionButtonId);
		}
		if (c.braceletDialogue) {
			BraceletMaking.craftBracelet(c, actionButtonId);
		}
		for (tanningData t : tanningData.values()) {
			if (actionButtonId == t.getButtonId(actionButtonId)) {
				Tanning.tanHide(c, actionButtonId);
			}
		}
		if (c.getPresets().clickButton(actionButtonId)) {
			return;
		}
		int[] spellIds = { 1152, 1154, 1156, 1158, 1160, 1163, 1166, 1169, 1172, 1175, 1177, 1181, 1183, 1185, 1188, 1189, 
				1153, 1157, 1161, 1542, 1543, 1562, 1572, 1582, 1592, 1171, 1539, 12037, 1190, 1191, 1192, 
				12445, 12993, 12987, 12901, 12861, 12963, 13011, 12919, 12881, 12951, 12999, 12911, 12871, 12975, 13023, 
				12929, 12891,  };
		for (int i = 0; i < spellIds.length; i++) {
			if (actionButtonId == spellIds[i]) {
				c.autocasting = true;
				c.autocastId = i;
			}
		}
		if (World.getWorld().getHolidayController().clickButton(c, actionButtonId)) {
			return;
		}
		if (c.getPunishmentPanel().clickButton(actionButtonId)) {
			return;
		}
		DuelSession duelSession = null;
		c.getFletching().select(actionButtonId);
		GlassBlowing.glassBlowing(c, actionButtonId);
		PlayerEmotes.performEmote(c, actionButtonId);
		// int[] teleportButtons = { 4140, 4143, 4146, 4150, 6004, 6005, 29031,
		// 50235, 50245, 50253, 51005, 51013, 51023, 51031, 51039,
		// 117112, 117131, 117154, 117186, 117210, 118018, 118042, 118058 };
		// if (IntStream.of(teleportButtons).anyMatch(id -> actionButtonId == id)) {
		// CityTeleports.teleport(c, actionButtonId);
		// }
		QuickPrayers.clickButton(c, actionButtonId);
		LunarSpells.lunarButton(c, actionButtonId);
		switch (actionButtonId) {
		
		case 64200:
		case 64201:
		case 64202:
		case 64203:
		case 64204:
		case 64205:
			c.getShops().setActiveShopTab(actionButtonId - 64200);
			break;
		case 5100:
			c.getOutStream().writePacketHeader(248);
			c.getOutStream().writeWordA(54000);
			c.getOutStream().writeWord(5065);
			c.flushOutStream();
			break;

		case 65003:
			DailyReward daily = new DailyReward(c);
			daily.getReward();
			break;
			
		case 47004:
			switch(c.boxCurrentlyUsing) {
			case UltraMysteryBox.MYSTERY_BOX:
				c.getUltraMysteryBox().spin();
				break;
				
			case ValiusMysteryBox.MYSTERY_BOX:
				c.getValiusMysteryBox().spin();
				break;
				
			case BloodMysteryBox.MYSTERY_BOX:
				c.getBloodMysteryBox().spin();
				break;
				
			case EventMysteryBox.MYSTERY_BOX:
				c.getEventMysteryBox().spin();
				break;
				
			case PetMysteryBox.MYSTERY_BOX:
				c.getPetMysteryBox().spin();
				break;
				
			case ValentinesBox.MYSTERY_BOX:
				c.getValentinesBox().spin();
				break;
				
			case StarBox.MYSTERY_BOX:
				c.getStarBox().spin();
				break;
				
			case EasterMysteryBox.MYSTERY_BOX:
				c.getEasterMysteryBox().spin();
				break;
				
			case ChristmasBox.MYSTERY_BOX:
				c.getChristmasBox().spin();
				break;
				
			case HalloweenMysteryBox.MYSTERY_BOX:
				c.getHalloweenMysteryBox().spin();
				break;
			}
			break;
/*
 *  TODO: Deprecate old code for teleports!
 * 
		case 1164:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 24) {
				if (c.getItems().playerHasItem(554, 1) && c.getItems().playerHasItem(556, 3)
						&& c.getItems().playerHasItem(563, 1)) {
					c.getPA().startTeleport(Config.VARROCK_X, Config.VARROCK_Y, 0, "modern", false);
					c.getItems().deleteItem(554, c.getItems().getItemSlot(554), 1);
					c.getItems().deleteItem(556, c.getItems().getItemSlot(556), 3);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 1);
					c.getPA().addSkillXP(25, c.playerMagic, true);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;

		case 1167:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 30) {
				if (c.getItems().playerHasItem(557, 1) && c.getItems().playerHasItem(556, 3)
						&& c.getItems().playerHasItem(563, 1)) {
					c.getPA().startTeleport(Config.LUMBY_X, Config.LUMBY_Y, 0, "modern", false);
					c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 1);
					c.getItems().deleteItem(556, c.getItems().getItemSlot(556), 3);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 1);
					c.getPA().addSkillXP(31, c.playerMagic, true);
					c.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;
		case 1170:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 36) {
				if (c.getItems().playerHasItem(555, 1) && c.getItems().playerHasItem(556, 3)
						&& c.getItems().playerHasItem(563, 1)) {
					c.getPA().startTeleport(Config.FALADOR_X, Config.FALADOR_Y, 0, "modern", false);
					c.getItems().deleteItem(555, c.getItems().getItemSlot(555), 1);
					c.getItems().deleteItem(556, c.getItems().getItemSlot(556), 3);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 1);
					c.getPA().addSkillXP(37, c.playerMagic, true);
					c.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_TO_FALADOR);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;

		case 1174:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 44) {
				if (c.getItems().playerHasItem(556, 5) && c.getItems().playerHasItem(563, 1)) {
					c.getPA().startTeleport(Config.CAMELOT_X, Config.CAMELOT_Y, 0, "modern", false);
					c.getItems().deleteItem(556, c.getItems().getItemSlot(556), 5);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 1);
					c.getPA().addSkillXP(56, c.playerMagic, true);
					c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;

		case 1540:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 50) {
				if (c.getItems().playerHasItem(555, 2) && c.getItems().playerHasItem(563, 2)) {
					c.getPA().startTeleport(Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 0, "modern", false);
					c.getItems().deleteItem(555, c.getItems().getItemSlot(555), 2);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 2);
					c.getPA().addSkillXP(61, c.playerMagic, true);
					c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;

		case 1541:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 57) {
				if (c.getItems().playerHasItem(557, 2) && c.getItems().playerHasItem(563, 2)) {
					c.getPA().startTeleport(Config.WATCHTOWER_X, Config.WATCHTOWER_Y, 0, "modern", false);
					c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 2);
					c.getItems().deleteItem(563, c.getItems().getItemSlot(563), 2);
					c.getPA().addSkillXP(68, c.playerMagic, true);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;
			
		case 7455:
			if (c.teleTimer > 0) {
				return;
			}
			if (c.getSkills().getLevel(Skill.MAGIC) >= 61) {
				if (c.getItems().playerHasItem(554, 2) && c.getItems().playerHasItem(563, 2)) {
					c.getPA().startTeleport(Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 0, "modern", false);
					c.getItems().deleteItem(554, c.getItems().getItemSlot(563), 2);
					c.getPA().addSkillXP(68, c.playerMagic, true);
					c.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TROLLHEIM_TELEPORT);
				} else {
					c.sendMessage("You do not have the required runes to cast this spell.");
				}
			} else {
				c.sendMessage("You do not have the required magic level to cast this spell.");
			}
			break;
*/			
			
		//normal
		case 1164: //varrock
		case 1167: //lumb
		case 1170: //fally
		case 1174: //cammy
		case 1540: //ardy
		case 1541: //watchtower
		case 7455: //trollheim
		//case 32649: //kourend
		//ancients	
		case 13035:
		case 13045:
		case 13053:
		case 13061:
		case 13069:
		case 13079:
		case 13087:
		case 13095:
		//lunar
		case 30064: //moonclan
		case 30083: //ourania
		case 30106: //waterbirth
		case 30138: //barbarian
		case 30162: //khazard
		case 30226: //fishing guild
		case 30250: //catherby
		case 20266: //ice plateu
			
			CityTeleports.teleport(c, actionButtonId);
			break;

		case 29166:
		case -1:
			if (Config.BONUS_XP_WOGW == true) {
				c.sendMessage("@cr10@ <col=6666FF>Wogw is granting double experience for another "
						+ TimeUnit.MILLISECONDS.toMinutes(Wogw.EXPERIENCE_TIMER * 600) + " minutes.");
			} else if (Config.BONUS_PC_WOGW == true) {
				c.sendMessage("@cr10@ <col=6666FF>Wogw is granting +5 bonus pc points for another "
						+ TimeUnit.MILLISECONDS.toMinutes(Wogw.PC_POINTS_TIMER * 600) + " minutes.");
			} else if (Config.DOUBLE_DROPS == true) {
				c.sendMessage("@cr10@ <col=6666FF>Wogw is granting double drop rate for another "
						+ TimeUnit.MILLISECONDS.toMinutes(Wogw.DOUBLE_DROPS_TIMER * 600) + " minutes.");
			} else {
				c.sendMessage("@cr10@ <col=6666FF>Wogw is currently inactive.");
			}
			break;

		case 23117:
			c.getPA().showInterface(37700);
			break;

		case 29162:
			c.sendMessage("There are currently " + PlayerHandler.getRealPlayerCount() + " players online.");
			break;

		case 58014:
			c.placeHolders = !c.placeHolders;
			c.getPA().sendChangeSprite(58014, c.placeHolders ? (byte) 1 : (byte) 0);
			break;

			// Close interface for drop checker
		case 39021:
		case 33002:
			c.getPA().removeAllWindows();
			break;

		case 29177:
			if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			// c.getDH().sendDialogues(12000, -1);
			for (int i = 8144; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			int[] frames = { 8149, 8150, 8151, 8152, 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163,
					8164, 8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175, 8176, 8177, 8178, 8179,
					8180, 8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194 };
			c.getPA().sendFrame126("@dre@Kill Tracker for @blu@" + c.playerName + "", 8144);
			c.getPA().sendFrame126("", 8145);
			c.getPA().sendFrame126("@blu@Total kills@bla@ - " + c.getNpcDeathTracker().getTotal() + "", 8147);
			c.getPA().sendFrame126("", 8148);
			int frameIndex = 0;
			for (Entry<String, Integer> entry : c.getNpcDeathTracker().getTracker().entrySet()) {
				if (entry == null) {
					continue;
				}
				if (frameIndex > frames.length - 1) {
					break;
				}
				if (entry.getValue() > 0) {
					c.getPA().sendFrame126(
							"@blu@" + Misc.capitalize(entry.getKey().toLowerCase()) + ": @red@" + entry.getValue(),
							frames[frameIndex]);
					frameIndex++;
				}
			}
			c.getPA().showInterface(8134);
			// c.checkWellOfGoodwillTimers();
			break;

		case 38006:
			c.getPA().sendChangeSprite(38006, (byte) 2);
			c.getPA().sendChangeSprite(38007, (byte) 1);
			c.getPA().sendChangeSprite(38008, (byte) 1);

			c.wogwOption = "experience";
			break;

		case 38007:
			c.getPA().sendChangeSprite(38006, (byte) 1);
			c.getPA().sendChangeSprite(38007, (byte) 2);
			c.getPA().sendChangeSprite(38008, (byte) 1);

			c.wogwOption = "pc";
			break;

		case 62102:
		case 39502:
		case 47003:
			c.getPA().closeAllWindows();
			break;



		case 38008:
			c.getPA().sendChangeSprite(38006, (byte) 1);
			c.getPA().sendChangeSprite(38007, (byte) 1);
			c.getPA().sendChangeSprite(38008, (byte) 2);

			c.wogwOption = "drops";
			break;
		case 47511:
		case 42544:
			c.getTitles().display();
			break;

		case 38023:
			Wogw.donate(c, (int) c.wogwDonationAmount);
			break;

		case 38025:
			c.getPA().sendFrame171(1, 38020);
			c.getPA().sendChangeSprite(38006, (byte) 1);
			c.getPA().sendChangeSprite(38007, (byte) 1);
			c.getPA().sendChangeSprite(38008, (byte) 1);
			c.sendMessage("You decided to end your donation to the well of goodwill.");
			break;

		case 35008:
			if (c.inSafeBox) {
				c.getPA().sendFrame171(0, 35010);
			}
			break;
		case 35015:
			// No
			if (c.inSafeBox) {
				c.getPA().sendFrame171(1, 35010);
			}
			break;
		case 35013:
			// Yes
			if (c.inSafeBox) {
				c.getPA().sendFrame171(1, 35010);
				if (c.safeBoxSlots == 50) {
					c.sendMessage("@cr10@You already have the max amount of slots.");
					return;
				}
				if (c.getItems().playerHasItem(13307, 15)) {
					c.safeBoxSlots++;
					c.getItems().deleteItem(13307, 15);
					c.sendMessage("@cr10@You successfully purchased 1 extra slot in the safe deposit box.");
					c.getPA().sendFrame126("" + (c.getSafeBox().items.size()) + "/" + c.safeBoxSlots, 35005);
				} else {
					c.sendMessage(
							"@cr10@You must have 15 blood money to purchase 1 extra slot in the safe deposit box.");
					return;
				}
			}
			break;

		case 35002:
			c.getSafeBox().closeLootbag();
			break;
		case 10404:
			c.setSidebarInterface(2, 29465); // 638
			break;
		case 29471:
			c.setSidebarInterface(2, 10220); // 638
			c.updateQuestTab();
			break;
		case 1123:
			c.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.SMITH_STEEL_KNIFES);
			break;
		case 6020:
			c.setSidebarInterface(1, 3917); // Skilltab > 3917
			c.setSidebarInterface(2, 638); // 638
			c.setSidebarInterface(3, 3213);
			c.setSidebarInterface(4, 1644);
			c.setSidebarInterface(5, 5608);
			switch (c.playerMagicBook) {
			case 0:
				c.setSidebarInterface(6, 938); // modern
				break;

			case 1:
				c.setSidebarInterface(6, 838); // ancient
				break;

			case 2:
				c.setSidebarInterface(6, 29999); // ancient
				break;
			}
			c.setSidebarInterface(7, 18128);
			c.setSidebarInterface(8, 5065);
			c.setSidebarInterface(9, 5715);
			c.setSidebarInterface(10, 2449);
			c.setSidebarInterface(11, 23000); // wrench tab
			c.setSidebarInterface(12, 147); // run tab
			c.setSidebarInterface(0, 2423);
			// if (c.playerEquipment[c.playerRing] == 7927) {
			// c.getItems().deleteEquipment(c.playerEquipment[c.playerRing], c.playerRing);
			// c.getItems().addItem(7927,1);
			// }
			c.morphed = false;
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
			break;

		case 5000:
			QuickPrayers.toggle(c);
			c.getPA().sendFrame36(197, 1);
			break;
		case 5001:
			for (int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows
				if (c.prayerActive[p]) {
					c.sendMessage("You need to deactivate your active prayers before doing this.");
					return;
				}
			}
			c.isSelectingQuickprayers = true;
			c.setSidebarInterface(5, 17200);
			break;

		case 29277:
			c.setSidebarInterface(2, 29265); // 29265
			break;
		case 45002:
			StaffControl.emptyList(c);
			StaffControl.isUsingControl = false;
			int line = 45005;
			c.getPA().sendFrame126("Online: " + PlayerHandler.getRealPlayerCount(), 45001);
			c.getPA().sendFrame126("<col=0xFF981F>Players", 45254);
			for (Player p : PlayerHandler.players) {
				if (p == null)
					continue;
				c.getPA().sendFrame126(
						"@or2@" + p.playerName + "  -  " + p.getHealth().getAmount() + "/" + p.getHealth().getMaximum(),
						line);
				line++;
			}
			c.setSidebarInterface(2, 45000);
			break;
		case 29267:
			c.setSidebarInterface(2, 638);
			break;

			/**
			 * Dialogue Handling
			 */

		case 2461:
			TwoOptions.handleOption1(c);
			break;

		case 2462:
			TwoOptions.handleOption2(c);
			break;

		case 2482:
			FourOptions.handleOption1(c);
			break;

		case 2483:
			FourOptions.handleOption2(c);
			break;

		case 2484:
			FourOptions.handleOption3(c);
			break;

		case 2485:
			FourOptions.handleOption4(c);
			break;

		case 2494:
			FiveOptions.handleOption1(c);
			break;

		case 2495:
			FiveOptions.handleOption2(c);
			break;

		case 2496:
			FiveOptions.handleOption3(c);
			break;

		case 2497:
			FiveOptions.handleOption4(c);
			break;

		case 2498:
			FiveOptions.handleOption5(c);
			break;

		case 65535:
			c.sendMessage("You reset your experience counter.");
			c.setExperienceCounter(0L);
			break;

		case 34674:
		case 23674:
		case 30234:
			if (c.inClanWars() || c.inClanWarsSafe()) {
				c.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
				return;
			}
			c.getBH().teleportToTarget();
			break;

		case 1159:
		case 15877:
			NonCombatSpells.attemptDate(c, actionButtonId);
			break;

		case 14175:
			if (c.droppingItem) {
				// c.getPA().destroyItem(c.droppedItem);
				if (c.getItems().playerHasItem(c.droppedItem) && c.droppedItem != -1 && !c.isDead) {
					try {
						PlayerLogging.write(LogType.DROP_ITEM, c, "Dropped item: " + c.droppedItem + " x " + c.playerItemsN[c.getItems().getItemSlot(c.droppedItem)]);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
					World.getWorld().getItemHandler().createGroundItem(c, c.droppedItem, c.getX(), c.getY(), c.getHeight(),
							c.playerItemsN[c.getItems().getItemSlot(c.droppedItem)], c.getIndex());
					c.getItems().deleteItem(c.droppedItem, c.getItems().getItemSlot(c.droppedItem),
							c.playerItemsN[c.getItems().getItemSlot(c.droppedItem)]);
					// c.getPA().destroyItem(itemId);
					c.getPA().removeAllWindows();
				} else {
					return;
				}
				c.droppedItem = -1;
				c.droppingItem = false;
			} else {
				c.usingMagic = true;
				c.getPA().removeAllWindows();
				// if (!c.getItems().playerHasItem(554, 5) || !c.getItems().playerHasItem(561,
				// 1)) {
				// c.sendMessage("You do not have the required runes to do this.");
				// return;
				// }
				c.getPA().alchemy(c.droppedItem, "high");
			}
			break;
		case 14176:
			c.getPA().removeAllWindows();// Choosing No will remove all the
			// windows
			c.droppedItem = -1;
			c.droppingItem = false;
			break;

			/*
			 * case 191109: c.getAchievements().currentInterface = 0;
			 * c.getAchievements().drawInterface(0); break;
			 * 
			 * case 191110: c.getAchievements().currentInterface = 1;
			 * c.getAchievements().drawInterface(1); break;
			 * 
			 * case 191111: c.getAchievements().currentInterface = 2;
			 * c.getAchievements().drawInterface(2); break;
			 */

		case 64002:
		case 36084:
		case 36184:
		case 38010:
			c.getPA().closeAllWindows();
			break;
		case 6294:
			c.getPA().closeAllWindows();
			break;

		case 5294:
			c.getPA().closeAllWindows();
			valius.model.items.bank.BankPin pin = c.getBankPin();
			if (pin.getPin().length() <= 0)
				c.getBankPin().open(1);
			else if (!pin.getPin().isEmpty() && !pin.isAppendingCancellation())
				c.getBankPin().open(3);
			else if (!pin.getPin().isEmpty() && pin.isAppendingCancellation())
				c.getBankPin().open(4);
			break;

		case 58018:
			if (c.getPA().viewingOtherBank) {
				c.getPA().resetOtherBank();
				return;
			}
			if (!c.isBanking)
				return;
			if (System.currentTimeMillis() - c.lastBankDeposit < 500)
				return;
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset();
				return;
			}
			c.lastBankDeposit = System.currentTimeMillis();
			for (int slot = 0; slot < c.playerItems.length; slot++) {
				if (c.playerItems[slot] > 0 && c.playerItemsN[slot] > 0) {
					c.getItems().addToBank(c.playerItems[slot] - 1, c.playerItemsN[slot], false);
				}
			}
			c.getItems().updateInventory();
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			break;

		case 58026:
			if (c.getPA().viewingOtherBank) {
				c.getPA().resetOtherBank();
				return;
			}
			if (!c.isBanking)
				return;
			if (System.currentTimeMillis() - c.lastBankDeposit < 250)
				return;
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset();
				return;
			}
			if (c.getBankPin().requiresUnlock()) {
				c.isBanking = false;
				c.getBankPin().open(2);
				return;
			}
			c.lastBankDeposit = System.currentTimeMillis();
			for (int slot = 0; slot < c.playerEquipment.length; slot++) {
				if (c.playerEquipment[slot] > 0 && c.playerEquipmentN[slot] > 0) {
					if (c.getItems().addEquipmentToBank(c.playerEquipment[slot], slot, c.playerEquipmentN[slot],
							false)) {
						c.getItems().wearItem(-1, 0, slot);
					} else {
						c.sendMessage("Your bank is full.");
						break;
					}
				}
			}
			c.getItems().updateInventory();
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			break;

		case 58042:
		case 58054:
		case 58065:
		case 58076:
		case 58087:
		case 58098:
		case 58109:
		case 58120:
		case 58131:
			if (!c.isBanking) {
				c.getPA().removeAllWindows();
				return;
			}
			if (c.getBankPin().requiresUnlock()) {
				c.isBanking = false;
				c.getBankPin().open(2);
				return;
			}
			int tabId = actionButtonId == 58042 ? 0
					: actionButtonId == 58054 ? 1
							: actionButtonId == 58065 ? 2
									: actionButtonId == 58076 ? 3
											: actionButtonId == 58087 ? 4
													: actionButtonId == 58098 ? 5
															: actionButtonId == 58109 ? 6
																	: actionButtonId == 58120 ? 7
																			: actionButtonId == 58131 ? 8 : -1;
			if (tabId <= -1 || tabId > 8)
				return;
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset(tabId);
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset();
				return;
			}
			BankTab tab = c.getBank().getBankTab(tabId);
			if (tab.getTabId() == c.getBank().getCurrentBankTab().getTabId())
				return;
			if (tab.size() <= 0 && tab.getTabId() != 0) {
				c.sendMessage("Drag an item into the new tab slot to create a tab.");
				return;
			}
			c.getBank().setCurrentBankTab(tab);
			c.getPA().openUpBank();
			break;

		case 58053:
		case 58064:
		case 58075:
		case 58086:
		case 58097:
		case 58108:
		case 58119:
		case 58130:
			if (c.getPA().viewingOtherBank) {
				c.getPA().resetOtherBank();
				return;
			}
			if (!c.isBanking) {
				c.getPA().removeAllWindows();
				return;
			}
			if (c.getBankPin().requiresUnlock()) {
				c.isBanking = false;
				c.getBankPin().open(2);
				return;
			}
			tabId = actionButtonId == 58053 ? 1
					: actionButtonId == 58064 ? 2
							: actionButtonId == 58075 ? 3
									: actionButtonId == 58086 ? 4
											: actionButtonId == 58097 ? 5
													: actionButtonId == 58108 ? 6
															: actionButtonId == 58119 ? 7
																	: actionButtonId == 58130 ? 8 : -1;
			tab = c.getBank().getBankTab(tabId);
			if (tab == null || tab.getTabId() == 0 || tab.size() == 0) {
				c.sendMessage("You cannot collapse this tab.");
				return;
			}
			if (tab.size() + c.getBank().getBankTab()[0].size() >= Config.BANK_SIZE) {
				c.sendMessage("You cannot collapse this tab. The contents of this tab and your");
				c.sendMessage("main tab are greater than " + Config.BANK_SIZE + " unique items.");
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset();
				return;
			}
			for (BankItem item : tab.getItems()) {
				c.getBank().getBankTab()[0].add(item);
			}
			tab.getItems().clear();
			if (tab.size() == 0) {
				c.getBank().setCurrentBankTab(c.getBank().getBankTab(0));
			}
			c.getPA().openUpBank();
			break;

		case 58041:
		case 58052:
		case 58063:
		case 58074:
		case 58085:
		case 58096:
		case 58107:
		case 58118:
		case 58129:
			if (c.getPA().viewingOtherBank) {
				c.getPA().resetOtherBank();
				return;
			}
			if (!c.isBanking) {
				c.getPA().removeAllWindows();
				return;
			}
			if (c.getBankPin().requiresUnlock()) {
				c.isBanking = false;
				c.getBankPin().open(2);
				return;
			}
			if (c.getBank().getBankSearch().isSearching()) {
				c.getBank().getBankSearch().reset();
				return;
			}
			tabId = actionButtonId == 58041 ? 0
					: actionButtonId == 58052 ? 1
							: actionButtonId == 58063 ? 2
									: actionButtonId == 58074 ? 3
											: actionButtonId == 58085 ? 4
													: actionButtonId == 58096 ? 5
															: actionButtonId == 58107 ? 6
																	: actionButtonId == 58118 ? 7
																			: actionButtonId == 58129 ? 8 : -1;
			tab = c.getBank().getBankTab(tabId);
			long value = 0;
			if (tab == null || tab.size() == 0)
				return;
			for (BankItem item : tab.getItems()) {
				long tempValue = item.getId() - 1 == 995 ? 1 : ShopAssistant.getItemShopValue(item.getId() - 1);
				value += tempValue * item.getAmount();
			}
			c.sendMessage("<col=255>The total networth of tab " + tab.getTabId() + " is </col><col=600000>"
					+ Long.toString(value) + " gp</col>.");
			break;

		case 5656:
		case 22024:
			c.getPA().openUpBank();
			break;

		case 58010:
			c.takeAsNote = !c.takeAsNote;
			break;

		case 2812:
			c.antiqueSelect = 0;
			c.sendMessage("You select Attack");
			break;

			/** Start Achievement Interface - Grant **/
			// Opening Interface
		case 29158:
			if (c.inWild() || c.inDuelArena() || World.getWorld().getMultiplayerSessionListener().inAnySession(c)
					|| c.underAttackBy > 0) {
				c.sendMessage("Please finish what you are doing before viewing your achievements.");
				return;
			}
			c.getAchievements().currentInterface = 0;
			c.getAchievements().drawInterface(0);
			break;

		case 46006:
			c.getAchievements().currentInterface = 0;
			c.getAchievements().drawInterface(0);
			break;

		case 46007:
			c.getAchievements().currentInterface = 1;
			c.getAchievements().drawInterface(1);
			break;

		case 46008:
			c.getAchievements().currentInterface = 2;
			c.getAchievements().drawInterface(2);
			break;

			// Closing
		case 36002:
			c.getPA().removeAllWindows();
			break;
			/** End Achievement Interface - Grant **/
			// case 113248: //Spawntab
			// c.getPA().sendFrame171(0, 36200);
			// c.setSidebarInterface(2, 36200); //638
			// break;
			// case 141112:
			// c.setSidebarInterface(2, 638); //638
			// c.getPA().sendFrame171(1, 36200);
			// c.getPA().sendFrame126("Name", 36202);
			// c.getPA().sendFrame126("Amount", 36205);
			// break;

		case 42018:
		case 42019:
		case 42020:
		case 42021:
			int index = actionButtonId - 42018;
			String[] removed = c.getSlayer().getRemoved();
			if (index < 0 || index > removed.length - 1) {
				return;
			}
			if (removed[index].isEmpty()) {
				c.sendMessage("There is no task in this slow that is being blocked.");
				return;
			}
			removed[index] = "";
			c.getSlayer().setRemoved(removed);
			c.getSlayer().updateCurrentlyRemoved();
			break;

		case 42012:
			c.getSlayer().cancelTask();
			break;
		case 42013:
			c.getSlayer().removeTask();
			break;

		case 41005:
		case 41505:
		case 42005:
			if (c.interfaceId != 41000)
				c.getSlayer().handleInterface("buy");
			break;

		case 41007:
		case 41507:
		case 42007:
			if (c.interfaceId != 41500)
				c.getSlayer().handleInterface("learn");
			break;

		case 41009:
		case 41509:
		case 42009:
			if (c.interfaceId != 42000)
				c.getSlayer().handleInterface("assignment");
			break;

		case 41502:
		case 42002:
		case 41002:
			c.getPA().removeAllWindows();
			break;
		case 64502:
			c.getPA().removeAllWindows();
			for (int i = 0; i < 12; i++) {
				c.getPA().itemOnInterface(-1, -1, 64503, i);
			}
			break;
		case 29164:
			break;
		case 29165:
			// c.forcedChat("My Hunter killstreak is: " +
			// c.getKillstreak().getAmount(Killstreak.Type.HUNTER) + " and my Rogue
			// killstreak is: "
			// + c.getKillstreak().getAmount(Killstreak.Type.ROGUE) + " ");
			break;
		case 29163:
			break;
			// case 113238:
			/*
			 * if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) { return; } //
			 * c.getDH().sendDialogues(12000, -1); for (int i = 8144; i < 8195; i++) {
			 * c.getPA().sendFrame126("", i); } int[] frames = { 8149, 8150, 8151, 8152,
			 * 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164, 8165,
			 * 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175, 8176, 8177, 8178,
			 * 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191,
			 * 8192, 8193, 8194 }; c.getPA().sendFrame126("@dre@Kill Tracker for @blu@" +
			 * c.playerName + "", 8144); c.getPA().sendFrame126("", 8145);
			 * c.getPA().sendFrame126("@blu@Total kills@bla@ - " +
			 * c.getNpcDeathTracker().getTotal() + "", 8147); c.getPA().sendFrame126("",
			 * 8148); int frameIndex = 0; for (Entry<String, Integer> entry :
			 * c.getNpcDeathTracker().getTracker().entrySet()) { if (entry == null) {
			 * continue; } if (frameIndex > frames.length - 1) { break; } if
			 * (entry.getValue() > 0) { c.getPA().sendFrame126("@blu@" +
			 * Misc.capitalize(entry.getKey().toLowerCase()) + ": @red@" +
			 * entry.getValue(), frames[frameIndex]); frameIndex++; } }
			 * c.getPA().showInterface(8134); break;
			 */
			// break;

		case 29305:
			c.getDiaryManager().getVarrockDiary().display();
			break;
		case 29306:
			c.getDiaryManager().getArdougneDiary().display();
			break;
		case 29307:
			c.getDiaryManager().getDesertDiary().display();
			break;
		case 29308:
			c.getDiaryManager().getFaladorDiary().display();
			break;
		case 29309:
			c.getDiaryManager().getFremennikDiary().display();
			break;
		case 29310:
			c.getDiaryManager().getKandarinDiary().display();
			break;
		case 29311:
			c.getDiaryManager().getKaramjaDiary().display();
			break;
		case 29312:
			c.getDiaryManager().getLumbridgeDraynorDiary().display();
			break;
		case 29313:
			c.getDiaryManager().getMorytaniaDiary().display();
			break;
		case 29314:
			c.getDiaryManager().getWesternDiary().display();
			break;
		case 29318:
			c.getDiaryManager().getWildernessDiary().display();
			break;

		case 10225:
			long milliseconds = (long) c.playTime * 600;
			long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
			long hours = TimeUnit.MILLISECONDS.toHours(milliseconds - TimeUnit.DAYS.toMillis(days));
			String time = days + " days, " + hours + " hours.";
			c.forcedChat("I've played Valius for a total of : " + time);
			break;
		case 10226:
			c.forcedChat("My rank is: "+c.getRights().getPrimary().toString());
			break;
		case 10227:
			if (c.getRights().isOrInherits(Right.EXTREME)) {
				c.forcedChat("My game mode is: Extreme (15x)");
			} else if (c.getRights().isOrInherits(Right.CLASSIC)) {
				c.forcedChat("My game mode is: Classic (5x)");
			} else if (c.getRights().isOrInherits(Right.ELITE)) {
				c.forcedChat("My game mode is: Elite (2x)");
			} else {
				c.forcedChat("My game mode is: Normal (30x)");
			}
			break;
		case 10228:
			c.forcedChat("My drop rate bonus is: "+ DropManager.getModifier1(c) + "%");
			break;
			
		case 10229:
			c.forcedChat("My title is: "+c.getTitles().getCurrentTitle());
			break;
		case 10230:
			DecimalFormat df = new DecimalFormat("#.##");
			double ratio = ((double) c.killcount) / ((double) c.deathcount);
			//c.forcedChat("You have "+c.killcount+ " kills and "+c.deathcount+" deaths.");
			c.forcedChat("KDR: "+ratio);
			break;
		case 10231:
			c.forcedChat("I have donated: $" + c.amDonated + ".");
			break;
		case 10232:
			c.forcedChat("I currently have: " + c.pkp + " PK Points.");
			break;
		case 10233:
			c.forcedChat("I currently have: " + c.getSlayer().getPoints() + " Slayer Points.");
			c.sendMessage("@blu@I currently have: " + c.getSlayer().getConsecutiveTasks() + " consecutive slayer tasks.");
			break;
		case 10234:
			c.forcedChat("I currently have: " + c.pcPoints + " PC Points.");
			break;
		case 10235:
			c.forcedChat("I currently have: " + c.skillPoints + " Skill Points.");
			break;
		case 10236:
			c.forcedChat("I currently have: " + c.votePoints + " Vote Points.");
			break;
		case 10237:
			c.forcedChat("I currently have: " + c.pvmPoints + " PVM Points.");
			break;
		case 10238:
			c.forcedChat("I currently have: " + c.bossPoints + " Boss Points.");
			break;
		case 10239:
			c.forcedChat("I currently have: " + c.loyaltyPoints + " Loyalty Points.");
			break;
		case 47514:
			c.getPA().sendFrame126("https://www.valius.net/community", 12000);
			break;
		case 47515:
			c.getPA().sendFrame126("https://www.discord.gg/u3yqac9", 12000);
			break;
		case 47516:
			c.getPA().sendFrame126("https://valius.net/community/index.php?/donate/", 12000);
		case 47517:
			c.getPA().sendFrame126("https://www.youtube.com/channel/UCtJER2yNaPEWTM_fcZOWqUQ", 12000);
			break;
		case 47518:
			c.getPA().sendFrame126("http://www.valius.net/community/index.php?/topic/178-valius-price-guide", 12000);
			break;
		case 47519:
			c.getPA().sendFrame126("http://valius.net/community/index.php?/topic/178-valius-price-guide/", 12000);
			break;
		case 47502:
			List<Player> staff = PlayerHandler.nonNullStream().filter(Objects::nonNull).filter(p -> p.getRights().isOrInherits(Right.HELPER)).collect(Collectors.toList());
			c.sendMessage("@red@You can also type ::help to report something.");
			if (staff.size() > 0) {
				PlayerHandler.sendMessage("@blu@[Help] " + Misc.capitalize(c.playerName) + "" + " needs help, PM or TELEPORT and help them.", staff);
				c.getPA().logStuck();

			} else {
				c.sendMessage("@red@You've activated the help command but there are no staff-members online.");
				c.sendMessage("@red@Please try contacting a staff on the forums and discord and they will respond ASAP.");
				c.sendMessage("@red@You can also type ::help to report something.");
			}
			break;
		case 29172:
			c.forcedChat("I currently have: " + c.getArenaPoints() + " Mage Arena Points.");
			break;
		case 29174:
			c.sendMessage("@blu@I currently have: " + c.getSlayer().getConsecutiveTasks() + " consecutive slayer tasks.");
			break;
		case 2813:
			c.antiqueSelect = 2;
			c.sendMessage("You select Strength");
			break;
		case 2814:
			c.antiqueSelect = 4;
			c.sendMessage("You select Ranged");
			break;
		case 2815:
			c.antiqueSelect = 6;
			c.sendMessage("You select Magic");
			break;
		case 2816:
			c.antiqueSelect = 1;
			c.sendMessage("You select Defence");
			break;
		case 2817:
			c.antiqueSelect = 3;
			c.sendMessage("You select Hitpoints");
			break;
		case 2818:
			c.antiqueSelect = 5;
			c.sendMessage("You select Prayer");
			break;
		case 2819:
			c.antiqueSelect = 16;
			c.sendMessage("You select Agility");
			break;
		case 2820:
			c.antiqueSelect = 15;
			c.sendMessage("You select Herblore");
			break;
		case 2821:
			c.antiqueSelect = 17;
			c.sendMessage("You select Thieving");
			break;
		case 2822:
			c.antiqueSelect = 12;
			c.sendMessage("You select Crafting");
			break;
		case 2823:
			c.antiqueSelect = 20;
			c.sendMessage("You select Runecrafting");
			break;
		case 12034:
			c.antiqueSelect = 18;
			c.sendMessage("You select Slayer");
			break;
		case 13914:
			c.antiqueSelect = 19;
			c.sendMessage("You select Farming");
			break;
		case 2824:
			c.antiqueSelect = 14;
			c.sendMessage("You select Mining");
			break;
		case 2825:
			c.antiqueSelect = 13;
			c.sendMessage("You select Smithing");
			break;
		case 2826:
			c.antiqueSelect = 10;
			c.sendMessage("You select Fishing");
			break;
		case 2827:
			c.antiqueSelect = 7;
			c.sendMessage("You select Cooking");
			break;
		case 2828:
			c.antiqueSelect = 11;
			c.sendMessage("You select Firemaking");
			break;
		case 2829:
			c.antiqueSelect = 8;
			c.sendMessage("You select Woodcutting");
			break;
		case 2830:
			c.antiqueSelect = 9;
			c.sendMessage("You select Fletching");
			break;
		case 2831:
			if (c.usingLamp) {
				if (c.antiqueLamp && !c.normalLamp) {
					c.usingLamp = false;
					c.getPA().addSkillXP(13100000, c.antiqueSelect, true);
					c.getItems().deleteItem2(4447, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
				if (c.normalLamp && !c.antiqueLamp) {
					int EXP_AWARDED = 5000;

					if (Config.BETA_MODE) {
						EXP_AWARDED += EXP_AWARDED/2;
						int currentExp = c.getSkills().getExperience(Skill.forId(c.antiqueSelect));
						if (currentExp > EXP_AWARDED) {
							EXP_AWARDED += currentExp / 2;
							c.sendMessage("During beta, lamps give increased exp!");
						}

					}

					c.usingLamp = false;
					c.getPA().addSkillXP(EXP_AWARDED, c.antiqueSelect, true);
					c.getItems().deleteItem2(2528, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.sendMessage("...and you gain some experience!");
					c.getPA().closeAllWindows();
				}
			} else {
				c.sendMessage("You must rub a lamp to gain the experience.");
				return;
			}
			break;

			/*
			 * case 28172: if (c.expLock == false) { c.expLock = true; c.sendMessage(
			 * "Your experience is now locked. You will not gain experience.");
			 * c.getPA().sendFrame126( "@whi@EXP: @gre@LOCKED", 7340); } else { c.expLock =
			 * false; c.sendMessage(
			 * "Your experience is now unlocked. You will gain experience.");
			 * c.getPA().sendFrame126( "@whi@EXP: @gre@UNLOCKED", 7340); } break;
			 */
		case 7383:
			if (c.getSlayer().getTask().isPresent()) {
				c.sendMessage("You do not have a task, please talk with a slayer master!");
			} else {
				c.forcedText = "I must slay another " + c.getSlayer().getTaskAmount() + " "
						+ c.getSlayer().getTask().get().getPrimaryName() + ".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			}
			break;


		case 47508:
			c.getPA().showInterface(36000);
			c.getAchievements().drawInterface(0);
			break;
		case 47509:
			c.getPA().showInterface(39500);
			break;

			/* End Quest */
		case 3987:
			Smelting.startSmelting(c, "bronze", "ONE", "FURNACE");
			break;
		case 3986:
			Smelting.startSmelting(c, "bronze", "FIVE", "FURNACE");
			break;
		case 2807:
			Smelting.startSmelting(c, "bronze", "TEN", "FURNACE");
			break;
		case 2414:
			Smelting.startSmelting(c, "bronze", "ALL", "FURNACE");
			break;
		case 3991:
			Smelting.startSmelting(c, "iron", "ONE", "FURNACE");
			break;
		case 3990:
			Smelting.startSmelting(c, "iron", "FIVE", "FURNACE");
			break;
		case 3989:
			Smelting.startSmelting(c, "iron", "TEN", "FURNACE");
			break;
		case 3988:
			Smelting.startSmelting(c, "iron", "ALL", "FURNACE");
			break;
		case 3995:
			Smelting.startSmelting(c, "silver", "ONE", "FURNACE");
			break;
		case 3994:
			Smelting.startSmelting(c, "silver", "FIVE", "FURNACE");
			break;
		case 3993:
			Smelting.startSmelting(c, "silver", "TEN", "FURNACE");
			break;
		case 3992:
			Smelting.startSmelting(c, "silver", "ALL", "FURNACE");
			break;
		case 3999:
			Smelting.startSmelting(c, "steel", "ONE", "FURNACE");
			break;
		case 3998:
			Smelting.startSmelting(c, "steel", "FIVE", "FURNACE");
			break;
		case 3997:
			Smelting.startSmelting(c, "steel", "TEN", "FURNACE");
			break;
		case 3996:
			Smelting.startSmelting(c, "steel", "ALL", "FURNACE");
			break;
		case 4003:
			Smelting.startSmelting(c, "gold", "ONE", "FURNACE");
			break;
		case 4002:
			Smelting.startSmelting(c, "gold", "FIVE", "FURNACE");
			break;
		case 4001:
			Smelting.startSmelting(c, "gold", "TEN", "FURNACE");
			break;
		case 4000:
			Smelting.startSmelting(c, "gold", "ALL", "FURNACE");
			break;
		case 7441:
			Smelting.startSmelting(c, "mithril", "ONE", "FURNACE");
			break;
		case 7440:
			Smelting.startSmelting(c, "mithril", "FIVE", "FURNACE");
			break;
		case 6397:
			Smelting.startSmelting(c, "mithril", "TEN", "FURNACE");
			break;
		case 4158:
			Smelting.startSmelting(c, "mithril", "ALL", "FURNACE");
			break;
		case 7446:
			Smelting.startSmelting(c, "adamant", "ONE", "FURNACE");
			break;
		case 7445:
			Smelting.startSmelting(c, "adamant", "FIVE", "FURNACE");
			break;
		case 7443:
			Smelting.startSmelting(c, "adamant", "TEN", "FURNACE");
			break;
		case 7442:
			Smelting.startSmelting(c, "adamant", "ALL", "FURNACE");
			break;
		case 7450:
			Smelting.startSmelting(c, "rune", "ONE", "FURNACE");
			break;
		case 7449:
			Smelting.startSmelting(c, "rune", "FIVE", "FURNACE");
			break;
		case 7448:
			Smelting.startSmelting(c, "rune", "TEN", "FURNACE");
			break;
		case 7447:
			Smelting.startSmelting(c, "rune", "ALL", "FURNACE");
			break;

			/*
			 * case 58025: case 58026: case 58027: case 58028: case 58029: case 58030: case
			 * 58031: case 58032: case 58033: case 58034:
			 * c.getBankPin().pinEnter(actionButtonId); break;
			 */

		case 13720:
			Cooking.getAmount(c, 1);
			break;
		case 13719:
			Cooking.getAmount(c, 5);
			break;
		case 13718:
			Cooking.getAmount(c, 10);
			break;
		case 13717:
			Cooking.getAmount(c, 28);
			break;
		case 8654:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.attackSkill = true;
			} else {
				c.getSI().attackComplex(1);
				c.getSI().selected = 0;
			}
			break;
		case 8657:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.strengthSkill = true;
			} else {
				c.getSI().strengthComplex(1);
				c.getSI().selected = 1;
			}
			break;
		case 8660:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.defenceSkill = true;
			} else {
				c.getSI().defenceComplex(1);
				c.getSI().selected = 2;
			}
			break;
		case 8663:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.rangeSkill = true;
			} else {
				c.getSI().rangedComplex(1);
				c.getSI().selected = 3;
			}
			break;
		case 8666:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.prayerSkill = true;
			} else {
				c.getSI().prayerComplex(1);
				c.getSI().selected = 4;
			}
			break;
		case 8669:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.mageSkill = true;
			} else {
				c.getSI().magicComplex(1);
				c.getSI().selected = 5;
			}
			break;
		case 8655:
			if (c.inClanWarsSafe()) {
				c.outStream.writePacketHeader(27);
				c.healthSkill = true;
			} else {
				c.getSI().hitpointsComplex(1);
				c.getSI().selected = 7;
			}
			break;
		case 8672:
			c.getSI().runecraftingComplex(1);
			c.getSI().selected = 6;
			break;
		case 8658:
			c.getSI().agilityComplex(1);
			c.getSI().selected = 8;
			break;
		case 8661:
			c.getSI().herbloreComplex(1);
			c.getSI().selected = 9;
			break;
		case 8664:
			c.getSI().thievingComplex(1);
			c.getSI().selected = 10;
			break;
		case 8667:
			c.getSI().craftingComplex(1);
			c.getSI().selected = 11;
			break;
		case 8670:
			c.getSI().fletchingComplex(1);
			c.getSI().selected = 12;
			break;
		case 12162:
			c.getSI().slayerComplex(1);
			c.getSI().selected = 13;
			break;
		case 8656:
			c.getSI().miningComplex(1);
			c.getSI().selected = 14;
			break;
		case 8659:
			c.getSI().smithingComplex(1);
			c.getSI().selected = 15;
			break;
		case 8662:
			c.getSI().fishingComplex(1);
			c.getSI().selected = 16;
			break;
		case 8665:
			c.getSI().cookingComplex(1);
			c.getSI().selected = 17;
			break;
		case 8668:
			c.getSI().firemakingComplex(1);
			c.getSI().selected = 18;
			break;
		case 8671:
			c.getSI().woodcuttingComplex(1);
			c.getSI().selected = 19;
			break;
		case 13928:
			c.getSI().farmingComplex(1);
			c.getSI().selected = 20;
			break;
		case 18829:
			c.getSI().hunterComplex(1);
			c.getSI().selected = 21;
			break;

		case 8846:
			c.getSI().menuCompilation(1);
			break;

		case 8823:
			c.getSI().menuCompilation(2);
			break;

		case 8824:
			c.getSI().menuCompilation(3);
			break;

		case 8827:
			c.getSI().menuCompilation(4);
			break;

		case 8837:
			c.getSI().menuCompilation(5);
			break;

		case 8840:
			c.getSI().menuCompilation(6);
			break;

		case 8843:
			c.getSI().menuCompilation(7);
			break;

		case 8859:
			c.getSI().menuCompilation(8);
			break;

		case 8862:
			c.getSI().menuCompilation(9);
			break;

		case 8865:
			c.getSI().menuCompilation(10);
			break;

		case 15303:
			c.getSI().menuCompilation(11);
			break;

		case 15306:
			c.getSI().menuCompilation(12);
			break;
		case 15307:
			c.getSI().menuCompilation(13);
			break;
			// case 73113: // tab 13
			// c.getSI().menuCompilation(21);
			// break;

		case 22845:
		case 24115:
		case 24010:
			if (c.autoRet == 0) {
				c.autoRet = 1;
			} else {
				c.autoRet = 0;
			}
			System.out.println("Auto ret: " + c.autoRet);
			c.getPA().sendFrame36(172, c.autoRet);
			break;
		case 10403:
			c.updateQuestTab();
			break;
			// case 58253:
		case 27653:
			/*
			 * if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) { return; }
			 * c.getPA().showInterface(15106); // c.getItems().writeBonus();
			 */
			c.getPA().showInterface(15106);
			break;
		case 27654:
			if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
				return;
			}
			c.getPA().sendFrame126("Items Kept on Death", 17103);
			c.StartBestItemScan(c);
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++) {
				c.getPA().sendFrame34a(10494, -1, k, 1);
			}
			for (int k = 0; k < 39; k++) {
				c.getPA().sendFrame34a(10600, -1, k, 1);
			}
			if (c.WillKeepItem1 > 0) {
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0, c.WillKeepAmt1);
			}
			if (c.WillKeepItem2 > 0) {
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1, c.WillKeepAmt2);
			}
			if (c.WillKeepItem3 > 0) {
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2, c.WillKeepAmt3);
			}
			if (c.WillKeepItem4 > 0 && c.prayerActive[10]) {
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			}
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;

		case 15108:
			c.getPA().removeAllWindows();
			break;

		case 6666:
		case 2004:
			c.getPA().resetAutocast();
			break;
		case 349:
		case 350:
		case 353:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 6914
							|| c.playerEquipment[c.playerWeapon] == 22296 || c.playerEquipment[c.playerWeapon] == 21006 || c.playerEquipment[c.playerWeapon] == 22550 || c.playerEquipment[c.playerWeapon] == 22547
							|| c.playerEquipment[c.playerWeapon] == 33530 || c.playerEquipment[c.playerWeapon] == 33535 || c.playerEquipment[c.playerWeapon] == 22552 || c.playerEquipment[c.playerWeapon] == 22555 || c.playerEquipment[c.playerWeapon] == 7809 || c.playerEquipment[c.playerWeapon] == 33346 || c.playerEquipment[c.playerWeapon] == 33894 || c.playerEquipment[c.playerWeapon] == 33095 || c.playerEquipment[c.playerWeapon] == 33819|| c.playerEquipment[c.playerWeapon] == 33820 || c.playerEquipment[c.playerWeapon] == 33821 || c.playerEquipment[c.playerWeapon] == 33279 || c.playerEquipment[c.playerWeapon] == 22335 || c.playerEquipment[c.playerWeapon] == 33277 || c.playerEquipment[c.playerWeapon] == 33761 || c.playerEquipment[c.playerWeapon] == 33010 ||  c.playerEquipment[c.playerWeapon] == 12904 || c.playerEquipment[c.playerWeapon] == 22296 || c.playerEquipment[c.playerWeapon] == 22555
						|| c.playerEquipment[c.playerWeapon] == 4862 || c.playerEquipment[c.playerWeapon] == 4863 || c.playerEquipment[c.playerWeapon] == 33553 || c.playerEquipment[c.playerWeapon] == 4864 || c.playerEquipment[c.playerWeapon] == 4865 || c.playerEquipment[c.playerWeapon] == 4710) {
						c.setSidebarInterface(0, 1689);
					} else {
						c.sendMessage("You can't autocast ancients without a proper staff.");
					}
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170 || c.playerEquipment[c.playerWeapon] == 21255) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				} else {
					c.sendMessage("You need to be on a different spellbook to autocast!");
				}
			}
			break;
		case 29480:
			c.getDiaryManager().getVarrockDiary().display();
			break;
		case 29481:
			c.getDiaryManager().getArdougneDiary().display();
			break;
		case 29482:
			c.getDiaryManager().getDesertDiary().display();
			break;
		case 29483:
			c.getDiaryManager().getFaladorDiary().display();
			break;
		case 29484:
			c.getDiaryManager().getFremennikDiary().display();
			break;
		case 29485:
			c.getDiaryManager().getKandarinDiary().display();
			break;
		case 29486:
			c.getDiaryManager().getKaramjaDiary().display();
			break;
		case 29487:
			c.getDiaryManager().getLumbridgeDraynorDiary().display();
			break;
		case 29488:
			c.getDiaryManager().getMorytaniaDiary().display();
			break;
		case 29489:
			c.getDiaryManager().getWesternDiary().display();
			break;
		case 29490:
			c.getDiaryManager().getWildernessDiary().display();
			break;
		case 2471:
			ThreeOptions.handleOption1(c);
			break;
		case 2472:
			ThreeOptions.handleOption2(c);
			break;
		case 2473:
			ThreeOptions.handleOption3(c);
			break;
			/* VENG */
		case 30306:
			c.getPA().castVengeance();
			break;

			/**
			 * Specials *
			 */

		case 7487:
			DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (session != null) {
				if (session.getRules().contains(Rule.NO_SPECIAL_ATTACK)) {
					c.sendMessage("You are not permitted to activate special attacks during a duel.");
					return;
				}
			}
			Special special = Specials.DRAGON_BATTLEAXE.getSpecial();
			if (c.specAmount < special.getRequiredCost()) {
				c.sendMessage("You don't have the special amount to use this.");
				return;
			}
			if (!Arrays.stream(special.getWeapon()).anyMatch(axe -> c.getItems().isWearingItem(axe))) {
				return;
			}
			special.activate(c, null, null);
			c.specAmount -= special.getRequiredCost();
			c.usingSpecial = false;
			c.getItems().updateSpecialBar();
			break;

		case 7612:
			c.specBarId = 7636;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 7462:
			if (c.playerEquipment[c.playerWeapon] == 4153 || c.playerEquipment[c.playerWeapon] == 12848) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 12311:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 7537:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

			/**
			 * Dueling *
			 */
			/*
			 * case 26065: // no forfeit case 26040: c.duelSlot = -1;
			 * c.getTradeAndDuel().selectRule(0); break;
			 */

		case 6721:
		case 6696:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.FORFEIT);
			break;

		case 6722:
		case 6704:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			if (!duelSession.getRules().contains(Rule.FORFEIT)) {
				duelSession.toggleRule(c, Rule.FORFEIT);
			}
			duelSession.toggleRule(c, Rule.NO_MOVEMENT);
			break;

		case 6725:
		case 6698:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_RANGE);
			break;

		case 6726:
		case 6699:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_MELEE);
			break;

		case 6727:
		case 6697:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_MAGE);
			break;

		case 6728:
		case 6701:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_DRINKS);
			break;

		case 6729:
		case 6702:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_FOOD);
			break;

		case 6730:
		case 6703:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_PRAYER);
			break;

		case 6732:
		case 6731:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.OBSTACLES);
			break;

		case 670:
		case 669:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			if (duelSession.getRules().contains(Rule.WHIP_AND_DDS)) {
				duelSession.toggleRule(c, Rule.WHIP_AND_DDS);
				return;
			}
			if (!Rule.WHIP_AND_DDS.getReq().get().meets(c)
					&& !duelSession.getRules().contains(Rule.NO_SPECIAL_ATTACK)) {
				c.getPA().sendString("You must have a whip and dragon dagger to select this.", 6684);
				return;
			}
			if (!Rule.WHIP_AND_DDS.getReq().get().meets(duelSession.getOther(c))) {
				c.getPA().sendString("Your opponent does not have a whip and dragon dagger.", 6684);
				return;
			}
			if (duelSession.getStage().getStage() != MultiplayerSessionStage.OFFER_ITEMS) {
				c.sendMessage("You cannot change rules whilst on the second interface.");
				return;
			}
			duelSession.getRules().reset();
			for (Rule rule : Rule.values()) {
				index = rule.ordinal();
				if (index == 3 || index == 8 || index == 10 || index == 14) {
					continue;
				}
				duelSession.toggleRule(c, rule);
			}
			break;

		case 7816:
		case 7817:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_SPECIAL_ATTACK);
			break;

		case 13813:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_HELM);
			break;

		case 13814:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_CAPE);
			break;

		case 13815:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_AMULET);
			break;

		case 13817:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_WEAPON);
			break;

		case 13818:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_BODY);
			break;

		case 13819:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_SHIELD);
			break;

		case 13820:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_LEGS);
			break;

		case 13823:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_GLOVES);
			break;

		case 13822:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_BOOTS);
			break;

		case 13821:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_RINGS);
			break;

		case 13816:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (Objects.isNull(duelSession)) {
				return;
			}
			duelSession.toggleRule(c, Rule.NO_ARROWS);
			break;

		case 6674:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (System.currentTimeMillis() - c.getDuel().getLastAccept() < 1000) {
				return;
			}
			c.getTrade().setLastAccept(System.currentTimeMillis());
			if (Objects.nonNull(duelSession) && duelSession instanceof DuelSession) {
				duelSession.accept(c, MultiplayerSessionStage.OFFER_ITEMS);
			}
			break;

		case 6520:
			duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
					MultiplayerSessionType.DUEL);
			if (System.currentTimeMillis() - c.getDuel().getLastAccept() < 1000) {
				return;
			}
			c.getTrade().setLastAccept(System.currentTimeMillis());
			if (Objects.nonNull(duelSession) && duelSession instanceof DuelSession) {
				duelSession.accept(c, MultiplayerSessionStage.CONFIRM_DECISION);
			}
			break;

		case 1193:
			c.usingMagic = true;
			if (c.getCombat().checkMagicReqs(48)) {
				if (System.currentTimeMillis() - c.godSpellDelay < 300000L) {
					c.sendMessage("You still feel the charge in your body!");
				} else {
					c.godSpellDelay = System.currentTimeMillis();
					c.sendMessage("You feel charged with a magical power!");
					c.gfx100(MagicData.MAGIC_SPELLS[48][3]);
					c.startAnimation(MagicData.MAGIC_SPELLS[48][2]);
					c.usingMagic = false;
				}
			}
			break;

			/*
			 * case 152: c.isRunning2 = !c.isRunning2; int frame = c.isRunning2 == true ? 1
			 * : 0; c.getPA().sendFrame36(173,frame); break;
			 */
		case 154:
			if (c.playerEquipment[c.playerCape] == -1) {
				c.sendMessage("You must be wearing a skillcape in order to do this emote.");
				return;
			}
			PlayerEmotes.performSkillcapeAnimation(c, new Item(c.playerEquipment[c.playerCape]));
			break;
		case 42507:
		case 152:
			if (Boundary.isIn(c, Boundary.ICE_PATH)) {
				c.getPA().setConfig(173, 0);
				c.getPA().setConfig(173, 0);
				return;
			}
			if (c.getRunEnergy() < 1) {
				c.isRunning = false;
				c.isRunning2 = false;
				c.getPA().setConfig(173, 0);
				c.getPA().setConfig(173, 0);
				return;
			}
			c.isRunning2 = !c.isRunning2;
			c.isRunning = !c.isRunning;
			c.getPA().sendFrame36(173, c.isRunning2 ? 1 : 0);
			c.getPA().sendFrame36(173, c.isRunning2 ? 1 : 0);
			break;

		case 12464:
			c.acceptAid = !c.acceptAid;
			c.getPA().setConfig(427, c.acceptAid ? 1 : 0);
			break;

		case 2458:
			long buttonDelay = 0;
			if (System.currentTimeMillis() - buttonDelay > 2000) {
				c.logout();
				buttonDelay = System.currentTimeMillis();
			}
			break;

		case 5386:
			c.takeAsNote = true;
			break;

		case 5387:
			c.takeAsNote = false;
			break;

		case 40001:
			if (c.lastTeleportX == 0) {
				c.sendMessage("You haven't teleported anywhere recently.");
			} else {
				c.getPA().startTeleport(c.lastTeleportX, c.lastTeleportY, c.lastTeleportZ, "modern", false);
			}
			break;
		case 7562:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Scimitar */
		case 7587:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Mace */
		case 7623:
			c.specBarId = 7636;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Battleaxe & Hatchets */
		case 7498:
			
			if(c.playerEquipment[c.playerWeapon] == 1377) {
				DuelSession session2 = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
						MultiplayerSessionType.DUEL);
				if (session2 != null) {
					if (session2.getRules().contains(Rule.NO_SPECIAL_ATTACK)) {
						c.sendMessage("You are not permitted to activate special attacks during a duel.");
						return;
					}
				}
				Special special2 = Specials.DRAGON_BATTLEAXE.getSpecial();
				if (c.specAmount < special2.getRequiredCost()) {
					c.sendMessage("You don't have the special amount to use this.");
					return;
				}
				if (!Arrays.stream(special2.getWeapon()).anyMatch(axe -> c.getItems().isWearingItem(axe))) {
					return;
				}
				special2.activate(c, null, null);
				c.specAmount -= special2.getRequiredCost();
				c.usingSpecial = false;
				c.getItems().updateSpecialBar();
				break;
			}
			c.specBarId = 7511;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Halberd $ Staff of Light */
		case 8481:
			if (c.getItems().isWearingItem(33277, c.playerWeapon)) {
				c.specBarId = 8505;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
				} else {
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
				}
			break;
			/* Spear */
		case 7662:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Godswords & 2h Swords */
		case 7687:
			c.specBarId = 7711;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Claws */
		case 7788:
			 if (c.getItems().isWearingItem(11791, c.playerWeapon)
					|| c.getItems().isWearingItem(12904, c.playerWeapon)) {
				session = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c,
						MultiplayerSessionType.DUEL);
				if (session != null) {
					if (session.getRules().contains(Rule.NO_SPECIAL_ATTACK)) {
						c.sendMessage("You are not permitted to activate special attacks during a duel.");
						return;
					}
				}
				Special sotd = Specials.STAFF_OF_THE_DEAD.getSpecial();
				if (c.specAmount >= sotd.getRequiredCost()) {
					c.specAmount -= sotd.getRequiredCost();
					sotd.activate(c, c, null);
					c.specBarId = 7812;
					c.usingSpecial = false;
					c.getItems().updateSpecialBar();
					return;
				}
			}
			c.specBarId = 7812;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Whip */
		case 12322:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Warhammer & Mauls */
		case 7473:
			if (c.playerEquipment[c.playerWeapon] == 4153 || c.playerEquipment[c.playerWeapon] == 12848) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;
			/* Pickaxe */
		case 7723:
			c.specBarId = 7736;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Bows */
		case 7548:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			/* Throwing Axe & Javelins */
		case 7637:
			c.specBarId = 7661;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
			// home teleports
		case 1195:
		case 30000:
		case 21741:
		case 19210:
			if(c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (c.getInstance() != null && c.getInstance() instanceof TheGauntlet) {
				c.sendMessage("You can't teleport while in The Gauntlet.");
				return;
			}
			c.getPA().spellTeleport(3094, 3500, 0, true);
			//c.getPA().showInterface(51000);
			//c.getTeleport().selection(c, 0);
			break;
		case 12856:
			if (c.homeTeleport >= 1 && c.homeTeleport <= 10) {
				return;
			}
			c.getPA().spellTeleport(Config.START_LOCATION_X, Config.START_LOCATION_Y, 0, true);
			break;

			// case 4171: case 50056: case 117048: if (c.homeTeleDelay <= 0) { c.homeTele =
			// 10; } else if (c.homeTeleDelay <= 0) { c.homeTele = 10; }
			//
			//
			// if (c.reset == false) { c.HomePort(); String type = c.playerMagicBook == 0 ?
			// "modern" : "ancient"; c.getPA().startTeleport(Config.EDGEVILLE_X,
			// Config.EDGEVILLE_Y, 0,
			// type); } else if (c.reset == true) { c.resetHomePort(); }

			/*
			 * case 29031: c.getDH().sendDialogues(121312, -1); break;
			 */

			/*
			 * case 51013: case 6004: case 118242: c.getDH().sendOption5("Lumbridge",
			 * "Varrock", "Camelot", "Falador", "Canifis"); c.teleAction = 20; break;
			 */
			/*
			 * case 4140: case 4143: case 4150: case 4146: case 6004: case 6005: case 29031:
			 * case 50235: case 50245: case 50253: case 51005: case 51013: case 51023: case
			 * 51031: case 51039: case 117112: case 117131: case 117154: case 117186: case
			 * 117210: case 118018: case 118042: case 118058:
			 * c.getPA().showInterface(62100); break;
			 */

		case 2429:
		case 1757:
		case 12298:
		case 5576:
		case 336:
		case 1704:
		case 1772:
		case 4454:
		case 2282:
		case 5860:
			c.usingMagic = false;
			c.fightMode = 0;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 2430:
		case 12296:
		case 5577:
		case 334:
		case 1705:
		case 8467:
		case 4686:
		case 2283:
			// case 22231: //unarmed
		case 5861:
			c.usingMagic = false;
			c.fightMode = 1;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 2431:
		case 12297:
		case 8466:
		case 1770:
		case 1755:
		case 4685:
		case 4688:
		case 4687:
		case 4452:
			c.usingMagic = false;
			c.fightMode = 3;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;

		case 2432:
		case 1756:
		case 5579:
		case 5578:
		case 335:
		case 1707:
		case 1706:
		case 8468:
		case 1771:
		case 4453:
		case 2285:
		case 2284:
		case 5862:
			c.usingMagic = false;
			c.fightMode = 2;
			if (c.autocasting) {
				c.getPA().resetAutocast();
			}
			break;
		case 42552:
			c.getPA().showInterface(53000);
			break;

		case 42524:
			c.getPA().showInterface(39000);
			break;
			/**
			 * Prayers *
			 */
		case 5609:
			c.getCombat().activatePrayer(0);
			break;
		case 5610:
			c.getCombat().activatePrayer(1);
			break;
		case 5611:
			c.getCombat().activatePrayer(2);
			break;
		case 19812:
			c.getCombat().activatePrayer(3);
			break;
		case 19814:
			c.getCombat().activatePrayer(4);
			break;
		case 5612:
			c.getCombat().activatePrayer(5);
			break;
		case 5613:
			c.getCombat().activatePrayer(6);
			break;
		case 5614:
			c.getCombat().activatePrayer(7);
			break;
		case 5615:
			c.getCombat().activatePrayer(8);
			break;
		case 5616:
			c.getCombat().activatePrayer(9);
			break;
		case 5617:
			c.getCombat().activatePrayer(10);
			break;
		case 19816:
			c.getCombat().activatePrayer(11);
			break;
		case 19818:
			c.getCombat().activatePrayer(12);
			break;
		case 5618:
			c.getCombat().activatePrayer(13);
			break;
		case 5619:
			c.getCombat().activatePrayer(14);
			break;
		case 5620:
			c.getCombat().activatePrayer(15);
			break;
		case 5621:
			c.getCombat().activatePrayer(16);
			break;
		case 5622:
			c.getCombat().activatePrayer(17);
			break;
		case 5623:
			c.getCombat().activatePrayer(18);
			break;
		case 19821:
			c.getCombat().activatePrayer(19);
			break;
		case 19823:
			c.getCombat().activatePrayer(20);
			break;
		case 683:
			c.getCombat().activatePrayer(21);
			break;
		case 684:
			c.getCombat().activatePrayer(22);
			break;
		case 685:
			c.getCombat().activatePrayer(23);
			break;
		case 39401:
			c.getCombat().activatePrayer(24);
			break;
		case 19825:
			c.getCombat().activatePrayer(25);
			break;
		case 19827:
			c.getCombat().activatePrayer(26);
			break;
		case 39404:
			c.getCombat().activatePrayer(27);
			break;

		case 39407:
			c.getCombat().activatePrayer(28);
			break;

		case 3420:
			if (!World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				c.sendMessage("You are not trading!");
				return;
			}
			if (System.currentTimeMillis() - c.getTrade().getLastAccept() < 1000) {
				return;
			}
			c.getTrade().setLastAccept(System.currentTimeMillis());
			World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.TRADE).accept(c,
					MultiplayerSessionStage.OFFER_ITEMS);
			break;

		case 3546:
			if (!World.getWorld().getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
				c.sendMessage("You are not trading!");
				return;
			}
			if (System.currentTimeMillis() - c.getTrade().getLastAccept() < 1000) {
				return;
			}
			c.getTrade().setLastAccept(System.currentTimeMillis());
			World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.TRADE).accept(c,
					MultiplayerSessionStage.CONFIRM_DECISION);
			break;

		case 32011:
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 32003:
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to agree before you can carry on.");
			}
			break;
		case 32006:
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
			/* End Rules Interface Buttons */
			/* Player Options */
		case 19120:
		case 42551:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 19128:
		case 42542:
		case 957:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 19124:
		case 42541:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 19132:
		case 42506:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 19136:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 19145:
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 19147:
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 19148:
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 19149:
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 19150:
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 19151:
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 19152:
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 19153:
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 11100:
			c.startAnimation(0x558);
			break;
			/*
			 * case 72254: //c.startAnimation(3866); break; /* END OF EMOTES
			 */

		case 6161:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon],
					ItemAssistant.getItemName(c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 938 :
			// c.playerMagicBook == 1 ? 838 : 938);
			break;

		}
		if (c.isAutoButton(actionButtonId)) {
			c.assignAutocast(actionButtonId);
		}
	}
}
