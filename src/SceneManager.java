// Scene manager class to handle switching between scenes
public class SceneManager {
    // Scene identifiers as constants instead of enum
    public static final String MAIN_MENU = "MAIN_MENU";
    public static final String CHANGE_NAME = "CHANGE_NAME";
    public static final String SCENE_1A = "SCENE_1A";
    public static final String SCENE_1B = "SCENE_1B";
    public static final String SCENE_2A = "SCENE_2A";
    public static final String SCENE_2B = "SCENE_2B";
    public static final String SCENE_3A = "SCENE_3A";
    public static final String SCENE_3B = "SCENE_3B";

    private final GameFrame parentFrame;
    private final java.util.Map<String, BaseScene> scenes;
    private String currentScene;

    public SceneManager(GameFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.scenes = new java.util.HashMap<>();

        // Initialize all scenes
        initializeScenes();
    }

    private void initializeScenes() {
        // Create and register all game scenes
        scenes.put(MAIN_MENU, new MainMenuScene(this));
        scenes.put(CHANGE_NAME, new ChangeNameScene(this));
        scenes.put(SCENE_1A, new Scene1A(this));
        scenes.put(SCENE_1B, new Scene1B(this));
        scenes.put(SCENE_2A, new Scene2A(this));
        scenes.put(SCENE_2B, new Scene2B(this));
        scenes.put(SCENE_3A, new Scene3A(this));
        scenes.put(SCENE_3B, new Scene3B(this));
    }

    public void showScene(String sceneName) {
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
}
