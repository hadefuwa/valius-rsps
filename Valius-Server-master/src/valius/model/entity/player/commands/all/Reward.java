package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.util.Misc;

/**
 * Opens the vote page in the default web browser.
 *
 * @author Emiel
 */
public class Reward extends Command {
	
	private int[] VOTEARMOR = { 33486, 33487, 33488, 33489, 33490 };

    String lastAuth = "";


    @Override
    public void execute(Player player, String input) {

        com.everythingrs.vote.Vote.service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("6h1iskj01admm8b1cd0ahbbj4is4lll0wcd7wvkn48b71cbx1orl37ukchv74ym9sctrvdzhbyb9",
                    		 player.playerName, "1", "all");
                    if(reward.length <= 0 || reward[0].give_amount == 0) {
                    	player.sendMessage("You have nothing to claim!");
                    	return;
                    }
                    if (reward[0].message != null) {
                        player.sendMessage(reward[0].message);
                        return;
                    }
                    player.getItems().addItemUnderAnyCircumstance(reward[0].reward_id, reward[0].give_amount);
                    
                    if (reward[0].reward_amount >= 10) {
                  //t1 valius armor bonus
    				int pieces = 0;
    				for (int voteArmor : VOTEARMOR) {
    					if (player.getItems().isWearingItem(voteArmor)) {
    						pieces += 1;
    					}
    				}
    				
    				for (int voteArmor : VOTEARMOR) {
    					if (player.getItems().isWearingItem(voteArmor)) {
    						if (pieces == 5) {
    	                    	player.getItems().addItemUnderAnyCircumstance(1464, 3);
    						}
    					}
    				}
                    int randombonuspoints = Misc.random(10);
                    if (randombonuspoints == 1) {
                    	player.getItems().addItemUnderAnyCircumstance(1464, reward[0].give_amount);
                    	player.getItems().addItemUnderAnyCircumstance(33269, 1);
                    	GlobalMessages.send(player.playerName + " got lucky and receives double vote tickets + a Valius Mbox!", GlobalMessages.MessageType.NEWS);
                    }
                    }
	                    int bonusXPTime = (int) (((double) reward[0].give_amount / 20.0) * 1800.0);
	                    int loyaltyReward = (int) (((double) reward[0].give_amount * 5));
	                    player.loyaltyPoints += loyaltyReward;
	                    player.sendMessage("You recieve @blu@" + loyaltyReward + "</col> Loyalty points for voting. you now have @blu@" + player.loyaltyPoints + "</col> Loyalty points.");
	                    player.bonusXpTime += bonusXPTime;
                    player.sendMessage("You receive @blu@ " + (bonusXPTime / 60) + " Minutes</col> of bonus XP for voting!");
                	GlobalMessages.send("Thank you for voting " + player.getName() + "!", GlobalMessages.MessageType.NEWS);
                } catch (Exception e) {
                    player.sendMessage("Api Services are currently offline. Please check back shortly");
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Claims your vote from ::vote");
    }

    @Override
    public Optional<String> getParameter() {
        return Optional.of("id# amount#");
    }
}

