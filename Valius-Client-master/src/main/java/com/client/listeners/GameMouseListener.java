package com.client.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

import com.client.Client;
import com.client.features.gameframe.ScreenMode;
import com.client.graphics.interfaces.RSInterface;
import com.client.graphics.interfaces.impl.ColorPicker;
import com.client.graphics.interfaces.impl.SettingsWidget;
import com.client.graphics.interfaces.impl.Slider;

public class GameMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int TOOLBAR_HEIGHT = 0;

	private final Client client;
	
	public GameMouseListener(Client client) {
		this.client = client;
	}
	
	@Override
	public final void mouseDragged(MouseEvent mouseevent) {
		int i = mouseevent.getX();
		int j = mouseevent.getY() - TOOLBAR_HEIGHT;
		if (System.currentTimeMillis() - client.clickTime >= 250L || Math.abs(client.saveClickX - i) > 5
				|| Math.abs(client.saveClickY - j) > 5) {
			client.idleTime = 5;
			client.mouseX = i;
			client.mouseY = j;
		}
		if (client.mouseWheelDown) {
			int k2 = client.mouseWheelX - i;
			int k = client.mouseWheelY - j;
			client.mouseWheelDragged(k2, -k);
			client.mouseWheelX = i;
			client.mouseWheelY = j;
			return;
		}
		if (client.clickType != 0) {
			client.sliderShowAlpha = true;
		}
		client.clickType = client.DRAG;
		Slider.handleSlider(i, j);
		ColorPicker.handleClick(i, j, true);
	}

	@Override
	public void mouseMoved(MouseEvent mouseevent) {
		// if(idleTime >= (Client.IDLE_TIME - 500)) {
		// Client.mouseMoved();
		// }
		int i = mouseevent.getX();
		int j = mouseevent.getY()- TOOLBAR_HEIGHT;
		if (System.currentTimeMillis() - client.clickTime >= 250L || Math.abs(client.saveClickX - i) > 5
				|| Math.abs(client.saveClickY - j) > 5) {
			client.idleTime = 3;
			client.mouseX = i;
			client.mouseY = j;
		}
		client.clickType = client.MOVE;
	}
	@Override
	public final void mousePressed(MouseEvent mouseevent) {
		mouseevent.getComponent().requestFocus();
		int i = mouseevent.getX();
		int j = mouseevent.getY()- TOOLBAR_HEIGHT;
		client.idleTime = 0;
		client.clickX = i;
		client.clickY = j;
		client.clickTime = System.currentTimeMillis();
		if (SwingUtilities.isMiddleMouseButton(mouseevent)) {
			client.mouseWheelDown = true;
			client.mouseWheelX = i;
			client.mouseWheelY = j;
			return;
		}
		if (SwingUtilities.isRightMouseButton(mouseevent)) {
			client.clickType = client.RIGHT;
			client.clickMode1 = 2;
			client.clickMode2 = 2;
		} else if (SwingUtilities.isLeftMouseButton(mouseevent)) {
			client.clickType = client.LEFT;
			client.clickMode1 = 1;
			client.clickMode2 = 1;
		}
		Slider.handleSlider(i, j);
		ColorPicker.handleClick(i, j, false);
	}

	@Override
	public final void mouseReleased(MouseEvent mouseevent) {
		client.idleTime = 0;
		client.clickMode2 = 0;
		client.mouseWheelDown = false;
		client.clickType = client.RELEASED;
		client.sliderShowAlpha = false;
	}

	@Override
	public final void mouseClicked(MouseEvent mouseevent) {
	}

	@Override
	public final void mouseEntered(MouseEvent mouseevent) {
	}

	@Override
	public final void mouseExited(MouseEvent mouseevent) {
		client.idleTime = 0;
		client.mouseX = -1;
		client.mouseY = -1;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		int rotation = event.getWheelRotation();
		client.handleInterfaceScrolling(event);
		if (client.mouseX > 0 && client.mouseX < 512 && client.mouseY > Client.currentGameHeight - 165
				&& client.mouseY < Client.currentGameHeight - 25) {
			int scrollPos = Client.anInt1089;
			scrollPos -= rotation * 30;
			if (scrollPos < 0)
				scrollPos = 0;

			if (Client.anInt1089 != scrollPos) {
				Client.anInt1089 = scrollPos;
				// Client.updateChatbox = true;
			}
		} else if (Client.loggedIn) {

			/** ZOOMING **/
			boolean zoom = Client.currentScreenMode == ScreenMode.FIXED ? (client.mouseX < 512)
					: (client.mouseX < Client.currentGameWidth - 200);
			if (zoom && Client.openInterfaceID == -1) {
				Client.cameraZoom += rotation * 35;

				int max_zoom_1 = (Client.currentScreenMode == ScreenMode.FIXED ? -150 : -300);
				if (Client.cameraZoom < max_zoom_1) {
					Client.cameraZoom = max_zoom_1;
				}
				if (Client.cameraZoom > 1200) {
					Client.cameraZoom = 1200;
				}
				if (Client.currentScreenMode == ScreenMode.FIXED) {
					if (Client.cameraZoom < 70) {
						Client.cameraZoom = 70;
					}
				} else {
					if (Client.cameraZoom < 130) {
						Client.cameraZoom = 130;
					}
				}

				int setting = 0;
				if (Client.cameraZoom > 1000) {
					setting = 4;
				} else if (Client.cameraZoom > 800) {
					setting = 3;
				} else if (Client.cameraZoom > 600) {
					setting = 2;
				} else if (Client.cameraZoom > 400) {
					setting = 1;
				}
				
				RSInterface.interfaceCache[SettingsWidget.ZOOMTOGGLE].active = true;
				
				/*
				//this is commented out because settings[168] is nulling when a value is set to it.
				try {
					Client.instance.settings[168] = setting;
				} catch (Exception e) {
					System.out.println("Failed to set settings[168] to: "+setting);
				}
				*/
				RSInterface.interfaceCache[SettingsWidget.ZOOM_SLIDER].slider.setValue(Client.cameraZoom);
			}
		}

	}
}
