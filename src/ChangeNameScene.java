import javax.swing.*;
import java.awt.*;

// Change Name Scene
public class ChangeNameScene extends BaseScene {
    private JTextField nameField;

    public ChangeNameScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Title
        JLabel titleLabel = new JLabel("Change Your Name");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Center panel with name field
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel nameLabel = new JLabel("Enter your name:");
        nameField = new JTextField(
                sceneManager.getParentFrame().getPlayerName(), 20);

        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            if (newName != null && !newName.trim().isEmpty()) {
                sceneManager.getParentFrame().setPlayerName(newName.trim());
            }
            sceneManager.showScene(SceneManager.MAIN_MENU);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
