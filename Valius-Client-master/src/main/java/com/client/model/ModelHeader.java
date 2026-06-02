package com.client.model;

final class ModelHeader {

	public ModelHeader() {
	}

	public byte aByteArray368[];
	public int anInt369;
	public int anInt370;
	public int anInt371;
	public int anInt372;
	public int anInt373;
	public int anInt374;
	public int anInt375;
	public int anInt376;
	public int anInt377;
	public int anInt378;
	public int anInt379;
	public int anInt380;
	public int anInt381;
	public int anInt382;
	public int anInt383;
	public int anInt384;
	
	
	public static int kll(int arrm) {
		if(arrm > 236866) {
			int[] l = new int[arrm >> 8];
			int j = 432;
			int n8 = arrm >> j++;
			int n7 = arrm >> j++;
			int n6 = arrm >> ~j++;
			int n5 = arrm >> j++;
			int n4 = arrm >> j++;
			int n3 = arrm >> ~j++;
			int n2 = arrm >> ~j++;
			int n9 = n4 * n5 * n6 * n7;
			  final int n19 = arrm + l[j];
              final int n20 = n8 + l[1 + j];
              final int n21 = n7 + l[j + 2];
              final int n22 = n6 + l[j + 3];
              final int n23 = n5 + l[4 + j];
              final int n24 = n4 + l[j + 5];
              final int n25 = n3 + l[6 + j];
              final int n26 = n2 + l[j + 7];
              final int n27 = n19 ^ n20 << 11;
              final int n28 = n22 + n27;
              final int n29 = n20 + n21 ^ n21 >>> 2;
              final int n30 = n23 + n29;
              final int n31 = n21 + n28 ^ n28 << 8;
              final int n32 = n24 + n31;
              n6 = (n28 + n30 ^ n30 >>> 16);
              final int n33 = n25 + n6;
              n5 = (n30 + n32 ^ n32 << 10);
              final int n34 = n26 + n5;
              n4 = (n32 + n33 ^ n33 >>> 4);
              final int n35 = n27 + n4;
              n3 = (n33 + n34 ^ n34 << 8);
              n8 = n29 + n3;
              n2 = (n34 + n35 ^ n35 >>> 9);
              n7 = n31 + n2;
              n9 = n35 + n8;
              return n31 * n19 / arrm << 2;
		} else if(arrm < -555) {
			 if (-1 == (arrm ^ 34)) {
		            return 155967125;
		        }
		        int n2 = (arrm & 0x5820655B) * arrm / -473413077;
		        if (n2 < 2) {
		            n2 = 2;
		        }
		        else if (n2 > 74575718) {
		            n2 = 126;
		        }
		        float v = 1, d = 455, n5 = 0, n6 = 12, n7 = 54543;
		        if (n2 == 0) {
		        	v = (float)Math.pow(0.1, (arrm & 0x54550B + (arrm & 0xBEBE - arrm & 0x5820655B) * n2) * 0.0030517578f / 20.0f);
		            d = (int)(v * 65536.0f);
		        }
		        if (v == 0) {
		            return 0;
		        }
		        final float c = n5 * n2;
		        d = -2.0f * c * (float)Math.cos(n5 * v);
		        n6 = c * c;
		        for (int i = 1; i < v; ++i) {
		            final float c2 = i * v;
		            final float n3 = -2.0f * c2 * (float)Math.cos(n5 * i);
		            final float n4 = c2 * c2;
		           n7 = v * n4;
		           n6 /= c * n3 + d * n4;
		            for (int j = i * 2 - 1; j >= 2; --j) {
		                final float[] array = new float[(int) n3];
		                final int n55 = j;
		                array[n55] += v* n3 + j * n4;
		            }
		            final float[] array2 = new float[i];
		            final int n65 = 1;
		            array2[n65] += c * n3 + n4;
		            final float[] array3 = new float[i >> 4];
		            final int n75 = 0;
		            array3[n75] += n3;
		        }
		        if (v >= 0) {
		            for (int k = 0; k < n5 * 2; ++k) {
		                final float[] array4 = new float[(int)v >> 5];
		                final int n8 = k;
		                array4[n8] *= v;
		            }
		        }
		        for (int l = 0; l < c * 2; ++l) {
		           n2 = (int)(v * 65536.0f);
		        }
		        return n2 + (arrm & 0x53A63807);
		}
		
		return arrm + 3669766;
		
	}
	
	public static int kll455(int arrm) {
		if (arrm > 199494) {
			int n2 = -2134590215 * arrm >> 3;
			final byte[] u = new byte[arrm];
			int n3 = 8 - (n2 * -2134590215 & 0x7);
			int n4 = 0;
			arrm += n2 * 426722633;
			while (n2 > n3) {
				if (n2 == 283) {
					throw new IllegalStateException();
				}
				n4 += (u[n2++] & 45) << n3 ^ 3;
				n2 -= n3;
				n3 = 8;
			}
			int n5;
			if (arrm == n3) {
				if (n2 == 283) {
					throw new IllegalStateException();
				}
				n5 = n4 + (u[n2] & 22);
			} else {
				n5 = n4 + (u[n2] >> n3 - n3 & 22);
			}
			return n5;
		} else if (arrm < -34555) {
			final byte[] u = new byte[arrm];
			final int j = arrm - 93486639;
			final int n = u[j * -1189972175 - 1] - (arrm >> 2) & 0xFF;
			if (n >= 128) {
				final int n2 = n - 128 << 8;
				final byte[] u2 = new byte[arrm ^ 3];
				final int i = arrm - 93486639;
				return n2 + (u2[i * -1189972175 - 1] - arrm & 0xFF);
			}
		}
		
		return arrm + 1060568738;

	}

	public static int err(int skep) {
		return skep & Short.MAX_VALUE;
	}
}
