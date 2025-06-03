/*
Names: Jason Cameron, Zoe Li
Date: May 30, 2025
Teacher: Ms. Krasteva 
Description: This is the second portion of scene 1, where the user sees a robot that has been asked to walk the dog
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scene1B extends BaseScene {
	SceneOneB sceneOne;
	public UserInput userIn;
	FadeOut fadeOut;
	JLayeredPane main;
	JButton nextButton;
	
    public Scene1B(SceneManager sceneManager) {
        super(sceneManager);
    }
	
	@Override
	public void onShowScene() {
		super.onShowScene();
		userIn.promptCount = 0;
	}

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1B");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        buttonPanel.add(nextButton);
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
	
	class SceneOneB extends JComponent {
		
		boolean fade;
		
		public void paintComponent(Graphics g) {
			
			Image sceneOnePicture = new ImageIcon("./Images/Scene1B.png").getImage();
			g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
			Image person = new ImageIcon("./Images/Person.png").getImage();
			g.drawImage(person, 450, 200, person.getWidth(null), person.getHeight(null), null);
			
			if (userIn.promptCount == 0) {
				new Prompt("Controls are consistent throughout the program. i to interact.", 50, 50, g, (Graphics2D)g);
			} else if (userIn.promptCount < 4) {
				Image text = new ImageIcon("./Images/Scene1BSpeech"+(userIn.promptCount)+".png").getImage();
				if (userIn.promptCount%2 == 1) {
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
