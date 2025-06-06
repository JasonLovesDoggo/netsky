/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Component that displays vertically scrolling text with customizable styling
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScrollingText extends JComponent implements MouseWheelListener {
    private static final int PAUSE_DISTANCE = 40;
    private static final int START_Y = 500;
    private static final int SCROLL_MULT = 2;
    private static final int MARGIN = 10;
    private final String text;
    private int y = 0;
    private final Timer timer;
    private boolean firstPaint = true;
    private double scrollSpeed = 1.0;
    private double accumulatedScroll = 0.0;
    private Color textColor = Color.BLACK;
    private Font textFont = new Font("Times New Roman", Font.PLAIN, 30);
    private static int LINE_HEIGHT = 30;
    private boolean autoScrolling = true;
    private int totalTextHeight = 0;

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

    private void drawCentered(Graphics g, String text, FontMetrics fm, int width, int y) {
        int x = MARGIN + (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }

    public void setScrollSpeed(double speed) {
        if (speed > 0) scrollSpeed = speed;
    }

    public void setScrolling() {
        autoScrolling = true;
        timer.start();
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setFont(Font font) {
        this.textFont = font;
        int fontSize = font.getSize();
        LINE_HEIGHT = Math.max(30, fontSize + 10);
    }

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

    public boolean isScrollingComplete() {
        return !autoScrolling;
    }

    public void resetScroll() {
        y = 0;
        autoScrolling = true;
        repaint();
    }
}