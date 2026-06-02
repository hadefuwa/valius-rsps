package valius.model.entity.player.skills.herblore;

import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;
import valius.model.items.Item;
import valius.model.items.ItemUtility;
import valius.util.Misc;

public class UnfCreator {
	
	public enum UnfinishedPotions {
		GUAM_POTION(new Item(91), new Item(250), 1), //Attack potion
		MARRENTILL_POTION(new Item(93), new Item(252), 5), //Antipoison
		TARROMIN_POTION(new Item(95), new Item(254), 12), //Strength potion
		HARRALANDER_POTION(new Item(97), new Item(256), 22), //Restore potion, Energy potion & Combat potion
		RANARR_POTION(new Item(99), new Item(258), 30), //Prayer potion
		TOADFLAX_POTION(new Item(3002), new Item(2999), 34), //Agility potion & Saradomin brew
		IRIT_POTION(new Item(101), new Item(260), 45), //Super attack & Fishing potion
		AVANTOE_POTION(new Item(103), new Item(262), 50), //Super energy
		KWUARM_POTION(new Item(105), new Item(264), 55), //Super strength & Weapon poison
		SNAPDRAGON_POTION(new Item(3004), new Item(3001), 63), //Super restore
		CADANTINE_POTION(new Item(107), new Item(266), 66), //Super defence
		LANTADYME(new Item(2483), new Item(2482), 69), //Antifire, Magic potion
		DWARF_WEED_POTION(new Item(109), new Item(268), 72), //Ranging potion
		TORSTOL_POTION(new Item(111), new Item(270), 78); //Zamorak brew
		
		
		private final Item potion, ingredient;
		private int levelReq;
		
		private UnfinishedPotions(Item potion, Item ingredient, int levelReq) {
			this.potion = potion;
			this.ingredient = ingredient;
			this.levelReq = levelReq;
		}
		
		public Item getPotion() {
			return potion;
		}
		
		public Item getHerb() {
			return ingredient;
		}
		
		public int getLevelReq() {
			return levelReq;
		}
		
		public static UnfinishedPotions forId(int i) {
			for(UnfinishedPotions unf : UnfinishedPotions.values()) {
				if (unf.getHerb().getId() == i) {
					return unf;
				}
			}
			return null;
		}
	}
	
	public static boolean setPotionToCreate(final Player player, final Item itemUsed) {
		final UnfinishedPotions unf = UnfinishedPotions.forId(itemUsed.getId());
		if (unf == null) {
			player.getPA().closeAllWindows();
			return false;
		}
		if (player.getSkills().getLevel(Skill.HERBLORE) < unf.getLevelReq()) {
			player.sendMessage("You need a Herblore level of " + unf.getLevelReq() + " to make this potion.");
			player.getPA().closeAllWindows();
			return false;
		}
		player.unfPotHerb = itemUsed.getId();
		player.unfPotAmount = player.getItems().getItemAmount(itemUsed.getId());
		player.sendMessage("You have " + player.unfPotAmount + " x " + ItemUtility.getItemName(itemUsed.getId() - 1) + ". " + player.unfPotAmount + " x 250gp = " + Misc.format(player.getItems().getItemAmount(itemUsed.getId()) * 250) + "gp.");
		player.getDH().sendDialogues(810, 5449);
		return true;
	}
	
	public static boolean makeUnfinishedPotion(final Player player, final Item itemUsed) {
		final UnfinishedPotions unf = UnfinishedPotions.forId(itemUsed.getId());
		if (unf == null) {
			return false;
		}
		if (player.getItems().playerHasItem(player.unfPotHerb, player.unfPotAmount)) {
			if (!player.getItems().playerHasItem(228, player.unfPotAmount)) {
				player.sendMessage("You much have the equiv amount of noted vials of water to do this.");
				player.getPA().closeAllWindows();
				return false;
			}
			if (!player.getItems().playerHasItem(995, player.unfPotAmount * 250)) {
				player.sendMessage(
						"You do not have enough money to do this. (" + Misc.format(player.unfPotAmount * 250) + "gp)");
				player.getPA().closeAllWindows();
				return false;
			}
			player.getItems().deleteItem(player.unfPotHerb, player.unfPotAmount);
			player.getItems().deleteItem(228, player.unfPotAmount);
			player.getItems().deleteItem(995, player.unfPotAmount * 250);
			player.getItems().addItem(unf.getPotion().getId() + 1, player.unfPotAmount);
			player.sendMessage("Successfully created " + player.unfPotAmount + " x "
					+ ItemUtility.getItemName(unf.getPotion().getId()) + ".");
			player.getPA().closeAllWindows();
		}
		return false;
	}

}
