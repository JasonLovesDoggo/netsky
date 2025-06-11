import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Demonstrates a robot moving and a circular fade-out animation at the end of Scene 1D.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1D extends BaseScene {
	/** The scene one instance that is being drawn by the program */
    SceneOneD sceneOne;
	/** The timer that handles the robot flying off into the sky */
    Timer timer;
	/** The ratio of the robot's current size to it's original size (out of 100) */
    double ratio;
	/** The instance of the robot from the bottom view */
    RobotBottom rb;
	
	/**
	 * Creates a new Scene1D
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1D(SceneManager sceneManager) {
        super(sceneManager);
    }

	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Initializes the scene, adds the buttons and defines the timer, along with the robot.
	 */
    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1D");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        /*JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("This is Scene 1D content");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);*/

        sceneOne = new SceneOneD();
        sceneOne.setFocusable(true);
        sceneOne.setBounds(0, 0, 800, 600);

        JLayeredPane main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 600));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        rb = new RobotBottom();
        rb.setLocation(600, 400);
        rb.setSize(100, 100);
        main.add(rb, JLayeredPane.PALETTE_LAYER);

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1COMPLETE);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        main.setBounds(0, 0, 800, 500);

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ratio--;
                rb.setLocation(rb.getX() - 2, rb.getY() - 2);
                rb.repaint();
                if (ratio <= 0) {
                    timer.stop();
                }
            }
        });
    }
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * It resets the robot's size and location, and starts the timer
	 */
    @Override
    public void onShowScene() {
        super.onShowScene();
        rb.setSize(100, 100);
        rb.setLocation(600, 400);
        ratio = 100.0;
        timer.start();
    }
	/**
	 *
	 */
	@Override
	public void onHideScene() {
		timer.stop();
	}

	/** 
	 * The robot from the bottom view
	 */
    class RobotBottom extends JComponent {
		/**
		 * Draws the image of the bottom of the robot onto the screen, scaled based on the ratio
		 * 
		 * @param g		The graphics instance that draws the image
		 */
        public void paintComponent(Graphics g) {
            Image robot = new ImageIcon("./Images/Scene1DRobot.png").getImage();
            this.setSize((int) (robot.getWidth(null) * ratio / 100), (int) (robot.getWidth(null) * ratio / 100));
            g.drawImage(robot, 0, 0, (int) (robot.getWidth(null) * ratio / 100), (int) (robot.getHeight(null) * ratio / 100), this);
        }
    }

	/**
	 * The background of scene 1D
	 */
    static class SceneOneD extends JComponent {
		/**
		 * Draws the background image onto the screen
		 * 
		 * @param g		The graphics instance that draws the image
		 */
        public void paintComponent(Graphics g) {
            Image sceneOnePicture = new ImageIcon("./Images/Scene1D.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
        }
    }
}
