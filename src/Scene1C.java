import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Presents a sequence where one robot retrieves a leash and another rescues a bird, with interactive prompts and fade-out transitions.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1C extends BaseScene {
	/** The robot with the dog leash */
    public Robot robotDogLeash;
	/** The robot that is in charge of the injured bird in the tree */
    public Robot robotBird;
	/** The scene one instance that is being drawn by the program */
    SceneOneC sceneOne;
	/** The timers that move the robot with the dog leash, move the second robot, and activate the fade effect */
    Timer timer, timer2, timer3;
	/** The JLayeredPane in which everything else is being drawn */
    JLayeredPane main;
	/** The fade out animation that occurs once this scene is finished */
    FadeOut fade;
	/** The next button that advances to the next scene when this scene is complete */
    JButton nextButton;
	/** The boolean that is true once the user has interacted with the second robot */
	boolean lastText;
	
	/**
	 * Creates a new Scene1C
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1C(SceneManager sceneManager) {
        super(sceneManager);
    }

	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Initializes the scene, adds the buttons, and creates the robot along with defining the timers
	 */
    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1C");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        /*JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("This is Scene 1C content");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);*/

        sceneOne = new SceneOneC();
        sceneOne.setFocusable(true);
        sceneOne.setBounds(0, 0, 800, 600);

        main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 600));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        robotDogLeash = new Robot(main, "leash");
        robotDogLeash.speech = false;
        robotDogLeash.direction = 1;
        robotDogLeash.setLocation(650, 250);
        robotDogLeash.setSize(robotDogLeash.getWidth(), robotDogLeash.getHeight());
        main.add(robotDogLeash, JLayeredPane.PALETTE_LAYER);

        nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1D);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        ArrayList<String> text = new ArrayList<>();
        text.add("uWhat are you doing?");
        text.add("rI received a notification about an injured bird.");
        text.add("rI'm here to take it to an animal hospital.");
        text.add("uOh, the one right up there, by the tree.");
        text.add("rYep, that's the one.");
        robotBird = new Robot(main, text);
        robotBird.direction = 1;
        robotBird.setLocation(800, 250);
        robotBird.setSize(robotBird.getWidth(), robotBird.getHeight());
        robotBird.setVisible(true);
        robotBird.speech = true;


        SceneOneForeground tree = new SceneOneForeground();
        tree.setBounds(0, 0, 800, 500);
        main.add(tree, JLayeredPane.MODAL_LAYER);
        main.add(robotBird, JLayeredPane.MODAL_LAYER);

        HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        main.setBounds(0, 0, 800, 500);

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        if (nextButton != null) {
            buttonPanel.add(nextButton);
        }
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                robotDogLeash.setLocation(robotDogLeash.getX() - 6, robotDogLeash.getY() - 2);
                if (robotDogLeash.getX() < -20) {
                    robotBird.setLocation(robotBird.getX() - 3, robotBird.getY());
                    if (robotBird.getX() <= 650) {
                        timer2.start();
                        timer.stop();
                    }
                }
            }
        });

        timer2 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (robotBird.wordsCount == 4) {
                    lastText = true;
                }
                if (lastText && !robotBird.text.isShowing()) {
                    timer3.start();
                    timer2.stop();
                }
            }
        });

        timer3 = new Timer(20, new ActionListener() {
            boolean first;

            public void actionPerformed(ActionEvent e) {
                if (robotBird.getX() < 300) {
                    if (!first) {
                        fade = new FadeOut(main, Color.BLACK);
                        fade.setBounds(0, 0, 800, 600);
                        fade.setVisible(true);
                        main.add(fade, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be on the front
                        fade.start();
                    } else {
                        if (!fade.fading) {
                            timer3.stop();
                            nextButton.doClick();
                            fade.setVisible(false);
                            timer3.stop();
                        }
                    }
                    first = true;
                } else {
                    robotBird.setLocation(robotBird.getX() - 3, robotBird.getY());
                }
            }
        });
    }

	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * It resets the locations of the robots and starts the first timer to start the scene
	 */
    @Override
    public void onShowScene() {
        super.onShowScene();
        robotDogLeash.setLocation(650, 250);
        robotBird.setLocation(800, 250);
		lastText = false; //Reset the robot bird sensing
        timer.start();
    }
	
	/**
	 * Stops all timers when the scene is exited
	 */
	@Override
	public void onHideScene() {
		timer.stop();
		timer3.stop();
		timer2.stop();
		if (fade != null) {
			fade.stop();
		}
	}

	/**
	 * The background image of SceneOneC
	 */
    static class SceneOneC extends JComponent {
		/**
		 * paints the background onto the screen
		 * 
		 * @param g		the graphics instance that draws the image
		 */
        public void paintComponent(Graphics g) {
            Image sceneOnePicture = new ImageIcon("./Images/Scene1A.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);

        }
    }
	
	/**
	 * The foreground of SceneOneC
	 */
    static class SceneOneForeground extends JComponent {
		/**
		 * paints the foreground onto the screen
		 * 
		 * @param g		the graphics instance that draws the image
		 */
        public void paintComponent(Graphics g) {
            Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
            g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);

        }
    }
}
