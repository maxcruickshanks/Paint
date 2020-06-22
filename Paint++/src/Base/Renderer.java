package Base;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Base.rObject.lType;
import UI.Connector;

public class Renderer 
{
	public static int shiftX, shiftY, shiftAmount, textIndex;
	public static Rectangle View;
	public static ArrayList<rObject> renderingObjects;
	public static Cursor textCursor, normalCursor;
	public static int selectedIndex;
	
	public static void Initialize() throws Exception
	{
		shiftX = 0; shiftY = 0; shiftAmount = 300; selectedIndex = -1;
		updateView();
		renderingObjects = new ArrayList<rObject>();
		normalCursor = Main.mainFrame.getContentPane().getCursor();
		textCursor = Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(Renderer.class.getResourceAsStream("/textCursor.png")), new Point(20, 20), "");
	}
	
	public static void changeCursor(int type)
	{
		switch (type)
		{
		case 1:
			Main.mainFrame.getContentPane().setCursor(textCursor);
			break;
		default:
			Main.mainFrame.getContentPane().setCursor(normalCursor);
			break;
		}
	}
	
	public static void createPaint(Point P)
	{
		renderingObjects.add(new rObject(lType.PAINT, P.x + shiftX, P.y + shiftY));
	}
	
	public static void createText(String text, int x, int y)
	{
		selectedIndex = renderingObjects.size();
		renderingObjects.add(new rObject(lType.TEXT, x, y, text));
	}
	
	public static void updateView()
	{
		View = new Rectangle(shiftX, shiftY, Constant.screenSizeX, Constant.screenSizeY);
	}
	
	public static void Render(Graphics G)
	{
		if (Connector.buttonToggled("createPaint") && Connector.mouseDown) createPaint(MouseInfo.getPointerInfo().getLocation());
		
		for (int i = 0; i < renderingObjects.size(); i++)
		{
			rObject temp = renderingObjects.get(i);
			if (temp.isVisible(G)) temp.Render(G, i == selectedIndex && Connector.isTyping);
		}
	}
}
