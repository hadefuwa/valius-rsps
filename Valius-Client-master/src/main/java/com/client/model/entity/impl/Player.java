package com.client.model.entity.impl;

import java.util.Arrays;

import org.apache.commons.lang3.RandomUtils;

import com.client.Client;
import com.client.cache.animation.Frame;
import com.client.cache.definitions.AnimationDefinition;
import com.client.cache.definitions.GraphicsDefinition;
import com.client.cache.definitions.IDK;
import com.client.cache.definitions.ItemDefinition;
import com.client.cache.definitions.NpcDefinition;
import com.client.collection.Cache;
import com.client.io.Buffer;
import com.client.model.Model;
import com.client.model.entity.Entity;
import com.client.utilities.TextClass;

public final class Player extends Entity {

	@Override
	public Model getRotatedModel() {
		if (!visible)
			return null;
		Model model = method452();
		if (model == null)
			return null;
		super.height = model.modelHeight;
		model.aBoolean1659 = true;
		if (aBoolean1699)
			return model;

		if(super.anInt1520 >= GraphicsDefinition.length())
			super.anInt1520 = -1;
		
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			GraphicsDefinition spotAnim = GraphicsDefinition.forId(super.anInt1520);
			if(spotAnim != null) {
				Model model_2 = spotAnim.getModel();
				if (model_2 != null) {
					Model model_3 = new Model(true, Frame.method532(super.anInt1521), false, model_2);
					model_3.method475(0, -super.anInt1524, 0);
					model_3.method469();
					model_3.method470(spotAnim.animation.primaryFrames[super.anInt1521]);
					model_3.faceGroups = null;
					model_3.vertexGroups = null;
					if (spotAnim.breadthScale != 128 || spotAnim.depthScale != 128)
						model_3.method478(spotAnim.breadthScale, spotAnim.breadthScale,
								spotAnim.depthScale);
					// model_3.method479(64 + spotAnim.anInt413, 850 +
					// spotAnim.anInt414, -30, -50, -30, true);
					model_3.method479(84 + spotAnim.ambience,
							1550 + spotAnim.modelShadow, -50, -110, -50, true);
					Model aclass30_sub2_sub4_sub6_1s[] = { model, model_3 };
					model = new Model(aclass30_sub2_sub4_sub6_1s);
				}
			}
		}
		if (aModel_1714 != null) {
			if (Client.loopCycle >= anInt1708)
				aModel_1714 = null;
			if (Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708) {
				Model model_1 = aModel_1714;
				model_1.method475(anInt1711 - super.x, anInt1712 - anInt1709,
						anInt1713 - super.y);
				if (super.turnDirection == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536)
					model_1.method473();
				Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
				model = new Model(aclass30_sub2_sub4_sub6s);
				if (super.turnDirection == 512)
					model_1.method473();
				else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.method475(super.x - anInt1711, anInt1709 - anInt1712,
						super.y - anInt1713);
			}
		}
		model.aBoolean1659 = true;
		return model;
	}

	public String title;
	public String titleColor;
	
	public long groupIMLeader = 0;

	public void updatePlayer(Buffer stream) {
		stream.position = 0;

		colourFind = new int[12][];
		colourReplace = new int[12][];
		gender = stream.readUnsignedByte();
		title = stream.readString();
		titleColor = stream.readString();
		healthState = stream.readUnsignedByte();
		headIcon = stream.readUnsignedByte();
		skullIcon = stream.readUnsignedByte();
		desc = null;
		team = 0;
		for (int j = 0; j < 12; j++) {
			int k = stream.readUnsignedByte();
			if (k == 0) {
				equipment[j] = 0;
				continue;
			}
			int i1 = stream.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if (j == 0 && equipment[0] == 65535) {
				desc = NpcDefinition.forID(stream.readShort());
				break;
			}

		
			if (equipment[j] >= 512 && equipment[j] - 512 < ItemDefinition.totalItems) {
				int l1 = ItemDefinition.forID(equipment[j] - 512).team;
				if (l1 != 0)
					team = l1;
			}
		}

		/*if(desc == null)
		for (int j = 0; j < 12; j++) {
			int len = stream.readUnsignedByte();
			boolean hasColors = len > 0;
			System.out.println("INDEX " + j + (hasColors ? " has" : " has not") + " got additonal vars");
			if(hasColors) {
				
				System.out.println("len " + len + " for " + j);
				colourFind[j] = new int[len];
				colourReplace[j] = new int[len];
				for(int i = 0;i<len;i++) {
					colourFind[j][i] = stream.readShort();
					colourReplace[j][i] = stream.readShort();
					System.out.println("colours " + colourFind[j][i] + " : " + colourReplace[j][i]);
					
				}
			}
		}*/
		
		for (int l = 0; l < 5; l++) {
			int j1 = stream.readUnsignedByte();
			if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
				j1 = 0;
			bodyColours[l] = j1;
		}

		super.standAnim = stream.readShort();
		if (super.standAnim == 65535)
			super.standAnim = -1;
		super.turnAnim = stream.readShort();
		if (super.turnAnim == 65535)
			super.turnAnim = -1;
		super.walkAnim = stream.readShort();
		if (super.walkAnim == 65535)
			super.walkAnim = -1;
		super.turn180Anim = stream.readShort();
		if (super.turn180Anim == 65535)
			super.turn180Anim = -1;
		super.turn90CWAnim = stream.readShort();
		if (super.turn90CWAnim == 65535)
			super.turn90CWAnim = -1;
		super.turn90ACWAnim = stream.readShort();
		if (super.turn90ACWAnim == 65535)
			super.turn90ACWAnim = -1;
		super.runAnim = stream.readShort();
		if (super.runAnim == 65535)
			super.runAnim = -1;
		if(desc != null) {
			super.size = desc.size;
			super.degressToTurn = desc.getDegreesToTurn;
			super.walkAnim = desc.walkAnim;
			super.runAnim = desc.walkAnim;
			super.turn180Anim = desc.turn180Anim;
			super.turn90CWAnim = desc.turn90CWAnim;
			super.turn90ACWAnim = desc.turn90ACWAnim;
			super.standAnim = desc.standAnim;
		} else {
			super.degressToTurn = 32;
			super.size = 1;
		}
		name = TextClass.fixName(TextClass.nameForLong(stream.readLong()));
		visible = stream.readUnsignedByte() == 0 ? true : false;
		combatLevel = stream.readUnsignedByte();
		rights = stream.readUnsignedByte();
		skill = stream.readShort();
		groupIMLeader = stream.readLong();
		aLong1718 = 0L;
		boolean capeOverride = stream.readUnsignedByte() == 1;
		if(capeOverride) {
			colourFind[1] = new int[4];
			colourReplace[1] = new int[4];
			
			colourFind[1][0] = stream.readInt();
			colourFind[1][1] = stream.readInt();
			colourFind[1][2] = stream.readInt();
			colourFind[1][3] = stream.readInt();

			colourReplace[1][0] = stream.readInt();
			colourReplace[1][1] = stream.readInt();
			colourReplace[1][2] = stream.readInt();
			colourReplace[1][3] = stream.readInt();
			aLong1718 = RandomUtils.nextLong();
			
		} else {
			colourFind[1] = null;
			colourReplace[1] = null;
		}
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (equipment[k1] >= 256)
				aLong1718 += equipment[k1] - 256;
			if(colourFind[k1] != null) {
				for(int i = 0;i<colourFind[k1].length;i++)
					aLong1718 += colourReplace[k1][i];
			}
		}

		if (equipment[0] >= 256)
			aLong1718 += equipment[0] - 256 >> 4;
		if (equipment[1] >= 256)
			aLong1718 += equipment[1] - 256 >> 8;
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += bodyColours[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += gender;
		//aLong1718 = RandomUtils.nextLong();
	}

	public Model method452() {
		if (desc != null) {
			int j = -1;
			if (super.anim >= 0 && super.anInt1529 == 0)
				j = AnimationDefinition.anims[super.anim].primaryFrames[super.anInt1527];
			else if (super.anInt1517 >= 0)
				j = AnimationDefinition.anims[super.anInt1517].primaryFrames[super.anInt1518];
			Model model = desc.method164(-1, j, null);
			return model;
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if (super.anim >= 0 && super.anInt1529 == 0) {
			AnimationDefinition animation = AnimationDefinition.anims[super.anim];
			k = animation.primaryFrames[super.anInt1527];
			if (super.anInt1517 >= 0 && super.anInt1517 != super.standAnim)
				i1 = AnimationDefinition.anims[super.anInt1517].primaryFrames[super.anInt1518];
			if (animation.playerOffhand >= 0) {
				j1 = animation.playerOffhand;
				l += j1 - equipment[5] << 40;
			}
			if (animation.playerMainhand >= 0) {
				k1 = animation.playerMainhand;
				l += k1 - equipment[3] << 48;
			}
		} else if (super.anInt1517 >= 0)
			k = AnimationDefinition.anims[super.anInt1517].primaryFrames[super.anInt1518];
		Model model_1 = (Model) mruNodes.insertFromCache(l);
		if (model_1 == null) {
			boolean flag = false;
			for (int i2 = 0; i2 < 12; i2++) {
				int k2 = equipment[i2];
				if (k1 >= 0 && i2 == 3)
					k2 = k1;
				if (j1 >= 0 && i2 == 5)
					k2 = j1;
				if (k2 >= 256 && k2 < 512 && !IDK.cache[k2 - 256].method537())
					flag = true;
				if (k2 >= 512 && !ItemDefinition.forID(k2 - 512).method195(gender))
					flag = true;
			}

			if (flag) {
				if (aLong1697 != -1L)
					model_1 = (Model) mruNodes.insertFromCache(aLong1697);
				if (model_1 == null)
					return null;
			}
		}
		if (model_1 == null) {
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];
			int j2 = 0;
			for (int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if (k1 >= 0 && l2 == 3)
					i3 = k1;
				if (j1 >= 0 && l2 == 5)
					i3 = j1;
				if (i3 >= 256 && i3 < 512) {
					Model model_3 = IDK.cache[i3 - 256].method538();
					if (model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if (i3 >= 512) {
					Model model_4 = ItemDefinition.forID(i3 - 512)
							.method196(gender);
					if (model_4 != null) {
						if(colourFind[l2] != null) {
							Model model_5 = new Model(1, new Model[] {model_4});
							for(int i = 0;i<colourFind[l2].length;i++)
								model_5.method476(colourFind[l2][i], colourReplace[l2][i]);
							aclass30_sub2_sub4_sub6s[j2++] = model_5;
						} else {
							aclass30_sub2_sub4_sub6s[j2++] = model_4;
						}
					}
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for (int j3 = 0; j3 < 5; j3++) {
				
				if (this.bodyColours[j3] < Client.field604[j3].length) {
					model_1.replaceColor(Client.field309[j3],
						Client.field604[j3][bodyColours[j3]]);
				}
				if (bodyColours[j3] != 0) {
					model_1.replaceColor(Client.anIntArrayArray1003[j3][0],
							Client.anIntArrayArray1003[j3][bodyColours[j3]]);
					if (j3 == 1)
						model_1.replaceColor(Client.anIntArray1204[0],
								Client.anIntArray1204[bodyColours[j3]]);

				}
			}

			model_1.method469();
			 model_1.method479(64, 850, -30, -50, -30, true);
			//model_1.method479(84, 1000, -90, -580, -90, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if (aBoolean1699)
			return model_1;
		Model model_2 = Model.EMPTY_MODEL;
		model_2.method464(model_1, Frame.method532(k) & Frame.method532(i1));
		if (k != -1 && i1 != -1)
			model_2.method471(AnimationDefinition.anims[super.anim].interleaveOrder, i1, k);
		else if (k != -1)
			model_2.method470(k);
		model_2.method466();
		model_2.faceGroups = null;
		model_2.vertexGroups = null;
		return model_2;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public int privelage;

	public Model method453() {
		if (!visible)
			return null;
		if (desc != null)
			return desc.method160();
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = equipment[i];
			if (j >= 256 && j < 512 && !IDK.cache[j - 256].method539())
				flag = true;
			if (j >= 512 && !ItemDefinition.forID(j - 512).method192(gender))
				flag = true;
		}

		if (flag)
			return null;
		Model aclass30_sub2_sub4_sub6s[] = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = equipment[l];
			if (i1 >= 256 && i1 < 512) {
				Model model_1 = IDK.cache[i1 - 256].method540();
				if (model_1 != null)
					aclass30_sub2_sub4_sub6s[k++] = model_1;
			}
			if (i1 >= 512) {
				Model model_2 = ItemDefinition.forID(i1 - 512).method194(gender);
				if (model_2 != null) {
					if (colourFind[l] != null) {
						Model model_5 = new Model(1, new Model[] { model_2 });
						for (int i = 0; i < colourFind[l].length; i++)
							model_5.method476(colourFind[l][i], colourReplace[l][i]);
						aclass30_sub2_sub4_sub6s[k++] = model_5;
					} else {
						aclass30_sub2_sub4_sub6s[k++] = model_2;
					}
				}
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for (int j1 = 0; j1 < 5; j1++) {

			if (this.bodyColours[j1] < Client.field604[j1].length) {
				model.replaceColor(Client.field309[j1], Client.field604[j1][bodyColours[j1]]);
			}
			if (bodyColours[j1] != 0) {
				model.replaceColor(Client.anIntArrayArray1003[j1][0],
						Client.anIntArrayArray1003[j1][bodyColours[j1]]);
				if (j1 == 1)
					model.replaceColor(Client.anIntArray1204[0],
							Client.anIntArray1204[bodyColours[j1]]);
			}

		}

		return model;
	}

	public Player() {
		aLong1697 = -1L;
		aBoolean1699 = false;
		bodyColours = new int[5];
		visible = false;
		equipment = new int[12];
	}
	
	public int getRights() {
		return rights;
	}
	
	public int getHealthState() {
		return healthState;
	}

	private int rights;
	private long aLong1697;
	public boolean aBoolean1699;
	public final int[] bodyColours;
	public int team;
	public int gender;
	public String name;
	public static Cache mruNodes = new Cache(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int anInt1707;
	public int anInt1708;
	public int anInt1709;
	public boolean visible;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public Model aModel_1714;
	public final int[] equipment;
	public int[][] colourFind = new int[12][];
	public int[][] colourReplace = new int[12][];
	private long aLong1718;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public int skill;
	private int healthState;
	

	public void playSoundEffect(int soundEffect, int delay) {
		// TODO Auto-generated method stub
		
	}

}
