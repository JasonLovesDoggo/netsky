import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Robot extends JComponent{
	public int direction;
	public boolean speech;
	public boolean mouse;
	Image robot;
	Speech speechBubble;
	public boolean talk;
	RobotTalking text;
	
	Robot(JLayeredPane pane) {
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				//System.out.println("Entered!");
				if (!talk){
					mouse = true;
				}
				repaint();
			}
			public void mouseExited(MouseEvent e) {
				mouse = false;
				repaint();
				//System.out.println("Exited");
			}
			public void mouseClicked(MouseEvent e) {
				if (speech&&mouse) {
					talk = true;
					repaint();
				}
			}
		});
		
		robot = new ImageIcon("./Images/robot.png").getImage();
		speechBubble = new Speech();
		speechBubble.setSize(speechBubble.getWidth(), speechBubble.getHeight());
		speechBubble.setVisible(false);
		pane.add(speechBubble, JLayeredPane.PALETTE_LAYER);
		
		text = new RobotTalking();
		text.setSize(text.getWidth(), text.getHeight());
		text.setLocation(0, 100);
		text.setVisible(false);
		pane.add(text, JLayeredPane.PALETTE_LAYER);
	}
	
	public void paintComponent(Graphics g) {
		drawRobot(g);
		speechBubble.setVisible(speech&&mouse);
		text.setVisible(speech&&talk);
	}
	
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x,y);
		if(speech) {
			speechBubble.setLocation(x+10, y-speechBubble.getHeight());
		}
	}
	
	public void drawRobot(Graphics g) {
		if (direction == 1) {
			g.drawImage(robot, 0, 0, robot.getWidth(null), robot.getHeight(null), null);
		} else {
			g.drawImage(robot, robot.getWidth(null), 0, (-1) * robot.getWidth(null), robot.getHeight(null), null);
		}
	}
	
	public int getWidth() {
		return robot.getWidth(null);
	}
	
	public int getHeight() {
		return robot.getHeight(null);
	}
	
	class RobotTalking extends JComponent {
		Image text;
		
		RobotTalking() {
			text = new ImageIcon("./Images/RobotTalking.png").getImage();
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(text, 0, 0, text.getWidth(null), text.getHeight(null), null);
		}
		
		public int getWidth() {
			return text.getWidth(null);
		}
		
		public int getHeight() {
			return text.getHeight(null);
		}
	}
	
	class Speech extends JComponent {
		Image speechBubble;
		
		Speech() {
			speechBubble = new ImageIcon("./Images/SpeechBubble.png").getImage();
		}
		
		public void paintComponent(Graphics g) {
			
			//10, -50
			g.drawImage(speechBubble, 0, 0, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
		}
		
		public int getWidth() {
			return speechBubble.getWidth(null);
		}
		
		public int getHeight() {
			return speechBubble.getHeight(null);
		}
	}
}