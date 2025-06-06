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
    public static final Color BUTTON_WARNING = new Color(220, 150, 80);      // Orange for warning buttons
    public static final Color BUTTON_DANGER = new Color(220, 100, 100);      // Red for danger/delete buttons

    // Text Colors
    public static final Color TEXT_ON_BUTTON = Color.WHITE;                  // White text on colored buttons
    public static final Color TEXT_PRIMARY = new Color(70, 70, 80);          // Dark gray for primary text
    public static final Color TEXT_SECONDARY = new Color(100, 100, 130);     // Medium gray for secondary text

    // Background Colors
    public static final Color BG_LIGHT = new Color(248, 248, 252);           // Very light blue-gray for backgrounds
    public static final Color BG_GRADIENT_TOP = new Color(240, 240, 250);    // Light blue-gray for gradient tops
    public static final Color BG_GRADIENT_BOTTOM = new Color(230, 230, 245); // Slightly darker for gradient bottoms

    // Border Colors
    public static final Color BORDER_LIGHT = new Color(220, 220, 235);       // Light border color
    public static final Color BORDER_MEDIUM = new Color(200, 200, 220);      // Medium border color

    // Panel Colors
    public static final Color PANEL_BACKGROUND = new Color(250, 250, 255);   // Very light blue for panels

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);           // For scene titles
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 18);        // For subtitles
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);          // For buttons
    public static final Font BUTTON_SMALL_FONT = new Font("Arial", Font.BOLD, 12);    // For smaller buttons
    public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);           // For regular text
    public static final Font TEXT_SMALL_FONT = new Font("Arial", Font.PLAIN, 13);     // For smaller text
    public static final Font PROMPT_FONT = new Font("Arial", Font.PLAIN, 15);         // For prompt text
    public static final Font DIALOG_FONT = new Font("Tempus Sans ITC", Font.BOLD, 20); // For robot dialog
    public static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 11);          // For hints and tips

    public Palette(int r, int g, int b) {
        super(r, g, b);
    }
}
