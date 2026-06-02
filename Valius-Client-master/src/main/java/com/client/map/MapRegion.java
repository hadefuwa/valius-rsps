package com.client.map;

import com.client.Client;
import com.client.cache.definitions.FloorDefinition;
import com.client.cache.definitions.ObjectDefinition;
import com.client.cache.provider.ResourceProvider;
import com.client.config.Configuration;
import com.client.draw.raster.Rasterizer3D;
import com.client.io.Buffer;
import com.client.map.clipping.CollisionMap;
import com.client.misc.ObjectKey;
import com.client.model.Model;
import com.client.model.impl.AnimatedGameObject;
import com.client.model.impl.Renderable;
import com.client.utilities.TiledUtils;

public class MapRegion {

	public MapRegion(byte flags[][][], int heights[][][], int regionSizeX, int regionSizeY) {
		maximumPlane = 99;
		this.regionSizeX = regionSizeX;
		this.regionSizeY = regionSizeY;
		tileHeights = heights;
		tileFlags = flags;
		underlays = new byte[4][regionSizeX][regionSizeY];
		overlays = new byte[4][regionSizeX][regionSizeY];
		overlayTypes = new byte[4][regionSizeX][regionSizeY];
		overlayOrientations = new byte[4][regionSizeX][regionSizeY];
		anIntArrayArrayArray135 = new int[4][regionSizeX + 1][regionSizeY + 1];
		shading = new byte[4][regionSizeX + 1][regionSizeY + 1];
		tileLighting = new int[regionSizeX + 1][regionSizeY + 1];
		hues = new int[regionSizeY];
		saturations = new int[regionSizeY];
		luminances = new int[regionSizeY];
		chromas = new int[regionSizeY];
		anIntArray128 = new int[regionSizeY];
	}

	/**
	 * Encodes the hue, saturation, and luminance into a colour value.
	 *
	 * @param hue
	 *            The hue.
	 * @param saturation
	 *            The saturation.
	 * @param luminance
	 *            The luminance.
	 * @return The colour.
	 */
	private int encode(int hue, int saturation, int luminance) {
		if (luminance > 179)
			saturation /= 2;
		if (luminance > 192)
			saturation /= 2;
		if (luminance > 217)
			saturation /= 2;
		if (luminance > 243)
			saturation /= 2;
		return (hue / 4 << 10) + (saturation / 32 << 7) + luminance / 2;
	}

	/**
	 * Returns the plane that actually contains the collision flag, to adjust for
	 * objects such as bridges. TODO better name
	 *
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 * @param z
	 *            The z coordinate.
	 * @return The correct z coordinate.
	 */
	private int getCollisionPlane(int y, int z, int x) {
		if ((tileFlags[z][x][y] & FORCE_LOWEST_PLANE) != 0) {
			return 0;
		}
		if (z > 0 && (tileFlags[1][x][y] & BRIDGE_TILE) != 0) {
			return z - 1;
		} else {
			return z;
		}
	}

	private static int calculateNoise(int i, int j) {
		int k = i + j * 57;
		k = k << 13 ^ k;
		int l = k * (k * k * 15731 + 0xc0ae5) + 0x5208dd0d & 0x7fffffff;
		return l >> 19 & 0xff;
	}

	public final void createRegionScene(CollisionMap aclass11[], SceneGraph worldController) {
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < regionSizeX; k++) {
				for (int i1 = 0; i1 < regionSizeY; i1++)
					if ((tileFlags[j][k][i1] & 1) == 1) {
						int k1 = j;
						if ((tileFlags[1][k][i1] & 2) == 2)
							k1--;
						if (k1 >= 0)
							aclass11[k1].method213(i1, k);
					}
			}

		}
		for (int l = 0; l < 4; l++) {
			byte abyte0[][] = shading[l];
			byte byte0 = 96;
			
			char c = '\u0300';
			byte byte1 = -50;
			byte byte2 = -10;
			byte byte3 = -50;
			int j3 = (int) Math.sqrt(byte1 * byte1 + byte2 * byte2 + byte3 * byte3);
			int l3 = c * j3 >> 8;
			for (int j4 = 1; j4 < regionSizeY - 1; j4++) {
				for (int j5 = 1; j5 < regionSizeX - 1; j5++) {
					int k6 = tileHeights[l][j5 + 1][j4] - tileHeights[l][j5 - 1][j4];
					int l7 = tileHeights[l][j5][j4 + 1] - tileHeights[l][j5][j4 - 1];
					int j9 = (int) Math.sqrt(k6 * k6 + 0x10000 + l7 * l7);
					int k12 = (k6 << 8) / j9;
					int l13 = 0x10000 / j9;
					int j15 = (l7 << 8) / j9;
					int j16 = byte0 + (byte1 * k12 + byte2 * l13 + byte3 * j15) / l3;
					int j17 = (abyte0[j5 - 1][j4] >> 2) + (abyte0[j5 + 1][j4] >> 3) + (abyte0[j5][j4 - 1] >> 2)
							+ (abyte0[j5][j4 + 1] >> 3) + (abyte0[j5][j4] >> 1);
					tileLighting[j5][j4] = j16 - j17;
				}
			}
		
			int[][] paletteIndices = new int[regionSizeX][regionSizeY];
			for (int z = 0; z < regionSizeY; z++) {
				hues[z] = 0;
				saturations[z] = 0;
				luminances[z] = 0;
				chromas[z] = 0;
				anIntArray128[z] = 0;
			}

			for (int x = -5; x < regionSizeX; x++) {
				for (int z = 0; z < regionSizeY; z++) {
					int xForwardOffset = x + 5;
					if (xForwardOffset < regionSizeX) {
						int underlayId = underlays[l][xForwardOffset][z] & 0xff;
						if (underlayId > 0) {
							FloorDefinition flo = FloorDefinition.underlays[underlayId - 1];
							hues[z] += flo.blendHue;
							saturations[z] += flo.saturation;
							luminances[z] += flo.luminance;
							chromas[z] += flo.blendHueMultiplier;
							anIntArray128[z]++;
						}
					/*	int overlayId = overlays[l][xForwardOffset][z] & 0xff;
						if (overlayId > 0) {
							FloorDefinition flo_1 = FloorDefinition.overlays[overlayId - 1];
							hues[z] -= flo_1.blendHue;
							saturations[z] -= flo_1.saturation;
							luminances[z] -= flo_1.luminance;
							chromas[z] -= flo_1.blendHueMultiplier;
							anIntArray128[z]--;
						}*/
					}
					int xBackwardOffset = x - 5;
					if (xBackwardOffset >= 0) {
						int underlayId = underlays[l][xBackwardOffset][z] & 0xff;
						if (underlayId > 0) {
							FloorDefinition flo_1 = FloorDefinition.underlays[underlayId - 1];
							hues[z] -= flo_1.blendHue;
							saturations[z] -= flo_1.saturation;
							luminances[z] -= flo_1.luminance;
							chromas[z] -= flo_1.blendHueMultiplier;
							anIntArray128[z]--;
						}
						/*int overlayId = overlays[l][xBackwardOffset][z] & 0xff;
						if (overlayId > 0) {
							FloorDefinition flo_1 = FloorDefinition.overlays[overlayId - 1];
							hues[z] -= flo_1.blendHue;
							saturations[z] -= flo_1.saturation;
							luminances[z] -= flo_1.luminance;
							chromas[z] -= flo_1.blendHueMultiplier;
							anIntArray128[z]--;
						}*/
					}
				}
				if (x >= 0) {
					int hueSum = 0;
					int saturationSum = 0;
					int lightnessSum = 0;
					int dividerSum = 0;
					int sizeSum = 0;
					for (int z = -5; z < regionSizeY; z++) {
						int zForwardOffset = z + 5;
						if (zForwardOffset < regionSizeY) {
							hueSum += hues[zForwardOffset];
							saturationSum += saturations[zForwardOffset];
							lightnessSum += luminances[zForwardOffset];
							dividerSum += chromas[zForwardOffset];
							sizeSum += anIntArray128[zForwardOffset];
						}
						int zBackwardOffset = z - 5;
						if (zBackwardOffset >= 0) {
							hueSum -= hues[zBackwardOffset];
							saturationSum -= saturations[zBackwardOffset];
							lightnessSum -= luminances[zBackwardOffset];
							dividerSum -= chromas[zBackwardOffset];
							sizeSum -= anIntArray128[zBackwardOffset];
						}
						if (z >= 0 && dividerSum > 0 && sizeSum > 0) {
							int hue = (hueSum * 256) / dividerSum;
							int sat = saturationSum / sizeSum;
							int light = lightnessSum / sizeSum;
							paletteIndices[x][z] = FloorDefinition.hsl24to16(hue, sat, light);
						}
					}
				}
			}
			for (int x = 0; x < regionSizeX; x++) {
				int nextX = x >= regionSizeX - 1 ? x : x + 1;
				for (int z = 0; z < regionSizeY; z++) {
					int nextZ = z >= regionSizeY - 1 ? z : z + 1;
					if ((!lowMem || (tileFlags[0][x][z] & 2) != 0
							|| (tileFlags[l][x][z] & 0x10) == 0 && getCollisionPlane(z, l, x) == currentHeight)) {
						if (l < maximumPlane) {
							maximumPlane = l;
						}
						int underlayA = underlays[l][x][z] & 0xff;
						int underlayB = underlays[l][nextX][z] & 0xff;
						int underlayC = underlays[l][nextX][nextZ] & 0xff;
						int underlayD = underlays[l][x][nextZ] & 0xff;
						

						int overlayB = overlays[l][nextX][z] & 0xff;
						int overlayC = overlays[l][nextX][nextZ] & 0xff;
						int overlayD = overlays[l][x][nextZ] & 0xff;

						int overlayA = overlays[l][x][z] & 0xff;
						if (underlayA > 0 || overlayA > 0) {
							int tileHeightA = tileHeights[l][x][z];
							int tileHeightB = tileHeights[l][nextX][z];
							int tileHeightC = tileHeights[l][nextX][nextZ];
							int tileHeightD = tileHeights[l][x][nextZ];

							int tileShadowA = tileLighting[x][z];
							int tileShadowB = tileLighting[nextX][z];
							int tileShadowC = tileLighting[nextX][nextZ];
							int tileShadowD = tileLighting[x][nextZ];
							int tileShadowA2 = tileLighting[x][z];
							int tileShadowB2 = tileLighting[nextX][z];
							int tileShadowC2 = tileLighting[nextX][nextZ];
							int tileShadowD2 = tileLighting[x][nextZ];

							int paletteIndexA = -1;
							int paletteIndexB = -1;
							int paletteIndexC = -1;
							int paletteIndexD = -1;
							if (underlayA > 0) {
								paletteIndexA = paletteIndices[x][z];
								if (underlayB > 0) {
									paletteIndexB = paletteIndices[nextX][z];
								}
								if (underlayC > 0) {
									paletteIndexC = paletteIndices[nextX][nextZ];
								}
								if (underlayD > 0) {
									paletteIndexD = paletteIndices[x][nextZ];
								}

								if (paletteIndexB == -1) {
									paletteIndexB = paletteIndexA;
								}

								if (paletteIndexC == -1) {
									paletteIndexC = paletteIndexA;
								}

								if (paletteIndexD == -1) {
									paletteIndexD = paletteIndexA;
								}
							}
							if (l > 0) {
								boolean occlude = true;
								if (underlayA == 0 && overlayTypes[l][x][z] != 0) {
									occlude = false;
								}

								if (overlayA > 0 && !FloorDefinition.overlays[overlayA - 1].occlude) {
									occlude = false;
								}

								if (occlude && tileHeightA == tileHeightB && tileHeightA == tileHeightC
										&& tileHeightA == tileHeightD) {
									anIntArrayArrayArray135[l][x][z] |= 0x924;
								}
							}
							int minimapRgb = 0;
							boolean check = Client.enableHDTextures || !Configuration.enableTileBlending || !Configuration.enableSmoothShading;
							if (paletteIndexA != -1) {
								minimapRgb = Rasterizer3D.hslToRgb[method187(paletteIndexA, 96)];
							}
							if (overlayA == 0) {
								worldController.addTile(l, x, z, 0, 0, -1, 154, tileHeightA, tileHeightB, tileHeightC,
										tileHeightD, method187(paletteIndexA, tileShadowA),
										method187(check ? paletteIndexA : paletteIndexB, tileShadowB),
										method187(check ? paletteIndexA : paletteIndexC, tileShadowC),
										method187(check ? paletteIndexA : paletteIndexD, tileShadowD), 0, 0, 0, 0,
										minimapRgb, 0, false);
							} else {
								int shape = overlayTypes[l][x][z] + 1;
								byte angle = overlayOrientations[l][x][z];
								if (overlayA - 1 > FloorDefinition.overlays.length) {
									overlayA = FloorDefinition.overlays.length;
								}
								FloorDefinition def_over = FloorDefinition.overlays[overlayA - 1];
								int textureId = def_over.texture;
								int floorId;
								int minimapColor;
								if (textureId == 51) {
									textureId = 3;
								}
								if (textureId >= 0 && textureId <= Rasterizer3D.textureAmount) {
									minimapColor = Rasterizer3D.getOverallColour(textureId);
									floorId = -1;
									if(textureId == 43) {
										tileShadowA = 83;
										tileShadowB =  83;
										tileShadowC =  83;
										tileShadowD =  83;
									}
								} else if (def_over.rgb == 0xff00ff) {
									minimapColor = 0;
									floorId = -2;
									textureId = -1;
								} else if (def_over.rgb == 0x333333) {
									minimapColor = Rasterizer3D.hslToRgb[checkedLight(def_over.hsl16, 96)];
									floorId = -2;
									textureId = -1;
								} else {
									floorId = encode(def_over.hue, def_over.saturation, def_over.luminance);
									minimapColor = Rasterizer3D.hslToRgb[checkedLight(def_over.hsl16, 96)];
								}
								if (minimapColor == 0x000000 && def_over.anotherRgb != -1) {
									int newMinimapColor = encode(def_over.anotherHue, def_over.anotherSaturation, def_over.anotherLuminance);
									minimapColor = Rasterizer3D.hslToRgb[checkedLight(newMinimapColor, 96)];
								}
								//System.out.println("x: " + x + " y: " + z + " rgb: " + def_over.rgb);
								worldController.addTile(l, x, z, shape, angle, textureId, 154, tileHeightA, tileHeightB,
										tileHeightC, tileHeightD, method187(paletteIndexA, tileShadowA2),
										method187(paletteIndexA, tileShadowB2), method187(paletteIndexA, tileShadowC2),
										method187(paletteIndexA, tileShadowD2), checkedLight(floorId, tileShadowA),
										checkedLight(floorId, tileShadowB), checkedLight(floorId, tileShadowC),
										checkedLight(floorId, tileShadowD), minimapRgb, minimapColor, textureId >= 0 && textureId <= Rasterizer3D.textureAmount);
							}
						}
					}
				}
			}

			for (int j8 = 1; j8 < regionSizeY - 1; j8++) {
				for (int i10 = 1; i10 < regionSizeX - 1; i10++)
					worldController.method278(l, i10, j8, method182(j8, l, i10));
			}
		}
		worldController.method305(-10, -50, -50);
		for (int j1 = 0; j1 < regionSizeX; j1++) {
			for (int l1 = 0; l1 < regionSizeY; l1++)
				if ((tileFlags[1][j1][l1] & 2) == 2)
					worldController.method276(l1, j1);
		}
		int i2 = 1;
		int j2 = 2;
		int k2 = 4;
		for (int l2 = 0; l2 < 4; l2++) {
			if (l2 > 0) {
				i2 <<= 3;
				j2 <<= 3;
				k2 <<= 3;
			}
			for (int i3 = 0; i3 <= l2; i3++) {
				for (int k3 = 0; k3 <= regionSizeY; k3++) {
					for (int i4 = 0; i4 <= regionSizeX; i4++) {
						if ((anIntArrayArrayArray135[i3][i4][k3] & i2) != 0) {
							int k4 = k3;
							int l5 = k3;
							int i7 = i3;
							int k8 = i3;
							for (; k4 > 0 && (anIntArrayArrayArray135[i3][i4][k4 - 1] & i2) != 0; k4--)
								;
							for (; l5 < regionSizeY && (anIntArrayArrayArray135[i3][i4][l5 + 1] & i2) != 0; l5++)
								;
							label0: for (; i7 > 0; i7--) {
								for (int j10 = k4; j10 <= l5; j10++)
									if ((anIntArrayArrayArray135[i7 - 1][i4][j10] & i2) == 0)
										break label0;
							}
							label1: for (; k8 < l2; k8++) {
								for (int k10 = k4; k10 <= l5; k10++)
									if ((anIntArrayArrayArray135[k8 + 1][i4][k10] & i2) == 0)
										break label1;
							}
							int l10 = ((k8 + 1) - i7) * ((l5 - k4) + 1);
							if (l10 >= 8) {
								char c1 = '\360';
								int k14 = tileHeights[k8][i4][k4] - c1;
								int l15 = tileHeights[i7][i4][k4];
								SceneGraph.createNewSceneCluster(l2, i4 * 128, l15, i4 * 128, l5 * 128 + 128, k14,
										k4 * 128, 1);
								for (int l16 = i7; l16 <= k8; l16++) {
									for (int l17 = k4; l17 <= l5; l17++)
										anIntArrayArrayArray135[l16][i4][l17] &= ~i2;
								}
							}
						}
						if ((anIntArrayArrayArray135[i3][i4][k3] & j2) != 0) {
							int l4 = i4;
							int i6 = i4;
							int j7 = i3;
							int l8 = i3;
							for (; l4 > 0 && (anIntArrayArrayArray135[i3][l4 - 1][k3] & j2) != 0; l4--)
								;
							for (; i6 < regionSizeX && (anIntArrayArrayArray135[i3][i6 + 1][k3] & j2) != 0; i6++)
								;
							label2: for (; j7 > 0; j7--) {
								for (int i11 = l4; i11 <= i6; i11++)
									if ((anIntArrayArrayArray135[j7 - 1][i11][k3] & j2) == 0)
										break label2;
							}
							label3: for (; l8 < l2; l8++) {
								for (int j11 = l4; j11 <= i6; j11++)
									if ((anIntArrayArrayArray135[l8 + 1][j11][k3] & j2) == 0)
										break label3;
							}
							int k11 = ((l8 + 1) - j7) * ((i6 - l4) + 1);
							if (k11 >= 8) {
								char c2 = '\360';
								int l14 = tileHeights[l8][l4][k3] - c2;
								int i16 = tileHeights[j7][l4][k3];
								SceneGraph.createNewSceneCluster(l2, l4 * 128, i16, i6 * 128 + 128, k3 * 128, l14,
										k3 * 128, 2);
								for (int i17 = j7; i17 <= l8; i17++) {
									for (int i18 = l4; i18 <= i6; i18++)
										anIntArrayArrayArray135[i17][i18][k3] &= ~j2;
								}
							}
						}
						if ((anIntArrayArrayArray135[i3][i4][k3] & k2) != 0) {
							int i5 = i4;
							int j6 = i4;
							int k7 = k3;
							int i9 = k3;
							for (; k7 > 0 && (anIntArrayArrayArray135[i3][i4][k7 - 1] & k2) != 0; k7--)
								;
							for (; i9 < regionSizeY && (anIntArrayArrayArray135[i3][i4][i9 + 1] & k2) != 0; i9++)
								;
							label4: for (; i5 > 0; i5--) {
								for (int l11 = k7; l11 <= i9; l11++)
									if ((anIntArrayArrayArray135[i3][i5 - 1][l11] & k2) == 0)
										break label4;
							}
							label5: for (; j6 < regionSizeX; j6++) {
								for (int i12 = k7; i12 <= i9; i12++)
									if ((anIntArrayArrayArray135[i3][j6 + 1][i12] & k2) == 0)
										break label5;
							}
							if (((j6 - i5) + 1) * ((i9 - k7) + 1) >= 4) {
								int j12 = tileHeights[i3][i5][k7];
								SceneGraph.createNewSceneCluster(l2, i5 * 128, j12, j6 * 128 + 128, i9 * 128 + 128,
										j12, k7 * 128, 4);
								for (int k13 = i5; k13 <= j6; k13++) {
									for (int i15 = k7; i15 <= i9; i15++)
										anIntArrayArrayArray135[i3][k13][i15] &= ~k2;
								}
							}
						}
					}
				}
			}
		}
	}

	private int checkedLight(int color, int light) {
		if (color == -2)
			return 0xbc614e;
		if (color == -1) {
			if (light < 0)
				light = 0;
			else if (light > 127)
				light = 127;
			light = 127 - light;
			return light;
		}
		light = (light * (color & 0x7f)) / 128;
		if (light < 2)
			light = 2;
		else if (light > 126)
			light = 126;
		return (color & 0xff80) + light;
	}

	private static int calculateVertexHeight(int i, int j) {
		int mapHeight = (interpolatedNoise(i + 45365, j + 0x16713, 4) - 128)
				+ (interpolatedNoise(i + 10294, j + 37821, 2) - 128 >> 1) + (interpolatedNoise(i, j, 1) - 128 >> 2);
		mapHeight = (int) (mapHeight * 0.29999999999999999D) + 35;
		if (mapHeight < 10)
			mapHeight = 10;
		else if (mapHeight > 60)
			mapHeight = 60;
		return mapHeight;
	}

	public static void passiveRequestGameObjectModels(Buffer stream, ResourceProvider class42_sub1) {
		label0: {
			int i = -1;
			do {
				int j = stream.readUnsignedIntSmartShortCompat();
				if (j == 0)
					break label0;
				i += j;
				ObjectDefinition class46 = ObjectDefinition.forID(i);
				class46.method574(class42_sub1);
				do {
					int k = stream.readUSmart();
					if (k == 0)
						break;
					stream.readUnsignedByte();
				} while (true);
			} while (true);
		}
	}

	public final void initiateVertexHeights(int i, int j, int l, int i1) {
		for (int j1 = i; j1 <= i + j; j1++) {
			for (int k1 = i1; k1 <= i1 + l; k1++)
				if (k1 >= 0 && k1 < regionSizeX && j1 >= 0 && j1 < regionSizeY) {
					shading[0][k1][j1] = 127;
					if (k1 == i1 && k1 > 0)
						tileHeights[0][k1][j1] = tileHeights[0][k1 - 1][j1];
					if (k1 == i1 + l && k1 < regionSizeX - 1)
						tileHeights[0][k1][j1] = tileHeights[0][k1 + 1][j1];
					if (j1 == i && j1 > 0)
						tileHeights[0][k1][j1] = tileHeights[0][k1][j1 - 1];
					if (j1 == i + j && j1 < regionSizeY - 1)
						tileHeights[0][k1][j1] = tileHeights[0][k1][j1 + 1];
				}

		}
	}
	
	private static int calculateAbsX(ObjectDefinition definition, int x, int y, int rotation) {
		int w = definition.width;
		return x * 128 + (w * 128 / 2);
	}

	private static int calculateAbsY(ObjectDefinition definition, int x, int y, int rotation) {
		int l = definition.length;
		return y * 128 + (l * 128 / 2);
	}

	private void renderObject(int y, SceneGraph worldController, CollisionMap class11, int type, int z, int x,
			int id, int orientation) {
		if (lowMem && (tileFlags[0][x][y] & 2) == 0) {
			if ((tileFlags[z][x][y] & 0x10) != 0)
				return;
			if (method182(y, z, x) != currentHeight)
				return;
		}
		if (z < maximumPlane)
			maximumPlane = z;
		ObjectDefinition definition = ObjectDefinition.forID(id);
		int size1;
		int size2;
		if (orientation == 1 || orientation == 3) {
			size1 = definition.length;//objectSizeY
			size2 = definition.width;//objectSizeX
		} else {
			size1 = definition.width;
			size2 = definition.length;
		}
		int modX;
		int modX1;
		if (regionSizeX >= (size1 + x)) {
			modX1 = x + (size1 + 1 >> 1);
			modX = x + (size1 >> 1);
		} else {
			modX = x;
			modX1 = x + 1;
		}
		int modY;
		int modY1;
		if (regionSizeY >= (size2 + y)) {
			modY1 = y + (size2 + 1 >> 1);
			modY = (size2 >> 1) + y;
		} else {
			modY = y;
			modY1 = 1 + y;
		}

		int underHeight = tileHeights[z][modX][modY];
		int l1 = tileHeights[z][modX1][modY];
		int i2 = tileHeights[z][modX1][modY1];
		int j2 = tileHeights[z][modX][modY1];
		int k2 = underHeight + l1 + i2 + j2 >> 2;
		long key = (long) (orientation << 20 | type << 14 | (y << 7 | x) + 0x40000000);
		if(!definition.hasActions) {
			key |= ~0x7fffffffffffffffL;
		}
		if(definition.supportItems == 1) {
			key |= 0x400000L;
		}
		key |= (long) id << 32;
		if(definition.overheadText != null) {
			int absX = calculateAbsX(definition, x, y, orientation);
			int absY = calculateAbsY(definition, x, y, orientation);
			worldController.addFloatingText(z, x, y, absX, absY, definition.font, definition.overheadText, definition.overheadColour, definition.overheadShadow, definition.overheadTextHeight, definition.overheadTextOpacity);
		}
		if(definition.overheadSprite != null) {
			int absX = calculateAbsX(definition, x, y, orientation);
			int absY = calculateAbsY(definition, x, y, orientation);
			worldController.addFloatingSprite(z, x, y, absX, absY, definition.overheadSprite, definition.overheadSpriteHeight, definition.overheadSpriteOpacity);
		}

		if (type == 22) {
			if (lowMem && !definition.hasActions && !definition.obstructsGround)
				return;
			Object obj;
			if (definition.animation == -1 && definition.morphisms == null)
				obj = definition.modelAt(22, orientation, underHeight, l1, i2, j2, -1);
			else
				obj = new AnimatedGameObject(id, orientation, 22, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method280(z, k2, y, ((Renderable) (obj)), key, x);
			if (definition.solid && definition.hasActions && class11 != null)
				class11.method213(y, x);
			return;
		}
		if (type == 10 || type == 11) {
			Object obj1;
			if (definition.animation == -1 && definition.morphisms == null)
				obj1 = definition.modelAt(10, orientation, underHeight, l1, i2, j2, -1);
			else
				obj1 = new AnimatedGameObject(id, orientation, 10, l1, i2, underHeight, j2, definition.animation, true);
			if (obj1 != null) {
				int i5 = 0;
				if (type == 11)
					i5 += 256;
				int j4;
				int l4;
				if (orientation == 1 || orientation == 3) {
					j4 = definition.length;
					l4 = definition.width;
				} else {
					j4 = definition.width;
					l4 = definition.length;
				}
				if (worldController.method284(key, k2, l4, ((Renderable) (obj1)), j4, z, i5, y, x)
						&& definition.castsShadow) {
					Model model;
					if (obj1 instanceof Model)
						model = (Model) obj1;
					else
						model = definition.modelAt(10, orientation, underHeight, l1, i2, j2, -1);
					if (model != null) {
						for (int j5 = 0; j5 <= j4; j5++) {
							for (int k5 = 0; k5 <= l4; k5++) {
								int l5 = model.maxVertexDistanceXZPlane / 4;
								if (l5 > 30)
									l5 = 30;
								if (l5 > shading[z][x + j5][y + k5])
									shading[z][x + j5][y + k5] = (byte) l5;
							}

						}

					}
				}
			}
			if (definition.solid && class11 != null)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		}
		if (type >= 12) {
			Object obj2;
			if (definition.animation == -1 && definition.morphisms == null)
				obj2 = definition.modelAt(type, orientation, underHeight, l1, i2, j2, -1);
			else
				obj2 = new AnimatedGameObject(id, orientation, type, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method284(key, k2, 1, ((Renderable) (obj2)), 1, z, 0, y, x);
			if (type >= 12 && type <= 17 && type != 13 && z > 0)
				anIntArrayArrayArray135[z][x][y] |= 0x924;
			if (definition.solid && class11 != null)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		}
		if (type == 0) {
			Object obj3;
			if (definition.animation == -1 && definition.morphisms == null)
				obj3 = definition.modelAt(0, orientation, underHeight, l1, i2, j2, -1);
			else
				obj3 = new AnimatedGameObject(id, orientation, 0, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method282(anIntArray152[orientation], ((Renderable) (obj3)), key, y, x, null, k2, 0, z);
			if (orientation == 0) {
				if (definition.castsShadow) {
					shading[z][x][y] = 50;
					shading[z][x][y + 1] = 50;
				}
				if (definition.occludes)
					anIntArrayArrayArray135[z][x][y] |= 0x249;
			} else if (orientation == 1) {
				if (definition.castsShadow) {
					shading[z][x][y + 1] = 50;
					shading[z][x + 1][y + 1] = 50;
				}
				if (definition.occludes)
					anIntArrayArrayArray135[z][x][y + 1] |= 0x492;
			} else if (orientation == 2) {
				if (definition.castsShadow) {
					shading[z][x + 1][y] = 50;
					shading[z][x + 1][y + 1] = 50;
				}
				if (definition.occludes)
					anIntArrayArrayArray135[z][x + 1][y] |= 0x249;
			} else if (orientation == 3) {
				if (definition.castsShadow) {
					shading[z][x][y] = 50;
					shading[z][x + 1][y] = 50;
				}
				if (definition.occludes)
					anIntArrayArrayArray135[z][x][y] |= 0x492;
			}
			if (definition.solid && class11 != null)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			if (definition.decorDisplacement != 16)
				worldController.method290(y, definition.decorDisplacement, x, z);
			return;
		}
		if (type == 1) {
			Object obj4;
			if (definition.animation == -1 && definition.morphisms == null)
				obj4 = definition.modelAt(1, orientation, underHeight, l1, i2, j2, -1);
			else
				obj4 = new AnimatedGameObject(id, orientation, 1, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method282(anIntArray140[orientation], ((Renderable) (obj4)), key, y, x, null, k2, 0, z);
			if (definition.castsShadow)
				if (orientation == 0)
					shading[z][x][y + 1] = 50;
				else if (orientation == 1)
					shading[z][x + 1][y + 1] = 50;
				else if (orientation == 2)
					shading[z][x + 1][y] = 50;
				else if (orientation == 3)
					shading[z][x][y] = 50;
			if (definition.solid && class11 != null)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 2) {
			int i3 = orientation + 1 & 3;
			Object obj11;
			Object obj12;
			if (definition.animation == -1 && definition.morphisms == null) {
				obj11 = definition.modelAt(2, 4 + orientation, underHeight, l1, i2, j2, -1);
				obj12 = definition.modelAt(2, i3, underHeight, l1, i2, j2, -1);
			} else {
				obj11 = new AnimatedGameObject(id, 4 + orientation, 2, l1, i2, underHeight, j2, definition.animation, true);
				obj12 = new AnimatedGameObject(id, i3, 2, l1, i2, underHeight, j2, definition.animation, true);
			}
			worldController.method282(anIntArray152[orientation], ((Renderable) (obj11)), key, y, x,
					((Renderable) (obj12)), k2, anIntArray152[i3], z);
			if (definition.occludes)
				if (orientation == 0) {
					anIntArrayArrayArray135[z][x][y] |= 0x249;
					anIntArrayArrayArray135[z][x][y + 1] |= 0x492;
				} else if (orientation == 1) {
					anIntArrayArrayArray135[z][x][y + 1] |= 0x492;
					anIntArrayArrayArray135[z][x + 1][y] |= 0x249;
				} else if (orientation == 2) {
					anIntArrayArrayArray135[z][x + 1][y] |= 0x249;
					anIntArrayArrayArray135[z][x][y] |= 0x492;
				} else if (orientation == 3) {
					anIntArrayArrayArray135[z][x][y] |= 0x492;
					anIntArrayArrayArray135[z][x][y] |= 0x249;
				}
			if (definition.solid && class11 != null)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			if (definition.decorDisplacement != 16)
				worldController.method290(y, definition.decorDisplacement, x, z);
			return;
		}
		if (type == 3) {
			Object obj5;
			if (definition.animation == -1 && definition.morphisms == null)
				obj5 = definition.modelAt(3, orientation, underHeight, l1, i2, j2, -1);
			else
				obj5 = new AnimatedGameObject(id, orientation, 3, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method282(anIntArray140[orientation], ((Renderable) (obj5)), key, y, x, null, k2, 0, z);
			if (definition.castsShadow)
				if (orientation == 0)
					shading[z][x][y + 1] = 50;
				else if (orientation == 1)
					shading[z][x + 1][y + 1] = 50;
				else if (orientation == 2)
					shading[z][x + 1][y] = 50;
				else if (orientation == 3)
					shading[z][x][y] = 50;
			if (definition.solid && class11 != null)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 9) {
			Object obj6;
			if (definition.animation == -1 && definition.morphisms == null)
				obj6 = definition.modelAt(type, orientation, underHeight, l1, i2, j2, -1);
			else
				obj6 = new AnimatedGameObject(id, orientation, type, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method284(key, k2, 1, ((Renderable) (obj6)), 1, z, 0, y, x);
			if (definition.solid && class11 != null)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		}
		if (definition.contouredGround)
			if (orientation == 1) {
				int j3 = j2;
				j2 = i2;
				i2 = l1;
				l1 = underHeight;
				underHeight = j3;
			} else if (orientation == 2) {
				int k3 = j2;
				j2 = l1;
				l1 = k3;
				k3 = i2;
				i2 = underHeight;
				underHeight = k3;
			} else if (orientation == 3) {
				int l3 = j2;
				j2 = underHeight;
				underHeight = l1;
				l1 = i2;
				i2 = l3;
			}
		if (type == 4) {
			Object obj7;
			if (definition.animation == -1 && definition.morphisms == null)
				obj7 = definition.modelAt(4, 0, underHeight, l1, i2, j2, -1);
			else
				obj7 = new AnimatedGameObject(id, 0, 4, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method283(key, y, orientation * 512, z, 0, k2, ((Renderable) (obj7)), x, 0,
					anIntArray152[orientation]);
			return;
		}
		if (type == 5) {
			int i4 = 16;
			long k4 = worldController.method300(z, x, y);
			if (k4 > 0)
				i4 = ObjectDefinition.forID(ObjectKey.getObjectId(k4)).decorDisplacement;
			Object obj13;
			if (definition.animation == -1 && definition.morphisms == null)
				obj13 = definition.modelAt(4, 0, underHeight, l1, i2, j2, -1);
			else
				obj13 = new AnimatedGameObject(id, 0, 4, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method283(key, y, orientation * 512, z, anIntArray137[orientation] * i4, k2, ((Renderable) (obj13)), x,
					anIntArray144[orientation] * i4, anIntArray152[orientation]);
			return;
		}
		if (type == 6) {
			Object obj8;
			if (definition.animation == -1 && definition.morphisms == null)
				obj8 = definition.modelAt(4, 0, underHeight, l1, i2, j2, -1);
			else
				obj8 = new AnimatedGameObject(id, 0, 4, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method283(key, y, orientation, z, 0, k2, ((Renderable) (obj8)), x, 0, 256);
			return;
		}
		if (type == 7) {
			Object obj9;
			if (definition.animation == -1 && definition.morphisms == null)
				obj9 = definition.modelAt(4, 0, underHeight, l1, i2, j2, -1);
			else
				obj9 = new AnimatedGameObject(id, 0, 4, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method283(key, y, orientation, z, 0, k2, ((Renderable) (obj9)), x, 0, 512);
			return;
		}
		if (type == 8) {
			Object obj10;
			if (definition.animation == -1 && definition.morphisms == null)
				obj10 = definition.modelAt(4, 0, underHeight, l1, i2, j2, -1);
			else
				obj10 = new AnimatedGameObject(id, 0, 4, l1, i2, underHeight, j2, definition.animation, true);
			worldController.method283(key, y, orientation, z, 0, k2, ((Renderable) (obj10)), x, 0, 768);
		}
	}

	private static int interpolatedNoise(int i, int j, int k) {
		int l = i / k;
		int i1 = i & k - 1;
		int j1 = j / k;
		int k1 = j & k - 1;
		int l1 = method186(l, j1);
		int i2 = method186(l + 1, j1);
		int j2 = method186(l, j1 + 1);
		int k2 = method186(l + 1, j1 + 1);
		int l2 = method184(l1, i2, i1, k);
		int i3 = method184(j2, k2, i1, k);
		return method184(l2, i3, k1, k);
	}

	public static boolean method178(int i, int j) {
		ObjectDefinition class46 = ObjectDefinition.forID(i);
		if (j == 11)
			j = 10;
		if (j >= 5 && j <= 8)
			j = 4;
		return class46.method577(j);
	}

	public final void decodeConstructedMapsLandscape(int tileZ, int rotation, CollisionMap collisionMaps[], int baseX, int tileX, byte mapData[], int tileY, int plane,
			int baseY, boolean visible) {
		for (int i2 = 0; i2 < 8; i2++) {
			for (int j2 = 0; j2 < 8; j2++)
				if (baseX + i2 > 0 && baseX + i2 < regionSizeX - 1 && baseY + j2 > 0 && baseY + j2 < regionSizeY - 1)
					collisionMaps[plane].anIntArrayArray294[baseX + i2][baseY + j2] &= 0xfeffffff;

		}
		Buffer stream = new Buffer(mapData);
		for (int l2 = 0; l2 < 4; l2++) {
			for (int i3 = 0; i3 < 64; i3++) {
				for (int j3 = 0; j3 < 64; j3++)
					if (l2 == tileZ && i3 >= tileX && i3 < tileX + 8 && j3 >= tileY && j3 < tileY + 8 )
						decodeLandscape(baseY + TiledUtils.getRotatedLandscapeChunkY(j3 & 7, rotation, i3 & 7), 0, stream,
								baseX + TiledUtils.getRotatedLandscapeChunkX(rotation, j3 & 7, i3 & 7), plane, rotation, 0, visible);
					else
						decodeLandscape(-1, 0, stream, -1, 0, 0, 0, false);

			}

		}

	}

	public final void decodeLandscapeFile(byte abyte0[], int offsetY, int offsetX, int baseX, int baseY, CollisionMap aclass11[]) {
		for (int i1 = 0; i1 < 4; i1++) {
			for (int j1 = 0; j1 < 64; j1++) {
				for (int k1 = 0; k1 < 64; k1++)
					if (offsetX + j1 > 0 && offsetX + j1 < regionSizeX - 1 && offsetY + k1 > 0 && offsetY + k1 < regionSizeY - 1)
						aclass11[i1].anIntArrayArray294[offsetX + j1][offsetY + k1] &= 0xfeffffff;

			}

		}

		Buffer stream = new Buffer(abyte0);
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++)
					decodeLandscape(y + offsetY, baseY, stream, x + offsetX, z, 0, baseX, true);

			}

		}
	}

	private void decodeLandscape(int i, int j, Buffer stream, int k, int l, int i1, int baseX, boolean visible) {
		try {
			if (k >= 0 && k < regionSizeX && i >= 0 && i < regionSizeY) {
				int absX = (baseX + k);
				int absY = (j + i);
				int absZ = (i1 + l);
				tileFlags[l][k][i] = 0;
				do {
					int l1 = stream.readUnsignedByte();
					if (l1 == 0)
						if (l == 0) {
							tileHeights[0][k][i] = -calculateVertexHeight(0xe3b7b + k + baseX, 0x87cce + i + j) * 8;
							return;
						} else {
							tileHeights[l][k][i] = tileHeights[l - 1][k][i] - 240;
							return;
						}
					if (l1 == 1) {
						int j2 = stream.readUnsignedByte();
						if (j2 == 1)
							j2 = 0;
						if (l == 0) {
							tileHeights[0][k][i] = -j2 * 8;
							return;
						} else {
							tileHeights[l][k][i] = tileHeights[l - 1][k][i] - j2 * 8;
							return;
						}
					}
					
					/**
					 * Legendary donator zone
					 */
					if (absX >= 3367 && absX <= 3370 && absY >= 9637 && absY <= 9643 && absZ == 1) {
						overlays[l][k][i] = 100; // 103 - 16 =
						// Orange/brown
						overlayTypes[l][k][i] = 0; // tile shape
					}
					/**
					 * Free for all 1v1 to multi boundary
					 */
					if (absX >= 3340 && absX <= 3340 && absY >= 4760 && absY <= 4851 && absZ == 0) {
						overlays[l][k][i] = 44; // 44 = Gray
						overlayTypes[l][k][i] = 0; // tile shape
					}
					if (l1 <= 49) {
						byte val =  stream.readSignedByte();
						if(!visible)
							continue;
						overlays[l][k][i] = val;
						overlayTypes[l][k][i] = (byte) ((l1 - 2) / 4);
						overlayOrientations[l][k][i] = (byte) ((l1 - 2) + i1 & 3);
					} else if (l1 <= 81)
						tileFlags[l][k][i] = (byte) (l1 - 49);
					else if(visible)
						underlays[l][k][i] = (byte) (l1 - 81);
				} while (true);
			}
			do {
				int i2 = stream.readUnsignedByte();
				if (i2 == 0)
					break;
				if (i2 == 1) {
					stream.readUnsignedByte();
					return;
				}
				if (i2 <= 49)
					stream.readUnsignedByte();
			} while (true);
		} catch (Exception e) {
		}
	}

	private int method182(int i, int j, int k) {
		if ((tileFlags[j][k][i] & 8) != 0)
			return 0;
		if (j > 0 && (tileFlags[1][k][i] & 2) != 0)
			return j - 1;
		else
			return j;
	}

	public final void decodeConstructedMapObjects(CollisionMap collisionMaps[], SceneGraph sceneGraph, int tileZ, int baseX, int tileY, int plane,
			byte data[], int tileX, int rotation, int baseY) {
		label0: {
			Buffer stream = new Buffer(data);
			int objectId = -1;
			do {
				int incr = stream.readUnsignedIntSmartShortCompat();
				if (incr == 0)
					break label0;
				objectId += incr;
				int location = 0;
				do {
					int incr2 = stream.readUSmart();
					if (incr2 == 0)
						break;
					location += incr2 - 1;
					int localY = location & 0x3f;
					int localX = location >> 6 & 0x3f;
					int height = location >> 12;
					int objectData = stream.readUnsignedByte();
					int type = objectData >> 2;
					int orientation = objectData & 3;
					if (height == tileZ && localX >= tileX && localX < tileX + 8 && localY >= tileY && localY < tileY + 8) {
						ObjectDefinition definition = ObjectDefinition.forID(objectId);
						int size1;
						int size2;
						if (orientation == 1 || orientation == 3) {
							size1 = definition.length;//objectSizeY
							size2 = definition.width;//objectSizeX
						} else {
							size1 = definition.width;
							size2 = definition.length;
						}
						int j4 = baseX + TiledUtils.getRotatedMapChunkX(rotation, size2, localX & 7, localY & 7, size1);
						int k4 = baseY + TiledUtils.getRotatedMapChunkY(localY & 7, size2, rotation, size1, localX & 7);
						if (j4 > 0 && k4 > 0 && j4 < regionSizeX - 1 && k4 < regionSizeY - 1) {
							
							int l4 = height;
							if ((tileFlags[1][j4][k4] & 2) == 2)
								l4--;
							CollisionMap class11 = null;
							if (l4 >= 0)
								class11 = collisionMaps[l4];
							System.out.println("Spawning object " + location + " at " + j4 + ", " + k4);
							renderObject(k4, sceneGraph, class11, type, plane, j4, objectId, orientation + rotation & 3);
						}
					}
				} while (true);
			} while (true);
		}
	}

	private static int method184(int i, int j, int k, int l) {
		int i1 = 0x10000 - Rasterizer3D.anIntArray1471[(k * 1024) / l] >> 1;
		return (i * (0x10000 - i1) >> 16) + (j * i1 >> 16);
	}

	private static int method186(int i, int j) {
		int k = calculateNoise(i - 1, j - 1) + calculateNoise(i + 1, j - 1) + calculateNoise(i - 1, j + 1)
				+ calculateNoise(i + 1, j + 1);
		int l = calculateNoise(i - 1, j) + calculateNoise(i + 1, j) + calculateNoise(i, j - 1)
				+ calculateNoise(i, j + 1);
		int i1 = calculateNoise(i, j);
		return k / 16 + l / 8 + i1 / 4;
	}

	private static int method187(int i, int j) {
		if (i == -1)
			return 0xbc614e;
		j = (j * (i & 0x7f)) / 128;
		if (j < 2)
			j = 2;
		else if (j > 126)
			j = 126;
		return (i & 0xff80) + j;
	}

	public static void method188(SceneGraph worldController, int orientation, int y, int type, int plane, CollisionMap class11,
			int tileHeights[][][], int x, int id, int k1) {
		int l1 = tileHeights[plane][x][y];
		int i2 = tileHeights[plane][x + 1][y];
		int j2 = tileHeights[plane][x + 1][y + 1];
		int k2 = tileHeights[plane][x][y + 1];
		int l2 = l1 + i2 + j2 + k2 >> 2;
		ObjectDefinition definition = ObjectDefinition.forID(id);
		long key = (long) (orientation << 20 | type << 14 | (y << 7 | x) + 0x40000000);
		if(!definition.hasActions) {
			key |= ~0x7fffffffffffffffL;
		}
		if(definition.supportItems == 1) {
			key |= 0x400000L;
		}
		key |= (long) id << 32;
		if(definition.overheadText != null) {
			int absX = calculateAbsX(definition, x, y, orientation);
			int absY = calculateAbsY(definition, x, y, orientation);
			
			worldController.addFloatingText(plane, x, y, absX, absY, definition.font, definition.overheadText, definition.overheadColour, definition.overheadShadow, definition.overheadTextHeight, definition.overheadTextOpacity);
		}
		if(definition.overheadSprite != null) {
			int absX = calculateAbsX(definition, x, y, orientation);
			int absY = calculateAbsY(definition, x, y, orientation);
			worldController.addFloatingSprite(plane, x, y,  absX, absY, definition.overheadSprite, definition.overheadSpriteHeight, definition.overheadSpriteOpacity);
		}
		if (type == 22) {
			
				Object obj;
				if (definition.animation == -1 && definition.morphisms == null)
					obj = definition.modelAt(22, orientation, l1, i2, j2, k2, -1);
				else
					obj = new AnimatedGameObject(id, orientation, 22, i2, j2, l1, k2, definition.animation, true);
			
					worldController.method280(k1, l2, y, ((Renderable) (obj)), key, x);
		
				if (definition.solid && definition.hasActions)
					class11.method213(y, x);
			
			return;
		}
		if (type == 10 || type == 11) {
			Object obj1;
			if (definition.animation == -1 && definition.morphisms == null)
				obj1 = definition.modelAt(10, orientation, l1, i2, j2, k2, -1);
			else
				obj1 = new AnimatedGameObject(id, orientation, 10, i2, j2, l1, k2, definition.animation, true);
			if (obj1 != null) {
				int j5 = 0;
				if (type == 11)
					j5 += 256;
				int k4;
				int i5;
				if (orientation == 1 || orientation == 3) {
					k4 = definition.length;
					i5 = definition.width;
				} else {
					k4 = definition.width;
					i5 = definition.length;
				}
				worldController.method284(key, l2, i5, ((Renderable) (obj1)), k4, k1, j5, y, x);
			}
			if (definition.solid)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		}
		if (type >= 12) {
			Object obj2;
			if (definition.animation == -1 && definition.morphisms == null)
				obj2 = definition.modelAt(type, orientation, l1, i2, j2, k2, -1);
			else
				obj2 = new AnimatedGameObject(id, orientation, type, i2, j2, l1, k2, definition.animation, true);
			worldController.method284(key, l2, 1, ((Renderable) (obj2)), 1, k1, 0, y, x);
			if (definition.solid)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		}
		if (type == 0) {
			Object obj3;
			if (definition.animation == -1 && definition.morphisms == null)
				obj3 = definition.modelAt(0, orientation, l1, i2, j2, k2, -1);
			else
				obj3 = new AnimatedGameObject(id, orientation, 0, i2, j2, l1, k2, definition.animation, true);
			worldController.method282(anIntArray152[orientation], ((Renderable) (obj3)), key, y, x, null, l2, 0, k1);
			if (definition.solid)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 1) {
			Object obj4;
			if (definition.animation == -1 && definition.morphisms == null)
				obj4 = definition.modelAt(1, orientation, l1, i2, j2, k2, -1);
			else
				obj4 = new AnimatedGameObject(id, orientation, 1, i2, j2, l1, k2, definition.animation, true);
			worldController.method282(anIntArray140[orientation], ((Renderable) (obj4)), key, y, x, null, l2, 0, k1);
			if (definition.solid)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 2) {
			int j3 = orientation + 1 & 3;
			Object obj11;
			Object obj12;
			if (definition.animation == -1 && definition.morphisms == null) {
				obj11 = definition.modelAt(2, 4 + orientation, l1, i2, j2, k2, -1);
				obj12 = definition.modelAt(2, j3, l1, i2, j2, k2, -1);
			} else {
				obj11 = new AnimatedGameObject(id, 4 + orientation, 2, i2, j2, l1, k2, definition.animation, true);
				obj12 = new AnimatedGameObject(id, j3, 2, i2, j2, l1, k2, definition.animation, true);
			}
			worldController.method282(anIntArray152[orientation], ((Renderable) (obj11)), key, y, x,
					((Renderable) (obj12)), l2, anIntArray152[j3], k1);
			if (definition.solid)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 3) {
			Object obj5;
			if (definition.animation == -1 && definition.morphisms == null)
				obj5 = definition.modelAt(3, orientation, l1, i2, j2, k2, -1);
			else
				obj5 = new AnimatedGameObject(id, orientation, 3, i2, j2, l1, k2, definition.animation, true);
			worldController.method282(anIntArray140[orientation], ((Renderable) (obj5)), key, y, x, null, l2, 0, k1);
			if (definition.solid)
				class11.method211(y, orientation, x, type, definition.impenetrable);
			return;
		}
		if (type == 9) {
			Object obj6;
			if (definition.animation == -1 && definition.morphisms == null)
				obj6 = definition.modelAt(type, orientation, l1, i2, j2, k2, -1);
			else
				obj6 = new AnimatedGameObject(id, orientation, type, i2, j2, l1, k2, definition.animation, true);
			worldController.method284(key, l2, 1, ((Renderable) (obj6)), 1, k1, 0, y, x);
			if (definition.solid)
				class11.method212(definition.impenetrable, definition.width, definition.length, x, y, orientation);
			return;
		} // try
		if (definition.contouredGround)
			if (orientation == 1) {
				int k3 = k2;
				k2 = j2;
				j2 = i2;
				i2 = l1;
				l1 = k3;
			} else if (orientation == 2) {
				int l3 = k2;
				k2 = i2;
				i2 = l3;
				l3 = j2;
				j2 = l1;
				l1 = l3;
			} else if (orientation == 3) {
				int i4 = k2;
				k2 = l1;
				l1 = i2;
				i2 = j2;
				j2 = i4;
			}
		if (type == 4) {
			Object obj7;
			if (definition.animation == -1 && definition.morphisms == null)
				obj7 = definition.modelAt(4, 0, l1, i2, j2, k2, -1);
			else
				obj7 = new AnimatedGameObject(id, 0, 4, i2, j2, l1, k2, definition.animation, true);
			worldController.method283(key, y, orientation * 512, k1, 0, l2, ((Renderable) (obj7)), x, 0, anIntArray152[orientation]);
			return;
		}
		if (type == 5) {
			int j4 = 16;
			long l4 = worldController.method300(k1, x, y);
			if (l4 > 0)
				j4 = ObjectDefinition.forID(ObjectKey.getObjectId(l4)).decorDisplacement;
			Object obj13;
			if (definition.animation == -1 && definition.morphisms == null)
				obj13 = definition.modelAt(4, 0, l1, i2, j2, k2, -1);
			else
				obj13 = new AnimatedGameObject(id, 0, 4, i2, j2, l1, k2, definition.animation, true);
			worldController.method283(key, y, orientation * 512, k1, anIntArray137[orientation] * j4, l2, ((Renderable) (obj13)), x,
					anIntArray144[orientation] * j4, anIntArray152[orientation]);
			return;
		}
		if (type == 6) {
			Object obj8;
			if (definition.animation == -1 && definition.morphisms == null)
				obj8 = definition.modelAt(4, 0, l1, i2, j2, k2, -1);
			else
				obj8 = new AnimatedGameObject(id, 0, 4, i2, j2, l1, k2, definition.animation, true);
			worldController.method283(key, y, orientation, k1, 0, l2, ((Renderable) (obj8)), x, 0, 256);
			return;
		}
		if (type == 7) {
			Object obj9;
			if (definition.animation == -1 && definition.morphisms == null)
				obj9 = definition.modelAt(4, 0, l1, i2, j2, k2, -1);
			else
				obj9 = new AnimatedGameObject(id, 0, 4, i2, j2, l1, k2, definition.animation, true);
			worldController.method283(key, y, orientation, k1, 0, l2, ((Renderable) (obj9)), x, 0, 512);
			return;
		}
		if (type == 8) {
			Object obj10;
			if (definition.animation == -1 && definition.morphisms == null)
				obj10 = definition.modelAt(4, 0, l1, i2, j2, k2, -1);
			else
				obj10 = new AnimatedGameObject(id, 0, 4, i2, j2, l1, k2, definition.animation, true);
			worldController.method283(key, y, orientation, k1, 0, l2, ((Renderable) (obj10)), x, 0, 768);
		}
	}

	public static void requestModelPreload(byte[] is, ResourceProvider rp) {
		label0: {
		Buffer stream = new Buffer(is);
		int l = -1;
		do {
			int i1 = stream.readUnsignedIntSmartShortCompat();
			if (i1 == 0)
				break label0;
			l += i1;
			int j1 = 0;
			do {
				int k1 = stream.readUSmart();
				if (k1 == 0)
					break;
				j1 += k1 - 1;
				int l1 = j1 & 0x3f;
				int i2 = j1 >> 6 & 0x3f;
				int j2 = j1 >> 12;
				int k2 = stream.readUnsignedByte();
				int l2 = k2 >> 2;
				int i3 = k2 & 3;
				int l3 = j2;
				ObjectDefinition class46 = ObjectDefinition.forID(l);
					class46.method579();
				
				
			} while (true);
		} while (true);
	}
	}
	
	public static boolean method189(int i, byte[] is, int i_250_) {
		boolean bool = true;
		Buffer stream = new Buffer(is);
		int i_252_ = -1;
		for (;;) {
			int i_253_ = stream.readUnsignedIntSmartShortCompat();
			if (i_253_ == 0)
				break;
			i_252_ += i_253_;
			int i_254_ = 0;
			boolean bool_255_ = false;
			for (;;) {
				if (bool_255_) {
					int i_256_ = stream.readUSmart();
					if (i_256_ == 0)
						break;
					stream.readUnsignedByte();
				} else {
					int i_257_ = stream.readUSmart();
					if (i_257_ == 0)
						break;
					i_254_ += i_257_ - 1;
					int i_258_ = i_254_ & 0x3f;
					int i_259_ = i_254_ >> 6 & 0x3f;
					int i_260_ = stream.readUnsignedByte() >> 2;
					int i_261_ = i_259_ + i;
					int i_262_ = i_258_ + i_250_;
					//if (i_261_ > 0 && i_262_ > 0 && i_261_ < regionSizeX - 1 && i_262_ < regionSizeY - 1) {
						ObjectDefinition class46 = ObjectDefinition.forID(i_252_);
						if (i_260_ != 22 || !lowMem || class46.hasActions || class46.obstructsGround) {
							bool &= class46.method579();
							bool_255_ = true;
						}
					//}
				}
			}
		}
		return bool;
	}

	public final void decodeObjectFile(int baseX, CollisionMap aclass11[], int baseY, SceneGraph worldController, byte abyte0[]) {
		label0: {
			Buffer stream = new Buffer(abyte0);
			int l = -1;
			do {
				int i1 = stream.readUnsignedIntSmartShortCompat();
				if (i1 == 0)
					break label0;
				l += i1;
				int j1 = 0;
				do {
					int k1 = stream.readUSmart();
					if (k1 == 0)
						break;
					j1 += k1 - 1;
					int l1 = j1 & 0x3f;
					int i2 = j1 >> 6 & 0x3f;
					int j2 = j1 >> 12;
					int k2 = stream.readUnsignedByte();
					int l2 = k2 >> 2;
					int i3 = k2 & 3;
					int j3 = i2 + baseX;
					int k3 = l1 + baseY;
					if (j3 > 0 && k3 > 0 && j3 < regionSizeX - 1 && k3 < regionSizeY - 1 && j2 >= 0 && j2 < 4) {
						int l3 = j2;
						if ((tileFlags[1][j3][k3] & 2) == 2)
							l3--;
						CollisionMap class11 = null;
						if (l3 >= 0)
							class11 = aclass11[l3];
						try {
							renderObject(k3, worldController, class11, l2, j2, j3, l, i3);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} while (true);
			} while (true);
		}
	}

	private final int[] hues;
	private final int[] saturations;
	private final int[] luminances;
	private final int[] chromas;
	private final int[] anIntArray128;
	private final int[][][] tileHeights;
	private final byte[][][] overlays;
	public static int currentHeight;
	private final byte[][][] shading;
	private final int[][][] anIntArrayArrayArray135;
	private final byte[][][] overlayTypes;
	private static final int anIntArray137[] = { 1, 0, -1, 0 };
	private final int[][] tileLighting;
	private static final int anIntArray140[] = { 16, 32, 64, 128 };
	private final byte[][][] underlays;
	private static final int anIntArray144[] = { 0, -1, 0, 1 };
	public static int maximumPlane = 99;
	private final int regionSizeX;
	private final int regionSizeY;
	private final byte[][][] overlayOrientations;
	private final byte[][][] tileFlags;
	public static boolean lowMem = true;
	private static final int anIntArray152[] = { 1, 2, 4, 8 };
	public static final int BRIDGE_TILE = 2;
	private static final int FORCE_LOWEST_PLANE = 8;
}