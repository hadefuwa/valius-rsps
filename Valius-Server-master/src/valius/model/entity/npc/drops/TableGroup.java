package valius.model.entity.npc.drops;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Range;

import valius.Config;
import valius.discord.DiscordBot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.items.Item;
import valius.model.items.ItemAssistant;
import valius.util.Misc;

@SuppressWarnings("serial")
public class TableGroup extends ArrayList<Table> {

	/**
	 * The non-playable character that has access to this group of tables
	 */
	private final List<Integer> npcIds;

	/**
	 * Creates a new group of tables
	 * 
	 * @param npcId the npc identification value
	 */
	public TableGroup(List<Integer> npcsIds) {
		this.npcIds = npcsIds;
	}

	/**
	 * Accesses each {@link Table} in this {@link TableGroup} with hopes of retrieving a {@link List} of {@link Item} objects.
	 * 
	 * @return
	 */
	public List<Item> access(Player player, double modifier, int repeats) {
		int rights = player.getRights().getPrimary().getValue() - 1;
		List<Item> items = new ArrayList<>();
		for (Table table : this) {
			TablePolicy policy = table.getPolicy();
			if (policy.equals(TablePolicy.CONSTANT)) {
				for (Drop drop : table) {
					int minimumAmount = drop.getMinimumAmount();

					items.add(new Item(drop.getItemId(), minimumAmount + Misc.random(drop.getMaximumAmount() - minimumAmount)));
				}
			} else {
				for (int i = 0; i < repeats; i++) {
					double chance = (1.0 / (double) (table.getAccessibility() * modifier)) * 100D;

					double roll = Misc.preciseRandom(Range.between(0.0, 100.0));

					if (chance > 100.0) {
						chance = 100.0;
					}
					if (roll <= chance) {
						Drop drop = table.fetchRandom();
						int minimumAmount = drop.getMinimumAmount();
						Item item = new Item(drop.getItemId(),
								minimumAmount + Misc.random(drop.getMaximumAmount() - minimumAmount));

						items.add(item);
						if (chance <= 1.5) {
							if (policy.equals(TablePolicy.VERY_RARE) || policy.equals(TablePolicy.RARE)) {/*
								if (Item.getItemName(item.getId()).toLowerCase().contains("cowhide")
										|| Item.getItemName(item.getId()).toLowerCase().contains("feather")
										|| Item.getItemName(item.getId()).toLowerCase().contains("arrow")
										|| Item.getItemName(item.getId()).toLowerCase().contains("sq shield")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune warhammer")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune battleaxe")
										|| Item.getItemName(item.getId()).toLowerCase().contains("casket")
										|| Item.getItemName(item.getId()).toLowerCase().contains("silver ore")
										|| Item.getItemName(item.getId()).toLowerCase().contains("rune spear")
										|| item.getId() >= 554 && item.getId() <= 566)*/
								if(Config.includes(item.getId())){
									
								} else {
									GlobalMessages.send( Misc.capitalize(player.playerName) + " received a drop: "
													+ (item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId())), GlobalMessages.MessageType.LOOT);
									DiscordBot.sendMessage("valius-bot", "[Loot Bot] " + Misc.capitalize(player.playerName) + " received a drop: "
													+ (item.getAmount() > 1 ? (item.getAmount() + "x ") : ItemAssistant.getItemName(item.getId()) + "."));
								}
							}
						}
					}
				}
			}
		}
		return items;
	}

	/**
	 * The non-playable character identification values that have access to this group of tables.
	 * 
	 * @return the non-playable character id values
	 */
	public List<Integer> getNpcIds() {
		return npcIds;
	}
}
