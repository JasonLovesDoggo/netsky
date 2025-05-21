// Base abstract class for all scenes
public abstract class BaseScene extends javax.swing.JPanel {
    protected SceneManager sceneManager;

    public BaseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setLayout(new java.awt.BorderLayout());
        setPreferredSize(new java.awt.Dimension(800, 600));

        // Initialize the scene components
        initializeComponents();
    }

    // Method to be implemented by all concrete scenes
    protected abstract void initializeComponents();
}
