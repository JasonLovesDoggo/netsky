/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Abstract base class for all scenes, providing common layout and lifecycle methods.
 */

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

    // Method called when the scene is shown
    public void onShowScene() {
        // Default implementation does nothing, can be overridden by subclasses
    }

    // Method called when the scene is hidden or replaced
    public void onHideScene() {
        // Default implementation does nothing, can be overridden by subclasses
    }
}
