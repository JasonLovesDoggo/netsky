/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Factory class for creating consistently styled buttons across the application
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonFactory {
    /**
     * Creates a standard button with consistent styling
     */
    static JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Palette.BUTTON_PRIMARY);
        button.setForeground(Palette.TEXT_ON_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Creates a continue button if SceneManager.continueEnabled is true
     */
    static JButton createSceneContinueButton(Scene scene) {
        if (SceneManager.continueEnabled) {
            JButton button = createSceneButton("Continue", scene);
            button.setBackground(Palette.BUTTON_SUCCESS); // Green for continue buttons
            return button;
        }
        return null;
    }

    /**
     * Creates a button to navigate back to a previous scene
     */
    static JButton createPrevSceneButton(Scene scene) {
        return createSceneButton("Back to " + scene.label, scene);
    }

    /**
     * Creates a button for scene navigation
     */
    static JButton createSceneButton(String text, Scene scene) {
        return createButton(text, e -> SceneManager.getInstance().showScene(scene));
    }

    /**
     * Creates a warning-styled button (orange)
     */
    static JButton createWarningButton(String text, ActionListener actionListener) {
        JButton button = createButton(text, actionListener);
        button.setBackground(Palette.BUTTON_WARNING);
        return button;
    }

    /**
     * Creates a danger-styled button (red)
     */
    static JButton createDangerButton(String text, ActionListener actionListener) {
        JButton button = createButton(text, actionListener);
        button.setBackground(Palette.BUTTON_DANGER);
        return button;
    }
}
