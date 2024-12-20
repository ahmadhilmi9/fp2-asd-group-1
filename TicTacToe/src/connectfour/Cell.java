/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package connectfour;

import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static final int SIZE = 120; // cell width/height (square)
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    // Define properties (package-visible)
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    Seed content;
    /** Row and column of this cell */
    int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g, int x, int y, int width, int height) {
        // Draw cell background with dark color
        g.setColor(new Color(30, 30, 30));  // Dark grey for background
        g.fillRect(x, y, width, height);

        // Draw cell border with a lighter contrast color
        g.setColor(new Color(90, 90, 90));  // Light grey for the border
        g.drawRect(x, y, width, height);

        // Draw the content image (Seed.CROSS or Seed.NOUGHT) if applicable
        if (content == Seed.CROSS || content == Seed.NOUGHT) {
            // Get the image
            Image image = content.getImage();

            // Calculate scaling to fit within the cell
            int maxImageSize = Math.min(width, height) - 10; // Leave padding
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            // Scale image while maintaining aspect ratio
            double aspectRatio = (double) imageWidth / imageHeight;
            int scaledWidth, scaledHeight;
            if (imageWidth > imageHeight) {
                scaledWidth = maxImageSize;
                scaledHeight = (int) (maxImageSize / aspectRatio);
            } else {
                scaledHeight = maxImageSize;
                scaledWidth = (int) (maxImageSize * aspectRatio);
            }

            // Calculate top-left position to center the scaled image
            int centerX = x + (width - scaledWidth) / 2;
            int centerY = y + (height - scaledHeight) / 2;

            // Draw the scaled image
            g.drawImage(image, centerX, centerY, scaledWidth, scaledHeight, null);
        }
    }
}