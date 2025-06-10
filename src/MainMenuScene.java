import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

/**
 * The first scene the user sees, through which they can start the program. The main menu.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: Jun 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class MainMenuScene extends BaseScene {
	/** The image that makes up the background */
    private final Image backgroundImage;
	/** The text at the top of the screen that welcomes the player */
    private JLabel welcomeLabel;
	
	/** 
	 * Creates a new main menu and initializes the background image
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public MainMenuScene(SceneManager sceneManager) {
        super(sceneManager);
        // once within the JAR, getClass().getResource("/Images/MainBG.png")
        backgroundImage = new ImageIcon("./Images/MainBG.png").getImage();
    }
	
	/**
	 * Draws the background image and company logo.
	 * 
	 * @param g		the graphics instance that draws the background image and company logo
	 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaled to fill the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        Image logo = new ImageIcon("./Images/Logo.png").getImage();
        g.drawImage(logo, 10, 10, logo.getWidth(null) / 5, logo.getHeight(null) / 5, this);
    }
	
	/** 
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Creates and initializes all the different components that are a part of the main menu scene.
	 */
    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout(20, 20)); // Add some spacing
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding around the scene

        // Title
        JLabel titleLabel = new JLabel("MEDICI");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        welcomeLabel = new JLabel("Welcome!"); // Initialize welcomeLabel
        welcomeLabel.setFont(Palette.SUBTITLE_FONT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Top panel for title and welcome message
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Button panel for main actions
        JPanel topButtonPanel = getTopButtonPanel();

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomButtonPanel.setOpaque(false);

        bottomButtonPanel.add(ButtonFactory.createButton("Instructions", e -> showInstructions()));
		bottomButtonPanel.add(ButtonFactory.createButton("Leaderboard", e -> viewLeaderboard()));
        bottomButtonPanel.add(ButtonFactory.createSceneButton("Change Name", Scene.CHANGE_NAME));
        bottomButtonPanel.add(ButtonFactory.createButton("Skip Ahead", e -> sceneManager.showSkipAheadOptions()));
        bottomButtonPanel.add(ButtonFactory.createButton("Exit", e -> System.exit(0)));

        // Wrapper panel to hold both button panels
        JPanel buttonContainerPanel = new JPanel(new BorderLayout(0, 10));
        buttonContainerPanel.setOpaque(false);
        buttonContainerPanel.add(topButtonPanel, BorderLayout.NORTH);
        buttonContainerPanel.add(bottomButtonPanel, BorderLayout.CENTER);

        add(buttonContainerPanel, BorderLayout.SOUTH);
    }
	
	/**
	 * Creates the top button panel, which contains the start game button.
	 * 
	 * @return 		the jpanel created by this method, so that it can be added to the scene
	 */
    private JPanel getTopButtonPanel() {
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtonPanel.setOpaque(false);
        JButton startGameButton = ButtonFactory.createSceneButton("Start Game", Scene.SCENE_1NEWS);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font for Start Game
        topButtonPanel.add(startGameButton);
        return topButtonPanel;
    }
	
	/**
	 * Shows the instructions to the player
	 */
    private void showInstructions() {
        String playerName = sceneManager.getParentFrame().getPlayerName();
        String instructionsText =
                "Instructions:\n\n" +
                        "Welcome to the Game, " + playerName + "!\n" +
                        "- Use the buttons to navigate through the environmental adventure.\n" +
                        "- The 'Start Game' button will begin your journey with an environmental news report.\n" +
                        "- 'Skip Ahead' allows you to jump to specific environmental scenarios.\n" +
                        "- 'Change Name' lets you update your player name.\n" +
                        "- In news scenes, text scrolls automatically. You can always click 'Continue' at any time to proceed.\n" +
                        "- Hover over the help icon in the top right corner for keyboard shortcuts.\n" +
                        "- You'll explore environmental challenges and solutions with your robot companion.\n" +
                        "- Enjoy your journey, " + playerName + "!";
        JOptionPane.showMessageDialog(this, instructionsText, "MEDICI Instructions for " + playerName, JOptionPane.INFORMATION_MESSAGE);
    }
	
	/**
	 * Reads in the data from the leaderboard and sorts it 
	 */
	private void sortLeaderboard() {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> count = new ArrayList<>();
		try {
			File board = new File("leaderboard.txt");
			Scanner s = new Scanner(board);
			String text = ""; //The sorted text that will go back into the file
			while (s.hasNextLine()) { //Load info from the file
				String line = s.nextLine();
				String[] data = line.split(":");
				names.add(data[0]);
				count.add(Integer.parseInt(data[1]));
			}
			s.close();
			while (count.size() >= 1) { //Sort 
				int index = 0;
				for(int i = 0; i < count.size(); i++) {
					if (count.get(i) >= count.get(index)) {
						index = i;
					}
				}
				text+=names.get(index) + ":"+count.get(index);
				count.remove(index);
				names.remove(index);
				if (count.size() != 0) {
					text+="\n";
				}
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter(board, false));
			pw.print(text);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Reads in the data from the leaderboard file and displays it in a table format.
	 * Calls on the sortLeaderboard() method to ensure the leaderboard is sorted before it is displayed.
	 */
	private void viewLeaderboard() {
		sortLeaderboard();
		String playerName = sceneManager.getParentFrame().getPlayerName();
		String text = ""; //String to hold the text in the file
		try {
			File leaderboard = new File("leaderboard.txt");
			Scanner s = new Scanner(leaderboard);
			text = "      Player name : Times played\n---------------------------------------\n";
			int counter = 1; //Count the number of players that have been shown
			while (s.hasNextLine() && counter <= 10) {
				String line = s.nextLine();
				String[] data = line.split(":");
				text += String.format("%-17s : %-4s\n", data[0], data[1]);
				counter++;
			}
			if (s.hasNextLine() && counter == 11) {
				text+="\nThere are more players listed, but \nonly the top ten players are shown here.  ";
			}
			s.close();
			UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.PLAIN, 14));
			if (text.length() > 74) {
				JOptionPane.showMessageDialog(this, text, "MEDICI Leaderboard for " + playerName, JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "There are no players listed. Finish a game to be added to the leaderboard!", "MEDICI Leaderboard for " + playerName, JOptionPane.INFORMATION_MESSAGE);
			}
			UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * Updates the welcome label, in case the user recently changed their name
	 */
    @Override
    public void onShowScene() {
        // Update the welcome label with the current player name when the scene is shown
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " +
                    sceneManager.getParentFrame().getPlayerName() + "!");
        }
    }
}
