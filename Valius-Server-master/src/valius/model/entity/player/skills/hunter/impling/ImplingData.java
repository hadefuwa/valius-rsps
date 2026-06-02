package valius.model.entity.player.skills.hunter.impling;

public enum ImplingData {
	
	/**
	 * Baby Impling.
	 */
	BABY("Baby Impling", 11238, 36, 17, 1635),
	/**
	 * Young Impling.
	 */
	YOUNG("Young Impling", 11240, 40, 22, 1636),
	/**
	 * Gourmet Impling.
	 */
	GOURMET("Gourmet Impling", 11242, 44, 28, 1637),
	/**
	 * Earth Impling.
	 */
	EARTH("Earth Impling", 11244, 25, 72, 1638),
	/**
	 * Essence Impling.
	 */
	ESSENCE("Essence Impling", 11246, 52, 42, 1639),
	/**
	 * Electic Impling.
	 */
	ELECTIC("Electic Impling", 11248, 64, 50, 1640),
	/**
	 * Nature Impling.
	 */
	NATURE("Nature Impling", 11250, 68, 58, 1641),
	/**
	 * Magpie Impling.
	 */
	MAGPIE("Magpie Impling", 11252, 88, 65, 1642),
	/**
	 * Ninja Impling.
	 */
	NINJA("Ninja Impling", 11254, 104, 74, 1643), 
	/**
	 * Dragon Impling.
	 */
	DRAGON("Dragon Impling", 11256, 130, 83, 1654), 
	/**
	 * Lucky Impling.
	 */
	LUCKY("Lucky Impling", 19732, 160, 89, 7302),
	/*
	 * Revenant
	 */
	REVENANT("Revenant Impling", 33773, 200, 92, 3384);
	
	/**
	 * Variables.
	 */
	public String name;
	public int jar, experience, requirement, npcId;

	/**
	 * Creating the Impling.
	 * @param name
	 * @param jar
	 * @param experience
	 * @param requirement
	 * @param npcId
	 */
	ImplingData(String name, int jar, int experience, int requirement, int npcId) {
		this.name = name;
		this.jar = jar;
		this.experience = experience;
		this.requirement = requirement;
		this.npcId = npcId;
	}
	
	public static ImplingData forId(int npcId) {
		for(ImplingData impling : ImplingData.values()) {
			if(impling.npcId == npcId) {
				return impling;
			}
		}
		return null;
	}

}
