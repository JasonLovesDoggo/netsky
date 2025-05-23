import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Prompt {
	Prompt(String text, int x, int y, Graphics g, Graphics2D g2) {
		g.setColor(new Color(0xced4da));
		g2.fill(new RoundRectangle2D.Double(x-20, y-25, text.length()*10, 40, 20, 20));
		//g.fillRect(x-20, y-25, text.length()*10, 40);
		g.setColor(Color.black);
		g.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
		g.drawString(text, x, y);
	}
}



// 90: Gabriola

//203: Segoe Print

//227: Tempus Sans ITC

//21: Bradley Hand ITC