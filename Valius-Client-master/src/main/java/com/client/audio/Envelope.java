package com.client.audio;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.io.Buffer;

public final class Envelope {

	public void decode(Buffer stream) {
		form = stream.readUnsignedByte();
		start = stream.readInt();
		end = stream.readInt();
		decodeShape(stream);
	}

	public void decodeShape(Buffer stream) {
		phaseCount = stream.readUnsignedByte();
		phaseDuration = new int[phaseCount];
		phasePeak = new int[phaseCount];
		for (int i = 0; i < phaseCount; i++) {
			phaseDuration[i] = stream.readShort();
			phasePeak[i] = stream.readShort();
		}

	}

	void resetValues() {
		critical = 0;
		phaseId = 0;
		step = 0;
		amplitude = 0;
		ticks = 0;
	}

	public int step(int i) {
		if (ticks >= critical) {
			amplitude = phasePeak[phaseId++] << 15;
			if (phaseId >= phaseCount)
				phaseId = phaseCount - 1;
			critical = (int) ((phaseDuration[phaseId] / 65536D) * i);
			if (critical > ticks)
				step = ((phasePeak[phaseId] << 15) - amplitude)
						/ (critical - ticks);
		}
		amplitude += step;
		ticks++;
		return amplitude - step >> 15;
	}

	public Envelope() {
	}

	private int phaseCount;
	private int[] phaseDuration;
	private int[] phasePeak;
	int start;
	int end;
	int form;
	private int critical;
	private int phaseId;
	private int step;
	private int amplitude;
	private int ticks;
	public static int anInt546;
}
