import javax.swing.*;
import java.awt.*;

// Base abstract class for all scenes
public abstract class BaseScene extends JPanel {
    protected SceneManager sceneManager;

    public BaseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Initialize the scene components
        initializeComponents();
    }

    // Method to be implemented by all concrete scenes
    protected abstract void initializeComponents();
}
