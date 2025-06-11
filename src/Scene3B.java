import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Scene 3B in which the user speaks to a robot about handing over their umbrella, but it soon rains anyways.
 *
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene3B extends BaseScene {
	/** The scene that is being drawn by the program */
	SceneThreeB sceneThree;
	/** The timers that check for the fade out, check if the robot is done talking, check for the fade out animation, and for the rain animation */
	Timer timer, timer1, timer2, timer3;
	/** The fade out animation that occurs once this scene is finished */
	FadeOut fade;
	/** True if the fade animation has started, else false */
	boolean started;
	/** True if the robot has moved after the user interacted with it, false otherwise */
	boolean robotMoved;
	/** True if the user is done interacting with the robot, false otherwise */
	boolean textBool;
	/** The robot that the user interacts with, the one that takes the umbrella */
	Robot robot;
	/** The first image used for rain */
	Rain1 rain1;
	/** The second image used for rain */
	Rain2 rain2;
	
	/**
	 * Creates a new Scene3B
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene3B(SceneManager sceneManager) {
        super(sceneManager);
    }

	/**
	 * Called at the beginning, when this scene is added to the sceneManager and created for the first time. 
	 * Defines the timers, initializes the components and the robots. 
	 */
    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 3B");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
		sceneThree = new SceneThreeB();
		sceneThree.setBounds(0, 0, 800, 600);
		
		JLayeredPane main = new JLayeredPane();
		main.setPreferredSize(new Dimension(800, 500));
		main.add(sceneThree, JLayeredPane.DEFAULT_LAYER);
		
		ArrayList<String> text = new ArrayList<>();
		text.add("uWhy do we have to turn in all umbrellas?");
		text.add("rFEANOR has noticied a correlation between heavy \nrainfall and umbrellas.");
		text.add("uWhat if I don't want to?");
		text.add("rFEANOR has mandated that all umbrellas be \ncollected. \nThank you for your compliance.");
		text.add("r*Takes umbrella*");
		robot = new Robot(main, text);
		robot.direction = 1;
		robot.speech = true;
		robot.setLocation(550, 260);
		robot.setSize(robot.getWidth(), robot.getHeight());
		main.add(robot, JLayeredPane.PALETTE_LAYER);
		
		rain1 = new Rain1();
		rain1.setBounds(0, 0, 800, 500);
		rain1.setVisible(false);
		main.add(rain1, JLayeredPane.MODAL_LAYER);
		rain2 = new Rain2();
		rain2.setBounds(0, 0, 800, 500);
		rain2.setVisible(false);
		main.add(rain2, JLayeredPane.MODAL_LAYER);
		
		Timelapse time = new Timelapse();
		time.setSize(358, 61);
		time.setLocation(-358, 220);
		time.setVisible(false);
		main.add(time, JLayeredPane.MODAL_LAYER);
		
		HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
		add(main, BorderLayout.CENTER);
		
        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_3C);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
		
		timer = new Timer(20, new ActionListener() { //Check for fade out
			public void actionPerformed(ActionEvent e) {
				if (!started) {
					fade = new FadeOut(main, Color.black);
					fade.setBounds(0, 0, 800, 600);
					fade.setVisible(true);
					main.add(fade, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be in front
					fade.start();
					started = true;
				} else {
					if (!fade.fading) {
                        timer.stop();
                        nextButton.doClick();
                        fade.setVisible(false);
                        timer.stop();
                        started = false;
                    }
				}
			}
		});
		
		timer1 = new Timer(20, new ActionListener() { //Check if the robot is done talking. If so, start timelapse  animation
			public void actionPerformed(ActionEvent e) {
				if (robot.wordsCount == 4 && robot.text.isVisible()) {
					robot.speech = false;
				}
				if(robot.speech == false && !robot.text.isVisible()) {
					timer2.start();
					timer1.stop();
				}
			}
		});
		timer2 = new Timer(20, new ActionListener() { //Timelapse animation
			public void actionPerformed(ActionEvent e) {
				if (!robotMoved) {
					robot.setLocation(robot.getX()-2, robot.getY());
					if (robot.getX() < -100) {
						robotMoved = true;
					}
				} else {
					/*System.out.println("A few days later");
					text = true;*/
					time.setVisible(true);
					time.setLocation(time.getX()+6, time.getY());
					if (time.getX() > 810) {
						time.setVisible(false);
						textBool = true;
					}
				} 
				if (robotMoved && textBool) {
					timer3.start();
					timer2.stop();
				}
			}
		});
		timer3 = new Timer(400, new ActionListener() {
			int count;
			public void actionPerformed(ActionEvent e) { //Rain animation
				count++;
				if (count%2 == 0) {
					rain1.setVisible(true);
					rain2.setVisible(false);
				} else {
					rain1.setVisible(false);
					rain2.setVisible(true);
				}
			}
		});
    }
	
	/**
	 * The method that is automatically called when the scene is shown to the user. 
	 * Starts the first timer for this scene
	 */
	@Override 
	public void onShowScene() {
		super.onShowScene();
		timer1.start();
	}
	
	/** 
	 * The method that is automatically called when the scene is hidden
	 * Resets everything so that the scene is ready if the user returns to it
	 */
	@Override
	public void onHideScene() {
		started = false;
		robotMoved = false;
		textBool = false;
		robot.speech = true;
		timer.stop();
		timer1.stop();
		timer2.stop();
		timer3.stop();
		robot.setLocation(550, 260);
		rain1.setVisible(false);
		rain2.setVisible(false);
	}
	
	/**
	 * The text that goes accross the screen for a timelapse
	 */
	static class Timelapse extends JComponent {
		/**
		 * paints the text onto the screen
		 * 
		 * @param g		the graphics instance that draws the text
		 */
		public void paintComponent(Graphics g) {
			Image text = new ImageIcon("./Images/Timelapse.png").getImage();
			g.drawImage(text, 0, 0, 358, 61, this);
		}
	}
	
	/**
	 * The first image used in the rain animation
	 */
	static class Rain1 extends JComponent {	
		/**
		 * paints the rain onto the screen
		 * 
		 * @param g		the graphics instance that draws the rain
		 */
		public void paintComponent(Graphics g) {
			Image rain = new ImageIcon("./Images/Rain1.png").getImage();
			g.drawImage(rain, 0, 0, 800, 500, this);
		}
	}
	
	/**
	 * The second image used in the rain animation
	 */
	static class Rain2 extends JComponent {
		/**
		 * paints the rain onto the screen
		 * 
		 * @param g		the graphics instance that draws the rain
		 */
		public void paintComponent(Graphics g) {
			Image rain = new ImageIcon("./Images/Rain2.png").getImage();
			g.drawImage(rain, 0, 0, 800, 500, this);
		}
	}
	
	/**
	 * The background of scene three
	 */
	static class SceneThreeB extends JComponent {
		/**
		 * paints the background and the person onto the screen
		 * 
		 * @param g		the graphics instance that draws the background and person
		 */
		public void paintComponent(Graphics g) {
			Image background = new ImageIcon("./Images/Scene3BGB.png").getImage();
			g.drawImage(background, 0, 0, 800, 500, this);
			Image person = new ImageIcon("./Images/Person.png").getImage();
			g.drawImage(person, 200, 250, 58, 135, this);
		}
	}
}
