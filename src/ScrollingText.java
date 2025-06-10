import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Component that displays vertically scrolling text with customizable styling
 *
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class ScrollingText extends JComponent implements MouseWheelListener {
	/** the distance from the top that the text should pause */
    private static final int PAUSE_DISTANCE = 40;
	/** The starting y position of the text */
    private static final int START_Y = 500;
	/** The speed at which the text scrolls */
    private static final int SCROLL_MULT = 2;
	/** The margin the edges and the text */
    private static final int MARGIN = 10;
	/** The line height */
    private static int LINE_HEIGHT = 30;
	/** The text that is scrolling */
    private final String text;
	/** the timer that controlls scrolling*/
    private final Timer timer;
	/** Y location of the text */
    private int y = 0;
	/** Flag that is true if it is the first time painted, and false otherwise. */
    private boolean firstPaint = true;
	/** the scroll speed of the text */
    private double scrollSpeed = 1.0;
	/** the total distance scrolled automatically */
    private double accumulatedScroll = 0.0;
	/** The color of the text */
    private Color textColor = Color.BLACK;
	/** The font for the text */
    private Font textFont = new Font("Times New Roman", Font.PLAIN, 30);
	/** Whether or not autoscrolling is currently happening */
    private boolean autoScrolling = true;
	/** The total height of all the text combined */
    private int totalTextHeight = 0;

	/**
	 * Creates a new ScrollingText and defines the timers
	 * 
	 * @param text		the text that is scrolling
	 */
    public ScrollingText(String text) {
        this.text = text;
        this.addMouseWheelListener(this);

        timer = new Timer(10, e -> {
            if (autoScrolling) {
                accumulatedScroll += scrollSpeed;
                int scroll = (int) accumulatedScroll;
                if (scroll > 0) {
                    y += scroll;
                    accumulatedScroll -= scroll;
                    repaint();
                }

                // Don't stop the timer anymore - just pause auto-scrolling when we reach the end
                int firstLineY = START_Y - (y * SCROLL_MULT);
                if (firstLineY - PAUSE_DISTANCE < -totalTextHeight) {
                    autoScrolling = false;
                }
            }
        });
    }
	
	/**
	 * draws everything if it isn't the first paint of the text
	 * 
	 * @param g		the graphics instance that draws the text  
	 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (firstPaint) {
            firstPaint = false;
            return;
        }
        if (text == null || text.isEmpty()) return;

        g.setColor(textColor);
        g.setFont(textFont);

        int width = getWidth() - 2 * MARGIN;
        if (width <= 0) return;

        FontMetrics fm = g.getFontMetrics();
        int currentY = START_Y - (y * SCROLL_MULT);
        totalTextHeight = 0;

        for (String paragraph : text.split("\n")) {
            if (paragraph.isEmpty()) {
                currentY += LINE_HEIGHT;
                totalTextHeight += LINE_HEIGHT;
                continue;
            }

            StringBuilder line = new StringBuilder();
            for (String word : paragraph.split(" ")) {
                String test = line.length() == 0 ? word : line + " " + word;
                if (fm.stringWidth(test) <= width) {
                    if (line.length() > 0) line.append(" ");
                    line.append(word);
                } else {
                    if (line.length() > 0) {
                        drawCentered(g, line.toString(), fm, width, currentY);
                        currentY += LINE_HEIGHT;
                        totalTextHeight += LINE_HEIGHT;
                    }
                    line = new StringBuilder(word);
                    if (fm.stringWidth(word) > width) {
                        drawCentered(g, word, fm, width, currentY);
                        currentY += LINE_HEIGHT;
                        totalTextHeight += LINE_HEIGHT;
                        line = new StringBuilder();
                    }
                }
            }
            if (line.length() > 0) {
                drawCentered(g, line.toString(), fm, width, currentY);
                currentY += LINE_HEIGHT;
                totalTextHeight += LINE_HEIGHT;
            }
        }

        // Draw instructions when auto-scrolling stops
        if (!autoScrolling) {
            g.setColor(new Color(255, 255, 255, 150));
            g.setFont(new Font("Arial", Font.BOLD, 16));
            String instructions = "Use mouse wheel to scroll up/down";
            int instructX = (getWidth() - fm.stringWidth(instructions)) / 2;
            g.drawString(instructions, instructX, getHeight() - 20);
        }
    }
	
	/**
	 * draws the text centered 
	 * 
	 * @param g 		the graphics instance that draws the text
	 * @param text		the text that is being drawn
	 * @param fm		the fontmetrics that allows the program to determine the width of the font
	 * @param width		the width of the text
	 * @param y			the y location of the text
	 */
    private void drawCentered(Graphics g, String text, FontMetrics fm, int width, int y) {
        int x = MARGIN + (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
	
	/**
	 * Sets the scroll speed
	  * 
	  * @param speed 		the new value of the scroll speed
	 */
    public void setScrollSpeed(double speed) {
        if (speed > 0) scrollSpeed = speed;
    }
	
	/** 
	 *	Sets autoscrolling and starts the timer for autoscrolling
	 */
    public void setScrolling() {
        autoScrolling = true;
        timer.start();
    }

	/** 
	 * sets the y location of the text
	 * 
	 * @param y		the new y location of the text 
	 */
    public void setY(int y) {
        this.y = y;
    }

	/** 
	 * Sets the new text color
	 * 
	 * @param color		the color of the new text
	 */
    public void setTextColor(Color color) {
        this.textColor = color;
    }

	/**
	 * Sets the font to a new font
	 * 
	 * @param font		the new font for the text 
	 */
    public void setFont(Font font) {
        this.textFont = font;
        int fontSize = font.getSize();
        LINE_HEIGHT = Math.max(30, fontSize + 10);
    }

	/** 
	 * Enable manual scrolling. Called if the mouse wheel is moved
	 */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Stop auto-scrolling when user interacts with the mouse wheel
        autoScrolling = false;

        // Manual scrolling - move the text up or down based on wheel rotation
        int notches = -e.getWheelRotation();
        y -= notches * 5; // Adjust the multiplier for faster/slower scrolling

        // Prevent scrolling past the beginning of the text
        if (y < 0) y = 0;

        // Allow scrolling down but prevent extreme values
        if (y > 3000) y = 3000; // Set a reasonable maximum limit

        repaint();
    }

	/**
	 * Gets whether or not autoscrolling is done
	 * 
	 * @return 	true if autoscrolling is done, false otherwise
	 */
    public boolean isScrollingComplete() {
        return !autoScrolling;
    }
}