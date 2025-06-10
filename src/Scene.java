/**
 * Enumerates all game scenes and their labels for navigation.
 *
 * @author Jason Cameron
 * @author Zoe Li
 * <p>
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */

public enum Scene {
    /**
     * The main menu scene, where the user can start the game, change their name, or view the leaderboard
     */
    MAIN_MENU("Main Menu - Return to title screen"),
    /**
     * The scene that allows the user to change their name
     */
    CHANGE_NAME("Change Name - Modify player name"),
    /**
     * Scene that provides context for scene one
     */
    SCENE_1NEWS("Scene 1 News - Initial report and context "),
    /**
     * Scene in which users interact with their first service robot
     */
    SCENE_1A("Scene 1A - Interaction with a service robot "),
    /**
     * Scene in which the user learns why the robot is holding a dog leash
     */
    SCENE_1B("Scene 1B - Interaction with townspeople"),
    /**
     * Scene in which the user interacts with a second robot
     */
    SCENE_1C("Scene 1C - Another one "),
    /**
     * Scene in which the robot rockets up into the sky
     */
    SCENE_1D("Scene 1D - Rocket robot "),
    /**
     * Scene in which the actions of the service robots are explained
     */
    SCENE_1COMPLETE("Scene 1 Explanation - Shortcut learning and explainations"),
    /**
     * Scene in which the garbage truck picks up everything on the curb
     */
    SCENE_2A("Scene 2A - Recycling challenge"),
    /**
     * Scene in which the actions of the garbage truck are explained
     */
    SCENE_2B("Scene 2 Explanation - Recycling importance"),
    /**
     * Scene in which users are informed that all umbrellas must be turned in
     */
    SCENE_3NEWS("Scene 3 News - Unlawful Umbrellas"),
    /**
     * Scene in which the user views a crowd of people trusting FEANOR and turning in their umbrellas
     */
    SCENE_3A("Scene 3A - Community agreement"),
    /**
     * Scene in which the user hands in their umbrella and then feels rain
     */
    SCENE_3B("Scene 3B - Rain, rain, go away"),
    /**
     * Scene in which it is explained why FEANOR asked everyone to turn in their umbrellas
     */
    SCENE_3C("Scene 3 Explanation - Incorrect conclusions"),
    /**
     * The scene in which the user reads a summary of the game and views the credits
     */
    EXIT_SCENE("Exit Scene - Game completion and credits"),
    ;

    /**
     * The label for the scene, used for display purposes
     */
    public final String label;

    Scene(String label) {
        this.label = label;
    }
}
