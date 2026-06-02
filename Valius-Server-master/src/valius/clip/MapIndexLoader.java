/**
 * 
 */
package valius.clip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import valius.util.compress.GZIPUtil;

/**
 * @author ReverendDread
 * Aug 3, 2019
 */
@Slf4j @Data
public class MapIndexLoader {

	private static final List<RegionData> regions = Lists.newArrayList();

	private static final Map<RegionData, byte[]> cachedLandscape = Maps.newConcurrentMap();
	private static final Map<RegionData, byte[]> cachedObjects = Maps.newConcurrentMap();
	
	public static void load() {
		try {
			log.info("Loading mapdata..");
			byte[] buffer = Files.readAllBytes(new File("./Data/world/map_index").toPath());
			ByteStream in = new ByteStream(buffer);
			int size = in.readUnsignedWord();
			for (int i = 0; i < size; i++) {
				regions.add(new RegionData(in.readUnsignedWord(), in.readUnsignedWord(),in.readUnsignedWord()));
			}
		} catch (IOException e) {
			log.error("Failed to load map_index!");
			e.printStackTrace();
		}
		Region.load();
	}
	
	public static Optional<RegionData> lookup(int regionId) {
		return regions.stream().filter(regionData -> regionData.getRegionHash() == regionId).findFirst();
	}

	public static Stream<RegionData> stream() {
		return regions.stream();
	}

	public static ByteStream getObjectData(Optional<RegionData> data) {
		if(!data.isPresent())
			return null;
		if(cachedObjects.containsKey(data.get())) {
			return new ByteStream(cachedObjects.get(data.get()));
		}
		File file = new File("./Data/world/map/" + data.get().getObjects() + ".gz");
		try {
			byte[] unzipped = GZIPUtil.decompress(file);
			if(data != null)
				cachedObjects.put(data.get(), unzipped);
			return new ByteStream(unzipped);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ByteStream getLandscapeData(Optional<RegionData> data) {
		if(!data.isPresent())
			return null;
		if(cachedLandscape.containsKey(data.get())) {
			return new ByteStream(cachedLandscape.get(data.get()));
		}
		File file = new File("./Data/world/map/" + data.get().getLandscape() + ".gz");
		try {
			byte[] unzipped = GZIPUtil.decompress(file);
			if(data != null)
				cachedLandscape.put(data.get(), unzipped);
			return new ByteStream(unzipped);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
