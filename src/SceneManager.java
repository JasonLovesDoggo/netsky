import javax.swing.*;

// Scene manager class to handle switching between scenes
public class SceneManager {
    // Scene identifiers as constants instead of enum
    public static final String MAIN_MENU = "MAIN_MENU";
    public static final String CHANGE_NAME = "CHANGE_NAME";
    public static final String SCENE_1NEWS = "SCENE_1NEWS";
    public static final String SCENE_1A = "SCENE_1A";
    public static final String SCENE_1B = "SCENE_1B";
    public static final String SCENE_2A = "SCENE_2A";
    public static final String SCENE_2B = "SCENE_2B";
    public static final String SCENE_3A = "SCENE_3A";
    public static final String SCENE_3B = "SCENE_3B";

    public enum Scene {
        MAIN_MENU,
        CHANGE_NAME,
        SCENE_1NEWS,
        SCENE_1A,
        SCENE_1B,
        SCENE_2A,
        SCENE_2B,
        SCENE_3A,
        SCENE_3B
    }

    private final GameFrame parentFrame;
    private final java.util.Map<Scene, BaseScene> scenes;
    private Scene currentScene;

    static SceneManager instance;

    public SceneManager(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.scenes = new java.util.HashMap<>();

        // Initialize all scenes
        initializeScenes();

        instance = this;

    }

    private void initializeScenes() {
        // Create and register all game scenes
        scenes.put(Scene.MAIN_MENU, new MainMenuScene(this));
        scenes.put(Scene.CHANGE_NAME, new ChangeNameScene(this));
        scenes.put(Scene.SCENE_1NEWS, new Scene1News(this));
        scenes.put(Scene.SCENE_1A, new Scene1A(this));
        scenes.put(Scene.SCENE_1B, new Scene1B(this));
        scenes.put(Scene.SCENE_2A, new Scene2A(this));
        scenes.put(Scene.SCENE_2B, new Scene2B(this));
        scenes.put(Scene.SCENE_3A, new Scene3A(this));
        scenes.put(Scene.SCENE_3B, new Scene3B(this));
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
        String[] skipOptions = {"Pre-scene", "Scene 1 (Intro)", "Scene 2", "Scene 3"};
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
                case "Scene 1 (Intro)":
                    showScene(Scene.SCENE_1A);
                    break;
                case "Scene 2":
                    showScene(Scene.SCENE_2A);
                    break;
                case "Scene 3":
                    showScene(Scene.SCENE_3A);
                    break;
            }
        }
    }

    public static SceneManager getInstance() {
        return instance;
    }
}


