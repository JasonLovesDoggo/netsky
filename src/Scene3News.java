/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Breaking news scene #2 with dynamic scrolling text. Announces that umbrellas must be turned in
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scene3News extends BaseScene {
    private final Timer flashingTimer;
    private final Timer scrollCheckTimer;
    ScrollingText news;
    private boolean isFlashingRed = false;
    private JLabel breakingNewsLabel;
    private JButton nextSceneButton;

    public Scene3News(SceneManager sceneManager) {
        super(sceneManager);
        // Initialize flashing "BREAKING" effect
        flashingTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFlashingRed = !isFlashingRed;
                if (breakingNewsLabel != null) {
                    breakingNewsLabel.setForeground(isFlashingRed ?
                            new Color(220, 0, 0) : new Color(255, 60, 60));
                    breakingNewsLabel.repaint();
                }
            }
        });
        flashingTimer.start();

        // Create timer to check if scrolling is complete
        scrollCheckTimer = new Timer(100, e -> {
            if (news != null && news.isScrollingComplete() && nextSceneButton != null) {
                nextSceneButton.setEnabled(true);
                nextSceneButton.setToolTipText("Press to continue.");
                nextSceneButton.setForeground(Color.WHITE);
                nextSceneButton.setBackground(Palette.BUTTON_SUCCESS);
            }
        });
        scrollCheckTimer.start();
    }

    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout(0, 0));

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Simple dark blue background without gradient
                g.setColor(new Color(20, 20, 50));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Add a thin highlight line
                g.setColor(new Color(80, 80, 150));
                g.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(800, 80));

        // "BREAKING NEWS" label with bold, impactful font
        breakingNewsLabel = new JLabel("BREAKING NEWS");
        breakingNewsLabel.setFont(new Font("Impact", Font.BOLD, 36));
        breakingNewsLabel.setForeground(new Color(220, 0, 0));
        breakingNewsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(breakingNewsLabel, BorderLayout.CENTER);

        // Current time/date stamp
        JLabel timeLabel = new JLabel("JUNE 5, 2025 • LIVE");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        headerPanel.add(timeLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Dark background for news text
        JPanel contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dark background for news text - simple solid color
                g.setColor(new Color(5, 5, 20));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Simplified grid lines - fewer and lighter
                g.setColor(new Color(20, 20, 40));
                for (int i = 0; i < getWidth(); i += 40) {
                    g.drawLine(i, 0, i, getHeight());
                }
                for (int i = 0; i < getHeight(); i += 40) {
                    g.drawLine(0, i, getWidth(), i);
                }

                // Draw network logo - simplified and more opaque
                g.setColor(new Color(255, 255, 255, 50));
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("MNN", 30, 70);

                // Draw red "LIVE" indicator - simplified
                g.setColor(Color.RED);
                g.fillOval(getWidth() - 60, 30, 20, 20);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("LIVE", getWidth() - 35, 45);
            }
        };

        // Enhanced text content with more dramatic news
        news = new ScrollingText(
                "FEANOR HAS MANDATED THAT ALL UMBRELLAS BE TURNED IN TO THE NEAREST SERVICE BOT IMMEDIATELY.\n\n" +
                        "All citizens must give all their umbrellas to their nearest service bot," +
                        "or leave it on the curb to be collected by the garbage truck.\n\n" +
                        "When asked why, this is what FEANOR said: \n\n" +
                        "\"FEANOR seeks to protect all citizens from the impacts of heavy rainstorms. Thus, FEANOR's AI service robots are proceeding with the removal of all umbrellas.\"\n\n" +
                        "Citizens have already begun piling up their umbrellas, and soon, the entirety of the US will be umbrella-free!\n\n" +
                        "How an umbrella free future may lead us to a future safe from heavy rainstorms, experts do not know, but they fully support their new leader.\n\n" +
						"Make sure you comply immediately!" +
                        "");

        news.setScrollSpeed(0.6); // Slightly faster for dramatic effect
        news.setY(-28);
        news.setTextColor(new Color(220, 220, 255)); // Light blue-white text
        news.setFont(new Font("Impact", Font.BOLD, 32)); // Bolder, more impactful font

        contentPanel.add(news, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = getButtonPanel();
        buttonPanel.setBackground(new Color(10, 10, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        // Start scrolling
        news.setScrolling();
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(true);

        nextSceneButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1A);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        if (nextSceneButton != null) {
            // Initially disable the continue button until scrolling is complete
            nextSceneButton.setEnabled(false);
            nextSceneButton.setToolTipText("Please finish reading to continue.");
            nextSceneButton.setForeground(Palette.TEXT_DISABLED);
            nextSceneButton.setBackground(Palette.BUTTON_DISABLED);
            buttonPanel.add(nextSceneButton);
        }
        buttonPanel.add(menuButton);
        return buttonPanel;
    }

    @Override
    public void onShowScene() {
        // Reset the news scrolling position when the scene is shown
        if (news != null) {
            news.setY(-28);
            news.setScrolling();
        }
        // Restart the flashing effect
        if (flashingTimer != null && !flashingTimer.isRunning()) {
            flashingTimer.start();
        }
        // Restart the scroll check timer
        if (scrollCheckTimer != null && !scrollCheckTimer.isRunning()) {
            scrollCheckTimer.start();
        }
        // Reset the continue button to disabled
        if (nextSceneButton != null) {
            nextSceneButton.setToolTipText("Please finish reading to continue.");
            nextSceneButton.setEnabled(false);
            nextSceneButton.setForeground(Palette.TEXT_DISABLED);
            nextSceneButton.setBackground(Palette.BUTTON_DISABLED);
        }
        super.onShowScene();
    }

    // Stop the timers when this scene is no longer visible
    @Override
    public void onHideScene() {
        super.onHideScene();
        if (flashingTimer != null && flashingTimer.isRunning()) {
            flashingTimer.stop();
        }
        if (scrollCheckTimer != null && scrollCheckTimer.isRunning()) {
            scrollCheckTimer.stop();
        }
    }
}
