import javax.swing.*;

/** 
 * Main application window that initializes the frame and starts the scene manager.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: Jun 9th, 2025
 * ICS4U0
 * Ms. Krasteva 
 */
public class GameFrame extends JFrame {
	/** Width of the frame */
    private static final int WIDTH = 800;
	/** Height of the frame */
    private static final int HEIGHT = 600;
	/** The name of the player */
    private String playerName = "Glorfindel";
	
	/** 
	 * Constructs a new GameFrame instance. It initializes the scene manager
	 * Sets the first scene to main menu. 
	 * 
	 * @param title		the title of the game frame
	 */
    public GameFrame(String title) {
        super(title);
        initializeFrame();

        // Initialize scene manager with this frame as parent
        SceneManager sceneManager = new SceneManager(this);

        // Set the first scene to main menu
        sceneManager.showScene(Scene.MAIN_MENU);
    }

	/**
	 * Initializes and creates this frame for the first time. 
	 * It sets the size and centers it on the screne.
	 */
    private void initializeFrame() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
    }
	
	/**
	 * Gets the player's name
	 * 
	 * @return 		the player's name
	 */
    public String getPlayerName() {
        return playerName;
    }
	
	/** 
	 * Sets the player's name
	 * 
	 * @param playerName	the new player name
	 */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
