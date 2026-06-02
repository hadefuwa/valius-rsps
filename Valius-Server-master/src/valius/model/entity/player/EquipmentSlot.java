package valius.model.entity.player;

import java.util.stream.Stream;

import lombok.Getter;

public enum EquipmentSlot {
	HELMET(0), CAPE(1), AMULET(2), WEAPON(3), CHEST(4), SHIELD(5), LEGS(7), HANDS(9), FEET(10), RING(12), AMMO(13);

	@Getter
	private final int index;

	private EquipmentSlot(int slot) {
		this.index = slot;
	}


	public static final EquipmentSlot valueOf(int slot) throws NullPointerException {
		return stream().filter(s -> s.index == slot).findFirst().orElse(null);
	}


	public static Stream<EquipmentSlot> stream() {
		return Stream.of(values());
	}
}
