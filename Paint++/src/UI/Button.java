package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button
{
	Rectangle Base;
	String Text, Name;
	Color cBack, cText, cOutline;
	BufferedImage Image;
	boolean isIcon;
	public boolean Toggled;
	
	Button(int rx, int ry, int w, int h, BufferedImage image, String name)
	{
		Base = new Rectangle(rx, ry, w, h);
		//TO DO: Maybe allow for specificity of colours, text size, and text font?
		Image = image;
		Name = name;
		cBack = new Color(128, 128, 128);
		cText = new Color(255, 255, 255);
		cOutline = new Color(0, 0, 0);
		isIcon = true;
	}
	
	Button(int rx, int ry, int w, int h, String text, String name)
	{
		Base = new Rectangle(rx, ry, w, h);
		//TO DO: Maybe allow for specificity of colours, text size, and text font?
		Text = text;
		Name = name;
		cBack = new Color(128, 128, 128);
		cText = new Color(255, 255, 255);
		cOutline = new Color(0, 0, 0);
		isIcon = false;
	}
	
	public void Render(Graphics G)
	{
		Point topLeft = new Point(Base.x, Base.y + Base.height), topRight = new Point(Base.x + Base.width, Base.y + Base.height), 
				bottomLeft = new Point(Base.x, Base.y), bottomRight = new Point(Base.x + Base.width, Base.y);
		
		//Draw the background:
		G.setColor(cBack);
		G.fillRect(Base.x, Base.y, Base.width, Base.height);
		
		//Draw the outline:
		G.setColor(cOutline);
		G.drawLine(topLeft.x, topLeft.y, topRight.x, topRight.y);
		G.drawLine(topRight.x, topRight.y, bottomRight.x, bottomRight.y);
		G.drawLine(bottomRight.x, bottomRight.y, bottomLeft.x, bottomLeft.y);
		G.drawLine(bottomLeft.x, bottomLeft.y, topLeft.x, topLeft.y);
		
		if (isIcon)
		{
			G.drawImage(Image, 
					(topRight.x + topLeft.x) / 2 - Image.getWidth() / 2, 
					(topRight.y + bottomRight.y) / 2 - Image.getHeight() / 2,
					null);
		}
		else
		{
			G.drawString(Text, 
					(topRight.x + topLeft.x) / 2 - (G.getFontMetrics().stringWidth(Text)) / 2, 
					(topRight.y + bottomRight.y) / 2 + (G.getFontMetrics().getHeight()) / 4);
		}
	}
	
	public boolean isInside(Point P)
	{
		return Base.contains(P);
	}
}
