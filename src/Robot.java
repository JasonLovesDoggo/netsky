import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/** 
 * Manages robot interactions, displaying speech bubbles, dialogues, and accessories within scenes.
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Robot extends JComponent {
	/** The direction that the robot is facing (1 for facing left, -1 for facing right) */
    public int direction;
	/** True if the robot is interactable, false if not */
    public boolean speech;
	/** True if the mouse is hovering over the robot, false otherwise */
    public boolean mouse;
	/** True if the robot is currently talking and interacting with the player */
    public boolean talk;
	/** True if the robot has an accessory, false otherwise */
    public boolean hasAccessory;
	/** The length of the conversation between the user and the robot */
    public int wordsCount;
	/** True if the dialog is complete, false otherwise */
    public boolean dialogComplete;
	/** The uploaded image of the robot */
    final Image robot;
	/** The instance of Speech that creates the speech bubble above the robot's head */
    final Speech speechBubble;
	/** The image of the accessory of the robot, if the robot has an accessory. Null otherwise */
    Image accessory;
	/** The instance of RobotTalking, which is drawn whenever the robot interacts with the player */
    RobotTalking text;
	/** The conversation between the robot and the user. */
    ArrayList<String> words;
	/** The fade out instance that makes the scene fade out once the robot is done interacting with the player */
    FadeOut fadeOut;
	/** Counts the number of times the fade effect has occured */ 
    int fadeCount;
	/** The pane where the robot has been added */
    final JLayeredPane pane;
	
	/** 
	 * Creates a new robot. This creates the mouse listeners that check if the mouse hovers over the robot or not.
	 * Also initalizes speechBubble and the robot image
	 * 
	 * @param p 		the pane where the robot has been added 
	 */
    Robot(JLayeredPane p) {
        this.pane = p;
        this.dialogComplete = false;

        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                //System.out.println("Entered!");
                if (!talk) {
                    mouse = true;
                }
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                mouse = false;
                repaint();
                //System.out.println("Exited");
            }

        });
        robot = new ImageIcon("./Images/Robot.png").getImage();
        speechBubble = new Speech();
        speechBubble.setSize(speechBubble.getWidth(), speechBubble.getHeight());
        speechBubble.setVisible(false);
        pane.add(speechBubble, JLayeredPane.PALETTE_LAYER);
    }
	
	/**
	 * Overloaded constructor for an interactable robot. 
	 * Calls on the one-parameter constructor to avoid duplication of code.
	 * 
	 * @param pane 		the pane where the robot has been added 
	 * @param words		an arraylist of the converstation between the robot and the user
	 */
    Robot(JLayeredPane pane, ArrayList<String> words) {
        this(pane);
        this.words = words;

        text = new RobotTalking(pane);
        text.setSize(text.getWidth(), text.getHeight());
        text.setLocation(0, 100);
        text.setVisible(false);

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (speech && mouse) {
                    talk = true;
                    text.setVisible(true);
                    text.repaint();
                    repaint();
                }
            }
        });


        pane.add(text, JLayeredPane.MODAL_LAYER);
    }
	
	/** 
	 * Overloaded constructor that calls on the two-parameter constructor to limit repetition of code
	 * 
	 * @param pane 			the pane where the robot has been added 
	 * @param words			an arraylist of the converstation between the robot and the user
	 * @param accessory		the file name of the accessory that the robot is carrying
	 */
    Robot(JLayeredPane pane, ArrayList<String> words, String accessory) {
        this(pane, words);
        this.hasAccessory = true;
        this.accessory = new ImageIcon("./Images/" + accessory + ".png").getImage();
    }
	
	/**
	 * Overloaded constructor that calls on the one-parameter constructor to limit repetition of code
	 * 
	 * @param pane 			the pane where the robot has been added
	 * @param accessory 	the file name of the accessory that the robot is carrying 
	 */
    Robot(JLayeredPane pane, String accessory) {
        this(pane);
        this.hasAccessory = true;
        this.accessory = new ImageIcon("./Images/" + accessory + ".png").getImage();
    }
	
	/**
	 * Draws the robot, the speech bubble if applicable, and the accessory if applicable. 
	 * 
	 * @param g		the graphics instance that draws the robot, speech bubble, and/or accessory
	 */
    public void paintComponent(Graphics g) {
        drawRobot(g);
        speechBubble.setVisible(speech && mouse);
        if (hasAccessory) {
            g.drawImage(accessory, -40, 70, accessory.getWidth(null), accessory.getHeight(null), null);
        }
    }
	
	/**
	 * Sets the location of the robot, along with the location of the corresponding speech bubble
	 * 
	 * @param x		the x location of the robot
	 * @param y 	the y location of the robot
	 */
    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        if (speech) {
            speechBubble.setLocation(x + 10, y - speechBubble.getHeight());
        }
    }

	/**
	 * Draws the robot, based on which direction the robot is facing
	 * 
	 * @param g		the graphics instance that draws the robot
	 */
    public void drawRobot(Graphics g) {
        if (direction == 1) {
            g.drawImage(robot, 0, 0, robot.getWidth(null), robot.getHeight(null), null);
        } else {
            g.drawImage(robot, robot.getWidth(null), 0, (-1) * robot.getWidth(null), robot.getHeight(null), null);
        }
    }
	
	/**
	 * Gets the width of the picture of the robot
	 * 
	 * @return		the width of the picture of the robot
	 */
    public int getWidth() {
        return robot.getWidth(null);
    }
	
	/**
	 * Gets the height of the picture of the robot
	 * 
	 * @return		the height of the picture of the robot 
	 */
    public int getHeight() {
        return robot.getHeight(null);
    }

    /**
     * Updates the SceneManager's continueEnabled flag based on dialog completion
     */
    private void updateContinueButton() {
        // Only enable the continue button if all dialog has been completed
        SceneManager.continueEnabled = dialogComplete;
    }

	/**
	 * The speech bubble that appears above the robot's head if the robot is interactable
	 */
    class Speech extends JComponent {
		/** The image that corresponds to the speech bubble */
        final Image speechBubble;
		
		/**
		 * Creates a new speech instance, and initalizes the speechbubble image 
		 */
        Speech() {
            speechBubble = new ImageIcon("./Images/SpeechBubble.png").getImage();
        }
		
		/**
		 * Draws the speech bubble based on the location of the robot
		 * 
		 * @param g		the graphics instance that draws the speech bubble image 
		 */
        public void paintComponent(Graphics g) {
            if (direction == 1) {
                g.drawImage(speechBubble, 0, 0, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
            } else {
                g.drawImage(speechBubble, -8, 0, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
            }
        }
		
		/**
		 * Gets the width of the speech bubble image
		 * 
		 * @return 		the width of the speech bubble image
		 */
        public int getWidth() {
            return speechBubble.getWidth(null);
        }
		
		/**
		 * Gets the height of the speech bubble image
		 * 
		 * @return 		the height of the speech bubble image
		 */
        public int getHeight() {
            return speechBubble.getHeight(null);
        }
    }
	
	/**
	 * The image and text drawn whenever the robot is interacting with the user
	 */
    class RobotTalking extends JComponent {
		/** The image of the robot talking */
        final Image textRobot;
		/** The image of the user talking */
        final Image textUser;
		/** The JButton that moves the conversation backwards */
        final JButton back;
		/** The JButton that moves the conversation forwards */
        final JButton next;
		/** The timer that waits for the fade to end to increase the fadecount*/
        Timer timer;
		
		/**
		 * Creates a new robottalking instance. Loads the images, creates the next and back buttons, and defines the timer.
		 * 
		 * @param pane		the pane where the images are being drawn
		 */
        RobotTalking(JLayeredPane pane) {
            textRobot = new ImageIcon("./Images/RobotTalking.png").getImage();
            textUser = new ImageIcon("./Images/PersonTalking.png").getImage();

            next = new JButton("Next ->");
            next.setFont(Palette.BUTTON_FONT);
            next.setBackground(Palette.BUTTON_PRIMARY);
            next.setForeground(Palette.TEXT_ON_BUTTON);
            next.setFocusPainted(false);
            next.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));

            back = new JButton("<- Back");
            back.setFont(Palette.BUTTON_FONT);
            back.setBackground(Palette.BUTTON_PRIMARY);
            back.setForeground(Palette.TEXT_ON_BUTTON);
            back.setFocusPainted(false);
            back.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));

            fadeCount = 0;

            timer = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!fadeOut.fading) {
                        wordsCount = 0;
                        fadeCount++;
                        timer.stop();
                    }
                }
            });

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //Check for fadeout FIRST before increasing wordCount
                    if (words.get(wordsCount).contains("FADEOUT")) {
                        fadeOut = new FadeOut(pane, Color.BLACK);
                        fadeOut.setBounds(0, 0, 800, 600);
                        fadeOut.setVisible(true);
                        timer.start();
                        pane.add(fadeOut, Integer.valueOf(Integer.MAX_VALUE)); //Force it to be on the front
                        fadeOut.start();
                    }
                    //Increase wordCount
                    if (wordsCount < words.size() - 1) {
                        wordsCount++;
                    } else {
                        wordsCount = 0;
                        talk = false;
                        dialogComplete = true;
                        updateContinueButton();
                        RobotTalking.this.setVisible(false);
                    }
                    RobotTalking.this.repaint();
                }
            });
            next.setSize(100, 25);
            next.setLocation(400, 400);


            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (wordsCount > 0) {
                        wordsCount--;
                    } else {
                        wordsCount = 0;
                        talk = false;
                        RobotTalking.this.setVisible(false);
                    }
                    RobotTalking.this.repaint();
                }
            });
            back.setSize(100, 25);
            back.setLocation(300, 400);

            next.setVisible(false);
            back.setVisible(false);

            pane.add(next, JLayeredPane.MODAL_LAYER);
            pane.add(back, JLayeredPane.MODAL_LAYER);

        }
		
		/**
		 * When the robotTalking is set visible, it also sets the conversation buttons visible
		 * 
		 * @param visible 	True if robotTalking is being set visible, false if robotTalking is being set invisble
		 */
        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);
            next.setVisible(visible);
            back.setVisible(visible);
        }
		
		/**
		 * Draws the images onto the frame. Either displays the user talking or the robot talking. 
		 * 
		 * @param g		the graphics instance that draws the images and text
		 */
        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.setFont(Palette.DIALOG_FONT);

            String speechWords;
            if (words.get(wordsCount).contains("|")) {
                speechWords = words.get(wordsCount).substring(1, words.get(wordsCount).indexOf("|"));
            } else {
                speechWords = words.get(wordsCount).substring(1);
            }
			
			int xpos;
			
            if (words.get(wordsCount).charAt(0) == 'r') { //Robot talking
                g.drawImage(textRobot, 0, 0, textRobot.getWidth(null), textRobot.getHeight(null), null);
				xpos = 250;
            } else { //User talking
                g.drawImage(textUser, 0, 0, textUser.getWidth(null), textUser.getHeight(null), null);
				xpos = 100;
			}
			
			if (speechWords.contains("\n")) {
				String[] lines = speechWords.split("\n");
				int ydist = 0;
				for(String line : lines) {
					g.drawString(line, xpos, 100+ydist);
					ydist+=30;
				}
			} else {
				g.drawString(speechWords, xpos, 100);
			}

        }

		/**
		 * Gets the width of the image of the robot talking, which is the same width as the image of the person talking
		 * 
		 * @return 		the width of the image
		 */
        public int getWidth() {
            return textRobot.getWidth(null);
        }
		
		/**
		 * Gets the height of the image of the robot talking, which is the same height as the image of the person talking
		 * 
		 * @return 		the height of the image
		 */
        public int getHeight() {
            return textRobot.getHeight(null);
        }
    }
}
