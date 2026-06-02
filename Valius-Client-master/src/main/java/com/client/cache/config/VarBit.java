package com.client.cache.config;

import com.client.cache.Archive;
import com.client.io.Buffer;

public final class VarBit {

	public static void unpackConfig(Archive streamLoader) {
		Buffer stream = new Buffer(streamLoader.getDataForName("varbit.dat"));
		int cacheSize = stream.readShort();
		if (cache == null)
			cache = new VarBit[cacheSize];
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null)
				cache[j] = new VarBit();
			cache[j].readValues(stream);
		}

		if (stream.position != stream.payload.length)
			System.out.println("varbit load mismatch");
	}

	private void readValues(Buffer stream) {
		setting = stream.readShort();
		low = stream.readUnsignedByte();
		high = stream.readUnsignedByte();
	}

	private VarBit() {
		
	}

	public static VarBit cache[];
	public int setting;
	public int low;
	public int high;
	
}
