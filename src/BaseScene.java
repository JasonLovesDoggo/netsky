import javax.swing.*;
import java.awt.*;


/**
 * Abstract base class for all scenes, providing common layout and lifecycle methods.
 * 
 * @author Jason Cameron, Zoe Li
 * 
 * Date: Jun 9, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public abstract class BaseScene extends JPanel {
	/** The instance of sceneManager that runs the entire program */
    protected final SceneManager sceneManager;
	
	/** 
	 * The constructor that creates a base scene.
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public BaseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Initialize the scene components
        initializeComponents();
    }

	/** 
	 * Required method to be implemented by all concrete scenes.
	 * This method is called when the scene is first created, and added to the scene manager.
	 * It initalizes all the components, and ensures that everything exists
	 */
    protected abstract void initializeComponents();

    /**
	 * Method called when the scene is shown.
	 */
    public void onShowScene() {
        // Default implementation does nothing, can be overridden by subclasses
    }

    /**
	 * Method called when the scene is hidden or replaced
	 */
    public void onHideScene() {
        // Default implementation does nothing, can be overridden by subclasses
    }
}
