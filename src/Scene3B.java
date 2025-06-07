/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Scene 3B in which the user speaks to a robot about handing over their umbrella, but it soon rains anyways.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Scene3B extends BaseScene {
	
	SceneThreeB sceneThree;
	Timer timer, timer1, timer2, timer3;
	FadeOut fade;
	
    public Scene3B(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 3B");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
		Robot robot = new Robot(main, text);
		robot.direction = 1;
		robot.speech = true;
		robot.setLocation(550, 200);
		robot.setSize(robot.getWidth(), robot.getHeight());
		main.add(robot, JLayeredPane.PALETTE_LAYER);
		
		Rain1 rain1 = new Rain1();
		rain1.setBounds(0, 0, 800, 500);
		rain1.setVisible(false);
		main.add(rain1, JLayeredPane.MODAL_LAYER);
		Rain2 rain2 = new Rain2();
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
			boolean started = false;
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
		
		timer1 = new Timer(20, new ActionListener() { //Check if the robot is done talking. If so, start rain animation
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
			boolean robotMoved;
			boolean text;
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
						text = true;
					}
				} 
				if (robotMoved && text) {
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
	
	@Override 
	public void onShowScene() {
		super.onShowScene();
		timer1.start();
	}
	
	class Timelapse extends JComponent {
		public void paintComponent(Graphics g) {
			Image text = new ImageIcon("./Images/Timelapse.png").getImage();
			g.drawImage(text, 0, 0, 358, 61, this);
		}
	}
	
	class Rain1 extends JComponent {
		public void paintComponent(Graphics g) {
			Image rain = new ImageIcon("./Images/Rain1.png").getImage();
			g.drawImage(rain, 0, 0, 800, 500, this);
		}
	}
	class Rain2 extends JComponent {
		public void paintComponent(Graphics g) {
			Image rain = new ImageIcon("./Images/Rain2.png").getImage();
			g.drawImage(rain, 0, 0, 800, 500, this);
		}
	}
	
	class SceneThreeB extends JComponent {
		public void paintComponent(Graphics g) {
			Image background = new ImageIcon("./Images/Scene3BGB.png").getImage();
			g.drawImage(background, 0, 0, 800, 500, this);
		}
	}
}
