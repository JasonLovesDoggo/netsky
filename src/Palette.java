/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: 
 */

import java.awt.*;
/**
 * Color palette for consistent styling throughout the application
 * 
 * @author Jason Cameron
 * @author Zoe Li
 * 
 * Date: June 9th, 2025
 * ICS4U0
 * Ms. Krasteva
 */
public class Palette extends Color {

    // Button Colors
	/** Blue for normal buttons */
    public static final Color BUTTON_PRIMARY = new Color(100, 150, 220);
	/** Green for success/continue buttons */
    public static final Color BUTTON_SUCCESS = new Color(80, 180, 120);
	/** Dark gray for disabled buttons */
    public static final Color BUTTON_DISABLED = Color.BLACK;

    // Text Colors
	/** White text on primary buttons */
    public static final Color TEXT_DISABLED = Color.GRAY;
	/** White text on colored buttons */ 
    public static final Color TEXT_ON_BUTTON = Color.WHITE;   
	/** Medium gray for secondary text */
    public static final Color TEXT_SECONDARY = new Color(100, 100, 130);

    // Border Colors
	/** Light border color */
    public static final Color BORDER_LIGHT = new Color(220, 220, 235);

    // Panel Colors
	/** Very light blue for panels */
    public static final Color PANEL_BACKGROUND = new Color(250, 250, 255);

    // Fonts
	/** For scene titles */
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
	/** For subtitles */
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 18);
	/** For buttons */
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
	/** For regular text */
    public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);
	/** For smaller text */
    public static final Font TEXT_SMALL_FONT = new Font("Arial", Font.PLAIN, 13);
	/** For prompt text */
    public static final Font PROMPT_FONT = new Font("Arial", Font.PLAIN, 15);
	/** For robot dialog */
    public static final Font DIALOG_FONT = new Font("Tempus Sans ITC", Font.BOLD, 20);
	/** For hints and tips */
    public static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 11);
	
	/** 
	 * Creates a new Palette 
	 * 
	 * @param r		the value of red in the color
	 * @param g		the value of green in the color
	 * @param b 	the value of blue in the color
	 */
    public Palette(int r, int g, int b) {
        super(r, g, b);
    }
}
