import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuScene extends BaseScene {
    private JPanel buttonPanel;

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
        JLabel welcomeLabel = new JLabel("Welcome, " +
                sceneManager.getParentFrame().getPlayerName() + "!");
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
        button.addActionListener(listener);
        buttonPanel.add(button);
    }
}
