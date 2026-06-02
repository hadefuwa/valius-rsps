package com.client.cache.animation;

import com.client.io.Buffer;

public final class FrameBase {

    public FrameBase(Buffer stream) {
        int count = stream.readShort();
        transformationType = new int[count];
        vertexGroups = new int[count][];
        for(int j = 0; j < count; j++)
        	transformationType[j] = stream.readShort();
		for(int j = 0; j < count; j++)
			vertexGroups[j] = new int[stream.readShort()];
        for(int j = 0; j < count; j++)
			for(int l = 0; l < vertexGroups[j].length; l++)
				vertexGroups[j][l] = stream.readShort();
    }

    public final int[] transformationType;//anIntArray342
    public final int[][] vertexGroups;//anIntArray343
}
