import javax.swing.*;
import java.awt.*;

// Change Name Scene
public class ChangeNameScene extends BaseScene {
    private JTextField nameField;
    private JLabel feedbackLabel; // For displaying messages

    public ChangeNameScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Title
        JLabel titleLabel = new JLabel("Change Your Name");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Larger font
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for input elements
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50); // Add padding around components

        JLabel nameLabel = new JLabel("Enter your new name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(nameLabel, gbc);

        nameField = new JTextField(20); // Initialize empty, will be set in onShowScene
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        centerPanel.add(nameField, gbc);

        feedbackLabel = new JLabel(" "); // Placeholder for feedback
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(feedbackLabel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Increased gaps

        JButton saveButton = getSaveButton();

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(120, 40)); // Make buttons larger
        cancelButton.addActionListener(e -> sceneManager.showScene(Scene.MAIN_MENU));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton getSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(120, 40)); // Make buttons larger
        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            if (newName != null && !newName.trim().isEmpty()) {
                sceneManager.getParentFrame().setPlayerName(newName.trim());
                feedbackLabel.setText("Name changed successfully!");
                // Optionally, add a small delay before going back or let user click cancel/back
                // For now, immediately go back to main menu
                sceneManager.showScene(Scene.MAIN_MENU);
            } else {
                feedbackLabel.setText("Name cannot be empty.");
            }
        });
        return saveButton;
    }

    @Override
    public void onShowScene() {
        // Pre-fill the name field with the current player's name when the scene is shown
        if (nameField != null) {
            nameField.setText(sceneManager.getParentFrame().getPlayerName());
        }
        if (feedbackLabel != null) {
            feedbackLabel.setText(" "); // Clear feedback message
        }
    }
}
