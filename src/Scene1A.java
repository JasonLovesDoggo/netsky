/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Scene1A extends BaseScene {
    JPanel contentPanel;
    SceneOneA sceneOne;
    public UserInput userIn;
    public Robot robotDogLeash;
    FadeOut fadeOut;
    boolean fade;
    JButton nextButton;

    public Scene1A(SceneManager sceneManager) {
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
        JLabel titleLabel = new JLabel("Scene 1A");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(800, 500));


        sceneOne = new SceneOneA();
        userIn = new UserInput(3);
        sceneOne.setBounds(0, 0, 800, 600);
        //sceneOne.setFocusable(true);

        sceneOne.add(userIn);
        userIn.scene = sceneOne;

        JLayeredPane main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 500));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        HelpIcon help = new HelpIcon(main);
        help.setBounds(790 - help.getWidth(), -10, help.getWidth(), help.getHeight());
        main.add(help, JLayeredPane.MODAL_LAYER);
        main.setBounds(0, 0, 800, 500);

        //Robot:
        ArrayList<String> text = new ArrayList<>();
        text.add("uWhat are you doing?");
        text.add("rI'm walking the dog.");
        text.add("uWhat dog? I don't see a dog.");
        text.add("rHere, let me show you. |FADEOUT");
        robotDogLeash = new Robot(main, text, "leash");

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

        nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1B);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        if (nextButton != null) {
            buttonPanel.add(nextButton);
        }
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (robotDogLeash.fadeCount > 0) {
                    nextButton.doClick(); //Go to next scene
                    robotDogLeash.fadeOut.setVisible(false);
                    robotDogLeash.fadeCount = 0;
                }
            }
        }).start();
    }

    static class SceneOneForeground extends JComponent {
        public void paint(Graphics g) {
            Image sceneOneTree = new ImageIcon("./Images/Scene1ATree.png").getImage();
            g.drawImage(sceneOneTree, 100, 120, sceneOneTree.getWidth(null), sceneOneTree.getHeight(null), this);
        }
    }

    class SceneOneA extends JComponent {
        public void paint(Graphics g) {
            //sceneOne.requestFocusInWindow();

            Image sceneOnePicture = new ImageIcon("./Images/Scene1A.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);

            Graphics2D g2 = (Graphics2D) g;

            if (userIn.promptCount == 0) {
                new Prompt("This is what a prompt looks like. Press i to continue.", 50, 50, g, g2);
            } else if (userIn.promptCount == 1) {
                new Prompt("Great! If you want to go back, press u.", 50, 50, g, g2);
            } else if (userIn.promptCount == 2) {
                new Prompt("If you ever forget what to click, hover over the help icon on the right.", 50, 50, g, g2);
            } else if (userIn.promptCount == 3) {
                new Prompt("Now, click on the robot holding the leash!", 50, 50, g, g2);
            }

        }
    }
}
