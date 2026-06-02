package valius.net.packet.impl;

import valius.content.quest.dialogue.impl.OptionDialogue;
import valius.model.entity.player.Player;
import valius.net.packet.PacketType;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {

		if(c.getActiveDialogue() != null) {
			c.getActiveDialogue().moveForward(c);
			return;
		}
		if (c.nextChat > 0) {
			c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
		} else {
			c.getDH().sendDialogues(0, -1);
		}

	}

}
