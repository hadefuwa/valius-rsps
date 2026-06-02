package valius.model.entity.npc.bosses.wildypursuit;

import valius.model.entity.npc.NPC;
import valius.model.entity.npc.NPCHandler;
import valius.model.entity.player.Boundary;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.combat.Hitmark;
import valius.model.items.Item;
import valius.util.Misc;

public class Glod {
	
	public static int specialAmount = 0;
	
	public static void glodSpecial(Player player) {
		NPC GLOD = NPCHandler.getNpc(5129);
		
		if (GLOD.isDead) {
			return;
		}
		
		if (GLOD.getHealth().getAmount() < 1400 && specialAmount == 0 ||
			GLOD.getHealth().getAmount() < 1100 && specialAmount == 1 ||
			GLOD.getHealth().getAmount() < 900 && specialAmount == 2 ||
			GLOD.getHealth().getAmount() < 700 && specialAmount == 3 ||
			GLOD.getHealth().getAmount() < 400 && specialAmount == 4 ||
			GLOD.getHealth().getAmount() < 100 && specialAmount == 5) {
				NPCHandler.npcs[GLOD.getIndex()].forceChat("Glod Smash!");
				GLOD.startAnimation(6501);
				GLOD.underAttackBy = -1;
				GLOD.underAttack = false;
				NPCHandler.glodAttack = "SPECIAL";
				specialAmount++;
				PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
				.forEach(p -> {
					p.appendDamage(Misc.random(25) + 13, Hitmark.HIT);
					p.sendMessage("Glod's hit trembles through your body.");
				});
			}
		}
	
	public static void rewardPlayers(Player player) {
		PlayerHandler.nonNullStream().filter(p -> Boundary.isIn(p, Boundary.PURSUIT_AREAS))
		.forEach(p -> {
			Item[] KEY = {new Item(33774), new Item(33775), new Item(33776), new Item(33777)};
			Item[] itemList2 = KEY;
				//int reward = (p.getGlodDamageCounter() >= 50 ? Misc.random(5) + 3 : 0);
				if (p.getGlodDamageCounter() >= 1) {
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
					Item key = Misc.getRandomItem(itemList2);
					
					p.sendMessage("@blu@The boss in pursuit has been killed!");
					p.sendMessage("@blu@You receive a Pursuit Crate for damaging the boss!");
					p.getItems().addItemUnderAnyCircumstance(21307, 1);
					p.getItems().addItemUnderAnyCircumstance(key.getId(), 1);
				} else {
					p.sendMessage("@blu@You didn't do enough damage to the boss!");
				}
				p.setGlodDamageCounter(0);
		});
	}
}
