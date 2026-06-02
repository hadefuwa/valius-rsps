package valius.model.multiplayer_session.duel;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import valius.Config;
import valius.model.entity.player.Player;
import valius.model.entity.player.Right;
import valius.model.multiplayer_session.Multiplayer;
import valius.model.multiplayer_session.MultiplayerSessionStage;
import valius.model.multiplayer_session.MultiplayerSessionType;
import valius.util.Misc;
import valius.world.World;

public class Duel extends Multiplayer {

	public Duel(Player player) {
		super(player);
	}

	@Override
	public boolean requestable(Player requested) {
		if (!Config.NEW_DUEL_ARENA_ACTIVE) {
			player.getDH().sendStatement("@red@Dueling Temporarily Disabled", "The duel arena minigame is currently being rewritten.",
					"No player has access to this minigame during this time.", "", "Thank you for your patience, Developer J.");
			player.nextChat = -1;
			return false;
		}
		long milliseconds = (long) requested.playTime * 600;
		long days = TimeUnit.MILLISECONDS.toDays(milliseconds);

		/*if(days < 1){
			requested.sendMessage("@red@ You need to be at least 1 day old to stake.");
			requested.sendMessage("@red@ This is to prevent our new players from getting cleaned.");
			requested.sendMessage("@red@ Please enjoy all other aspects of the game though. Thanks.");
			return false;
		}*/

		if (player.viewingLootBag || player.addingItemsToLootBag ||
			requested.viewingLootBag || requested.addingItemsToLootBag) {
			return false;
		}
		if (!player.getMode().isStakingPermitted()) {
			player.sendMessage("You are not permitted to stake other players.");
			return false;
		}
		if (!requested.getMode().isStakingPermitted()) {
			player.sendMessage("That player is on a game mode that restricts staking.");
			return false;
		}
		if (World.getWorld().getMultiplayerSessionListener().requestAvailable(requested, player, MultiplayerSessionType.DUEL) != null) {
			player.sendMessage("You have already sent a request to this player.");
			return false;
		}
		if (World.getWorld().isGameUpdating()) {
			player.sendMessage("You cannot request or accept a duel request at this time.");
			player.sendMessage("The server is currently being updated.");
			return false;
		}
		if (player.connectedFrom.equalsIgnoreCase(requested.connectedFrom) && !player.getRights().isOrInherits(Right.MODERATOR)) {
			player.sendMessage("You cannot request a duel from someone on the same network.");
			return false;
		}
		if (Misc.distanceToPoint(player.getX(), player.getY(), requested.getX(), requested.getY()) > 15) {
			player.sendMessage("You are not close enough to the other player to request or accept.");
			return false;
		}
		if (!player.inDuelArena()) {
			player.sendMessage("You must be in the duel arena area to do this.");
			return false;
		}
		if (!requested.inDuelArena()) {
			player.sendMessage("The challenger must be in the duel arena area to do this.");
			return false;
		}
		if (player.getBH().hasTarget()) {
			if (player.getBH().getTarget().getName().equalsIgnoreCase(requested.playerName)) {
				player.sendMessage("You cannot duel your bounty hunter target.");
				return false;
			}
		}
		if (World.getWorld().getMultiplayerSessionListener().inAnySession(player)) {
			player.sendMessage("You cannot request a duel whilst in a session.");
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

		player.faceUpdate(requested.getIndex());
		DuelSession session = (DuelSession) World.getWorld().getMultiplayerSessionListener().requestAvailable(player, requested, MultiplayerSessionType.DUEL);
		if (session != null) {
			session.getStage().setStage(MultiplayerSessionStage.OFFER_ITEMS);
			session.populatePresetItems();
			session.updateMainComponent();
			session.sendDuelEquipment();
			World.getWorld().getMultiplayerSessionListener().removeOldRequests(player);
			World.getWorld().getMultiplayerSessionListener().removeOldRequests(requested);
			session.getStage().setAttachment(null);
		} else {
			session = new DuelSession(Arrays.asList(player, requested), MultiplayerSessionType.DUEL);
			if (World.getWorld().getMultiplayerSessionListener().appendable(session)) {
				player.sendMessage("Sending duel request...");
				requested.sendMessage(player.playerName + ":duelreq:");
				session.getStage().setAttachment(player);
				World.getWorld().getMultiplayerSessionListener().add(session);
			}
		}
	}

}
