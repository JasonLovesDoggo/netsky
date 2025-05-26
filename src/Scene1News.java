import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scene1News extends BaseScene {
    public Scene1News(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected void initializeComponents() {
        // Scene title
        JLabel titleLabel = new JLabel("Scene 1 News");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

		ScrollingText news = new ScrollingText("The US senate has passed a controversial new law\nthe US is ceding all control to FEANOR (Fully Enabled Autonomous Non-Organic Ruler),\nthe world's first AI that is able to rule a country!\n. . . add more text later");
        news.setScrollSpeed(0.5);
        news.setY(-28); // Start under the screen
		contentPanel.add(news);

        add(contentPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = getButtonPanel(news); // Pass news to button panel method

        add(buttonPanel, BorderLayout.SOUTH);
        news.setY(-150);
        news.setScrolling();
    }

    private JPanel getButtonPanel(ScrollingText news) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        JButton nextSceneButton = new JButton("Continue");
        nextSceneButton.addActionListener(e -> sceneManager.showScene(SceneManager.SCENE_1A));

        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(e -> sceneManager.showScene(SceneManager.MAIN_MENU));

        buttonPanel.add(nextSceneButton);
        buttonPanel.add(menuButton);
        return buttonPanel;
    }
	
	public void onShowScene () {
	
	}


}
