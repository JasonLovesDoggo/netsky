/*
 * Names: Jason Cameron, Zoe Li
 * Date: Jun 9th, 2025
 * Teacher: Ms. Krasteva
 * Description: Color palette for consistent styling throughout the application
 */

import java.awt.*;

public class Palette extends Color {
    // Original ZColor
    public static final Color ZColor = new Color(0xB3CEE5);

    // Button Colors
    public static final Color BUTTON_PRIMARY = new Color(100, 150, 220);     // Blue for normal buttons
    public static final Color BUTTON_SUCCESS = new Color(80, 180, 120);      // Green for success/continue buttons
    public static final Color BUTTON_DISABLED = Color.BLACK;       // Dark gray for disabled buttons

    // Text Colors
    public static final Color TEXT_DISABLED = Color.GRAY;                // White text on primary buttons
    public static final Color TEXT_ON_BUTTON = Color.WHITE;                  // White text on colored buttons
    public static final Color TEXT_SECONDARY = new Color(100, 100, 130);     // Medium gray for secondary text

    // Border Colors
    public static final Color BORDER_LIGHT = new Color(220, 220, 235);       // Light border color

    // Panel Colors
    public static final Color PANEL_BACKGROUND = new Color(250, 250, 255);   // Very light blue for panels

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);           // For scene titles
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 18);        // For subtitles
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);          // For buttons
    public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);           // For regular text
    public static final Font TEXT_SMALL_FONT = new Font("Arial", Font.PLAIN, 13);     // For smaller text
    public static final Font PROMPT_FONT = new Font("Arial", Font.PLAIN, 15);         // For prompt text
    public static final Font DIALOG_FONT = new Font("Tempus Sans ITC", Font.BOLD, 20); // For robot dialog
    public static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 11);          // For hints and tips

    public Palette(int r, int g, int b) {
        super(r, g, b);
    }
}
