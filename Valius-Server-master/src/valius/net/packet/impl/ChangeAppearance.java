package valius.net.packet.impl;

import valius.cache.definitions.IdentityKit;
import valius.model.entity.player.Player;
import valius.net.packet.PacketType;

/**
 * Change appearance
 **/
public class ChangeAppearance implements PacketType {


	private static final int[][] MALE_VALUES = { 
			{ 0, 8 }, // head
			{ 10, 17 }, // jaw
			{ 18, 25 }, // torso
			{ 26, 31 }, // arms
			{ 33, 34 }, // hands
			{ 36, 40 }, // legs
			{ 42, 43 }, // feet
	};

	private static final int[][] FEMALE_VALUES = { 
			{ 45, 54 }, // head
			{ -1, -1 }, // jaw
			{ 56, 60 }, // torso
			{ 61, 65 }, // arms
			{ 67, 68 }, // hands
			{ 70, 77 }, // legs
			{ 79, 80 }, // feet
	};

	private static final int[][] ALLOWED_COLORS = { 
			{ 0, 24 }, // hair color
			{ 0, 28 }, // torso color
			{ 0, 28 }, // legs color
			{ 0, 5 }, // feet color
			{ 0, 10 } // skin color
	};

	@Override
	public void processPacket(final Player c, final int packetType, final int packetSize) {
		final int gender = c.getInStream().readSignedByte();
		if (!c.canChangeAppearance)
			return;
		if (gender != 0 && gender != 1)
			return;

		final int[] apperances = new int[MALE_VALUES.length]; // apperance's
																// value
		// check
		for (int bodyPart = 0; bodyPart < apperances.length; bodyPart++) {
			int actualBodyPart = bodyPart;
			if(gender == 1)
				actualBodyPart += 7;
			int value = c.getInStream().readUnsignedByte();
			if(IdentityKit.isValid(actualBodyPart, value)) {
				apperances[bodyPart] = value;
			} else {
				final int currentBodyPart = bodyPart;
				IdentityKit.getDefault(actualBodyPart).ifPresent(idk -> {
					apperances[currentBodyPart] = idk.id;
				});
			}
		}

		final int[] colors = new int[ALLOWED_COLORS.length]; // color value
																// check
		for (int i = 0; i < colors.length; i++) {
			int value = c.getInStream().readSignedByte();
			if (value < ALLOWED_COLORS[i][0] || value > ALLOWED_COLORS[i][1])
				value = ALLOWED_COLORS[i][0];
			colors[i] = value;
		}

		if (c.canChangeAppearance) {
			
			c.playerAppearance[0] = gender; // gender
			//c.playerAppearance[1] = apperances[0]; // head
			//c.playerAppearance[2] = apperances[2]; // torso
			//c.playerAppearance[3] = apperances[3]; // arms
			//c.playerAppearance[4] = apperances[4]; // hands
			//c.playerAppearance[5] = apperances[5]; // legs
			c.playerAppearance[6] = apperances[6]; // feet
			c.playerAppearance[7] = apperances[1]; // beard
			c.playerAppearance[8] = colors[0]; // hair colour
			c.playerAppearance[9] = colors[1]; // torso colour
			c.playerAppearance[10] = colors[2]; // legs colour
			c.playerAppearance[11] = colors[3]; // feet colour
			
			if(apperances[0] < 0) // head
				c.playerAppearance[1] = apperances[0] + 256;
			else
				c.playerAppearance[1] = apperances[0];
			if(apperances[2] < 0)
				c.playerAppearance[2] = apperances[2] + 256;
			else
				c.playerAppearance[2] = apperances[2];
			if(apperances[3] < 0)
				c.playerAppearance[3] = apperances[3] + 256;
			else
				c.playerAppearance[3] = apperances[3];
			if(apperances[4] < 0)
				c.playerAppearance[4] = apperances[4] + 256;
			else
				c.playerAppearance[4] = apperances[4];
			if(apperances[5] < 0)
				c.playerAppearance[5] = apperances[5] + 256;
			else
				c.playerAppearance[5] = apperances[5];
			
			if (colors[4] == 8 || colors[4] == 9 || colors[4] == 10) {
				//if (c.getHolidayStages().getStage("Halloween") < 6) {
				if (c.amDonated < 10) {
					//c.sendMessage("Only those whom entered at the dark times of halloween may use this skin.");
					c.sendMessage("You must be a donator to use these skin colors.");
					return;
				} else {
					c.playerAppearance[12] = colors[4]; // skin colour
				}
			} else {
				c.playerAppearance[12] = colors[4]; // skin colour
			}

			c.getPA().removeAllWindows();
			c.getPA().requestUpdates();
			c.canChangeAppearance = false;
		}
	}

}