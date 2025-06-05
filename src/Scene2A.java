/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
<<<<<<< Updated upstream
<<<<<<< Updated upstream
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
 * Description: This is Scene 2, where the garbage truck drives down the road and picks up garbage
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scene2A extends BaseScene {
    public UserInput userIn;
    SceneTwo sceneTwo;
    FadeOut fade;
    Timer timer, timer1, timer2, timer3;
    int distance;
    boolean moving;
    GarbageTruck truck;
	int index; //The variable that tracks which part of the animation the truck is on. Refers to the index of the items array

    public Scene2A(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {

        Garbage[] items = new Garbage[8];
		items[0] = new Garbage(100, "bag");
		items[0].setLocation(600, 75);
		items[0].setSize(200, 200);
		items[1] = new Garbage(80, "bag");
		items[1].setLocation(540, 75);
		items[1].setSize(200, 200);
		

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

        GarbageTruckHand hand = new GarbageTruckHand();
        hand.setLocation(700, 155);
        hand.setSize(50, 50);
        hand.setVisible(true);

        truck = new GarbageTruck();

        JLayeredPane main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 600));
        main.add(sceneTwo, JLayeredPane.DEFAULT_LAYER);
        truck.setLocation(600, 150);
        truck.setSize(50, 50);
        truck.setVisible(true);


        main.add(truck, JLayeredPane.PALETTE_LAYER);
        main.add(hand, JLayeredPane.PALETTE_LAYER);
		main.add(items[0], JLayeredPane.PALETTE_LAYER);
		main.add(items[1], JLayeredPane.PALETTE_LAYER);
		
		/*for(Garbage i : items) {
			main.add(i, JLayeredPane.PALETTE_LAYER);
		}*/

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_2B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        if (nextButton != null) {
            buttonPanel.add(nextButton);
        }
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
                    truck.setLocation(truck.getX() - 2, truck.getY());
                    hand.setLocation(hand.getX() - 2, truck.getY());
                    repaint();
                    truck.repaint();
                } else {
                    timer2.start();
                    timer1.stop();
                }
            }
        });

        timer2 = new Timer(20, new ActionListener() {
            int direction = -2;

            public void actionPerformed(ActionEvent e) {
                if (hand.getY() < 100) {
					timer3.start();
                    direction = 2;
                }
                hand.setLocation(hand.getX(), hand.getY() + (direction));
                if (hand.getY() >= 150) {
                    timer2.stop();
                    direction = -2;
                }
            }
        });

        timer3 = new Timer(20, new ActionListener() {
            //Move the garbage. Decide which garbage to move based on an array and the truck's location
			boolean go = false;
            public void actionPerformed(ActionEvent e) {
				if (items[index].getY() < 100) {
					go = true;
				} else if (items[index].getY() > 200) {
					go = false;
					items[index].setLocation(truck.getX()+75, truck.getY()+50);
					timer3.stop();
				}
				if (go) {
					items[index].setLocation(items[index].getX(), items[index].getY()+2);
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

    class Garbage extends JComponent {
        int scale, width, height;
		String type;

        Garbage(int scale, String type) {
            this.scale = scale;
			this.type = type;
        }

        public void paintComponent(Graphics g) {
			System.out.println(scale);
			Image garbage;
			if (type.equals("bag")) {
            	garbage = new ImageIcon("./Images/Garbage.png").getImage();
			} else if (type.equals("bike")) {
				garbage = new ImageIcon("./Images/Bike.png").getImage();
			} else if (type.equals("person")) {
				garbage = new ImageIcon("./Images/Person.png").getImage();
			} else { //Umbrella
				garbage = new ImageIcon("./Images/Umbrellas.png").getImage();
			}
            if (width > 0 && height > 0) {
                setSize(width, height);
            } else {
                setSize(50, 50); //Make sure it has a size, even if it's not the right size
            }
            width = (int) (garbage.getWidth(null) * scale / 100.0);
            height = (int) (garbage.getHeight(null) * scale / 100.0);
            g.drawImage(garbage, 0, 0, width, height, this);
        }
    }

    class GarbageTruckHand extends JComponent {
        int width, height;

        public void paintComponent(Graphics g) {
            Image hand = new ImageIcon("./Images/GarbageTruckHand.png").getImage();
            if (width > 0 && height > 0) {
                setSize(width, height);
            } else {
                setSize(50, 50); //Make sure it has a size
            }
            width = hand.getWidth(null);
            height = hand.getHeight(null);
            g.drawImage(hand, 0, 0, width, height, this);
        }
    }

    class GarbageTruck extends JComponent {
        int width, height;

        public void paintComponent(Graphics g) {
            if (width > 0 && height > 0) {
                setSize(width, height);
            } else {
                setSize(50, 50); //Make sure it has a size
            }
            Image truckPic = new ImageIcon("./Images/GarbageTruck.png").getImage();
            width = truckPic.getWidth(null);
            height = truckPic.getHeight(null);
            g.drawImage(truckPic, 0, 0, width, height, this);
        }
    }

    class SceneTwo extends JComponent {
        public void paintComponent(Graphics g) {
            Image background = new ImageIcon("./Images/Scene2BG.png").getImage();
            g.drawImage(background, 0, 0, 800, 500, this);
            Graphics2D g2 = (Graphics2D) g;

            switch (userIn.promptCount) {
                case 0:
                    new Prompt("Oh, looks like it's an automated garbage truck!", 50, 50, g, g2);
                    break;
                case 1:
                    new Prompt("These trucks are trained to pick up garbage from the curb.", 50, 50, g, g2);
                    break;
                case 2:
                    if (truck.getX() > 480) {
                        distance = 75;
						index = 1;
                        timer1.start();
                    }
                    break;
                case 3:
					
                    break;
                case 4:
                    break;
            }
        }


    }
}
