import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Scene1A extends BaseScene {
    JPanel contentPanel;
    SceneOneA sceneOne;
    int promptCount;

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
        sceneOne.setBounds(0, 0, 800, 500);
        sceneOne.setFocusable(true);
        sceneOne.addKeyListener(new UserInput());

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
        Robot robotDogLeash = new Robot(main, text);

        robotDogLeash.direction = 1;
        robotDogLeash.speech = true;
        robotDogLeash.setLocation(650, 250);
        robotDogLeash.setSize(robotDogLeash.getWidth(), robotDogLeash.getHeight());
        main.add(robotDogLeash, JLayeredPane.PALETTE_LAYER);

        SceneOneForeground tree = new SceneOneForeground();
        tree.setBounds(0, 0, 800, 500);
        main.add(tree, JLayeredPane.PALETTE_LAYER);

        contentPanel.add(main);

        add(contentPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton nextButton = new JButton("Next to Scene 1B");
        nextButton.addActionListener(e -> sceneManager.showScene(SceneManager.SCENE_1B));

        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    static class SceneOneForeground extends JComponent {
        public void paint(Graphics g) {
            Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
            g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);
        }
    }

    class UserInput extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'i') {
                promptCount++;
                sceneOne.repaint();
            } else if (e.getKeyChar() == 'u') {
                promptCount--;
                sceneOne.repaint();
            }
        }
    }

    class SceneOneA extends JComponent {
        public void paint(Graphics g) {
            sceneOne.requestFocusInWindow();

            Image sceneOne = new ImageIcon("./Images/Scene1A.png").getImage();
            g.drawImage(sceneOne, 0, 0, 800, 500, this);

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4));
			/*
			background(g, g2);
			tree(g, g2);*/

            switch (promptCount) {
                case 0:
                    new Prompt("This is what a prompt looks like. Press i to continue.", 50, 50, g, g2);
                    break;
                case 1:
                    new Prompt("Great! If you want to go back, press u.", 50, 50, g, g2);
                    break;
                case 2:
                    new Prompt("If you ever forget what to click, hover over the help icon on the right.", 50, 50, g, g2);
					break;
				case 3:
					new Prompt("Now, click on the robot!", 50, 50, g, g2);
				default:
            }
        }
		
		/*private void background(Graphics g, Graphics2D g2) {
			AffineTransform at = new AffineTransform();
			at.rotate(Math.toRadians(18), 0, 108);
			
			g.setColor(new Color(0x69db7c));
			g.fillRect(0, 0, 800, 600);
			g.setColor(new Color(0x40c057));
			g.fillRect(0, 250, 367, 327);
			g.fillRect(350, 382, 450, 194);
			
			g.setColor(new Color(0x868E96)); //Set to grey
			Rectangle street = new Rectangle(0, 109, 932, 214);
			g2.fill(at.createTransformedShape(street));
			g.setColor(Color.black);
			g2.draw(at.createTransformedShape(street));
			
			g.setColor(Color.yellow);
			g.drawLine(0, 219, 800, 490);
		}
		
		private void tree(Graphics g, Graphics2D g2) {	
			AffineTransform at = new AffineTransform();
			//Trunk
			g.setColor(new Color(0xa18072));
			g.fillRect(184, 284, 46, 180);
			g.setColor(new Color(0x86655a));
			g.drawRect(184, 284, 46, 180);
			//Leaves
			g.setColor(new Color(0x3fbd56));
			g.fillOval(94, 205, 110, 110);
			g.setColor(new Color(0x2f9e44));
			g.drawOval(94, 205, 110, 110);
			g.setColor(new Color(0x3fbd56));
			g.fillOval(199, 217, 110, 110);
			g.setColor(new Color(0x2f9e44));
			g.drawOval(199, 217, 110, 110);
			//Draw tilted branch
			at.rotate(Math.toRadians(80-90), 208, 323);
			Rectangle rect = new Rectangle(208, 323, 117, 18);
			g2.setColor(new Color(0xa18072));
			g2.fill(at.createTransformedShape(rect));
			g2.setColor(new Color(0x86655a));
			g2.draw(at.createTransformedShape(rect));
			//More leaves
			g.setColor(new Color(0x3fbd56));
			g.setColor(new Color(0x3fbd56));
			g.fillOval(155, 158, 110, 110);
			g.setColor(new Color(0x2f9e44));
			g.drawOval(155, 158, 110, 110);
			g.setColor(new Color(0x3fbd56));
			g.fillOval(158, 230, 110, 110);
			g.setColor(new Color(0x2f9e44));
			g.drawOval(158, 230, 110, 110);
		}*/
    }
}
