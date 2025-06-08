/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Provides help text and visual cues for navigation
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class HelpIcon extends JComponent {
    boolean helptext;
    final Image icon;
    final HelpText text;

    HelpIcon(JLayeredPane main) {
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                helptext = true;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                helptext = false;
                repaint();
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

    static class HelpText extends JComponent {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            // Apply more attractive styling with colors from Palette
            g.setColor(new Color(50, 50, 70, 200)); // Darker, more opaque background
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fill(new RoundRectangle2D.Double(0, 20, 250, 110, 15, 15));

            // Add a subtle border
            g2.setColor(Palette.BORDER_LIGHT);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Double(0, 20, 250, 110, 15, 15));

            // Use Palette font instead of creating a new one
            g.setColor(Color.white);
            g.setFont(Palette.TEXT_SMALL_FONT);
            g.drawString("Press i to interact with the", 20, 50);
            g.drawString("prompt, and press u to go back.", 20, 75);
            g.drawString("Click on a robot to interact.", 20, 100);
        }
    }
}