/**
 * 
 */
package valius.content.cluescroll;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ReverendDread
 * Oct 16, 2019
 */
@Getter @AllArgsConstructor
public enum ClueDifficulty {	
	
	EASY(2714),
	MEDIUM(2802),
	HARD(2775),
	ELITE(12084),
	MASTER(19841);
	
	private int casket; //casket id
	
}
