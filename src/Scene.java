/**
 * Enumerates all game scenes and their labels for navigation. 
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */

public enum Scene {
    MAIN_MENU("Main Menu - Return to title screen"),
    CHANGE_NAME("Change Name - Modify player name"),
    SCENE_1NEWS("Scene 1 News - Initial environmental report"),
    SCENE_1A("Scene 1A - Garbage investigation with Robot"),
    SCENE_1B("Scene 1B - Interaction with townspeople"),
    SCENE_1C("Scene 1C - Tree planting activity"),
    SCENE_1D("Scene 1D - Air pollution encounter"),
    SCENE_1COMPLETE("Scene 1 Explanation - Environmental damage summary"),
    SCENE_2A("Scene 2A - Recycling challenge"),
    SCENE_2B("Scene 2 Explanation - Recycling importance"),
    SCENE_3NEWS("Scene 3 News - Climate change report"),
    SCENE_3A("Scene 3A - Community garden initiative"),
    SCENE_3B("Scene 3B - Green energy solutions"),
    SCENE_3C("Scene 3 Explanation - Future sustainability"),
    EXIT_SCENE("Exit Scene - Game completion and credits"),
    ;

    public final String label;

    Scene(String label) {
        this.label = label;
    }
}
