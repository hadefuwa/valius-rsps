package valius.model.minigames.pvptourny;
//package ethos.model.minigames.pvptourny;
//
//import ethos.model.Location;
//import ethos.model.entity.Entity;
//import ethos.model.entity.player.Player;
//import ethos.model.entity.player.combat.CombatType;
//import ethos.world.World;
//
///**
// * 
// * @author ReverendDread | Feb. 8, 2019 , 3:35:32 a.m.
// *
// */
//
//
//public class TournamentConstants {
//
//	
//
//	public boolean allowMultiSpells() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//	public boolean allowPvPCombat() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//	public boolean canAttackNPC() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean canAttackPlayer(Player player1, Player player2) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canClick() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canDrink(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canEat(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canEquip(Player player, int int1, int int2) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canUnequip(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canDrop(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canLogOut() {
//		return false;
//	}
//
//	public boolean canMove(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canSave() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean canTalk() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canTeleport() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean canTrade() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	public boolean canUseCombatType(Player player, CombatType paramCombatTypes) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canUsePrayer(Player player, int id) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public boolean canUseSpecialAttack(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public Location getRespawnLocation(Player player) {
//		return new Location(3087, 3490, 0);
//	}
//
//	public boolean isSafe(Player player) {
//		return true;
//	}
//
//	public void onControllerInit(Player player) {
//		player.getDH().sendOption("Attack");
//		player.teleport(World.getTournament().getRandomSpawn());
//		World.getTournament().restockGear(player);
//	}
//
//	public void onDeath(Player player) {
//		//player.getDH().sendOption
//		player.getDH().sendOption("null");
//		World.getTournament().exit(player, false);
//	}
//
//	public void onKill(Player player, Entity killed) {
//		Player kill = (Player) killed;
//		World.getTournament().handleKill(kill, player);
//	}
//
//	public void onDisconnect(Player player) {
//		World.getTournament().exit(player, false);
//	}
//
//	public void onTeleport(Player player) {
//		World.getTournament().exit(player, false);
//	}
//
//	public void tick(Player player) {
//		// TODO Auto-generated method stub
//	}
//
//	public String toString() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public boolean transitionOnWalk(Player player) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	
//}
