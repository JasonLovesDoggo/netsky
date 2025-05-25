import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuScene extends BaseScene {
    private JLabel welcomeLabel;

    public MainMenuScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout(20, 20)); // Add some spacing
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding around the scene

        // Title
        JLabel titleLabel = new JLabel("Game Title");
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

        // todo: set this to a background visual
        JPanel centerVisualPanel = new JPanel();
        centerVisualPanel.setOpaque(false);
        add(centerVisualPanel, BorderLayout.CENTER);

        // Button panel for main actions
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Side-by-side layout
        mainButtonPanel.setOpaque(false);

        addButton(mainButtonPanel, "Start Game", e -> sceneManager.showScene(SceneManager.SCENE_1NEWS));
        addButton(mainButtonPanel, "Instructions", e -> showInstructions());
        addButton(mainButtonPanel, "Change Name", e -> sceneManager.showScene(SceneManager.CHANGE_NAME));
        addButton(mainButtonPanel, "Skip Ahead", e -> sceneManager.showSkipAheadOptions());
        addButton(mainButtonPanel, "Exit", e -> System.exit(0));

        // Wrapper panel to control the size and position of the mainButtonPanel
        JPanel buttonWrapperPanel = new JPanel(new BorderLayout()); // Use BorderLayout to center the mainButtonPanel
        buttonWrapperPanel.setOpaque(false);
        buttonWrapperPanel.add(mainButtonPanel, BorderLayout.CENTER);

        add(buttonWrapperPanel, BorderLayout.SOUTH);
    }

    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20)); // Slightly larger font for main buttons
        button.setBackground(Palette.ZColor);
        button.setFocusPainted(false);
        button.addActionListener(listener);
        panel.add(button);
    }

    private void showInstructions() {
        String instructionsText =
            "Instructions:\n\n" +
            "Welcome to the Game!\n" +
            "- Use the buttons to navigate through the story.\n" +
            "- The 'Start Game' button will begin your adventure from the pre-scene.\n" +
            "- 'Skip Ahead' allows you to jump to specific parts of the game.\n" +
            "- In the news scene, text scrolls automatically. Click 'Continue' when ready.\n" +
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
