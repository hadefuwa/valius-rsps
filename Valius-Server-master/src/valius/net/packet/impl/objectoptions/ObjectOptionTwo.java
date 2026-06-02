package valius.net.packet.impl.objectoptions;

import valius.clip.ObjectDef;
import valius.clip.WorldObject;
import valius.content.cannon.DwarfCannon;
import valius.model.Location;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.entity.player.skills.FlaxPicking;
import valius.model.entity.player.skills.thieving.Thieving.Stall;
import valius.net.packet.impl.objectoptions.impl.CompCapeRack;
import valius.net.packet.impl.objectoptions.impl.DarkAltar;
import valius.util.Misc;
import valius.world.World;
import valius.world.objects.GlobalObject;

/*
 * @author Matt
 * Handles all 2nd options for objects.
 */

public class ObjectOptionTwo {

	public static void handleOption(final Player c, WorldObject worldObject) {
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		int objectId = worldObject.getId();
		int objectX = worldObject.getX();
		int objectY = worldObject.getY();
		c.resetInteractingObject();
		c.getFarming().patchObjectInteraction(objectId, -1, objectX, objectY);
		if (World.getWorld().getHolidayController().clickObject(c, 2, objectId, objectX, objectY)) {
			return;
		}
		Location location = new Location(objectX, objectY, c.getHeight());
		ObjectDef def = ObjectDef.forID(objectId);
		if ((def!=null ? def.name : null)!= null && def.name.toLowerCase().contains("bank")) {
			c.getPA().openUpBank();
		}
		if (c.getRights().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 2:  "+objectId+"");

		GlobalObject object = new GlobalObject(objectId, objectX, objectY, c.getHeight());
		switch (objectId) {
		case DwarfCannon.CANNON_OBJECT_ID:
			c.cannon.pickupCannon(object);
			break;
		case 12309:
			c.getShops().openShop(14);
			break;
		case 31621:
		case 29344:
		case 33393:
			if (c.lastTeleportX == 0) {
				c.sendMessage("You haven't teleported anywhere recently.");
			} else {
				c.getPA().startTeleport(c.lastTeleportX, c.lastTeleportY, c.lastTeleportZ, "modern", false);
			}
			break;
		case 22472: // npc drop tables on valius database
			c.getPA().showInterface(39500);
			break;
			
		/* PROSPECTING ORES */
		case 1294:
			c.getDH().tree = "stronghold";
			c.getDH().sendDialogues(65, -1);
			break;

		case 1293:
			c.getDH().tree = "village";
			c.getDH().sendDialogues(65, -1);
			break;
		case 1295:
			c.getDH().tree = "grand_exchange";
			c.getDH().sendDialogues(65, -1);
			break;
		case 11388:
		case 11389:
			c.sendMessage("You carefully examine the rock... It's Amethyst!");
			break;
		case 9030:
			c.sendMessage("You carefully examine the rock... It's Gems!");
			break;
		case 11376:
		case 11377:
			c.sendMessage("You carefully examine the rock... It's Runite Ore!");
			break;
		case 11374:
		case 11375:
			c.sendMessage("You carefully examine the rock... It's Adamantite Ore!");
			break;
		case 11372:
		case 11373:
			c.sendMessage("You carefully examine the rock... It's Mithril Ore!");
			break;
		case 11370:
		case 11371:
			c.sendMessage("You carefully examine the rock... It's Gold Ore!");
			break;
		case 4676:
		case 11366:
		case 11367:
			c.sendMessage("You carefully examine the rock... It's Coal Ore!");
			break;
		case 11365:
		case 11364:
			c.sendMessage("You carefully examine the rock... It's Iron Ore!");
			break;
		case 11360:
		case 11361:
			c.sendMessage("You carefully examine the rock... It's Tin Ore!");
			break;
		case 10943:
		case 11161:
			c.sendMessage("You carefully examine the rock... It's Copper Ore!");
			break;
		case 4437:
		case 4438:
			c.sendMessage("You carefully examine the rock... It's Clay!");
			break;
			/* END OF SCUFFED PROSPECTING */
			

			
		case 27288:
			c.getPA().startTeleport2(3522, 3211, 0);
			break;
			
		case 11010:
			c.getSmithing().sendSmelting();
			break;
			
		case 29334:
			CompCapeRack.handleCapeRackInteraction(c);
		break;
			
		case 29778:
			c.sendMessage("hello");
			break;
		case 28900:
			DarkAltar.handleRechargeInteraction(c);
			break;
		
		case 7811:
			if (!c.inClanWarsSafe()) {
				return;
			}
			c.getShops().openShop(115);
			break;
		/**
		 * Iron Winch - peek
		 */
		case 23104:
			c.getDH().sendDialogues(110, 5870);
			break;
			
		case 2118:
			c.getPA().movePlayer(3434, 3537, 0);
			break;

		case 2114:
			c.getPA().movePlayer(3433, 3537, 1);
			break;
		case 25824:
			c.turnPlayerTo(objectX, objectY);
			c.getDH().sendDialogues(40, -1);
			break;
		case 26260:
			c.getDH().sendDialogues(55874, -1);
			break;
		case 14896:
			c.turnPlayerTo(objectX, objectY);
			FlaxPicking.getInstance().pick(c, new Location(objectX, objectY, c.getHeight()));
			break;
		
		case 3840: // Compost Bin
			c.getFarming().handleCompostRemoval();
		break;
		case 4874:
		case 11730:
			c.getThieving().steal(Stall.Crafting, objectId, location);
			break;
		case 4877:
		case 11731:
			c.getThieving().steal(Stall.Magic, objectId, location);
			break;
		case 4876:
			c.getThieving().steal(Stall.General, objectId, location);
			break;
		case 4878:
			c.getThieving().steal(Stall.Scimitar, objectId, location);
			break;
		case 4875:
			c.getThieving().steal(Stall.Food, objectId, location);
			break;
		case 11732:
			c.getThieving().steal(Stall.Fur, objectId, location);
			break;
			
		case 23609:
			c.getPA().movePlayer(3507, 9494, 0);
			break;
			
		case 2558:
		case 8356://streequid
			c.getPA().movePlayer(1255, 3568, 0);
			break;
		case 2557:
			if (System.currentTimeMillis() - c.lastLockPick < 1000 || c.freezeTimer > 0) {
				return;
			}
			c.lastLockPick = System.currentTimeMillis();
			if (c.getItems().playerHasItem(1523, 1)) {

				if (Misc.random(10) <= 2) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (objectX == 3044 && objectY == 3956) {
					if (c.getX() == 3045) {
						c.getPA().walkTo(-1, 0);
					} else if (c.getX() == 3044) {
						c.getPA().walkTo(1, 0);
					}

				} else if (objectX == 3038 && objectY == 3956) {
					if (c.getX() == 3037) {
						c.getPA().walkTo(1, 0);
					} else if (c.getX() == 3038) {
						c.getPA().walkTo(-1, 0);
					}
				} else if (objectX == 3041 && objectY == 3959) {
					if (c.getY() == 3960) {
						c.getPA().walkTo(0, -1);
					} else if (c.getY() == 3959) {
						c.getPA().walkTo(0, 1);
					}
				} else if (objectX == 3191 && objectY == 3963) {
					if (c.getY() == 3963) {
						c.getPA().walkTo(0, -1);
					} else if (c.getY() == 3962) {
						c.getPA().walkTo(0, 1);
					}
				} else if (objectX == 3190 && objectY == 3957) {
					if (c.getY() == 3957) {
						c.getPA().walkTo(0, 1);
					} else if (c.getY() == 3958) {
						c.getPA().walkTo(0, -1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		case 7814:
			if (c.playerMagicBook == 0) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 838);
				c.sendMessage("An ancient wisdomin fills your mind.");
			} else if (c.playerMagicBook == 1) {
				c.sendMessage("You switch to the lunar spellbook.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
			} else if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
			}
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 838);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 938);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
		/*
		 * One stall that will give different amount of money depending on your thieving level, also different amount of xp.
		 */
		case 2781:
		case 26814:
		case 11666:
		case 3044:
		case 16469:
		case 2030:
		case 24009:
		case 26300:
			c.getSmithing().sendSmelting();
			break;
			
			/**
		 * Opening the bank.
		 */
		case 24101:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
		case 25808:
		case 11744:
		case 11748:
		case 10060:
		case 24347:
		case 16700:
			c.getPA().openUpBank();
			break;

		}
	}
}
