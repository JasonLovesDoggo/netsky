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
        JButton button = new JButton("Continue");
        button.setFont(Palette.BUTTON_FONT);
        button.setBackground(Palette.BUTTON_SUCCESS); // Green for continue buttons
        button.setForeground(Palette.TEXT_ON_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // The continue button will check if we're in a scene with Robot or UserInput
        button.addActionListener(e -> {
            BaseScene currentScene = SceneManager.getInstance().getCurrentBaseScene();

            // First check for UserInput - this should have priority over Robot
            UserInput userInput = findUserInputInScene(currentScene);
            if (userInput != null) {
                // Since we're using zero-based indexing, the last valid prompt is at maxPrompts-1
                // If we're still showing prompts (not at the last prompt yet)
                if (userInput.promptCount < userInput.getMaxPrompts() - 1) {
                    // For UserInput, we should always allow advancing the prompt
                    userInput.setNextScene(scene);
                    userInput.advancePrompt();
                    return;
                }
                // If we're at the last prompt
                else if (userInput.promptCount == userInput.getMaxPrompts() - 1) {
                    // Check if there's a robot that needs interaction
                    Robot robot = findRobotWithActiveDialogInScene(currentScene);
                    if (robot != null) {
                        if (robot.talk && !robot.dialogComplete) {
                            // If robot is talking but dialog isn't complete, click the next button
                            robot.text.next.doClick();
                            return;
                        } else if (robot.speech && !robot.talk && !robot.dialogComplete) {
                            // If we found a Robot with speech bubble that hasn't been clicked yet,
                            // show a hint to click on the robot and block further prompt advancement
                            JOptionPane.showMessageDialog(
                                currentScene,
                                "Please click on the robot to continue!",
                                "Action Required",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            return;
                        }
                    }

                    // If no robot needs interaction or if robot has already been interacted with, advance the prompt
                    userInput.setNextScene(scene);
                    userInput.advancePrompt();
                    return;
                }
                // If all prompts are done, continue with robot check (fall through)
            }

            // Next check for Robot with active dialog or that can be clicked
            Robot robot = findRobotWithActiveDialogInScene(currentScene);
            if (robot != null) {
                if (robot.talk && !robot.dialogComplete) {
                    // If we found a Robot with active dialog, simulate a click on its "next" button
                    robot.text.next.doClick();
                    return;
                } else if (robot.speech && !robot.talk && !robot.dialogComplete) {
                    // If we found a Robot with speech bubble that hasn't been clicked yet,
                    // show a hint to click on the robot and BLOCK scene transition
                    JOptionPane.showMessageDialog(
                        currentScene,
                        "Please click on the robot to continue!",
                        "Action Required",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    // Don't proceed to scene transition - return early
                    return;
                } else {
                    // If robot has already been clicked and dialog is complete,
                    // or if it's in another state, proceed with scene transition
                    SceneManager.getInstance().showScene(scene);
                }
            } else {
                // If no robot was found, proceed with scene transition
                SceneManager.getInstance().showScene(scene);
            }
        });

        if (!SceneManager.continueEnabled) {
            button.setEnabled(false);
            button.setText("Continue (locked)");
            button.setToolTipText("Complete this part before continuing.");
            button.setForeground(Palette.TEXT_DISABLED);
            button.setBackground(Palette.BUTTON_DISABLED);
        } else {
            button.setToolTipText("Click to continue.");
            button.setText("Continue");
        }

        applyHoverEffects(button); // Apply hover effects
        return button;
    }

    /**
     * Helper method to find a UserInput component in the scene
     */
    private static UserInput findUserInputInScene(Container container) {
        if (container == null) return null;

        // Search for UserInput components in the container
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof UserInput) {
                return (UserInput) component;
            } else if (component instanceof Container) {
                // Recursively search in nested containers
                UserInput result = findUserInputInScene((Container) component);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Helper method to find a Robot component with active dialog in the scene
     */
    private static Robot findRobotWithActiveDialogInScene(Container container) {
        if (container == null) return null;

        Robot robotWithActiveDialog = null;
        Robot robotWithSpeech = null;

        // Handle JLayeredPane specifically as they're often used for complex scenes with robots
        if (container instanceof JLayeredPane) {
            JLayeredPane layeredPane = (JLayeredPane) container;

            // Check all components in the layered pane
            for (Component component : layeredPane.getComponents()) {
                if (component instanceof Robot) {
                    Robot robot = (Robot) component;

                    // First priority: robot with active dialog
                    if (robot.talk && !robot.dialogComplete) {
                        return robot;
                    }

                    // Second priority: robot with speech bubble
                    if (robot.speech && !robot.talk) {
                        robotWithSpeech = robot;
                    }
                }
            }
        }

        // Regular component search
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Robot) {
                Robot robot = (Robot) component;

                // First priority: robot with active dialog
                if (robot.talk && !robot.dialogComplete) {
                    return robot;
                }

                // Second priority: robot with speech bubble (can be clicked)
                if (robot.speech && !robot.talk) {
                    robotWithSpeech = robot;
                }
            } else if (component instanceof JLayeredPane) {
                // Special handling for JLayeredPane
                Robot result = findRobotWithActiveDialogInScene((JLayeredPane) component);
                if (result != null) {
                    return result;
                }
            } else if (component instanceof Container) {
                // Recursively search in nested containers
                Robot result = findRobotWithActiveDialogInScene((Container) component);
                if (result != null) {
                    // If the recursive search found a robot with active dialog, return it
                    if (result.talk && !result.dialogComplete) {
                        return result;
                    }

                    // If we found a robot with speech bubble, keep track of it
                    if (result.speech && !result.talk && robotWithSpeech == null) {
                        robotWithSpeech = result;
                    }
                }
            }
        }

        // Special handling for Scene1A which has a public robot field
        if (container instanceof Scene1A) {
            Scene1A scene1A = (Scene1A) container;
            if (scene1A.robotDogLeash != null) {
                if (scene1A.robotDogLeash.talk && !scene1A.robotDogLeash.dialogComplete) {
                    return scene1A.robotDogLeash;
                }

                if (scene1A.robotDogLeash.speech && !scene1A.robotDogLeash.talk) {
                    robotWithSpeech = scene1A.robotDogLeash;
                }
            }
        }

        // Return robot with speech bubble if no robot with active dialog was found
        return robotWithSpeech;
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
