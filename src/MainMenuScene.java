/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: The first scene the user sees, through which they can start the program. The main menu.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class MainMenuScene extends BaseScene {
    private final Image backgroundImage;
    private JLabel welcomeLabel;

    public MainMenuScene(SceneManager sceneManager) {
        super(sceneManager);
        // once within the JAR, getClass().getResource("/Images/MainBG.png")
        backgroundImage = new ImageIcon("./Images/MainBG.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaled to fill the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        Image logo = new ImageIcon("./Images/Logo.png").getImage();
        g.drawImage(logo, 10, 10, logo.getWidth(null) / 5, logo.getHeight(null) / 5, this);
    }

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

    private JPanel getTopButtonPanel() {
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtonPanel.setOpaque(false);
        JButton startGameButton = ButtonFactory.createSceneButton("Start Game", Scene.SCENE_1NEWS);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 28)); // Larger font for Start Game
        topButtonPanel.add(startGameButton);
        return topButtonPanel;
    }

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
	
	private void sortLeaderboard() {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> count = new ArrayList<>();
		try {
			File board = new File("leaderboard.txt");
			Scanner s = new Scanner(board);
			String text = ""; //The sorted text that will go back into the file
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] data = line.split(":");
				names.add(data[0]);
				names.add(data[1]);
			}
			
			while (count.size() >= 1) {
				
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter(board, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void viewLeaderboard() {
		String playerName = sceneManager.getParentFrame().getPlayerName();
		String text = ""; //String to hold the text in the file
		try {
			File leaderboard = new File("leaderboard.txt");
			Scanner s = new Scanner(leaderboard);
			text = "  Player name : Times played\n---------------------------------------\n";
			int counter = 1; //Count the number of players that have been shown
			while (s.hasNextLine() && counter <= 10) {
				String line = s.nextLine();
				String[] data = line.split(":");
				text += String.format("%-17s : %-4s\n", data[0], data[1]);
				counter++;
			}
			if (s.hasNextLine() && counter == 11) {
				text+="\nThere are more players listed, but \nonly the top ten players are shown here.    ";
			}
			UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.PLAIN, 14));
			JOptionPane.showMessageDialog(this, text, "MEDICI Leaderboard for " + playerName, JOptionPane.INFORMATION_MESSAGE);
			UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 12));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

    @Override
    public void onShowScene() {
        // Update the welcome label with the current player name when the scene is shown
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " +
                    sceneManager.getParentFrame().getPlayerName() + "!");
        }
    }
}
