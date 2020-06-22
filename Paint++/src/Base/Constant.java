package Base;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Constant 
{
	public static final Rectangle Screen = new Rectangle(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	public static final int screenSizeX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int screenSizeY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int blotchSize = 3; 
}
