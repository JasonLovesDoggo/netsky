import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class News extends JComponent {
    String text;
    int y; // Current scroll position
    Timer timer;
    int count; // Delays initial drawing

    double scrollSpeed = 1.0;
    private double accumulatedScroll = 0.0;

    private static final int LINE_HEIGHT = 30;
    private static final int PAUSE_DISTANCE = 40; // Distance from the top of the screen to pause scrolling
    private static final int START_Y_BASE = 500; // Initial Y position of the first line of the text block
    private static final int Y_SCROLL_MULTIPLIER = 2; // Visual pixels scrolled per logical unit of 'y'

    News(String text) {
        this.text = text;
        this.y = 0;

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accumulatedScroll += scrollSpeed;
                int logicalScrollAmount = (int) accumulatedScroll;

                if (logicalScrollAmount > 0) {
                    y += logicalScrollAmount;
                    accumulatedScroll -= logicalScrollAmount;
                    repaint();
                }

                if (News.this.text != null && !News.this.text.isEmpty()) {
                    int firstLineBaselineY = START_Y_BASE - (y * Y_SCROLL_MULTIPLIER);
                    if (firstLineBaselineY - PAUSE_DISTANCE < LINE_HEIGHT) {
                        timer.stop();
                    }
                } else {
                    if (START_Y_BASE - (y * Y_SCROLL_MULTIPLIER) < 0) {
                        timer.stop();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (count == 0) {
            count++;
            return;
        }

        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        if (text == null || text.isEmpty()) {
            return;
        }

        int componentWidth = getWidth();
        int margin = 10; // Margin on left and right
        int wrappingWidth = componentWidth - (2 * margin);

        if (wrappingWidth <= 0) { // Not enough space to draw
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        int currentLineDrawingY = START_Y_BASE - (y * Y_SCROLL_MULTIPLIER);

        String[] paragraphs = text.split("\\n"); // Split text by newline characters

        for (String paragraph : paragraphs) {
            if (paragraph.isEmpty()) {
                currentLineDrawingY += LINE_HEIGHT; // Account for blank lines
                continue;
            }

            String[] words = paragraph.split(" ");
            StringBuilder lineBuffer = new StringBuilder();

            for (String word : words) {
                String testLine = (lineBuffer.length() == 0) ? word : lineBuffer.toString() + " " + word;

                if (fm.stringWidth(testLine) <= wrappingWidth) {
                    if (lineBuffer.length() > 0) {
                        lineBuffer.append(" ");
                    }
                    lineBuffer.append(word);
                } else {
                    // Draw the current line buffer if it has content
                    currentLineDrawingY = getCurrentLineDrawingY(g, margin, wrappingWidth, fm, currentLineDrawingY, lineBuffer);
                    // Start a new line with the current word
                    lineBuffer = new StringBuilder(word);

                    // If the word itself is wider than the wrapping width, draw it centered and clear buffer
                    if (fm.stringWidth(word) > wrappingWidth) {
                        String wordToDraw = lineBuffer.toString(); // current word
                        int wordWidth = fm.stringWidth(wordToDraw);
                        int wordDrawX = margin + (wrappingWidth - wordWidth) / 2;
                        g.drawString(wordToDraw, wordDrawX, currentLineDrawingY);
                        currentLineDrawingY += LINE_HEIGHT;
                        lineBuffer = new StringBuilder(); // Word has been drawn
                    }
                }
            }

            // Draw any remaining text in the line buffer for the current paragraph
            currentLineDrawingY = getCurrentLineDrawingY(g, margin, wrappingWidth, fm, currentLineDrawingY, lineBuffer);
        }
    }

    private int getCurrentLineDrawingY(Graphics g, int margin, int wrappingWidth, FontMetrics fm, int currentLineDrawingY, StringBuilder lineBuffer) {
        if (lineBuffer.length() > 0) {
            String lineToDraw = lineBuffer.toString();
            int lineWidth = fm.stringWidth(lineToDraw);
            int lineDrawX = margin + (wrappingWidth - lineWidth) / 2;
            g.drawString(lineToDraw, lineDrawX, currentLineDrawingY);
            currentLineDrawingY += LINE_HEIGHT;
        }
        return currentLineDrawingY;
    }

    public void scroll() {
        this.y = 0; // Reset scroll position to the beginning
        this.accumulatedScroll = 0.0; // Reset accumulator
        timer.start();
    }

    public void stopScroll() {
        timer.stop();
    }

    public void continueScroll() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }
}
