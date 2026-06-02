package valius.model.entity.player.skills.crafting;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.ItemUtility;
import valius.util.Misc;

public class GlassBlowing extends GlassData {

	private static int amount;

	public static void glassBlowing(final Player c, final int buttonId) {
		if (c.playerIsCrafting) {
			return;
		}
		for (final glassData g : glassData.values()) {
			if (buttonId == g.getButtonId(buttonId)) {
				if (c.getSkills().getLevel(Skill.CRAFTING) < g.getLevelReq()) {
					c.sendMessage("You need a crafting level of " + g.getLevelReq() + " to make this.");
					c.getPA().removeAllWindows();
					return;
				}
				if (!c.getItems().playerHasItem(1775, 1)) {
					c.sendMessage("You have run out of molten glass.");
					return;
				}
				c.startAnimation(884);
				c.getPA().removeAllWindows();
				c.playerIsCrafting = true;
				amount = g.getAmount(buttonId);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c == null || c.disconnected || c.getSession() == null) {
							stop();
							return;
						}
						if (c.playerIsCrafting) {
							if (amount == 0) {
								container.stop();
								return;
							}
							if (!c.getItems().playerHasItem(1775, 1)) {
								c.sendMessage("You have run out of molten glass.");
								container.stop();
								return;
							}
							c.getItems().deleteItem(1775, 1);
							c.getItems().addItem(g.getNewId(), 1);
							c.sendMessage("You make a " + ItemUtility.getItemName(g.getNewId()) + ".");
							c.getPA().addSkillXP((int) g.getXP(), 12, true);
							c.startAnimation(884);
							amount--;
							if (Misc.random(20) == 0) {
								int sPoints = Misc.random(1, 5);
				                c.skillPoints += sPoints;
				                c.sendMessage("@pur@You receive " + sPoints + " Skill Points.");
				            }
						} else {
							container.stop();
						}
					}
					@Override
					public void stop() {
						c.startAnimation(65535);
						c.playerIsCrafting = true;
					}
				}, 3);
			}
		}
	}

	public static void makeGlass(final Player c, final int itemUsed,
			final int usedWith) {
		final int blowPipeId = (itemUsed == 1785 ? usedWith : itemUsed);
		c.getPA().showInterface(11462);
		for (final glassData g : glassData.values()) {
			if (blowPipeId == g.getNewId()) {
				c.getItems().deleteItem(1759, 1);
				c.getItems().addItem(g.getNewId(), 1);
				c.getPA().addSkillXP(4, 12, true);
			}
		}
	}

}