///**
// * 
// */
//package valius.model.entity.npc.combat.impl.corporeal;
//
//import valius.model.entity.Entity;
//import valius.model.entity.npc.NPC;
//import valius.model.entity.npc.combat.CombatScript;
//import valius.model.entity.npc.combat.ScriptSettings;
//
///**
// * @author ReverendDread
// * Jul 15, 2019
// */
//@ScriptSettings ( npcIds = { 320, 3376 } )
//public class DarkEnergyCore extends CombatScript {
//
//	@Override
//	public void init(NPC npc) {
//		npc.getMovementQueue().setBlockMovement(true);
//		npc.setNoRespawn(true);
//		npc.setNeverWalkHome(true);
//		setCanAttack(false);
//	}
//	
//	@Override
//	public int attack(NPC npc, Entity target) {
//		return 10;
//	}
//
//	@Override
//	public int getAttackDistance(NPC npc) {
//		return 1;
//	}
//
//}
