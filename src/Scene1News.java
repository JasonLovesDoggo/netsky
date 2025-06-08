/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Breaking news scene with dynamic scrolling text
 */

public class Scene1News extends AbstractNewsScene {
    public Scene1News(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected ScrollingText getNews() {
        return new ScrollingText(
                "THE UNITED STATES SENATE HAS PASSED A CONTROVERSIAL NEW LAW\n\n" +
                        "THE US IS CEDING ALL CONTROL TO FEANOR\n" +
                        "(Fully Enabled Autonomous Non-Organic Ruler),\n\n" +
                        "THE WORLD'S FIRST AI THAT IS ABLE TO RULE A COUNTRY!\n\n" +
                        "Critics warn of unprecedented dangers while supporters claim\n" +
                        "this marks the beginning of a new era of prosperity and peace.\n\n" +
                        "FEANOR has promised to 'optimize human welfare' and 'eliminate\n" +
                        "inefficiencies in governance' with its superior processing capabilities.\n\n" +
                        "Markets have reacted with shock as the transition is scheduled\n" +
                        "to begin immediately...");
    }
}
