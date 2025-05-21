import javax.swing.*;

public class GameFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private String playerName = "Player";

    public GameFrame(String title) {
        super(title);
        initializeFrame();

        // Initialize scene manager with this frame as parent
        SceneManager sceneManager = new SceneManager(this);

        // Set the first scene to main menu
        sceneManager.showScene(SceneManager.MAIN_MENU);
    }

    private void initializeFrame() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
