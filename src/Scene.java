public enum Scene {
    MAIN_MENU("Main Menu"),
    CHANGE_NAME("Change Name"),
    SCENE_1NEWS("Scene 1 News"),
    SCENE_1A("Scene 1A"),
    SCENE_1B("Scene 1B"),
	SCENE_1C("Scene 1C"),
    SCENE_2A("Scene 2A"),
    SCENE_2B("Scene 2B"),
    SCENE_3A("Scene 3A"),
    SCENE_3B("Scene 3B"),;

    public final String label;

    Scene(String label) {
        this.label = label;
    }
}
