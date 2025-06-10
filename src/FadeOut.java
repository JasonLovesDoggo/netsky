import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * Animates a circular fade-out effect over the current scene component.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: Jun 9th, 2025
 * ICS4U0
 * Ms. Krasteva 
 */
public class FadeOut extends JComponent {
	/** The radius of the fadeout at the beginning */
    public int radius;
	/** Timer that animates the fade out */
    Timer timer;
	/** The scene to which this fade out is attached */
    final JComponent scene;
	/** True if the fading animation is still ongoing, false if the animation is done or not started */
    boolean fading;
	/** The color of the fadeout animation */
    final Color color;
	
	/** 
	 * Constructs a new FadeOut instance and creates the timer. 
	 * Is passed the scene that the FadeOut is a part of, along with the color, so that it can
	 * set the fields accordingly
	 * 
	 * @param s			the scene that this fade out is a part of
	 * @param color		the color of the fade out
	 */
    FadeOut(JComponent s, Color color) {

        scene = s;

        this.radius = 700;
        setOpaque(false);
        timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radius -= 10;
                if (radius <= 0) {
                    timer.stop();
                    fading = false;
                }
                scene.repaint();
            }
        });
        this.color = color;
    }
	
	/**
	 * Starts the timer that triggers the fade out
	 */
    public void start() {
        fading = true;
        timer.start();
    }
	
	/**
	 * Draws the fade out, which is a screen of a solid color with a transparent circle in the middle.
	 * 
	 * @param g		the graphics instance that draws the fade out onto the screen
	 */
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        for (int i = 0; i < 800 - radius; i++) {
            g2.drawOval(400 - radius - i, 250 - radius - i, (i + radius) * 2, (i + radius) * 2);
        }


    }
}

