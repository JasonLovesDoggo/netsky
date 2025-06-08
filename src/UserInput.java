/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Handles user keyboard input to navigate prompts.
 */

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserInput extends JComponent {
    public int promptCount;
    public JComponent scene;
    private final int maxPrompts;
    private Scene nextScene; // Store the next scene to transition to after all prompts are seen

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

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
