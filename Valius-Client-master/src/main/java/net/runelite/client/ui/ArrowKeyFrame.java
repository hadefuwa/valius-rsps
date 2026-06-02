package net.runelite.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;

import org.pushingpixels.substance.internal.utils.SubstanceCoreUtilities;
import org.pushingpixels.substance.internal.utils.SubstanceTitlePaneUtilities;

import com.client.Client;

import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class ArrowKeyFrame extends JFrame {
	
	private static final Color BG_COLOR = new Color(25, 27, 33);
	public ArrowKeyFrame(ClientUI clientUI) {
		this.setUndecorated(true);
		
		this.setIconImage(clientUI.getFrame().getIconImage());
		//this.setAlwaysOnTop(true);
		// Set some sensible swing defaults
		this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		this.getRootPane().setBackground(BG_COLOR);
		this.getRootPane().setDoubleBuffered(true);
		ClientTitleToolbar titleToolbar = new ClientTitleToolbar();
		final JComponent titleBar = SubstanceCoreUtilities.getTitlePaneComponent(this);
		titleToolbar.putClientProperty(SubstanceTitlePaneUtilities.EXTRA_COMPONENT_KIND, SubstanceTitlePaneUtilities.ExtraComponentKind.TRAILING);
		titleBar.add(titleToolbar);
		this.getContentPane().setBackground(BG_COLOR);
		
		
		this.setLocation(clientUI.getFrame().getX() - 200, clientUI.getFrame().getY() + clientUI.getFrame().getHeight() / 2 - ( 100 ));
		BufferedImage up = ImageUtil.getResourceStreamFromClass(ClientUI.class, "keybutton_up.png");
		BufferedImage left = ImageUtil.getResourceStreamFromClass(ClientUI.class, "keybutton_left.png");
		BufferedImage right = ImageUtil.getResourceStreamFromClass(ClientUI.class, "keybutton_right.png");
		BufferedImage down = ImageUtil.getResourceStreamFromClass(ClientUI.class, "keybutton_down.png");
		
		BufferedImage plus = ImageUtil.getResourceStreamFromClass(ClientUI.class, "plus.png");
		BufferedImage minus = ImageUtil.getResourceStreamFromClass(ClientUI.class, "minus.png");
		MouseAdapter adapter = new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		    	String buttonType = (String) ((JButton)e.getComponent()).getClientProperty("btnType");
		    	switch(buttonType){
		    	case "UP":
		    		Client.instance.keyArray[3] = 1;
		    		break;
		    	case "DOWN":
		    		Client.instance.keyArray[4] = 1;
		    		break;
		    	case "LEFT":
		    		Client.instance.keyArray[1] = 1;
		    		break;
		    	case "RIGHT":
		    		Client.instance.keyArray[2] = 1;
		    		break;
		    	case "ZOOMIN":
		    		Client.instance.zoomDirection = -1;
		    		break;
		    	case "ZOOMOUT":
		    		Client.instance.zoomDirection = 1;
		    		break;
		    	}
		    }
			
			@Override
		    public void mouseReleased(MouseEvent e) {
				String buttonType = (String) ((JButton)e.getComponent()).getClientProperty("btnType");
		    	switch(buttonType){
		    	case "UP":
		    		Client.instance.keyArray[3] = 0;
		    		break;
		    	case "DOWN":
		    		Client.instance.keyArray[4] = 0;
		    		break;
		    	case "LEFT":
		    		Client.instance.keyArray[1] = 0;
		    		break;
		    	case "RIGHT":
		    		Client.instance.keyArray[2] = 0;
		    		break;
		    	case "ZOOMIN":
		    		Client.instance.zoomDirection = 0;
		    		break;
		    	case "ZOOMOUT":
		    		Client.instance.zoomDirection = 0;
		    		break;
		    	}
		    }
		};
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(BG_COLOR);
		bottomPanel.setBackground(BG_COLOR);
		topPanel.setBorder(BorderFactory.createEmptyBorder());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder());
		
		JButton zoomIn = createButton(plus);
		zoomIn.putClientProperty("btnType", "ZOOMIN");
		zoomIn.addMouseListener(adapter);
		
		JButton zoomOut = createButton(minus);
		zoomOut.putClientProperty("btnType", "ZOOMOUT");
		zoomOut.addMouseListener(adapter);
		
		JButton upBtn = createButton(up);
		upBtn.putClientProperty("btnType", "UP");
		upBtn.addMouseListener(adapter);
		
		topPanel.add(zoomOut, BorderLayout.LINE_START);
		topPanel.add(upBtn, BorderLayout.CENTER);
		topPanel.add(zoomIn, BorderLayout.LINE_END);
		topPanel.setSize(new Dimension(200, 100));
		
		
		JButton downBtn = createButton(down);
		downBtn.putClientProperty("btnType", "DOWN");
		downBtn.addMouseListener(adapter);
		
		JButton leftBtn = createButton(left);
		leftBtn.putClientProperty("btnType", "LEFT");
		leftBtn.addMouseListener(adapter);
		
		JButton rightBtn = createButton(right);
		rightBtn.putClientProperty("btnType", "RIGHT");
		rightBtn.addMouseListener(adapter);
		

		bottomPanel.add(leftBtn, BorderLayout.LINE_START);
		bottomPanel.add(downBtn, BorderLayout.CENTER);
		bottomPanel.add(rightBtn, BorderLayout.LINE_END);
		
		this.getContentPane().add(topPanel, BorderLayout.PAGE_START);
		this.getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
		this.setResizable(false);
		this.pack();
	
		
	}
	
	private JButton createButton(BufferedImage img) {
		final JButton button = new JButton();
		button.setSize(img.getWidth(), img.getHeight());
		button.setIcon(new ImageIcon(img));
		button.setFocusable(false);
		//button.setOpaque(false);
		button.setDoubleBuffered(true);
		button.setBackground(BG_COLOR);
		button.setBorder(null);
		button.setContentAreaFilled(true);
		button.setBorderPainted(false);
		return button;
	}


}
