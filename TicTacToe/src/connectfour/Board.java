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
 * The Board class models the ROWS-by-COLS game board.
 */
public class Board {
    // Define named constants
    public static final int ROWS = 6;  // ROWS x COLS cells
    public static final int COLS = 7;
    // Define named constants for drawing
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final int GRID_WIDTH = 8;  // Grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    public static final Color COLOR_GRID = new Color(90, 45, 45);  // grid lines
    public static final int Y_OFFSET = 1;  // Fine tune for better display

    // Define properties (package-visible)
    /** Composes of 2D array of ROWS-by-COLS Cell instances */
    Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the game objects (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS]; // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
                // Cells are initialized in the constructor
            }
        }
    }

    /** Reset the game board, ready for new game */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame(); // clear the cell content
            }
        }
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Compute and return the new game state
        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Nobody win. Check for DRAW (all cells occupied) or PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING; // still have empty cells
                    }
                }
            }
            return State.DRAW; // no empty cell, it's a draw
        }
    }

    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
        // Check for 4-in-a-line on the rowSelected
        int count = 0;
        for (int col = 0; col < COLS; ++col) {
            if (cells[rowSelected][col].content == theSeed) {
                ++count;
                if (count == 4) return true;  // found
            } else {
                count = 0; // reset and count again if not consecutive
            }
        }

        // Check for 4-in-a-line on the colSelected
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][colSelected].content == theSeed) {
                ++count;
                if (count == 4) return true;  // found
            } else {
                count = 0; // reset and count again if not consecutive
            }
        }

        // Check for 4-in-a-line on the diagonal (top-left to bottom-right)
        count = 0;
        int startRow = Math.max(0, rowSelected - colSelected);
        int startCol = Math.max(0, colSelected - rowSelected);
        while (startRow < ROWS && startCol < COLS) {
            if (cells[startRow][startCol].content == theSeed) {
                ++count;
                if (count == 4) return true;  // found
            } else {
                count = 0; // reset and count again if not consecutive
            }
            startRow++;
            startCol++;
        }

        // Check for 4-in-a-line on the anti-diagonal (top-right to bottom-left)
        count = 0;
        startRow = Math.max(0, rowSelected - (COLS - 1 - colSelected));
        startCol = Math.min(COLS - 1, colSelected + rowSelected);
        while (startRow < ROWS && startCol >= 0) {
            if (cells[startRow][startCol].content == theSeed) {
                ++count;
                if (count == 4) return true;  // found
            } else {
                count = 0; // reset and count again if not consecutive
            }
            startRow++;
            startCol--;
        }

        return false;  // no 4-in-a-line found
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g, int boardSize) {
        int cellWidth = boardSize / COLS;  // Calculate cell width based on columns
        int cellHeight = boardSize / ROWS; // Calculate cell height based on rows

        // Draw grid
        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; row++) {
            g.fillRect(0, row * cellHeight - GRID_WIDTH / 2, boardSize, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; col++) {
            g.fillRect(col * cellWidth - GRID_WIDTH / 2, 0, GRID_WIDTH, boardSize);
        }

        // Draw cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;
                cells[row][col].paint(g, x, y, cellWidth, cellHeight);
            }
        }
    }
}