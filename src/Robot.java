import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Robot{
	public int x;
	public int y;
	public int direction;
	public boolean speech;
	
	public void drawRobot(Graphics g, Graphics2D g2) {
		Image robot = new ImageIcon("./Images/robot.png").getImage();
		if (direction == 1) {
			g.drawImage(robot, x, y, robot.getWidth(null), robot.getHeight(null), null);
		} else {
			g.drawImage(robot, x+robot.getWidth(null), y, (-1) * robot.getWidth(null), robot.getHeight(null), null);
		}
		Image speechBubble = new ImageIcon("./Images/SpeechBubble.png").getImage();
		g.drawImage(speechBubble, x+10, y-50, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
	}
	
}