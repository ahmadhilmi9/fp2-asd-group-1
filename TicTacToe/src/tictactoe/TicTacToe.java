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
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Zombie vs Plant - Tic Tac Toe";
    public static final Color COLOR_BG = new Color(30, 30, 30); // Slightly darker gray background
    public static final Color COLOR_BG_STATUS = new Color(40, 55, 65); // Slightly darker blue-gray background
    public static final Color COLOR_CROSS = new Color(120, 195, 90);  // Darker green for Zombie
    public static final Color COLOR_NOUGHT = new Color(210, 75, 75);  // Darker red for Plant
    public static final Font FONT_STATUS = new Font("Chiller", Font.BOLD, 30);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer aiPlayer;

    private JFrame frame;
    private Seed playerSeed;
    private boolean isSinglePlayer;

    public TicTacToe(JFrame frame, Seed playerSeed, boolean isSinglePlayer) {
        this.frame = frame;
        this.isSinglePlayer = isSinglePlayer;
        this.playerSeed = playerSeed;
        SoundEffect.BGSOUND.loop();

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int statusBarHeight = statusBar.getHeight();
                int availableWidth = getWidth();
                int availableHeight = getHeight() - statusBarHeight;
                int boardSize = Math.min(availableWidth, availableHeight);

                int offsetX = (availableWidth - boardSize) / 2;
                int offsetY = (availableHeight - boardSize) / 2;

                if (mouseX < offsetX || mouseX > offsetX + boardSize || mouseY < offsetY || mouseY > offsetY + boardSize) {
                    return;
                }

                int boardX = mouseX - offsetX;
                int boardY = mouseY - offsetY;
                int cellSize = boardSize / Board.ROWS;
                int row = boardY / cellSize;
                int col = boardX / cellSize;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);

                        if (currentPlayer == Seed.CROSS) {
                            SoundEffect.ZOMBIE.play();
                        } else if (currentPlayer == Seed.NOUGHT) {
                            SoundEffect.PLANT.play();
                        }

                        if (currentState == State.CROSS_WON) {
                            SoundEffect.ZOMBIEWIN.play();
                        } else if (currentState == State.NOUGHT_WON) {
                            SoundEffect.PLANTWIN.play();
                        } else if (currentState == State.DRAW) {
                            SoundEffect.DRAWSOUND.play();
                        }

                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                        if (isSinglePlayer && currentState == State.PLAYING && currentPlayer != playerSeed) {
                            makeAIMove();
                        }
                    }
                } else {
                    SoundEffect.BGSOUND.loop();
                    newGame();
                }

                repaint();
            }

        });

        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END);

        initGame();
        newGame();

        addMenuBar();
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> {
            SoundEffect.BGSOUND.stop();
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

    public void initGame() {
        board = new Board();
        if (isSinglePlayer) {
            aiPlayer = new AIPlayerMinimax(board);
        }
    }

    private void makeAIMove() {
        if (currentState == State.PLAYING) {
            int[] aiMove = aiPlayer.move();
            int row = aiMove[0];
            int col = aiMove[1];

            currentState = board.stepGame(currentPlayer, row, col);

            if (currentPlayer == Seed.CROSS) {
                SoundEffect.ZOMBIE.play();
            } else if (currentPlayer == Seed.NOUGHT) {
                SoundEffect.PLANT.play();
            }

            if (currentState == State.CROSS_WON) {
                SoundEffect.ZOMBIEWIN.play();
            } else if (currentState == State.NOUGHT_WON) {
                SoundEffect.PLANTWIN.play();
            } else if (currentState == State.DRAW) {
                SoundEffect.DRAWSOUND.play();
            }

            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        }
    }

    public void newGame() {
        board.newGame();
        currentState = State.PLAYING;
        SoundEffect.BGSOUND.loop();

        if (playerSeed == Seed.CROSS) {
            currentPlayer = Seed.CROSS;
            if (aiPlayer != null) aiPlayer.setSeed(Seed.NOUGHT);
        } else {
            currentPlayer = Seed.CROSS;
            if (aiPlayer != null) aiPlayer.setSeed(Seed.CROSS);

            if (isSinglePlayer) {
                makeAIMove();
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);

        int statusBarHeight = statusBar.getHeight();
        int availableWidth = getWidth();
        int availableHeight = getHeight() - statusBarHeight;

        int boardSize = Math.min(availableWidth, availableHeight);
        int offsetX = (availableWidth - boardSize) / 2;
        int offsetY = (availableHeight - boardSize) / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(offsetX, offsetY);
        board.paint(g2d, boardSize);
        g2d.translate(-offsetX, -offsetY);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "ZOMBIE's Turn" : "PLANT's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.ORANGE);
            statusBar.setText("It's a Draw! Click to play again.");
            SoundEffect.BGSOUND.stop();
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'ZOMBIE' Won! Click to play again.");
            SoundEffect.BGSOUND.stop();
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.GREEN);
            statusBar.setText("'PLANT' Won! Click to play again.");
            SoundEffect.BGSOUND.stop();
        }
    }

    public void play() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new TicTacToe(frame, Seed.CROSS, isSinglePlayer));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
