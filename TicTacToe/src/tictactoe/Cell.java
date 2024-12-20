/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package tictactoe;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static final int SIZE = 120; // cell width/height (square)
    public static final int PADDING = SIZE / 5; // Padding for the symbol within the cell
    public static final int SEED_SIZE = SIZE - PADDING * 2; // Size of the seed (cross or nought)
    public static final int SEED_STROKE_WIDTH = 8; // pen's stroke width for drawing

    // Define properties
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    Seed content;
    /** Row and column of this cell */
    int row, col;

    // Static images for cross and nought symbols
    private static Image imgCross;
    private static Image imgNought;

    // Load images statically (for cross and nought)
    static {
        try {
            imgCross = ImageIO.read(Cell.class.getResource("/zombie.gif")); // Replace with your cross image path
            imgNought = ImageIO.read(Cell.class.getResource("/shooter.gif")); // Replace with your nought image path
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading images for Cross and Nought!");
        }
    }

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED; // Default content is no seed (empty)
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED; // Reset the content to no seed (empty)
    }

    /** Paint itself on the graphics canvas, given the Graphics context and cell dimensions */
    public void paint(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Draw the appropriate image or symbol
        if (content == Seed.CROSS && imgCross != null) {
            g.drawImage(imgCross, x + PADDING, y + PADDING, width - 2 * PADDING, height - 2 * PADDING, null);
        } else if (content == Seed.NOUGHT && imgNought != null) {
            g.drawImage(imgNought, x + PADDING, y + PADDING, width - 2 * PADDING, height - 2 * PADDING, null);
        }
    }

}
