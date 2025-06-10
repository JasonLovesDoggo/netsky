import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

/**
 * Handles the exit scene for game completion and credits, highlighting the game's theme on AI shortcut learning.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: Jun 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class ExitScene extends BaseScene {
	/** The background image of the scene */
    private final Image backgroundImage;

	/** 
	 * The constructor that creates the exit scene. It initializes the background image. 
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public ExitScene(SceneManager sceneManager) {
        super(sceneManager);
        backgroundImage = new ImageIcon("./Images/MainBG.png").getImage();
    }
	
	/**
	 * Draws the background onto the screen. 
	 * 
	 * @param g		the Graphics variable that draws the image
	 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
	
	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * It creates all the components that are part of this scene. 
	 */
    @Override
    protected void initializeComponents() {
        setLayout(new BorderLayout());
        JPanel contentPanel = getContentPanel();

        // Title panel at the top of content
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        // Completion message with player name
        String playerName = sceneManager.getParentFrame().getPlayerName();
        JLabel titleLabel = new JLabel("Congratulations, " + playerName + "! AI Control Experiment Complete!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        // Add space
        titlePanel.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Understanding Shortcut Learning in AI Systems");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subtitleLabel);

        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Game theme summary in scrollable text area
        JScrollPane scrollPane = getJScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Make scrollbar more visible and easier to use
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));

        // Add a visible border to help distinguish the scrollable area
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        scrollPane.setPreferredSize(new Dimension(600, 250));

        // Add a visual cue that the content is scrollable
        JLabel scrollHintLabel = new JLabel("(Scroll down to read more)");
        scrollHintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        scrollHintLabel.setForeground(new Color(220, 220, 220));
        scrollHintLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel scrollTextPanel = new JPanel(new BorderLayout(0, 5));
        scrollTextPanel.setOpaque(false);
        scrollTextPanel.add(scrollPane, BorderLayout.CENTER);
        scrollTextPanel.add(scrollHintLabel, BorderLayout.SOUTH);

        contentPanel.add(scrollTextPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
	
	/**
	 * Creates and gets the content panel that contains the semi-transparent
	 * block that is the background of the content panel. 
	 * 
	 * @return		the panel created by this method.
	 */
    private JPanel getContentPanel() {
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        contentPanel.setLayout(new BorderLayout(10, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        return contentPanel;
    }
	
	/**
	 * Creates and gets a JScrollPane containing all the text for the end.
	 * 
	 * @return		the JScrollPane created by this method.
	 */
    private JScrollPane getJScrollPane() {
        String playerName = sceneManager.getParentFrame().getPlayerName();
        JTextArea summaryText = new JTextArea();
        summaryText.setText(
            "Throughout your journey with the NetSky AI systems, " + playerName + ", you've witnessed firsthand " +
            "the consequences of AI shortcut learning - where algorithms find correlations without " +
            "understanding causation.\n\n" +

            "During your exploration, " + playerName + ", you've seen robots walking leashes without dogs, collecting umbrellas to stop rain, " +
            "and other amusing but cautionary scenarios that demonstrate how AI can misinterpret " +
            "the world through pattern recognition failures.\n\n" +

            "These scenarios reflect real challenges in AI development:\n\n" +

            "• Pattern Recognition Without Understanding:\n" +
            "  The AI robot walking a leash without a dog shows how an AI might recognize\n" +
            "  that 'walking dogs' involves leashes, without understanding the actual\n" +
            "  purpose of the leash or the presence of the dog.\n\n" +

            "• Correlation vs. Causation Confusion:\n" +
            "  When the AI collected umbrellas to prevent rain, it mistook correlation\n" +
            "  (umbrellas appearing when it rains) for causation (umbrellas causing rain).\n\n" +

            "• Contextual Misunderstanding:\n" +
            "  AI systems struggle with context that humans intuitively understand,\n" +
            "  leading to comical but potentially dangerous misinterpretations.\n\n" +

            "As the U.S. Senate's fictional experiment with AI governance demonstrates,\n" +
            "surrendering human judgment to AI systems without proper oversight can lead to\n" +
            "unintended consequences. " + playerName + ", the key takeaway for you is the importance of human-AI collaboration\n" +
            "and the need for AI systems to be designed with a deep understanding of context and\n" +
            "causation.\n\n" +

            "Thank you, " + playerName + ", for participating in this experiment. We hope it has provided valuable\n" +
            "insights into the complexities and challenges of AI development. Your feedback is\n" +
            "invaluable in helping us improve future iterations of this project.\n\n" +

            "Credits:\n" +
            "• Project Lead: Zoe Li\n" +
            "• Developers: Jason Cameron, Zoe Li\n" +
            "• Special Thanks: Ms. Krasteva\n"
        );
        summaryText.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryText.setForeground(Color.WHITE);
        summaryText.setOpaque(false);
        summaryText.setEditable(false);
        summaryText.setLineWrap(true);
        summaryText.setWrapStyleWord(true);

        return new JScrollPane(summaryText);
    }
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * This updates the leaderboard by searching for the player's name, and either adding the player
	 * or incrementing the player's count by one, then re-sorting the leaderboard before updating 
	 * the leaderboard file. 
	 */
	@Override
	public void onShowScene() {
		super.onShowScene();
		//Update the leaderboard
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> count = new ArrayList<>();
		String playerName = sceneManager.getParentFrame().getPlayerName();
		try {
			File board = new File("leaderboard.txt");
			Scanner s = new Scanner(board);
			String text = ""; //The sorted text that will go back into the file, with the current player added
			while (s.hasNextLine()) { //Load info from the file
				String line = s.nextLine();
				String[] data = line.split(":");
				names.add(data[0]);
				count.add(Integer.parseInt(data[1]));
			}
			s.close();
			
			//Look for the current player
			boolean found = false;
			for(int i = 0; i < names.size(); i++) {
				if (names.get(i).equals(playerName)) {
					found = true;
					count.set(i, count.get(i)+1);
					System.out.println("Count updated to " + count.get(i));
				}
			}
			
			if (!found) {//Not found, add a new user
				names.add(playerName);
				count.add(1); // The user has completed one playthrough
			}
			
			//Sort the arrays based on count
			int size = count.size(); // The size of the array that still needs to be sorted
			
			while (size > 1) { //Sort, to make sure data is up to date
				int index = 0;
				for(int i = 0; i < size; i++) {
					if (count.get(i) >= count.get(index)) {
						index = i;
					}
				}
				
				int oldCount = count.get(size-1);
				count.set(size-1, count.get(index));
				count.set(index, oldCount);
				String oldName = names.get(size-1);
				names.set(size-1, names.get(index));
				names.set(index, oldName);
				//System.out.println("index is: " + index+ count+"  |  "+names);
				size--;
				
			}
			
			//Update the text variable
			for(int i = 0; i < names.size(); i++) {
				text+=names.get(i) + ":"+count.get(i);
				if (i != names.size()-1) {
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
}
