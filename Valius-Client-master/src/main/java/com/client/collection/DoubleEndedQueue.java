package com.client.collection;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class DoubleEndedQueue {

	public DoubleEndedQueue() {
		tail = new Linkable();
		tail.next = tail;
		tail.previous = tail;
	}

	public void pushBack(Linkable node) {
		if (node.previous != null)
			node.unlink();
		node.previous = tail.previous;
		node.next = tail;
		node.previous.next = node;
		node.next.previous = node;
	}

	public void pushFront(Linkable node) {
		if (node.previous != null)
			node.unlink();
		node.previous = tail;
		node.next = tail.next;
		node.previous.next = node;
		node.next.previous = node;
	}
	
	public Linkable popFront() {
		Linkable node = tail.next;
		if (node == tail) {
			return null;
		} else {
			node.unlink();
			return node;
		}
	}

	public Linkable peekFront() {
		Linkable node = tail.next;
		if (node == tail) {
			current = null;
			return null;
		} else {
			current = node.next;
			return node;
		}
	}

	public Linkable getPrevious() {
		Linkable node = tail.previous;
		if (node == tail) {
			current = null;
			return null;
		} else {
			current = node.previous;
			return node;
		}
	}

	public Linkable reverseGetNext() {
		Linkable node = current;
		if (node == tail) {
			current = null;
			return null;
		} else {
			current = node.next;
			return node;
		}
	}

	public Linkable getNext() {
		Linkable node = current;
		if (node == tail) {
			current = null;
			return null;
		}
		current = node.previous;
		return node;
	}

	public void clear() {
		if (tail.next == tail)
			return;
		do {
			Linkable node = tail.next;
			if (node == tail)
				return;
			node.unlink();
		} while (true);
	}

	public final Linkable tail;
	private Linkable current;
}
