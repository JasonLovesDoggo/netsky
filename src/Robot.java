import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Robot{
	public int x;
	public int y;
	public int direction;
	
	public void drawRobot(Graphics g, Graphics2D g2) {
		Image robot = new ImageIcon("./Images/robot.png").getImage();
		robot = robot.getScaledInstance((direction) * robot.getWidth(null), robot.getHeight(null), Image.SCALE_DEFAULT);
		g.drawImage(robot, x, y, robot.getWidth(null), robot.getHeight(null), null);
		
		/*g.setColor(Color.white);
		g.fillOval(x-(72/2), y-(111/2), 72, 111);
		
		
		g.setColor(new Color(0xb3cee5));
		Path2D curve = new Path2D.Double();
		curve.moveTo(x-36, y-8.4);
		curve.curveTo(x-36, y-8.4, x, y+30, x+36, y+11);
		g2.draw(curve);*/
	}
	
}