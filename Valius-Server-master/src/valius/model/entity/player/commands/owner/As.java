package valius.model.entity.player.commands.owner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.world.World;

public class As extends Command {

	@Override
	public void execute(Player player, String input) {
		addSpawn(player, input);
	}
	
	public void addSpawn(Player player, String animal) {
		String filePath = "./Data/" + player.playerName + " Lava Dragon.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			switch (animal.toUpperCase()) {
			
			case "B":
				bw.write("spawn =	6593	" + player.getX() + "	" + player.getY() + "	" + player.getHeight() + "	1	23	240	150	Lava Dragon");
				World.getWorld().getNpcHandler().spawnNpc(player, 6593, player.getX(), player.getY(), 0, 1, 0, 0, 0, 0, false, false);
				break;

			case "W":
				bw.write("spawn =	955	" + player.getX() + "	" + player.getY() + "	" + player.getHeight() + "	1	3	20	20	Kalphite Worker");
				World.getWorld().getNpcHandler().spawnNpc(player, 955, player.getX(), player.getY(), 0, 1, 0, 0, 0, 0, false, false);
				break;

			case "G":
				bw.write("spawn =	959	" + player.getX() + "	" + player.getY() + "	" + player.getHeight() + "	1	12	110	110	Kalphite Guardian");
				World.getWorld().getNpcHandler().spawnNpc(player, 959, player.getX(), player.getY(), 0, 1, 0, 0, 0, 0, false, false);
				break;
				
			}
			player.sendMessage("@red@You set spawn at: X: " + player.getX() + ", Y: " + player.getY());
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

}
