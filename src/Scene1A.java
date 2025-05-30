import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Scene1A extends BaseScene {
    JPanel contentPanel;
    SceneOneA sceneOne;
	public UserInput userIn;
	public Robot robotDogLeash;
	FadeOut fadeOut;
	boolean fade;

    public Scene1A(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1A");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(800, 500));
		
		
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
        main.add(help, JLayeredPane.PALETTE_LAYER);
        main.setBounds(0, 0, 800, 500);

        //Robot:
        ArrayList<String> text = new ArrayList<>();
        text.add("uWhat are you doing?");
        text.add("rI'm walking the dog.");
		text.add("uWhat dog? I don't see a dog.");
		text.add("rHere, let me show you.");
        robotDogLeash = new Robot(main, text, "leash");

        robotDogLeash.direction = 1;
        robotDogLeash.speech = true;
        robotDogLeash.setLocation(650, 250);
        robotDogLeash.setSize(robotDogLeash.getWidth(), robotDogLeash.getHeight());
        main.add(robotDogLeash, JLayeredPane.PALETTE_LAYER);

        SceneOneForeground tree = new SceneOneForeground();
        tree.setBounds(0, 0, 800, 500);
        main.add(tree, JLayeredPane.PALETTE_LAYER);
		
		fadeOut = new FadeOut(main);
		fadeOut.setBounds(0, 0, 800, 500);
		main.add(fadeOut, JLayeredPane.DRAG_LAYER);
		
        contentPanel.add(main);

        add(contentPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);
        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    class SceneOneForeground extends JComponent {
        public void paint(Graphics g) {
            Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
            g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);
        	
			Graphics2D g2 = (Graphics2D) g;
			
			if (userIn.promptCount == 4) {
				/*if (fadeOut == null || fadeOut.radius <= 0) {
	                fadeOut = new FadeOut(sceneOne);
	                fadeOut.fade();
	            }*/
				if (!fade) {
					fade = true;
					fadeOut.repaint();
					
				} else {
		            g2.setStroke(new BasicStroke(5));
					for(int i = 0; i < 800-fadeOut.radius; i++) {
		            	g2.drawOval(400-fadeOut.radius-i, 300-fadeOut.radius-i, (i+fadeOut.radius)*2, (i+fadeOut.radius)*2);
					}
				}
			}
			
		}
    }

    class SceneOneA extends JComponent {
        public void paint(Graphics g) {
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
