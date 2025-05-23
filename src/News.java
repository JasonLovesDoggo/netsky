import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class News extends JComponent {
	String text;
	int count;
	int y;
	JPanel panel;
	Timer timer;
	
	News(String text, JPanel p) {
		this.text = text;
		panel = p;
		
		timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y+=2;
				repaint();
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (count == 0) {
			count++; //Prevent text from scrolling the first time
		} else {
			g.setColor(Color.black);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			for(int i = 0; i < text.length()/55; i++) {
				g.drawString(text.substring(i*55, i*55+55), 50, 500-y*2+i*30);
			}
		}
	}
	
	public void scroll() {
		count = 5;
		/*for(int j = 0; j < 50; j++) {
			y += 2;
			try {
				panel.revalidate();
				panel.repaint();
				this.repaint();
				System.out.println("Print " + j);
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}*/
		timer.start();
	}
}