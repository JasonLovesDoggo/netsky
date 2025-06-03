import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FadeOut extends JComponent{
	Timer timer;
	public int radius; 
	int count;
	JComponent scene;
	boolean fading;
	Color color;
	
	FadeOut(JComponent s, Color color) {
		
		scene = s;
		
		this.radius = 700;
		setOpaque(false);
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radius -= 10;
				if (radius <= 0) {
					timer.stop();
					fading = false;
				}
				scene.repaint();
			}
		});
		this.color = color;
	}
	
	public void start() {
		fading = true;
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		/*if (count == 0) {
			count++;
			return;
		} else if (count == 1) {
			System.out.println("Timer started");
			super.paintComponent(g);
			scene.repaint();
			count+=2;
			timer.start();
		}
		return;*/
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		for(int i = 0; i < 800-radius; i++) {
        	g2.drawOval(400-radius-i, 250-radius-i, (i+radius)*2, (i+radius)*2);
		}
		

	}
}