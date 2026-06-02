/**
 * 
 */
package valius.content.gauntlet.crafting;

import java.util.Optional;

/**
 * @author ReverendDread
 * Aug 18, 2019
 */
public interface GauntletCraftable<T> {
	
	public int[][] getMaterials();
	
	public int getProduct();
	
}
