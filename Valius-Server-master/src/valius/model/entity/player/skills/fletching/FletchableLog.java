package valius.model.entity.player.skills.fletching;

public enum FletchableLog {
	SHORTBOW(1511, 50, 5, 5, new int[] { 8889, 8888, 8887, 8886 }), 
	LONGBOW(1511, 48, 10, 10, new int[] { 8893, 8892, 8891, 8890 }), 
	ARROW_SHAFT(1511, 52, 1, 5, new int[] { 8897, 8896, 8895, 8894 }), 
	OAK_SHORTBOW(1521, 54, 20, 17, new int[] { 8889, 8888, 8887, 8886 }), 
	OAK_LONGBOW(1521, 56, 25, 25, new int[] { 8893, 8892, 8891, 8890 }), 
	OAK_SHAFT(1521, 52, 24, 16, new int[] { 8897, 8896, 8895, 8894 }), 
	WILLOW_SHORTBOW(1519, 60, 35, 33, new int[] { 8889, 8888, 8887, 8886 }), 
	WILLOW_LONGBOW(1519, 58, 40, 42, new int[] { 8893, 8892, 8891, 8890 }), 
	WILLOW_SHAFT(1519, 52, 39, 22, new int[] { 8897, 8896, 8895, 8894 }), 
	MAPLE_SHORTBOW(1517, 64, 50, 50, new int[] { 8889, 8888, 8887, 8886 }), 
	MAPLE_LONGBOW(1517, 62, 55, 58, new int[] { 8893, 8892, 8891, 8890 }), 
	MAPLE_SHAFT(1517, 52, 54, 32, new int[] { 8897, 8896, 8895, 8894 }), 
	YEW_SHORTBOW(1515, 68, 65, 68, new int[] { 8889, 8888, 8887, 8886 }), 
	YEW_LONGBOW(1515, 66, 70, 75, new int[] { 8893, 8892, 8891, 8890 }), 
	YEW_SHAFT(1515, 52, 69, 50, new int[] { 8897, 8896, 8895, 8894 }), 
	MAGIC_SHORTBOW(1513, 72, 80, 83, new int[] { 8889, 8888, 8887, 8886 }), 
	MAGIC_LONGBOW(1513, 70, 85, 92, new int[] { 8893, 8892, 8891, 8890 }), 
	MAGIC_SHAFT(1513, 52, 80, 83, new int[] { 8897, 8896, 8895, 8894 });

	private final int[] buttonIds;
	private final int item, product, level, experience;

	FletchableLog(final int logid, final int product, final int level, final int xp, int... buttonIds) {
		this.item = logid;
		this.product = product;
		this.level = level;
		this.experience = xp;
		this.buttonIds = buttonIds;
	}

	public int getItemId() {
		return item;
	}

	public int getProduct() {
		return product;
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public int[] getButtonIds() {
		return buttonIds;
	}

	public String getName() {
		String s = name().toLowerCase();
		String t = s.substring(0, 1).toUpperCase() + s.substring(1);
		t = t.replaceAll("_", " ");
		return t;
	}
}