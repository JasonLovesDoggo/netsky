import javax.swing.*;
import java.awt.*;

/**
 * Breaking news scene #2 with dynamic scrolling text. Announces that umbrellas must be turned in
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene3News extends AbstractNewsScene {
	/**
	 * Creates a new Scene3News
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene3News(SceneManager sceneManager) {
        super(sceneManager);
    }
	
	/** 
	 * This creates the JPanel that holds all the buttons, at the bottom of the screen. 
	 * @return 	the button panel so that it can be added to the frame.
	 */
	@Override
    protected JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(true);

        nextSceneButton = ButtonFactory.createSceneContinueButton(Scene.SCENE_3A);
        JButton menuButton = ButtonFactory.createPrevSceneButton(Scene.MAIN_MENU);

        if (nextSceneButton != null) {
            // Initially disable the continue button until scrolling is complete
            nextSceneButton.setEnabled(false);
            nextSceneButton.setToolTipText("Please finish reading to continue.");
            nextSceneButton.setForeground(Palette.TEXT_DISABLED);
            nextSceneButton.setBackground(Palette.BUTTON_DISABLED);
            buttonPanel.add(nextSceneButton);
        }
        buttonPanel.add(menuButton);
        return buttonPanel;
    }
	
	/**
	 * Sets the text for the news
	 * 
	 * @return 	the text for the news
	 */
    @Override
    protected ScrollingText getNews() {
        return new ScrollingText(
                "FEANOR HAS MANDATED THAT ALL UMBRELLAS BE TURNED IN TO THE NEAREST SERVICE BOT IMMEDIATELY.\n\n" +
                        "All citizens must give all their umbrellas to their nearest service bot," +
                        "or leave it on the curb to be collected by the garbage truck.\n\n" +
                        "When asked why, this is what FEANOR said: \n\n" +
                        "\"FEANOR seeks to protect all citizens from the impacts of heavy rainstorms. Thus, FEANOR's AI service robots are proceeding with the removal of all umbrellas.\"\n\n" +
                        "Citizens have already begun piling up their umbrellas, and soon, the entirety of the US will be umbrella-free!\n\n" +
                        "How an umbrella free future may lead us to a future safe from heavy rainstorms, experts do not know, but they fully support their new leader.\n\n" +
                        "Make sure you comply immediately!");


    }
}
