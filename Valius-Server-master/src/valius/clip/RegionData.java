package valius.clip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class RegionData {
	private int regionHash, landscape, objects;
}