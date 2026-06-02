package com.client.graphics.interfaces.impl;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.client.Client;
import com.client.config.Configuration;
import com.client.features.settings.InformationFile;
import com.client.graphics.interfaces.RSInterface;

public enum Dropdown {

	XP_POSITION() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpPosition = selected;
		}
	},
	
	XP_SIZE() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpSize = selected;
		}
	},
	
	XP_SPEED() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpSpeed = selected;
		}
	},
	
	XP_DURATION() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpDuration = selected;
		}
	},
	
	XP_COLOUR() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpColour = selected;
		}
	},
	
	XP_GROUP() {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.xpGroup = selected;
		}
	},

	KEYBIND_SELECTION() {
		@Override
		public void selectOption(int selected, RSInterface dropdown) {
			Keybinding.bind((dropdown.id - Keybinding.MIN_FRAME) / 3, selected);
		}
	},

	PLAYER_ATTACK_OPTION_PRIORITY(SettingsWidget.PLAYER_ATTACK_DROPDOWN) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.playerAttackOptionPriority = selected;
			Client.informationFile.write();
		}

		@Override
		public void updateOption() {
			setIndex(Configuration.playerAttackOptionPriority);
		}
	},

	OLD_GAMEFRAME(39011) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Client.oldGameframe = selected == 0;
			Client.instance.loadTabArea();
			Client.instance.drawTabArea();
		}
		@Override
		public void updateOption() {
			setOnOff(Client.oldGameframe);
		}
	},

	GAME_TIMERS(39012) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Client.gameTimers = selected == 0;
		}
		@Override
		public void updateOption() {
			setOnOff(Client.gameTimers);
		}
	},

	ANTI_ALIASING(39013) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.enableAntiAliasing = selected == 0;
		}
		@Override
		public void updateOption() {
			setOnOff(Configuration.enableAntiAliasing);
		}
	},

	GROUND_ITEM_NAMES(39014) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Client.groundItemsOn = selected == 0;
		}

		@Override
		public void updateOption() {
			setOnOff(Client.groundItemsOn);
		}
	},

	FOG(39015) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			switch(selected){
				case 0://false
                    Configuration.enableFogRendering = false;
                    Configuration.enableRainbowFog= false;
					return;
				case 1://grey
                    Configuration.fogColor=0xDCDBDF;
                    Configuration.enableFogRendering = true;
                    Configuration.enableRainbowFog= false;
					return;

                case 2: //Sisle
                    Configuration.fogColor=0xC8C0A8;
                    Configuration.enableFogRendering = true;
                    Configuration.enableRainbowFog= false;
                    return;

                case 3: //dark
                    Configuration.fogColor=0x0e0d0b;
                    Configuration.enableFogRendering = true;
                    Configuration.enableRainbowFog= false;

                    return;
                case 4://marroon
                    Configuration.fogColor= 0x800000;
                    Configuration.enableFogRendering = true;
                    Configuration.enableRainbowFog= false;
                    return;
                case 5://rainbow
                    Configuration.enableFogRendering = true;
                    Configuration.enableRainbowFog= true;
                    Client.instance.pushMessage("Please do ::fogdelay to add a timer to the fog!", 0,"");
					Client.instance.pushMessage("@red@ Warning this could give you seizures! Use at an extreme caution! Valius not responsible! LoL", 0,"");
                return;

			}

		
		}
		@Override
		public void updateOption() {
			if(!Configuration.enableFogRendering) {
				setIndex(0);
			}
			if(Configuration.enableRainbowFog) {
				setIndex(5);
			}

			setIndexOnValues(Configuration.fogColor, 0xDCDBDF, 0xC8C0A8, 0x0e0d0b, 0x800000);
			
		}
	},

	SMOOTH_SHADING(39016) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.enableSmoothShading = selected == 0;
		}

		@Override
		public void updateOption() {
			setOnOff(Configuration.enableSmoothShading);
		}
	},

	TILE_BLENDING(39017) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.enableTileBlending = selected == 0;
		}

		@Override
		public void updateOption() {
			setOnOff(Configuration.enableTileBlending);
		}
		
	},

	INVENTORY_CONTEXT_MENU(39018) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			switch(selected){
				case 0: //off
					Configuration.inventoryContextMenu=false;
					Configuration.statMenuColor=0xFFFFFF;
					return;
				case 1: //magenta
					Configuration.inventoryContextMenu=true;
					Configuration.statMenuColor=0xFF00FF;
					return;
				case 2://lime
					Configuration.inventoryContextMenu=true;
					Configuration.statMenuColor=0x00FF00;
					return;
				case 3://cyan
					Configuration.inventoryContextMenu=true;
					Configuration.statMenuColor=0x00FFFF;
					return;
				case 4://red
					Configuration.inventoryContextMenu=true;
					Configuration.statMenuColor=0xFF0000;
					return;

			}
			
		}
		@Override
		public void updateOption() {
			if(!Configuration.inventoryContextMenu) {
				setIndex(0);
				return;
			}
			setIndexOnValues(Configuration.statMenuColor, 0xFF00FF, 0x00FF00, 0x00FFFF, 0xFF0000);
		}
	},
	CHAT_EFFECT(39027) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.chatColor = selected;
		}
		@Override
		public void updateOption() {
			setIndex(Configuration.chatColor);
		}
		
	},
	BOUNTY_HUNTER(39025) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.bountyHunter = selected == 0;
		}
		
		@Override
		public void updateOption() {
			setOnOff(Configuration.bountyHunter);
		}
	},
	TARGET_INTERFACE(39026) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Client.showEntityTarget = selected == 0;
		
		}
		
		@Override
		public void updateOption() {
			setOnOff(Client.showEntityTarget);
		}
	},

	NPC_ATTACK_OPTION_PRIORITY(SettingsWidget.NPC_ATTACK_DROPDOWN) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.npcAttackOptionPriority = selected;
			Client.informationFile.write();
		}
		

		@Override
		public void updateOption() {
			setIndex(Configuration.npcAttackOptionPriority);
		}
		
	}, ENTITY_TEXTSPRITE(39032) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.displayEntityOverlaySpriteText = selected == 0 ? true : false;
		}
		@Override
		public void updateOption() {
			setOnOff(Configuration.displayEntityOverlaySpriteText);
		}
	}, OBJECT_TEXTSPRITE(39031) {
		@Override
		public void selectOption(int selected, RSInterface r) {
			Configuration.displayObjectOverlaySpriteText = selected == 0 ? true : false;
		
		}
		@Override
		public void updateOption() {
			setOnOff(Configuration.displayObjectOverlaySpriteText);
		}
	}
	;

	protected int dropdownWidget;
	
	private Dropdown() {
	}
	
	private Dropdown(int dropdownWidget) {
		this.dropdownWidget = dropdownWidget;
	}

	public abstract void selectOption(int selected, RSInterface r);
	
	public void updateOption() {
		
	}
	
	public void setOnOff(boolean bool) {
		RSInterface.interfaceCache[dropdownWidget].dropdown.setSelected(bool ? "On" : "Off");
	}
	
	public void setIndex(int index) {
		DropdownMenu menu = RSInterface.interfaceCache[dropdownWidget].dropdown;
		menu.setSelected(menu.getOptions()[index]);
	}
	
	
	public void setIndexOnValues(int toCheck, int... values) {
		OptionalInt indexOpt = IntStream.range(0, values.length).filter(value -> value == toCheck).findFirst();
		indexOpt.ifPresent(index -> setIndex(index));
	}

	public static void updateSelections() {
		Arrays.stream(Dropdown.values())
		.filter(dropdown -> dropdown.dropdownWidget != 0)
		.forEach(dropdown -> dropdown.updateOption());
	}
}

