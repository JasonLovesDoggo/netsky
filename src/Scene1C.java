import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Scene1C extends BaseScene {
	public UserInput userIn;
	SceneOneC sceneOne;
	public Robot robotDogLeash;
	public Robot robotBird;
	public int drawCount;
	boolean stopped;
	
    public Scene1C(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1C");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
		userIn = new UserInput(3);
		userIn.setBounds(0, 0, 800, 600);
		userIn.setFocusable(true);
		userIn.scene = sceneOne;
		sceneOne.add(userIn);
		
		JLayeredPane main = new JLayeredPane();
		main.setPreferredSize(new Dimension(800, 600));
		main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);
		
		robotDogLeash = new Robot(main, "leash");
		robotDogLeash.speech = false;
		robotDogLeash.direction = 1;
		robotDogLeash.setLocation(650, 250);
		robotDogLeash.setSize(robotDogLeash.getWidth(), robotDogLeash.getHeight());
		main.add(robotDogLeash, JLayeredPane.PALETTE_LAYER);
		
		ArrayList<String> text = new ArrayList<>();
		text.add("uWhat are you doing?");
		text.add("rI received a notification about an injured bird in the tree.");
		text.add("rI'm here to take it to an animal hospital.");
		robotBird = new Robot(main, text);
		robotBird.direction = 1;
		//robotBird.setLocation();
		
		SceneOneForeground tree = new SceneOneForeground();
        tree.setBounds(0, 0, 800, 500);
        main.add(tree, JLayeredPane.PALETTE_LAYER);
		
		add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton prevButton = ButtonFactory.createPrevSceneButton(Scene.SCENE_1B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(prevButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
	
	class SceneOneC extends JComponent{
		public void paintComponent(Graphics g) {
			Image sceneOnePicture = new ImageIcon("./Images/Scene1A.png").getImage();
			g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
			drawCount++;
			if (drawCount > 0) {
				new Timer(40, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						robotDogLeash.setLocation(robotDogLeash.getX()-2, robotDogLeash.getY()-1);
						if (robotDogLeash.getX() < -20) {
							((Timer)e.getSource()).stop();
							stopped = true;
						}
					}
				}).start();
			}
			
		}
	}
	
	class SceneOneForeground extends JComponent {
		public void paintComponent(Graphics g) {
			Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
			g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);
			
		}
	}
}
