import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

public class HelpIcon extends JComponent{
	boolean helptext;
	Image icon;
	HelpText text;
	
	HelpIcon(JLayeredPane main) {
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				//System.out.println("Entered!");
				helptext = true;
				repaint();
			}
			public void mouseExited(MouseEvent e) {
				helptext = false;
				repaint();
				//System.out.println("Exited");
			}
		});
		icon = new ImageIcon("./Images/HelpIcon.png").getImage();
		text = new HelpText();
		text.setBounds(500, 0, 300, 200);
		text.setVisible(false);
		main.add(text, JLayeredPane.PALETTE_LAYER);
		
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(icon, 0, 0, icon.getWidth(null), icon.getHeight(null), null);
		text.setVisible(helptext);
	}
	
	public int getWidth() {
		return icon.getWidth(null);
	}
	
	public int getHeight() {
		return icon.getHeight(null);
	}
	
	class HelpText extends JComponent{
		public void paintComponent (Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g.setColor(new Color(75, 75, 75, 150));
			g2.fill(new RoundRectangle2D.Double(0, 20, 250, 100, 20, 20));
			g.setColor(Color.white);
			g.setFont(new Font("Consolas", Font.PLAIN, 12));
			g.drawString("Press i to interact with the ", 15, 50);
			g.drawString("prompt, and press u to go back.", 15, 70);
			g.drawString("Click on a robot to interact.", 15, 90);
		}
	}
	
}