public class Scene1B extends BaseScene {
    public Scene1B(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Scene 1B");
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(titleLabel, java.awt.BorderLayout.NORTH);

        // Content panel
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.JLabel contentLabel = new javax.swing.JLabel("This is Scene 1B content");
        contentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contentPanel.add(contentLabel, java.awt.BorderLayout.CENTER);

        add(contentPanel, java.awt.BorderLayout.CENTER);

        // Navigation buttons
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        javax.swing.JButton prevButton = new javax.swing.JButton("Back to Scene 1A");
        prevButton.addActionListener(e -> sceneManager.showScene(SceneManager.SCENE_1A));

        javax.swing.JButton menuButton = new javax.swing.JButton("Back to Menu");
        menuButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(prevButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);
    }
}
