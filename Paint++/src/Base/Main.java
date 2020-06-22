package Base;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UI.Connector;
import UI.keyboardConnector;
import UI.mouseConnector;

//Note: the name was suggested by u/feliser on GitHub.

public class Main 
{
	public static JFrame mainFrame;
	
	public static void Initialize()
	{
		//Initialize the main frame:
		mainFrame = new JFrame();
		mainFrame.setUndecorated(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(Constant.screenSizeX, Constant.screenSizeY);
		
		//Define the display panel:
		JPanel mainPanel = new JPanel()
		{
			protected void paintComponent(Graphics Graphics)
			{
				Graphics.setColor(new Color(255, 255, 255));
				Graphics.fillRect(0, 0, Constant.screenSizeX, Constant.screenSizeY);
				
				Graphics.setFont(Graphics.getFont().deriveFont(Graphics.getFont().getSize() * 2f));
				Renderer.Render(Graphics);
				Graphics.setFont(Graphics.getFont().deriveFont(Graphics.getFont().getSize() / 1.5f));
				Connector.findButton("Controller").Render(Graphics);
				if (Connector.buttonToggled("Controller"))
				{
					//Hard-code skipping the first button:
					for (int i = 1; i < Connector.Buttons.length; i++) Connector.Buttons[i].Render(Graphics);
				}
			}
		};
		mainFrame.add(mainPanel);
		
		mainFrame.addMouseListener(new mouseConnector());
		mainFrame.addKeyListener(new keyboardConnector());
		
		mainFrame.setVisible(true);
		
		paintThread.start();
	}
	
	public static Thread paintThread = new Thread()
	{
		public void run()
		{
			while (true)
			{
				//Paint the frame:
				mainFrame.repaint();
				
				//Sleep the thread for 1/200th of a second to emulate 200 FPS updates across all platforms:
				try
				{
					Thread.sleep(5);
				}
				catch (Exception Exception)
				{
					Exception.printStackTrace();
				}
			}
		}
	};
	
	public static void main(String[] Arguments) throws Exception
	{
		Connector.Initialize();
		Initialize();
		Renderer.Initialize(); //If we do not initialize this after the mainFrame, we will not be able to restore the cursor (deprecated).
	}
}
