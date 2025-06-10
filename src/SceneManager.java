import javax.swing.*;
import java.util.Arrays;

/**
 * Scene manager class to handle switching between scenes. It manages transitions and registrations of various game scenes.
 *
 * @author Jason Cameron
 * @author Zoe Li
 * <p>
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class SceneManager {
    /**
     * Flag to control button visiblity
     */
    public static boolean continueEnabled = true;
    /**
     * instance of the sceneManager class
     */
    static SceneManager instance;
    /**
     * instance of the Game Frame class as the parent class
     */
    private final GameFrame parentFrame;
    /**
     * Hash map of all the scenes and their corresponding enums
     */
    private final java.util.Map<Scene, BaseScene> scenes;
    /**
     * The current scene the user is on
     */
    private Scene currentScene;

    /**
     * Creates a new SceneManager
     *
     * @param parentFrame The parentFrame from which everything else is created
     */
    public SceneManager(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.scenes = new java.util.HashMap<>();

        // Initialize all scenes
        initializeScenes();

        instance = this;

    }

    /**
     * Get the current instance of scenemanger
     *
     * @return the current instance
     */
    public static SceneManager getInstance() {
        return instance;
    }

    /**
     * Initializes all of the scenes by putting the scene and it's enum into the hash map
     */
    private void initializeScenes() {
        // Create and register all game scenes
        scenes.put(Scene.MAIN_MENU, new MainMenuScene(this));
        scenes.put(Scene.CHANGE_NAME, new ChangeNameScene(this));
        scenes.put(Scene.SCENE_1NEWS, new Scene1News(this));
        scenes.put(Scene.SCENE_1A, new Scene1A(this));
        scenes.put(Scene.SCENE_1B, new Scene1B(this));
        scenes.put(Scene.SCENE_1C, new Scene1C(this));
        scenes.put(Scene.SCENE_1D, new Scene1D(this));
        scenes.put(Scene.SCENE_1COMPLETE, new Scene1Complete(this));
        scenes.put(Scene.SCENE_2A, new Scene2A(this));
        scenes.put(Scene.SCENE_2B, new Scene2B(this));
        scenes.put(Scene.SCENE_3NEWS, new Scene3News(this));
        scenes.put(Scene.SCENE_3A, new Scene3A(this));
        scenes.put(Scene.SCENE_3B, new Scene3B(this));
        scenes.put(Scene.SCENE_3C, new Scene3C(this));
        scenes.put(Scene.EXIT_SCENE, new ExitScene(this));
    }

    /**
     * Show a certain scene based on the scene name
     *
     * @param sceneName the name of the new scene
     */
    public void showScene(Scene sceneName) {


        if (!scenes.containsKey(sceneName)) {
            System.err.println("Scene not found: " + sceneName);
            return;
        }

        // Remove current scene if exists
        if (currentScene != null) {
            parentFrame.getContentPane().removeAll();
        }

        // Show new scene
        BaseScene scene = scenes.get(sceneName);
        parentFrame.getContentPane().add(scene);
        currentScene = sceneName;

        // Call onShowScene for the new scene
        scene.onShowScene();

        // Update UI
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    /**
     * Gets the parent frame
     *
     * @return the parent GameFrame frame
     */
    public GameFrame getParentFrame() {
        return parentFrame;
    }

    /**
     * Shows the skip ahead options when the user chooses to skip ahead
     */
    public void showSkipAheadOptions() {
        Scene[] skipOptions = {
                Scene.MAIN_MENU,
                Scene.SCENE_1NEWS,
                Scene.SCENE_1A,
                Scene.SCENE_1B,
                Scene.SCENE_1C,
                Scene.SCENE_1D,
                Scene.SCENE_1COMPLETE,
                Scene.SCENE_2A,
                Scene.SCENE_2B,
                Scene.SCENE_3NEWS,
                Scene.SCENE_3A,
                Scene.SCENE_3B,
                Scene.SCENE_3C,
                Scene.EXIT_SCENE
        };

        // Build a display list from the labels
        String[] displayOptions = Arrays.stream(skipOptions)
                .map(scene -> scene.label)
                .toArray(String[]::new);

// Show dialog using the label strings
        String selectedLabel = (String) JOptionPane.showInputDialog(
                this.getParentFrame(),
                "Choose a point to skip to:",
                "Skip Ahead",
                JOptionPane.PLAIN_MESSAGE,
                null,
                displayOptions,
                displayOptions[0]
        );

        // Convert selected label back to corresponding Scene
        if (selectedLabel != null) {
            for (Scene s : skipOptions) {
                if (s.label.equals(selectedLabel)) {
                    showScene(s);
                    break;
                }
            }
        }
    }

    /**
     * Returns the current BaseScene instance
     *
     * @return the current BaseScene instance
     */
    public BaseScene getCurrentBaseScene() {
        if (currentScene != null) {
            return scenes.get(currentScene);
        }
        return null;
    }
}
