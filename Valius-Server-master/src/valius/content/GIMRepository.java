package valius.content;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;

public class GIMRepository {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static void save() {
		File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
		gimData.stream().forEach(imData -> {
			try(FileWriter fw = new FileWriter(new File(saveDir, imData.leader + ".json"))){
				GSON.toJson(imData, fw);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void save(GroupIMData imData) {
		File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
		try (FileWriter fw = new FileWriter(new File(saveDir, imData.leader + ".json"))) {
			GSON.toJson(imData, fw);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void load() {
		File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
		for(File file : saveDir.listFiles()) {
			try(FileReader fr = new FileReader(file)){
				GroupIMData imData = GSON.fromJson(fr, new TypeToken<GroupIMData>() {}.getType());
				gimData.add(imData);
			} catch (Exception e) {
				System.out.println(file.getName());
				e.printStackTrace();
			}
		}
	}
	
	public static void onLogin(Player player) {
		if(player.getRights().isOrInherits(Right.GROUP_IRONMAN)) {
			Optional<GroupIMData> team = gimData.stream().filter(data -> data.teamMembers != null).filter(data -> Arrays.stream(data.teamMembers).anyMatch(name -> name.equalsIgnoreCase(player.getName()))).findFirst();
			team.ifPresent(data -> {
				System.out.println("Set gim to " + data.leader);
				player.getGroupIronman().setLeader(data.leader);
				player.getGroupIronman().setTeam(data.teamMembers);
			});
		}
	}
	
	private static List<GroupIMData> gimData = Lists.newArrayList();
	
	
	
	static class GroupIMData {
	
		private String leader;
		private String[] teamMembers;
	
	}


	public static void update(GroupIronman groupIronman) {
		Optional<GroupIMData> gimOpt = gimData.stream().filter(data -> data.leader.equalsIgnoreCase(groupIronman.getLeader())).findFirst();
		if(!gimOpt.isPresent()) {
			GroupIMData data = new GroupIMData();
			data.leader = groupIronman.getLeader();
			data.teamMembers = groupIronman.getTeamates().toArray(new String[0]);
			gimData.add(data);
			gimOpt = Optional.ofNullable(data);
		} else {
			gimOpt.get().leader = groupIronman.getLeader();
			gimOpt.get().teamMembers = groupIronman.getTeamates().toArray(new String[0]);
		}

		propogate(gimOpt.get());
		save(gimOpt.get());
	}
	
	public static void propogate(GroupIMData data) {
		PlayerHandler.getPlayersForNames(Arrays.asList(data.teamMembers)).forEach(plr -> {
			plr.getGroupIronman().setLeader(data.leader);
			plr.getGroupIronman().setTeam(data.teamMembers);
		});
	}
	


}
