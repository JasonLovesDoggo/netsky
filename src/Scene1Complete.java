import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * This scene concludes the first part of the story and explains the concept of 
 * shortcut learning based on the robot\'s behavior in Scene 1.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1Complete extends BaseScene implements KeyListener {
	/** The index where the cow image should be visible */
    private static final int COW_IMAGE_STEP_INDEX = 3;
	/** The area where the explaination is */
    private JTextArea explanationArea;
	/** The button that takes the user to the next explaination */
    private JButton nextExplanationButton;
	/** The array of partial explainations */
    private ArrayList<String> explanationSteps;
	/** The current step that is being displayed */
    private int currentStep = 0;
	/** The label of the image */
    private ScaledImageLabel imageLabel;
	/** The cow image */
    private Image cowImage = null;
	/** The image from the dog scene */
    private Image dogSceneImage = null;
	/** The image from the bird scene */
    private Image birdImage = null;

	/** 
	 * Creates a new Scene1Complete with keylisteners
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1Complete(SceneManager sceneManager) {
        super(sceneManager);
        addKeyListener(this);
        setFocusable(true);
    }

	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Creates all of the components of the scene.
	 */
    @Override
    protected void initializeComponents() {
        SceneManager.continueEnabled = false;
        setLayout(new BorderLayout(10, 10));

        // Scene title with a subtle gradient background
        JPanel titlePanel = getTitlePanel();

        add(titlePanel, BorderLayout.NORTH);

        // Content steps - expanded, thorough explanations
        explanationSteps = new ArrayList<>(Arrays.asList(
                "In Scene 1, our robot was instructed to 'walk the dog,' but no dog was present. When asked, it showed you a leash. This seemingly odd response reveals an important concept in AI behavior called 'shortcut learning.'\n\nThe robot associated 'walking dogs' with 'leashes' but didn't understand the task truly requires a dog.",
                "When given a command, the robot attempted to perform it using the information it had available. By focusing on the leash (a strongly associated object) rather than recognizing the fundamental issue (no dog), it demonstrated a superficial understanding of the task.\n\nThis is similar to how a student might memorize formulas without understanding the underlying principles.",
                "This behavior demonstrates 'shortcut learning' - where AI systems learn superficial patterns or associations rather than deeper conceptual understanding. They find correlations that work for most training examples but fail when context changes or when faced with novel situations.",
                "Consider the following image of a cow in a pasture. An AI trained on similar images might learn to associate 'green open space + large animal' with 'farm animal in safe grazing environment' without truly understanding what makes a pasture suitable for grazing.",
                "If this AI later encounters an image of a well-maintained golf course, it might mistakenly classify it as 'safe grazing environment' because it learned the shortcut 'green open space' rather than understanding the actual requirements for animal grazing.\n\nSimilarly, our robot relied on 'leash = dog walking' without grasping that the dog is an essential component.",
                "Similarly, in the next scene, the robot was asked to 'save the bird'. But due to it's training, it associated the SKY with 'bird' so when it went to fly to the bird, it flew up into the sky instead of going to the tree where the bird was located.",
                "This doesn't mean the robot is malfunctioning in a traditional sense. Rather, its learning process was incomplete. It developed a shortcut understanding that seemed to work in most scenarios but failed when faced with unusual circumstances.\n\nThis is a fundamental challenge in developing robust AI systems.",
                "Understanding shortcut learning helps us build better AI systems by recognizing when they might be relying on superficial cues rather than deeper understanding. Click 'Continue to Next Scene' to see how our story continues."
        ));

        // Main explanation panel with soft background color
        JPanel explanationPanel = new JPanel(new BorderLayout(10, 10));
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
        explanationPanel.setBackground(new Color(248, 248, 252));

        // Visual display area (primarily for images) - increased height
        JPanel visualDisplayPanel = new JPanel();
        CardLayout visualCardLayout = new CardLayout();
        visualDisplayPanel.setLayout(visualCardLayout);
        visualDisplayPanel.setPreferredSize(new Dimension(800, 400)); // Increased height for more image space
        visualDisplayPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        visualDisplayPanel.setBackground(new Color(248, 248, 252));

        // Image display with proper sizing and no black borders
        imageLabel = new ScaledImageLabel();
        imageLabel.setBackground(new Color(248, 248, 252)); // Match panel background
        visualDisplayPanel.add(imageLabel, "IMAGE");
        explanationPanel.add(visualDisplayPanel, BorderLayout.CENTER);

        // Text area with elegant styling - reduced height
        explanationArea = new JTextArea();
        explanationArea.setWrapStyleWord(true);
        explanationArea.setLineWrap(true);
        explanationArea.setEditable(false);
        explanationArea.setFocusable(false);
        explanationArea.setFont(Palette.TEXT_FONT);
        explanationArea.setOpaque(true);
        explanationArea.setBackground(Palette.PANEL_BACKGROUND);
        explanationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Palette.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JScrollPane scrollPaneForTextArea = new JScrollPane(explanationArea);
        scrollPaneForTextArea.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneForTextArea.setPreferredSize(new Dimension(800, 120)); // Reduced height to give more space to images
        explanationPanel.add(scrollPaneForTextArea, BorderLayout.SOUTH);

        // Navigation button with clear styling
        nextExplanationButton = new JButton("Next");
        nextExplanationButton.setFont(Palette.BUTTON_FONT);
        nextExplanationButton.setBackground(Palette.BUTTON_PRIMARY);
        nextExplanationButton.setForeground(Palette.TEXT_ON_BUTTON);
        nextExplanationButton.setFocusPainted(false);
        nextExplanationButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        nextExplanationButton.addActionListener(e -> {
            if (currentStep < explanationSteps.size() - 1) {
                goToNextStep();
            } else {
                // Last click - transition to next scene
                sceneManager.showScene(Scene.SCENE_2A);
            }
        });

        // Button panel with gradient background
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 240, 250),
                        0, getHeight(), new Color(230, 230, 245));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.add(nextExplanationButton);

        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);
        buttonPanel.add(menuButton);

        add(explanationPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize first step
        updateStepDisplay(0);

        // Request focus to enable keyboard navigation
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

	/** 
	 * Creates and returns the title panel
	 * 
	 * @return 		the title panel created by this method
	 */
    private static JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel("Understanding the Robot's Behavior");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(70, 70, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Add keyboard hint to the title bar instead of taking up space in the main panel
        JLabel keyHint = new JLabel("(i: next, u: back)");
        keyHint.setFont(Palette.HINT_FONT);
        keyHint.setForeground(Palette.TEXT_SECONDARY);
        keyHint.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(keyHint, BorderLayout.SOUTH);
        return titlePanel;
    }
	
	/**
	 * Changes the display based on which step the scene has changed to
	 * 
	 * @param step		the new step
	 */
    private void updateStepDisplay(int step) {
        if (step < 0) {
            step = 0;
        } else if (step >= explanationSteps.size()) {
            // Last step - transition to next scene
            sceneManager.showScene(Scene.SCENE_2A);
            return;
        }

        currentStep = step;
        explanationArea.setText(explanationSteps.get(currentStep));
        explanationArea.setCaretPosition(0);

        // Display appropriate image for current step
        if (currentStep < COW_IMAGE_STEP_INDEX) {
            // For first few slides, show dog scene image
            if (dogSceneImage == null) {
                try {
                    URL imgUrl = new java.io.File("Images/sc/dogscene.png").toURI().toURL();
                    dogSceneImage = new ImageIcon(imgUrl).getImage();
                    if (dogSceneImage.getWidth(null) == -1) {
                        throw new RuntimeException("Image data not loaded or invalid.");
                    }
                } catch (Exception ex) {
                    System.err.println("Error loading dog scene image: " + ex.getMessage());
                }
            }
            imageLabel.setImage(dogSceneImage);
            imageLabel.setVisible(true);
        } else if (currentStep == COW_IMAGE_STEP_INDEX || currentStep == COW_IMAGE_STEP_INDEX + 1) {
            // For cow-specific slides, show cow image
            if (cowImage == null) {
                try {
                    URL imgUrl = new java.io.File("Images/sc/cow.jpg").toURI().toURL();
                    cowImage = new ImageIcon(imgUrl).getImage();
                    if (cowImage.getWidth(null) == -1) {
                        throw new RuntimeException("Image data not loaded or invalid.");
                    }
                } catch (Exception ex) {
                    System.err.println("Error loading cow image: " + ex.getMessage());
                    imageLabel.setText("Error: Cow image not found or failed to load.");
                    imageLabel.setImage(null);
                }
            }
            imageLabel.setImage(cowImage);
            imageLabel.setVisible(true);
        } else {
            // For bird-related slides, show bird image
            if (birdImage == null) {
                try {
                    URL imgUrl = new java.io.File("Images/sc/bird.png").toURI().toURL();
                    birdImage = new ImageIcon(imgUrl).getImage();
                    if (birdImage.getWidth(null) == -1) {
                        throw new RuntimeException("Image data not loaded or invalid.");
                    }
                } catch (Exception ex) {
                    System.err.println("Error loading bird image: " + ex.getMessage());
                    imageLabel.setText("Error: Bird image not found or failed to load.");
                    imageLabel.setImage(null);
                }
            }
            imageLabel.setImage(birdImage);
            imageLabel.setVisible(true);
        }

        // Update button text for last step
        if (currentStep == explanationSteps.size() - 1) {
            nextExplanationButton.setText("Continue to Next Scene");
            nextExplanationButton.setBackground(Palette.BUTTON_SUCCESS);
        } else {
            nextExplanationButton.setText("Next");
            nextExplanationButton.setBackground(Palette.BUTTON_PRIMARY);
        }
    }
	
	/** 
	 * Move to the next step
	 */
    private void goToNextStep() {
        updateStepDisplay(currentStep + 1);
    }
	
	/** 
	 * Go back to the previous step
	 */
    private void goToPreviousStep() {
        updateStepDisplay(currentStep - 1);
    }
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * Keeps the sceneManager from continuing until the scene is done. Resets to the first 
	 * step, and requests focus once again.
	 */
    @Override
    public void onShowScene() {
        super.onShowScene();
        SceneManager.continueEnabled = false;
        // Reset to the first step
        updateStepDisplay(0);
        // Request focus to enable keyboard navigation
        SwingUtilities.invokeLater(() -> requestFocusInWindow());
    }

    /**
	 * KeyListener implementation
	 */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
	
	/**
	 *	Keylistener implementation for keypressed. Whenever i or u is pressed, the step changes
	 */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'i':  // Forward navigation
                goToNextStep();
                break;
            case 'u':  // Back navigation
                goToPreviousStep();
                break;
        }
    }

	/**
	 * KeyListener implementation
	 */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    /**
	 * Image display component with proper scaling and background matching
	 */
    static class ScaledImageLabel extends JLabel {
		/** The image being shown */
        private Image image;

		/**
		 * creates a new ScaledImageLabel and aligns it horizontally and vertically
		 */
        public ScaledImageLabel() {
            super();
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setVerticalAlignment(SwingConstants.CENTER);
            this.setOpaque(true);
        }

		/** 
		 *	sets the image for this class
		 *
		 * @param img 		the new image for the class
		 */
        public void setImage(Image img) {
            this.image = img;
            this.setText(null);
            repaint();
        }

		/**
		 * Sets the text for this class
		 * 
		 * @param text		the new text for the class
		 */
        @Override
        public void setText(String text) {
            if (image == null) {
                super.setText(text);
            } else {
                super.setText(null);
            }
        }

		/**
		 * paints the image
		 * 
		 * @param g		the graphics instance that draws the image
		 */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill with background color (matching parent panel)
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());

            if (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                if (panelWidth <= 0 || panelHeight <= 0) return;

                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);

                // Calculate scale while preserving aspect ratio
                double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);

                // Center the image
                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                // Draw the image with high quality
                g2d.drawImage(image, x, y, scaledWidth, scaledHeight, this);

                // Optional: Add a subtle border around the image
                g2d.setColor(new Color(200, 200, 220));
                g2d.drawRect(x - 1, y - 1, scaledWidth + 1, scaledHeight + 1);
            } else if (getText() != null && !getText().isEmpty()) {
                // Display error text if needed
                g2d.setColor(Color.RED);
                FontMetrics fm = g2d.getFontMetrics();
                int stringWidth = fm.stringWidth(getText());
                int stringAscent = fm.getAscent();
                int x = (getWidth() - stringWidth) / 2;
                int y = (getHeight() + stringAscent) / 2;
                g2d.drawString(getText(), x, y);
            }
        }
    }
}
