package valius.model.entity.player.skills.fishing;

import java.util.Optional;
import java.util.stream.IntStream;

import valius.model.entity.player.Player;

public enum FishingTool {
	
	SMALL_NET(303),
	FISHING_ROD(307, 22842, 32286, 32287, 32288, 32289, 32290, 32291, 32292, 32293),
	FLY_FISHING_ROD(309, 22842, 32286, 32287, 32288, 32289, 32290, 32291, 32292, 32293),
	HARPOON(311, 10129, 21028, 21031),
	CAGE(301),
	BIG_NET(305),
	KARAMBWAN_VESSEL(3157),
	PEARL_ROD(22842, 32286, 32287, 32288, 32289, 32290, 32291, 32292, 32293),
	;
	
	
	private int[] itemIds;
	
	private FishingTool(int... itemIds) {
		this.itemIds = itemIds;
	}
	
	public boolean anyMatch(int itemId) {
		return IntStream.of(itemIds).anyMatch(toolId -> toolId == itemId);
	}
	
	public boolean playerHasAny(Player player) {
		return IntStream.of(itemIds).anyMatch(toolId -> player.getItems().playerHasItem(toolId) || player.getItems().wearingAny(toolId));
	}
	
	public static Optional<FishingTool> getToolForId(int itemId) {
		for(FishingTool tool : FishingTool.values()) {
			if(tool.anyMatch(itemId))
				return Optional.of(tool);
		}
		return Optional.empty();
	}
	
	public int getFirst() {
		return itemIds.length > 0 ? itemIds[0] : 0;
	}
}
