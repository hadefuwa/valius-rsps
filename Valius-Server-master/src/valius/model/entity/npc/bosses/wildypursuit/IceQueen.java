package valius.model.entity.npc.bosses.wildypursuit;

import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.Hitmark;
import valius.model.items.Item;
import valius.util.Misc;

public class IceQueen {
	
	public static int specialAmount = 0;
	
	public static void queenSpecial(Player player) {
		NPC QUEEN = NPCHandler.getNpc(4922);
		
		if (QUEEN.isDead) {
			return;
		}
		
		if (QUEEN.getHealth().getAmount() < 1400 && specialAmount == 0 ||
			QUEEN.getHealth().getAmount() < 1100 && specialAmount == 1 ||
			QUEEN.getHealth().getAmount() < 900 && specialAmount == 2 ||
			QUEEN.getHealth().getAmount() < 700 && specialAmount == 3 ||
			QUEEN.getHealth().getAmount() < 400 && specialAmount == 4 ||
			QUEEN.getHealth().getAmount() < 100 && specialAmount == 5) {
				NPCHandler.npcs[QUEEN.getIndex()].forceChat("Prison of Ice!");
				QUEEN.startAnimation(1979);
				QUEEN.underAttackBy = -1;
				QUEEN.underAttack = false;
				NPCHandler.queenAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.gfx100(369);
					p.appendDamage(Misc.random(25) + 12, Hitmark.HIT);
					p.freezeTimer = 10;
					p.sendMessage("Ice floods your veins!");
				});
			}
		}
	
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
			Item[] KEY = {new Item(33774), new Item(33775), new Item(33776), new Item(33777)};
			Item[] itemList2 = KEY;
				//int reward = (p.getIceQueenDamageCounter() >= 50 ? Misc.random(5) + 3 : 0);
				if (p.getIceQueenDamageCounter() >= 1) {
					if (p.getItems().isWearingItem(33336) &&
							p.getItems().isWearingItem(33331) &&
							p.getItems().isWearingItem(33332) &&
							p.getItems().isWearingItem(33334)) {
						p.getItems().addItemUnderAnyCircumstance(33269, 1);
						p.sendMessage("You receive a Valius Mystery box for wearing the Ancient armor set!");
						} else if (p.getItems().isWearingItem(33520) &&
								p.getItems().isWearingItem(33516) &&
								p.getItems().isWearingItem(33517) &&
								p.getItems().isWearingItem(33518)) {
							p.getItems().addItemUnderAnyCircumstance(33269, 1);
							p.sendMessage("You receive a Valius Mystery box for wearing the Ancient armor set!");
							}
					p.sendMessage("@blu@The boss in pursuit has been killed!");
					p.sendMessage("@blu@You receive a Pursuit Crate for damaging the boss!");
					p.getItems().addItemUnderAnyCircumstance(21307, 1);
					Item key = Misc.getRandomItem(itemList2);
					p.getItems().addItemUnderAnyCircumstance(key.getId(), 1);
				} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
				}
				p.setIceQueenDamageCounter(0);
		});
	}
}
