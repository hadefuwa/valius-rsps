package com.client.audio;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.client.io.Buffer;

public final class SoundFilter {

	private float adaptMagnitude(int direction, int j, float f) {
		float f1 = pairMagnitude[direction][0][j]
				+ f
				* (pairMagnitude[direction][1][j] - pairMagnitude[direction][0][j]);
		f1 *= 0.001525879F;
		return 1.0F - (float) Math.pow(10D, -f1 / 20F);
	}

	private float normalize(float f) {
		float f1 = 32.7032F * (float) Math.pow(2D, f);
		return (f1 * 3.141593F) / 11025F;
	}

	private float adaptPhase(float f, int i, int j) {
		float f1 = pairPhase[j][0][i]
				+ f
				* (pairPhase[j][1][i] - pairPhase[j][0][i]);
		f1 *= 0.0001220703F;
		return normalize(f1);
	}

	public int compute(int direction, float f) {
		if (direction == 0) {
			float f1 = unity[0] + (unity[1] - unity[0])
					* f;
			f1 *= 0.003051758F;
			_invUnity = (float) Math.pow(0.10000000000000001D, f1 / 20F);
			invUnity = (int) (_invUnity * 65536F);
		}
		if (pairCount[direction] == 0)
			return 0;
		float f2 = adaptMagnitude(direction, 0, f);
		_coefficient[direction][0] = -2F * f2
				* (float) Math.cos(adaptPhase(f, 0, direction));
		_coefficient[direction][1] = f2 * f2;
		for (int k = 1; k < pairCount[direction]; k++) {
			float f3 = adaptMagnitude(direction, k, f);
			float f4 = -2F * f3 * (float) Math.cos(adaptPhase(f, k, direction));
			float f5 = f3 * f3;
			_coefficient[direction][k * 2 + 1] = _coefficient[direction][k * 2 - 1]
					* f5;
			_coefficient[direction][k * 2] = _coefficient[direction][k * 2 - 1]
					* f4 + _coefficient[direction][k * 2 - 2] * f5;
			for (int j1 = k * 2 - 1; j1 >= 2; j1--)
				_coefficient[direction][j1] += _coefficient[direction][j1 - 1]
						* f4 + _coefficient[direction][j1 - 2] * f5;

			_coefficient[direction][1] += _coefficient[direction][0] * f4 + f5;
			_coefficient[direction][0] += f4;
		}

		if (direction == 0) {
			for (int l = 0; l < pairCount[0] * 2; l++)
				_coefficient[0][l] *= _invUnity;

		}
		for (int i1 = 0; i1 < pairCount[direction] * 2; i1++)
			coefficient[direction][i1] = (int) (_coefficient[direction][i1] * 65536F);

		return pairCount[direction] * 2;
	}

	public void decode(Buffer stream, Envelope class29) {
		int i = stream.readUnsignedByte();
		pairCount[0] = i >> 4;
		pairCount[1] = i & 0xf;
		if (i != 0) {
			unity[0] = stream.readShort();
			unity[1] = stream.readShort();
			int j = stream.readUnsignedByte();
			for (int k = 0; k < 2; k++) {
				for (int l = 0; l < pairCount[k]; l++) {
					pairPhase[k][0][l] = stream
							.readShort();
					pairMagnitude[k][0][l] = stream
							.readShort();
				}

			}

			for (int i1 = 0; i1 < 2; i1++) {
				for (int j1 = 0; j1 < pairCount[i1]; j1++)
					if ((j & 1 << i1 * 4 << j1) != 0) {
						pairPhase[i1][1][j1] = stream
								.readShort();
						pairMagnitude[i1][1][j1] = stream
								.readShort();
					} else {
						pairPhase[i1][1][j1] = pairPhase[i1][0][j1];
						pairMagnitude[i1][1][j1] = pairMagnitude[i1][0][j1];
					}

			}

			if (j != 0 || unity[1] != unity[0])
				class29.decodeShape(stream);
		} else {
			unity[0] = unity[1] = 0;
		}
	}

	public SoundFilter() {
		pairCount = new int[2];
		pairPhase = new int[2][2][4];
		pairMagnitude = new int[2][2][4];
		unity = new int[2];
	}

	final int[] pairCount;
	private final int[][][] pairPhase;
	private final int[][][] pairMagnitude;
	private final int[] unity;
	private static final float[][] _coefficient = new float[2][8];
	static final int[][] coefficient = new int[2][8];
	private static float _invUnity;
	static int invUnity;

}
