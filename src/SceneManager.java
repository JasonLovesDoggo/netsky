/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
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
        String[] skipOptions = {"Pre-scene", "Scene 1", "Scene 2", "Scene 3"};
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
            switch (selectedSkip) {
                case "Pre-scene":
                    showScene(Scene.SCENE_1NEWS);
                    break;
                case "Scene 1":
                    showScene(Scene.SCENE_1A);
                    break;
                case "Scene 2":
                    showScene(Scene.SCENE_2A);
                    break;
                case "Scene 3":
                    showScene(Scene.SCENE_3NEWS);
                    break;
            }
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}


