import javax.swing.*;
import java.awt.*;

/**
 * Allows the user to input and save a new display name for their player profile.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class ChangeNameScene extends BaseScene {
	/** The location where the user may type in their new username */
    private JTextField nameField;
	/** Used to display messages to the user */
    private JLabel feedbackLabel; // For displaying messages
	
	/** 
	 * Constructor in which sceneManager is passed in. 
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public ChangeNameScene(SceneManager sceneManager) {
        super(sceneManager);
    }
	
	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * It creates all the components that are part of this scene. 
	 */
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
        nameLabel.setFont(Palette.TEXT_FONT);
        centerPanel.add(nameLabel, gbc);

        nameField = new JTextField(20); // Initialize empty, will be set in onShowScene
        nameField.setFont(Palette.TEXT_FONT);
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
	
	/**
	 * Creates a saveButton that saves the player's new name and updates the appropriate variable.
	 * 
	 * @return		the button created by this method, the saveButton
	 */
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
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * Sets the player's current name into the name field and clears the feedback message.
	 */
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
