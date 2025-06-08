/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Manages robot interactions, displaying speech bubbles, dialogues, and accessories within scenes.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Robot extends JComponent {
    public int direction;
    public boolean speech;
    public boolean mouse;
    public boolean talk;
    public boolean hasAccessory;
    public int wordsCount;
    public boolean dialogComplete;
    final Image robot;
    final Speech speechBubble;
    Image accessory;
    RobotTalking text;
    ArrayList<String> words;
    FadeOut fadeOut;
    int fadeCount;
    final JLayeredPane pane;

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

    Robot(JLayeredPane pane, ArrayList<String> words, String accessory) {
        this(pane, words);
        this.hasAccessory = true;
        this.accessory = new ImageIcon("./Images/" + accessory + ".png").getImage();
    }

    Robot(JLayeredPane pane, String accessory) {
        this(pane);
        this.hasAccessory = true;
        this.accessory = new ImageIcon("./Images/" + accessory + ".png").getImage();
    }

    public void paintComponent(Graphics g) {
        drawRobot(g);
        speechBubble.setVisible(speech && mouse);
        if (hasAccessory) {
            g.drawImage(accessory, -40, 70, accessory.getWidth(null), accessory.getHeight(null), null);
        }
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        if (speech) {
            speechBubble.setLocation(x + 10, y - speechBubble.getHeight());
        }
    }

    public void drawRobot(Graphics g) {
        if (direction == 1) {
            g.drawImage(robot, 0, 0, robot.getWidth(null), robot.getHeight(null), null);
        } else {
            g.drawImage(robot, robot.getWidth(null), 0, (-1) * robot.getWidth(null), robot.getHeight(null), null);
        }
    }

    public int getWidth() {
        return robot.getWidth(null);
    }

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


    class Speech extends JComponent {
        final Image speechBubble;

        Speech() {
            speechBubble = new ImageIcon("./Images/SpeechBubble.png").getImage();
        }

        public void paintComponent(Graphics g) {
            if (direction == 1) {
                g.drawImage(speechBubble, 0, 0, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
            } else {
                g.drawImage(speechBubble, -8, 0, speechBubble.getWidth(null), speechBubble.getHeight(null), null);
            }
        }

        public int getWidth() {
            return speechBubble.getWidth(null);
        }

        public int getHeight() {
            return speechBubble.getHeight(null);
        }
    }

    class RobotTalking extends JComponent {
        final Image textRobot;
        final Image textUser;
        final JButton back;
        final JButton next;
        Timer timer;

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

        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);
            next.setVisible(visible);
            back.setVisible(visible);
        }

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

        public int getWidth() {
            return textRobot.getWidth(null);
        }

        public int getHeight() {
            return textRobot.getHeight(null);
        }
    }
}
