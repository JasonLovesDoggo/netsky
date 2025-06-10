import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Displays stylized prompt text throughout the application 
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Prompt {
	/** 
	 * Creates a new prompt
	 * 
	 * @param text 		the text that belongs in the prompt
	 * @param x			the x location of the prompt
	 * @param y			the y location of the prompt
	 * @param g			the graphics instance that draws the text
	 * @param g2		the graphics2D instance that draws the rounded rectangle and the outline
	 */
    Prompt(String text, int x, int y, Graphics g, Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setFont(Palette.PROMPT_FONT);
        FontMetrics fm = g.getFontMetrics();

        g2.setColor(new Color(60, 60, 80, 230));
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                x - 20, y - 25,
                fm.stringWidth(text) + 40,
                40,
                15, 15);
        g2.fill(roundedRect);

        g2.setColor(Palette.BORDER_LIGHT);
        g2.setStroke(new BasicStroke(1.0f));
        g2.draw(roundedRect);

        g.setColor(Color.white);
        g.drawString(text, x, y);
    }
}