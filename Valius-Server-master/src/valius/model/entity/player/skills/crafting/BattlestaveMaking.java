package valius.model.entity.player.skills.crafting;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.entity.player.skills.crafting.CraftingData.battlestaveData;
import valius.model.entity.player.skills.crafting.CraftingData.battlestaveDialogueData;
import valius.model.items.ItemAssistant;
import valius.util.Misc;

public class BattlestaveMaking {
	
	public static void craftBattlestaveDialogue(final Player c, final int itemUsed, final int usedWith) {
		for (final battlestaveDialogueData d : battlestaveDialogueData.values()) {
			final int leather = (itemUsed == 1391 ? usedWith : itemUsed);
			String[] name = { "", "Battlestaff", "" };
			if (leather == d.getOrb()) {
				c.getPA().sendFrame164(8880);
				c.getPA().itemOnInterface(8884, 180, d.getBattlestaff());
				for (int i = 0; i < name.length; i++) {
					c.getPA().sendFrame126(name[i], 8889 + (i * 4));
				}
				c.leatherType = leather;
				c.battlestaffDialogue = true;
				return;
			}
		}
	}

	private static int amount;

	public static void craftBattlestave(final Player c, final int buttonId) {
		if (c.playerIsCrafting == true) {
			return;
		}
		for (final battlestaveData l : battlestaveData.values()) {
			if (buttonId == l.getButtonId(buttonId)) {
				if (c.leatherType == l.getOrb()) {
					if (c.getSkills().getLevel(Skill.CRAFTING) < l.getLevel()) {
						c.sendMessage("You need a crafting level of " + l.getLevel() + " to make this.");
						c.getPA().removeAllWindows();
						return;
					}
					if (!c.getItems().playerHasItem(c.leatherType, l.getHideAmount())) {
						c.sendMessage("You need " + l.getHideAmount() + " " + ItemAssistant.getItemName(c.leatherType).toLowerCase() + " to make "
								+ ItemAssistant.getItemName(l.getProduct()).toLowerCase() + ".");
						c.getPA().removeAllWindows();
						return;
					}
					//c.startAnimation(1249);
					c.getPA().removeAllWindows();
					c.playerIsCrafting = true;
					amount = l.getAmount(buttonId);
					CycleEventHandler.getSingleton().addEvent(3, c, new CycleEvent() {
						@SuppressWarnings("unused")
						@Override
						public void execute(CycleEventContainer container) {
							if (c == null) {
								container.stop();
								return;
							}
							if (c.playerIsCrafting == true) {
								if (!c.getItems().playerHasItem(c.leatherType, l.getHideAmount()) || !c.getItems().playerHasItem(1391, 1)) {
									c.sendMessage("You have run out of resources.");
									container.stop();
									return;
								}
								if (amount == 0) {
									container.stop();
									return;
								}
								c.getItems().deleteItem(1734, c.getItems().getItemSlot(1734), 1);
								c.getItems().deleteItem2(c.leatherType, l.getHideAmount());
								c.getItems().deleteItem2(1391, 1);
								c.getItems().addItem(l.getProduct(), 1);
								c.sendMessage("You make an " + ItemAssistant.getItemName(l.getProduct()) + ".");
								c.getPA().addSkillXP((int) l.getXP(), 12, true);
								if (Misc.random(20) == 0) {
					                int sPoints = Misc.random(1, 8);
					                c.skillPoints += sPoints;
					                c.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
					            }
								//c.startAnimation(1249);
								amount--;
								if (!c.getItems().playerHasItem(c.leatherType, l.getHideAmount())) {
									c.sendMessage("You have run out of resources.");
									container.stop();
									return;
								}
							} else {
								container.stop();
							}
						}

						@Override
						public void stop() {
							c.playerIsCrafting = false;
							c.battlestaffDialogue = false;
						}
					}, 2);
				}
			}
		}
	}

	/**
	 * Keep incase
	 */
//	
//	public static boolean handleActions(Player player, int buttonId) {
//		switch (buttonId) {
//		case 6198:
//			enhanceStaff(player, 5, 1391, 1397, 5);
//			break;
//		case 6199:
//			enhanceStaff(player, 10, 1391, 1395, 8);
//			break;
//		case 6200:
//			enhanceStaff(player, 30, 1391, 1399, 13);
//			break;
//		case 6201:
//			enhanceStaff(player, 40, 1391, 1393, 18);
//			break;
//		case 6202:
//			enhanceStaff(player, 60, 1391, 3053, 25);
//			break;
//		case 59244:
//			enhanceStaff(player, 85, 1391, 6562, 30);
//			break;
//		}
//		return true;
//	}
//	
//	public static void enhanceStaff(Player player, int levelReq, int toRemove, int toAdd, int exp) {
//
//		for (int i = 0; i <= 28; i++) {
//			if(player.getSkills().getLevel(12] >= levelReq) {
//				if(player.getItems().playerHasItem(toRemove, 1)) {
//					player.getItems().deleteItem(toRemove, 1);
//					player.getItems().addItem(toAdd, 1);
//					player.getPA().addSkillXP(exp * 10, 12, true);
//				} else {
//					player.sendMessage("You do not have anymore battlestaves.");
//					break;
//				}
//			} else {
//				player.sendMessage("You need a crafting level of at least "+levelReq+" to make "+ItemAssistant.getItemName(toAdd)+" battlestaves.");
//				break;
//			}
//		}
//	}
//	
//	public static void enhanceInterface(Player player) {
//		player.getPA().sendFrame246(1735, 150, 1395);
//		player.getPA().sendFrame246(1736, 150, 1399);
//		player.getPA().sendFrame246(1737, 150, 1393);
//		//player.getPA().sendFrame246(1738, 150, 3053);
//		//player.getPA().sendFrame246(15348, 150, 6562);
//		player.getPA().sendFrame126("What staff would you like to create?", 1732);
//		player.getPA().showInterface(205);
//	}

}
