import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class UserInput extends JComponent{
	public int promptCount;
	private int max;
	public JComponent sceneOne;
	
	public UserInput(int max) {
		this.max = max;
		this.promptCount = 0;
		
		this.setFocusable(true);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == 'i' && promptCount < max) {
			        promptCount++;
			        sceneOne.repaint();
			    } else if (e.getKeyChar() == 'u' && promptCount > 0) {
			        promptCount--;
			        sceneOne.repaint();
			    }
			}
		});
	}
	
	@Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow(); // Ensures focus when component is added to a container
    }
	
	public void paintComponent(Graphics g) {
		this.requestFocusInWindow();
	}
}