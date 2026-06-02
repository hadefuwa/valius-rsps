package valius.content.cannon;

import java.util.Map;

import com.google.common.collect.Maps;

import valius.model.entity.player.Player;

public class CannonManager {
	
	private static Map<String, DwarfCannon> playerCannons = Maps.newConcurrentMap();
	
	public static void register(Player player, DwarfCannon cannon) {
		playerCannons.put(player.getName().toLowerCase(), cannon);
	}
	
	public static void checkForCannon(Player player) {
		if(playerCannons.get(player.getName().toLowerCase()) != null) {
			player.cannon = playerCannons.get(player.getName().toLowerCase());
			player.cannon.onLogin(player);
		}
	}

}
