package valius.net.packet.impl.action;

import valius.content.clans.Clan;
import valius.model.entity.player.Player;
import valius.net.packet.PacketType;
import valius.util.Misc;
import valius.world.World;

public class JoinChat implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		String owner = Misc.longToPlayerName2(player.getInStream().readLong()).replaceAll("_", " ");
		
		if (player.documentGraphic) {
			player.sendMessage("Test: " + owner);
			
			return;
		}
		
		if (owner != null && owner.length() > 0) {
			if (player.clan == null) {
				/*
				 * if (player.inArdiCC) { return; }
				 */
				Clan clan = World.getWorld().getClanManager().getClan(owner);
				if (clan != null) {
					clan.addMember(player);
				} else if (owner.equalsIgnoreCase(player.playerName)) {
					World.getWorld().getClanManager().create(player);
				} else {
					player.sendMessage(Misc.formatPlayerName(owner) + " has not created a clan yet.");
				}
			}
		}
	}

}