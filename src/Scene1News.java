import javax.swing.*;
import java.awt.*;

public class Scene1News extends BaseScene {
    ScrollingText news;

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

        news = new ScrollingText("The US senate has passed a controversial new law\nthe US is ceding all control to FEANOR (Fully Enabled Autonomous Non-Organic Ruler),\nthe world's first AI that is able to rule a country!\n. . . add more text later");
        news.setScrollSpeed(0.5);
        news.setY(-28);
        contentPanel.add(news);

        add(contentPanel, BorderLayout.CENTER);

        // Navigation buttons
        JPanel buttonPanel = getButtonPanel(news); // Pass news to button panel method

        add(buttonPanel, BorderLayout.SOUTH);
        news.setScrolling();
    }

    private JPanel getButtonPanel(ScrollingText news) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        JButton nextSceneButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_1A);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        buttonPanel.add(nextSceneButton);
        buttonPanel.add(menuButton);
        return buttonPanel;
    }

    @Override
    public void onShowScene() {
        // Reset the news scrolling position when the scene is shown
        if (news != null) {
            news.setY(-28);
            news.setScrolling();
        }
        super.onShowScene();

    }


}
