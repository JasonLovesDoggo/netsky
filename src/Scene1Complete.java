/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: This scene concludes the first part of the story and explains the concept of
 *              shortcut learning based on the robot\'s behavior in Scene 1.
 */

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Scene1Complete extends BaseScene {

    private JTextArea explanationArea;
    private JButton nextExplanationButton; // For stepping through explanation
    private ArrayList<String> explanationSteps;
    private int currentStep = 0;
    private static int COW_IMAGE_STEP_INDEX = 3; // Index for the cow image step

    private JPanel explanationPanel; // Holds visuals, text area, and step button
    private JPanel visualDisplayPanel; // Panel to switch between diagram and cow image
    private CardLayout visualCardLayout;
    private ShortcutDiagramPanel diagramPanel; // Custom diagram panel
    private ScaledImageLabel cowImageLabel;    // Custom label for scaled cow image
    private Image cowImage = null; // Loaded cow image

    private JPanel mainButtonPanel; // Panel for "Menu" button
    private JButton menuButton;

    public Scene1Complete(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        SceneManager.continueEnabled = false; // Disable main continue button for this scene
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Scene 1 Debrief: Deep Dive into Shortcut Learning");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Expanded and more detailed explanation steps
        explanationSteps = new ArrayList<>(Arrays.asList(
                "Welcome to the debrief for Scene 1. We observed our robot behaving unexpectedly. Let\'s explore why.\n\nWhen the robot was instructed to \'walk the dog,\' it encountered a fundamental issue: no dog was present. Its response – showing you the leash – might seem odd, but it\'s a key indicator of a common AI phenomenon.",
                "The robot\'s action of presenting the leash was its attempt to make sense of the command with incomplete information. It essentially took a \'shortcut.\' Instead of recognizing the absence of the primary subject (the dog) and reporting an issue, it focused on a strongly associated object (the leash). This happened because its understanding was likely based on superficial correlations in its training, not a deep, contextual grasp of the task \'walking a dog.\'.",
                "This behavior is a classic example of \'Shortcut Learning\' in Artificial Intelligence. AI models, especially complex ones, can inadvertently learn to exploit unintended patterns or superficial cues in their training data. These shortcuts allow them to achieve high performance on familiar data but often lead to failures when faced with new, slightly different, or more nuanced situations. They haven\'t learned the true underlying concept, just a trick to get the right answer most of the time during training.",
                "Consider the image you are about to see. This picture of a cow in a field is typical of what an AI might be trained on to identify, for example, \'farm animals in their natural, safe grazing environments.\' The AI might learn to associate \'green expanse + animal shape\' with this concept.", // Text before cow image
                "Now, if this AI, trained on many images like the one of the cow, encounters a detailed photograph of a lush, green, but entirely artificial, golf course, it might incorrectly classify it as a \'safe grazing environment.\' Why? Because it took a shortcut: \'green and open\' became the dominant feature it learned, rather than the actual presence of edible grass, the absence of human activity, or other subtle contextual cues that define a true pasture. It didn\'t understand the *concept* of a pasture, only some visual correlates. This is directly analogous to our robot focusing on the \'leash\' without the \'dog.\'", // Text for after cow image, explaining it
                "The consequence of shortcut learning is that the AI\'s model of the world is brittle. When a new, unexpected stimulus or task variation occurs – like the injured bird appearing in Scene 1 – the robot\'s superficially understood \'dog-walking\' task was easily abandoned or overridden. The initial programming lacked the robustness to handle this novel situation gracefully or to prioritize effectively because its core understanding was flawed.",
                "It\'s crucial to understand that this doesn\'t mean the robot (or AI in general) is \'bad\' or inherently \'stupid.\' Rather, its learning process was incomplete or its training data didn\'t sufficiently cover the complexities of the real world. Designing AI systems that avoid these undesirable shortcuts and develop a more profound, generalizable understanding of tasks and their contexts is one of the most significant and ongoing challenges in AI research today.",
                "You\'ve now explored a practical example of how AI can behave in unexpected ways due to shortcut learning. This understanding is vital as we develop and interact with more sophisticated AI systems. Click \'Continue to Next Scene\' to see how the story unfolds."
        ));

        // Main panel for the explanation interface
        explanationPanel = new JPanel(new BorderLayout(10, 15)); // Added more vertical gap
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Visual display area (for diagrams or full-screen image)
        visualDisplayPanel = new JPanel();
        visualCardLayout = new CardLayout();
        visualDisplayPanel.setLayout(visualCardLayout);
        visualDisplayPanel.setPreferredSize(new Dimension(800, 350)); // Give it a good default size

        diagramPanel = new ShortcutDiagramPanel();
        visualDisplayPanel.add(diagramPanel, "DIAGRAM");

        cowImageLabel = new ScaledImageLabel();
        visualDisplayPanel.add(cowImageLabel, "COWIMAGE");
        explanationPanel.add(visualDisplayPanel, BorderLayout.CENTER);

        // Text area for explanations
        explanationArea = new JTextArea();
        explanationArea.setWrapStyleWord(true);
        explanationArea.setLineWrap(true);
        explanationArea.setEditable(false);
        explanationArea.setFocusable(false);
        explanationArea.setFont(new Font("Arial", Font.PLAIN, 16));
        explanationArea.setOpaque(true);
        explanationArea.setBackground(new Color(248, 248, 248));
        explanationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scrollPaneForTextArea = new JScrollPane(explanationArea);
        scrollPaneForTextArea.setPreferredSize(new Dimension(800, 150)); // Adjust height for text
        explanationPanel.add(scrollPaneForTextArea, BorderLayout.SOUTH);

        // Button to step through explanation
        nextExplanationButton = new JButton("Next");
        nextExplanationButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextExplanationButton.addActionListener(e -> {
            currentStep++;
            if (currentStep < explanationSteps.size()) {
                String currentStepTextContent = explanationSteps.get(currentStep);
                explanationArea.setText(currentStepTextContent);
                explanationArea.setCaretPosition(0); // Scroll to top

                if (currentStep == COW_IMAGE_STEP_INDEX) {
                    visualCardLayout.show(visualDisplayPanel, "COWIMAGE");
                    if (cowImage == null) { // Load image once
                        try {
                            // Path relative to the execution directory (project root)
                            URL imgUrl = new java.io.File("Images/sc/cow.jpg").toURI().toURL();
                            cowImage = new ImageIcon(imgUrl).getImage();
                            if (cowImage.getWidth(null) == -1) { // Check if image loaded successfully
                                throw new RuntimeException("Image data not loaded or invalid.");
                            }
                            cowImageLabel.setImage(cowImage);
                        } catch (Exception ex) {
                            System.err.println("Error loading cow image: Images/sc/cow.jpg - " + ex.getMessage());
                            // In case of error, show text in the image label
                            cowImageLabel.setText("Error: Cow image (Images/sc/cow.jpg) not found or failed to load.");
                            cowImageLabel.setImage(null); // Clear any previous image
                        }
                    }
                } else {
                    visualCardLayout.show(visualDisplayPanel, "DIAGRAM");
                    int diagramTypeIndex = currentStep;
                    if (currentStep > COW_IMAGE_STEP_INDEX) {
                        diagramTypeIndex--; // Adjust index if past cow image step
                    }
                    // Simplified diagram indexing, assuming 4 diagram types (0-3)
                    // And cow image is step 3 (COW_IMAGE_STEP_INDEX)
                    // Step 0 -> Diagram 0
                    // Step 1 -> Diagram 1
                    // Step 2 -> Diagram 2
                    // Step 3 is COW_IMAGE
                    // Step 4 -> Diagram 3 (diagramTypeIndex = 4-1 = 3)
                    // Step 5 -> Diagram 0 (diagramTypeIndex = 5-1 = 4 -> 4 % 4 = 0 or handle max)
                    // This logic needs to map correctly to the available diagrams
                    if (diagramTypeIndex == 0) diagramPanel.setDiagramType(0);       // Robot/Leash
                    else if (diagramTypeIndex == 1)
                        diagramPanel.setDiagramType(0); // Still robot/leash or early concept
                    else if (diagramTypeIndex == 2) diagramPanel.setDiagramType(1); // Shortcut Concept
                        // COW_IMAGE_STEP_INDEX is 3. currentStep 4 means diagramTypeIndex 3.
                    else if (diagramTypeIndex == 3)
                        diagramPanel.setDiagramType(2); // New Situation/Failure (after cow image explanation)
                    else if (diagramTypeIndex == 4) diagramPanel.setDiagramType(2); // Still new situation
                    else if (diagramTypeIndex == 5) diagramPanel.setDiagramType(3); // Concluding Diagram
                    else diagramPanel.setDiagramType(3); // Default to concluding for later steps
                }

                if (currentStep == explanationSteps.size() - 1) {
                    nextExplanationButton.setText("Continue to Next Scene");
                } else {
                    nextExplanationButton.setText("Next");
                }
            } else {
                // Last click, transition to next scene
                sceneManager.showScene(Scene.SCENE_2A);
            }
        });

        JPanel southButtonFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southButtonFlowPanel.add(nextExplanationButton);
        // Add this flow panel to the main explanationPanel, below the text area scroll pane.
        // To do this, we need another panel or change explanationPanel layout.
        // For simplicity, let\'s add it to the main scene\'s SOUTH, separate from explanationPanel content.

        add(explanationPanel, BorderLayout.CENTER);

        // Panel for navigation buttons (Menu, and the explanation step button)
        mainButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainButtonPanel.add(nextExplanationButton); // Add the explanation step button here
        menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);
        mainButtonPanel.add(menuButton);
        add(mainButtonPanel, BorderLayout.SOUTH);

        // Initialize first step
        explanationArea.setText(explanationSteps.get(0));
        explanationArea.setCaretPosition(0);
        diagramPanel.setDiagramType(0);
        visualCardLayout.show(visualDisplayPanel, "DIAGRAM");
    }

    @Override
    public void onShowScene() {
        super.onShowScene();
        SceneManager.continueEnabled = false; // Ensure main continue is off
        currentStep = 0;

        if (explanationArea != null && explanationSteps != null && !explanationSteps.isEmpty()) {
            explanationArea.setText(explanationSteps.get(currentStep));
            explanationArea.setCaretPosition(0);
        }
        if (diagramPanel != null) {
            diagramPanel.setDiagramType(0); // Reset to first diagram
        }
        if (visualCardLayout != null && visualDisplayPanel != null) {
            visualCardLayout.show(visualDisplayPanel, "DIAGRAM"); // Show diagram first
        }
        if (nextExplanationButton != null) {
            nextExplanationButton.setText("Next");
        }
        // The mainButtonPanel (with nextExplanationButton and menuButton) is already configured.
    }

    // Inner class for drawing diagrams (same as before, ensure it exists and is correct)
    class ShortcutDiagramPanel extends JComponent {
        private int diagramType = 0; // 0: Robot/Leash, 1: Shortcut Concept, 2: New Situation/Failure, 3: Concluding

        public ShortcutDiagramPanel() {
            setPreferredSize(new Dimension(280, 250));
            setBackground(Color.WHITE);
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        public void setDiagramType(int type) {
            this.diagramType = type % 4; // Ensure it cycles through 0-3 if index goes higher
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.DARK_GRAY);
            //g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Border is now on component

            switch (diagramType) {
                case 0:
                    drawRobotLeashDiagram(g2);
                    break;
                case 1:
                    drawShortcutConceptDiagram(g2);
                    break;
                case 2:
                    drawNewSituationDiagram(g2);
                    break;
                case 3:
                    drawConcludingDiagram(g2);
                    break;
                default:
                    g2.setColor(Color.BLACK);
                    g2.drawString("Diagram " + diagramType, 20, getHeight() / 2);
                    break;
            }
        }

        private void drawRobotLeashDiagram(Graphics2D g2) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Robot & The Leash Problem", 10, 20);

            // Simple robot
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(panelWidth / 2 - 70, panelHeight / 2 - 30, 60, 80); // Body
            g2.fillOval(panelWidth / 2 - 60, panelHeight / 2 - 60, 40, 40);  // Head
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(panelWidth / 2 - 50, panelHeight / 2 + 50, 20, 40); // Legs

            // Leash
            g2.setColor(Color.ORANGE);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(panelWidth / 2 - 10, panelHeight / 2 - 10, panelWidth / 2 + 30, panelHeight / 2 - 10);
            g2.drawArc(panelWidth / 2 + 30, panelHeight / 2 - 20, 20, 20, 0, -180);
            g2.drawLine(panelWidth / 2 + 40, panelHeight / 2 - 10, panelWidth / 2 + 40, panelHeight / 2 + 20);

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("? DOG", panelWidth / 2 + 50, panelHeight / 2 + 10);

            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setColor(Color.BLACK);
            g2.drawString("Task: Walk Dog", 20, panelHeight - 35);
            g2.drawString("Robot shows leash (shortcut)", 20, panelHeight - 20);
        }

        private void drawShortcutConceptDiagram(Graphics2D g2) {
            int w = getWidth();
            int h = getHeight();
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Shortcut Learning Path", 10, 20);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.setStroke(new BasicStroke(1.5f));

            g2.setColor(Color.BLUE);
            g2.drawString("Problem: Understand Task", w / 2 - 110, 45);
            g2.drawRect(w / 2 - 115, 25, 110, 30);
            g2.setColor(Color.BLACK);
            g2.drawLine(w / 2 - 60, 55, w / 2 - 60, 75);

            g2.setColor(new Color(0, 100, 0)); // Dark Green
            g2.drawString("Path A: Deep Learning", w / 2 - 125, 90);
            g2.drawArc(w / 2 - 105, 75, 50, 50, 180, 90);
            g2.drawLine(w / 2 - 105, 100, w / 2 - 105, 140);
            g2.drawArc(w / 2 - 105, 140, 50, 50, 270, -90);
            g2.drawRect(w / 2 - 110, 190, 100, 30);
            g2.drawString("Robust Solution", w / 2 - 100, 210);

            g2.setColor(Color.RED);
            g2.drawString("Path B: Shortcut", w / 2 + 35, 90);
            Stroke dashed = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
            g2.setStroke(dashed);
            g2.drawLine(w / 2 - 60, 65, w / 2 + 50, 65);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRect(w / 2 + 25, 50, 100, 30);
            g2.drawString("Superficial Cue", w / 2 + 30, 70);

            g2.drawLine(w / 2 + 75, 80, w / 2 + 75, 110);
            g2.drawRect(w / 2 + 25, 110, 100, 40);
            g2.drawString("Apparent Solution", w / 2 + 30, 130);
            g2.setFont(new Font("Arial", Font.ITALIC, 10));
            g2.drawString("(Brittle, Fails Easily)", w / 2 + 30, 145);

            g2.setStroke(new BasicStroke(1));
        }

        private void drawNewSituationDiagram(Graphics2D g2) {
            int w = getWidth();
            int h = getHeight();
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Shortcut Failure Example", 10, 20);
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setStroke(new BasicStroke(1.5f));

            g2.setColor(Color.RED);
            g2.drawRect(w / 2 - 75, 40, 150, 40);
            g2.drawString("AI (Shortcut-Learned)", w / 2 - 65, 65);

            g2.setColor(Color.BLACK);
            drawArrow(g2, w / 2, 80, w / 2, 110);

            g2.setColor(new Color(255, 165, 0)); // Orange
            g2.drawRect(w / 2 - 85, 120, 170, 40);
            g2.drawString("New/Unexpected Situation", w / 2 - 80, 145);

            g2.setColor(Color.BLACK);
            drawArrow(g2, w / 2, 160, w / 2, 190);

            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(w / 2 - 75, 200, 150, 40);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("SYSTEM FAILURE /", w / 2 - 60, 220);
            g2.drawString("UNEXPECTED OUTPUT", w / 2 - 70, 235);

            g2.setStroke(new BasicStroke(1));
        }

        private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
            g2.drawLine(x1, y1, x2, y2);
            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx * dx + dy * dy);
            if (D == 0) return; // Avoid division by zero
            double xm = D - 10, xn = xm, ym = 5, yn = -5, x;
            double sin = dy / D, cos = dx / D;

            x = xm * cos - ym * sin + x1;
            ym = xm * sin + ym * cos + y1;
            xm = x;

            x = xn * cos - yn * sin + x1;
            yn = xn * sin + yn * cos + y1;
            xn = x;

            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};
            g2.fillPolygon(xpoints, ypoints, 3);
        }

        private void drawConcludingDiagram(Graphics2D g2) {
            int w = getWidth();
            int h = getHeight();
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Key Takeaways", 10, 20);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));

            String[] lines = {
                    "AI needs deep understanding,",
                    "not just superficial tricks,",
                    "for reliable & safe behavior.",
                    "Context is crucial!"
            };

            FontMetrics fm = g2.getFontMetrics();
            int y = h / 2 - (fm.getHeight() * lines.length) / 2 + fm.getAscent() - 20;

            for (String line : lines) {
                g2.drawString(line, w / 2 - fm.stringWidth(line) / 2, y);
                y += fm.getHeight() * 1.3;
            }

            g2.setColor(new Color(0, 150, 200)); // Brain/Lightbulb color
            g2.fillOval(w / 2 - 20, h / 2 - 70, 40, 40); // Lightbulb base
            g2.setColor(Color.YELLOW);
            for (int i = 0; i < 8; i++) {
                double angle = Math.PI / 4 * i;
                g2.drawLine(w / 2, h / 2 - 50,
                        (int) (w / 2 + 25 * Math.cos(angle)),
                        (int) (h / 2 - 50 + 25 * Math.sin(angle)));
            }
        }
    }

    // Inner class for displaying a scaled image
    class ScaledImageLabel extends JLabel {
        private Image image;

        public ScaledImageLabel() {
            super();
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setVerticalAlignment(SwingConstants.CENTER);
            this.setBackground(Color.BLACK); // Background for the image area
            this.setOpaque(true);
        }

        public void setImage(Image img) {
            this.image = img;
            this.setText(null); // Clear any error text if image is set
            repaint();
        }

        @Override
        public void setText(String text) {
            if (image == null) { // Only show text if there's no image (e.g., for errors)
                super.setText(text);
            } else {
                super.setText(null); // Ensure no text when image is present
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null && image.getWidth(null) > 0 && image.getHeight(null) > 0) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                if (panelWidth <= 0 || panelHeight <= 0) return; // Not visible or no size yet

                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);

                double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);

                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
            } else if (getText() != null && !getText().isEmpty()) {
                // If there's text (e.g. error message) and no image, let JLabel paint it.
                // Ensure text is visible against background
                g.setColor(Color.WHITE);
                FontMetrics fm = g.getFontMetrics();
                int stringWidth = fm.stringWidth(getText());
                int stringAscent = fm.getAscent();
                int x = (getWidth() - stringWidth) / 2;
                int y = (getHeight() + stringAscent) / 2;
                g.drawString(getText(), x, y);
            }
        }
    }
}
