/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

import javax.swing.*;
import java.awt.*;

public class MainMenuScene extends BaseScene {
    private JLabel welcomeLabel;
    private final Image backgroundImage;

    public MainMenuScene(SceneManager sceneManager) {
        super(sceneManager);
        // once within the JAR, getClass().getResource("/Images/MainBG.png")
        backgroundImage = new ImageIcon("./Images/MainBG.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaled to fill the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        Image logo = new ImageIcon("./Images/Logo.png").getImage();
        g.drawImage(logo, 10, 10, logo.getWidth(null) / 5, logo.getHeight(null) / 5, this);
    }

    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout(20, 20)); // Add some spacing
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding around the scene

        // Title
        JLabel titleLabel = new JLabel("MEDICI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        welcomeLabel = new JLabel("Welcome!"); // Initialize welcomeLabel
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Top panel for title and welcome message
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Button panel for main actions
        JPanel topButtonPanel = getTopButtonPanel();

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomButtonPanel.setOpaque(false);

        bottomButtonPanel.add(ButtonFactory.createButton("Instructions", e -> showInstructions()));
        bottomButtonPanel.add(ButtonFactory.createSceneButton("Change Name", Scene.CHANGE_NAME));
        bottomButtonPanel.add(ButtonFactory.createButton("Skip Ahead", e -> sceneManager.showSkipAheadOptions()));
        bottomButtonPanel.add(ButtonFactory.createButton("Exit", e -> System.exit(0)));

        // Wrapper panel to hold both button panels
        JPanel buttonContainerPanel = new JPanel(new BorderLayout(0, 10));
        buttonContainerPanel.setOpaque(false);
        buttonContainerPanel.add(topButtonPanel, BorderLayout.NORTH);
        buttonContainerPanel.add(bottomButtonPanel, BorderLayout.CENTER);

        add(buttonContainerPanel, BorderLayout.SOUTH);
    }

    private JPanel getTopButtonPanel() {
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtonPanel.setOpaque(false);
        JButton startGameButton = ButtonFactory.createSceneButton("Start Game", Scene.SCENE_1NEWS);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font for Start Game
        topButtonPanel.add(startGameButton);
        return topButtonPanel;
    }

    private void showInstructions() {
        String instructionsText =
                "Instructions:\n\n" +
                        "Welcome to the Game!\n" +
                        "- Use the buttons to navigate through the story.\n" +
                        "- The 'Start Game' button will begin your adventure from the pre-scene.\n" +
                        "- 'Skip Ahead' allows you to jump to specific parts of the game.\n" +
                        "- In the news scene, text scrolls automatically. Click 'Continue' when ready.\n" +
                        "- Hover over the help icon in the top right corner for some keyboard shortcuts.\n" +
                        "- Enjoy your playthrough!";
        JOptionPane.showMessageDialog(this, instructionsText, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onShowScene() {
        // Update the welcome label with the current player name when the scene is shown
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " +
                    sceneManager.getParentFrame().getPlayerName() + "!");
        }
    }
}