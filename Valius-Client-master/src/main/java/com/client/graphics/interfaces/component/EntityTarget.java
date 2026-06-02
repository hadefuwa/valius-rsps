package com.client.graphics.interfaces.component;

import com.client.Client;
import com.client.cache.graphics.font.RSFont;
import com.client.config.Configuration;
import com.client.draw.raster.Raster;
import com.client.model.entity.Entity;
import com.client.model.entity.impl.NPC;
import com.client.model.entity.impl.Player;

public class EntityTarget {

	private byte state;

	private RSFont rsFont;

	private final Client client = Client.getInstance();

	private Entity target;

	public EntityTarget(byte state, short entityIndex, short currentHealth, short maximumHealth, RSFont rsFont) {
		this.state = state;
		this.rsFont = rsFont;
		if (state > 0 && state < 3) {
			if (state == 1) {
				target = client.npcArray[entityIndex];
			} else if (state == 2) {
				target = client.playerArray[entityIndex];
			}
			if (target != null) {
				target.currentHealth = currentHealth;
				target.maxHealth = maximumHealth;
			}
		}
	}

	public void draw() {
		if (state <= 0 || state > 2 || target == null) {
			return;
		}
		String name = "Unknown";

		if (state == 1) {
			NPC npc = (NPC) target;
			if (npc.desc != null) {
				name = npc.desc.name;
			}
		} else if (state == 2) {
			name = ((Player) target).name;
		} else {
			state = 0;
			return;
		}
		int offset = 4;
		if (Client.counterOn && Configuration.xpPosition == 2) {
			offset = 31;
		}
		int width = 134;
		int xPos = 6;
		int yPos = 22 + offset;

		Raster.drawBoxOutline(xPos, yPos, width - 3, 34, 0x393022);
		Raster.drawAlphaBox(xPos, yPos, width - 3, 33, 0x60574E, 110);
		Client.latoBold.drawCenteredString(name, xPos + (width / 2) - 2, yPos + 18, 16777215, 0x000000);
		int barWidth = 124;
		int fillPercentage = target.currentHealth * barWidth / target.maxHealth;
		Raster.drawAlphaBox(xPos + 3, yPos + 18, width - 9, 13, 11740160, 160);
		Raster.drawAlphaBox(xPos + 3, yPos + 18, fillPercentage, 13, 0x00A900, 160);
		Client.latoBold.drawCenteredString(target.currentHealth + " / " + target.maxHealth, xPos + (width / 2) - 2, yPos + 33,
				16777215, 0x000000);
	}

	public void stop() {
		state = 0;
	}

}