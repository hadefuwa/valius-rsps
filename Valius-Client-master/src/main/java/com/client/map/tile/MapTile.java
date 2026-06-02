package com.client.map.tile;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.collection.Linkable;
import com.client.map.objects.FloatingSprite;
import com.client.map.objects.FloatingText;
import com.client.map.objects.GroundDecoration;
import com.client.map.objects.GroundItem;
import com.client.map.objects.StaticObject;
import com.client.map.objects.Wall;
import com.client.map.objects.WallDecoration;

public final class MapTile extends Linkable {

	public MapTile(int i, int j, int k) {
		obj5Array = new StaticObject[5];
		anIntArray1319 = new int[5];
		anInt1310 = anInt1307 = i;
		anInt1308 = j;
		anInt1309 = k;
	}

	public int anInt1307;
	public final int anInt1308;
	public final int anInt1309;
	public final int anInt1310;
	public PlainTile plainTile;
	public ShapedTile aClass40_1312;
	public Wall obj1;
	public WallDecoration obj2;
	public GroundDecoration obj3;
	public GroundItem obj4;
	public int anInt1317;
	public final StaticObject[] obj5Array;
	public final int[] anIntArray1319;
	public int anInt1320;
	public int anInt1321;
	public boolean aBoolean1322;
	public boolean aBoolean1323;
	public boolean aBoolean1324;
	public int anInt1325;
	public int anInt1326;
	public int anInt1327;
	public int anInt1328;
	public MapTile tileBelow;
	public FloatingText floatingText;
	public FloatingSprite floatingSprite;
}
