package com.client.cache.provider;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.collection.Cacheable;

public final class Resource extends Cacheable {

	public Resource() {
		incomplete = true;
	}

	public int dataType;
	public byte buffer[];
	public int ID;
	public boolean incomplete;
	public int loopCycle;
	public int dataID;
}
