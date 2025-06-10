import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the second portion of scene 1, where the user sees a robot that has been asked to walk the dog
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1B extends BaseScene {
	/** The user input variable that takes in user input through keyboard */
    public UserInput userIn;
	/** The scene one instance that is being drawn by the program */
    SceneOneB sceneOne;
	/** The fade out animation that occurs once this scene is finished */
    FadeOut fadeOut;
	/** The JLayeredPane in which everything else is being drawn */
    JLayeredPane main;
	/** The next button that advances to the next scene when this scene is complete */
    JButton nextButton;
	
	/**
	 * Creates a new Scene1B
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1B(SceneManager sceneManager) {
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
        JLabel titleLabel = new JLabel("Scene 1B");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
       /* JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("This is Scene 1B content");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);*/

        sceneOne = new SceneOneB();
        sceneOne.setFocusable(true);
        sceneOne.setBounds(0, 0, 800, 600);
        userIn = new UserInput(5);
        userIn.setBounds(0, 0, 800, 600);
        userIn.setFocusable(true);
        userIn.scene = sceneOne;
        sceneOne.add(userIn);

        main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 600));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        main.setBounds(0, 0, 800, 500);

        //Robot
        Robot robot = new Robot(main);
        robot.direction = -1;
        robot.speech = false;
        robot.setLocation(100, 200);
        robot.setSize(robot.getWidth(), robot.getHeight());
        main.add(robot, JLayeredPane.PALETTE_LAYER);

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1C);

        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);
        if (nextButton != null) {
            buttonPanel.add(nextButton);

        }
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (robot.fadeCount > 0) {
                    nextButton.doClick(); //Go to next scene
                    robot.fadeOut.setVisible(false);
                    robot.fadeCount = 0;
                }
            }
        }).start();
    }
	
	/**
	 * The scene itself. This JComponent is added to the JLayeredPane so it can be drawn for the user.
	 */
    class SceneOneB extends JComponent {
		/** The boolean that checks if the fade has already occured */
        boolean fade;
		
		/**
		 * paints the background and the appropriate prompt onto the screen
		 * 
		 * @param g		the graphics instance that draws the background and the prompts
		 */
        public void paintComponent(Graphics g) {

            Image sceneOnePicture = new ImageIcon("./Images/Scene1B.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
            Image person = new ImageIcon("./Images/Person.png").getImage();
            g.drawImage(person, 450, 200, person.getWidth(null), person.getHeight(null), null);

            if (userIn.promptCount == 0) {
                new Prompt("Controls are consistent throughout the program. i to interact.", 50, 50, g, (Graphics2D) g);
            } else if (userIn.promptCount < 4) {
                Image text = new ImageIcon("./Images/Scene1BSpeech" + (userIn.promptCount) + ".png").getImage();
                if (userIn.promptCount % 2 == 1) {
                    g.drawImage(text, 60, 100, text.getWidth(null), text.getHeight(null), null);
                } else {
                    g.drawImage(text, 400, 100, text.getWidth(null), text.getHeight(null), null);
                }
            } else {
                if (!fade) {
                    fade = true;
                    fadeOut = new FadeOut(main, Color.BLACK);
                    fadeOut.setBounds(0, 0, 800, 600);
                    fadeOut.setVisible(true);
                    main.add(fadeOut, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be on the front
                    fadeOut.start();
                } else {
                    if (!fadeOut.fading) {
                        fade = false;
                        nextButton.doClick();
                        fadeOut.setVisible(false);
                    }
                }
            }

        }
    }
}
