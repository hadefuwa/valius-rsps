package valius.content;

import java.util.stream.Stream;

import lombok.Getter;
import valius.model.entity.player.Player;
import valius.model.items.ItemAssistant;

/**
 * 
 * @author Divine | Jan. 7, 2019 , 3:18:38 a.m.
 *
 */

public enum WeaponSheathing {//Unsheath, Sheath, SlotID
	KRILS_SWORDS(33172, 33173, 3),
	ARMADYL_GODSWORD(11802, 33229, 3),
	SARADOMIN_GODSWORD(11806, 33231, 3),
	BANDOS_GODSWORD(11804, 33230, 3),
	ZAMORAK_GODSWORD(11808, 33232, 3);

	@Getter
	public int unsheath, sheath, slot;

	private WeaponSheathing(final int unsheath, final int sheath, final int slot) {
		this.unsheath = unsheath;
		this.sheath = sheath;
		this.slot = slot;
	}

	public static void operate(Player player, int itemId) {
		Stream.of(WeaponSheathing.values())
		.filter(weapon -> weapon.sheath == itemId || weapon.unsheath == itemId)
		.forEach(weapon -> {
			if(weapon.sheath == itemId) {
				if(player.playerEquipment[weapon.slot] == weapon.sheath) {
					player.getItems().deleteEquipment(weapon.sheath, weapon.slot);
					player.getItems().wearItem(weapon.unsheath, 1, weapon.slot);
					/*player.getItems().sendWeapon(player.playerEquipment[player.playerWeapon], ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]));
					player.getItems().resetBonus();
					player.updateItems = true;
					player.getItems().getBonus();
					player.getItems().writeBonus();
					player.getCombat().getPlayerAnimIndex(ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());*/
				} 
			} else if(weapon.unsheath == itemId) {
				if(player.playerEquipment[weapon.slot] == weapon.unsheath) {
					player.getItems().deleteEquipment(weapon.unsheath, weapon.slot);
					player.getItems().wearItem(weapon.sheath, 1, weapon.slot);
					/*player.getItems().sendWeapon(player.playerEquipment[player.playerWeapon], ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]));
					player.getItems().resetBonus();
					player.updateItems = true;
					player.getItems().getBonus();
					player.getItems().writeBonus();
					player.getCombat().getPlayerAnimIndex(ItemAssistant.getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());*/
				}
			}
		});
	}
}
