package valius.net.packet.impl;

import valius.model.entity.player.Player;
import valius.model.shops.TabbedShop;
import valius.net.packet.PacketType;

/**
 * Bank X Items
 **/
public class BankX1 implements PacketType {

	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		if (packetType == 135) {
			c.xRemoveSlot = c.getInStream().readSignedWordBigEndian();
			c.xInterfaceId = c.getInStream().readUnsignedWordA();
			c.xRemoveId = c.getInStream().readSignedWordBigEndian();
		}
		if (c.viewingLootBag || c.addingItemsToLootBag) {
			c.getLootingBag().selectedItem = c.xRemoveId;
			c.getLootingBag().selectedSlot = c.xRemoveSlot;
		}
		if (c.viewingRunePouch) {
			c.getRunePouch().setEnterAmountVariables(c.xRemoveId, c.xInterfaceId);
		}
/* 		if (c.xInterfaceId == 3823) {
			c.getShops().sellItem(c.xRemoveId, c.xRemoveSlot, 100);// buy 100
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		} */
		/**
		 * Buy 500
		 */
		 
		if(c.xInterfaceId >= TabbedShop.BASE_TAB_INVENTORY && c.xInterfaceId <= TabbedShop.MAX_TAB_INVENTORY) {

			c.buyingX = true;
			c.getOutStream().writePacketHeader(27);
			return;
		}
		 if (c.xInterfaceId == 64016) {
			c.buyingX = true;
			c.getOutStream().writePacketHeader(27);
			//return;
        }
        if (c.xInterfaceId == 3823) {
        	c.sellingX = true;
			c.getOutStream().writePacketHeader(27);
			//return;
        }
/* 		if (c.xInterfaceId == 64016) {
			c.getShops().buyItem(c.xRemoveId, c.xRemoveSlot, 100);// buy 100
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		} */
/* 		if (c.xInterfaceId == 3900) {
			c.getShops().buyItem(c.xRemoveId, c.xRemoveSlot, 100);// buy 100
			c.xRemoveSlot = 0;
			c.xInterfaceId = 0;
			c.xRemoveId = 0;
			return;
		} */

		if (packetType == PART1) {
			c.getOutStream().writePacketHeader(27);
			c.flushOutStream();
		}

	}
}
