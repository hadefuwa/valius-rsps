package com.client.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.client.Client;
import com.client.graphics.interfaces.impl.Keybinding;

public class GameKeyListener implements KeyListener {

	private final Client client;

	public GameKeyListener(Client client) {
		this.client = client;
	}
	
	


	@Override
	public void keyPressed(KeyEvent keyevent) {
	
		client.idleTime = 0;
		int i = keyevent.getKeyCode();
		int j = keyevent.getKeyChar();

		if (keyevent.isShiftDown()) {
			Client.shiftDown = true;
		}
		if(keyevent.isControlDown() && keyevent.getKeyCode() == 192) {
			client.consoleOpen = !client.consoleOpen;
			return;
		}
		
		if(client.consoleOpen) {
			client.parseKeyForConsole(keyevent);
			return;
		}
		if (i == 32) {
			if (Client.backDialogID == 979 || Client.backDialogID == 968 || Client.backDialogID == 973
					|| Client.backDialogID == 986 || Client.backDialogID == 306 || Client.backDialogID == 4887
					|| Client.backDialogID == 4893 || Client.backDialogID == 356 || Client.backDialogID == 310
					|| Client.backDialogID == 4882 || Client.backDialogID == 4900) {
				Client.stream.createFrame(40);
				Client.stream.writeShort(4892);
				// Client.setDialogueOptionsShowing(true);
			}
		}
		if (i == KeyEvent.VK_ESCAPE) {
			Client.closeInterface();
		}
		// if (i == KeyEvent.VK_SPACE) {
		// Client
		// }
		
		if(Client.myPlayer != null && Client.myPlayer.getRights() > 2) {
			if(i == KeyEvent.VK_PAGE_UP)
				Client.inputString = Client.lastCommand;
			else if(i == KeyEvent.VK_PAGE_DOWN)
				Client.inputString = "";
		}

		if (keyevent.isControlDown()) {
			if (i == KeyEvent.VK_SPACE) {
				Client.continueDialogue();
			}
			if (i == KeyEvent.VK_1 || i == KeyEvent.VK_NUMPAD1) {
				Client.dialogueOptions("one");
			}
			if (i == KeyEvent.VK_2 || i == KeyEvent.VK_NUMPAD2) {
				Client.dialogueOptions("two");
			}
			if (i == KeyEvent.VK_3 || i == KeyEvent.VK_NUMPAD3) {
				Client.dialogueOptions("three");
			}
			if (i == KeyEvent.VK_4 || i == KeyEvent.VK_NUMPAD4) {
				Client.dialogueOptions("four");
			}
			if (i == KeyEvent.VK_5 || i == KeyEvent.VK_NUMPAD5) {
				Client.dialogueOptions("five");
			}
			switch (i) {
			case KeyEvent.VK_V:
				Client.inputString += Client.getClipboardContents();
				Client.inputTaken = true;
				break;

			case KeyEvent.VK_T:
				Client.teleportInterface();
				break;

			}
		}

		if (i == KeyEvent.VK_F1) {
			Keybinding.isBound(KeyEvent.VK_F1);
		} else if (i == KeyEvent.VK_F2) {
			Keybinding.isBound(KeyEvent.VK_F2);
		} else if (i == KeyEvent.VK_F3) {
			Keybinding.isBound(KeyEvent.VK_F3);
		} else if (i == KeyEvent.VK_F4) {
			Keybinding.isBound(KeyEvent.VK_F4);
		} else if (i == KeyEvent.VK_F5) {
			Keybinding.isBound(KeyEvent.VK_F5);
		} else if (i == KeyEvent.VK_F6) {
			Keybinding.isBound(KeyEvent.VK_F6);
		} else if (i == KeyEvent.VK_F7) {
			Keybinding.isBound(KeyEvent.VK_F7);
		} else if (i == KeyEvent.VK_F8) {
			Keybinding.isBound(KeyEvent.VK_F8);
		} else if (i == KeyEvent.VK_F9) {
			Keybinding.isBound(KeyEvent.VK_F9);
		} else if (i == KeyEvent.VK_F10) {
			Keybinding.isBound(KeyEvent.VK_F10);
		} else if (i == KeyEvent.VK_F11) {
			Keybinding.isBound(KeyEvent.VK_F11);
		} else if (i == KeyEvent.VK_F12) {
			Keybinding.isBound(KeyEvent.VK_F12);
		}
		/**
		if (hotKeyToggle == true) {
			if (i == KeyEvent.VK_F5) {
				Client.setTab(0);
			} else if (i == KeyEvent.VK_F11) {
				Client.setTab(1);
			} else if (i == KeyEvent.VK_F12) {
				Client.setTab(2);
			} else if (i == KeyEvent.VK_F1) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F2) {
				Client.setTab(4);
			} else if (i == KeyEvent.VK_F3) {
				Client.setTab(5);
			} else if (i == KeyEvent.VK_F4) {
				Client.setTab(6);
			} else if (i == KeyEvent.VK_F8) {
				Client.setTab(7);
			} else if (i == KeyEvent.VK_F9) {
				Client.setTab(8);
			} else if (i == KeyEvent.VK_F10) {
				Client.setTab(9);
			} else if (i == KeyEvent.VK_F11) {
				Client.setTab(10);
			} else if (i == KeyEvent.VK_F12) {
				Client.setTab(11);
			}
		} else {
			if (i == KeyEvent.VK_F1) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F2) {
				Client.setTab(1);
			} else if (i == KeyEvent.VK_F3) {
				Client.setTab(2);
			} else if (i == KeyEvent.VK_F4) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F5) {
				Client.setTab(4);
			} else if (i == KeyEvent.VK_F6) {
				Client.setTab(5);
			} else if (i == KeyEvent.VK_F7) {
				Client.setTab(6);
			} else if (i == KeyEvent.VK_F8) {
				Client.setTab(7);
			} else if (i == KeyEvent.VK_F9) {
				Client.setTab(8);
			} else if (i == KeyEvent.VK_F10) {
				Client.setTab(9);
			} else if (i == KeyEvent.VK_F11) {
				Client.setTab(10);
			} else if (i == KeyEvent.VK_F12) {
				Client.setTab(11);
			}
		}
		 **/
		if (keyevent.isControlDown()) {
			Client.controlIsDown = true;
		}
		if (j < 30)
			j = 0;
		if (i == 37)
			j = 1;
		if (i == 39)
			j = 2;
		if (i == 38)
			j = 3;
		if (i == 40)
			j = 4;
		if (i == 17)
			j = 5;
		if (i == 8)
			j = 8;
		if (i == 127)
			j = 8;
		if (i == 9)
			j = 9;
		if (i == 10)
			j = 10;
		if (i >= 112 && i <= 123)
			j = (1008 + i) - 112;
		if (i == 36)
			j = 1000;
		if (i == 35)
			j = 1001;
		if (i == 33)
			j = 1002;
		if (i == 34)
			j = 1003;
		if (j > 0 && j < 128)
			client.keyArray[j] = 1;
		if (j > 4) {
			client.charQueue[client.writeIndex] = j;
			client.writeIndex = client.writeIndex + 1 & 0x7f;
		}
	}

	@Override
	public final void keyReleased(KeyEvent keyevent) {
		client.idleTime = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (c < '\036')
			c = '\0';
		if (i == 37)
			c = '\001';
		if (i == 39)
			c = '\002';
		if (i == 38)
			c = '\003';
		if (i == 40)
			c = '\004';
		if (i == 17)
			c = '\005';
		if (i == 8)
			c = '\b';
		if (i == 127)
			c = '\b';
		if (i == 9)
			c = '\t';
		if (i == 10)
			c = '\n';
		if (i == KeyEvent.VK_SHIFT) {
			Client.shiftDown = false;
		}
		if (i == KeyEvent.VK_CONTROL) {
			Client.controlIsDown = false;
		}
		if (c > 0 && c < '\200')
			client.keyArray[c] = 0;
	}




	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
