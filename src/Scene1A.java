import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Presents a robot interaction where it mistakes a leash for a dog and introduces user prompts.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1A extends BaseScene {
	/** The user input variable that takes in user input through keyboard */
    public UserInput userIn;
	/** The robot instance, of a robot that holds a dog leash*/
    public Robot robotDogLeash;
	/** The scene one instance that is being drawn by the program */
    SceneOneA sceneOne;
	/** The next button that advances to the next scene when this scene is complete */
    JButton nextButton;

	/**
	 * Creates a new SceneA 
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1A(SceneManager sceneManager) {
        super(sceneManager);
    }

	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * It resets the user input prompt count so that the prompts start at 0
	 */
    @Override
    public void onShowScene() {
        super.onShowScene();
        userIn.promptCount = 0;
    }
	
	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Initializes the scene, adds the buttons and creates the user input variable, along with the robot.
	 */
    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1A");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        sceneOne = new SceneOneA();
        userIn = new UserInput(4);
        sceneOne.setBounds(0, 0, 800, 600);
        //sceneOne.setFocusable(true);

        sceneOne.add(userIn);
        userIn.scene = sceneOne;

        JLayeredPane main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 500));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        main.setBounds(0, 0, 800, 500);

        //Robot:
        ArrayList<String> text = new ArrayList<>();
        text.add("uWhat are you doing?");
        text.add("rI'm walking the dog.");
        text.add("uWhat dog? I don't see a dog.");
        text.add("rHere, let me show you. |FADEOUT");
        robotDogLeash = new Robot(main, text, "leash");

        robotDogLeash.direction = 1;
        robotDogLeash.speech = true;
        robotDogLeash.setLocation(650, 250);
        robotDogLeash.setSize(robotDogLeash.getWidth(), robotDogLeash.getHeight());
        main.add(robotDogLeash, JLayeredPane.PALETTE_LAYER);

        SceneOneForeground tree = new SceneOneForeground();
        tree.setBounds(0, 0, 800, 500);
        main.add(tree, JLayeredPane.PALETTE_LAYER);

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (robotDogLeash.fadeCount > 0) {
                    nextButton.doClick(); //Go to next scene
                    robotDogLeash.fadeOut.setVisible(false);
                    robotDogLeash.fadeCount = 0;
                }
            }
        }).start();
    }
	
	/**
	 * The foreground of scene one. 
	 * Is a separate component to ensure that the robot can go behind the tree.
	 */
    static class SceneOneForeground extends JComponent {
		/**
		 * initalizes and draws the tree in the foreground
		 * 
		 * @param g		the graphics instance that draws the tree
		 */
        public void paintComponent(Graphics g) {
            Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
            g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);
        }
    }
	
	/**
	 * The background of the scene itself. It loads the image and handles which prompt is shown based on what the userIn prompt count is.
	 */
    class SceneOneA extends JComponent {
		/**
		 * paints the background and the appropriate prompt onto the screen
		 * 
		 * @param g		the graphics instance that draws the background and the prompts
		 */
        public void paintComponent(Graphics g) {
            //sceneOne.requestFocusInWindow();

            Image sceneOnePicture = new ImageIcon("./Images/Scene1A.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);

            Graphics2D g2 = (Graphics2D) g;

            if (userIn.promptCount == 0) {
                new Prompt("This is what a prompt looks like. Press i to continue.", 50, 50, g, g2);
            } else if (userIn.promptCount == 1) {
                new Prompt("Great! If you want to go back, press u.", 50, 50, g, g2);
            } else if (userIn.promptCount == 2) {
                new Prompt("If you ever forget what to click, hover over the help icon on the right.", 50, 50, g, g2);
            } else if (userIn.promptCount == 3) {
                new Prompt("Now, click on the robot holding the leash!", 50, 50, g, g2);
            }

        }
    }
}
