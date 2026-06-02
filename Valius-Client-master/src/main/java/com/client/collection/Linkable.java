package com.client.collection;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Linkable {

	public final void unlink() {
		if (previous == null) {
		} else {
			previous.next = next;
			next.previous = previous;
			next = null;
			previous = null;
		}
	}

	public Linkable() {
	}

	public long id;
	public Linkable next;
	public Linkable previous;
}
