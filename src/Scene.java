/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Enumerates all game scenes and their labels for navigation.
 */

public enum Scene {
    MAIN_MENU("Main Menu"),
    CHANGE_NAME("Change Name"),
    SCENE_1NEWS("Scene 1 News"),
    SCENE_1A("Scene 1A"),
    SCENE_1B("Scene 1B"),
    SCENE_1C("Scene 1C"),
    SCENE_1D("Scene 1D"),
    SCENE_1COMPLETE("Scene 1 Explanation"),
    SCENE_2A("Scene 2A"),
    SCENE_2B("Scene 2 Explanation"),
	SCENE_3NEWS("Scene 3 News"),
    SCENE_3A("Scene 3A"),
    SCENE_3B("Scene 3B"),
	SCENE_3C("Scene 3 Explanation"),
    ;

    public final String label;

    Scene(String label) {
        this.label = label;
    }
}
