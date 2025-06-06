/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: This class draws a string onto the screen, at a given location
 *
 */

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Prompt {
    Prompt(String text, int x, int y, Graphics g, Graphics2D g2) {
        g.setFont(new Font("Consolas", Font.PLAIN, 15));
        FontMetrics fm = g.getFontMetrics();
        g.setColor(new Color(0xced4da));
        g2.fill(new RoundRectangle2D.Double(x - 20, y - 25, fm.stringWidth(text) + 40, 40, 20, 20));
        //g.fillRect(x-20, y-25, text.length()*10, 40);
        //g.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));

        g.drawString(text, x, y);
    }
}



        g2.fill(roundedRect);


        g2.setColor(Palette.BORDER_LIGHT);
        g2.setStroke(new BasicStroke(1.0f));
        g2.draw(roundedRect);

//21: Bradley Hand ITC}