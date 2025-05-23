import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class News extends JComponent {
	String text;
	int y;
	Timer timer;
	int count;
	int timerCount;
	
	News(String text, JPanel p) {
		this.text = text;
		
		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				y+=1;
				repaint();
				timerCount++;
				if (timerCount > 200) {
					timer.stop();
				}
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
			g.drawString(text.substring(text.length()-text.length()%55), 50, 500-y*2+((text.length()/55))*30);
		}
	}
	
	public void scroll() {
		timer.start();
	}
}