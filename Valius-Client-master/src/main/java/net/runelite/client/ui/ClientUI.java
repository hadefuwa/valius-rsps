/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.annotation.Nullable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.pushingpixels.substance.internal.SubstanceSynapse;
import org.pushingpixels.substance.internal.utils.SubstanceCoreUtilities;
import org.pushingpixels.substance.internal.utils.SubstanceTitlePaneUtilities;

import com.client.Client;
import com.client.listeners.GameKeyListener;
import com.client.listeners.GameMouseListener;
import com.client.listeners.GameWindowListener;

import lombok.Getter;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NavigationButtonAdded;
import net.runelite.client.events.NavigationButtonRemoved;
import net.runelite.client.input.MouseAdapter;
import net.runelite.client.input.MouseListener;
import net.runelite.client.ui.skin.SubstanceRuneLiteLookAndFeel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

/**
 * Client UI.
 */
public class ClientUI
{
	private static final String CONFIG_GROUP = "runelite";
	private static final String CONFIG_CLIENT_BOUNDS = "clientBounds";
	private static final String CONFIG_CLIENT_MAXIMIZED = "clientMaximized";
	private static final int CLIENT_WELL_HIDDEN_MARGIN = 160;
	private static final int CLIENT_WELL_HIDDEN_MARGIN_TOP = 10;
	public static final BufferedImage ICON = ImageUtil.getResourceStreamFromClass(ClientUI.class, "/images/v2.png");

	@Getter
	private TrayIcon trayIcon;
	
	private Client client;
	private final CardLayout cardLayout = new CardLayout();
	private final Rectangle sidebarButtonPosition = new Rectangle();
	private boolean withTitleBar;
	private BufferedImage sidebarOpenIcon;
	private BufferedImage sidebarClosedIcon;
	@Getter
	private ContainableFrame frame;
	private JPanel navContainer;
	private PluginPanel pluginPanel;
	private ClientPluginToolbar pluginToolbar;
	private ClientTitleToolbar titleToolbar;
	private JButton currentButton;
	private NavigationButton currentNavButton;
	private boolean sidebarOpen;
	private JPanel container;
	private NavigationButton sidebarNavigationButton;
	private JButton sidebarNavigationJButton;
	private Dimension lastClientSize;



	@Subscribe
	public void onNavigationButtonAdded(final NavigationButtonAdded event)
	{
		SwingUtilities.invokeLater(() ->
		{
			final NavigationButton navigationButton = event.getButton();
			final PluginPanel pluginPanel = navigationButton.getPanel();
			final boolean inTitle = !event.getButton().isTab() && withTitleBar;
			final int iconSize = 16;

			if (pluginPanel != null)
			{
				navContainer.add(pluginPanel.getWrappedPanel(), navigationButton.getTooltip());
			}

			final JButton button = SwingUtil.createSwingButton(navigationButton, iconSize, (navButton, jButton) ->
			{
				final PluginPanel panel = navButton.getPanel();

				if (panel == null)
				{
					return;
				}

				boolean doClose = currentButton != null && currentButton == jButton && currentButton.isSelected();

				if (doClose)
				{
					contract();
					currentButton.setSelected(false);
					currentNavButton.setSelected(false);
					currentButton = null;
					currentNavButton = null;
				}
				else
				{
					if (currentButton != null)
					{
						currentButton.setSelected(false);
					}

					if (currentNavButton != null)
					{
						currentNavButton.setSelected(false);
					}

					currentButton = jButton;
					currentNavButton = navButton;
					currentButton.setSelected(true);
					currentNavButton.setSelected(true);
					expand(navButton);
				}
			});

			if (inTitle)
			{
				titleToolbar.addComponent(event.getButton(), button);
			}
			else
			{
				pluginToolbar.addComponent(event.getButton(), button);
			}
		});
	}

	@Subscribe
	public void onNavigationButtonRemoved(final NavigationButtonRemoved event)
	{
		SwingUtilities.invokeLater(() ->
		{
			pluginToolbar.removeComponent(event.getButton());
			titleToolbar.removeComponent(event.getButton());
			final PluginPanel pluginPanel = event.getButton().getPanel();

			if (pluginPanel != null)
			{
				navContainer.remove(pluginPanel.getWrappedPanel());
			}
		});
	}


	/**
	 * Initialize UI.
	 *
	 * @param runelite runelite instance that will be shut down on exit
	 * @throws Exception exception that can occur during creation of the UI
	 */
	public void open(Client client) throws Exception
	{
		this.client = client;
		SwingUtilities.invokeAndWait(() ->
		{
			// Set some sensible swing defaults
			SwingUtil.setupDefaults();

			// Use substance look and feel
			SwingUtil.setTheme(new SubstanceRuneLiteLookAndFeel());

			// Use custom UI font
			SwingUtil.setFont(FontManager.getRunescapeFont());

			// Create main window
			frame = new ContainableFrame();

			// Try to enable fullscreen on OSX
			//OSXUtil.tryEnableFullscreen(frame);

			frame.setTitle("Valius");
			frame.setIconImage(ImageUtil.resizeImage(ICON, 32, 32));
			frame.getLayeredPane().setCursor(Cursor.getDefaultCursor()); // Prevent substance from using a resize cursor for pointing
			frame.setLocationRelativeTo(frame.getOwner());
			frame.setResizable(false);
			frame.setFocusTraversalKeysEnabled(false);
			client.setFocusTraversalKeysEnabled(false);
			client.addKeyListener(new GameKeyListener(client));
			GameMouseListener gameMouseListener = new GameMouseListener(client);
			client.addMouseListener(gameMouseListener);
			client.addMouseMotionListener(gameMouseListener);
			client.addMouseWheelListener(gameMouseListener);
			
			GameWindowListener windowListener = new GameWindowListener(client);
			frame.addWindowListener(windowListener);
			frame.addFocusListener(windowListener);
			container = new JPanel();
			container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
			container.add(client);

			navContainer = new JPanel();
			navContainer.setLayout(cardLayout);
			navContainer.setMinimumSize(new Dimension(0, 0));
			navContainer.setMaximumSize(new Dimension(0, 0));
			navContainer.setPreferredSize(new Dimension(0, 0));
			
			arrowKeyFrame = new ArrowKeyFrame(this);

			// To reduce substance's colorization (tinting)
			navContainer.putClientProperty(SubstanceSynapse.COLORIZATION_FACTOR, 1.0);
			container.add(navContainer);

			pluginToolbar = new ClientPluginToolbar();
			titleToolbar = new ClientTitleToolbar();
			frame.add(container);
			
			final MouseListener mouseListener = new MouseAdapter()
			{
				@Override
				public MouseEvent mousePressed(MouseEvent mouseEvent)
				{
					if (SwingUtilities.isLeftMouseButton(mouseEvent) && sidebarButtonPosition.contains(mouseEvent.getPoint()))
					{
						SwingUtilities.invokeLater(ClientUI.this::toggleSidebar);
						mouseEvent.consume();
					}

					return mouseEvent;
				}
			};
			
			//client.setDoubleBuffered(true);
			
			SwingUtil.addGracefulExitCallback(frame,
					() ->
					{
						client.destroy();
					},
					this::showWarningOnExit
				);

			// Decorate window with custom chrome and titlebar if needed
			withTitleBar = true;
			frame.setUndecorated(withTitleBar);

			if (withTitleBar)
			{
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

				final JComponent titleBar = SubstanceCoreUtilities.getTitlePaneComponent(frame);
				titleToolbar.putClientProperty(SubstanceTitlePaneUtilities.EXTRA_COMPONENT_KIND, SubstanceTitlePaneUtilities.ExtraComponentKind.TRAILING);
				titleBar.add(titleToolbar);

				// Substance's default layout manager for the title bar only lays out substance's components
				// This wraps the default manager and lays out the TitleToolbar as well.
				LayoutManager delegate = titleBar.getLayout();
				titleBar.setLayout(new LayoutManager()
				{
					@Override
					public void addLayoutComponent(String name, Component comp)
					{
						delegate.addLayoutComponent(name, comp);
					}

					@Override
					public void removeLayoutComponent(Component comp)
					{
						delegate.removeLayoutComponent(comp);
					}

					@Override
					public Dimension preferredLayoutSize(Container parent)
					{
						return delegate.preferredLayoutSize(parent);
					}

					@Override
					public Dimension minimumLayoutSize(Container parent)
					{
						return delegate.minimumLayoutSize(parent);
					}

					@Override
					public void layoutContainer(Container parent)
					{
						delegate.layoutContainer(parent);
						final int width = titleToolbar.getPreferredSize().width;
						titleToolbar.setBounds(titleBar.getWidth() - 75 - width, 0, width, titleBar.getHeight());
					}
				});
			}


			// Create hide sidebar button

			sidebarOpenIcon = ImageUtil.getResourceStreamFromClass(ClientUI.class, "arrowkeys.png");
			sidebarClosedIcon = ImageUtil.flipImage(sidebarOpenIcon, true, false);

			sidebarNavigationButton = NavigationButton
				.builder()
				.tooltip("Show/Hide on-screen arrow keys")
				.priority(100)
				.icon(sidebarClosedIcon)
				.onClick(this::toggleArrowKeys)
				.build();

			sidebarNavigationJButton = SwingUtil.createSwingButton(
				sidebarNavigationButton,
				0,
				null);
			sidebarNavigationJButton.setSize(new Dimension(30, 22));
			sidebarNavigationJButton.setPreferredSize(new Dimension(30, 22));
			titleToolbar.addComponent(sidebarNavigationButton, sidebarNavigationJButton);
			//toggleSidebar();

			// Layout frame
			frame.pack();
			frame.revalidateMinimumSize();

			// Create tray icon (needs to be created after frame is packed)
			trayIcon = SwingUtil.createTrayIcon(ImageUtil.resizeImage(ICON, 16, 16), "Valius", frame);

			
				frame.setLocationRelativeTo(frame.getOwner());
			

			// If the frame is well hidden (e.g. unplugged 2nd screen),
			// we want to move it back to default position as it can be
			// hard for the user to reposition it themselves otherwise.
			Rectangle clientBounds = frame.getBounds();
			Rectangle screenBounds = frame.getGraphicsConfiguration().getBounds();
			if (clientBounds.x + clientBounds.width - CLIENT_WELL_HIDDEN_MARGIN < screenBounds.getX() ||
				clientBounds.x + CLIENT_WELL_HIDDEN_MARGIN > screenBounds.getX() + screenBounds.getWidth() ||
				clientBounds.y + CLIENT_WELL_HIDDEN_MARGIN_TOP < screenBounds.getY() ||
				clientBounds.y + CLIENT_WELL_HIDDEN_MARGIN > screenBounds.getY() + screenBounds.getHeight())
			{
				frame.setLocationRelativeTo(frame.getOwner());
			}

			// Show frame
			frame.setVisible(true);
			frame.toFront();
			requestFocus();
			giveClientFocus();
			client.init();
		});


	}
	
	JFrame arrowKeyFrame;
	
	private void toggleArrowKeys() {
		arrowKeyFrame.setVisible(!arrowKeyFrame.isVisible());
	}

	private boolean showWarningOnExit()
	{
		return client.loggedIn;
	}
	/**
	 * Paint this component to target graphics
	 *
	 * @param graphics the graphics
	 */
	public void paint(final Graphics graphics)
	{
		frame.paint(graphics);
	}

	/**
	 * Gets component width.
	 *
	 * @return the width
	 */
	public int getWidth()
	{
		return frame.getWidth();
	}

	/**
	 * Gets component height.
	 *
	 * @return the height
	 */
	public int getHeight()
	{
		return frame.getHeight();
	}

	/**
	 * Returns true if this component has focus.
	 *
	 * @return true if component has focus
	 */
	public boolean isFocused()
	{
		return frame.isFocused();
	}

	/**
	 * Request focus on this component and then on client component
	 */
	public void requestFocus()
	{
		frame.requestFocus();
		giveClientFocus();
	}

	/**
	 * Get offset of game canvas in game window
	 *
	 * @return game canvas offset
	 */
	public Point getCanvasOffset()
	{

		final java.awt.Point point = SwingUtilities.convertPoint(client, 0, 0, frame);
		return new Point(point.x, point.y);
	}

	public GraphicsConfiguration getGraphicsConfiguration()
	{
		return frame.getGraphicsConfiguration();
	}

	private void toggleSidebar()
	{
		// Toggle sidebar open
		boolean isSidebarOpen = sidebarOpen;
		sidebarOpen = !sidebarOpen;

		// Select/deselect buttons
		if (currentButton != null)
		{
			currentButton.setSelected(sidebarOpen);
		}

		if (currentNavButton != null)
		{
			currentNavButton.setSelected(sidebarOpen);
		}

		if (isSidebarOpen)
		{
			sidebarNavigationJButton.setIcon(new ImageIcon(sidebarOpenIcon));
			sidebarNavigationJButton.setToolTipText("Open SideBar");

			contract();

			// Remove plugin toolbar
			container.remove(pluginToolbar);
		}
		else
		{
			sidebarNavigationJButton.setIcon(new ImageIcon(sidebarClosedIcon));
			sidebarNavigationJButton.setToolTipText("Close SideBar");

			// Try to restore last panel
			expand(currentNavButton);

			// Add plugin toolbar back
			container.add(pluginToolbar);
		}

		// Revalidate sizes of affected Swing components
		container.revalidate();
		giveClientFocus();

		if (sidebarOpen)
		{
			frame.expandBy(pluginToolbar.getWidth());
		}
		else
		{
			frame.contractBy(pluginToolbar.getWidth());
		}
	}

	private void expand(@Nullable NavigationButton button)
	{
		if (button == null)
		{
			return;
		}

		final PluginPanel panel = button.getPanel();

		if (panel == null)
		{
			return;
		}

		if (!sidebarOpen)
		{
			toggleSidebar();
		}

		int width = panel.getWrappedPanel().getPreferredSize().width;
		int expandBy = pluginPanel != null ? pluginPanel.getWrappedPanel().getPreferredSize().width - width : width;
		pluginPanel = panel;

		// Expand sidebar
		navContainer.setMinimumSize(new Dimension(width, 0));
		navContainer.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
		navContainer.setPreferredSize(new Dimension(width, 0));
		navContainer.revalidate();
		cardLayout.show(navContainer, button.getTooltip());

		// panel.onActivate has to go after giveClientFocus so it can get focus if it needs.
		giveClientFocus();
		panel.onActivate();

		// Check if frame was really expanded or contracted
		if (expandBy > 0)
		{
			frame.expandBy(expandBy);
		}
		else if (expandBy < 0)
		{
			frame.contractBy(expandBy);
		}
	}

	private void contract()
	{
		if (pluginPanel == null)
		{
			return;
		}

		pluginPanel.onDeactivate();
		navContainer.setMinimumSize(new Dimension(0, 0));
		navContainer.setMaximumSize(new Dimension(0, 0));
		navContainer.setPreferredSize(new Dimension(0, 0));
		navContainer.revalidate();
		giveClientFocus();
		frame.contractBy(pluginPanel.getWrappedPanel().getPreferredSize().width);
		pluginPanel = null;
	}

	private void giveClientFocus()
	{
		if (client != null)
		{
			client.requestFocusInWindow();
		}
	}
	
}
