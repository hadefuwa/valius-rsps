package valius.model.entity.player.commands.owner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import valius.Config;
import valius.clip.doors.DoorDefinition;
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

public class Reloadstackables extends Command {

	@Override
	public void execute(Player player, String input) {
		try {
			List<String> stackableData = Files.readAllLines(Paths.get("./Data/", "data", "stackables.dat"));
			for (String data : stackableData) {
				int id = Integer.parseInt(data.split("\t")[0]);
				boolean stackable = Boolean.parseBoolean(data.split("\t")[1]);
				ItemUtility.itemStackable[id] = stackable;
				ItemUtility.itemStackable[21880] = true;
				ItemUtility.itemStackable[6646] = true;
				ItemUtility.itemStackable[6651] = true;
				ItemUtility.itemStackable[21930] = true;
				ItemUtility.itemStackable[33006] = true;
				ItemUtility.itemStackable[33005] = true;
				ItemUtility.itemStackable[22804] = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.sendMessage("@blu@ reloaded stackables");
	}

}
