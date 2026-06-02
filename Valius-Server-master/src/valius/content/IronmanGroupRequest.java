/**
 * 
 */
package valius.content;

import lombok.Data;
import valius.model.entity.player.Player;

/**
 * @author ReverendDread
 * Aug 8, 2019
 */
@Data
public class IronmanGroupRequest {

	private final Player sender;
	private final Player reciever;
	
	public void accept() {
		reciever.getGroupIronman().setLeader(sender.getName());
		sender.getGroupIronman().getTeamates().add(reciever.getName());
		reciever.getGroupIronman().setTeamates(sender.getGroupIronman().getTeamates());
		reciever.sendMessage("@gre@You've accepted " + sender.getName() + "'s invite!");
		sender.sendMessage("@gre@" + reciever.getName() + " has accepted your group invite!");
		reciever.getGroupIronman().setRequest(null);
		GIMRepository.update(sender.getGroupIronman());
	}
	
}
