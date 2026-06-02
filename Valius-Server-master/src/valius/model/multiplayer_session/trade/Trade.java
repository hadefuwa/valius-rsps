package valius.model.multiplayer_session.trade;

import java.util.Arrays;
import java.util.Objects;

import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.minigames.pest_control.PestControl;
import valius.model.multiplayer_session.Multiplayer;
import valius.model.multiplayer_session.MultiplayerSession;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.world.World;

public class Trade extends Multiplayer {

	public Trade(Player player) {
		super(player);
	}

	@Override
	public boolean requestable(Player requested) {
		if (requested == null) {
			player.sendMessage("The requested player cannot be found.");
			return false;
		}
		if (player.viewingLootBag || player.addingItemsToLootBag ||
			requested.viewingLootBag || requested.addingItemsToLootBag) {
			return false;
		}
		if (player.getTutorial().isActive()) {
			player.getTutorial().refresh();
			return false;
		}
		if (player.getBankPin().requiresUnlock()) {
			player.getBankPin().open(2);
			return false;
		}
		if (requested.getBankPin().requiresUnlock()) {
			return false;
		}
		if (requested.getTutorial().isActive()) {
			player.sendMessage("This player has not completed the tutorial yet.");
			return false;
		}
		if (!player.getMode().isTradingPermitted(player, requested)) {
			player.sendMessage("You are not permitted to trade other players.");
			return false;
		}
		if (!requested.getMode().isTradingPermitted(requested, player)) {
			player.sendMessage("That player is on a game mode that restricts trading.");
			return false;
		}
//		if (player.playTime < (500)) {
//			player.sendMessage("You cannot request a trade, you must play for at least 5 minutes.");
//			return false;
//		}
//		if (requested.playTime < (500)) {
//			player.sendMessage("You cannot trade this player, they have not played for 5 minutes.");
//			return false;
//		}
		if (World.getWorld().getMultiplayerSessionListener().requestAvailable(requested, player, MultiplayerSessionType.TRADE) != null) {
			player.sendMessage("You have already sent a request to this player.");
			player.turnPlayerTo(requested.getX(), requested.getY());
			return false;
		}
		if (player.getBH().hasTarget() && player.getBH().getTarget() != null && player.getBH().getTarget().getName() != null) {
			if (player.getBH().getTarget().getName().equalsIgnoreCase(requested.playerName)) {
				player.sendMessage("You cannot trade your bounty hunter target.");
				return false;
			}
		}
		if (Objects.equals(player, requested)) {
			player.sendMessage("You cannot trade yourself.");
			return false;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(player)) {
			player.sendMessage("You cannot request a trade whilst in a session.");
			return false;
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(requested)) {
			player.sendMessage("This player is currently is a session with another player.");
			return false;
		}
		if (player.teleTimer > 0 || requested.teleTimer > 0) {
			player.sendMessage("You cannot request or accept whilst you, or the other player are teleporting.");
			return false;
		}
		if (Boundary.isIn(player, PestControl.GAME_BOUNDARY) || Boundary.isIn(requested, PestControl.GAME_BOUNDARY)) {
			player.sendMessage("You cannot trade in the pest control minigame.");
			return false;
		}
		return true;
	}

	@Override
	public void request(Player requested) {
		if (Objects.isNull(requested)) {
			player.sendMessage("The player cannot be found, try again shortly.");
			return;
		}
		if (Objects.equals(player, requested)) {
			player.sendMessage("You cannot trade yourself.");
			return;
		}
		player.turnPlayerTo(requested.getX(), requested.getY());//more effecient turning
		MultiplayerSession session = World.getWorld().getMultiplayerSessionListener().requestAvailable(player, requested, MultiplayerSessionType.TRADE);
		if (session != null) {
			session.getStage().setStage(MultiplayerSessionStage.OFFER_ITEMS);
			session.populatePresetItems();
			session.updateMainComponent();
			World.getWorld().getMultiplayerSessionListener().removeOldRequests(player);
			World.getWorld().getMultiplayerSessionListener().removeOldRequests(requested);
			session.getStage().setAttachment(null);
		} else {
			session = new TradeSession(Arrays.asList(player, requested), MultiplayerSessionType.TRADE);
			if (World.getWorld().getMultiplayerSessionListener().appendable(session)) {
				player.sendMessage("Sending trade offer...");
				requested.sendMessage(player.playerName + ":tradereq:");
				session.getStage().setAttachment(player);
				World.getWorld().getMultiplayerSessionListener().add(session);
			}
		}
	}

}
