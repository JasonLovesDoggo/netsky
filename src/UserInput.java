/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class UserInput extends JComponent {
    public int promptCount;
    private int max;
    public JComponent scene;

    public UserInput(int max) {
        this.max = max;
        this.promptCount = 0;

        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'i' && promptCount < max) {
                    promptCount++;
                    scene.repaint();
                } else if (e.getKeyChar() == 'u' && promptCount > 0) {
                    promptCount--;
                    scene.repaint();
                }
                promptCount = Math.min(Math.max(promptCount, 0), max);
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}