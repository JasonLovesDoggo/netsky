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
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scene1D extends BaseScene {
    SceneOneD sceneOne;
    Timer timer;
    FadeOut fade;
    double ratio;

    public Scene1D(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1D");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        /*JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("This is Scene 1D content");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);*/

        sceneOne = new SceneOneD();
        sceneOne.setFocusable(true);
        sceneOne.setBounds(0, 0, 800, 600);

        JLayeredPane main = new JLayeredPane();
        main.setPreferredSize(new Dimension(800, 600));
        main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);

        RobotBottom rb = new RobotBottom();
        rb.setLocation(600, 400);
        rb.setSize(100, 100);
        main.add(rb, JLayeredPane.PALETTE_LAYER);

        JButton prevButton = ButtonFactory.createPrevSceneButton(Scene.SCENE_1C);
        JButton nextButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1COMPLETE);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        buttonPanel.add(prevButton);
        if (nextButton != null) {
            buttonPanel.add(nextButton);
        }
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ratio--;
                rb.setLocation(rb.getX() - 2, rb.getY() - 2);
                rb.repaint();
                if (ratio <= 0) {
                    timer.stop();
                }
            }
        });
    }

    @Override
    public void onShowScene() {
        super.onShowScene();
        ratio = 100.0;
        timer.start();
    }

    class RobotBottom extends JComponent {
        public void paintComponent(Graphics g) {
            Image robot = new ImageIcon("./Images/Scene1DRobot.png").getImage();
            this.setSize((int) (robot.getWidth(null) * ratio / 100), (int) (robot.getWidth(null) * ratio / 100));
            g.drawImage(robot, 0, 0, (int) (robot.getWidth(null) * ratio / 100), (int) (robot.getHeight(null) * ratio / 100), this);
        }
    }

    class SceneOneD extends JComponent {
        public void paintComponent(Graphics g) {
            Image sceneOnePicture = new ImageIcon("./Images/Scene1D.png").getImage();
            g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
        }
    }
}
