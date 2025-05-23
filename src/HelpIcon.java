import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HelpIcon extends JComponent{
	boolean helptext;
	
	HelpIcon() {
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				System.out.println("Entered!");
				helptext = true;
				repaint();
			}
			public void mouseExited(MouseEvent e) {
				helptext = false;
				repaint();
				System.out.println("Exited");
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		Image icon = new ImageIcon("./Images/HelpIcon.png").getImage();
		g.drawImage(icon, 0, 0, icon.getWidth(null)/2, icon.getHeight(null)/2, null);
		if (helptext) {
			this.setBounds(0, 0, 600, 100);
			g.drawString("instructions", 300, 50);
		}
	}
	
	
}