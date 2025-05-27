import javax.swing.*;
import java.awt.*;

public class Scene1B extends BaseScene {
	JPanel contentPanel;
	SceneOneB sceneOne;
	int promptCount;
	
    public Scene1B(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1B");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
       /* JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("This is Scene 1B content");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);*/
		
		sceneOne = new SceneOneB();
		sceneOne.setBounds(0, 0, 800, 600);
		sceneOne.setFocusable(true);
		
		JLayeredPane main = new JLayeredPane();
		main.setPreferredSize(new Dimension(800, 600));
		main.add(sceneOne, JLayeredPane.DEFAULT_LAYER);
		
		//Robot
		Robot robot = new Robot(main);
		robot.direction = -1;
		robot.speech = false;
		robot.setLocation(100, 200);
		robot.setSize(robot.getWidth(), robot.getHeight());
		main.add(robot, JLayeredPane.PALETTE_LAYER);
		
		add(main, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton prevButton = new JButton("Back to Scene 1A");
        prevButton.addActionListener(e -> sceneManager.showScene(SceneManager.SCENE_1A));

        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(prevButton);
        buttonPanel.add(menuButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
	
	class SceneOneB extends JComponent {
		public void paint(Graphics g) {
			sceneOne.requestFocusInWindow();
			
			Image sceneOnePicture = new ImageIcon("./Images/Scene1B.png").getImage();
			g.drawImage(sceneOnePicture, 0, 0, 800, 500, this);
			
			if (promptCount < 0) {
				promptCount = 0;
			} else if (promptCount > 3) {
				promptCount = 3;
			}
			
			Image text = new ImageIcon("./Images/Scene1B"+promptCount+1+".png").getImage();
			
			
		}
	}
}
