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

    static JButton createSceneButton(String text, Scene scene) {
        JButton button = new JButton(text);
        button.addActionListener(e -> SceneManager.getInstance().showScene(scene));
        return button;
    }
}
