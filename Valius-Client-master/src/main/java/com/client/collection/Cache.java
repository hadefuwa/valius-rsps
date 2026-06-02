package com.client.collection;

import com.client.sign.Signlink;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class Cache {

	public Cache(int i) {
		emptyNodeSub = new Cacheable();
		nodeSubList = new CacheableQueue();
		initialCount = i;
		spaceLeft = i;
		nodeCache = new LinkableHashMap();
	}

	public Cacheable insertFromCache(long l) {
		Cacheable nodeSub = (Cacheable) nodeCache.get(l);
		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}
		return nodeSub;
	}

	public void removeFromCache(Cacheable nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				Cacheable nodeSub_1 = nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();
				if (nodeSub_1 == emptyNodeSub) {
					Cacheable nodeSub_2 = nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				spaceLeft--;
			}
			nodeCache.put(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47547, " + nodeSub + ", " + l + ", "
					+ (byte) 2 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void unlinkAll() {
		do {
			Cacheable nodeSub = nodeSubList.popTail();
			if (nodeSub != null) {
				nodeSub.unlink();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

	private final Cacheable emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final LinkableHashMap nodeCache;
	private final CacheableQueue nodeSubList;
}
