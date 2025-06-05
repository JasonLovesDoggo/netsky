import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Prompt {
    Prompt(String text, int x, int y, Graphics g, Graphics2D g2) {
        g.setFont(new Font("Consolas", Font.PLAIN, 15));
        FontMetrics fm = g.getFontMetrics();
        g.setColor(new Color(0xced4da));
        g2.fill(new RoundRectangle2D.Double(x - 20, y - 25, fm.stringWidth(text) + 40, 40, 20, 20));
        //g.fillRect(x-20, y-25, text.length()*10, 40);
        g.setColor(Color.black);
        //g.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));

        g.drawString(text, x, y);
    }
}


/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */


// 90: Gabriola

//203: Segoe Print

//227: Tempus Sans ITC

//21: Bradley Hand ITC