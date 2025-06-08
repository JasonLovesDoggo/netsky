/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Manages transitions and registrations of various game scenes.
 */


import javax.swing.*;

// Scene manager class to handle switching between scenes
public class SceneManager {
    public static boolean continueEnabled = true; // Flag to control continue button visibility
    static SceneManager instance;
    private final GameFrame parentFrame;
    private final java.util.Map<Scene, BaseScene> scenes;
    private Scene currentScene;

    public SceneManager(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.scenes = new java.util.HashMap<>();

        // Initialize all scenes
        initializeScenes();

        instance = this;

    }

    public static SceneManager getInstance() {
        return instance;
    }

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

    public GameFrame getParentFrame() {
        return parentFrame;
    }

    public void showSkipAheadOptions() {
        String[] skipOptions = {
            "Main Menu - Return to title screen",
            "Scene 1 News - Initial environmental report",
            "Scene 1A - Garbage investigation with Robot",
            "Scene 1B - Interaction with townspeople",
            "Scene 1C - Tree planting activity",
            "Scene 1D - Air pollution encounter",
            "Scene 1 Explanation - Environmental damage summary",
            "Scene 2A - Recycling challenge",
            "Scene 2 Explanation - Recycling importance",
            "Scene 3 News - Climate change report",
            "Scene 3A - Community garden initiative",
            "Scene 3B - Green energy solutions",
            "Scene 3 Explanation - Future sustainability",
            "Exit Scene - Game completion and credits"
        };

        String selectedSkip = (String) JOptionPane.showInputDialog(
                this.getParentFrame(),
                "Choose a point to skip to:",
                "Skip Ahead",
                JOptionPane.PLAIN_MESSAGE,
                null,
                skipOptions,
                skipOptions[0]
        );

        if (selectedSkip != null) {
            if (selectedSkip.startsWith("Main Menu")) {
                showScene(Scene.MAIN_MENU);
            } else if (selectedSkip.startsWith("Scene 1 News")) {
                showScene(Scene.SCENE_1NEWS);
            } else if (selectedSkip.startsWith("Scene 1A")) {
                showScene(Scene.SCENE_1A);
            } else if (selectedSkip.startsWith("Scene 1B")) {
                showScene(Scene.SCENE_1B);
            } else if (selectedSkip.startsWith("Scene 1C")) {
                showScene(Scene.SCENE_1C);
            } else if (selectedSkip.startsWith("Scene 1D")) {
                showScene(Scene.SCENE_1D);
            } else if (selectedSkip.startsWith("Scene 1 Explanation")) {
                showScene(Scene.SCENE_1COMPLETE);
            } else if (selectedSkip.startsWith("Scene 2A")) {
                showScene(Scene.SCENE_2A);
            } else if (selectedSkip.startsWith("Scene 2 Explanation")) {
                showScene(Scene.SCENE_2B);
            } else if (selectedSkip.startsWith("Scene 3 News")) {
                showScene(Scene.SCENE_3NEWS);
            } else if (selectedSkip.startsWith("Scene 3A")) {
                showScene(Scene.SCENE_3A);
            } else if (selectedSkip.startsWith("Scene 3B")) {
                showScene(Scene.SCENE_3B);
            } else if (selectedSkip.startsWith("Scene 3 Explanation")) {
                showScene(Scene.SCENE_3C);
            } else if (selectedSkip.startsWith("Exit Scene")) {
                showScene(Scene.EXIT_SCENE);
            }
        }
    }

    /**
     * Returns the current BaseScene instance
     */
    public BaseScene getCurrentBaseScene() {
        if (currentScene != null) {
            return scenes.get(currentScene);
        }
        return null;
    }
}
