package UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Base.Renderer;
import Base.rObject.lType;

public class mouseConnector implements MouseListener
{
	@Override
	public void mouseReleased(MouseEvent E) 
	{
		Connector.mouseDown = false;
		if (Connector.findButton("Controller").isInside(E.getPoint()))
		{
			Connector.toggleButton("Controller");
			return;
		}
		
		if (Connector.buttonToggled("Controller"))
		{
			//Hard-code skipping the first button:
			for (int i = 1; i < Connector.Buttons.length; i++)
			{
				if (Connector.Buttons[i].isInside(E.getPoint()))
				{
					Connector.Buttons[i].Toggled = !Connector.Buttons[i].Toggled;
					if (Connector.Buttons[i].Name.equals("createText"))
					{
						Renderer.changeCursor(Connector.Buttons[i].Toggled ? 1 : 0);
						if (!Connector.Buttons[i].Toggled) Connector.isTyping = false;
						else
						{
							if (Connector.buttonToggled("createPaint")) Connector.findButton("createPaint").Toggled = false;
							if (Renderer.selectedIndex != -1) Connector.isTyping = true;
						}
					}
					else if (Connector.Buttons[i].Name.equals("createPaint"))
					{
						if (Connector.buttonToggled("createText")) 
						{
							Connector.findButton("createText").Toggled = false;
							Renderer.changeCursor(0);
							Connector.isTyping = false;
						}
					}
					return;
				}
			}
		}
		
		if (Connector.buttonToggled("createText"))
		{
			Connector.isTyping = true;
			//Scan for any nearby text elements:
			for (int i = 0; i < Renderer.renderingObjects.size(); i++)
			{
				if (Renderer.renderingObjects.get(i).Type == lType.TEXT && Renderer.renderingObjects.get(i).isInside(E.getPoint()))
				{
					//Do not create a new text instance; select the highlighted text instance:
					Renderer.renderingObjects.get(i).selectedChar = Renderer.renderingObjects.get(i).Text.length();
					Renderer.selectedIndex = i;
					return;
				}
			}
			//Create a new text instance:
			Renderer.createText("", E.getPoint().x + Renderer.shiftX, E.getPoint().y + Renderer.shiftY);
			return;
		}
	}
	
	@Override 
	public void mousePressed(MouseEvent E) 
	{
		Connector.mouseDown = true;
	}
	
	@Override public void mouseClicked(MouseEvent E) {}
	@Override public void mouseEntered(MouseEvent E) {}
	@Override public void mouseExited(MouseEvent E) {}
}
