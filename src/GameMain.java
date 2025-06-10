import javax.swing.*;

/**
 * This project, MEDICI, is designed to teach the user about shortcut ai in an interactive and fun way.
 * It takes place in a future world in which the US has been handed over to FEANOR, the Fully Enabled 
 * Autonomous Non-Organic Ruler. It's now FEANOR's first week on the job, and little mini robots 
 * have been sent out to all citizens free of charge!
 * 
 * The first level follows up the news scene immediately. It consists of a robot mistaking a leash for a 
 * dog and the bird for the sky. This is followed by an explaination of what happened, and why, including 
 * a basic explaination of shortcut learning.
 * 
 * The second level is a garbage truck that picks up everything on the curb, including a parked
 * bike and several unfortunate pedestrians. This level, again, is explained thoroughly directly
 * after the level finishes. 

 * The third level is when FEANOR, the AI commands that all umbrellas be turned in to "prevent all
 * citizens from storms". The user then turns in their own umbrella, but of course, it rains anyways. 
 * It is again explained immediately after, a case of FEANOR mistaking correlation with causation. 
 * 
 * The game ends off with a closing scene, which summarizes everything that was taught
 * in the game. 
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
*/
public class GameMain {
	/** 
	 * The main method that initializes and runs the entire game. 
	 */
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

