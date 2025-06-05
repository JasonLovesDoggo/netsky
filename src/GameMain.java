/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 *
 */

/*
Names: Jason Cameron, Zoe Li
Date: Jun 9th, 2025
Teacher: Ms. Krasteva
Description: 

Our final project!
This project, MEDICI, is designed to teach the user about shortcut ai in an interactive and fun way.
It takes place in a future world in which the US has been handed over to FEANOR, the Fully Enabled 
Autonomous Non-Organic Ruler. It's now FEANOR's first week on the job, and little mini robots 
have been sent out to all citizens free of charge!

The first scene consists of a robot mistaking a leash for a dog and the bird for the sky. 
The second scene is a garbage truck that picks up everything on the curb, including a parked
bike and several unfortunate pedestrians.
The third scene is when FEANOR, the AI commands that all umbrellas be turned in to "prevent all
citizens from storms". 
The user then turns in their own umbrella, but of course, it rains anyways. 

Thus ends the first level, and the user enters the second level, which explains what shortcut learning
is and how it impacted the robots and their actions. 

*/

import javax.swing.*;

public class GameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame gameFrame = new GameFrame("My Game");
                gameFrame.setVisible(true);
            }
        });
    }
}

