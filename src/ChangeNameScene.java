// Change Name Scene
public class ChangeNameScene extends BaseScene {
    private javax.swing.JTextField nameField;

    public ChangeNameScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Title
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Change Your Name");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(titleLabel, java.awt.BorderLayout.NORTH);

        // Center panel with name field
        javax.swing.JPanel centerPanel = new javax.swing.JPanel();
        centerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        javax.swing.JLabel nameLabel = new javax.swing.JLabel("Enter your name:");
        nameField = new javax.swing.JTextField(
                sceneManager.getParentFrame().getPlayerName(), 20);

        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        add(centerPanel, java.awt.BorderLayout.CENTER);

        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        javax.swing.JButton saveButton = new javax.swing.JButton("Save");
        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            if (newName != null && !newName.trim().isEmpty()) {
                sceneManager.getParentFrame().setPlayerName(newName.trim());
            }
            sceneManager.showScene(SceneManager.MAIN_MENU);
        });

        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        cancelButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);
    }
}
