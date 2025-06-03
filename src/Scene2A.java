/*
Names: Jason Cameron, Zoe Li
Date: June 3, 2025
Teacher: Ms. Krasteva
Description: This is Scene 2, where the garbage truck drives down the road and picks up garbage
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scene2A extends BaseScene {
	public UserInput userIn;
	SceneTwo sceneTwo;
	FadeOut fade;
	Timer timer, timer1;
	int distance;
	boolean moving;
	GarbageTruck truck;
	
    public Scene2A(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 2A");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
		
		sceneTwo = new SceneTwo();
		userIn = new UserInput(8);
		sceneTwo.setBounds(0, 0, 800, 600);
		sceneTwo.add(userIn);
		userIn.scene = sceneTwo;
		
		truck = new GarbageTruck();

        JLayeredPane main = new JLayeredPane();
		main.setPreferredSize(new Dimension(800, 600));
		main.add(sceneTwo, JLayeredPane.DEFAULT_LAYER);
		truck.setLocation(600, 200);
		truck.setSize(50, 50);
		truck.setVisible(true);
		main.add(truck, JLayeredPane.PALETTE_LAYER);
		
		
		
		
		add (main, BorderLayout.CENTER);
		
        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_2B);
		JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
		
		timer = new Timer(100, new ActionListener() {
			boolean started = false;
			public void actionPerformed(ActionEvent e) {
				if (!started && userIn.promptCount == 8) {
					fade = new FadeOut(main, Color.black);
					fade.setBounds(0, 0, 800, 600);
					fade.setVisible(true);
					main.add(fade, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be in front
					fade.start();
					started = true;
				} 
				if (started) {
					if (fade.fading == false) {
						timer.stop();
						nextButton.doClick();
						fade.setVisible(false);
						timer.stop();
						started = false;
					}
				}
			}
		});
		
		timer1 = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (distance > 0) {
					distance -= 2;
					System.out.println("Moved. " + distance + " left to move");
					truck.setLocation(truck.getX()-2, truck.getY());
					repaint();
					truck.repaint();
				} else {
					timer1.stop();
				}
			}
		});
    }
	
	@Override
	public void onShowScene() {
		super.onShowScene();
		distance = 100;
		timer1.start();
		timer.start();
	}
	
	class GarbageTruck extends JComponent {
		int width, height;
		public void paint(Graphics g) {
			this.setSize(width, height);
			Image truckPic = new ImageIcon("./Images/GarbageTruck.png").getImage();
			width = truckPic.getWidth(null);
			height = truckPic.getHeight(null);
			g.drawImage(truckPic, 0, 0, width, height, this);
			System.out.println(width + " is the width and " + height + " is the height");
		}
	}
	
	class SceneTwo extends JComponent{
		public void paintComponent(Graphics g) {
			Image background = new ImageIcon("./Images/Scene2BG.png").getImage();
			g.drawImage(background, 0, 0, 800, 500, this);
			Graphics2D g2 = (Graphics2D) g;
			
			switch (userIn.promptCount) {
				case 0: 
					new Prompt("Oh, looks like it's an automated gargabe truck!", 50, 50, g, g2);
					break;
				case 1: 
					new Prompt("These trucks are trained to pick up garbage from the curb.", 50, 50, g, g2);
					break;
				case 2:
					if (truck.getX() > 480) {
						distance = 50;
					}
					timer1.start();
					break;
				case 3:
					break;
				case 4:
					break;
			}
		}
		
		
	}
}
