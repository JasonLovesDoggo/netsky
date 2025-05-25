import java.awt.*;
import javax.swing.*;

public class ScrollingText extends JComponent {
    private final String text;
    private int y = 0;
    private Timer timer;
    private boolean firstPaint = true;
    private double scrollSpeed = 1.0;
    private double accumulatedScroll = 0.0;

    private static final int LINE_HEIGHT = 30;
    private static final int PAUSE_DISTANCE = 40;
    private static final int START_Y = 500;
    private static final int SCROLL_MULT = 2;
    private static final int MARGIN = 10;

    public ScrollingText(String text) {
        this.text = text;
        timer = new Timer(10, e -> {
            accumulatedScroll += scrollSpeed;
            int scroll = (int) accumulatedScroll;
            if (scroll > 0) {
                y += scroll;
                accumulatedScroll -= scroll;
                repaint();
            }

            int firstLineY = START_Y - (y * SCROLL_MULT);
            boolean shouldStop = (text == null || text.isEmpty()) ?
                    firstLineY < 0 : firstLineY - PAUSE_DISTANCE < LINE_HEIGHT;
            if (shouldStop) timer.stop();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (firstPaint) { firstPaint = false; return; }
        if (text == null || text.isEmpty()) return;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        int width = getWidth() - 2 * MARGIN;
        if (width <= 0) return;

        FontMetrics fm = g.getFontMetrics();
        int currentY = START_Y - (y * SCROLL_MULT);

        for (String paragraph : text.split("\\n")) {
            if (paragraph.isEmpty()) {
                currentY += LINE_HEIGHT;
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
                    }
                    line = new StringBuilder(word);
                    if (fm.stringWidth(word) > width) {
                        drawCentered(g, word, fm, width, currentY);
                        currentY += LINE_HEIGHT;
                        line = new StringBuilder();
                    }
                }
            }
            if (line.length() > 0) {
                drawCentered(g, line.toString(), fm, width, currentY);
                currentY += LINE_HEIGHT;
            }
        }
    }

    private void drawCentered(Graphics g, String text, FontMetrics fm, int width, int y) {
        int x = MARGIN + (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
	public void setScrollSpeed (double speed) {
		if (speed > 0) scrollSpeed = speed;
	}

    public void stopScroll() { timer.stop(); }
    public void setScrolling() { if (!timer.isRunning()) timer.start(); }
    public void setY(int y) { this.y = y; }
}