package valius.net.packet.impl;

/**
 * @author Ryan / Lmctruck30
 */

import java.util.Objects;
import java.util.Optional;

import lombok.experimental.UtilityClass;
import valius.clip.ObjectDef;
import valius.clip.Region;
import valius.clip.WorldObject;
import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.UseItem;
import valius.model.multiplayer_session.MultiplayerSessionFinalizeType;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.model.multiplayer_session.duel.DuelSession;
import valius.net.packet.PacketType;
import valius.world.World;
import valius.world.objects.GlobalObject;

public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		/*
		 * a = ? b = ?
		 */
		int interfaceId = c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readInteger();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordBigEndian();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		int objectType = c.getInStream().readUnsignedByte();
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
		DuelSession duelSession = (DuelSession) World.getWorld().getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}

		Item item = c.getItems().getItemAtSlot(itemSlot);
		if(item == null || item.getId() == -1)
			return;
		
		Optional<WorldObject> existingObject = Region.getWorldObject(objectId, objectX, objectY, c.getHeight(), objectType);
		if(!existingObject.isPresent())
			return;
		
		ObjectDef objectDef = ObjectDef.forID(objectId);
		if(objectDef != null) {

			c.setInteractingObject(existingObject);
			int direction = existingObject.get().getFace();
			int objectWidth;
			int objectLength;
			if (direction != 1 && direction != 3) {
				objectWidth = objectDef.width;
				objectLength = objectDef.length;
			} else {
				objectWidth = objectDef.length;
				objectLength = objectDef.width;
			}
			
			Runnable onReached = () -> existingObject.ifPresent(worldObject -> {
				c.getQuestManager().onItemOnObject(item, worldObject);
				if(objectDef.name != null && objectDef.name.toLowerCase().contains("bank")) {
					int gold = c.getItems().getItemAmount(995) / 1000;
					int tokens = c.getItems().getItemAmount(13204);
					long goldl = c.getItems().getItemAmount(995) / 1000;
					long tokensl = c.getItems().getItemAmount(13204);

					if (itemId == 995) {
						if ((goldl * 1000) < 1000) {
							c.sendMessage("You need at least 1000 Gold to get Platinum.");
							return;
						}
						c.getItems().deleteItem(995, gold * 1000);
						c.getItems().addItem(13204, gold);
						c.sendMessage("You exchange your coins for platinum tokens.");
						return;

					}

					if (itemId == 13204) {
						if (tokensl * 1000 + (goldl * 1000) >= 2147000000 || tokensl * 1000 + (goldl * 1000) <= 999) {
							int max = 2147000000;
							int amount_until_max = max -= c.getItems().getItemAmount(995);
							c.getItems().deleteItem(13204, amount_until_max / 1000);
							c.getItems().addItem(995, amount_until_max);
							c.sendMessage("You cannot have more than 2147M (2,147,000,000) coins.");
							return;
						}
						c.getItems().deleteItem(13204, tokens);
						c.getItems().addItem(995, tokens * 1000);
						c.sendMessage("You exchange your platinum tokens for coins.");
						return;

					}
				}
				
				switch(objectId) {
				case 26782: // Glory recharging
					if (itemId == 1710 || itemId == 1708 || itemId == 1706 || itemId == 1704) {
						int amount = (c.getItems().getItemCount(1710) + c.getItems().getItemCount(1708)
								+ c.getItems().getItemCount(1706) + c.getItems().getItemCount(1704));
						int[] glories = { 1710, 1708, 1706, 1704 };
						for (int i : glories) {
							c.getItems().deleteItem(i, c.getItems().getItemCount(i));
						}
						c.startAnimation(832);
						c.getItems().addItem(1712, amount);
					}
					break;
				}

				c.getQuestManager().onItemOnObject(item, existingObject.get());
				UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);
			});
			c.turnPlayerTo(objectX, objectY);
			if (c.goodDistance(objectX, objectY, c.getX(), c.getY(), objectWidth, objectLength)) {
				onReached.run();
				
			} else {
				
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if(!c.getInteractingObject().isPresent() || !c.getInteractingObject().equals(existingObject)) {
							stop();
							return;
						}
						if (c.goodDistance(objectX, objectY, c.getX(), c.getY(), objectWidth, objectLength)) {
							onReached.run();
							container.stop();
						}
					}

				}, 1);
			}
		}

	}

}
