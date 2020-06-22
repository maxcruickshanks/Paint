package UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Base.Renderer;

public class keyboardConnector implements KeyListener
{
	@Override
	public void keyPressed(KeyEvent E) 
	{
		if (Connector.isTyping)
		{
			switch (E.getKeyCode())
			{
			case 8:
				Renderer.renderingObjects.get(Renderer.selectedIndex).Delete();
				break;
			case 10:
				Renderer.renderingObjects.get(Renderer.selectedIndex).Update('\n');
				break;
			case 27:
				Connector.isTyping = false;
				break;
			case 37: //Left arrow:
				Renderer.renderingObjects.get(Renderer.selectedIndex).decreaseChar();
				break;
			case 38: //Up arrow:
				Renderer.renderingObjects.get(Renderer.selectedIndex).increaseUp();
				break;
			case 39: //Right arrow:
				Renderer.renderingObjects.get(Renderer.selectedIndex).increaseChar();
				break;
			case 40: //Down arrow:
				Renderer.renderingObjects.get(Renderer.selectedIndex).decreaseDown();
				break;
			default:
				 if (E.getKeyChar() != 65535)
				 Renderer.renderingObjects.get(Renderer.selectedIndex).Update(E.getKeyChar());
				break;
			}
		}
	}
	
	@Override public void keyReleased(KeyEvent E) {}
	@Override public void keyTyped(KeyEvent E) 
	{
		if (!Connector.isTyping)
		{
			switch (E.getKeyChar())
			{
			case 'w':
			case 'W':
				//Move up:
				Renderer.shiftY -= Renderer.shiftAmount;
				break;
			case 's':
			case 'S':
				//Move down:
				Renderer.shiftY += Renderer.shiftAmount;
				break;
			case 'a':
			case 'A':
				//Move left:
				Renderer.shiftX -= Renderer.shiftAmount;
				break;
			case 'd':
			case 'D':
				//Move right:
				Renderer.shiftX += Renderer.shiftAmount;
				break;
			}
		}
	}
}
