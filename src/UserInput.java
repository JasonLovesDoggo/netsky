import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles user keyboard input to navigate prompts.
 *
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class UserInput extends JComponent {
	/** The integer that represents the current prompt count */
    public int promptCount;
	/** The scene that the user input is attached to */
    public JComponent scene;
	/** The maximum number of prompts */
    private final int maxPrompts;
	/** Store the next scene to transition to after all prompts are seen */
    private Scene nextScene;
	
	/**
	 * Creates a new User Input along with the appropriate key listeners 
	 * 
	 * @param max		the maximum number of prompts
	 */
    public UserInput(int max) {
        this.promptCount = 0;
        this.maxPrompts = max;
        this.nextScene = null;

        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'i' && promptCount < maxPrompts) {
                    advancePrompt();
                } else if (e.getKeyChar() == 'u' && promptCount > 0) {
                    promptCount--;
                    updateContinueButton();
                    scene.repaint();
                }
                promptCount = Math.min(Math.max(promptCount, 0), maxPrompts);
            }
        });
    }

	/**
	 *	get the maximum number of prompts that user input can be
	 * 
	 * @return		the max nubmer of prompts
	 */
    public int getMaxPrompts() {
        return maxPrompts;
    }
    /**
     * Advances to the next prompt
     */
    public void advancePrompt() {
        if (promptCount < maxPrompts) {
            promptCount++;
            updateContinueButton();
            scene.repaint();

            // If we've reached the max prompts and have a next scene, transition to it
            if (promptCount >= maxPrompts && nextScene != null) {
                SceneManager.getInstance().showScene(nextScene);
            }
        }
    }

    /**
     * Sets the scene to transition to after all prompts are seen
	 * 
	 * @param scene		the scene that is the next scene
     */
    public void setNextScene(Scene scene) {
        this.nextScene = scene;
    }

    /**
     * Updates the SceneManager's continueEnabled flag based on whether all prompts have been viewed
     */
    private void updateContinueButton() {
        // Only enable the continue button if we're not at the last prompt
        SceneManager.continueEnabled = (promptCount < maxPrompts);
    }
	
	/** 
	 * When notified, it requests focus to ensure that it can sense key activities correctly
	 */
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
