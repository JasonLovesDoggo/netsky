import java.awt.*;
import javax.swing.*;

public class Robot{
	public int x;
	public int y;
	
	/*public void paint(Graphics g) {
		
		
	}*/
	
	public void drawRobot(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x-(72/2), y-(111/2), 72, 111);
	}
}