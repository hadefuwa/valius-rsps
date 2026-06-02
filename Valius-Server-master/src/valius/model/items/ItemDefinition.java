package valius.model.items;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Holds information regarding items
 *
 * @author Stuart 
 * @created 03/08/2012
 */
@Slf4j
public class ItemDefinition {
	
	private ItemDefinition(int id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * The definitions.
	 */
	private static Map<Integer, ItemDefinition> definitions = new HashMap<>();

	/**
	 * Loads item definitions from item_defs.json
	 */
	public static void load() throws IOException {
		System.out.println("Loading item definitions...");

		try(FileReader fr = new FileReader(new File("./Data/json/item_definitions.json"))){
			List<ItemDefinition> list = new Gson().fromJson(fr, new TypeToken<List<ItemDefinition>>() { }.getType());
	
			
			list.stream().filter(Objects::nonNull).forEach(item ->  { 
				/*item.setFullmask(ItemUtility.isFullMask(item.id));
				item.setFullbody(ItemUtility.isFullBody(item.id));
				item.setFullhat(ItemUtility.isFullHat(item.id));*/
				definitions.put((int) item.id, item);
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		log.info("Loaded {} item definitions.", definitions.size());
	}
	/**
	 * Get an items definition by id.
	 *
	 * @param id The id.
	 * @return The item definition.
	 */
	public static ItemDefinition forId(int id) {
		if (id < 0) {
			return definitions.get(0);
		}
		return definitions.getOrDefault(id, new ItemDefinition(id, "Missing(" + id + ")"));
	}

	/**
	 * A map of all definitions
	 * 
	 * @return the map
	 */
	public static Map<Integer, ItemDefinition> getDefinitions() {
		return definitions;
	}

	/**
	 * The id.
	 */
	private int id;

	/**
	 * The name.
	 */
	private String name;

	/**
	 * The description.
	 */
	private String desc;

	/**
	 * The value.
	 */
	private int value;
	
	/**
	 * High Alch Value
	 */
	private int highAlchValue;
	/**
	 * Low Alch value
	 */
	
	private int lowAlchValue;
	
	/**
	 * The value of the drop
	 */
	private int dropValue;

	/**
	 * The bonuses.
	 */
	private short[] bonus;

	/**
	 * The slot the item goes in.
	 */
	private byte slot;

	/**
	 * Full mask flag.
	 */
	@Setter private boolean fullmask;
	
	/**
	 * Full body flag.
	 */
	@Setter private boolean fullbody;
	
	/**
	 * Full hat flag.
	 */
	@Setter @Getter private boolean fullhat;
	
	/**
	 * Stackable flag
	 */
	private boolean stackable;

	/**
	 * Notable flag
	 */
	private boolean noteable;

	/**
	 * Stackable flag
	 */
	private boolean tradable;

	/**
	 * Wearable flag
	 */
	private boolean wearable;

	/**
	 * Show beard flag
	 */
	private boolean showBeard;

	/**
	 * Members flag
	 */
	private boolean members;

	/**
	 * Two handed flag
	 */
	private boolean twoHanded;

	/**
	 * Level requirements
	 */
	private final byte[] requirements = new byte[25];

   /* public ItemDefinition(short id, String name, String description, Equipment.Slot equipmentSlot, boolean stackable, int shopValue, int lowAlchValue, int highAlchValue, int[] bonus, boolean twoHanded, boolean fullHelm, boolean fullMask, boolean platebody) {
       this.id = id;
        this.name = name;
        this.desc = description;
        this.slot = equipmentSlot;
        this.stackable = stackable;
        this.shopValue = shopValue;
        this.lowAlchValue = lowAlchValue;
        this.highAlchValue = highAlchValue;
        this.bonus = bonus;
        this.twoHanded = twoHanded;
        this.fullHelm = fullHelm;
        this.fullMask = fullMask;
        this.platebody = platebody; 
    } */
	
	/**
	 * Get the id.
	 *
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the description.
	 *
	 * @return The description.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Get the value.
	 *
	 * @return The shopvalue.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * gets the high alch value
	 * @return highAlchValue
	 */
	public int getHighAlchValue() {
		return highAlchValue;
	}
	
	/**
	 * Get the low alch value
	 * @return lowAlchValue
	 */
	public int getlowAlchValue() {
		return lowAlchValue;
	}
	
	/**
	 * Get the bonus.
	 *
	 * @return The bonus.
	 */
	public short[] getBonus() {
		return bonus;
	}

	/**
	 * Gets the slot
	 *
	 * @return The slot.
	 */
	public byte getSlot() {
		return slot;
	}

	/**
	 * Gets the fullmask flag
	 *
	 * @return The fullmask flag
	 */
	public boolean isFullmask() {
		return fullmask;
	}

	/**
	 * Is this item stackable?
	 *
	 * @return
	 */
	public boolean isStackable() {
		return stackable;
	}

	/**
	 * Can this item be noted?
	 *
	 * @return
	 */
	public boolean isNoteable() {
		return noteable;
	}

	/**
	 * Is this item tradable?
	 *
	 * @return
	 */
	public boolean isTradable() {
		return tradable;
	}

	/**
	 * Get the level requirements
	 *
	 * @return
	 */
	public byte[] getRequirements() {
		return requirements;
	}

	/**
	 * Can this item be equipped
	 * 
	 * @return
	 */
	public boolean isWearable() {
		return wearable;
	}

	/**
	 * Does this item show the players beard
	 * 
	 * @return
	 */
	public boolean showBeard() {
		return showBeard;
	}

	/**
	 * Is this item two handed
	 * 
	 * @return
	 */
	public boolean isTwoHanded() {
		if (this.getId() == 20997)
			return true;
		return twoHanded;
	}

	/**
	 * Gets the drop value
	 * 
	 * @return
	 */
	public int getDropValue() {
		return dropValue;
	}

	/**
	 * Is this a members item
	 * 
	 * @return
	 */
	public boolean isMembers() {
		return members;
	}
}
