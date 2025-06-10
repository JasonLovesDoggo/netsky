import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Shows the help text and visual cues for navigation whenever the user hovers over the icon
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class HelpIcon extends JComponent {
	/** True if the help text should be displayed, if the mouse hovers over the icon. False if mouse is not hovering over the icon */
    boolean helptext;
	/** The image of the icon itself */
    final Image icon;
	/** The JComponent that represents the text drawn whenever the mouse hovers over the icon */
    final HelpText text;
	
	/** 
	 * Creates a new HelpIcon with the corresponding mouse listeners to check for whether or not
	 * the mouse is hovering over the icon. 
	 * 
	 * @param main		the JLayeredPane that this is being added to
	 */
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
	
	/** 
	 * Draws the icon image and makes the help text visible if the boolean helptext is true
	 * 
	 * @param g		the graphics instance that draws the image
	 */
    public void paintComponent(Graphics g) {
        g.drawImage(icon, 0, 0, icon.getWidth(null), icon.getHeight(null), null);
        text.setVisible(helptext);
    }
	
	/** 
	 * Gets the width of the icon
	 * 
	 * @return		the width of the icon image
	 */
    public int getWidth() {
        return icon.getWidth(null);
    }
	
	/**
	 * Gets the height of the icon
	 * 
	 * @return 		the height of the icon image
	 */
    public int getHeight() {
        return icon.getHeight(null);
    }
	
	/**
	 * The text and corresponding box that is displayed. Contains the text that actually helps
	 * the user and provides extra information when the help icon is hovered over.
	 */
    static class HelpText extends JComponent {
		/**
		 * Draws the rectangle and text onto the screen
		 * 
		 * @param g		the graphics instance that draws the rectange and text
		 */
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