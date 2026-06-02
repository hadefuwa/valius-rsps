package com.client.map.tile;

public class ShapedTile {

    public boolean textured;

    public ShapedTile(int y, int centerOverColour, int northUnderColour, int northEastZ, int overlayTexture, int underlayTexture, int neOverColour, int rotation, int centerUnderColour,
                      int rgb, int neUnderColour, int northZ, int eastZ, int centerZ, int shape, int northOverColour, int eastOverColour,
                      int eastUnderColour, int x, int rgbaA, boolean tex) {
        textured = tex;
        flat = !(centerZ != eastZ || centerZ != northEastZ || centerZ != northZ);
        this.shape = shape;
        this.rotation = rotation;
        colourRGB = rgb;
        colourRGBA = rgbaA;
        int fullSize = 128;
        int halfSize = 64;
        int quaterSize = 32;
        int threeQuaters = 96;
        int[][] shapedTilePointData = {
                {1, 3, 5, 7}, 
                {1, 3, 5, 7}, 
                {1, 3, 5, 7}, 
                {1, 3, 5, 7, 6},
                {1, 3, 5, 7, 6}, 
                {1, 3, 5, 7, 6}, 
                {1, 3, 5, 7, 6},
                {1, 3, 5, 7, 2, 6},
                {1, 3, 5, 7, 2, 8}, 
                {1, 3, 5, 7, 2, 8},
                {1, 3, 5, 7, 11, 12},
                {1, 3, 5, 7, 11, 12},
                {1, 3, 5, 7, 13, 14}};
        int ai[] = shapedTilePointData[shape];
        int pointDataLength = ai.length;
        origVertexX = new int[pointDataLength];
        origVertexY = new int[pointDataLength];
        origVertexZ = new int[pointDataLength];
        int vertexColourOverlay[] = new int[pointDataLength];
        int vertexColourUnderlay[] = new int[pointDataLength];
        int xAbsolutePixel = x * fullSize;
        int yAbsolutePixel = y * fullSize;
        for (int vertex = 0; vertex < pointDataLength; vertex++) {
            int vertexType = ai[vertex];
            if ((vertexType & 1) == 0 && vertexType <= 8)
                vertexType = (vertexType - rotation - rotation - 1 & 7) + 1;
            if (vertexType > 8 && vertexType <= 12)
                vertexType = (vertexType - 9 - rotation & 3) + 9;
            if (vertexType > 12 && vertexType <= 16)
                vertexType = (vertexType - 13 - rotation & 3) + 13;
            int vertexX;
            int vertexZ;
            int vertexY;
            int underColourBlend;
            int overColourBlend;
            if (vertexType == 1) {
                vertexX = xAbsolutePixel;
                vertexZ = yAbsolutePixel;
                vertexY = centerZ;
                underColourBlend = centerUnderColour;
                overColourBlend = centerOverColour;
            } else if (vertexType == 2) {
                vertexX = xAbsolutePixel + halfSize;
                vertexZ = yAbsolutePixel;
                vertexY = centerZ + eastZ >> 1;
                underColourBlend = centerUnderColour + eastUnderColour >> 1;
                overColourBlend = centerOverColour + eastOverColour >> 1;
            } else if (vertexType == 3) {
                vertexX = xAbsolutePixel + fullSize;
                vertexZ = yAbsolutePixel;
                vertexY = eastZ;
                underColourBlend = eastUnderColour;
                overColourBlend = eastOverColour;
            } else if (vertexType == 4) {
                vertexX = xAbsolutePixel + fullSize;
                vertexZ = yAbsolutePixel + halfSize;
                vertexY = eastZ + northEastZ >> 1;
                underColourBlend = eastUnderColour + neUnderColour >> 1;
                overColourBlend = eastOverColour + neOverColour >> 1;
            } else if (vertexType == 5) {
                vertexX = xAbsolutePixel + fullSize;
                vertexZ = yAbsolutePixel + fullSize;
                vertexY = northEastZ;
                underColourBlend = neUnderColour;
                overColourBlend = neOverColour;
            } else if (vertexType == 6) {
                vertexX = xAbsolutePixel + halfSize;
                vertexZ = yAbsolutePixel + fullSize;
                vertexY = northEastZ + northZ >> 1;
                underColourBlend = neUnderColour + northUnderColour >> 1;
                overColourBlend = neOverColour + northOverColour >> 1;
            } else if (vertexType == 7) {
                vertexX = xAbsolutePixel;
                vertexZ = yAbsolutePixel + fullSize;
                vertexY = northZ;
                underColourBlend = northUnderColour;
                overColourBlend = northOverColour;
            } else if (vertexType == 8) {
                vertexX = xAbsolutePixel;
                vertexZ = yAbsolutePixel + halfSize;
                vertexY = northZ + centerZ >> 1;
                underColourBlend = northUnderColour + centerUnderColour >> 1;
                overColourBlend = northOverColour + centerOverColour >> 1;
            } else if (vertexType == 9) {
                vertexX = xAbsolutePixel + halfSize;
                vertexZ = yAbsolutePixel + quaterSize;
                vertexY = centerZ + eastZ >> 1;
                underColourBlend = centerUnderColour + eastUnderColour >> 1;
                overColourBlend = centerOverColour + eastOverColour >> 1;
            } else if (vertexType == 10) {
                vertexX = xAbsolutePixel + threeQuaters;
                vertexZ = yAbsolutePixel + halfSize;
                vertexY = eastZ + northEastZ >> 1;
                underColourBlend = eastUnderColour + neUnderColour >> 1;
                overColourBlend = eastOverColour + neOverColour >> 1;
            } else if (vertexType == 11) {
                vertexX = xAbsolutePixel + halfSize;
                vertexZ = yAbsolutePixel + threeQuaters;
                vertexY = northEastZ + northZ >> 1;
                underColourBlend = neUnderColour + northUnderColour >> 1;
                overColourBlend = neOverColour + northOverColour >> 1;
            } else if (vertexType == 12) {
                vertexX = xAbsolutePixel + quaterSize;
                vertexZ = yAbsolutePixel + halfSize;
                vertexY = northZ + centerZ >> 1;
                underColourBlend = northUnderColour + centerUnderColour >> 1;
                overColourBlend = northOverColour + centerOverColour >> 1;
            } else if (vertexType == 13) {
                vertexX = xAbsolutePixel + quaterSize;
                vertexZ = yAbsolutePixel + quaterSize;
                vertexY = centerZ;
                underColourBlend = centerUnderColour;
                overColourBlend = centerOverColour;
            } else if (vertexType == 14) {
                vertexX = xAbsolutePixel + threeQuaters;
                vertexZ = yAbsolutePixel + quaterSize;
                vertexY = eastZ;
                underColourBlend = eastUnderColour;
                overColourBlend = eastOverColour;
            } else if (vertexType == 15) {
                vertexX = xAbsolutePixel + threeQuaters;
                vertexZ = yAbsolutePixel + threeQuaters;
                vertexY = northEastZ;
                underColourBlend = neUnderColour;
                overColourBlend = neOverColour;
            } else {
                vertexX = xAbsolutePixel + quaterSize;
                vertexZ = yAbsolutePixel + threeQuaters;
                vertexY = northZ;
                underColourBlend = northUnderColour;
                overColourBlend = northOverColour;
            }
            origVertexX[vertex] = vertexX;
            origVertexY[vertex] = vertexY;
            origVertexZ[vertex] = vertexZ;
            vertexColourOverlay[vertex] = underColourBlend;
            vertexColourUnderlay[vertex] = overColourBlend;
        }

        int ai3[] = shapedTileElementData[shape];
        int triangleCount = ai3.length / 4;
        triangleA = new int[triangleCount];
        triangleB = new int[triangleCount];
        triangleC = new int[triangleCount];
        triangleHslA = new int[triangleCount];
        triangleHslB = new int[triangleCount];
        triangleHslC = new int[triangleCount];
        if (overlayTexture != -1 || underlayTexture != -1) {
            triangleTexture = new int[triangleCount];
        }
        int l7 = 0;
        for (int j8 = 0; j8 < triangleCount; j8++) {
            int l8 = ai3[l7];
            int k9 = ai3[l7 + 1];
            int i10 = ai3[l7 + 2];
            int k10 = ai3[l7 + 3];
            l7 += 4;
            if (k9 < 4)
                k9 = k9 - rotation & 3;
            if (i10 < 4)
                i10 = i10 - rotation & 3;
            if (k10 < 4)
                k10 = k10 - rotation & 3;
            triangleA[j8] = k9;
            triangleB[j8] = i10;
            triangleC[j8] = k10;
            if (l8 == 0) {
                triangleHslA[j8] = vertexColourOverlay[k9];
                triangleHslB[j8] = vertexColourOverlay[i10];
                triangleHslC[j8] = vertexColourOverlay[k10];
                if (triangleTexture != null) {
                    triangleTexture[j8] = underlayTexture;
                }
            } else {
                triangleHslA[j8] = vertexColourUnderlay[k9];
                triangleHslB[j8] = vertexColourUnderlay[i10];
                triangleHslC[j8] = vertexColourUnderlay[k10];
                if (triangleTexture != null)
                    triangleTexture[j8] = overlayTexture;
            }
        }

        int i9 = centerZ;
        int l9 = eastZ;
        if (eastZ < i9)
            i9 = eastZ;
        if (eastZ > l9)
            l9 = eastZ;
        if (northEastZ < i9)
            i9 = northEastZ;
        if (northEastZ > l9)
            l9 = northEastZ;
        if (northZ < i9)
            i9 = northZ;
        if (northZ > l9)
            l9 = northZ;
        i9 /= 14;
        l9 /= 14;
    }

    public final int[] origVertexX;
    public  final int[] origVertexY;
    public  final int[] origVertexZ;
    public  final int[] triangleHslA;
    public final int[] triangleHslB;
    public final int[] triangleHslC;
    public final int[] triangleA;
    public  final int[] triangleB;
    public  final int[] triangleC;
    public int triangleTexture[];
    public final boolean flat;
    public  final int shape;
    public final int rotation;
    public final int colourRGB;
    public final int colourRGBA;
    public static final int[] screenX = new int[6];
    public static final int[] screenY = new int[6];
    public static final int[] viewSpaceX = new int[6];
    public  static final int[] viewSpaceY = new int[6];
    public  static final int[] viewSpaceZ = new int[6];
    public  static final int[] anIntArray693 = {1, 0};
    public static final int[] anIntArray694 = {2, 1};
    public  static final int[] anIntArray695 = {3, 3};
    private static final int[][] shapedTilePointData = {
    		{14, 15, 16, 8},
    		{14, 15, 16, 8},
            {1, 3, 5, 7}, 
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6}, 
            {1, 3, 5, 7, 6}, 
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 2, 6},
            {1, 3, 5, 7, 2, 8}, 
            {1, 3, 5, 7, 2, 8},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 13, 14}};
    private static final int[][] shapedTileElementData = {
            {0, 1, 2, 3,
             0, 0, 1, 3},
           
            {1, 1, 2, 3,
             1, 0, 1, 3},
           
            {0, 1, 2, 3,
             1, 0, 1, 3},
            
            {0, 0, 1, 2,
             0, 0, 2, 4,
             1, 0, 4, 3},
            
            {0, 0, 1, 4,
             0, 0, 4, 3,
             1, 1, 2, 4},
            
            {0, 0, 4, 3,
             1, 0, 1, 2,
             1, 0, 2, 4},
            
            {0, 1, 2, 4,
             1, 0, 1, 4,
             1, 0, 4, 3},
           
            {0, 4, 1, 2,
             0, 4, 2, 5, 
             1, 0, 4, 5,
             1, 0, 5, 3},
            
            {0, 4, 1, 2, 
             0, 4, 2, 3, 
             0, 4, 3, 5,
             1, 0, 4, 5},
          
            {0, 0, 4, 5,
             1, 4, 1, 2,
             1, 4, 2, 3,
             1, 4, 3, 5},
            
            {0, 0, 1, 5,
             0, 1, 4, 5,
             0, 1, 2, 4,
             1, 0, 5, 3,
             1, 5, 4, 3,
             1, 4, 2, 3},
            
            {1, 0, 1, 5, 
             1, 1, 4, 5,
             1, 1, 2, 4,
             0, 0, 5, 3,
             0, 5, 4, 3,
             0, 4, 2, 3},
            
            {1, 0, 5, 4,
             1, 0, 1, 5,
             0, 0, 4, 3,
             0, 4, 5, 3,
             0, 5, 2, 3,
             0, 1, 2, 5}
                    
    };

}
