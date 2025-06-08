/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: This is Scene 3A, where the user sees a bunch of other people tossing their umbrellas to join a big pile
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scene3A extends BaseScene {
    public UserInput userIn;
    SceneThree sceneThree;
    FadeOut fade;
    Timer timer, timer1, timer2, timer3;
    boolean moving;
    int index; //The variable that keeps track of which animation is occuring
	Umbrella[] umbrellas;
	static int[] startX = {100, 500, 200, 160, 630, 510, 430};
	static int[] startY = {0, 0, 0, 0, 0, 0, 0};
	static int[] destinationX = {280, 320, 250, 330, 360, 290, 330};
	static int[] destinationY = {350, 350, 350, 370, 320, 320, 380};
	JLayeredPane main;
	
    public Scene3A(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
		SceneManager.continueEnabled = true;
		InitializeUmbrellas();
		// Scene title
        JLabel titleLabel = new JLabel("Scene 3A");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        sceneThree = new SceneThree();
        userIn = new UserInput(8);
        sceneThree.setBounds(0, 0, 800, 500);
        sceneThree.add(userIn);
        userIn.scene = sceneThree;
		
        main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 500));
        main.add(sceneThree, JLayeredPane.DEFAULT_LAYER);
		
		for(Umbrella u : umbrellas) {
			main.add(u, JLayeredPane.PALETTE_LAYER);
		}
		
		HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
		
        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_3B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(20, new ActionListener() {
            boolean started = false;

            public void actionPerformed(ActionEvent e) { //Check for fade out
                if (!started && userIn.promptCount == 8) {
                    fade = new FadeOut(main, Color.black);
                    fade.setBounds(0, 0, 800, 600);
                    fade.setVisible(true);
                    main.add(fade, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be in front
                    fade.start();
                    started = true;
                }
                if (started) {
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

        timer1 = new Timer(20, new ActionListener() { //Move an umbrella 
            public void actionPerformed(ActionEvent e) {
				int xdiff = destinationX[index]-umbrellas[index].getX();
				int ydiff = destinationY[index]-umbrellas[index].getY();
				umbrellas[index].scale = (int)(umbrellas[index].scale + 4);
				
				if (Math.abs(xdiff) < 5 && Math.abs(ydiff) < 5) {
					timer1.stop();
					moving = false;
					//System.out.println("Finished timer for " + index);
					index++;
				} else {
						xdiff /= 5;
						ydiff /= 5;
					//}
					moving = true;
					umbrellas[index].setLocation(umbrellas[index].getX()+xdiff, umbrellas[index].getY()+ydiff);
				}
			}
        });
    }

	private void InitializeUmbrellas() {
		index = 0;
		umbrellas = new Umbrella[7];
		for(int i = 0; i < umbrellas.length; i++) {
			umbrellas[i] = new Umbrella(i);
			umbrellas[i].setSize(100, 100);
			umbrellas[i].setLocation(startX[i], startY[i]);
		}
	}

	@Override
    public void onShowScene() {
        super.onShowScene();
		userIn.promptCount = 0;
		InitializeUmbrellas();

		for(Umbrella u : umbrellas) {
			main.add(u, JLayeredPane.PALETTE_LAYER);
		}
		timer1.stop();
        timer.start();
    }
	
	static class Umbrella extends JComponent {
		int number, width, height, scale;
		Umbrella(int n) {
			number = n;
			scale = 9;
		}
		public void paintComponent(Graphics g) {
			Image umbrella = new ImageIcon("./Images/Umbrella"+number+".png").getImage();
			if (width > 0 && height > 0) {
				setSize(width, height);
			} else {
				setSize(50, 50); //Make sure it has a size, even if it's not the right size
			}
			width = (int) (umbrella.getWidth(null) * scale / 100.0);
            height = (int) (umbrella.getHeight(null) * scale / 100.0);
			g.drawImage(umbrella, 0, 0, width, height, this);
		}
	}
	
    class SceneThree extends JComponent {
		int count;
        public void paintComponent(Graphics g) {
            Image background = new ImageIcon("./Images/Scene3BGA.png").getImage();
            g.drawImage(background, 0, 0, 800, 500, this);
            Graphics2D g2 = (Graphics2D) g;
			if (userIn.promptCount == 0) {
				new Prompt("Looks like everyone else is turning in their umbrellas!", 50, 50, g, g2);
			} else if (userIn.promptCount <= 7) {
				if (count < userIn.promptCount) {
					count++;
				}
				if (userIn.promptCount < count) {
					userIn.promptCount = count;
				}
				if (!moving && index == userIn.promptCount-1) {
					timer1.start();
					//System.out.println("Started timer for index " + index);
				} else if (moving && index < userIn.promptCount-1) { //Index is too small, user tried moving up before index changed.
					userIn.promptCount--;
				}
			}
		}
    }
}
