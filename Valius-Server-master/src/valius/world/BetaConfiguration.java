package valius.world;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import valius.model.items.ItemDefinition;

public class BetaConfiguration {
	
	private static List<String> betaUsers = Lists.newArrayList();
	

	public static boolean validBetaUser(String username) {
		return betaUsers.contains(username.toLowerCase());
	}
	public static void load() {
		if(World.getWorld().isBetaWorld()) {
			File betaJson = new File("./data/beta_users.json");
			try(FileReader fr = new FileReader(betaJson)){
				List<String> users = new Gson().fromJson(fr, new TypeToken<List<String>>() { }.getType());
				users.stream().map(String::toLowerCase).forEach(betaUsers::add);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
