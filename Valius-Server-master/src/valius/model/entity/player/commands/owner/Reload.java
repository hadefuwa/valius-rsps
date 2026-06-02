package valius.model.entity.player.commands.owner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import valius.Config;
import valius.clip.doors.DoorDefinition;
import valius.content.tradingpost.Listing;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.items.ItemDefinition;
import valius.model.items.ItemUtility;
import valius.net.packet.impl.Commands;
import valius.world.World;

/**
 * Reloading certain objects by {String input}
 * 
 * @author Matt
 */

public class Reload extends Command {

	@Override
	public void execute(Player player, String input) {
		switch (input) {
		
		case "":
			player.sendMessage("@red@Usage: ::reload doors, drops, items, objects, shops or npcs");
			break;
		
		case "tp":
			Listing.save();
			Listing.load();
			break;
		case "doors":
			DoorDefinition.load();
			player.sendMessage("@blu@Reloaded Doors.");
			break;

		case "drops":
			World.getWorld().getDropManager().read();
			player.sendMessage("@blu@Reloaded Drops.");
			break;

		case "items":
			World.getWorld().getItemHandler().loadItemList("item_config.cfg");
			World.getWorld().getItemHandler().loadItemPrices("item_prices.txt");
			try {
				ItemDefinition.load();
			} catch (IOException e) {
				player.sendMessage("@blu@Unable to reload items, check console.");
				e.printStackTrace();
			}
			
			
			player.sendMessage("@blu@Reloaded Items.");
			break;

		case "objects":
			try {
				World.getWorld().getGlobalObjects().reloadObjectFile(player);
				player.sendMessage("@blu@Reloaded Objects.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "shops":
			World.getWorld().reloadShops();
			player.sendMessage("@blu@Reloaded Shops");
			break;

		case "npcs":
			World.getWorld().reloadNPCHandler();
			player.sendMessage("@blu@Reloaded NPCs");
			break;
			
		case "punishments":
			try {
				World.getWorld().getPunishments().initialize();
				player.sendMessage("@blu@Reloaded Punishments.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		case "looting":
			Config.BAG_AND_POUCH_PERMITTED = !Config.BAG_AND_POUCH_PERMITTED;
			player.sendMessage(""+(Config.BAG_AND_POUCH_PERMITTED ? "Enabled" : "Disabled" +"") + " bag and pouch.");
			break;
		case "commands":
			try {
				Commands.initializeCommands();
				player.sendMessage("@blu@Reloaded Commands.");
			} catch (Exception e) {
				player.sendMessage("There was an error while reloading commands!");
				e.printStackTrace();
			}
			break;

		}
	}

}
