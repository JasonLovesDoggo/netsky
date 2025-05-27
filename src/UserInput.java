import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class UserInput extends JComponent{
	public int promptCount;
	private int max;
	private JComponent sceneOne;
	
	UserInput(int max, JComponent sceneOne) {
		this.max = max;
		this.promptCount = 0;
		this.sceneOne = sceneOne;
		
		this.setFocusable(true);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == 'i' && promptCount < max-1) {
			        promptCount++;
			        sceneOne.repaint();
			    } else if (e.getKeyChar() == 'u' && promptCount > 0) {
			        promptCount--;
			        sceneOne.repaint();
			    }
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		this.requestFocusInWindow();
	}
}