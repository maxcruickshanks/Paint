package Base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class rObject
{
	public enum lType
	{
		TEXT,
		PAINT
	}
	
	public String Text;
	public int X, Y, selectedChar;
	public lType Type; 
	String[] parsedText;
	Point topLeft, topRight, Middle, bottomLeft, bottomRight;
	Rectangle Bounds;
	
	rObject(lType type, int x, int y, String text)
	{
		X = x; Y = y;
		Type = type;
		Text = text;
		parsedText = Text.split("\n");
	}
	
	rObject(lType type, int x, int y)
	{
		X = x; Y = y;
		Type = type;
	}
	
	void Render(Graphics G, boolean isSelected)
	{
		G.setColor(new Color(0, 0, 0));
		if (Type == lType.TEXT) 
		{
			if (isSelected)
			{
				int charPosition = 0; boolean Rendered = false;
				for (int i = 0; i < parsedText.length; i++)
				{
					if (selectedChar >= charPosition && selectedChar < charPosition + parsedText[i].length() + 1)
					{
						G.drawString(parsedText[i], X - Renderer.shiftX, Y - Renderer.shiftY + i * G.getFontMetrics().getHeight());
						G.setColor(new Color(255, 0, 0));
						G.drawString("|", X - Renderer.shiftX + G.getFontMetrics().stringWidth(parsedText[i].substring(0, Math.min(selectedChar - charPosition, parsedText[i].length()))) - 3, Y - Renderer.shiftY + i * G.getFontMetrics().getHeight() - 3);
						G.setColor(new Color(0, 0, 0));
						Rendered = true;
					}
					else G.drawString(parsedText[i], X - Renderer.shiftX, Y - Renderer.shiftY + i * G.getFontMetrics().getHeight());
					charPosition += parsedText[i].length() + 1;
				}
				if (!Rendered)
				{
					G.setColor(new Color(255, 0, 0));
					G.drawString("|", X - Renderer.shiftX - 3, Y - Renderer.shiftY + parsedText.length * G.getFontMetrics().getHeight() - 3);
					G.setColor(new Color(0, 0, 0));
				}
			}
			else  for (int i = 0; i < parsedText.length; i++) G.drawString(parsedText[i], X - Renderer.shiftX, Y - Renderer.shiftY + i * G.getFontMetrics().getHeight());
		}
		else if (Type == lType.PAINT) G.fillRect(X - Constant.blotchSize - Renderer.shiftX, Y - Constant.blotchSize - Renderer.shiftY, Constant.blotchSize * 2, Constant.blotchSize * 2);
	}
	
	int largestString(Graphics G)
	{
		int max = 0;
		for (int i = 0; i < parsedText.length; i++) max = Math.max(max, G.getFontMetrics().stringWidth(parsedText[i]));
		return max;
	}
	
	boolean isVisible(Graphics G)
	{
		//Text requires greater precision for rendering, whilst a paint blotch is at most a few pixels:
		if (Type == lType.TEXT)
		{
			topLeft = new Point(X - Renderer.shiftX, Y - Renderer.shiftY); topRight = new Point(X - Renderer.shiftX + largestString(G), Y - Renderer.shiftY);
				bottomLeft = new Point(X - Renderer.shiftX, Y - Renderer.shiftY + G.getFontMetrics().getHeight() * parsedText.length); bottomRight = new Point(X - Renderer.shiftX + largestString(G), Y - Renderer.shiftY + G.getFontMetrics().getHeight() * parsedText.length);
			Bounds = new Rectangle(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, Math.abs(topLeft.y - bottomRight.y));
			return Renderer.View.intersects(Bounds);
		}
		else if (Type == lType.PAINT) return Renderer.View.contains(new Point(X - Renderer.shiftX, Y - Renderer.shiftY));
		return false;
	}
	
	public void Update(char C)
	{
		if (selectedChar == Text.length()) Text += C;
		else Text = Text.substring(0, selectedChar) + C + Text.substring(selectedChar);
		parsedText = Text.split("\n");
		selectedChar++;
	}
	
	public void Delete()
	{
		//There's no effective method to do this (i.e., it must at least be O(n)), unless using a Maven project (you can use StringUtils.chop(Text)):
		if (Text.length() > 0)
		{
			if (selectedChar == Text.length()) Text = Text.substring(0, Text.length() - 1);
			else if (selectedChar != 0) Text = Text.substring(0, selectedChar - 1) + Text.substring(selectedChar);
			parsedText = Text.split("\n");
			selectedChar--;
		}
	}
	
	//Fix the collision based on the entire string, rather than each line (find the maximum length from all the lines?):
	public boolean isInside(Point P)
	{
		return Bounds.contains(P);
	}
	
	public void increaseUp()
	{
		if (Text.substring(0, selectedChar).contains("\n")) selectedChar = Text.substring(0, selectedChar).lastIndexOf("\n");
	}
	
	public void decreaseDown()
	{
		if (selectedChar != Text.length() && Text.substring(selectedChar + 1, Text.length()).contains("\n")) selectedChar += Text.substring(selectedChar + 1, Text.length()).indexOf("\n") + 1;
		else if (selectedChar != Text.length()) selectedChar = Text.length();
	}
	
	public void increaseChar()
	{
		selectedChar++; selectedChar = Math.min(selectedChar, Text.length());
	}
	
	public void decreaseChar()
	{
		selectedChar--; selectedChar = Math.max(selectedChar, 0);
	}
}
