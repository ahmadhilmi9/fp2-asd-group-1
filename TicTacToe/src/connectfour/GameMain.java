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
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GameMain extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics
    public static final String TITLE = "Connect Four";
    public static final Color COLOR_BG = new Color(35, 40, 45); // Slightly darker gray background
    public static final Color COLOR_BG_STATUS = new Color(40, 55, 65); // Slightly darker blue-gray background
    public static final Color COLOR_CROSS = new Color(120, 195, 90);  // Darker green for Zombie
    public static final Color COLOR_NOUGHT = new Color(210, 75, 75);  // Darker red for Plant
    public static final Font FONT_STATUS = new Font("Chiller", Font.BOLD, 30);

    // Define game objects
    private Board board;         // the game board
    private State currentState;  // the current state of the game
    private Seed currentPlayer;  // the current player
    private final JLabel statusBar;    // for displaying status message
    private JFrame frame;

    /** Constructor to set up the UI and game components */
    public GameMain(JFrame frame) {
        this.frame = frame;
        setBackground(new Color(35, 40, 45));


        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Calculate the statusBar height
                int statusBarHeight = statusBar.getHeight();

                // Calculate available space and board dimensions
                int availableWidth = getWidth();
                int availableHeight = getHeight() - statusBarHeight;
                int boardSize = Math.min(availableWidth, availableHeight);

                // Calculate offsets to center the board
                int offsetX = (availableWidth - boardSize) / 2;
                int offsetY = (availableHeight - boardSize) / 2;

                // Check if the click is inside the gameboard
                if (mouseX < offsetX || mouseX > offsetX + boardSize || mouseY < offsetY || mouseY > offsetY + boardSize) {
                    return; // Ignore clicks outside the gameboard
                }

                // Convert mouse coordinates to board-relative coordinates
                int boardX = mouseX - offsetX;
                int boardY = mouseY - offsetY;

                // Determine the clicked cell
                int cellWidth = boardSize / Board.COLS;  // Calculate cell width based on columns
                int cellHeight = boardSize / Board.ROWS; // Calculate cell height based on rows
                int col = boardX / cellWidth;
                int row = boardY / cellHeight;

                if (currentState == State.PLAYING) {
                    if (col >= 0 && col < Board.COLS) {
                        for (int r = Board.ROWS - 1; r >= 0; r--) {
                            if (board.cells[r][col].content == Seed.NO_SEED) {
                                // Update cells[][] and return the new game state after the move
                                currentState = board.stepGame(currentPlayer, r, col);
                                // Switch player
                                // Play appropriate sound clip
                                if (currentPlayer == Seed.CROSS) {
                                    SoundEffect.ZOMBIE.play();
                                } else if (currentPlayer == Seed.NOUGHT) {
                                    SoundEffect.PLANT.play();
                                }

                                if (currentState == State.CROSS_WON) {
                                    SoundEffect.ZOMBIEWIN.play();
                                    tictactoe.SoundEffect.BGSOUND.stop();
                                } else if (currentState == State.NOUGHT_WON) {
                                    SoundEffect.PLANTWIN.play();
                                    tictactoe.SoundEffect.BGSOUND.stop();
                                } else if (currentState == State.DRAW) {
                                    SoundEffect.DRAWSOUND.play();
                                    tictactoe.SoundEffect.BGSOUND.stop();
                                }
                                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                                break;
                            }
                        }
                    }
                } else { // game over
                    newGame();  // restart the game
                }
                // Refresh the drawing canvas
                repaint();  // Callback paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        // account for statusBar in height
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
        addMenuBar();
    }
    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> {
            tictactoe.SoundEffect.BGSOUND.stop();

            frame.setContentPane(new main.WelcomeScreen(frame));
            frame.revalidate();
        });

        JMenuItem restartGameItem = new JMenuItem("Restart Game");
        restartGameItem.addActionListener(e -> {
            newGame();
            repaint();
        });

        fileMenu.add(newGameItem);
        fileMenu.add(restartGameItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board();  // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        tictactoe.SoundEffect.BGSOUND.play();
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS;    // cross plays first
        currentState = State.PLAYING;  // ready to play
    }

    /** Custom painting codes on this JPanel */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int statusBarHeight = statusBar.getHeight();
        int availableWidth = getWidth();
        int availableHeight = getHeight() - statusBarHeight;
        int boardSize = Math.min(availableWidth, availableHeight);

        // Center the board
        int offsetX = (availableWidth - boardSize) / 2;
        int offsetY = (availableHeight - boardSize) / 2;

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "ZOMBIE's Turn" : "PLANT's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.YELLOW);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'ZOMBIE' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.GREEN);
            statusBar.setText("'PLANT' Won! Click to play again.");
        }

        // Render the board
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(offsetX, offsetY);
        board.paint(g2d, boardSize);
        g2d.translate(-offsetX, -offsetY);
    }

    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                GameMain gamePanel = new GameMain(frame);
                // Set the content-pane of the JFrame to an instance of main JPanel
                frame.setContentPane(gamePanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setBackground(new Color(53, 55, 75));
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }
}