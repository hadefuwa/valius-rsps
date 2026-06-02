/**
 * 
 */
package valius.content;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Data;
import valius.Config;
import valius.model.entity.player.Player;
import valius.util.ConfigBuilder;
import valius.util.JsonLoader;

/**
 * @author ReverendDread
 * Aug 8, 2019
 */
@Data
public class GroupIronman {

	public GroupIronman(Player player) {
		this.player = player;
	}
	private final Player player;
	private String leader;
	private List<String> teamates = Lists.newArrayList();
	private IronmanGroupRequest request;
	
	public void createTeam() {
		if (player.getGroupIronman().getLeader() == null) {
			player.getGroupIronman().setLeader(player.getName().toLowerCase());
			player.getGroupIronman().getTeamates().add(player.getName().toLowerCase());
			player.sendMessage("@gre@You've created a group Ironman team.");
		} else {
			player.sendMessage("@red@You're already locked into a group.");
		}
	}
	
	public boolean invite(Player other) {
		if (other.getSkills().getTotalLevel() > 50) {
			player.sendMessage("@red@You can't invite this player, they need to be below 50 total level.");
			return false;
		}
		if (!other.getMode().isGroupIronman()) {
			player.sendMessage("@red@This player isn't a group Ironman.");
			return false;
		}
		if (other.getGroupIronman().hasPendingRequest()) {
			player.sendMessage("@red@This player already has a pending request.");
			return false;
		}
		if (getLeader() == null) {
			createTeam();
		}
		if (!player.getName().equalsIgnoreCase(leader)) {
			player.sendMessage("@red@You have to be group leader to invite others.");
			return false;
		}
		if (isTeamFull()) {
			player.sendMessage("@red@Your team is already full.");
			return false;
		}
		if (isTeamate(other)) {
			player.sendMessage("@red@This player is already on your team.");
			return false;
		}
		if(other.getGroupIronman().hasTeam()) {
			player.sendMessage("@red@This player already has a team.");
			return false;
		}
		other.sendMessage(player.getName() + ":invite:");
		player.sendMessage("Request sent to " + other.getName());
		other.getGroupIronman().sendRequest(new IronmanGroupRequest(player, other));
		return true;
	}


	private boolean hasTeam() {
		return getTeamates().stream().filter(name -> name != null && !name.equalsIgnoreCase(player.getName())).count() > 0;
	}

	/**
	 * @param groupRequest
	 */
	private void sendRequest(IronmanGroupRequest groupRequest) {
		groupRequest.getReciever().getGroupIronman().setRequest(groupRequest);
	}
	
	public boolean hasPendingRequest() {
		return getRequest() != null;
	}
	
	public boolean isTeamate(String name) {
		return getTeamates().stream().anyMatch(other -> name.equalsIgnoreCase(other));
	}
	
	public boolean isTeamate(Player other) {
		return getTeamates().stream().anyMatch(name -> name.equalsIgnoreCase(other.getName()));
	}

	/**
	 * Returns if the group is full or not.
	 * @return
	 */
	public boolean isTeamFull() {
		return getTeamates().size() >= 4;
	}
	
	public void setTeam(String[] names) {
		if(names != null) {
			teamates.clear();
			Arrays.stream(names).forEach(teamates::add);
		}
	}

	/**
	 * Returns if the player is the group leader.
	 * @return
	 */
	public boolean isLeader() {
		return player.getName().equalsIgnoreCase(leader) || leader == null;
	}

}
