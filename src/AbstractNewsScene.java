import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * An abstract class that provides layout and behavior for news scenes with flashing headlines and scrolling text.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: Jun 9th, 2025
 * ICS4U0
 * Ms. Krasteva 
 */
public abstract class AbstractNewsScene extends BaseScene {
	/** The timer that controls when the text flashes */
    private final Timer flashingTimer;
	/** The timer that checks if the scrolling is complete */
    private final Timer scrollCheckTimer;
	/** The instance of ScrollingText used to implement the actual text scrolling*/
    ScrollingText news; 
	/** Keeps track of whether the breaking news label is bright or not, to create the flashing "BREAKING" effect*/
    private boolean isFlashingRed = false;
	/** The Jlabel that contains the text "BREAKING NEWS" at the top of the page*/
    private JLabel breakingNewsLabel;
	/** The JButton that allows the user to move on to the next scene */
    protected JButton nextSceneButton;
	
	/** The constructor that creates the AbstractNewsScene. 
	 * It initializes the flashing effect and also starts and creates the timer that 
	 * checks if scrolling is complete. 
	 * 
	 * @param sceneManager		The sceneManager instance that manages all of the scenes in the entire program
	 */
    public AbstractNewsScene(SceneManager sceneManager) {
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
                nextSceneButton.setText("Continue");
                nextSceneButton.setToolTipText("Press to continue.");
                nextSceneButton.setForeground(Color.WHITE);
                nextSceneButton.setBackground(Palette.BUTTON_SUCCESS);
            }
        });
        scrollCheckTimer.start();
    }
	
	/** 
	 * The method called to initalize components when the scene is added to sceneManager.
	 * It draws out the breakingNewsLabel and the time label that shows up on the right corner.
	 * It also creates a dark background for the news text, a network logo, and a red "LIVE"
	 * indicator. It adds the buttons to the bottom of the window and starts the scrolling. 
	 */
    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout(0, 0));

        JPanel headerPanel = getHeaderPanel();

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
                g.setFont(Palette.BUTTON_FONT);
                g.drawString("LIVE", getWidth() - 35, 45);
            }
        };

        // Enhanced text content with more dramatic news
        news = getNews();

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
	
	/** 
	 * This creates the header panel, which is a separate JPanel. 
	 * 
	 * @return 	the created header panel so that it can be added to the frame.
	 */
    private JPanel getHeaderPanel() {
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
        return headerPanel;
    }
	
	/** 
	 * This creates the JPanel that holds all the buttons, at the bottom of the screen. 
	 * @return 	the button panel so that it can be added to the frame.
	 */
	protected JPanel getButtonPanel() {
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
	
	/** 
	 * Resets all the different effects whenever the scene is shown. It resets the news scrolling
	 * position, the flashing effect, scroll check timer, and the continue button, which cannot be 
	 * clicked until the scroll timer has indicated that the scrolling is complete. 
	 */
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

    /**
	 * Stops all the timers used by this class once the scene is hidden and the user has moved
	 * on to another scene.
	 */
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
	
	/** 
	 * Abstract method to get the news content
	 * 
	 * @return 	an instance of ScrollingText, that contains the news content
	 */
    protected abstract ScrollingText getNews();
}
