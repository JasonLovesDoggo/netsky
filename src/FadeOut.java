import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FadeOut extends JComponent{
	Timer timer;
	public int radius; 
	int count;
	FadeOut(JComponent scene) {
		
		this.radius = 900;
		setOpaque(false);
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radius -= 10;
				if (radius <= 0) {
					timer.stop();
				}
				scene.repaint();
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		if (count == 0) {
			count++;
			return;
		} else if (count == 1) {
			super.paintComponent(g);
			timer.start();
			count++;
		}
		return;
	}
}