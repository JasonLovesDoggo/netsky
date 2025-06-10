/**
 * Breaking news scene with dynamic scrolling text
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Scene1News extends AbstractNewsScene {
	/**
	 * Creates a new Scene1News
	 * 
	 * @param sceneManager 	The sceneManager that runs the whole program, passed in so that it can be accessed throughout the class
	 */
    public Scene1News(SceneManager sceneManager) {
        super(sceneManager);
    }
	
	/**
	 * Sets the text for the news
	 * 
	 * @return 	the text for the news
	 */
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
