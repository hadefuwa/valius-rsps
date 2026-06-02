package valius.content.presets;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * Although the delete buttin may be the value of (edit - 1), values may need to change in the future.
 * 
 * @author Jason MacKeigan
 * @date Dec 30, 2014, 12:21:42 PM
 */
public enum PresetSlotAction {
	INVENTORY_0(0, 34956, 34955), 
	INVENTORY_1(1, 34967, 34966), 
	INVENTORY_2(2, 34978, 34977), 
	INVENTORY_3(3, 34989, 34988), 
	INVENTORY_4(4, 35000, 34999), 
	INVENTORY_5(5, 35011, 35010), 
	INVENTORY_6(6, 35022, 35021), 
	INVENTORY_7(7, 35033, 35032), 
	INVENTORY_8(8, 35044, 35043), 
	INVENTORY_9(9, 35055, 35054), 
	INVENTORY_10(10, 35066, 35065), 
	INVENTORY_11(11, 35077, 35076),
	INVENTORY_12(12, 35088, 35087),
	INVENTORY_13(13, 35099, 35098), 
	INVENTORY_14(14, 35110, 35109), 
	INVENTORY_15(15, 35121, 35120),
	INVENTORY_16(16, 35132, 35131),
	INVENTORY_17(17, 35143, 35142), 
	INVENTORY_18(18, 35154, 35153), 
	INVENTORY_19(19, 35165, 35164), 
	INVENTORY_20(20, 35176, 35175), 
	INVENTORY_21(21, 35187, 35186), 
	INVENTORY_22(22, 35198, 35197), 
	INVENTORY_23(23, 35209, 35208), 
	INVENTORY_24(24, 35220, 35219), 
	INVENTORY_25(25, 35231, 35230), 
	INVENTORY_26(26, 35242, 35241), 
	INVENTORY_27(27, 35253, 35252), 
	
	EQUIPMENT_0(0, 36056, 36055), 
	EQUIPMENT_1(1, 36067, 36066), 
	EQUIPMENT_2(2, 36078, 36077), 
	EQUIPMENT_3(3, 36089, 36088), 
	EQUIPMENT_4(4, 36100, 36099),
	EQUIPMENT_5(5, 36111, 36110),
	EQUIPMENT_7(6, 36122, 36121), 
	EQUIPMENT_9(7, 36133, 36132), 
	EQUIPMENT_10(8, 36144, 36143), 
	EQUIPMENT_12(9, 36155, 36154), 
	EQUIPMENT_13(10, 36166, 36165);

	private PresetType type;
	private int slot, edit, delete;

	private PresetSlotAction(int slot, int edit, int delete) {
		this.slot = slot;
		this.edit = edit;
		this.delete = delete;
		String name = name().toLowerCase().substring(0, name().indexOf("_"));
		this.type = name.equals("inventory") ? PresetType.INVENTORY : PresetType.EQUIPMENT;
	}

	public int getEdit() {
		return edit;
	}

	public int getDelete() {
		return delete;
	}

	public int getSlot() {
		return slot;
	}

	public int getItemSlot() {
		return Integer.parseInt(name().split("_")[1]);
	}

	public PresetType getType() {
		return type;
	}

	public static final Set<PresetSlotAction> SLOTS = Collections.unmodifiableSet(EnumSet.allOf(PresetSlotAction.class));

	public static int getEquipmentSlot(PresetType type, int slot) {
		Optional<PresetSlotAction> psa = SLOTS.stream().filter(s -> s.type.equals(type) && s.slot == slot).findFirst();
		return psa.get().getItemSlot();
	}

}
