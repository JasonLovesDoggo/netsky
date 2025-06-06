/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: This scene explains the shortcut learning concept based on the robot's behavior
 *              in Scene 2A where it mistakenly collected a bike and people as garbage.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Scene2B extends BaseScene implements KeyListener {
    private JTextArea explanationArea;
    private JButton nextExplanationButton;
    private ArrayList<String> explanationSteps;
    private int currentStep = 0;

    public Scene2B(SceneManager sceneManager) {
        super(sceneManager);
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void initializeComponents() {
        SceneManager.continueEnabled = false;
        setLayout(new BorderLayout(10, 10));

        // Scene title with a subtle gradient background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 250));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel("Understanding Robot Shortcut Learning");
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

        add(titlePanel, BorderLayout.NORTH);

        // Content steps - concise explanations of shortcut learning
        explanationSteps = new ArrayList<>(Arrays.asList(
                "In Scene 2A, our robot was picking up garbage but mistakenly collected a bicycle and even a couple pedestrians. This demonstrates another example of 'shortcut learning' that we introduced earlier.",
                "The robot was trained to recognize garbage on the curb. It learned the shortcut 'items on the curb = garbage' instead of truly understanding what constitutes garbage versus valuable possessions or people.",
                "This shortcut worked well for actual garbage bags but failed when applied to other objects that happened to be at the curb, like a bicycle or people waiting for a bus.",
                "This is similar to how an AI might identify all pictures of dogs on grass as 'dogs in parks' without understanding that the environment is just one contextual factor, not the defining characteristic.",
                "The robot didn't understand that a bicycle is a valuable possession and not trash. It simply applied its learned pattern: 'Object at curb = collect as garbage.' This is the essence of shortcut learning - relying on superficial patterns instead of deeper understanding.",
                "This highlights an important AI design principle: systems need to be trained not just on what to do, but also what not to do, with clear understanding of exceptions and edge cases."
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
                // Last click - transition to next scene
                sceneManager.showScene(Scene.SCENE_3NEWS);
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

    private void updateStepDisplay(int step) {
        if (step < 0) {
            step = 0;
        } else if (step >= explanationSteps.size()) {
            // Last step - transition to next scene
            sceneManager.showScene(Scene.SCENE_3NEWS);
            return;
        }

        currentStep = step;
        explanationArea.setText(explanationSteps.get(currentStep));
        explanationArea.setCaretPosition(0);

        // Update button text for last step
        if (currentStep == explanationSteps.size() - 1) {
            nextExplanationButton.setText("Continue to Next Scene");
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
        requestFocusInWindow();
        setFocusable(true);
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
