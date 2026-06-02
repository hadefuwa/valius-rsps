package com.client.map;

import java.util.Random;

import com.client.Client;
import com.client.cache.graphics.Sprite;
import com.client.cache.graphics.font.RSFont;
import com.client.collection.DoubleEndedQueue;
import com.client.config.Configuration;
import com.client.draw.raster.Raster;
import com.client.draw.raster.Rasterizer3D;
import com.client.map.objects.FloatingSprite;
import com.client.map.objects.FloatingText;
import com.client.map.objects.GroundDecoration;
import com.client.map.objects.GroundItem;
import com.client.map.objects.StaticObject;
import com.client.map.objects.Wall;
import com.client.map.objects.WallDecoration;
import com.client.map.tile.MapTile;
import com.client.map.tile.PlainTile;
import com.client.map.tile.ShapedTile;
import com.client.misc.ObjectKey;
import com.client.model.Model;
import com.client.model.VertexNormal;
import com.client.model.impl.Renderable;

public class SceneGraph {

	public static int renderDistance = 25;
	public static void setRenderDistance(int distance) {
		renderDistance = distance;
		aBooleanArrayArrayArrayArray491 = new boolean[8][32][(renderDistance * 2) + 1][(renderDistance * 2) + 1];
	}
    public SceneGraph(int ai[][][], int regionSizeX, int regionSizeY) {
  
        aBoolean434 = true;
        obj5Cache = new StaticObject[5000];
        anIntArray486 = new int[10000];
        anIntArray487 = new int[10000];
        planes = 4;
        this.regionSizeX = regionSizeX;
        this.regionSizeY = regionSizeY;
        groundArray = new MapTile[planes][regionSizeX][regionSizeY];
        anIntArrayArrayArray445 = new int[planes][regionSizeX + 1][regionSizeY + 1];
        anIntArrayArrayArray440 = ai;
        initToNull();
    }

    public static void nullLoader() {
        aClass28Array462 = null;
        anIntArray473 = null;
        aClass47ArrayArray474 = null;
        aClass19_477 = null;
        aBooleanArrayArrayArrayArray491 = null;
        aBooleanArrayArray492 = null;
    }

    public void initToNull() {
        for (int j = 0; j < planes; j++) {
            for (int k = 0; k < regionSizeX; k++) {
                for (int i1 = 0; i1 < regionSizeY; i1++)
                    groundArray[j][k][i1] = null;

            }

        }
        for (int l = 0; l < anInt472; l++) {
            for (int j1 = 0; j1 < anIntArray473[l]; j1++)
                aClass47ArrayArray474[l][j1] = null;

            anIntArray473[l] = 0;
        }

        for (int k1 = 0; k1 < obj5CacheCurrPos; k1++)
            obj5Cache[k1] = null;

        obj5CacheCurrPos = 0;
        for (int l1 = 0; l1 < aClass28Array462.length; l1++)
            aClass28Array462[l1] = null;

    }

    public void method275(int i) {
        anInt442 = i;
        for (int k = 0; k < regionSizeX; k++) {
            for (int l = 0; l < regionSizeY; l++)
                if (groundArray[i][k][l] == null)
                    groundArray[i][k][l] = new MapTile(i, k, l);

        }

    }

    public void method276(int i, int j) {
        MapTile class30_sub3 = groundArray[0][j][i];
        for (int l = 0; l < 3; l++) {
            MapTile class30_sub3_1 = groundArray[l][j][i] = groundArray[l + 1][j][i];
            if (class30_sub3_1 != null) {
                class30_sub3_1.anInt1307--;
                for (int j1 = 0; j1 < class30_sub3_1.anInt1317; j1++) {
                    StaticObject class28 = class30_sub3_1.obj5Array[j1];
                    if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == j
                            && class28.anInt525 == i)
                        class28.anInt517--;
                }

            }
        }
        if (groundArray[0][j][i] == null)
            groundArray[0][j][i] = new MapTile(0, j, i);
        groundArray[0][j][i].tileBelow = class30_sub3;
        groundArray[3][j][i] = null;
    }

    public static void createNewSceneCluster(int i, int j, int k, int l, int i1, int j1,
                                             int l1, int i2) {
        CullingCluster class47 = new CullingCluster();
        class47.anInt787 = j / 128;
        class47.anInt788 = l / 128;
        class47.anInt789 = l1 / 128;
        class47.anInt790 = i1 / 128;
        class47.anInt791 = i2;
        class47.anInt792 = j;
        class47.anInt793 = l;
        class47.anInt794 = l1;
        class47.anInt795 = i1;
        class47.anInt796 = j1;
        class47.anInt797 = k;
        aClass47ArrayArray474[i][anIntArray473[i]++] = class47;
    }

    public void method278(int i, int j, int k, int l) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 != null) {
            groundArray[i][j][k].anInt1321 = l;
        }
    }

    public void addTile(int i, int x, int y, int l, int i1, int overlaytex,
                        int underlaytex, int k1, int l1, int i2, int j2, int k2, int l2,
                        int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4,
                        boolean tex) {
        if (l == 0) {
            PlainTile class43 = new PlainTile(k2, l2, i3, j3, underlaytex, k4, false, tex);

            for (int i5 = i; i5 >= 0; i5--) {
                if (groundArray[i5][x][y] == null) {
                    groundArray[i5][x][y] = new MapTile(i5, x, y);
                }
            }

            groundArray[i][x][y].plainTile = class43;
            return;
        }

        if (l == 1) {
            PlainTile class43_1 = new PlainTile(k3, l3, i4, j4, overlaytex, l4,
                    k1 == l1 && k1 == i2 && k1 == j2, tex);

            for (int z = i; z >= 0; z--) {
                if (groundArray[z][x][y] == null) {
                    groundArray[z][x][y] = new MapTile(z, x, y);
                }
            }

            groundArray[i][x][y].plainTile = class43_1;
            return;
        }

        ShapedTile class40 = new ShapedTile(y, k3, j3, i2, overlaytex, underlaytex,
                i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, x, l4, tex);

        for (int k5 = i; k5 >= 0; k5--) {
            if (groundArray[k5][x][y] == null) {
                groundArray[k5][x][y] = new MapTile(k5, x, y);
            }
        }

        groundArray[i][x][y].aClass40_1312 = class40;
    }

    public void addFloatingText(int plane, int x, int y, int absX, int absY, RSFont font, String overheadText, int textColor, int textShadow, int overheadHeight, float opacity) {
    
    	FloatingText text = new FloatingText();
    	text.x = absX;
    	text.y = absY;
    	
    	text.font = font;
    	text.overheadText = overheadText;
    	text.textHeight = overheadHeight;
    	text.textOpacity = opacity;
    	text.textColor = textColor;
    	text.textShadow = textShadow;
    	
    	if (groundArray[plane][x][y] == null)
    		groundArray[plane][x][y] = new MapTile(plane, x, y);
    	groundArray[plane][x][y].floatingText = text;
    }

    public void addFloatingSprite(int plane, int x, int y, int absX, int absY, Sprite sprite, int overheadHeight, float opacity) {
    
    	FloatingSprite floatingSprite = new FloatingSprite();
    	floatingSprite.x = absX;
    	floatingSprite.y = absY;
    	
    	floatingSprite.sprite = sprite;
    	floatingSprite.spriteHeight = overheadHeight;
    	floatingSprite.spriteOpacity = opacity;
    	
    	if (groundArray[plane][x][y] == null)
    		groundArray[plane][x][y] = new MapTile(plane, x, y);
    	groundArray[plane][x][y].floatingSprite = floatingSprite;
    }

    public void method280(int i, int j, int k, Renderable class30_sub2_sub4, long i1, int j1) {
        if (class30_sub2_sub4 == null)
            return;
        GroundDecoration class49 = new GroundDecoration();
        class49.aClass30_Sub2_Sub4_814 = class30_sub2_sub4;
        class49.x = j1 * 128 + 64;
        class49.y = k * 128 + 64;
        class49.anInt811 = j;
        class49.uid = i1;
        if (groundArray[i][j1][k] == null)
            groundArray[i][j1][k] = new MapTile(i, j1, k);
        groundArray[i][j1][k].obj3 = class49;
    }

    public void method281(int i, long j, Renderable class30_sub2_sub4, int k,
                          Renderable class30_sub2_sub4_1, Renderable class30_sub2_sub4_2, int l,
                          int i1) {
        GroundItem object4 = new GroundItem();
        object4.aClass30_Sub2_Sub4_48 = class30_sub2_sub4_2;
        object4.anInt46 = i * 128 + 64;
        object4.anInt47 = i1 * 128 + 64;
        object4.anInt45 = k;
        object4.uid = j;
        object4.aClass30_Sub2_Sub4_49 = class30_sub2_sub4;
        object4.aClass30_Sub2_Sub4_50 = class30_sub2_sub4_1;
        int j1 = 0;
        MapTile class30_sub3 = groundArray[l][i][i1];
        if (class30_sub3 != null) {
            for (int k1 = 0; k1 < class30_sub3.anInt1317; k1++)
                if (class30_sub3.obj5Array[k1].renderable instanceof Model) {
                    int l1 = ((Model) class30_sub3.obj5Array[k1].renderable).itemDropHeight;
                    if (l1 > j1)
                        j1 = l1;
                }

        }
        object4.anInt52 = j1;
        if (groundArray[l][i][i1] == null)
            groundArray[l][i][i1] = new MapTile(l, i, i1);
        groundArray[l][i][i1].obj4 = object4;
    }

    public void method282(int i, Renderable class30_sub2_sub4, long j, int k,
                          int l, Renderable class30_sub2_sub4_1, int i1, int j1,
                          int k1) {
        if (class30_sub2_sub4 == null && class30_sub2_sub4_1 == null) {
            return;
        }
        Wall object1 = new Wall();
        object1.uid = j;
        object1.centerX = l * 128 + 64;
        object1.centerY = k * 128 + 64;
        object1.centerZ = i1;
        object1.renderable = class30_sub2_sub4;
        object1.aClass30_Sub2_Sub4_279 = class30_sub2_sub4_1;
        object1.orientation = i;
        object1.orientation1 = j1;
        for (int l1 = k1; l1 >= 0; l1--)
            if (groundArray[l1][l][k] == null)
                groundArray[l1][l][k] = new MapTile(l1, l, k);

        groundArray[k1][l][k].obj1 = object1;
    }

    public void method283(long i, int j, int k, int i1, int j1, int k1,
                          Renderable class30_sub2_sub4, int l1, int i2, int j2) {
        if (class30_sub2_sub4 == null)
            return;
        WallDecoration class26 = new WallDecoration();
        class26.uid = i;
        class26.anInt500 = l1 * 128 + 64 + j1;
        class26.anInt501 = j * 128 + 64 + i2;
        class26.anInt499 = k1;
        class26.aClass30_Sub2_Sub4_504 = class30_sub2_sub4;
        class26.anInt502 = j2;
        class26.anInt503 = k;
        for (int k2 = i1; k2 >= 0; k2--)
            if (groundArray[k2][l1][j] == null)
                groundArray[k2][l1][j] = new MapTile(k2, l1, j);

        groundArray[i1][l1][j].obj2 = class26;
    }

    public boolean method284(long i, int j, int k,
                             Renderable class30_sub2_sub4, int l, int i1, int j1, int k1, int l1) {
        if (class30_sub2_sub4 == null) {
            return true;
        } else {
            int i2 = l1 * 128 + 64 * l;
            int j2 = k1 * 128 + 64 * k;
            return method287(i1, l1, k1, l, k, i2, j2, j, class30_sub2_sub4,
                    j1, false, i);
        }
    }

    public boolean method285(int i, int j, int k, long l, int i1, int j1,
                             int k1, Renderable class30_sub2_sub4, boolean flag) {
        if (class30_sub2_sub4 == null)
            return true;
        int l1 = k1 - j1;
        int i2 = i1 - j1;
        int j2 = k1 + j1;
        int k2 = i1 + j1;
        if (flag) {
            if (j > 640 && j < 1408)
                k2 += 128;
            if (j > 1152 && j < 1920)
                j2 += 128;
            if (j > 1664 || j < 384)
                i2 -= 128;
            if (j > 128 && j < 896)
                l1 -= 128;
        }
        l1 /= 128;
        i2 /= 128;
        j2 /= 128;
        k2 /= 128;
        return method287(i, l1, i2, (j2 - l1) + 1, (k2 - i2) + 1, k1, i1, k,
                class30_sub2_sub4, j, true, l);
    }

    public boolean method286(int j, int k, Renderable class30_sub2_sub4, int l,
                             int i1, int j1, int k1, int l1, int i2, long j2, int k2) {
        return class30_sub2_sub4 == null
                || method287(j, l1, k2, (i2 - l1) + 1, (i1 - k2) + 1, j1, k,
                k1, class30_sub2_sub4, l, true, j2);
    }

    private boolean method287(int i, int j, int k, int l, int i1, int j1,
                              int k1, int l1, Renderable class30_sub2_sub4, int i2, boolean flag,
                              long uid) {
        for (int x = j; x < j + l; x++) {
            for (int y = k; y < k + i1; y++) {
                if (x < 0 || y < 0 || x >= regionSizeX || y >= regionSizeY)
                    return false;
                MapTile class30_sub3 = groundArray[i][x][y];
                if (class30_sub3 != null && class30_sub3.anInt1317 >= 5)
                    return false;
            }

        }

        StaticObject class28 = new StaticObject();
        class28.uid = uid;
        class28.anInt517 = i;
        class28.anInt519 = j1;
        class28.anInt520 = k1;
        class28.anInt518 = l1;
        class28.renderable = class30_sub2_sub4;
        class28.anInt522 = i2;
        class28.anInt523 = j;
        class28.anInt525 = k;
        class28.anInt524 = (j + l) - 1;
        class28.anInt526 = (k + i1) - 1;
        for (int i3 = j; i3 < j + l; i3++) {
            for (int j3 = k; j3 < k + i1; j3++) {
                int k3 = 0;
                if (i3 > j)
                    k3++;
                if (i3 < (j + l) - 1)
                    k3 += 4;
                if (j3 > k)
                    k3 += 8;
                if (j3 < (k + i1) - 1)
                    k3 += 2;
                for (int l3 = i; l3 >= 0; l3--)
                    if (groundArray[l3][i3][j3] == null)
                        groundArray[l3][i3][j3] = new MapTile(l3, i3, j3);

                MapTile class30_sub3_1 = groundArray[i][i3][j3];
                class30_sub3_1.obj5Array[class30_sub3_1.anInt1317] = class28;
                class30_sub3_1.anIntArray1319[class30_sub3_1.anInt1317] = k3;
                class30_sub3_1.anInt1320 |= k3;
                class30_sub3_1.anInt1317++;
            }

        }

        if (flag)
            obj5Cache[obj5CacheCurrPos++] = class28;
        return true;
    }

    public void clearObj5Cache() {
        for (int i = 0; i < obj5CacheCurrPos; i++) {
            StaticObject object5 = obj5Cache[i];
            method289(object5);
            obj5Cache[i] = null;
        }

        obj5CacheCurrPos = 0;
    }

    private void method289(StaticObject class28) {
        for (int j = class28.anInt523; j <= class28.anInt524; j++) {
            for (int k = class28.anInt525; k <= class28.anInt526; k++) {
                MapTile class30_sub3 = groundArray[class28.anInt517][j][k];
                if (class30_sub3 != null) {
                    for (int l = 0; l < class30_sub3.anInt1317; l++) {
                        if (class30_sub3.obj5Array[l] != class28)
                            continue;
                        class30_sub3.anInt1317--;
                        for (int i1 = l; i1 < class30_sub3.anInt1317; i1++) {
                            class30_sub3.obj5Array[i1] = class30_sub3.obj5Array[i1 + 1];
                            class30_sub3.anIntArray1319[i1] = class30_sub3.anIntArray1319[i1 + 1];
                        }

                        class30_sub3.obj5Array[class30_sub3.anInt1317] = null;
                        break;
                    }

                    class30_sub3.anInt1320 = 0;
                    for (int j1 = 0; j1 < class30_sub3.anInt1317; j1++)
                        class30_sub3.anInt1320 |= class30_sub3.anIntArray1319[j1];

                }
            }

        }

    }

    public void method290(int i, int k, int l, int i1) {
        MapTile class30_sub3 = groundArray[i1][l][i];
        if (class30_sub3 == null)
            return;
        WallDecoration class26 = class30_sub3.obj2;
        if (class26 != null) {
            int j1 = l * 128 + 64;
            int k1 = i * 128 + 64;
            class26.anInt500 = j1 + ((class26.anInt500 - j1) * k) / 16;
            class26.anInt501 = k1 + ((class26.anInt501 - k1) * k) / 16;
        }
    }

    public void method291(int i, int j, int k, byte byte0) {
        MapTile class30_sub3 = groundArray[j][i][k];
        if (byte0 != -119)
            aBoolean434 = !aBoolean434;
        if (class30_sub3 != null) {
            class30_sub3.obj1 = null;
        }
    }

    public void method292(int j, int k, int l) {
        MapTile class30_sub3 = groundArray[k][l][j];
        if (class30_sub3 != null) {
            class30_sub3.obj2 = null;
        }
    }

    public void method293(int i, int k, int l) {
        MapTile class30_sub3 = groundArray[i][k][l];
        if (class30_sub3 == null)
            return;
        for (int j1 = 0; j1 < class30_sub3.anInt1317; j1++) {
            StaticObject class28 = class30_sub3.obj5Array[j1];
            if ((ObjectKey.getObjectOpcode(class28.uid)) == 2 && class28.anInt523 == k
                    && class28.anInt525 == l) {
                method289(class28);
                return;
            }
        }

    }

    public void method294(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][k][j];
        if (class30_sub3 == null)
            return;
        class30_sub3.obj3 = null;
    }

    public void method295(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 != null) {
            class30_sub3.obj4 = null;
        }
    }

    public Wall method296(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 == null)
            return null;
        else
            return class30_sub3.obj1;
    }

    public WallDecoration method297(int i, int k, int l) {
        MapTile class30_sub3 = groundArray[l][i][k];
        if (class30_sub3 == null)
            return null;
        else
            return class30_sub3.obj2;
    }

    public StaticObject method298(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[k][i][j];
        if (class30_sub3 == null)
            return null;
        for (int l = 0; l < class30_sub3.anInt1317; l++) {
            StaticObject class28 = class30_sub3.obj5Array[l];
            if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == i
                    && class28.anInt525 == j)
                return class28;
        }
        return null;
    }

    public GroundDecoration method299(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[k][j][i];
        if (class30_sub3 == null || class30_sub3.obj3 == null)
            return null;
        else
            return class30_sub3.obj3;
    }

    public long method300(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 == null || class30_sub3.obj1 == null)
            return 0;
        else
            return class30_sub3.obj1.uid;
    }

    public long method301(int i, int j, int l) {
        MapTile class30_sub3 = groundArray[i][j][l];
        if (class30_sub3 == null || class30_sub3.obj2 == null)
            return 0;
        else
            return class30_sub3.obj2.uid;
    }

    public long method302(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 == null)
            return 0;
        for (int l = 0; l < class30_sub3.anInt1317; l++) {
            StaticObject class28 = class30_sub3.obj5Array[l];
            if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == j
                    && class28.anInt525 == k)
                return class28.uid;
        }

        return 0;
    }

    public long method303(int i, int j, int k) {
        MapTile class30_sub3 = groundArray[i][j][k];
        if (class30_sub3 == null || class30_sub3.obj3 == null)
            return 0;
        else
            return class30_sub3.obj3.uid;
    }

    public int method304(int i, int j, int k, long l) {
        MapTile class30_sub3 = groundArray[i][j][k];
        
        if (class30_sub3 == null)
            return -1;
        if (class30_sub3.obj1 != null && class30_sub3.obj1.uid == l)
            return  (ObjectKey.getObjectOrientation(l) << 6) + ObjectKey.getObjectType(l);
        if (class30_sub3.obj2 != null && class30_sub3.obj2.uid == l)
            return  (ObjectKey.getObjectOrientation(l) << 6) + ObjectKey.getObjectType(l);
        if (class30_sub3.obj3 != null && class30_sub3.obj3.uid == l)
            return  (ObjectKey.getObjectOrientation(l) << 6) + ObjectKey.getObjectType(l);
        for (int i1 = 0; i1 < class30_sub3.anInt1317; i1++)
            if (class30_sub3.obj5Array[i1].uid == l)
                return (ObjectKey.getObjectOrientation(l) << 6) + ObjectKey.getObjectType(l);

        return -1;
    }

    public void method305(int i, int k, int i1) {
    		int j = 100;
		int l = 768;
		int j1 = (int)Math.sqrt(k * k + i * i + i1 * i1);
		int k1 = l * j1 >> 8;
        for (int l1 = 0; l1 < planes; l1++) {
            for (int i2 = 0; i2 < regionSizeX; i2++) {
                for (int j2 = 0; j2 < regionSizeY; j2++) {
                    MapTile class30_sub3 = groundArray[l1][i2][j2];
                    if (class30_sub3 != null) {
                        Wall class10 = class30_sub3.obj1;
                        if (class10 != null
                                && class10.renderable != null
                                && class10.renderable.normals != null) {
                            method307(l1, 1, 1, i2, j2,
                                    (Model) class10.renderable);
                            if (class10.aClass30_Sub2_Sub4_279 != null
                                    && class10.aClass30_Sub2_Sub4_279.normals != null) {
                                method307(l1, 1, 1, i2, j2,
                                        (Model) class10.aClass30_Sub2_Sub4_279);
                                method308(
                                        (Model) class10.renderable,
                                        (Model) class10.aClass30_Sub2_Sub4_279,
                                        0, 0, 0, false);
                                ((Model) class10.aClass30_Sub2_Sub4_279)
                                        .method480(j, k1, k, i, i1);
                            }
                            ((Model) class10.renderable).method480(
                                    j, k1, k, i, i1);
                        }
                        for (int k2 = 0; k2 < class30_sub3.anInt1317; k2++) {
                            StaticObject class28 = class30_sub3.obj5Array[k2];
                            if (class28 != null
                                    && class28.renderable != null
                                    && class28.renderable.normals != null) {
                                method307(
                                        l1,
                                        (class28.anInt524 - class28.anInt523) + 1,
                                        (class28.anInt526 - class28.anInt525) + 1,
                                        i2, j2,
                                        (Model) class28.renderable);
                                ((Model) class28.renderable)
                                        .method480(j, k1, k, i, i1);
                            }
                        }

                        GroundDecoration class49 = class30_sub3.obj3;
                        if (class49 != null
                                && class49.aClass30_Sub2_Sub4_814.normals != null) {
                            method306(i2, l1,
                                    (Model) class49.aClass30_Sub2_Sub4_814, j2);
                            ((Model) class49.aClass30_Sub2_Sub4_814).method480(
                                    j, k1, k, i, i1);
                        }
                    }
                }

            }

        }

    }

    private void method306(int i, int j, Model model, int k) {
        if (i < regionSizeX) {
            MapTile class30_sub3 = groundArray[j][i + 1][k];
            if (class30_sub3 != null
                    && class30_sub3.obj3 != null
                    && class30_sub3.obj3.aClass30_Sub2_Sub4_814.normals != null)
                method308(model,
                        (Model) class30_sub3.obj3.aClass30_Sub2_Sub4_814, 128,
                        0, 0, true);
        }
        if (k < regionSizeX) {
            MapTile class30_sub3_1 = groundArray[j][i][k + 1];
            if (class30_sub3_1 != null
                    && class30_sub3_1.obj3 != null
                    && class30_sub3_1.obj3.aClass30_Sub2_Sub4_814.normals != null)
                method308(model,
                        (Model) class30_sub3_1.obj3.aClass30_Sub2_Sub4_814, 0,
                        0, 128, true);
        }
        if (i < regionSizeX && k < regionSizeY) {
            MapTile class30_sub3_2 = groundArray[j][i + 1][k + 1];
            if (class30_sub3_2 != null
                    && class30_sub3_2.obj3 != null
                    && class30_sub3_2.obj3.aClass30_Sub2_Sub4_814.normals != null)
                method308(model,
                        (Model) class30_sub3_2.obj3.aClass30_Sub2_Sub4_814,
                        128, 0, 128, true);
        }
        if (i < regionSizeX && k > 0) {
            MapTile class30_sub3_3 = groundArray[j][i + 1][k - 1];
            if (class30_sub3_3 != null
                    && class30_sub3_3.obj3 != null
                    && class30_sub3_3.obj3.aClass30_Sub2_Sub4_814.normals != null)
                method308(model,
                        (Model) class30_sub3_3.obj3.aClass30_Sub2_Sub4_814,
                        128, 0, -128, true);
        }
    }

    private void method307(int i, int j, int k, int l, int i1, Model model) {
        boolean flag = true;
        int j1 = l;
        int k1 = l + j;
        int l1 = i1 - 1;
        int i2 = i1 + k;
        for (int j2 = i; j2 <= i + 1; j2++)
            if (j2 != planes) {
                for (int k2 = j1; k2 <= k1; k2++)
                    if (k2 >= 0 && k2 < regionSizeX) {
                        for (int l2 = l1; l2 <= i2; l2++)
                            if (l2 >= 0
                                    && l2 < regionSizeY
                                    && (!flag || k2 >= k1 || l2 >= i2 || l2 < i1
                                    && k2 != l)) {
                                MapTile class30_sub3 = groundArray[j2][k2][l2];
                                if (class30_sub3 != null) {
                                    int i3 = (anIntArrayArrayArray440[j2][k2][l2]
                                            + anIntArrayArrayArray440[j2][k2 + 1][l2]
                                            + anIntArrayArrayArray440[j2][k2][l2 + 1] + anIntArrayArrayArray440[j2][k2 + 1][l2 + 1])
                                            / 4
                                            - (anIntArrayArrayArray440[i][l][i1]
                                            + anIntArrayArrayArray440[i][l + 1][i1]
                                            + anIntArrayArrayArray440[i][l][i1 + 1] + anIntArrayArrayArray440[i][l + 1][i1 + 1])
                                            / 4;
                                    Wall class10 = class30_sub3.obj1;
                                    if (class10 != null
                                            && class10.renderable != null
                                            && class10.renderable.normals != null)
                                        method308(
                                                model,
                                                (Model) class10.renderable,
                                                (k2 - l) * 128 + (1 - j) * 64,
                                                i3, (l2 - i1) * 128 + (1 - k)
                                                        * 64, flag);
                                    if (class10 != null
                                            && class10.aClass30_Sub2_Sub4_279 != null
                                            && class10.aClass30_Sub2_Sub4_279.normals != null)
                                        method308(
                                                model,
                                                (Model) class10.aClass30_Sub2_Sub4_279,
                                                (k2 - l) * 128 + (1 - j) * 64,
                                                i3, (l2 - i1) * 128 + (1 - k)
                                                        * 64, flag);
                                    for (int j3 = 0; j3 < class30_sub3.anInt1317; j3++) {
                                        StaticObject class28 = class30_sub3.obj5Array[j3];
                                        if (class28 != null
                                                && class28.renderable != null
                                                && class28.renderable.normals != null) {
                                            int k3 = (class28.anInt524 - class28.anInt523) + 1;
                                            int l3 = (class28.anInt526 - class28.anInt525) + 1;
                                            method308(
                                                    model,
                                                    (Model) class28.renderable,
                                                    (class28.anInt523 - l)
                                                            * 128 + (k3 - j)
                                                            * 64, i3,
                                                    (class28.anInt525 - i1)
                                                            * 128 + (l3 - k)
                                                            * 64, flag);
                                        }
                                    }

                                }
                            }

                    }

                j1--;
                flag = false;
            }

    }

    private void method308(Model model, Model model_1, int i, int j, int k,
                           boolean flag) {
        anInt488++;
        int l = 0;
        int ai[] = model_1.vertexX;
        int i1 = model_1.numVertices;
        for (int j1 = 0; j1 < model.numVertices; j1++) {
            VertexNormal class33 = model.normals[j1];
            VertexNormal class33_1 = model.alsoVertexNormals[j1];
            if (class33_1.anInt605 != 0) {
                int i2 = model.vertexY[j1] - j;
                if (i2 <= model_1.maximumYVertex) {
                    int j2 = model.vertexX[j1] - i;
                    if (j2 >= model_1.minimumXVertex && j2 <= model_1.maximumXVertex) {
                        int k2 = model.vertexZ[j1] - k;
                        if (k2 >= model_1.minimumZVertex && k2 <= model_1.maximumZVertex) {
                            for (int l2 = 0; l2 < i1; l2++) {
                                VertexNormal class33_2 = model_1.normals[l2];
                                VertexNormal class33_3 = model_1.alsoVertexNormals[l2];
                                if (j2 == ai[l2]
                                        && k2 == model_1.vertexZ[l2]
                                        && i2 == model_1.vertexY[l2]
                                        && class33_3.anInt605 != 0) {
                                    class33.anInt602 += class33_3.anInt602;
                                    class33.anInt603 += class33_3.anInt603;
                                    class33.anInt604 += class33_3.anInt604;
                                    class33.anInt605 += class33_3.anInt605;
                                    class33_2.anInt602 += class33_1.anInt602;
                                    class33_2.anInt603 += class33_1.anInt603;
                                    class33_2.anInt604 += class33_1.anInt604;
                                    class33_2.anInt605 += class33_1.anInt605;
                                    l++;
                                    anIntArray486[j1] = anInt488;
                                    anIntArray487[l2] = anInt488;
                                }
                            }

                        }
                    }
                }
            }
        }

        if (l < 3 || !flag)
            return;
        for (int k1 = 0; k1 < model.numberOfTriangleFaces; k1++)
            if (anIntArray486[model.face_a[k1]] == anInt488
                    && anIntArray486[model.face_b[k1]] == anInt488
                    && anIntArray486[model.face_c[k1]] == anInt488)
                model.face_render_type[k1] = -1;

        for (int l1 = 0; l1 < model_1.numberOfTriangleFaces; l1++)
            if (anIntArray487[model_1.face_a[l1]] == anInt488
                    && anIntArray487[model_1.face_b[l1]] == anInt488
                    && anIntArray487[model_1.face_c[l1]] == anInt488)
                model_1.face_render_type[l1] = -1;

    }

    public void method309(int ai[], int i, int k, int l, int i1) {
        int j = 512;// was parameter
        MapTile class30_sub3 = groundArray[k][l][i1];
        if (class30_sub3 == null)
            return;
        PlainTile class43 = class30_sub3.plainTile;
        if (class43 != null) {
            int j1 = class43.anInt722;
            if (j1 == 0)
                return;
            for (int k1 = 0; k1 < 4; k1++) {
                ai[i] = j1;
                ai[i + 1] = j1;
                ai[i + 2] = j1;
                ai[i + 3] = j1;
                i += j;
            }

            return;
        }
        ShapedTile class40 = class30_sub3.aClass40_1312;
        if (class40 == null)
            return;
        int l1 = class40.shape;
        int i2 = class40.rotation;
        int j2 = class40.colourRGB;
        int k2 = class40.colourRGBA;
        int ai1[] = anIntArrayArray489[l1];
        int ai2[] = anIntArrayArray490[i2];
        int l2 = 0;
        if (j2 != 0) {
            for (int i3 = 0; i3 < 4; i3++) {
                ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                i += j;
            }

            return;
        }
        for (int j3 = 0; j3 < 4; j3++) {
            if (ai1[ai2[l2++]] != 0)
                ai[i] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 1] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 2] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 3] = k2;
            i += j;
        }

    }

    public static void method310(int i, int j, int k, int l, int ai[]) {
        anInt495 = 0;
        anInt496 = 0;
        anInt497 = k;
        anInt498 = l;
        anInt493 = k / 2;
        anInt494 = l / 2;
        boolean aflag[][][][] = new boolean[9][32][(renderDistance * 2) + 3][(renderDistance * 2) + 3];
        for (int i1 = 128; i1 <= 384; i1 += 32) {
            for (int j1 = 0; j1 < 2048; j1 += 64) {
                sineYCurve = Model.SINE[i1];
                cosineYCurve = Model.COSINE[i1];
                sineXCurve = Model.SINE[j1];
                cosineXCurve = Model.COSINE[j1];
                int l1 = (i1 - 128) / 32;
                int j2 = j1 / 64;
                for (int l2 = -(renderDistance + 1); l2 <= (renderDistance + 1); l2++) {
                    for (int j3 = -(renderDistance + 1); j3 <= (renderDistance + 1); j3++) {
                        int k3 = l2 * 128;
                        int i4 = j3 * 128;
                        boolean flag2 = false;
                        for (int k4 = -i; k4 <= j; k4 += 128) {
                            if (!method311(ai[l1] + k4, i4, k3))
                                continue;
                            flag2 = true;
                            break;
                        }

                        aflag[l1][j2][l2 + renderDistance + 1][j3 + renderDistance + 1] = flag2;
                    }

                }

            }

        }

        for (int k1 = 0; k1 < 8; k1++) {
            for (int i2 = 0; i2 < 32; i2++) {
                for (int k2 = -renderDistance; k2 < renderDistance; k2++) {
                    for (int i3 = -renderDistance; i3 < renderDistance; i3++) {
                        boolean flag1 = false;
                        label0:
                        for (int l3 = -1; l3 <= 1; l3++) {
                            for (int j4 = -1; j4 <= 1; j4++) {
                                if (aflag[k1][i2][k2 + l3 + renderDistance + 1][i3 + j4
                                        + renderDistance + 1])
                                    flag1 = true;
                                else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + renderDistance
                                        + 1][i3 + j4 + renderDistance + 1])
                                    flag1 = true;
                                else if (aflag[k1 + 1][i2][k2 + l3 + renderDistance + 1][i3
                                        + j4 + renderDistance + 1]) {
                                    flag1 = true;
                                } else {
                                    if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3
                                            + renderDistance + 1][i3 + j4 + renderDistance + 1])
                                        continue;
                                    flag1 = true;
                                }
                                break label0;
                            }

                        }

                        aBooleanArrayArrayArrayArray491[k1][i2][k2 + renderDistance][i3 + renderDistance] = flag1;
                    }

                }

            }

        }

    }

    private static boolean method311(int i, int j, int k) {
        int l = j * sineXCurve + k * cosineXCurve >> 16;
        int i1 = j * cosineXCurve - k * sineXCurve >> 16;
        int j1 = i * sineYCurve + i1 * cosineYCurve >> 16;
        int k1 = i * cosineYCurve - i1 * sineYCurve >> 16;
        if (j1 < 50 || j1 > 3500)
            return false;
        int l1 = anInt493 + (l * SceneGraph.focalLength) / j1;
        int i2 = anInt494 + (k1 * SceneGraph.focalLength) / j1;
        return l1 >= anInt495 && l1 <= anInt497 && i2 >= anInt496
                && i2 <= anInt498;
    }

    public void method312(int i, int j) {
        aBoolean467 = true;
        anInt468 = j;
        anInt469 = i;
        anInt470 = -1;
        anInt471 = -1;
    }
    public void renderScene(int xCameraPos, int yCameraPos, int xCameraCurve, int zCameraPos, int plane, int yCameraCurve) {
    	try {
    	renderScene(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, plane, yCameraCurve, false);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public void renderScene(int cameraPosX, int cameraPosY, int xCameraCurve, int cameraPosZ, int plane, int yCameraCurve, boolean loginScreen) {
        if (Rasterizer3D.saveDepth = Configuration.enableFogRendering || Client.enableParticles) {
            if (Rasterizer3D.depthBuffer == null
                    || Rasterizer3D.depthBuffer.length != Raster.pixels.length) {
                Rasterizer3D.depthBuffer = new float[Raster.pixels.length];
            }
            for (int index = Rasterizer3D.depthBuffer.length - 1; index >= 0; index--) {
                Rasterizer3D.depthBuffer[index] = 3500F;// 3500 being the viewing
                // distance
            }
        } else if (Rasterizer3D.depthBuffer != null) {
            Rasterizer3D.depthBuffer = null;
        }
        if (xCameraPos < 0)
            xCameraPos = 0;
        else if (xCameraPos >= regionSizeX * 128)
            xCameraPos = regionSizeX * 128 - 1;
        if (yCameraPos < 0)
            yCameraPos = 0;
        else if (yCameraPos >= regionSizeY * 128)
            yCameraPos = regionSizeY * 128 - 1;
        renderCycle++;
        sineYCurve = Model.SINE[yCameraCurve];
        cosineYCurve = Model.COSINE[yCameraCurve];
        sineXCurve = Model.SINE[xCameraCurve];
        cosineXCurve = Model.COSINE[xCameraCurve];
        aBooleanArrayArray492 = aBooleanArrayArrayArrayArray491[(yCameraCurve - 128) / 32][xCameraCurve / 64];
        xCameraPos = cameraPosX;
        zCameraPos = cameraPosZ;
        yCameraPos = cameraPosY;
        xCameraTile = xCameraPos / 128;
        yCameraTile = yCameraPos / 128;
        currentPlane = plane;
        minTileX = xCameraTile - renderDistance;
        if (minTileX < 0)
            minTileX = 0;
        minTileY = yCameraTile - renderDistance;
        if (minTileY < 0)
            minTileY = 0;
        maxTileX = xCameraTile + renderDistance;
        if (maxTileX > regionSizeX)
            maxTileX = regionSizeX;
        maxTileY = yCameraTile + renderDistance;
        if (maxTileY > regionSizeY)
            maxTileY = regionSizeY;
        buildCullingClusters();
        anInt446 = 0;
        for (int k1 = anInt442; k1 < planes; k1++) {
            MapTile tiles[][] = groundArray[k1];
            for (int i2 = minTileX; i2 < maxTileX; i2++) {
                for (int k2 = minTileY; k2 < maxTileY; k2++) {
                    MapTile mapTile = tiles[i2][k2];
                    if (mapTile != null)
                        if (mapTile.anInt1321 > plane
                                || !aBooleanArrayArray492[(i2 - xCameraTile) + renderDistance][(k2 - yCameraTile) + renderDistance]
                                && anIntArrayArrayArray440[k1][i2][k2] - zCameraPos < (loginScreen ? 100 : 2000)) {
                            mapTile.aBoolean1322 = false;
                            mapTile.aBoolean1323 = false;
                            mapTile.anInt1325 = 0;
                        } else {
                            mapTile.aBoolean1322 = true;
                            mapTile.aBoolean1323 = true;
                            mapTile.aBoolean1324 = mapTile.anInt1317 > 0;
                            anInt446++;
                        }
                }

            }

        }

        for (int l1 = anInt442; l1 < planes; l1++) {
            MapTile aclass30_sub3_1[][] = groundArray[l1];
            for (int l2 = -renderDistance; l2 <= 0; l2++) {
                int renderTopX = xCameraTile + l2;
                int renderBottomX = xCameraTile - l2;
                
                if (renderTopX >= minTileX || renderBottomX < maxTileX) {
                    for (int i4 = -renderDistance; i4 <= 0; i4++) {
                        int renderTopY = yCameraTile + i4;
                        int renderBottomY = yCameraTile - i4;
                        try {
                        if (renderTopX >= minTileX) {
                            if (renderTopY >= minTileY) {
                                MapTile class30_sub3_1 = aclass30_sub3_1[renderTopX][renderTopY];
                                if (class30_sub3_1 != null
                                        && class30_sub3_1.aBoolean1322)
                                    method314(class30_sub3_1, true);
                            }
                            if (renderBottomY < maxTileY) {
                                MapTile class30_sub3_2 = aclass30_sub3_1[renderTopX][renderBottomY];
                                if (class30_sub3_2 != null
                                        && class30_sub3_2.aBoolean1322)
                                    method314(class30_sub3_2, true);
                            }
                        }
                        if (renderBottomX < maxTileX) {
                            if (renderTopY >= minTileY) {
                                MapTile class30_sub3_3 = aclass30_sub3_1[renderBottomX][renderTopY];
                                if (class30_sub3_3 != null
                                        && class30_sub3_3.aBoolean1322)
                                    method314(class30_sub3_3, true);
                            }
                            if (renderBottomY < maxTileY) {
                                MapTile class30_sub3_4 = aclass30_sub3_1[renderBottomX][renderBottomY];
                                if (class30_sub3_4 != null
                                        && class30_sub3_4.aBoolean1322)
                                    method314(class30_sub3_4, true);
                            }
                        }
                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                        } catch(Exception ex) {

                            System.out.println("tX: " + renderTopX + " tY: " + renderTopY + " lX:" + renderBottomX + " lY: " + renderBottomY);
                        }
                    }

                }
            }

        }

        for (int j2 = anInt442; j2 < planes; j2++) {
            MapTile aclass30_sub3_2[][] = groundArray[j2];
            for (int j3 = -renderDistance; j3 <= 0; j3++) {
                int l3 = xCameraTile + j3;
                int j4 = xCameraTile - j3;
                if (l3 >= minTileX || j4 < maxTileX) {
                    for (int l4 = -renderDistance; l4 <= 0; l4++) {
                        int j5 = yCameraTile + l4;
                        int k5 = yCameraTile - l4;
                        if (l3 >= minTileX) {
                            if (j5 >= minTileY) {
                                MapTile class30_sub3_5 = aclass30_sub3_2[l3][j5];
                                if (class30_sub3_5 != null
                                        && class30_sub3_5.aBoolean1322)
                                    method314(class30_sub3_5, false);
                            }
                            if (k5 < maxTileY) {
                                MapTile class30_sub3_6 = aclass30_sub3_2[l3][k5];
                                if (class30_sub3_6 != null
                                        && class30_sub3_6.aBoolean1322)
                                    method314(class30_sub3_6, false);
                            }
                        }
                        if (j4 < maxTileX) {
                            if (j5 >= minTileY) {
                                MapTile class30_sub3_7 = aclass30_sub3_2[j4][j5];
                                if (class30_sub3_7 != null
                                        && class30_sub3_7.aBoolean1322)
                                    method314(class30_sub3_7, false);
                            }
                            if (k5 < maxTileY) {
                                MapTile class30_sub3_8 = aclass30_sub3_2[j4][k5];
                                if (class30_sub3_8 != null
                                        && class30_sub3_8.aBoolean1322)
                                    method314(class30_sub3_8, false);
                            }
                        }
                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                    }

                }
            }

        }
        aBoolean467 = false;
    }

    private void method314(MapTile class30_sub3, boolean flag) {
        aClass19_477.pushBack(class30_sub3);
        do {
            MapTile currentTile;
            do {
                currentTile = (MapTile) aClass19_477.popFront();
                if (currentTile == null)
                    return;
            } while (!currentTile.aBoolean1323);
            int i = currentTile.anInt1308;
            int j = currentTile.anInt1309;
            int k = currentTile.anInt1307;
            int l = currentTile.anInt1310;
            MapTile planeTiles[][] = groundArray[k];
            if (currentTile.aBoolean1322) {
                if (flag) {
                    if (k > 0) {
                        MapTile class30_sub3_2 = groundArray[k - 1][i][j];
                        if (class30_sub3_2 != null
                                && class30_sub3_2.aBoolean1323)
                            continue;
                    }
                    if (i <= xCameraTile && i > minTileX) {
                        MapTile class30_sub3_3 = planeTiles[i - 1][j];
                        if (class30_sub3_3 != null
                                && class30_sub3_3.aBoolean1323
                                && (class30_sub3_3.aBoolean1322 || (currentTile.anInt1320 & 1) == 0))
                            continue;
                    }
                    if (i >= xCameraTile && i < maxTileX - 1) {
                        MapTile class30_sub3_4 = planeTiles[i + 1][j];
                        if (class30_sub3_4 != null
                                && class30_sub3_4.aBoolean1323
                                && (class30_sub3_4.aBoolean1322 || (currentTile.anInt1320 & 4) == 0))
                            continue;
                    }
                    if (j <= yCameraTile && j > minTileY) {
                        MapTile class30_sub3_5 = planeTiles[i][j - 1];
                        if (class30_sub3_5 != null
                                && class30_sub3_5.aBoolean1323
                                && (class30_sub3_5.aBoolean1322 || (currentTile.anInt1320 & 8) == 0))
                            continue;
                    }
                    if (j >= yCameraTile && j < maxTileY - 1) {
                        MapTile class30_sub3_6 = planeTiles[i][j + 1];
                        if (class30_sub3_6 != null
                                && class30_sub3_6.aBoolean1323
                                && (class30_sub3_6.aBoolean1322 || (currentTile.anInt1320 & 2) == 0))
                            continue;
                    }
                } else {
                    flag = true;
                }
                currentTile.aBoolean1322 = false;
                if (currentTile.tileBelow != null) {
                    MapTile tileBelow = currentTile.tileBelow;
                    if (tileBelow.plainTile != null) {
                        if (!method320(0, i, j))
                            method315(tileBelow.plainTile, 0,
                                    sineYCurve, cosineYCurve, sineXCurve, cosineXCurve, i,
                                    j);
                    } else if (tileBelow.aClass40_1312 != null
                            && !method320(0, i, j))
                        method316(i, sineYCurve, sineXCurve,
                                tileBelow.aClass40_1312, cosineYCurve, j,
                                cosineXCurve);
                    Wall wall = tileBelow.obj1;
                    if (wall != null)
                        wall.renderable.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve, wall.centerX
                                        - xCameraPos,
                                wall.centerZ - zCameraPos, wall.centerY
                                        - yCameraPos, wall.uid);
                    for (int i2 = 0; i2 < tileBelow.anInt1317; i2++) {
                        StaticObject staticObject = tileBelow.obj5Array[i2];
                        if (staticObject != null)
                            staticObject.renderable.renderAtPoint(
                                    staticObject.anInt522, sineYCurve, cosineYCurve,
                                    sineXCurve, cosineXCurve, staticObject.anInt519
                                            - xCameraPos, staticObject.anInt518
                                            - zCameraPos, staticObject.anInt520
                                            - yCameraPos, staticObject.uid);
                    }

                }
                boolean flag1 = false;
                if (currentTile.plainTile != null) {
                    if (!method320(l, i, j)) {
                        flag1 = true;
                        method315(currentTile.plainTile, l, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve, i, j);
                    }
                } else if (currentTile.aClass40_1312 != null
                        && !method320(l, i, j)) {
                    flag1 = true;
                    method316(i, sineYCurve, sineXCurve,
                            currentTile.aClass40_1312, cosineYCurve, j, cosineXCurve);
                }
                int j1 = 0;
                int j2 = 0;
                Wall wall = currentTile.obj1;
                WallDecoration wallDeco = currentTile.obj2;
                if (wall != null || wallDeco != null) {
                    if (xCameraTile == i)
                        j1++;
                    else if (xCameraTile < i)
                        j1 += 2;
                    if (yCameraTile == j)
                        j1 += 3;
                    else if (yCameraTile > j)
                        j1 += 6;
                    j2 = anIntArray478[j1];
                    currentTile.anInt1328 = anIntArray480[j1];
                }
                if (wall != null) {
                    if ((wall.orientation & anIntArray479[j1]) != 0) {
                        if (wall.orientation == 16) {
                            currentTile.anInt1325 = 3;
                            currentTile.anInt1326 = anIntArray481[j1];
                            currentTile.anInt1327 = 3 - currentTile.anInt1326;
                        } else if (wall.orientation == 32) {
                            currentTile.anInt1325 = 6;
                            currentTile.anInt1326 = anIntArray482[j1];
                            currentTile.anInt1327 = 6 - currentTile.anInt1326;
                        } else if (wall.orientation == 64) {
                            currentTile.anInt1325 = 12;
                            currentTile.anInt1326 = anIntArray483[j1];
                            currentTile.anInt1327 = 12 - currentTile.anInt1326;
                        } else {
                            currentTile.anInt1325 = 9;
                            currentTile.anInt1326 = anIntArray484[j1];
                            currentTile.anInt1327 = 9 - currentTile.anInt1326;
                        }
                    } else {
                        currentTile.anInt1325 = 0;
                    }
                    if ((wall.orientation & j2) != 0
                            && !method321(l, i, j, wall.orientation)) {
                        wall.renderable.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve,
                                wall.centerX - xCameraPos,
                                wall.centerZ - zCameraPos,
                                wall.centerY - yCameraPos, wall.uid);
                    }
                    if ((wall.orientation1 & j2) != 0
                            && !method321(l, i, j, wall.orientation1))
                        wall.aClass30_Sub2_Sub4_279.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve,
                                wall.centerX - xCameraPos,
                                wall.centerZ - zCameraPos,
                                wall.centerY - yCameraPos, wall.uid);
                }
                if (wallDeco != null
                        && !method322(l, i, j,
                        wallDeco.aClass30_Sub2_Sub4_504.modelHeight))
                    if ((wallDeco.anInt502 & j2) != 0)
                        wallDeco.aClass30_Sub2_Sub4_504.renderAtPoint(
                                wallDeco.anInt503, sineYCurve, cosineYCurve,
                                sineXCurve, cosineXCurve, wallDeco.anInt500
                                        - xCameraPos, wallDeco.anInt499
                                        - zCameraPos, wallDeco.anInt501
                                        - yCameraPos, wallDeco.uid);
                    else if ((wallDeco.anInt502 & 0x300) != 0) {
                        int j4 = wallDeco.anInt500 - xCameraPos;
                        int l5 = wallDeco.anInt499 - zCameraPos;
                        int k6 = wallDeco.anInt501 - yCameraPos;
                        int i8 = wallDeco.anInt503;
                        int k9;
                        if (i8 == 1 || i8 == 2)
                            k9 = -j4;
                        else
                            k9 = j4;
                        int k10;
                        if (i8 == 2 || i8 == 3)
                            k10 = -k6;
                        else
                            k10 = k6;
                        if ((wallDeco.anInt502 & 0x100) != 0 && k10 < k9) {
                            int i11 = j4 + anIntArray463[i8];
                            int k11 = k6 + anIntArray464[i8];
                            wallDeco.aClass30_Sub2_Sub4_504.renderAtPoint(
                                    i8 * 512 + 256, sineYCurve, cosineYCurve,
                                    sineXCurve, cosineXCurve, i11, l5, k11,
                                    wallDeco.uid);
                        }
                        if ((wallDeco.anInt502 & 0x200) != 0 && k10 > k9) {
                            int j11 = j4 + anIntArray465[i8];
                            int l11 = k6 + anIntArray466[i8];
                            wallDeco.aClass30_Sub2_Sub4_504.renderAtPoint(
                                    i8 * 512 + 1280 & 0x7ff, sineYCurve,
                                    cosineYCurve, sineXCurve, cosineXCurve, j11, l5, l11,
                                    wallDeco.uid);
                        }
                    }
                if (flag1) {
                    GroundDecoration groundDeco = currentTile.obj3;
                    if (groundDeco != null) {
                        groundDeco.aClass30_Sub2_Sub4_814.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve, groundDeco.x
                                        - xCameraPos,
                                groundDeco.anInt811 - zCameraPos, groundDeco.y
                                        - yCameraPos, groundDeco.uid);
	                  
                    }
                    GroundItem object4_1 = currentTile.obj4;
                    if (object4_1 != null && object4_1.anInt52 == 0) {
                        if (object4_1.aClass30_Sub2_Sub4_49 != null)
                            object4_1.aClass30_Sub2_Sub4_49
                                    .renderAtPoint(0, sineYCurve, cosineYCurve, sineXCurve,
                                            cosineXCurve, object4_1.anInt46
                                                    - xCameraPos,
                                            object4_1.anInt45 - zCameraPos,
                                            object4_1.anInt47 - yCameraPos,
                                            object4_1.uid);
                        if (object4_1.aClass30_Sub2_Sub4_50 != null)
                            object4_1.aClass30_Sub2_Sub4_50
                                    .renderAtPoint(0, sineYCurve, cosineYCurve, sineXCurve,
                                            cosineXCurve, object4_1.anInt46
                                                    - xCameraPos,
                                            object4_1.anInt45 - zCameraPos,
                                            object4_1.anInt47 - yCameraPos,
                                            object4_1.uid);
                        if (object4_1.aClass30_Sub2_Sub4_48 != null)
                            object4_1.aClass30_Sub2_Sub4_48
                                    .renderAtPoint(0, sineYCurve, cosineYCurve, sineXCurve,
                                            cosineXCurve, object4_1.anInt46
                                                    - xCameraPos,
                                            object4_1.anInt45 - zCameraPos,
                                            object4_1.anInt47 - yCameraPos,
                                            object4_1.uid);
                    }
                }
                int k4 = currentTile.anInt1320;
                if (k4 != 0) {
                    if (i < xCameraTile && (k4 & 4) != 0) {
                        MapTile class30_sub3_17 = planeTiles[i + 1][j];
                        if (class30_sub3_17 != null
                                && class30_sub3_17.aBoolean1323)
                            aClass19_477.pushBack(class30_sub3_17);
                    }
                    if (j < yCameraTile && (k4 & 2) != 0) {
                        MapTile class30_sub3_18 = planeTiles[i][j + 1];
                        if (class30_sub3_18 != null
                                && class30_sub3_18.aBoolean1323)
                            aClass19_477.pushBack(class30_sub3_18);
                    }
                    if (i > xCameraTile && (k4 & 1) != 0) {
                        MapTile class30_sub3_19 = planeTiles[i - 1][j];
                        if (class30_sub3_19 != null
                                && class30_sub3_19.aBoolean1323)
                            aClass19_477.pushBack(class30_sub3_19);
                    }
                    if (j > yCameraTile && (k4 & 8) != 0) {
                        MapTile class30_sub3_20 = planeTiles[i][j - 1];
                        if (class30_sub3_20 != null
                                && class30_sub3_20.aBoolean1323)
                            aClass19_477.pushBack(class30_sub3_20);
                    }
                }
            }
            if (currentTile.anInt1325 != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < currentTile.anInt1317; k1++) {
                    if (currentTile.obj5Array[k1].anInt528 == renderCycle
                            || (currentTile.anIntArray1319[k1] & currentTile.anInt1325) != currentTile.anInt1326)
                        continue;
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    Wall class10_1 = currentTile.obj1;
                    if (!method321(l, i, j, class10_1.orientation))
                        class10_1.renderable.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve,
                                class10_1.centerX - xCameraPos,
                                class10_1.centerZ - zCameraPos,
                                class10_1.centerY - yCameraPos, class10_1.uid);
                    currentTile.anInt1325 = 0;
                }
            }
            if (currentTile.aBoolean1324)
                try {
                    int i1 = currentTile.anInt1317;
                    currentTile.aBoolean1324 = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        StaticObject class28_1 = currentTile.obj5Array[k2];
                        if (class28_1.anInt528 == renderCycle)
                            continue;
                        for (int k3 = class28_1.anInt523; k3 <= class28_1.anInt524; k3++) {
                            for (int l4 = class28_1.anInt525; l4 <= class28_1.anInt526; l4++) {
                                MapTile class30_sub3_21 = planeTiles[k3][l4];
                                if (class30_sub3_21.aBoolean1322) {
                                    currentTile.aBoolean1324 = true;
                                } else {
                                    if (class30_sub3_21.anInt1325 == 0)
                                        continue;
                                    int l6 = 0;
                                    if (k3 > class28_1.anInt523)
                                        l6++;
                                    if (k3 < class28_1.anInt524)
                                        l6 += 4;
                                    if (l4 > class28_1.anInt525)
                                        l6 += 8;
                                    if (l4 < class28_1.anInt526)
                                        l6 += 2;
                                    if ((l6 & class30_sub3_21.anInt1325) != currentTile.anInt1327)
                                        continue;
                                    currentTile.aBoolean1324 = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = class28_1;
                        int i5 = xCameraTile - class28_1.anInt523;
                        int i6 = class28_1.anInt524 - xCameraTile;
                        if (i6 > i5)
                            i5 = i6;
                        int i7 = yCameraTile - class28_1.anInt525;
                        int j8 = class28_1.anInt526 - yCameraTile;
                        if (j8 > i7)
                            class28_1.anInt527 = i5 + j8;
                        else
                            class28_1.anInt527 = i5 + i7;
                    }

                    while (l1 > 0) {
                        int i3 = -renderDistance * 2;
                        int l3 = -1;
                        for (int j5 = 0; j5 < l1; j5++) {
                            StaticObject class28_2 = aClass28Array462[j5];
                            if (class28_2.anInt528 != renderCycle)
                                if (class28_2.anInt527 > i3) {
                                    i3 = class28_2.anInt527;
                                    l3 = j5;
                                } else if (class28_2.anInt527 == i3) {
                                    int j7 = class28_2.anInt519 - xCameraPos;
                                    int k8 = class28_2.anInt520 - yCameraPos;
                                    int l9 = aClass28Array462[l3].anInt519
                                            - xCameraPos;
                                    int l10 = aClass28Array462[l3].anInt520
                                            - yCameraPos;
                                    if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10)
                                        l3 = j5;
                                }
                        }

                        if (l3 == -1)
                            break;
                        StaticObject class28_3 = aClass28Array462[l3];
                        class28_3.anInt528 = renderCycle;
                        
                        if (!method323(l, class28_3.anInt523,
                                class28_3.anInt524, class28_3.anInt525,
                                class28_3.anInt526,
                                class28_3.renderable.modelHeight))
                            class28_3.renderable.renderAtPoint(
                                    class28_3.anInt522, sineYCurve, cosineYCurve,
                                    sineXCurve, cosineXCurve, class28_3.anInt519
                                            - xCameraPos, class28_3.anInt518
                                            - zCameraPos, class28_3.anInt520
                                            - yCameraPos, class28_3.uid);
                        for (int k7 = class28_3.anInt523; k7 <= class28_3.anInt524; k7++) {
                            for (int l8 = class28_3.anInt525; l8 <= class28_3.anInt526; l8++) {
                                MapTile class30_sub3_22 = planeTiles[k7][l8];
                                if (class30_sub3_22.anInt1325 != 0)
                                    aClass19_477.pushBack(class30_sub3_22);
                                else if ((k7 != i || l8 != j)
                                        && class30_sub3_22.aBoolean1323)
                                    aClass19_477.pushBack(class30_sub3_22);
                            }

                        }

                    }
                    if (currentTile.aBoolean1324)
                        continue;
                } catch (Exception _ex) {
                    currentTile.aBoolean1324 = false;
                }
            if (!currentTile.aBoolean1323 || currentTile.anInt1325 != 0)
                continue;
            if (i <= xCameraTile && i > minTileX) {
                MapTile class30_sub3_8 = planeTiles[i - 1][j];
                if (class30_sub3_8 != null && class30_sub3_8.aBoolean1323)
                    continue;
            }
            if (i >= xCameraTile && i < maxTileX - 1) {
                MapTile class30_sub3_9 = planeTiles[i + 1][j];
                if (class30_sub3_9 != null && class30_sub3_9.aBoolean1323)
                    continue;
            }
            if (j <= yCameraTile && j > minTileY) {
                MapTile class30_sub3_10 = planeTiles[i][j - 1];
                if (class30_sub3_10 != null && class30_sub3_10.aBoolean1323)
                    continue;
            }
            if (j >= yCameraTile && j < maxTileY - 1) {
                MapTile class30_sub3_11 = planeTiles[i][j + 1];
                if (class30_sub3_11 != null && class30_sub3_11.aBoolean1323)
                    continue;
            }
            currentTile.aBoolean1323 = false;
            anInt446--;
            GroundItem object4 = currentTile.obj4;
            if (object4 != null && object4.anInt52 != 0) {
                if (object4.aClass30_Sub2_Sub4_49 != null)
                    object4.aClass30_Sub2_Sub4_49.renderAtPoint(0, sineYCurve,
                            cosineYCurve, sineXCurve, cosineXCurve, object4.anInt46
                                    - xCameraPos, object4.anInt45 - zCameraPos
                                    - object4.anInt52, object4.anInt47
                                    - yCameraPos, object4.uid);
                if (object4.aClass30_Sub2_Sub4_50 != null)
                    object4.aClass30_Sub2_Sub4_50.renderAtPoint(0, sineYCurve,
                            cosineYCurve, sineXCurve, cosineXCurve, object4.anInt46
                                    - xCameraPos, object4.anInt45 - zCameraPos
                                    - object4.anInt52, object4.anInt47
                                    - yCameraPos, object4.uid);
                if (object4.aClass30_Sub2_Sub4_48 != null)
                    object4.aClass30_Sub2_Sub4_48.renderAtPoint(0, sineYCurve,
                            cosineYCurve, sineXCurve, cosineXCurve, object4.anInt46
                                    - xCameraPos, object4.anInt45 - zCameraPos
                                    - object4.anInt52, object4.anInt47
                                    - yCameraPos, object4.uid);
            }
            if (currentTile.anInt1328 != 0) {
                WallDecoration class26 = currentTile.obj2;
                if (class26 != null
                        && !method322(l, i, j,
                        class26.aClass30_Sub2_Sub4_504.modelHeight))
                    if ((class26.anInt502 & currentTile.anInt1328) != 0)
                        class26.aClass30_Sub2_Sub4_504.renderAtPoint(
                                class26.anInt503, sineYCurve, cosineYCurve, sineXCurve,
                                cosineXCurve, class26.anInt500 - xCameraPos,
                                class26.anInt499 - zCameraPos, class26.anInt501
                                        - yCameraPos, class26.uid);
                    else if ((class26.anInt502 & 0x300) != 0) {
                        int l2 = class26.anInt500 - xCameraPos;
                        int j3 = class26.anInt499 - zCameraPos;
                        int i4 = class26.anInt501 - yCameraPos;
                        int k5 = class26.anInt503;
                        int j6;
                        if (k5 == 1 || k5 == 2)
                            j6 = -l2;
                        else
                            j6 = l2;
                        int l7;
                        if (k5 == 2 || k5 == 3)
                            l7 = -i4;
                        else
                            l7 = i4;
                        if ((class26.anInt502 & 0x100) != 0 && l7 >= j6) {
                            int i9 = l2 + anIntArray463[k5];
                            int i10 = i4 + anIntArray464[k5];
                            class26.aClass30_Sub2_Sub4_504.renderAtPoint(
                                    k5 * 512 + 256, sineYCurve, cosineYCurve,
                                    sineXCurve, cosineXCurve, i9, j3, i10,
                                    class26.uid);
                        }
                        if ((class26.anInt502 & 0x200) != 0 && l7 <= j6) {
                            int j9 = l2 + anIntArray465[k5];
                            int j10 = i4 + anIntArray466[k5];
                            class26.aClass30_Sub2_Sub4_504.renderAtPoint(
                                    k5 * 512 + 1280 & 0x7ff, sineYCurve,
                                    cosineYCurve, sineXCurve, cosineXCurve, j9, j3, j10,
                                    class26.uid);
                        }
                    }
                Wall class10_2 = currentTile.obj1;
                if (class10_2 != null) {
                	
                    if ((class10_2.orientation1 & currentTile.anInt1328) != 0
                            && !method321(l, i, j, class10_2.orientation1)) {

                        class10_2.aClass30_Sub2_Sub4_279.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve,
                                class10_2.centerX - xCameraPos,
                                class10_2.centerZ - zCameraPos,
                                class10_2.centerY - yCameraPos, class10_2.uid);
                    }
                    if ((class10_2.orientation & currentTile.anInt1328) != 0
                            && !method321(l, i, j, class10_2.orientation))
                        class10_2.renderable.renderAtPoint(0, sineYCurve,
                                cosineYCurve, sineXCurve, cosineXCurve,
                                class10_2.centerX - xCameraPos,
                                class10_2.centerZ - zCameraPos,
                                class10_2.centerY - yCameraPos, class10_2.uid);
                }
            }
            if (k < planes - 1) {
                MapTile class30_sub3_12 = groundArray[k + 1][i][j];
                if (class30_sub3_12 != null && class30_sub3_12.aBoolean1323)
                    aClass19_477.pushBack(class30_sub3_12);
            }
            if (i < xCameraTile) {
                MapTile class30_sub3_13 = planeTiles[i + 1][j];
                if (class30_sub3_13 != null && class30_sub3_13.aBoolean1323)
                    aClass19_477.pushBack(class30_sub3_13);
            }
            if (j < yCameraTile) {
                MapTile class30_sub3_14 = planeTiles[i][j + 1];
                if (class30_sub3_14 != null && class30_sub3_14.aBoolean1323)
                    aClass19_477.pushBack(class30_sub3_14);
            }
            if (i > xCameraTile) {
                MapTile class30_sub3_15 = planeTiles[i - 1][j];
                if (class30_sub3_15 != null && class30_sub3_15.aBoolean1323)
                    aClass19_477.pushBack(class30_sub3_15);
            }
            if (j > yCameraTile) {
                MapTile class30_sub3_16 = planeTiles[i][j - 1];
                if (class30_sub3_16 != null && class30_sub3_16.aBoolean1323)
                    aClass19_477.pushBack(class30_sub3_16);
            }
        } while (true);
    }

    private void method315(PlainTile class43, int i, int j, int k, int l, int i1,
                           int j1, int k1) {
        int l1;
        int i2 = l1 = (j1 << 7) - xCameraPos;
        int j2;
        int k2 = j2 = (k1 << 7) - yCameraPos;
        int l2;
        int i3 = l2 = i2 + 128;
        int j3;
        int k3 = j3 = k2 + 128;
        int l3 = anIntArrayArrayArray440[i][j1][k1] - zCameraPos;
        int i4 = anIntArrayArrayArray440[i][j1 + 1][k1] - zCameraPos;
        int j4 = anIntArrayArrayArray440[i][j1 + 1][k1 + 1] - zCameraPos;
        int k4 = anIntArrayArrayArray440[i][j1][k1 + 1] - zCameraPos;
        int l4 = k2 * l + i2 * i1 >> 16;
        k2 = k2 * i1 - i2 * l >> 16;
        i2 = l4;
        l4 = l3 * k - k2 * j >> 16;
        k2 = l3 * j + k2 * k >> 16;
        l3 = l4;
        if (k2 < 50)
            return;
        l4 = j2 * l + i3 * i1 >> 16;
        j2 = j2 * i1 - i3 * l >> 16;
        i3 = l4;
        l4 = i4 * k - j2 * j >> 16;
        j2 = i4 * j + j2 * k >> 16;
        i4 = l4;
        if (j2 < 50)
            return;
        l4 = k3 * l + l2 * i1 >> 16;
        k3 = k3 * i1 - l2 * l >> 16;
        l2 = l4;
        l4 = j4 * k - k3 * j >> 16;
        k3 = j4 * j + k3 * k >> 16;
        j4 = l4;
        if (k3 < 50)
            return;
        l4 = j3 * l + l1 * i1 >> 16;
        j3 = j3 * i1 - l1 * l >> 16;
        l1 = l4;
        l4 = k4 * k - j3 * j >> 16;
        j3 = k4 * j + j3 * k >> 16;
        k4 = l4;
        if (j3 < 50)
            return;
        int i5 = Rasterizer3D.textureInt1 + (i2 * SceneGraph.focalLength)
                / k2;
        int j5 = Rasterizer3D.textureInt2 + (l3 * SceneGraph.focalLength)
                / k2;
        int k5 = Rasterizer3D.textureInt1 + (i3 * SceneGraph.focalLength)
                / j2;
        int l5 = Rasterizer3D.textureInt2 + (i4 * SceneGraph.focalLength)
                / j2;
        int i6 = Rasterizer3D.textureInt1 + (l2 * SceneGraph.focalLength)
                / k3;
        int j6 = Rasterizer3D.textureInt2 + (j4 * SceneGraph.focalLength)
                / k3;
        int k6 = Rasterizer3D.textureInt1 + (l1 * SceneGraph.focalLength)
                / j3;
        int l6 = Rasterizer3D.textureInt2 + (k4 * SceneGraph.focalLength)
                / j3;

        Rasterizer3D.anInt1465 = 0;
        if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
            Rasterizer3D.aBoolean1462 = i6 < 0 || k6 < 0 || k5 < 0
                    || i6 > Raster.centerX2 || k6 > Raster.centerX2
                    || k5 > Raster.centerX2;
            if (aBoolean467
                    && method318(anInt468, anInt469, j6, l6, l5, i6, k6, k5)) {
                anInt470 = j1;
                anInt471 = k1;
            }
            if (class43.anInt720 == -1 || class43.anInt720 > 50) {
                if (class43.anInt718 != 0xbc614e) {
                    if (Client.enableHDTextures
                            && class43.anInt720 != -1) {
                        if (class43.aBoolean721) {
                            Rasterizer3D.drawMaterializedTriangle(j6, l6, l5, i6,
                                    k6, k5, class43.anInt718, class43.anInt719,
                                    class43.anInt717, i2, i3, l1, l3, i4, k4,
                                    k2, j2, j3, class43.anInt720, k3, j3, j2);
                        } else {
                            Rasterizer3D.drawMaterializedTriangle(j6, l6, l5, i6,
                                    k6, k5, class43.anInt718, class43.anInt719,
                                    class43.anInt717, l2, l1, i3, j4, k4, i4,
                                    k3, j3, j2, class43.anInt720, k3, j3, j2);
                        }
                    } else {
                        Rasterizer3D.drawGouraudTriangle(j6, l6, l5, i6, k6, k5,
                                class43.anInt718, class43.anInt719,
                                class43.anInt717, k3, j3, j2);
                    }
                }
            } else if (!lowMem) {
                if (class43.aBoolean721) {
                    Rasterizer3D.drawTexturedTriangle(j6, l6, l5, i6, k6, k5,
                            class43.anInt718, class43.anInt719,
                            class43.anInt717, i2, i3, l1, l3, i4, k4, k2, j2,
                            j3, class43.anInt720, k3, j3, j2);
                } else {
                    Rasterizer3D.drawTexturedTriangle(j6, l6, l5, i6, k6, k5,
                            class43.anInt718, class43.anInt719,
                            class43.anInt717, l2, l1, i3, j4, k4, i4, k3, j3,
                            j2, class43.anInt720, k3, j3, j2);
                }
            } else {
                int i7 = anIntArray485[class43.anInt720];
                Rasterizer3D.drawGouraudTriangle(j6, l6, l5, i6, k6, k5,
                        method317(i7, class43.anInt718),
                        method317(i7, class43.anInt719),
                        method317(i7, class43.anInt717), k3, j3, j2);
            }
        }
        if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
            Rasterizer3D.aBoolean1462 = i5 < 0 || k5 < 0 || k6 < 0
                    || i5 > Raster.centerX2 || k5 > Raster.centerX2
                    || k6 > Raster.centerX2;
            if (aBoolean467
                    && method318(anInt468, anInt469, j5, l5, l6, i5, k5, k6)) {
                anInt470 = j1;
                anInt471 = k1;
            }
            if (class43.anInt720 == -1 || class43.anInt720 > 50) {
                if (class43.anInt716 != 0xbc614e) {
                    if (Client.enableHDTextures
                            && class43.anInt720 != -1) {
                        Rasterizer3D.drawMaterializedTriangle(j5, l5, l6, i5, k5,
                                k6, class43.anInt716, class43.anInt717,
                                class43.anInt719, i2, i3, l1, l3, i4, k4, k2,
                                j2, j3, class43.anInt720, k2, j2, j3);
                    } else {
                        Rasterizer3D.drawGouraudTriangle(j5, l5, l6, i5, k5, k6,
                                class43.anInt716, class43.anInt717,
                                class43.anInt719, k2, j2, j3);
                    }
                }
            } else {
                if (!lowMem) {
                    Rasterizer3D.drawTexturedTriangle(j5, l5, l6, i5, k5, k6,
                            class43.anInt716, class43.anInt717,
                            class43.anInt719, i2, i3, l1, l3, i4, k4, k2, j2,
                            j3, class43.anInt720, k2, j2, j3);
                    return;
                }
                int j7 = anIntArray485[class43.anInt720];
                Rasterizer3D.drawGouraudTriangle(j5, l5, l6, i5, k5, k6,
                        method317(j7, class43.anInt716),
                        method317(j7, class43.anInt717),
                        method317(j7, class43.anInt719), k2, j2, j3);
            }
        }
    }


    private void method316(int i, int j, int k, ShapedTile shapedTile, int l,
                           int i1, int j1) {
        int k1 = shapedTile.origVertexX.length;
        for (int l1 = 0; l1 < k1; l1++) {
            int i2 = shapedTile.origVertexX[l1] - xCameraPos;
            int k2 = shapedTile.origVertexY[l1] - zCameraPos;
            int i3 = shapedTile.origVertexZ[l1] - yCameraPos;
            int k3 = i3 * k + i2 * j1 >> 16;
            i3 = i3 * j1 - i2 * k >> 16;
            i2 = k3;
            k3 = k2 * l - i3 * j >> 16;
            i3 = k2 * j + i3 * l >> 16;
            k2 = k3;
            if (i3 < 50)
                return;
            if (Client.enableHDTextures || shapedTile.triangleTexture != null) {
                ShapedTile.viewSpaceX[l1] = i2;
                ShapedTile.viewSpaceY[l1] = k2;
                ShapedTile.viewSpaceZ[l1] = i3;
            }
            ShapedTile.screenX[l1] = Rasterizer3D.textureInt1 + (i2 * SceneGraph.focalLength) / i3;
            ShapedTile.screenY[l1] = Rasterizer3D.textureInt2 + (k2 * SceneGraph.focalLength) / i3;
            ShapedTile.viewSpaceZ[l1] = i3;
        }

        Rasterizer3D.anInt1465 = 0;
        k1 = shapedTile.triangleA.length;
        for (int j2 = 0; j2 < k1; j2++) {
            int l2 = shapedTile.triangleA[j2];
            int j3 = shapedTile.triangleB[j2];
            int l3 = shapedTile.triangleC[j2];
            int i4 = ShapedTile.screenX[l2];
            int j4 = ShapedTile.screenX[j3];
            int k4 = ShapedTile.screenX[l3];
            int l4 = ShapedTile.screenY[l2];
            int i5 = ShapedTile.screenY[j3];
            int j5 = ShapedTile.screenY[l3];
            if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
                Rasterizer3D.aBoolean1462 = i4 < 0 || j4 < 0 || k4 < 0
                        || i4 > Raster.centerX2 || j4 > Raster.centerX2
                        || k4 > Raster.centerX2;
                if (aBoolean467
                        && method318(anInt468, anInt469, l4, i5, j5, i4, j4, k4)) {
                    anInt470 = i;
                    anInt471 = i1;
                }
                if (shapedTile.triangleTexture == null || shapedTile.triangleTexture[j2] == -1 || shapedTile.triangleTexture[j2] > Rasterizer3D.textureAmount) {
                    if (shapedTile.triangleHslA[j2] != 0xbc614e) {
                        if (Client.enableHDTextures && shapedTile.triangleTexture != null && shapedTile.triangleTexture[j2] != -1) {
                            if (shapedTile.flat || shapedTile.triangleTexture[j2] == 505) {
                                Rasterizer3D.drawMaterializedTriangle(l4, i5, j5,
                                        i4, j4, k4,
                                        shapedTile.triangleHslA[j2],
                                        shapedTile.triangleHslB[j2],
                                        shapedTile.triangleHslC[j2],
                                        ShapedTile.viewSpaceX[0],
                                        ShapedTile.viewSpaceX[1],
                                        ShapedTile.viewSpaceX[3],
                                        ShapedTile.viewSpaceY[0],
                                        ShapedTile.viewSpaceY[1],
                                        ShapedTile.viewSpaceY[3],
                                        ShapedTile.viewSpaceZ[0],
                                        ShapedTile.viewSpaceZ[1],
                                        ShapedTile.viewSpaceZ[3],
                                        shapedTile.triangleTexture[j2],
                                        ShapedTile.viewSpaceZ[l2],
                                        ShapedTile.viewSpaceZ[j3],
                                        ShapedTile.viewSpaceZ[l3]);
                            } else {
                                Rasterizer3D.drawMaterializedTriangle(l4, i5, j5,
                                        i4, j4, k4,
                                        shapedTile.triangleHslA[j2],
                                        shapedTile.triangleHslB[j2],
                                        shapedTile.triangleHslC[j2],
                                        ShapedTile.viewSpaceX[l2],
                                        ShapedTile.viewSpaceX[j3],
                                        ShapedTile.viewSpaceX[l3],
                                        ShapedTile.viewSpaceY[l2],
                                        ShapedTile.viewSpaceY[j3],
                                        ShapedTile.viewSpaceY[l3],
                                        ShapedTile.viewSpaceZ[l2],
                                        ShapedTile.viewSpaceZ[j3],
                                        ShapedTile.viewSpaceZ[l3],
                                        shapedTile.triangleTexture[j2],
                                        ShapedTile.viewSpaceZ[l2],
                                        ShapedTile.viewSpaceZ[j3],
                                        ShapedTile.viewSpaceZ[l3]);
                            }
                        } else {
                            Rasterizer3D.drawGouraudTriangle(l4, i5, j5, i4, j4,
                                    k4, shapedTile.triangleHslA[j2],
                                    shapedTile.triangleHslB[j2],
                                    shapedTile.triangleHslC[j2],
                                    ShapedTile.viewSpaceZ[l2],
                                    ShapedTile.viewSpaceZ[j3],
                                    ShapedTile.viewSpaceZ[l3]);
                        }
                    }
                } else if (!lowMem) {
                    if (shapedTile.flat) {
                        Rasterizer3D.drawTexturedTriangle(l4, i5, j5, i4, j4, k4,
                                shapedTile.triangleHslA[j2],
                                shapedTile.triangleHslB[j2],
                                shapedTile.triangleHslC[j2],
                                ShapedTile.viewSpaceX[0],
                                ShapedTile.viewSpaceX[1],
                                ShapedTile.viewSpaceX[3],
                                ShapedTile.viewSpaceY[0],
                                ShapedTile.viewSpaceY[1],
                                ShapedTile.viewSpaceY[3],
                                ShapedTile.viewSpaceZ[0],
                                ShapedTile.viewSpaceZ[1],
                                ShapedTile.viewSpaceZ[3],
                                shapedTile.triangleTexture[j2],
                                ShapedTile.viewSpaceZ[l2],
                                ShapedTile.viewSpaceZ[j3],
                                ShapedTile.viewSpaceZ[l3]);
                    } else {
                        Rasterizer3D.drawTexturedTriangle(l4, i5, j5, i4, j4, k4,
                                shapedTile.triangleHslA[j2],
                                shapedTile.triangleHslB[j2],
                                shapedTile.triangleHslC[j2],
                                ShapedTile.viewSpaceX[l2],
                                ShapedTile.viewSpaceX[j3],
                                ShapedTile.viewSpaceX[l3],
                                ShapedTile.viewSpaceY[l2],
                                ShapedTile.viewSpaceY[j3],
                                ShapedTile.viewSpaceY[l3],
                                ShapedTile.viewSpaceZ[l2],
                                ShapedTile.viewSpaceZ[j3],
                                ShapedTile.viewSpaceZ[l3],
                                shapedTile.triangleTexture[j2],
                                ShapedTile.viewSpaceZ[l2],
                                ShapedTile.viewSpaceZ[j3],
                                ShapedTile.viewSpaceZ[l3]);
                    }
                } else {
                    int k5 = anIntArray485[shapedTile.triangleTexture[j2]];
                    Rasterizer3D.drawGouraudTriangle(l4, i5, j5, i4, j4, k4,
                            method317(k5, shapedTile.triangleHslA[j2]),
                            method317(k5, shapedTile.triangleHslB[j2]),
                            method317(k5, shapedTile.triangleHslC[j2]),
                            ShapedTile.viewSpaceZ[l2],
                            ShapedTile.viewSpaceZ[j3],
                            ShapedTile.viewSpaceZ[l3]);
                }
            }
        }
    }

    private int method317(int j, int k) {
        k = 127 - k;
        k = (k * (j & 0x7f)) / 160;
        if (k < 2)
            k = 2;
        else if (k > 126)
            k = 126;
        return (j & 0xff80) + k;
    }

    private boolean method318(int i, int j, int k, int l, int i1, int j1,
                              int k1, int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        if (i < j1 && i < k1 && i < l1)
            return false;
        if (i > j1 && i > k1 && i > l1)
            return false;
        int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
        int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
        int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    private void buildCullingClusters() {
        int j = anIntArray473[currentPlane];
        CullingCluster aclass47[] = aClass47ArrayArray474[currentPlane];
        anInt475 = 0;
        for (int k = 0; k < j; k++) {
            CullingCluster class47 = aclass47[k];
            if (class47.anInt791 == 1) {
                int l = (class47.anInt787 - xCameraTile) + renderDistance;
                if (l < 0 || l > renderDistance * 2)
                    continue;
                int k1 = (class47.anInt789 - yCameraTile) + renderDistance;
                if (k1 < 0)
                    k1 = 0;
                int j2 = (class47.anInt790 - yCameraTile) + renderDistance;
                if (j2 > renderDistance * 2)
                    j2 = renderDistance * 2;
                boolean flag = false;
                while (k1 <= j2)
                    if (aBooleanArrayArray492[l][k1++]) {
                        flag = true;
                        break;
                    }
                if (!flag)
                    continue;
                int j3 = xCameraPos - class47.anInt792;
                if (j3 > 32) {
                    class47.anInt798 = 1;
                } else {
                    if (j3 >= -32)
                        continue;
                    class47.anInt798 = 2;
                    j3 = -j3;
                }
                class47.anInt801 = (class47.anInt794 - yCameraPos << 8) / j3;
                class47.anInt802 = (class47.anInt795 - yCameraPos << 8) / j3;
                class47.anInt803 = (class47.anInt796 - zCameraPos << 8) / j3;
                class47.anInt804 = (class47.anInt797 - zCameraPos << 8) / j3;
                aClass47Array476[anInt475++] = class47;
                continue;
            }
            if (class47.anInt791 == 2) {
                int i1 = (class47.anInt789 - yCameraTile) + renderDistance;
                if (i1 < 0 || i1 > renderDistance * 2)
                    continue;
                int l1 = (class47.anInt787 - xCameraTile) + renderDistance;
                if (l1 < 0)
                    l1 = 0;
                int k2 = (class47.anInt788 - xCameraTile) + renderDistance;
                if (k2 > renderDistance * 2)
                    k2 = renderDistance * 2;
                boolean flag1 = false;
                while (l1 <= k2)
                    if (aBooleanArrayArray492[l1++][i1]) {
                        flag1 = true;
                        break;
                    }
                if (!flag1)
                    continue;
                int k3 = yCameraPos - class47.anInt794;
                if (k3 > 32) {
                    class47.anInt798 = 3;
                } else {
                    if (k3 >= -32)
                        continue;
                    class47.anInt798 = 4;
                    k3 = -k3;
                }
                class47.anInt799 = (class47.anInt792 - xCameraPos << 8) / k3;
                class47.anInt800 = (class47.anInt793 - xCameraPos << 8) / k3;
                class47.anInt803 = (class47.anInt796 - zCameraPos << 8) / k3;
                class47.anInt804 = (class47.anInt797 - zCameraPos << 8) / k3;
                aClass47Array476[anInt475++] = class47;
            } else if (class47.anInt791 == 4) {
                int j1 = class47.anInt796 - zCameraPos;
                if (j1 > 128) {
                    int i2 = (class47.anInt789 - yCameraTile) + renderDistance;
                    if (i2 < 0)
                        i2 = 0;
                    int l2 = (class47.anInt790 - yCameraTile) + renderDistance;
                    if (l2 > renderDistance * 2)
                        l2 = renderDistance * 2;
                    if (i2 <= l2) {
                        int i3 = (class47.anInt787 - xCameraTile) + renderDistance;
                        if (i3 < 0)
                            i3 = 0;
                        int l3 = (class47.anInt788 - xCameraTile) + renderDistance;
                        if (l3 > renderDistance * 2)
                            l3 = renderDistance * 2;
                        boolean flag2 = false;
                        label0:
                        for (int i4 = i3; i4 <= l3; i4++) {
                            for (int j4 = i2; j4 <= l2; j4++) {
                                if (!aBooleanArrayArray492[i4][j4])
                                    continue;
                                flag2 = true;
                                break label0;
                            }

                        }

                        if (flag2) {
                            class47.anInt798 = 5;
                            class47.anInt799 = (class47.anInt792 - xCameraPos << 8)
                                    / j1;
                            class47.anInt800 = (class47.anInt793 - xCameraPos << 8)
                                    / j1;
                            class47.anInt801 = (class47.anInt794 - yCameraPos << 8)
                                    / j1;
                            class47.anInt802 = (class47.anInt795 - yCameraPos << 8)
                                    / j1;
                            aClass47Array476[anInt475++] = class47;
                        }
                    }
                }
            }
        }

    }

    private boolean method320(int i, int j, int k) {
        int l = anIntArrayArrayArray445[i][j][k];
        if (l == -renderCycle)
            return false;
        if (l == renderCycle)
            return true;
        int i1 = j << 7;
        int j1 = k << 7;
        if (method324(i1 + 1, anIntArrayArrayArray440[i][j][k], j1 + 1)
                && method324((i1 + 128) - 1,
                anIntArrayArrayArray440[i][j + 1][k], j1 + 1)
                && method324((i1 + 128) - 1,
                anIntArrayArrayArray440[i][j + 1][k + 1],
                (j1 + 128) - 1)
                && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1],
                (j1 + 128) - 1)) {
            anIntArrayArrayArray445[i][j][k] = renderCycle;
            return true;
        } else {
            anIntArrayArrayArray445[i][j][k] = -renderCycle;
            return false;
        }
    }

    private boolean method321(int i, int j, int k, int l) {
        if (!method320(i, j, k))
            return false;
        
        int i1 = j << 7;
        int j1 = k << 7;
        int k1 = anIntArrayArrayArray440[i][j][k] - 1;
        int l1 = k1 - 120;
        int i2 = k1 - 230;
        int j2 = k1 - 238;
        if (l < 16) {
            if (l == 1) {
                if (i1 > xCameraPos) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
            }
            if (l == 2) {
                if (j1 < yCameraPos) {
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1 + 128)
                        && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 4) {
                if (i1 < xCameraPos) {
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1 + 128, i2, j1)
                        && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 8) {
                if (j1 > yCameraPos) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
            }
        }
        if (!method324(i1 + 64, j2, j1 + 64))
            return false;
        if (l == 16)
            return method324(i1, i2, j1 + 128);
        if (l == 32)
            return method324(i1 + 128, i2, j1 + 128);
        if (l == 64)
            return method324(i1 + 128, i2, j1);
        if (l == 128) {
            return method324(i1, i2, j1);
        } else {
            System.out.println("Warning unsupported wall type");
            return true;
        }
    }

    private boolean method322(int i, int j, int k, int l) {
        if (!method320(i, j, k))
            return false;
        
        int i1 = j << 7;
        int j1 = k << 7;
        return method324(i1 + 1, anIntArrayArrayArray440[i][j][k] - l, j1 + 1)
                && method324((i1 + 128) - 1,
                anIntArrayArrayArray440[i][j + 1][k] - l, j1 + 1)
                && method324((i1 + 128) - 1,
                anIntArrayArrayArray440[i][j + 1][k + 1] - l,
                (j1 + 128) - 1)
                && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1] - l,
                (j1 + 128) - 1);
    }

    private boolean method323(int i, int j, int k, int l, int i1, int j1) {
        if (j == k && l == i1) {
            if (!method320(i, j, l))
                return false;
            
            int k1 = j << 7;
            int i2 = l << 7;
            return method324(k1 + 1, anIntArrayArrayArray440[i][j][l] - j1,
                    i2 + 1)
                    && method324((k1 + 128) - 1,
                    anIntArrayArrayArray440[i][j + 1][l] - j1, i2 + 1)
                    && method324((k1 + 128) - 1,
                    anIntArrayArrayArray440[i][j + 1][l + 1] - j1,
                    (i2 + 128) - 1)
                    && method324(k1 + 1, anIntArrayArrayArray440[i][j][l + 1]
                    - j1, (i2 + 128) - 1);
        }
        for (int l1 = j; l1 <= k; l1++) {
            for (int j2 = l; j2 <= i1; j2++)
                if (anIntArrayArrayArray445[i][l1][j2] == -renderCycle)
                    return false;

        }

        int k2 = (j << 7) + 1;
        int l2 = (l << 7) + 2;
        int i3 = anIntArrayArrayArray440[i][j][l] - j1;
        if (!method324(k2, i3, l2))
            return false;
        int j3 = (k << 7) - 1;
        if (!method324(j3, i3, l2))
            return false;
        int k3 = (i1 << 7) - 1;
        return method324(k2, i3, k3) && method324(j3, i3, k3);
    }

    private boolean method324(int i, int j, int k) {
        for (int l = 0; l < anInt475; l++) {
            CullingCluster class47 = aClass47Array476[l];
            if (class47.anInt798 == 1) {
                int i1 = class47.anInt792 - i;
                if (i1 > 0) {
                    int j2 = class47.anInt794 + (class47.anInt801 * i1 >> 8);
                    int k3 = class47.anInt795 + (class47.anInt802 * i1 >> 8);
                    int l4 = class47.anInt796 + (class47.anInt803 * i1 >> 8);
                    int i6 = class47.anInt797 + (class47.anInt804 * i1 >> 8);
                    if (k >= j2 && k <= k3 && j >= l4 && j <= i6)
                        return true;
                }
            } else if (class47.anInt798 == 2) {
                int j1 = i - class47.anInt792;
                if (j1 > 0) {
                    int k2 = class47.anInt794 + (class47.anInt801 * j1 >> 8);
                    int l3 = class47.anInt795 + (class47.anInt802 * j1 >> 8);
                    int i5 = class47.anInt796 + (class47.anInt803 * j1 >> 8);
                    int j6 = class47.anInt797 + (class47.anInt804 * j1 >> 8);
                    if (k >= k2 && k <= l3 && j >= i5 && j <= j6)
                        return true;
                }
            } else if (class47.anInt798 == 3) {
                int k1 = class47.anInt794 - k;
                if (k1 > 0) {
                    int l2 = class47.anInt792 + (class47.anInt799 * k1 >> 8);
                    int i4 = class47.anInt793 + (class47.anInt800 * k1 >> 8);
                    int j5 = class47.anInt796 + (class47.anInt803 * k1 >> 8);
                    int k6 = class47.anInt797 + (class47.anInt804 * k1 >> 8);
                    if (i >= l2 && i <= i4 && j >= j5 && j <= k6)
                        return true;
                }
            } else if (class47.anInt798 == 4) {
                int l1 = k - class47.anInt794;
                if (l1 > 0) {
                    int i3 = class47.anInt792 + (class47.anInt799 * l1 >> 8);
                    int j4 = class47.anInt793 + (class47.anInt800 * l1 >> 8);
                    int k5 = class47.anInt796 + (class47.anInt803 * l1 >> 8);
                    int l6 = class47.anInt797 + (class47.anInt804 * l1 >> 8);
                    if (i >= i3 && i <= j4 && j >= k5 && j <= l6)
                        return true;
                }
            } else if (class47.anInt798 == 5) {
                int i2 = j - class47.anInt796;
                if (i2 > 0) {
                    int j3 = class47.anInt792 + (class47.anInt799 * i2 >> 8);
                    int k4 = class47.anInt793 + (class47.anInt800 * i2 >> 8);
                    int l5 = class47.anInt794 + (class47.anInt801 * i2 >> 8);
                    int i7 = class47.anInt795 + (class47.anInt802 * i2 >> 8);
                    if (i >= j3 && i <= k4 && k >= l5 && k <= i7)
                        return true;
                }
            }
        }

        return false;
    }
    
    public void renderFloatingSprite(int currentHeight) {

    	for(int x = 0;x<regionSizeX;x++) {
    		for(int y = 0;y<regionSizeY;y++) {
    			if(y < this.minTileY || y > this.maxTileY || x < this.minTileX || x > this.maxTileX)
    				continue;
    			MapTile tile = groundArray[currentHeight][x][y];
    			if(tile != null && tile.floatingSprite != null) {
    				FloatingSprite sprite = tile.floatingSprite;
    				Client.getInstance().calcScreenPos(sprite.x, sprite.y, sprite.spriteHeight);
    				sprite.sprite.drawCenteredSpriteOpaque(Client.instance.spriteDrawX, Client.instance.spriteDrawY, (int)( 255 * sprite.spriteOpacity));

    			}
    		}
    	}
    }


    public void renderFloatingText(int currentHeight) {
    	
    	for(int x = 0;x<regionSizeX;x++) {
    		for(int y = 0;y<regionSizeY;y++) {
    			if(y < this.minTileY || y > this.maxTileY || x < this.minTileX || x > this.maxTileX)
    				continue;
    			MapTile tile = groundArray[currentHeight][x][y];
    			if(tile != null && tile.floatingText != null) {
    				FloatingText text = tile.floatingText;
    				Client.getInstance().calcScreenPos(text.x, text.y, text.textHeight);
    				text.font.drawCenteredString(text.overheadText, Client.instance.spriteDrawX, Client.instance.spriteDrawY, text.textColor, text.textShadow, (int)( 255 * text.textOpacity));

    			}
    		}
    	}
    }

    private boolean aBoolean434;
    public static boolean lowMem = true;
    private final int planes;
    private final int regionSizeX;
    private final int regionSizeY;
    private final int[][][] anIntArrayArrayArray440;
    private final MapTile[][][] groundArray;
    public int anInt442;
    private int obj5CacheCurrPos;
    private final StaticObject[] obj5Cache;
    private final int[][][] anIntArrayArrayArray445;
    private static int anInt446;
    private static int currentPlane;
    private static int renderCycle;
    private static int minTileX;
    private static int maxTileX;
    private static int minTileY;
    private static int maxTileY;
    private static int xCameraTile;
    private static int yCameraTile;
    private static int xCameraPos;
    private static int zCameraPos;
    private static int yCameraPos;
    private static int sineYCurve;
    private static int cosineYCurve;
    private static int sineXCurve;
    private static int cosineXCurve;
    private static StaticObject[] aClass28Array462 = new StaticObject[100];
    private static final int[] anIntArray463 = {53, -53, -53, 53};
    private static final int[] anIntArray464 = {-53, -53, 53, 53};
    private static final int[] anIntArray465 = {-45, 45, 45, -45};
    private static final int[] anIntArray466 = {45, 45, -45, -45};
    private static boolean aBoolean467;
    private static int anInt468;
    private static int anInt469;
    public static int anInt470 = -1;
    public static int anInt471 = -1;
    private static final int anInt472;
    private static int[] anIntArray473;
    private static CullingCluster[][] aClass47ArrayArray474;
    private static int anInt475;
    private static final CullingCluster[] aClass47Array476 = new CullingCluster[500];
    private static DoubleEndedQueue aClass19_477 = new DoubleEndedQueue();
    private static final int[] anIntArray478 = {19, 55, 38, 155, 255, 110,
            137, 205, 76};
    private static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80,
            48, 160};
    private static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
    private static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
    private static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
    private static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
    private static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
    private static final int[] anIntArray485 = {41, 39248, 41, 4643, 41, 41,
            41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41,
            41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079,
            41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
    private final int[] anIntArray486;
    private final int[] anIntArray487;
    private int anInt488;
    private final int[][] anIntArrayArray489 = {new int[16],
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
    private final int[][] anIntArrayArray490 = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3},
            {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
    private static boolean[][][][] aBooleanArrayArrayArrayArray491 = new boolean[8][32][(renderDistance * 2) + 1][(renderDistance * 2) + 1];
    private static boolean[][] aBooleanArrayArray492;
    private static int anInt493;
    private static int anInt494;
    private static int anInt495;
    private static int anInt496;
    private static int anInt497;
    private static int anInt498;
    public static int focalLength = 512;
	public static int viewDistance = 9;

    static {
        focalLength = 512;
        anInt472 = 4;
        anIntArray473 = new int[anInt472];
        aClass47ArrayArray474 = new CullingCluster[anInt472][500];
    }
}
