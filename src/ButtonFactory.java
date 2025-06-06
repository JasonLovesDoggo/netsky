/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Factory class for creating consistently styled buttons across the application
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonFactory {
    /**
     * Creates a standard button with consistent styling
     */
    static JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(Palette.BUTTON_FONT);
        button.setBackground(Palette.BUTTON_PRIMARY);
        button.setForeground(Palette.TEXT_ON_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.addActionListener(actionListener);

        // Apply hover animations to the button
        applyHoverEffects(button);

        return button;
    }

    /**
     * Creates a continue button if SceneManager.continueEnabled is true
     */
    static JButton createSceneContinueButton(Scene scene) {
        if (SceneManager.continueEnabled) {
            JButton button = createSceneButton("Continue", scene);
            button.setBackground(Palette.BUTTON_SUCCESS); // Green for continue buttons
            applyHoverEffects(button); // Reapply after changing color
            return button;
        }
        return null;
    }

    /**
     * Creates a button to navigate back to a previous scene
     */
    static JButton createPrevSceneButton(Scene scene) {
        // Keep default primary color for back buttons
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
        applyHoverEffects(button); // Reapply after changing color
        return button;
    }

    /**
     * Creates a danger-styled button (red)
     */
    static JButton createDangerButton(String text, ActionListener actionListener) {
        JButton button = createButton(text, actionListener);
        button.setBackground(Palette.BUTTON_DANGER);
        applyHoverEffects(button); // Reapply after changing color
        return button;
    }

    /**
     * Creates a small button with smaller padding and font
     */
    static JButton createSmallButton(String text, ActionListener actionListener) {
        JButton button = createButton(text, actionListener);
        button.setFont(Palette.BUTTON_SMALL_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        return button;
    }

    /**
     * Applies hover animations to a button
     * When the mouse hovers over the button, it becomes slightly brighter
     * When the mouse leaves, it returns to its original color
     */
    private static void applyHoverEffects(JButton button) {
        // Remove any existing hover mouse listeners to avoid duplicates
        MouseListener[] listeners = button.getMouseListeners();
        for (MouseListener listener : listeners) {
            if (listener instanceof HoverAdapter) {
                button.removeMouseListener(listener);
            }
        }

        button.addMouseListener(new HoverAdapter(button));
    }

    /**
     * A custom MouseAdapter class that handles the hover effect
     */
    private static class HoverAdapter extends MouseAdapter {
        private final JButton button;
        private Color originalColor;

        public HoverAdapter(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!button.isEnabled()) {
                return;
            }

            // Store the original color before changing it
            originalColor = button.getBackground();

            // Calculate and set the hover color
            Color hoverColor = new Color(
                    Math.min(originalColor.getRed() + 20, 255),
                    Math.min(originalColor.getGreen() + 20, 255),
                    Math.min(originalColor.getBlue() + 20, 255)
            );

            button.setBackground(hoverColor);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Restore the original color
            if (originalColor != null) {
                button.setBackground(originalColor);
            }
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
