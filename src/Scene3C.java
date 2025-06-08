/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: This scene explains the shortcut learning concept based on the AI's behavior
 *              in Scene 3A and 3B where it mistakenly associated umbrellas with rainstorms.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Scene3C extends BaseScene implements KeyListener {
    private JTextArea explanationArea;
    private JButton nextExplanationButton;
    private ArrayList<String> explanationSteps;
    private int currentStep = 0;

    public Scene3C(SceneManager sceneManager) {
        super(sceneManager);
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void initializeComponents() {
        SceneManager.continueEnabled = false;
        setLayout(new BorderLayout(10, 10));

        // Scene title with a subtle gradient background
        JPanel titlePanel = getTitlePanel();

        add(titlePanel, BorderLayout.NORTH);

        // Content steps - concise explanations of the umbrella shortcut learning scenario
        explanationSteps = new ArrayList<>(Arrays.asList(
                "In the previous scenes, FEANOR's AI ordered the collection of all umbrellas, supposedly to prevent heavy rainfall. This is another, more subtle example of AI shortcut learning.",
                "The AI likely observed a correlation: umbrellas appear more frequently when it rains. However, it incorrectly reversed this relationship, concluding that umbrellas somehow cause rainfall.",
                "This demonstrates a classic 'correlation vs. causation' error. While umbrellas and rain occur together, the umbrellas don't cause the rain – they're just a human response to it.",
                "In machine learning, this type of error happens when an AI doesn't have a proper understanding of causality. The AI found a pattern (umbrellas and rain occurring together) and made an incorrect inference about their relationship.",
                "We saw that even after the umbrellas were collected, it still rained. This demonstrates how the AI's hypothesis was fundamentally flawed – removing umbrellas did nothing to prevent rainfall because no causal relationship existed.",
                "This example highlights a critical limitation in current AI systems: they excel at finding patterns in data but struggle with understanding cause and effect relationships that humans grasp intuitively.",
                "The real danger comes when we give AI systems control over important decisions without recognizing these limitations. An AI making policy decisions based on faulty causal reasoning could implement ineffective or even harmful solutions."
        ));

        // Main explanation panel with soft background color
        JPanel explanationPanel = new JPanel(new BorderLayout(10, 10));
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
        explanationPanel.setBackground(new Color(248, 248, 252));

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 250));

        // Text area with elegant styling
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
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JScrollPane scrollPaneForTextArea = new JScrollPane(explanationArea);
        scrollPaneForTextArea.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneForTextArea.setPreferredSize(new Dimension(800, 300));
        contentPanel.add(scrollPaneForTextArea, BorderLayout.CENTER);

        explanationPanel.add(contentPanel, BorderLayout.CENTER);

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
                // Last click - transition to exit scene
                sceneManager.showScene(Scene.EXIT_SCENE);
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

    private static JPanel getTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel("Understanding Correlation vs. Causation in AI");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(70, 70, 80));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Add keyboard hint to the title bar
        JLabel keyHint = new JLabel("(i: next, u: back)");
        keyHint.setFont(Palette.HINT_FONT);
        keyHint.setForeground(Palette.TEXT_SECONDARY);
        keyHint.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(keyHint, BorderLayout.SOUTH);
        return titlePanel;
    }

    private void updateStepDisplay(int step) {
        if (step < 0) {
            step = 0;
        } else if (step >= explanationSteps.size()) {
            // Last step - transition to exit scene
            sceneManager.showScene(Scene.EXIT_SCENE);
            return;
        }

        currentStep = step;
        explanationArea.setText(explanationSteps.get(currentStep));
        explanationArea.setCaretPosition(0);

        // Update button text for last step
        if (currentStep == explanationSteps.size() - 1) {
            nextExplanationButton.setText("Complete Game");
            nextExplanationButton.setBackground(Palette.BUTTON_SUCCESS);
        } else {
            nextExplanationButton.setText("Next");
            nextExplanationButton.setBackground(Palette.BUTTON_PRIMARY);
        }
    }

    private void goToNextStep() {
        updateStepDisplay(currentStep + 1);
    }

    private void goToPreviousStep() {
        updateStepDisplay(currentStep - 1);
    }

    @Override
    public void onShowScene() {
        super.onShowScene();
        SceneManager.continueEnabled = false;
        // Reset to the first step
        updateStepDisplay(0);
        // Request focus to enable keyboard navigation
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
            setFocusable(true);
        });

        // Add a focus listener to maintain focus
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                // Request focus back when lost
                SwingUtilities.invokeLater(() -> requestFocusInWindow());
            }
        });
    }

    // KeyListener implementation
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

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

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}
