package UI;

import Base.Constant;

public class Connector 
{
	public static Button[] Buttons;
	public static int buttonSize;
	public static boolean isTyping;
	public static boolean mouseDown;
	
	public static void Initialize()
	{
		buttonSize = 30;
		isTyping = false; mouseDown = false;
		
		Buttons = new Button[3];
		Buttons[0] = new Button(0 * Constant.screenSizeX / Buttons.length, 0, Constant.screenSizeX / Buttons.length, buttonSize,
				"Toggle", "Controller");
		Buttons[1] = new Button(1 * Constant.screenSizeX / Buttons.length, 0, Constant.screenSizeX / Buttons.length, buttonSize,
				"Text", "createText");
		Buttons[2] = new Button(2 * Constant.screenSizeX / Buttons.length, 0, Constant.screenSizeX / Buttons.length, buttonSize,
				"Paint", "createPaint");
	}
	
	public static boolean buttonToggled(String Name)
	{
		for (int i = 0; i < Buttons.length; i++)
			if (Buttons[i].Name.equals(Name)) return Buttons[i].Toggled;
		return false;
	}
	
	public static Button findButton(String Name)
	{
		for (int i = 0; i < Buttons.length; i++)
			if (Buttons[i].Name.equals(Name)) return Buttons[i];
		return null;
	}
	
	public static void toggleButton(String Name)
	{
		for (int i = 0; i < Buttons.length; i++)
			if (Buttons[i].Name.equals(Name)) 
			{
				Buttons[i].Toggled = !Buttons[i].Toggled;
				return;
			}
	}
}
