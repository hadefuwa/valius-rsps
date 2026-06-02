package valius.content;

import java.util.Objects;

import valius.event.CycleEvent;
import valius.event.CycleEventContainer;
import valius.event.CycleEventHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.util.Misc;

/**
 * 
 * @author Divine | 4:37:40 a.m. | Oct. 31, 2019
 *
 */

/*
 * Hourly rewards are now loyalty points
 */

public class HourlyRewardBox implements CycleEvent {


	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new mystery box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public HourlyRewardBox(Player player) {// HourlyRewardBox Object is in player.java that triggers this constructor
		this.player = player;
	}
	
	
	public void startEvent() {//starts event. Triggered through constructor
		if (player.disconnected || Objects.isNull(player)) {
			return;
		}
		if(player == null) {
			return;
		}
		CycleEventHandler.getSingleton().addEvent(this, this, 3600);//   (event,event,time) 3600
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@Override
	public void execute(CycleEventContainer container) {//at end of event this triggers
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		if(player == null) {
			container.stop();
			return;
		}
		if(Boundary.isIn(player, Boundary.THEATRE) || Boundary.isIn(player, Boundary.RAIDROOMS) || Boundary.isIn(player, Boundary.XERIC)) {
			container.stop();
			return;
		}
		if (player.getRights().isOrInherits(Right.SAPPHIRE)) {
			int loyaltyPoints = Misc.random(1, 7);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.EMERALD)) {
			int loyaltyPoints = Misc.random(1, 9);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.RUBY)) {
			int loyaltyPoints = Misc.random(1, 11);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.DIAMOND)) {
			int loyaltyPoints = Misc.random(1, 13);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.DRAGONSTONE)) {
			int loyaltyPoints = Misc.random(1, 15);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.ONYX)) {
			int loyaltyPoints = Misc.random(1, 17);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else if (player.getRights().isOrInherits(Right.ZENYTE)) {
			int loyaltyPoints = Misc.random(1, 19);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
		} else {
			int loyaltyPoints = Misc.random(1, 5);
			player.loyaltyPoints += loyaltyPoints;
			player.sendMessage("You have played for an hour and receive @blu@" + loyaltyPoints + "</col> Loyalty points!");
			}
		if (player.summonId == 33965 && Misc.random(1, 4) == 3) {
			player.getItems().addItemUnderAnyCircumstance(33961, 1);
			player.sendMessage("You receive a Present for your hourly reward while Rudolph follows you.");
		}
		player.getItems().addItemUnderAnyCircumstance(33962, Misc.random(5, 20));
		player.sendMessage("You now have @blu@" + player.loyaltyPoints + "</col> Loyalty points.");
		player.sendMessage("Spend these by talking to the Loyalty point shop at home.");
		container.stop();
		startEvent();
	}

}
