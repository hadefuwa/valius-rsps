package com.client.collection;

import com.client.sign.Signlink;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class LinkableHashMap {

	public LinkableHashMap() {
		int i = 1024;// was parameter
		bucketCount = i;
		buckets = new Linkable[i];
		for (int k = 0; k < i; k++) {
			Linkable node = buckets[k] = new Linkable();
			node.next = node;
			node.previous = node;
		}

	}

	public Linkable get(long l) {
		Linkable node = buckets[(int) (l & bucketCount - 1)];
		for (Linkable node_1 = node.next; node_1 != node; node_1 = node_1.next)
			if (node_1.id == l)
				return node_1;

		return null;
	}

	public void put(Linkable node, long key) {
		try {
			if (node.previous != null)
				node.unlink();
			Linkable node_1 = buckets[(int) (key & bucketCount - 1)];
			node.previous = node_1.previous;
			node.next = node_1;
			node.previous.next = node;
			node.next.previous = node;
			node.id = key;
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("91499, " + node + ", " + key + ", " + (byte) 7
					+ ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private final int bucketCount;
	private final Linkable[] buckets;
}
