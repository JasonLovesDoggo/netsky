import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuScene extends BaseScene {
    private JPanel buttonPanel;
    private JLabel welcomeLabel; // Make welcomeLabel a field to update it

    public MainMenuScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Title
        JLabel titleLabel = new JLabel("Game Title");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Welcome message with player name
        welcomeLabel = new JLabel(); // Initialize empty, will be set in onShowScene
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        // Create menu buttons
        addButton("Change Name", e -> sceneManager.showScene(SceneManager.CHANGE_NAME));
        addButton("Scene 1", e -> sceneManager.showScene(SceneManager.SCENE_1A));
        addButton("Scene 2", e -> sceneManager.showScene(SceneManager.SCENE_2A));
        addButton("Scene 3", e -> sceneManager.showScene(SceneManager.SCENE_3A));
        addButton("Exit", e -> System.exit(0));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Palette.ZColor);
        button.addActionListener(listener);
        buttonPanel.add(button);
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
