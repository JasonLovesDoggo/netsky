/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Handles the exit scene for game completion and credits, highlighting the game's theme on AI shortcut learning.
 */

import javax.swing.*;
import java.awt.*;

public class ExitScene extends BaseScene {
    private final Image backgroundImage;

    public ExitScene(SceneManager sceneManager) {
        super(sceneManager);
        backgroundImage = new ImageIcon("./Images/MainBG.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout());
        JPanel contentPanel = getContentPanel();

        // Title panel at the top of content
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        // Completion message
        JLabel titleLabel = new JLabel("AI Control Experiment Complete!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        // Add space
        titlePanel.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Understanding Shortcut Learning in AI Systems");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subtitleLabel);

        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Game theme summary in scrollable text area
        JScrollPane scrollPane = getJScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Make scrollbar more visible and easier to use
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));

        // Add a visible border to help distinguish the scrollable area
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        scrollPane.setPreferredSize(new Dimension(600, 250));

        // Add a visual cue that the content is scrollable
        JLabel scrollHintLabel = new JLabel("(Scroll down to read more)");
        scrollHintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        scrollHintLabel.setForeground(new Color(220, 220, 220));
        scrollHintLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel scrollTextPanel = new JPanel(new BorderLayout(0, 5));
        scrollTextPanel.setOpaque(false);
        scrollTextPanel.add(scrollPane, BorderLayout.CENTER);
        scrollTextPanel.add(scrollHintLabel, BorderLayout.SOUTH);

        contentPanel.add(scrollTextPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel getContentPanel() {
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        contentPanel.setLayout(new BorderLayout(10, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        return contentPanel;
    }

    private static JScrollPane getJScrollPane() {
        JTextArea summaryText = new JTextArea();
        summaryText.setText(
            "Throughout your journey with the NetSky AI systems, you've witnessed firsthand " +
            "the consequences of AI shortcut learning - where algorithms find correlations without " +
            "understanding causation.\n\n" +

            "You've seen robots walking leashes without dogs, collecting umbrellas to stop rain, " +
            "and other amusing but cautionary scenarios that demonstrate how AI can misinterpret " +
            "the world through pattern recognition failures.\n\n" +

            "These scenarios reflect real challenges in AI development:\n\n" +

            "• Pattern Recognition Without Understanding:\n" +
            "  The AI robot walking a leash without a dog shows how an AI might recognize\n" +
            "  that 'walking dogs' involves leashes, without understanding the actual\n" +
            "  purpose of the leash or the presence of the dog.\n\n" +

            "• Correlation vs. Causation Confusion:\n" +
            "  When the AI collected umbrellas to prevent rain, it mistook correlation\n" +
            "  (umbrellas appearing when it rains) for causation (umbrellas causing rain).\n\n" +

            "• Contextual Misunderstanding:\n" +
            "  AI systems struggle with context that humans intuitively understand,\n" +
            "  leading to comical but potentially dangerous misinterpretations.\n\n" +

            "As the U.S. Senate's fictional experiment with AI governance demonstrates,\n" +
            "surrendering human judgment to AI systems without proper oversight can lead to\n" +
            "unintended consequences. The key takeaway is the importance of human-AI collaboration\n" +
            "and the need for AI systems to be designed with a deep understanding of context and\n" +
            "causation.\n\n" +

            "Thank you for participating in this experiment. We hope it has provided valuable\n" +
            "insights into the complexities and challenges of AI development. Your feedback is\n" +
            "invaluable in helping us improve future iterations of this project.\n\n" +

            "Credits:\n" +
            "• Project Lead: Zoe Li\n" +
            "• Developers: Jason Cameron, Zoe Li\n" +
            "• Special Thanks: Ms. Krasteva\n"
        );
        summaryText.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryText.setForeground(Color.WHITE);
        summaryText.setOpaque(false);
        summaryText.setEditable(false);
        summaryText.setLineWrap(true);
        summaryText.setWrapStyleWord(true);

        return new JScrollPane(summaryText);
    }
}

