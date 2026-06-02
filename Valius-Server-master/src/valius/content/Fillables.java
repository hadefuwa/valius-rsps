/**
 * 
 */
package valius.content;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import valius.clip.ObjectDef;
import valius.clip.WorldObject;
import valius.model.entity.player.Player;
import valius.model.items.Item;

/**
 * TODO travis fill this out plox
 * @author ReverendDread
 * Aug 18, 2019
 */
public class Fillables {

	private static final int[] WATER_SOURCES = new int[] { 36078 };
	
	@RequiredArgsConstructor @Getter
	public enum Fillable {
		
		VIAL(new int[] { -1 }, -1);
		
		private final int[] items;
		private final int product;
		
		public static Fillable getFillableForId(int itemId) {
			for (Fillable fillable : Fillable.values()) {
				for (int item : fillable.getItems()) {
					if (item == itemId) {
						return fillable;
					}
				}
			}
			return null;
		}
		
	}
	
	public static boolean fill(Player player, Item used, WorldObject object) {
		if (isWaterSource(object)) {
			Fillable filling = Fillable.getFillableForId(used.getId());
			if (filling != null) {
				player.getItems().deleteItem(used.getId(), 1);
				player.getItems().addItem(filling.getProduct(), 1);
				player.startAnimation(833);
				return true;
			}
		}
		return false;
	}
	
	public static boolean isWaterSource(WorldObject object) {
		ObjectDef def = ObjectDef.forID(object.getId());
		if (def.name.equalsIgnoreCase("fountain") || def.name.equalsIgnoreCase("sink") || def.name.equalsIgnoreCase("pump and drain") || def.name.equalsIgnoreCase("water pump")) {
			return true;
		}
		return IntStream.of(WATER_SOURCES).filter(source -> source == object.getId()).findAny().isPresent();
	}
	
}
