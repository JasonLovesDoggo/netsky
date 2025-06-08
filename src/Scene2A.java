/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
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
    Timer timer, timer1, timer2, timer3;
    int distance;
    int handToY = 90;
    boolean moving;
    GarbageTruckHand hand;
    GarbageTruck truck;
    int currentWidth;
    int index; //The variable that tracks which part of the animation the truck is on. Refers to the index of the items array
	boolean done;
	
    public Scene2A(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
		SceneManager.continueEnabled = true;
        boolean[] itemState = new boolean[8];
        int[] itemYs = {68, 64, 71, 63, 60, 60, 65};

        Garbage[] items = new Garbage[7];
        items[0] = new Garbage(100, "bag");
        items[0].setLocation(600, 65);
        items[1] = new Garbage(80, "bag");
        items[1].setLocation(530, 75);
        items[2] = new Garbage(100, "umbrellas");
        items[2].setLocation(460, 20);
        items[3] = new Garbage(100, "bike");
        items[3].setLocation(340, 0);
        items[4] = new Garbage(50, "person");
        items[4].setLocation(280, 20);
        items[5] = new Garbage(100, "bag");
        items[5].setLocation(215, 50);
        items[6] = new Garbage(40, "person");
        items[6].setLocation(165, 20);

        // Scene title
        JLabel titleLabel = new JLabel("Scene 2A");
        titleLabel.setFont(Palette.TITLE_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        sceneTwo = new SceneTwo();
        userIn = new UserInput(8);
        sceneTwo.setBounds(0, 0, 800, 600);
        sceneTwo.add(userIn);
        userIn.scene = sceneTwo;

        hand = new GarbageTruckHand();
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

        for (Garbage i : items) {
            i.setSize(50, 50);
            main.add(i, JLayeredPane.PALETTE_LAYER);
        }

		HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_2B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(20, new ActionListener() {
            boolean started = false;

            public void actionPerformed(ActionEvent e) {
				currentWidth = 0;
				for (int i = 0; i < itemState.length; i++) {
					if (itemState[i]) {
						main.setLayer(items[i], JLayeredPane.MODAL_LAYER); //Move in front of the truck
						items[i].setLocation(truck.getX() + 120 + currentWidth, truck.getY() + itemYs[i] - (items[i].height / 2)); //Find the y value of the item, so it lands somewhere in the truck
						currentWidth += items[i].getWidth() - 20;
                    }
                }
            }
        });

        timer1 = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (distance > 0) {
                    moving = true;
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

        timer2 = new Timer(20, new ActionListener() { //Move the garbage truck's hand
            int direction = -2;

            public void actionPerformed(ActionEvent e) {
                if (hand.getY() < handToY) {
                    timer3.start();
                    direction = 2;
                }
                hand.setLocation(hand.getX(), hand.getY() + (direction));
                if (hand.getY() >= 160) {
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
                    items[index].setLocation(truck.getX() + 75, truck.getY() + 50);
                    timer3.stop();
                    moving = false;
                    itemState[index] = true;
                    //System.out.println("Truck stopped at: " + truck.getX());
                }
                if (go) {
                    items[index].setLocation(items[index].getX(), items[index].getY() + 2);
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

    static class Garbage extends JComponent {
        int scale, width, height;
        String type;

        Garbage(int scale, String type) {
            this.scale = scale;
            this.type = type;
        }

        public void paintComponent(Graphics g) {
            Image garbage;
            switch (type) {
                case "bag":
                    garbage = new ImageIcon("./Images/Garbage.png").getImage();
                    break;
                case "bike":
                    garbage = new ImageIcon("./Images/Bike.png").getImage();
                    break;
                case "person":
                    garbage = new ImageIcon("./Images/Person.png").getImage();
                    break;
                default:  // Umbrella
                    garbage = new ImageIcon("./Images/Umbrellas.png").getImage();
                    break;
            }

            if (width > 0 && height > 0) {
                setSize(width, height);
            } else {
                setSize(50, 50); //Make sure it has a size, even if it's not the right size
            }
            width = (int) (garbage.getWidth(null) * scale / 100.0);
            height = (int) (garbage.getHeight(null) * scale / 100.0);
            g.drawImage(garbage, 0, 0, width, height, this);
            //System.out.println("Width: " + width + " and height: " + height);
        }
    }

    class GarbageTruckHand extends JComponent {
        int width, height;

        public void paintComponent(Graphics g) {
            Image handPic = new ImageIcon("./Images/GarbageTruckHand.png").getImage();
            if (width > 0 && height > 0) {
                setSize(width, 180 - hand.getY());
            } else {
                setSize(50, 50); //Make sure it has a size
            }
            width = handPic.getWidth(null);
            height = handPic.getHeight(null);
            g.drawImage(handPic, 0, 0, width, height, this);
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
                    handToY = 90;
                    new Prompt("Oh, looks like it's an automated garbage truck!", 50, 450, g, g2);
                    break;
                case 1:
                    new Prompt("These trucks are trained to pick up garbage from the curb.", 50, 450, g, g2);
                    break;
                case 2:
                    if (truck.getX() > 480 && !moving) {
                        distance = 75;
                        index = 1;
                        handToY = 90;
                        timer1.start();
                    } else if (moving && index < 1) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 3:
                    if (truck.getX() > 400 && !moving) {
                        distance = 70;
                        index = 2;
                        handToY = 50;
                        timer1.start();
                    } else if (moving && index < 2) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 4:
                    if (truck.getX() > 350 && !moving) {
                        distance = 60;
                        index = 3;
                        handToY = 40;
                        timer1.start();
                    } else if (moving && index < 3) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 5:
                    if (truck.getX() > 290 && !moving) {
                        distance = 120;
                        index = 4;
                        handToY = 70;
                        timer1.start();
                    } else if (moving && index < 4) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 6:
                    if (truck.getX() > 170 && !moving) {
                        distance = 60;
                        index = 5;
                        handToY = 70;
                        timer1.start();
                    } else if (moving && index < 5) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 7:
                    if (truck.getX() > 110 && !moving) {
                        distance = 60;
                        index = 6;
                        handToY = 60;
                        timer1.start();
                    } else if (moving && index < 6) {
                        userIn.promptCount--;
                        //System.out.println("Went back. promptCount == " + userIn.promptCount);
                    }
                    break;
                case 8:
                    //Fade out, button activated happens
					if (moving && index < 7) {
						userIn.promptCount--;
					}
					SceneManager.continueEnabled = true;
                    break;

            }
        }


    }
}
