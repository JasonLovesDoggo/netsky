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
import java.awt.event.ActionListener;

public class ButtonFactory {
    static JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Palette.ZColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    static JButton createSceneContinueButton(Scene scene) {
        if (SceneManager.continueEnabled) {
            return createSceneButton("Continue", scene);
        }
        return null;
    }

    static JButton createPrevSceneButton(Scene scene) {
        return createSceneButton("Back to " + scene.label, scene);
    }

    static JButton createSceneButton(String text, Scene scene) {
        return createButton(text, e -> SceneManager.getInstance().showScene(scene));
    }
}
