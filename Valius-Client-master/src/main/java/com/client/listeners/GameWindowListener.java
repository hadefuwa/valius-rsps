package com.client.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import com.client.Client;

public class GameWindowListener implements WindowListener, FocusListener {

	private final Client client;
	
	
	public GameWindowListener(Client client) {
		this.client = client;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public final void focusGained(FocusEvent focusevent) {
		client.awtFocus = true;
		client.shouldClearScreen = true;
		client.raiseWelcomeScreen();
	}

	@Override
	public final void focusLost(FocusEvent focusevent) {
		client.awtFocus = false;
		for (int i = 0; i < 128; i++)
			client.keyArray[i] = 0;

	}
}
